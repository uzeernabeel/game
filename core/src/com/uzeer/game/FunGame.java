package com.uzeer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;
import com.uzeer.game.Screens.StartScreen;

public class FunGame extends Game {
	public static final int V_WIDTH1 = 920;
	public static final int V_HEIGHT1 = 480;
	public static final int V_WIDTH2 = 750;
	public static final int V_HEIGHT2 = 390;
    public static final int V_WIDTH = 750;
    public static final int V_HEIGHT = 330;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short COIN_BIT = 4;
	public static final short FIRE_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short GROUND_BIT = 256;
	public static final short BULLET_BIT = 512;
	public static final short ITEM_BIT = 2048;
	public static final short CHECK_POINT_BIT = 1024;

	public static int LEVEL;
	public static boolean player2Selected;
	public static boolean PlayScreen;
	public static boolean SecondScreen;
	public static boolean FinalScreen;
	public static int lives;

	public static int playScreenStages = 1;
	public static int secondScreenStages = 1;


	public SpriteBatch batch;

	public static AssetManager manager;

	
	@Override
	public void create () {

		//player2Selected = true;
		PlayScreen = true;
		//SecondScreen = true;

		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("sounds/FinalGameBackground.mp3", Music.class);
		manager.load("sounds/enemyHit1.wav", Sound.class);
		manager.load("sounds/enemyHit2.wav", Sound.class);
		manager.load("sounds/checkPoint.wav", Sound.class);
        manager.load("sounds/thank you.mp3", Sound.class);
		manager.load("sounds/welcome.mp3", Sound.class);
		manager.load("sounds/coin.wav", Sound.class);
		manager.load("sounds/Decline.wav", Sound.class);
		manager.load("sounds/hitByEnemy.wav", Sound.class);
        manager.load("sounds/powerUp.mp3", Sound.class);
		manager.finishLoading();


		//setScreen(new PlayScreen(this));
		//setScreen(new SecondStage(this));
		//setScreen(new FinalStage(this));
		setScreen(new StartScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
        super.render();

	}
}
