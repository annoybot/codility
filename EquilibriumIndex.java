package codility;

/*
 * Score 100%
 * https://codility.com/demo/results/demoW3GR7Y-TPJ/
 */

class EquilibriumIndex {
    public int solution(int[] A) {
    	if(A.length == 0)
    		return -1;
    	else if(A.length ==1)
    		return 0;
    	
        long sum=0;
        long bsum[] = new long[A.length];
        
        for(int i=0; i<A.length; i++) {
        	bsum[i] = sum;
        	sum +=A[i];
        }
        
        for(int i=0; i<A.length; i++) {
        	long fsum;
        	
        	if( i<A.length-1) {
        		fsum = sum-bsum[i]-A[i];
        	}
        	else {
        		fsum = 0;
        	}
        	
        	if( fsum == bsum[i] )
        		return i;
        }
        
        return -1;
    }
   
	public static void main(String args[]) {
		EquilibriumIndex instance = new EquilibriumIndex();
		int A[] = {-2147483648};
		//int A[] =  {2, -1, -2, 1, 500};
		//int A[] =  {500, 2, -1, -2, 1};
		//int A[] = new int[] { -1, 3, -4, 5, 1, -6, 2, 1};
		
		System.err.println(instance.solution(A));
		
	}
}
