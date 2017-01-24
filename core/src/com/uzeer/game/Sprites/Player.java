package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;

/**
 * Created by Uzeer on 12/25/2016.
 */

public class Player extends Sprite {


    public enum State { FALLING, JUMPING, STANDING, RUNNING, KICKING };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private TextureRegion playerFalling;
    private TextureRegion playerDuck;
    private Animation playerRun;
    private Animation playerJump;
    private Animation playerKick;
    private float stateTimer;
    private boolean runningRight;
    protected Fixture fixture;
    private boolean timeToRedefinePlayer;

    public Player(PlayScreen screen){
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;

        timeToRedefinePlayer = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 9; i++) {
            if(i == 1)
                frames.add(new TextureRegion(getTexture(), 92, 124, 31, 58));
            else if(i == 2)
                frames.add(new TextureRegion(getTexture(), 123, 124, 31, 61));
            else if(i == 3)
                frames.add(new TextureRegion(getTexture(), 158, 124, 41, 58));
            else if(i == 4)
                frames.add(new TextureRegion(getTexture(), 204, 124, 39, 58));
            else if(i == 5)
                frames.add(new TextureRegion(getTexture(), 245, 124, 31, 58));
            else if(i == 6)
                frames.add(new TextureRegion(getTexture(), 281, 124, 34, 62));
            else if(i == 7)
                frames.add(new TextureRegion(getTexture(), 322, 124, 40, 58));
            else if(i == 8)
                frames.add(new TextureRegion(getTexture(), 366, 124, 32, 58));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();


        for(int i = 1; i < 15; i++) {
            if(i == 1)
                frames.add(new TextureRegion(getTexture(), 2, 543, 44, 40));
            if(i == 2)
                frames.add(new TextureRegion(getTexture(), 6, 670, 33, 35));
            if(i == 3)
                frames.add(new TextureRegion(getTexture(), 132, 669, 31, 38));
            if(i == 4)
                frames.add(new TextureRegion(getTexture(), 190, 685, 34, 34));
            if(i == 5)
                frames.add(new TextureRegion(getTexture(), 4, 123, 24, 63));
            if(i > 5)
                frames.add(new TextureRegion(getTexture(), 329, 306, 38, 68));
        }

        playerJump = new Animation(0.1f, frames);
        frames.clear();

        playerFalling = new TextureRegion(getTexture(), 329, 306, 38, 68);

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 53, 123, 30, 63);
        setBounds(0, 0, 21 / FunGame.PPM, 38 / FunGame.PPM);
        setRegion(playerStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getHeight() / 2) + 11 / FunGame.PPM);
        setRegion(getFrame(dt));
        if(timeToRedefinePlayer)
            timeToRedefinePlayer();
    }


    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = playerFalling;
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }



    private State getState() {
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) )
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }


    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / FunGame.PPM, 32 / FunGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //Rectangle shape = new Rectangle();
        //CircleShape shape = new CircleShape();
        //shape.setRadius(7 / FunGame.PPM);
        shape.setAsBox(5 / FunGame.PPM, 17 / FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);

        fdef.filter.categoryBits = FunGame.PLAYER_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.FIRE_BIT |
                FunGame.ENEMY_BIT |
                FunGame.OBJECT_BIT |
                FunGame.GROUND_BIT |
                FunGame.ENEMY_HEAD_BIT;

        fdef.shape = shape;

       // b2body.createFixture(fdef).setUserData("player");
        b2body.createFixture(fdef).setUserData(this);


       // b2body.createFixture(fdef).setUserData(this);

       /* PolygonShape body = new PolygonShape();
        body.set(new Vector2(2 / FunGame.PPM, 7 / FunGame.PPM), );
        fdef.shape = body;

        b2body.createFixture(fdef).setUserData("body");

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / FunGame.PPM, 7 / FunGame.PPM), new Vector2(2 / FunGame.PPM, 7 / FunGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");  */
    }

    public boolean IsPlayerOnGround(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) )
            return false;
        else
            return true;
    }

    public void hit() {
        timeToRedefinePlayer = true;
        Gdx.app.log("hit by Enemy", "ha!");
    }

    private void timeToRedefinePlayer() {
        //Gdx.app.log("hit by Enemy", "ha!");
    }


}
