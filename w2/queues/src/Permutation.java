/**
 * @author Alan
 * 
 * Coursera: Princeton Algorithms and Data Structures 1
 * Randomized Queues and Deques
 * 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomizedQueue<String> s = new RandomizedQueue<String>();
		
		while(!StdIn.isEmpty()) {
			s.enqueue(StdIn.readString());
		}
		
		int k = Integer.parseInt(args[0]);
		for (int i = 0; i < k; i++) {
			StdOut.println(s.dequeue());
		}
	}

}
