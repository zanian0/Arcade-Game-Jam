package com.zanian0.game;

import com.badlogic.gdx.Game;
import com.zanian0.game.screen.PongGameScreen;

public class MyGdxGame extends Game {

    // The screens used in the game.
    //private MenuScreen menuScreen;
    //private CharSelectScreen charSelectScreen;
    private PongGameScreen gameScreen;
    //private OptionsScreen optionsScreen;
    
    
    
/*   
    private OrthographicCamera camera;
	//private SpriteBatch batch;
	
	private ShapeRenderer renderer;
	//private Texture texture;
	//private Sprite sprite;
	
	// Screen size.
	float w;
	float h;
	
	float frameTimer;
*/	
	@Override
	public void create() 
	{
	    gameScreen = new PongGameScreen(this);
	    
	    setScreen(gameScreen);
/*	    
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		//batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		
		frameTimer = 0.0f;
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
*/
	}

	@Override
	public void dispose() 
	{
		//batch.dispose();
		//texture.dispose();
	    gameScreen.dispose();
	}

	@Override
	public void render() 
	{
	    
	}

	public void update()
	{
	    
	}
	
	public void draw()
	{
	    
	}
	
	@Override
	public void resize(int width, int height) 
	{
	    
	}

	@Override
	public void pause() 
	{
	    
	}

	@Override
	public void resume() 
	{
	    
	}
}
