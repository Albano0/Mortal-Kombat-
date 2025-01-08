package com.java.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MortalKombat extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, tScorpion, tSubZero, tBall;
	private Sprite Scorpion, SubZero, Ball;
	private float posX, posY, velocity, XBall, YBall;
	private boolean special;

	@Override
	public void create () {

		batch = new SpriteBatch();
		img = new Texture("Arenas/Arena Shrine.png");
		posX = 0;
		posY = 0;
		velocity = 10;
		
		tScorpion = new Texture("Personagens/Scorpion/A.jpg");
		Scorpion = new Sprite(tScorpion);
		
		tSubZero = new Texture("Personagens/SubZero/B.jpg");
		SubZero = new Sprite(tSubZero);

		tBall = new Texture("Personagens/Scorpion/Fire.jpg");
		Ball = new Sprite(tBall);
		XBall = posX;
		YBall = posY;

	}

	@Override
	public void render () {

		this.movePlayer();
		this.moveBall();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);

		if(special){
			batch.draw(Scorpion, posX, posY);
		}
		batch.draw(Ball, XBall, YBall);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	private void movePlayer(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			if(posX < Gdx.graphics.getWidth() - Scorpion.getWidth()){
			posX += velocity;
			}
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			if(posX > 0)
			posX -= velocity;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			if(posY < Gdx.graphics.getWidth() - Scorpion.getWidth())
			posY += velocity;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			if(posY > 0)
			posY -= velocity;
		}

	}

	private void moveBall(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !special){
			XBall += 20;
			special = true;
			YBall = posY;
		}

		if(special){
			if(XBall < Gdx.graphics.getWidth()){
			XBall += 20;
			}else{
				XBall = posX;
				special = false;
			}
		}else{
			XBall = posX;
			YBall = posY;
		}
		
		
	}
}
