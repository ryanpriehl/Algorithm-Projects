package pond_scum;

public class Matrix {

	private BigFraction[][] matrix;
	private int rows;
	private int cols;
	
	public Matrix(int r, int c){
		matrix = new BigFraction[r][c + 1];
		rows = r;
		cols = c;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(i == j) 
					matrix[i][j] = new BigFraction(4);
				else
					matrix[i][j] = new BigFraction(0);
			}
		}
	}
	
	public void print(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				matrix[i][j].print();
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void set(int r, int c, BigFraction val){
		matrix[r][c] = val;
	}
	
	public void getLeading1(int r){
		BigFraction leading = new BigFraction(matrix[r][r]);
		for(int j = 0; j < cols; j++){
			matrix[r][j] = matrix[r][j].multiplyReciprocal(leading);
			matrix[r][j].simplify();
		}
	}
	
	public void multiplyScalar(int r, BigFraction b){
		for(int j = 0; j < cols; j++){
			matrix[r][j] = matrix[r][j].multiply(b);
		}
	}
	
	public void eliminateCol(int safeRow){
		for(int i = 0; i < rows; i++){
			if(i != safeRow){
				if(!matrix[i][safeRow].equals(new BigFraction(0))){
					BigFraction scalar = new BigFraction(matrix[i][safeRow]);
					for(int j = 0; j < cols; j++){
						matrix[i][j] = matrix[i][j].multiply(matrix[safeRow][safeRow].negate()).add(matrix[safeRow][j].multiply(scalar));
					}
				}
			}
		}
	}
	
	public void addRows(int r1, int r2){
		for(int j = 0; j < cols; j++){
			matrix[r2][j] = matrix[r2][j].add(matrix[r1][j]);
			matrix[r2][j].simplify();
		}
	}
	
	public String[] getAnswers(){
		String[] answers = new String[cols];
		for(int i = 0; i < rows; i++){
			if(matrix[i][cols - 1].isNegative())
				matrix[i][cols - 1] = matrix[i][cols - 1].negate();
			answers[i] = matrix[i][cols - 1].toString();
		}
		return answers;
	}
}
