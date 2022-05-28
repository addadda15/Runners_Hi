package com.example.runnershi_final;

public class LoginMember {
    String id, pw;
    int age, height, weight, time, goal;
    double dis;

    LoginMember() {
    }

    public LoginMember(String id, String pw, int age, int height, int weight, double dis, int time, int goal) {
        this.id = id;
        this.pw = pw;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.dis = dis;
        this.time = time;
        this.goal = goal;
    }

    public LoginMember(String id, String pw, int goal) {
        this.id = id;
        this.pw = pw;
        this.age = 0;
        this.height = 0;
        this.weight = 0;
        this.dis = 0;
        this.time = 0;
        this.goal = goal;
    }

    public LoginMember(String id, String pw) {
        this.id = id;
        this.pw = pw;
        this.age = 0;
        this.height = 0;
        this.weight = 0;
        this.dis = 0;
        this.time = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public double getDis() {
        return dis;
    }

    public void setDis(double dis) {
        this.dis = dis;
    }
}
