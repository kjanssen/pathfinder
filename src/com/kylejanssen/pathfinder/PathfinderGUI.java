package com.kylejanssen.pathfinder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PathfinderGUI extends JPanel {

    Pathfinder pathfinder;
    JPanel contentPanel;
    FormPanel formPanel;
    GridPanel gridPanel;

    public PathfinderGUI () {
        pathfinder = new Pathfinder(35, 25, new GridParser("assets/plain.grid").getGrid());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel title = new JLabel("Pathfinder                  ");
        title.setFont(new Font("Arial", Font.PLAIN, 46));
        title.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(title);
        contentPanel = new JPanel();
        formPanel = new FormPanel(this);
        contentPanel.add(formPanel);
        gridPanel = new GridPanel();
        contentPanel.add(gridPanel);
        add(contentPanel);
    }

    private void setInput (String input) {
        pathfinder.initialize(new GridParser("assets/" + input.toLowerCase() + ".grid").getGrid());
        gridPanel.refresh();
    }

    private void solveInput (String algorithm) {
        PathfinderNode solution = null;

        if (algorithm.equals("Depth First Search"))
            solution = pathfinder.DepthFirstSearch(1, 5, 30, 20);
        else if (algorithm.equals("Breadth First Search"))
            solution = pathfinder.BreadthFirstSearch(1, 5, 30, 20);
        else if (algorithm.equals("Dijkstra's Algorithm"))
            solution = pathfinder.DijkstraSearch(1, 5, 30, 20);
        else if (algorithm.equals("Best First Search"))
            solution = pathfinder.BestFirstSearch(1, 5, 30, 20);
        else if (algorithm.equals("A*"))
            solution = pathfinder.AStarSearch(1, 5, 30, 20);

        for (int x = 0; x < 35; x++)
            for (int y = 0; y < 25; y++)
                if (pathfinder.grid[x][y].open)
                    gridPanel.tiles[x][y].setBackground(pathfinder.grid[x][y].visited ? Color.LIGHT_GRAY : Color.CYAN);

        while (solution != null) {
            System.out.println("( " + solution.x + ", " + solution.y + ")");
            gridPanel.tiles[solution.x][solution.y].setBackground(Color.DARK_GRAY);
            solution = solution.parent;
        }
    }

    private class FormPanel extends JPanel implements ActionListener {

        PathfinderGUI parent;
        JComboBox algorithmList;
        JComboBox inputList;
        JButton goButton;

        FormPanel (PathfinderGUI parent) {
            this.parent = parent;
            BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxLayout);
            JLabel algorithmLabel = new JLabel("Select Algorithm:  ");
            algorithmLabel.setAlignmentX(LEFT_ALIGNMENT);
            algorithmLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            add(algorithmLabel);
            String[] algorithms = {"Depth First Search", "Breadth First Search", "Dijkstra's Algorithm", "Best First Search", "A*"};
            algorithmList = new JComboBox(algorithms);
            algorithmList.addActionListener(this);
            algorithmList.setAlignmentX(LEFT_ALIGNMENT);
            algorithmList.setBorder(new EmptyBorder(0, 0, 20, 0));
            add(algorithmList);
            JLabel inputLabel = new JLabel("Select Input:  ");
            inputLabel.setAlignmentX(LEFT_ALIGNMENT);
            inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            add(inputLabel);
            String[] inputs = {"Plain", "Trap", "Slolem"};
            inputList = new JComboBox(inputs);
            inputList.addActionListener(this);
            inputList.setAlignmentX(LEFT_ALIGNMENT);
            inputList.setBorder(new EmptyBorder(0, 0, 20, 0));
            add(inputList);
            goButton = new JButton("Go");
            goButton.addActionListener(this);
            add(goButton);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == inputList) {
                parent.setInput((String)inputList.getSelectedItem());
            } else if (e.getSource() == goButton) {
                parent.setInput((String)inputList.getSelectedItem());
                parent.solveInput((String)algorithmList.getSelectedItem());
            }
        }
    }

    private class GridPanel extends JPanel {

        Tile [][] tiles;

        GridPanel () {
            setLayout(new GridLayout(25, 35, 1, 1));
            setBackground(Color.BLACK);
            setBorder(new EmptyBorder(2, 2, 2, 2));

            tiles = new Tile[35][25];
            for (int y = 0; y < 25; y++)
                for (int x = 0; x < 35; x++) {
                    tiles[x][y] = new Tile(pathfinder.grid[x][y]);
                    add(tiles[x][y]);
                }
        }

        void refresh () {
            for (int i = 0; i < 35; i++)
                for (int j = 0; j < 25; j++)
                    tiles[i][j].refresh();
        }

        private class Tile extends JPanel {

            PathfinderNode node;

            Tile(PathfinderNode node) {
                this.node = node;
                setPreferredSize(new Dimension(16, 16));
                setBackground (node.passable ? Color.WHITE : Color.BLACK);
            }

            void refresh () {
                setBackground (node.passable ? Color.WHITE : Color.BLACK);
            }
        }
    }

    public static void main (String [] args)
    {
        JFrame frame = new JFrame ("Pathfinder");
        PathfinderGUI gui = new PathfinderGUI();
        frame.getContentPane().add(gui);
        frame.setSize (800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable (false);
        frame.setVisible (true);
    }
}
