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
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

public class FunGame extends Game {
	public static final int V_WIDTH = 920;
	public static final int V_HEIGHT = 480;
    //public static final int V_WIDTH = 750;
    //public static final int V_HEIGHT = 330;
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
	public static final short OBJECT_HEAD_BIT = 512;


	public SpriteBatch batch;

	public static AssetManager manager;

	
	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("sounds/game background1.mp3", Music.class);
		//manager.load("sounds/background.mp3", Music.class);
		manager.load("sounds/is that all stranger.mp3", Sound.class);
		//manager.load("sounds/enemyHit.wav", Sound.class);
		manager.load("sounds/stranger stranger.mp3", Sound.class);
		manager.load("sounds/thank you.mp3", Sound.class);
		manager.load("sounds/welcome.mp3", Sound.class);
		manager.load("sounds/what are you buying.mp3", Sound.class);
		manager.load("sounds/what are you selling.mp3", Sound.class);
		manager.load("sounds/coin.wav", Sound.class);
		//manager.load("sounds/Decline.wav", Sound.class);
		//manager.load("sounds/enemy hit.wav", Sound.class);
		//manager.load("687474703a2f2f692e696d6775722e636f6d2f6856694b67416e2e706e67", Image.class);
		manager.finishLoading();

        //setScreen(new PlayScreen(this));

		setScreen(new SecondStage(this));
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
