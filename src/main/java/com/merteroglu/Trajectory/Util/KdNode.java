package com.merteroglu.Trajectory.Util;

public class KdNode {
    private KdNode left;
    private KdNode right;
    private boolean vertical;
    private double x;
    private double y;

    public KdNode( double x, double y, KdNode left, KdNode right, boolean vertical) {
        this.left = left;
        this.right = right;
        this.vertical = vertical;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isVertical() {
        return vertical;
    }

    public KdNode getLeft() {
        return left;
    }

    public KdNode getRight() {
        return right;
    }

    public void setLeft(KdNode left) {
        this.left = left;
    }

    public void setRight(KdNode right) {
        this.right = right;
    }
}
