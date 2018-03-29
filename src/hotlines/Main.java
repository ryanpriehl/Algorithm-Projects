package hotlines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		/*
		 * NOTE: Instance 4 takes a huge amount of memory, copy/paste
		 * -Xmx4096M into Run > Run Configurations > Arguments > VM Arguments
		 * to increase memory for heap.
		 */

		Scanner scan = new Scanner(new File("edges_daniel.txt"));
		int n = scan.nextInt() + 1;
		scan.nextLine();

		ArrayList<Integer> path = new ArrayList<Integer>();
		Queue<Node> queue = new LinkedList<Node>();

		scan.useDelimiter("\\s|,");

		int[][] adjMatrix = new int[n][n];
		int[][] adjMatrixMod = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				adjMatrix[i][j] = 0;
				adjMatrixMod[i][j] = 0;
			}
		}

		// filling adjacency matrix
		while (scan.hasNext()) {
			int start = Integer.valueOf(scan.next());
			int end = Integer.valueOf(scan.next());
			adjMatrix[start][end] = 1;
			adjMatrixMod[start][end] = 1;
		}

		PrintWriter writer = new PrintWriter("paths.txt");

		boolean finished = false;
		boolean pathFound = false;
		
		// Does BFS until no paths left
		while (!finished) {
			finished = true;
			
			queue.clear();
			Node start = new Node(0);
			queue.add(start);
			
			while (!queue.isEmpty() && !pathFound) {
				Node node = queue.remove();
				if (node.getValue() == (n - 1)) {
					for (int num : node.getPrevious()) {
						path.add(num);
					}
					path.add(node.getValue());
					pathFound = true;
					finished = false;
				}
				if (!pathFound) {
					for (int i = 0; i < n; i++) {
						if (adjMatrixMod[node.getValue()][i] == 1) {
							for (int j = 0; j < n; j++) {
								adjMatrixMod[j][i] = 0;
							}
							Node newNode = new Node(i);
							newNode.addPrevious(node.getPrevious());
							newNode.addPrevious(node.getValue());
							queue.add(newNode);
						}
					}
				}
			}

			if (pathFound) {
				for (int i = 0; i < path.size() - 1; i++) {
					System.out.print(path.get(i) + ",");
					writer.print(path.get(i) + ",");
					adjMatrix[path.get(i)][path.get(i + 1)] = 0;
				}
				System.out.println(path.get(path.size() - 1));
				writer.println(path.get(path.size() - 1));

				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						adjMatrixMod[i][j] = adjMatrix[i][j];
					}
				}
				pathFound = false;
				path.clear();
			}
		}

		writer.close();
	}

	public static void printMatrix(int[][] matrix, int n) {
		System.out.print("     ");
		for (int i = 0; i < n; i++) {
			System.out.print(i / 10 + " ");
		}
		System.out.println();

		System.out.print("     ");
		for (int i = 0; i < n; i++) {
			System.out.print(i % 10 + " ");
		}
		System.out.println();

		System.out.print("     ");
		for (int i = 0; i < n; i++) {
			System.out.print("- ");
		}
		System.out.println();

		for (int i = 0; i < n; i++) {
			if (i <= 9)
				System.out.print(i + ":   ");
			else if (i <= 99)
				System.out.print(i + ":  ");
			else
				System.out.print(i + ": ");
			for (int j = 0; j < n; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
