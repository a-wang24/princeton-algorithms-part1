/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * Week 4: 8 Puzzle
 * 
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class Board {
	private int n;
	private int[] tiles;
	private final static int BLANK = 0;
	
	/** construct board from nxn array of blocks. Assume blocks is nxn containing integers between 0 and n^2-1 */
	public Board(int[][] blocks) {
		n = blocks.length;
		tiles = new int[n*n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tiles[to1D(i,j)] = blocks[i][j];
			}
		}
	}
	
	/** board dimension n */
	public int dimension() {
		return n;
	}
	
	/** hamming priority = number of blocks out of place */
	public int hamming() {
		int ham = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (notInPlace(i, j)) {
					ham++;
				}
			}
		}
		
		return ham;
	}
	
	/** test if tile is not in the right place */
	private boolean notInPlace(int row, int col) {
		int tile = tiles[to1D(row, col)];
		return !isBlank(row, col) && tile != goalFor(row, col); 
	}
	
	/** returns correct tile for certain row and col */
	private int goalFor(int row, int col) {
		//if ( row == n-1 && col == n-1) return 0;
		return n*row + col + 1;
	}
	
	/** test if tile is blank */
	private boolean isBlank(int row, int col) {
		return tiles[to1D(row, col)] == BLANK;
	}
	
	/** change index from 2D to 1D */
	private int to1D(int row, int col) {
		return n*row + col;
	}
	
	/** manhattan priority = sum of Manhattan distances between blocks and goal */
	public int manhattan() {
		int man = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				man += calcDistance(i, j);
			}
		}
		
		return man;
	}
	
	/** calculate manhattan distance */
	private int calcDistance(int row, int col) {
		int tile = tiles[to1D(row, col)];
		return (isBlank(row, col)) ? 0 : Math.abs(row - ((tile-1)/n)) + Math.abs(col - ((tile-1)%n));
	}
	
	/** is this board the goal board? */
	public boolean isGoal() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (notInPlace(i, j)) return false;
			}
		}
		return true;
	}
	
	/** a board that is obtained by exchanging any pair of blocks
	 * needs to be immutable so can't use StdRandom...
	 */
	public Board twin() {
		if (n == 1) return null;
		
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n -1 ; j++) {
				if (!isBlank(i, j) && !isBlank(i, j+1)) {
					return new Board(toMatrix(swapTiles(to1D(i, j), to1D(i, j+1))));
				}
			}
		}
		
		throw new RuntimeException();
/*		int rand1 = StdRandom.uniform(numTiles);
		while (tiles[rand1] == BLANK) {
			rand1 = StdRandom.uniform(numTiles);
		}
		
		int rand2 = StdRandom.uniform(numTiles);
		while (tiles[rand2] == BLANK || rand2 == rand1) {
			rand2 = StdRandom.uniform(numTiles);
		}*/
	}
	
	/** copy array */
	private int[] copy(int[] input) {
		int[] copy = new int[input.length];
		for (int i = 0; i < input.length; i++) {
			copy[i] = input[i];
		}
		return copy;
	}
	
	/** swap the tiles in the two locations passed as inputs */
	private int[] swapTiles(int ind1, int ind2) {
		int[] output = copy(tiles);
		int temp = output[ind1];
		output[ind1] = output[ind2];
		output[ind2] = temp;
		return output;
	}
	
	/** takes tiles 1D array and returns a matrix */
	private int[][] toMatrix(int[] array) {
		int[][] output = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				output[i][j] = array[to1D(i, j)];
			}
		}
		return output;
	}
	
	/** does this board equal y? */
	public boolean equals(Object y) {
		if ( y == this) return true;
		if (y == null || !(y instanceof Board) || ((Board)y).dimension() != dimension()) return false;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (((Board) y).tiles[to1D(i, j)] != tiles[to1D(i, j)]) return false;
			}
		}
		return true;
	}
	
	/** all neighboring boards */
	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new Queue<Board>();
		int row = -1;
		int col = -1;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (isBlank(i, j)) {
					row = i;
					col = j;
				}
			}
		}
		
		if (row > 0) {
			neighbors.enqueue(new Board(toMatrix(swapTiles(to1D(row, col), to1D(row-1, col)))));
		}
		if (row < dimension() - 1) {
			neighbors.enqueue(new Board(toMatrix(swapTiles(to1D(row, col), to1D(row+1, col)))));
		}
		if (col > 0) {
			neighbors.enqueue(new Board(toMatrix(swapTiles(to1D(row, col), to1D(row, col-1)))));
		}
		if (col < dimension() - 1) {
			neighbors.enqueue(new Board(toMatrix(swapTiles(to1D(row, col), to1D(row, col+1)))));
		}
		
		return neighbors;
	}
	
	/** string representation of this board */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				str.append(String.format("%2d ",  tiles[to1D(i, j)]));
			}
			str.append("\n");
		}
		return str.toString();
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
		StdOut.print(initial.toString());
		
		for (Board board : initial.neighbors()) {
			StdOut.print(board.toString());
		}
	}

}
