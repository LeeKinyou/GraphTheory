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

        JLabel authorLabel = new JLabel("李金洋、严书盈、冯佳慧、曹冰雪");
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel coderAuthorLabel = new JLabel("Coded & designed by Lee Kinyou with Java");
        coderAuthorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coderAuthorLabel.setForeground(Color.GRAY);
        coderAuthorLabel.setFont(coderAuthorLabel.getFont().deriveFont(12f)); // 设置字体大小为12

        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(5));
        add(authorLabel);
        add(Box.createVerticalStrut(10));
        add(coderAuthorLabel);
        add(Box.createVerticalStrut(10));
    }
}
