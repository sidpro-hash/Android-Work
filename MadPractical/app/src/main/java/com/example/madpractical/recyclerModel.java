package com.example.madpractical;

public class recyclerModel {
    private int imageView;
    private String name;
    private String address;

    public recyclerModel(int imageView, String name, String address) {
        this.imageView = imageView;
        this.name = name;
        this.address = address;
    }

    public int getImageView() {
        return imageView;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
