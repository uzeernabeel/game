package com.uzeer.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;
import com.uzeer.game.Sprites.BadGuy;
import com.uzeer.game.Sprites.Boxes;
import com.uzeer.game.Sprites.Coin;
import com.uzeer.game.Sprites.Fire;
import com.uzeer.game.Sprites.Flinkstone;

/**
 * Created by Uzeer on 12/26/2016.
 */

public class B2WorldCreator {

    private Array<Flinkstone> flinkstone;
    private Array<BadGuy> badGuys;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //This is for Ground Layer # 2
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.GROUND_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.FIRE_BIT |
                    FunGame.ENEMY_BIT |
                    FunGame.OBJECT_BIT|
                    FunGame.BULLET_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef);
        }

        //This is for Coins Layer # 3
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect, "Coins");
        }

        //This is for Breaking Boxes Layer # 4
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

           /*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef); */
            new Boxes(screen, rect, "Boxes");
        }
        //This is for Fire Layer # 5
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Fire(screen, rect, "Fire");

        }
        //This is for Power Up's Layer # 6
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }
            //creat flinkstone
            flinkstone = new Array<Flinkstone>();
            for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                flinkstone.add(new Flinkstone(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }
    }

    public B2WorldCreator(SecondStage screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //This is for Ground Layer # 2
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.GROUND_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.FIRE_BIT |
                    FunGame.ENEMY_BIT |
                    FunGame.OBJECT_BIT|
                    FunGame.BULLET_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef).setUserData(this);
        }

        //This is for Coin Layer
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect, "Coins");
        }

        //creat flinkstone Layer
        flinkstone = new Array<Flinkstone>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            flinkstone.add(new Flinkstone(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat badGuy Layer
        badGuys = new Array<BadGuy>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            badGuys.add(new BadGuy(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        /*This is for Ground Layer
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.ITEM_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.FIRE_BIT |
                    FunGame.OBJECT_BIT|
                    FunGame.BULLET_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef).setUserData(this);
        } */

    }

    public B2WorldCreator(FinalStage screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //This is for Ground Layer # 2
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.GROUND_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.FIRE_BIT |
                    FunGame.ENEMY_BIT |
                    FunGame.OBJECT_BIT|
                    FunGame.BULLET_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef).setUserData(this);
        }

        //This is for Coin Layer
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect, "Coins");
        }

       /* //creat flinkstone Layer
        flinkstone = new Array<Flinkstone>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            flinkstone.add(new Flinkstone(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat badGuy Layer
        badGuys = new Array<BadGuy>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            badGuys.add(new BadGuy(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        /*This is for Ground Layer
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.ITEM_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.FIRE_BIT |
                    FunGame.OBJECT_BIT|
                    FunGame.BULLET_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef).setUserData(this);
        } */

    }

    public Array<Flinkstone> getFlinkstone() {
        return flinkstone;
    }

    public Array<BadGuy> getBadGuys(){
        return badGuys;
    }
}
