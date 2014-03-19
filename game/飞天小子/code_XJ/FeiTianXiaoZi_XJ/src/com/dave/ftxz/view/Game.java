package com.dave.ftxz.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.model.Aerolite;
import com.dave.ftxz.model.Bird;
import com.dave.ftxz.model.Bullet;
import com.dave.ftxz.model.Coin;
import com.dave.ftxz.model.Hero;
import com.dave.ftxz.model.Pet;
import com.dave.ftxz.model.Treasure;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;

/**
 * @author Administrator 游戏界面
 */
public class Game implements Showable {

	public CanvasControl canvasControl;

	public Image img_map;// 地图
	public Image img_bar_side;//刻度条
	public Image img_bar_distance;
	public Image img_bar_coin;
	public Image img_number_gura;//刻度数字
	public Image img_key;
	public Image img_airship;
	public Image img_hero;
	public Image img_aerolite;//陨石
	public Image img_coin;
	public Image img_slow;
	public Image img_sprint;//冲刺效果图2
	public Image img_protected;//无敌效果
	
	public Image[] a_img_count_number;//倒计时数字图片
	public Image[] a_img_full_coin;//金币转化为距离效果图片
	
	public Image[] a_img_attract;//吸铁石效果
	public Image[] a_img_sprint;//冲刺效果图1
	public Image[] a_img_boom_bird;//坏鸟爆炸了
	public Image[] a_img_bullet;//子弹图片
	public Image[] a_img_pet;//萌宠图片
	public Image[] a_img_alert;//感叹号
	public Image[] a_img_beHit_bird;//坏鸟被攻击动画图片
	public Image[] a_img_beHit_hero;//英雄被攻击动画图片
	public Image[] a_img_bar_blood;//血条
	public Image[] a_img_treasure;//宝物的图片
	public Image[][] a_2_img_bird;//坏鸟的图片
	
	/**
	 * 道具的信息。[道具type][0]道具等级，与该道具购买的数量相等；
	 * [道具type][1]道具冷却时间。
	 * [道具type][2]道具持续剩余时间。
	 * type0~3:减速、冲刺、无敌、吸铁石
	 */
	public int[][] treasure_info;
	
	/**
	 * y坐标
	 */
	public int y_map = -372;
	
	/**
	 * 存储所有子弹
	 */
	public Vector v_bullet;
	
	/**
	 * 坏鸟
	 */
	public Vector v_bird;
	
	/**
	 * 陨石
	 */
	public Vector v_aerolite;
	
	/**
	 * 金币
	 */
	public Vector v_coin;
	
	/**
	 * 宝物
	 */
	public Vector v_treasure;
	
	/**
	 * vector的size
	 */
	public int size_v;
	
	/**
	 * 控制的英雄
	 */
	public Hero hero;
	
	/**
	 * 金币数量（100以内，100个加为距离100米）
	 */
	public int num_coin;
	
	/**
	 * 距离的刻度单位长度
	 */
	private int length_unit;
	
	/**
	 * 威力等级（购买后就生效。）
	 */
	public int power_level = 0;

	private int gura_ctrl;

	/**
	 * 开始游戏时的倒计时
	 */
	private boolean countdown;

	/**
	 * 倒计时数字动画帧
	 */
	private int frame_count_number;
	
	/**
	 * 进入游戏后的时间
	 */
	private int time_start;

	/**
	 * 倒计时数字y坐标
	 */
	private int y_number;
	
	/**
	 * 倒计时数字y坐标下落速度
	 */
	private int downspeed_y = 20;

	/**
	 * 金币转化效果的坐标
	 */
	private int trans_x, trans_y;

	/**
	 * 金币转化效果的y轴速度
	 */
	private int trans_speed_y;

	/**
	 * 正在转化
	 */
	public boolean trans;
	
	/**
	 * 金币转化效果的图片帧
	 */
	private int frame_trans;

