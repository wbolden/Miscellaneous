import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;


public class Mandelbrot 
{	
	public static void main(String[] args) 
	{
		JFrame GUI = new JFrame();
		GUI.setTitle("Mandelbrot set");
		GUI.setSize(1000, 1000);
		GUI.setResizable(false);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Grid panel = new Grid(Color.black);
		Container pane = GUI.getContentPane();
		pane.add(panel);
		GUI.setVisible(true);
	}

}
