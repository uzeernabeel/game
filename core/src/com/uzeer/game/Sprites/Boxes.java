package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 1/5/2017.
 */

public class Boxes extends InteractiveTileObject {


    public Boxes(PlayScreen screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
    }

    public Boxes(SecondStage screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
    }

    public Boxes(FinalStage screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
    }

    @Override
    public void bodyHit() {
        Gdx.app.log("box", "hit");
    }

}
