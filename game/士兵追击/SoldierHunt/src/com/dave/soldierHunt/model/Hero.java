package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Hero {
	/**
	 * �洢Ӣ�۵��ƶ�����
	 * 0����ֹ��
	 * 1�����ң�Ĭ��ֵ����
	 * 2�����ϡ�
	 * 3������
	 * 4�����¡�
	 */
	public int direction = 1;
	
	/**
	 * ��¼����ʱ�ķ���
	 */
	public int attackDir;
	
	/**
	 * �����������ʱ��洢��һ������
	 */
	public int nextDirection;
	
	/**
	 * ʿ��������
	 * 0����Ӣ��
	 * 1~5�ֱ����ʿ��1~ʿ��5.
	 */
	public int type;
	
	/**
	 * ��Ӣ�ۻ�ʿ������һ��ʿ��
	 */
	private Hero nextHero;

	/**
	 * ��Ӣ�ۻ�ʿ������һ��Ӣ�ۻ�ʿ��
	 */
	private Hero previousHero;
	
	/**
	 * �ƶ���ƫ��ֵ
	 */
	private static final int MOVE_OFFER = 10;
	
	/**
	 * ���Ż�����ȥ��
	 */
	public boolean alive = true;
	
	/**
	 * Ѫֵ����
	 */
	public int lifeMax;
	
	/**
	 * ����ֵ��Ѫ����
	 */
	public int life;
	
	/**
	 * ������
	 */
	public int attackPower;
	
	/**
	 * Ӣ�۵ȼ�
	 */
	public static int level;
	
	/**
	 * ����ڵ�ͼx����ֵ��
	 */
	public int x = 320;
	
	/**
	 * ����ڵ�ͼy����ֵ��
	 */
	public int y = 489;
	
	/**
	 * �ƶ�ͼƬ�±�ֵ
	 */
	private int moveIndex;

	/**
	 * Ӣ�����ڵ���Ϸ
	 */
	private Game game;

	/**
	 * ���ڸոı䷽���״̬����ֹ����ı䷽��
	 */
	private boolean afterChange;

	private int keySpeedControl;

	/**
	 * ���ڸչ�����״̬
	 */
	private boolean attacked;
	
	/**
	 * ��������ʱ���¼
	 */
	private int afterAttactTime;
	
	/**
	 * ����ͼƬ�±�ֵ��¼
	 */
	private int attactImageIndex;
	
	/**
	 * �Ƿ���ʾ������ͼƬ
	 */
	private boolean showAttactImage;

	/**
	 * ����ͼƬ�±�ֵ
	 */
	private int ironBallIndex;

	/**
	 * ����Ч��ͼƬx����ֵ��
	 */
	private int effectX;
	/**
	 * ����Ч��ͼƬy����ֵ��
	 */
	private int effectY;
	
	/**
	 * �ӵ�����ʸ��������ƶ���x����ƫ����
	 */
	private int attackingDevilXOffer;
	/**
	 * �ӵ�����ʸ��������ƶ���y����ƫ����
	 */
	private int attackingDevilYOffer;

	/**
	 * �ӵ��Ȼ�û�򵽵���
	 */
	private boolean effectShowing;

	/**
	 * �����ӵ��ȵ���ʾʱ��
	 */
	private int effectShowControl;
	
	/**
	 * �ӵ��ȵ��ƶ��ٶȡ�
	 */
	private static final int bulletOffer = 50;
	
	/**
	 * ��������
	 */
	private Devil hittingDevil;

	/**
	 * ����
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
	 * ��ʾ������Ч
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
	 * �ı䷽�򷽷�
	 * @param gotoDir ���÷������
	 */
	public void changeDirection(int gotoDir) {
		if(gotoDir != direction && gotoDir - direction != 2 && gotoDir - direction != -2){
			direction = gotoDir;
			afterChange = true;

			System.gc();
		}
	}
	
	/**
	 * ���߼�����
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
	 * ʱ����Ʒ���
	 */
	private void timeControl() {
		if(attacked) {//���������ͼƬ��ʾ�Ŀ���
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

		if(afterChange) {//�ı䷽��ʱ��������
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
	 * ����Ӣ���ƶ��ķ���
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
	 * �ӵ������������ƶ�
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
	 * �ڵ��ɹ�ȥ�ˡ��Ƿ�򵽵����أ�
	 * @param devil ��������
	 */
	public boolean bulletHitDevil(Devil devil) {
		if(Math.abs(effectX - devil.x) < 50 && Math.abs(effectY - devil.y) < 50) {
			return true;
		} else return false;
	}
	
	/**
	 * ײ������դ���ˡ���Ǯ���ɡ�
	 */
	public void hitFences() {
		if(x <= 25 || x >= 935 || y <= 20 || y >= 685) {
			alive = false;
		}
	}
	
	/**
	 * ײ���Լ���ʿ���ˡ�Ҳ���ˡ�
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
	 * �Խ��
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
	 * �Զ�����
	 * ������Χ�жϷ���
	 */
	private void autoAttack() {
		switch(type) {
		case 0://Ӣ���Զ�����
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
		case 1://ʿ��1�Զ�����
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
	 * ����
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
	 * ������һ��ʿ���ķ���
	 */
	public void follow() {
		if(!previousHero.afterChange) {
			changeDirection(previousHero.direction);
		}
	}
	
	/**
	 * ��������
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
