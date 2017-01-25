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
    private TextureRegion texture;
    float stateTimer;

    public Bullets2(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        texture = new TextureRegion(screen.getAtlas().findRegion("player"), 213, 203, 9, 12);
        setBounds(getX(), getY(), 10 / FunGame.PPM, 11 / FunGame.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1 / FunGame.PPM, 5/ FunGame.PPM, new Vector2(1 / FunGame.PPM, 5 / FunGame.PPM), 0);

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

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
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 15 / FunGame.PPM);
       // setRegion(texture);
    }

    @Override
    public void hitByEnemy(Player userData) {

    }
}
