package com.zanian0.game.objects;

public class PaddleLR extends BaseObject
{
    
    public void center()
    {
        // Center the X-Axis
        position.x = (outerBounds.width / 2.0f) - (bounds.width / 2.0f);

    }
    
    public void updateBounds()
    {
        if (position.x + bounds.width > outerBounds.width)
        {
            position.x = outerBounds.width - bounds.width;
            
        }
        
        if (position.x < outerBounds.x)
        {
            position.x = outerBounds.x;
        }
        
        bounds.set(position.x, position.y, bounds.width, bounds.height);
    }  
}
