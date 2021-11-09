package com.example.testgallery.models;

import java.util.List;

public class Album {
    private Image img;
    private String name;
    private List<Image> listImage;
    public Album(Image img, String name, List<Image> listImage) {
        this.listImage = listImage;
        this.name = name;
        this.img = img;
    }

    public Image getImg() {
        return img;
    }
    public String getName() {
        return name;
    }
    public List<Image> getList() {
        return listImage;
    }
}
