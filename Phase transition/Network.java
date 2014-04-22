
import java.util.ArrayList;

public class Network
{

	private final static int		ITER		= 700;
	private final static int		NODETOTAL	= 10;
	public static ArrayList<Node>	nodeList	= new ArrayList<Node>();
	public static long[]			set			= new long[ITER];
	public static long[]			average		= new long[ITER];

	public static void test()
	{

		for (int i = NODETOTAL; i > 0; i--)
		{
			nodeList.add(new Node(i));
		}
		runTest();
		for (int i = 0; i < set.length; i++)
		{
			System.out.println(set[i]);
		}

	}

	public static void runTest()
	{
		int fi;
		int ei = 0;
		int gen;
		boolean cont = true;
		for (long it = 1; it <= 999999999; it++)
		{
			System.out.println("iteration: " + (it));
			for (int i = 0; i < ITER; i++)
			{
				fi = (int) (Math.random() * NODETOTAL);
				while (cont)
				{
					gen = (int) (Math.random() * NODETOTAL);
					ei = gen;
					cont = gen == fi ? true : false;

				}
				cont = true;
				// System.out.println("fi \t \t"+fi);
				// System.out.println("ei \t \t"+ei);

				nodeList.get(fi).connect(nodeList.get(ei));
				set[i] += nodeList.get(fi).connectedLength(-1) + 1;

				// System.out.println(nodeList.get(fi).connectedLength(-1)+1);

				for (int x = 0; x < (nodeList.size()); x++)
				{
					nodeList.get(x).resetSer();
				}
			}
			for (int x = 0; x < (nodeList.size()); x++)
			{
				nodeList.get(x).restart();
			}

			for (int x = 0; x < set.length; x++)
			{
				//System.out.println(set[x]);
				average[x] = set[x] / it;
			//	System.out.println(average[x]);
			//	assert average[x] >= 0;

			}
	//		System.out.println();
			//tt

		}
	}
}
