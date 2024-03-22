import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class EdgeConnector {
   private int numConnector, endPoint1, endPoint2;
   private String endStyle1, endStyle2;
   private boolean isEP1Field, isEP2Field, isEP1Table, isEP2Table;
   public static Logger log = LogManager.getLogger(EdgeConnector.class);

   public EdgeConnector(String inputString) {
      log.debug("EdgeConnector constructor made");
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      try {
         numConnector = Integer.parseInt(st.nextToken());
         endPoint1 = Integer.parseInt(st.nextToken());
         endPoint2 = Integer.parseInt(st.nextToken());
         endStyle1 = st.nextToken();
         endStyle2 = st.nextToken();
         isEP1Field = false;
         isEP2Field = false;
         isEP1Table = false;
         isEP2Table = false;
      } catch (NumberFormatException | NoSuchElementException e) {
         log.error("Error parsing inputString:", inputString, e);
         throw new IllegalArgumentException("Invalid inputString format: " + inputString, e);
      }
   }
   
   public int getNumConnector() {
      log.debug("getting number connector:" + numConnector);
      return numConnector;
   }
   
   public int getEndPoint1() {

      log.debug("end point 1 called: " + endPoint1);
      return endPoint1;
   }
   
   public int getEndPoint2() {

      log.debug("end point 1 called: " + endPoint2);
      return endPoint2;
   }
   
   public String getEndStyle1() {
      log.debug("end style 1 called: " + endStyle1);
      return endStyle1;
   }
   
   public String getEndStyle2() {
      log.debug("end style 2 called: " + endStyle1);
      return endStyle2;
   }
   public boolean getIsEP1Field() {
      log.debug("ep1filed called");
      return isEP1Field;
   }
   
   public boolean getIsEP2Field() {
      log.debug("ep2filed called");
      return isEP2Field;
   }

   public boolean getIsEP1Table() {
      log.debug("ep1table called");
      return isEP1Table;
   }

   public boolean getIsEP2Table() {
      log.debug("ep2table called");
      return isEP2Table;
   }

   public void setIsEP1Field(boolean value) {
      log.debug("setting ep1field called");
      isEP1Field = value;
   }
   
   public void setIsEP2Field(boolean value) {
      log.debug("setting ep2field called");
      isEP2Field = value;
   }

   public void setIsEP1Table(boolean value) {
      log.debug("set ep1table called");
      isEP1Table = value;
   }

   public void setIsEP2Table(boolean value) {
      log.debug("set ep2table called");
      isEP2Table = value;
   }
}
