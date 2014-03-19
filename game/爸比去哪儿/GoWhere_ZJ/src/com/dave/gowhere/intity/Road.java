package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * Â·»ù
 */
public class Road extends MapElements {

	public Road(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_ROAD;
	}
	
	protected void initRect() {
		rect = new Rect(x - 40, y + 20, 80, 80);
	}

}
