package com.cqupt.graphtheory;

import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainAppFrame().setVisible(true);
        });
    }
}