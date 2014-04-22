import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Marker extends Head
{
	Random generator = new Random();
	public Marker(int x, int y, int w, int h) {
		super(x, y, w, h);
		
	}
	public void drawFood(Graphics g)
	{
		g.setColor(Color.orange);
		g.drawRect(x, y, width, height);
	}
	
	public void relocate()
	{
		x = 18*(generator.nextInt(31));
		y = 18*(generator.nextInt(27)+4);
		if(Grid.checkCollision(x, y))
		{
			relocate();
		}
	}
}
