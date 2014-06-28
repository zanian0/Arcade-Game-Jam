package com.zanian0.game.objects;

import com.badlogic.gdx.Gdx;

public class PaddleUD extends BaseObject
{
    public PaddleUD ()
    {
        super();
    }
    
    public void center()
    {
        // Center the Y-Axis
        position.y = outerBounds.y + (outerBounds.height / 2.0f) - (bounds.height / 2.0f);
        
        position.x = bounds.x;
        
        updateBounds();
        
    }
    
    public void updateBounds()
    {
        if ( (position.y + bounds.height) > (outerBounds.y + outerBounds.height) )
        {
            position.y = outerBounds.y + outerBounds.height - bounds.height;
            
        }
        
        if (position.y < outerBounds.y)
        {
            position.y = outerBounds.y;
        }
        
        bounds.set(position.x, position.y, bounds.width, bounds.height);
    }
 
    public void moveCheck()
    {
        velocity.y = 0.0f;
        
        if ( Gdx.input.isKeyPressed(upRightMoveKey) )
            velocity.y += PADDLE_SPEED;
        
        if ( Gdx.input.isKeyPressed(downLeftMoveKey) )
            velocity.y -= PADDLE_SPEED;;      
    }
}
