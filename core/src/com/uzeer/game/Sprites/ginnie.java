package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.Level_complition;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;


/**
 * Created by uzeer on 3/10/2017.
 */

public class ginnie extends Sprite {

    private Animation ginnieDance;
    private float stateTime;
    private Array<TextureRegion> frames;
    private TextureAtlas atlas2;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    FixtureDef fdef;
    Level_complition screen;
    TextureRegion region;

    public ginnie(Level_complition screen, float x, float y) {

        this.screen = screen;
        atlas2 = new TextureAtlas("stuff.pack");

        frames = new Array<TextureRegion>();
        for (int i = 1; i < 10; i++) {
            if (i == 1)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 0, 402, 85, 101));
            else if (i == 2)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 78, 402, 88, 101));
            else if (i == 3)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 151, 402, 88, 101));
            else if (i == 4)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 239, 402, 88, 101));
            else if (i == 5)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 338, 402, 92, 101));
            else if (i == 6)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 437, 402, 101, 101));
            else if (i == 7)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 538, 402, 76, 101));
            else if (i == 8)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 605, 402, 81, 101));
            else if (i == 9)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 669, 402, 81, 101));
        }
        ginnieDance = new Animation(0.2f, frames);
        frames.clear();

        region = ginnieDance.getKeyFrame(stateTime, true);

        setBounds(0, 0, 55 / FunGame.PPM, 70 / FunGame.PPM);

        stateTime = 0; //call of duty
    }

    public void update(float dt){
        stateTime += dt;
        //setPosition(FunGame.V_WIDTH / 2 / FunGame.PPM, FunGame.V_HEIGHT / 2 / FunGame.PPM);
        setRegion(getFrame(stateTime));
    }


   // public void draw(Batch batch){
         //   super.draw(batch);
    //}


    public TextureRegion getFrame(float dt) {
        region = ginnieDance.getKeyFrame(stateTime, true);

        if (stateTime > 15) {
            region.flip(true, false);
        }
        return region;
    }


    public void dispose(){
        atlas2.dispose();
    }

}
  /*private Animation ginnieDance;
    Array<TextureRegion> frames;
    private TextureAtlas atlas2;
    private float stateTime;
    private SpriteBatch batch;

   /* public ginnie(Level_complition screen, float x, float y) {
        super(screen, x, y);

        atlas2 = new TextureAtlas("stuff.pack");


        frames = new Array<TextureRegion>();
        for (int i = 1; i < 10; i++) {
            if (i == 1)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 0, 402, 85, 101));
            else if (i == 2)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 78, 402, 88, 101));
            else if (i == 3)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 151, 402, 88, 101));
            else if (i == 4)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 239, 402, 88, 101));
            else if (i == 5)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 338, 402, 92, 101));
            else if (i == 6)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 437, 402, 101, 101));
            else if (i == 7)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 538, 402, 76, 101));
            else if (i == 8)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 605, 402, 81, 101));
            else if (i == 9)
                frames.add(new TextureRegion(atlas2.findRegion("ginnei"), 669, 402, 81, 101));
        }
        ginnieDance = new Animation(0.2f, frames);
        frames.clear();

        setBounds(0, 0, 21 / FunGame.PPM, 38 / FunGame.PPM);
    }
} */
