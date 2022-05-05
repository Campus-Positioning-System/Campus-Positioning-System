package com.example.campus_positioning_system;

import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class LocationControl {

    /*----------------------------------------------------------------
    Takes a List of NNObjects and finds the most abundent Node in the
    List. Returns that Node.

    ----------------------------------------------------------------*/
    private Node findLocation(List<NNObject> list) {
        TreeSet<CNode> check = new TreeSet<>();

        for (NNObject nn : list) {
            CNode a = new CNode(nn.getLocation(), 1);
            if (check.contains(a))
                check.floor(a).increment();

            else
                check.add(a);

        }
        Comparator<CNode> comparator = new Comparator<CNode>() {
            @Override
            public int compare(final CNode o1, final CNode o2) {
                return o1.getParent().equals(o2.getParent()) ? o1.getCount().compareTo(o2.getCount())
                        : o1.getParent().compareTo(o2.getParent());
            }
        };
        TreeSet<CNode> sort = new TreeSet<>(comparator);
        sort.addAll(check);
        return sort.first().getParent();
    }
}
