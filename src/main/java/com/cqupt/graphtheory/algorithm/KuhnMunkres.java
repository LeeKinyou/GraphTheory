package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;

@Getter
public class KuhnMunkres {
    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;
    private final int n;
    private final ArrayList<ArrayList<Integer>> weight;
    private final ArrayList<Integer> lx, ly;
    private final ArrayList<Integer> matchY;
    private final ArrayList<Boolean> S, T;

    private ArrayList<Edge> matchedEdges;
    private int totalWeight;

    public KuhnMunkres(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.n = nodes.size() / 2;

        this.weight = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            this.weight.add(new ArrayList<>(Collections.nCopies(n, 0)));
        }
        this.lx = new ArrayList<>(Collections.nCopies(n, 0));
        this.ly = new ArrayList<>(Collections.nCopies(n, 0));
        this.matchY = new ArrayList<>(Collections.nCopies(n, -1));
        this.S = new ArrayList<>(Collections.nCopies(n, false));
        this.T = new ArrayList<>(Collections.nCopies(n, false));

        for (Edge edge : edges) {
            int u = edge.getFrom().getId();
            int v = edge.getTo().getId();
            if (u >= n && v < n) {
                int temp = u;
                u = v;
                v = temp;
            }
            if (u < n && v >= n) {
                weight.get(u).set(v - n, edge.getWeight());
            }
        }
    }

    public ArrayList<Edge> executeKuhnMunkres() {
        for (int i = 0; i < n; i++) {
            int maxWeight = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                if (weight.get(i).get(j) > maxWeight) {
                    maxWeight = weight.get(i).get(j);
                }
            }
            lx.set(i, maxWeight);
        }

        for (int i = 0; i < n; i++) {
            while (true) {
                Collections.fill(S, false);
                Collections.fill(T, false);
                if (dfsMatch(i)) {
                    break;
                } else {
                    updateLabels();
                }
            }
        }

        matchedEdges = new ArrayList<>();
        totalWeight = 0;
        for (int j = 0; j < n; j++) {
            if (matchY.get(j) != -1) {
                int i = matchY.get(j);
                totalWeight += weight.get(i).get(j);
                for (Edge edge : this.edges) {
                    int u = edge.getFrom().getId();
                    int v = edge.getTo().getId();
                    if ((u == i && v == j + n) || (v == i && u == j + n)) {
                        matchedEdges.add(edge);
                        break;
                    }
                }
            }
        }
        return matchedEdges;
    }

    private boolean dfsMatch(int u) {
        S.set(u, true);
        for (int v = 0; v < n; v++) {
            if (!T.get(v) && lx.get(u) + ly.get(v) == weight.get(u).get(v)) {
                T.set(v, true);
                if (matchY.get(v) == -1 || dfsMatch(matchY.get(v))) {
                    matchY.set(v, u);
                    return true;
                }
            }
        }
        return false;
    }

    private void updateLabels() {
        int delta = Integer.MAX_VALUE;
        for (int u = 0; u < n; u++) {
            if (S.get(u)) {
                for (int v = 0; v < n; v++) {
                    if (!T.get(v)) {
                        delta = Math.min(delta, lx.get(u) + ly.get(v) - weight.get(u).get(v));
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (S.get(i)) {
                lx.set(i, lx.get(i) - delta);
            }
            if (T.get(i)) {
                ly.set(i, ly.get(i) + delta);
            }
        }
    }
}
