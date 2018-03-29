package pond_scum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main 
{	
	public static void main(String[] args) throws FileNotFoundException 
	{
		int variablePonds = 0;
		int asciiCounter = 65;
		
		File f = new File("ponds.txt");
		
		// counting the number of rows/columns in the text file
		Scanner scan2 = new Scanner(f);
		int rows = 0;
		int cols = 1;
		
		for(char c : scan2.nextLine().toCharArray()){
			if(c == ',') 
				cols++;
		}
		rows++;
		
		while(scan2.hasNextLine()){
			scan2.nextLine();
			rows++;
		}
		
		String[][] ponds = new String[rows][cols];

		// copy the text file into a 2D array
		Scanner scan = new Scanner(f);
		scan.useDelimiter(",|\\s+");
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(scan.hasNext()){
					ponds[i][j] = scan.next();
				}
			}
		}
		
		// prints copied text file
		/*for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				System.out.print(ponds[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println();*/
		
		// replaces variable pools with ascii characters
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(ponds[i][j].charAt(0) == '!'){
					/*
					 * Causes problems if run out of available ascii codes
					 */
					ponds[i][j] = "!" +String.valueOf((char) asciiCounter);
					asciiCounter++;
					variablePonds++;
				}
			}
		}
		
		// prints with variable ponds replaced
		/*for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				System.out.print(ponds[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println();*/
		
		Matrix matrix = new Matrix(variablePonds, variablePonds + 1);
		
		int sum = 0;
		int rowCounter = 0;
		
		// fills the matrix that'll actually be solved
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(ponds[i][j].charAt(0) == '!'){
					if(ponds[i - 1][j].charAt(0) != '!') 
						sum += Integer.valueOf(ponds[i - 1][j]);
					else
						matrix.set(rowCounter, (int) ponds[i - 1][j].charAt(1) - 65, new BigFraction(-1));
					if(ponds[i][j + 1].charAt(0) != '!') 
						sum += Integer.valueOf(ponds[i][j + 1]);
					else
						matrix.set(rowCounter, (int) ponds[i][j + 1].charAt(1) - 65, new BigFraction(-1));
					if(ponds[i + 1][j].charAt(0) != '!') 
						sum += Integer.valueOf(ponds[i + 1][j]);
					else
						matrix.set(rowCounter, (int) ponds[i + 1][j].charAt(1) - 65, new BigFraction(-1));
					if(ponds[i][j - 1].charAt(0) != '!') 
						sum += Integer.valueOf(ponds[i][j - 1]);
					else
						matrix.set(rowCounter, (int) ponds[i][j - 1].charAt(1) - 65, new BigFraction(-1));
					matrix.set(rowCounter, variablePonds, new BigFraction(sum));
					rowCounter++;
				}
				sum = 0;
			}
		}
		
		// matrix.print();
		
		// actually solves the matrix
		for(int i = 0; i < variablePonds; i++){
			matrix.getLeading1(i);
			matrix.eliminateCol(i);
		}
		
		String[] answers = matrix.getAnswers();
		
		PrintWriter write = new PrintWriter("heights.txt");
		
		// extracts answers from matrix and prints in correct format
		int counter = 0;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(j != 0){
					System.out.print(",");
					write.print(",");
				}
				if(ponds[i][j].charAt(0) == '!'){
					ponds[i][j] = answers[counter];
					counter++;
				}
				System.out.print(ponds[i][j]);
				write.print(ponds[i][j]);
			}
			System.out.println();
			write.println();
		}
		
		write.close();
	}
}
