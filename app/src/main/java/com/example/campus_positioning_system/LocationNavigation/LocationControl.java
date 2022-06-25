package com.example.campus_positioning_system.LocationNavigation;

import com.example.campus_positioning_system.CNode;
import com.example.campus_positioning_system.Database.AppDatabase;
import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.Database.NNObjectDao;
import com.example.campus_positioning_system.Node;

import java.util.Locale;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class LocationControl {

    private final static NNObjectDao dao = AppDatabase.getInstance().nnObjectDao();

    public LocationControl(){
    }

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
            public int compare(final CNode o1, final CNode o2) {
                return o1.getCount().equals(o2.getCount()) ? o1.getParent().compareTo(o2.getParent())
                        : o1.getCount().compareTo(o2.getCount());
            }
        };
        TreeSet<CNode> sort = new TreeSet<>(comparator);
        sort.addAll(check);
        return sort.last().getParent();
    }

    /*----------------------------------------------------------------
    Takes a List of NNObjects, finds nearest matches and stores them
    in a List.
    Values are then passed to findLocation().
    ----------------------------------------------------------------*/
    public Node locate(List<NNObject> search) {
        System.out.println("Location starting...");
        /*Was relevant for testing....
        TreeSet<NNObject> tree = DBMock.getTree();*/
        List<String> outOf = new LinkedList<>();
        for(NNObject s: search) {
            outOf.add(s.getMac());
        }
        TreeSet<NNObject> tree = new TreeSet<>(dao.getRelevantData(outOf));

        List<NNObject> found = new LinkedList<NNObject>();
        if(tree.isEmpty() || search.isEmpty()) {
            System.out.println("Location not found -> Current Location is set to Default (55/67/1)");
            Node defaultNode = new Node("default",55,67,1);
            /*  //Demo
            if(test == 0) {
                defaultNode = new Node("default",100,67,1);
                test++;
            } else if(test == 1) {
                defaultNode = new Node("default",20,67,0);
                test++;
            } else {
                defaultNode = new Node("default",30,67,2);
                test = 0;
            }
            */
            PathfindingControl.updateCurrentLocation(defaultNode);
            return defaultNode;
        }
        for (; !search.isEmpty(); search.remove(0))
            found.addAll(new NNControl().getKNN(search.get(0), 3, tree));

        Node loc = findLocation(found);
        PathfindingControl.updateCurrentLocation(loc);
        System.out.println("Location located at: " + loc.toString());
        return loc;
    }
    //public static int test = 0;   //For Demo Use
}