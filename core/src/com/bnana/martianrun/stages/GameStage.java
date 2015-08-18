package com.bnana.martianrun.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bnana.martianrun.actors.Background;
import com.bnana.martianrun.actors.Enemy;
import com.bnana.martianrun.actors.Ground;
import com.bnana.martianrun.actors.Runner;
import com.bnana.martianrun.utils.BodyUtils;
import com.bnana.martianrun.utils.Constants;
import com.bnana.martianrun.utils.WorldUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Luca on 8/18/2015.
 */
public class GameStage extends Stage implements ContactListener{

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private Runner runner;
    private World world;
    private Ground ground;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    //private Box2DDebugRenderer renderer;

    private Vector3 touchPoint;
    private Rectangle screenRightSide;
    private Rectangle screenLeftSide;

    public GameStage(){
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));

        setupWorld();
        setupCamera();
        setupTouchControlAreas();
        //renderer = new Box2DDebugRenderer();
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    private void setupWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setupBackGround();
        setupGround();
        setupRunner();
        createEnemy();
    }

    private void setupBackGround() {
        addActor(new Background());
    }

    private void createEnemy() {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        addActor(enemy);
    }

    private void setupRunner() {
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setupGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void act(float delta){
        super.act(delta);

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        accumulator += delta;

        while (accumulator >= delta){
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body)){
            if(BodyUtils.bodyIsEnemy(body) && !runner.isHit()){
                createEnemy();
            }
            world.destroyBody(body);
        }
    }

//    @Override
//    public void draw(){
//        super.draw();
//        renderer.render(world, camera.combined);
//    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        translateToWorldCoordinates(x, y);
        if(rightSideTouched(touchPoint.x, touchPoint.y)){
            runner.jump();
        }else if(leftSideIsTouched(touchPoint.x, touchPoint.y)){
            runner.dodge();
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button){
        if(runner.isDodging()){
            runner.stopDodge();
        }

        return super.touchUp(x, y, pointer, button);
    }

    private boolean leftSideIsTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private void translateToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) || BodyUtils.bodyIsRunner(b) && BodyUtils.bodyIsEnemy(a)){
            runner.hit();
        } else if(BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b) || BodyUtils.bodyIsRunner(b) && BodyUtils.bodyIsGround(a)){
            runner.landed();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
