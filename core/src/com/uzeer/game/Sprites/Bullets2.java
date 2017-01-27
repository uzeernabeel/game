package com.uzeer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;

/**
 * Created by uzeer on 1/25/2017.
 */
public class Bullets2 extends Enemy {
    private TextureRegion apple;
    float stateTimer;
    private boolean rightSide;
    private boolean leftSide;

    public Bullets2(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        apple = new TextureRegion(screen.getAtlas().findRegion("player"), 213, 203, 9, 12);
        setBounds(getX(), getY(), 20 / FunGame.PPM, 20 / FunGame.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);
        if(screen.player.isFlipX())
            leftSide = true;
        else
            rightSide = true;

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / FunGame.PPM, 5/ FunGame.PPM, new Vector2(5 / FunGame.PPM, 5 / FunGame.PPM), 0);

        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        setRegion(apple);

    }

    public void draw(Batch batch){
            super.draw(batch);
    }

    @Override
    public void hitOnHead() {

    }

    @Override
    public void update(float dt) {
        stateTimer += dt;
        if(leftSide)
            b2body.setLinearVelocity(NegVelocity2);
        if(rightSide)
            b2body.setLinearVelocity(velocity2);

       // setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 15 / FunGame.PPM);
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
        setRegion(apple);

    }

    @Override
    public void hitByEnemy(Player userData) {

    }

}
