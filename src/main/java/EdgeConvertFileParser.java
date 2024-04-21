import java.io.*;
import java.util.*;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class  EdgeConvertFileParser {
   //private String filename = "test.edg";

   protected ArrayList alTables, alFields, alConnectors;
   protected EdgeTable[] tables;
   protected EdgeField[] fields;
   protected EdgeConnector[] connectors;
   public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this
   public static final String DELIM = "|";
   public static Logger log = LogManager.getLogger(EdgeConvertFileParser.class);

   

   public EdgeConvertFileParser() {
      alTables = new ArrayList();
      alFields = new ArrayList();
      alConnectors = new ArrayList();
   }
   

   

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
   

} // EdgeConvertFileHandler
