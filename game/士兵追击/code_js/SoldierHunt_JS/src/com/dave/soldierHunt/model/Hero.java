package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.soldierHunt.main.CanvasControl;
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
	private static final int MOVE_OFFER = 5;
	
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
	public boolean willDie;

	/**
	 * 死亡后消失动画的控制
	 */
	private int willDieControl;

	/**
	 * 消失动画下标值
	 */
	private int dieImageIndex;

	/**
	 * 血条长度
	 */
	public int healthLength;

	/**
	 * 打我的怪的类型值
	 */
	private int hitMeType;

	/**
	 * 显示被打效果
	 */
	private boolean showBeHit;

	/**
	 * 控制被打效果显示时间
	 */
	private int showBeHitControl;

	/**
	 * 被打效果图下标值
	 */
	private int beHitImageIndex;

	/**
	 * 被打效果图
	 */
	private Image[] ima_beHit;

	/**
	 * 刚刚加入
	 */
	public boolean justAdd;

	/**
	 * 加入效果图片下标值
	 */
	private int justAddIndex;

	/**
	 * 撞到士兵了。
	 */
	public boolean hittedSoldier;
	
	/**
	 * 前一步的X坐标
	 */
	private int lastX;
	
	/**
	 * 前一步的Y坐标
	 */
	private int lastY;

	/**
	 * 用后面的士兵替代前面的士兵时保存的原来的X值
	 */
	private int oldX;
	/**
	 * 用后面的士兵替代前面的士兵时保存的原来的Y值
	 */
	private int oldY;

	/**
	 * 原来的方向
	 */
	private int oldDirection;

	/**
	 * 原来是否处于刚改变方向状态
	 */
	private boolean oldAfterChange;

	/**
	 * 原来的改变方向间隔控制的值
	 */
	private int oldKeySpeedControl;

	/**
	 * 撞到障碍物上了。
	 */
	public boolean hittedObstacle;

	/**
	 * 加血效果显示时间控制
	 */
	private int bloodAddTimeControl;

	/**
	 * 加血的量
	 */
	private int bloodAddValue;

	/**
	 * 正在加血，显示加血的数字
	 */
	private boolean bloodAdding;

	private int joinTime;

	/**
	 * 被栅栏撞死了。
	 */
	public boolean hittedFences;

	public Hero(Game game, int type) {
		this.game = game;
		this.type = type;
		
		init();
	}
	
	private void init() {
		if(type == 0) {
			this.lifeMax = 600 + level * 100;
			this.attackPower = 80 + level * 50;
		} else {
			this.lifeMax = C.soldierParameter[type - 1][0] + (CanvasControl.level - 1) * 50;
			this.attackPower = C.soldierParameter[type - 1][1] + (CanvasControl.level - 1) * 30;
		}
		
		this.life = this.lifeMax;
		
		healthLength = 34 - 1200 / lifeMax;
	}

	public void show(Graphics g) {
		if(willDie) {//显示死亡后消失的效果
			g.drawImage(ImageManager.ima_die[dieImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(ImageManager.img_shadow, x + game.mapX, game.mapY + y - 759 + 25, Graphics.VCENTER | Graphics.HCENTER);
			
			if(showAttactImage) {
				g.drawImage(ImageManager.ima_hero_attack[type][attackDir - 1][attactImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(ImageManager.ima_hero_move[type][direction - 1][moveIndex >> 1], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			}
			if(showBeHit) {
				showBeHit(g);
			}
//			
//			g.setColor(200, 200, 200);
//			g.drawRoundRect(x + game.mapX - healthLength / 2, game.mapY + y - 759 - 40, healthLength, 6, 1, 1);
//			g.setColor(0, 200, 0);
//			g.fillRoundRect(x + game.mapX - healthLength / 2 + 1, game.mapY + y - 759 - 40 + 1, healthLength * life / lifeMax - 1, 5, 1, 1);
//			g.setColor(0, 0, 0);
//			g.drawString("x:" + x + "----y:" + y,x + game.mapX - healthLength / 2 + 1, game.mapY + y - 759 - 40 + 1,0);
			
			if(justAdd) {
				showJustAdd(g);
			}
			
			
			if(bloodAdding) {
				int length = String.valueOf(bloodAddValue).length();
				C.drawString(g, game.img_number_blood, "+" + bloodAddValue, 
						"0123456789+", x + game.mapX - (length + 1) * 27 / 2, game.mapY + y - 759 - 50 - 10 * bloodAddTimeControl,
						27, 27, 0, 0, 0);
			}
			
		}
		
		if(effectShowing) {
			showAttackEffect(g);
		}
		
		if(game.heroProtected) {
			showProtected(g);
		}
	}

	/**
	 * 无敌效果
	 * @param g
	 */
	private void showProtected(Graphics g) {
		g.drawImage(ImageManager.ima_protected[game.protectedImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
	}

	/**
	 * 归队效果
	 * @param g
	 */
	private void showJustAdd(Graphics g) {
		g.drawImage(ImageManager.ima_join[justAddIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
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
	 * 被打效果
	 * @param g
	 */
	public void showBeHit(Graphics g) {
		if(hitMeType < 3 || hitMeType > 4) {
			g.drawImage(ima_beHit[beHitImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
		}
	}
	
	/**
	 * 改变方向方法
	 * @param gotoDir 目标方向
	 */
	public void changeDirection(int gotoDir, int keySpeedStart) {
		if(gotoDir != direction && gotoDir - direction != 2 && gotoDir - direction != -2 && alive){
			direction = gotoDir;
			afterChange = true;
			keySpeedControl = keySpeedStart;

			System.gc();
		}
	}
	
	/**
	 * 主逻辑方法
	 */
	public void logic() {
		move();
		
		if(!game.heroProtected) hitDevil();
		
		if(!attacked) autoAttack();
		
		timeControl();
	}

	/**
	 * 时间控制方法
	 */
	private void timeControl() {
		if(attacked) {//攻击间隔、图片显示的控制
			if(effectShowing) {
				attackEffectMove();
			}
			
			afterAttactTime ++;
			if(afterAttactTime == 2) {
				attactImageIndex ++;
			} else if(afterAttactTime == 3) {
				showAttactImage = false;
				attactImageIndex = 0;
			} else if(afterAttactTime > 20 && !effectShowing) {
				attacked = false;
				afterAttactTime = 0;
			}
		}

		if(afterChange) {//改变方向时间间隔控制
			keySpeedControl ++;
			if(keySpeedControl > 10) {
				afterChange = false;
				keySpeedControl = 0;
				if(nextHero != null) {
					nextHero.changeDirection(direction, 0);
				}
				if(nextDirection != 0) {
					changeDirection(nextDirection, 0);
					nextDirection = 0;
				} 
			}
		}
		
		if(willDie) {//死亡消失动画控制
			willDieControl ++;
			dieImageIndex ++;
			if(dieImageIndex > 3) {
				dieImageIndex = 0;
				willDie = false;
				removeMe();
			}
		}
		
		if(showBeHit) {//被攻击效果控制
			showBeHitControl ++;
			
			if(hitMeType < 3) {
				if(showBeHitControl == 2) {
					beHitImageIndex ++;
				} else if(showBeHitControl == 4) {
					beHitImageIndex = 0;
					showBeHit = false;
					showBeHitControl = 0;
				}
			} else if(hitMeType > 4) {
				beHitImageIndex ++;
				if(beHitImageIndex > 3) {
					beHitImageIndex = 0;
					showBeHit = false;
					showBeHitControl = 0;
				}
			}
		}
		
		if(justAdd) {//归队效果显示控制
			justAddIndex ++;
			if(justAddIndex > 3) {
				justAddIndex = 0;
				justAdd = false;
			}
		}
		
		if(bloodAdding) {//加血效果显示控制
			bloodAddTimeControl ++;
			if(bloodAddTimeControl > 4) {
				bloodAdding = false;
			}
		}
		
	}
	
	/**
	 * 把我移除了。
	 */
	public void removeMe() {
		Hero lastHero = (Hero)game.soldiers.elementAt(game.soldiers.size() - 1);
		synchronized(game.soldiers){
			if(lastHero != this) {
				oldX = x;
				oldY = y;
				oldDirection = direction;
				oldKeySpeedControl = keySpeedControl;
				oldAfterChange = afterChange;
				replace(lastHero, this, nextHero);
				
				previousHero.nextHero = nextHero;
				nextHero.previousHero = previousHero;
			}
			game.soldiers.removeElement(this);
		}
	}
	
	/**
	 * 每一个后面的替换替换前面的士兵
	 * @param nowHero 最开始替换的士兵
	 * @param endHero 替换到这个士兵为止
	 */
	public void replace (Hero lastHero, Hero nowHero, Hero nextHero) {
		nextHero.oldX = nextHero.x;
		nextHero.oldY = nextHero.y;
		nextHero.oldDirection = nextHero.direction;
		nextHero.oldKeySpeedControl = nextHero.keySpeedControl;
		nextHero.oldAfterChange = nextHero.afterChange;
		nextHero.x = nowHero.oldX;
		nextHero.y = nowHero.oldY;
		nextHero.direction = nowHero.oldDirection;
		nextHero.keySpeedControl = nowHero.oldKeySpeedControl;
		nextHero.afterChange = nowHero.oldAfterChange;
//		nextHero.changeDirection(nowHero.oldDirection, 10);
//		nextHero.keySpeedControl = 10;
		if(nextHero != lastHero)
			replace(lastHero, nextHero, nextHero.nextHero);
	}

	/**
	 * 控制英雄移动的方法
	 */
	public void move() {
		lastX = x;
		lastY = y;
		
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
		
		if(moveIndex > 7) {
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
		if(x <= 80 || x >= 872 || y <= 56 || y >= 625) {
			x = lastX;
			y = lastY;
			Hero soldier;
			for(int j=0; j<game.soldiers.size(); j++) {
				soldier = (Hero)game.soldiers.elementAt(j);
				soldier.x = soldier.lastX;
				soldier.y = soldier.lastY;
			}
			
			if(game.heroProtected) {
				int nextDierction = direction + 1 > 4 ? 1 : direction + 1;
				changeDirection(nextDierction, 0);
			} else {
				alive = false;
				hittedFences = true;
			}
		}
	}
	
	/**
	 * 撞到自己的士兵了。也挂了。
	 */
	public void hitSolider() {
		int size = game.soldiers.size();
		for(int i=0; i<size; i++) {
			Hero soldier = (Hero)game.soldiers.elementAt(i);
			if(Math.abs(x - soldier.x) < 20 && Math.abs(y - soldier.y) < 20) {
//				x = lastX;
//				y = lastY;
//				Hero hitSoldier;
//				for(int j=0; j<game.soldiers.size(); j++) {
//					hitSoldier = (Hero)game.soldiers.elementAt(j);
//					hitSoldier.x = hitSoldier.lastX;
//					hitSoldier.y = hitSoldier.lastY;
//				}
//				
				hittedSoldier = true;
				alive = false;
				return;
			}
		}
	}
	
	/**
	 * 撞到怪了。挂。
	 */
	public void hitDevil() {
		int size = game.devils.size();
		for(int i=0; i<size; i++) {
			Devil devil = (Devil)game.devils.elementAt(i);
			if(Math.abs(x - devil.x) < 30 && Math.abs(y - devil.y) < 30) {
				if(devil.willDie == true) continue;
				
				if(type == 0) {
					alive = false;
				} else {
					willDie = true;
				}
				
				devil.willDie = true;
				return;
			}
		}
	}
	
	/**
	 * 撞到障碍物
	 */
	public void hitObstacle() {
		int size = game.obstacles.size();
		for(int i=0; i<size; i++) {
			Obstacle obstacle = (Obstacle)game.obstacles.elementAt(i);
			if(Math.abs(x - obstacle.x) <= 25 && Math.abs(y + 25 - (obstacle.y + 15)) <= 30) {
				x = lastX;
				y = lastY;
				Hero soldier;
				for(int j=0; j<game.soldiers.size(); j++) {
					soldier = (Hero)game.soldiers.elementAt(j);
					soldier.x = soldier.lastX;
					soldier.y = soldier.lastY;
				}
				
				if(game.heroProtected) {
					int nextDierction = direction + 1 > 4 ? 1 : direction + 1;
					changeDirection(nextDierction, 0);
				} else {
					hittedObstacle = true;
					alive = false;
				}
				return;
			}
		}
	}
	
	/**
	 * 吃金币
	 */
	public void eatCoin() {
		for(int i=0; i<game.coins.size(); i++) {
			Coin coin = (Coin)game.coins.elementAt(i);
			if(coin.beAte) continue;
			if(Math.abs(x - coin.x) < 30 && Math.abs(y - coin.y) < 30) {
				game.canvasControl.playerHandler.toPlay(4);
				
				Game.coinCount += coin.value;
//				game.coins.removeElement(coin);
				coin.beAte = true;
			}
		}
	}
	
	/**
	 * 救落单的士兵
	 */
	public void eatAloneSoldier() {
		for(int i=0; i<game.aloneSoldiers.size(); i++) {
			AloneSoldier aloneSoldier = (AloneSoldier)game.aloneSoldiers.elementAt(i);
			if(Math.abs(x - aloneSoldier.x) < 50 && Math.abs(y - aloneSoldier.y) < 50) {
//				if(game.soldiers.size() >= 5) {
//					game.eattingSoldier = aloneSoldier;
//					game.aloneSoldiers.removeElement(aloneSoldier);
//					
//					game.canvasControl.setView(game.canvasControl.nullView);
//					game.removeResource();
//					game.canvasControl.setView(new Dialog(game.canvasControl, 10));
//					game.canvasControl.setGoBackView(game);
//				} else {
					game.addSoldier(aloneSoldier.type);
					((Hero)game.soldiers.elementAt(game.soldiers.size() - 1)).justAdd = true;
					game.aloneSoldiers.removeElement(aloneSoldier);
					
					if(game.soldiers.size() >= 5) {
						game.pressOk = true;
						game.loadHelpImage();
					}
//				}
			}
		}
	}
	
	/**
	 * 自动攻击 士兵5自动加血。
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
			
		case 5:
			joinTime ++;
			if(joinTime > 9) {
				game.hero.addBlood(10);
				for(int i=0; i<game.soldiers.size(); i++) {
					((Hero)game.soldiers.elementAt(i)).addBlood(10);
				}
				joinTime = 0;
			}
			break;
		}
	}
	
	/**
	 * 增加指定量的血量
	 * @param blood 指定的量
	 */
	public void addBlood(int blood) {
		int addBlood = life + blood > lifeMax ? lifeMax - life : blood;
		life += addBlood;
		
		if(addBlood != 0) {
			bloodAdding = true;
			bloodAddValue = addBlood;
			bloodAddTimeControl = 0;
		}
	}

	/**
	 * 攻击
	 */
	public void fire(Devil devil) {
		game.canvasControl.playerHandler.toPlay(2);
		
		attacked = true;
		showAttactImage = true;
		effectShowing = true;
		hittingDevil = devil;
		attackDir = direction;
		
		if(type == 0 || type == 2 || type == 3) {
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
				changeDirection(1, 0);
			} else {
				nextDirection = 1;
			}
			game.pause = false;
			break;
		case C.KEY_UP:
			if(!afterChange) {
				changeDirection(2, 0);
			} else {
				nextDirection = 2;
			}
			game.pause = false;
			break;
		case C.KEY_LEFT:
			if(!afterChange) {
				changeDirection(3, 0);
			} else {
				nextDirection = 3;
			}
			game.pause = false;
			break;
		case C.KEY_DOWN:
			if(!afterChange) {
				changeDirection(4, 0);
			} else {
				nextDirection = 4;
			}
			game.pause = false;
			break;
		}
	}
	
	/**
	 * 复活重置
	 */
	public void reset() {
		life = lifeMax;
		alive = true;
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

	
	/**
	 * 被打了。
	 * @param devil
	 */
	public void beHitted(Devil devil) {
		hitMeType = devil.type;
		showBeHit = true;
		showBeHitControl = 0;
		if(hitMeType < 3) {//近战怪攻击我的效果
			ima_beHit = ImageManager.ima_hero_beHitClose[hitMeType - 1];
			
		} else if(hitMeType > 4) {//远程怪攻击我的效果
			ima_beHit = ImageManager.ima_hero_beHitRemote[hitMeType - 5];
		}
		
		if(!game.heroProtected){
			life -= devil.attackPower;
			if(type == 0) {
				if(life < lifeMax * 3 / 10 && !Game.weak) {
					game.loadWeakImage();
					Game.weak = true;
				}
			}
			
			if(life <=0){
				game.canvasControl.playerHandler.toPlay(3);
				
				if(type == 0) {
					alive = false;
				} else {
					willDie = true;
				}
			}
		}
			
	}

}
