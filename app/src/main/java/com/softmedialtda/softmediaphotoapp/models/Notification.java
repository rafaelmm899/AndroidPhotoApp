package com.softmedialtda.softmediaphotoapp.models;

/**
 * Created by Agustin on 3/4/2018.
 */

public class Notification {

    private int id;
    private String subject;
    private String content;
    private String date;
    private String hour;
    private int viewed;
    private String sender;

    public Notification() {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.date = date;
        this.hour = hour;
        this.viewed = viewed;
        this.sender = sender;
    }

    public Notification(int id, String subject, String content, String date, String hour, int viewed, String sender) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.date = date;
        this.hour = hour;
        this.viewed = viewed;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
