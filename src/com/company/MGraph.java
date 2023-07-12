package com.company;

import java.util.ArrayList;
import java.util.Stack;

public class MGraph extends Graph  {
    int[][] matrixEdges;

    public MGraph(int size){
        super(size);
        matrixEdges=new int[v][v];
    }

    public void addNode(Object e){
        if(currentNodeSize >=v){
            throw new ArrayIndexOutOfBoundsException();
        }
        nodes[currentNodeSize]= (Integer) e;
        currentNodeSize++;
    }


    public void deleteNode(Object e){
        int index=findIndex(e);
        if (index==-1){
            return;
        }
        nodes[index]=null;

        for (int i = 0; i <matrixEdges.length ; i++) {
            for (int j = 0; j <matrixEdges[i].length ; j++) {
                if (i==index || j==index){
                    matrixEdges[i][j]=0;
                    edgesNum--;
                }
            }
        }

        currentNodeSize--;

    }

    public void addEdge(Object e1 , Object e2) {
        int e1i = findIndex(e1);
        int e2i = findIndex(e2);
        if (e1i == -1 || e2i == -1) {
            return;
        }
        //set edge in matrix
        matrixEdges[e1i][e2i]=1;
        matrixEdges[e2i][e1i]=1;
        edgesNum++;
    }

    public void deleteEdge(Object e1 , Object e2){
        int e1i=findIndex(e1);
        int e2i=findIndex(e2);
        if (e1i==-1 || e2i==-1){
            return;
        }
        //delete from matrix
        matrixEdges[e1i][e2i]=0;
        matrixEdges[e2i][e1i]=0;
        edgesNum--;

    }

    @Override
    public EdgePoint[] edgePoints(ArrayList<Integer> range) {
        EdgePoint[] edgePoints=new EdgePoint[edgesNum];
        int index=0;
        for (int i = 0; i <matrixEdges.length ; i++) {
            for (int j = i; j <matrixEdges[i].length ; j++) {
                if (matrixEdges[i][j]==1 && range.contains(i) && range.contains(j)){
                    float cijP=cij(i, j)+1;
                    float min=Math.min(k(i),k(j))-1;
                    float point;
                    if (min>0){

                        point=cijP/min;
                    }else {
                        point=Float.MAX_VALUE;
                    }
                    edgePoints[index]=new EdgePoint(i,j,point);
                    index++;
                }
            }
        }
        edgePoints=newEdgePoint(edgePoints);
        return edgePoints;
    }

    @Override
    public ArrayList<Integer> DFS(ArrayList<Integer> unvisited, int start) {
        ArrayList<Integer> visited = new ArrayList<>();
        unvisited.remove((Object) start);
        visited.add(start);
        Stack<Integer> stack=new Stack<>();
        stack.push(start);
        while (!stack.empty()){
            int node=stack.pop();
            for (int i = 0; i <matrixEdges[node].length ; i++) {
                if (matrixEdges[node][i]==1){
                    if (isExist(i,unvisited)){
                        stack.push(i);
                        visited.add(i);
                        unvisited.remove((Object) i);
                    }
                }

            }
        }
        return visited;
    }

    @Override
    public ArrayList<Integer> getChildsOf(int node) {
        ArrayList<Integer> childs=new ArrayList<>();
        for (int i = 0; i <matrixEdges[node].length ; i++) {
            if (matrixEdges[node][i]==1){
                childs.add(i);
            }
        }
        return childs;
    }


    @Override
    public boolean isAlmostComplete(ArrayList<Integer> range) {
        if (range.size()<4){
            return true;
        }
        int edgeCounter=edgeCounter(range);
        int rangeI=range.size();
        int max=rangeI*(rangeI-1)/2;
        EdgePoint[] edgePoints=this.edgePoints(range);
        if (edgeCounter>(max)/2 || min(edgePoints).point > 1){
            return true;
        }
        return false;
    }

    public int edgeCounter(ArrayList<Integer> range){
        int edgeCounter=0;
        for (int i = 0; i <range.size() ; i++) {
            for (int j = 0; j <matrixEdges[range.get(i)].length ; j++) {
                if (matrixEdges[range.get(i)][j]==1 && range.contains(j) && j>range.get(i)){
                    edgeCounter++;
                }
            }
        }
        return edgeCounter;
    }

    @Override
    public Graph clone() {
        MGraph graph=new MGraph(this.v);
        for (int i = 0; i <v ; i++) {
            graph.addNode(i);
        }
        int[][] ints=new int[v][v];
        for (int i = 0; i <matrixEdges.length ; i++) {
            for (int j = 0; j <matrixEdges[i].length ; j++) {
                ints[i][j]=matrixEdges[i][j];
            }
        }
        graph.matrixEdges=ints;
        return graph;
    }


    private int k(int i) {
        int counter = 0;
        for (int j = 0; j <matrixEdges[i].length ; j++) {
            if (matrixEdges[i][j]==1){
                counter++;
            }
        }
        return counter;
    }

    private float cij(int i, int j) {
        float counter=0;
        if (matrixEdges[i][j]==1){
            for (int k = 0; k <matrixEdges[i].length ; k++) {
                if (matrixEdges[i][k]==1 && matrixEdges[j][k]==1){
                    counter++;
                }
            }
        }
        return counter;
    }


    private int findIndex(Object e){
        for (int i = 0; i <nodes.length ; i++) {
            if (nodes[i]==e){
                return i;
            }
        }
        return -1;
    }
}
