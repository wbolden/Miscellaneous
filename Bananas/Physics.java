import java.awt.Color;
import java.awt.Graphics;


public class Physics 
{
	//physics, hitbox, banana, gorillas
	double velocity;
	double angle;
	private int centerX, centerY, radius;
	private int actualX;
	private double actualY;
	private Color color;
	double seconds = 0;
	double vx;
	double vy;
	static int wind;
	
	/*
	 * implement wind,
	 * applies directly? -!saved value that statcks-, same form as normal x move
	 */
	
	public Physics(int x, int y, int r, Color c)
	{
		centerX = x;
		centerY = y;
		radius = r;
		color = c;
	}
	
	public void setup(double v, double a, int x, int y)
	{
		velocity = v;
		angle = a;
	}
	
	public void setX(int x)
	{
		actualX = x;
	}
	
	public void setY(int y)
	{
		actualY = y;
	}
	
	public int getX()
	{
		return actualX;	
	}
	
	public double getY()
	{
		return actualY;
	}
	public static double gravity(double t)
	{
		double actualY = (.5*9.8*25*(Math.pow(t,2))); //y value + (one half * gravity * t(5 ms intervals sqaured) 
		return actualY;
	}

	public static void setWind(int x)
	{
		wind = x;
	}
	
	public static int getWind()
	{
		return wind;
	}
	
	public static String getWindDirection()
	{
		if(wind < 0)
		{
			return "right";
		}
		return "left";
	}
	
	public double getTime()
	{
		return seconds;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.drawOval((int)actualX, (int)actualY, radius* 2, radius * 2);
	
	}

	public static double findAngle(double o, double a)
	{
		double angle = Math.atan2(o, a);
		return angle;
		
	}
	
	public static double findVelocity(double x, double y)
	{
		double distance = Math.sqrt((Math.pow(x, 2)+Math.pow(y, 2))*2);
		return distance;
	}
	
	public boolean containsPoint(int x, int y)
	{
		int xSquared = (x - actualX)* (x - actualX);
		int ySquared = (int)((y - actualY)* (y - actualY));
		int radiusSquared = radius * radius;
		return xSquared + ySquared - radiusSquared <= 0;
		
	}
	
	public boolean withinPoints(int x, int y)
	{
		if((actualX  > (x-22) && actualX < (x+100)) && (actualY > y-20 && actualY < 2000))
		{
			return true;
		}
		return false;
	}
	
	public boolean withinGorPoints(int x, int y)
	{
		//FIX!
		if((actualX  > (x-13) && actualX < (x+62)) && (actualY > y-89 && actualY < y))
		{
			return true;
		}
		return false;
	}
	
	public void move(int xAmount, int yAmount)
	{
		actualX = (int) (centerX + xAmount - (wind * seconds));
		actualY = centerY - yAmount + gravity(seconds);
		seconds+= 0.010;
	
	}
	
	public void move()
	{
		vx += velocity * Math.cos(Math.toRadians(angle));
		vy += velocity * Math.sin(Math.toRadians(angle));
		move((int)(vx*.01), (int)(vy*.01));
	}

	public void fill(Graphics g) 
	{
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillOval((int) (actualX), (int) actualY, radius*2, radius*2);
		g.setColor(oldColor);
		
	}
	
	
}
