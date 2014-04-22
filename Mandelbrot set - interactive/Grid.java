import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.JPanel;

public class Grid extends JPanel implements ActionListener
{
	static javax.swing.Timer timer;
	private int xp, yp;
	private boolean change = true;
	private int iterations = 90;
	private double scrollX = 300, scrollY=0, zoomX, zoomY;
	private double magnification = 2;
	private boolean works = false;		//keep as false
	private boolean increment = false;
	private int incTotal = 170;
	private boolean l30 = true;			//keep as true
	private boolean loop = false;
	private boolean colorFractal = false;
	private boolean colorOther = true;
	private boolean showInfo = true;
	public Grid(Color backColor)
	{
		setBackground(backColor);
		setFocusable( true );
		addMouseListener(new PanelListener());
		addKeyListener(new MoveListener());

//		addMouseMotionListener(new panelMotionListener());

		if(increment)
		{
			timer = new javax.swing.Timer(1, this);
			timer.start();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.white);
		
		
		
		for(double x = -500; x < 500; x++)
		{
			for(double y = -500; y < 500; y++)
			{
				double it = iterate(x-scrollX,y-scrollY);
				
				if(works)
				{
					xp = (int)(x + 500);
					yp = (int)(y + 500);
					works = false;
					
					if(colorFractal)
					{
						g.setColor(Color.white);
						g.drawLine(xp, yp, xp, yp);
					}
					
				}
				else
				{
					
					if(colorOther)
					{
						xp = (int)(x + 500);
						yp = (int)(y + 500);
			//			Color fillColor = new Color(231,47,39);
			//			Color fillColor = new Color(227,169,28);  //gold
			//			Color fillColor = new Color(0,0,255);
						
						/*
						if(it < 1)
						{
							g.setColor(fillColor.darker());
							g.drawLine(xp, yp, xp, yp);
						}
						
						
						
						for(double tempIt = it; tempIt > 0; tempIt--)
						{
							fillColor = fillColor.darker();
						}
						*/
						
						
						int colors1 = 0;
						int colors2 = 255;
						int colors3 = 150;
						
						for(double tempIt = it; tempIt > 0; tempIt--)
						{
							if(colors1 < 255)
								colors1+= .7;
						}
						
						for(double tempIt = it; tempIt > 0; tempIt--)
						{
							if(colors2 > 10)
								colors2-= 1;
						}
						
						for(double tempIt = it; tempIt > 0; tempIt--)
						{
							if(colors3 > 10)
							{
								if(colors3 < 95)
								{
									colors3-= .01;

								}
								else
								{
									colors3-= .3;
								}
							}
						}
						
							
						Color fillColor = new Color(colors3,colors3,colors3);	//grey
						
					//	Color fillColor = new Color(colors1,colors1,colors3);	//yellow and blue
							
						g.setColor(fillColor);
						g.drawLine(xp, yp, xp, yp);
						
					}

				}	
			}
		}
		
		if(showInfo)
		{
			g.setColor(Color.white);
			g.drawString("X: "+scrollX, 10, 20);
			g.drawString("Y: "+scrollY, 10, 30);
			g.drawString("M: "+magnification, 10, 40);
			g.drawString("I: "+iterations, 10, 50);
		}

	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(change)
		{
			repaint();	
		}
		
		if(loop)
		{
			if(l30)
			{
				iterations++;
			}
			if(iterations > incTotal)
			{
				l30 = false;
			}
			else if(iterations < 1)
			{
				l30 = true;
			}
				
			if (!l30)
				iterations--;
			

		}
		
		if(increment && !loop)
		{
			iterations++;
		}
		
	}
	
