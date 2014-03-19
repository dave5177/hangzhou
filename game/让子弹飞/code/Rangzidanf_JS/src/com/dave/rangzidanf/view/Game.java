package com.dave.rangzidanf.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.rangzidanf.gameModel.Enemy;
import com.dave.rangzidanf.gameModel.EnemyFactory;
import com.dave.rangzidanf.gameModel.Gun;
import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.net.ServerIptv;
import com.dave.rangzidanf.tool.AudioPlay;
import com.dave.rangzidanf.tool.C;

public class Game implements Showable {
	private CanvasControl canvasControl;
	
	/**
	 * 你还活着吗？
	 */
	private boolean alive;
	
	/**
	 * 记录第几关
	 */
	private int level;
	
	/**
	 * 教学篇的时候用来记录进行到第几步了
	 */
	private int step = 1;
	
	/**
	 * 记录正在使用的抢
	 */
	private Gun gun;
	
	/**
	 * 存储所有的敌人
	 */
	private Vector enemies;
	
	/**
	 * 记录方向键被按下的状态
	 */
	private boolean keyLeftIsDown;
	private boolean keyDownIsDown;
	private boolean keyRightIsDown;
	private boolean keyUpIsDown;
	
	/**
	 * 记录是否处于隐蔽状态
	 */
	private boolean covered;
	
	/**
	 * 正在开枪
	 */
	private boolean shootting;
	
	/**
	 * 是否被发现了。
	 */
	private boolean beDiscover;
	
	/**
	 * 记录刚开完枪的状态，此时枪械需要回复时间。
	 */
	private boolean afterShoot;
	
	/**
	 * 是否显示游戏结束后的选择对话框。
	 */
	private boolean showGameOverDialog;
	
	private Image img_back;
	private Image img_topBar;
	private Image img_bottomBar;
//	private Image img_bunker;
	private Image img_sight;
	private Image img_bulletBar;
	private Image img_bullet;
	private Image img_redBullet;
	private Image img_platform;
	private Image img_number;
	private Image img_number_countdown;
	private Image img_key_5;
	private Image img_key_4;
	private Image img_beDiscover;
	private Image img_coverPress_1;
	private Image img_sigh;
	private Image img_sigh_small;
	private Image img_gameOver;
	private Image img_dialogBack;
	private Image img_gameOverButton;
	private Image img_gameOverselect;
	private Image img_score;
	private Image img_arrow;
	private Image img_fillMagazine;
	private Image img_number_bullets;
	private Image img_beHitted;
	private Image[] ima_fire;
	
	
	/**
	 * 存储各个不同军衔的敌人的图片
	 */
	private Image[][] ima_aliveEnemy;
	private Image[][] ima_deadEnemy;
	private Image[][] ima_moveEnemy;
	
	/**
	 * 瞄准镜的X轴坐标，默认为画面中心点。
	 */
	private int sightX = C.WIDTH / 2;
	/**
	 * 瞄准镜的Y轴坐标，默认为画面中心点。
	 */
	private int sightY = C.HEIGTH / 2;
	
	/**
	 * 瞄准镜移动速度控制
	 */
	private int sightMoveSpeed = 1;
	
	/**
	 * 江苏中兴盒子的瞄准镜移动偏移量
	 */
	private static final int sightMoveOffer = 5;
	
	/**
	 * 用来存储开完枪后瞄准镜和枪回复的长度
	 */
	private int restoreExtend;
	
	/**
	 * 抢的x轴坐标
	 */
	private int gunX = 350;

	/**
	 * 抢的Y轴坐标
	 */
	private int gunY = 406;

	/**
	 * 平台遮挡物的y坐标
	 */
	private int platformY = 477;
	
	/**
	 * 存活的敌人数
	 */
	private int enemyAmount;
	
	/**
	 * 每关敌人总数
	 */
	private int allEnemy;
	
	/**
	 * 开枪图片的下标值
	 */
	private int fireIndex;
	
	
	/**
	 * 射击的总次数
	 */
	private float shootTimes;
	
	/**
	 * 射中的总次数
	 */
	private float hitTimes;
	
	/**
	 * 命中率String
	 */
	private String hitRate = "100%";
	
	/**
	 * 使用的时间
	 */
	private String useTime = "00:00";
	
	/**
	 * 记录关卡开始时间
	 */
	private long missionStartTime;
	
	/**
	 * 存储关卡秒数
	 */
	private int second;
	/**
	 * 存储分钟数
	 */
	private int minute;
	
	/**
	 * 记录要被发现的开始时间
	 */
	private long willBeDiscoverStartTime;
	
	/**
	 * 被发现了，就要死了。
	 */
	private long willDieStartTime;
	
	/**
	 * 记录过多久显示死亡后选择对话框的开始时间
	 */
	private long gameOverDialogStartTime;
	
	/**
	 * 被发现几秒后死亡
	 */
	private int dieAfterSecond;
	
	/**
	 * "gameover"图片显示的x坐标
	 */
	private int gameOverX = 660;
	
	/**
	 * 游戏结束后选择的对话框y坐标
	 */
	private int gameOverDialogY;
	
	/**
	 * 游戏结束后选择的下标值。
	 * 0：更换更强的武器。
	 * 1：云南白药。
	 * 2：退出游戏。
	 */
	private int gameOverSelectIndex;

	/**
	 * 是否过关
	 */
	private boolean complete;

	/**
	 * 关卡最终得分
	 */
	private int score;

	private int scoreIndex;

	private long completeTime;

	private int arrowX = 225;
	
	/**
	 * 发现你的敌人
	 */
	private Enemy discoverEnemy;

	private int coverY;

	/**
	 * 教学篇文字提示
	 */
	private String[] guide = {
		"按方向键移动瞄准镜，使枪对准敌人",
		"按'ok'键射击敌人",
		"按'1'键隐藏，让敌人无法发现你",
		"按'2'键换子弹，装满弹匣",
		"按'3'键使用高爆子弹，提高攻击力。",
		"按'4'键使用隐身衣，即使不隐藏敌人也无法发现你",
		"按'5'键使用自动瞄准，枪口自动对准敌人",
		"按'6'键使用自动换子弹",
		""
	};

	private int lastKeyCode;

 	
	public Game(CanvasControl canvasControl, int level, Gun gun) {
		this.canvasControl = canvasControl;
		canvasControl.setNeedRepaint(false);
		canvasControl.isGameing = true;
		
		this.level = level;
		this.gun = gun;
		
		init();
	}

	/**
	 * 游戏初始化
	 */
	public void init() {
		if(CanvasControl.firstTimes == 1) {
			sightX = 600;
			sightY = 250;
			gunX = sightX + 30;
			gunY = sightY / 3 + 318;
		}
		
		alive = true;
		beDiscover = false;
		
		CanvasControl.autoAim = 0;
		CanvasControl.stealth = 0;
		CanvasControl.autoAimRemain = 0;
		CanvasControl.stealthRemain = 0;
		
		switch(level) {
		case 1:
			dieAfterSecond = 10;
			break;
		case 2:
			dieAfterSecond = 9;
			break;
		case 3:
			dieAfterSecond = 8;
			break;
		case 4:
			dieAfterSecond = 7;
			break;
		case 5:
			dieAfterSecond = 6;
			break;
		case 6:
			dieAfterSecond = 6;
			break;
		}
		
		enemies = new Vector();
		enemyInit();
		enemyAmount = enemies.size();
		
		missionStartTime = System.currentTimeMillis();
		willBeDiscoverStartTime = missionStartTime;
	}
	
