package com.uzeer.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;

/**
 * Created by uzeer on 1/25/2017.
 */
public class Bullets extends Sprite {

    public static final int SPEED = 200;
    public static TextureRegion texture;
    float x, y;
    public boolean remove = false;

    public Bullets(PlayScreen screen, float x, float y){
        super(screen.getAtlas().findRegion("player"));
        texture = new TextureRegion(getTexture(), 213, 203, 9, 12);
        setBounds(getX(), getY(), 30 / FunGame.PPM, 41 / FunGame.PPM);
    }

    public void update(float dt){
        x += SPEED * dt;
        if(x > FunGame.V_WIDTH)
            remove = true;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

}
