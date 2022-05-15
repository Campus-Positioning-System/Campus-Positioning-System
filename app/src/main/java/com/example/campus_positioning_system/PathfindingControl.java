package com.example.campus_positioning_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import es.usc.citius.hipster.algorithm.Algorithm;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;

public class PathfindingControl {
    //If Distance to last location is larger than the Metric, the new Location is invalid
    private final double distanceMetric = 5.0;

    //Scaling factor to get true Distance values
    private final double distanceScale = 0.3;


    private Node currentLocation;
    private Node targetLocation;

    private final HipsterGraph<Node, Double> graph = buildGraph("Hallo");

    public synchronized Node updateCurrentLocation(Node newLocation){
        synchronized (currentLocation) {
            if (!(euclideanDistance(currentLocation, newLocation) > distanceMetric) || currentLocation == null)
             return this.currentLocation = newLocation;
            return null;
        }
    }

    public synchronized Node updateTargetLocation(Node newLocation){
        synchronized (targetLocation){
            return this.targetLocation = newLocation;
        }
    }

    private double euclideanDistance(Node a, Node b){
        return Math.sqrt(Math.pow(a.getX() - b.getX(),2) + Math.pow(a.getY() - b.getY(), 2)) * distanceScale;
    }

    public static HipsterGraph<Node, Double> buildGraph(String filename){
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            TreeSet<Node> tree = new TreeSet<>();
            GraphBuilder<Node, Double> graph = GraphBuilder.<Node, Double>create(); //
            while (scanner.hasNextLine()) {
                String[] arr = scanner.nextLine().split("[!-]+");
                Node a = Converters.fromString(arr[0]);
                Node b = Converters.fromString(arr[1]);
                if (!tree.contains(a))
                    tree.add(a);
                else
                    a = tree.floor(a);

                if (!tree.contains(b))
                    tree.add(b);
                else
                    b = tree.floor(b);
                graph.connect(a).to(b).withEdge(Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2)));
            }
            scanner.close();

            return graph.createUndirectedGraph();
        }catch(FileNotFoundException e){}
    return null;
    }


    public List<Algorithm.SearchResult> calculatePath(){
        SearchProblem p = GraphSearchProblem.startingFrom(currentLocation).in(graph).takeCostsFromEdges().build();
        Algorithm.SearchResult x = Hipster.createAStar(p).search(targetLocation);
        return x.getOptimalPaths();
    }

}