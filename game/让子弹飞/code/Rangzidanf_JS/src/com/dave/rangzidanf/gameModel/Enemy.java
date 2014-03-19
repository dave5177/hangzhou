package com.dave.rangzidanf.gameModel;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.tool.C;

public class Enemy {
	private Image[] ima_aliveEnemy;
	private Image[] ima_deadEnemy;
	private Image[] ima_moveEnemy;
	
	/**
	 * ����
	 * 0�����ȱ���
	 * 1����ξ��
	 * 2����У��
	 * 3���Ͻ���
	 * 4�����ࡣ
	 */
	private int ranks;
	
	/**
	 * ����ֵ
	 */
	private int health;
	
	/**
	 * ���Ѫ��
	 */
	private int healthMax;
	
	/**
	 * ��¼Ѫ������
	 */
	private int healthLength;
	
	private int moveSpeed;
	
	/**
	 * x������ֵ
	 */
	private int x;
	/**
	 * y����ֵ
	 */
	private int y;
	
	/**
	 * ����ʵ�ֶ���
	 */
	private int aliveImageIndex = C.R.nextInt(2);
	private int aliveActionControl = 1;
	
	private int deadImageIndex = 0;
	
	private int moveImageIndex = C.R.nextInt(9);
	private int moveActionControl;
	
	private long animationStartTime;
	
	/**
	 * ����
	 */
	private boolean alive;
	
	/**
	 * ��¼�Ƿ��ڱ������档
	 */
	private boolean inBunker;
	
	/**
	 * �ڵﱤ�򱻵�ס��ʱ����Ҫ��ʾ������xֵ
	 */
	private int bunkerX;
	/**
	 * �ڵﱤ�򱻵�ס��ʱ����Ҫ��ʾ������yֵ
	 */
	private int bunkerY;
	/**
	 * �ڵﱤ�򱻵�ס��ʱ����Ҫ��ʾ�����Ŀ��
	 */
	private int bunkerW;
	/**
	 * �ڵﱤ�򱻵�ס��ʱ����Ҫ��ʾ�����ĸ߶�
	 */
	private int bunkerH;

	public Enemy(int ranks) {
		this.ranks = ranks;
		alive = true;
		inBunker = false;
		
		init();
		
		if(moveImageIndex > 4) moveActionControl = -1;
		else moveActionControl = 1;
//		loadResource();
	}
	
	/**
	 * ��ʼ������ֵ���ƶ��ٶ�
	 */
	public void init() {
		switch(ranks){
		case 0:
			health = 100;
			healthMax = 100;
			moveSpeed = 2;//������ֻ����������ͷ��������
			break;
		case 1:
			health = 120;
			healthMax = 120;
			moveSpeed = 2;
			break;
		case 2:
			health = 220;
			healthMax = 220;
			moveSpeed = 2;
			break; 
		case 3:
			health = 420;
			healthMax = 420;
			moveSpeed = 4;
			break;
		case 4:
			health = 600;
			healthMax = 600;
			moveSpeed = 6;
			break;
		}
		
		healthLength = 42 - 2400 / healthMax;
	}
	
