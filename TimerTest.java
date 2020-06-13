package LandMineSearch;

import java.awt.*;
import javax.swing.*;
 
public class TimerTest {
    JButton timerButton = null;
    public TimerTest()
    {
    	JFrame frame = new JFrame("timer test");
    	JMenuBar mb = new JMenuBar();
        timerButton = new JButton("0");
        timerButton.setEnabled(false);
       
        mb.add(timerButton); 
        frame.setSize(150,150);
        frame.setVisible(true);
        frame.add(mb);
        
        
        int k = 0;
        while(k < 51) {
            timerButton.setText(""+k+"ÃÊ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            k++;
        }
    }
    
    public static void main(String[] args) {
    	new TimerTest();
    }
}
