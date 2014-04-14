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
        passable = true;
        open = false;
        visited = false;
    }
}
