import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class EdgeField {

   public static Logger logger = LogManager.getLogger(EdgeField.class);
   private int numFigure, tableID, tableBound, fieldBound, dataType, varcharValue;
   private String name, defaultValue;
   private boolean disallowNull, isPrimaryKey;
   private static String[] strDataType = {"Varchar", "Boolean", "Integer", "Double"};
   public static final int VARCHAR_DEFAULT_LENGTH = 1;
   
   public EdgeField(String inputString) {
      logger.info("Edge Field created");
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      try {
         numFigure = Integer.parseInt(st.nextToken());
         name = st.nextToken();
         tableID = 0;
         tableBound = 0;
         fieldBound = 0;
         disallowNull = false;
         isPrimaryKey = false;
         defaultValue = "";
         varcharValue = VARCHAR_DEFAULT_LENGTH;
         dataType = 0;
      } catch (NumberFormatException | NoSuchElementException e) {
         logger.error("Error parsing inputString:", inputString, e);
         throw new IllegalArgumentException("Invalid inputString format: " + inputString, e);
      }
   }

   public int getNumFigure() {
      logger.debug("getNumFigure called");
      return numFigure;
   }

   public String getName() {
      logger.debug("getName called");
      return name;
   }

   public int getTableID() {
      logger.debug("getTableID called");
      return tableID;
   }

   public void setTableID(int value) {
      logger.debug("setTableID called with value: "+ value);
      tableID = value;
   }

   public int getTableBound() {
      logger.debug("getTableBound called");
      return tableBound;
   }

   public void setTableBound(int value) {
      logger.debug("setTableBound called with value: "+ value);
      tableBound = value;
   }

   public int getFieldBound() {
      logger.debug("getFieldBound called");
      return fieldBound;
   }

   public void setFieldBound(int value) {
      logger.debug("setFieldBound called with value: "+ value);
      fieldBound = value;
   }

   public boolean getDisallowNull() {
      logger.debug("getDisallowNull called");
      return disallowNull;
   }

   public void setDisallowNull(boolean value) {
      logger.debug("setDisallowNull called with value: "+ value);
      disallowNull = value;
   }

   public boolean getIsPrimaryKey() {
      logger.debug("getIsPrimaryKey called");
      return isPrimaryKey;
   }

   public void setIsPrimaryKey(boolean value) {
      logger.debug("setIsPrimaryKey called with value: " + value);
      isPrimaryKey = value;
   }

   public String getDefaultValue() {
      logger.debug("getDefaultValue called");
      return defaultValue;
   }

   public void setDefaultValue(String value) {
      logger.debug("setDefaultValue called with value:" + value);
      defaultValue = value;
   }

   public int getVarcharValue() {
      logger.debug("getVarcharValue called");
      return varcharValue;
   }

   public void setVarcharValue(int value) {
      logger.debug("setVarcharValue called with value:" + value);
      if (value > 0) {
         varcharValue = value;
      }
   }
   public int getDataType() {
      logger.debug("getDataType called");
      return dataType;
   }

   public void setDataType(int value) {
      logger.debug("setDataType called with value:" + value);
      if (value >= 0 && value < strDataType.length) {
         dataType = value;
      }
   }

   public static String[] getStrDataType() {
      logger.debug("getStrDataType called");
      return strDataType;
   }

   public String toString() {
      logger.debug("toString called for EdgeField " + name);
      return numFigure + EdgeConvertFileParser.DELIM +
      name + EdgeConvertFileParser.DELIM +
      tableID + EdgeConvertFileParser.DELIM +
      tableBound + EdgeConvertFileParser.DELIM +
      fieldBound + EdgeConvertFileParser.DELIM +
      dataType + EdgeConvertFileParser.DELIM +
      varcharValue + EdgeConvertFileParser.DELIM +
      isPrimaryKey + EdgeConvertFileParser.DELIM +
      disallowNull + EdgeConvertFileParser.DELIM +
      defaultValue;
   }
}
