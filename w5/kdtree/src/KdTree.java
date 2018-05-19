import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * Week 5: 8 Kd-Tree
 * 
 */

public class KdTree {
	private Node root;
	
	private static class Node {
		private final Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private int size; // number of nodes in subtree
		private boolean splitVertical; // is this node split vertically or horizontally?
		
		public Node(Point2D p, int size, boolean splitVertical, RectHV rect) {
			this.p = p;
			this.size = size;
			this.splitVertical = splitVertical;
			this.rect = rect;
		}
	}
	
	/** construct empty set of points */
	public KdTree() {
	}
	
	/** check if set is empty */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/** number of points in the set */
	public int size() {
		return size(root);
	}
	
	/** return number of nodes rooted at x */
	private int size(Node node) {
		if (node == null) return 0;
		else return node.size;
	}
	
	/** add the point to the set (if not already in the set) */
	public void insert(final Point2D p) {
		if (p == null) throw new IllegalArgumentException("calls insert() with null argument");
		if (isEmpty()) root = insert(root, p, true, new RectHV(0, 0, 1, 1));
		else root = insert(root, p, true, root.rect);
	}

	private Node insert(final Node node, final Point2D p, final boolean splitVertical, final RectHV rect) {
		if (node == null) return new Node(p, 1, splitVertical, rect);
		if (p.equals(node.p)) return node;
		
		if (node.splitVertical) {
			if (p.x() < node.p.x()) {
				if (node.lb == null) node.lb = insert(node.lb, p, !node.splitVertical, new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax()));
				else node.lb = insert(node.lb, p, !node.splitVertical, node.lb.rect);
			}
			else {
				if (node.rt == null) node.rt = insert(node.rt, p, !node.splitVertical, new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax()));
				else node.rt = insert(node.rt, p, !node.splitVertical, node.rt.rect);
			}
		}
		else {
			if (p.y() < node.p.y()) {
				if (node.lb == null) node.lb = insert(node.lb, p, !node.splitVertical, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y()));
				else node.lb = insert(node.lb, p, !node.splitVertical, node.lb.rect);
			}
			else {
				if (node.rt == null) node.rt = insert(node.rt, p, !node.splitVertical, new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax()));
				else node.rt = insert(node.rt, p, !node.splitVertical, node.rt.rect);
			}
		}
		node.size = 1 + size(node.lb) + size(node.rt);
		return node;
	}
	
	/** does the set contain point p? */
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		return get(root, p) != null;
	}
	
	/** returns node associated with the given Point2D */
	private Node get(final Node node, final Point2D p) {
		if (node == null) return null;
		if (p.equals(node.p)) return node; 
		if (node.splitVertical && p.x() < node.p.x() || !node.splitVertical && p.y() < node.p.y()) {
			return get(node.lb, p);
		}
		else {
			return get(node.rt, p);
		}
	}
	
	/** draw all points to standard draw */
	public void draw() {
		draw(root);
	}
	
	private void draw(Node node) {
		if (node == null) return;
		if (node.lb != null) draw(node.lb);
		if (node.rt != null) draw(node.rt);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		node.p.draw();
		
		if (node.splitVertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
		}
	}
	
	/** all points that are inside the rectangle or on the boundary */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException("null argument");
		ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();
		range(root, rect, pointsInside);
		return pointsInside;
	}
	
	private void range(Node node, RectHV rect, ArrayList<Point2D> pointsInside) {
		if (node == null) return;
		if (rect.contains(node.p)) pointsInside.add(node.p); 
		if (rect.intersects(node.rect)) {
			if (node.lb != null && rect.intersects(node.lb.rect)) range(node.lb, rect, pointsInside);
			if (node.rt != null && rect.intersects(node.rt.rect)) range(node.rt, rect, pointsInside);
		}
	}
	
	/** a nearest neighbor in the set to point p; null if the set is empty */
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		if (isEmpty()) return null;
		return nearest(root, p, root.p);
	}
	
	private Point2D nearest(Node node, Point2D p, Point2D pMin) {
		Point2D min = pMin;
		if (node == null) return min;
		if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(min)) min = node.p;
		
		// choose subtree on same side of splitting line as the query point to search first
		if ((node.splitVertical && p.x() < node.p.x()) || (!node.splitVertical && p.y() < node.p.y())) {
			if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(min)) min = nearest(node.lb, p, min);
			if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(min)) min = nearest(node.rt, p, min);
		}
		else {
			if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(min)) min = nearest(node.rt, p, min);
			if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(min)) min = nearest(node.lb, p, min);
		}
		return min;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		In in = new In(args[0]);
		KdTree test = new KdTree();
		while (in.hasNextLine()) {
			if (in.isEmpty()) break;
			test.insert(new Point2D(in.readDouble(), in.readDouble()));
		}
		test.draw();
	}

}
