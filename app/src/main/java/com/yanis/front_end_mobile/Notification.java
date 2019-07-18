package com.yanis.front_end_mobile;

public class Notification {
    String event_name;
    String user_email;

    public Notification(String event_name, String user_email) {
        this.event_name = event_name;
        this.user_email = user_email;
    }

    public Notification() {
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
