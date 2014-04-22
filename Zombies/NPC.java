import java.util.Random;

public class NPC 
{
	protected int x, y, moveX, moveY, // x location, y location, move directions
	id, //The NPC's position in the ArrayList, used in theCity array
	speed, //Speed at which the NPC moves, the ratio of ActionEvents to moves
	changeRate, //Rate at which an NPC changes direction, filled in by subclasses
	direction, //A number 0-3, the current direction
	state, //The current state of the NPC 0 = survivor (Light Blue), 1 = panicked (Yellow), 2 = zombie (Red)
	lockTime = 0;
	protected boolean threat, //Zombies and scared survivors are marked as true, otherwise false (DELETE THIS: UNUSED)
	locked = false; //Locks the current direction until changed by method
	static Random gen = new Random(); //used in generating direction
	
	
	
	
	public NPC()
	{
//		x = gen.nextInt(300);
//		y = gen.nextInt(300);
		state = 0;
		speed = 2;
		changeDirection();
	}
	
	public NPC(int s, int i)
	{
//		x = gen.nextInt(300);
//		y = gen.nextInt(300);
		state = s;
		id = i;
		changeRate = 10;
		setSpeed();
		changeDirection();
	}
	
	public NPC(int xLoc, int yLoc, int s, int i)
	{
		x = xLoc;
		y = yLoc;
		state = s;
		id = i;
		setSpeed();
		changeDirection();
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int newX)
	{
		x = newX;
	}
	
	public void setY(int newY)
	{
		y = newY;
	}
	
	public boolean isThreat()
	{
		return threat;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public int getState()
	{
		return state;
	}
	
	public void move(int timerCount)
	{		
		if(!locked && gen.nextInt(changeRate) == 1)
		{
			changeDirection();
		}
		if(locked)
		{
			lockTime++;
			if(lockTime > 10)
			{
				unlock();
			}
		}
		
		if(timerCount%speed == 0)
		{
			if(!(x+moveX < Grid.theCity.length)||!(x+moveX > -1)||!(y+moveY < Grid.theCity.length)||!(y+moveY > -1))
			{
			//	System.out.println("no move made");
			}
			/*
			else if(Grid.theCity[x+moveX][y+moveY] == -2)
			{
				Grid.theCity[x][y] = -2;
				x += moveX;
				y += moveY;
				Grid.theCity[x][y] = id;
			}
			*/
			/*//No collision w/ npcs
			 else if(Grid.theCity[x+moveX][y+moveY] == -2 || Grid.theCity[x+moveX][y+moveY] >= 0)
			{
				Grid.theCity[x][y] = -2;
				x += moveX;
				y += moveY;
				Grid.theCity[x][y] = id;
			} 
			 
			 */
			
			//No collision at all
			 else if(state == 2)
			 {
				 if((Grid.theCity[x+moveX][y+moveY] == -1))
				 {
					 if(gen.nextInt(10) == 0)
					 {
						 Grid.theCity[x][y] = -2;
						 x += moveX;
						 y += moveY;
						 Grid.theCity[x][y] = id;
					 }
				 }
				 else
				 {
					 Grid.theCity[x][y] = -2;
					 x += moveX;
					 y += moveY;
					 Grid.theCity[x][y] = id;
				 }
			 }
			 else if(Grid.theCity[x+moveX][y+moveY] == -2)
			{
				Grid.theCity[x][y] = -2;
				x += moveX;
				y += moveY;
				Grid.theCity[x][y] = id;
			} 
			 
			 
		}
		
	}
	
	public void changeDirection()
	{
		direction = gen.nextInt(4);
		
		if(direction == 0) 		//Right
		{
			moveX = 1;
			moveY = 0;
		}
		else if(direction ==1) 	//Left
		{
			moveX = -1;
			moveY = 0;
		}
		else if(direction ==2)	//Down
		{
			moveX = 0;
			moveY = 1;
		}
		else					//Up
		{
			moveX = 0;
			moveY = -1;
		}
	}
	
	public void respondTo(NPC f)
	{
		if(state < 2)
		{
			if(f.getState() == 1)
			{
				panick();

			}
			else if(f.getState() == 2)
			{
				turnAround();
				lock();
				panick();
			}
			else
			{
				calm();
			}
		}
		else if(state == 2 && f.getState() <2)
		{
			lock();
			nextTo(f);
		}
	}
	
	public void panick()
	{
		
		state = 1;
		setSpeed();

	}
	
	public void calm()
	{
		if(gen.nextInt(3)==1)
		{
			state = 0;
			setSpeed();
		}

	}
	
	public void zombify()
	{
		state = 2;
		setSpeed();
		Grid.survivorCount--;
		Grid.zombieCount++;
	}
	
	public void setSpeed()
	{
		if(state == 0) 
		{
			speed = 2;
		}
		else if (state == 1)
		{
			speed = 1;
		}
		else
		{
			speed = 4;
		}
		
	}
	
	public void turnAround()
	{
		moveX *= -1;
		moveY *= -1;
		
		if(moveX == 1) 		    //Right
		{
			direction = 0;
		}
		else if(moveX == -1) 	//Left
		{
			direction = 1;
		}
		else if(moveY == 1)	    //Down
		{
			direction = 2;
		}
		else					//Up
		{
			direction = 3;
		}
	}
	
	private void lock()
	{
		locked = true;
	}
	
	private void unlock()
	{
		if(gen.nextInt(3) == 0)
		{
			locked = false;
			lockTime = 0;
		}
	}
	
	//Call only if this is a ZOMBIE! Call only if f is in the line of sight of this
	private void nextTo(NPC f)
	{
		if(Math.abs(x - f.getX()) == 1 || Math.abs(y - f.getY()) == 1 )
		{
			f.zombify();
		}
	}
	
	//checks if there are any NPCs ahead
	public int look()
	{
		//Takes start location/direction from the object that calls it, returns when it finds another NPC or runs out of space
		int tempX = x, tempY = y;
			
		if(direction == 0)
		{
			while(tempX<Grid.theCity.length-1)
			{
				tempX++;
				
				//update respond to!!!!
			
				
			if(Grid.theCity[tempX][tempY] > -2)
				{
					return Grid.theCity[tempX][tempY];
				}
			}
		}
		else if(direction ==1)
		{
			while(tempX>0)
			{
				tempX--;
				if(Grid.theCity[tempX][tempY] > -2)
				{
					return Grid.theCity[tempX][tempY];
				}
			}
		}
		else if(direction ==2)
		{
			while(tempY<Grid.theCity.length-1)
			{
				tempY++;
				if(Grid.theCity[tempX][tempY] > -2)
				{
					return Grid.theCity[tempX][tempY];
				}
			}
		}
		else
		{
			while(tempY>0)
			{
				tempY--;
				if(Grid.theCity[tempX][tempY] > -2)
				{
					return Grid.theCity[tempX][tempY];
				}
			}
		}
				
		return -1;
	}
	
	public String toString()
	{
		String str ="State: "+state
				+"\n Locked: "+locked
				+"\n Direction: "+direction;
		return str;
	}
	
}
