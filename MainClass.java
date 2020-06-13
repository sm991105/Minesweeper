package LandMineSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainClass extends JFrame {

  
	
  public static void main(String args[]) {
    JFrame frame = new JFrame("Button Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JButton button = new JButton("click Me");

    MouseListener mouseListener = new MouseAdapter() {
     
    	public void mouseReleased(MouseEvent mouseEvent) {
        
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
          if(button.getBackground() == Color.BLUE) {
        	  button.setBackground(null);
        	 
          }
          else {
        	  button.setBackground(Color.BLUE);
          }
        }
        System.out.println();
      }
    };

    button.addMouseListener(mouseListener);

    frame.add(button, BorderLayout.SOUTH);
    frame.setSize(300, 100);
    frame.setVisible(true);

  }
}
