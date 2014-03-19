package com.dave.rangzidanf.gameModel;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.tool.C;

public class Enemy {
	private Image[] ima_aliveEnemy;
	private Image[] ima_deadEnemy;
	private Image[] ima_moveEnemy;
	
	/**
	 * 军衔
	 * 0：二等兵。
	 * 1：上尉。
	 * 2：中校。
	 * 3：上将。
	 * 4：首相。
	 */
	private int ranks;
	
	/**
	 * 生命值
	 */
	private int health;
	
	/**
	 * 最大血量
	 */
	private int healthMax;
	
	/**
	 * 记录血条长度
	 */
	private int healthLength;
	
	private int moveSpeed;
	
	/**
	 * x轴坐标值
	 */
	private int x;
	/**
	 * y坐标值
	 */
	private int y;
	
	/**
	 * 用来实现动画
	 */
	private int aliveImageIndex = C.R.nextInt(2);
	private int aliveActionControl = 1;
	
	private int deadImageIndex = 0;
	
	private int moveImageIndex = C.R.nextInt(9);
	private int moveActionControl;
	
	private long animationStartTime;
	
	/**
	 * 生死
	 */
	private boolean alive;
	
	/**
	 * 记录是否在壁垒里面。
	 */
	private boolean inBunker;
	
	/**
	 * 在碉堡或被挡住的时候需要显示出来的x值
	 */
	private int bunkerX;
	/**
	 * 在碉堡或被挡住的时候需要显示出来的y值
	 */
	private int bunkerY;
	/**
	 * 在碉堡或被挡住的时候需要显示出来的宽度
	 */
	private int bunkerW;
	/**
	 * 在碉堡或被挡住的时候需要显示出来的高度
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
	 * 初始化生命值和移动速度
	 */
	public void init() {
		switch(ranks){
		case 0:
			health = 100;
			healthMax = 100;
			moveSpeed = 2;//不动，只是用来控制头部动画。
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
				
//				g.setColor(0);//血条
//				g.fillRect(x - healthMax / 32, y - 42, healthMax / 16, 4);
//				g.setColor(230, 0, 17);
//				g.fillRect(x - healthMax / 32, y - 42, health / 16, 4);
				
				g.setColor(0);//血条
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
			

			g.setColor(0);//血条
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
	 * 动画逻辑
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
