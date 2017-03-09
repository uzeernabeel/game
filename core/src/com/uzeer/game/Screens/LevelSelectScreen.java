package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Sprites.Player;

/**
 * Created by uzeer on 3/8/2017.
 */

public class LevelSelectScreen implements Screen {

    private Game game;
    private BitmapFont fontHa;
    private Viewport viewport;
    private Stage stage;

    boolean onePressed;
    boolean twoPressed;
    boolean threePressed;
    boolean fourPressed;

    Image oneImg;
    Image twoImg;
    Image threeImg;
    Image fourImg;
    private Texture texture;

    public LevelSelectScreen(Game game){
    this.game = game;
    fontHa = new BitmapFont();
    viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
    stage = new Stage(viewport, ((FunGame)game).batch);
    Gdx.input.setInputProcessor(stage);

    texture = new Texture("levelSelect.png");

    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

    Table table = new Table();
    table.center();
    table.setFillParent(true);


    Label gameOverLabel = new Label("Select The Character", font);
    table.add(gameOverLabel);
    table.row();


    oneImg = new Image(new TextureRegion(texture, 0, 0, 242, 314));
    oneImg.setSize(242, 314);
    oneImg.addListener(new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            onePressed = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            onePressed = false;
        }
    });

        twoImg = new Image(new TextureRegion(texture, 243, 0, 242, 314));
        twoImg.setSize(242, 314);
        twoImg.addListener(new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            twoPressed = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            twoPressed = false;
        }
    });

        threeImg = new Image(new TextureRegion(texture, 0, 315, 242, 314));
        threeImg.setSize(242, 314);
        threeImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                threePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                threePressed = false;
            }
        });

        fourImg = new Image(new TextureRegion(texture, 243, 315, 242, 314));
        fourImg.setSize(242, 314);
        fourImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fourPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                fourPressed = false;
            }
        });


        table.add(oneImg).size(oneImg.getWidth(), oneImg.getHeight());
        table.add(twoImg).size(twoImg.getWidth(), twoImg.getHeight());
        table.add(threeImg).size(threeImg.getWidth(), threeImg.getHeight());
        table.add(fourImg).size(fourImg.getWidth(), fourImg.getHeight());

        stage.addActor(table);

        Player.num = 0;
}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if(onePressed) {
            FunGame.playScreenStages = 1;
            FunGame.PlayScreen = true;
            FunGame.SecondScreen = false;
            game.setScreen(new PlayScreen((FunGame) game));
            dispose();
        } else if(twoPressed) {
            FunGame.secondScreenStages = 1;
            FunGame.SecondScreen = true;
            FunGame.PlayScreen = false;
            game.setScreen(new SecondStage((FunGame) game));
            dispose();
        } else if(threePressed) {
            FunGame.playScreenStages = 2;
            FunGame.PlayScreen = true;
            FunGame.SecondScreen = false;
            game.setScreen(new PlayScreen((FunGame) game));
            dispose();
        } else if(fourPressed) {
            FunGame.secondScreenStages = 2;
            FunGame.SecondScreen = true;
            FunGame.PlayScreen = false;
            game.setScreen(new SecondStage((FunGame) game));
            dispose();
        }
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
