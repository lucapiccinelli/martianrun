package com.bnana.martianrun.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.martianrun.box2d.UserData;
import com.bnana.martianrun.utils.Constants;

/**
 * Created by Luca on 8/18/2015.
 */
public abstract class GameActor extends Actor {

    protected final UserData userData;
    protected Rectangle screenRectangle;
    protected Body body;

    public GameActor(Body body){
        this.body = body;
        this.userData = (UserData)body.getUserData();
        screenRectangle = new Rectangle();
    }

    public abstract UserData getUserData();

    @Override
    public void act(float delta){
        super.act(delta);

        if(body.getUserData() != null){
            updateRectangle();
        }else {
            remove();
        }
    }

    protected void updateRectangle(){
        screenRectangle.x = transformToScreen(body.getPosition().x - userData.getWidth() / 2);
        screenRectangle.y = transformToScreen(body.getPosition().y - userData.getHeight() / 2);
        screenRectangle.width = transformToScreen(userData.getWidth());
        screenRectangle.height = transformToScreen(userData.getHeight());
    }

    protected float transformToScreen(float n) {
        return Constants.WORLD_TO_SCREEN * n;
    }
}
