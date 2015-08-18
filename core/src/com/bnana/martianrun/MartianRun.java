package com.bnana.martianrun;

import com.badlogic.gdx.Game;
import com.bnana.martianrun.screens.GameScreen;

public class MartianRun extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}
