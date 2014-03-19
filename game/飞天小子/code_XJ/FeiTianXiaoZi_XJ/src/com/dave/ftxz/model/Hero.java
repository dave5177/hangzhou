package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Dialog;
import com.dave.ftxz.view.Game;

public class Hero implements Model {
	
	/**
	 * 英雄所处游戏
	 */
	protected Game game;
	
	/**
	 * （0~3：甲到丁四种小子）
	 */
	public int type;
	
	/**
	 * 坐标
	 */
	public int x, y;
	
	/**
	 * 碰撞矩形
	 */
	public Rect rect_clsn;
	
	/**
	 * 用有的宠物
	 */
	public Pet pet;

	/**
	 * 按下的键（1，向左；2，向右）
	 */
	private int codeKeyDown;
	
	/**
	 * 攻击后的时间消耗
	 */
	protected int time_fire;
	
	/**
	 * 飞行速度（默认为10像素/分秒（每10像素代表1米））
	 */
	public int speed_fly = 2;

	/**
	 * 被攻击到
	 */
	public boolean beHit;

	/**
	 * 被攻击动画帧
	 */
	private int frame_beHit;

	/**
	 * 冲刺的闪烁控制
	 */
	private int blink_sprint;

	/**
	 * 冲刺动画帧
	 */
	private int frame_sprint;

	/**
	 * 无敌效果闪烁控制
	 */
	private int blink_protected;

	/**
	 * 吸铁石效果帧
	 */
	private int frame_attract;

	/**
	 * 吸铁石效果闪烁控制
	 */
	private int blink_attract;

	/**
	 * 可复活次数。小子丁有一次
	 */
	private int numb_relive;

	/**
	 * 正在复活
	 */
	private boolean goin;
	
	/**
	 * 攻击间隔（单位：分秒）
	 */
	protected static final int INTERVAL_FIRE = 2;

	/**
	 * 左右控制移动的速度
	 */
	private static final int moveSpeed = 10;

	public Hero(Game game, int type) {
		this.game = game;
		this.type = type;
		
		init();
	}

	private void init() {
		y = 600;
		x = 320;
		rect_clsn = new Rect(x - 20, y - 45, 40, 40);
		goin = true;
		if(type != 5)game.treasure_info[2][2] = 30;
		
		if(type == 3)
			numb_relive = 1;
	}

