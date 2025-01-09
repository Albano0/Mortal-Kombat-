package com.java.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.java.game.AssetManager.AssetControl;

public class MortalKombat extends ApplicationAdapter {
    private SpriteBatch batch;
    private AssetControl assetControl;
    private Animation<TextureRegion> scorpionIdleAnimation;
    private Animation<TextureRegion> scorpionMoveAnimation;
    private Animation<TextureRegion> ballAnimation;
    private TextureRegion currentFrame;
    private TextureRegion currentBallFrame;
    private TextureRegion background;
    private float posX, posY, velocity;
    private float ballX, ballY;
    private boolean isMoving, isSpecialActive;
    private Music backgroundMusic;


    @Override
    public void create() {

        
        batch = new SpriteBatch();
        assetControl = new AssetControl();
        assetControl.create();

        backgroundMusic = AssetControl.getSounds("Music");
        backgroundMusic.setLooping(true); // Repetir automaticamente
        backgroundMusic.play();

        // Configurar fundo da arena
        background = new TextureRegion(AssetControl.getTexture("Arena"));
        
        // Configurar animações do Scorpion
        TextureRegion[][] scorpionIdle = AssetControl.getTextureRegions("ScorpionIdle", new Vector2(74, 139));
        TextureRegion[][] scorpionMove = AssetControl.getTextureRegions("ScorpionMove", new Vector2(102, 148));
        scorpionIdleAnimation = AssetControl.getAnimation(scorpionIdle, 0, 0.1f); // Linha 0: Animação Idle
        scorpionMoveAnimation = AssetControl.getAnimation(scorpionMove, 0, 0.3f); // Linha 1: Animação Move

        // Configurar animação da bola de fogo
        TextureRegion[][] ballFrames = AssetControl.getTextureRegions("Fire", new Vector2(50, 50));
        ballAnimation = AssetControl.getAnimation(ballFrames, 0, 0.1f); // Linha 0: Animação da bola

        posX = 0;
        posY = 0;
        velocity = 10;

        ballX = posX;
        ballY = posY;
        isMoving = false;
        isSpecialActive = false;
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();

        // Desenhar o fundo da arena
        batch.draw(background, 0, 0, 800, 480);

        // Desenhar o quadro atual da animação do Scorpion
        batch.draw(currentFrame, posX, posY);

        // Desenhar a bola de fogo, se ativa
        if (isSpecialActive) {
            batch.draw(currentBallFrame, ballX, ballY);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetControl.dispose();
    }

    private void update(float deltaTime) {
        handleInput();

        // Atualizar animação do Scorpion com base no estado
        if (isMoving) {
            currentFrame = AssetControl.getCurrentTRegion(scorpionMoveAnimation);
        } else {
            currentFrame = AssetControl.getCurrentTRegion(scorpionIdleAnimation);
        }

        // Atualizar animação da bola de fogo
        if (isSpecialActive) {
            currentBallFrame = AssetControl.getCurrentTRegion(ballAnimation);
            ballX += 200 * deltaTime; // Velocidade da bola

            // Verificar se a bola saiu da tela
            if (ballX > Gdx.graphics.getWidth()) {
                resetBall();
            }
        }

        assetControl.update(deltaTime);
    }

    private void handleInput() {
        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (posX < Gdx.graphics.getWidth() - currentFrame.getRegionWidth()) {
                posX += velocity;
                isMoving = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (posX > 0) {
                posX -= velocity;
                isMoving = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (posY < Gdx.graphics.getHeight() - currentFrame.getRegionHeight()) {
                posY += velocity;
                isMoving = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (posY > 0) {
                posY -= velocity;
                isMoving = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isSpecialActive) {
            activateBall();
        }
    }

    private void activateBall() {
        isSpecialActive = true;
        ballX = posX + 48; // Posição inicial da bola em relação ao Scorpion
        ballY = posY + 24; // Centralizar a bola verticalmente com o Scorpion
    }

    private void resetBall() {
        isSpecialActive = false;
        ballX = posX;
        ballY = posY;
    }
}
