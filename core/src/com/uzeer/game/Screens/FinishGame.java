package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Sprites.Player;

/**
 * Created by uzeer on 3/8/2017.
 */
public class FinishGame implements Screen {

        private Viewport viewport;
        private Stage stage;
        private Game game;
        BitmapFont fontHa;
        private Music music;

        public FinishGame(Game game) {
            this.game = game;
            fontHa = new BitmapFont();
            viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
            stage = new Stage(viewport, ((FunGame)game).batch);

            Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

            Table table = new Table();
            table.center();
            table.setFillParent(true);

            Label highScore = new Label("High Score: " + String.valueOf(Hud.highScore()), font);
            Label coinsCollected = new Label("Coins Collected: " + String.valueOf(Hud.coins), font);
            Label gameOverLabel = new Label("Hurray Game Complete!", font);
            Label playAgainLabel = new Label("Click To Start Game Again", font);

            table.add(gameOverLabel).expandX();
            table.row();
            table.add(highScore);
            table.row();
            table.row();
            table.add(coinsCollected);
            table.row();
            table.add(playAgainLabel).expandX().padTop(10f);

            stage.addActor(table);

            Player.num = 0;

            FunGame.manager.get("sounds/thank you.mp3", Sound.class).play();
            music = FunGame.manager.get("sounds/powerUp.mp3", Music.class);
            music.setVolume(.9f);
            music.setLooping(true);
            music.play();
        }

        @Override
        public void show() {

        }

        @Override
        public void render(float delta) {
            if(Gdx.input.justTouched()) {
                game.setScreen(new StartScreen(game));
                music.stop();
                dispose();
            }

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height);
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