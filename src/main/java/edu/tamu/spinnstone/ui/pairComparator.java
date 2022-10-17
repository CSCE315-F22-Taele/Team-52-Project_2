package edu.tamu.spinnstone.ui;

import java.util.ArrayList;
import java.util.Comparator;

// for sortedlist, greatest to smallest
public class pairComparator implements Comparator<ArrayList<String>> {
    @Override
    public int compare(ArrayList<String> lhs, ArrayList<String> rhs) {
        int lhsVal = Integer.valueOf(lhs.get(1));
        int rhsVal = Integer.valueOf(rhs.get(1));

        return Integer.compare(rhsVal, lhsVal);
    }
}
