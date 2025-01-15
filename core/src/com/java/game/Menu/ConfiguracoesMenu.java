package com.java.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.audio.Sound;

public class ConfiguracoesMenu implements Screen {

    private final ObjetoMenu game;
    private final Screen previousScreen;
    private OrthographicCamera camera;
    private Stage stage;
    private boolean isMuted = false;
    private Sound sound; // Tornar o som uma variável de instância
    private long soundId; // Identificador do som para controle

    public ConfiguracoesMenu(ObjetoMenu game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen; // Salva a tela anterior
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Inicializar o som
        sound = Gdx.audio.newSound(Gdx.files.internal("assets//Sons//Vazio.mp3"));
        soundId = sound.play(0.5f); // Reproduz o som e salva o ID para controle

        // Configurar estilos dos botões
        ImageButtonStyle buttonStyle = new ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new Texture("assets//Voltar.png"));

        ImageButtonStyle exitStyle = new ImageButtonStyle();
        exitStyle.up = new TextureRegionDrawable(new Texture("assets/Exit.png"));

        ImageButtonStyle pauseStyle = new ImageButtonStyle();
        pauseStyle.up = new TextureRegionDrawable(new Texture("assets/Pause.png"));

        ImageButtonStyle homeStyle = new ImageButtonStyle();
        homeStyle.up = new TextureRegionDrawable(new Texture("assets//Home.png"));

        // Botão "Voltar"
        ImageButton voltarButton = new ImageButton(buttonStyle);
        voltarButton.setSize(190, 30);
        voltarButton.setPosition(300, 170);
        voltarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(previousScreen); // Retorna para a tela anterior
                dispose(); 
            }
        });

        // Botão "Mute"
        ImageButton pauseMusic = new ImageButton(pauseStyle);
        pauseMusic.setSize(190, 30);
        pauseMusic.setPosition(300, 130);
        pauseMusic.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isMuted) {
                    sound.resume(soundId); // Retomar o som pausado
                    isMuted = false;
                } else {
                    sound.pause(soundId); // Pausar o som
                    isMuted = true;
                }
            }
        });

        // Botão "Exit"
        ImageButton exitButton = new ImageButton(exitStyle);
        exitButton.setSize(190, 30);
        exitButton.setPosition(300, 90);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Botão "Home"
        ImageButton homeButton = new ImageButton(homeStyle);
        homeButton.setSize(190, 30);
        homeButton.setPosition(300, 210);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 game.setScreen(new MenuManager(game));
            }
        });

        // Adicionar botões ao palco
        stage.addActor(voltarButton);
        stage.addActor(pauseMusic);
        stage.addActor(exitButton);
        stage.addActor(homeButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        sound.dispose(); // Liberar os recursos do som
    }
}
