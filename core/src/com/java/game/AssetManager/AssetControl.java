package com.java.game.AssetManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AssetControl {

    private AssetManager assetManager;
    private static Map<String, Texture> textures;
    private static Map<String, Music> musics;
    private static Map<String, Sound> sounds;
    private static float stateTime;

    public void create() {
        assetManager = new AssetManager();
        textures = new HashMap<>();
        musics = new HashMap<>();
        sounds = new HashMap<>();

        // Carregar texturas
        assetManager.load("Arenas/Arena Shrine.png", Texture.class);
        assetManager.load("Sons/MusicaTema.mp3", Music.class);
        assetManager.load("Sons/FireBall/FireBall.mp3", Sound.class);

        assetManager.load("Personagens/Scorpion/idle.png", Texture.class);
        assetManager.load("Personagens/Scorpion/move.png", Texture.class);
        assetManager.load("Personagens/Scorpion/Fire.jpg", Texture.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("ScorpionIdle", assetManager.get("Personagens/Scorpion/idle.png", Texture.class));
        textures.put("ScorpionMove", assetManager.get("Personagens/Scorpion/move.png", Texture.class));
        textures.put("Arena", assetManager.get("Arenas/Arena Shrine.png", Texture.class));
        textures.put("Fire", assetManager.get("Personagens/Scorpion/Fire.jpg", Texture.class));

        // Obter os sons carregados
        musics.put("Music", assetManager.get("Sons/MusicaTema.mp3", Music.class));
        sounds.put("FireSound", assetManager.get("Sons/FireBall/FireBall.mp3", Sound.class));

        stateTime = 0f;
    }

    public void update(float deltaTime) {
        // Atualiza o tempo de animação
        stateTime += deltaTime;
    }

    // Métodos para texturas
    public static Texture getTexture(String key) {
        return textures.get(key);
    }

    public static TextureRegion[][] getTextureRegions(String key, Vector2 size) {
        return TextureRegion.split(
            textures.get(key),
            (int) size.x,
            (int) size.y
        );
    }

    public static TextureRegion[][] getTextureRegions(String key) {
        return getTextureRegions(key, new Vector2(48, 48));
    }

    public static Animation<TextureRegion> getAnimation(TextureRegion[][] textureRegion, int line, float frameDuration) {
        // Filtrar frames nulos antes de criar a animação
        TextureRegion[] validFrames = Arrays.stream(textureRegion[line])
                                            .filter(Objects::nonNull)
                                            .toArray(TextureRegion[]::new);
        return new Animation<>(frameDuration, validFrames);
    }

    public static TextureRegion getCurrentTRegion(Animation<TextureRegion> animation) {
        return animation.getKeyFrame(stateTime, true);
    }

    // Métodos para sons e músicas
    public static Music getMusic(String key) {
        return musics.get(key);
    }

    public static Sound getSound(String key) {
        return sounds.get(key);
    }

    public static void setMusicVolume(String key, float volume) {
        if (musics.containsKey(key)) {
            musics.get(key).setVolume(volume);
        }
    }

    public static void playSound(String key, float volume) {
        if (sounds.containsKey(key)) {
            sounds.get(key).play(volume);
        }
    }

    public void dispose() {
        assetManager.dispose();
    }
}
