package com.kylejanssen.pathfinder;

public class PathfinderNode {
    int x;
    int y;
    int cost;
    int depth;
    boolean passable;
    boolean open;
    boolean visited;
    PathfinderNode parent;

    public PathfinderNode(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        depth = 0;
        passable = cost == 0 ? false : true;
        open = false;
        visited = false;
    }

    public void reset (int cost) {
        this.cost = cost;
        passable = cost == 0 ? false : true;
        open = false;
        visited = false;
    }
}
