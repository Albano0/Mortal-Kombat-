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

    // Scorpion
    private Animation<TextureRegion> scorpionIdleAnimation;
    private Animation<TextureRegion> scorpionMoveAnimation;
    private Animation<TextureRegion> fireballAnimation;
    private Animation<TextureRegion> SpecialAnimation;
    private TextureRegion Life;

    // SubZero (IA)
    private Animation<TextureRegion> subZeroIdleAnimation;
    private Animation<TextureRegion> subZeroMoveAnimation;

    private TextureRegion currentFrame;
    private TextureRegion currentFireballFrame;
    private TextureRegion background;
    private TextureRegion currentSubZeroFrame;

    private float specialAnimationStateTime = 0; // Tempo de execução da animação especial
    private boolean isSpecialAnimationActive = false;
    private boolean fireballLaunched = false; // Flag para garantir que o Fireball só seja lançado após a animação
    private IAState currentState = IAState.IDLE;
    private float posX, posY, velocity;
    private float fireballX, fireballY;
    private boolean isMoving, isFireballActive;

    private float subZeroX = 150, subZeroY = 25; // Posição inicial da IA
    private float iaSpeed = 1.5f;

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

        // Scorpion
        TextureRegion[][] scorpionIdleFrames = AssetControl.getTextureRegions("ScorpionIdle", new Vector2(88, 175));
        TextureRegion[][] scorpionMoveFrames = AssetControl.getTextureRegions("ScorpionMove", new Vector2(110, 175));
        scorpionIdleAnimation = AssetControl.getAnimation(scorpionIdleFrames, 0, 0.1f);
        scorpionMoveAnimation = AssetControl.getAnimation(scorpionMoveFrames, 0, 0.1f);

        TextureRegion[][] BarLife = AssetControl.getTextureRegions("BarLife", new Vector2(35, 35));
        Life = AssetControl.getTextureRegions(BarLife, 0);

        TextureRegion[][] fireballFrames = AssetControl.getTextureRegions("BolaFogo", new Vector2(35, 35));
        fireballAnimation = AssetControl.getAnimation(fireballFrames, 0, 0.1f);

        TextureRegion[][] SpecialFrames = AssetControl.getTextureRegions("SpecialScorpion", new Vector2(120, 160));
        SpecialAnimation = AssetControl.getAnimation(SpecialFrames, 0, 0.05f);

        // SubZero (IA)
        TextureRegion[][] subZeroIdleFrames = AssetControl.getTextureRegions("SubZeroIdle", new Vector2(88, 175));
        TextureRegion[][] subZeroMoveFrames = AssetControl.getTextureRegions("SubZeroMove", new Vector2(110, 175));
        subZeroIdleAnimation = AssetControl.getAnimation(subZeroIdleFrames, 0, 0.1f);
        subZeroMoveAnimation = AssetControl.getAnimation(subZeroMoveFrames, 0, 0.1f);

        posX = 0;
        posY = 25;
        velocity = 2;
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
        batch.draw(currentSubZeroFrame, subZeroX, subZeroY);

        if (isFireballActive) {
            batch.draw(currentFireballFrame, fireballX, fireballY);
        }

        batch.end();

        stage.act(delta);
        stage.draw();
        
    }

    private void update(float deltaTime) {
        handleInput();
        handleIA(deltaTime);
    
        // Se a animação especial estiver ativa, usamos o tempo dela para atualizar
        if (isSpecialAnimationActive) {
            specialAnimationStateTime += deltaTime;  // Incrementa o tempo da animação especial
            currentFrame = SpecialAnimation.getKeyFrame(specialAnimationStateTime, false);  // Atualiza o frame da animação
    
            // Ativar o Fireball um pouco antes de terminar a animação (exemplo: 70% do tempo total)
            if (specialAnimationStateTime >= SpecialAnimation.getAnimationDuration() * 0.7f && !fireballLaunched) {
                activateFireballProjectile(); // Ativa o Fireball antes de terminar
                fireballLaunched = true;  // Marca que o Fireball foi lançado
            }
    
            // Verifica se a animação especial terminou
            if (specialAnimationStateTime >= SpecialAnimation.getAnimationDuration()) {
                isSpecialAnimationActive = false; // Permite o movimento novamente
                fireballLaunched = false;  // Reset flag
            }
        } else {
            // Se não estiver em animação especial, a animação normal do Scorpion continua
            if (isMoving) {
                currentFrame = AssetControl.getCurrentTRegion(scorpionMoveAnimation);
            } else {
                currentFrame = AssetControl.getCurrentTRegion(scorpionIdleAnimation);
            }
        }
    
        // Se o Fireball foi lançado, começamos a desenhá-lo
        if (isFireballActive) {
            currentFireballFrame = AssetControl.getCurrentTRegion(fireballAnimation);
    
            // Move o Fireball para a direita
            fireballX += 350 * deltaTime;  // Aumenta a posição em X
    
            // Se a bola de fogo saiu da tela, reinicia o estado
            if (fireballX > Gdx.graphics.getWidth()) {
                resetFireball();
            }
        }
    
        assetControl.update(deltaTime);
    }

    private void activateFireballProjectile() {
        isFireballActive = true;
        fireballX = posX + 48;  // Define a posição inicial do Fireball
        fireballY = posY + 70;  // Define a posição inicial da bola de fogo
    }

    private boolean isColliding(float x1, float y1, float width1, float height1,
                            float x2, float y2, float width2, float height2) {
    return x1 < x2 + width2 &&
           x1 + width1 > x2 &&
           y1 < y2 + height2 &&
           y1 + height1 > y2;
    }

    private void handleInput() {
        isMoving = false;
    
        // Não permite o movimento se a animação especial estiver ativa
        if (isSpecialAnimationActive) {
            return;
        }
    
        // Movimento para a direita
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            float nextPosX = posX + velocity;
    
            // Verifica se o movimento não colidirá com o SubZero
            if (!isColliding(nextPosX, posY, 88, 175, subZeroX, subZeroY, 88, 175)) {
                posX = nextPosX;
            }
            isMoving = true;
        }
    
        // Movimento para a esquerda
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            float nextPosX = posX - velocity;
    
            // Verifica se o movimento não colidirá com o SubZero
            if (!isColliding(nextPosX, posY, 88, 175, subZeroX, subZeroY, 88, 175)) {
                posX = nextPosX;
            }
            isMoving = true;
        }
    
        // Lançamento do projétil (habilita o Fireball)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isFireballActive) {
            activateFireball();
        }
    }
    

    private void handleIA(float deltaTime) {
        switch (currentState) {
            case IDLE:
                currentSubZeroFrame = AssetControl.getCurrentTRegion(subZeroIdleAnimation);
                if (Math.random() < 0.01) {
                    currentState = IAState.MOVING;
                }
                break;
    
            case MOVING:
                currentSubZeroFrame = AssetControl.getCurrentTRegion(subZeroMoveAnimation);
    
                // Atualiza a posição horizontal do SubZero
                float nextSubZeroX = subZeroX + iaSpeed;
    
                // Verifica se o próximo movimento não colidirá com o Scorpion
                if (!isColliding(nextSubZeroX, subZeroY, 88, 175, posX, posY, 88, 175)) {
                    subZeroX = nextSubZeroX;
                } else {
                    // Troca de direção ao encontrar uma colisão
                    iaSpeed = -iaSpeed;
                }
    
                // Limita o movimento horizontal dentro de um intervalo
                if (subZeroX > 600) {
                    iaSpeed = -Math.abs(iaSpeed); // Move para a esquerda
                } else if (subZeroX < 150) {
                    iaSpeed = Math.abs(iaSpeed); // Move para a direita
                }
    
                // Troca para o estado IDLE com uma probabilidade
                if (Math.random() < 0.01) {
                    currentState = IAState.IDLE;
                }
                break;
    
            default:
                currentState = IAState.IDLE;
        }
    }

    private void activateFireball() {
        specialAnimationStateTime = 0;  // Reseta o tempo da animação
        currentFrame = SpecialAnimation.getKeyFrame(specialAnimationStateTime, false);  // Começa a animação
        fireballLaunched = false;  // Garante que o Fireball ainda não foi lançado
        isSpecialAnimationActive = true;
        AssetControl.playSound("FireSound", 1.0f); // Toca o som da habilidade
    }

    private void resetFireball() {
        isFireballActive = false;
        fireballX = -100;  // Reseta a posição do fireball fora da tela para não ser desenhado
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

    public enum IAState {
        IDLE, MOVING, ATTACKING, DEFENDING
    }
}
