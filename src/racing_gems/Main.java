package racing_gems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static ArrayList<Node> gems;
	private static PrintWriter print;

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scan = new Scanner(new File("gems.txt"));
		print = new PrintWriter("race.txt");
		scan.useDelimiter("\\s|,");
		int n = scan.nextInt();
		double r = scan.nextDouble();
		double w = scan.nextDouble();
		double h = scan.nextDouble();
		scan.nextLine();

		// reading the file
		gems = new ArrayList<Node>();
		while (scan.hasNext()) {
			double x = scan.nextDouble();
			double y = scan.nextDouble();
			int v = scan.nextInt();
			Node node = new Node(v, x, y);
			gems.add(node);
		}

		// sorts by value on y-axis
		gems.sort(null);

		// adds every gem that can be reached from this gem as a child
		for (int i = 0; i < gems.size(); i++) {
			for (int j = i + 1; j < gems.size(); j++) {
				if (gems.get(i).canReach(gems.get(j), r))
					gems.get(i).addChild(gems.get(j));
			}
		}

		// removes loops (makes graph acyclic)
		for (Node node : gems) {
			node.removeLoops();
		}

		// finds the max value that can be gotten if starting from this gem
		for (Node node : gems) {
			findMaxValue(node);
		}

		// prints the highest value path
		printMax(gems);
		print.close();
	}

	public static void findMaxValue(Node n) {
		if (!n.getChildren().isEmpty()) {
			for (Node child : n.getChildren()) {
				findMaxValue(child);
			}
			n.setMaxValue(n.getValue() + n.maxChild());
		} else {
			n.setMaxValue(n.getValue());
		}
	}

	public static void printMax(ArrayList<Node> nodes) throws FileNotFoundException {
		if (nodes.isEmpty())
			return;
		int maxIndex = 0;
		int max = nodes.get(0).getMaxValue();
		for (int i = 1; i < nodes.size(); i++) {
			if (nodes.get(i).getMaxValue() > max) {
				maxIndex = i;
				max = nodes.get(i).getMaxValue();
			}
		}
		Node n = nodes.get(maxIndex);
		print.println(n.getLoc().x + "," + n.getLoc().y + "," + n.getValue());
		printMax(nodes.get(maxIndex).getChildren());
	}

}
