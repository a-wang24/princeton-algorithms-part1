/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * Week 5: 8 Kd-Tree
 * 
 */

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private SET<Point2D> points;
	
	/** construct empty set of points */
	public PointSET() {
		points = new SET<Point2D>();
	}
	
	/** check if set is empty */
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	/** number of points in the set */
	public int size() {
		return points.size();
	}
	
	/** add the point to the set (if not already in the set) */
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		points.add(p);
	}
	
	/** does the set contain point p? */
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		return points.contains(p);
	}
	
	/** draw all points to standard draw */
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		for (Point2D p : points) {
			p.draw();
		}
	}
	
	/** all points that are inside the rectangle or on the boundary */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException("null argument");
		ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();
		for (Point2D p : points) {
			if (rect.contains(p)) pointsInside.add(p);
		}
		return pointsInside;
	}
	
	/** a nearest neighbor in the set to point p; null if the set is empty */
	public Point2D nearest(Point2D pIn) {
		if (pIn == null) throw new IllegalArgumentException("null argument");
		if (points.isEmpty()) return null;
		double shortestDist = Double.POSITIVE_INFINITY;
		Point2D returnVal = new Point2D(-1.0, -1.0);
		for (Point2D p : points) {
			double dist = pIn.distanceSquaredTo(p);
			if (dist < shortestDist) {
				shortestDist = dist;
				returnVal = p;
			}
		}
		return returnVal;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		In in = new In(args[0]);
		PointSET test = new PointSET();
		while (in.hasNextLine()) {
			if (in.isEmpty()) break;
			test.insert(new Point2D(in.readDouble(), in.readDouble()));
		}
		test.draw();
	}

}
