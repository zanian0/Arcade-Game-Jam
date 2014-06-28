package com.zanian0.game.objects;

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
        setPosition( (outerBounds.width / 2) - (bounds.width / 2) , (outerBounds.height / 2) - (bounds.height / 2) );
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
    
    public void SetAngle(int angle)
    {
        velocity.setAngle( (float)angle );
        
        
    }
}