	public void show(Graphics g) {
		if (game.treasure_info[1][2] > 0) {
			for (int i = 0; i < 4; i++) {
				g.drawImage(game.img_sprint, x, y - 30 - i * 119, Graphics.HCENTER | Graphics.BOTTOM);
			}
		}
		g.drawImage(game.img_airship, x, y - 20, Graphics.VCENTER | Graphics.HCENTER);
		g.drawImage(game.img_hero, x, y, Graphics.HCENTER | Graphics.BOTTOM);
		
		if(beHit)
			g.drawImage(game.a_img_beHit_hero[frame_beHit], x, y - 20, Graphics.VCENTER | Graphics.HCENTER);
		
		if ( game.treasure_info[2][2] > 50) {//无敌
			g.drawImage(game.img_protected, x, y - 20, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (game.treasure_info[2][2] > 0) {// 无敌闪烁
			if (blink_protected < 4) {
				g.drawImage(game.img_protected, x, y - 20, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}

		if ( game.treasure_info[3][2] > 50) {//吸铁石
			g.drawImage(game.a_img_attract[frame_attract], x, y - 20, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (game.treasure_info[3][2] > 0) {//吸铁石闪烁
			if (blink_attract < 4) {
				g.drawImage(game.a_img_attract[frame_attract], x, y - 20, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}
		
		if ( game.treasure_info[1][2] > 50) {//冲刺
			g.drawImage(game.a_img_sprint[frame_sprint], x, y, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (game.treasure_info[1][2] > 0) {// 冲刺闪烁
			if (blink_sprint < 4) {
				g.drawImage(game.a_img_sprint[frame_sprint], x, y, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}
		
		if(pet != null) pet.show(g);
//		g.drawRect(rect_clsn.x, rect_clsn.y, rect_clsn.w, rect_clsn.h);
	}

	public void keyPressed(int keyCode) {
		if(goin)
			return;
		
		switch (keyCode) {
		case C.KEY_LEFT:
			codeKeyDown = 1;
			if(pet != null)
				pet.moveToRight();
			break;
		case C.KEY_RIGHT:
			codeKeyDown = 2;
			if(pet != null)
				pet.moveToLeft();
			break;
		
		default:
			break;
		}
	}

	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT:
			if(codeKeyDown == 1)
				codeKeyDown = 0;
			break;
		case C.KEY_RIGHT:
			if(codeKeyDown == 2)
				codeKeyDown = 0;
			break;
		default:
			break;
		}
	}
	
	public void move() {
		if(codeKeyDown == 1) {
			if(x > 170) {
				x -= moveSpeed;
				rect_clsn.x -= moveSpeed;
				if(pet != null && pet.moveToSide == 0) {
					pet.x -= moveSpeed;
					pet.rect_clsn.x -= moveSpeed;
				}
			}
		} else if(codeKeyDown == 2){
			if(x < 470) {
				x += moveSpeed ;
				rect_clsn.x += moveSpeed;
				if(pet != null && pet.moveToSide == 0) {
					pet.x += moveSpeed;
					pet.rect_clsn.x += moveSpeed;
				}
			}
		}
	}

	public void logic() {
		fly();
		
		if(goin) {
			if(y > 500) {
				y -= 10;
				rect_clsn.y -= 10;
			} else {
				y = 500;
				rect_clsn.y = 500 - 45;
				goin = false;
				if(CanvasControl.goodsNumber[3] > 0) {
					new Pet(game, this);
					CanvasControl.goodsNumber[3] --;
					game.canvasControl.saveParam();
				}
			}
		
			return;
		}
		
		move();
		fire();
		if(pet != null) pet.logic();
		
		if(beHit) {
			frame_beHit ++;
			if(frame_beHit > 1) {//已经死亡，显示死亡对话框
				frame_beHit = 0;
				beHit = false;
				pet = null;
				
				if(type == 3 && numb_relive > 0) {
					numb_relive --;
					relive();
				} else {
					CanvasControl.goodsIndex = 9;
					game.canvasControl.setGoBackView(game);
					game.canvasControl.setView(game.canvasControl.nullView);
					game.removeResource();
					game.canvasControl.setView(new Dialog(game.canvasControl, 7, game));
				}
			}
		}
		
		if(game.treasure_info[1][2] > 0) {//冲刺
			blink_sprint ++;
			if(blink_sprint >= 8)
				blink_sprint = 0;
			
			frame_sprint ++;
			if(frame_sprint > 3)
				frame_sprint = 0;
		}
		if(game.treasure_info[2][2] > 0) {//无敌
			blink_protected ++;
			if(blink_protected >= 8)
				blink_protected = 0;
		}
		
		if(game.treasure_info[3][2] > 0) {//吸铁石
			blink_attract ++;
			if(blink_attract == 8)
				blink_attract = 0;
			
			frame_attract ++;
			if(frame_attract > 3)
				frame_attract = 0;
		}
	}

	/**
	 * 复活
	 */
	public void relive() {
		game.treasure_info[2][2] = 30;
		y = 600;
		x = 320;
		rect_clsn.x = 320 - 20;
		rect_clsn.y = 600 - 45;
		goin = true;
	}

	/**
	 * 往前飞
	 */
	public void fly() {
		game.y_map += speed_fly;
		CanvasControl.distance += speed_fly / 2;
		if(game.y_map > 530) 
			game.y_map -= 902;
		
	}

	public void fire() {
		game.canvasControl.playerHandler.playByIndex(2);
		time_fire ++;
		if(time_fire >= INTERVAL_FIRE) {
			time_fire = 0;
			game.v_bullet.addElement(new Bullet(game, x, y - 30, game.power_level));
		}
	}

	/**
	 * 被攻击，马上死亡
	 */
	public void beHit() {
		if(game.treasure_info[1][2] <= 0 && game.treasure_info[2][2] <= 0)//冲刺和无敌不会受到伤害
			beHit = true;
	}

	/**
	 * 获得宝物
	 * @param type 宝物的类型
	 */
	public void getTreasure(int type) {
		game.treasure_info[type][2] = 300;
		switch (type) {
		case 0://减速
			break;
		case 1://冲刺
			game.treasure_info[type][2] = 150;
			speed_fly = 32;
			break;
		case 2://无敌
			
			break;
		case 3://吸铁石
			
			break;

		default:
			break;
		}
	}

}
