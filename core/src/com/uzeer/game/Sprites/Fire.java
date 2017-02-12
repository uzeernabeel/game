package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.FinalStage;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 12/26/2016.
 */


public class Fire extends InteractiveTileObject {


    public Fire(PlayScreen screen, Rectangle bounds, String value) {
        super(screen, bounds, value);

        fixture.setUserData(this);
        setCategoryFilter(FunGame.FIRE_BIT);

    }

    public Fire(SecondStage screen, Rectangle bounds, String value) {
        super(screen, bounds, value);

        fixture.setUserData(this);
        setCategoryFilter(FunGame.FIRE_BIT);

    }

    public Fire(FinalStage screen, Rectangle bounds, String value) {
        super(screen, bounds, value);

        fixture.setUserData(this);
        setCategoryFilter(FunGame.FIRE_BIT);

    }

    @Override
    public void bodyHit() {
        Gdx.app.log("Fire Touch", "Die Ha Ha!");
        FunGame.manager.get("sounds/Decline.wav", Sound.class).play();
        Player.num++;
        if(Player.num > 3)
        Player.playerDead = true;
        Hud.addScore(-1000);
    }
}
