package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * ’œ∞≠
 */
public class Barrier extends MapElements {

	public Barrier(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_BARRIER;
	}
	
	protected void initRect() {
		rect = new Rect(x - 20, y, 40, 240);
	}

}
