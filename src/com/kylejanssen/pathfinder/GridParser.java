package com.kylejanssen.pathfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GridParser {

    int[][] grid;

    public GridParser(String filename) {
        grid = new int[35][25];
        Scanner scanner;

        try {
            scanner = new Scanner(new File(filename));

            for (int y = 0; y < 25; y++)
                for (int x = 0; x < 35; x++) {
                    grid[x][y] = scanner.hasNextInt() ? scanner.nextInt() : 0;
                    System.out.println( x + ", " + y + ": " + grid[x][y]);
                }
        } catch (FileNotFoundException e) {
            ;
        }
    }

    int[][] getGrid () {
        return grid;
    }
}
