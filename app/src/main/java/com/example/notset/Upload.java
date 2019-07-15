package com.example.notset;

public class Upload {
    private String mTitle;
    private String mDescription;
    private String mImageUrl;

    public Upload(){

    }

    public Upload(String title, String description, String imageUrl){

        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getDescription(){
        return mDescription;
    }

    public void setDescription(String description){
        mDescription = description;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
