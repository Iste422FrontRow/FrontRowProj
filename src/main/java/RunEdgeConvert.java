import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class RunEdgeConvert {
   public static Logger log = LogManager.getLogger(RunEdgeConvert.class);

   public static void main(String[] args) {
      log.info("Application starts");
      EdgeConvertGUI edge = new EdgeConvertGUI();
   }
}