/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * Week 4: 8 Puzzle
 * 
 */
import edu.princeton.cs.algs4.MinPQ;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int moves;
		private int priority;
		private SearchNode predecessor;
		
		public SearchNode(Board board, int moves, SearchNode predecessor) {
			this.board = board;
			this.moves = moves;
			priority = moves + board.manhattan();
			this.predecessor = predecessor;
		}
		
		public int compareTo(SearchNode that) {
			return (this.priority - that.priority);
		}
	}
	
	// private MinPQ<SearchNode> pq;
	// private MinPQ<SearchNode> pqTwin;
	// private Board start;
	private SearchNode lastNode;
	
	/** find a solution to initial board using A* algorithm */
	public Solver(Board initial) {
		if (initial == null) throw new IllegalArgumentException("null input to Solver constructor");
		
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
		MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
		SearchNode min;
		SearchNode minTwin;
		
		pq.insert(new SearchNode(initial, 0, null));
		pqTwin.insert(new SearchNode(initial.twin(), 0, null));
		while (!pq.min().board.isGoal() && !pqTwin.min().board.isGoal()) {
			min = pq.min();
			minTwin = pqTwin.min();
			pq.delMin();
			pqTwin.delMin();
			
			for (Board neighbor : min.board.neighbors()) {
				if (min.moves == 0) {
					pq.insert(new SearchNode(neighbor, min.moves+1, min));
				}
				else if(!neighbor.equals(min.predecessor.board)) {
					pq.insert(new SearchNode(neighbor, min.moves+1, min));
				}
			}
			
			for (Board neighbor : minTwin.board.neighbors()) {
				if (min.moves == 0) {
					pqTwin.insert(new SearchNode(neighbor, minTwin.moves+1, minTwin));
				}
				else if(!neighbor.equals(minTwin.predecessor.board)) {
					pqTwin.insert(new SearchNode(neighbor, minTwin.moves+1, minTwin));
				}
			}
		}
		
		if (pq.min().board.isGoal()) {
			lastNode = pq.min();
		}
		else {
			lastNode = null;
		}
	}
	
	/** is the initial board solvable? */
	public boolean isSolvable() {
		if (lastNode == null) return false;
		//if (pqTwin.min().board.isGoal()) return false;
		return true;
	}
	
	/** min number of moves to solve initial board; -1 if unsolvable */
	public int moves() {
		if (!isSolvable()) return -1;
		return lastNode.moves;
	}
	
	/** sequence of boards in a shortest solution; null if unsolvable */
	public Iterable<Board> solution() {
		if(!isSolvable()) return null;
		Stack<Board> solution = new Stack<Board>();
		
		SearchNode cur = lastNode;
		while (cur != null) {
			solution.push(cur.board);
			cur = cur.predecessor;
		}
		return solution;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				blocks[i][j] = in.readInt();
			}
		}
		Board initial = new Board(blocks);
		
		Solver solver = new Solver(initial);
		
		if (!solver.isSolvable()) StdOut.println("No solution possible");
		else {
			StdOut.println("Min. # of moves = " + solver.moves());
			for (Board board : solver.solution()) {
				StdOut.println(board);
			};
		}
	}

}
