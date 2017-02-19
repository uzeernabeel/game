package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 12/25/2016.
 */

public class Player extends Sprite {


    public static float checkPointX;

    public enum State { FALLING, JUMPING, STANDING, RUNNING, KICKING, DEAD, THROWING }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private TextureRegion playerFalling;
    private TextureRegion playerDuck;
    private TextureRegion playerIsDead;
    private Animation playerRun;
    private Animation playerJump;
    private Animation playerKick;
    private Animation playerThrow;
    private float stateTimer;
    private boolean runningRight;
    protected Fixture fixture;
    private boolean timeToRedefinePlayer;
    public static boolean playerDead;
    public static boolean spacePressed;
    private BodyDef bdef;

    public Player(PlayScreen screen){
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;
        FunGame.lives = 3;

        timeToRedefinePlayer = false;
        playerDead = false;
        spacePressed = false;

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

        for(int i = 1; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(getTexture(), 6, 193, 33, 59));
            if(i == 2)
                frames.add(new TextureRegion(getTexture(),49, 193, 35, 59));
            if(i == 3)
                frames.add(new TextureRegion(getTexture(), 89, 193, 49, 59));
            if(i == 4)
                frames.add(new TextureRegion(getTexture(), 143, 193, 49, 59));
        }
        playerThrow = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 15; i++) {
            if(i == 1)
                frames.add(new TextureRegion(getTexture(), 2, 543, 44, 55));
            if(i == 2)
                frames.add(new TextureRegion(getTexture(), 6, 670, 33, 55));
            if(i == 3)
                frames.add(new TextureRegion(getTexture(), 132, 669, 31, 55));
            if(i == 4)
                frames.add(new TextureRegion(getTexture(), 190, 685, 34, 55));
            if(i == 5)
                frames.add(new TextureRegion(getTexture(), 4, 123, 24, 63));
            if(i > 5)
                frames.add(new TextureRegion(getTexture(), 329, 306, 38, 68));
        }

        playerJump = new Animation(0.1f, frames);
        frames.clear();


        playerIsDead = new TextureRegion(getTexture(), 189, 684, 35, 40);

        playerFalling = new TextureRegion(getTexture(), 329, 306, 38, 68);

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 53, 123, 30, 63);
        setBounds(0, 0, 21 / FunGame.PPM, 38 / FunGame.PPM);
        setRegion(playerStand);
    }

    public Player(SecondStage screen){
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;

        timeToRedefinePlayer = false;
        playerDead = false;
        spacePressed = false;

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

        for(int i = 1; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(getTexture(), 6, 193, 38, 59));
            if(i == 2)
                frames.add(new TextureRegion(getTexture(),49, 193, 38, 59));
            if(i == 3)
                frames.add(new TextureRegion(getTexture(), 89, 193, 45, 59));
            if(i == 4)
                frames.add(new TextureRegion(getTexture(), 143, 193, 47, 59));
        }
        playerThrow = new Animation(0.1f, frames);
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


        playerIsDead = new TextureRegion(getTexture(), 189, 684, 35, 40);

        playerFalling = new TextureRegion(getTexture(), 329, 306, 38, 68);

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 53, 123, 30, 63);
        setBounds(0, 0, 21 / FunGame.PPM, 38 / FunGame.PPM);
        setRegion(playerStand);
    }

    public Player(FinalStage screen){
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;

        timeToRedefinePlayer = false;
        playerDead = false;
        spacePressed = false;

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

        for(int i = 1; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(getTexture(), 6, 193, 38, 59));
            if(i == 2)
                frames.add(new TextureRegion(getTexture(),49, 193, 38, 59));
            if(i == 3)
                frames.add(new TextureRegion(getTexture(), 89, 193, 45, 59));
            if(i == 4)
                frames.add(new TextureRegion(getTexture(), 143, 193, 47, 59));
        }
        playerThrow = new Animation(0.1f, frames);
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


        playerIsDead = new TextureRegion(getTexture(), 189, 684, 35, 40);

        playerFalling = new TextureRegion(getTexture(), 329, 306, 38, 68);

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 53, 123, 30, 63);
        setBounds(0, 0, 21 / FunGame.PPM, 38 / FunGame.PPM);
        setRegion(playerStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getHeight() / 2) + 11 / FunGame.PPM);
        setRegion(getFrame(dt));

        if(b2body.getPosition().y < -1f)
            playerDead = true;

        if(timeToRedefinePlayer)
            timeToRedefinePlayer();
    }


    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer, false);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = playerFalling;
                break;
            case DEAD:
                region = playerIsDead;
                break;
            case THROWING:
                region = playerThrow.getKeyFrame(stateTimer, false);
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
        if(playerDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) )
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(spacePressed && stateTimer < 3f)
            return State.THROWING;
        else
            return State.STANDING;
    }


    public void definePlayer() {
        bdef = new BodyDef();
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
        return !(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING));
    }

    public static int num = 0;

    public void hit() {
        Hud.addScore(-1000);
        FunGame.manager.get("sounds/enemy hit.wav", Sound.class).play();
        num++;
        if(num == 1) {
            Gdx.app.log("hit by Enemy: ", "1");
            Hud.chances(3);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
        if(num == 2) {
            Gdx.app.log("hit by Enemy: ", "2");
            Hud.chances(2);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
        if(num == 3) {
            Gdx.app.log("hit by Enemy: ", "3");
            Hud.chances(1);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
        if(num > 3) {
            playerDead = true;
            Hud.chances(0);
            Gdx.app.log("hit by Enemy: ", "Dead!");
            Filter filter = new Filter();
            filter.maskBits = FunGame.NOTHING_BIT;
            for(Fixture fixture: b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0, 5f), b2body.getWorldCenter(), true);
            timeToRedefinePlayer = true;
        }
    }

    private void timeToRedefinePlayer() {
        Hud.chances(4);
        num = 0;

        bdef = new BodyDef();
        bdef.position.set(checkPointX / FunGame.PPM, 32 / FunGame.PPM);
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

        timeToRedefinePlayer = false;

    }

    public boolean isDead(){
        return playerDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

}
