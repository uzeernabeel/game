package com.uzeer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;

import java.util.Iterator;

/**
 * Created by Uzeer on 12/26/2016.
 */

//successfully shared the project on the github to work from work ha!

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected EdgeShape head;
    protected FixtureDef fixtureDef;
    protected Segment line;

    public InteractiveTileObject(PlayScreen screen, Rectangle bounds, int value){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / FunGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / FunGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / FunGame.PPM, bounds.getHeight() / 2 / FunGame.PPM);
        fdef.shape = shape;

        if(value == 2) {
            fdef.filter.categoryBits = FunGame.OBJECT_BIT;
            fixtureDef = new FixtureDef();
            head = new EdgeShape();
           // line = new Segment(body.getPosition().x - 22 / FunGame.PPM, body.getPosition().x + 22 / FunGame.PPM, 0,
              //      body.getPosition().x + 22 / FunGame.PPM, body.getPosition().x + 22 / FunGame.PPM, 0);
            head.set(new Vector2(-11 / FunGame.PPM,  11 / FunGame.PPM),
                    new Vector2(11/ FunGame.PPM,  11 / FunGame.PPM));

            fixtureDef.shape = head;
            fixtureDef.filter.categoryBits = FunGame.OBJECT_HEAD_BIT;
            fixtureDef.isSensor = true;

            body.createFixture(fixtureDef).setUserData(this);
            //PolygonShape objectHead = new PolygonShape();  // Start editing here to get a bounce when player hit the head of box.
            //objectHead.setAsBox(2, 0);

        }

        else if (value == 1)
            fdef.isSensor = true;

        else if(value == 3) {

        }
        fixture = body.createFixture(fdef);
    }



    public abstract void bodyHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * FunGame.PPM / 22),
                             (int)(body.getPosition().y * FunGame.PPM / 22));
    }

}

