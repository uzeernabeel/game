package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.ginnie;

/**
 * Created by uzeer on 1/31/2017.
 */
public class Level_complition implements Screen {
    private Viewport viewport;
    private Stage stage;
    private FunGame game;
    BitmapFont fontHa;
    private Animation ginnieDance;
    Array<TextureRegion> frames;
    private TextureAtlas atlas2;
    private float stateTime;
    private SpriteBatch batch;
    private ginnie ginnies;

    public Level_complition(FunGame game) {

        this.game = game;

        fontHa = new BitmapFont();
        viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        ginnies = new ginnie(this, FunGame.V_WIDTH / 2 / FunGame.PPM, FunGame.V_HEIGHT / 2 / FunGame.PPM);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Hurray Level Complete!", font);
        Label playAgainLabel = new Label("Click To Play Next Level", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);

        Player.num = 0;
        stateTime = 0;
    }

    @Override
    public void show() {

    }

    public void update(float dt){
        ginnies.update(dt);
    }


    @Override
    public void render(float delta) {
        update(delta);

        game.batch.begin();
        ginnies.draw(game.batch);
        game.batch.end();


        if(Gdx.input.justTouched() && FunGame.SecondScreen) {
            game.setScreen(new SecondStage(game));
            dispose();
        }

        if(Gdx.input.justTouched() && FunGame.PlayScreen) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
        ginnies.dispose();
    }
}
