// NS3R.java 
// usage: java NS3R neighborsFile percentOfEdgesToRemove > new neighborsFile
// Randomly removes a percentage of edges from a directed or bipartite graph
// 

import java.io.*;
import java.util.*;

class NS3R{

	int N = 0;  // number of vertices/nodes
	ArrayList<String> labels = new ArrayList<String>(); // node labels
	ArrayList<HashSet<Integer>> neighbors = new ArrayList<HashSet<Integer>>();

  void readNet(String filename){
	Scanner in = null;
	try {
		in = new Scanner(new File(filename));
	} catch (FileNotFoundException e){
		System.err.println(filename + " not found");
		System.exit(1);
	}
	while (in.hasNextLine()){
		String[] terms = in.nextLine().split(" ");
		labels.add(terms[0]);
		HashSet<Integer> hset = new HashSet<Integer>();
		for (int j = 1; j < terms.length; j++) hset.add(Integer.parseInt(terms[j]));
		neighbors.add(hset);
	}
	in.close();
	N = labels.size();
  }

  void removeEdges(String percent){
	double p = Double.parseDouble(percent) / 100.0;
	Random random = new Random();
	for (int i = 0; i < N; i++)
		neighbors.get(i).removeIf(x -> random.nextDouble() < p);
 }

  void printNeighbors(){
	for (int i = 0; i < N; i++){
		System.out.print(labels.get(i));
		for (int j: neighbors.get(i)) System.out.print(" " + j);
		System.out.println();
	}
  }

 public static void main(String[] args){
   if (args.length < 2){
     System.err.println("Usage: java NS3R neighborsFile percent");
     System.exit(1);
   }
   NS3R ns3 = new NS3R();
   ns3.readNet(args[0]);
   ns3.removeEdges(args[1]);
   ns3.printNeighbors();
 }
}
