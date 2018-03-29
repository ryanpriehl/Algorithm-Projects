package hotlines;

import java.util.ArrayList;

public class Node {

	private int value;
	private ArrayList<Integer> previous;
	
	public Node(int v){
		value = v;
		previous = new ArrayList<Integer>();
	}
	
	public int getValue(){
		return value;
	}
	
	public void addPrevious(int n){
		previous.add(n);
	}
	
	public void addPrevious(ArrayList<Integer> prev){
		for(int n : prev){
			previous.add(n);
		}
	}
	
	public ArrayList<Integer> getPrevious(){
		return previous;
	}
	
	public void clearPrevious(){
		previous.clear();
	}
	
	public void printPrevious(){
		for(int i = 0; i < previous.size() - 1; i++){
			System.out.print(previous.get(i) + ",");
		}
		if(previous.size() > 0)
			System.out.println(previous.get(previous.size() - 1) + "," + value);
	}
	
	public void print(){
		System.out.print(value);
	}
}
