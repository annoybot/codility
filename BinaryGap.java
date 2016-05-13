package codility;

/*
 * Score 100%
 * 
 * https://codility.com/programmers/task/binary_gap/
 */

public class BinaryGap {
	public int solution(int N) {
		int n=N;
		int zeroCount =0;
		int longestRun=0;
		boolean encounteredOne = false;
		
        do {
        	int bit = n %2;
        	
        	if( bit == 0 ){
        		zeroCount++;
        	}
        	else  {
        		if( encounteredOne ) {
        			longestRun = Math.max(longestRun, zeroCount);
        		}
        		
        		encounteredOne =true;
        		zeroCount = 0;
        	}
        	
        	n = n / 2;
        } while( n !=0);
       
        return longestRun;
    }
	
	public static void main(String args[]) {
		BinaryGap instance = new BinaryGap();
		System.err.println( instance.solution(9) );
		System.err.println( instance.solution(529) );
		System.err.println( instance.solution(20) );
		System.err.println( instance.solution(15) );
		System.err.println( instance.solution(1041) );
		System.err.println( instance.solution(2147483647));
	}
}
