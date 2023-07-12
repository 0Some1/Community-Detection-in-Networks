package com.company;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Graph{
    int v;
    int currentNodeSize = 0;
    int edgesNum = 0;
    Integer[] nodes;

    public Graph(int size) {
        v = size;
        nodes =  new Integer[v];
    }

    public abstract void addNode(Object e);

    public abstract void deleteNode(Object e);

    public abstract void addEdge(Object e1, Object e2);

    public abstract void deleteEdge(Object e1, Object e2);

    public abstract EdgePoint[] edgePoints(ArrayList<Integer> range);

    public abstract ArrayList<Integer> DFS(ArrayList<Integer> unvisited, int start);

    public boolean isExist(int p,ArrayList<Integer> list){
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i)==p){
                return true;
            }
        }
        return false;
    }

    public void insertionSort(EdgePoint[] edgePoints) {
       
    }

    private void merge(EdgePoint[] edgesPoints, int l, int m, int r) {


    }

    public void MergeSort(EdgePoint[] edgePoints, int l, int r) {

    }

    private int partition(EdgePoint[] edgePoints, int low, int high) {
        return 0;
    }

    public void quickSort(EdgePoint[] edgePoints, int low, int high) {

    }

    public abstract ArrayList<Integer> getChildsOf(int node);

    public ArrayList<Integer> getChildsOf(int node, ArrayList<Integer> range){
        ArrayList<Integer> childrenOf=this.getChildsOf(node);

        int i=0;
        while (childrenOf.size()>0){
            if (i>=childrenOf.size()){
                break;
            }
            if (!range.contains(childrenOf.get(i))){
                childrenOf.remove(i);
            }else {
                i++;
            }
        }

        return childrenOf;
    }

    public abstract boolean isAlmostComplete(ArrayList<Integer> range);

    protected EdgePoint[] newEdgePoint(EdgePoint[] edgePoints) {
        int c=0;
        while ( c<edgePoints.length && edgePoints[c] != null){
            c++;
        }
        EdgePoint[] edgePoints1=new EdgePoint[c];
        for (int i = 0; i <c ; i++) {
            edgePoints1[i]=edgePoints[i];
        }

        return edgePoints1;
    }


    public abstract int  edgeCounter(ArrayList<Integer> range);

    protected EdgePoint min(EdgePoint[] edgePoints){
        EdgePoint min=edgePoints[0];
        for (int i = 0; i <edgePoints.length ; i++) {
            if (min.point>edgePoints[i].point){
                min=edgePoints[i];
            }
        }
        return min;
    }

    public abstract Graph clone();

}
