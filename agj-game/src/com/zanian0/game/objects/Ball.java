package com.zanian0.game.objects;

import com.badlogic.gdx.math.Rectangle;

public class Ball extends BaseObject
{
    public Ball ()
    {
        super( );
    }

    public static final float BALL_MAX_X_VELO = 500.0f;
    public static final float BALL_X_INC = 25.0f;
    
    public void center()
    {
        setPosition( outerBounds.x + (outerBounds.width / 2) - (bounds.width / 2) , outerBounds.y + (outerBounds.height / 2) - (bounds.height / 2) );
    }
    
    public void reflect(boolean xAxis, boolean yAxis)
    {
        if (xAxis)
            velocity.x = -velocity.x;
        if (yAxis)
            velocity.y = -velocity.y;
    }
    
    public void increaseXVelocity()
    {
        if (velocity.x < BALL_MAX_X_VELO ||
            velocity.x > -BALL_MAX_X_VELO )
        {
            if (velocity.x > 0.0f)
                velocity.x += BALL_X_INC;
            else
                velocity.x -= BALL_X_INC;
        }
    }
    
    public void increaseYVelocity()
    {
        if (velocity.y < BALL_MAX_X_VELO ||
            velocity.y > -BALL_MAX_X_VELO )
        {
            if (velocity.y > 0.0f)
                velocity.y += BALL_X_INC;
            else
                velocity.y -= BALL_X_INC;
        }
    }
    
    public void SetAngle(int angle)
    {
        velocity.setAngle( (float)angle );
    }
    
    public void paddleReflectX( Rectangle paddleBounds, int startAngle )
    {
        // Find the distance between the paddle center and ball center.
        float distance = (paddleBounds.y + (paddleBounds.height / 2.0f)) - (bounds.y + (bounds.height / 2.0f));
        
        float percentage = distance / ((paddleBounds.height / 2.0f) + bounds.height);
        
        int angle = (int)(45.0f * percentage);
        
        SetAngle(angle + startAngle);
        
    }
    
    public void paddleReflectY( Rectangle paddleBounds, int startAngle )
    {
        // Find the distance between the paddle center and ball center.
        float distance = (paddleBounds.x + (paddleBounds.width / 2.0f)) - (bounds.x + (bounds.width / 2.0f));
        
        float percentage = distance / ((paddleBounds.width / 2.0f) + bounds.height);
        
        int angle = (int)(45.0f * percentage);
        
        SetAngle(angle + startAngle);
        
    }
}
