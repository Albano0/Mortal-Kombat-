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
        assetManager.load("BarLife.png", Texture.class);

        // Carregar assets do Scorpion
        assetManager.load("Personagens/Scorpion/PosturaLuta.png", Texture.class);
        assetManager.load("Personagens/Scorpion/Andando.png", Texture.class);
        assetManager.load("Personagens/Scorpion/BolaFogo.png", Texture.class);
        assetManager.load("Personagens/Scorpion/SpecialAnimation.png", Texture.class);

        // Carregar assets do SubZero
        assetManager.load("Personagens/SubZero/PosturaLuta.png", Texture.class);
        assetManager.load("Personagens/SubZero/Andando.png", Texture.class);
        assetManager.load("Personagens/SubZero/Bola de Gelo.png", Texture.class);

        //BarLife
        assetManager.load("HealthBarBackground.png", Texture.class);
        assetManager.load("HealthBarFill.png", Texture.class);

        assetManager.load("Personagens/Scorpion/VictoryAnimation.png", Texture.class);

        // Carregar sons e músicas
        assetManager.load("Sons/MusicaTema.mp3", Music.class);
        assetManager.load("Sons/FireBall/FireBall.mp3", Sound.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("Arena", assetManager.get("Arenas/Arena Shrine.png", Texture.class));
        textures.put("BarLife", assetManager.get("BarLife.png", Texture.class));

        // Assets do Scorpion
        textures.put("ScorpionIdle", assetManager.get("Personagens/Scorpion/PosturaLuta.png", Texture.class));
        textures.put("ScorpionMove", assetManager.get("Personagens/Scorpion/Andando.png", Texture.class));
        textures.put("BolaFogo", assetManager.get("Personagens/Scorpion/BolaFogo.png", Texture.class));
        textures.put("SpecialScorpion", assetManager.get("Personagens/Scorpion/SpecialAnimation.png", Texture.class));

        // Assets do SubZero
        textures.put("SubZeroIdle", assetManager.get("Personagens/SubZero/PosturaLuta.png", Texture.class));
        textures.put("SubZeroMove", assetManager.get("Personagens/SubZero/Andando.png", Texture.class));
        textures.put("BolaGelo", assetManager.get("Personagens/SubZero/Bola de Gelo.png", Texture.class));

        //BarLife
        textures.put("HealthBarBackground", assetManager.get("HealthBarBackground.png", Texture.class));
        textures.put("HealthBarFill", assetManager.get("HealthBarFill.png", Texture.class));

        textures.put("VictoryAnimation", assetManager.get("Personagens/Scorpion/VictoryAnimation.png", Texture.class));

        // Obter os sons e músicas carregados
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
        return getTextureRegions(key, new Vector2(50, 50));
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
