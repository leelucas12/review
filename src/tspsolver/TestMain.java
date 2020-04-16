package tspsolver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *主程序 调用ACO求解问题
 * @author FashionXu
 */
public class TestMain {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        // TODO code application logic here
        ACO aco;
        aco=new ACO();
        try {
            aco.init("data.txt", 50);
            aco.run(2000);
            aco.ReportResult();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
