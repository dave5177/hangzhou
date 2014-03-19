package com.dave.gowhere.intity;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * 导弹
 */
public class Missile extends MapElements {
	
	/**
	 * 导弹的状态：1：为提示。2：为飞行 
	 */
	private int state = 0;
	
	/**
	 * 警示时间
	 */
	private int alertTime = 0;
	
	public Missile(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_MISSILE;
		state = 1;
	}
	
	protected void initRect() {
		rect = new Rect(x - 30, y - 10, 60, 40);
	}
	
	public void show(Graphics g) {
		if(state == 1) {
			if(frameIndex < 2)
				g.drawImage(frameImg[frameIndex], 320, y, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(frameImg[2], x, y, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(frameImg[frameIndex], x + 20, y, Graphics.HCENTER | Graphics.VCENTER);
		}
	}
	
	public void logic() {
		if(state == 1) {
			frameIndex ++;
			if(frameIndex > 3)
				frameIndex = 0;
			alertTime ++;
			if(alertTime > 20)
				state = 2;
		} else {
			frameIndex = frameIndex == 3 ? 4 : 3;
			x -= game.speed_run + 20;
			rect.x -= game.speed_run + 20;
			
			if (x < -100)
				game.v_missile.removeElement(this);
		}
	}

}
