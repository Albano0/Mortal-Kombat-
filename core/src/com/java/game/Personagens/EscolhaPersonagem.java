package com.java.game.Personagens;

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
import com.java.game.MortalKombat;
import com.java.game.Menu.ObjetoMenu;


public class EscolhaPersonagem implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private final ObjetoMenu game;
    private Texture background;

    public EscolhaPersonagem(ObjetoMenu game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("assets//EscolhaPersonagem.png");
        // Configurar estilos dos botões

        ImageButtonStyle playStyle = new ImageButtonStyle();
        playStyle.up = new TextureRegionDrawable(new Texture("assets//Play.png"));

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

        stage.addActor(playButton);
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
        background.dispose();

    }

}
