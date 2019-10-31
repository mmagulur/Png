package com.banedecane.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Game extends ApplicationAdapter {
	int leftPaddleX,leftPaddleY;
	int rightPaddleX,rightPaddleY;
	int screenWidth,screenHeight;
	int paddleWidth,paddleHeight;

	int paddleSpeed=8;

	float ballX,ballY;
	int ballWidth,ballHeight;

	ShapeRenderer sr;
	Vector2 ballVelocity;

	@Override
	public void create () {
		sr = new ShapeRenderer();
		ballVelocity= new Vector2(100,50);

		screenWidth=640;
		screenHeight=480;

		paddleWidth=25;
		paddleHeight=150;

		leftPaddleX=0;
		leftPaddleY=screenHeight/2-paddleHeight/2;

		rightPaddleX=screenWidth-paddleWidth;
		rightPaddleY=screenHeight/2-paddleHeight/2;

		ballWidth=10;
		ballHeight=10;

		ballX=screenWidth/2-ballWidth/2;
		ballY=screenHeight/2-ballHeight/2;
	}

	@Override
	public void render () {
		// Clears the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Check input to update paddle position
		updatePaddlePositions();

		// Update of ball Position
		updateBallPosition();

		// Check Collisions
		checkCollisions();

		// Draw the objects
		drawObjects();

	}

	private void updatePaddlePositions() {
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			leftPaddleY += paddleSpeed;
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
			leftPaddleY -= paddleSpeed;

		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			rightPaddleY += paddleSpeed;
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			rightPaddleY -= paddleSpeed;
	}
	private void updateBallPosition(){
		ballX= ballVelocity.x*(Gdx.graphics.getDeltaTime())+ballX;
		ballY= ballVelocity.y*(Gdx.graphics.getDeltaTime())+ballY;
	}

	private void drawObjects(){
		// Draw left paddle
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.rect(leftPaddleX,leftPaddleY,paddleWidth,paddleHeight);
		sr.end();

		// Draw Right paddle
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.rect(rightPaddleX,rightPaddleY,paddleWidth,paddleHeight);
		sr.end();

		//Draw ball
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.rect(ballX,ballY,ballWidth,ballHeight);
		sr.end();
	}

	private void checkCollisions(){
		// Ball hits top of screen
		if (ballY + ballHeight> screenHeight){
			ballVelocity.y = -ballVelocity.y;
		}

		else if (ballY < 0){
			ballVelocity.y= -ballVelocity.y;
		}
		else if(ballX + ballWidth > screenWidth){
			//ballX values: 638.123, 639.428, 640.128
			ballX=screenWidth/2-ballWidth/2;
			ballY=screenHeight/2-ballHeight/2;
		}
		else if(ballX < 0){
			ballX=screenWidth/2-ballWidth/2;
			ballY=screenHeight/2-ballHeight/2;
		}
		else if (ballX < paddleWidth && ballY < leftPaddleY+paddleHeight && ballY > leftPaddleY){
			ballVelocity.x= -ballVelocity.x;
		}
		else if(ballX + ballWidth > screenWidth-paddleWidth && ballY < rightPaddleY+paddleHeight && ballY > rightPaddleY){
			ballVelocity.x = -ballVelocity.x;
		}

	}

	@Override
	public void dispose () {
	}
}
