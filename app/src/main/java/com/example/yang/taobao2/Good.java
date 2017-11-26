package com.example.yang.taobao2;
public class Good {
    private String name;
    private int imageId;
    public Good(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}