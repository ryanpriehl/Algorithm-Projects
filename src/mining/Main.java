package mining;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static ArrayList<ArrayList<Brick>> layers;
	private static Brick source = new Brick(0, 0, 0, 0, -1);
	private static Brick sink = new Brick(0, 0, 0, -1, 0);
	private static ArrayList<Edge> path, savedPath;
	private static boolean pathFound;

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File("pit1.txt"));
		layers = new ArrayList<ArrayList<Brick>>();
		scan.useDelimiter("\\s|\\D");
		int numCounter = 0;
		int layerCounter = 0;
		while(scan.hasNextLine()){
			String layerString = scan.nextLine();
			ArrayList<Brick> layer = new ArrayList<Brick>();
			while(layerString.length() > 0){
				layerString = layerString.substring(layerString.indexOf('(') + 1);
				int width = Integer.valueOf(layerString.substring(0, layerString.indexOf(',')));
				layerString = layerString.substring(layerString.indexOf(',') + 1);
				int value = Integer.valueOf(layerString.substring(0, layerString.indexOf(',')));
				layerString = layerString.substring(layerString.indexOf(',') + 1);
				int cost = Integer.valueOf(layerString.substring(0, layerString.indexOf(')')));
				layerString = layerString.substring(layerString.indexOf(')') + 1);
				Brick b = new Brick(width, value, cost, numCounter, layerCounter);
				layer.add(b);
				numCounter++;
			}
			numCounter = 0;
			layers.add(layer);
			layerCounter++;
		}
		
		// setting bounds
		for(int i = 0; i < layers.size(); i++){
			for(int j = 0; j < layers.get(i).size(); j++){
				if(j == 0)
					layers.get(i).get(j).setBounds(0, layers.get(i).get(j).getWidth());
				else
					layers.get(i).get(j).setBounds(layers.get(i).get(j - 1).getEnd(), layers.get(i).get(j - 1).getEnd() + layers.get(i).get(j).getWidth());
			}
		}
		
		// adding prerequisite edges
		for(int i = 1; i < layers.size(); i++){
			for(int j = 0; j < layers.get(i - 1).size(); j++){
				for(int k = 0; k < layers.get(i).size(); k++){
					if(layers.get(i - 1).get(j).isAbove(layers.get(i).get(k))){
						layers.get(i).get(k).addEdge(new Edge(layers.get(i).get(k), layers.get(i - 1).get(j), Integer.MAX_VALUE));
					}
				}
			}
		}
		
		// adding source and sink edges
		for(ArrayList<Brick> brickList : layers){
			for(Brick  b : brickList){
				if(b.getProfit() > 0)
					source.addEdge(new Edge(source, b, b.getProfit()));
				else
					b.addEdge(new Edge(b, sink, Math.abs(b.getProfit())));
			}
		}

		// prints bricks with their edges
		/*for(ArrayList<Brick> brickList : layers){
			for(Brick  b : brickList){
				b.print();
				System.out.println();
				for(Edge e : b.getEdges()){
					System.out.print("     ");
					e.print();
					System.out.println();
				}
				System.out.println();
			}
		}*/
		
		path = new ArrayList<Edge>();
		savedPath = new ArrayList<Edge>();
		
		// Ford-Fulkerson
		pathFound = true;
		while(pathFound){
			path.clear();
			savedPath.clear();
			
			pathFound = false;
			findPath(source);
			
			// prints paths
			/*for(Edge e : savedPath){
				e.print();
			}
			System.out.println();*/
			
			int maxFlow = Integer.MAX_VALUE;
			for(int i = 0; i < savedPath.size(); i++){
				if(savedPath.get(i).getCapacity() <= maxFlow){
					maxFlow = savedPath.get(i).getCapacity();
				}
			}
			
			for(Edge e : savedPath){
				e.addFlow(maxFlow);
				if(!e.getEnd().hasEdgeTo(e.getStart())){
					e.getEnd().addEdge(new Edge(e.getEnd(), e.getStart(), maxFlow));
				}
				else{
					for(Edge ed : e.getEnd().getEdges()){
						if(ed.equals(new Edge(e.getEnd(), e.getStart(), 0)))
							ed.increaseCapacity(maxFlow);
					}
				}
			}
			clearBricks();
		}
		
		findMinCut(source);
		
		// printing bricks to remove
		int toggle = 0;
		PrintWriter write = new PrintWriter("blocks.txt");
		for(ArrayList<Brick> brickList : layers){
			for(int i = 0; i < brickList.size(); i++){
				if(brickList.get(i).isRemoved()){
					if(toggle == 1){
						System.out.print(",");
						write.print(",");
					}
					System.out.print(brickList.get(i).getNumber());
					write.print(brickList.get(i).getNumber());
					toggle = 1;
				}
			}
			toggle = 0;
			System.out.println();
			write.println();
		}
		write.close();
	}
	
	public static void findPath(Brick b){
		if(b.equals(sink) && !pathFound){
			pathFound = true;
			for(Edge e : path){
				savedPath.add(e);
			}
		}
		else if(!pathFound && !b.wasChecked()){
			b.toggleChecked();
			for(Edge e : b.getEdges()){
				if(!path.contains(e) && !path.contains(e.getOpposite()) && e.getCapacity() > 0){
					path.add(e);
					findPath(e.getEnd());
					path.remove(e);
				}
			}
		}
	}
	
	public static void clearBricks(){
		for(ArrayList<Brick> brickList : layers){
			for(Brick b : brickList){
				if(b.wasChecked()){
					b.toggleChecked();
				}
			}
		}
		if(source.wasChecked())
			source.toggleChecked();
		if(sink.wasChecked())
			sink.toggleChecked();
	}
	
	public static void findMinCut(Brick b){
		b.setRemoved();
		for(Edge e : b.getEdges()){
			if(e.getCapacity() > 0){
				if(!e.getEnd().isRemoved())
					findMinCut(e.getEnd());
			}
		}
	}

}
