package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Hero {
	/**
	 * 存储英雄的移动方向。
	 * 0：静止。
	 * 1：向右（默认值）。
	 * 2：向上。
	 * 3：向左。
	 * 4：向下。
	 */
	public int direction = 1;
	
	/**
	 * 记录攻击时的方向
	 */
	public int attackDir;
	
	/**
	 * 方向键连按的时候存储下一个方向
	 */
	public int nextDirection;
	
	/**
	 * 士兵的类型
	 * 0代表英雄
	 * 1~5分别代表士兵1~士兵5.
	 */
	public int type;
	
	/**
	 * 该英雄或士兵的下一个士兵
	 */
	private Hero nextHero;

	/**
	 * 该英雄或士兵的上一个英雄或士兵
	 */
	private Hero previousHero;
	
	/**
	 * 移动的偏移值
	 */
	private static final int MOVE_OFFER = 10;
	
	/**
	 * 活着还是死去？
	 */
	public boolean alive = true;
	
	/**
	 * 血值上限
	 */
	public int lifeMax;
	
	/**
	 * 生命值（血量）
	 */
	public int life;
	
	/**
	 * 攻击力
	 */
	public int attackPower;
	
	/**
	 * 英雄等级
	 */
	public static int level;
	
	/**
	 * 相对于地图x坐标值。
	 */
	public int x = 320;
	
	/**
	 * 相对于地图y坐标值。
	 */
	public int y = 489;
	
	/**
	 * 移动图片下标值
	 */
	private int moveIndex;

	/**
	 * 英雄所在的游戏
	 */
	private Game game;

	/**
	 * 处于刚改变方向的状态，防止过快改变方向。
	 */
	private boolean afterChange;

	private int keySpeedControl;

	/**
	 * 处于刚攻击完状态
	 */
	private boolean attacked;
	
	/**
	 * 攻击完后的时间记录
	 */
	private int afterAttactTime;
	
	/**
	 * 攻击图片下标值记录
	 */
	private int attactImageIndex;
	
	/**
	 * 是否显示攻击的图片
	 */
	private boolean showAttactImage;

	/**
	 * 铁球图片下标值
	 */
	private int ironBallIndex;

	/**
	 * 攻击效果图片x坐标值。
	 */
	private int effectX;
	/**
	 * 攻击效果图片y坐标值。
	 */
	private int effectY;
	
	/**
	 * 子弹、箭矢、法球等移动的x坐标偏移量
	 */
	private int attackingDevilXOffer;
	/**
	 * 子弹、箭矢、法球等移动的y坐标偏移量
	 */
	private int attackingDevilYOffer;

	/**
	 * 子弹等还没打到敌人
	 */
	private boolean effectShowing;

	/**
	 * 控制子弹等的显示时间
	 */
	private int effectShowControl;
	
	/**
	 * 子弹等的移动速度。
	 */
	private static final int bulletOffer = 50;
	
	/**
	 * 攻击对象
	 */
	private Devil hittingDevil;

	/**
	 * 死了
	 */
	private boolean willDie;

	public Hero(Game game, int type) {
		this.game = game;
		this.type = type;
		
		init();
	}
	
	private void init() {
		if(type == 0) {
			this.lifeMax = C.heroParameter[level][0];
			this.attackPower = C.heroParameter[level][1];
		} else {
			this.lifeMax = C.soldierParameter[type - 1][0];
			this.attackPower = C.soldierParameter[type - 1][1];
		}
		
		this.life = this.lifeMax;
	}

	public void show(Graphics g) {
		if(showAttactImage) {
			g.drawImage(ImageManager.ima_hero_attack[type][attackDir - 1][attactImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(ImageManager.ima_hero_move[type][direction - 1][moveIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		if(effectShowing) {
			showAttackEffect(g);
		}
	}

	/**
	 * 显示攻击特效
	 * @param g
	 */
	public void showAttackEffect(Graphics g) {
		switch(type) {
		case 0:
			g.drawImage(ImageManager.ima_ironBall[ironBallIndex], effectX + game.mapX, game.mapY + effectY - 759, Graphics.VCENTER | Graphics.HCENTER);
			break;
		case 2:
			g.drawImage(ImageManager.ima_arrow[attackDir - 1], effectX + game.mapX, game.mapY + effectY - 759, Graphics.VCENTER | Graphics.HCENTER);
			break;
		case 3:
			g.drawImage(ImageManager.ima_fireBall[attackDir - 1], effectX + game.mapX, game.mapY + effectY - 759, Graphics.VCENTER | Graphics.HCENTER);
			break;
		}
	}
	
	/**
	 * 改变方向方法
	 * @param gotoDir 设置方向参数
	 */
	public void changeDirection(int gotoDir) {
		if(gotoDir != direction && gotoDir - direction != 2 && gotoDir - direction != -2){
			direction = gotoDir;
			afterChange = true;

			System.gc();
		}
	}
	
	/**
	 * 主逻辑方法
	 */
	public void logic() {
		move();
		if(!attacked) autoAttack();
		
		timeControl();
		
		if(type != 0) {
			follow();
		}
	}

	/**
	 * 时间控制方法
	 */
	private void timeControl() {
		if(attacked) {//攻击间隔、图片显示的控制
			attackEffectMove();
			
			afterAttactTime ++;
			if(afterAttactTime == 1) {
				attactImageIndex ++;
			} else if(afterAttactTime == 3) {
				showAttactImage = false;
				attactImageIndex = 0;
			} else if(afterAttactTime > 10 && !effectShowing) {
				attacked = false;
				afterAttactTime = 0;
			}
		}

		if(afterChange) {//改变方向时间间隔控制
			keySpeedControl ++;
			if(keySpeedControl >= 5) {
				afterChange = false;
				keySpeedControl = 0;
			}
		} else if(nextDirection != 0) {
			changeDirection(nextDirection);
			nextDirection = 0;
		} 
		
	}
	

	/**
	 * 控制英雄移动的方法
	 */
	public void move() {
		if(type == 0) {
			switch(direction) {
			case 1:
				if(x + game.mapX >= Game.CENTER_X && game.mapX > -320) {
					game.mapX -= MOVE_OFFER;
					x += MOVE_OFFER;
				} else {
					x += MOVE_OFFER;
				}
				break;
			case 2:
				if(game.mapY + y - 759 <= Game.CENTER_Y && game.mapY < 795) {
					game.mapY += MOVE_OFFER;
					y -= MOVE_OFFER;
				} else {
					y -= MOVE_OFFER;
				}
				break;
			case 3:
				if(x + game.mapX <= Game.CENTER_X && game.mapX < 0) {
					game.mapX += MOVE_OFFER;
					x -= MOVE_OFFER;
				} else {
					x -= MOVE_OFFER;
				}
				break;
			case 4:
				if(game.mapY + y - 759 >= Game.CENTER_Y && game.mapY > 530) {
					game.mapY -= MOVE_OFFER;
					y+= MOVE_OFFER;
				} else {
					y+= MOVE_OFFER;
				}
				break;
			}
		} else {
			switch(direction) {
			case 1:
				x += MOVE_OFFER;
				break;
			case 2:
				y -= MOVE_OFFER;
				break;
			case 3:
				x -= MOVE_OFFER;
				break;
			case 4:
				y+= MOVE_OFFER;
				break;
			}
		}
		moveIndex ++;
		
		if(moveIndex >= 3) {
			moveIndex = 0;
		}
	}
	
	/**
	 * 子弹弓箭法术的移动
	 */
	public void attackEffectMove() {
		effectShowControl ++;
		ironBallIndex ++;
		if(ironBallIndex > 3) ironBallIndex = 0;
		
		if(effectShowControl > 12) {
			effectShowing = false;
			effectShowControl = 0;
		}
		if(bulletHitDevil(hittingDevil)) {
			hittingDevil.beHitted(attackPower, this);
			effectShowing = false;
			effectShowControl = 0;
		}
		effectX += attackingDevilXOffer;
		effectY += attackingDevilYOffer;
	}
	
	/**
	 * 炮弹飞过去了。是否打到敌人呢？
	 * @param devil 攻击对象
	 */
	public boolean bulletHitDevil(Devil devil) {
		if(Math.abs(effectX - devil.x) < 50 && Math.abs(effectY - devil.y) < 50) {
			return true;
		} else return false;
	}
	
	/**
	 * 撞到边上栅栏了。花钱买活吧。
	 */
	public void hitFences() {
		if(x <= 25 || x >= 935 || y <= 20 || y >= 685) {
			alive = false;
		}
	}
	
	/**
	 * 撞到自己的士兵了。也挂了。
	 */
	public void hitSolider() {
		int size = game.soldiers.size();
		for(int i=0; i<size; i++) {
			Hero soldier = (Hero)game.soldiers.elementAt(i);
			if(Math.abs(x - soldier.x) < 30 && Math.abs(y - soldier.y) < 30) {
				alive = false;
				return;
			}
		}
	}
	
	/**
	 * 吃金币
	 */
	public void eatCoin() {
//		int size = game.coins.size();
		for(int i=0; i<game.coins.size(); i++) {
			Coin coin = (Coin)game.coins.elementAt(i);
			if(Math.abs(x - coin.x) < 30 && Math.abs(y - coin.y) < 30) {
				game.coinCount += coin.value;
				game.coins.removeElement(coin);
			}
		}
	}
	
	/**
	 * 自动攻击
	 * 攻击范围判断方法
	 */
	private void autoAttack() {
		switch(type) {
		case 0://英雄自动攻击
			switch(direction) {
			case 1:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x > x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 600 &&
							C.arctan(Math.abs(devil.y - y) * 1000000 / (devil.x - x)) < 30){
						fire(devil);
						return;
					}
				}
				break;
			case 2:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y < y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 600 &&
							C.arctan(Math.abs(devil.x - x) * 1000000 / y - devil.y) < 30){
						fire(devil);
						return;
					}
				}
				break;
			case 3:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x < x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 600 &&
							C.arctan(Math.abs(devil.y - y) * 1000000 / x - devil.x) < 30){
						fire(devil);
						return;
					}
				}
				break;
			case 4:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y > y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 600 &&
							C.arctan(Math.abs(devil.x - x) * 1000000 / devil.y - y) < 30){
						fire(devil);
						return;
					}
				}
				break;
			}
			break;
		case 1://士兵1自动攻击
			switch(direction) {
			case 1:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x > x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 80){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			case 2:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y < y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 80){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			case 3:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x < x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 80){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			case 4:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y > y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 80){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			}
			break;
		case 2:
			switch(direction) {
			case 1:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x > x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.y - y) * 1000000 / (devil.x - x)) < 10){
						fire(devil);
						return;
					}
				}
				break;
			case 2:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y < y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.x - x) * 1000000 / y - devil.y) < 10){
						fire(devil);
						return;
					}
				}
				break;
			case 3:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x < x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.y - y) * 1000000 / x - devil.x) < 10){
						fire(devil);
						return;
					}
				}
				break;
			case 4:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y > y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.x - x) * 1000000 / devil.y - y) < 10){
						fire(devil);
						return;
					}
				}
				break;
			}
			break;
		case 3:
			switch(direction) {
			case 1:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x > x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.y - y) * 1000000 / (devil.x - x)) < 15){
						fire(devil);
						return;
					}
				}
				break;
			case 2:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y < y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.x - x) * 1000000 / y - devil.y) < 15){
						fire(devil);
						return;
					}
				}
				break;
			case 3:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x < x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.y - y) * 1000000 / x - devil.x) < 15){
						fire(devil);
						return;
					}
				}
				break;
			case 4:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y > y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 1000 &&
							C.arctan(Math.abs(devil.x - x) * 1000000 / devil.y - y) < 15){
						fire(devil);
						return;
					}
				}
				break;
			}
			break;
		case 4:
			switch(direction) {
			case 1:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x > x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 100){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			case 2:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y < y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 100){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			case 3:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.x < x &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 100){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			case 4:
				for(int i=0; i<game.devils.size(); i++) {
					Devil devil = (Devil)game.devils.elementAt(i);
					if(devil.y > y &&
							(int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y)) < 100){
						fire(devil);
						devil.beHitted(attackPower, this);
						return;
					}
				}
				break;
			}
			break;
			
			
		}
	}
	
	/**
	 * 攻击
	 */
	public void fire(Devil devil) {
		attacked = true;
		showAttactImage = true;
		effectShowing = true;
		hittingDevil = devil;
		attackDir = direction;
		
		if(type == 0 && type == 2 && type == 3) {
			switch(direction) {
			case 1:
				effectX = x + 50;
				effectY = y;
				break;
			case 2:
				effectX = x;
				effectY = y - 50;
				break;
			case 3:
				effectX = x - 50;
				effectY = y;
				break;
			case 4:
				effectX = x;
				effectY = y + 50;
				break;
			}
			
			int l = (int)Math.sqrt((devil.x - x) * (devil.x - x) + (devil.y - y) * (devil.y - y));
			attackingDevilXOffer = (devil.x - x) * bulletOffer / l;
			attackingDevilYOffer = (devil.y - y) * bulletOffer / l;
		}
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_RIGHT:
			if(!afterChange) {
				changeDirection(1);
			} else {
				nextDirection = 1;
			}
			break;
		case C.KEY_UP:
			if(!afterChange) {
				changeDirection(2);
			} else {
				nextDirection = 2;
			}
			break;
		case C.KEY_LEFT:
			if(!afterChange) {
				changeDirection(3);
			} else {
				nextDirection = 3;
			}
			break;
		case C.KEY_DOWN:
			if(!afterChange) {
				changeDirection(4);
			} else {
				nextDirection = 4;
			}
			break;
		}
	}
	
	/**
	 * 跟随上一个士兵的方法
	 */
	public void follow() {
		if(!previousHero.afterChange) {
			changeDirection(previousHero.direction);
		}
	}
	
	/**
	 * 复活重置
	 */
	public void reset() {
		life = lifeMax;
	}
	
	public Hero next() {
		return nextHero;
	}
	
	public void setNextHero(Hero nextHero) {
		this.nextHero = nextHero;
	}
	
	public Hero previous() {
		return previousHero;
	}
	
	public void setPreviousHero(Hero previousHero) {
		this.previousHero = previousHero;
	}

	
	public void beHitted(Devil devil) {
		life -= devil.attackPower;
		if(life <= 0 && type != 0) {
			willDie = true;
		}
	}

}
