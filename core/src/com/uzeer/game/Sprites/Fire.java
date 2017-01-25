package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;

/**
 * Created by Uzeer on 12/26/2016.
 */


public class Fire extends InteractiveTileObject {

    public AnimatedTiledMapTile animatedTiledMapTile;
    public Array<StaticTiledMapTile> staticTiledMapTile;
    private static TiledMapTileSet tileSet;

    public Fire(PlayScreen screen, Rectangle bounds, String value) {
        super(screen, bounds, value);

        fixture.setUserData(this);

        setCategoryFilter(FunGame.FIRE_BIT);

        tileSet  = map.getTileSets().getTileSet("687474703a2f2f692e696d6775722e636f6d2f6856694b67416e2e706e67");

        staticTiledMapTile = new Array<StaticTiledMapTile>();

        staticTiledMapTile.add((StaticTiledMapTile) tileSet.getTile(39));
        staticTiledMapTile.add((StaticTiledMapTile) tileSet.getTile(40));
        staticTiledMapTile.add((StaticTiledMapTile) tileSet.getTile(41));
        staticTiledMapTile.add((StaticTiledMapTile) tileSet.getTile(42));

        animatedTiledMapTile = new AnimatedTiledMapTile(.2f, staticTiledMapTile);

        TiledMapTileLayer.Cell fire = new TiledMapTileLayer.Cell();
        fire.setTile(animatedTiledMapTile);



    }

    public void update(float dt){
        tileSet  = map.getTileSets().getTileSet("687474703a2f2f692e696d6775722e636f6d2f6856694b67416e2e706e67");
        Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(4);
        //for(int i = 39; i < 43; i++) {
           // frameTiles.add((StaticTiledMapTile)tileSet.getTile(i));
            animatedTiledMapTile = new AnimatedTiledMapTile(dt, staticTiledMapTile);
       // }
    }



    @Override
    public void bodyHit() {
        Gdx.app.log("Fire Touch", "Die Ha Ha!");
        //FunGame.manager.get("sounds/Decline.wav", Sound.class).play();
        Player.num++;
        if(Player.num > 3)
        Player.playerDead = true;
        Hud.addScore(-1000);
    }
}
