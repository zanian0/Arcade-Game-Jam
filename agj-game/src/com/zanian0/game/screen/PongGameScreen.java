package com.zanian0.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zanian0.game.MyGdxGame;

public class PongGameScreen implements Screen
{
    private OrthographicCamera camera;
    
    //Renderers
    private ShapeRenderer renderer;
    
    // Screen size.
    float w;
    float h;
    
    float frameTimer;

    private MyGdxGame game;
    
    public PongGameScreen(MyGdxGame game)
    {
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        //camera = new OrthographicCamera(1, h/w);
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
        
        //renderer.setProjectionMatrix( camera.combined );
        
        renderer.begin( ShapeRenderer.ShapeType.Rectangle );
            renderer.setColor( new Color(1,1,1,1) );
            renderer.rect( 20, 20, w-40, h-40 );
        renderer.end();
        
    }
    
    @Override
    public void resize( int width, int height )
    {
        //camera = new OrthographicCamera(1, h/w); 
    }

    @Override
    public void show()
    {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        camera = new OrthographicCamera(1, h/w);
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
