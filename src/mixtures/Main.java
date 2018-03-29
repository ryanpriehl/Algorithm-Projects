package mixtures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main 
{	
	
	private static File f = new File("solutions.txt");
	private static int rows = 1, cols = 0;
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner scan = new Scanner(f);
		scan.useDelimiter("\\s|:");
		
		String ratio = scan.nextLine();
		while(ratio.contains(":")){
			ratio = ratio.substring(ratio.indexOf(":") + 1);
			rows++;
		}
		while(scan.hasNextLine()){
			scan.nextLine();
			cols++;
		}
		
		//System.out.println("rows/ratio: " +rows);
		//System.out.println("mixtures: " +cols);
		cols++;
		//System.out.println("cols: " +cols);
		//System.out.println();
		
		int rowsOG = rows;
		int colsOG = cols;
		
		if(rows > cols - 1){
			rows = cols - 1;
		}
		else if(rows < cols - 1){
			cols = rows + 1;
		}

		Matrix matrix = fillMatrix(rows, cols);

		//matrix.print();
		
		for(int i = 0; i < rows; i++){
			matrix.getLeading1(i);
			matrix.eliminateCol(i);
		}
		
		matrix.makePositive();
		//matrix.print();
		
		BigFraction[] answers = new BigFraction[colsOG - 1];
		for(int i = 0; i < answers.length; i++){
			answers[i] = new BigFraction(0);
		}
		for(int i = 0; i < matrix.getAnswers().length; i++){
			answers[i] = matrix.getAnswers()[i];
		}

		boolean isPossible = checkAnswer(answers, rowsOG, colsOG);
		
		PrintWriter write = new PrintWriter("answer.txt");
		
		for(int i = 0; i < answers.length; i++){
			if(isPossible){
				System.out.println(answers[i].toStringNum());
				write.println(answers[i].toStringNum());
			}
			else{
				System.out.println(-1);
				write.println(-1);
			}
		}
		
		write.close();
		
	}
	
	public static Matrix fillMatrix(int rows, int cols) throws FileNotFoundException{
		Matrix matrix = new Matrix(rows, cols);
		Scanner scan = new Scanner(f);
		scan.useDelimiter(":|\\s");
		
		String ratio = scan.nextLine();
		
		for(int i = 0; i < rows; i++){
			if(ratio.contains(":"))
				matrix.set(i, cols - 1, new BigFraction(new BigInteger(ratio.substring(0, ratio.indexOf(":")))));
			else
				matrix.set(i, cols - 1, new BigFraction(new BigInteger(ratio)));
			ratio = ratio.substring(ratio.indexOf(":") + 1);
		}
		
		ratio = scan.nextLine();
		
		for(int i = 0; i < cols - 1; i++){
			for(int j = 0; j < rows; j++){
				if(ratio.contains(":"))
					matrix.set(j, i, new BigFraction(new BigInteger(ratio.substring(0, ratio.indexOf(":")))));
				else{
					matrix.set(j, i, new BigFraction(new BigInteger(ratio)));
				}
				ratio = ratio.substring(ratio.indexOf(":") + 1);
			}
			if(scan.hasNextLine()){
				ratio = scan.nextLine();
			}
		}
		
		return matrix;
	}
	
	public static boolean checkAnswer(BigFraction[] answers, int rows, int cols) throws FileNotFoundException{
		Matrix matrix = fillMatrix(rows, cols);
		
		BigFraction[] amounts = new BigFraction[rows];
		
		for(int i = 0; i < rows; i++){
			amounts[i] = new BigFraction(0);
		}
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols - 1; j++){
				amounts[i] = amounts[i].add(answers[j].multiply(matrix.get(i, j)));
			}
		}
		
		for(int i = 0; i < amounts.length; i++){
			if(!amounts[i].equals(matrix.get(i, cols - 1))){
				return false;
			}
		}
		
		return true;
	}
}
