package com.dave.gowhere.intity;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * 地图元素基类
 */
public abstract class MapElements implements Model{
	
	protected Game game;
	
	public int x, y;
	
	protected Image[] frameImg;

	public Rect rect;// 表层矩形
	
	/**
	 * 最大帧数
	 */
	protected int frameMax;
	
	/**
	 * 动画帧数
	 */
	protected int frameIndex;
	
	protected int type;
	
	/**
	 * 死亡的
	 */
	protected boolean dead = false;

	public static final int TYPE_ROAD = 1;//基础路基

	public static final int TYPE_MONSTERAIR = 2;//空中的怪

	public static final int TYPE_MONSTEREARTH = 3;//路基上的怪
	
	public static final int TYPE_BRICK = 4;//顶块
	
	public static final int TYPE_SPRING = 5;//弹簧
	
	public static final int TYPE_THORN = 6;//尖刺
	
	public static final int TYPE_MISSILE = 7;//导弹
	
	public static final int TYPE_BARRIER = 8;//障碍
	
	public static final int TYPE_FUTI = 9;//扶梯
	
	public MapElements(Game game, int x, int y, Image[] frameImg) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.frameImg = frameImg;
		frameMax = frameImg.length - 1;
	}
	
	protected abstract void initRect();

	public void show(Graphics g) {
		if(dead)
			g.drawImage(game.a_img_dead[frameIndex], x, y + 20, Graphics.HCENTER | Graphics.VCENTER);
		else
			g.drawImage(frameImg[frameIndex], x, y, Graphics.HCENTER | Graphics.TOP);
			
		////////////////////////////调试信息///////////////////////////////
		if(CanvasControl.DEBUG) {
			g.setColor(0x0000ff);
			rect.show(0, 0, g);
		}
	}

	public void keyPressed(int keyCode) {
		
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void logic() {
		if(dead) {
			frameIndex ++;
			if(frameIndex > 2)
				game.removeMapEl(this);
		}else {
			if(frameMax > 0) {
				if(frameIndex < frameMax) {
					frameIndex ++;
				} else {
					frameIndex = 0;
				}
			}
			if(game.baby.flying) {
				if(x < 400 && getType() != TYPE_ROAD && type != TYPE_SPRING)
					beKill();
			}
		}
		
		x -= game.speed_run;
		rect.x -= game.speed_run;

		if (!dead && x < -100)
			game.removeMapEl(this);
	}

	public void fire() {
		// TODO Auto-generated method stub
		
	}

	public int getType() {
//		System.out.println("type:" + type);
		return type;
	}

	/**
	 * 被杀死
	 */
	public void beKill() {
		dead = true;
		game.v_gold.addElement(new Gold(game, x, y, 5));
	}
	
	public boolean isDead() {
		return dead;
	}

}
