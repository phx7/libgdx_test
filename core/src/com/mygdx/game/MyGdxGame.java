package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	public Player player;
	private float moveFactor = 150;
	public OrthographicCamera camera;
	public TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private World world;

	final float PIXELS_TO_METERS = 10f;
	float torque = 0.0f;
	private Matrix4 debugMatrix;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		player = new Player(100,100, "sprite_64.png");
		controller = new KeyboardController(this);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		
		world = new World(new Vector2(0, 0),true);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(player.x,player.y);
		player.body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(player.sprite.getWidth()/2.4f, player.sprite.getHeight()/2.4f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.0f;
		fixtureDef.restitution = 0.0f;

		player.body.createFixture(fixtureDef);
		shape.dispose();

		debugRenderer = new Box2DDebugRenderer();
		tiledMap = new TmxMapLoader().load("maps/map1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		TiledMapTileLayer tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collision");

		for (int y = 0; y <= 20; y++) {
			for (int x = 0; x <= 20; x++) {
				TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
				if (cell != null) {
					// create body for i,j cell
					createCollidable(x * 32, y * 32, 32);
				}
			}
		}

		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render () {
		// Step the physics simulation forward at a rate of 60hz
		world.step(1f/60f, 6, 2);

		// Set velocity
		keys();

		player.body.applyTorque(torque,true);

		player.sprite.setPosition(player.x, player.y);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(new Vector2(player.body.getPosition().x, player.body.getPosition().y), 0);
		camera.update();
		
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(1,
				1, 0);

		batch.begin();

		batch.draw(player.sprite, player.body.getPosition().x - (player.sprite.getWidth()/2), player.body.getPosition().y - (player.sprite.getHeight()/2),
				player.body.getPosition().x, player.body.getPosition().y,
				player.sprite.getWidth(), player.sprite.getHeight(),
				player.sprite.getScaleX(), player.sprite.getScaleY(), player.sprite.getRotation());

		batch.end();

		debugRenderer.render(world, debugMatrix);
	}

	public void createCollidable(int x, int y, int h) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x + 16, y + 16); //put here block dimensions variables
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(h/2, h/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;

		body.createFixture(fixtureDef);
		shape.dispose();
	}

	public void keys() {
		float x = 0f;
		float y = 0f;
		if (controller.left) x = -moveFactor;
		if (controller.right) x = moveFactor;
		if (controller.up) y = moveFactor;
		if (controller.down) y = -moveFactor;
		if (controller.left && controller.right) x = 0f;
		if (controller.up && controller.down) y = 0f;
		if (Math.abs(x) > 0f && Math.abs(y) > 0f) {
			x = x * 0.7f;
			y = y * 0.7f;
		}
		player.body.setLinearVelocity(x,y);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		world.dispose();
	}
	
}
