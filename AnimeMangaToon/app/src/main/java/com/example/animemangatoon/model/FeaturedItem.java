package com.example.animemangatoon.model;

public class FeaturedItem {

    private int imageResId;
    private String featuredText;
    private String title;
    private String description;

    public FeaturedItem(int imageResId, String featuredText, String title, String description) {
        this.imageResId = imageResId;
        this.featuredText = featuredText;
        this.title = title;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getFeaturedText() {
        return featuredText;
    }

    public void setFeaturedText(String featuredText) {
        this.featuredText = featuredText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
