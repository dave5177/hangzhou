package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

/**
 * @author dave
 * 陨石
 */
public class Aerolite implements Model {
	
	/**
	 * 所在游戏
	 */
	Game game;
	
	/**
	 * 坐标
	 */
	int x, y;
	
	/**
	 * 碰撞矩形
	 */
	public Rect rect_clsn;
	
	/**
	 * 产生后的时间时间
	 */
	private int time;

	/**
	 * 感叹号的两种图
	 */
	private int frame_alert;
	
	/**
	 * 坠落速度
	 */
	private static final int SPEED_FALL = 50;
	
	public Aerolite(Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		
		rect_clsn = new Rect(x - 33, y - 100, 66, 96);
	}

	public void show(Graphics g) {
		if(time < 20) {
			if(frame_alert == 0)
				frame_alert = 1;
			else 
				frame_alert = 0;
			g.drawImage(game.a_img_alert[frame_alert], x, 5, Graphics.TOP | Graphics.HCENTER);
		}
		g.drawImage(game.img_aerolite, x, y, Graphics.BOTTOM | Graphics.HCENTER);
//		g.drawRect(rect_clsn.x, rect_clsn.y, rect_clsn.w, rect_clsn.h);
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void logic() {
		falling();
		fire();
	}

	/**
	 * 坠落
	 */
	private void falling() {
		time ++;
		if(time > 20) {
			y += SPEED_FALL;
			rect_clsn.y += SPEED_FALL;
		} else {
			game.canvasControl.playerHandler.playByIndex(4);
		}
		
		if(y > 800)
			removeMe();
	}

	private void removeMe() {
		game.v_aerolite.removeElement(this);
	}

	public void fire() {
		if(C.rectInsect(game.hero.rect_clsn, rect_clsn)) {
			game.hero.beHit();
		} else if(game.hero.pet != null) {
			if(C.rectInsect(game.hero.pet.rect_clsn, rect_clsn)) {
				game.hero.pet.beHit();
			}
		}
	}

}
