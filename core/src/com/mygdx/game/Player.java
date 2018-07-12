package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class Player {
    int x,y;
    float factor;
    Sprite sprite;

    Player(int x, int y, String sprite){
        this.sprite = new Sprite(new Texture(sprite));
        factor = 64/((this.sprite.getWidth() + this.sprite.getHeight())/2);
        this.sprite.setScale(factor);
        this.x = x;
        this.y = y;
    };
}
