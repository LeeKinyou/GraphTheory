package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.algorithm.util.GraphGenerationFactory;
import com.cqupt.graphtheory.algorithm.util.TreeGeneration;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import java.util.*;

public class TearCircle {
    private final ArrayList<Edge> mstEdges; // 存储被删除的边（破圈移除的边）
    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;
    private final Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> adjacencyList; // 邻接表（节点ID -> 邻接节点ID列表）
    private final Map<String, Edge> edgeMap; // 快速查找两点间的边（key: "u-v"或"v-u"）

    public TearCircle(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.mstEdges = new ArrayList<>();
        this.adjacencyList = GraphGenerationFactory.generateAdjacencyList("tree", nodes, edges);
        this.edgeMap = buildEdgeMap(); // 构建边的快速查找映射
    }

    /**
     * 破圈法核心逻辑：不断找环并移除环中权值最大的边，直到无环
     */
    public ArrayList<Edge> executeTearCircle() {
        // 循环检测环，直到图中无环
        while (true) {
            // 1. 查找图中的一个环（返回环的节点ID列表，如[1,2,3,1]）
            List<Integer> cycle = findCycle();
            if (cycle == null || cycle.size() < 3) {
                break; // 无环则退出
            }

            // 2. 找出环中权值最大的边
            Edge maxEdge = findMaxEdgeInCycle(cycle);
            if (maxEdge != null) {
                // 3. 移除该边（从边集合、邻接表中删除），并加入被删除的边列表
                removeEdge(maxEdge);
                mstEdges.add(maxEdge);
            }
        }
        return mstEdges;
    }

    /**
     * 构建边的快速查找映射：key为"u-v"或"v-u"，value为对应的边
     */
    private Map<String, Edge> buildEdgeMap() {
        Map<String, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            int u = edge.getFrom().getId();
            int v = edge.getTo().getId();
            map.put(u + "-" + v, edge);
            map.put(v + "-" + u, edge);
        }
        return map;
    }

    /**
     * DFS检测环：返回环的节点列表（如[1,2,3,1]）
     */
    private List<Integer> findCycle() {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parent = new HashMap<>(); // 记录节点的父节点（避免回边误判）
        Deque<Integer> path = new ArrayDeque<>(); // 记录当前DFS路径

        // 遍历所有节点（处理非连通图）
        for (Node node : nodes) {
            int startId = node.getId();
            if (!visited.contains(startId)) {
                List<Integer> cycle = dfs(startId, -1, visited, parent, path);
                if (cycle != null) {
                    return cycle;
                }
            }
        }
        return null; // 无环
    }

    /**
     * 递归DFS查找环
     */
    private List<Integer> dfs(int current, int parentNode, Set<Integer> visited,
                              Map<Integer, Integer> parent, Deque<Integer> path) {
        visited.add(current);
        path.push(current);
        parent.put(current, parentNode);

        // 遍历邻接节点
        for (Map.Entry<Integer, Integer> neighborEntry : adjacencyList.getOrDefault(current, new ArrayList<>())) {
            int neighbor = neighborEntry.getKey();
            if (neighbor == parentNode) {
                continue; // 跳过父节点，避免回边
            }
            if (visited.contains(neighbor)) {
                // 找到环：从当前路径中提取环的节点
                List<Integer> cycle = new ArrayList<>();
                cycle.add(neighbor); // 环的起点
                while (!path.isEmpty() && path.peek() != neighbor) {
                    cycle.add(path.pop());
                }
                cycle.add(neighbor); // 环的终点（闭合）
                return cycle;
            }
            // 递归DFS
            List<Integer> cycle = dfs(neighbor, current, visited, parent, path);
            if (cycle != null) {
                return cycle;
            }
        }

        path.pop(); // 回溯
        return null;
    }

    /**
     * 找出环中权值最大的边
     */
    private Edge findMaxEdgeInCycle(List<Integer> cycle) {
        Edge maxEdge = null;
        int maxWeight = -1;

        // 遍历环的边（如cycle为[1,2,3,1]，则边为1-2、2-3、3-1）
        for (int i = 0; i < cycle.size() - 1; i++) {
            int u = cycle.get(i);
            int v = cycle.get(i + 1);
            Edge edge = edgeMap.get(u + "-" + v);
            if (edge != null && edge.getWeight() > maxWeight) {
                maxWeight = edge.getWeight();
                maxEdge = edge;
            }
        }
        return maxEdge;
    }

    /**
     * 从图中移除指定边（更新边集合、邻接表、edgeMap）
     */
    private void removeEdge(Edge edge) {
        // 1. 从边集合中移除
        edges.remove(edge);

        // 2. 从edgeMap中移除
        int u = edge.getFrom().getId();
        int v = edge.getTo().getId();
        edgeMap.remove(u + "-" + v);
        edgeMap.remove(v + "-" + u);

        // 3. 从邻接表中移除
        adjacencyList.get(u).remove(java.lang.Integer.valueOf(v));
        adjacencyList.get(v).remove(java.lang.Integer.valueOf(u));
    }
}