import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * collinear - fast solution for finding collinear points from a set of points
 * 
 */

public class FastCollinearPoints {
	//* Array of found line segments *//
	private ArrayList<LineSegment> segments;
	private HashMap<Double, ArrayList<Point>> checkRepeat;
/*	private ArrayList<Double> slopes;
	private ArrayList<Point> endPoints;
	private ArrayList<Point> startPoints;*/
	
	//* Constructor *//
	public FastCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Null array");
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Point in array is null");
		}
		checkRepeatPoints(points);
		
		Arrays.sort(points);
		Point[] pointsCopy = Arrays.copyOf(points, points.length);
		checkRepeat = new HashMap<Double, ArrayList<Point>>();
/*		endPoints = new ArrayList<Point>();
		startPoints = new ArrayList<Point>();
		slopes = new ArrayList<Double>();*/
		segments = new ArrayList<LineSegment>();
		//ArrayList<LineSegment> foundSegments = new ArrayList<>();
		
		for (Point p : points) {
			Arrays.sort(pointsCopy, p.slopeOrder());
			double slope = 0;
			double previousSlope = Double.NEGATIVE_INFINITY;
			//int count = 0;
			ArrayList<Point> sameSlopePoints = new ArrayList<>();
			
			// Start at i = 1 bc first entry will be p itself
			for (int i = 1; i < pointsCopy.length; i++) {
				slope = p.slopeTo(pointsCopy[i]);
				if (slope == previousSlope) {
					sameSlopePoints.add(pointsCopy[i]);
				} else {
					if (sameSlopePoints.size() >= 3) {
						//foundSegments.add(new LineSegment(p, pointsCopy[i-1]));
						sameSlopePoints.add(p);
						Point [] sspArray = sameSlopePoints.toArray(new Point[sameSlopePoints.size()]);
						Arrays.sort(sspArray);
						addSegmentIfNew(previousSlope, sspArray[0], sspArray[sspArray.length-1]);
					}
					sameSlopePoints.clear();
					sameSlopePoints.add(pointsCopy[i]);
				}
				previousSlope = slope;
			}
			
			if (sameSlopePoints.size() >= 3) {
				//foundSegments.add(new LineSegment(p, pointsCopy[pointsCopy.length-1]));
				sameSlopePoints.add(p);
				Point [] sspArray = sameSlopePoints.toArray(new Point[sameSlopePoints.size()]);
				Arrays.sort(sspArray);
				addSegmentIfNew(previousSlope, sspArray[0], sspArray[sspArray.length-1]);
			}
			
		}
		
		//segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
	}
	
	//* returns number of line segments *//
	public int numberOfSegments() {
		return segments.size();
	}
	
	//* returns array of found line segments *//
	public LineSegment[] segments() {
		return segments.toArray(new LineSegment[segments.size()]);
	}
	
	//* adds segment to list if not already in list *//
	private void addSegmentIfNew(double slope, Point startPoint, Point endPoint) {
		ArrayList<Point> endPoints = checkRepeat.get(slope);
		
		if (endPoints == null) {
			endPoints = new ArrayList<>();
			endPoints.add(endPoint);
			checkRepeat.put(slope, endPoints);
			segments.add(new LineSegment(startPoint, endPoint));
		} else {
			for (Point tempP : endPoints) {
				if (tempP.compareTo(endPoint) == 0) return;
			}
			endPoints.add(endPoint);
			segments.add(new LineSegment(startPoint, endPoint));
		}
	}
	
	//* add start point, end point, and slope of found line segment to respective array lists *//
/*	private void addComponents(double slope, Point startPoint, Point endPoint) {
		if (!slopes.contains(slope)) slopes.add(slope);
		if (!endPoints.contains(endPoint)) endPoints.add(endPoint);
		if (!startPoints.contains(startPoint)) startPoints.add(startPoint);
	}*/
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	    	StdOut.println(segment);
	    	segment.draw();
	    }
	    StdDraw.show();
	}
}
