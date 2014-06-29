package com.zanian0.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zanian0.game.MyGdxGame;

public class IntroScreen implements Screen
{
    
    private SpriteBatch fontBatch;
    private BitmapFont font;
    
    private MyGdxGame game;
    
    public IntroScreen(MyGdxGame game)
    {
        this.game = game;
    }

    @Override
    public void render( float delta )
    {
        fontBatch.begin();
        
            font.draw( fontBatch, "HIT ANY BUTTON TO START", Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f );
            
        fontBatch.end();
        
        if ( Gdx.input.isButtonPressed( Input.Keys.C ))
            Gdx.app.exit();
        
        if ( Gdx.input.isButtonPressed( Input.Keys.X ) )
            game.setScreen(game.gameScreen);
        
    }

    @Override
    public void resize( int width, int height )
    {
        
    }

    @Override
    public void show()
    {
        font = new BitmapFont();                                                                        
        // set color to red
        font.setColor(Color.RED);
        
        fontBatch = new SpriteBatch();
    }

    @Override
    public void hide()
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

    @Override
    public void dispose()
    {
        fontBatch.dispose();
        
    }

}
