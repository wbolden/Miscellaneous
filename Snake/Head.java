import java.awt.Color;
import java.awt.Graphics;

public class Head 
{
	
	int velocity;
	int direction;
	int moveX;
	int moveY;
	
	
	public Head(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	int x, y, height, width;
	
	public boolean containsPoint(int x, int y)
	{
		return false;
		
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void move(int d, int yAmount)
	{
		
		x = x + d;
		y = y + yAmount;
		
		if(x < 0)
		{
			System.out.println("Game Over: Hit a wall");
			x = x - d;
			y = y - yAmount;
			Grid.gameOver = true;
			Grid.timer.stop();
		}
		else if(x >540)
		{
			System.out.println("Game Over: Hit a wall");
			x = x - d;
			y = y - yAmount;
			Grid.gameOver = true;
			Grid.timer.stop();
			
		}
		else if(y < 36)
		{
			System.out.println("Game Over: Hit a wall");
			x = x - d;
			y = y - yAmount;
			Grid.gameOver = true;
			Grid.timer.stop();
		}
		else if(y > 540)
		{
			System.out.println("Game Over: Hit a wall");
			x = x - d;
			y = y - yAmount;
			Grid.gameOver = true;
			Grid.timer.stop();
		}
		
		System.out.println(x+","+y);
	}
	
	void setX(int x)
	{
		this.x = x;
	}

	void setY(int y)
	{
		this.y = y;
	}
	
	public void move()
	{
		Grid.bodyMove();
		move(moveX, moveY);
		
		
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.white);
		g.drawRect(x, y, width, height);

	}
	

	public void setVelocity(int v)
	{
		velocity = v;
	}
	
	public int getVelocity()
	{
		return velocity;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public void setRight()
	{	
		moveX = 18;
		moveY = 0;
	}
	
	public void setLeft()
	{
		moveX = -18;
		moveY = 0;
	}
	
	public void setUp()
	{
		moveX = 0;
		moveY = -18;
	}
	
	public void setDown()
	{
		moveX = 0;
		moveY = 18;
	}
	
	
	public void setDirection(int degrees)
	{
		if(degrees == 0)
		{
			setRight();
		}
		else if(degrees == 180)
		{
			setLeft();
		}
		else if(degrees == 90)
		{
			setDown();
		}
		else if(degrees == 270)
		{
			setUp();
		}
		direction = degrees;
	}

}
