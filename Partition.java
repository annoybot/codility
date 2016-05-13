package codility;

/*
 * TT question. Scored 20% IIRC
 */

public class Partition {
	
	public int solution(int X, int[] A) {

		if( A.length == 0)
			return 0;
		
		int sameBeforeCount =0;
		int differentAfterCount =0;
		int k=1;

		for(int i=0; i<A.length/2; i++,k++) {

			if(A[i] == X)
				++sameBeforeCount;

			if(A[A.length-i-1] != X)
				++differentAfterCount;

			if( sameBeforeCount == differentAfterCount) {
				if( A.length %2 ==0) {
					return k;
				}
				else {
					int middleElement = A[A.length/2+1];

					if(middleElement == X)
						k-=1;
					else
						k+=1;

				}

				return k;
			}
		}

		if( sameBeforeCount != 0 )
			return 0;
		else 
			return A.length;
	}
	
	public static void main(String args[]) {
		Partition instance = new Partition();
		
		/*int[] A = new int[] {2,2,2,2,2};
		int[] A = new int[] {5,5,5,5,5};
		int[] A = new int[] {5,5,1,2,3,5};
		int[] A = new int[] {5,5,1,7,2,3,5};
		 */
		int[] A = new int[] {};
		
		System.err.println(instance.solution(5, A));
		
	}
}
