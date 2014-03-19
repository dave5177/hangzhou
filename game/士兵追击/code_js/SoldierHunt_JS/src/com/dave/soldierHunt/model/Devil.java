package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Devil {
	/**
	 * 怪物的类型
	 * 1~8代表怪物1~怪物8
	 */
	public int type;
	
	/**
	 * 怪所在的游戏
	 */
	private Game game;
	
	/**
	 * 移动速度，也就是每一帧的偏移量
	 */
	private static final int MOVE_OFFER = 3;
	
	/**
	 * x坐标值（相对于地图）
	 */
	public int x;
	
	/**
	 * y坐标值（相对于地图）
	 */
	public int y;
	
	/**
	 * 前一步的x，y值。
	 */
	private int oldX, oldY;
	
	private Image[] ima_move;
	
	/**
	 * 怪物的运动方向
	 * @1：向右；
	 * @2：向上；
	 * @3：向左；
	 * @4：向下。
	 */
	public int direction;
	
	/**
	 * 最大生命值
	 */
	public int lifeMax;
	
	/**
	 * 攻击力
	 */
	public int attackPower;
	
	/**
	 * 当前血量
	 */
	public int life;
	
	/**
	 * 怪物的攻击方式s
	 * @0：近战。
	 * @1：远程。
	 */
	public int attackMode;

	/**
	 * 记录怪物行走的图片显示的下标值
	 */
	private int moveImageIndex;

	/**
	 * 自动改变方向的时间间隔
	 */
	private int changeDirectionInterval;

	/**
	 * 自动改变方向后的时间
	 */
	private int changeDirectionTime;

	/**
	 * 生命条长度
	 */
	public int healthLength;

	/**
	 * 被击中后的状态
	 */
	private boolean beHittedByHero;
	
	/**
	 * 眩晕图片下标值
	 */
	private int giddyImageIndex;

	/**
	 * 被攻击效果显示时间控制
	 */
	private int giddyControl;

	/**
	 * 血量为0要死的状态
	 */
	public boolean willDie;
	
	/**
	 * 控制多久后死亡
	 */
	private int willDieControl;
	
	/**
	 * 死亡消失图片下标值
	 */
	private int dieImageIndex;

	/**
	 * 眩晕状态
	 */
	private boolean giddy;

	private boolean beHittedBySoldier3;

	/**
	 * 被火球打中身上着火图片下标值
	 */
	private int fireImageIndex;

	private int fireControl;

	/**
	 * 远程攻击的攻击效果图片x坐标
	 */
	private int effectX;

	/**
	 * 远程攻击的攻击效果y坐标
	 */
	private int effectY;

	/**
	 * 攻击效果x偏移量
	 */
	private int attackingDevilXOffer;

	/**
	 * 攻击效果y偏移量
	 */
	private int attackingDevilYOffer;

	/**
	 * 攻击完状态
	 */
	private boolean attacked;

	/**
	 * 显示攻击图片
	 */
	private boolean showAttactImage;

	/**
	 * 显示攻击效果
	 */
	private boolean effectShowing;

	/**
	 * 正在攻击的士兵
	 */
	private Hero hittingSoldier;

	/**
	 * 攻击方向
	 */
	private int attackDir;

	/**
	 * 攻击后的时间控制
	 */
	private int afterAttactTime;

	/**
	 * 攻击图片下标值
	 */
	private int attackImageIndex;

	/**
	 * 攻击效果显示控制
	 */
	private int effectShowControl;

	/**
	 * 远程攻击效果图下标值
	 */
	private int effectImageIndex;

	private boolean beHittedBySoldier2;

	private int arrowControl;

	private int arrowHitImageIndex;

	private boolean beHittedBySoldierClose;

	private int beHitIndex;
	
	/**
	 * 远程怪的攻击子弹速度
	 */
	private static final int bulletOffer = 40;

	
	/**
	 * 怪物构造方法
	 * @param game 怪物所在的游戏
	 * @param type 怪物类型
	 * @param direction 初始方向
	 * @param x 初始x坐标
	 * @param y 初始y坐标
	 */
	public Devil(Game game, int type, int direction, int x, int y) {
		this.game = game;
		this.type = type;
		this.direction = direction;
		this.x = x;
		this.y = y;
		
		init();
	}
	
	/**
	 * 根据类型和初始方向初始化怪物
	 * @param type
	 * @param direction
	 */
	private void init() {
		this.lifeMax = C.devilParameter[type - 1][0] + (CanvasControl.level - 1) * 50;
		this.attackPower = C.devilParameter[type - 1][1] + (CanvasControl.level - 1) * 30;
		this.life = lifeMax;
		healthLength = 34 - 1200 / lifeMax;
		
		if(type <= 4) attackMode = 0;
		else attackMode = 1;
		ima_move = new Image[4];
		ima_move = ImageManager.ima_devil_move[type - 1][direction - 1];
	}
	
	public void show(Graphics g) {
		if(willDie) {//显示死亡后消失的效果
			g.drawImage(ImageManager.ima_die[dieImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			if(beHittedByHero) {
				g.drawImage(ImageManager.img_bang, x + game.mapX, game.mapY + y - 759 + 20, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			if(showAttactImage) {
				g.drawImage(ImageManager.ima_devil_attack[type - 1][attackDir - 1][attackImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(ima_move[moveImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			}
			
//			g.setColor(50, 50, 50);
//			g.fillRoundRect(x + game.mapX - healthLength / 2, game.mapY + y - 759 - 40, healthLength, 6, 1, 1);
//			g.setColor(150, 50, 50);
//			g.fillRoundRect(x + game.mapX - healthLength / 2 + 1, game.mapY + y - 759 - 40 + 1, healthLength * life / lifeMax - 2, 4, 1, 1);
//			
			if(beHittedByHero) {//显示被攻击的效果
				g.drawImage(ImageManager.ima_giddy[giddyImageIndex], x + game.mapX, game.mapY + y - 759 - 30, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			if(beHittedBySoldier2) {
				g.drawImage(ImageManager.ima_arrowHit[arrowHitImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			if(beHittedBySoldier3) {
				g.drawImage(ImageManager.ima_burning[fireImageIndex], x + game.mapX, game.mapY + y - 759 + 20, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			if(beHittedBySoldierClose) {
				g.drawImage(ImageManager.ima_hit[beHitIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			}
			
		}
		
		if(effectShowing) {
			showAttackEffect(g);
		}
		
	}

	/**
	 * @param g
	 */
	private void showAttackEffect(Graphics g) {
		g.drawImage(ImageManager.ima_devil_attackEffect[type - 5][effectImageIndex], effectX + game.mapX, game.mapY + effectY - 759, Graphics.VCENTER | Graphics.HCENTER);
	}
	

	/**
	 * 改变方向方法
	 * @param gotoDir 设置方向参数
	 */
	public void changeDirection(int gotoDir) {
		if(gotoDir != direction && Math.abs(gotoDir - direction) != 2){
			direction = gotoDir;
			changeDirectionInterval = C.R.nextInt(50);//每次改变方向之后随机得到一个0~50的到下次改变方向的时间间隔数。
			changeDirectionTime = 0;

			ima_move = ImageManager.ima_devil_move[type - 1][gotoDir - 1];
			
			System.gc();
		}
	}
	
	/**
	 * 主逻辑方法
	 */
	public void logic() {
		if(!willDie && !giddy && !game.showBomb) {
			move();
			if(!attacked &&
					x + game.mapX < 640 &&
					x + game.mapX > 0 &&
					game.mapY + y - 759 > 0 &&
					game.mapY + y - 759 < 530) autoAttack();
			
			autoChangeDirection();
		}
		
		
		if(beHittedByHero) {
			giddyControl ++;
			
			giddyImageIndex ++;
			if(giddyImageIndex > 3) giddyImageIndex = 0;
			
			if(giddyControl > 20) {
				giddy = false;//不晕了。
				beHittedByHero = false;
				giddyControl = 0;
			}
		}
		
		if(beHittedBySoldier2) {
			arrowControl ++;
			
			if(arrowControl == 2) arrowHitImageIndex ++;
			
			if(arrowControl > 4) {
				beHittedBySoldier2 = false;
				arrowControl = 0;
				arrowHitImageIndex = 0;
			}
		}
		
		if(beHittedBySoldier3) {
			fireControl ++;
			
			fireImageIndex ++;
			if(fireImageIndex > 2) fireImageIndex = 0;
			
			if(fireControl > 10) {
				beHittedBySoldier3 = false;
				fireControl = 0;
			}
		}
		
		if(beHittedBySoldierClose) {
			beHitIndex ++;
			if(beHitIndex > 2) {
				beHitIndex = 0;
				beHittedBySoldierClose = false;
			}
		}
		
		if(willDie) {
			willDieControl ++;
			dieImageIndex ++;
			if(dieImageIndex > 3) {
				dieImageIndex = 0;
				willDie = false;
				afterDie();
			}
		}
		
		if(attacked) {//攻击间隔、图片显示的控制
			if(effectShowing) {
				attackEffectMove();
			}
			
			afterAttactTime ++;
			if(afterAttactTime == 1) {
				attackImageIndex ++;
			} else if(afterAttactTime == 3) {
				showAttactImage = false;
				attackImageIndex = 0;
			} else if(afterAttactTime > 20 && !effectShowing) {
				attacked = false;
				afterAttactTime = 0;
			}
		}
	}

	/**
	 * 攻击效果的移动和碰撞检测。
	 */
	private void attackEffectMove() {
		effectShowControl ++;
		
		effectImageIndex ++;
		if(effectImageIndex > 3) effectImageIndex = 0;
		
		if(effectShowControl > 15) {
			effectShowing = false;
			effectShowControl = 0;
		}
		if(bulletHitSoldier()) {
			hittingSoldier.beHitted(this);
			effectShowing = false;
			effectShowControl = 0;
		}
		effectX += attackingDevilXOffer;
		effectY += attackingDevilYOffer;
	}

	/**
	 * 攻击英雄
	 * @param hittingSoldier
	 * @return
	 */
	private boolean bulletHitSoldier() {
		if(Math.abs(effectX - game.hero.x) < 30 && Math.abs(effectY - game.hero.y) < 30) {
			return true;
		} 
		
		int size = game.soldiers.size();
		for(int i=0; i<size; i++) {
			Hero hittingSoldier = (Hero)game.soldiers.elementAt(i);
			if(Math.abs(effectX - hittingSoldier.x) < 30 && Math.abs(effectY - hittingSoldier.y) < 30) {
				return true;
			} 
		}
		return false;
	}

	private void move() {
		oldX = x;
		oldY = y;
		
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
			y += MOVE_OFFER;
			break;
		}
		
		moveImageIndex ++;
		
		if(moveImageIndex > 3) {
			moveImageIndex = 0;
		}
	}

	/**
	 * 自动改变方向
	 */
	private void autoChangeDirection() {
		changeDirectionTime ++;
		if(changeDirectionTime >= changeDirectionInterval) {
			randomChangeDirection();
		}
		
		int size = game.obstacles.size();
		for(int i=0; i<size; i++) {
			Obstacle obstacle = (Obstacle)game.obstacles.elementAt(i);
			if(Math.abs(x - obstacle.x) <= 50 && Math.abs(y + 25 - (obstacle.y + 15)) <= 30) {
				x = oldX;
				y = oldY;
				randomChangeDirection();
				return;
			}
		}
		
		if(x <= 80 && direction == 3){
			x = oldX;
			y = oldY;
			randomChangeDirection();
		} else if(x >= 872 && direction == 1) {
			x = oldX;
			y = oldY;
			randomChangeDirection();
		} else if(y <= 56 && direction == 2) {
			x = oldX;
			y = oldY;
			randomChangeDirection();
		} else if(y >= 625 && direction == 4) {
			x = oldX;
			y = oldY;
			randomChangeDirection();
		}
	}
	
	/**
	 * 在当前方向下随机改变一次方向。
	 */
	private void randomChangeDirection() {
		int temp = C.R.nextInt(2);
		if(temp == 0) {
			int gotoDirection = direction + 1;
			if(gotoDirection > 4) gotoDirection = 1;
			changeDirection(gotoDirection);
		} else {
			int gotoDirection = direction - 1;
			if(gotoDirection < 1) gotoDirection = 4;
			changeDirection(gotoDirection);
		}
	}
	
	/**
	 * 自动攻击
	 * 攻击范围判断方法
	 */
	private void autoAttack() {
		int size = game.soldiers.size();
		switch(type) {
		case 1:
		case 2:
		case 3:
		case 4://前4个近战
			switch(direction) {
			case 1:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.x > x &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 80){
						fire(soldier);
						soldier.beHitted(this);
						return;
					}
				}
				if(game.hero.x > x &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 80){
					fire(game.hero);
					game.hero.beHitted(this);
					return;
				}
				break;
			case 2:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.y < y &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 80){
						fire(soldier);
						soldier.beHitted(this);
						return;
					}
				}
				if(game.hero.y < y &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 80){
					fire(game.hero);
					game.hero.beHitted(this);
					return;
				}
				break;
			case 3:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.x < x &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 80){
						fire(soldier);
						soldier.beHitted(this);
						return;
					}
				}
				if(game.hero.x < x &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 80){
					fire(game.hero);
					game.hero.beHitted(this);
					return;
				}
				break;
			case 4:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.y > y &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 80){
						fire(soldier);
						soldier.beHitted(this);
						return;
					}
				}
				if(game.hero.y > y &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 80){
					fire(game.hero);
					game.hero.beHitted(this);
					return;
				}
				break;
			}
			break;
		case 5:
		case 6:
		case 7:
		case 8://后4个远程
			switch(direction) {
			case 1:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.x > x &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 600 &&
							C.arctan(Math.abs(soldier.y - y) * 1000000 / (soldier.x - x)) < 10){
						fire(soldier);
						return;
					}
				}
				if(game.hero.x > x &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 600 &&
						C.arctan(Math.abs(game.hero.y - y) * 1000000 / (game.hero.x - x)) < 10){
					fire(game.hero);
					return;
				}
				break;
			case 2:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.y < y &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 600 &&
							C.arctan(Math.abs(soldier.x - x) * 1000000 / y - soldier.y) < 10){
						fire(soldier);
						return;
					}
				}
				if(game.hero.y < y &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 600 &&
						C.arctan(Math.abs(game.hero.x - x) * 1000000 / y - game.hero.y) < 10){
					fire(game.hero);
					return;
				}
				break;
			case 3:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.x < x &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 600 &&
							C.arctan(Math.abs(soldier.y - y) * 1000000 / x - soldier.x) < 10){
						fire(soldier);
						return;
					}
				}
				if(game.hero.x < x &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 600 &&
						C.arctan(Math.abs(game.hero.y - y) * 1000000 / x - game.hero.x) < 10){
					fire(game.hero);
					return;
				}
				break;
			case 4:
				for(int i=0; i<size; i++) {
					Hero soldier = (Hero)game.soldiers.elementAt(i);
					if(soldier.y > y &&
							(int)Math.sqrt((soldier.x - x) * (soldier.x - x) + (soldier.y - y) * (soldier.y - y)) < 600 &&
							C.arctan(Math.abs(soldier.x - x) * 1000000 / soldier.y - y) < 10){
						fire(soldier);
						return;
					}
				}
				if(game.hero.y > y &&
						(int)Math.sqrt((game.hero.x - x) * (game.hero.x - x) + (game.hero.y - y) * (game.hero.y - y)) < 600 &&
						C.arctan(Math.abs(game.hero.x - x) * 1000000 / game.hero.y - y) < 10){
					fire(game.hero);
					return;
				}
				break;
			}
			break;
		}
	}
	
	/**
	 * 攻击
	 */
	public void fire(Hero hero) {
		attacked = true;
		showAttactImage = true;
		hittingSoldier = hero;
		attackDir = direction;
		
		if(type > 4) {
			effectShowing = true;
			
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
			
			int l = (int)Math.sqrt((hero.x - x) * (hero.x - x) + (hero.y - y) * (hero.y - y));
			attackingDevilXOffer = (hero.x - x) * bulletOffer / l;
			attackingDevilYOffer = (hero.y - y) * bulletOffer / l;
		}
	}
	
	/**
	 * 被打了，草。
	 * @param heroAttackPower 打我的攻击力
	 * @param hero 打我的人
	 */
	public void beHitted(int heroAttackPower, Hero hero) {
		switch(hero.type) {
		case 0:
			beHittedByHero = true;
			giddy = true;
			break;
		case 2:
			beHittedBySoldier2 = true;
			break;
		case 3:
			beHittedBySoldier3 = true;
			break;
		default:
			beHittedBySoldierClose = true;
			break;
		}
		
		this.life -= heroAttackPower;
		if(life <= 0) {
			game.canvasControl.playerHandler.toPlay(3);
			
			willDie = true;
		}
	}

	/**
	 * 遗产
	 */
	public void afterDie() {
		switch(type) {
		case 1:
			for(int i=0; i<5; i++) {
				int[] point = game.getRandomCoinPoint();
				game.coins.addElement(new Coin(1, point[0], point[1], game));
			}
			break;
		case 2:
			if(game.soldiers.size() < 10)
				game.aloneSoldiers.addElement(new AloneSoldier(1, x, y, game));
			break;
		case 3:
			for(int i=0; i<10; i++) {
				int[] point = game.getRandomCoinPoint();
				game.coins.addElement(new Coin(1, point[0], point[1], game));
			}
			break;
		case 4:
			if(game.soldiers.size() < 10)
				game.aloneSoldiers.addElement(new AloneSoldier(2, x, y, game));
			break;
		case 5:
			for(int i=0; i<15; i++) {
				int[] point = game.getRandomCoinPoint();
				game.coins.addElement(new Coin(1, point[0], point[1], game));
			}
			break;
		case 6:
			if(game.soldiers.size() < 10)
				game.aloneSoldiers.addElement(new AloneSoldier(3, x, y, game));
			break;
		case 7:
			if(game.soldiers.size() < 10)
				game.aloneSoldiers.addElement(new AloneSoldier(5, x, y, game));
			break;
		case 8:
			if(game.soldiers.size() < 10)
				game.aloneSoldiers.addElement(new AloneSoldier(4, x, y, game));
			break;
		}
		game.devils.removeElement(this);
	}

}
