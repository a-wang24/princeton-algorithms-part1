/**
 * 
 * @author Alan
 * 04/12/2018
 * Percolation class - Week 1 programming assignment Princeton Algorithms Part 1
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  WeightedQuickUnionUF wquf;
 
  public Percolation(int n)
  {
     if (n < 0)
         throw new IllegalArgumentException("n must be greater than 0");
     
     wquf = new WeightedQuickUnionUF(n*n);
  }
  
  public void open(int row, int col)
  {
      
  }
}