 	public Game(CanvasControl canvasControl) {
 		this.canvasControl = canvasControl;
 		
 		length_unit = 100;
 		v_bullet = new Vector();
 		v_bird = new Vector();
 		v_aerolite = new Vector();
 		v_coin = new Vector();
 		v_treasure = new Vector();
 		
 		treasure_info = new int[4][3];
 		for (int i = 0; i < 4; i++) {
			treasure_info[i][0] = CanvasControl.goodsNumber[i + 5];
			CanvasControl.goodsNumber[i + 5] = 0;
		}
 		
 		////////////////距离和金币清零/////////
 		CanvasControl.distance = 0;
 		CanvasControl.coin_total = 0;
 		
 		createBird();

 		//////////////初始化道具//////////////////
		power_level = CanvasControl.goodsNumber[4];
		CanvasControl.goodsNumber[4] = 0;
 		hero = new Hero(this, CanvasControl.type_hero);

		if(CanvasControl.goodsNumber[3] > 0) {
			new Pet(this, hero);
			CanvasControl.goodsNumber[3] --;
		}
 		
 		canvasControl.saveParam();
 		
 		countdown = true;
 		loadCountImage();
	}

	public void show(Graphics g) {
		g.setClip(0, 0, 640, 530);
		g.drawImage(img_map, 0, y_map, 0);
		g.drawImage(img_map, 0, y_map, Graphics.LEFT | Graphics.BOTTOM);
		
		if(countdown) {
			g.drawImage(a_img_count_number[frame_count_number], 320, y_number, Graphics.VCENTER | Graphics.HCENTER);
			return;
		}
		
		///////////////////////内容///////////////////////////
		size_v = v_coin.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Coin)v_coin.elementAt(i)).show(g);
		}
		
		size_v = v_treasure.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Treasure)v_treasure.elementAt(i)).show(g);
		}
		
		size_v = v_bullet.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Bullet)v_bullet.elementAt(i)).show(g);
		}
		
		hero.show(g);
		
		size_v = v_bird.size(); 
		for (int i = size_v - 1; i >= 0; i--) {
			((Bird)v_bird.elementAt(i)).show(g);
		}
		
		size_v = v_aerolite.size(); 
		for (int i = size_v - 1; i >= 0; i--) {
			((Aerolite)v_aerolite.elementAt(i)).show(g);
		}
		
		///////////////////////////刻度///////////////////////
		g.drawImage(img_bar_side, 10, 250, Graphics.LEFT | Graphics.VCENTER);
		for (int i = 1; i < 10; i++) {
			C.drawString(g, img_number_gura, (length_unit * i) + "", "0123456789", 30, 445 - i * 40, 10, 13, 0, 0, 0);
		}
		g.drawRegion(img_bar_side, 0, 0, 18, 397, Sprite.TRANS_MIRROR, 630, 250, Graphics.RIGHT | Graphics.VCENTER);
		for (int i = 1; i < 10; i++) {
			C.drawString(g, img_number_gura, (10 * i) + "", "0123456789", 610, 445 - i * 40, 10, 13, Graphics.RIGHT | Graphics.TOP, 0, 0);
		}
		gura_ctrl = num_coin - 3;
		gura_ctrl = gura_ctrl > 0 ? gura_ctrl : 0;
		g.setClip(400, 439 - (gura_ctrl) * 385 / 97, 240, 420);
		g.drawImage(img_bar_coin, 612, 53, 0);
		
		gura_ctrl = CanvasControl.distance - 3 * (length_unit / 10);
		gura_ctrl = gura_ctrl > 0 ? gura_ctrl : 0;
		g.setClip(0, 439 - (gura_ctrl) * 385 / (length_unit * 10 - 3 * (length_unit / 10)), 200, 420);
		g.drawImage(img_bar_distance, 1, 53, 0);
		g.setClip(0, 0, 640, 530);
		
		//按键9帮助，0返回。
		g.drawImage(img_key, 630, 520, Graphics.BOTTOM | Graphics.RIGHT);
		
		if(trans) {
			g.drawImage(a_img_full_coin[frame_trans], trans_x, trans_y, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		/////////////////////////////调试面板////////////////////////////////////////
//		g.setColor(0);
//		g.fillRect(200, 0, 100, 120);
//		g.setColor(255, 255, 255);
//		g.drawString("坏鸟数量:" + v_bird.size(), 203, 2, 0);
//		g.drawString("子弹数量:" + v_bullet.size(), 203, 13, 0);
//		g.drawString("金币数量:" + v_coin.size(), 203, 23, 0);
//		g.drawString("宝物数量:" + v_treasure.size(), 203, 33, 0);
//		g.drawString("飞行距离:" + CanvasControl.distance, 203, 43, 0);
//		g.drawString("获得的金币数:" + num_coin, 203, 53, 0);
//		g.drawString("刻度截取:" + gura_ctrl, 203, 63, 0);
//		g.drawString("单位长度:" + length_unit, 203, 73, 0);
	}

	/**
	 * 加载倒计时的图片
	 */
	private void loadCountImage() {
		try {
			a_img_count_number = new Image[7];
			Image img_get = Image.createImage("/game/count.png");
			for (int i = 0; i < 7; i++) {
				a_img_count_number[i] = Image.createImage(img_get, i* 84, 0, 84, 129, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除倒计时图片
	 */
	private void removeCountImage() {
		for (int i = 0; i < 7; i++) {
			a_img_count_number[i] = null;
		}
		a_img_count_number = null;
		
		System.gc();
	}

	public void loadResource() {
		try {
			img_map = Image.createImage("/game/map_" + C.R.nextInt(3) + ".png");
			img_airship = Image.createImage("/game/airship.png");
			img_bar_coin = Image.createImage("/game/bar_coin.png");
			img_bar_distance = Image.createImage("/game/bar_distance.png");
			img_bar_side = Image.createImage("/game/bar_side.png");
			img_key = Image.createImage("/game/key.png");
			img_number_gura = Image.createImage("/game/number_gura.png");
			img_hero = Image.createImage("/game/hero_" + CanvasControl.type_hero + ".png");
			img_aerolite = Image.createImage("/game/aerolite.png");
			img_coin = Image.createImage("/game/coin.png");
			img_slow = Image.createImage("/game/slow.png");
			img_sprint = Image.createImage("/game/sprint_2.png");
			img_protected = Image.createImage("/game/protected.png");

			a_img_bullet = new Image[5];
			for (int i = 0; i < 5; i++) {
				a_img_bullet[i] = Image.createImage("/game/bullet_" + i + ".png");
			}

			a_img_treasure = new Image[4];
			for (int i = 0; i < 4; i++) {
				a_img_treasure[i] = Image.createImage("/game/treasure_" + i + ".png");
			}
			a_img_pet = new Image[4];
			Image img_temp = Image.createImage("/game/pet.png");
			for (int i = 0; i < 4; i++) {
				a_img_pet[i] = Image.createImage(img_temp, i * 51, 0, 51,
						46, 0);
			}
			a_img_attract = new Image[4];
			img_temp = Image.createImage("/game/attract.png");
			for (int i = 0; i < 4; i++) {
				a_img_attract[i] = Image.createImage(img_temp, i * 89, 0, 89,
						78, 0);
			}
			a_img_beHit_bird = new Image[4];
			img_temp = Image.createImage("/game/be_hit_bird.png");
			for (int i = 0; i < 4; i++) {
				a_img_beHit_bird[i] = Image.createImage(img_temp, i * 73, 0, 73,
						64, 0);
			}
			a_img_sprint = new Image[4];
			img_temp = Image.createImage("/game/sprint_1.png");
			for (int i = 0; i < 4; i++) {
				a_img_sprint[i] = Image.createImage(img_temp, i * 111, 0, 111,
						154, 0);
			}
			a_img_boom_bird = new Image[5];
			img_temp = Image.createImage("/game/boom_bird.png");
			for (int i = 0; i < 5; i++) {
				a_img_boom_bird[i] = Image.createImage(img_temp, i * 102, 0, 102,
						87, 0);
			}
			a_img_beHit_hero = new Image[2];
			img_temp = Image.createImage("/game/be_hit_hero.png");
			for (int i = 0; i < 2; i++) {
				a_img_beHit_hero[i] = Image.createImage(img_temp, i * 199, 0, 199,
						191, 0);
			}
			a_img_alert = new Image[2];
			img_temp = Image.createImage("/game/alert.png");
			for (int i = 0; i < 2; i++) {
				a_img_alert[i] = Image.createImage(img_temp, i * 84, 0, 84,
						84, 0);
			}
			a_img_bar_blood = new Image[2];
			img_temp = Image.createImage("/game/bar_blood.png");
			for (int i = 0; i < 2; i++) {
				a_img_bar_blood[i] = Image.createImage(img_temp, i * 48, 0, 48,
						6, 0);
			}

			a_2_img_bird = new Image[5][2];
			for (int i = 0; i < 5; i++) {
				img_temp = Image.createImage("/game/bird_" + i + ".png");
				a_2_img_bird[i][0] = Image.createImage(img_temp, 0, 0,
						img_temp.getWidth() >> 1, img_temp.getHeight(), 0);
				a_2_img_bird[i][1] = Image.createImage(img_temp,
						img_temp.getWidth() >> 1, 0, img_temp.getWidth() >> 1,
						img_temp.getHeight(), 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除从服务器下载的图片
	 */
	public void removeServerImage() {
		img_map = null;
		img_airship = null;
		img_bar_coin = null;
		img_bar_distance = null;
		img_bar_side = null;
		img_key = null;
		img_number_gura = null;
		img_hero = null;
		img_aerolite = null;
		img_coin = null;
		img_slow = null;
		img_sprint = null;
		img_protected = null;

		for (int i = 0; i < 5; i++) {
			a_img_bullet[i] = null;
		}
		a_img_bullet = null;

		for (int i = 0; i < 4; i++) {
			a_img_treasure[i] = null;
		}
		a_img_treasure = null;
		for (int i = 0; i < 4; i++) {
			a_img_pet[i] = null;
		}
		a_img_pet = null;
		for (int i = 0; i < 4; i++) {
			a_img_attract[i] = null;
		}
		a_img_attract = null;
		for (int i = 0; i < 4; i++) {
			a_img_beHit_bird[i] = null;
		}
		a_img_beHit_bird = null;
		for (int i = 0; i < 4; i++) {
			a_img_sprint[i] = null;
		}
		a_img_sprint = new Image[4];
		for (int i = 0; i < 5; i++) {
			a_img_boom_bird[i] = null;
		}
		a_img_boom_bird = null;
		for (int i = 0; i < 2; i++) {
			a_img_beHit_hero[i] = null;
		}
		a_img_beHit_hero = null;
		for (int i = 0; i < 2; i++) {
			a_img_alert[i] = null;
		}
		a_img_alert = null;
		for (int i = 0; i < 2; i++) {
			a_img_bar_blood[i] = null;
		}
		a_img_bar_blood = null;
		
		for (int i = 0; i < 5; i++) {
			a_2_img_bird[i][0] = null;
			a_2_img_bird[i][1] = null;
		}
		a_2_img_bird = null;
		System.gc();
	}

	public void removeResource() {
		
		System.gc();
	}

	public void keyPressed(int keyCode) {
		hero.keyPressed(keyCode);
		switch (keyCode) {
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(new Dialog(canvasControl, 1, this));
			break;
		default:
			break;
		}
	}
	
	public void keyReleased(int keyCode) {
		hero.keyReleased(keyCode);
	}

	public void keyRepeated(int keyCode) {
		
	}

	public void logic() {
		if(countdown) {
			time_start ++;
			if(time_start == 10) {
				frame_count_number ++;
				downspeed_y = 0;
				y_number = 0;
			}
			else if(time_start >= 20) {
				if(frame_count_number < 6) 
					frame_count_number ++;
				
				if(time_start > 30){
					countdown = false;
					removeCountImage();
				}
			}
			
			if(time_start < 20) {
				downspeed_y += 5;
				y_number += downspeed_y;
				if(y_number >= 200) {
					y_number = 200;
				}
			}
			return;
		}
		size_v = v_bird.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Bird)v_bird.elementAt(i)).logic();
		}
		
		size_v = v_aerolite.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Aerolite)v_aerolite.elementAt(i)).logic();
		}
		
		size_v = v_bullet.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Bullet)v_bullet.elementAt(i)).logic();
		}
		
		size_v = v_coin.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Coin)v_coin.elementAt(i)).logic();
		}
		
		size_v = v_treasure.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Treasure)v_treasure.elementAt(i)).logic();
		}
		
		hero.logic();
		
		check();
	}
	
	/**
	 * 其他的检测逻辑
	 */
	private void check() {
		if(canvasControl.reliving) {
			relive();
			canvasControl.reliving = false;
		}
		
		if(CanvasControl.distance > 100000000)
			length_unit = 100000000;
		else if(CanvasControl.distance > 10000000)
			length_unit = 10000000;
		else if(CanvasControl.distance > 1000000)
			length_unit = 1000000;
		else if(CanvasControl.distance > 100000)
			length_unit = 100000;
		else if(CanvasControl.distance > 10000)
			length_unit = 10000;
		else if(CanvasControl.distance > 1000)
			length_unit = 1000;
		
		if(v_bird.isEmpty())
			createBird();
		
		for (int i = 0; i < 4; i++) {
			if(treasure_info[i][2] > 0) {
				treasure_info[i][2] --;
				if(i == 1) {//冲刺
					if(treasure_info[i][2] < 50) {
						if(hero.speed_fly > 2)
							hero.speed_fly --;
					}
				}
			}
		}
		
		if(trans) {//金币转化距离的逻辑处理
			trans_x -= 50;
			
			if(trans_x < 150)
				frame_trans = 4;
			else if(trans_x < 300)
				frame_trans = 3;
			else if(trans_x < 400)
				frame_trans = 2;
			else if(trans_x < 500)
				frame_trans = 1;
			
			if(trans_x <= 320)
				trans_y += trans_speed_y;
			else 
				trans_y -= trans_speed_y;
			
			if(trans_x < 20) {
				trans = false;
				CanvasControl.distance += 500;
//				num_coin -= 100;
				removeFullcoinImage();
			}
		}
		
		createAerolite();
		createTreasure();
	}

	/**
	 * 生产敌人。
	 */
	public void createBird() {
		int type_ctrl = 0;
		if(CanvasControl.distance <= 1000)
			type_ctrl = 1;
		else if (CanvasControl.distance <= 2000)
			type_ctrl = 2;
		else if (CanvasControl.distance <= 3000)
			type_ctrl = 3;
		else if (CanvasControl.distance <= 4000)
			type_ctrl = 4;
		else
			type_ctrl = 5;
		
		for (int i = 0; i < 5; i++) {
			int type_temp = C.R.nextInt(type_ctrl);
			v_bird.addElement(new Bird(170 + 75 * i, 100, this, type_temp));
		}
	}
	
	/**
	 * 产生陨石
	 */
	public void createAerolite() {
		if(CanvasControl.distance % 400 == 0) {//产生三个陨石
			for (int i = 0; i < 3; i++) {
				v_aerolite.addElement(new Aerolite(this, hero.x - 80 + i * 80, 0));
			}
		} else if(CanvasControl.distance % 200 == 0) {//产生一个陨石
			v_aerolite.addElement(new Aerolite(this, hero.x, 0));
		}
	}
	
	/**
	 * 产生宝物
	 */
	public void createTreasure() {
		for (int i = 0; i < 4; i++) {
			if(treasure_info[i][2] <= 0) {
				if(treasure_info[i][0] > 5) {
					treasure_info[i][1] ++;
					if(treasure_info[i][1] > 100){
						treasure_info[i][1] = 0;
						v_treasure.addElement(new Treasure(this, C.R.nextInt(300) + 170, 0, i));
					}
				} else if(treasure_info[i][0] > 0) {
					treasure_info[i][1] ++;
					if(treasure_info[i][1] > 350 - treasure_info[i][0] * 50){
						treasure_info[i][1] = 0;
						v_treasure.addElement(new Treasure(this, C.R.nextInt(300) + 170, 0, i));
					}
				}
			}
		}
	}
	
	/**
	 * 复活
	 */
	public void relive() {
		hero = new Hero(this, CanvasControl.type_hero);
	}

	
	/**
	 * 100金币转化为500距离
	 */
	public void coinTransformDistance() {
		trans = true;
		trans_x = 600;
		trans_y = 50;
		trans_speed_y = 5;
		loadFullCoinImage();
	}

	/**
	 * 加载金币转化距离的效果图
	 */
	private void loadFullCoinImage() {
		try {
			a_img_full_coin = new Image[5];
			Image img_get = Image.createImage("/game/full_coin.png");
			for (int i = 0; i < 5; i++) {
				a_img_full_coin[i] = Image.createImage(img_get, 0, i * 46, 104, 46, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除金币转化距离的效果图
	 */
	private void removeFullcoinImage() {
		for (int i = 0; i < 5; i++) {
			a_img_full_coin[i] = null;
		}
		a_img_full_coin = null;
		
		System.gc();
	}
	
}
