import java.awt.Color;
import java.awt.Container;
import java.io.FileNotFoundException;
import javax.swing.JFrame;


public class Bananas 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		
		JFrame theGUI = new JFrame();
		theGUI.setTitle("Bananas");
		theGUI.setSize(1005, 600);
		theGUI.setResizable(false);
		theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Color lightBlue= new Color(30,144,255);  
		Grid panel = new Grid(lightBlue);
		Container pane = theGUI.getContentPane();
		pane.add(panel);
		pane.setBackground(lightBlue);
		theGUI.setVisible(true);
	}

}


