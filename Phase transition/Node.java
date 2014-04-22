
import java.util.ArrayList;

public class Node
{
	int nodeID = -1;
	public ArrayList<Node>	connections	= new ArrayList<Node>();
	public boolean			searched	= false;

	public Node(int i)
	{
		nodeID = i; 
	}

	public void connect(Node n)
	{
		if (!connectedTo(n))
		{
			connections.add(n);
			n.connections.add(this);
		}
	}

	private boolean connectedTo(Node n)
	{
		for (int i = 0; i < connections.size(); i++)
		{
			if (connections.get(i) == n)
				return true;

		}
		return false;
	}

	public long connectedLength(int id)
	{
		long tConnections = 0;
		if (!searched)
		{
//			System.out.println("cb ("+id+")\t"+nodeID);
			searched = true;
			tConnections = connections.size()-1;
		//	Node n = connections.get(i);
			for (int i = 0; i < connections.size(); i++)
			{
				tConnections += connections.get(i).connectedLength(nodeID);
			}
		}

		return tConnections;
	}

	public void resetSer()
	{
		searched = false;
	}
	
	public void restart(){
		connections = new ArrayList<Node>();
	}
}
