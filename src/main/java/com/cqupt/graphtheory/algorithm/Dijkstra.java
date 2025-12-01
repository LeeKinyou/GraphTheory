package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.algorithm.util.GraphGenerationFactory;
import com.cqupt.graphtheory.algorithm.util.TreeGeneration;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.*;

@Getter
public class Dijkstra {

    private final ArrayList<Node> nodes;

    private final ArrayList<Edge> edges;

    private final Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> adjacencyList;

    private final PriorityQueue<Map.Entry<Integer, Integer>> pq;

    private final ArrayList<Integer> parent;

    private final ArrayList<Integer> distance;

    private final ArrayList<Boolean> visited;

    private final int s;

    private final int t;

    public Dijkstra(ArrayList<Node> nodes, ArrayList<Edge> edges, int s, int t) {
        this.nodes = nodes;
        this.edges = edges;
        this.adjacencyList = GraphGenerationFactory.generateAdjacencyList("graph", nodes, edges);
        this.parent = new ArrayList<>(Collections.nCopies(nodes.size(), -1));
        this.distance = new ArrayList<>(Collections.nCopies(nodes.size(), Integer.MAX_VALUE));
        this.visited = new ArrayList<>(Collections.nCopies(nodes.size(), false));
        this.pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        this.s = s;
        this.t = t;
    }

    public ArrayList<Edge> executeDijkstra() {
        distance.set(s, 0);
        pq.add(new AbstractMap.SimpleEntry<>(s, 0));
        while (!pq.isEmpty()) {
            Map.Entry<Integer, Integer> entry = pq.poll();
            int u = entry.getKey();
            if (visited.get(u)) {
                continue;
            }
            visited.set(u, true);
            for (Map.Entry<Integer, Integer> neighbor : adjacencyList.get(u)) {
                int v = neighbor.getKey();
                int weight = neighbor.getValue();
                if (distance.get(v) > distance.get(u) + weight) {
                    distance.set(v, distance.get(u) + weight);
                    parent.set(v, u);
                    pq.add(new AbstractMap.SimpleEntry<>(v, distance.get(v)));
                }
            }
        }
        ArrayList<Edge> shortestPath = new ArrayList<>();
        int current = t;
        while (parent.get(current) != -1) {
            shortestPath.add(findNode(current, parent.get(current)));
            current = parent.get(current);
        }
        Collections.reverse(shortestPath);
//        System.out.println("最短路径长度为：" + distance.get(t));
//        System.out.println("最短路径为：");
//        System.out.println(shortestPath);
        return shortestPath;
    }

    private Edge findNode(int from, int to) {
        for (Edge edge : edges) {
            if (edge.getFrom().getId() == from && edge.getTo().getId() == to) {
                return edge;
            }
            if (edge.getFrom().getId() == to && edge.getTo().getId() == from) {
                return edge;
            }
        }
        return null;
    }
}
