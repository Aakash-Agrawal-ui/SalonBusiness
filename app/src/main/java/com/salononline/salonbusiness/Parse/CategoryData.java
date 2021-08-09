package com.salononline.salonbusiness.Parse;

public class CategoryData {
    private String categoryUuid,categoryName;

    public CategoryData() {
    }

    public CategoryData(String categoryUuid, String categoryName) {
        this.categoryUuid = categoryUuid;
        this.categoryName = categoryName;
    }

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
