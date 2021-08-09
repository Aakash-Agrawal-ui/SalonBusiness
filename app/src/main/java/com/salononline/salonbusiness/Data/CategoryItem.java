package com.salononline.salonbusiness.Data;

public class CategoryItem {

    private String category_uid;
    private String category_name;

    public CategoryItem(String category_name) {
        this.category_name = category_name;
    }

    public CategoryItem(String category_uid, String category_name) {
        this.category_uid = category_uid;
        this.category_name = category_name;
    }

    public String getCategory_uid() {
        return category_uid;
    }

    public void setCategory_uid(String category_uid) {
        this.category_uid = category_uid;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
