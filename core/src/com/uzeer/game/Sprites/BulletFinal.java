package com.uzeer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by uzeer on 1/26/2017.
 */
public class BulletFinal extends Sprite {
    private TextureRegion apple;
    float stateTimer;
    private boolean rightSide;
    private boolean leftSide;
    protected PlayScreen screen;
    protected SecondStage screen1;
    protected World world;
    public Body b2body;
    public Vector2 velocity2;
    public Vector2 NegVelocity2;
    private boolean setToDestroy;
    private boolean destroyed;
    private FixtureDef fdef;

    public BulletFinal(PlayScreen screen, float x, float y) {
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineBullet();
        velocity2 = new Vector2(1.5f, 0f);
        NegVelocity2 = new Vector2(-1.5f, 0f);
        apple = new TextureRegion(getTexture(), 213, 203, 9, 12);
        setBounds(getX(), getY(), 12 / FunGame.PPM, 12 / FunGame.PPM);
    }

    public BulletFinal(SecondStage screen, float x, float y) {
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        this.screen1 = screen;
        setPosition(x, y);
        defineBullet();
        velocity2 = new Vector2(3f, 0f);
        NegVelocity2 = new Vector2(-3f, 0f);
        apple = new TextureRegion(getTexture(), 213, 203, 9, 12);
        setBounds(getX(), getY(), 12 / FunGame.PPM, 12 / FunGame.PPM);
    }

    protected void defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);
        if(screen1.player.isFlipX())
            leftSide = true;
        else
            rightSide = true;

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / FunGame.PPM, 5/ FunGame.PPM, new Vector2(5 / FunGame.PPM, 5 / FunGame.PPM), 0);

        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
       // setRegion(apple);

        setToDestroy = false;
        destroyed = false;

    }

    public void draw(Batch batch){
        if(!destroyed)
        super.draw(batch);
    }



    public void update(float dt) {
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            fdef.filter.maskBits = FunGame.DESTROYED_BIT;
            stateTimer += dt;
            setTexture(null);
        }
        if(leftSide)
            b2body.setLinearVelocity(NegVelocity2);
        if(rightSide)
            b2body.setLinearVelocity(velocity2);

        setPosition(b2body.getPosition().x + getWidth() / 2 - 8 / FunGame.PPM , b2body.getPosition().y + getHeight() / 2 - 7 / FunGame.PPM);
        setRegion(apple);

        if(b2body.getPosition().x > 138 / FunGame.PPM)
            setToDestroy = true;

    }

}
