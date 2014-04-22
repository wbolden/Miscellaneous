import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;


public class GameOfLife 
{

	public static void main(String[] args) 
	{
		JFrame theGUI = new JFrame();
		theGUI.setTitle("Game of Life");
		theGUI.setSize(1000, 1000);
		
		theGUI.setResizable(true);
		theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Grid panel = new Grid(Color.gray);
		theGUI.pack();
		Container pane = theGUI.getContentPane();
		pane.add(panel);
		theGUI.pack();
		theGUI.setVisible(true);
	}

}


