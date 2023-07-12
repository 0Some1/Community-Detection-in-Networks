package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class InputForCDG {
    static MGraph mGraph;
    static LGraph lGraph;
    public static void run(boolean plus){
        Main.clearScreen();
        System.out.println("Enter num of vertex: ");
        int size=Main.inputAnInt(1,9999);
        System.out.println("Enter num of Edges: ");
        int nEdges=Main.inputAnInt(0,size*(size-1)/2);
        mGraph =new MGraph(size);
        lGraph =new LGraph(size);
        for (int i = 0; i <size ; i++) {
            mGraph.addNode(i);
            lGraph.addNode(i);
        }
        Scanner scanner=new Scanner(System.in);
        for (int i = 0; i <nEdges ; i++) {
            int from=scanner.nextInt();
            int to=scanner.nextInt();
            mGraph.addEdge(from,to);
            lGraph.addEdge(from,to);
        }

        char[] chars={'A','B','C','D','E','F','G','H','I'};
        ArrayList<ArrayList<Integer>> communities = CDG.run(lGraph,"m",plus);
        System.out.println();
        for (int i = 0; i <communities.size() ; i++) {
            for (int j = 0; j <communities.get(i).size() ; j++) {
                System.out.println(chars[i]+": "+communities.get(i).get(j));
            }
            System.out.println();
        }
    }
}
