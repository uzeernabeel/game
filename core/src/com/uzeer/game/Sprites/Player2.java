package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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

public class Player2 extends Sprite {


    private boolean playerHitted;

    public enum State { FALLING, JUMPING, STANDING, RUNNING, KICKING, DEAD, THROWING, STANDING_STILL, STANDING2 }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private Animation playerStand;
    private Animation playerStand2;
    private TextureRegion playerStand1;
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
    private Texture texture;
    FixtureDef fdef;
    private boolean hitted;
    private boolean finalStage;
    private PlayScreen screen;
    private SecondStage screen1;
    private FinalStage screen2;

    private Array<Bullet> bullet;


    public static float checkPointX;

    public Player2(PlayScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;
        FunGame.lives = 3;

        texture = new Texture("player2.png");

        timeToRedefinePlayer = false;
        playerDead = false;
        spacePressed = false;

        checkPointX = 32;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 14; i++) {
            if(i == 1)
                frames.add(new TextureRegion(texture, 13, 1214, 52, 61));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 65, 1214, 53, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 119, 1214, 51, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 170, 1214, 49, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 219, 1214, 53, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 278, 1214, 53, 61));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 333, 1214, 52, 61));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 385, 1214, 53, 61));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 439, 1214, 47, 61));
            else if(i == 10)
                frames.add(new TextureRegion(texture, 487, 1214, 53, 61));
            else if(i == 11)
                frames.add(new TextureRegion(texture, 546, 1214, 56, 61));
            else if(i == 12)
                frames.add(new TextureRegion(texture, 610, 1214, 56, 61));
            else if(i == 13)
                frames.add(new TextureRegion(texture, 678, 1214, 53, 61));
        }

        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(new TextureRegion(texture, 6, 230, 50, 61));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 56, 230, 52, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 108, 230, 53, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 162, 230, 53, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 220, 230, 47, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 267, 230, 53, 61));
        }
        playerThrow = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 9; i++) {
            if(i == 1)
                frames.add(new TextureRegion(texture, 65, 691, 57, 67));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 123, 685, 58, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 198, 685, 59, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 272, 685, 62, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 349, 685, 59, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 418, 685, 58, 61));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 389, 817, 55, 80));
        }
        //setBounds(0, 0, 35 / FunGame.PPM, 48 / FunGame.PPM);
        playerJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 10; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 1, 46, 57));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 46, 1, 46, 57));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 196, 1, 46, 57));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 249, 1, 46, 57));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 302, 1, 46, 57));
        }

        playerStand = new Animation(0.20f, frames);
        frames.clear();

        for(int i = 1; i < 17; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 138, 43, 80));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 45, 138, 43, 80));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 90, 138, 43, 80));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 187, 138, 43, 80));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 237, 138, 43, 80));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 287, 138, 43, 80));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 333, 138, 43, 80));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 377, 138, 43, 80));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 424, 138, 43, 80));
            else if(i == 10)
                frames.add(new TextureRegion(texture, 471, 138, 43, 80));
            else if(i == 11)
                frames.add(new TextureRegion(texture, 518, 138, 43, 80));
            else if(i == 12)
                frames.add(new TextureRegion(texture, 567, 138, 43, 80));
            else if(i == 13)
                frames.add(new TextureRegion(texture, 614, 138, 43, 80));
            else if(i == 14)
                frames.add(new TextureRegion(texture, 665, 138, 43, 80));
            else if(i == 15)
                frames.add(new TextureRegion(texture, 714, 138, 43, 80));
            else if(i == 16)
                frames.add(new TextureRegion(texture, 760, 138, 43, 80));
        }

        playerStand2 = new Animation(0.1f, frames);
        frames.clear();


        playerIsDead = new TextureRegion(texture, 406, 2229, 47, 61);

        //playerFalling = new TextureRegion(new TextureRegion(texture, 491, 679, 58, 86));
        playerFalling = new TextureRegion(new TextureRegion(texture, 389, 817, 50, 80));
        //playerFalling = new TextureRegion(new TextureRegion(texture, 564, 811, 58, 93));

        definePlayer();
        setBounds(0, 0, 28 / FunGame.PPM, 41 / FunGame.PPM);
       // setRegion(playerStand1);

        bullet = new Array<Bullet>();
    }

    public Player2(SecondStage screen){
        this.screen1 = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING_STILL;
        previousState = State.STANDING_STILL;
        stateTimer = 0f;
        runningRight = true;

        playerHitted = false;
        FunGame.lives = 3;

        texture = new Texture("player2.png");

        timeToRedefinePlayer = false;
        playerDead = false;
        spacePressed = false;

        checkPointX = 32;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 14; i++) {
            if(i == 1)
                frames.add(new TextureRegion(texture, 13, 1214, 52, 61));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 65, 1214, 53, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 119, 1214, 53, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 170, 1214, 49, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 219, 1214, 53, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 278, 1214, 53, 61));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 333, 1214, 52, 61));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 385, 1214, 53, 61));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 439, 1214, 47, 61));
            else if(i == 10)
                frames.add(new TextureRegion(texture, 487, 1214, 53, 61));
            else if(i == 11)
                frames.add(new TextureRegion(texture, 546, 1214, 56, 61));
            else if(i == 12)
                frames.add(new TextureRegion(texture, 610, 1214, 56, 61));
            else if(i == 13)
                frames.add(new TextureRegion(texture, 678, 1214, 53, 61));
        }

        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(new TextureRegion(texture, 6, 230, 50, 61));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 56, 230, 52, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 108, 230, 53, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 162, 230, 53, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 220, 230, 47, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 267, 230, 53, 61));
        }
        playerThrow = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 9; i++) {
            if(i == 1)
                frames.add(new TextureRegion(texture, 65, 691, 57, 67));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 123, 685, 58, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 198, 685, 59, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 272, 685, 62, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 349, 685, 59, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 418, 685, 58, 61));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 389, 817, 55, 80));
        }
        //setBounds(0, 0, 35 / FunGame.PPM, 48 / FunGame.PPM);
        playerJump = new Animation(0.1f, frames);
        frames.clear();

       /* for(int i = 1; i < 7; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 1, 46, 57));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 46, 1, 46, 57));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 196, 1, 46, 57));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 249, 1, 46, 57));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 302, 1, 46, 57));
        }

        playerStand = new Animation(0.20f, frames);
        frames.clear(); */

        for(int i = 1; i < 10; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 1, 46, 57));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 46, 1, 46, 57));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 196, 1, 46, 57));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 249, 1, 46, 57));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 302, 1, 46, 57));
        }

        playerStand = new Animation(0.20f, frames);
        frames.clear();

        for(int i = 1; i < 17; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 138, 43, 80));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 45, 138, 43, 80));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 90, 138, 43, 80));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 187, 138, 43, 80));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 237, 138, 43, 80));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 287, 138, 43, 80));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 333, 138, 43, 80));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 377, 138, 43, 80));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 424, 138, 43, 80));
            else if(i == 10)
                frames.add(new TextureRegion(texture, 471, 138, 43, 80));
            else if(i == 11)
                frames.add(new TextureRegion(texture, 518, 138, 43, 80));
            else if(i == 12)
                frames.add(new TextureRegion(texture, 567, 138, 43, 80));
            else if(i == 13)
                frames.add(new TextureRegion(texture, 614, 138, 43, 80));
            else if(i == 14)
                frames.add(new TextureRegion(texture, 665, 138, 43, 80));
            else if(i == 15)
                frames.add(new TextureRegion(texture, 714, 138, 43, 80));
            else if(i == 16)
                frames.add(new TextureRegion(texture, 760, 138, 43, 80));
        }

        playerStand2 = new Animation(0.1f, frames);
        frames.clear();

        playerIsDead = new TextureRegion(texture, 406, 2229, 47, 61);

        //playerFalling = new TextureRegion(new TextureRegion(texture, 491, 679, 58, 86));
        playerFalling = new TextureRegion(new TextureRegion(texture, 389, 817, 50, 80));
        //playerFalling = new TextureRegion(new TextureRegion(texture, 564, 811, 58, 93));

        //playerStand1 = new TextureRegion(new TextureRegion(texture, 2, 1, 46, 57));

        definePlayer();

        setBounds(0, 0, 28 / FunGame.PPM, 41 / FunGame.PPM);

        bullet = new Array<Bullet>();
        //setRegion(playerStand);
    }

    public Player2(FinalStage screen){
        this.screen2 = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING_STILL;
        previousState = State.STANDING_STILL;
        stateTimer = 0f;
        runningRight = true;

        finalStage = true;

        playerHitted = false;

        texture = new Texture("player2.png");

        timeToRedefinePlayer = false;
        playerDead = false;
        spacePressed = false;

        FunGame.lives = 3;
        checkPointX = 32;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 14; i++) {
            if(i == 1)
                frames.add(new TextureRegion(texture, 13, 1214, 52, 61));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 65, 1214, 53, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 119, 1214, 51, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 170, 1214, 49, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 219, 1214, 53, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 278, 1214, 53, 61));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 333, 1214, 52, 61));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 385, 1214, 53, 61));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 439, 1214, 47, 61));
            else if(i == 10)
                frames.add(new TextureRegion(texture, 487, 1214, 53, 61));
            else if(i == 11)
                frames.add(new TextureRegion(texture, 546, 1214, 56, 61));
            else if(i == 12)
                frames.add(new TextureRegion(texture, 610, 1214, 56, 61));
            else if(i == 13)
                frames.add(new TextureRegion(texture, 678, 1214, 53, 61));
        }

        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(new TextureRegion(texture, 6, 230, 50, 61));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 56, 230, 52, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 108, 230, 53, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 162, 230, 53, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 220, 230, 47, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 267, 230, 53, 61));
        }
        playerThrow = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 9; i++) {
            if(i == 1)
                frames.add(new TextureRegion(texture, 65, 691, 57, 67));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 123, 685, 58, 61));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 198, 685, 59, 61));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 272, 685, 62, 61));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 349, 685, 59, 61));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 418, 685, 58, 61));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 389, 817, 55, 80));
        }
        //setBounds(0, 0, 35 / FunGame.PPM, 48 / FunGame.PPM);
        playerJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 7; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 1, 46, 57));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 46, 1, 46, 57));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 94, 1, 46, 57));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 196, 1, 46, 57));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 249, 1, 46, 57));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 302, 1, 46, 57));
        }

        playerStand = new Animation(0.20f, frames);
        frames.clear();

        for(int i = 1; i < 17; i++) {
            if (i == 1)
                frames.add(new TextureRegion(texture, 2, 138, 43, 80));
            else if(i == 2)
                frames.add(new TextureRegion(texture, 45, 138, 43, 80));
            else if(i == 3)
                frames.add(new TextureRegion(texture, 90, 138, 43, 80));
            else if(i == 4)
                frames.add(new TextureRegion(texture, 187, 138, 43, 80));
            else if(i == 5)
                frames.add(new TextureRegion(texture, 237, 138, 43, 80));
            else if(i == 6)
                frames.add(new TextureRegion(texture, 287, 138, 43, 80));
            else if(i == 7)
                frames.add(new TextureRegion(texture, 333, 138, 43, 80));
            else if(i == 8)
                frames.add(new TextureRegion(texture, 377, 138, 43, 80));
            else if(i == 9)
                frames.add(new TextureRegion(texture, 424, 138, 43, 80));
            else if(i == 10)
                frames.add(new TextureRegion(texture, 471, 138, 43, 80));
            else if(i == 11)
                frames.add(new TextureRegion(texture, 518, 138, 43, 80));
            else if(i == 12)
                frames.add(new TextureRegion(texture, 567, 138, 43, 80));
            else if(i == 13)
                frames.add(new TextureRegion(texture, 614, 138, 43, 80));
            else if(i == 14)
                frames.add(new TextureRegion(texture, 665, 138, 43, 80));
            else if(i == 15)
                frames.add(new TextureRegion(texture, 714, 138, 43, 80));
            else if(i == 16)
                frames.add(new TextureRegion(texture, 760, 138, 43, 80));
        }

        playerStand2 = new Animation(0.1f, frames);
        frames.clear();

        playerIsDead = new TextureRegion(texture, 406, 2229, 47, 61);

        //playerFalling = new TextureRegion(new TextureRegion(texture, 491, 679, 58, 86));
        playerFalling = new TextureRegion(new TextureRegion(texture, 389, 817, 50, 80));
        //playerFalling = new TextureRegion(new TextureRegion(texture, 564, 811, 58, 93));

        //playerStand1 = new TextureRegion(new TextureRegion(texture, 2, 1, 46, 57));

        definePlayer();

        setBounds(0, 0, 28 / FunGame.PPM, 41 / FunGame.PPM);

        //setRegion(playerStand);
        bullet = new Array<Bullet>();
    }

    public void update(float dt){
        //stateTimer += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getHeight() / 2) + 11 / FunGame.PPM);
        setRegion(getFrame(dt));

        if(b2body.getPosition().y < -1f)
            playerDead = true;

        if(timeToRedefinePlayer)
            timeToRedefinePlayer();

        for(Bullet ball : bullet){
            ball.update(dt);
            if(ball.isDestroyed())
                bullet.removeValue(ball, true);
        }

    }


    public TextureRegion getFrame(float dt) {
        currentState = getState(dt);

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
                region = playerStand.getKeyFrame(stateTimer, true);
                break;
            case STANDING2:
                region = playerStand2.getKeyFrame(stateTimer, true);
                break;
            //case STANDING_STILL:
            default:
                region = playerStand.getKeyFrame(stateTimer, true);
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



    private State getState(float dt) {
        if(playerDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) ) {
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(spacePressed && stateTimer > 3f)
            return State.THROWING;
        else if(b2body.getLinearVelocity().x == 0 && stateTimer > 6)
            return State.STANDING2;
        else if(b2body.getLinearVelocity().x == 0 && stateTimer < 6)
            return State.STANDING;
        else
            return State.STANDING;
    }


    public void definePlayer() {

        bdef = new BodyDef();
        bdef.position.set(32 / FunGame.PPM, 32 / FunGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //Rectangle shape = new Rectangle();
        //CircleShape shape = new CircleShape();
        //shape.setRadius(7 / FunGame.PPM);

        shape.setAsBox(7 / FunGame.PPM, 17 / FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);

        //fdef.isSensor = false;

        fdef.filter.categoryBits = FunGame.PLAYER_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.FIRE_BIT |
                FunGame.ENEMY_BIT |
                FunGame.OBJECT_BIT |
                FunGame.GROUND_BIT |
                FunGame.CHECK_POINT_BIT |
                FunGame.ENEMY_HEAD_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

    }

    public boolean IsPlayerOnGround(){
        return !(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING));
    }

    public static int num = 0;

    public void hit() {
        Hud.addScore(-1000);

        //FunGame.manager.get("sounds/hitByEnemy.wav", Sound.class).play();
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
            FunGame.lives--;
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

        float postion = b2body.getPosition().x;
        float postionY = b2body.getPosition().y;

        world.destroyBody(b2body);

        bdef = new BodyDef();
        bdef.position.set(postion / FunGame.PPM, postionY / FunGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //Rectangle shape = new Rectangle();
        //CircleShape shape = new CircleShape();
        //shape.setRadius(7 / FunGame.PPM);

        shape.setAsBox(7 / FunGame.PPM, 17 / FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);
        //fdef.isSensor = false;

        fdef.filter.categoryBits = FunGame.PLAYER_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.FIRE_BIT |
                FunGame.ENEMY_BIT |
                FunGame.OBJECT_BIT |
                FunGame.GROUND_BIT |
                FunGame.ENEMY_HEAD_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefinePlayer = false;
    }

    public boolean isDead(){
        return playerDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void dispose(){
        world.dispose();
        texture.dispose();

    }

    public void fire(){
        bullet.add(new Bullet(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Bullet ball: bullet)
            ball.draw(batch);
    }

}
