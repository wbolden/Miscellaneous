import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Grid extends JPanel implements ActionListener 
{
	static javax.swing.Timer timer;
	private int x1, y1, x2, y2;
	private int[] buildingY = new int[10];
	private int[] buildingX = new int[10];
	double velocity;
	double angle;
	private ImageIcon image = new ImageIcon("Building1.jpg");
	private ImageIcon gorilla1 = new ImageIcon("cartoon_gorilla.gif");
	private ImageIcon gorilla2 = new ImageIcon("cartoon_gorilla_inv.gif");
	private ImageIcon bananaImage = new ImageIcon("bananas1.png");
	private Random generator = new Random();
	public static Physics banana;
	private boolean throwMade = false;
	private boolean g1Turn = false;
//	private boolean g2Turn = false;
//	Color back ;                   					//Used for explosions
	/*
	private Physics[] explosions = new Physics[50];
	private int[] ex = new int[50];
	private int[] ey = new int[50];
	*/
	int g1x, g2x, g1Building, g2Building;
//	int explosionCount = -1;
	private int p1Score = 0;
	private int p2Score = 0;
	private boolean turnOver = false;
	private boolean gameOver = false;
	boolean drawHitbox = false;
	boolean waitForPlayer = true;
	boolean showThrowTime = false;
//	private boolean useExplosions = false;

	public Grid(Color backColor) throws FileNotFoundException
	{
		setBackground(backColor);
	//	back = backColor;
		
		setFocusable( true );
		randomBuildings();
		
		nextTurn();
		
		Physics.setWind(randomWind());
		addKeyListener(new MoveListener());
		addMouseListener(new PanelListener());
		addMouseMotionListener(new panelMotionListener());
		
		timer = new javax.swing.Timer(1, this);
	}
	
	public void newMap()
	{
		throwMade = false;
		gameOver = false;
		randomBuildings();
		//explosionCount=-1;
		Physics.setWind(randomWind());
		if(buildingY[g1Building+1] < buildingY[g1Building]-60)
		{
			buildingY[g1Building+1] = buildingY[g1Building]-60;
			System.out.println(buildingY[g1Building]);
			System.out.println(buildingY[g1Building+1]);
		}
		if(buildingY[g2Building-1] < buildingY[g2Building]-60)
		{
			buildingY[g2Building-1] = buildingY[g2Building]-60;
		}
		
		if(g1Turn)
		{
			g1Turn=false;
			x1 =g2x+15;
			y1 =(buildingY[g2Building] -40);
			x2 =g2x+15;
			y2 =(buildingY[g2Building] -40);
			p1Score++;
		}
		else
		{
			g1Turn=true;
			x1 =g1x+59;
			y1 =(buildingY[g1Building] -40);
			x2 =g1x+59;
			y2 =(buildingY[g1Building] -40);
			p2Score++;
		
		}
	}
	
	public void nextTurn()
	{
		throwMade = false;
		turnOver = false;
		if(g1Turn)
		{
			g1Turn=false;
			x1 =g2x+15;
			y1 =(buildingY[g2Building] -40);
			x2 =g2x+15;
			y2 =(buildingY[g2Building] -40);
		}
		else
		{
			g1Turn=true;
			x1 =g1x+59;
			y1 =(buildingY[g1Building] -40);
			x2 =g1x+59;
			y2 =(buildingY[g1Building] -40);
		
		}
		//if a player misses
		//player that didn't go last goes
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		banana.move();
		repaint();
	}
	
	public void randomBuildings()
	{
		for(int x =0; x <10; x++)
		{
			buildingY[x] = 300+ generator.nextInt(150) - generator.nextInt(100);
		}
		for(int x = 0; x <10; x++)
		{
			buildingX[x] = x * 100;
		}
		buildingY[4]-= 50;
		buildingY[5]-= 100;
		buildingY[6]-= 50;
		
		randomGorillas();
	}
	
	public void randomGorillas()
	{
		g1Building = generator.nextInt(2);
		g2Building = 7 + generator.nextInt(2);
		
		g1x = buildingX[g1Building]-10;
		g2x = buildingX[g2Building]+40;
	}
	
	public int randomWind()
	{
		//number generated between 0 and 20, 50/50 chance to be negative via another number gen
		int sign = generator.nextInt(2);
		int wind = generator.nextInt(25)+10;
		if(sign == 0)
		{
			wind = wind * -1;
		}
		return wind;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int x = 0; x < 10; x++)
		{
		image.paintIcon(this, g, buildingX[x], buildingY[x]);
		}
		
		gorilla1.paintIcon(this, g, g1x, (buildingY[g1Building])-73);
		gorilla2.paintIcon(this, g, g2x, (buildingY[g2Building])-73);
		
		boolean hit = false;
		if(!throwMade)
		{
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(x2, y2, x2, y1);
			g.drawLine(x1, y1, x2, y1);
			
			
			if(g1Turn)
			{
				g.drawString("Player 1 (left) turn", 450, 25);
			}
			else
			{
				
				g.drawString("Player 2 (right) turn", 450, 25);
			}
			g.drawString("Player 1 Score: "+p1Score+". Player 2 Score: "+p2Score, 400, 40);
			
			angle = -180*(Physics.findAngle((y2 - y1),(x2 - x1))/Math.PI);
			velocity = Physics.findVelocity((x2-x1), (y2-y1));
			
			/*
			g.drawString("   "+Math.abs(y2-y1), x2, (y2+y1)/2);
			g.drawString(""+Math.abs(x2-x1),(x2+x1)/2, y1);
			g.drawString(""+(int)velocity,((x1+x2)/2)-20, (y2+y1)/2);
			*/
			
			g.drawString("Angle: "+angle+ " ", 30, 20);
			g.drawString("Velocity: "+velocity/25 /*+" meters per second/"*/, 30, 40);
			g.drawString("Wind: "+Math.abs(Physics.getWind())+" "+Physics.getWindDirection(), 30, 60);
			
			//g.drawString("by 3", 800, buildingY[3]);
			
			
			
		}
		else
		{
			if(showThrowTime)
			{
				g.drawString(""+banana.getTime(), 450, 25);
			}
			
			banana.setup(velocity, angle, x1, y1);
			if(banana.getY() > 90)
			{
				//g.drawString("==============by 3===============", 800, buildingY[3]);
				if(collision())
				{
					timer.stop();
					if(!waitForPlayer)
					{
						nextTurn();
						hit = true;
					}
					turnOver = true;
				}
				else if(hitEnemy())
				{
					timer.stop();
					if(!waitForPlayer)
					{
						newMap();
						hit = true;
					}
					gameOver = true;
				}
			}
			
			if(banana.getX() > 1040)
			{
				nextTurn();
			}
			else if(banana.getX() < -40)
			{
				nextTurn();
			}
			
			if(banana.getY()+3 < 0)
			{
				g.drawLine(banana.getX()-4, 0, banana.getX()-4, 20);
				g.drawString(banana.getX()-4+", "+(int)banana.getY()+3, banana.getX()-27, 34);
			}
			if(hit == false)
				bananaImage.paintIcon(this, g, banana.getX()-2, (int) banana.getY()+4);
			if(drawHitbox)
			{
				banana.draw(g);
			}
			if(turnOver || gameOver)
			{
				g.drawString("Press space to continue", 450, 100);
			}
			//g.drawString("==============by 3===============", 800, buildingY[3]);
		}
		
	}
