package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * ×©¿é
 */
public class Brick extends MapElements {
	
	public Brick(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_BRICK;
	}
	
	protected void initRect() {
		rect = new Rect(x - 25, y, 50, 40);
	}

}
