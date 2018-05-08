/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * collinear - bruteforce solution for finding collinear points from a set of points
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	//* array of found line segments *//
	private LineSegment[] segments;
	
	//* constructor *//
	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Null array");
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Point in array is null");
		}
		checkRepeatPoints(points);
		 
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
		Arrays.sort(pointsCopy);
		ArrayList<LineSegment> foundSegments = new ArrayList<>();
		
		for (int i = 0; i < pointsCopy.length-3; i++) {
			for (int j = i+1; j < pointsCopy.length-2; j++) {
				for (int k = j+1; k < pointsCopy.length-1; k++) {
					for (int l = k+1; l < pointsCopy.length; l++) {
						if ((pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k])) &&
								(pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l]))) {
							foundSegments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
						}
					}
				}
			}
		}
		
		segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
	}
	
	//* returns number of line segments found *//
	public int numberOfSegments() {
		return segments.length;
	}
	
	//* returns an array of the found line segments *//
	public LineSegment[] segments() {
		return Arrays.copyOf(segments, segments.length);
	}
	
	//* helper function to check if there are repeated points in the constructor argument *//
	private void checkRepeatPoints(Point[] points) {
		for (int i=0; i < points.length; i++) {
			for (int j=i+1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("Repeated points");
			}
		}
	}
	
	public static void main(String[] args) {
		// read points in from file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x,y);
		}
		
		// draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();
	    
	    // print and draw line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	    	StdOut.println(segment);
	    	segment.draw();
	    }
	    StdDraw.show();
	}
}
