package com.game.MortalKombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture background; // Adicionada a textura da imagem de fundo

    public MainMenuScreen(final Drop gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Carregando a imagem de fundo
        background = new Texture("assets//Design sem nome.png"); 
    }

    @Override
    public void render(float delta) {
        // Limpa a tela com uma cor preta (opcional)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atualiza a câmera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Começa o desenho
        game.batch.begin();

        // Desenha a imagem de fundo
        game.batch.draw(background, 0, 0, 800, 480); // Ajuste a posição e o tamanho da imagem

        // Desenha o texto por cima da imagem de fundo
        

        game.batch.end();

        // Verifica se o mouse foi pressionado
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
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
        // Libera a textura da imagem de fundo
        if (background != null) {
            background.dispose();
        }
    }
}
