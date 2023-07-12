package com.company;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class TimeAndChart {
    static MGraph matrixG;
    static LGraph listG;

    public static void run(){
        Main.clearScreen();
        int size=21;
        int edgeNum=29;
        int nEdges=edgeNum;
        matrixG =new MGraph(size);
        listG =new LGraph(size);
        for (int i = 0; i <size ; i++) {
            matrixG.addNode(i);
            listG.addNode(i);
        }

        //-------------------------------------matrix-------------------------------------//
        ArrayList<ArrayList<Integer>> communities;
        long startTime;
        long endTime;

        makeGraphEdges(matrixG);
        startTime = System.nanoTime();
        CDG.run(matrixG,"i",false);
        endTime = System.nanoTime();
        long mif=(endTime - startTime);

        makeGraphEdges(matrixG);
        startTime = System.nanoTime();
        communities=CDG.run(matrixG,"q",false);
        endTime = System.nanoTime();
        long mqf=(endTime - startTime);

        makeGraphEdges(matrixG);
        startTime = System.nanoTime();
        CDG.run(matrixG,"m",false);
        endTime=System.nanoTime();
        long mmf=(endTime - startTime);


        makeGraphEdges(matrixG);
        startTime = System.nanoTime();
        CDG.run(matrixG,"m",true);
        endTime = System.nanoTime();
        long mmt=(endTime - startTime);

        makeGraphEdges(matrixG);
        startTime = System.nanoTime();
        CDG.run(matrixG,"i",true);
        endTime = System.nanoTime();
        long mit=(endTime - startTime);

        makeGraphEdges(matrixG);
        startTime = System.nanoTime();
        CDG.run(matrixG,"q",true);
        endTime = System.nanoTime();
        long mqt=(endTime - startTime);

        //-------------------------------------list-------------------------------------//


        makeGraphEdges(listG);
        startTime = System.nanoTime();
        CDG.run(listG,"m",false);
        endTime = System.nanoTime();
        long lmf=(endTime - startTime);

        makeGraphEdges(listG);
        startTime = System.nanoTime();
        CDG.run(listG,"i",false);
        endTime = System.nanoTime();
        long lif=(endTime - startTime);

        makeGraphEdges(listG);
        startTime = System.nanoTime();
        CDG.run(listG,"q",false);
        endTime = System.nanoTime();
        long lqf=(endTime - startTime);

        makeGraphEdges(listG);
        startTime = System.nanoTime();
        CDG.run(listG,"m",true);
        endTime = System.nanoTime();
        long lmt=(endTime - startTime);

        makeGraphEdges(listG);
        startTime = System.nanoTime();
        CDG.run(listG,"i",true);
        endTime = System.nanoTime();
        long lit=(endTime - startTime);

        makeGraphEdges(listG);
        startTime = System.nanoTime();
        CDG.run(listG,"q",true);
        endTime = System.nanoTime();
        long lqt=(endTime - startTime);


    //-------size-----------------//


        char[] chars={'A','B','C','D','E','F','G','H','I'};
        for (int i = 0; i <communities.size() ; i++) {
            for (int j = 0; j <communities.get(i).size() ; j++) {
                System.out.println(chars[i]+": "+communities.get(i).get(j));
            }
            System.out.println();
        }




        double listsize;
        listsize=listG.nodes.length*4+edgeNum*2*4;
        double matrixsize;
        matrixsize=matrixG.nodes.length*matrixG.nodes.length*4;



        String row = "Row";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(mmf, row, "Merge-Matrix");
        dataset.addValue(mqf, row, "Quick-Matrix");
        dataset.addValue(mif, row, "Insertion-matrix");
        dataset.addValue(mmt, row, "Merge-Matrix-plus");
        dataset.addValue(mqt, row, "Quick-Matrix-plus");
        dataset.addValue(mit, row, "Insertion-matrix-plus");
        dataset.addValue(lmf, row, "Merge-list");
        dataset.addValue(lqf, row, "Quick-list");
        dataset.addValue(lif, row, "Insertion-list");
        dataset.addValue(lmt, row, "Merge-list-plus");
        dataset.addValue(lqt, row, "Quick-list-plus");
        dataset.addValue(lit, row, "Insertion-list-plus");

        dataset.addValue(listsize, row, "heapSize-list");

        dataset.addValue(matrixsize, row, "heapSize-matrix");


        EventQueue.invokeLater(() -> {
            new BarChart().display(dataset);
        });


    }
    public static Graph makeGraphEdges(Graph graph){
        int[][] conn={{1,2},{3,14},{},{15,16,6,4},{5,6},{8,7,13},{13,15},{8},{},{},{11},{12,9},{20,10},{12},{15,17,18,3},{16},{},{},{19},{},{10}};

        for (int i = 0; i <conn.length ; i++) {
            for (int j = 0; j <conn[i].length ; j++) {
                graph.addEdge(i,conn[i][j]);
            }
        }
        return graph;
    }
}
