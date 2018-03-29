package mining;

public class Edge {

	private Brick start, end;
	private int capacity;
	
	public Edge(Brick s, Brick e, int c){
		start = s;
		end = e;
		capacity = c;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public void addFlow(int f){
		capacity -= f;
	}
	
	public void increaseCapacity(int f){
		capacity += f;
	}

	public Brick getStart(){
		return start;
	}
	
	public Brick getEnd(){
		return end;
	}
	
	public Edge getOpposite(){
		return new Edge(end, start, capacity);
	}
	
	public void print(){
		System.out.print(" --" +capacity +"--> ");
		System.out.print("(" +end.getLayer() +"," +end.getNumber() +")");
	}
	
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(!(o instanceof Edge))
			return false;
		Edge e = (Edge) o;
		return (this.end.equals(e.end) && this.start.equals(e.start));
	}

}
