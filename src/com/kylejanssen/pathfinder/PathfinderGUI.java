package com.kylejanssen.pathfinder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PathfinderGUI extends JPanel {

    Pathfinder pathfinder;
    GridPanel gridPanel;

    public PathfinderGUI () {
        gridPanel = new GridPanel();
        add(gridPanel);

        pathfinder = new Pathfinder(35, 25);
        PathfinderNode solution = pathfinder.AStarSearch(1, 1, 25, 20);

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

    private class GridPanel extends JPanel {

        Tile [][] tiles;

        GridPanel () {
            setLayout(new GridLayout(25, 35, 1, 1));
            setBackground(Color.BLACK);
            setBorder(new EmptyBorder(2, 2, 2, 2));

            tiles = new Tile[35][25];
            for (int y = 0; y < 25; y++)
                for (int x = 0; x < 35; x++) {
                    tiles[x][y] = new Tile();
                    add(tiles[x][y]);
                }
        }

        private class Tile extends JPanel {

            Tile() {
                setPreferredSize(new Dimension(15, 15));
                setBackground (Color.WHITE);
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
