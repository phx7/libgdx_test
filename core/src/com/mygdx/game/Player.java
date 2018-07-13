package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Player {
    int x,y;
    float factor = 1;
    Sprite sprite;
    Body body;

    Player(int x, int y, String sprite){
        this.sprite = new Sprite(new Texture(sprite));
//        factor = 64/((this.sprite.getWidth() + this.sprite.getHeight())/2);
        this.sprite.setScale(factor);
        this.x = x;
        this.y = y;
    };
}
