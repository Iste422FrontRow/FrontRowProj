import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class EdgeTable {
   public Logger log = LogManager.getLogger(EdgeTable.class);

   private int numFigure;
   private String name;
   private ArrayList alRelatedTables, alNativeFields;
   private int[] relatedTables, relatedFields, nativeFields;
   
   public EdgeTable(String inputString) {

      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      log.info("Creating row in edge table based on .edg file Name:" + name);
      alRelatedTables = new ArrayList();
      alNativeFields = new ArrayList();
   }
   
   public int getNumFigure() {
      return numFigure;
   }
   
   public String getName() {
      return name;
   }
   
   public void addRelatedTable(int relatedTable) {
      log.debug("Connecting:" + name + "in edge tables array with index:" + numFigure+ "to table with index" + relatedTable);
      alRelatedTables.add(new Integer(relatedTable));
   }
   
   public int[] getRelatedTablesArray() {
      log.debug(name + " table is getting all associated tables indices");
      return relatedTables;
   }
   
   public int[] getRelatedFieldsArray() {
      log.debug(name + " is getting all associated related fields indices");
      return relatedFields;
   }
   
   public void setRelatedField(int index, int relatedValue) {
      log.debug(name + "setting associated field in relatedFields with given index and value from outside array");
      relatedFields[index] = relatedValue;
   }
   
   public int[] getNativeFieldsArray() {
      log.debug(name + " is getting all associated native fields indices as an array");
      return nativeFields;
   }

   public void addNativeField(int value) {
      log.debug("Connecting:" + name + "in edge tables array with its native fields with value/index " + value );

      alNativeFields.add(new Integer(value));
   }

   public void moveFieldUp(int index) {
      log.info("Element is moved up in the array of native fields");
//move the field closer to the beginning of the list
      if (index == 0) {
         return;
      }
      int tempNative = nativeFields[index - 1]; //save element at destination index
      nativeFields[index - 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index - 1]; //save element at destination index
      relatedFields[index - 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
   }
   
   public void moveFieldDown(int index) { //move the field closer to the end of the list
      log.info("Element is moved down in the array of native fields");

      if (index == (nativeFields.length - 1)) {
         return;
      }
      int tempNative = nativeFields[index + 1]; //save element at destination index
      nativeFields[index + 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index + 1]; //save element at destination index
      relatedFields[index + 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
   }

   public void makeArrays() { //convert the ArrayLists into int[]
      log.info(name + ": Converting arraylists into an arrays");
      Integer[] temp;
      temp = (Integer[])alNativeFields.toArray(new Integer[alNativeFields.size()]);
      nativeFields = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         nativeFields[i] = temp[i].intValue();
      }
      log.debug(name + ": Converted nativefields into an array");
      
      temp = (Integer[])alRelatedTables.toArray(new Integer[alRelatedTables.size()]);
      relatedTables = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         relatedTables[i] = temp[i].intValue();
      }
      log.debug(name + ": Converted relatedTables into an array");
      
      relatedFields = new int[nativeFields.length];
      for (int i = 0; i < relatedFields.length; i++) {
         relatedFields[i] = 0;
      }
      log.debug(name + ": Converted relatedFields into an array");

   }

   public String toString() {
      log.info(name + " calls toString()");

      StringBuffer sb = new StringBuffer();
      sb.append("Table: " + numFigure + "\r\n");
      sb.append("{\r\n");
      sb.append("TableName: " + name + "\r\n");
      sb.append("NativeFields: ");
      for (int i = 0; i < nativeFields.length; i++) {
         sb.append(nativeFields[i]);
         if (i < (nativeFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\nRelatedTables: ");
      for (int i = 0; i < relatedTables.length; i++) {
         sb.append(relatedTables[i]);
         if (i < (relatedTables.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\nRelatedFields: ");
      for (int i = 0; i < relatedFields.length; i++) {
         sb.append(relatedFields[i]);
         if (i < (relatedFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\n}\r\n");
      log.debug(name + " created a string of itself");
      
      return sb.toString();
   }
}
