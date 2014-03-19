package com.dave.soldierHunt.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.model.AloneSoldier;
import com.dave.soldierHunt.model.Coin;
import com.dave.soldierHunt.model.Devil;
import com.dave.soldierHunt.model.Hero;
import com.dave.soldierHunt.model.Obstacle;
import com.dave.soldierHunt.net.ServerIptv;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;

/**
 * 游戏区
 * @author Dave
 *
 */
public class Game implements Showable {
	public CanvasControl canvasControl;
	
	public Image img_map;
	private Image img_bottom;
	private Image img_top;
	private Image img_number;
//	private Image[]  ima_blood;

	public Image img_number_blood;
	public Image img_number_eatCoin;
	public Image img_point;
	public Image img_word_eatCoin;
	public Image img_help;
	
//	private Image img_weak;
//	private Image[] ima_weak;
	
	private Image img_weak_word;

	private Image[] ima_bomb;
	public Image[] ima_obstacle;
	
	/**
	 * 给二逼玩家操作的,英雄
	 */
	public Hero hero;
	
	/**
	 * 存储当前所有的士兵
	 */
	public Vector soldiers;
	
	/**
	 * 没归队的士兵
	 */
	public Vector aloneSoldiers;
	
	/**
	 * 当前地图上的金币
	 */
	public Vector coins;
	
	/**
	 * 存储当前所有的敌人
	 */
	public Vector devils;

	/**
	 * 当前地图上的障碍物
	 */
	public Vector obstacles;

	/**
	 * 屏幕的中点x坐标
	 */
	public static final int CENTER_X = 320;
	/**
	 * 屏幕的中点y坐标
	 */
	public static final int CENTER_Y = 260;
	
	/**
	 * 地图左下角顶点x坐标
	 */
	public int mapX = 0;
	/**
	 * 地图左下角顶点y坐标
	 */
	public int mapY = 530;
	
	/**
	 * 第几关
	 */
	private int level;
	
	/**
	 * 暂停
	 */
	public boolean pause;
	
	/**
	 * 关卡使用时间（单位：分秒（1秒 = 10分秒））
	 */
	private int levelUseTime;
	
	/**
	 * 本关吃到的金币数。
	 */
	public static int coinCount;
	
	/**
	 * 本关目标金币数
	 */
	public final int targetCoin;
	
	/**
	 * 显示炸弹效果
	 */
	public boolean showBomb;

	/**
	 * 炸弹图片下标值
	 */
	private int bombImageIndex;

	/**
	 * 炸弹在怪物右边的水平距离
	 */
	private int bombX = 60;

	/**
	 * 炸弹在怪物上边的垂直距离
	 */
	private int bombY = 60;

	/**
	 * 无敌状态
	 */
	public boolean heroProtected = true;

	/**
	 * 无敌剩余时间。
	 */
	private int protectedLeftTime = 30;

	/**
	 * 无敌图片下标值
	 */
	public int protectedImageIndex;

	/**
	 * 吸铁石吸金币状态
	 */
	public boolean inhale;

	/**
	 * 正在救的士兵
	 */
	public AloneSoldier eattingSoldier;

	/**
	 * 已过关
	 */
	private boolean complete;

	/**
	 * 残血了
	 */
	public static boolean weak;

	/**
	 * 提示使用吸铁石
	 */
	private boolean showManyCoin;

	/**
	 * 是否需要按‘ok’键加入
	 */
	public boolean pressOk;

	public Game(CanvasControl canvasControl, int level, int soldierQuantity) {
		this.canvasControl = canvasControl;
		this.level = level;
		targetCoin = 30 + (level - 1) * 50;
		coinCount = 0;
		
		hero = new Hero(this, 0);
		soldiers = new Vector();
		devils = new Vector();
		coins = new Vector();
		aloneSoldiers = new Vector();
		obstacles = new Vector();
		
		mapInit();
		
		for(int i=0; i<soldierQuantity; i++) {
			addSoldier(2);
		}
		
		if(soldiers.size() >= 5) pressOk = true;
	}

