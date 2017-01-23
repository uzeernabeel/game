package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;

/**
 * Created by Uzeer on 1/5/2017.
 */

public class Boxes extends InteractiveTileObject {

    private Player player;

    public Boxes(PlayScreen screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
    }

    @Override
    public void bodyHit() {
        applyimpluse(player);
        Gdx.app.log("box", "hit");
    }

    private void applyimpluse(Player player) {
        player.b2body.applyLinearImpulse(new Vector2(0, 2f), player.b2body.getWorldCenter(), true);
    }

}