	/**
	 * 初始化敌人
	 */
	public void enemyInit() {
		loadEnemyImage();
		
		switch(level){
		case 1:
			for(int i=0; i<12; i++) {
				enemies.addElement(EnemyFactory.createEnemy(0).
						setImage(ima_aliveEnemy[0], ima_deadEnemy[0], ima_moveEnemy[0]));
			}
			for(int i=0; i<3; i++) {
				enemies .addElement(EnemyFactory.createEnemy(1).
						setImage(ima_aliveEnemy[1], ima_deadEnemy[1], ima_moveEnemy[1]));
			}
			break;
		case 2:
			for(int i=0; i<13; i++) {
				enemies.addElement(EnemyFactory.createEnemy(0).
						setImage(ima_aliveEnemy[0], ima_deadEnemy[0], ima_moveEnemy[0]));
			}
			for(int i=0; i<3; i++) {
				enemies .addElement(EnemyFactory.createEnemy(1).
						setImage(ima_aliveEnemy[1], ima_deadEnemy[1], ima_moveEnemy[1]));
			}
			for(int i=0; i<2; i++) {
				enemies .addElement(EnemyFactory.createEnemy(2).
						setImage(ima_aliveEnemy[2], ima_deadEnemy[2], ima_moveEnemy[2]));
			}
			break;
		case 3:
			for(int i=0; i<9; i++) {
				enemies.addElement(EnemyFactory.createEnemy(0).
						setImage(ima_aliveEnemy[0], ima_deadEnemy[0], ima_moveEnemy[0]));
			}
			for(int i=0; i<5; i++) {
				enemies .addElement(EnemyFactory.createEnemy(1).
						setImage(ima_aliveEnemy[1], ima_deadEnemy[1], ima_moveEnemy[1]));
			}
			for(int i=0; i<5; i++) {
				enemies .addElement(EnemyFactory.createEnemy(2).
						setImage(ima_aliveEnemy[2], ima_deadEnemy[2], ima_moveEnemy[2]));
			}
			for(int i=0; i<1; i++) {
				enemies .addElement(EnemyFactory.createEnemy(3).
						setImage(ima_aliveEnemy[3], ima_deadEnemy[3], ima_moveEnemy[3]));
			}
			break;
		case 4:
			for(int i=0; i<8; i++) {
				enemies.addElement(EnemyFactory.createEnemy(0).
						setImage(ima_aliveEnemy[0], ima_deadEnemy[0], ima_moveEnemy[0]));
			}
			for(int i=0; i<6; i++) {
				enemies .addElement(EnemyFactory.createEnemy(1).
						setImage(ima_aliveEnemy[1], ima_deadEnemy[1], ima_moveEnemy[1]));
			}
			for(int i=0; i<7; i++) {
				enemies .addElement(EnemyFactory.createEnemy(2).
						setImage(ima_aliveEnemy[2], ima_deadEnemy[2], ima_moveEnemy[2]));
			}
			for(int i=0; i<2; i++) {
				enemies .addElement(EnemyFactory.createEnemy(3).
						setImage(ima_aliveEnemy[3], ima_deadEnemy[3], ima_moveEnemy[3]));
			}
			for(int i=0; i<1; i++) {
				enemies .addElement(EnemyFactory.createEnemy(4).
						setImage(ima_aliveEnemy[4], ima_deadEnemy[4], ima_moveEnemy[4]));
			}
			break;
		case 5:
			for(int i=0; i<9; i++) {
				enemies.addElement(EnemyFactory.createEnemy(0).
						setImage(ima_aliveEnemy[0], ima_deadEnemy[0], ima_moveEnemy[0]));
			}
			for(int i=0; i<8; i++) {
				enemies .addElement(EnemyFactory.createEnemy(1).
						setImage(ima_aliveEnemy[1], ima_deadEnemy[1], ima_moveEnemy[1]));
			}
			for(int i=0; i<3; i++) {
				enemies .addElement(EnemyFactory.createEnemy(2).
						setImage(ima_aliveEnemy[2], ima_deadEnemy[2], ima_moveEnemy[2]));
			}
			for(int i=0; i<3; i++) {
				enemies .addElement(EnemyFactory.createEnemy(3).
						setImage(ima_aliveEnemy[3], ima_deadEnemy[3], ima_moveEnemy[3]));
			}
			for(int i=0; i<3; i++) {
				enemies .addElement(EnemyFactory.createEnemy(4).
						setImage(ima_aliveEnemy[4], ima_deadEnemy[4], ima_moveEnemy[4]));
			}
			break;
		case 6:
			for(int i=0; i<5; i++) {
				enemies.addElement(EnemyFactory.createEnemy(0).
						setImage(ima_aliveEnemy[0], ima_deadEnemy[0], ima_moveEnemy[0]));
			}
			for(int i=0; i<10; i++) {
				enemies .addElement(EnemyFactory.createEnemy(1).
						setImage(ima_aliveEnemy[1], ima_deadEnemy[1], ima_moveEnemy[1]));
			}
			for(int i=0; i<6; i++) {
				enemies .addElement(EnemyFactory.createEnemy(2).
						setImage(ima_aliveEnemy[2], ima_deadEnemy[2], ima_moveEnemy[2]));
			}
			for(int i=0; i<4; i++) {
				enemies .addElement(EnemyFactory.createEnemy(3).
						setImage(ima_aliveEnemy[3], ima_deadEnemy[3], ima_moveEnemy[3]));
			}
			for(int i=0; i<5; i++) {
				enemies .addElement(EnemyFactory.createEnemy(4).
						setImage(ima_aliveEnemy[4], ima_deadEnemy[4], ima_moveEnemy[4]));
			}
			break;
		}
		
		allEnemy = enemies.size();
		
		putEnemyAtMap();
	}
	
