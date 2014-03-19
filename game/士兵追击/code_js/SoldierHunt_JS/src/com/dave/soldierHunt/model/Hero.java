package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.soldierHunt.main.CanvasControl;
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
	private static final int MOVE_OFFER = 5;
	
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
	public boolean willDie;

	/**
	 * ��������ʧ�����Ŀ���
	 */
	private int willDieControl;

	/**
	 * ��ʧ�����±�ֵ
	 */
	private int dieImageIndex;

	/**
	 * Ѫ������
	 */
	public int healthLength;

	/**
	 * ���ҵĹֵ�����ֵ
	 */
	private int hitMeType;

	/**
	 * ��ʾ����Ч��
	 */
	private boolean showBeHit;

	/**
	 * ���Ʊ���Ч����ʾʱ��
	 */
	private int showBeHitControl;

	/**
	 * ����Ч��ͼ�±�ֵ
	 */
	private int beHitImageIndex;

	/**
	 * ����Ч��ͼ
	 */
	private Image[] ima_beHit;

	/**
	 * �ոռ���
	 */
	public boolean justAdd;

	/**
	 * ����Ч��ͼƬ�±�ֵ
	 */
	private int justAddIndex;

	/**
	 * ײ��ʿ���ˡ�
	 */
	public boolean hittedSoldier;
	
	/**
	 * ǰһ����X����
	 */
	private int lastX;
	
	/**
	 * ǰһ����Y����
	 */
	private int lastY;

	/**
	 * �ú����ʿ�����ǰ���ʿ��ʱ�����ԭ����Xֵ
	 */
	private int oldX;
	/**
	 * �ú����ʿ�����ǰ���ʿ��ʱ�����ԭ����Yֵ
	 */
	private int oldY;

	/**
	 * ԭ���ķ���
	 */
	private int oldDirection;

	/**
	 * ԭ���Ƿ��ڸոı䷽��״̬
	 */
	private boolean oldAfterChange;

	/**
	 * ԭ���ĸı䷽�������Ƶ�ֵ
	 */
	private int oldKeySpeedControl;

	/**
	 * ײ���ϰ������ˡ�
	 */
	public boolean hittedObstacle;

	/**
	 * ��ѪЧ����ʾʱ�����
	 */
	private int bloodAddTimeControl;

	/**
	 * ��Ѫ����
	 */
	private int bloodAddValue;

	/**
	 * ���ڼ�Ѫ����ʾ��Ѫ������
	 */
	private boolean bloodAdding;

	private int joinTime;

	/**
	 * ��դ��ײ���ˡ�
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
		if(willDie) {//��ʾ��������ʧ��Ч��
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
	 * �޵�Ч��
	 * @param g
	 */
	private void showProtected(Graphics g) {
		g.drawImage(ImageManager.ima_protected[game.protectedImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
	}

	/**
	 * ���Ч��
	 * @param g
	 */
	private void showJustAdd(Graphics g) {
		g.drawImage(ImageManager.ima_join[justAddIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
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
	 * ����Ч��
	 * @param g
	 */
	public void showBeHit(Graphics g) {
		if(hitMeType < 3 || hitMeType > 4) {
			g.drawImage(ima_beHit[beHitImageIndex], x + game.mapX, game.mapY + y - 759, Graphics.VCENTER | Graphics.HCENTER);
		}
	}
	
	/**
	 * �ı䷽�򷽷�
	 * @param gotoDir Ŀ�귽��
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
	 * ���߼�����
	 */
	public void logic() {
		move();
		
		if(!game.heroProtected) hitDevil();
		
		if(!attacked) autoAttack();
		
		timeControl();
	}

	/**
	 * ʱ����Ʒ���
	 */
	private void timeControl() {
		if(attacked) {//���������ͼƬ��ʾ�Ŀ���
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

		if(afterChange) {//�ı䷽��ʱ��������
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
		
		if(willDie) {//������ʧ��������
			willDieControl ++;
			dieImageIndex ++;
			if(dieImageIndex > 3) {
				dieImageIndex = 0;
				willDie = false;
				removeMe();
			}
		}
		
		if(showBeHit) {//������Ч������
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
		
		if(justAdd) {//���Ч����ʾ����
			justAddIndex ++;
			if(justAddIndex > 3) {
				justAddIndex = 0;
				justAdd = false;
			}
		}
		
		if(bloodAdding) {//��ѪЧ����ʾ����
			bloodAddTimeControl ++;
			if(bloodAddTimeControl > 4) {
				bloodAdding = false;
			}
		}
		
	}
	
	/**
	 * �����Ƴ��ˡ�
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
	 * ÿһ��������滻�滻ǰ���ʿ��
	 * @param nowHero �ʼ�滻��ʿ��
	 * @param endHero �滻�����ʿ��Ϊֹ
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
	 * ����Ӣ���ƶ��ķ���
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
	 * ײ���Լ���ʿ���ˡ�Ҳ���ˡ�
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
	 * ײ�����ˡ��ҡ�
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
	 * ײ���ϰ���
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
	 * �Խ��
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
	 * ���䵥��ʿ��
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
	 * �Զ����� ʿ��5�Զ���Ѫ��
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
	 * ����ָ������Ѫ��
	 * @param blood ָ������
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
	 * ����
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
	 * ��������
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
	 * �����ˡ�
	 * @param devil
	 */
	public void beHitted(Devil devil) {
		hitMeType = devil.type;
		showBeHit = true;
		showBeHitControl = 0;
		if(hitMeType < 3) {//��ս�ֹ����ҵ�Ч��
			ima_beHit = ImageManager.ima_hero_beHitClose[hitMeType - 1];
			
		} else if(hitMeType > 4) {//Զ�ֹ̹����ҵ�Ч��
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
