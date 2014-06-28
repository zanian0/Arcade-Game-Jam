package com.zanian0.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BaseObject
{
    public Vector2 position;
    
    public Vector2 velocity;
    
    public Rectangle bounds;
    
    public Color color;
    
    public Rectangle outerBounds;
    
    public int upRightMoveKey;
    
    public int downLeftMoveKey;
    
    public int moveDir;
    
    public boolean isActive;
    
    public static final float PADDLE_SPEED = 200.0f;
    
    public int score;
    
    BaseObject ()
    {
        bounds = new Rectangle( 0.0f, 0.0f, 0.0f, 0.0f );
        
        outerBounds = new Rectangle( 0.0f, 0.0f, 0.0f, 0.0f );
        
        color = new Color( 1.0f, 1.0f, 1.0f, 1.0f );

        position = new Vector2( 0.0f, 0.0f );
        
        velocity = new Vector2( 0.0f, 0.0f );
        
        upRightMoveKey = 0;
        
        downLeftMoveKey = 0;
        
        moveDir = 0;
        
        isActive = false;
        
        score = 1;
    }
    
    public void integrate( float dt ) 
    {
        position.add( velocity.x * dt, velocity.y * dt );
        
        updateBounds();
    }
    
    public void updateBounds() 
    {
        bounds.set( position.x, position.y, bounds.width, bounds.height );
    }
    
    public void SetColor( Color colorInput )
    {
        color = colorInput;
    }

    public void draw( ShapeRenderer renderer )
    {
        if ( score > 0 )
        {
            renderer.setColor( color );
            renderer.filledRect( bounds.x, bounds.y, bounds.width, bounds.height );
        }
    }
    
    public void setPosition( float x, float y )
    {
        position.x = x;
        position.y = y;
        
        updateBounds();
    }
    
    public void setOuterBounds(Rectangle ob)
    {
        outerBounds = ob;
    }
}