	/**
	 * 把敌人放在地图中的特定位置
	 */
	public void putEnemyAtMap() {
		switch (level) {
		case 1 :
			((Enemy) enemies.elementAt(0)).put(35, 425);
			((Enemy) enemies.elementAt(1)).put(85, 367);
			((Enemy) enemies.elementAt(2)).put(290, 345);
			((Enemy) enemies.elementAt(3)).put(430, 405);
			((Enemy) enemies.elementAt(4)).put(405, 280);
			((Enemy) enemies.elementAt(5)).put(600, 388);
			((Enemy) enemies.elementAt(6)).put(93, 246).setInBunker(true, 80, 191, 20, 40);
			((Enemy) enemies.elementAt(7)).put(140, 78).setInBunker(true, 127, 23, 20, 40);
			((Enemy) enemies.elementAt(8)).put(244, 137).setInBunker(true, 231, 82, 20, 40);
			((Enemy) enemies.elementAt(9)).put(490, 108).setInBunker(true, 477, 53, 20, 40);
			((Enemy) enemies.elementAt(10)).put(577, 225);
			((Enemy) enemies.elementAt(11)).put(332, 237);
			((Enemy) enemies.elementAt(12)).put(130, 150);
			((Enemy) enemies.elementAt(13)).put(360, 100);
			((Enemy) enemies.elementAt(14)).put(200, 222);
			break;
		case 2 :
			((Enemy) enemies.elementAt(0)).put(100, 301);
			((Enemy) enemies.elementAt(1)).put(205, 318);
			((Enemy) enemies.elementAt(2)).put(187, 112).setInBunker(true, 167, 57, 50, 40);
			((Enemy) enemies.elementAt(3)).put(350, 97).setInBunker(true, 330, 42, 50, 40);
			((Enemy) enemies.elementAt(4)).put(365, 297);
			((Enemy) enemies.elementAt(5)).put(455, 263);
			((Enemy) enemies.elementAt(6)).put(558, 240).setInBunker(true, 530, 185, 50, 40);
			((Enemy) enemies.elementAt(7)).put(500, 360);
			((Enemy) enemies.elementAt(8)).put(595, 308);
			((Enemy) enemies.elementAt(9)).put(401, 467);
			((Enemy) enemies.elementAt(10)).put(15, 378);
			((Enemy) enemies.elementAt(11)).put(67, 124);
			((Enemy) enemies.elementAt(12)).put(449, 90);
			((Enemy) enemies.elementAt(13)).put(345, 363);
			((Enemy) enemies.elementAt(14)).put(181, 380);
			((Enemy) enemies.elementAt(15)).put(540, 420);
			((Enemy) enemies.elementAt(16)).put(145, 265).setInBunker(true, 110, 211, 35, 71);
			((Enemy) enemies.elementAt(17)).put(285, 242).setInBunker(true, 240, 195, 50, 60);
			break;
		case 3:
			((Enemy) enemies.elementAt(0)).put(171, 175).setInBunker(true, 161, 132, 21, 21);
			((Enemy) enemies.elementAt(1)).put(463, 172).setInBunker(true, 447, 129, 26, 21);
			((Enemy) enemies.elementAt(2)).put(324, 113).setInBunker(true, 311, 71, 24, 23);
			((Enemy) enemies.elementAt(3)).put(364, 116).setInBunker(true, 351, 73, 20, 21);
			((Enemy) enemies.elementAt(4)).put(282, 118).setInBunker(true, 272, 76, 16, 21);
			((Enemy) enemies.elementAt(5)).put(231, 115).setInBunker(true, 219, 73, 18, 21);
			((Enemy) enemies.elementAt(6)).put(173, 119).setInBunker(true, 162, 78, 20, 16);
			((Enemy) enemies.elementAt(7)).put(459, 115).setInBunker(true, 447, 74, 24, 19);
			((Enemy) enemies.elementAt(8)).put(412, 107).setInBunker(true, 401, 64, 18, 21);//上面二等兵
			((Enemy) enemies.elementAt(9)).put(212, 308).setInBunker(true, 197, 268, 28, 22);
			((Enemy) enemies.elementAt(10)).put(151, 305).setInBunker(true, 133, 265, 29, 26);
			((Enemy) enemies.elementAt(11)).put(456, 292).setInBunker(true, 442, 251, 25, 22);
			((Enemy) enemies.elementAt(12)).put(503, 292).setInBunker(true, 484, 251, 25, 21);
			((Enemy) enemies.elementAt(13)).put(328, 156);//上面是上尉
			((Enemy) enemies.elementAt(14)).put(600, 385);
			((Enemy) enemies.elementAt(15)).put(616, 123).setInBunker(true, 593, 51, 44, 56);
			((Enemy) enemies.elementAt(16)).put(495, 465);
			((Enemy) enemies.elementAt(17)).put(52, 357);
			((Enemy) enemies.elementAt(18)).put(38, 222).setInBunker(true, 11, 130, 44, 79);//上面是中校
			((Enemy) enemies.elementAt(19)).put(306, 361);//这个是上将
			break;
		case 4:
			((Enemy) enemies.elementAt(0)).put(69, 391);
			((Enemy) enemies.elementAt(1)).put(187, 427);
			((Enemy) enemies.elementAt(2)).put(503, 429);
			((Enemy) enemies.elementAt(3)).put(345, 376);
			((Enemy) enemies.elementAt(4)).put(460, 311);
			((Enemy) enemies.elementAt(5)).put(190, 251).setInBunker(true, 180, 214, 16, 14);
			((Enemy) enemies.elementAt(6)).put(394, 195).setInBunker(true, 387, 157, 11, 19);//
			((Enemy) enemies.elementAt(7)).put(589, 326);//上面二等兵
			((Enemy) enemies.elementAt(8)).put(49, 164);
			((Enemy) enemies.elementAt(9)).put(161, 274).setInBunker(true, 151, 234, 15, 13);
			((Enemy) enemies.elementAt(10)).put(114, 258).setInBunker(true, 106, 219, 18, 13);
			((Enemy) enemies.elementAt(11)).put(83, 239).setInBunker(true, 74, 201, 17, 12);
			((Enemy) enemies.elementAt(12)).put(84, 214).setInBunker(true, 73, 175, 18, 13);
			((Enemy) enemies.elementAt(13)).put(21, 238).setInBunker(true, 10, 199, 18, 14);//上面是上尉
			((Enemy) enemies.elementAt(14)).put(188, 271).setInBunker(true, 180, 231, 16, 14);
			((Enemy) enemies.elementAt(15)).put(188, 237).setInBunker(true, 180, 197, 16, 14);
			((Enemy) enemies.elementAt(16)).put(160, 215).setInBunker(true, 151, 174, 16, 14);
			((Enemy) enemies.elementAt(17)).put(301, 231).setInBunker(true, 294, 192, 16, 12);
			((Enemy) enemies.elementAt(18)).put(105, 326);
			((Enemy) enemies.elementAt(19)).put(253, 313);
			((Enemy) enemies.elementAt(20)).put(398, 286);//上面是中校
			((Enemy) enemies.elementAt(21)).put(300, 163);
			((Enemy) enemies.elementAt(22)).put(393, 196).setInBunker(true, 385, 156, 14, 23);//上面是上将
			((Enemy) enemies.elementAt(23)).put(455, 168).setInBunker(true, 449, 121, 80, 50);//这个是首相
			break;
		case 5:
			((Enemy) enemies.elementAt(0)).put(318, 355);
			((Enemy) enemies.elementAt(1)).put(268, 354);
			((Enemy) enemies.elementAt(2)).put(316, 297);
			((Enemy) enemies.elementAt(3)).put(362, 352);
			((Enemy) enemies.elementAt(4)).put(263, 292);
			((Enemy) enemies.elementAt(5)).put(364, 291);
			((Enemy) enemies.elementAt(6)).put(315, 230);
			((Enemy) enemies.elementAt(7)).put(266, 230);
			((Enemy) enemies.elementAt(8)).put(366, 228);//上面二等兵
//			((Enemy) enemies.elementAt(0)).put(318, 355).setInBunker(true, 293, 334, 55, 22);
//			((Enemy) enemies.elementAt(1)).put(268, 354).setInBunker(true, 248, 333, 39, 21);
//			((Enemy) enemies.elementAt(2)).put(316, 297).setInBunker(true, 294, 269, 35, 28);
//			((Enemy) enemies.elementAt(3)).put(362, 352).setInBunker(true, 345, 332, 37, 20);
//			((Enemy) enemies.elementAt(4)).put(263, 292).setInBunker(true, 248, 269, 39, 23);
//			((Enemy) enemies.elementAt(5)).put(364, 291).setInBunker(true, 345, 268, 38, 23);
//			((Enemy) enemies.elementAt(6)).put(215, 230).setInBunker(true, 290, 209, 49, 21);
//			((Enemy) enemies.elementAt(7)).put(266, 230).setInBunker(true, 248, 208, 36, 22);
//			((Enemy) enemies.elementAt(8)).put(366, 228).setInBunker(true, 347, 207, 38, 21);//上面二等兵
			((Enemy) enemies.elementAt(9)).put(317, 169).setInBunker(true, 293, 134, 48, 35);
			((Enemy) enemies.elementAt(10)).put(264, 164).setInBunker(true, 245, 134, 38, 30);
			((Enemy) enemies.elementAt(11)).put(367, 164).setInBunker(true, 347, 134, 39, 30);
			((Enemy) enemies.elementAt(12)).put(471, 281);
			((Enemy) enemies.elementAt(13)).put(50, 285);
			((Enemy) enemies.elementAt(14)).put(160, 280).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(15)).put(588, 280).setInBunker(true, 0, 0, 640, 262);
			((Enemy) enemies.elementAt(16)).put(497, 413).setInBunker(true, 0, 0, 640, 530);//上面是上尉
			((Enemy) enemies.elementAt(17)).put(463, 358).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(18)).put(155, 416).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(19)).put(176, 354).setInBunker(true, 0, 0, 640, 530);;//上面是中校
			((Enemy) enemies.elementAt(20)).put(563, 337);
			((Enemy) enemies.elementAt(21)).put(20, 390);
			((Enemy) enemies.elementAt(22)).put(610, 420);//上面是上将
			((Enemy) enemies.elementAt(23)).put(102, 317);//这个是首相
			((Enemy) enemies.elementAt(24)).put(428, 450);//这个是首相
			((Enemy) enemies.elementAt(25)).put(200, 450);//这个是首相
			break;
		case 6:
			((Enemy) enemies.elementAt(0)).put(513, 432);
			((Enemy) enemies.elementAt(1)).put(605, 463);
			((Enemy) enemies.elementAt(2)).put(10, 426);
			((Enemy) enemies.elementAt(3)).put(50, 217);
			((Enemy) enemies.elementAt(4)).put(260, 177);//上面二等兵
			((Enemy) enemies.elementAt(5)).put(330, 362).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(6)).put(637, 201).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(7)).put(185, 322).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(8)).put(220, 215).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(9)).put(123, 338).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(10)).put(457, 298).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(11)).put(270, 267);
			((Enemy) enemies.elementAt(12)).put(30, 265);
			((Enemy) enemies.elementAt(13)).put(584, 238).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(14)).put(619, 304).setInBunker(true, 0, 0, 640, 288);//上面是上尉
			((Enemy) enemies.elementAt(15)).put(565, 320).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(16)).put(62, 393).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(17)).put(425, 220).setInBunker(true, 0, 0, 640, 202);
			((Enemy) enemies.elementAt(18)).put(295, 230).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(19)).put(127, 216).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(20)).put(150, 365);//上面是中校
			((Enemy) enemies.elementAt(21)).put(367, 229).setInBunker(true, 0, 0, 640, 530);
			((Enemy) enemies.elementAt(22)).put(492, 243);
			((Enemy) enemies.elementAt(23)).put(345, 262);
			((Enemy) enemies.elementAt(24)).put(80, 300);//上面是上将
			((Enemy) enemies.elementAt(25)).put(500, 198);//这个是首相
			((Enemy) enemies.elementAt(26)).put(550, 397).setInBunker(true, 0, 0, 640, 530);//这个是首相
			((Enemy) enemies.elementAt(27)).put(440, 352);//这个是首相
			((Enemy) enemies.elementAt(28)).put(255, 326);//这个是首相
			((Enemy) enemies.elementAt(29)).put(185, 262);//这个是首相
			break;
		}
	}
	
	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		/*switch(level) {
		case 1:
		case 2:
		case 3:
			g.drawImage(img_bunker, 0, 0, 0);
			break;
		case 4:
			g.drawImage(img_bunker, 0, 450, Graphics.BOTTOM | Graphics.LEFT);
			break;
		case 5:
			g.drawImage(img_bunker, 0, 470, Graphics.BOTTOM | Graphics.LEFT);
			break;
		case 6:
			g.drawImage(img_bunker, 0, 460, Graphics.BOTTOM | Graphics.LEFT);
			break;
		}*/
		
		if(!alive) {
			if(showGameOverDialog) {
				showGameOverDialog(g);
				return;
			}
			g.drawImage(img_gameOver, gameOverX, 200, 0);
			return;
		}
		
		if(alive && !complete) {
			
			for(int i=0; i<allEnemy; i++) {//敌人
				((Enemy) enemies.elementAt(i)).show(g);
			}
			g.setClip(0, 0, 640, 477);
			if(covered || platformY < 477) {
				g.drawImage(img_platform, 0, platformY , 0);//遮蔽物
			}else g.drawImage(img_sight, sightX, sightY, Graphics.VCENTER | Graphics.HCENTER);//瞄准镜
			
			if(CanvasControl.highboom == 1 && shootting) {
				g.drawImage(ima_fire[fireIndex], gunX, gunY - 10, Graphics.VCENTER | Graphics.HCENTER);
			}
			
			gun.show(g, gunX, gunY);//枪
			g.setClip(0, 0, 640, 530);
			
			g.drawImage(img_bulletBar, C.WIDTH, 478, Graphics.RIGHT | Graphics.BOTTOM);//子弹背景
			if(CanvasControl.highboom == 1) {
				gun.showBullets(g, img_redBullet, img_number_bullets);//子弹
			} else gun.showBullets(g, img_bullet, img_number_bullets);//子弹
			
		} else if(complete) {//过关了，显示战绩
			g.drawImage(img_score, C.WIDTH / 2, 400, Graphics.BOTTOM | Graphics.HCENTER);
			for(int i=0; i<scoreIndex; i++) {
				if(i<5) C.drawString(g, img_number, CanvasControl.enemyQuantity[level - 1][i] + "", "0123456789/:%", 320, 110 + i * 30, 15, 21, 0, 0, 0);
				else if(i<6)
				C.drawString(g, img_number, (int)((shootTimes - hitTimes) / shootTimes * 100) + "%", "0123456789/:%", 320, 110 + i * 30, 15, 21, 0, 0, 0);
				else if(i<7)
				C.drawString(g, img_number, useTime, "0123456789/:%", 318, 110 + i * 30, 15, 21, 0, 0, 0);
				else if(i<8)
				C.drawString(g, img_number, score + "", "0123456789/:%", 318, 110 + i * 30, 15, 21, 0, 0, 0);
			}
			return;
		}
		
		g.drawImage(img_topBar, C.WIDTH / 2, 0, Graphics.TOP | Graphics.HCENTER);//上边的显示栏
		g.drawImage(img_bottomBar, 0, 530, Graphics.BOTTOM | Graphics.LEFT);//底部的显示栏

		if(CanvasControl.stealth == 0) {//没有隐身
			g.drawImage(img_key_4, 194, 528, Graphics.BOTTOM | Graphics.HCENTER);
		} else {//隐身倒计时
			C.drawString(g, img_number_countdown, CanvasControl.stealthRemain 	+ "", "0123456789", 187, 492, 25, 36, 0, 0, 0);
		}
		
		if(CanvasControl.autoAim == 0) {//没有自动瞄准
			g.drawImage(img_key_5, 320, 528, Graphics.BOTTOM | Graphics.HCENTER);
		} else {//自动瞄准倒计时
			C.drawString(g, img_number_countdown, CanvasControl.autoAimRemain + "", "0123456789", 310, 492, 25, 36, 0, 0, 0);
		}
		
