package com.dave.paoBing.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.tool.C;
import com.dave.paoBing.view.Dialog;
import com.dave.paoBing.view.Game;

public class Hero {

	/**
	 * 所在的游戏
	 */
	private Game game;

	/**
	 * 角度下标值 取值范围是0~10. 5是水平0度。减1角度加10度。加1角度减10度。
	 */
	public int angle_index = 5;

	/**
	 * 动画的帧
	 */
	private int frame;

	/**
	 * 帧速度控制
	 */
	private int speedControl;

	/**
	 * 长按键延迟控制
	 */
	private int keyPingControl;

	/**
	 * 按下向上的键
	 */
	private boolean keyUpPress;

	/**
	 * 按下向下的键
	 */
	private boolean keyDownPress;

	/**
	 * x,y坐标
	 */
	public int x, y;

	/**
	 * 枪口的坐标
	 */
	public int x_muzzle, y_muzzle;
	
	/**
	 * 攻击力
	 */
	public int attackPower;
	
	/**
	 * 最大生命值
	 */
	public static int lifeMax;
	
	/**
	 * 生命值
	 */
	public static int life;

	/**
	 * 开完枪的状态
	 */
	private boolean afterFier;

	/**
	 * 被打中了，显示爆炸效果
	 */
	private boolean showBoom;

	/**
	 * 爆炸的三针图片
	 */
	private Image[] a_img_boom;
	
	/**
	 * 掉血的三帧动画图片
	 */
	private Image[] a_img_bleed;

	/**
	 * 爆炸图片的帧
	 */
	private int boomFrame;

	/**
	 * 残血状态
	 */
	public boolean weak;

	/**
	 * 构造一个英雄
	 * @param game 所在游戏
	 * @param pier 英雄所站的树墩
	 */
	public Hero(Game game, Pier pier) {
		this.game = game;
		x = pier.x;
		y = pier.y - 25;
		
		init();
		
		updateMuzzlePoint();
	}

	/**
	 * 初始化属性值等。
	 */
	private void init() {
		this.attackPower = 50;
		lifeMax = 100 + Game.remainLife;
		life = lifeMax;
		
		System.out.println("英雄生命：" + life);
	}

