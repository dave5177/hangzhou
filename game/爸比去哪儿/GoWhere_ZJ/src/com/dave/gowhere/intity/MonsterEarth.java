package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * Â·»ù
 */
public class MonsterEarth extends MapElements {

	
	public MonsterEarth(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_MONSTEREARTH;
	}
	
	protected void initRect() {
		rect = new Rect(x - 35, y + 10, 70, 40);
	}

}
