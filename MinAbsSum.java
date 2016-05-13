package codility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Score 72%
 * 
 * https://codility.com/programmers/task/min_abs_sum/

 */
public class MinAbsSum {

	public int solution(int[] A) {

		int N = A.length;

		if (N == 0)
			return 0;

		int s = 0;
		int result;

		/* Convert all input numbers to abs value and also 
		sum up the whole array.*/
		for (int i = 0; i < N; i++) {
			A[i] = Math.abs(A[i]);
			s += A[i];
		}

		/*Create an array to track which sums can be created 
		 * by combinations of the elements in the input array A.
		 * Elements from A can only be used once.
		 * 
		 * For example, if A = { 1, 5 }, sum =6
		 * 
		 * int[] possibleSums will be { true, true, 0, 0, 0, true, true}
		 * 
		 * indicating that a sum of 0, 1, 5 and 6 can be 
		 * made by combining zero to 2 of the elements from A.
		 * 
		 * possibleSums[0] is always one since we can always 
		 * make the sum of zero by choosing none of the elements from A.
		 */
		boolean[] possibleSums = new boolean[s + 1];

		// Initialize to zero and set possibleSums[0] = true;
		possibleSums[0] = true;
		for (int i = 1; i < possibleSums.length; i++) {
			possibleSums[i] = false;
		}

		/* For each element in A, first scan possibleSums
		 * for any flags that are already set, and for each one
		 * set possibleSums[j+num] to true (if the index is in range).
		 * For example if possbileSums[1] = true and num is 5, the 
		 * possibleSum[1+5] is also true, since we can form a sum of 6.
		 * 
		 */
		for (int i = 0; i < N; i++) {
			int num = A[i];

			//Order of initialization is important.
			for(int j=possibleSums.length-1; j>0; j--) {
				if (possibleSums[j]  && j + num < s + 1)
					possibleSums[j + num] = true;
			}
			
			//Order is important. This must not be moved above the j-loop.
			possibleSums[num]=true;
		}
		
		result = s;
		
		// p is the largest sum that we can make that is < s/2
		for(int p=0; p*2 <= s; p++) {
			if( possibleSums[p] ) {
				int q = s -p;
				result = Math.min(result,  q-p);
			}
		}
		

		return Math.abs(result);
	}

	class TestCase {
		int expectedResult;
		int[] input;
		
		public TestCase(int expectedResult, int[] input) {
			super();
			this.expectedResult = expectedResult;
			this.input = input;
		}
		
		
	}
	
	public static void main(String args[]) {
		MinAbsSum instance = new MinAbsSum();

		List<TestCase> testCases = new ArrayList<TestCase>();
		
		testCases.add(instance.new TestCase(4,new int[] { 1, 5}));
		testCases.add(instance.new TestCase(0,new int[] { 1, 5, 2, -2 }));
		testCases.add(instance.new TestCase(1,new int[] { 2 ,2, 1 }));
		testCases.add(instance.new TestCase(91,  new int[] {-100,3,2,4}));
	

		for(TestCase testCase : testCases) {
			int result = instance.solution(testCase.input);
			if(testCase.expectedResult == result)
				System.out.println("pass: "+Arrays.toString(testCase.input)+"->"+result);
			else
				System.out.println("fail: "+Arrays.toString(testCase.input)+"->"+result+"\texpected "+testCase.expectedResult);
		}
	}
	
	
}
