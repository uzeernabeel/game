package com.uzeer.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.uzeer.game.Sprites.BulletFinal;
import com.uzeer.game.Sprites.Bullets;
import com.uzeer.game.Sprites.Bullets2;
import com.uzeer.game.Sprites.Enemy;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Tools.B2WorldCreator;
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
    private TextureMapObjectRenderer textureMapObjectRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    public Player player;
    private B2WorldCreator creator;

    private Music music;
    private boolean playerIsTouchingTheGround;

    private ArrayList<Bullets> bullets;

    private Bullets2 bullets2;
    private BulletFinal bulletFinal;

    private float maxPosition;
    private float minPosition;

    TextureMapObject textureMapObject;
    TextureMapObject textureObject;

    SpriteBatch spriteBatch;

    public SecondStage(FunGame game){
        atlas = new TextureAtlas("sprite sheet2.pack");
        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FunGame.V_WIDTH / FunGame.PPM, FunGame.V_HEIGHT / FunGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("FinalSecond.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FunGame.PPM);
        textureMapObjectRenderer = new TextureMapObjectRenderer(map, 1 / FunGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2 , 0);

       gamecam.position.x = (gamePort.getScreenWidth() / 2) + 4f;

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(this);

        bulletFinal = new BulletFinal(this, 6, 70);
        //bullets2 = new Bullets2(this, 5, 70);

        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListner());

        maxPosition = 0;
        minPosition = 0;

        //FunGame.manager.get("sounds/welcome.mp3", Sound.class).play();
        music = FunGame.manager.get("sounds/background.mp3", Music.class);
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

        player.update(dt);

        for(Enemy enemy : creator.getFlinkstone())
            enemy.update(dt);

        // bullets2.update(dt);
        bulletFinal.update(dt);

        hud.update(dt);

        minPosition = player.b2body.getPosition().y;

        if (player.currentState != Player.State.DEAD) {
            if(minPosition >= maxPosition) {
                gamecam.position.y = player.b2body.getPosition().y;
                maxPosition = player.b2body.getPosition().y;
            }
            if(minPosition < maxPosition + 3f)
                gamecam.position.y = player.b2body.getPosition().y;
        }
        gamecam.update();
        renderer.setView(gamecam);
        textureMapObjectRenderer.setView(gamecam);

    }

    private void handleInput(float dt) {
        if(player.currentState != Player.State.DEAD){
            if ((player.IsPlayerOnGround())) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                player.b2body.applyLinearImpulse(new Vector2(0, -2f), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                //bullets2 = new Bullets2(this, player.b2body.getPosition().x + .1f, player.b2body.getPosition().y + .2f);
                bulletFinal = new BulletFinal(this, player.b2body.getPosition().x + .2f, player.b2body.getPosition().y + .2f);
                player.spacePressed(dt);

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
        //renderer.renderObjects(map.getLayers().get(0));
        //renderer.renderObjects(map.getLayers().get(1));


        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();
        player.draw(game.batch);

        /*for (MapObject object : map.getLayers().get(1).getObjects()) {
            //textureMapObjectRenderer.renderObject(object);

            if (object instanceof TextureMapObject) {
                textureObject = (TextureMapObject) object;
                game.batch.draw(
                        textureObject.getTextureRegion(),
                        textureObject.getX(),
                        textureObject.getY()
                );
            }
        }
    */
        for(Enemy enemy : creator.getFlinkstone())
            enemy.draw(game.batch);

       // textureMapObjectRenderer.draw(game.batch);

        //bullets2.draw(game.batch);
        bulletFinal.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if(levelComplete()){
            game.setScreen(new Level_complition(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
    }

    public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3)
            return true;
        else
            return false;
    }

    public boolean levelComplete(){
        if(player.b2body.getPosition().y > 8294 / FunGame.PPM)
            return true;
        else
            return false;
    }
}
