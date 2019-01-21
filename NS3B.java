// NS3B.java 
// decomposes a directed network into strongly connected components
// Usage: java NS3B outNeighbors-file
import java.io.*;
import java.util.*;

public class NS3B{

	int N = 0;  // number of vertices/nodes
	ArrayList<String> labels = new ArrayList<String>(); // node labels
	ArrayList<HashSet<Integer>> outNeighbors = new ArrayList<HashSet<Integer>>();
	ArrayList<HashSet<Integer>> inNeighbors = new ArrayList<HashSet<Integer>>();
	int[] components = null;  
	HashSet<Integer> forward = new HashSet<Integer>();
	HashSet<Integer> backward = new HashSet<Integer>();
	int numberOfSCCs = 0;

  void readNet(String filename){  // fill outNeighbors and N
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
		outNeighbors.add(hset);
	}
	in.close();
	N = labels.size(); 
  }

 void makeBackwardNet(){  // make inNeighbors from outNeighbors, need your code
	for (int i = 0; i < N; i++) 
		inNeighbors.add(new HashSet<Integer>());  // all empty
	// Akhil | 14-Sep-18 | Starts
	int i =0; // for iterating through index of outneighbhours

	HashSet<Integer> hset,inNeighSet;		//inNeighbors label list

	for (String j: labels){
		// take each out neighbhour of the label and add it as a inneighbour for the outneighbhour element.
		hset = outNeighbors.get(i);
		i++;
		for(int e : hset){
			inNeighSet = inNeighbors.get(e);
			inNeighSet.add(Integer.parseInt(j));	//add the inNeighbors 
		}
	}
	//Code to print outneighbhours
	/*
	for(HashSet<Integer> hset1 : inNeighbors){
		for (int k: hset1)
			System.out.print(k);
		System.out.println();
	}*/
	
	// Akhil | 14-Sep-18 | Ends
 }

 void forwardReach(int v){  // all nodes reachable from v
	forward.add(v);  // the result is in forward
	for (int j: outNeighbors.get(v)) 
		if (components[j] < 0 && !forward.contains(j)) forwardReach(j);
 }

 void backwardReach(int v){  // all nodes that can reach v
	backward.add(v);  // the result is in backward
	// your code to fill the set "backward"
	//akhil added on 09/13/2018 --start
	for (int j: inNeighbors.get(v))
		if(components[j]<0 && !backward.contains(j)) backwardReach(j);
	 //akhil added on 09/13/2018 -- end
	 }
	 
.
 void findSCCs(){  // only report those with more than one node
	components = new int[N]; 
	for (int i = 0; i < N; i++) components[i] = -1; 
	forward.clear(); backward.clear();
	for (int i = 0; i < N; i++) if (components[i] == -1){
		forward.clear(); backward.clear();
		forwardReach(i); backwardReach(i);
		forward.retainAll(backward);  // intersection in forward
		int size = forward.size();  // size of the latest SCC
		if (size > 1) System.out.println(numberOfSCCs + " " + forward.size());
		for (int j: forward) components[j] = numberOfSCCs;
		numberOfSCCs++;
	}
 }

 
 public static void main(String[] args){
   if (args.length < 1){ System.err.println("Usage: java NS3B neighbors-file");
	return; } 
   NS3B ns3 = new NS3B();
   ns3.readNet(args[0]);  
   ns3.makeBackwardNet();
  ns3.findSCCs();
 }   
} 
