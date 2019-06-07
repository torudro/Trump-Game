package com.torudro.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.torudro.TestGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TestGameClass(), config);

		config.title = "Trumpy boi";
		//sets screen size
		config.width = 1280;
		config.height = 720;
		//config.fullscreen = true;

	}
}
