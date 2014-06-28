package com.zanian0.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.zanian0.game.MyGdxGame;
import com.zanian0.game.objects.Ball;
import com.zanian0.game.objects.PaddleLR;
import com.zanian0.game.objects.PaddleUD;

public class GameScreen implements Screen
{
    //Renderers
    private ShapeRenderer renderer;
    
    // Screen size.
    float w;
    float h;

    float frameTimer;

    private MyGdxGame game;

    // Player bounds.
    Rectangle BoundsLeft;
    Rectangle BoundsRight;
    Rectangle BoundsTop;
    Rectangle BoundsBottom;
    
    // Player paddles.
    PaddleLR paddleLeft;
    PaddleLR paddleRight;
    PaddleUD paddleTop;
    PaddleUD Bottom;

    // Game ball.
    Ball ball;
    
    // Determines the size of the player area.
    Rectangle fieldBounds;
    
    public GameScreen(MyGdxGame game)
    {
        this.game = game; 
    }
    
    @Override
    public void show()
    {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        renderer = new ShapeRenderer();
        
        frameTimer = 0.0f;
        
        fieldBounds = new Rectangle();
        
        fieldBounds.height = h - (h * 0.2f);
        fieldBounds.width = fieldBounds.height;
        fieldBounds.y = (h - fieldBounds.height) / 2.0f;
        fieldBounds.x = (w - fieldBounds.width) / 2.0f;
        
        // Instantiate player objects.
        BoundsLeft = new Rectangle();
        BoundsRight = new Rectangle();
        BoundsTop = new Rectangle();
        BoundsBottom = new Rectangle();
        paddleLeft = new PaddleLR();
        paddleRight = new PaddleLR();
        paddleTop = new PaddleUD();
        Bottom = new PaddleUD();
        
        
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
        collision();
        
    }
    
    public void draw()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
     
        renderer.begin( ShapeRenderer.ShapeType.Rectangle );
            
            renderer.setColor( new Color(1,1,1,1) );
            renderer.rect( fieldBounds.x, fieldBounds.y, fieldBounds.width, fieldBounds.height );
            
            
        renderer.end();
        
    }
    
    public void collision()
    {
        
        
    }
    
    @Override
    public void resize( int width, int height )
    {

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
