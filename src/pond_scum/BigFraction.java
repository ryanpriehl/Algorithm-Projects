package pond_scum;

import java.math.BigInteger;

public class BigFraction {

	private BigInteger numer;
	private BigInteger denom;
	
	public BigFraction(int n){
		numer = new BigInteger(String.valueOf(n));
		denom = new BigInteger("1");
	}

	public BigFraction(int n , int d){
		numer = new BigInteger(String.valueOf(n));
		denom = new BigInteger(String.valueOf(d));
	}
	
	public BigFraction(BigInteger n, BigInteger d){
		numer = n;
		denom = d;
	}
	
	public BigFraction(BigFraction b){
		numer = b.numer;
		denom = b.denom;
	}
	
	public void print(){
		if(denom.equals(new BigInteger("0")))
			System.out.print("ERROR");
		else if(numer.equals(new BigInteger("0")) || denom.equals(new BigInteger("1")))
			System.out.print(numer);
		else
			System.out.print(numer + "/" +denom);
	}
	
	public void simplify(){
		BigInteger gcd = numer.gcd(denom);
		if(!gcd.equals(new BigInteger("0"))){
			numer = numer.divide(gcd);
			denom = denom.divide(gcd);
		}
		dealWithNegatives();
	}
	
	public BigFraction multiply(BigFraction b){
		BigFraction result = new BigFraction(numer.multiply(b.numer), denom.multiply(b.denom));
		result.simplify();
		return result;
	}
	
	public BigFraction multiplyReciprocal(BigFraction b){
		BigFraction result = new BigFraction(numer.multiply(b.denom), denom.multiply(b.numer));
		result.simplify();
		return result;
	}
	
	public BigFraction add(BigFraction b){
		BigFraction result = new BigFraction((numer.multiply(b.denom)).add(b.numer.multiply(denom)), denom = denom.multiply(b.denom));
		result.simplify();
		return result;
	}
	
	public void dealWithNegatives(){
		if(numer.compareTo(new BigInteger("0")) < 0 && denom.compareTo(new BigInteger("0")) < 0){
			numer = numer.negate();
			denom = denom.negate();
		}
		else if(denom.compareTo(new BigInteger("0")) < 0){
			numer = numer.negate();
			denom = denom.negate();
		}
	}
	
	public boolean isNegative(){
		return numer.compareTo(new BigInteger("0")) < 0;
	}
	
	public BigFraction negate(){
		return new BigFraction(numer.negate(), denom);
	}
	
	public String toString(){
		if(numer.equals(new BigInteger("0")) || denom.equals(new BigInteger("1")))
			return numer.toString();
		else
			return numer.toString() +"/" +denom.toString();
	}
}
