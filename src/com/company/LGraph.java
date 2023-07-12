package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class LGraph extends Graph {
    ArrayList<Integer>[] edges;

    public LGraph(int size){
        super(size);
        edges=new ArrayList[v];
        for (int i = 0; i <edges.length ; i++) {
            edges[i]=new ArrayList<>();
        }
    }

    public void addEdge(Object e1 ,Object e2){
        int e1i=findIndex(e1);
        int e2i=findIndex(e2);
        if (e1i==-1 || e2i==-1){
            return;
        }
        /* set in list */
        edges[e1i].add(e2i);
        edges[e2i].add(e1i);
        edgesNum++;

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
        edges[index].clear();
        for (int i = 0; i <edges.length ; i++) {
            if (edges[i].contains(index)){
                edges[i].remove(index);
                edgesNum--;
            }

        }

        currentNodeSize--;

    }

    public void deleteEdge(Object e1 , Object e2){
        int e1i=findIndex(e1);
        int e2i=findIndex(e2);
        if (e1i==-1 || e2i==-1){
            return;
        }
        //delete from list
        edges[e1i].remove((Object)e2i);
        edges[e2i].remove((Object)e1i);
        edgesNum--;

    }

    private int findIndex(Object e){
        for (int i = 0; i <nodes.length ; i++) {
            if (nodes[i]==e){
                return i;
            }
        }
        return -1;
    }

    public EdgePoint[] edgePoints(ArrayList<Integer> range){
        EdgePoint[] edgePoints=new EdgePoint[edgesNum];
        int index=0;
        for (int i = 0; i <edges.length ; i++) {
            for (int j = 0; j <edges[i].size() ; j++) {
                if (edges[i].get(j)>i && range.contains(i) && range.contains(edges[i].get(j))){
                    float cijP=cij(i,(Integer) edges[i].get(j))+1;
                    float min=Math.min(k(i),k((Integer) edges[i].get(j)))-1;
                    float point;
                    if (min>0){
                        point=cijP/min;
                    }else {
                        point=Float.MAX_VALUE;
                    }
                    edgePoints[index]=new EdgePoint(i,edges[i].get(j),point);
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
        unvisited.remove((Object)start);
        visited.add(start);
        Stack<Integer> stack=new Stack<>();
        stack.push(start);
        while (!stack.empty()){
            int node=stack.pop();
            for (int i = 0; i <edges[node].size() ; i++) {
                if (isExist(edges[node].get(i),unvisited)){
                    stack.push(edges[node].get(i));
                    visited.add(edges[node].get(i));
                    unvisited.remove((Object)edges[node].get(i));
                }
            }
        }
        return visited;
    }

    @Override
    public ArrayList<Integer> getChildsOf(int node) {
        ArrayList<Integer> childs=new ArrayList<>();
        childs= (ArrayList<Integer>) edges[node].clone();
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
        if (edgeCounter>(max)/2 || min(edgePoints).point>1){
            return true;
        }
        return false;
    }

    public int edgeCounter(ArrayList<Integer> range){
        int edgeCounter=0;
        for (int i = 0; i <range.size() ; i++) {
            for (int j = 0; j <edges[range.get(i)].size() ; j++) {
                if (range.contains(edges[range.get(i)].get(j)) && edges[range.get(i)].get(j)>range.get(i) && range.contains(i)){
                    edgeCounter++;
                }
            }
        }
        return edgeCounter;
    }
    @Override
    public Graph clone() {
        LGraph graph=new LGraph(v);
        ArrayList<Integer>[] arrayLists=new ArrayList[v];
        for (int i = 0; i <edges.length ; i++) {
            arrayLists[i]= (ArrayList<Integer>) edges[i].clone();
        }
        graph.edges=arrayLists;
        return graph;
    }


    private int cij(int i,int j){
        int counter=0;
        for (int k = 0; k <edges[i].size() ; k++) {
            for (int l = 0; l <edges[j].size() ; l++) {
                if (edges[i].get(k).equals(edges[j].get(l))){
                    counter++;
                }
            }
        }
        return counter;
    }

    private int k(int i){
        return edges[i].size();
    }




}
