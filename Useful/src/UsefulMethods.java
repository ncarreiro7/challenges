public class UsefulMethods {

	public static void main(String[] args) {

		int value = 3; // change me

		System.out.println("Fibonacci " + value + " element:" + fibonacciRecursive(value));
		System.out.println("Fibonacci " + value + " element:" + fibonacciIter(value));
		System.out.println("Is " + value + " prime? " + isPrime(value));
	}

	/**
	 * Fibonacci method done in recursive
	 * 
	 * @param n
	 *            the position which we want to know the value of the fibonacci
	 *            sequence
	 * @return the value of the n position in the fibonacci sequence
	 */
	public static int fibonacciRecursive(int n) {

		if (n == 0)
			return n;
		if (n == 1)
			return n;

		return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
	}

	/**
	 * Fibonacci method done in iterative
	 * 
	 * @param n
	 *            the position which we want to know the value of the fibonacci
	 *            sequence
	 * @return the value of the n position in the fibonacci sequence
	 */
	public static int fibonacciIter(int n) {

		int res=0;
		int last = 0;
		int next = 1;
		for (int i = 1; i < n; i++) {
				res = last + next;
				last = next;
				next = res;
		}
		
		return res;
	}
	
	/**
	 * Check if a number is prime in an efficient way
	 * @param n the number to see if it is prime or not
	 * @return true is n is prime false otherwise
	 */
	public static boolean isPrime(int n){
		
		if(n == 2 )
			return true;
		if(n%2 ==0)
			return false;
		
		for(int i = 3; i<n;i+=2){
			if(n%i == 0)
				return false;
		}
		return true;
		
	}
	
	
}
