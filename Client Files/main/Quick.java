import java.util.*;
/*******************************************************************
* %%%%%%%%%%%%%%%%%%%%%%%%
* %%%% IMPORTANT NOTE %%%%
* %%%%%%%%%%%%%%%%%%%%%%%%
* This was originally part of the algs4.cs.princeton.edu/ textbook
* code repository. It has been modified by Jake Ballinger in order
* to work with our particular implementation of a Vector of Items. 
*******************************************************************/


/*************************************************************************
 *  Compilation:  javac Quick.java
 *  Execution:    java Quick < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                http://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using quicksort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Quick < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *       
 *  % java Quick < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *
 *  Remark: For a type-safe version that uses static generics, see
 *
 *    http://algs4.cs.princeton.edu/23quicksort/QuickPedantic.java
 *
 *************************************************************************/

/**
 *  The <tt>Quick</tt> class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Quick {

    // This class should not be instantiated.
    private Quick() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Vector<Item> v) {
    	Collections.shuffle(v);
    	sort(v, 0, v.size() - 1);
    	assert isSorted(v);
    	//Knapsack.printSolution(v);
    }
    
    /*
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    } */

	private static void sort(Vector<Item> v, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(v, lo, hi);
		sort(v, lo, j-1);
		sort(v, j+1, hi);
		assert isSorted(v, lo, hi);
	}

	/*		
    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        assert isSorted(a, lo, hi);
    } */

	private static int partition(Vector<Item> v, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		Item v1 = new Item(v.get(i));
		while(true) {
			// find item on lo to swap
			while (less(v.get(++i), v1))
				if (i == hi) break;

			while (less(v1, v.get(--j))) 
				if (j == lo) break;
				
			if (i >= j) break;
			
			exch(v, i, j);
		}
		
		exch(v, lo, j);
		
		return j;
	}
		
	/*
    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    } */
    
    /*
    public static Vector<Item> select(Vector<Item> v, int k) {
    	if (k < 0 || k >= v.size()) {
    		throw new IndexOutOfBoundsException("Selected element out of bounds.");
    	}
    	Collections.shuffle(v);
    	int lo = 0, hi = v.size() - 1;
    	while (hi > lo) {
    		int i = partition(v, lo, hi);
    		if (i>k) hi = i - 1;
    		else if (i<k) lo = i + 1;
    		else return v.elementAt(i);
    	}
    	return v.elementAt(lo);
    }*/

	
    /**
     * Rearranges the array so that a[k] contains the kth smallest key;
     * a[0] through a[k-1] are less than (or equal to) a[k]; and
     * a[k+1] through a[N-1] are greater than (or equal to) a[k].
     * @param a the array
     * @param k find the kth smallest
     *
    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if      (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }*/



   /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    private static boolean less(Item i, Item i1) {
    	return i.ratio < i1.ratio ? true : false;
    }
    
    /*
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    } */
    
    private static void exch(Vector<Item> v, int i, int j) {
    	Item swap = new Item(v.get(i));
    	v.set(i, v.get(j));
    	v.set(j, swap);
    }
    
    /*    
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    } */


   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/
    private static boolean isSorted(Vector<Item> v) {
    	return isSorted(v, 0, v.size() - 1);
    }
    
    /*
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    } */

	private static boolean isSorted(Vector<Item> v, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++) 
			if (less(v.get(i), v.get(i-1))) return false;
		return true;
	}

	/*
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    } */

	private static void show(Vector<Item> v) {
		for (int i = 0; i < v.size(); i++) {
			System.out.println(v.get(i).toString());
		}
	}
	
	/*
    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    } */

    /**
     * Reads in a sequence of strings from standard input; quicksorts them; 
     * and prints them to standard output in ascending order. 
     * Shuffles the array and then prints the strings again to
     * standard output, but this time, using the select method.
     *
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Quick.sort(a);
        show(a);

        // shuffle
        StdRandom.shuffle(a);

        // display results again using select
        StdOut.println();
        for (int i = 0; i < a.length; i++) {
            String ith = (String) Quick.select(a, i);
            StdOut.println(ith);
        }
    } */
}
