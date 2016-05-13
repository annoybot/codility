package codility;

/*
 * Score 100%
 * 
 * https://codility.com/programmers/task/max_counters/
 */

public class MaxCounters {
	public int[] solution(int N, int[] A) {
        int results[] = new int[N];
        int maxCounterValue=0;

        
        for(int i =0; i<N; i++)
        	results[i] =0;
        
        for(int i=0; i<A.length; i++) {
        	int x =A[i];
        	
        	if( 1<=x && x<=N ) {
        		++results[x-1];
        		
        		maxCounterValue = Math.max(maxCounterValue, results[x-1]);
        	}
        	else if( x == N+1) {
        	    if( maxCounterValue !=0 ) {
        	    
        		    for(int j =0; j<N; j++)
                	    results[j] = maxCounterValue;
        	    }
        	}
        	
        	printCounters(N, results);
        }
        
        
        return results;
    }

	private void printCounters(int N, int[] results) {
		System.err.print("{ ");
		for(int j =0; j<N; j++) {
			System.err.print(results[j]+ " ");
			
		}
		System.err.println("}");
	}
	
	public static void main(String args[])
	{
		MaxCounters instance = new MaxCounters();
		
		instance.solution(5, new int[] {3, 4, 4, 6, 1, 4, 4});
	}
 }
