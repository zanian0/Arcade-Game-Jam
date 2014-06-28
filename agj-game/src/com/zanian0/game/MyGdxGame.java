package com.zanian0.game;

import com.badlogic.gdx.Game;
import com.zanian0.game.screen.GameScreen;

public class MyGdxGame extends Game {

    // The screens used in the game.
    private GameScreen gameScreen;

	@Override
	public void create() 
	{
	    gameScreen = new GameScreen(this);
	    
	    setScreen(gameScreen);
	}

	@Override
	public void dispose() 
	{
	    gameScreen.dispose();
	}

}
