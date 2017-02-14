
package edu.kettering.cs102.notes;


public class test {
	
	public test () {
		// dummy constructor
	}
	
	public static int euclid (int a, int b) {
		if (a % b == 0)
			return b;
		else
			return euclid(b, a % b);
	}
	
	public static void main(String[] args) {
		int num1 = 100;
		int num2 = 50;
		int gcd = euclid(num1, num2);
		System.out.print("GCD of " + num1 + " and " + num2 + " is: " + gcd);
	}
}