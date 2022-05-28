package com.example.runnershi_final;

public class Member {
    //public int iv_member;
    public String member;
    int profile;

    Member(){}

    public Member(String member, int profile) {
        this.member = member;
        this.profile = profile;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
