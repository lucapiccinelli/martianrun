package com.bnana.martianrun.box2d;

import com.bnana.martianrun.enums.UserDataType;

/**
 * Created by Luca on 8/18/2015.
 */
public abstract class UserData {

    protected UserDataType userDataType;
    float width;
    float height;

    public UserData(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public UserDataType getUserDataType(){
        return userDataType;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
