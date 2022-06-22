package com.example.campus_positioning_system.LocationNavigation;

import com.example.campus_positioning_system.NNObject;

import java.util.TreeSet;
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class NNControl {
    /*----------------------------------------------------------------
    Return best Match for search
    ----------------------------------------------------------------*/
    private NNObject computeMatch(NNObject search, NNObject UMatch, NNObject BMatch)
            throws NoSuchElementException {
        if (UMatch == null)
            return BMatch;
        if (BMatch == null)
            return UMatch;
        boolean upB = !UMatch.getMac().equals(search.getMac());
        boolean loB = !BMatch.getMac().equals(search.getMac());
        if (upB && loB) // Making sure Adresses are equal
            throw new NoSuchElementException("No valid match found for " + search);
        else if (upB)
            return BMatch;
        else if (loB)
            return UMatch;

        // Returning match with min distance to search
        return Math.abs(UMatch.getRssi() - search.getRssi()) <= Math.abs(BMatch.getRssi() - search.getRssi()) ? UMatch
                : BMatch;

    }
    /*----------------------------------------------------------------
     Finds Nearest Neighbor of given Object in TreeSet
    ----------------------------------------------------------------*/
    public NNObject getNN(NNObject search, TreeSet<NNObject> tree) {

        NNObject resUpper = tree.floor(search); // Get Upper Match
        NNObject resLower = tree.ceiling(search); // Get Lower Match

        return new NNControl().computeMatch(search, resUpper, resLower);

    }

    /*----------------------------------------------------------------
    Finds K Neares Neighbors of given Object
    ----------------------------------------------------------------*/
    public LinkedList<NNObject> getKNN(NNObject search, int k, TreeSet<NNObject> tree) {
        LinkedList<NNObject> result = new LinkedList<NNObject>();
        for (int i = 0; i < k; i++) {
            try {
                result.add(getNN(search, tree));
                tree.remove(result.getLast());
            } catch (NoSuchElementException e) {
               // System.out.println(e.getMessage());
                i = k;
            }
        }
        tree.addAll(result);
        return result;
    }

}