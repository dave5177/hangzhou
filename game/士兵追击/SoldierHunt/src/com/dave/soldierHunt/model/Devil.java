package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Devil {
	/**
	 * ���������
	 * 1~8�������1~����8
	 */
	private int type;
	
	/**
	 * �����ڵ���Ϸ
	 */
	private Game game;
	
	/**
	 * �ƶ��ٶȣ�Ҳ����ÿһ֡��ƫ����
	 */
	private static final int MOVE_OFFER = 8;
	
	/**
	 * x����ֵ������ڵ�ͼ��
	 */
	public int x;
	
	/**
	 * y����ֵ������ڵ�ͼ��
	 */
	public int y;
	
	private Image[] ima_move;
	
	/**
	 * ������˶�����
	 * @1�����ң�
	 * @2�����ϣ�
	 * @3������
	 * @4�����¡�
	 */
	public int direction;
	
	/**
	 * �������ֵ
	 */
	public int lifeMax;
	
	/**
	 * ������
	 */
	public int attackPower;
	
	/**
	 * ��ǰѪ��
	 */
	public int life;
	
	/**
	 * ����Ĺ�����ʽs
	 * @0����ս��
	 * @1��Զ�̡�
	 */
	public int attackMode;

	/**
	 * ��¼�������ߵ�ͼƬ��ʾ���±�ֵ
	 */
	private int moveImageIndex;

	/**
	 * �Զ��ı䷽���ʱ����
	 */
	private int changeDirectionInterval;

	/**
	 * �Զ��ı䷽����ʱ��
	 */
	private int changeDirectionTime;

	/**
	 * ����������
	 */
	private int healthLength;

	/**
	 * �����к��״̬
	 */
	private boolean beHittedByHero;
	
	/**
	 * ѣ��ͼƬ�±�ֵ
	 */
	private int giddyImageIndex;

	/**
	 * ������Ч����ʾʱ�����
	 */
	private int giddyControl;

	/**
	 * Ѫ��Ϊ0Ҫ����״̬
	 */
	private boolean willDie;
	
	/**
	 * ���ƶ�ú�����
	 */
	private int willDieControl;
	
	/**
	 * ������ʧͼƬ�±�ֵ
	 */
	private int dieImageIndex;

	/**
	 * ѣ��״̬
	 */
	private boolean giddy;

	private boolean beHittedBySoldier3;

	/**
	 * ��������������Ż�ͼƬ�±�ֵ
	 */
	private int fireImageIndex;

	private int fireControl;

	/**
	 * Զ�̹����Ĺ���Ч��ͼƬx����
	 */
	private int effectX;

	/**
	 * Զ�̹����Ĺ���Ч��y����
	 */
	private int effectY;

	/**
	 * ����Ч��xƫ����
	 */
	private int attackingDevilXOffer;

	/**
	 * ����Ч��yƫ����
	 */
	private int attackingDevilYOffer;

	/**
	 * ������״̬
	 */
	private boolean attacked;

	/**
	 * ��ʾ����ͼƬ
	 */
	private boolean showAttactImage;

	/**
	 * ��ʾ����Ч��
	 */
	private boolean effectShowing;

	/**
	 * ���ڹ�����ʿ��
	 */
	private Hero hittingSoldier;

	/**
	 * ��������
	 */
	private int attackDir;

	/**
	 * �������ʱ�����
	 */
	private int afterAttactTime;

	/**
	 * ����ͼƬ�±�ֵ
	 */
	private int attactImageIndex;

	/**
	 * ����Ч����ʾ����
	 */
	private int effectShowControl;

	/**
	 * Զ�̹���Ч��ͼ�±�ֵ
	 */
	private int effectImageIndex;
	
	/**
	 * Զ�ֵ̹Ĺ����ӵ��ٶ�
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
	 * �������ͺͳ�ʼ�����ʼ������
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
		if(willDie) {//��ʾ��������ʧ��Ч��
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
			
			if(beHittedByHero) {//��ʾ��������Ч��
				g.drawImage(ImageManager.ima_giddy[giddyImageIndex], x + game.mapX, game.mapY + y - 759 - 30, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			if(beHittedBySoldier3) {
				g.drawImage(ImageManager.ima_burning[fireImageIndex], x + game.mapX, game.mapY + y - 759 + 20, Graphics.VCENTER | Graphics.HCENTER);
			}
			
		}
		
	}

	/**
	 * �ı䷽�򷽷�
	 * @param gotoDir ���÷������
	 */
	public void changeDirection(int gotoDir) {
		if(gotoDir != direction && Math.abs(gotoDir - direction) != 2){
			direction = gotoDir;
			changeDirectionInterval = C.R.nextInt(50) + 50;//ÿ�θı䷽��֮������õ�һ��50~100�ĵ��´θı䷽���ʱ��������
			changeDirectionTime = 0;

			ima_move = ImageManager.ima_devil_move[type - 1][gotoDir - 1];
			
			System.gc();
		}
	}
	
	/**
	 * ���߼�����
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
				giddy = false;//�����ˡ�
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
	 * �Զ��ı䷽��
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
	 * �ڵ�ǰ����������ı�һ�η���
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
	 * �Զ�����
	 * ������Χ�жϷ���
	 */
	private void autoAttack() {
		int size = game.soldiers.size();
		switch(type) {
		case 1:
		case 2:
		case 3:
		case 4://ǰ4����ս
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
		case 8://��4��Զ��
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
	 * ����
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
	 * �����ˣ��ݡ�
	 * @param heroAttackPower ���ҵĹ�����
	 * @param hero ���ҵ���
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
	 * �Ų�
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
