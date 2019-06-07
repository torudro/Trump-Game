package com.torudro;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Random;

public class TestGameClass extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture cageImage;
	Texture trumpImage;
	Texture backgroundImage;
	private Rectangle cage;
	private OrthographicCamera camera;
	private Array<Rectangle> trumpFaces;
	private long lastTrumpTime;
	private Music backMusic;
	private Sound chinaSound;
	private Sound wrongSound;
	private Sound gebtpSound;
	private Random random;



	private void spawnTrump(){

		Rectangle trumpFace = new Rectangle();
		trumpFace.x = MathUtils.random(0, 800-64);
		trumpFace.y = 480;
		trumpFaces.add(trumpFace);
		lastTrumpTime = TimeUtils.nanoTime();

	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		backgroundImage = new Texture(Gdx.files.internal("backgroundimage.png"));
		img = new Texture(Gdx.files.internal("badlogic.jpg"));

		trumpFaces = new Array<Rectangle>();
		spawnTrump();

		//sounds

		chinaSound = Gdx.audio.newSound(Gdx.files.internal("china.mp3"));
		wrongSound = Gdx.audio.newSound(Gdx.files.internal("wrong.mp3"));
		gebtpSound = Gdx.audio.newSound(Gdx.files.internal("gebtp.mp3"));

		backMusic = Gdx.audio.newMusic(Gdx.files.internal("saxguy.mp3"));
		backMusic.setLooping(true);
		backMusic.play();


camera = new OrthographicCamera();

		camera.setToOrtho(false, 800, 400);

		//loads images
		cageImage = new Texture(Gdx.files.internal("cage.png"));
		trumpImage = new Texture(Gdx.files.internal("trump.png"));

		cage = new Rectangle();
		cage.x = 800/2 - 64/2;
		cage.y = 20;
		cage.width = 64;
		cage.height = 64;

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//updates camera matrix
		camera.update();


		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(backgroundImage, 0, 0);
		batch.draw(cageImage, cage.x, cage.y);
		for(Rectangle trumpFace: trumpFaces) {
			batch.draw(trumpImage, trumpFace.x, trumpFace.y);
		}

		batch.end();

		if(Gdx.input.isTouched()){

			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			cage.x = touchPos.x - 64/2;

		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) cage.x -= 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) cage.x += 300 * Gdx.graphics.getDeltaTime();

		if(cage.x < 0) cage.x = 0;
		if(cage.x > 800-64) cage.x = 800-64;


		if(TimeUtils.nanoTime() - lastTrumpTime > 1000000000) spawnTrump();
//for sound play.
int randNum;
random = new Random();
		for (Iterator<Rectangle> iter = trumpFaces.iterator(); iter.hasNext(); ) {
			Rectangle trumpFace = iter.next();
			trumpFace.y -= 200 * Gdx.graphics.getDeltaTime();
			if(trumpFace.y + 64 < 0) iter.remove();
			if(trumpFace.overlaps(cage)) {
				//dropSound.play();
				iter.remove();
				randNum = random.nextInt(3);
				if(randNum == 0) chinaSound.play();
				if(randNum == 1) wrongSound.play();
				if(randNum == 2) gebtpSound.play();



			}
		}



	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
