package com.zanian0.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.zanian0.game.MyGdxGame;

public class GameScreen implements Screen
{
    //Renderers
    private ShapeRenderer renderer;
    
    // Screen size.
    float w;
    float h;

    float frameTimer;

    private MyGdxGame game;

    Rectangle BoundsLeft;
    Rectangle BoundsRight;
    Rectangle BoundsTop;
    Rectangle BoundsBottom;
    
    
    public GameScreen(MyGdxGame game)
    {
        this.game = game;
    }

    @Override
    public void render( float delta )
    {
        frameTimer += Gdx.graphics.getDeltaTime();
        
        if (frameTimer > 0.15f)
        {
            frameTimer = 0.0f;
            update();
        }
        
        draw();
    }

    public void update()
    {
        
        
    }
    
    public void draw()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
     
        renderer.begin( ShapeRenderer.ShapeType.Rectangle );
            
            renderer.setColor( new Color(1,1,1,1) );
            renderer.rect( 20, 20, w-40, h-40 );
        renderer.end();
        
    }
    
    @Override
    public void resize( int width, int height )
    {

    }

    @Override
    public void show()
    {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        renderer = new ShapeRenderer();
        
        frameTimer = 0.0f;
    }

    @Override
    public void hide()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        
    }

}
