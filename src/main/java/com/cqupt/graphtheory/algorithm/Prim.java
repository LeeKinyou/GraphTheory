package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.*;

@Getter
public class Prim {
    private final ArrayList<Edge> mstEdges;

    private final ArrayList<Node> nodes;

    private final ArrayList<Edge> edges;

    private int minValue = 0;

    public Prim(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        mstEdges = new ArrayList<>();
    }

    public ArrayList<Edge> executePrim() {
        if (nodes.isEmpty()) return mstEdges;

        Map<Node, List<Map.Entry<Node, Edge>>> mp = new HashMap<>();
        for (Node node : nodes) {
            mp.put(node, new ArrayList<>());
        }
        for (Edge edge : edges) {
            mp.get(edge.getFrom()).add(new AbstractMap.SimpleEntry<>(edge.getTo(), edge));
            mp.get(edge.getTo()).add(new AbstractMap.SimpleEntry<>(edge.getFrom(), edge));
        }

        Node startNode = nodes.getFirst();
        Set<Node> visited = new HashSet<>();

        // 小根堆
        PriorityQueue<Map.Entry<Integer, Map.Entry<Node, Edge>>> heap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));

        heap.add(new AbstractMap.SimpleEntry<>(0, new AbstractMap.SimpleEntry<>(startNode, null)));

        while (!heap.isEmpty() && visited.size() < nodes.size()) {
            Map.Entry<Integer, Map.Entry<Node, Edge>> curr = heap.poll();
            int weight = curr.getKey();
            Node currNode = curr.getValue().getKey();
            Edge currEdge = curr.getValue().getValue();

            if (visited.contains(currNode)) continue;

            visited.add(currNode);
            if (currEdge != null) {
                mstEdges.add(currEdge);
                minValue += weight;
            }

            // 把当前节点的所有未遍历邻接节点加入堆中
            for (Map.Entry<Node, Edge> neighborEntry : mp.get(currNode)) {
                Node neighbor = neighborEntry.getKey();
                Edge edgeToNeighbor = neighborEntry.getValue();
                if (!visited.contains(neighbor)) {
                    heap.add(new AbstractMap.SimpleEntry<>(
                            edgeToNeighbor.getWeight(),
                            new AbstractMap.SimpleEntry<>(neighbor, edgeToNeighbor)
                    ));
                }
            }
        }

        return mstEdges;
    }
}
