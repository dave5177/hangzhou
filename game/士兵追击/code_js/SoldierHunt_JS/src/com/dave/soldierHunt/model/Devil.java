package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Devil {
	/**
	 * ���������
	 * 1~8�������1~����8
	 */
	public int type;
	
	/**
	 * �����ڵ���Ϸ
	 */
	private Game game;
	
	/**
	 * �ƶ��ٶȣ�Ҳ����ÿһ֡��ƫ����
	 */
	private static final int MOVE_OFFER = 3;
	
	/**
	 * x����ֵ������ڵ�ͼ��
	 */
	public int x;
	
	/**
	 * y����ֵ������ڵ�ͼ��
	 */
	public int y;
	
	/**
	 * ǰһ����x��yֵ��
	 */
	private int oldX, oldY;
	
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
	public int healthLength;

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
	public boolean willDie;
	
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
	private int attackImageIndex;

	/**
	 * ����Ч����ʾ����
	 */
	private int effectShowControl;

	/**
	 * Զ�̹���Ч��ͼ�±�ֵ
	 */
	private int effectImageIndex;

	private boolean beHittedBySoldier2;

	private int arrowControl;

	private int arrowHitImageIndex;

	private boolean beHittedBySoldierClose;

	private int beHitIndex;
	
	/**
	 * Զ�ֵ̹Ĺ����ӵ��ٶ�
	 */
	private static final int bulletOffer = 40;

	
	/**
	 * ���ﹹ�췽��
	 * @param game �������ڵ���Ϸ
	 * @param type ��������
	 * @param direction ��ʼ����
	 * @param x ��ʼx����
	 * @param y ��ʼy����
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
	 * �������ͺͳ�ʼ�����ʼ������
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
		if(willDie) {//��ʾ��������ʧ��Ч��
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
			if(beHittedByHero) {//��ʾ��������Ч��
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
	 * �ı䷽�򷽷�
	 * @param gotoDir ���÷������
	 */
	public void changeDirection(int gotoDir) {
		if(gotoDir != direction && Math.abs(gotoDir - direction) != 2){
			direction = gotoDir;
			changeDirectionInterval = C.R.nextInt(50);//ÿ�θı䷽��֮������õ�һ��0~50�ĵ��´θı䷽���ʱ��������
			changeDirectionTime = 0;

			ima_move = ImageManager.ima_devil_move[type - 1][gotoDir - 1];
			
			System.gc();
		}
	}
	
	/**
	 * ���߼�����
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
				giddy = false;//�����ˡ�
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
		
		if(attacked) {//���������ͼƬ��ʾ�Ŀ���
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
	 * ����Ч�����ƶ�����ײ��⡣
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
	 * ����Ӣ��
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
	 * �Զ��ı䷽��
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
	 * ����
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
	 * �Ų�
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
