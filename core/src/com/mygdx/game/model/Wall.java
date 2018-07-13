package com.mygdx.game.model;

public class Wall {
	
	private int cellPositionX;
	private int cellPositionY;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Wall(long x, long y, int width, int height) {
		this.x = (int)x;
		this.y = (int)y;
		this.width = width;
		this.height = height;
	}
	
	public Wall(int cellPositionX, int cellPositionY, int width, int height) {
		this.cellPositionX = cellPositionX;
		this.cellPositionY = cellPositionY;
		this.x = cellPositionX * width;
		this.y = cellPositionY * height;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getCellPositionX() {
		return cellPositionX;
	}
	
	public int getCellPositionY() {
		return cellPositionY;
	}
	
}
