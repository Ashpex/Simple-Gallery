package category;

import java.util.List;

import girl.Girl;

public class Category {
    private String nameCategory;
    private List<Girl> listGirl;

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Girl> getListGirl() {
        return listGirl;
    }

    public void setListGirl(List<Girl> listGirl) {
        this.listGirl = listGirl;
    }

    public Category(String nameCategory, List<Girl> listGirl) {
        this.nameCategory = nameCategory;
        this.listGirl = listGirl;
    }
}
