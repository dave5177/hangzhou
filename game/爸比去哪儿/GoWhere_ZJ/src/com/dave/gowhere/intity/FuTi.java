package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * ¸¡ÌÝ
 */
public class FuTi extends MapElements {

	public FuTi(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_FUTI;
	}
	
	protected void initRect() {
		rect = new Rect(x - 40, y + 20, 80, 80);
	}
	
}
