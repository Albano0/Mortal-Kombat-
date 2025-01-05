package com.game.MortalKombat;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mortal Kombat");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(90);
        new Lwjgl3Application(new Drop(), config);
    }
}
