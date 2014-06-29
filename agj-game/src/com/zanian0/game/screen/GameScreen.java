package com.zanian0.game.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    // Timers
    float frameTimer;
    float scoreTimer;
    float winTimer;
    float introTimer;
    
    private OrthographicCamera camera;

    private MyGdxGame game;

    // Player bounds.
    Rectangle BoundsLeft;
    Rectangle BoundsRight;
    Rectangle BoundsTop;
    Rectangle BoundsBottom;
    
    // Player paddles.
    PaddleUD paddleLeft;
    PaddleUD paddleRight;
    PaddleLR paddleTop;
    PaddleLR paddleBottom;

    // Game ball.
    Ball ball;
    
    // Determines the size of the player area.
    Rectangle fieldBounds;
    
    // Sizes of the paddle fields.
    float paddleFieldHeight;
    float paddleFieldWidth;
    
    // Last paddle hit color.
    Color lastHitColor;
    
    // Did this get instantiated?
    boolean wasCreated = false;
    
    //Game states.
    public enum GameStates {
        INTRO,
        PLAY,
        SCORE,
        RESET,
        WIN
    };
    
    GameStates state;
    
    Random rand = new Random();
    
    // DEFINES
    public static final int BALL_SIZE = 6;
    public static final float PADDLE_HEIGHT = 50.0f;
    public static final float PADDLE_WIDTH = 5.0f;
    public static final int STARTING_SCORE = 7;
    public static final float BALL_START_SPEED = 200.0f;
  
    
    public GameScreen(MyGdxGame game)
    {
        this.game = game; 
    }
    
    @Override
    public void show()
    {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        camera = new OrthographicCamera(w, h);
        
        camera.translate( w/2, h/2 );
        
        camera.update();
        
        renderer = new ShapeRenderer();
        
        renderer.setProjectionMatrix( camera.combined );
        
        // Set timers.
        frameTimer = 0.0f;
        scoreTimer = 0.0f;
        winTimer = 0.0f;
        introTimer = 0.0f;
        
        // Set up the field.
        fieldBounds = new Rectangle();
        
        fieldBounds.height = h - (h * 0.2f);
        fieldBounds.width = fieldBounds.height;
        fieldBounds.y = (h - fieldBounds.height) / 2.0f;
        fieldBounds.x = (w - fieldBounds.width) / 2.0f;
        
        // Set up paddle fields.
        paddleFieldHeight = (fieldBounds.height / 2.0f);
        paddleFieldWidth = 20.0f;
        
        // Instantiate player objects.
        BoundsLeft = new Rectangle();
        BoundsRight = new Rectangle();
        BoundsTop = new Rectangle();
        BoundsBottom = new Rectangle();
        paddleLeft = new PaddleUD();
        paddleRight = new PaddleUD();
        paddleTop = new PaddleLR();
        paddleBottom = new PaddleLR();
        
        // Instantiate the ball.
        ball = new Ball();
        
        // Initialize all of the players.
        initialize();
        
        // Init the ball color.
        lastHitColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        
        // Set the initial state.
        state = GameStates.INTRO;
        
        // Say that this was created.
        wasCreated = true;
        
        // Set the random ball flinger
        //rand = new Random();
    }

    @Override
    public void render( float delta )
    {
        
        switch (state)
        {
            case INTRO:
                introTimer += Gdx.graphics.getDeltaTime();
                
                if ( introTimer > 2.0 )
                    state = GameStates.PLAY;
                break;
                
            case PLAY:
                frameTimer += Gdx.graphics.getDeltaTime();
                
                if (frameTimer > 0.015f)
                {
                    frameTimer = 0.0f;
                    update();
                }

                break;
                
            case SCORE:
               
                scoreTimer += Gdx.graphics.getDeltaTime();
                if ( scoreTimer > 2.0f )
                    state = GameStates.RESET;
                break;
                
            case RESET:
                frameTimer = 0.0f;
                scoreTimer = 0.0f;
                
                reset();
                
                state = GameStates.PLAY;
                break;
                
            case WIN:
                
                winTimer += Gdx.graphics.getDeltaTime();
                if ( winTimer > 2.0f )
                    game.setScreen( game.introScreen );
                break;
                
            default:
                break;
        };
        
        draw();
        
        if ( Gdx.input.isKeyPressed( Input.Keys.C ) )
            Gdx.app.exit(); 
 
    }
    
    public void update()
    {
        
        input();

        ball.integrate( Gdx.graphics.getDeltaTime() );
        paddleLeft.integrate( Gdx.graphics.getDeltaTime() );
        paddleRight.integrate( Gdx.graphics.getDeltaTime() );
        paddleTop.integrate( Gdx.graphics.getDeltaTime() );
        paddleBottom.integrate( Gdx.graphics.getDeltaTime() );
        
        collision();
        
        scoreCheck();
        
    }
    
    private void scoreCheck()
    {
        int playerCount = 0;
        
        // Check how many people are in the game.
        if (paddleLeft.score > 0)
            playerCount++;
        if (paddleRight.score > 0)
            playerCount++;
        if (paddleTop.score > 0)
            playerCount++;
        if (paddleBottom.score > 0)
            playerCount++;
        
        if (playerCount <= 1)
            state = GameStates.WIN;

        
    }

    public void input()
    {
        // Check for reset.
        if ( Gdx.input.isKeyPressed( Input.Keys.ALT_LEFT ) )
            reset();
        
        paddleLeft.moveCheck();
        paddleRight.moveCheck();
        paddleTop.moveCheck();
        paddleBottom.moveCheck();
    }
    
    public void draw()
    {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        // Draw outlines.
        renderer.begin( ShapeRenderer.ShapeType.Rectangle );
            
            //Draw the field.
            renderer.setColor( lastHitColor );
            renderer.rect( fieldBounds.x, fieldBounds.y, fieldBounds.width, fieldBounds.height );
            // Draw the Left Paddle Field
            renderer.setColor( paddleLeft.color );
            renderer.rect( paddleLeft.outerBounds.x, paddleLeft.outerBounds.y, paddleLeft.outerBounds.width, paddleLeft.outerBounds.height );
            // Draw the Right Paddle Field
            renderer.setColor( paddleRight.color );
            renderer.rect( paddleRight.outerBounds.x, paddleRight.outerBounds.y, paddleRight.outerBounds.width, paddleRight.outerBounds.height );
            // Draw the Top Paddle Field
            renderer.setColor( paddleTop.color );
            renderer.rect( paddleTop.outerBounds.x, paddleTop.outerBounds.y, paddleTop.outerBounds.width, paddleTop.outerBounds.height );
            // Draw the Bottom Paddle Field
            renderer.setColor( paddleBottom.color );
            renderer.rect( paddleBottom.outerBounds.x, paddleBottom.outerBounds.y, paddleBottom.outerBounds.width, paddleBottom.outerBounds.height );

        renderer.end();
        
        // Draw Test Lines.
        /*
        renderer.begin(  ShapeRenderer.ShapeType.Line );
            renderer.setColor( new Color(1.0f, 1.0f, 1.0f, 1.0f) );
            renderer.line( (fieldBounds.x + fieldBounds.height / 2.0f), fieldBounds.y, (fieldBounds.x + fieldBounds.height / 2.0f), (fieldBounds.y + fieldBounds.height) );
            renderer.line( fieldBounds.x, (fieldBounds.y + fieldBounds.height / 2.0f), (fieldBounds.x + fieldBounds.width), (fieldBounds.y + fieldBounds.height / 2.0f) );
        renderer.end();
        */
        
        // Draw solids.
        renderer.begin( ShapeRenderer.ShapeType.FilledRectangle );

            ball.color = lastHitColor;
            ball.draw( renderer );
            paddleLeft.draw( renderer );
            
            paddleRight.draw( renderer );
            
            paddleTop.draw( renderer );
            
            paddleBottom.draw( renderer );
        
        renderer.end();
        
    }
    
    public void collision()
    {
        boolean collideFields = false;
        
        collideFields = collisionBallToPaddleFields();
        
        if ( !collideFields )
            collisionBallToWalls();
        
        collisionBallToPaddles();
    }
    
    public boolean collisionBallToPaddleFields()
    {
        boolean hitField = false;
        
        //Check for collision with the left paddle field.
        if ( (paddleLeft.score > 0) && ball.bounds.overlaps( paddleLeft.outerBounds ) )
        {
            hitField = true;
            
            if ( paddleLeft.outerBounds.contains( ball.bounds ))
            {
                //change state
                state = GameStates.SCORE;
                paddleLeft.score -= 1;
            }
            
        }
        
        //Check for collision with the right paddle field.
        if( (paddleRight.score > 0) && !hitField && ball.bounds.overlaps( paddleRight.outerBounds ) )
        {
            hitField = true;        
            
            if ( paddleRight.outerBounds.contains( ball.bounds ))
            {
                //change state
                state = GameStates.SCORE;
                paddleRight.score -= 1;
            }
        }
     
        //Check for collision with the top paddle field.
        if( (paddleTop.score > 0) && !hitField && ball.bounds.overlaps( paddleTop.outerBounds ) )
        {
            hitField = true;        
            
            if ( paddleTop.outerBounds.contains( ball.bounds ))
            {
                //change state
                state = GameStates.SCORE;
                paddleTop.score -= 1;
            }
        }
        
        //Check for collision with the bottom paddle field.
        if( (paddleBottom.score > 0) && !hitField && ball.bounds.overlaps( paddleBottom.outerBounds ) )
        {
            hitField = true;        
            
            if ( paddleBottom.outerBounds.contains( ball.bounds ))
            {
                //change state
                state = GameStates.SCORE;
                paddleBottom.score -= 1;
            }
        }
        
        return hitField;
    }
    
    public void collisionBallToWalls()
    {
        // If the Ball Top is greater than the Field Top
        if ( (ball.position.y + ball.bounds.height) > (fieldBounds.y + fieldBounds.height) )
        {
            ball.reflect( false, true );
            ball.setPosition( ball.position.x, fieldBounds.y + fieldBounds.height - ball.bounds.height );
        }
        
        // If the Ball Bottom is less than the Field Top
        if ( ball.position.y < fieldBounds.y )
        {
            ball.reflect( false, true );
            ball.setPosition( ball.position.x, fieldBounds.y );
        }
        
        // If the Ball Right is greater than the Field Right
        if ( (ball.position.x + ball.bounds.width) > (fieldBounds.x + fieldBounds.width) )
        {
            ball.reflect( true, false );
            ball.setPosition( fieldBounds.x + fieldBounds.width - ball.bounds.width, ball.position.y );
        }
        
        // If the Ball Left is less than the Field Left
        if ( ball.position.x < fieldBounds.x )
        {
            ball.reflect( true, false );
            ball.setPosition( fieldBounds.x, ball.position.y );
        }
        
    }

    public void collisionBallToPaddles()
    {
        // Collision - Left Paddle
        if ( (paddleLeft.score > 0) && ball.bounds.overlaps( paddleLeft.bounds ) )
        {
            lastHitColor = paddleLeft.color;
            
            // Check if the ball is heading towards the paddle.
            if ( ball.velocity.x < 0.0f )
            {
                ball.reflect( true, false );
                ball.increaseXVelocity();
                ball.paddleReflectX( paddleLeft.bounds, 0  );
            }
        }
        
        // Collision - Right Paddle
        if ( (paddleRight.score > 0) && ball.bounds.overlaps( paddleRight.bounds ) )
        {
            lastHitColor = paddleRight.color;
            
            // Check if the ball is heading towards the paddle.
            if ( ball.velocity.x > 0.0f )
            {
                ball.reflect( true, false );
                ball.increaseXVelocity();
                ball.paddleReflectX( paddleRight.bounds, 180);
            }
        }
        
        // Collision - Top Paddle
        if ( (paddleTop.score > 0) && ball.bounds.overlaps( paddleTop.bounds ) )
        {
            lastHitColor = paddleTop.color;
            
            // Check if the ball is heading towards the paddle.
            if ( ball.velocity.y > 0.0f )
            {
                ball.reflect( false, true );
                ball.increaseYVelocity();
                ball.paddleReflectY(paddleTop.bounds, 270);
            }
        }
        
        // Collision - Bottom Paddle
        if ( (paddleBottom.score > 0) && ball.bounds.overlaps( paddleBottom.bounds ) )
        {
            lastHitColor = paddleBottom.color;
            
            // Check if the ball is heading towards the paddle.
            if ( ball.velocity.y < 0.0f )
            {
                ball.reflect( false, true );
                ball.increaseYVelocity();
                ball.paddleReflectY(paddleBottom.bounds, 90);
            }
        } 
    }
    
    public void initialize()
    {
        // Initialize the ball.
        ball.setOuterBounds( fieldBounds );
        
        // Paddle variable. 
        float leftRightFieldY = fieldBounds.y + (fieldBounds.height / 2.0f) - (paddleFieldHeight / 2.0f);
        float topBottomFieldX = fieldBounds.x + (fieldBounds.width / 2.0f) - (paddleFieldHeight / 2.0f);
        
        // Initialize paddle left - Aqua Blue
        paddleLeft.outerBounds.set( fieldBounds.x - (paddleFieldWidth), leftRightFieldY, paddleFieldWidth, (fieldBounds.height / 2.0f) );
        paddleLeft.color = new Color(0.0f, 0.85f, 1.0f, 1.0f);
        paddleLeft.upRightMoveKey = Input.Keys.I;
        paddleLeft.downLeftMoveKey = Input.Keys.K;
        paddleLeft.score = STARTING_SCORE;

        // Initialize paddle right - Hot Pink
        paddleRight.outerBounds.set( (fieldBounds.x + fieldBounds.width), leftRightFieldY, paddleFieldWidth, (fieldBounds.height / 2.0f) );
        paddleRight.color = new Color(1.0f, 0.1f, 0.45f, 1.0f);
        paddleRight.upRightMoveKey = Input.Keys.Y;
        paddleRight.downLeftMoveKey = Input.Keys.N;
        paddleRight.score = STARTING_SCORE;
   
        // Initialize paddle top - Bright Yellow
        paddleTop.outerBounds.set( topBottomFieldX, (fieldBounds.y + fieldBounds.height), paddleFieldHeight, paddleFieldWidth );
        paddleTop.color = new Color(1.0f, 1.0f, 0.25f, 1.0f);
        paddleTop.upRightMoveKey = Input.Keys.RIGHT;
        paddleTop.downLeftMoveKey = Input.Keys.LEFT;
        paddleTop.score = STARTING_SCORE;

        // Initialize paddle bottom - Neon Green
        paddleBottom.outerBounds.set( topBottomFieldX, (fieldBounds.y) - (paddleFieldWidth), paddleFieldHeight, paddleFieldWidth );
        paddleBottom.color = new Color(0.18f, 1.0f, 0.09f, 1.0f);
        paddleBottom.upRightMoveKey = Input.Keys.G;
        paddleBottom.downLeftMoveKey = Input.Keys.D;
        paddleBottom.score = STARTING_SCORE;
        
        // Call reset to set paddle initial locations.
        reset();
    }
    
    public void reset()
    {
        float leftRightPaddleY = fieldBounds.y + (fieldBounds.height / 2.0f) - (PADDLE_HEIGHT / 2.0f);
        float topBottomPaddleX = fieldBounds.x + (fieldBounds.width / 2.0f) - (PADDLE_HEIGHT / 2.0f); 
        
        // Reset the ball
        ball.bounds.set( 0.0f, 0.0f, BALL_SIZE, BALL_SIZE );
        ball.center();
        ball.velocity.x = BALL_START_SPEED;
        ball.velocity.y = BALL_START_SPEED;
        int angle = rand.nextInt( 360 );
        if ( (angle % 90) == 0 )
            angle += 5;
        ball.velocity.setAngle( angle );
        
        // Reset Left Paddle
        paddleLeft.bounds.set( (paddleLeft.outerBounds.x + paddleLeft.outerBounds.width), leftRightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT );
        paddleLeft.center();

        // Reset Right Paddle
        paddleRight.bounds.set( (paddleRight.outerBounds.x - PADDLE_WIDTH), leftRightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT );
        paddleRight.center();
        
        // Reset Top Paddle
        paddleTop.bounds.set( topBottomPaddleX, paddleTop.outerBounds.y - PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_WIDTH  );
        paddleTop.center();
        
        // Reset Bottom Paddle
        paddleBottom.bounds.set( topBottomPaddleX, paddleBottom.outerBounds.y + paddleFieldWidth, PADDLE_HEIGHT, PADDLE_WIDTH  );
        paddleBottom.center();
        
        // Reset the game color.
        lastHitColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
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
        if ( wasCreated )
            renderer.dispose();
    }

}
