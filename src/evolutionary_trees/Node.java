package evolutionary_trees;

import java.util.ArrayList;

public class Node {

	private Node leftChild, rightChild, parent;
	private char[] DNA;
	private boolean complete;
	private ArrayList<Character> possible;
	
	public Node(int k){
		DNA = new char[k];
		for(int i = 0; i < k; i++){
			DNA[i] = '_';
		}
		leftChild = null;
		rightChild = null;
		parent = null;
		complete = true;
		possible = new ArrayList<Character>();
	}
	
	public char[] getDNA(){
		return DNA;
	}
	
	public Node getLeftChild(){
		return leftChild;
	}
	
	public Node getRightChild(){
		return rightChild;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public boolean hasChild(){
		return !(leftChild == null && rightChild == null);
	}
	
	public void addChild(int dir, Node n){
		if(dir == 0){
			leftChild = n;
			complete = false;
		}
		else if(dir == 1){
			rightChild = n;
			complete = false;
		}
		else
			System.out.println("ERROR");
	}
	
	public void setParent(Node n){
		parent = n;
	}
	
	public void setDNA(char[] array){
		DNA = array;
	}
	
	public void printTree(Node n){
		System.out.println(n.DNA);
		if(n.leftChild != null){
			printTree(n.leftChild);
		}
		if(n.rightChild != null){
			printTree(n.rightChild);
		}
		
	}
	
	public void addPossible(char c){
		possible.add(c);
	}
	
	public void setPossible(ArrayList<Character> p){
		possible = p;
	}
	
	public ArrayList<Character> getPossible(){
		return possible;
	}
	
	public ArrayList<Character> getIntersection(Node n){
		ArrayList<Character> intersection = new ArrayList<Character>();
		for(Character c : possible){
			if(n.getPossible().contains(c)){
				intersection.add(c);
			}
		}
		return intersection;
	}
	
	public ArrayList<Character> getUnion(Node n){
		ArrayList<Character> union = new ArrayList<Character>();
		for(Character c : possible){
			union.add(c);
		}
		for(Character c : n.getPossible()){
			if(!union.contains(c))
				union.add(c);
		}
		return union;
	}
	
	public boolean isComplete(){
		return complete;
	}
	
	public void setComplete(){
		complete = true;
	}
	
}
