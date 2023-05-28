package com.example.moblefinpro;

public class Item {

    String name;
    String reps;
    String duration;

    public Item(String name, String reps,  String duration) {
        this.name = name;
        this.reps = reps;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String email) {
        this.reps = email;
    }

    public String getduration() {
        return duration;
    }

    public void setduration(String email) {
        this.duration = email;
    }
}