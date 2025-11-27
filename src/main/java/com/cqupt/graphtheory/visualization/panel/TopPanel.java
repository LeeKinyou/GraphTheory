package com.cqupt.graphtheory.visualization.panel;


import javax.swing.*;
import java.awt.*;

/**
 * 主面板
 */
public class TopPanel extends JPanel {

    public TopPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("第五组");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18));

        JLabel authorLabel = new JLabel("李金洋");
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel coderAuthorLabel = new JLabel("Coded & designed by Lee Kinyou with Java");
        coderAuthorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(5));
        add(authorLabel);
        add(Box.createVerticalStrut(10));
        add(coderAuthorLabel);
        add(Box.createVerticalStrut(10));
    }
}