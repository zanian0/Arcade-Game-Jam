package com.zanian0.game.objects;

public class PaddleUD extends BaseObject
{

    public void center()
    {
        // Center the Y-Axis
        position.y = (outerBounds.height / 2.0f) - (bounds.height / 2.0f);
        
    }
    
    public void updateBounds()
    {
        if (position.y + bounds.height > outerBounds.height)
        {
            position.y = outerBounds.height - bounds.height;
            
        }
        
        if (position.y < outerBounds.y)
        {
            position.y = outerBounds.y;
        }
        
        bounds.set(position.x, position.y, bounds.width, bounds.height);
    }
    
}
