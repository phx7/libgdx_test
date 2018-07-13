package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private Player player;
	private float moveFactor = 15;
	private OrthographicCamera camera;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private World world;

	final float PIXELS_TO_METERS = 10f;
	float torque = 0.0f;
	private Matrix4 debugMatrix;
	private boolean drawSprite = true;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		player = new Player(100,100, "player.gif");
		controller = new KeyboardController();

		world = new World(new Vector2(0, 0),true);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((player.sprite.getX() + player.sprite.getWidth()/2) /
						PIXELS_TO_METERS,
				(player.sprite.getY() + player.sprite.getHeight()/2) / PIXELS_TO_METERS);
		player.body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(player.sprite.getWidth()/2 / PIXELS_TO_METERS, player.sprite.getHeight()
				/2 / PIXELS_TO_METERS);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;

		player.body.createFixture(fixtureDef);
		shape.dispose();

		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("maps/map1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		for(MapObject obj: tiledMap.getLayers().get(2).getObjects()) {
//			obj.getProperties();
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

		player.sprite.setPosition((player.body.getPosition().x * PIXELS_TO_METERS) - player.sprite.
						getWidth()/2 ,
				(player.body.getPosition().y * PIXELS_TO_METERS) - player.sprite.getHeight()/2 );

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
				PIXELS_TO_METERS, 0);

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.begin();

		batch.draw(player.sprite, player.sprite.getX(), player.sprite.getY(),player.sprite.getOriginX(),
					player.sprite.getOriginY(),
					player.sprite.getWidth(),player.sprite.getHeight(),player.sprite.getScaleX(),player.sprite.
							getScaleY(),player.sprite.getRotation());

		batch.end();

		debugRenderer.render(world, debugMatrix);
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
		};
		player.body.setLinearVelocity(x,y);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
