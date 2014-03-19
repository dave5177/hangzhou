package com.dave.rangzidanf.gameModel;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.tool.AudioPlaydalin;
import com.dave.rangzidanf.tool.C;

public class Gun {
	/**
	 * ǹ���ͺ�
	 * 0��awm-a��
	 * 1��ak47.
	 * 2��m16.
	 * 3��at15.
	 * 4��m4.
	 * 5��barret.
	 */
	private int type;
	
	/**
	 * �洢����ǹ֧�Ĳ���
	 * �����ҷֱ��ǣ�
	 * �˺�ֵ����̣����٣������ٶȣ�����������ϻ����
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
	 * ��¼��ǰǹ֧��ʣ�ӵ�����
	 */
	private int bullets;
	
	/**
	 * ���Ĺ�����
	 */
	private int attackPower;
	
	/**
	 * ǹ����Զ�������
	 */
	private int shotDistance;
	
	/**
	 * ǹ�����٣�����������Ƶ��
	 */
	private int firingRate;
	
	/**
	 * ǹ�Ļ����ٶ�
	 */
	private int reloadingSpeed;
	
	/**
	 * ǹ�ĺ�����
	 */
	private int recoil;
	
	/**
	 * ǹ֧�ӵ�����
	 */
	private int magazineCapacity;
	
	/**
	 * ��ǰ��ϻ��ʣ�ӵ�
	 */
	private int magazineBullets = 5;
	
	/**
	 * �Ƿ��ڻ��ӵ���
	 */
	private boolean isResetting;

//	/**
//	 * ǹ��
//	 */
//	private AudioPlay gunSoundPlayer;
	
	/**
	 * ���ӵ���ʼʱ�䡣
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
	 * ���
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
	 * ���ӵ�
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
	 * ���ز���
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
	 * ѭ�����ƣ���Ҫ���ڿ��ƻ��ӵ���
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
