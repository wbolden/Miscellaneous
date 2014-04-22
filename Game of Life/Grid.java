import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.JPanel;

public class Grid extends JPanel implements ActionListener
{
	static javax.swing.Timer timer;
	static int[][] life = new int[105][105];
	int xm, ym;		//mouse x and y
	boolean fill;
//	static int iterations = 0;
	
	public Grid(Color backColor)
	{
		setBackground(backColor);
		
		setFocusable( true );
		addKeyListener(new MoveListener());
		addMouseListener(new PanelListener());
		addMouseMotionListener(new panelMotionListener());
		timer = new javax.swing.Timer(101, this);
//		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
//		g.setColor(Color.white);
//		g.drawString(iterations+"",  10, 10);
		g.setColor(Color.black);
		//draws grid
		for(int x = 0; x < 1000; x+=10)
		{
			g.drawLine(x, 0, x, 1000);
		}
		for(int y = 0; y < 1000; y+=10)
		{
			g.drawLine(0, y, 1000, y);
		}
		
		//draws life
		for(int x = 0; x < 1000; x+=10)
		{
			for(int y = 0; y < 1000; y+=10)
			{
				if(life[x/10][y/10] == 1)
				{
					//g.drawRect(x, y, 8, 8);
					g.setColor(Color.yellow);
					g.fillRect(x+1, y+1, 9, 9);
				}
				
			}
			
		}
	//	System.out.println("pc");
		
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		for(int x = 2; x < 100; x++)
		{
			for(int y = 2; y < 100; y++)
			{
				if(life[x][y] != 0)
				{
					alterLife(x, y);
				}
			}
		}
		
		for(int x = 3; x < 97; x++)
		{
			for(int y = 3; y < 97; y++)
			{
				if(life[x][y] == 2)
				{
					life[x][y] = 1;
				}
				
				if(life[x][y] == -1)
				{
					life[x][y] = 0;
				}
			}
		}
//		System.out.println("done");
//		iterations++;
		repaint();
	}
	
	private void alterLife(int x, int y)
	{
		for(int w = -1; w < 2; w++)
		{
			for(int z = -1; z < 2; z++)
			{
				if(life[x+w][y+z] != 2 || life[x+w][y+z] != -1)
					{
					int res = searchLife((x+w),(y+z));
					
					if(res > 3)
					{
						
						if(life[x+w][y+z] == 1)
						{
							life[x+w][y+z] = -1;
						}
					}
					else if ( res == 3)
					{
						if(life[x+w][y+z] == 0)
						{
							life[x+w][y+z] = 2;
						}
						
					}
					else if ( res < 2)
					{
						if(life[x+w][y+z] == 1)
						{
							life[x+w][y+z] = -1;
						}
					}
				}
			}
		}
		
		//	System.out.println("Done");

	}
	
	public int searchLife(int x, int y)
	{
		int surroundingLife = 0; 
		
		for(int w = -1; w < 2; w++)
		{
			for(int z = -1; z < 2; z++)
			{
//				System.out.println(w+", "+z);
				if(life[x+w][y+z] == 1 || life[x+w][y+z] == -1)
				{
					surroundingLife++;
				}
			}
		}
		
		if(life[x][y] == 1 || life[x][y] == -1)
		{
			surroundingLife--;
		}
		return surroundingLife;
	}
	
	private void changeState()
	{
		
		if(fill == true)
		{
			life[xm][ym] = 1;
		}
		else
		{
			life[xm][ym] = 0;
		}
	}
	
	private class PanelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			
			xm = e.getX()/10;
			ym = e.getY()/10;
			if(life[xm][ym] == 0)
			{
				fill = true;
			}
			else
			{
				fill = false;
			}
			changeState();
			repaint();
			
		}
		
	}

	private class panelMotionListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			xm = e.getX()/10;
			ym = e.getY()/10;
			changeState();
			repaint();
			
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
				
				case KeyEvent.VK_W:
					if(timer.getDelay() > 10)
					{
						timer.setDelay(timer.getDelay()-10);
					}
				break;
				
				case KeyEvent.VK_Q:
					if(timer.getDelay() > 1)
					{
						timer.setDelay(timer.getDelay()-1);
					}
				break;
				
				case KeyEvent.VK_S:
					timer.setDelay(timer.getDelay()+10);
				break;
				
				case KeyEvent.VK_R:
					for(int x = 0; x < 100; x++)
					{
						for(int y = 0; y < 100; y++)
						{
							life[x][y] = 0;
						}
					}
					timer.setDelay(100);
					timer.stop();
					repaint();
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
