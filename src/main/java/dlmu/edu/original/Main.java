package dlmu.edu.original;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 主程序 调用ACO求解问题
 * Created by lenovo on 2017-03-16.
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        System.out.println("start:"+start);
        ACO aco;
        aco=new ACO();
        try {
            aco.init("f://00ideaProgram//paper//src//main//resources//att48.tsp", 50);
            aco.run(2000);
            aco.ReportResult();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            long useTime=System.currentTimeMillis()-start;
            System.out.println("use time:"+useTime);
        }
    }

}
