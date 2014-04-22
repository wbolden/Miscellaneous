import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;


public class ZombieGame 
{
	static JFrame GUI = new JFrame();
	public static void main(String[] args) 
	{	
		GUI.setTitle("Zombie Infection Simulation");
		GUI.setSize(805, 445); //405 is good normal x
		GUI.setResizable(false);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Grid panel = new Grid(Color.black);
		Container pane = GUI.getContentPane();
		pane.add(panel);
		GUI.setVisible(true);
		
		
	}
	
	public static void setWindowSize(int x)
	{
		GUI.setSize(x, 445);
	}
	

}