	/**
	 * 显示
	 * 
	 * @param g
	 */
	public void show(Graphics g) {
//		showPoint(g);
		g.drawImage(game.a_img_hero[angle_index][frame], x + game.x_map, y, Graphics.BOTTOM	| Graphics.HCENTER);
		showBloodBar(g);
		
		if (showBoom && a_img_boom != null && a_img_bleed != null) {
			g.drawImage(a_img_boom[boomFrame], x + 10 + game.x_map, y - 50, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(a_img_bleed[boomFrame], x + 10 + game.x_map, y - 80, Graphics.VCENTER
					| Graphics.RIGHT);
		}
	}

	/**
	 * 显示箭头
	 * 
	 * @param g
	 */
	public void showPoint(Graphics g) {
	}

	/**
	 * 画血条
	 * @param g
	 */
	private void showBloodBar(Graphics g) {
		g.drawImage(game.a_img_blood_bar[0], x + game.x_map, y - 109, Graphics.BOTTOM
				| Graphics.HCENTER);
		
		g.setClip(0, 0, x - Game.LENGTH_BLOOD_BAR / 2 + life * Game.LENGTH_BLOOD_BAR / lifeMax + game.x_map, 535);
		g.drawImage(game.a_img_blood_bar[1], x + game.x_map, y - 109, Graphics.BOTTOM
				| Graphics.HCENTER);
		g.setClip(0, 0, 645, 535);
	}
	
	/**
	 * 导入游戏的图片
	 */
	public void loadGameHeroImage(){
		for (int a = 0; a < 11; a++) {
			for (int b = 0; b < 3; b++) {
				game.a_img_hero[a][b] = null;
			}
		}
		System.gc();
		//先把图片资源清空，但是不把整个二维图片数组清空。
		
		Image temp = null;
		int w, h;
		int i = angle_index ;
//		for (int i = 0; i < 11; i++) {
			try {
				temp = Image.createImage("/hero/hero_" + i + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			w = temp.getWidth();
			h = temp.getHeight();
			for (int j = 0; j < 3; j++) {
				game.a_img_hero[i][j] = Image.createImage(temp, w * j / 3,0, w / 3, h, 0);
			}
			temp = null;
//		}
	}
	
	/**
	 * 按键处理
	 * 
	 * @param keyCode
	 */
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT:
		case C.KEY_UP:
			if (angle_index > 0) {
				angle_index--;
				updateMuzzlePoint();
				keyPingControl = 0;
				keyDownPress = false;
				keyUpPress = true;
				this.loadGameHeroImage();
			}
			break;
		case C.KEY_RIGHT:
		case C.KEY_DOWN:
			if (angle_index < 10) {
				angle_index++;
				updateMuzzlePoint();
				keyPingControl = 0;
				keyUpPress = false;
				keyDownPress = true;
				this.loadGameHeroImage();
			}
			break;
		case C.KEY_FIRE:
			if(Game.shooterIndex == 0 && game.missile == null && Game.remain_missile > 0 && !game.showMap) 
				fire();
			else if (Game.shooterIndex == 0 && game.missile == null && Game.remain_missile <= 0) {
				game.getCanvasControl().setGoBackView(game);
				game.getCanvasControl().setView(game.getCanvasControl().nullView);
				game.removeResource();
				game.getCanvasControl().setView(new Dialog(game.getCanvasControl(), 6, null));
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 按键释放
	 * 
	 * @param keyCode
	 */
	public void keyReleased(int keyCode) {
		if (!C.isZHONGXING) {
			switch (keyCode) {
			case C.KEY_LEFT:
			case C.KEY_UP:
				keyUpPress = false;
				keyPingControl = 0;
				break;
			case C.KEY_RIGHT:
			case C.KEY_DOWN:
				keyDownPress = false;
				keyPingControl = 0;
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 更新枪口坐标
	 */
	private void updateMuzzlePoint() {
		switch (angle_index) {
		case 0:
			x_muzzle = x;
			y_muzzle = y - 102;
			break;
		case 1:
			x_muzzle = x + 12;
			y_muzzle = y - 98;
			break;
		case 2:
			x_muzzle = x + 22;
			y_muzzle = y - 94;
			break;
		case 3:
			x_muzzle = x + 30;
			y_muzzle = y - 87;
			break;
		case 4:
			x_muzzle = x + 40;
			y_muzzle = y - 81;
			break;
		case 5:
			x_muzzle = x + 48;
			y_muzzle = y - 75;
			break;
		case 6:
			x_muzzle = x + 49;
			y_muzzle = y - 69;
			break;
		case 7:
			x_muzzle = x + 49;
			y_muzzle = y - 55;
			break;
		case 8:
			x_muzzle = x + 43;
			y_muzzle = y - 43;
			break;
		case 9:
			x_muzzle = x + 46;
			y_muzzle = y - 28;
			break;
		case 10:
			x_muzzle = x + 40;
			y_muzzle = y - 21;
			break;
		}
	}

	/**
	 * 开炮~~~
	 */
	private void fire() {
//		game.getCanvasControl().playerHandler.playByIndex(2);
		
		int power = 0;
		if(Game.doublePower > 0) {
			power = attackPower * 2;
			game.missile = new Missile(game, x_muzzle, y_muzzle, (5 - angle_index) * 10, power, true);
			Game.doublePower --;
		} else {
			power = attackPower;
			game.missile = new Missile(game, x_muzzle, y_muzzle, (5 - angle_index) * 10, power, false);
		}
		Game.remain_missile --;
		afterFier = true;
		
//		game.nextShooter();
	}

	/**
	 * 主逻辑
	 */
	public void logic() {
		if (keyUpPress && !C.isZHONGXING) {
			keyPingControl++;
			if (keyPingControl > 5) {
				if (angle_index > 0) {
					angle_index--;
					updateMuzzlePoint();
					this.loadGameHeroImage();
				}
			}
		} else if (keyDownPress && !C.isZHONGXING) {
			keyPingControl++;
			if (keyPingControl > 5) {
				if (angle_index < 10) {
					angle_index++;
					updateMuzzlePoint();
					this.loadGameHeroImage();
				}
			}
		}
		
		if(afterFier) {
			speedControl++;
			if(speedControl % 2 == 0) {
				frame ++;
				if(frame > 2) {
					frame = 0;
					afterFier = false;
				}
			}
		}
		
		if(showBoom) {
			boomFrame++;
			if (boomFrame > 2) {
				game.nextShooter();
				boomFrame = 0;
				removeBoomImage();
				showBoom = false;
				if(life <= 0) {//闯关失败
//					game.getCanvasControl().playerHandler.playByIndex(6);
					
					game.getCanvasControl().setGoBackView(game);
					game.getCanvasControl().setView(game.getCanvasControl().nullView);
					game.removeResource();
					game.getCanvasControl().setView(new Dialog(game.getCanvasControl(), 8, game));
					
					for (int i = 0; i < game.v_enemy.size(); i++) {
						((Enemy)game.v_enemy.elementAt(i)).shootting = false;
					}
					
					Game.shooterIndex = 0;
					game.x_map = 0;
				}
				System.gc();
			} 
		}
	}
	
	/**
	 * 被打了草
	 * @param attackPower2
	 */
	public void beHit(int power) {
		life -= power;
		if(life <= 30) {
			weak = true;
			game.loadWeakImage();
		}
		showBoom = true;
		loadBoomImage();
	}

	/**
	 * 加载爆炸图片
	 */
	private void loadBoomImage() {
		try {
			Image img_boom = Image.createImage("/hero/boom.png");
			int w = img_boom.getWidth();
			int h = img_boom.getHeight();
			a_img_boom = new Image[3];
			for (int i = 0; i < 3; i++) {
				a_img_boom[i] = Image.createImage(img_boom, w * i / 3, 0, w / 3, h, 0);
			}
			
			img_boom = Image.createImage("/hero/bleed.png");
			w = img_boom.getWidth();
			h = img_boom.getHeight();
			a_img_bleed = new Image[3];
			for (int i = 0; i < 3; i++) {
				a_img_bleed[i] = Image.createImage(img_boom, w * i / 3, 0, w / 3, h, 0);
			}
			
			img_boom = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除爆炸的效果图片
	 */
	private void removeBoomImage() {
		for (int i = 0; i < 3; i++) {
			a_img_boom[i] = null;
		}
		a_img_boom = null;
		
		for (int i = 0; i < 3; i++) {
			a_img_bleed[i] = null;
		}
		a_img_bleed = null;
		
		System.gc();
	}

}
