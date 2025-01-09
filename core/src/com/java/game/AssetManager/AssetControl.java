package com.java.game.AssetManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AssetControl {

    private AssetManager AssetManager;
    private static Map<String, Texture> textures;
    private static Map<String, Music> sounds;
    private static float stateTime;


    public void create(){

        AssetManager = new AssetManager();
        textures = new HashMap<String, Texture>();
        sounds = new HashMap<String, Music>();

        //Carregar texturas

        AssetManager.load("Arenas/Arena Shrine.png", Texture.class);
        AssetManager.load("Sons/MusicaTema.mp3", Music.class);

        AssetManager.load("Personagens/Scorpion/idle.png", Texture.class);
        AssetManager.load("Personagens/Scorpion/move.png", Texture.class);

        AssetManager.load("Personagens/Scorpion/Fire.jpg", Texture.class);

        //AssetManager.load("Personagens/Subzero/teste.png", Texture.class);
        //AssetManager.load("Personagens/Subzero/Fire.jpg", Texture.class);
        
        /*AssetManager.load("Personagens/Scorpion/A.jpg", Texture.class);
        AssetManager.load("Personagens/Scorpion/A.jpg", Texture.class);
        AssetManager.load("Personagens/Scorpion/A.jpg", Texture.class);
        AssetManager.load("Personagens/Scorpion/A.jpg", Texture.class);
        */

        // Espera que todos os assets sejam carregados
        AssetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("ScorpionIdle", AssetManager.get("Personagens/Scorpion/idle.png", Texture.class));
        textures.put("ScorpionMove", AssetManager.get("Personagens/Scorpion/move.png", Texture.class));
        textures.put("Arena", AssetManager.get("Arenas/Arena Shrine.png", Texture.class));
        textures.put("Fire", AssetManager.get("Personagens/Scorpion/Fire.jpg", Texture.class));

        sounds.put("Music", AssetManager.get("Sons/MusicaTema.mp3", Music.class));
        //textures.put("Scorpion", AssetManager.get("Personagens/Scorpion/A.jpg", Texture.class));

        stateTime = 0f;
    }

    public void update(float deltaTime) {
        // Atualiza o tempo de animação
        stateTime += deltaTime;
    }

    public static Texture getTexture(String key) {
        return textures.get(key);
    }

    public static Music getSounds(String key) {
        return sounds.get(key);

    }

    public static TextureRegion[][] getTextureRegions(String key, Vector2 size) {
        return TextureRegion.split(textures.get(key),
                /* textures.get(key).getWidth() / 4 */ (int) size.x,
                /* textures.get(key).getHeight() / 6 */(int) size.y);
    }

    public static TextureRegion[][] getTextureRegions(String key) {
        return getTextureRegions(key, new Vector2(48, 48));
    }

    public static Animation<TextureRegion> getAnimation(TextureRegion[][] textureRegion, int line, float frameDuration) {
    // Filtra frames nulos antes de criar a animação
    TextureRegion[] validFrames = Arrays.stream(textureRegion[line])
                                        .filter(Objects::nonNull)
                                        .toArray(TextureRegion[]::new);
    return new Animation<>(frameDuration, validFrames);
    }


    public static TextureRegion getCurrentTRegion(Animation<TextureRegion> animation) {
        return animation.getKeyFrame(stateTime, true);
    }

    public void dispose() {
        AssetManager.dispose();
    }
}
