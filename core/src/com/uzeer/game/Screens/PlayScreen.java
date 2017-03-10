package com.uzeer.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Sprites.BulletFinal;
import com.uzeer.game.Sprites.Enemy;
import com.uzeer.game.Sprites.Fire;
import com.uzeer.game.Sprites.Flinkstone;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.Player2;
import com.uzeer.game.Tools.B2WorldCreator;
import com.uzeer.game.Tools.Controller;
import com.uzeer.game.Tools.WorldContactListner;

import java.util.ArrayList;

public class PlayScreen implements Screen {

    private FunGame game;

    private TextureAtlas atlas;
    private TextureAtlas atlas2;

    //game camera
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private B2WorldCreator creator;
    private World world;
    private Box2DDebugRenderer b2dr;

    //Sprites
    public Player player;
    public Player2 player2;


    private Controller controller;

    private Music music;

    private BulletFinal bulletFinal;

    private static final float TIMER = 0.5f;
    private float shootTimer;

    private int startingPoint;
    private int endPoint;


    public PlayScreen(FunGame game) {
        atlas = new TextureAtlas("sprite sheet.pack");
        atlas2 = new TextureAtlas("stuff.pack");
        this.game = game;

        FunGame.player2Selected = true;

        gamecam = new OrthographicCamera();
        FunGame.LEVEL = 1;
        gamePort = new FitViewport(FunGame.V_WIDTH / FunGame.PPM, FunGame.V_HEIGHT / FunGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();

        //selecting the stage # 1, 2, 3.
        if(FunGame.playScreenStages == 1)
            map = mapLoader.load("Small1.tmx");
        if(FunGame.playScreenStages == 2)
            map = mapLoader.load("Small2.tmx");
        if(FunGame.playScreenStages == 3)
            map = mapLoader.load("Small3.tmx");

        FileHandle file = Gdx.files.local("saveData.txt");
        file.writeString("2", false);


        renderer = new OrthogonalTiledMapRenderer(map, 1 / FunGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        controller = new Controller(game.batch);

        creator = new B2WorldCreator(this, player);

        //crating player
        player2 = new Player2(this);

        //creating bullet
        bulletFinal = new BulletFinal(this, 5, 70);

        world.setContactListener(new WorldContactListner());

        FunGame.manager.get("sounds/welcome.mp3", Sound.class).play();
        music = FunGame.manager.get("sounds/FinalGameBackground.mp3", Music.class);
        music.setVolume(.09f);
        music.setLooping(true);
        music.play();

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TextureAtlas getAtlas2(){
        return atlas2;
    }

    @Override
    public void show() {

    }

    private void handleInput(float dt) {
        // android specific code
        if (FunGame.player2Selected) {
            if (player2.currentState != Player2.State.DEAD) {
                if (controller.isRightPressed() && player2.b2body.getLinearVelocity().x <= 2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(0.125f, 0), player2.b2body.getWorldCenter(), true);
                if (controller.isLeftPressed() && player2.b2body.getLinearVelocity().x >= -2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(-0.125f, 0), player2.b2body.getWorldCenter(), true);
                if(player2.IsPlayerOnGround()) {
                    if (controller.isJumpPressed())
                        player2.b2body.applyLinearImpulse(new Vector2(0, 4.5f), player2.b2body.getWorldCenter(), true);
                }
                if (controller.isBulletPressed() && shootTimer >= TIMER) {
                    shootTimer = 0;
                    //player2.fire();
                    if(player2.isFlipX())
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
                    else
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x - .2f, player2.b2body.getPosition().y + .2f);
                }

                if ((player2.IsPlayerOnGround())) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                        player2.b2body.applyLinearImpulse(new Vector2(0, 4.85f), player2.b2body.getWorldCenter(), true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player2.b2body.getLinearVelocity().x <= 2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(0.125f, 0), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player2.b2body.getLinearVelocity().x >= -2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(-0.125f, 0), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    player2.b2body.applyLinearImpulse(new Vector2(0, -2f), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= TIMER) {
                    shootTimer = 0;
                    //bullets2 = new Bullets2(this, player.b2body.getPosition().x + .1f, player.b2body.getPosition().y + .2f);
                    //player2.fire();
                    if(player2.isFlipX())
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x - .3f, player2.b2body.getPosition().y + .2f);
                    else
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
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
                    if(player2.isFlipX())
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x - .3f, player2.b2body.getPosition().y + .2f);
                    else
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
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
                    if(player2.isFlipX())
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x - .3f, player2.b2body.getPosition().y + .2f);
                    else
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
                }

            }
        }
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        if(FunGame.player2Selected){  //Stage selecting (if) statements and deciding when to complete.
            player2.update(dt);
            if(FunGame.playScreenStages == 1)
                if (player2.b2body.getPosition().x > 32.70 && player2.b2body.getPosition().y >= 0.5) {// 173
                    FunGame.secondScreenStages = 1;
                    levelComplete();
                }
            if(FunGame.playScreenStages == 2)
                if (player2.b2body.getPosition().x > 32.48 && player2.b2body.getPosition().y >= 0.3) { // 173
                    FunGame.secondScreenStages = 2;
                    levelComplete();
                }
            if(FunGame.playScreenStages == 3)
                if (player2.b2body.getPosition().x > 32.48 && player2.b2body.getPosition().y >= 0.3){ // 173
                    FunGame.secondScreenStages = 3;
                    levelComplete();
                }
        } else {
            player.update(dt);
            if (player.b2body.getPosition().x > 173) // 173
                levelComplete();
        }

        for(Enemy enemy : creator.getEnemies())
            enemy.update(dt);

        /*for(Enemy enemy : creator.getFlinkstone())
            enemy.update(dt);

        for(Enemy enemy : creator.getBadGuys())
            enemy.update(dt);*/

        bulletFinal.update(dt);

        hud.update(dt);
        shootTimer += dt;

        if(FunGame.player2Selected) {
            if (player2.currentState != Player2.State.DEAD)
                gamecam.position.x = player2.b2body.getPosition().x;

        } else {
            if (player.currentState != Player.State.DEAD)
                gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(); //map render
        b2dr.render(world, gamecam.combined); //box2D renderer

        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin(); // Begin!

        if(!FunGame.player2Selected)
        player.draw(game.batch);
        else
        player2.draw(game.batch);

       /* for(Enemy enemy : creator.getFlinkstone())
            enemy.draw(game.batch);

        for(Enemy enemy : creator.getBadGuys())
            enemy.draw(game.batch); */
        for (Enemy enemy : creator.getEnemies())
             enemy.draw(game.batch);

        bulletFinal.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

    }

    public boolean gameOver(){
        if(FunGame.player2Selected)
            return player2.currentState == Player2.State.DEAD && player2.getStateTimer() > 3;
        else
            return player.currentState == Player.State.DEAD && player.getStateTimer() > 3;
    }

    public void levelComplete(){
        if(FunGame.playScreenStages == 1)
        FunGame.secondScreenStages = 1;
        if(FunGame.playScreenStages == 2)
            FunGame.secondScreenStages = 2;
        if(FunGame.playScreenStages == 3)
            FunGame.secondScreenStages = 3;

        FunGame.PlayScreen = false;
        FunGame.SecondScreen = true;
        game.setScreen(new Level_complition(game));
        //dispose();
    }

    public PlayScreen getScreen(){
        return this;
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
        atlas.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }


}
