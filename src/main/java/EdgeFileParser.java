import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeFileParser {
    private BufferedReader br;
    private String currentLine;
    private File parseFile;
    public static Logger log = LogManager.getLogger(EdgeFileParser.class);
    private int numFigure, numConnector, numFields, numTables, numNativeRelatedFields;
    private boolean isEntity, isAttribute, isUnderlined = false;
    private String style;
    private String text;
    public static final String DELIM = "|";
    private ArrayList alTables, alFields, alConnectors;
    private EdgeField tempField;
    private int endPoint1, endPoint2;
    private String endStyle1, endStyle2;







public EdgeFileParser(BufferedReader br, File parseFile){
    numFigure = 0;
    isEntity = false;
    alTables = new ArrayList();
    alFields = new ArrayList();
    alConnectors = new ArrayList();
    this.br = br;


}

public void parseEdgeFile() throws IOException {
log.info("Parsing Edge Diagrammer file: {}", parseFile.getName());
while ((currentLine = br.readLine()) != null) {
    currentLine = currentLine.trim();
    this.parseFigureEntry();
    this.parseConnectorEntry();
}
}

private void parseFigureEntry() throws IOException {
    log.debug("Parsing Figure entry");
    numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Figure number
    currentLine = br.readLine().trim(); // this should be "{"
    currentLine = br.readLine().trim();
    if (!currentLine.startsWith("Style")) { // this is to weed out other Figures, like Labels
       log.warn("Unexpected figure found");
       return;
    } else {
       style = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); //get the Style parameter
       if (style.startsWith("Relation")) { //presence of Relations implies lack of normalization
          JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile + "\ncontains relations.  Please resolve them and try again.");
          EdgeConvertGUI.setReadSuccess(false);
          return;
       } 
       if (style.startsWith("Entity")) {
          isEntity = true;
       }
       if (style.startsWith("Attribute")) {
          isAttribute = true;
       }
       if (!(isEntity || isAttribute)) { //these are the only Figures we're interested in
          return;
       }
       currentLine = br.readLine().trim(); //this should be Text
       text = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")).replaceAll(" ", ""); //get the Text parameter
       if (text.equals("")) {
          log.error("Blank names provided in diagram");
          JOptionPane.showMessageDialog(null, "There are entities or attributes with blank names in this diagram.\nPlease provide names for them and try again.");
          EdgeConvertGUI.setReadSuccess(false);
          return;
       }
       int escape = text.indexOf("\\");
       if (escape > 0) { //Edge denotes a line break as "\line", disregard anything after a backslash
          text = text.substring(0, escape);
       }

       do { //advance to end of record, look for whether the text is underlined
          currentLine = br.readLine().trim();
          if (currentLine.startsWith("TypeUnderl")) {
             isUnderlined = true;
          }
       } while (!currentLine.equals("}")); // this is the end of a Figure entry

       if (isEntity) { //create a new EdgeTable object and add it to the alTables ArrayList
          if (isTableDup(text)) {
             log.error("There are multiple tables with this name");
             JOptionPane.showMessageDialog(null, "There are multiple tables called " + text + " in this diagram.\nPlease rename all but one of them and try again.");
             EdgeConvertGUI.setReadSuccess(false);
             return;
          }
          alTables.add(new EdgeTable(numFigure + DELIM + text));
       }
       if (isAttribute) { //create a new EdgeField object and add it to the alFields ArrayList
          tempField = new EdgeField(numFigure + DELIM + text);
          tempField.setIsPrimaryKey(isUnderlined);
          alFields.add(tempField);
       }
       //reset flags
       isEntity = false;
       isAttribute = false;
       isUnderlined = false;
    }
}


private void parseConnectorEntry() throws IOException {

    if (currentLine.startsWith("Connector ")) { //this is the start of a Connector entry
        numConnector = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Connector number
        currentLine = br.readLine().trim(); // this should be "{"
        currentLine = br.readLine().trim(); // not interested in Style
        currentLine = br.readLine().trim(); // Figure1
        endPoint1 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));
        currentLine = br.readLine().trim(); // Figure2
        endPoint2 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));
        currentLine = br.readLine().trim(); // not interested in EndPoint1
        currentLine = br.readLine().trim(); // not interested in EndPoint2
        currentLine = br.readLine().trim(); // not interested in SuppressEnd1
        currentLine = br.readLine().trim(); // not interested in SuppressEnd2
        currentLine = br.readLine().trim(); // End1
        endStyle1 = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); //get the End1 parameter
        currentLine = br.readLine().trim(); // End2
        endStyle2 = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); //get the End2 parameter

        do { //advance to end of record
           currentLine = br.readLine().trim();
        } while (!currentLine.equals("}")); // this is the end of a Connector entry
        
        alConnectors.add(new EdgeConnector(numConnector + DELIM + endPoint1 + DELIM + endPoint2 + DELIM + endStyle1 + DELIM + endStyle2));
     } // if("Connector")



}




private boolean isTableDup(String testTableName) {
    log.debug("making sure table "+ testTableName+ " is not a dup");
    for (int i = 0; i < alTables.size(); i++) {
       EdgeTable tempTable = (EdgeTable)alTables.get(i);
       if (tempTable.getName().equals(testTableName)) {
          return true;
       }
    }
    return false;
 }



}
