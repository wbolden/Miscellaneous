import java.util.Scanner;


public class Shuffle 
{
	public static void main(String[] args) 
	{
		Scanner reader = new Scanner(System.in);
		long count = 0;
		int n;
		boolean finished = false;
		
		System.out.print("Enter deck size (even number):");
		n = reader.nextInt();
		reader.close();
		
		int[] unshuffled = new int[n];
		int[] shuffler1 = new int[n/2];
		int[] shuffler2 = new int[n/2];
		int[] shuffled = new int[n];
	
	
		for(int x = 0; x < shuffled.length; x++)
		{
			shuffled[x] = x+1;
		}
		for(int x = 0; x < unshuffled.length; x++)
		{
			unshuffled[x] = x+1;
		}
		
		System.out.println("\n");
		
		while(finished == false)
		{
			int y = 0;
			//first half of cards placed in shuffler 1
			for(int x = 0; x < shuffler1.length; x++)
			{
				shuffler1[x] = shuffled[x];
			}
			//second half of cards placed in shuffler 2
			for(int x = shuffler2.length; x < shuffled.length; x++)
			{
				shuffler2[y] = shuffled[x];
				y++;
			}
			y = shuffler1.length-1;
			//cards placed every other spot in shuffled starting at 0
			for(int x = 1; x < shuffled.length; x+=2)
			{
				shuffled[x] = shuffler1[y];
				y--;
			}
			y = shuffler1.length-1;
			//cards placed every other spot in shuffled starting at 1
			for(int x = 0; x < shuffled.length; x+=2)
			{
				shuffled[x] = shuffler2[y];
				y--;
			}

			/*
			 y = 0;
			//cards placed every other spot in shuffled starting at 0
			for(int x = 1; x < shuffled.length; x+=2)
			{
				shuffled[x] = shuffler1[y];
				y++;
			}
			y = 0;
			//cards placed every other spot in shuffled starting at 1
			for(int x = 0; x < shuffled.length; x+=2)
			{
				shuffled[x] = shuffler2[y];
				y++;
			}
			 */
				
			count++;
			System.out.println("Shuffler1"); // Prints out the contents of shuffler 1

			for(int x = 0; x < shuffler1.length; x++)
			{
				System.out.println(shuffler1[x]);
			}
			System.out.println("\n");
			
			System.out.println("Shuffler2"); // Prints out the contents of shuffler 2

			for(int x = 0; x < shuffler2.length; x++)
			{
				System.out.println(shuffler2[x]);
			}
			System.out.println("\n");
			
			
			System.out.println("Times shuffled: "+count); // Prints out the contents of shuffled
			
			for(int x = 0; x < shuffled.length; x++)
			{
				System.out.println(shuffled[x]);
			}
			System.out.println("\n");
			
			
			
			
			for(int x = 0; x < shuffled.length; x++)
			{
				if(shuffled[x] != unshuffled[x])
				{
					break;
				}
						
				if((x == (shuffled.length-1)) && (shuffled[x] == unshuffled[x]))
				{
					finished = true;
					System.out.println("(Deck of "+n+") Shuffled == unshuffled after "+count+" iterations.");
				}
			}	
		}
	}
}