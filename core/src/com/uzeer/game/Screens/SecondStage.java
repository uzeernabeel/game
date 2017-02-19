package com.uzeer.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Sprites.BadGuy;
import com.uzeer.game.Sprites.BulletFinal;
import com.uzeer.game.Sprites.Enemy;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.Player2;
import com.uzeer.game.Tools.B2WorldCreator;
import com.uzeer.game.Tools.Controller;
import com.uzeer.game.Tools.TextureMapObjectRenderer;
import com.uzeer.game.Tools.WorldContactListner;

import java.util.ArrayList;

/**
 * Created by Uzeer on 1/27/2017.
 */

public class SecondStage implements Screen {
    private FunGame game;

    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //private TextureMapObjectRenderer textureMapObjectRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    public Player player;
    public Player2 player2;
    private B2WorldCreator creator;

    private Controller controller;

    private Music music;
    private boolean playerIsTouchingTheGround;

    private BulletFinal bulletFinal;

    private float maxPosition;
    private float minPosition;

    public static final float TIMER = 0.5f;
    float shootTimer;



    public SecondStage(FunGame game){
        atlas = new TextureAtlas("sprite sheet2.pack");
        this.game = game;
        FunGame.SecondScreen = true;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FunGame.V_WIDTH1 / FunGame.PPM, FunGame.V_HEIGHT1 / FunGame.PPM, gamecam);
        //gamePort = new FitViewport(1980 / FunGame.PPM, 1080 / FunGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("FinalSecond.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FunGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2 , 0);

        gamecam.position.x = (gamePort.getScreenWidth() / 2) + 4f;

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        controller = new Controller(game.batch);
        //player = new Player(this);
        player2 = new Player2(this);

        bulletFinal = new BulletFinal(this, 6, 70);

        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListner());

        maxPosition = 0;
        minPosition = 0;

        //FunGame.manager.get("sounds/welcome.mp3", Sound.class).play();
        music = FunGame.manager.get("sounds/FinalGameBackground.mp3", Music.class);
        //music.setVolume(.09f);
        music.setLooping(true);
        music.play();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        if(FunGame.player2Selected){
            player2.update(dt);
            if (player2.b2body.getPosition().y > 30) //
                levelComplete();
        } else {
            player.update(dt);
            if (player.b2body.getPosition().y > 30) //
                levelComplete();
        }

        for(Enemy enemy : creator.getFlinkstone())
            enemy.update(dt);

        for(Enemy enemy : creator.getBadGuys())
            enemy.update(dt);

        Gdx.app.log("Player Position: ", String.format("%03f", player2.b2body.getPosition().x));

        bulletFinal.update(dt);

        hud.update(dt);

        shootTimer += dt;

       /* minPosition = player.b2body.getPosition().y;

        if (player.currentState != Player.State.DEAD) {
            if(minPosition >= maxPosition) {
                gamecam.position.y = player.b2body.getPosition().y;
                maxPosition = player.b2body.getPosition().y;
            }
            if(minPosition < maxPosition + 3f)
                gamecam.position.y = player.b2body.getPosition().y;
        } */
        if(FunGame.player2Selected) {
            if (player2.currentState != Player2.State.DEAD)
                gamecam.position.y = player2.b2body.getPosition().y;

        } else {
            if (player.currentState != Player.State.DEAD)
                gamecam.position.y = player.b2body.getPosition().y;
        }
        gamecam.update();
        renderer.setView(gamecam);
       // textureMapObjectRenderer.setView(gamecam);

    }

    private void handleInput(float dt) {
        // android specific code
        if (FunGame.player2Selected) {
            if (player2.currentState != Player2.State.DEAD) {
                if (controller.isRightPressed() && player2.b2body.getLinearVelocity().x <= 3)
                    player2.b2body.applyLinearImpulse(new Vector2(0.15f, 0), player2.b2body.getWorldCenter(), true);
                if (controller.isLeftPressed() && player2.b2body.getLinearVelocity().x >= -3)
                    player2.b2body.applyLinearImpulse(new Vector2(-0.15f, 0), player2.b2body.getWorldCenter(), true);
                if (controller.isJumpPressed() && player2.b2body.getLinearVelocity().y == 0)
                    player2.b2body.applyLinearImpulse(new Vector2(0, 5f), player2.b2body.getWorldCenter(), true);
                if (controller.isBulletPressed() && shootTimer >= TIMER) {
                    shootTimer = 0;
                    bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
                }

                // if ((player.IsPlayerOnGround())) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    player2.b2body.applyLinearImpulse(new Vector2(0, 5f), player2.b2body.getWorldCenter(), true);
                // }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player2.b2body.getLinearVelocity().x <= 3)
                    player2.b2body.applyLinearImpulse(new Vector2(0.15f, 0), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player2.b2body.getLinearVelocity().x >= -3)
                    player2.b2body.applyLinearImpulse(new Vector2(-0.15f, 0), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    player2.b2body.applyLinearImpulse(new Vector2(0, -2f), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= TIMER) {
                    shootTimer = 0;
                    //bullets2 = new Bullets2(this, player.b2body.getPosition().x + .1f, player.b2body.getPosition().y + .2f);
                    bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .1f, player2.b2body.getPosition().y + .2f);
                    //Player.spacePressed = true;
                }
            }
        } else {
            if (player.currentState != Player.State.DEAD) {
                if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 3)
                    player.b2body.applyLinearImpulse(new Vector2(0.15f, 0), player.b2body.getWorldCenter(), true);
                if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -3)
                    player.b2body.applyLinearImpulse(new Vector2(-0.15f, 0), player.b2body.getWorldCenter(), true);
                if (controller.isJumpPressed() && player.b2body.getLinearVelocity().y == 0)
                    player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
                if (controller.isBulletPressed() && shootTimer >= TIMER) {
                    shootTimer = 0;
                    bulletFinal = new BulletFinal(this, player.b2body.getPosition().x + .2f, player.b2body.getPosition().y + .2f);
                }

                if ((player.IsPlayerOnGround())) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                        player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 3)
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -3)
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    player.b2body.applyLinearImpulse(new Vector2(0, -2f), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && shootTimer >= TIMER) {
                    shootTimer = 0;
                    //bullets2 = new Bullets2(this, player.b2body.getPosition().x + .1f, player.b2body.getPosition().y + .2f);
                    bulletFinal = new BulletFinal(this, player.b2body.getPosition().x + .1f, player.b2body.getPosition().y + .2f);
                    //Player.spacePressed = true;
                }

            }
        }
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin(); // Begin

        if(!FunGame.player2Selected)
            player.draw(game.batch);
        else
            player2.draw(game.batch);

        for(Enemy enemy : creator.getFlinkstone())
            enemy.draw(game.batch);

        for(Enemy enemy : creator.getBadGuys())
            enemy.draw(game.batch);

        bulletFinal.draw(game.batch);

        game.batch.end(); // End

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            //dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        hud.resize(width, height);
        controller.resize(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        game.dispose();
        bulletFinal.dispose();
    }

    public boolean gameOver(){
        if(FunGame.player2Selected)
            return player2.currentState == Player2.State.DEAD && player2.getStateTimer() > 3;
        else
            return player.currentState == Player.State.DEAD && player.getStateTimer() > 3;
    }

    public void levelComplete(){
        FunGame.LEVEL++;
        game.setScreen(new Level_complition(game));
        FunGame.SecondScreen = false;
        //dispose();
    }

    public SecondStage getScreen(){
        return this;
    }
}
