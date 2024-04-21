import java.io.*;
import java.util.*;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class  EdgeConvertFileParser {
   //private String filename = "test.edg";
   private File parseFile;
   public ArrayList alTables, alFields, alConnectors;
   private EdgeTable[] tables;
   private EdgeField[] fields;
   private EdgeConnector[] connectors;
   private BufferedReader br;


   public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this
   public static final String DELIM = "|";
   public static Logger log = LogManager.getLogger(EdgeConvertFileParser.class);

   
   public EdgeConvertFileParser(File constructorFile) {
      parseFile = constructorFile;
   }
   public EdgeConvertFileParser() {

   }
   
   protected void resolveConnectors() { //Identify nature of Connector endpoints
      log.debug("Resolving connector endpoint");
      int endPoint1, endPoint2;
      int fieldIndex = 0, table1Index = 0, table2Index = 0;
      for (int cIndex = 0; cIndex < connectors.length; cIndex++) {
         endPoint1 = connectors[cIndex].getEndPoint1();
         endPoint2 = connectors[cIndex].getEndPoint2();
         fieldIndex = -1;
         for (int fIndex = 0; fIndex < fields.length; fIndex++) { //search fields array for endpoints
            if (endPoint1 == fields[fIndex].getNumFigure()) { //found endPoint1 in fields array
               connectors[cIndex].setIsEP1Field(true); //set appropriate flag
               fieldIndex = fIndex; //identify which element of the fields array that endPoint1 was found in
            }
            if (endPoint2 == fields[fIndex].getNumFigure()) { //found endPoint2 in fields array
               connectors[cIndex].setIsEP2Field(true); //set appropriate flag
               fieldIndex = fIndex; //identify which element of the fields array that endPoint2 was found in
            }
         }
         for (int tIndex = 0; tIndex < tables.length; tIndex++) { //search tables array for endpoints
            if (endPoint1 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
               connectors[cIndex].setIsEP1Table(true); //set appropriate flag
               table1Index = tIndex; //identify which element of the tables array that endPoint1 was found in
            }
            if (endPoint2 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
               connectors[cIndex].setIsEP2Table(true); //set appropriate flag
               table2Index = tIndex; //identify which element of the tables array that endPoint2 was found in
            }
         }
         
         if (connectors[cIndex].getIsEP1Field() && connectors[cIndex].getIsEP2Field()) { //both endpoints are fields, implies lack of normalization
            JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile + "\ncontains composite attributes. Please resolve them and try again.");
            log.warn("Edge Diagram file had issues");
            EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
            break; //stop processing list of Connectors
         }

         if (connectors[cIndex].getIsEP1Table() && connectors[cIndex].getIsEP2Table()) { //both endpoints are tables
            if ((connectors[cIndex].getEndStyle1().indexOf("many") >= 0) &&
                (connectors[cIndex].getEndStyle2().indexOf("many") >= 0)) { //the connector represents a many-many relationship, implies lack of normalization
               JOptionPane.showMessageDialog(null, "There is a many-many relationship between tables\n\"" + tables[table1Index].getName() + "\" and \"" + tables[table2Index].getName() + "\"" + "\nPlease resolve this and try again.");
               log.warn("Edge Diagram file had a m-n relationship");
               EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
               break; //stop processing list of Connectors
            } else { //add Figure number to each table's list of related tables
               tables[table1Index].addRelatedTable(tables[table2Index].getNumFigure());
               tables[table2Index].addRelatedTable(tables[table1Index].getNumFigure());
               continue; //next Connector
            }
         }
         log.debug("Making sure each field has been connected to table");
         if (fieldIndex >=0 && fields[fieldIndex].getTableID() == 0) { //field has not been assigned to a table yet
            if (connectors[cIndex].getIsEP1Table()) { //endpoint1 is the table
               tables[table1Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
               fields[fieldIndex].setTableID(tables[table1Index].getNumFigure()); //tell the field what table it belongs to
            } else { //endpoint2 is the table
               tables[table2Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
               fields[fieldIndex].setTableID(tables[table2Index].getNumFigure()); //tell the field what table it belongs to
            }
         } else if (fieldIndex >=0) { //field has already been assigned to a table
            JOptionPane.showMessageDialog(null, "The attribute " + fields[fieldIndex].getName() + " is connected to multiple tables.\nPlease resolve this and try again.");
            log.warn("Atr has been assigned to to many tables");
            EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
            break; //stop processing list of Connectors
         }
      } // connectors for() loop
   } // resolveConnectors()
   

   protected void makeArrays() { //convert ArrayList objects into arrays of the appropriate Class type
      log.debug("Converting ArrayList into arrays");
      if (alTables != null) {
         tables = (EdgeTable[])alTables.toArray(new EdgeTable[alTables.size()]);
      }
      if (alFields != null) {
         fields = (EdgeField[])alFields.toArray(new EdgeField[alFields.size()]);
      }
      if (alConnectors != null) {
         connectors = (EdgeConnector[])alConnectors.toArray(new EdgeConnector[alConnectors.size()]);
      }
   }
   
   public EdgeTable[] getEdgeTables() {
      log.debug("getting edge tables");
      return tables;
   }
   
   public EdgeField[] getEdgeFields() {
      log.debug("getting the edge fields");
      return fields;
   }
   public abstract void openFile(File inputFile);
   
//   public void openFile(File inputFile) {
//      int numLine = 0;
//      try {
//         log.info("Opening file: {}", inputFile.getName());
//         FileReader fr = new FileReader(inputFile);
//         br = new BufferedReader(fr);
//         EdgeFileParser parser = new EdgeFileParser(inputFile,br);
//         //test for what kind of file we have
//         String currentLine = br.readLine().trim();
//         numLine++;
//         if (currentLine.startsWith(EDGE_ID)) { //the file chosen is an Edge Diagrammer file
//            parser.parseEdgeFile(); //parse the file
//            br.close();
//            this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
//            this.resolveConnectors(); //Identify nature of Connector endpoints
//         } else {
//            if (currentLine.startsWith(SAVE_ID)) { //the file chosen is a Save file created by this application
//               EdgeSaveParser saveparser = new EdgeSaveParser(inputFile);
//               saveparser.parseSaveFile(); //parse the file
//               br.close();
//               this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
//            } else { //the file chosen is something else
//               JOptionPane.showMessageDialog(null, "Unrecognized file format");
//               log.warn("Unrecognized file format");
//            }
//         }
//      } // try
//      catch (FileNotFoundException fnfe) {
//         log.error("Cannot find file: {}", inputFile.getName());
//         System.exit(0);
//      } // catch FileNotFoundException
//      catch (IOException ioe) {
//         log.error("Error reading file: {}", ioe.getMessage());
//         System.exit(0);
//      } // catch IOException
//   } // openFile()
} // EdgeConvertFileHandler
