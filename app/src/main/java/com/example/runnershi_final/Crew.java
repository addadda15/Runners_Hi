package com.example.runnershi_final;

public class Crew {

    public String name;
    public String intro;
    public String recruit; // 0일때 모집x, 1일때 모집중
    public String leaderID;
    //public String member;

    public Crew() {
    }

    public Crew(String name, String intro, String recruit, String leaderID) {
        this.name = name;
        this.intro = intro;
        this.recruit = recruit;
        this.leaderID = leaderID;
    }

    public String getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(String leaderID) {
        this.leaderID = leaderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getRecruit() {
        return recruit;
    }

    public void setRecruit(String recruit) {
        this.recruit = recruit;
    }
}