package com.cqupt.graphtheory.visualization.panel;


import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlgorithmViewPanel extends JPanel {
    protected MainAppFrame parentFrame;
    protected String algorithmName;
    
    // 控制面板组件
    protected JTextField nodeCountField;
    protected JTextField edgeCountField;
    protected JButton generateButton;
    protected JButton runAlgorithmButton;
    protected JButton backButton;
    
    // 图形显示区域
    protected JPanel graphPanel;
    
    public AlgorithmViewPanel(MainAppFrame parent, String algorithmName) {
        this.parentFrame = parent;
        this.algorithmName = algorithmName;
        initializeComponents();
        setupLayout();
        addEventListeners();
    }
    
    private void initializeComponents() {
        // 初始化输入组件
        nodeCountField = new JTextField(5);
        edgeCountField = new JTextField(5);
        generateButton = new JButton("生成图");
        runAlgorithmButton = new JButton("运行算法");
        backButton = new JButton("返回主页面");
        
        // 初始化图形显示区域
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 这里绘制图结构
                drawGraph(g);
            }
        };
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setPreferredSize(new Dimension(600, 400));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("节点数:"));
        controlPanel.add(nodeCountField);
        controlPanel.add(new JLabel("边数:"));
        controlPanel.add(edgeCountField);
        controlPanel.add(generateButton);
        controlPanel.add(runAlgorithmButton);
        controlPanel.add(backButton);
        
        add(controlPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        // 生成图按钮事件
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateGraph();
            }
        });
        
        // 运行算法按钮事件
        runAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAlgorithm();
            }
        });
        
        // 返回按钮事件
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showView(MainAppFrame.VIEW_SELECTION);
            }
        });
    }
    
    // 生成图的方法
    protected void generateGraph() {
    }
    
    // 运行算法的方法
    protected void runAlgorithm() {
    }
    
    // 绘制图的方法
    protected void drawGraph(Graphics g) {

    }
}
