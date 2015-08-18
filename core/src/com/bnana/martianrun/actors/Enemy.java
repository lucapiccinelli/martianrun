package com.bnana.martianrun.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.martianrun.box2d.EnemyUserData;
import com.bnana.martianrun.box2d.UserData;
import com.bnana.martianrun.utils.Constants;

/**
 * Created by Luca on 8/18/2015.
 */
public class Enemy extends GameActor {
    private final Animation animation;
    private float stateTime;

    public Enemy(Body body) {
        super(body);
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[getUserData().getTextureRegions().length];
        for(int i = 0; i < getUserData().getTextureRegions().length; i++){
            runningFrames[i] = textureAtlas.findRegion(getUserData().getTextureRegions()[i]);
        }
        animation = new Animation(0.1f, runningFrames);
        stateTime = 0f;
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(stateTime, true), screenRectangle.x - screenRectangle.width * 0.1f, screenRectangle.y - screenRectangle.height * 0.1f, screenRectangle.width * 1.2f, screenRectangle.height* 1.2f);
    }

    @Override
    public EnemyUserData getUserData() {
        return (EnemyUserData) userData;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }
}
