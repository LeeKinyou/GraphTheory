package com.cqupt.graphtheory.visualization.panel;


import javax.swing.*;
import java.awt.*;

/**
 * 主面板
 */
public class TopPanel extends JPanel {

    private final JLabel titleLabel;
    private final JLabel authorLabel;

    public TopPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY); // Optional styling

        titleLabel = new JLabel("第五组");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18));

        authorLabel = new JLabel("李金洋");
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(5));
        add(authorLabel);
        add(Box.createVerticalStrut(10));
    }
}