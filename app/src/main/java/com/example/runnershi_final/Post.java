package com.example.runnershi_final;

public class Post {
    String title;
    String content;
    String id;
    int tag;
    int profile;

    public Post() {
    }

    public Post(String title, String content, String id, int tag) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
