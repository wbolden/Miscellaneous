import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;



import javax.swing.JPanel;

public class Grid extends JPanel implements ActionListener
{
	static javax.swing.Timer timer;
	static int size = 400;
			//buildingCount = 500;
	static int npcCount = 1000;
	int ticks = 1;
	static int survivorCount = npcCount-1,
			zombieCount = 1;
	static int buildings = size/2 -((size/2)/2);
	static int[][] theCity = City.createCity(size, size, buildings);
	ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	static int graphTicks = 0;
	static int[] graph = new int[900000];
	static boolean displayGraph = true;
	
	static int xInit, yInit;
	static int thickness = 5;
	
	static int yOffset = 400, 
	xOffset = 400;
	
	public Grid(Color backColor)
	{
		setBackground(backColor);
		setFocusable(true);
		addKeyListener(new MoveListener());
		addMouseListener(new PanelListener());
		addMouseMotionListener(new panelMotionListener());

		
		City.populateList(npcs, npcCount);
		City.populateCity(size, size, npcCount, npcs);
		
//		repaint();
		timer = new javax.swing.Timer(1, this);
		timer.start();
	}
	
	public void reset()
	{
		theCity = City.createCity(size, size, buildings);
		npcs.clear();
		City.populateList(npcs, npcCount);
		City.populateCity(size, size, npcCount, npcs);
		survivorCount = npcCount-1;
		zombieCount = 1;
		graph = new int[900000];
		graphTicks = 0;
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int x =0; x < theCity.length; x++)
		{
			for(int y =0; y < theCity.length; y++)
			{
				int temp=theCity[x][y];
				if(temp == -1)
				{
					int tempY = y;
					while(theCity[x][y] == -1)
					{
						y++;
					}
					y--;
					g.setColor(Color.gray);
					g.drawLine(x, y, x, tempY);

				}
				else if(temp != -2) //(temp != -2)
				{
					g.setColor(selectColor(x, y, temp));
					g.drawLine(x, y, x, y);
				}
			}
		}
		g.setColor(Color.white);
		g.drawLine(-2, 400, 400, 400);
		g.drawString("Survivors Remaining: "+survivorCount, 1, 412);
		g.drawString("Zombies: "+zombieCount, 310, 412);
		graphTicks++;
		graph[graphTicks] = survivorCount;
		
		if(displayGraph)
		{
			/*
			g.drawLine(400, 0, 400, 430);
			
			g.setColor(Color.cyan);
			
			
			for(int x=0;x < graphTicks/20; x++)
			{
			//	g.drawLine(x+xOffset, yOffset-graph[x*20]/(npcCount/200), x+xOffset, 0-graph[x*20]/(npcCount/200) - thickness);
				
				
			}
			g.fillRect(400, 0, 800, 400);
			
			g.setColor(Color.red);
			//g.drawLine(400, 0, 400, 430);
			
			for(int x=0;x < graphTicks/20; x++)
			{
				g.drawLine(x+xOffset, 800-10, x+xOffset, 200 +graph[x*20]/(npcCount/200));
				
			}
			g.setColor(Color.white);
			g.drawLine(400, 0, 400, 430);
			*/
			
			
			g.setColor(Color.cyan);
			
			
			for(int x=0;x < graphTicks/20; x++)
			{
				g.drawLine(x+xOffset, yOffset-graph[x*20]/(npcCount/200), x+xOffset, yOffset-graph[x*20]/(npcCount/200) - thickness);
				
			}
			
			g.setColor(Color.red);
			//g.drawLine(400, 0, 400, 430);
			
			for(int x=0;x < graphTicks/20; x++)
			{
				g.drawLine(x+xOffset, 200+graph[x*20]/(npcCount/200), x+xOffset, 200+graph[x*20]/(npcCount/200) - thickness);
				
			}
			g.setColor(Color.white);
			g.drawLine(400, 0, 400, 430);
			
		}
		
		
	}

	//determines the color to be painted, call only from paintComponent.
	private Color selectColor(int x, int y, int temp) 
	{
		if(temp == -1)
		{
			return Color.gray;
		}
		else
		{
			int state = npcs.get(temp).getState();
			if(state == 2)
			{
				return Color.red;
			}
			else if(state == 0)
			{
				return Color.cyan;
			}
			else
			{
				return Color.yellow;
			}
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		updateTick();
		
		for(int x=0; x < npcs.size(); x++)
		{
			int temp = npcs.get(x).look();
			if(temp >-1)
			{
				npcs.get(x).respondTo(npcs.get(temp));
			}
		}
		for(int x=0; x < npcs.size(); x++)
		{
			npcs.get(x).move(ticks);
		}
		//System.out.println("move");
		repaint();
	}
	
	public void updateTick()
	{
		ticks++;
		if(ticks > 10)
		{
			ticks = 1;
		}
	}
	
	private class PanelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			
			int mx = e.getX();
			int my = e.getY();
			
			if(mx < 400)
			{
				if(theCity[mx][my] > -1)
				{
					//System.out.println(npcs.get(theCity[mx][my]).toString());
					npcs.get(theCity[mx][my]).zombify();
					repaint();
				}
				else
				{
					xInit = mx;
					yInit = my;
				}
			}
			
		}
	}
	
	private class panelMotionListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			int mx = e.getX();
			int my = e.getY();
			
			if(mx < 400)
			{
				if(theCity[mx][my] > -1)
				{
					//System.out.println(npcs.get(theCity[mx][my]).toString());
					npcs.get(theCity[mx][my]).zombify();
					repaint();
				}
			}
			else
			{
				xOffset = 400 +( mx - 400);
				yOffset = 400 +( my - 400);
		//		System.out.println(xOffset);
		//		System.out.println(yOffset);
			}
		}
	}
	
	private class MoveListener implements KeyListener
	{

		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			switch( keyCode ) 
			{ 
				case KeyEvent.VK_SPACE:
					if(timer.isRunning())
					{
						timer.stop();
					}
					else
					{
						timer.start();
					}
				break;
				
				case KeyEvent.VK_UP:
					if(timer.getDelay() > 1)
					{
						timer.setDelay(timer.getDelay()-1);
					}
				break;
				
				case KeyEvent.VK_DOWN:
					timer.setDelay(timer.getDelay()+1);
				break;
				
				case KeyEvent.VK_G:
					if(displayGraph)
					{
						ZombieGame.setWindowSize(405);
						displayGraph = false;
					}
					else
					{
						ZombieGame.setWindowSize(805);
						displayGraph = true;
					}
				break;
				
				case KeyEvent.VK_R:
					reset();
				break;
				
				case KeyEvent.VK_W:
					npcCount+= 1000;
					reset();
				break;
				
				case KeyEvent.VK_S:
					
					if(npcCount > 1000)
					{
						npcCount-= 1000;
					}
					reset();
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
