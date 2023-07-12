package com.company;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class CreateGraph {
    static Game[] games=new Game[20];
    static Client[] clients=new Client[10];
    static MGraph mGraph=new MGraph(games.length);
    static Scanner scanner=new Scanner(System.in);

    public static void run(){
        addALL();
        makeGraph(mGraph);
        boolean isLogin=false;
        while (!isLogin){
            Main.clearScreen();
            System.out.println("Enter your username:");
            String username=scanner.next();
            System.out.println("Enter your password:");
            String pass=scanner.next();
            for (int i = 0; i <clients.length ; i++) {
                if (clients[i].username.equals(username) && clients[i].password.equals(pass)){
                    isLogin=true;
                    showProfile(clients[i]);
                    break;
                }
            }
        }

    }

    private static void showProfile(Client client) {
        Main.clearScreen();
        System.out.println("--------------------- "+client.username+" ------------------------");
        showAllGames();
        int input=Main.inputAnInt(0,games.length-1);
        showGame(input,games[input],client);


    }


    public static void showAllGames(){
        for (int i = 0; i <games.length ; i++) {
            System.out.println(i+": "+games[i].name);
        }
    }

    public static void showGame(int input,Game game,Client client){
        Main.clearScreen();
        System.out.println("name : "+game.name);
        System.out.println("Publisher :"+game.programmerName);
        System.out.println("*.Back To Profile");
        if(!isDownloaded(game,client)){
            System.out.println("!.Download The app");
        }else {
            System.out.println("!.The app is Downloaded");
        }
        System.out.println("#.Rate The app");
        System.out.println("\n");
        ArrayList<Integer> related=showRelated(game,input);
        String s=scanner.next();

        if (s.equals("*")){
            showProfile(client);
            return;
        }else if (s.equals("!")&&!isDownloaded(game,client)){
            client.installedGame.add(game);
            showGame(input,game,client);
        }else if (s.equals("#")&&isDownloaded(game,client)){
            addRate(game);
            showGame(input,game,client);
        }else if (s.equals("$")){
            run();
            return;
        }else {
            Character character=s.charAt(0);
            if (Character.isDigit(character)){
                int index=Character.getNumericValue(character);
                showGame(related.get(index),games[related.get(index)],client);
            }

            showProfile(client);

        }



    }
    public static ArrayList<Integer> showRelated(Game game, int i){
        ArrayList<Integer> range=new ArrayList<>();
        for (int j = 0; j <mGraph.v ; j++) {
            range.add(j);
        }
        CDG.CDGMethod(mGraph,range,0);
        ArrayList<ArrayList<Integer>> communities=CDG.foundCommunities(mGraph,range);
        System.out.println();
//        for (int h = 0; h <communities.size() ; h++) {
//            for (int j = 0; j <communities.get(h).size() ; j++) {
//                System.out.println(communities.get(h).get(j));
//            }
//            System.out.println();
//        }
        ArrayList<Integer> related=new ArrayList<>();
        int counter=0;
        for (int j = 0; j <communities.size() ; j++) {
            if (communities.get(j).contains(i)){
                for (int k = 0; k <communities.get(j).size() ; k++) {
                    if(communities.get(j).get(k)!=i){
                        System.out.println(counter+": "+games[communities.get(j).get(k)].name);
                        related.add(communities.get(j).get(k));
                        counter++;
                    }
                }
            }
        }

        return related;

    }

    private static boolean isDownloaded(Game game,Client client) {
        return client.installedGame.contains(game);
    }

    public static void addALL(){
        Random random=new Random();
        for (int i = 0; i <5 ; i++) {
            games[i]=new Game("strategy "+i,random.nextFloat()+4,(float)random.nextInt(2),(float)random.nextInt(2),(float)random.nextInt(2));
        }
        for (int i = 5; i <10 ; i++) {
            games[i]=new Game("sport "+i,(float)random.nextInt(2),random.nextFloat()+4,(float)random.nextInt(2),(float)random.nextInt(2));

        }
        for (int i = 10; i <15 ; i++) {
            games[i]=new Game("educational "+i,(float)random.nextInt(2),(float)random.nextInt(2),random.nextFloat()+4,(float)random.nextInt(2));

        }
        for (int i =15; i <20; i++) {
            games[i]=new Game("adventure "+i,(float)random.nextInt(2),(float)random.nextInt(2),(float)random.nextInt(2),random.nextFloat()+4);
        }

        for (int i = 0; i <clients.length ; i++) {
            clients[i]=new Client("user"+i,"1234");
        }

    }

    public static void makeGraph(Graph graph){
        for (int i = 0; i <graph.nodes.length ; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i <games.length ; i++) {
            for (int j = i+1; j <games.length ; j++) {
                if (isGameConnected(games[i],games[j])){
                    graph.addEdge(i,j);
                }
            }
        }
    }

    public static void addRate(Game game){
        Main.clearScreen();
        float a,b,c,d;
        boolean isvalid=true;
        do {
            System.out.println("stratgy point:");
            a=scanner.nextFloat();
            System.out.println("sport point:");
             b=scanner.nextFloat();
            System.out.println("education point:");
            c=scanner.nextFloat();
            System.out.println("Adventure point:");
             d=scanner.nextFloat();

            if (a>5||b>5||c>5||d>5){
                isvalid=false;
                System.out.println("is not valid");
            }
        }while (!isvalid);

        game.strategy=a;
        game.sports=b;
        game.education=c;
        game.adventure=d;

        mGraph=new MGraph(games.length);
        makeGraph(mGraph);


    }

    public static boolean isGameConnected(Game game1,Game game2){
        float a=game1.adventure-game2.adventure;
        float b=game1.sports-game2.sports;
        float c=game1.strategy-game2.strategy;
        float d=game1.education-game2.education;
        float sum = (float) Math.pow(a,2)+(float) Math.pow(b,2)+(float) Math.pow(c,2)+(float) Math.pow(d,2);
        float sqrt = (float) Math.sqrt(sum);
        if (sqrt<2.5){
            return true;
        }else {
            return false;
        }
    }
}
