import java.util.ArrayList;
import java.util.Random;


public class City 
{
	//Used to generate the ArrayList of NPC, generate the city, and populate the city with NPC

	static Random gen = new Random();
	
	public static int[][] createCity(int xSize, int ySize, int buildingCount)
	{
		int[][] tempCity = new int[xSize][ySize];
		
		for(int x = 0; x <xSize; x++)
		{
			for(int y = 0; y <ySize; y++)
			{
				tempCity[x][y] = -2;
			}
		}
		
		for(int x = buildingCount; x > 0; x--)
		{
			makeBuildings(xSize, ySize, tempCity );
		}
		
		
		return tempCity;
	}
	
	private static int sign()
	{
		if(gen.nextInt(2)==1)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
	
	private static void makeBuildings(int xSize, int ySize, int[][] t)
	{
		int x1 = gen.nextInt(xSize-1), 
		y1 = gen.nextInt(ySize-1), 
		x2 = (x1+gen.nextInt(50)+14), 
		y2 = (y1+gen.nextInt(50)+14);
		
		if(x2 <0)
		{
			x2 =0;
		}
		
		if(x2 > xSize-1)
		{
			x2 =xSize-1;
		}
		
		if(y2 <0)
		{
			y2 =0;
		}
		
		if(y2 > ySize-1)
		{
			y2 =ySize-1;
		}
		
		for(int x = (x1); x <= (x2); x++)
		{
			for(int y = (y1); y <= (y2); y++)
			{
				if(x == x1 || y == y1 || x == x2 || y == y2)
				{
					t[x][y] = -2;
				}
				else
				{
					t[x][y] = -1;
				}
			}
		}
	}

	
	
	public static void populateList(ArrayList<NPC> n, int maxValue)
	{
		n.add(new NPC(2,n.size()));
		for (int x = n.size();x < maxValue;x++)
		{
			n.add(new NPC(0, x));
		}
		
	}
	
	public static void populateCity(int xSize, int ySize, int maxValue,ArrayList<NPC> npcs)
	{
		
		for(int x = 0; x < maxValue; x++)
		{			
			placeNPCs(xSize, ySize, Grid.theCity, x, npcs);
		}
		
	}

	private static void placeNPCs(int xSize, int ySize, int[][] t, int loc, ArrayList<NPC> npcs)
	{
		boolean done = false;
		while(!done)
		{
			int tempX = gen.nextInt(t.length-1),
			tempY = gen.nextInt(t.length-1);
			
			if(t[tempX][tempY] == -2)
			{
				t[tempX][tempY] = loc;
				npcs.get(loc).setX(tempX);
				npcs.get(loc).setY(tempY);
				done=true;
			}
				
		}
		
	}

	
	
	
	
	
	
}
