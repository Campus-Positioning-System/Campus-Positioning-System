package com.example.campus_positioning_system;

//Deprecated, this is now located in NNControl class

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Logic {
    private static AppDatabase db;
    private static NNObjectDao nnObjectDao;

    public Logic(AppDatabase db) {
        this.db = db;
    }


    //Return best Match for search
    public static NNObject computeMatch(NNObject search, NNObject UMatch, NNObject BMatch)
            throws IllegalArgumentException {
        boolean upB;
        boolean loB;
        upB = !UMatch.getMac().equals(search.getMac());
        loB = !BMatch.getMac().equals(search.getMac());
        if (upB && loB)     //Making sure Adresses are equal
            throw new IllegalArgumentException("no valid match found");
        else if (upB)
            return BMatch;
        else if (loB)
            return UMatch;

        //Returning match with min distance to search
        return Math.abs(UMatch.getRssi() - search.getRssi()) <= Math.abs(BMatch.getRssi() - search.getRssi()) ? UMatch
                : BMatch;


    }


    //Gets nearest Values from Tree Structure
    public static NNObject getNN(NNObject search) {
        nnObjectDao = db.getNNObjectDao();
        TreeSet<NNObject> tree;
        tree = (TreeSet<NNObject>) nnObjectDao.getAllData()
                .stream()
                .collect(Collectors.toSet()); // Get Database
        NNObject resUpper = tree.floor(search); // Get Upper Match
        NNObject resLower = tree.ceiling(search); // Get Lower Match

        return computeMatch(search,resUpper,resLower);

    }


}
