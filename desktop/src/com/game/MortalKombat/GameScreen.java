package com.game.MortalKombat;
//import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

    final Drop game;
    //Texture dropImage;
    Texture bucketImage;
    Texture background = new Texture("assets//Arenas//ArenaCourtyard.png");
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    int tempo = 99 * 150;
    //Array<Rectangle> raindrops;
    //long lastDropTime;
    //int dropsGathered;

    public GameScreen(final Drop gam) {
        this.game = gam;

        //carregamento das imagens do balde e da gota 
        // dropImage = new Texture(Gdx.files.internal("assets//liuKang//liu_walking_01.png"));
        bucketImage = new Texture(Gdx.files.internal("assets//scorpio//b_hit_05.png"));

        //carregamento dos efeitos sonoros da chuva e dos pingos
        dropSound = Gdx.audio.newSound(Gdx.files.internal("assets\\Sons\\dark-crypt-144812.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("assets\\Sons\\MusicaTema.mp3"));
        rainMusic.setLooping(true);

        //cria a camera de visão do jogador e o sprite batch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //aqui é criado o "retangulo" onde o sprite do balde ficará localizado
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; //aqui o balde é colocado centralizado na horizontal
        bucket.y = 30; //aqui defini-se a posição do balde sendo no canto inferior
        //da tela 20 pixels acima da borda.
        bucket.width = 64;
        bucket.height = 150;

        //aqui é criada a matriz que irá gerar os pingos de chuva 
        /*raindrops = new Array<Rectangle>();
        spawnRaindrop();*/
    }

    /*private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }*/
    @Override
    public void render(float delta) {
        //Limpa a tela com um azul escuro.
        // Os argumentos para glClearColor sâo vermelho , verde
        // azul e o componente alpha na posição [0,1]
        // da cor a ser usada para limpar a tela.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // aqui o programa diz a camera para atualizar suas
        // matrizes.
        camera.update();

        //aqui o spritebatch recebe ordens para renderizar no 
        // sistema nas coordenadas definidas pela camera.
        game.batch.setProjectionMatrix(camera.combined);

        //aqui o balde e as gotas são desenhados novamente
        game.batch.begin();
        // game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(background, 0, 0, 800, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        /*  for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }*/

       
        game.batch.end();

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            bucket.x -= 600 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            bucket.x += 600 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            bucket.y += 600 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            bucket.y -= 600 * Gdx.graphics.getDeltaTime();
        }

        //aqui o programa se certifica de que o balde está 
        // dentro dos limites da tela.
        if (bucket.y < 0) {
            bucket.y = 0;
        }
        if (bucket.y > 480 - 150) {
            bucket.y = 480 - 150;
        }

        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        //Impedindo que o  personagem fique abaixo da borda inferior da tela
        if (bucket.y < 30) {
            bucket.y = 30;
        }
       
        tempo -= Gdx.graphics.getDeltaTime();

        // Impedir que fique negativo
        if (tempo < 0) {
            tempo = 0;
        }
        
        // Exibir o tempo restante
        System.out.println("Tempo restante: " + tempo + " segundos");
        if (tempo == 0) {
            System.out.println("Tempo esgotado!");
            Gdx.app.exit();
        }

        

        //aqui é verificado se é necessário criar novos pingos
        // de chuva.
        /*if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
                            spawnRaindrop();
                        }*/
        //aqui são movidas as gotas de chuva, removendo as que estão abaixo
        // da borda inferior da tela ou as que atingiram o balde, na qual 
        // será executado um efeito sonoro caso atinja o balde.
        /*     Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }*/
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
       
        //iniciar a reprodução da música de 
        //fundo assim que a tela é mostrada
        rainMusic.play();
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
        // dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
