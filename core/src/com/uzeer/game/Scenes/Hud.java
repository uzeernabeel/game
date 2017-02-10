package com.uzeer.game.Scenes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;

/**
 * Created by Uzeer on 12/25/2016.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    protected static Integer score;
    public static Integer coins;

    Label countLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label GameLabel;
    Label coin;
    static Label coinLabel;

    Label chances;
    static Label chancesNumber;

    static int chancesLeft;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 500;
        coins = 0;
        chancesLeft = 4;

        viewport = new FitViewport(FunGame.V_WIDTH, FunGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%08d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        chancesNumber = new Label(String.format("%01d", chancesLeft), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(" - 2 - ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("World", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        GameLabel = new Label("Fun", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        chances = new Label("Chances Left", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coin = new Label("Stars: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(GameLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(chances).expandX().padTop(10);
        table.add(coin).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countLabel).expandX();
        table.add(chancesNumber).expandX();
        table.add(coinLabel).expandX();

        stage.addActor(table);

    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%08d", score));
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    public static void addCoin(int value){
        coins += value;
        coinLabel.setText(String.format("%03d", coins));
    }

    public static void chances(int i) {
        chancesLeft = i;
        chancesNumber.setText(String.format("%01d", chancesLeft));
    }
}
