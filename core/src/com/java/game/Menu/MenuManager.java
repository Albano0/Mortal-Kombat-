package com.java.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
// import com.java.game.AssetManager.AssetControl;
import com.java.game.MortalKombat;

public class MenuManager implements Screen {

    private final ObjetoMenu game; 
    private OrthographicCamera camera;
    private Texture background;
    private Stage stage;
    // private Music MusicMenu;
    // private AssetControl assetControl;

    public MenuManager(final ObjetoMenu game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // assetControl = new AssetControl();
        // assetControl.create();
    
        // MusicMenu = AssetControl.getMusic("MusicMenu");
        // MusicMenu.setLooping(true);
        // MusicMenu.setVolume(0.5f);
        // MusicMenu.play();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("Design_sem_nome.png");

        // Estilos para cada botão
        ImageButtonStyle playStyle = new ImageButtonStyle();
        playStyle.up = new TextureRegionDrawable(new Texture("assets/Play.png"));

        ImageButtonStyle exitStyle = new ImageButtonStyle();
        exitStyle.up = new TextureRegionDrawable(new Texture("assets/Exit.png"));

        ImageButtonStyle configStyle = new ImageButtonStyle();
        configStyle.up = new TextureRegionDrawable(new Texture("assets/Configuracao.png"));

        // Botão Play
        ImageButton playButton = new ImageButton(playStyle);
        playButton.setSize(190, 30);
        playButton.setPosition(300, 50);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MortalKombat(game));
                dispose();
            }
        });

        // Botão Exit
        ImageButton exitButton = new ImageButton(exitStyle);
        exitButton.setSize(190, 30);
        exitButton.setPosition(300, 10);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Botão Configurações
        ImageButton configButton = new ImageButton(configStyle);
        configButton.setSize(50, 50);
        configButton.setPosition(740, 400);
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ConfiguracoesMenu(game, MenuManager.this)); // Passa a instância atual
            }
        });

        // Adiciona os botões ao Stage
        stage.addActor(playButton);
        stage.addActor(exitButton);
        stage.addActor(configButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, 800, 480);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}