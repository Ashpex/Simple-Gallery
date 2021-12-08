package com.example.testgallery.models;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private String pathFolder;
    private Image img;
    private String name;
    private List<Image> listImage;
    public Album(Image img, String name) {
        this.name = name;
        this.img = img;
        listImage = new ArrayList<>();
    }

    public void setPathFolder(String pathFolder) {
        this.pathFolder = pathFolder;
    }

    public String getPathFolder() {
        return pathFolder;
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
    public void addList(List<Image> list) {
        listImage = new ArrayList<>(list);
    }
    public void addItem(Image img) {
        listImage.add(img);
    }
}
