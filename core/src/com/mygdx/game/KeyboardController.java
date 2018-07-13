package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class KeyboardController  implements InputProcessor {
    public boolean left,right,up,down;
    public boolean isMouse1Down, isMouse2Down,isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2(0,0);
    private MyGdxGame game;

    public KeyboardController(MyGdxGame game){
        this.game = game;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Keys.LEFT:     // if keycode is the same as Keys.LEFT a.k.a 21
                left = true;    // do this
                keyProcessed = true;    // we have reacted to a keypress 
                break;
            case Keys.RIGHT:    // if keycode is the same as Keys.LEFT a.k.a 22
                right = true;   // do this
                keyProcessed = true;    // we have reacted to a keypress 
                break;
            case Keys.UP:       // if keycode is the same as Keys.LEFT a.k.a 19
                up = true;      // do this
                keyProcessed = true;    // we have reacted to a keypress 
                break;
            case Keys.DOWN:     // if keycode is the same as Keys.LEFT a.k.a 20
                down = true;    // do this
                keyProcessed = true;    // we have reacted to a keypress
        }
        return keyProcessed;    //  return our peyProcessed flag
    }
    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Keys.LEFT:     // if keycode is the same as Keys.LEFT a.k.a 21
                left = false;   // do this
                keyProcessed = true;    // we have reacted to a keypress 
                break;
            case Keys.RIGHT:    // if keycode is the same as Keys.LEFT a.k.a 22
                right = false;  // do this
                keyProcessed = true;    // we have reacted to a keypress 
                break;
            case Keys.UP:       // if keycode is the same as Keys.LEFT a.k.a 19
                up = false;     // do this
                keyProcessed = true;    // we have reacted to a keypress 
                break;
            case Keys.DOWN:     // if keycode is the same as Keys.LEFT a.k.a 20
                down = false;   // do this
                keyProcessed = true;    // we have reacted to a keypress
        }
        
        //TODO delete it
        if(keycode == Input.Keys.A)
            game.camera.translate(-32,0);
        if(keycode == Input.Keys.D)
            game.camera.translate(32,0);
        if(keycode == Input.Keys.W)
            game.camera.translate(0,32);
        if(keycode == Input.Keys.S)
            game.camera.translate(0,-32);
        if(keycode == Input.Keys.NUM_1)
            game.tiledMap.getLayers().get(0).setVisible(!game.tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            game.tiledMap.getLayers().get(1).setVisible(!game.tiledMap.getLayers().get(1).isVisible());
        if(keycode == Input.Keys.NUM_4) {
        
        }
        
        return keyProcessed;    //  return our peyProcessed flag
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0){
            isMouse1Down = true;
        }else if(button == 1){
            isMouse2Down = true;
        }else if(button == 2){
            isMouse3Down = true;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        //System.out.println(button);
        if(button == 0){
            isMouse1Down = false;
        }else if(button == 1){
            isMouse2Down = false;
        }else if(button == 2){
            isMouse3Down = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}