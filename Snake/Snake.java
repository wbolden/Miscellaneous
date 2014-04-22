import java.awt.Color;
import java.awt.Container;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

	

public class Snake {

	public static void main(String[] args) throws FileNotFoundException
	{
			//GUI interface to setup before game is run, runs game.	
		
		JFrame theGUI = new JFrame();
		theGUI.setTitle("Snake");
		theGUI.setSize(566, 587);
		theGUI.setResizable(false);
		theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Grid panel = new Grid(Color.black);
		Container pane = theGUI.getContentPane();
		pane.add(panel);
		theGUI.setVisible(true);
	}
	
	static public void setHighScore(int score)throws IOException
	{
		File f;
		f = new File("HighScore.txt");
		f.createNewFile();
		PrintWriter writer = new PrintWriter(f);
		writer.println(score);
		writer.close();
	}
	
	public static int getHighScore() throws FileNotFoundException
	{
		
		File f;
		f = new File("HighScore.txt");
		if(f.exists())
		{
			Scanner reader = new Scanner(f);
			reader.close();                  //this work?
			return reader.nextInt();
		}
		else
		{
		return 0;
		}
		
	}
	
}
