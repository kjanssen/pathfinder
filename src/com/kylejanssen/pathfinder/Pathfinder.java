package com.kylejanssen.pathfinder;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Pathfinder {

    PathfinderNode [][] grid;
    int xSize;
    int ySize;

    public Pathfinder (int x, int y, int[][] map) {

        xSize = x;
        ySize = y;
        grid = new PathfinderNode[xSize][ySize];

        for (int i = 0; i < xSize; i++)
            for (int j = 0; j < ySize; j++)
                grid[i][j] = new PathfinderNode(i, j, map[i][j]);
    }

    public void initialize (int[][] map) {
        for (int i = 0; i < xSize; i++)
            for (int j = 0; j < ySize; j++)
                grid[i][j].reset(map[i][j]);
    }

    public boolean inBounds(int x, int y) {
        return (x >= 0 && x < xSize && y >= 0 && y < ySize);
    }

    public PathfinderNode DepthFirstSearch (int startX, int startY, int goalX, int goalY) {

        // A deque that will be used as a stack
        ArrayDeque<PathfinderNode> open = new ArrayDeque<PathfinderNode>();

        PathfinderNode goal = grid[goalX][goalY];
        PathfinderNode curr = grid[startX][startY];
        curr.open = true;
        if (curr.passable)
            open.push(curr);

        while (!open.isEmpty()) {
            curr = open.pop();
            if (!curr.visited) {
                curr.visited = true;

                for (int i = -1; i < 2; i ++) {
                    for (int j = -1; j < 2; j++) {
                        if (((i == 0 && j != 0) || (i != 0 && j == 0)) &&
                                inBounds(curr.x + i, curr.y + j) &&
                                grid[curr.x + i][curr.y + j].passable &&
                                !grid[curr.x + i][curr.y + j].open) {
                            grid[curr.x + i][curr.y + j].open = true;
                            grid[curr.x + i][curr.y + j].parent = curr;
                            open.push(grid[curr.x + i][curr.y + j]);

                            if (grid[curr.x + i][curr.y + j] == goal)
                                return goal;
                        }
                    }
                }
            }
        }

        return null;
    }

    public PathfinderNode BreadthFirstSearch (int startX, int startY, int goalX, int goalY) {

        // a deque that will be used as a queue
        ArrayDeque<PathfinderNode> open = new ArrayDeque<PathfinderNode>();

        PathfinderNode goal = grid[goalX][goalY];
        PathfinderNode curr = grid[startX][startY];
        curr.open = true;
        if (curr.passable)
            open.add(curr);

        while (!open.isEmpty()) {
            curr = open.poll();
            if (!curr.visited) {
                curr.visited = true;

                for (int i = -1; i < 2; i ++) {
                    for (int j = -1; j < 2; j++) {
                        if (((i == 0 && j != 0) || (i != 0 && j == 0)) &&
                                inBounds(curr.x + i, curr.y + j) &&
                                grid[curr.x + i][curr.y + j].passable &&
                                !grid[curr.x + i][curr.y + j].open) {
                            grid[curr.x + i][curr.y + j].open = true;
                            grid[curr.x + i][curr.y + j].parent = curr;
                            open.add(grid[curr.x + i][curr.y + j]);

                            if (grid[curr.x + i][curr.y + j] == goal)
                                return goal;
                        }
                    }
                }
            }
        }

        return null;
    }

    public PathfinderNode DijkstraSearch (final int startX, final int startY, int goalX, int goalY) {

        // a priority queue that sorts by manhattan distance from the starting node
        PriorityQueue<PathfinderNode> open = new PriorityQueue<PathfinderNode>(100,
                new Comparator<PathfinderNode>() {
                    @Override
                    public int compare(PathfinderNode first, PathfinderNode second) {

                        int dist1 = Math.abs(first.x - startX) + Math.abs(first.y - startY);
                        int dist2 = Math.abs(second.x - startX) + Math.abs(second.y - startY);

                        if (dist1 < dist2) return -1;
                        if (dist1 > dist2) return 1;

                        return 0;
                    }
                });

        PathfinderNode goal = grid[goalX][goalY];
        PathfinderNode curr = grid[startX][startY];
        curr.open = true;
        if (curr.passable)
            open.add(curr);

        while (!open.isEmpty()) {
            curr = open.poll();
            if (!curr.visited) {
                curr.visited = true;

                for (int i = -1; i < 2; i ++) {
                    for (int j = -1; j < 2; j++) {
                        if (((i == 0 && j != 0) || (i != 0 && j == 0)) &&
                                inBounds(curr.x + i, curr.y + j) &&
                                grid[curr.x + i][curr.y + j].passable &&
                                !grid[curr.x + i][curr.y + j].open) {
                            grid[curr.x + i][curr.y + j].open = true;
                            grid[curr.x + i][curr.y + j].parent = curr;
                            open.add(grid[curr.x + i][curr.y + j]);

                            if (grid[curr.x + i][curr.y + j] == goal)
                                return goal;
                        }
                    }
                }
            }
        }

        return null;
    }

    public PathfinderNode BestFirstSearch (int startX, int startY, final int goalX, final int goalY) {

        // a priority queue that sorts by manhattan distance to the goal node.
        PriorityQueue<PathfinderNode> open = new PriorityQueue<PathfinderNode>(100,
                new Comparator<PathfinderNode>() {
                    @Override
                    public int compare(PathfinderNode first, PathfinderNode second) {

                        int dist1 = Math.abs(first.x - goalX) + Math.abs(first.y - goalY);
                        int dist2 = Math.abs(second.x - goalX) + Math.abs(second.y - goalY);

                        if (dist1 < dist2) return -1;
                        if (dist1 > dist2) return 1;

                        return 0;
                    }
                });

        PathfinderNode goal = grid[goalX][goalY];
        PathfinderNode curr = grid[startX][startY];
        curr.open = true;
        if (curr.passable)
            open.add(curr);

        while (!open.isEmpty()) {
            curr = open.poll();
            if (!curr.visited) {
                curr.visited = true;

                for (int i = -1; i < 2; i ++) {
                    for (int j = -1; j < 2; j++) {
                        if (((i == 0 && j != 0) || (i != 0 && j == 0)) &&
                                inBounds(curr.x + i, curr.y + j) &&
                                grid[curr.x + i][curr.y + j].passable &&
                                !grid[curr.x + i][curr.y + j].open) {
                            grid[curr.x + i][curr.y + j].open = true;
                            grid[curr.x + i][curr.y + j].parent = curr;
                            open.add(grid[curr.x + i][curr.y + j]);

                            if (grid[curr.x + i][curr.y + j] == goal)
                                return goal;
                        }
                    }
                }
            }
        }

        return null;
    }

    public PathfinderNode AStarSearch (int startX, int startY, final int goalX, final int goalY) {

        // a priority queue that sorts by manhattan distance to the goal node + the back cost.
        PriorityQueue<PathfinderNode> open = new PriorityQueue<PathfinderNode>(100,
                new Comparator<PathfinderNode>() {
                    @Override
                    public int compare(PathfinderNode first, PathfinderNode second) {

                        int f1 = Math.abs(first.x - goalX) + Math.abs(first.y - goalY) + first.depth;
                        int f2 = Math.abs(second.x - goalX) + Math.abs(second.y - goalY) + second.depth;

                        if (f1 < f2) return -1;
                        if (f1 > f2) return 1;
                        if (first.depth < second.depth) return -1;
                        if (first.depth > second.depth) return 1;

                        return 0;
                    }
                });

        PathfinderNode goal = grid[goalX][goalY];
        PathfinderNode curr = grid[startX][startY];
        curr.open = true;
        if (curr.passable)
            open.add(curr);

        while (!open.isEmpty()) {
            curr = open.poll();
            if (!curr.visited) {
                curr.visited = true;

                for (int i = -1; i < 2; i ++) {
                    for (int j = -1; j < 2; j++) {
                        if (((i == 0 && j != 0) || (i != 0 && j == 0)) &&
                                inBounds(curr.x + i, curr.y + j) &&
                                grid[curr.x + i][curr.y + j].passable &&
                                !grid[curr.x + i][curr.y + j].open) {
                            grid[curr.x + i][curr.y + j].open = true;
                            grid[curr.x + i][curr.y + j].parent = curr;
                            grid[curr.x + i][curr.y + j].depth = curr.depth + 1;
                            open.add(grid[curr.x + i][curr.y + j]);

                            if (grid[curr.x + i][curr.y + j] == goal)
                                return goal;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static void main (String [] args) {
        Pathfinder pathfinder = new Pathfinder(35, 25, new GridParser("assets/plain.grid").getGrid());
        PathfinderNode solution = pathfinder.DepthFirstSearch(1, 1, 33, 23);

        while (solution != null) {
            System.out.println("( " + solution.x + ", " + solution.y + ")");
            solution = solution.parent;
        }
    }
}
