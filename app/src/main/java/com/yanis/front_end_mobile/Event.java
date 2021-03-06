package com.yanis.front_end_mobile;

public class Event {
    private String id;
    private String name;

    public Event(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Event(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
