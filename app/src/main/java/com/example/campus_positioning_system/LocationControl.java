package com.example.campus_positioning_system;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class LocationControl {

    private static AppDatabase db;
    private static NNObjectDao dao;

    public LocationControl(){
        if (db == null || dao == null)
            createDB();
    }
    private static void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.getNNObjectDao();
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
                return o1.getParent().equals(o2.getParent()) ? o1.getCount().compareTo(o2.getCount())
                        : o1.getParent().compareTo(o2.getParent());
            }
        };
        TreeSet<CNode> sort = new TreeSet<>(comparator);
        sort.addAll(check);
        return sort.first().getParent();
    }

    /*----------------------------------------------------------------
    Takes a List of NNObjects, finds nearest matches and stores them
    in a List.
    Values are then passed to findLocation().
    ----------------------------------------------------------------*/
    public Node locate(List<NNObject> search, List<String> outOf) {
        /*Was relevant for testing....
        TreeSet<NNObject> tree = DBMock.getTree();*/
        TreeSet<NNObject> tree = new TreeSet<>();
        tree.addAll(dao.getRelevantData(outOf));

        List<NNObject> found = new LinkedList<NNObject>();
        for (; !search.isEmpty(); search.remove(0))
            found.addAll(new NNControl().getKNN(search.get(0), 3, tree));
        return findLocation(found);
    }

}