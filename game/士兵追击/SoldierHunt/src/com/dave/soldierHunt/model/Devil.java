package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Devil {
	/**
	 * 怪物的类型
	 * 1~8代表怪物1~怪物8
	 */
	private int type;
	
	/**
	 * 怪所在的游戏
	 */
	private Game game;
	
	/**
	 * 移动速度，也就是每一帧的偏移量
	 */
	private static final int MOVE_OFFER = 8;
	
	/**
	 * x坐标值（相对于地图）
	 */
	public int x;
	
	/**
	 * y坐标值（相对于地图）
	 */
	public int y;
	
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
	private int healthLength;

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
	private boolean willDie;
	
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
	private int attactImageIndex;

	/**
	 * 攻击效果显示控制
	 */
	private int effectShowControl;

	/**
	 * 远程攻击效果图下标值
	 */
	private int effectImageIndex;
	
	/**
	 * 远程怪的攻击子弹速度
	 */
	private static final int bulletOffer = 40;

	
	public Devil(Game game, int type, int direction, int x, int y) {
		this.game = game;
		this.type = type;
		this.direction = direction;
		this.x = x;
		this.y = y;
		
		init(type, direction);
	}
	
	/**
	 * 根据类型和初始方向初始化怪物
	 * @param type
	 * @param direction
	 */
	private void init(int type, int direction) {
		this.lifeMax = C.devilParameter[type][0];
		this.attackPower = C.devilParameter[type][1];
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
				g.drawImage(ImageManager.ima_devil_attack[type - 1][attackDir - 1][attactImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(ima_move[moveImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			g.setColor(0, 100, 0);
			g.drawRoundRect(x + game.mapX - healthLength / 2, game.mapY + y - 759 - 30, healthLength, 6, 1, 1);
			g.setColor(100, 50, 50);
			g.fillRoundRect(x + game.mapX - healthLength / 2 + 1, game.mapY + y - 759 - 30 + 1, healthLength * life / lifeMax - 1, 5, 1, 1);
			
			if(beHittedByHero) {//显示被攻击的效果
				g.drawImage(ImageManager.ima_giddy[giddyImageIndex], x + game.mapX, game.mapY + y - 759 - 30, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			if(beHittedBySoldier3) {
				g.drawImage(ImageManager.ima_burning[fireImageIndex], x + game.mapX, game.mapY + y - 759 + 20, Graphics.VCENTER | Graphics.HCENTER);
			}
			
		}
		
	}

	/**
	 * 改变方向方法
	 * @param gotoDir 设置方向参数
	 */
	public void changeDirection(int gotoDir) {
		if(gotoDir != direction && Math.abs(gotoDir - direction) != 2){
			direction = gotoDir;
			changeDirectionInterval = C.R.nextInt(50) + 50;//每次改变方向之后随机得到一个50~100的到下次改变方向的时间间隔数。
			changeDirectionTime = 0;

			ima_move = ImageManager.ima_devil_move[type - 1][gotoDir - 1];
			
			System.gc();
		}
	}
	
	/**
	 * 主逻辑方法
	 */
	public void logic() {
		if(!willDie && !giddy) {
			move();
			autoAttack();
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
		
		if(beHittedBySoldier3) {
			fireControl ++;
			
			fireImageIndex ++;
			if(fireImageIndex > 2) fireImageIndex = 0;
			
			if(fireControl > 10) {
				beHittedBySoldier3 = false;
				fireControl = 0;
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
	}

	private void attackEffectMove() {
		effectShowControl ++;
		
		effectImageIndex ++;
		if(effectImageIndex > 3) effectImageIndex = 0;
		
		if(effectShowControl > 12) {
			effectShowing = false;
			effectShowControl = 0;
		}
		if(bulletHitSoldier(hittingSoldier)) {
			hittingSoldier.beHitted(this);
			effectShowing = false;
			effectShowControl = 0;
		}
		effectX += attackingDevilXOffer;
		effectY += attackingDevilYOffer;
	}

	private boolean bulletHitSoldier(Hero hittingSoldier) {
		if(Math.abs(effectX - hittingSoldier.x) < 50 && Math.abs(effectY - hittingSoldier.y) < 50) {
			return true;
		} else return false;
	}

	private void move() {
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
		if(x <= 45 && direction == 3){
			randomChangeDirection();
		} else if(x >= 915 && direction == 1) {
			randomChangeDirection();
		} else if(y <= 50 && direction == 2) {
			randomChangeDirection();
		} else if(y >= 670 && direction == 4) {
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
		effectShowing = true;
		hittingSoldier = hero;
		attackDir = direction;
		
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
		case 3:
			beHittedBySoldier3 = true;
			break;
		}
		
		this.life -= heroAttackPower;
		if(life <= 0) {
			willDie = true;
		}
	}


	/**
	 * 遗产
	 */
	public void afterDie() {
		switch(type) {
		case 1:
			game.coins.addElement(new Coin(5, x, y, game));
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		}
		game.devils.removeElement(this);
	}

}
