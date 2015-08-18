package com.bnana.martianrun.box2d;

import com.bnana.martianrun.enums.UserDataType;

/**
 * Created by Luca on 8/18/2015.
 */
public class GroundUserData extends UserData {

    public GroundUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.GROUND;
    }
}
