//MANSI SRINIVAS
//B01035912
//MST USING KRUSKAL'S ALGORITHM
import java.io.*;
import java.util.*;

//this class is to keep track of all the edges and it's characteristics like the weight and
//the source node and the destination node.
class Edges {
    int m, n, wt;
    //this is the constructor which is used to initialize the variables.
    public Edges(int source, int destination, int weight) {
        this.m = source;
        this.n = destination;
        this.wt = weight;
    }
}


//this is the class to store the graph which will be created after reading from input file
class Inp_graph
{
	
    int V, E;
    List<Edges> edges;
    //creating an ArrayList to store the graph edges
    public Inp_graph(int v, int e) {
        V = v;
        E = e;
        edges = new ArrayList<>();
    }
    //the function to add these information into the ArrayList
    public void addEdge(int src, int dest, int weight) {
    	
        edges.add(new Edges(src, dest, weight));
    }
    //this function is to sort the edges with weights and to find the parents of the source and destination nodes
    public List<Edges> kruskalMST() {
        List<Edges> result = new ArrayList<>();
        Collections.sort(edges, Comparator.comparingInt(e -> e.wt));

        int[] parent = new int[V];
        int[] rank = new int[V];
        makeSet(parent, rank);

        int edgeCount = 0;

        for (Edges edge : edges) {
            if (edgeCount >= V - 1) {
                break;  // MST is complete
            }

            int u = edge.m;
            int v = edge.n;

            int uparent = findParent(parent, u);
            int vparent = findParent(parent, v);
            //this is to call the unionSet function when the parent nodes of the two nodes are not same
            if (uparent != vparent) {
                result.add(edge);
                //this is to make the union between two edges.
                unionSet(parent, rank, uparent, vparent);
                edgeCount++;
            }
        }

        return result;
    }
    //this function is to make the disjoint set
    private void makeSet(int[] parent, int[] rank) {
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    //this is to find the parent of the node
    private int findParent(int[] parent, int node) {
        if (parent[node] == node) {
            return node;
        }
        //this is for the path compression
        return parent[node] = findParent(parent, parent[node]);
    }
    //this function is used when we need to make the union of two nodes in a disjoint set
    private void unionSet(int[] parent, int[] rank, int u, int v) {
        int parU = findParent(parent, u);
        int parV = findParent(parent, v);
       //this code below is used to group the nodes under one parent node by comparing the ranks
        if (rank[parU] < rank[parV]) {
            parent[parU] = parV;
        } else if (rank[parU] > rank[parV]) {
            parent[parV] = parU;
        } else {
            parent[parU] = parV;
            rank[parV]++;
        }
    }
}

public class Mst {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Mst <input_file>");
            System.exit(1);
        }

        String inputFile = args[0];

        try (BufferedReader bf = new BufferedReader(new FileReader(inputFile))) {
            String line = bf.readLine();
            String[] parts = line.split(" ");
            int V = Integer.parseInt(parts[0]);
            int E = Integer.parseInt(parts[1]);

            Inp_graph graph = new Inp_graph(V, E);

            while ((line = bf.readLine()) != null) {
                parts = line.split(" ");
                int src = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                graph.addEdge(src,dest,weight);
            }

            List<Edges> mst = graph.kruskalMST();

            
            //System.out.println("Edges in the Minimum Spanning Tree:");
            int finalweight = 0; // initializing the value of finalweight to 0

            for (Edges edge : mst) {
                //System.out.println(edge.m + " - " + edge.n + " : " + edge.wt);
                finalweight += edge.wt; // Add the weight of the current edge to the total
            }

            System.out.println("Total Weight of MST: " + finalweight);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


