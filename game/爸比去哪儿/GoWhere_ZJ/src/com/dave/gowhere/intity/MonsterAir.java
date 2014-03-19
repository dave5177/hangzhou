package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * Â·»ù
 */
public class MonsterAir extends MapElements {

	
	public MonsterAir(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_MONSTERAIR;
	}
	
	protected void initRect() {
		rect = new Rect(x - 30, y + 10, 60, 40);
	}

}
