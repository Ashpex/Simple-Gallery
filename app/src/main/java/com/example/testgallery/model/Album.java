package com.example.testgallery.model;

public class Album {
    public int resource;
    public String name;
    public int count;
    public Album(int resource, String name, int count) {
        this.count = count;
        this.name = name;
        this.resource = resource;
    }
}
