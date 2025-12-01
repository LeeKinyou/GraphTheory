package com.cqupt.graphtheory.algorithm.util;


import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Generation {

    public Generation() {
    }

    public abstract ArrayList<Node> generateNodes(int count, JPanel graphPanel);
    public abstract ArrayList<Edge> generateEdges(int count, ArrayList<Node> nodes);

    // Factory method to get instance based on type
    public static Generation getInstance(String type) {
        switch (type.toLowerCase()) {
            case "circle":
                return new CircleGeneration();
            case "tree":
                return new TreeGeneration();
            default:
                throw new IllegalArgumentException("Unknown generation type: " + type);
        }
    }
}