/*	
	private boolean onExplosion() 
	{
		return false;
		
		
	}
*/
	private class PanelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			x2 = e.getX();
			y2 = e.getY();
			repaint();
		}
		
		public void mouseReleased(MouseEvent e)
		{
			
			x2 = e.getX();
			y2 = e.getY();
			
		}
	}

	private class panelMotionListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			x2 = e.getX();
			y2 = e.getY();
			repaint();
			
		}
	}
	
	
	public boolean collision()
	{
		for(int x = 0; x < 10; x++)
		{
			if(banana.withinPoints(buildingX[x], buildingY[x]))
			{
				return true;
			}
		}
		
		return false; 
	}
	
	public boolean hitEnemy()
	{
		
		if(!g1Turn)
		{
			if(banana.withinGorPoints(g1x+5,buildingY[g1Building]))
			{
				return true;
			}
		}
		else
		{
			if(banana.withinGorPoints(g2x+5,buildingY[g2Building]))
				{
					return true;
				}
		}	
		
		return false;
		
	}
	
	//                                          =========Finished Below==========
	
	private class MoveListener implements KeyListener
	{

		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			switch( keyCode ) 
			{ 
				case KeyEvent.VK_ENTER:
					if(turnOver)
					{
						nextTurn();
					}
					else if(gameOver)
					{
						newMap();
					}
					break;
					
				
				case KeyEvent.VK_SPACE:
					
				if(turnOver)
				{
					nextTurn();
				}
				else if(gameOver)
				{
					newMap();
				}
				else
				{
					if(throwMade)
					{
						if(timer.isRunning())
						{
							timer.stop();
						}
						else
						{
							timer.start();
						}
					}
					else if(!throwMade)
					{
						
						banana = new Physics(x1, y1, 11, Color.red);
						throwMade=true;
						timer.start();
						repaint();
					}
				}
				break;
				
				
				case KeyEvent.VK_R:
					
					randomBuildings();
				break;
				
				case KeyEvent.VK_H:
					if(drawHitbox)
					{
						drawHitbox = false;
					}
					else
					{
						drawHitbox = true;
					}
				break;
				
				case KeyEvent.VK_T:
					if(showThrowTime)
					{
						showThrowTime = false;
					}
					else
					{
						showThrowTime = true;
					}
				break;
			}		
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}

