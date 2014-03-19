package com.dave.rangzidanf.gameModel;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.tool.AudioPlaydalin;
import com.dave.rangzidanf.tool.C;

public class Gun {
	/**
	 * 枪的型号
	 * 0：awm-a。
	 * 1：ak47.
	 * 2：m16.
	 * 3：at15.
	 * 4：m4.
	 * 5：barret.
	 */
	private int type;
	
	/**
	 * 存储各个枪支的参数
	 * 从左到右分别是：
	 * 伤害值，射程，射速，换弹速度，后坐力，弹匣容量
	 */
	private int[][] gunparameter = {
			{100, 3, 5, 5, 6, 25},
			{120, 6, 7, 7, 6, 30},
			{120, 6, 8, 8, 6, 30},
			{120, 6, 8, 7, 6, 30},
			{140, 8, 9, 7, 8, 30},
			{150, 9, 9, 5, 10, 30}
	};
	
	/**
	 * 记录当前枪支所剩子弹数量
	 */
	private int bullets;
	
	/**
	 * 抢的攻击力
	 */
	private int attackPower;
	
	/**
	 * 枪的最远射击距离
	 */
	private int shotDistance;
	
	/**
	 * 枪的射速，就是最快射击频率
	 */
	private int firingRate;
	
	/**
	 * 枪的换弹速度
	 */
	private int reloadingSpeed;
	
	/**
	 * 枪的后坐力
	 */
	private int recoil;
	
	/**
	 * 枪支子弹容量
	 */
	private int magazineCapacity;
	
	/**
	 * 当前弹匣所剩子弹
	 */
	private int magazineBullets = 5;
	
	/**
	 * 是否在换子弹。
	 */
	private boolean isResetting;

//	/**
//	 * 枪声
//	 */
//	private AudioPlay gunSoundPlayer;
	
	/**
	 * 换子弹开始时间。
	 */
	private long resetStartTime;
	
	private Image img_gun;
	
	public Gun(int type) {
		this.type = type;
		loadResource(type);
		loadParameter(type);
		this.bullets = this.magazineCapacity;
	}

	public void show(Graphics g, int x, int y) {
		g.drawImage(img_gun, x, y , 0);
	}
	
	/**
	 * 射击
	 */
	public void shoot(AudioPlaydalin play) {
		magazineBullets --;
		bullets --;
//		if(isResetting) isResetting = false;
		
		play.playSound(type);
//		gunSoundPlayer.playSound();
		
	}
	
	public void showBullets(Graphics g, Image img_bullet, Image img_number_bullets) {
		for(int i=0; i<magazineBullets; i++) {
			g.drawImage(img_bullet, 633 - 15 * i, 475, Graphics.BOTTOM | Graphics.RIGHT);
		}
		C.drawString(g, img_number_bullets, "" + bullets, "0123456789", 543, 452, 8, 10, 0, 0, 0);
		g.setColor(255, 255, 255);
	}

	/**
	 * 换子弹
	 */
	public void fillMagazine() {
		isResetting = true;
		resetStartTime = System.currentTimeMillis();
	}
	
	public void loadResource(int gunIndex) {
		try {
			img_gun = Image.createImage("/game/gun_" + gunIndex + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

//		gunSoundPlayer = new AudioPlay(type);
		
	}
	
	/**
	 * @param gunIndex
	 * 加载参数
	 */
	public void loadParameter(int gunIndex) {
		attackPower = gunparameter[gunIndex][0];
		shotDistance = gunparameter[gunIndex][1];
		firingRate = gunparameter[gunIndex][2];
		reloadingSpeed = gunparameter[gunIndex][3];
		recoil = gunparameter[gunIndex][4];
		magazineCapacity = gunparameter[gunIndex][5];
	}
	
	public void removeResource() {
		img_gun = null;
//		gunSoundPlayer.close();
		
		System.gc();
	}
	
	public final int getAttackPower() {
		return attackPower;
	}
	
	public final int getShotDistance() {
		return shotDistance;
	}
	
	public final int getFiringRate() {
		return firingRate;
	}
	
	public final int getReloadingSpeed() {
		return reloadingSpeed;
	}
	
	public final int getRecoil() {
		return recoil;
	}
	
	public final int getMagazineCapacity() {
		return magazineCapacity;
	}

	public final int getBullets() {
		return bullets;
	}
	
	public final void setBullets(int bullets) {
		this.bullets = bullets;
	}
	
	public final int getType() {
		return type;
	}

	public final int getMagazineBullets() {
		return magazineBullets;
	}

	/**
	 * 循环控制，主要用于控制换子弹。
	 */
	public void logic() {
		if(isResetting) {
			long thisTime = System.currentTimeMillis();
			if(thisTime - resetStartTime >= 5000 / reloadingSpeed) {
				if(magazineBullets < 5 && magazineBullets < bullets) {
					magazineBullets ++;
				} else isResetting = false;
				
				resetStartTime = thisTime;
			}
		}
	}

	public boolean isResetting() {
		return isResetting;
	}
}
