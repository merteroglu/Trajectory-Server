package com.merteroglu.Trajectory.Util;

import com.merteroglu.Trajectory.Model.Coordinate;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class KdTree {

    private KdNode root;
    private int size;
    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);

    public KdTree(){
        size = 0;
        root = null;
    }

    public boolean contains(final Coordinate p){
        return contains(root,p.getLatitude(),p.getLongitude());
    }

    private boolean contains(KdNode node, double x , double y){
        if(node == null) return false;
        if(node.getX() == x && node.getY() == y) return true;

        if(node.isVertical() && x < node.getX() || !node.isVertical() && y < node.getY()){
            return contains(node.getLeft(),x,y);
        }else{
            return contains(node.getRight(),x,y);
        }
    }

    private KdNode insert(final KdNode node , final Coordinate p,final boolean vertical){

        if(node == null){
            size++;
            return new KdNode(p.getLatitude(),p.getLongitude(),null,null,vertical);
        }

        if(node.getX() == p.getLatitude() && node.getY() == p.getLongitude()) return node;

        if(node.isVertical() && p.getLatitude() < node.getX() || !node.isVertical() && p.getLongitude() < node.getY()){
            node.setLeft(insert(node.getLeft(),p,!node.isVertical()));
        }else{
            node.setRight(insert(node.getRight(),p,!node.isVertical()));
        }

        return node;
    }

    public void insert(final Coordinate p){
        root = insert(root,p,true);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private RectHV leftRect(final RectHV rect,final KdNode node){
        if(node.isVertical()){
            return new RectHV(rect.xmin(),rect.ymin(),node.getX(),rect.ymax());
        }else{
            return new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),node.getY());
        }
    }

    private RectHV rightRect(final RectHV rect,final KdNode node){
        if(node.isVertical()){
            return new RectHV(node.getX(),rect.ymin(),rect.xmax(),rect.ymax());
        }else{
            return new RectHV(rect.xmin(),node.getY(),rect.xmax(),rect.ymax());
        }
    }

    public int size(){
        return size;
    }

    private void range(final KdNode node,final RectHV nrect,final RectHV rect,final Queue<Coordinate> queue){
        if(node == null) return;

        if (rect.intersects(nrect)){
            final Coordinate p = new Coordinate(node.getX(),node.getY());
            if(rect.contains(p)) queue.add(p);
            range(node.getLeft(),leftRect(nrect,node),rect,queue);
            range(node.getRight(),rightRect(nrect,node),rect,queue);
        }

    }

    public Iterable<Coordinate> range(final  RectHV rect){
        final Queue<Coordinate> queue = new LinkedList<>();
        range(root,CONTAINER,rect,queue);
        return queue;
    }



}
