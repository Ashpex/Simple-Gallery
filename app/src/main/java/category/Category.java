package category;

import java.util.List;

import image.Image;

public class Category {
    private String nameCategory;
    private List<Image> listImage;

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Image> getListGirl() {
        return listImage;
    }

    public void setListGirl(List<Image> listImage) {
        this.listImage = listImage;
    }

    public Category(String nameCategory, List<Image> listImage) {
        this.nameCategory = nameCategory;
        this.listImage = listImage;
    }
}
