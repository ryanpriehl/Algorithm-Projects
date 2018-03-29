package party;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		// getting k1, k2 and N
		File f = new File("description.txt");
		Scanner scan = new Scanner(f);

		int k1 = scan.nextInt();
		int k2 = scan.nextInt();
		scan.nextLine();
		int candidates = 0;
		while(scan.hasNextLine()){
			scan.nextLine();
			candidates++;
		}
		
		//System.out.println("k1: " +k1);
		//System.out.println("k2: " +k2);
		//System.out.println("candidates: " +candidates);
		
		int[][] matrix = new int[candidates][candidates];
		
		// reading in the adjacency matrix
		Scanner scan2 = new Scanner(f);
		scan2.nextLine();
		scan2.nextLine();
		for(int i = 0; i < candidates; i++){
			char[] row = scan2.nextLine().toCharArray();
			for(int j = 0; j < candidates; j++){
				matrix[i][j] = row[j] - 48;
				//System.out.print(matrix[i][j] +" ");
			}
			//System.out.println();
		}
		//System.out.println();
		
		int zeros = -1;
		int ones = 0;
		boolean cleared = false;
		boolean finished = false;
		
		// removing people (replacing with 2's)
		while(!finished){
			finished = true;
			for(int i = 0; i < candidates; i++){
				if(matrix[i][i] == 2){
					cleared = true;
				}
				for(int j = 0; j < candidates; j++){
					if(cleared){
						j = candidates;
					}
					else if(matrix[i][j] == 0)
						zeros++;
					else if(matrix[i][j] == 1)
						ones++;
				}
				if((zeros < k2 || ones < k1) && !cleared){
					for(int k = 0; k < candidates; k++){
						matrix[i][k] = 2;
						matrix[k][i] = 2;
					}
					finished = false;
				}
				zeros = -1;
				ones = 0;
				cleared = false;
			}
		}
		
		PrintWriter write = new PrintWriter("party.txt");
		
		// printing the answer
		for(int i = 0; i < candidates; i++){
			for(int j = 0; j < candidates; j++){
				if(matrix[i][j] != 2){
					System.out.println(i + 1);
					write.println(i + 1);
					j = candidates;
				}
			}
		}
		
		write.close();
	}
}
