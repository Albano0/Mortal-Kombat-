package core.src.com.java.MortalKombat;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {
	private int keyGame;
	//private int dificultyGame;
	private boolean checkNext;
	private boolean enableSoundMusic;
	private boolean enableSoundEffects;
	
	public GameScreen() {
		//dificultyGame = 0;
		enableSoundMusic = true;
		enableSoundEffects = true;

		update();
	}

	@Override
	public void resize(int width, int height) {
		// ((GameStage) stage).resize(height, width);
		// stage.act();
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void show() {
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
}
