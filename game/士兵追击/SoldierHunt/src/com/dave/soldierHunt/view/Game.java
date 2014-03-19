package com.dave.soldierHunt.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.model.Coin;
import com.dave.soldierHunt.model.Devil;
import com.dave.soldierHunt.model.Hero;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;

public class Game implements Showable {
	private CanvasControl canvasControl;
	
	private Image img_map;
	private Image img_bottom;
	private Image img_top;
	private Image img_number;
	
	/**
	 * ��������Ҳ�����Ӣ��
	 */
	public Hero hero;
	
	/**
	 * �洢��ǰ���е�ʿ��
	 */
	public Vector soldiers;
	
	/**
	 * ��ǰ��ͼ�ϵĽ��
	 */
	public Vector coins;
	
	/**
	 * �洢��ǰ���еĵ���
	 */
	public Vector devils;
	
	/**
	 * ��Ļ���е�x����
	 */
	public static final int CENTER_X = 320;
	/**
	 * ��Ļ���е�y����
	 */
	public static final int CENTER_Y = 260;
	
	/**
	 * ��ͼ���½Ƕ���x����
	 */
	public int mapX = 0;
	/**
	 * ��ͼ���½Ƕ���y����
	 */
	public int mapY = 530;
	
	/**
	 * �ڼ���
	 */
	private int level;
	
	/**
	 * �ؿ�ʹ��ʱ�䣨��λ�����루1�� = 10���룩��
	 */
	private int levelUseTime;
	
	/**
	 * ���سԵ��Ľ������
	 */
	public int coinCount;
	
	/**
	 * ����Ŀ������
	 */
	private final int targetCoin;

	public Game(CanvasControl canvasControl, int level) {
		this.canvasControl = canvasControl;
		this.level = level;
		targetCoin = 30 + (level - 1) * 50;
		
		ImageManager.loadHeroImage();
		ImageManager.loadDevilImage();
		hero = new Hero(this, 0);
		soldiers = new Vector();
		devils = new Vector();
		coins = new Vector();
		
	}

	/**
	 * �Զ���������
	 */
	public void autoCreateDevil() {
		switch(level) {
		case 1:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, 1, C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		}
	}
	
	public void show(Graphics g) {
		g.drawImage(img_map, mapX, mapY, Graphics.LEFT | Graphics.BOTTOM);
		
		hero.show(g);
		int size = soldiers.size();
		for(int i=0; i<size; i++){
			((Hero)soldiers.elementAt(i)).show(g);
		}
		size = devils.size();
		for(int i=0; i<size; i++){
			((Devil)devils.elementAt(i)).show(g);
		}
		size = coins.size();
		for(int i=0; i<size; i++) {
			((Coin)coins.elementAt(i)).show(g);
		}
		
		g.drawImage(img_bottom, 0, 530, Graphics.LEFT | Graphics.BOTTOM);
		g.drawImage(img_top, 0, 0, 0);
		
		C.drawString(g, img_number, level + "", "0123456789X", 100, 7, 19, 24, 0, 0, 0);
		C.drawString(g, img_number, targetCoin + "", "0123456789X", 295, 7, 19, 24, 0, 0, 0);
		C.drawString(g, img_number, coinCount + "", "0123456789X", 535, 7, 19, 24, 0, 0, 0);
	}

	public void loadResource() {
		try {
			img_map = Image.createImage("/game/map.png");
			img_bottom = Image.createImage("/game/bottom.png");
			img_top = Image.createImage("/game/top.png");
			img_number = Image.createImage("/game/number.png");
			
			C.out("number image width : " + img_number.getWidth());
			C.out("number image height : " + img_number.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_map = null;
		img_bottom = null;
		img_top = null;
		img_number = null;
		
		System.gc();
	}

	public void keyPressed(int keyCode) {
		hero.keyPressed(keyCode);
		
		switch(keyCode) {
		case C.KEY_1://��������ʿ��1
			addSoldier(1);
			break;
		case C.KEY_2://��������ʿ��2
			addSoldier(2);
			break;
		case C.KEY_3://��������ʿ��3
			addSoldier(3);
			break;
		case C.KEY_4://��������ʿ��4
			addSoldier(4);
			break;
		case C.KEY_5://��������ʿ��5
			addSoldier(5);
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(new Home(canvasControl));
			this.removeResource();
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		autoCreateDevil();
		levelUseTime ++;
		
		hero.logic();
		hero.hitFences();
		hero.hitSolider();
		hero.eatCoin();
		
		for(int i=0; i<soldiers.size(); i++){
			((Hero)soldiers.elementAt(i)).logic();
		}
		
		for(int i=0; i<devils.size(); i++){
			((Devil)devils.elementAt(i)).logic();
		}
		
		for(int i=0; i<coins.size(); i++) {
			((Coin)coins.elementAt(i)).logic();
		}
		check();
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

	private void check() {
		if(!hero.alive) {
			canvasControl.setView(new Dialog(canvasControl, 2));
			canvasControl.setGoBackView(this);
			this.removeResource();
		}
	}

	
	/**
	 * ����һ���ض����͵�ʿ��
	 * @param type ���͡�
	 */
	public void addSoldier(int type) {
		Hero newHero = new Hero(this, type);
		if(soldiers.size() == 0){
			hero.setNextHero(newHero);
			newHero.setPreviousHero(hero);
		} else {
			Hero lastHero = (Hero)soldiers.elementAt(soldiers.size() - 1);
			lastHero.setNextHero(newHero);
			newHero.setPreviousHero(lastHero);
		}
		Hero previousHero = newHero.previous();
		newHero.direction = previousHero.direction;
		switch(newHero.direction) {
		case 1://����
			newHero.x = previousHero.x - 50;
			newHero.y = previousHero.y;
			break;
		case 2://����
			newHero.x = previousHero.x;
			newHero.y = previousHero.y + 50;
			break;
		case 3://����
			newHero.x = previousHero.x + 50;
			newHero.y = previousHero.y;
			break;
		case 4://����
			newHero.x = previousHero.x;
			newHero.y = previousHero.y - 50;
			break;
		}
		soldiers.addElement(newHero);
	}
	
}
