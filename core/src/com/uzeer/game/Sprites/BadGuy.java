package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 2/3/2017.
 */

public class BadGuy extends Enemy{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    TextureRegion region;

    private Texture badGuyTexture;

    FixtureDef fdef;

    public BadGuy(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        badGuyTexture = new Texture("enemy.png");
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 5, 138, 57, 50));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 63, 138, 57, 50));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 120, 138, 58, 50));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 186, 131, 58, 50));
            else if(i == 5)
                frames.add(new TextureRegion(badGuyTexture, 246, 138, 61, 50));
            else if(i == 6)
                frames.add(new TextureRegion(badGuyTexture, 310, 135, 58, 50));
        }
        walkAnimation = new Animation(0.3f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTime = 0;
        setBounds(getX(), getY(), 52 / FunGame.PPM, 39 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public BadGuy(SecondStage screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        badGuyTexture = new Texture("enemy.png");
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 6; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 5, 138, 57, 50));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 68, 138, 55, 50));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 123, 138, 56, 50));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 190, 138, 55, 50));
            else if(i == 5)
                frames.add(new TextureRegion(badGuyTexture, 314, 138, 58, 50));
        }
        //else if(i == 5)
            //frames.add(new TextureRegion(badGuyTexture, 246, 138, 61, 50));
        walkAnimation = new Animation(0.3f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTime = 0;
        setBounds(getX(), getY(), 52 / FunGame.PPM, 39 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public BadGuy(FinalStage screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        badGuyTexture = new Texture("enemy.png");
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 6; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 5, 138, 57, 50));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 68, 138, 55, 50));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 123, 138, 56, 50));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 190, 138, 55, 50));
            else if(i == 5)
                frames.add(new TextureRegion(badGuyTexture, 314, 138, 58, 50));
        }
        //else if(i == 5)
        //frames.add(new TextureRegion(badGuyTexture, 246, 138, 61, 50));
        walkAnimation = new Animation(0.3f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTime = 0;
        setBounds(getX(), getY(), 52 / FunGame.PPM, 39 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setBounds(getX(), getY(), 42 / FunGame.PPM, 50 / FunGame.PPM);
            setRegion(new TextureRegion(badGuyTexture, 5, 424, 45, 62));
            //setRegion(new TextureRegion(screen.getAtlas().findRegion("enemy"), 113, 52, 49, 51));
            stateTime = 0;
            fdef.filter.maskBits = FunGame.DESTROYED_BIT;
            dispose();
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 15 / FunGame.PPM);
            // setRegion(walkAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(stateTime));
        }
    }

    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setGravityScale(10);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //Rectangle shape = new Rectangle();
        //CircleShape shape = new CircleShape();
        //shape.setRadius(7 / FunGame.PPM);
        shape.setAsBox(9 / FunGame.PPM, 14/ FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);

        fdef.filter.categoryBits = FunGame.ENEMY_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.FIRE_BIT |
                FunGame.ENEMY_BIT |
                FunGame.OBJECT_BIT|
                FunGame.GROUND_BIT |
                FunGame.BULLET_BIT |
                FunGame.PLAYER_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

        //creat the head here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6, 27).scl(1 / FunGame.PPM);
        vertice[1] = new Vector2(6, 27).scl(1 / FunGame.PPM);
        vertice[2] = new Vector2(-3, 21).scl(1 / FunGame.PPM);
        vertice[3] = new Vector2(3, 21).scl(1 / FunGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.7f;
        fdef.filter.categoryBits = FunGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 0.7f)
            super.draw(batch);
    }


    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        return region;
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        FunGame.manager.get("sounds/enemyHit2.wav", Sound.class).play();
    }

    public static int hit = 0;

    @Override
    public void hitByEnemy(Player userData) {
        hit++;
        Hud.addScore(-1000);
        if(hit > 3){
            Gdx.app.log("Game", "End");
        }
    }

    public void dispose(){
        frames.clear();
        badGuyTexture.dispose();
    }
}
