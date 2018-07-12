package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Player player;
	float moveFactor = 3;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		player = new Player(100,100, "player.gif");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// input
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.sprite.translate(-moveFactor, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.sprite.translate(moveFactor, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.sprite.translate(0, -moveFactor);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.sprite.translate(0, moveFactor);
		}
		batch.begin();
		player.sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
