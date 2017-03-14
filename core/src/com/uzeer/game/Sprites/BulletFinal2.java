package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by uzeer on 1/26/2017.
 */
public class BulletFinal2 extends Sprite {
    private float currentPosition;
    private float previousPosition;

    private TextureRegion apple;
    private Animation bulletFire;
    float stateTimer;
    private boolean rightSide;
    private boolean leftSide;
    protected PlayScreen screen;
    protected SecondStage screen1;
    protected FinalStage screen2;
    protected World world;
    public Body b2body;
    public Vector2 velocity2;
    public Vector2 NegVelocity2;
    private boolean setToDestroy;
    private boolean destroyed;
    private FixtureDef fdef;
    float stateTime;


    public BulletFinal2(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        velocity2 = new Vector2(5f, 0f);
        NegVelocity2 = new Vector2(-5f, 0f);
        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(screen.getAtlas3().findRegion("power (4)"));
            else if(i == 2)
                frames.add(screen.getAtlas3().findRegion("power (5)"));
            else if(i == 3)
                frames.add(screen.getAtlas3().findRegion("power (6)"));
            else if(i == 4)
                frames.add(screen.getAtlas3().findRegion("power (7)"));
            else if(i == 5)
                frames.add(screen.getAtlas3().findRegion("power (8)"));
            else if(i == 6)
                frames.add(screen.getAtlas3().findRegion("power (9)"));
        }

        bulletFire = new Animation(0.05f, frames);
        frames.clear();

        setBounds(getX(), getY(), 30/ FunGame.PPM, 15 / FunGame.PPM);
        defineBullet();
    }

    public BulletFinal2(SecondStage screen, float x, float y) {
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        this.screen1 = screen;
        setPosition(x, y);
        velocity2 = new Vector2(5f, 0f);
        NegVelocity2 = new Vector2(-5f, 0f);
        apple = new TextureRegion(getTexture(), 213, 203, 9, 12);
        setBounds(getX(), getY(), 12 / FunGame.PPM, 12 / FunGame.PPM);
        setRegion(apple);
        defineBullet();
    }


    protected void defineBullet() {
        //rightSide = true;
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        if(FunGame.PlayScreen) {
            if (FunGame.player2Selected) {
                if (screen.player2.isFlipX())
                    leftSide = true;
                else
                    rightSide = true;
            } else {
                if (screen.player.isFlipX())
                    leftSide = true;
                else
                    rightSide = true;
            }
        }
        if(FunGame.SecondScreen) {
            if (FunGame.player2Selected) {
                if (screen1.player2.isFlipX())
                    leftSide = true;
                else
                    rightSide = true;
            } else {
                if (screen1.player.isFlipX())
                    leftSide = true;
                else
                    rightSide = true;
            }
        }
        if(FunGame.FinalScreen) {
            if (FunGame.player2Selected) {
                if (screen2.player2.isFlipX())
                    leftSide = true;
                else
                    rightSide = true;
            } else {
                if (screen2.player.isFlipX())
                    leftSide = true;
                else
                    rightSide = true;
            }
        }

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / FunGame.PPM, 10/ FunGame.PPM, new Vector2(10 / FunGame.PPM, 10 / FunGame.PPM), 0);

        fdef.shape = shape;
        //fdef.isSensor = true;
        fdef.filter.categoryBits = FunGame.BULLET_BIT2;

        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.FIRE_BIT |
                FunGame.ENEMY_BIT |
                FunGame.OBJECT_BIT|
                FunGame.GROUND_BIT |
                FunGame.PLAYER_BIT;

        b2body.createFixture(fdef).setUserData(this);

        b2body.setGravityScale(0);

        //setRegion(apple);

        setToDestroy = false;
        destroyed = false;
        currentPosition = 0;
        previousPosition = 0;

        previousPosition = b2body.getPosition().x;

    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);

    }


    public void update(float dt) {
        stateTime += dt;
        currentPosition = b2body.getPosition().x;
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            fdef.filter.maskBits = FunGame.DESTROYED_BIT;
            stateTimer += dt;
            setTexture(null);
        }
        if(leftSide) {
            b2body.setLinearVelocity(NegVelocity2);
            if (currentPosition <= previousPosition - 200 / FunGame.PPM)
                setToDestroy = true;
        }
        if(rightSide) {
            b2body.setLinearVelocity(velocity2);
            if (currentPosition >= previousPosition + 200 / FunGame.PPM)
                setToDestroy = true;
        }

        if(!destroyed) {
            b2body.setLinearVelocity(velocity2);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 15 / FunGame.PPM);
            // setRegion(walkAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(stateTime));
        }

    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        region = bulletFire.getKeyFrame(stateTime, false);
        return region;
    }

    public void destroyBullet() {
        setToDestroy = true;
        //dispose();
        Gdx.app.log("touch ", "ground");
    }

    public void dispose() {
        apple.getTexture().dispose();
        //world.dispose();
        //screen.dispose();
        //screen1.dispose();
    }
}
