import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;


public class EdgeSaveParser extends EdgeConvertFileParser {
 
   private BufferedReader br;
    private int numFigure;
    public static Logger log = LogManager.getLogger(EdgeSaveParser.class);

    public EdgeSaveParser(File parseFile){
        super();
        numFigure = 0;
        this.openFile(parseFile);

   }

    public void parseSaveFile() throws IOException { //this method is unclear and confusing in places
        log.debug("Changing save file and parsing it");
        StringTokenizer stTables, stNatFields, stRelFields, stField;
        EdgeTable tempTable;
        EdgeField tempField;
        String currentLine = br.readLine();
        currentLine = br.readLine(); //this should be "Table: "
        while (currentLine.startsWith("Table: ")) {
           numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
           currentLine = br.readLine(); //this should be "{"
           currentLine = br.readLine(); //this should be "TableName"
            String tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
           tempTable = new EdgeTable(numFigure + DELIM + tableName);
           
           currentLine = br.readLine(); //this should be the NativeFields list
           stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
            int numFields = stNatFields.countTokens();
           for (int i = 0; i < numFields; i++) {
              tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
           }
           
           currentLine = br.readLine(); //this should be the RelatedTables list
           stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
            int numTables = stTables.countTokens();
           for (int i = 0; i < numTables; i++) {
              tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
           }
           tempTable.makeArrays();
           
           currentLine = br.readLine(); //this should be the RelatedFields list
           stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
           numFields = stRelFields.countTokens();
  
           for (int i = 0; i < numFields; i++) {
              tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
           }
  
           alTables.add(tempTable);
           currentLine = br.readLine(); //this should be "}"
           currentLine = br.readLine(); //this should be "\n"
           currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
        }
        while ((currentLine = br.readLine()) != null) {
           stField = new StringTokenizer(currentLine, DELIM);
           numFigure = Integer.parseInt(stField.nextToken());
            String fieldName = stField.nextToken();
           tempField = new EdgeField(numFigure + DELIM + fieldName);
           tempField.setTableID(Integer.parseInt(stField.nextToken()));
           tempField.setTableBound(Integer.parseInt(stField.nextToken()));
           tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
           tempField.setDataType(Integer.parseInt(stField.nextToken()));
           tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
           tempField.setIsPrimaryKey(Boolean.valueOf(stField.nextToken()).booleanValue());
           tempField.setDisallowNull(Boolean.valueOf(stField.nextToken()).booleanValue());
           if (stField.hasMoreTokens()) { //Default Value may not be defined
              tempField.setDefaultValue(stField.nextToken());
           }
           alFields.add(tempField);
        }

     } // parseSaveFile()
    public void openFile(File inputFile){
        int numLine = 0;
        try {
            log.info("Opening file: {}", inputFile.getName());
            FileReader fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
            //test for what kind of file we have
            String currentLine = br.readLine().trim();
            numLine++;

            if (currentLine.startsWith(SAVE_ID)) { //the file chosen is a Save file created by this applicatio
                parseSaveFile(); //parse the file
                br.close();
                this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
            } else { //the file chosen is something else
                JOptionPane.showMessageDialog(null, "Unrecognized file format");
                log.warn("Unrecognized file format");
            }

        } // try
        catch (FileNotFoundException fnfe) {
            log.error("Cannot find file: {}", inputFile.getName());
            JOptionPane.showMessageDialog(null, "Cannot find file: " + inputFile.getName());

            System.exit(0);
        } // catch FileNotFoundException
        catch (IOException ioe) {
            log.error("Error reading file: {}", ioe.getMessage());
            JOptionPane.showMessageDialog(null, "Error reading file:: " + ioe.getMessage());

            System.exit(0);
        } // catch IOException
    }
}
