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
    private Animation<TextureRegion> fireballAnimation;

    private TextureRegion currentFrame;
    private TextureRegion currentFireballFrame;
    private TextureRegion background;

    private float posX, posY, velocity;
    private float fireballX, fireballY;
    private boolean isMoving, isFireballActive;

    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetControl = new AssetControl();
        assetControl.create();

        // Configurar música de fundo
        backgroundMusic = AssetControl.getMusic("Music");
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f); // Volume ajuste
        backgroundMusic.play();

        // Configurar fundo da arena
        background = new TextureRegion(AssetControl.getTexture("Arena"));

        // Configurar animações do Scorpion
        TextureRegion[][] scorpionIdleFrames = AssetControl.getTextureRegions("ScorpionIdle", new Vector2(74, 139));
        TextureRegion[][] scorpionMoveFrames = AssetControl.getTextureRegions("ScorpionMove", new Vector2(102, 148));
        scorpionIdleAnimation = AssetControl.getAnimation(scorpionIdleFrames, 0, 0.1f);
        scorpionMoveAnimation = AssetControl.getAnimation(scorpionMoveFrames, 0, 0.1f);

        // Configurar animação da bola de fogo
        TextureRegion[][] fireballFrames = AssetControl.getTextureRegions("Fire", new Vector2(50, 50));
        fireballAnimation = AssetControl.getAnimation(fireballFrames, 0, 0.1f);

        // Inicializar variáveis de estado
        posX = 0;
        posY = 25;
        velocity = 5;
        fireballX = posX;
        fireballY = posY;
        isMoving = false;
        isFireballActive = false;
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();

        // Desenhar o fundo da arena
        batch.draw(background, 0, 0, 800, 480);

        // Desenhar o Scorpion
        batch.draw(currentFrame, posX, posY);

        // Desenhar a bola de fogo, se estiver ativa
        if (isFireballActive) {
            batch.draw(currentFireballFrame, fireballX, fireballY);
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

        // Atualizar animação do Scorpion
        if (isMoving) {
            currentFrame = AssetControl.getCurrentTRegion(scorpionMoveAnimation);
        } else {
            currentFrame = AssetControl.getCurrentTRegion(scorpionIdleAnimation);
        }

        // Atualizar animação da bola de fogo
        if (isFireballActive) {
            currentFireballFrame = AssetControl.getCurrentTRegion(fireballAnimation);
            fireballX += 350 * deltaTime; // Velocidade da bola de fogo

            // Verificar se a bola de fogo saiu da tela
            if (fireballX > Gdx.graphics.getWidth()) {
                resetFireball();
            }
        }

        assetControl.update(deltaTime);
    }

    private void handleInput() {
        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && posX < Gdx.graphics.getWidth() - currentFrame.getRegionWidth()) {
            posX += velocity;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && posX > 0) {
            posX -= velocity;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && posY < Gdx.graphics.getHeight() - currentFrame.getRegionHeight()) {
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && posY > 0) {
            isMoving = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isFireballActive) {
            activateFireball();
        }
    }

    private void activateFireball() {
        isFireballActive = true;
        fireballX = posX + 48; // Posição inicial da bola em relação ao Scorpion
        fireballY = posY + 70; // Centralizar a bola verticalmente com o Scorpion
        AssetControl.playSound("FireSound", 1.0f); // Tocar som ao lançar a bola de fogo
    }

    private void resetFireball() {
        isFireballActive = false;
        fireballX = posX;
        fireballY = posY;
    }
}
