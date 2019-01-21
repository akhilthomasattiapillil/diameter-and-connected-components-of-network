// NS3R2.java 
// usage: java NS3R2 neighborsFile percentOfEdgesToRemove > new neighborsFile
// Randomly removes a percentage of edges from an undirected network
// 

import java.io.*;
import java.util.*;

class NS3R2{

	int N = 0;  // number of vertices/nodes
	ArrayList<String> labels = new ArrayList<String>(); // node labels
	ArrayList<HashSet<Integer>> neighbors = new ArrayList<HashSet<Integer>>();
	HashSet<Integer> toRemove = new HashSet<Integer>();

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

  void removeMirrorEdges(){
	HashSet<Integer> toRemove = new HashSet<Integer>();	
	for (int i = 0; i < N; i++){
		toRemove.clear();
		for (int j : neighbors.get(i)) 
			if (!neighbors.get(j).contains(i)) toRemove.add(j);
		neighbors.get(i).removeIf(j -> toRemove.contains(j));
	}
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
   NS3R2 ns3 = new NS3R2();
   ns3.readNet(args[0]);
   ns3.removeEdges(args[1]);
   ns3.removeMirrorEdges();
   ns3.printNeighbors();
 }
}
