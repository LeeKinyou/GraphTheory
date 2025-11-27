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

        // 1. 构建邻接表：key为节点，value为<邻接节点, 对应边>
        Map<Node, List<Map.Entry<Node, Edge>>> adj = new HashMap<>();
        for (Node node : nodes) {
            adj.put(node, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adj.get(edge.getFrom()).add(new AbstractMap.SimpleEntry<>(edge.getTo(), edge));
            adj.get(edge.getTo()).add(new AbstractMap.SimpleEntry<>(edge.getFrom(), edge));
        }

        // 2. 初始化辅助结构
        Node startNode = nodes.getFirst(); // 选第一个节点为起点
        Set<Node> visited = new HashSet<>(); // 已加入MST的节点
        // 小根堆：存储<边权值, <当前节点, 关联边>>，按权值升序
        PriorityQueue<Map.Entry<Integer, Map.Entry<Node, Edge>>> heap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));

        // 起点入堆（初始无关联边，权值0）
        heap.add(new AbstractMap.SimpleEntry<>(0, new AbstractMap.SimpleEntry<>(startNode, null)));

        while (!heap.isEmpty() && visited.size() < nodes.size()) {
            // 取出权值最小的边/节点
            Map.Entry<Integer, Map.Entry<Node, Edge>> curr = heap.poll();
            int weight = curr.getKey();
            Node currNode = curr.getValue().getKey();
            Edge currEdge = curr.getValue().getValue();

            // 若当前节点已加入MST，跳过
            if (visited.contains(currNode)) continue;

            // 将当前节点加入MST
            visited.add(currNode);
            if (currEdge != null) { // 跳过起点的空边
                mstEdges.add(currEdge);
                minValue += weight; // 累加总权值
            }

            // 遍历邻接节点，更新堆
            for (Map.Entry<Node, Edge> neighborEntry : adj.get(currNode)) {
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