	/**
	 * 摆好地图
	 */
	public void mapInit() {
		int[][] obstacleArray = null;
		int loadMapIndex = level > 30 ? 30 : level;
		try {
			obstacleArray = C.loadObstacleArray("/mapArray/level_" + loadMapIndex + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int lengthOut = obstacleArray.length;
		int lengthIn = obstacleArray[0].length;
		
		for(int i=0; i<lengthOut; i++) {
			for(int j=0; j<lengthIn; j++) {
				int value = obstacleArray[i][j];
				if(value != 0) obstacles.addElement(new Obstacle(value, 102 + j * 47, 86 + i * 52, this));
			}
		}
	}
	
	/**
	 * 自动产生敌人
	 */
	public void autoCreateDevil() {
		if(devils.size() == 0) {
			int mount = C.R.nextInt(3) * 2 + 6;
			
			for(int i=0; i<mount; i++) {
				int[] devil_x_y = getRandomDevilPoint();
				devils.addElement(new Devil(this, C.R.nextInt(8) + 1, C.R.nextInt(4) + 1, devil_x_y[0], devil_x_y[1]));
//				devils.addElement(new Devil(this, C.R.nextInt(7) + 1, C.R.nextInt(4) + 1, devil_x_y[0], devil_x_y[1]));
			}
		}
		/*switch(level) {
		case 1:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, 1, C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 2:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, C.R.nextInt(2) + 1, C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 3:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(40, 30, 30, 0, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 4:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(30, 40, 30, 0, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 5:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(30, 30, 40, 0, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 6:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(20, 40, 40, 0, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 7:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(10, 50, 40, 0, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 8:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(10, 40, 50, 0, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 9:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(30, 30, 30, 10, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 10:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(20, 30, 30, 20, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 11:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(10, 30, 40, 20, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 12:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(10, 20, 30, 40, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		case 13:
			if(levelUseTime % 100 == 0 || levelUseTime == 0) {
				for(int i=0; i<3; i++) {
					devils.addElement(new Devil(this, getDevilTypeRandom(5, 15, 40, 40, 0, 0, 0, 0), C.R.nextInt(4) + 1, C.R.nextInt(800) + 50, C.R.nextInt(600) + 50));
				}
			}
			break;
		}*/
	}
	
	/**
	 * 产生一个地图随机的点，用于产生怪物
	 * @return
	 */
	public int[] getRandomDevilPoint() {
		int[] result = new int[2];
		int random_x = C.R.nextInt(770) + 90;
		int random_y = C.R.nextInt(530) + 70;
		result[0] = random_x;
		result[1] = random_y;
		
		int size = obstacles.size();
		for(int i=0; i<size; i++) {
			Obstacle obstacle = (Obstacle)obstacles.elementAt(i);
			if(Math.abs(random_x - obstacle.x) <= 50 && Math.abs(random_y + 25 - (obstacle.y + 15)) <= 50) {
				result = getRandomDevilPoint();
			}
		}
		
		if(Math.abs(random_x - hero.x) <= 200 && Math.abs(random_y - hero.y) <= 200) {
			result = getRandomDevilPoint();
		}
		
		return result;
	}
	
	/**
	 * 产生一个地图上随机的点要求在屏幕内,用于生产宝石金币
	 * @return x,y坐标的数组
	 */
	public int[] getRandomCoinPoint() {
		int[] result = new int[2];
		int random_x = C.R.nextInt(770) + 90;
		int random_y = C.R.nextInt(530) + 70;
		result[0] = random_x;
		result[1] = random_y;
		
		if(random_x + mapX > 640 ||
				random_x + mapX < 0 ||
				mapY + random_y - 759 < 0 ||
				mapY + random_y - 759 > 530)
			result = getRandomCoinPoint();
		
		int size = obstacles.size();
		for(int i=0; i<size; i++) {
			Obstacle obstacle = (Obstacle)obstacles.elementAt(i);
			if(Math.abs(random_x - obstacle.x) <= 50 && Math.abs(random_y + 25 - (obstacle.y + 15)) <= 30) {
				result = getRandomCoinPoint();
			}
		}
		
		if(Math.abs(random_x - hero.x) <= 200 && Math.abs(random_y - hero.y) <= 200) {
			result = getRandomCoinPoint();
		}
		
		return result;
	}
	
	/**
	 * 按特定的概率生成敌人类型
	 * @param probabilistic1 怪物1的概率的百分数。
	 * @param probabilistic2
	 * @param probabilistic3
	 * @param probabilistic4
	 * @param probabilistic5
	 * @param probabilistic6
	 * @param probabilistic7
	 * @param probabilistic8
	 * @return 
	 */
	public int getDevilTypeRandom(int probabilistic1, int probabilistic2, 
			int probabilistic3, int probabilistic4, int probabilistic5, 
			int probabilistic6, int probabilistic7, int probabilistic8) {
		int randomNum = C.R.nextInt(100);
		if(randomNum < probabilistic1) return 1;
		else if(randomNum < probabilistic1 + probabilistic2) return 2;
		else if(randomNum < probabilistic1 + probabilistic2 + probabilistic3) return 3;
		else if(randomNum < probabilistic1 + probabilistic2 + probabilistic3 + probabilistic4) return 4;
		else if(randomNum < probabilistic1 + probabilistic2 + probabilistic3 + probabilistic4 + probabilistic5) return 5;
		else if(randomNum < probabilistic1 + probabilistic2 + probabilistic3 + probabilistic4 + probabilistic5 + probabilistic6) return 6;
		else if(randomNum < probabilistic1 + probabilistic2 + probabilistic3 + probabilistic4 + probabilistic5 + probabilistic6 + probabilistic7) return 7;
		else return 8;
	}
	
	public void show(Graphics g) {
		g.setClip(0, 0, 640, 530);
		g.drawImage(img_map, mapX, mapY, Graphics.LEFT | Graphics.BOTTOM);
		
		for(int i=0; i<obstacles.size(); i++) {
			((Obstacle)obstacles.elementAt(i)).show(g);
		}
		
		for(int i=0; i<coins.size(); i++) {
			((Coin)coins.elementAt(i)).show(g);
		}
		for(int i=0; i<devils.size(); i++){
			((Devil)devils.elementAt(i)).show(g);
		}
		
		Devil devil = null;
		for(int i=0; i<devils.size(); i++){
			devil = ((Devil)devils.elementAt(i));
			if(!devil.willDie) {
				g.setColor(50, 50, 50);
				g.fillRoundRect(devil.x + mapX - devil.healthLength / 2, mapY + devil.y - 759 - 40, devil.healthLength, 6, 1, 1);
				g.setColor(150, 50, 50);
				g.fillRoundRect(devil.x + mapX - devil.healthLength / 2 + 1, mapY + devil.y - 759 - 40 + 1, devil.healthLength * devil.life / devil.lifeMax - 2, 4, 1, 1);
			}
		}
		
		for(int i=0; i<aloneSoldiers.size(); i++) {
			((AloneSoldier)aloneSoldiers.elementAt(i)).show(g);
		}
		
		if(pause) {
			if(levelUseTime % 2 == 0) {
				hero.show(g);
				for(int i=0; i<soldiers.size(); i++){
					((Hero)soldiers.elementAt(i)).show(g);
				}

				Hero soldier = null;
				soldier = hero;
				g.setColor(50, 50, 50);
				g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2, mapY + soldier.y - 759 - 40, soldier.healthLength, 6, 1, 1);
				g.setColor(0, 180, 0);
				g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2 + 1, mapY + soldier.y - 759 - 40 + 1, soldier.healthLength * soldier.life / soldier.lifeMax - 2, 4, 1, 1);
				
				g.drawImage(img_point, soldier.x + mapX, mapY + soldier.y - 759 - 60, Graphics.VCENTER | Graphics.HCENTER);
				
				for(int i=0; i<soldiers.size(); i++){
					soldier = (Hero)soldiers.elementAt(i);
					g.setColor(50, 50, 50);
					g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2, mapY + soldier.y - 759 - 40, soldier.healthLength, 6, 1, 1);
					g.setColor(0, 180, 0);
					g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2 + 1, mapY + soldier.y - 759 - 40 + 1, soldier.healthLength * soldier.life / soldier.lifeMax - 2, 4, 1, 1);
				}
			}
		} else {
			hero.show(g);
			for(int i=0; i<soldiers.size(); i++){
				((Hero)soldiers.elementAt(i)).show(g);
			}

			Hero soldier = null;
			soldier = hero;
			g.setColor(50, 50, 50);
			g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2, mapY + soldier.y - 759 - 40, soldier.healthLength, 6, 1, 1);
			g.setColor(0, 180, 0);
			g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2 + 1, mapY + soldier.y - 759 - 40 + 1, soldier.healthLength * soldier.life / soldier.lifeMax - 2, 4, 1, 1);
			
			g.drawImage(img_point, soldier.x + mapX, mapY + soldier.y - 759 - 60, Graphics.VCENTER | Graphics.HCENTER);
			
			for(int i=0; i<soldiers.size(); i++){
				soldier = (Hero)soldiers.elementAt(i);
				if(!soldier.willDie) {
					g.setColor(50, 50, 50);
					g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2, mapY + soldier.y - 759 - 40, soldier.healthLength, 6, 1, 1);
					g.setColor(0, 180, 0);
					g.fillRoundRect(soldier.x + mapX - soldier.healthLength / 2 + 1, mapY + soldier.y - 759 - 40 + 1, soldier.healthLength * soldier.life / soldier.lifeMax - 2, 4, 1, 1);
				}
			}
		}
		
		if(showBomb) {
			g.drawImage(ima_bomb[bombImageIndex], 320 + bombX - 200, 200 - bombY - 100, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(ima_bomb[bombImageIndex], 320 + bombX + 200, 200 - bombY - 100, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(ima_bomb[bombImageIndex], 320 + bombX - 200, 200 - bombY + 150, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(ima_bomb[bombImageIndex], 320 + bombX + 200, 200 - bombY + 150, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		g.drawImage(img_bottom, 0, 530, Graphics.LEFT | Graphics.BOTTOM);
		g.drawImage(img_top, 0, 0, 0);
		
		C.drawString(g, img_number, level + "", "0123456789X", 100, 7, 19, 24, 0, 0, 0);
		C.drawString(g, img_number, targetCoin + "", "0123456789X", 295, 7, 19, 24, 0, 0, 0);
		C.drawString(g, img_number, coinCount + "", "0123456789X", 535, 7, 19, 24, 0, 0, 0);
		
		C.drawString(g, img_number, "X" + canvasControl.goodsCount[0], "0123456789X", 89, 504, 19, 24, 0, 0, 0);
		C.drawString(g, img_number, "X" + canvasControl.goodsCount[1], "0123456789X", 225, 504, 19, 24, 0, 0, 0);
		if(heroProtected) {
			C.drawString(g, img_number, protectedLeftTime / 10 + "", "0123456789X", 357, 504, 19, 24, 0, 0, 0);
		} else {
			C.drawString(g, img_number, "X" + canvasControl.goodsCount[2], "0123456789X", 357, 504, 19, 24, 0, 0, 0);
		}
		C.drawString(g, img_number, "X" + canvasControl.goodsCount[3], "0123456789X", 509, 504, 19, 24, 0, 0, 0);
		
		if(weak && !showBomb) {
			if(levelUseTime % 10 < 5) {
//				g.drawImage(img_weak, 0, 34, 0);
//				g.drawImage(ima_weak[0], 320, 261, Graphics.BOTTOM | Graphics.RIGHT);
//				g.drawImage(ima_weak[1], 320, 261, Graphics.BOTTOM | Graphics.LEFT);
//				g.drawImage(ima_weak[2], 320, 261, Graphics.TOP | Graphics.RIGHT);
//				g.drawImage(ima_weak[3], 320, 261, Graphics.TOP | Graphics.LEFT);
			}
			g.drawImage(img_weak_word, 320, 100 + (levelUseTime % 2) * 5, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		if(showManyCoin) {
			g.drawImage(img_word_eatCoin, 320, 130 + (levelUseTime % 2) * 5, Graphics.HCENTER | Graphics.VCENTER);
		}
	}

	public void loadResource() {
		try {
//			img_map = Image.createImage("/game/map.png");
			img_bottom = Image.createImage("/game/bottom.png");
			img_top = Image.createImage("/game/top.png");
			img_number = Image.createImage("/game/number.png");
			img_number_blood = Image.createImage("/game/number_blood.png");
			img_number_eatCoin = Image.createImage("/game/number_eatCoin.png");
			img_word_eatCoin = Image.createImage("/game/word_eatCoin.png");
			img_point = Image.createImage("/game/point.png");
			
//			if(weak) loadWeakImage();
			
//			ima_blood = new Image[3];
//			Image img_blood = Image.createImage("/game/blood.png");
//			int w = img_blood.getWidth();
//			int h = img_blood.getHeight();
//			for(int i=0; i<3; i++) {
//				ima_blood[i] = Image.createImage(img_blood, w / 3 * i, 0, w / 3, h, 0);
//			}
			
			ima_obstacle = new Image[8];
			Image img_obstacle = Image.createImage("/game/obstacle.png");
			int w = img_obstacle.getWidth();
			int h = img_obstacle.getHeight();
			for(int i=0; i<8; i++) {
				if(i < 4) ima_obstacle[i] = Image.createImage(img_obstacle, (w >> 2) * i, 0, w >> 2, h >> 1, 0);
				else ima_obstacle[i] = Image.createImage(img_obstacle, (w >> 2) * (i - 4), h >> 1, w >> 2, h >> 1, 0);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadHelpImage();
		
		ImageManager.loadHeroImage();
//		ImageManager.loadDevilImage();

		GasStation.soldierCount = 0;
	}

	/**
	 * 残血效果的图片
	 */
	public void loadWeakImage() {
		try {
//			img_weak = Image.createImage("/game/weak.png");
			
//			ima_weak = new Image[4];
//			for(int i=0; i<4; i++) {
//				ima_weak[i] = Image.createImage("/game/weak_0" + (i + 1) + ".png");
//			}
			img_weak_word = Image.createImage("/game/weak_word.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadHelpImage() {
		try {
			if(pressOk) {
				img_help = Image.createImage("/game/help_ok.png");
			} else {
				img_help = Image.createImage("/game/help.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.gc();
	}
	
	/**
	 * 移除残血效果图片
	 */
	public void removeWeakImage() {
		
		if(weak) {
//			img_weak = null;
//			for(int i=0; i<4; i++) {
//				ima_weak[i] = null;
//			}
//			ima_weak = null;
			
			img_weak_word = null;
			
			System.gc();
		}
	}
	
	/**
	 * 加载炸弹图片
	 */
	public void loadBombImage() {
		try {
			ima_bomb = new Image[9];
			Image img_bomb1;
			img_bomb1 = Image.createImage("/game/bomb1.png");
			int w = img_bomb1.getWidth();
			int h = img_bomb1.getHeight();
			for(int i=0; i<4; i++) {
				ima_bomb[i] = Image.createImage(img_bomb1, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			Image img_bomb2 = Image.createImage("/game/bomb2.png");
			w = img_bomb2.getWidth();
			h = img_bomb2.getHeight();
			for(int i=0; i<5; i++) {
				ima_bomb[i + 4] = Image.createImage(img_bomb2, (w / 5) * i, 0, w / 5, h, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除炸弹图片
	 */
	public void removeBombImage() {
		for(int i=0; i<9; i++) {
			ima_bomb[i] = null;
		}
		ima_bomb = null;
		
		System.gc();
	}
	
	public void removeResource() {
//		img_map = null;
		img_bottom = null;
		img_top = null;
		img_number = null;
		img_number_blood = null;
		img_number_eatCoin = null;
		img_point = null;
		img_word_eatCoin = null;
		img_help = null;
		
//		if(weak) removeWeakImage();
		
//		for(int i=0; i<3; i++) {
//			ima_blood[i] = null;
//		}
//		ima_blood = null;
		
		for(int i=0; i<8; i++) {
			ima_obstacle = null;
		}
		ima_obstacle = null;
		
//		ImageManager.removeImage();
		ImageManager.removeHeroImage();
		
		System.gc();
	}
	
	/**
	 * 过关和退出游戏时移除服务器下载下来的图片
	 */
	public void removeOtherImage() {
		img_map = null;
		ImageManager.removeDevilImage();
	}

	public void keyPressed(int keyCode) {
		hero.keyPressed(keyCode);
		
		switch(keyCode) {
//		case C.KEY_8://测试添加士兵按键
//			addSoldier(1);
//			break;
//		case C.KEY_7://测试掉血
//			hero.life -= 20;
//			if(hero.life < hero.lifeMax * 3 / 10 && !weak) {
//				loadWeakImage();
//				weak = true;
//			}
//			break;
		case C.KEY_FIRE:
			if(pressOk && soldiers.size() < 10 && aloneSoldiers.size() > 0) {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setGoBackView(this);
//				new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_M16);
				CanvasControl.goodsIndex = 5;
				try {
					canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case C.KEY_1://使用生命药水或购买
			if(!pause) {
				canvasControl.playerHandler.toPlay(1);
				
				if(canvasControl.goodsCount[0] != 0) {
					canvasControl.goodsCount[0] --;
					hero.addBlood(hero.lifeMax);
				} else {
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 6));
					canvasControl.setGoBackView(this);
				}
			}
			break;
		case C.KEY_2://使用炸弹或购买
			if(!pause) {
				canvasControl.playerHandler.toPlay(1);
				
				if(canvasControl.goodsCount[1] != 0) {
					canvasControl.goodsCount[1] --;
					
					removeWeakImage();
					
					loadBombImage();
					showBomb = true;
					
				} else {
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 7));
					canvasControl.setGoBackView(this);
				}
			}
			break;
		case C.KEY_3://使用无敌或购买
			if(!pause) {
				canvasControl.playerHandler.toPlay(1);
				
				if(canvasControl.goodsCount[2] != 0) {
					canvasControl.goodsCount[2] --;
					heroProtected = true;
					protectedLeftTime += 300;
					
				} else {
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 8));
					canvasControl.setGoBackView(this);
				}
			}
			break;
		case C.KEY_4://使用吸铁石或购买
			if(!pause) {
				canvasControl.playerHandler.toPlay(1);
				
				if(canvasControl.goodsCount[3] != 0) {
					canvasControl.goodsCount[3] --;
					inhale = true;
				} else {
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 9));
					canvasControl.setGoBackView(this);
				}
			}
			break;
		case C.KEY_9://帮助
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Help(canvasControl, 1));
			canvasControl.setGoBackView(this);
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX://返回
			Dialog.game = this;
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 1));
			canvasControl.setGoBackView(this);
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		if(canvasControl.relive) {
			pause = true;
			relive();
			canvasControl.relive = false;
			return;
		}
		
		if(!pause) {
			autoCreateDevil();
			autoCreateCoin();
			
			hero.logic();
			hero.hitFences();
			if(!heroProtected) hero.hitSolider();
			hero.hitObstacle();
			hero.eatCoin();
			if(soldiers.size() < 5) hero.eatAloneSoldier();
			
			for(int i=0; i<soldiers.size(); i++){
				((Hero)soldiers.elementAt(i)).logic();
			}
			
			for(int i=0; i<devils.size(); i++){
				((Devil)devils.elementAt(i)).logic();
			}
			
			for(int i=0; i<coins.size(); i++) {
				((Coin)coins.elementAt(i)).logic();
			}
			
			for(int i=0; i<aloneSoldiers.size(); i++) {
				((AloneSoldier)aloneSoldiers.elementAt(i)).logic();
			}
			
			check();
		}
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
		
		levelUseTime ++;
	}

	/**
	 * 复活
	 */
	private void relive() {
		hero.reset();
		for(int i=0; i<soldiers.size(); i++){
			((Hero)soldiers.elementAt(i)).reset();
		}
		
		/*if(hero.x >= 935) {
			hero.x -= 50;
			for(int i=0; i<soldiers.size(); i++){
				((Hero)soldiers.elementAt(i)).x -= 50;
			}
		} else if(hero.x <= 25) {
			hero.x += 50;
			for(int i=0; i<soldiers.size(); i++){
				((Hero)soldiers.elementAt(i)).x += 50;
			}
		} else if(hero.y <= 20) {
			hero.y += 50;
			for(int i=0; i<soldiers.size(); i++){
				((Hero)soldiers.elementAt(i)).y += 50;
			}
		} else if(hero.y >= 685) {
			hero.y -= 50;
			for(int i=0; i<soldiers.size(); i++){
				((Hero)soldiers.elementAt(i)).y -= 50;
			}
		} else */if(hero.hittedSoldier) {
			hero.x = 320;
			hero.y = 489;
			hero.direction = 1;
			for(int i=0; i<soldiers.size(); i++){
				Hero soldier = ((Hero)soldiers.elementAt(i));
				soldier.x = 320 - (i + 1) * 50;
				soldier.y = 489;
				soldier.direction = 1;
			}
			
			mapX = 0;
			mapY = 530;
			hero.hittedSoldier = false;
		} else if(hero.hittedObstacle || hero.hittedFences){
			switch(hero.direction) {
			case 1:
				hero.x -= 55;
				for(int i=0; i<soldiers.size(); i++){
					((Hero)soldiers.elementAt(i)).x -= 55;
				}
				break;
			case 2:
				hero.y += 55;
				for(int i=0; i<soldiers.size(); i++){
					((Hero)soldiers.elementAt(i)).y += 55;
				}
				break;
			case 3:
				hero.x += 55;
				for(int i=0; i<soldiers.size(); i++){
					((Hero)soldiers.elementAt(i)).x += 55;
				}
				break;
			case 4:
				hero.y -= 55;
				for(int i=0; i<soldiers.size(); i++){
					((Hero)soldiers.elementAt(i)).y -= 55;
				}
				break;
			}
			
			hero.hittedObstacle = false;
			hero.hittedFences = false;
		}
		
		weak = false;
		heroProtected = true;
		protectedLeftTime = 30;
	}

	private void check() {
		if(!hero.alive) {
			Dialog.game = this;
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			removeWeakImage();
			canvasControl.setView(new Dialog(canvasControl, 2));
			canvasControl.setGoBackView(this);
		}
		
		if(heroProtected) {
			protectedImageIndex = protectedImageIndex == 1 ? 0 : 1;
			protectedLeftTime --;
			if(protectedLeftTime <= 0) {
				heroProtected = false;
			}
		}
		
		if(showBomb) {
			bombImageIndex ++;
			if(bombImageIndex < 3) {
				bombX -= 20;
				bombY -= 20;
			} else if(bombImageIndex > 8) {
				showBomb = false;
				bombImageIndex = 0;
				bombX = 60;
				bombY = 60;
				removeBombImage();
				loadWeakImage();
				
				for(int i=0; i<devils.size(); i++){
					Devil devil = ((Devil)devils.elementAt(i));
					devil.willDie = true;
				}
			}
		}
		
		if(inhale) {
			if(coins.size() == 0) inhale = false;
		}
		
		if(canvasControl.addSoldier) {
			int size = aloneSoldiers.size();
			if(size > 0) {
				int index = C.R.nextInt(size);
				AloneSoldier aloneSoldier = (AloneSoldier) aloneSoldiers.elementAt(index);
				
				addSoldier(aloneSoldier.type);
				((Hero)soldiers.elementAt(soldiers.size() - 1)).justAdd = true;
				aloneSoldiers.removeElement(aloneSoldier);
				
				canvasControl.addSoldier = false;
				aloneSoldier = null;
			}
		}
		

		if(coinCount >= targetCoin && !inhale && !complete) {//过关啦。
			canvasControl.playerHandler.toPlay(5);
			complete = true;
			
			CanvasControl.level ++;
			
			new ServerIptv(canvasControl).doSendScore();
			
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			removeOtherImage();
			removeWeakImage();
			canvasControl.setView(new Dialog(canvasControl, 14));
			canvasControl.setGoBackView(this);
		}
		
		if(weak) {//补血了。
			if(hero.life >= hero.lifeMax * 3 / 10) {
				weak = false;
				removeWeakImage();
			}
		}
		
		if(coins.size() > 30 && !showManyCoin) {
			showManyCoin = true;
		} else if(coins.size() <= 30 && showManyCoin) showManyCoin = false;
		
//		if(soldiers.size() >= 5 && !pressOk) {
//			pressOk = true;
//		} else if(soldiers.size() < 5 && pressOk) pressOk = false;
		
		if(soldiers.size() < 5 && pressOk) {
			pressOk = false;
			loadHelpImage();
		}
		
		if(canvasControl.addBlood) {
			canvasControl.goodsCount[0] --;
			hero.addBlood(hero.lifeMax);
			
			canvasControl.addBlood = false;
		}
	}

	/**
	 * 自动产生金币
	 */
	private void autoCreateCoin() {
		if(C.R.nextInt(20) == 0) {
			int[] point = getRandomCoinPoint();
			coins.addElement(new Coin(1, point[0], point[1], this));
		}
	}
	
	/**
	 * 增加一个特定类型的士兵
	 * @param type 类型。
	 */
	public void addSoldier(int type) {
		Hero newHero = new Hero(this, type);
		Hero lastHero = null;
		if(soldiers.size() == 0){
			lastHero = hero;
		} else {
			lastHero = (Hero)soldiers.elementAt(soldiers.size() - 1);
		}
		lastHero.setNextHero(newHero);
		newHero.setPreviousHero(lastHero);
		newHero.direction = lastHero.direction;
		switch(newHero.direction) {
		case 1://向右
			newHero.x = lastHero.x - 50;
			newHero.y = lastHero.y;
			break;
		case 2://向上
			newHero.x = lastHero.x;
			newHero.y = lastHero.y + 50;
			break;
		case 3://向左
			newHero.x = lastHero.x + 50;
			newHero.y = lastHero.y;
			break;
		case 4://向下
			newHero.x = lastHero.x;
			newHero.y = lastHero.y - 50;
			break;
		}
		soldiers.addElement(newHero);
		
		C.out("new Hero x: " + newHero.x);
		C.out("last hero x : " + lastHero.x);
		
		C.out("new hero y : " + newHero.y);
		C.out("last hero y : " + lastHero.y);
	}
	
}
