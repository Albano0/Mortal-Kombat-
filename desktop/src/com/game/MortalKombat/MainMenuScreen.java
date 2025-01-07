package com.game.MortalKombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MainMenuScreen implements Screen {

    private final Drop game;
    private OrthographicCamera camera;
    private Texture background;
    private Stage stage;

    public MainMenuScreen(final Drop gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cria o Stage para gerenciar os botões
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Carrega a imagem de fundo
        background = new Texture("assets//Design sem nome.png");

        // Estilo dos botões
        BitmapFont font = new BitmapFont(); // Fonte padrão
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;


        // Botão Play
        TextButton playButton = new TextButton("Play", buttonStyle);
        playButton.setSize(200, 50); // Define tamanho do botão
        playButton.setPosition(300, 50); // Define posição do botão na tela
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Troca para a tela do jogo
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        // Botão Exit
        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.setSize(200, 50); // Define tamanho do botão
        exitButton.setPosition(300, 10); // Define posição do botão na tela
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fecha o jogo
                Gdx.app.exit();
            }
        });

        // Adiciona os botões ao Stage
        stage.addActor(playButton);
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atualiza a câmera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Desenha a imagem de fundo
        game.batch.begin();
        game.batch.draw(background, 0, 0, 800, 480);
        game.batch.end();

        // Renderiza o Stage 
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (background != null) {
            background.dispose();
        }
    }
}
