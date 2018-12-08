package com.example.user.LayoutClass;

public class Note {
    private String title = "";
    private String content = "";
    private String date = "0000-00-00";

    public Note(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getDate(){
        return date;
    }
}
