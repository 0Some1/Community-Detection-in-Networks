package com.company;

import java.util.ArrayList;

public class Client {
    String username;
    String password;
    ArrayList<Game> installedGame=new ArrayList<>();

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