	public void show(Graphics g){
		if(inBunker){
//			g.setClip(x - 13, y - 80, 20, 60);
			g.setClip(bunkerX, bunkerY, bunkerW, bunkerH);
			if(alive) {
				g.drawImage(ima_aliveEnemy[aliveImageIndex], x, y, Graphics.BOTTOM | Graphics.HCENTER);
				
//				g.setColor(0);//Ѫ��
//				g.fillRect(x - healthMax / 32, y - 42, healthMax / 16, 4);
//				g.setColor(230, 0, 17);
//				g.fillRect(x - healthMax / 32, y - 42, health / 16, 4);
				
				g.setColor(0);//Ѫ��
				g.fillRect(x - healthLength / 2, y - 42, healthLength, 4);
				g.setColor(230, 0, 17);
				g.fillRect(x - healthLength / 2, y - 42, healthLength * health / healthMax, 4);
				
				g.setClip(0, 0, 640, 530);
				return;
			}
		}
		
		if(alive){
			if(ranks == 0) g.drawImage(ima_aliveEnemy[aliveImageIndex], x, y, Graphics.BOTTOM | Graphics.HCENTER);
			else g.drawImage(ima_moveEnemy[moveImageIndex], x, y, Graphics.BOTTOM | Graphics.HCENTER);
			

			g.setColor(0);//Ѫ��
			g.fillRect(x - healthLength / 2, y - 42, healthLength, 4);
			g.setColor(230, 0, 17);
			g.fillRect(x - healthLength / 2, y - 42, healthLength * health / healthMax, 4);
//			g.setColor(235, 97, 0);
//			g.drawRect(x - healthMax / 8, y - 50, healthMax / 4, 5);
			
		}else g.drawImage(ima_deadEnemy[deadImageIndex], x, y, Graphics.BOTTOM | Graphics.HCENTER);
		
		g.setClip(0, 0, 640, 530);
	}

	public Enemy setImage(Image[] ima_aliveEnemy, Image[] ima_deadEnemy, Image[] ima_moveEnemy) {
		this.ima_aliveEnemy = ima_aliveEnemy;
		this.ima_deadEnemy = ima_deadEnemy;
		this.ima_moveEnemy = ima_moveEnemy;
		return this;
	}
	
	public Enemy put(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	/**
	 * �����߼�
	 */
	public void move() {
		if(animationStartTime == 0) {
			animationStartTime = System.currentTimeMillis();
		}else if(System.currentTimeMillis() - animationStartTime > 1000 / moveSpeed) {
			switch(ranks) {
			case 0:{
				if(alive) {
					aliveImageIndex += aliveActionControl;
					if(aliveImageIndex <= 0 || aliveImageIndex >= 2) aliveActionControl *= -1;
				} else {
					if(deadImageIndex < 1) deadImageIndex ++;
				}
			} break;
			case 1:
			case 2: 
			case 3:
			case 4:{
				if(alive && !inBunker) {
					moveImageIndex += 1;
					if(moveImageIndex >= 10) moveImageIndex = 0;
					x += moveActionControl * 10;
					if(moveImageIndex == 0 || moveImageIndex == 5) moveActionControl *= -1;
				} else if(alive && inBunker) {
					aliveImageIndex += aliveActionControl;
					if(aliveImageIndex <= 0 || aliveImageIndex >= 2) aliveActionControl *= -1;
				} else if(!alive) {
					if(deadImageIndex < 1) deadImageIndex ++;
				}
//				if(alive && !inBunker) {
//					moveImageIndex += 1;
//					if(moveImageIndex >= 10) moveImageIndex = 0;
//					x += moveActionControl * 10;
//					if(moveImageIndex == 0 || moveImageIndex == 5) moveActionControl *= -1;
//				} else if (!alive){
//					if(deadImageIndex < 1) deadImageIndex ++;
//				} 
			} break;
			}
			animationStartTime = System.currentTimeMillis();
		}
	}
	
	public final boolean isAlive() {
		return alive;
	}

	public final void setAlive(boolean alive) {
		this.alive = alive;
	}

	
	public final boolean isInBunker() {
		return inBunker;
	}

	
	public final void setInBunker(boolean inBunker, int x, int y, int w, int h) {
		this.inBunker = inBunker;
		this.bunkerX = x;
		this.bunkerY = y;
		this.bunkerW = w;
		this.bunkerH = h;
	}

	
	public final int getX() {
		return x;
	}

	
	public final int getY() {
		return y;
	}

	
	public final int getRanks() {
		return ranks;
	}

	
	public final int getHealth() {
		return health;
	}

	
	public final void setHealth(int health) {
		this.health = health;
	}

	public final int getDeadImageIndex() {
		return deadImageIndex;
	}

	public final int getBunkerX() {
		return bunkerX;
	}

	public final int getBunkerY() {
		return bunkerY;
	}

	public final int getBunkerW() {
		return bunkerW;
	}

	public final int getBunkerH() {
		return bunkerH;
	}

}
