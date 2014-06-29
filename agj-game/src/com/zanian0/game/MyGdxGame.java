package com.zanian0.game;

import com.badlogic.gdx.Game;
import com.zanian0.game.screen.GameScreen;
import com.zanian0.game.screen.IntroScreen;

public class MyGdxGame extends Game {

    // The screens used in the game.
    public GameScreen gameScreen;
    public IntroScreen introScreen;

	@Override
	public void create() 
	{
	    gameScreen = new GameScreen(this);
	    introScreen = new IntroScreen(this);
	    
	    setScreen(introScreen);
	}

	@Override
	public void dispose() 
	{
	    gameScreen.dispose();
	    introScreen.dispose();
	}

}
