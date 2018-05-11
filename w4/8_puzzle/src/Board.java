/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * Week 4: 8 Puzzle
 * 
 */

public class Board {
	private int n;
	private Board predecessor;
	private int[] tiles;
	private final int BLANK = 0;
	private int manhattan;
	
	/** construct board from nxn array of blocks. Assume blocks is nxn containing integers between 0 and n^2-1 */
	public Board(int[][] blocks) {
		n = blocks.length;
		tiles = new int[n*n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tiles[to1D(i,j)] = blocks[i][j];
			}
		}
		
		manhattan = manhattan();
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
	
	/** a board that is obtained by exchanging any pair of blocks */
	public Board twin() {
		
	}
	
	/** does this board equal y? */
	public boolean equals(Object y) {
		
	}
	
	/** all neighboring boards */
	public Iterable<Board> neighbors() {
		
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
	
	private void setPredecessor(Board pred) {
		predecessor = pred;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
