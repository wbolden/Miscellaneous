import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Grid extends JPanel implements ActionListener
{
	static javax.swing.Timer timer;
	private static Head head;
	private static Marker food;
	static Body[] pieces = new Body[900];
	static int bodyCount= 0;
	static int number = 0;
	int score = 0;
	static boolean gameOver = false;
	int gamesPlayed = 1;
	int nextMove = 0;
	int highScore = 0;
	
	
	
	public Grid(Color backColor) throws FileNotFoundException
	{
		setBackground(backColor);
		int[] a = {1, 2, 3};
	//	System.out.println(a[a[2]]);
		head = new Head(270, 270, 18, 18);
		pieces[bodyCount] = new Body(270, 252,18,18);
		bodyCount++;
		food = new Marker(600, 600, 18, 18);
		food.relocate();
		highScore = Snake.getHighScore();
		setFocusable( true );
		addKeyListener(new MoveListener());
		
		timer = new javax.swing.Timer(70, this);
		timer.start();
	}
	
	public static boolean checkCollision(int x, int y)
	{
		int x1 = x;
		int y1 = y;
		
		if((x1 == head.getX()) && (y1 == head.getY()))
		{
			return true;
		}
		
		for(int checkForCollision = number; checkForCollision >= 0; checkForCollision--)
		{
			if((pieces[checkForCollision].getX() == x1) && (pieces[checkForCollision].getY() == y1))
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		head.draw(g);
		g.drawLine(0, 36, 600, 36);
		if(gameOver == true)
		{
			g.drawString("Game Over!   Press space to start a new game", 220, 20);
			if(score >= highScore)
			{
				try {
					Snake.setHighScore(score);
				} catch (IOException e) {
					e.printStackTrace();
				}
				highScore = score;
				
				
			}
		}
		else
		{
			g.drawString("Press space to pause the game", 260, 20);
		}
		
		g.drawString("Game "+gamesPlayed, 505, 20);
		g.drawString("Score: "+score+"", 10, 20);
		g.drawString("High Score: "+highScore+"", 100, 20);
		for(int drawBodies = (bodyCount-1); drawBodies >=0; drawBodies--)
		{
			pieces[drawBodies].draw(g);
		}
		food.drawFood(g);
		
	}
	
	public void newGame()
	{
		head.setRight();
		head.setX(270);
		head.setY(270);
		food.relocate();
		bodyCount = 0;
		number = 0;
		pieces[bodyCount].setX(270);
		pieces[bodyCount].setY(252);
		bodyCount = 1;
		score = 0;
		gameOver = false;
		gamesPlayed += 1;
		timer.setDelay(70);
		nextMove = 0;
		
	}
	public void actionPerformed(ActionEvent e) 
	{
		head.setDirection(nextMove);
		head.move();
		if((head.getX() == food.getX()) && (head.getY() == food.getY()))
		{
			food.relocate();
			score += 10;
			if(score > highScore)
			{
				highScore = score;
			}
			if(timer.getDelay() > 20)
			{
				timer.setDelay(timer.getDelay()-1);
			}
			newBody();
			bodyCount++;
		}
		for(int checkForCollision = number; checkForCollision >= 0; checkForCollision--)
		{
			if((pieces[checkForCollision].getX() == head.getX()) && (pieces[checkForCollision].getY() == head.getY()))
			{
				timer.stop();
				Grid.gameOver = true;
				System.out.println("Game Over: Hit a body");
			}
		}
		repaint();	
	}
	
	
	
	public void newBody()
	{
		pieces[bodyCount] = new Body(557, 578, 18, 18 );
		number++;
	}
	
	static public void bodyMove()
	{
		
		for(int tempBodyCount = number; tempBodyCount > 0; tempBodyCount--)
		{
			pieces[tempBodyCount].setX(pieces[tempBodyCount-1].getX());
			pieces[tempBodyCount].setY(pieces[tempBodyCount-1].getY());
		}
		
		
		pieces[0].setX(head.getX());
		pieces[0].setY(head.getY());
	}
	
	private class MoveListener implements KeyListener
	{

		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			switch( keyCode ) 
			{ 
				case KeyEvent.VK_UP:
					if(((head.getDirection() + 270) != 360 )&&( head.getDirection() != 270))
					{
						
						nextMove = 270;
						System.out.println("Up");
					}
					break;
				case KeyEvent.VK_DOWN:
					if(((head.getDirection() + 90) != 360 )&&( head.getDirection() != 90))
					{
					
						nextMove = 90;
	           	 		System.out.println("Down");
					}
					break;
				case KeyEvent.VK_LEFT:
					if(((head.getDirection() + 360) != 360 )&&( head.getDirection() != 180))
					{
						
						nextMove = 180;
						System.out.println("Left");
					}
					break;
				case KeyEvent.VK_RIGHT :
					if(((head.getDirection() + 180) != 360 )&&( head.getDirection() != 0))
					{
						
						nextMove = 0;
						System.out.println("Right");
					}
					break;
				case KeyEvent.VK_W:
					if(((head.getDirection() + 270) != 360 )&&( head.getDirection() != 270))
					{
						
						nextMove = 270;
						System.out.println("Up");
					}
					break;
				case KeyEvent.VK_S:
					if(((head.getDirection() + 90) != 360 )&&( head.getDirection() != 90))
					{
					
						nextMove = 90;
	           	 		System.out.println("Down");
					}
					break;
				case KeyEvent.VK_A:
					if(((head.getDirection() + 360) != 360 )&&( head.getDirection() != 180))
					{
						
						nextMove = 180;
						System.out.println("Left");
					}
					break;
				case KeyEvent.VK_D :
					if(((head.getDirection() + 180) != 360 )&&( head.getDirection() != 0))
					{
						
						nextMove = 0;
						System.out.println("Right");
					}
					break;
				case KeyEvent.VK_SPACE :
					if(timer.isRunning() && gameOver == false)
					{
						timer.stop();
						System.out.println("			GAME PAUSED");
					}
					else if(!timer.isRunning() && gameOver == false)
					{
						timer.start();
						System.out.println("			GAME RESUMED");
					}
					else if(gameOver == true)
					{
						newGame();
						timer.start();
					}
			}
	 }

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	

			
	}
}

	
	

	

