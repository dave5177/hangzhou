package com.dave.gowhere.intity;

import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * µ¯»É
 */
public class Spring extends MapElements {

	public Spring(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_SPRING;
	}
	
	protected void initRect() {
		rect = new Rect(x - 30, y, 60, 60);
	}
	
	public void logic() {
		x -= game.speed_run;
		rect.x -= game.speed_run;

		if (x < -100)
			game.removeMapEl(this);
	}

}
