package com.company;

import java.util.ArrayList;
import java.util.Stack;

public class CDG {
    static boolean plus=true;
    static String sort="m";

    public static ArrayList<ArrayList<Integer>> run(Graph graphi,String sorti,boolean plusi){

        sort=sorti;
        plus=plusi;


        //algorithm for sort type and plus and graph in one class :-)  !!!!

        ArrayList<Integer> range=new ArrayList<>();
        for (int i = 0; i < graphi.nodes.length ; i++) {
            range.add(i);
        }

        CDGMethod(graphi,range,0);
        ArrayList<ArrayList<Integer>> comm=foundCommunities(graphi,range);

        return comm;
    }

    public static void CDGMethod(Graph graph, ArrayList<Integer> range,int counter){
        if (counter>0){
            if (graph.isAlmostComplete(range)){
                return;
            }
        }

        boolean isBroken=false;
        int tries=0;
        int edges=graph.edgeCounter(range);
        Graph graph2=graph.clone();
        while (!isBroken){
            EdgePoint[] edgePoints=graph.edgePoints(range);
            switch (sort){
                case "m":
                    graph.MergeSort(edgePoints,0,edgePoints.length-1);
                    break;
                case "i":
                    graph.insertionSort(edgePoints);
                    break;
                case "q":
                    graph.quickSort(edgePoints,0,edgePoints.length-1);;
                    break;
                default:
                    graph.MergeSort(edgePoints,0,edgePoints.length-1);;
                    break;
            }
            boolean check = false;
            ArrayList<Integer> unvisited;
            for (int i = 0; i <howManyMin(edgePoints) ; i++) {
                if(isCuttingEdge(graph,edgePoints[i].i,edgePoints[i].j,range)){
                    graph.deleteEdge(edgePoints[i].i,edgePoints[i].j);
                    ArrayList<Integer> tempRange= (ArrayList<Integer>) range.clone();
                    ArrayList<Integer> tempVisited = graph.DFS(tempRange, tempRange.get(0));
                    if (plus & (tempVisited.size() < 3 || tempRange.size() < 3)){
                        graph.addEdge(edgePoints[i].i,edgePoints[i].j);
                        return;
                    }
                    counter++;
                    isBroken=true;
                    break;
                }
            }
            if (!isBroken){
                boolean getOneCut=false;
                for (int i = 0; i <howManyMin(edgePoints) ; i++) {
                    if(isCuttingVertex(graph,edgePoints[i].i,edgePoints[i].j,range)){
                        graph.deleteEdge(edgePoints[i].i,edgePoints[i].j);
                        getOneCut=true;
                        break;
                    }
                }
                if (!getOneCut)
                    graph.deleteEdge(edgePoints[0].i,edgePoints[0].j);
                    tries++;
                    if (tries>=edges/1.5){
                        graph=graph2;
                        return;
                    }
            }

        }
        ArrayList<Integer> wanted= (ArrayList<Integer>) range.clone();
        ArrayList<Integer> visited=graph.DFS(wanted,wanted.get(0));

        if (!plus ) {
            return;
        }

        CDGMethod(graph,wanted,counter);
        CDGMethod(graph,visited,counter);

    }

    public static ArrayList<ArrayList<Integer>> foundCommunities(Graph graph,ArrayList<Integer> unvisited){
        ArrayList<ArrayList<Integer>> comm=new ArrayList<ArrayList<Integer>>();
        while(unvisited.size()!=0){
            comm.add(graph.DFS(unvisited,unvisited.get(0)));
        }
        return comm;
    }

    public static boolean isGettingCut(Graph graph,int from,int to,ArrayList<Integer> range){
        boolean fromTo=false;
        ArrayList<Integer> unvisited= (ArrayList<Integer>) range.clone();
        unvisited.remove((Object)from);
        ArrayList<Integer> visited=graph.DFS(unvisited,to);
        if (unvisited.size()!=0){
            return true;
        }
        return false;
    }

    public static boolean isCuttingEdge(Graph graph,int from,int to,ArrayList<Integer> range){
        ArrayList<Integer> newRange= (ArrayList<Integer>) range.clone();
        newRange.remove((Object)from);
        ArrayList<Integer> childrenOf=graph.getChildsOf(from,range);
        childrenOf.remove((Object)to);
        Stack<Integer> stack=new Stack<>();
        for (int i = 0; i <childrenOf.size() ; i++) {
            if (range.contains((Object)childrenOf.get(i))){
                stack.push(childrenOf.get(i));
                newRange.remove((Object)childrenOf.get(i));
            }
        }
        while (!stack.isEmpty()){
            int p=stack.pop();
            childrenOf=graph.getChildsOf(p,newRange);
            for (int i = 0; i <childrenOf.size() ; i++) {
                if (childrenOf.get(i)==to){
                    return false;
                }
                stack.push(childrenOf.get(i));
                newRange.remove((Object)childrenOf.get(i));
            }

        }

        return true;
    }

    public static boolean isCuttingVertex(Graph graph,int from,int to,ArrayList<Integer> range){
        if (isGettingCut(graph,from,to,range) || isGettingCut(graph,to,from,range)){
            return true;
        }
        return false;
    }

    public static int howManyMin(EdgePoint[] edgePoints){
        int counter=0;
        EdgePoint min=edgePoints[0];
        for (int i = 0; i <edgePoints.length ; i++) {
            if (edgePoints[i].point.equals(min.point)){
                counter++;
            }
        }
        return counter;
    }

}
