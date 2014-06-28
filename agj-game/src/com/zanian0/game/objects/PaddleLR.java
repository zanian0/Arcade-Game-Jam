package com.zanian0.game.objects;

import com.badlogic.gdx.Gdx;

public class PaddleLR extends BaseObject
{
    public PaddleLR ()
    {
        super();
    }
    
    
    public void center()
    {
        // Center the X-Axis
        position.x = outerBounds.x + (outerBounds.width / 2.0f) - (bounds.width / 2.0f);
        
        position.y = bounds.y;

    }
    
    public void updateBounds()
    {
        if ( (position.x + bounds.width) > (outerBounds.x + outerBounds.width) )
        {
            position.x = outerBounds.x + outerBounds.width - bounds.width;
            
        }
        
        if (position.x < outerBounds.x)
        {
            position.x = outerBounds.x;
        }
        
        bounds.set(position.x, position.y, bounds.width, bounds.height);
    }
    
    public void moveCheck()
    {
        velocity.x = 0.0f;
        
        if ( Gdx.input.isKeyPressed(upRightMoveKey) )
            velocity.x += PADDLE_SPEED;
        
        if ( Gdx.input.isKeyPressed(downLeftMoveKey) )
            velocity.x -= PADDLE_SPEED;;      
    }
}
