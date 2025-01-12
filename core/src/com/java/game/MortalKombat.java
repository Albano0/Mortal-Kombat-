package com.java.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.java.game.AssetManager.AssetControl;
import com.java.game.Menu.ConfiguracoesMenu;
import com.java.game.Menu.ObjetoMenu;

public class MortalKombat implements Screen {
    private final ObjetoMenu game;
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

    private Stage stage;

    public MortalKombat(ObjetoMenu game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = game.batch;
        assetControl = new AssetControl();
        assetControl.create();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backgroundMusic = AssetControl.getMusic("Music");
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        background = new TextureRegion(AssetControl.getTexture("Arena"));

        TextureRegion[][] scorpionIdleFrames = AssetControl.getTextureRegions("ScorpionIdle", new Vector2(74, 139));
        TextureRegion[][] scorpionMoveFrames = AssetControl.getTextureRegions("ScorpionMove", new Vector2(102, 148));
        scorpionIdleAnimation = AssetControl.getAnimation(scorpionIdleFrames, 0, 0.1f);
        scorpionMoveAnimation = AssetControl.getAnimation(scorpionMoveFrames, 0, 0.1f);

        TextureRegion[][] fireballFrames = AssetControl.getTextureRegions("Fire", new Vector2(50, 50));
        fireballAnimation = AssetControl.getAnimation(fireballFrames, 0, 0.1f);


        posX = 0;
        posY = 25;
        velocity = 5;
        fireballX = posX;
        fireballY = posY;
        isMoving = false;
        isFireballActive = false;

        ImageButtonStyle configStyle = new ImageButtonStyle();
        configStyle.up = new TextureRegionDrawable(new Texture("assets//Configuracao.png"));

        

        ImageButton configButton = new ImageButton(configStyle);
        configButton.setSize(50, 50);
        configButton.setPosition(700, 400);
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ConfiguracoesMenu(game, MortalKombat.this)); // Passa a instância atual
            }
        });
        stage.addActor(configButton);
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();

        batch.draw(background, 0, 0, 800, 480);
        batch.draw(currentFrame, posX, posY);

        if (isFireballActive) {
            batch.draw(currentFireballFrame, fireballX, fireballY);
        }

        batch.end();

        stage.act(delta);
        stage.draw();

         
    }

    private void update(float deltaTime) {
        handleInput();

        if (isMoving) {
            currentFrame = AssetControl.getCurrentTRegion(scorpionMoveAnimation);
        } else {
            currentFrame = AssetControl.getCurrentTRegion(scorpionIdleAnimation);
        }

        if (isFireballActive) {
            currentFireballFrame = AssetControl.getCurrentTRegion(fireballAnimation);
            fireballX += 350 * deltaTime;

            if (fireballX > Gdx.graphics.getWidth()) {
                resetFireball();
            }
        }

        assetControl.update(deltaTime);
    }

    private void handleInput() {
        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posX += velocity;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posX -= velocity;
            isMoving = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isFireballActive) {
            activateFireball();
        }
    }

    private void activateFireball() {
        isFireballActive = true;
        fireballX = posX + 48;
        fireballY = posY + 70;
        AssetControl.playSound("FireSound", 1.0f);
    }

    private void resetFireball() {
        isFireballActive = false;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        assetControl.dispose();
        backgroundMusic.dispose();
        stage.dispose();
    }
}