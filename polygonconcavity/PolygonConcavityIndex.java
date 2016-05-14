package codility.polygonconcavity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Score 100%
 * 
 * https://codility.com/programmers/task/polygon_concavity_index/
 * 
 * https://codility.com/demo/results/trainingNNB423-PS3/
 */
public class PolygonConcavityIndex {

	public int solution(Point2D[] A) {
		if(A.length >=0 && A.length <=2)
			return -1;

		/*  Strictly speaking keeping two separate counters for this info is redundant, 
		 *  but it makes the logic so much easier to understand
		 */
		int plusOneCount =0;
		int minusOneCount =0;
		int orientations[] = new int[A.length];

		/*
		 * Take every three consecutive points from the polygon: a,b and c.
		 * 
		 * These form a triangle.
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
		 * The polygon is convex <=> if each triangle formed this way
		 * retains the same orientation for each initial point 'a'.
		 * 
		 * The algorithm works as follows:
		 * 
		 * We iterate through all the points in the polygon and  keep track 
		 * of the orientations of the triangles described above 
		 * in an array and also keep a count of clockwise orientations and 
		 * counter clockwise orientations. 
		 * 
		 * At the end of the loop if all the nonzero orientations 
		 * are of the same sign, then the figure is convex and we return -1
		 * 
		 * Otherwise we use the info collected to find the midpoint 
		 * of the first line segment that has an orientation opposite to the rest.
		 * 
		 * Note: that co-linear points will give an orientation of zero.
		 */
		for(int i=0; i<A.length; i++) {
			Point2D a = A[i];
			Point2D b = A[(i+1) % A.length];
			Point2D c = A[(i+2) % A.length];

			int orientation = triangleOrientation(a,b,c);
			orientations[i] = orientation;

			if( orientation > 0) plusOneCount++;

			if( orientation < 0) minusOneCount++;
		}

		//Convex figures will have the same sign for all nozero orientations in the array
		if( plusOneCount >0 && minusOneCount ==0 || minusOneCount >0 && plusOneCount ==0 ) {
			return -1;
		}
		else {
			/* FIXME: The requirement is that we return a point that is not on the convex hull.
			 * 
			 * I don't think this code will satisfy this requirement.
			 * 
			 * For example if you had   a figure where there was a greater density of points in 
			 * the 'concave' portion of the polygon then this code would end up returning a point 
			 * on the convex part of the polygon, which is incorrect.
			 * 
			 * Fix this later.
			 * 
			 */
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


	public static void main(String args[]) {
		PolygonConcavityIndex instance = new PolygonConcavityIndex();

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
