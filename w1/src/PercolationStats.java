/**
 * 
 * @author Alan
 * 04/16/2018
 * Percolation class - Week 1 programming assignment Princeton Algorithms Part 1
 */

//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
//import Percolation;

/** Computational Experiment Class */
public class PercolationStats {
	/** Array holding fraction of open sites at percolation for all trials */
	private final double[] fracOpen;
	
	/** T represents number of trials to be run */
	private final int T;
	
	/** Constructor - run all trials with nxn Percolation object
	 * save fraction of open sites during percolation for each trial */
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException("n or trials must be greater than 0");
		}
		
		fracOpen = new double[trials];
		T = trials;
		
		for (int i=0; i<T; i++) {
			Percolation perc = new Percolation(n);
			while (!perc.percolates()) {
				int row = StdRandom.uniform(1,n+1);
				int col = StdRandom.uniform(1,n+1);
				if (!perc.isOpen(row,col)) {
					perc.open(row, col);
				}
			}
			fracOpen[i] = (double) perc.numberOfOpenSites()/(n*n);
		}
	}
	
	/** returns mean of fraction of open sites */
	public double mean() {
		return StdStats.mean(fracOpen);
	}
	
	/** returns standard deviation of fraction of open sites */
	public double stddev() {
		return StdStats.stddev(fracOpen);
	}
	
	/** returns low side of 95% confidence interval for mean of fraction of open sites  */
	public double confidenceLo() {
		return mean() - (1.96*stddev())/Math.sqrt(T);
	}
	
	/** returns high side of 95% confidence interval for mean of fraction of open sites  */
	public double confidenceHi() {
		return mean() + (1.96*stddev())/Math.sqrt(T);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length > 0) {
			PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			System.out.println("mean                    = " + percStats.mean());
			System.out.println("stddev                  = " + percStats.stddev());
			System.out.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
		}
	}

}
