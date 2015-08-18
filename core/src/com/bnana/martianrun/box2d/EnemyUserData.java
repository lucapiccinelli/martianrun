package com.bnana.martianrun.box2d;

import com.badlogic.gdx.math.Vector2;
import com.bnana.martianrun.enums.UserDataType;
import com.bnana.martianrun.utils.Constants;

/**
 * Created by Luca on 8/18/2015.
 */
public class EnemyUserData extends UserData{
    private Vector2 linearVelocity;
    private String[] textureRegions;

    public EnemyUserData(float width, float height, String[] textureRegions) {
        super(width, height);
        this.textureRegions = textureRegions;
        userDataType = UserDataType.ENEMY;
        linearVelocity = Constants.ENEMY_LINEAR_VELOCITY;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public String[] getTextureRegions() {
        return textureRegions;
    }
}
