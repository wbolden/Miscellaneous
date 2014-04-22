import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Playfair 
{
//	int[][] key = new int[5][5];
	static String output = "";
	static char[][] key = new char[5][5];
	static int x1,y1,x2,y2;
	static int xXloc, yXloc;
//	static boolean aFirst;
	static int condition = 1;
	
	public static void main(String[] args) throws FileNotFoundException
	{
//		char[][] key = new char[5][5];
		String input;
//		String output = "";
		File f = new File("PlayfairKey.txt");
		Scanner reader = new Scanner(f);
		Scanner in = new Scanner(System.in);
		//fills in the key array
		for(int y = 0; y < 5; y++)
		{
	//		System.out.println("Reached x");
			for(int x = 0; x < 5; x++)
			{
				String temp = reader.nextLine();
	//			System.out.println("Reached y");
				key[x][y] = temp.charAt(0);
			}
		}
		
		reader.close();
		/*
		for(int y = 0; y < 5; y++)
		{
			for(int x = 0; x < 5; x++)
			{
				System.out.print(key[x][y]+"  ");
				//System.out.print(x+","+y+" ");
			}
			System.out.println("\t");
		}
		*/
		
		/*
		for(int y = 0; y < 5; y++)
		{
			for(int x = 0; x < 5; x++)
			{
				//System.out.print(key[x][y]+"  ");
				System.out.print(x+","+y+" ");
			}
			System.out.println("\t");
		}
		*/
		
		
		
		//test
	//	System.out.println("Enter 1 to encrypt, 2 to decrypt, -1 to end the program");
		while(condition != -1)
		{
			
			System.out.println("Enter 1 to encrypt, 2 to decrypt, -1 to end the program");
		
			condition = in.nextInt();
		
			if(condition == -1)
			{
				break;
			}
			
			System.out.println("Enter a String:");
			in.nextLine();
			input = in.nextLine();
			
			input = input.replaceAll(" ","");
		
			input = input.toLowerCase();
			
			
			if(condition == 1)
			{
				input = input.replaceAll("j","i");
				char[] tempInputArray = input.toCharArray();
				int found = 1;
				
				for(int i =0; i < tempInputArray.length-1; i++)
				{
					
					if(tempInputArray[i] == tempInputArray[i+1])
					{
						
						input = input.substring(0, i+found) + "x" + input.substring(i+found, input.length());
						found++;
					}
				}
				
				if(input.length() % 2 != 0)
				{
					input+="x";
				}
				
//				System.out.println(input);
				
				char[] inputArray = input.toCharArray();
				System.out.println("Encrypted:");
				encrypt(inputArray);
			}
			else if(condition == 2)
			{
//				System.out.println(input);
				char[] inputArray = input.toCharArray();
				System.out.println("Decrypted:");
				decrypt(inputArray);
			}
			
			System.out.println("\n\n");
			
		}

		
	}
	
	
	
	public static void encrypt(char[] input)
	{
		char a, b;
		for(int i = 0; i < input.length; i+=2)
		{
			a = input[i];
			b = input[i+1];
			searchArray(a, b);
			
			if((x1 != x2)&& (y1 != y2))
			{
				
				System.out.print(key[x2][y1]);
				System.out.print(key[x1][y2]);
				System.out.print(" ");
			}
			else if(y1 == y2)
			{
				if(x1+1 < key.length)
				{
					System.out.print(key[x1+1][y1]);
				}
				else
				{
					System.out.print(key[0][y1]);
				}
				
				if(x2+1 < key.length)
				{
					System.out.print(key[x2+1][y2]);
				}
				else
				{
					System.out.print(key[0][y2]);
				}
				System.out.print(" ");
				
			}
			else if(x1 == x2)
			{
				
				if(y1+1 < key.length)
				{
					System.out.print(key[x1][y1+1]);				
				}
				else
				{
					System.out.print(key[x1][0]);			
				}
				
				if(y2+1 < key.length)
				{
					System.out.print(key[x2][y2+1]);				
				}
				else
				{
					System.out.print(key[x2][0]);				
				}
				System.out.print(" ");
			}
			
		}
	}
	
	public static void decrypt(char[] input)
	{
		char a, b;
		for(int i = 0; i < input.length; i+=2)
		{
			a = input[i];
			b = input[i+1];
			searchArray(a, b);
			
			
			
			if((x1 != x2)&& (y1 != y2))
			{
				if(notX(x2, y1))
				{
					System.out.print(key[x2][y1]);
				}
				if(notX(x1, y2))
				{
					System.out.print(key[x1][y2]);
				}
			}
			else if(y1 == y2)
			{
				if(x1-1 > -1)
				{
					if(notX(x1-1, y1))
						System.out.print(key[x1-1][y1]);
				}
				else
				{	
					if(notX(4, y2))
						System.out.print(key[4][y1]);
				}
				
				if(x2-1 > -1)
				{
					if(notX(x2-1, y2))
						System.out.print(key[x2-1][y2]);
				}
				else
				{
					if(notX(4, y2))
						System.out.print(key[4][y2]);
				}
				
			}
			else if(x1 == x2)
			{
				
				if(y1-1 > -1)
				{
					if(notX(x1, y1-1))
						System.out.print(key[x1][y1-1]);				
				}
				else
				{
					if(notX(x1, 4))
						System.out.print(key[x1][4]);			
				}
				
				if(y2-1 > -1)
				{
					if(notX(x2, y2-1))
						System.out.print(key[x2][y2-1]);				
				}
				else
				{
					if(notX(x2, 4))
						System.out.print(key[x2][4]);				
				}
				
			}
			
		}
	}
	
	public static boolean notX(int x, int y)
	{
		if(key[x][y] == ("x").charAt(0))
		{
			return false;
		}
		return true;
	}
	
	
	public static void searchArray(char a, char b)
	{
		for(int x = 0; x < 5; x++)
		{
			for(int y = 0; y < 5; y++)
			{
				if(key[x][y] == a)
				{
					x1 = x;
					y1 = y;
				}
			}
		}
		
		for(int x = 0; x < 5; x++)
		{
			for(int y = 0; y < 5; y++)
			{
				if(key[x][y] == b)
				{
					x2 = x;
					y2 = y;
				}
			}
		}
		
		
		
	}

	
	
}
