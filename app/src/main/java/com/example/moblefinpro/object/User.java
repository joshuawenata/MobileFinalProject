package com.example.moblefinpro.object;

public class User {
    private String email;
    private String username;
    private Exercise exercise;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
