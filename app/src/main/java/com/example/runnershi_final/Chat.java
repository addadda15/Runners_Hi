package com.example.runnershi_final;

public class Chat {
    private String Id;
    private String Content;
    private int type;

    public Chat() {

    }

    public Chat(String id, String content) {
        Id = id;
        Content = content;
        this.type = 0;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
