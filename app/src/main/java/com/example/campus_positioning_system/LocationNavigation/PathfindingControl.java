package com.example.campus_positioning_system.LocationNavigation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Database.Converters;
import com.example.campus_positioning_system.Node;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;

public class PathfindingControl{
    //If Distance to last location is larger than the Metric, the new Location is invalid
    private static final double distanceMetric = 5.0;

    //Scaling factor to get true Distance values
    private static final double distanceScale = 1.0;


    private static Node currentLocation = new Node("", 64,44,0);
    private static Node targetLocation = null;

    private static final HipsterGraph<Node, Double> graph = buildGraph("GraphRef.txt");
    private static TreeSet<Node> tree;

    public static synchronized Node updateCurrentLocation(Node newLocation){
            if(currentLocation == null) {
                return currentLocation = newLocation;
            }
            if (!(euclideanDistance(currentLocation, newLocation) > distanceMetric)) {
                return currentLocation = newLocation;
            }
            return currentLocation = newLocation;
    }

    public static synchronized Node updateTargetLocation(Node newLocation){
        return targetLocation = newLocation;
    }

    public synchronized Node getCurrentLocation(){
            return currentLocation;
    }
    public synchronized Node getTargetLocation(){
            return targetLocation;
    }

    private static double euclideanDistance(Node a, Node b){
        return Math.sqrt(Math.pow(a.getX() - b.getX(),2) + Math.pow(a.getY() - b.getY(), 2)) * distanceScale;
    }


    public static HipsterGraph<Node, Double> buildGraph(String filename){
        try {
            Scanner scanner = new Scanner(MainActivity.mainContext().getAssets().open(filename));
            GraphBuilder<Node, Double> graph = GraphBuilder.<Node, Double>create(); //
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("//") || line.isEmpty()) // Skip "commented" lines and empty lines
                    continue;
                String[] arr = line.split("-");
                Node a = Converters.fromString(arr[0]);
                Node b = Converters.fromString(arr[1]);
                if(tree == null)
                    tree = new TreeSet<>();

                if (!tree.contains(a))
                    tree.add(a);
                else
                    a = tree.floor(a);

                if (!tree.contains(b))
                    tree.add(b);
                else
                    b = tree.floor(b);
                graph.connect(a).to(b).withEdge(PathfindingControl.euclideanDistance(a,b));
            }
            scanner.close();

            return graph.createUndirectedGraph();
        }catch(FileNotFoundException e){} catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Node> calculatePath(){
        System.out.println("Calculating Path from: " + currentLocation.toString() + " to: " + targetLocation);
        SearchProblem p = GraphSearchProblem.startingFrom(tree.floor(currentLocation)).in(graph).takeCostsFromEdges().build();
        List<List<Node>> listTest = Hipster.createAStar(p).search(tree.floor(targetLocation)).getOptimalPaths();
        return listTest.get(0);
    }


    public static TreeSet<Node> getTree(){
        return tree;
    }


}
