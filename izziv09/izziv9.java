import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class Node{	
	int id;
	//marks for the algorithm
	//------------------------------------
	boolean marked = false;
	Edge augmEdge = null; //the edge over which we brought the flow increase
	int incFlow = -1; //-1 means a potentially infinite flow
	int predznak = 0;
	int predhodnik = -1;
	//------------------------------------
	ArrayList<Edge> inEdges;
	ArrayList<Edge> outEdges;
	
	public Node(int i) {
		id = i;
		inEdges = new ArrayList<Edge>();
		outEdges = new ArrayList<Edge>();
	}

	
    public boolean oznaci(Edge povezava, int pretok, Node prejsnje, int pr){
        if(marked) return false;
        augmEdge = povezava;
		if(prejsnje != null && prejsnje.incFlow >= 0) incFlow = Math.min(pretok, prejsnje.incFlow);
		else incFlow = pretok;
		marked = true;
		predznak = pr;
		predhodnik = pr>0 ? povezava.startID : povezava.endID;
        return true;
    }
}

class Edge{
	int startID; 
	int endID;
	int capacity; 
	int currFlow;
	
	public Edge(int fromNode, int toNode, int capacity2) {
		startID = fromNode;
		endID = toNode;
		capacity = capacity2;
		currFlow = 0;
	}
}

class Network{
	Node[] nodes;
	
	/**
	 * Create a new network with n nodes (0..n-1).
	 * @param n the size of the network.
	 */
	public Network(int n){
		nodes = new Node[n];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i]= new Node(i);
		}
	}
	/**
	 * Add a connection to the network, with all the corresponding in and out edges.
	 * @param fromNode
	 * @param toNode
	 * @param capacity
	 */
	public void addConnection(int fromNode, int toNode, int capacity){
		Edge e = new Edge(fromNode, toNode, capacity);
		nodes[fromNode].outEdges.add(e);
		nodes[toNode].inEdges.add(e);
	}

	/**
	 * Reset all the marks of the algorithm, before the start of a new iteration.
	 */
	public void resetMarks(){
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].marked = false;
			nodes[i].augmEdge = null;
			nodes[i].incFlow = -1;
			nodes[i].predznak = 0;
			nodes[i].predhodnik = -1;
		}
	}
}

class izziv9 {
    //Ford-Fulkerson max pretok
    public static void main(String[] args) {
        Scanner amogus = new Scanner(System.in);
        int n = amogus.nextInt();
        Network mreza = new Network(n);
        while(amogus.hasNextInt()){
            mreza.addConnection(amogus.nextInt(), amogus.nextInt(), amogus.nextInt());
        }
        amogus.close();


		Node prejsnji = null;
		LinkedList<Node> zaPregledati = new LinkedList<Node>();
		zaPregledati.add(mreza.nodes[0]);

		while(true){
			mreza.nodes[0].marked = true;
			while(!zaPregledati.isEmpty()){
				Node obravnavan = minIndNode(zaPregledati);
				zaPregledati.remove(obravnavan);
				//po + povezavah
				for(Edge e : obravnavan.outEdges){
					if(mreza.nodes[n-1].marked) continue;
					if(e.capacity == e.currFlow) continue; //zasicena
					if(mreza.nodes[e.endID].oznaci(e,e.capacity-e.currFlow,obravnavan,1))
						zaPregledati.add(mreza.nodes[e.endID]);
					
				}
				//po - povezavah
				for(Edge e : obravnavan.inEdges){
					if(mreza.nodes[n-1].marked) continue;
					if(e.currFlow == 0) continue; //zasicena
					if(mreza.nodes[e.startID].oznaci(e,e.currFlow,obravnavan,-1))
						zaPregledati.add(mreza.nodes[e.startID]);
				}
				prejsnji = obravnavan;
			}
			Node ponor = mreza.nodes[n-1];
			if(mreza.nodes[n-1].marked){
				System.out.printf("%d: ",ponor.incFlow);
				Node kaz = ponor;
				while(kaz != null){
					System.out.printf("%d%s",kaz.id, kaz.id==0 ? "\n" : (kaz.predznak>0 ? "+  " : "- "));
					if(kaz.id != 0) kaz.augmEdge.currFlow += kaz.predznak * ponor.incFlow;
					kaz = kaz.predhodnik>=0 ? mreza.nodes[kaz.predhodnik] : null;
				}
				
				//reset
				mreza.resetMarks();
				zaPregledati.add(mreza.nodes[0]);
				prejsnji = null;
			}
			else break;
		}
    }

	public static Node minIndNode(LinkedList<Node> s){
		Node rez = s.get(0);
		for(int i=0; i<s.size(); i++){
			Node o = s.get(i);
			if(o.id<rez.id) rez = o;
		}
		return rez;
	}

    
}