package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Sprites.Player;

/**
 * Created by uzeer on 1/31/2017.
 */
public class Level_complition implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    BitmapFont fontHa;

    public Level_complition(Game game) {
        this.game = game;
        fontHa = new BitmapFont();
        viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
        stage = new Stage(viewport, ((FunGame)game).batch);

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
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched() && FunGame.LEVEL == 2) {
            game.setScreen(new SecondStage((FunGame) game));
            dispose();
        }
        if(Gdx.input.justTouched() && FunGame.LEVEL == 3) {
            game.setScreen(new FinalStage((FunGame) game));
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
    }
}
