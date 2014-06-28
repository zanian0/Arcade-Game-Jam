package com.zanian0.game.screen;

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

    float frameTimer;
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
    
    // DEFINES
    public static final int BALL_SIZE = 6;
    public static final float PADDLE_HEIGHT = 50.0f;
    public static final float PADDLE_WIDTH = 5.0f;
  
    
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
        
        frameTimer = 0.0f;
        
        // Set up the field.
        fieldBounds = new Rectangle();
        
        fieldBounds.height = h - (h * 0.2f);
        fieldBounds.width = fieldBounds.height;
        fieldBounds.y = (h - fieldBounds.height) / 2.0f;
        fieldBounds.x = (w - fieldBounds.width) / 2.0f;
        
        // Set up paddle fields.
        paddleFieldHeight = (fieldBounds.height / 2.0f);
        paddleFieldWidth = 10.0f;
        
        // Instantiate player objects.
        BoundsLeft = new Rectangle();
        BoundsRight = new Rectangle();
        BoundsTop = new Rectangle();
        BoundsBottom = new Rectangle();
        paddleLeft = new PaddleUD();
        paddleRight = new PaddleUD();
        paddleTop = new PaddleLR();
        paddleBottom = new PaddleLR();
        
        //Instantiate the ball.
        ball = new Ball();
        
        //Initialize all of the players.
        initialize();
    }

    @Override
    public void render( float delta )
    {
        frameTimer += Gdx.graphics.getDeltaTime();
        
        if (frameTimer > 0.015f)
        {
            frameTimer = 0.0f;
            update();
        }
        
        draw();
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
        
    }
    
    public void input()
    {
        // Check if we need to kill the game.
        if ( Gdx.input.isKeyPressed(Input.Keys.C) )
            Gdx.app.exit();

        // Check if we need to kill the game.
        if ( Gdx.input.isKeyPressed(Input.Keys.R) )
            initialize();
        
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
            renderer.setColor( new Color(1,1,1,1) );
            renderer.rect( fieldBounds.x, fieldBounds.y, fieldBounds.width, fieldBounds.height );
            
            renderer.rect( paddleLeft.outerBounds.x, paddleLeft.outerBounds.y, paddleLeft.outerBounds.width, paddleLeft.outerBounds.height );
            renderer.rect( paddleRight.outerBounds.x, paddleRight.outerBounds.y, paddleRight.outerBounds.width, paddleRight.outerBounds.height );
            renderer.rect( paddleTop.outerBounds.x, paddleTop.outerBounds.y, paddleTop.outerBounds.width, paddleTop.outerBounds.height );
            renderer.rect( paddleBottom.outerBounds.x, paddleBottom.outerBounds.y, paddleBottom.outerBounds.width, paddleBottom.outerBounds.height );

        renderer.end();
        
        // Draw Test Lines.
        renderer.begin(  ShapeRenderer.ShapeType.Line );
            renderer.line( (fieldBounds.x + fieldBounds.height / 2.0f), fieldBounds.y, (fieldBounds.x + fieldBounds.height / 2.0f), (fieldBounds.y + fieldBounds.height) );
            renderer.line( fieldBounds.x, (fieldBounds.y + fieldBounds.height / 2.0f), (fieldBounds.x + fieldBounds.width), (fieldBounds.y + fieldBounds.height / 2.0f) );
        renderer.end();
        
        // Draw solids.
        renderer.begin( ShapeRenderer.ShapeType.FilledRectangle );

            ball.draw( renderer );
            paddleLeft.draw( renderer );
            
            paddleRight.draw( renderer );
            
            paddleTop.draw( renderer );
            
            paddleBottom.draw( renderer );
        
        renderer.end();
        
    }
    
    public void collision()
    {
        collisionBallToWall();
        
        
        
    }
    
    private void collisionBallToWall()
    {
        // If the Ball Top is greater than the Field Top
        if ( (ball.position.y + ball.bounds.height) > (fieldBounds.y + fieldBounds.height) )
            ball.reflect( false, true );
        
        // If the Ball Bottom is less than the Field Top
        if ( ball.position.y < fieldBounds.y )
            ball.reflect( false, true );
        
        // If the Ball Right is greater than the Field Right
        if ( (ball.position.x + ball.bounds.width) > (fieldBounds.x + fieldBounds.width) )
            ball.reflect( true, false ); 
        
        // If the Ball Left is less than the Field Left
        if ( ball.position.x < fieldBounds.x )
            ball.reflect( true, false ); 
        
    }

    public void initialize()
    {
        // Initialize the ball.
        ball.setOuterBounds( fieldBounds );
        ball.bounds.set( 0.0f, 0.0f, BALL_SIZE, BALL_SIZE );
        ball.center();
        ball.velocity.x = 200;
        ball.velocity.setAngle( 125 );
        
        // Paddle variable. 
        float leftRightPaddleY = fieldBounds.y + (fieldBounds.height / 2.0f) - (PADDLE_HEIGHT / 2.0f);
        float leftRightFieldY = fieldBounds.y + (fieldBounds.height / 2.0f) - (paddleFieldHeight / 2.0f);
        float topBottomPaddleX = fieldBounds.x + (fieldBounds.width / 2.0f) - (PADDLE_HEIGHT / 2.0f); 
        float topBottomFieldX = fieldBounds.x + (fieldBounds.width / 2.0f) - (paddleFieldHeight / 2.0f);
        
        // Initialize paddle left - Aqua Blue
        paddleLeft.outerBounds.set( fieldBounds.x - (paddleFieldWidth), leftRightFieldY, paddleFieldWidth, (fieldBounds.height / 2.0f) );
        paddleLeft.bounds.set( (paddleLeft.outerBounds.x + paddleLeft.outerBounds.width), leftRightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT );
        paddleLeft.center();
        paddleLeft.color = new Color(0.0f, 0.85f, 1.0f, 1.0f);
        paddleLeft.upRightMoveKey = Input.Keys.W;
        paddleLeft.downLeftMoveKey = Input.Keys.S;

        // Initialize paddle right - Hot Pink
        paddleRight.outerBounds.set( (fieldBounds.x + fieldBounds.width), leftRightFieldY, paddleFieldWidth, (fieldBounds.height / 2.0f) );
        paddleRight.bounds.set( (paddleRight.outerBounds.x - PADDLE_WIDTH), leftRightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT );
        paddleRight.center();
        paddleRight.color = new Color(1.0f, 0.1f, 0.45f, 1.0f);
        paddleRight.upRightMoveKey = Input.Keys.W;
        paddleRight.downLeftMoveKey = Input.Keys.S;
   
        // Initialize paddle top - Bright Yellow
        paddleTop.outerBounds.set( topBottomFieldX, (fieldBounds.y + fieldBounds.height), paddleFieldHeight, paddleFieldWidth );
        paddleTop.bounds.set( topBottomPaddleX, paddleTop.outerBounds.y - PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_WIDTH  );
        paddleTop.center();
        paddleTop.color = new Color(1.0f, 1.0f, 0.25f, 1.0f);
        paddleTop.upRightMoveKey = Input.Keys.D;
        paddleTop.downLeftMoveKey = Input.Keys.A;

        // Initialize paddle top - Neon Green
        paddleBottom.color = new Color(0.18f, 1.0f, 0.09f, 1.0f);
        paddleBottom.outerBounds.set( topBottomFieldX, (fieldBounds.y) - (paddleFieldWidth), paddleFieldHeight, paddleFieldWidth );
        paddleBottom.bounds.set( topBottomPaddleX, paddleBottom.outerBounds.y + paddleFieldWidth, PADDLE_HEIGHT, PADDLE_WIDTH  );
        paddleBottom.center();
        paddleBottom.upRightMoveKey = Input.Keys.D;
        paddleBottom.downLeftMoveKey = Input.Keys.A;
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