//C.out("数字图片的宽度----" + img_number.getWidth());
//C.out("数字图片的高度----" + img_number.getHeight());
		C.drawString(g, img_number, enemyAmount + "", "0123456789/:%", 130, 10, 15, 21, 0, 0, 0);//敌人数量
		C.drawString(g, img_number, hitRate, "0123456789/:%", 545, 10, 15, 21, 0, 0, 0);//命中率
		C.drawString(g, img_number, useTime, "0123456789/:%", 318, 10, 15, 21, 0, 0, 0);//使用时间
		
		if(gun.getMagazineBullets() <= 0 && !gun.isResetting() && CanvasControl.autocb == 0 && !covered) {//弹匣子弹用光了,按2换子弹
			g.drawImage(img_fillMagazine, sightX, sightY + 30, Graphics.HCENTER | Graphics.BOTTOM);
//			g.setColor(255, 255, 255);
//			g.drawString("按‘2’换子弹", sightX, sightY + 30, Graphics.HCENTER | Graphics.BOTTOM);
		}
		
		if(beDiscover) {
			if(second % 2 == 0) g.drawImage(img_beHitted, 0, 0, 0);
			if(discoverEnemy != null) g.drawImage(img_sigh_small, discoverEnemy.getX() + 3, discoverEnemy.getY() - 44, Graphics.LEFT | Graphics.TOP);//敌人头上的感叹号
			g.drawImage(img_sigh, sightX - 80, sightY + 55, Graphics.HCENTER | Graphics.VCENTER);//感叹号
			g.drawImage(img_beDiscover, sightX, sightY + 40, Graphics.HCENTER | Graphics.VCENTER);//你呗发现咯
			g.drawImage(img_coverPress_1, sightX + 7, /*sightY + 65*/ coverY, Graphics.HCENTER | Graphics.VCENTER);//你呗发现咯
		}
		
		if(CanvasControl.firstTimes == 1) {//第一次教学篇
			g.setColor(255, 0, 0);
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			g.drawString(guide[step - 1], 320, 250, Graphics.BOTTOM | Graphics.HCENTER);
		}
	}

	public void showGameOverDialog(Graphics g) {
		g.drawImage(img_dialogBack, C.WIDTH / 2, gameOverDialogY, Graphics.BOTTOM | Graphics.HCENTER);
		g.drawImage(img_gameOverselect, C.WIDTH / 2, gameOverDialogY - 145 + gameOverSelectIndex * 57, Graphics.BOTTOM | Graphics.HCENTER);
		g.drawImage(img_arrow, arrowX, gameOverDialogY - 172 + gameOverSelectIndex * 57, Graphics.VCENTER | Graphics.RIGHT);
		g.drawImage(img_gameOverButton, C.WIDTH / 2, gameOverDialogY - 40, Graphics.BOTTOM | Graphics.HCENTER);
	}
	
	public void loadResource (){
		try {
			img_back = Image.createImage("/game/back_" + level + ".png");
//			img_bunker = Image.createImage("/game/bunker_" + level + ".png");
			img_topBar = Image.createImage("/game/topBar.png");
			img_bottomBar = Image.createImage("/game/bottomBar.png");
			img_sight = Image.createImage("/game/sight.png");
			img_bulletBar = Image.createImage("/game/bulletBar.png");
			img_bullet = Image.createImage("/game/bullet.png");
			img_redBullet = Image.createImage("/game/redBullet.png");
			img_platform = Image.createImage("/game/platform.png");
			img_number = Image.createImage("/game/number.png");
			img_number_countdown = Image.createImage("/game/number_countdown.png");
			img_key_4 = Image.createImage("/game/key_4.png");
			img_key_5 = Image.createImage("/game/key_5.png");
			img_beDiscover = Image.createImage("/game/beDiscover.png");
			img_coverPress_1 = Image.createImage("/game/coverPress_1.png");
			img_sigh = Image.createImage("/game/sigh.png");
			img_sigh_small = Image.createImage("/game/sigh_small.png");
			img_arrow = Image.createImage("/button/arrow.png");
			img_fillMagazine = Image.createImage("/game/fillMagazine.png");
			img_number_bullets = Image.createImage("/game/number_bullets.png");
			img_beHitted = Image.createImage("/game/beHitted.png");
			
			ima_fire = new Image[3];
			Image img_fire = Image.createImage("/game/fire.png");
			int w = img_fire.getWidth();
			int h = img_fire.getHeight();
			for(int i=0; i<2; i++) {
				ima_fire[i + 1] = Image.createImage(img_fire, i * w / 2, 0, w / 2, h, 0);
			}
			ima_fire[0] = ima_fire[1];
			
			if(showGameOverDialog) {
				loadGameOverDialogRes();
			} else if(ima_aliveEnemy == null) loadEnemyImage();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		gun.loadResource(gun.getType());

		willDieStartTime = System.currentTimeMillis();
		
	}

	/**
	 * 加载敌人的图片资源
	 */
	public void loadEnemyImage() {
		try {
			Image alive0 = Image.createImage("/game/aliveEnemy_" + 0 + ".png");
			Image alive1 = Image.createImage("/game/aliveEnemy_" + 1 + ".png");
			Image alive2 = Image.createImage("/game/aliveEnemy_" + 2 + ".png");
			Image alive3 = Image.createImage("/game/aliveEnemy_" + 3 + ".png");
			Image alive4 = Image.createImage("/game/aliveEnemy_" + 4 + ".png");
//				Image alive1 = Image.createImage("/game/aliveEnemy_" + 1 + ".png");
//C.out("敌人图片高度-----" + alive1.getHeight());
//C.out("敌人图片宽度-----" + alive1.getWidth() / 3);
			ima_aliveEnemy = new Image[5][3];
			
			for(int i=0; i<3; i++) {
				ima_aliveEnemy[0][i] = Image.createImage(alive0, 0 + i * (alive0.getWidth() / 3), 0, alive0.getWidth() / 3, alive0.getHeight(), 0);
				ima_aliveEnemy[1][i] = Image.createImage(alive1, 0 + i * (alive1.getWidth() / 3), 0, alive1.getWidth() / 3, alive1.getHeight(), 0);
				ima_aliveEnemy[2][i] = Image.createImage(alive2, 0 + i * (alive2.getWidth() / 3), 0, alive2.getWidth() / 3, alive2.getHeight(), 0);
				ima_aliveEnemy[3][i] = alive3;
				ima_aliveEnemy[4][i] = alive4;
//					ima_aliveEnemy[1][i] = Image.createImage(alive1, 0 + i * (alive1.getWidth() / 3), 0, alive1.getWidth() / 3, alive1.getHeight(), 0);
			}
			
			
			Image dead0 = Image.createImage("/game/deadEnemy_" + 0 + ".png");
			Image dead1 = Image.createImage("/game/deadEnemy_" + 1 + ".png");
			Image dead2 = Image.createImage("/game/deadEnemy_" + 2 + ".png");
			Image dead3 = Image.createImage("/game/deadEnemy_" + 3 + ".png");
			Image dead4 = Image.createImage("/game/deadEnemy_" + 4 + ".png");
			
//				int dead_w = dead1.getWidth();
//				int dead_h = dead1.getHeight();
			ima_deadEnemy = new Image[5][2];
			for(int i=0; i<2; i++) {
				ima_deadEnemy[0][i] = Image.createImage(dead0, 0 + i * (dead0.getWidth() / 2), 0, dead0.getWidth() / 2, dead0.getHeight(), 0);
				ima_deadEnemy[1][i] = Image.createImage(dead1, 0 + i * (dead1.getWidth() / 2), 0, dead1.getWidth() / 2, dead1.getHeight(), 0);
				ima_deadEnemy[2][i] = Image.createImage(dead2, 0 + i * (dead2.getWidth() / 2), 0, dead2.getWidth() / 2, dead2.getHeight(), 0);
				ima_deadEnemy[3][i] = Image.createImage(dead3, 0 + i * (dead3.getWidth() / 2), 0, dead3.getWidth() / 2, dead3.getHeight(), 0);
				ima_deadEnemy[4][i] = Image.createImage(dead4, 0 + i * (dead4.getWidth() / 2), 0, dead4.getWidth() / 2, dead4.getHeight(), 0);
			}
			if(gun.getType() == 5) {
				Image dead = Image.createImage("/game/deadEnemy_barret.png");
				ima_deadEnemy[0][1] = Image.createImage(dead, 0 + 1 * (dead.getWidth() / 2), 0, dead.getWidth() / 2, dead.getHeight(), 0);
				for(int j=1; j<5; j++) {
					ima_deadEnemy[j][1] = ima_deadEnemy[0][1];
				}
			} 
				
			
			
			
//				Image move0 = Image.createImage("/game/moveEnemy_" + 0 + ".png");
			Image move1 = Image.createImage("/game/moveEnemy_" + 1 + ".png");
			Image move2 = Image.createImage("/game/moveEnemy_" + 2 + ".png");
			Image move3 = Image.createImage("/game/moveEnemy_" + 3 + ".png");
			Image move4 = Image.createImage("/game/moveEnemy_" + 4 + ".png");
//				int move_w = move1.getWidth();
//				int move_h = move1.getHeight();
			ima_moveEnemy = new Image[5][10];
			for(int i=0; i<10; i++) {
				if(i == 0) {
//						ima_moveEnemy[0][i] = Image.createImage(move0, 0 + 3 * (move0.getWidth() / 4), 0, move0.getWidth() / 4, move0.getHeight(), 0);
					ima_moveEnemy[1][i] = Image.createImage(move1, 0 + 3 * (move1.getWidth() / 4), 0, move1.getWidth() / 4, move1.getHeight(), 0);
					ima_moveEnemy[2][i] = Image.createImage(move2, 0 + 3 * (move2.getWidth() / 4), 0, move2.getWidth() / 4, move2.getHeight(), 0);
					ima_moveEnemy[3][i] = Image.createImage(move3, 0 + 3 * (move3.getWidth() / 4), 0, move3.getWidth() / 4, move3.getHeight(), 0);
					ima_moveEnemy[4][i] = Image.createImage(move4, 0 + 3 * (move4.getWidth() / 4), 0, move4.getWidth() / 4, move4.getHeight(), 0);
				}else if(i < 5) {
//						ima_moveEnemy[0][i] = Image.createImage(move0, 0 + (i - 1) * (move0.getWidth() / 4), 0, move0.getWidth() / 4, move0.getHeight(), 0);
					ima_moveEnemy[1][i] = Image.createImage(move1, 0 + (i - 1) * (move1.getWidth() / 4), 0, move1.getWidth() / 4, move1.getHeight(), 0);
					ima_moveEnemy[2][i] = Image.createImage(move2, 0 + (i - 1) * (move2.getWidth() / 4), 0, move2.getWidth() / 4, move2.getHeight(), 0);
					ima_moveEnemy[3][i] = Image.createImage(move3, 0 + (i - 1) * (move3.getWidth() / 4), 0, move3.getWidth() / 4, move3.getHeight(), 0);
					ima_moveEnemy[4][i] = Image.createImage(move4, 0 + (i - 1) * (move4.getWidth() / 4), 0, move4.getWidth() / 4, move4.getHeight(), 0);
				}else {
//						ima_moveEnemy[0][i] = Image.createImage(ima_moveEnemy[0][i - 5], 0, 0, move0.getWidth() / 4, move0.getHeight(), Sprite.TRANS_MIRROR);
					ima_moveEnemy[1][i] = Image.createImage(ima_moveEnemy[1][i - 5], 0, 0, move1.getWidth() / 4, move1.getHeight(), Sprite.TRANS_MIRROR);
					ima_moveEnemy[2][i] = Image.createImage(ima_moveEnemy[2][i - 5], 0, 0, move2.getWidth() / 4, move2.getHeight(), Sprite.TRANS_MIRROR);
					ima_moveEnemy[3][i] = Image.createImage(ima_moveEnemy[3][i - 5], 0, 0, move3.getWidth() / 4, move3.getHeight(), Sprite.TRANS_MIRROR);
					ima_moveEnemy[4][i] = Image.createImage(ima_moveEnemy[4][i - 5], 0, 0, move4.getWidth() / 4, move4.getHeight(), Sprite.TRANS_MIRROR);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadGameOverDialogRes() {
		gameOverSelectIndex = 0;
		
		try {
			img_dialogBack = Image.createImage("/dialog/back.png");
			img_gameOverButton = Image.createImage("/game/gameOverButton.png");
			img_gameOverselect = Image.createImage("/game/gameOverSelect.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeGameOverDialogRes() {
		if(img_dialogBack != null) {
			img_dialogBack = null;
			img_gameOverButton = null;
			img_gameOverselect = null;
		}
	}
	
	public void removeResource() {
//		img_back = null;
		img_topBar = null;
		img_bottomBar = null;
//		img_bunker = null;
		img_sight = null;
		img_bulletBar = null;
		img_bullet = null;
		img_redBullet = null;
		img_platform = null;
		img_number = null;
		img_number_countdown = null;
		img_key_4 = null;
		img_key_5 = null;
		img_beDiscover = null;
		img_sigh = null;
		img_sigh_small = null;
		img_fillMagazine = null;
		img_number_bullets = null;
		img_coverPress_1 = null;
		img_beHitted = null;
		
//		for(int i=0; i<2; i++) {
//		}
//		ima_fire[0] = null;
//		ima_fire[1] = null;
//		ima_fire[2] = null;
		ima_fire = null;
		
		gun.removeResource();
		
		removeGameOverDialogRes();
		
		if(img_score != null) img_score = null;
		
		System.gc();
	}

	public void removeBackImage() {
		img_back = null;
//		img_bunker = null;
	}
	
	public void removeEnemyImage() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<2; j++){
				ima_deadEnemy[i][j] = null;
			}
			for(int j=0; j<3; j++){
				ima_aliveEnemy[i][j] = null;
			}
			
			for(int j=0; j<10; j++){
				ima_moveEnemy[i][j] = null;
			}
		}
		ima_aliveEnemy = null;
		ima_deadEnemy = null;
		ima_moveEnemy = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		if(CanvasControl.firstTimes == 1) {//教学篇按键事件
			if(keyCode == C.KEY_0 || keyCode == C.KEY_BACK || keyCode == C.KEY_BACK_ZX) {
				canvasControl.setView(new Dialog(canvasControl, 5));
				canvasControl.setGoBackView(this);
				this.removeBackImage();
				this.removeResource();
				return;
			}
			
			switch(step) {
			case 1://移动瞄准镜
//				sightMoveGrid(keyCode);
//				break;
				
				switch(keyCode) {
				case C.KEY_LEFT :
					keyLeftIsDown = true;
					keyDownIsDown = false;
					keyRightIsDown = false;
					keyUpIsDown = false;
					break;
				case C.KEY_DOWN :
					keyLeftIsDown = false;
					keyDownIsDown = true;
					keyRightIsDown = false;
					keyUpIsDown = false;
					break;
				case C.KEY_RIGHT :
					keyLeftIsDown = false;
					keyDownIsDown = false;
					keyRightIsDown = true;
					keyUpIsDown = false;
					break;
				case C.KEY_UP :
					keyLeftIsDown = false;
					keyDownIsDown = false;
					keyRightIsDown = false;
					keyUpIsDown = true;
					break;
				}
				break;
			case 2://开枪
				if(keyCode == C.KEY_FIRE) {
					shoot(canvasControl.audio);
					beDiscover = true;
					enemyDiscover();
					step = 3;
				}
				break;
			case 3://按‘1’隐藏
				if(keyCode == C.KEY_1) {
					covered = true;
					beDiscover = false;
					step = 4;
				}
				break;
			case 4://按‘2’换子弹
				if(keyCode == C.KEY_2) {
					fillMagazine();
					step = 5;
				}
				break;
			case 5://使用高爆子弹
				if(keyCode == C.KEY_3) {
					CanvasControl.highboom = 1;
					step = 6;
				}
				break;
			case 6://使用隐身衣
				if(keyCode == C.KEY_4) {
					CanvasControl.stealth = 1;
					CanvasControl.stealthRemain = 20;
					step = 7;
				}
				break;
			case 7://使用自动瞄准
				if(keyCode == C.KEY_5) {
					CanvasControl.autoAim = 1;
					CanvasControl.autoAimRemain = 30;
					step = 8;
				}
				break;
			case 8://使用自动换子弹
				if(keyCode == C.KEY_6) {
					CanvasControl.autocb = 1;
					step = 9;
				}
				break;
			}
		} else {//正常游戏按键事件
			switch(keyCode) {
			case C.KEY_LEFT :
				keyLeftIsDown = true;
				keyDownIsDown = false;
				keyRightIsDown = false;
				keyUpIsDown = false;
				if(lastKeyCode != keyCode) 
					sightMoveSpeed = 1;
				break;
			case C.KEY_DOWN :
				if(showGameOverDialog) {
					if(gameOverSelectIndex < 2) {
						canvasControl.audio.toPlay(6);
						gameOverSelectIndex ++;
					}
					return;
				}
				keyLeftIsDown = false;
				keyDownIsDown = true;
				keyRightIsDown = false;
				keyUpIsDown = false;
				if(lastKeyCode != keyCode) 
					sightMoveSpeed = 1;
				break;
			case C.KEY_RIGHT :
				keyLeftIsDown = false;
				keyDownIsDown = false;
				keyRightIsDown = true;
				keyUpIsDown = false;
				if(lastKeyCode != keyCode) 
					sightMoveSpeed = 1;
				break;
			case C.KEY_UP :
				if(showGameOverDialog) {
					if(gameOverSelectIndex > 0) {
						canvasControl.audio.toPlay(6);
						gameOverSelectIndex --;
					}
					return;
				}
				keyLeftIsDown = false;
				keyDownIsDown = false;
				keyRightIsDown = false;
				keyUpIsDown = true;
				if(lastKeyCode != keyCode) 
					sightMoveSpeed = 1;
				break;
			case C.KEY_1 :
				if(covered) covered = false;
				else covered = true;
//				willBeDiscover();
				break;
			case C.KEY_FIRE:
				if(complete) {
//					canvasControl.setView(new Home(canvasControl));
					if(level < 6) {
						canvasControl.setView(new Explanation(canvasControl, CanvasControl.level));
						this.removeBackImage();
						this.removeResource();
					} else {
						CanvasControl.level = 1;
						new ServerIptv(canvasControl).doGetRank();
						canvasControl.setView(new Ranking(canvasControl));
						this.removeBackImage();
						this.removeResource();
					}
					return;
				}
				if(showGameOverDialog) {
					canvasControl.audio.toPlay(6);
					switch(gameOverSelectIndex) {
					case 0://富二代换大炮
						canvasControl.setView(new Arsenal(canvasControl));
						this.removeBackImage();
						this.removeResource();
						break;
					case 1://高帅富买云南白药
						canvasControl.setView(new Dialog(canvasControl, 11));
						canvasControl.setGoBackView(this);
//						showGameOverDialog = false;
//						alive = true;
//						beDiscover = false;
//						gameOverDialogStartTime = 0;
//						willBeDiscoverStartTime = System.currentTimeMillis();
//						gameOverX = 660;
						break;
					case 2://屌丝退出游戏
						canvasControl.setView(new Dialog(canvasControl, 12));
						canvasControl.setGoBackView(this);
						this.removeBackImage();
						this.removeResource();
						break;
					}
					return;
				}
				if(alive && !covered && gun.getMagazineBullets() > 0 && !afterShoot) {
					shoot(canvasControl.audio);
				} else if(gun.getBullets() <= 0) {//弹出购买子弹对话框
					canvasControl.setView(new Dialog(canvasControl, 13));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			case C.KEY_2 :
				if(gun.getBullets() > 0 && gun.getMagazineBullets() < 5) fillMagazine();
				break;
			case C.KEY_3 :
				if(alive && !complete) {
					canvasControl.audio.toPlay(6);
					canvasControl.setView(new Dialog(canvasControl, 6));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			case C.KEY_4 :
				if(alive && !complete) {
					canvasControl.audio.toPlay(6);
					canvasControl.setView(new Dialog(canvasControl, 7));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			case C.KEY_5 :
				if(alive && !complete) {
					canvasControl.audio.toPlay(6);
					canvasControl.setView(new Dialog(canvasControl, 8));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			case C.KEY_6 :
				if(alive && !complete) {
					canvasControl.audio.toPlay(6);
					canvasControl.setView(new Dialog(canvasControl, 9));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			case C.KEY_9 :
				if(alive && !complete) {
					canvasControl.audio.toPlay(6);
					canvasControl.setView(new Help(canvasControl, 1));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			case C.KEY_0 :
			case C.KEY_BACK:
			case C.KEY_BACK_ZX:
				if(alive && !complete) {
					canvasControl.audio.toPlay(6);
					canvasControl.setView(new Dialog(canvasControl, 1));
					canvasControl.setGoBackView(this);
					this.removeBackImage();
					this.removeResource();
				}
				break;
			}
			
			if(keyCode != C.KEY_UP && keyCode != C.KEY_DOWN && keyCode != C.KEY_LEFT && keyCode != C.KEY_RIGHT) {
				keyLeftIsDown = false;
				keyDownIsDown = false;
				keyRightIsDown = false;
				keyUpIsDown = false;
			}
			
			lastKeyCode = keyCode;
//			
//			sightMoveGrid(keyCode);
		}
	}

	public void keyReleased(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT :
			keyLeftIsDown = false;
			break;
		case C.KEY_DOWN :
			keyDownIsDown = false;
			break;
		case C.KEY_RIGHT :
			keyRightIsDown = false;
			break;
		case C.KEY_UP :
			keyUpIsDown = false;
			break;
		}
	}

	public void keyRepeated(int keyCode) {

	}

	/**
	 * 用于江苏中兴盒子，移动一小格。
	 */
	public void sightMoveGrid(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT:
			sightX -= sightMoveOffer;
			gunX -= sightMoveOffer;
			break;
		case C.KEY_DOWN:
			sightY += sightMoveOffer;
			gunY = sightY / 3 + 318;
			break;
		case C.KEY_RIGHT:
			sightX += sightMoveOffer;
			gunX += sightMoveOffer;
			break;
		case C.KEY_UP:
			sightY -= sightMoveOffer;
			gunY = sightY / 3 + 318;
			break;
		}
	}
	
	/**
	 * 键的长按处理
	 */
	public void doKeyIsDown() {
		if(keyLeftIsDown) {
			if(sightX > 0) {
				if(sightMoveSpeed < 60) sightMoveSpeed += 2;
				sightX -= sightMoveSpeed;
				gunX -= sightMoveSpeed;
			}
		}
		if(keyDownIsDown) {
			if(sightY < 477) {
				if(sightMoveSpeed < 60) sightMoveSpeed += 2;
				sightY += sightMoveSpeed;
//				gunY += sightMoveSpeed / 3;
				gunY = sightY / 3 + 318;
			}
		}
		if(keyRightIsDown) {
			if(sightX < 640) {
				if(sightMoveSpeed < 60) sightMoveSpeed += 2;
				sightX += sightMoveSpeed;
				gunX += sightMoveSpeed;
			}
		}
		if(keyUpIsDown) {
			if(sightY > 0) {
				if(sightMoveSpeed < 60) sightMoveSpeed += 2;
				sightY -= sightMoveSpeed;
//				gunY -= sightMoveSpeed / 3;
				gunY = sightY / 3 + 318;
			}
		}
		
		if(!keyLeftIsDown && !keyDownIsDown && !keyRightIsDown && !keyUpIsDown) sightMoveSpeed = 1;
	}
	
	/**
	 * 隐藏的时候的逻辑处理
	 */
	public void doCovered() {
		if(covered && platformY > 130) {
			platformY -= 60;
		}else if(!covered && platformY < 477) {
			platformY += 60;
		}
	}
	
	/**
	 * 射击后瞄准镜恢复的处理逻辑
	 */
	public void doAfterShoot() {
		if(afterShoot) {
			if(shootting) {
				fireIndex ++;
				if(fireIndex >= 3) {
					shootting = false;
					fireIndex = 0;
				}
			}
			if(restoreExtend > 0) {
				int offer = gun.getFiringRate();
				
				sightY += offer;
				gunX -= offer;
				gunY --;
//				gunY = sightY / 3 + 318;
				restoreExtend -= offer;
			}else {
				afterShoot = false;
				gunX = sightX + 30;
				gunY = sightY / 3 + 318;
			}
		}
	}
	
	/**
	 * 射击。
	 */
	public void shoot(AudioPlay player) {
		gun.shoot(player);
		shootTimes ++;
		
		for(int i=0; i<allEnemy; i++) {
			Enemy enemy = (Enemy) enemies.elementAt(i);
			if(hit(enemy)) {
				hitTimes ++;
				int attackPower = 0;
				if(CanvasControl.highboom == 1) attackPower = gun.getAttackPower() * 11 / 10;
				else attackPower = gun.getAttackPower();
				enemy.setHealth(enemy.getHealth() - attackPower);
				if(enemy.getHealth() <= 0) {
					enemy.setAlive(false);
					enemyAmount --;
					
					if(enemyAmount <= 0) {//牛逼啊，都打死了。
						doAtComplete();
					}
				}
				if(beDiscover && !discoverEnemy.isAlive()) {//打死了发现你的敌人
					beDiscover = false;
					willBeDiscoverStartTime = System.currentTimeMillis();
				}
				break;
			}
		}
		
		hitRate = (int)((hitTimes / shootTimes)* 100) + "%";
		restoreExtend = gun.getRecoil() * 10;
		
		if(gun.getBullets() > 0 && gun.getMagazineBullets() <= 0 && CanvasControl.autocb == 1) {
			fillMagazine();//自动换子弹
		} else if(gun.getBullets() <= 0 && !complete) {//弹出购买子弹对话框
			canvasControl.setView(new Dialog(canvasControl, 13));
			canvasControl.setGoBackView(this);
			this.removeBackImage();
			this.removeResource();
		}
		
		shootting = true;
		afterShoot = true;
		sightY -= restoreExtend;
		
		gunX += restoreExtend;
		gunY += gun.getRecoil() * 2;
	}
	
	/**
	 * 祝贺你，过关了。
	 */
	private void doAtComplete() {
		score = 6000 / ((int)((shootTimes - hitTimes) / shootTimes + 1) * (minute * 60 + second));
//		score = 10 + ((int)(hitTimes / shootTimes) + 1) * (1200 / (minute * 60 + second));
		uploadScore(score);
		
		CanvasControl.level ++ ;
		
		completeTime = System.currentTimeMillis();
		
		try {//加载过关图片
//			this.removeResource();
			img_score = Image.createImage("/game/score.png");
			img_number = Image.createImage("/game/number.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		complete = true;
	}

	
	/**
	 * 击中敌人方法，也就是碰撞检测方法。
	 * @param enemy
	 * @return
	 */
	public boolean hit(Enemy enemy) {
		if(!enemy.isAlive()) {
			return false;
		} else {
			if(enemy.isInBunker()) {//在碉堡里的
				if(enemy.getRanks() == 0) {//不动的二等兵
					if(sightX > enemy.getX() - 12 && sightX < enemy.getX() + 6 &&
							sightY < enemy.getY() - 8 && sightY > enemy.getY() - 38 &&
							sightX > enemy.getBunkerX() && sightX < enemy.getBunkerX() + enemy.getBunkerW() &&
							sightY > enemy.getBunkerY() && sightY < enemy.getBunkerY() + enemy.getBunkerH()) return true;
					else return false;
				} else {//会动的
					if(sightX > enemy.getX() - 8 && sightX < enemy.getX() + 6 &&
							sightY < enemy.getY() - 8 && sightY > enemy.getY() - 38 &&
							sightX > enemy.getBunkerX() && sightX < enemy.getBunkerX() + enemy.getBunkerW() &&
							sightY > enemy.getBunkerY() && sightY < enemy.getBunkerY() + enemy.getBunkerH()) return true;
					else return false;
				}
			} else {
				if(enemy.getRanks() == 0) {//不动的二等兵
					if(sightX > enemy.getX() - 12 && sightX < enemy.getX() + 6 &&
							sightY < enemy.getY() - 10 && sightY > enemy.getY() - 38) return true;
					else return false;
				} else {//会动的
					if(sightX > enemy.getX() - 8 && sightX < enemy.getX() + 6 &&
							sightY < enemy.getY() - 8 && sightY > enemy.getY() - 38) return true;
					else return false;
				}
			}
		}
	}

	/**
	 * 换子弹
	 */
	public void fillMagazine(){
		gun.fillMagazine();
	}
	
	public void logic() {
		if(CanvasControl.firstTimes == 1) {//教学篇
			switch(step) {
			case 1:
				doKeyIsDown();
				for(int i=0; i<allEnemy; i++) {
					Enemy enemy = (Enemy) enemies.elementAt(i);
					if(hit(enemy)) {
						step = 2;
						return;
					}
				}
				break;
			case 9:
				canvasControl.setView(new Dialog(canvasControl, 5));
				canvasControl.setGoBackView(this);
				this.removeBackImage();
				this.removeResource();
				break;
			}
			
			long thisTime = System.currentTimeMillis();
			if(thisTime - missionStartTime > 1000) {//关卡用时
				if(CanvasControl.stealth == 1) {//隐身时间倒计时
					CanvasControl.stealthRemain --;
					if(CanvasControl.stealthRemain <= 0) {
						CanvasControl.stealth = 0;
					}
				}
				
				if(CanvasControl.autoAim == 1) {//自动瞄准倒计时
					CanvasControl.autoAimRemain --;
					if(CanvasControl.autoAimRemain <= 0) {
						CanvasControl.autoAim = 0;
					}
				}
				
				missionStartTime = thisTime;
			}
			
			gun.logic();
			
			doCovered();
			doAfterShoot();
			
			if(beDiscover) {
				if(coverY == sightY + 65) coverY = sightY + 69;
				else coverY = sightY + 65;
			}
			
			for(int i=0; i<allEnemy; i++) {
				((Enemy) enemies.elementAt(i)).move();
			}
			
			canvasControl.repaint();
			canvasControl.serviceRepaints();
			return;
		}
		
		if(CanvasControl.relive == 1) {//买完复活
			relive();
			return;
		}
		if(CanvasControl.buyBullets == 1) {//买完子弹
			resetGun();
			return;
		}
		
		if(showGameOverDialog){//显示死亡后对话框
			if(gameOverDialogY < 400) gameOverDialogY += 50;
			
			if(arrowX == 225) arrowX = 221;
			else arrowX = 225;
			
			canvasControl.repaint();
			return;
		}
		
		gun.logic();
		timeIsGoing();
		
		if(complete) {
			return;
		}
		
		if(!alive) {
			if(gameOverX > 10) {
				gameOverX -= 50;
				if(gameOverX == 60){//显示死后对话框
					gameOverDialogStartTime = System.currentTimeMillis();
				}
			}
			canvasControl.repaint();
			return;
		} 
		
//		if(CanvasControl.stealth == 1) {
//			if(willDieTimerTask != null) willDieTimerTask.cancel();
//			if(willBeDiscoverTimerTask != null) willBeDiscoverTimerTask.cancel();
//		} else {
//			willBeDiscoverTimerTask = new BeDiscoverTimerTask();
//			willBeDiscoverTimer.schedule(willBeDiscoverTimerTask, (C.R.nextInt(4) + 4) * 1000);
//		}
		
		if(CanvasControl.autoAim == 0) {
			doKeyIsDown();
		} else if(!afterShoot){
			autoAim();
		}
		doCovered();
		doAfterShoot();
		
		if(alive) {
			if(beDiscover) {
				if(coverY == sightY + 65) coverY = sightY + 69;
				else coverY = sightY + 65;
			}
		}
		
		for(int i=0; i<allEnemy; i++) {
			((Enemy) enemies.elementAt(i)).move();
		}
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

	/**
	 * 重新装填所有弹匣
	 */
	private void resetGun() {
		gun.setBullets(gun.getMagazineCapacity());
		gun.fillMagazine();
		CanvasControl.buyBullets = 0;
	}
	
	/**
	 * 随机取一个活的敌人发现你。
	 */
	private void enemyDiscover() {
		int discoverIndex = C.R.nextInt(allEnemy);
		discoverEnemy = (Enemy)enemies.elementAt(discoverIndex);
		if(!discoverEnemy.isAlive()) {
			enemyDiscover();
		}
	}

	/**
	 * 复活
	 */
	private void relive() {
		showGameOverDialog = false;
		alive = true;
		beDiscover = false;
		gameOverDialogStartTime = 0;
		willBeDiscoverStartTime = System.currentTimeMillis();
		gameOverX = 660;
		
		gameOverDialogY = 0;
		
		CanvasControl.relive = 0;
	}

	/**
	 * 自动瞄准
	 */
	private void autoAim() {
		Enemy enemy;
		for(int i=0; i<allEnemy; i++) {
			enemy = (Enemy) enemies.elementAt(i);
			if(!enemy.isAlive()) continue;
			sightX = enemy.getX();
			gunX = sightX + 30;
			sightY = enemy.getY() - 25;
			gunY = sightY / 3 + 318;
			break;
		}
	}
	
	/**
	 * 上传积分
	 */
	public void uploadScore(int score) {
		switch(level) {
		case 1:
			CanvasControl.level_1_score = score;
			break;
		case 2:
			CanvasControl.level_2_score = score;
			break;
		case 3:
			CanvasControl.level_3_score = score;
			break;
		case 4:
			CanvasControl.level_4_score = score;
			break;
		case 5:
			CanvasControl.level_5_score = score;
			break;
		case 6:
			CanvasControl.level_6_score = score;
			break;
		}
		
		new ServerIptv(canvasControl).doSendScore();
	}

	/**
	 * 时间控制方法
	 * 有被发现控制
	 * 死亡控制
	 * 各个倒计时和游戏时间
	 */
	private void timeIsGoing() {
		long thisTime = System.currentTimeMillis();
		
		if(complete) {
			if(thisTime - completeTime > 300) {
				if(scoreIndex < 8) {
					scoreIndex ++;
					canvasControl.repaint();//因为在此处logic里不再刷屏。
					completeTime = thisTime;
				}
			}
			return;
		}
		
		if(gameOverDialogStartTime != 0 && thisTime - gameOverDialogStartTime > 1000) {//gameover一秒后显示选择对话框
			loadGameOverDialogRes();
			img_gameOver = null;//移除gameover图片
//			this.removeResource();
			showGameOverDialog = true;
			return;
		}
		
		if(thisTime - missionStartTime > 1000) {//关卡用时
			addUseTime();
			
			if(CanvasControl.stealth == 1) {//隐身时间倒计时
				CanvasControl.stealthRemain --;
				if(CanvasControl.stealthRemain <= 0) {
					CanvasControl.stealth = 0;
				}
			}
			
			if(CanvasControl.autoAim == 1) {//自动瞄准倒计时
				CanvasControl.autoAimRemain --;
				if(CanvasControl.autoAimRemain <= 0) {
					CanvasControl.autoAim = 0;
				}
			}
			
			missionStartTime = thisTime;
		}
		
		//显示gameover
		if(beDiscover && thisTime - willDieStartTime >= dieAfterSecond * 1000 && CanvasControl.stealth == 0) {//你挂了
			if(img_gameOver == null) {
				try {
					img_gameOver =  Image.createImage("/game/gameOver.png");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			alive = false;
			CanvasControl.restEnemy = enemyAmount;
			
			this.removeResource();
			
			return;
		}
		
		if(covered || CanvasControl.stealth == 1) {//
			willBeDiscoverStartTime = thisTime;
			beDiscover = false;
		} else if(thisTime - willBeDiscoverStartTime > 16000) {//被发现
			beDiscover = true;
			if(thisTime - willDieStartTime > 12000) {
				enemyDiscover();
				willDieStartTime = thisTime;
			}
		}
	}

	public final Gun getGun() {
		return gun;
	}

	public final void setGun(Gun gun) {
		this.gun = gun;
	}

	/**
	 * 关卡使用时间计算方法。
	 */
	public void addUseTime() {
		if(!alive) return;
		second ++;
		if(second % 60 == 0) {
			minute ++;
			second = 0;
		}
		String secondStr = "";
		String minuteStr = "";
		if(second < 10) {
			secondStr = "0" + second;
		} else secondStr = second + "";
		if(minute < 10) {
			minuteStr = "0" + minute;
		} else minuteStr = "" + minute;
		
		useTime = minuteStr + ":" + secondStr;
	}
	
}
