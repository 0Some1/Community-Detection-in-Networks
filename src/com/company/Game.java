package com.company;

public class Game {
    String name;
    String programmerName="Someone";
    float strategy=0;
    float sports=0;
    float education=0;
    float adventure=0;

    public Game(String name, Float strategy, Float sports, Float education, Float adventure) {
        this.name = name;
        this.strategy = strategy;
        this.sports = sports;
        this.education = education;
        this.adventure = adventure;
    }
}