	public double iterate(double x, double y)
	{
												//Mandelbrot
		double z1Re=0, z1Im=0, z2Re=0, z2Im=0;
		
		for(int i = iterations; i > 0; i--)
		{
			z2Re = z1Re;
			z2Im = z1Im;
			
			z1Re = ((z2Re * z2Re) + (z2Im * z2Im* -1)) + x/(250*magnification);
			z1Im = (2*(z2Re * z2Im)) + y/(250*magnification);

			if(2 < Math.sqrt(Math.abs((z1Re*z1Re) + (z1Im*z1Im*-1))))
			{
//				return (Math.sqrt(Math.abs((z1Re*z1Re) + (z1Im*z1Im*-1)))); //cool bands
				return (iterations - i);
//				return i;
				
			}
			
		}
		works=true;
		return 0;
	}
	
//	private int
	
	private class PanelListener extends MouseAdapter
	{
//		int x1, y1;
		public void mousePressed(MouseEvent e)
		{
			scrollX += 500-e.getX();
			scrollY += 500-e.getY();
			
			repaint();
		}
		
		public void mouseReleased(MouseEvent e)
		{
			
			//scrollX += x1 - e.getX();
			//scrollY += y1 - e.getY();
			//repaint();
			
		}
	}
	
	
	private class MoveListener implements KeyListener
	{

		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			switch( keyCode ) 
			{ 
				case KeyEvent.VK_E:
					if(magnification < 1)
					{
						magnification += .1;
						repaint();
					}
					else
					{
						magnification++;
						scrollX += scrollX/magnification + 250/magnification; 
						scrollY += scrollY/magnification ;
						repaint();
					}
				break;
				// 9 d
				//10d
				case KeyEvent.VK_R:
					if(magnification > 1)
					{
						scrollX -= scrollX/magnification;
						scrollY -= scrollY/magnification;
						magnification--;
						repaint();
					}
					else
					{
						magnification-=.1;
						repaint();
					}
				break;
				case KeyEvent.VK_I:
					Scanner reader = new Scanner(System.in);
					iterations = reader.nextInt();
				break;
				
				case KeyEvent.VK_Q:
					magnification = 1;
					scrollX = 300 ;
					scrollY=0;
					iterations = 70;
					repaint();
					
				break;
				
				case KeyEvent.VK_M:
					
					for(int x = 100; x > 0; x--)
					{
						magnification++;
						scrollX += scrollX/magnification;
						scrollY += scrollY/magnification;
						
					}
					repaint();
					
				break;
				case KeyEvent.VK_N:
					
					for(int x = 1000; x > 0; x--)
					{
						magnification++;
						scrollX += scrollX/magnification;
						scrollY += scrollY/magnification;
						
					}
					repaint();
				break;
				
					case KeyEvent.VK_B:
					
					for(int x = 10000; x > 0; x--)
					{
						magnification++;
						scrollX += scrollX/magnification;
						scrollY += scrollY/magnification;
						
					}
					repaint();
					
				break;
				
					case KeyEvent.VK_V:
						
						for(int x = 1000000; x > 0; x--)
						{
							magnification++;
							scrollX += scrollX/magnification;
							scrollY += scrollY/magnification;
							
						}
						repaint();
						
					break;
					
					case KeyEvent.VK_L:
						
						iterations +=50;
						repaint();
						
					break;
					

					case KeyEvent.VK_K:
						
						iterations -=50;
						repaint();
						
					break;
					
					case KeyEvent.VK_O:
						if(colorOther == false)
						{
							colorFractal = false;
							colorOther = true;
						}
						else
						{
							colorFractal = true;
							colorOther = false;
						}
						repaint();
						
					break;
					
					case KeyEvent.VK_T:
						for(int x = 25; x > 0; x--)
						{
							scrollX -= scrollX/magnification;
							scrollY -= scrollY/magnification;
							magnification--;
						}
						repaint();
						
					break;
					
					case KeyEvent.VK_Y:
						for(int x = 100000; x > 0; x--)
						{
							scrollX -= scrollX/magnification;
							scrollY -= scrollY/magnification;
							magnification--;
						}
						repaint();
						
					break;
					
					case KeyEvent.VK_W:
						if(showInfo)
						{
							showInfo = false;
						}
						else
						{
							showInfo = true;
						}
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
