package mining;

import java.util.ArrayList;

public class Brick implements Comparable<Brick>{

	private int width, start, end, profit, number, layer;
	private ArrayList<Edge> edges;
	private boolean removed, checked;
	
	public Brick(int w, int v, int c, int n, int l){
		width = w;
		profit = v - c;
		number = n;
		layer = l;
		removed = false;
		checked = false;
		edges = new ArrayList<Edge>();
	}
	
	public void setBounds(int s, int e){
		start = s;
		end = e;
	}
	
	public int getStart(){
		return start;
	}
	
	public int getEnd(){
		return end;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getProfit(){
		return profit;
	}
	
	public int getNumber(){
		return number;
	}
	
	public int getLayer(){
		return layer;
	}
	
	public void toggleChecked(){
		checked = !checked;
	}
	
	public boolean wasChecked(){
		return checked;
	}
	
	public void setRemoved(){
		removed = true;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	
	public void addEdge(Edge e){
		edges.add(e);
	}
	
	public void removeEdge(Edge e){
		edges.remove(e);
	}
	
	public boolean hasEdgeTo(Brick b){
		for(Edge e : edges){
			if(e.getEnd().equals(b)){
				return true;
			}
		}
		return false;
	}
	
	public void print(){
		System.out.print("(" +layer +"," +number +")");
	}
	
	public void println(){
		System.out.println("(" +layer +"," +number +")");
	}
	
	public boolean equals(Brick b){
		return (layer == b.layer && number == b.number);
	}
	
	public boolean isAbove(Brick b){
		if(start < b.end && start > b.start)
			return true;
		if(end < b.end && end > b.start)
			return true;
		if(b.start < end && b.start > start)
			return true;
		if(b.end < end && b.end > start)
			return true;
		if(start == b.start || end == b.end)
			return true;
		return false;
	}

	@Override
	public int compareTo(Brick o) {
		if(layer < o.layer)
			return -1;
		if(layer > o.layer)
			return 1;
		if(layer == o.layer){
			if(number < o.number)
				return -1;
			if(number > o.number)
				return 1;
		}
		return 0;
	}
	
}
