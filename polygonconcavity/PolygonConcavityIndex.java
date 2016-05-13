package codility.polygonconcavity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Score 100%
 * 
 * https://codility.com/programmers/task/polygon_concavity_index/
 */
public class PolygonConcavityIndex {

	public int solution(Point2D[] A) {
		if(A.length >=0 && A.length <=2)
			return -1;

		/*  Strictly speaking keeping two counters for this info is redundant, 
		 *  but it makes the logic so much easier to understand
		 */
		int plusOneCount =0;
		int minusOneCount =0;
		int orientations[] = new int[A.length];

		/*
		 * Take every point, a and the next two points
		 * b, and c following a. These form a triangle.
		 * 
		 * Use the formula in  triangleOrientation
		 * to see if the orientation of the 
		 * triangle is 'clockwise' or 'counterclockwise'.
		 * 
		 * For example:
		 * 
		 * This triangle is clockwise.
		 *  
		 * b--c
		 * | /
		 * |/
		 * a
		 * 
		 * and this one is counterclockwise.
		 * 
		 * c--b
		 * \  |
		 *   \|
		 *    a
		 * 
		 * The figure is convex <=> if each triangle formed this way
		 * retains the same orientation for each a.
		 * 
		 * The algorithm works as follows:
		 * 
		 * We iterate through all the points in the figure and  keep track 
		 * of the orientations of the triangles described above 
		 * in an array and also keep a count of clockwise orientations and 
		 * counter clockwise orientations. 
		 * 
		 * At the end of the loop if all the nonzero orientations 
		 * are of the same sign, then the figure is convex and we return -1
		 * 
		 * Otherwise we use the info collected to find the midpoint 
		 * of the first line segment that has the opposite orientation.
		 * 
		 * Note: that co-linear points will give an orientation of zero.
		 */
		for(int i=0; i<A.length; i++) {
			int indexOfA = i;
			int indexOfB = (i+1) % A.length;
			int indexOfC = (i+2) % A.length;

			Point2D a = A[indexOfA];
			Point2D b = A[indexOfB];
			Point2D c = A[indexOfC];

			int orientation = triangleOrientation(a,b,c);
			orientations[i] = orientation;

			if( orientation > 0) plusOneCount++;

			if( orientation < 0) minusOneCount++;
		}

		//System.out.println(Arrays.toString(orientations));

		//Convex figures will have the same sign for all nozero orientations in the array
		if( plusOneCount >0 && minusOneCount ==0 || minusOneCount >0 && plusOneCount ==0 ) {
			return -1;
		}
		else {
			int lookingFor = plusOneCount > minusOneCount ? -1 : 1;
			for(int j=0; j<orientations.length; j++)
				if(orientations[j]==lookingFor)
					return (j+1) % A.length;
		}

		//Can't happen.
		return 99;

	}

	/*
	See: https://www-sop.inria.fr/prisme/fiches/Arithmetique/index.html.en
	 */
	public int triangleOrientation(Point2D p, Point2D q, Point2D r)
	{
		long Xp = p.x; long Xq = q.x; long  Xr = r.x;
		long Yp = p.y; long Yq = q.y;  long Yr=  r.y; 

		long d= (Xq-Xp) * (Yr-Yp) - (Xr-Xp) * (Yq-Yp);

		if( d ==0 )
			return 0;
		else
			return d<0 ? -1 : 1;
	}

	/**
	 * Return the determinant, of the two provided points
	 * normalized to 1 or -1.
	 * 
	 * | 1 a.x  a.y |
	 * | 1 b.x  b.y |
	 * | 1 c.x  c.y |
	 * 
	 * Basically we are just preserving the sign of the determinant.
	 *
	 * This formula gives the orientation of the triangle formed
	 * by the points a,b,c.  If traversing from a to b to c takes you 
	 * clockwise around the plane, then you get a +1, if it takes
	 * you counterclockwise, you get a -1.
	 * 
	 * See: http://mathoverflow.net/a/44098
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	/*
	@Deprecated
	public int triangleOrientation(Point2D a, Point2D b, Point2D c)
	{
		long d = ((long)b.x*c.y - (long)b.y*c.x) - 
				  (long)a.x*((long)c.y-(long)b.y) + 
				  (long)a.y*((long)c.x-(long)b.x);

		if( d ==0 )
			return 0;
		else
			return d<0 ? -1 : 1;
	}
	 */

	public static void main(String args[]) {
		PolygonConcavityIndex instance = new PolygonConcavityIndex();

		//System.err.println(instance.triangleOrientation(new Point2D(1,1), new Point2D(2,2), new Point2D(20,20)));

		List<TestCase> testCases = new ArrayList<TestCase>();

		//Triangle
		testCases.add(new TestCase(-1,new Point2D[] { new Point2D(0,0), new Point2D(1,0), 
				new Point2D(0,1)}));

		//Same Triangle, rotating the other way
		testCases.add(new TestCase(-1,new Point2D[] { new Point2D(0,0), new Point2D(0,1), 
				new Point2D(1,0)}));
		//Square
		testCases.add(new TestCase(-1,new Point2D[] { new Point2D(0,0), new Point2D(0,1), 
				new Point2D(1,1), new Point2D(1,0)}));
		//Codility example from problem statement, not convex, index 2.
		testCases.add(new TestCase(-1,new Point2D[] { new Point2D(-1,3), new Point2D(3,1), 
				new Point2D(0,-1), new Point2D(-2,1)}));
		// 'M' figure, square with an indent in it at (5,5,). Not Convex.
		testCases.add(new TestCase(2,new Point2D[] { new Point2D(0,0), new Point2D(10,0), 
				new Point2D(5,5), new Point2D(10,10), 
				new Point2D(0,10)}));
		// Boomerang
		testCases.add(new TestCase(3,new Point2D[] { new Point2D(0,0), new Point2D(1,5), 
				new Point2D(5,5), new Point2D(2,4)}));

		// Triangle with colinear segments
		testCases.add(new TestCase(-1,new Point2D[] { new Point2D(0,0), new Point2D(1,1), 
				new Point2D(2,2), new Point2D(3,1),
				new Point2D(4,0), new Point2D(2,0)}));

		// Boomerang with colinear segments
		testCases.add(new TestCase(2,new Point2D[] { new Point2D(0,0), new Point2D(2,2), 
				new Point2D(4,4), new Point2D(5,6),
				new Point2D(6,8), new Point2D(6,0)}));

		for(TestCase testCase : testCases) {
			int result = instance.solution(testCase.input);
			if(testCase.expectedResult == result)
				System.out.println("pass: "+Arrays.toString(testCase.input)+"->"+result);
			else
				System.out.println("fail: "+Arrays.toString(testCase.input)+"->"+result+"\texpected "+testCase.expectedResult);
		}

	}
}
