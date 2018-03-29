package evolutionary_trees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static PrintWriter write;
	private static String path = "";
	private static int length;
	private static int parsimony = 0;
	
	public static void main(String[] args) throws FileNotFoundException{
		
		/**
		 * First builds the tree and fills in the leaves.
		 * Then fills in the internal nodes from the bottom up.
		 * Each node keeps track of the set of POSSIBLE bases (A,C,G,T) for a position according to the following:
		 * 1. If the INT
		 */
		
		// For reading/writing the files
		Scanner scan = new Scanner(new File("genetics4.txt"));
		write = new PrintWriter("tree.txt");
		
		// The directions for building the tree
		String directions = scan.nextLine();
		String DNA = scan.nextLine();
		length = DNA.length();
		
		Node root = new Node(length);
		Node current = root;
		
		// Building the tree
		while(directions.length() > 0){
			char direction = directions.charAt(0);
			directions = directions.substring(1);
			if(direction == 'L'){
				Node newNode = new Node(length);
				newNode.setParent(current);
				current.addChild(0, newNode);
				current = newNode;
			}
			else if(direction == 'R'){
				Node newNode = new Node(length);
				newNode.setParent(current);
				current.addChild(1, newNode);
				current = newNode;
			}
			else if(direction == 'U'){
				// Fills in the leaves
				if(!current.hasChild()){
					current.setDNA(DNA.toCharArray());
					if(scan.hasNextLine())
						DNA = scan.nextLine();
				}
				current = current.getParent();
			}
			else{
				System.out.println("ERROR");
			}
		}
		
		current = root;
		
		for(int i = 0; i < length; i++){
			fillPossible(root, i);
			root.getDNA()[i] = root.getPossible().get(0);
			fillDNA(root.getLeftChild(), i);
			fillDNA(root.getRightChild(), i);
			clear(root);
		}
		
		printTree(root);
		write.close();
		
		findParsimony(root);
		System.out.println("parsimony: " +parsimony);
		
	}
	
	public static void printTree(Node n) throws FileNotFoundException{
		
		System.out.print(path +":");
		for(char c : n.getDNA()){
			System.out.print(c);
		}
		/*for(char c : n.getPossible()){
			System.out.print(c);
		}*/
		System.out.println();
		
		write.print(path +":");
		for(char c : n.getDNA()){
			write.print(c);
		}
		write.println();
		
		if(n.getLeftChild() != null){
			path += "0";
			printTree(n.getLeftChild());
			path = path.substring(0, path.length() - 1);
		}
		if(n.getRightChild() != null){
			path += "1";
			printTree(n.getRightChild());
			path = path.substring(0, path.length() - 1);
		}

	}
	
	public static void fillPossible(Node n, int i){
		if(n.getLeftChild() != null && n.getRightChild() != null){
			fillPossible(n.getLeftChild(), i);
			fillPossible(n.getRightChild(), i);
			
			if(n.getLeftChild().getIntersection(n.getRightChild()).size() > 0)
				n.setPossible(n.getLeftChild().getIntersection(n.getRightChild()));
			else
				n.setPossible(n.getLeftChild().getUnion(n.getRightChild()));
			
		}
		else{
			n.addPossible(n.getDNA()[i]);
		}
	}
	
	// Fills in the actual DNA base from the set of possible bases at base i.
	public static void fillDNA(Node n, int i){
		if(n.getLeftChild() != null && n.getRightChild() != null){
			
			if(n.getPossible().contains(n.getParent().getDNA()[i]))
				n.getDNA()[i] = n.getParent().getDNA()[i];
			else
				n.getDNA()[i] = n.getPossible().get(0);


			fillDNA(n.getLeftChild(), i);
			fillDNA(n.getRightChild(), i);
		}
	}
	
	// Recursively clears all the sets of possible bases.
	public static void clear(Node n){
		
		n.getPossible().clear();
		
		if(n.getLeftChild() != null && n.getRightChild() != null){
			clear(n.getLeftChild());
			clear(n.getRightChild());
		}
	}
	
	// Recursively finds the parsimony of the tree.
	public static void findParsimony(Node n){
		if(n.getLeftChild() != null && n.getRightChild() != null){
			findParsimony(n.getLeftChild());
			findParsimony(n.getRightChild());
			
			for(int i = 0; i < n.getDNA().length; i++){
				if(n.getDNA()[i] != n.getLeftChild().getDNA()[i]){
					parsimony++;
				}
				if(n.getDNA()[i] != n.getRightChild().getDNA()[i]){
					parsimony++;
				}
			}
		}
	}

}
