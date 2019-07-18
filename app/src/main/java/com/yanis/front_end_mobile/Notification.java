package com.yanis.front_end_mobile;

public class Notification {
    String event_name;
    String user_id;

    public Notification(String event_name, String user_id) {
        this.event_name = event_name;
        this.user_id = user_id;
    }

    public Notification() {
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
