package com.dave.worldWar.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.model.Bomb;
import com.dave.worldWar.model.Bomber;
import com.dave.worldWar.model.Bullet;
import com.dave.worldWar.model.Soldier;
import com.dave.worldWar.tool.C;

/**
 * @author Administrator 游戏界面
 */
public class Game implements Showable {

	public CanvasControl canvasControl;

	public Image img_map;// 地图
	public Image img_top_bar;// 顶部显示条
	public Image img_bottom_bar;// 底部显示条
	public Image img_me_bar;// 我的进度条
	public Image img_enemy_bar;// 敌方进度条
	public Image img_number;// 数字图片，用来显示第几关
	public Image img_cd;// 派兵cd蒙版
	public Image img_cd_line;// cd回复的line
	public Image img_friendly;// 我方标志
	public Image img_key_bar;// 按键显示条
	public Image img_go_right;//
	public Image img_go_left;//
	public Image img_icon_l;//
	public Image img_icon_r;//
	public Image img_lock;

	/**
	 * 血条图片；0：血条背景，1：绿色，2：黄色，3：红色。
	 */
	public Image[] a_img_blood_bar;

	public Image[] a_img_word_key;
	public Image[] a_img_head;

	/**
	 * 各士兵图片，士兵行走图片、士兵攻击图片、士兵跳下图片、士兵爬起图片、士兵死亡图片
	 */
	public Image[][] a_2_img_soldier;

	/**
	 * 各子弹图片
	 */
	public Image[] a_img_bullet;

	/**
	 * 地图左上角坐标
	 */
	public int x_map = -160, y_map;

	/**
	 * 开始中，显示对战动画。
	 */
	private boolean starting;

	private Image img_word_fight;

	/**
	 * 还需要生产的敌人波数。
	 */
	public static int remain_create;

	private Image[] a_img_boom_fight;

	public Image[] a_img_boom_0;// 步兵子弹爆炸效果
	public Image[] a_img_boom_1;// 炮兵子弹爆炸效果
	public Image[] a_img_boom_2;// 火箭子弹爆炸效果
	public Image[] a_img_boom_4;// 坦克子弹爆炸效果

	/**
	 * 轰炸机图片
	 */
	public Image img_bomber;
	public Image[] a_img_boom_5;// 轰炸机子弹爆炸效果

	private Image[] a_img_fire_right;

	private Image[] a_img_fire_left;

	public Image[] a_img_burning;// 坦克，大炮燃烧图片

	public Image[] a_img_broken;// 损坏。

	public Image[] a_img_gun_fire;// 枪口火焰

	/**
	 * 杀敌数
	 */
	public static int numb_killEnemy;

	/**
	 * 轰炸机
	 */
	public Bomber bomber;

	/**
	 * 开始战斗动画控制
	 */
	private int startFrameContorl;

	/**
	 * 开始战斗爆炸帧
	 */
	private int boom_fight_frame;

	/**
	 * 开始战斗文字y坐标
	 */
	private int y_word_fight = 20;

	/**
	 * 左右键某个键按下。1：向左的键。2：向右的键。0：都释放。
	 */
	private int keyDown;

	/**
	 * 左右移动展示地图
	 */
	private boolean showMap;

	/**
	 * 左右移动展示地图左右控制
	 */
	private int showMapControl = 1;

	/**
	 * 所有的士兵，包括坦克和炮兵
	 */
	public Vector v_soldier;

	/**
	 * 关卡开始后消耗的时间
	 */
	public static int useTime;

	/**
	 * 子弹
	 */
	public Vector v_bullet;

	/**
	 * 轰炸机视图
	 */
	public boolean bomberView;

	/**
	 * 轰炸机的炸弹
	 */
	public Vector v_bomb;

	/**
	 * 5种士兵冷却剩余
	 */
	private int[] coolTime = { 0, 0, 0, 0, 0 };

	/**
	 * 每次使用技能的冷却基准时间
	 */
	private int[] coolTimeMax = { 0, 0, 0, 0, 0 };

	/**
	 * 5种士兵冷却时间
	 */
	private final static int[] coolDown = { 250, 250, 250, 200, 200 };

	/**
	 * 敌人总数
	 */
	private int total_enemy;

	/**
	 * 剩余敌人数量
	 */
	public int remain_enemy;

	/**
	 * 剩余士兵数量（初始化为免费的25个）
	 */
	public int remain_army;

	/**
	 * 我军的进度条最右边距离屏幕最左边的距离。
	 */
	public int width_my_bar;

	/**
	 * 已派出的士兵数量
	 */
	public static int sentNumber;

	/**
	 * 每种士兵可免费派遣次数
	 */
	private final static int[] freeTimes = { 5, 3, 3, 2, 2 };

	/**
	 * 剩余可派遣次数
	 */
	public static int[] rem_sendTimes = { 5, 3, 3, 2, 2 };

	/**
	 * 已派兵波数
	 */
	public static int sendTimes;

	/**
	 * 使用轰炸机
	 */
	public static boolean sendBomber;

	/**
	 * 正在复活
	 */
	public static boolean relive;

	/**
	 * 界面以跳转，避免循环中重复跳转
	 */
	public static boolean viewChanged;

	/**
	 * 提示按键派兵
	 */
	private boolean prompt;

	/**
	 * 死法，0：被打死，1：阵地被占领而死
	 */
	public int dieWay;

	public Game(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;

		v_soldier = new Vector();
		v_bullet = new Vector();
		v_bomb = new Vector();

		initMession();
	}

	/**
	 * 初始化关卡
	 */
	public void initMession() {
		x_map = -160;
		numb_killEnemy = 0;
		starting = true;
		remain_create = 5;
		width_my_bar = 320;
		sentNumber = 0;
		prompt = true;

		for (int i = 0; i < 5; i++) {
			coolTime[i] = 0;
		}

		loadStartImage();

		if (CanvasControl.mission == 1) {
			total_enemy = 15;
		} else if (CanvasControl.mission > 1) {
			total_enemy = 46;
		}

		v_bomb.removeAllElements();
		v_bullet.removeAllElements();
		v_soldier.removeAllElements();

		useTime = 0;
		for (int i = 0; i < rem_sendTimes.length; i++) {
			rem_sendTimes[i] = freeTimes[i];
		}
		sendTimes = 0;
		remain_enemy = total_enemy;
		remain_army = 25;
		viewChanged = false;
	}

	/**
	 * 开始战斗动画图片加载
	 */
	private void loadStartImage() {
		try {
			a_img_boom_fight = new Image[5];
			for (int i = 0; i < 5; i++) {
				a_img_boom_fight[i] = Image.createImage("/game/boom_fight_" + i
						+ ".png");
			}

			a_img_fire_left = new Image[2];
			a_img_fire_right = new Image[2];

			Image left_0 = Image.createImage("/game/fire_0_left.png");
			Image right_1 = Image.createImage("/game/fire_1_right.png");

			a_img_fire_left[0] = left_0;
			a_img_fire_left[1] = Image.createImage(left_0, 0, 0,
					left_0.getWidth(), left_0.getHeight(), Sprite.TRANS_MIRROR);
			a_img_fire_right[0] = Image.createImage(right_1, 0, 0,
					right_1.getWidth(), right_1.getHeight(),
					Sprite.TRANS_MIRROR);
			a_img_fire_right[1] = right_1;

			img_word_fight = Image.createImage("/game/word_fight.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除开始战斗动画图片
	 */
	private void removeStartImage() {
		for (int i = 0; i < 5; i++) {
			a_img_boom_fight[i] = null;
		}
		a_img_boom_fight = null;

		a_img_fire_left[0] = null;
		a_img_fire_left[1] = null;
		a_img_fire_right[0] = null;
		a_img_fire_right[1] = null;

		a_img_fire_left = null;
		a_img_fire_right = null;

		img_word_fight = null;

		System.gc();
	}

	public void show(Graphics g) {
		g.drawImage(img_map, x_map, y_map, 0);

		if (keyDown == 1)
			g.drawImage(img_go_left, 5, 275, Graphics.VCENTER | Graphics.LEFT);
		else if (keyDown == 2)
			g.drawImage(img_go_right, 635, 275, Graphics.VCENTER
					| Graphics.RIGHT);

		for (int i = 0; i < v_soldier.size(); i++) {
			((Soldier) v_soldier.elementAt(i)).show(g);
		}

		for (int i = 0; i < v_bullet.size(); i++) {
			((Bullet) v_bullet.elementAt(i)).show(g);
		}

		for (int i = 0; i < v_bomb.size(); i++) {
			((Bomb) v_bomb.elementAt(i)).show(g);
		}

		if (bomber != null)
			bomber.show(g);

		g.drawImage(img_top_bar, 0, 0, 0);
		g.drawImage(img_bottom_bar, 0, 530, Graphics.BOTTOM | Graphics.LEFT);

		g.drawImage(img_icon_l, 0, 25, Graphics.VCENTER | Graphics.LEFT);
		g.drawImage(img_icon_r, 640, 25, Graphics.VCENTER | Graphics.RIGHT);

		if (starting) {
			showStarting(g);
		} else {
			C.drawString(g, img_number, CanvasControl.mission + "",
					"0123456789", 322, 33, 24, 27, Graphics.BOTTOM
							| Graphics.HCENTER, 0, 0);

			// 进度条
			g.drawImage(img_enemy_bar, 74, 22, Graphics.VCENTER | Graphics.LEFT);
			g.drawImage(img_enemy_bar, 569, 22, Graphics.VCENTER
					| Graphics.RIGHT);
			g.setClip(0, 0, width_my_bar, 530);
			g.drawImage(img_me_bar, 569, 22, Graphics.VCENTER | Graphics.RIGHT);
			g.drawImage(img_me_bar, 74, 22, Graphics.VCENTER | Graphics.LEFT);
			g.setClip(0, 0, 640, 530);

			if (!showMap && prompt) {
				g.setColor(0, 255, 255);
				g.setFont(C.FONT_LARGE_BLOD);
				g.drawString("按1~5键派遣士兵", 320, 200, Graphics.BOTTOM
						| Graphics.HCENTER);
				g.drawString("按左右键查看地图", 320, 230, Graphics.BOTTOM
						| Graphics.HCENTER);
			}
		}

		for (int i = 0; i < 6; i++) {
			g.drawImage(a_img_head[i], 31 + i * 91, 512, Graphics.BOTTOM
					| Graphics.HCENTER);
			if (i != 5) {
				if (CanvasControl.soldiers_level[i] == 0) {
					g.drawImage(img_cd, 43 + i * 91, 512, Graphics.BOTTOM
							| Graphics.HCENTER);

					g.drawImage(img_lock, 31 + i * 91, 500, Graphics.BOTTOM
							| Graphics.HCENTER);
				} else {
					if (coolTime[i] > 0) {
						g.setClip(i * 91 + (coolTimeMax[i] - coolTime[i]) * 88
								/ coolTimeMax[i], 0, 88, 530);
						g.drawImage(img_cd, 43 + i * 91, 512, Graphics.BOTTOM
								| Graphics.HCENTER);
						g.setClip(0, 0, 640, 530);
						g.drawImage(img_cd_line, i * 91
								+ (coolTimeMax[i] - coolTime[i]) * 88
								/ coolTimeMax[i], 480, Graphics.VCENTER
								| Graphics.HCENTER);
					}
				}
			}

			g.drawImage(img_key_bar, 69 + i * 91, 462, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawImage(a_img_word_key[i], 69 + i * 91, 462, Graphics.VCENTER
					| Graphics.HCENTER);

		}
	}

	/**
	 * 开始战斗动画
	 * 
	 * @param g
	 */
	private void showStarting(Graphics g) {
		if (startFrameContorl < 2) {
			g.drawImage(a_img_fire_left[0], 73, 22, Graphics.VCENTER
					| Graphics.LEFT);
			g.drawImage(a_img_fire_right[0], 565, 22, Graphics.VCENTER
					| Graphics.RIGHT);
		} else if (startFrameContorl < 4) {
			g.drawImage(img_me_bar, 74, 22, Graphics.VCENTER | Graphics.LEFT);
			g.drawImage(img_enemy_bar, 569, 22, Graphics.VCENTER
					| Graphics.RIGHT);

			g.drawImage(a_img_fire_left[1], 285, 22, Graphics.VCENTER
					| Graphics.RIGHT);
			g.drawImage(a_img_fire_right[1], 355, 22, Graphics.VCENTER
					| Graphics.LEFT);
		} else if (boom_fight_frame < 4) {
			C.drawString(g, img_number, CanvasControl.mission + "",
					"0123456789", 322, 33, 24, 27, Graphics.BOTTOM
							| Graphics.HCENTER, 0, 0);

			g.drawImage(img_me_bar, 74, 22, Graphics.VCENTER | Graphics.LEFT);
			g.drawImage(img_enemy_bar, 569, 22, Graphics.VCENTER
					| Graphics.RIGHT);

			g.drawImage(a_img_boom_fight[boom_fight_frame], 320, 22,
					Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(img_word_fight, 320, y_word_fight, Graphics.VCENTER
					| Graphics.HCENTER);
		} else {
			C.drawString(g, img_number, CanvasControl.mission + "",
					"0123456789", 322, 33, 24, 27, Graphics.BOTTOM
							| Graphics.HCENTER, 0, 0);

			g.drawImage(img_me_bar, 74, 22, Graphics.VCENTER | Graphics.LEFT);
			g.drawImage(img_enemy_bar, 569, 22, Graphics.VCENTER
					| Graphics.RIGHT);

			g.drawImage(img_word_fight, 320, y_word_fight, Graphics.VCENTER
					| Graphics.HCENTER);
		}
	}

	public void loadResource() {
		try {
			a_img_gun_fire = new Image[3];
			for (int i = 0; i < 3; i++) {
				a_img_gun_fire[i] = Image.createImage("/game/gun_fire_" + i
						+ ".png");
			}

			a_img_broken = new Image[6];
			for (int i = 0; i < 6; i++) {
				a_img_broken[i] = Image.createImage("/game/broken_" + i
						+ ".png");
			}

			a_img_burning = new Image[4];
			Image img_t = Image.createImage("/game/burning.png");
			for (int i = 0; i < 4; i++) {
				a_img_burning[i] = Image.createImage(img_t, i * 13, 0, 13, 20,
						0);
			}

			a_img_blood_bar = new Image[4];
			for (int i = 0; i < 4; i++) {
				a_img_blood_bar[i] = Image.createImage("/game/blood_bar_" + i
						+ ".png");
			}

			a_img_bullet = new Image[6];
			for (int i = 0; i < 6; i++) {
				a_img_bullet[i] = Image.createImage("/bullet/bullet_" + i
						+ ".png");
			}

			a_img_boom_0 = new Image[5];
			Image boom_t = Image.createImage("/game/boom_0.png");
			for (int i = 0; i < 5; i++) {
				a_img_boom_0[i] = Image.createImage(boom_t, 79 * i, 0, 79, 63,
						0);
			}

			a_img_boom_1 = new Image[7];
			for (int i = 0; i < 7; i++) {
				a_img_boom_1[i] = Image.createImage("/game/boom_1_" + i
						+ ".png");
			}

			a_img_boom_2 = new Image[7];
			for (int i = 0; i < 7; i++) {
				a_img_boom_2[i] = Image.createImage("/game/boom_2_" + i
						+ ".png");
			}

			a_img_boom_4 = new Image[7];
			for (int i = 0; i < 7; i++) {
				a_img_boom_4[i] = Image.createImage("/game/boom_4_" + i
						+ ".png");
			}

			// a_img_boom_5 = new Image[8];
			// for (int i = 0; i < 8; i++) {
			// a_img_boom_5[i] = Image.createImage("/game/boom_5_" + i +
			// ".png");
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 移除从服务器下载的图片
	 */
	public void removeServerImage() {
		img_map = null;
		img_top_bar = null;// 顶部显示条
		img_bottom_bar = null;// 底部显示条
		img_me_bar = null;// 我的进度条
		img_enemy_bar = null;// 敌方进度条
		img_number = null;// 数字图片，用来显示第几关
		img_cd = null;// 派兵cd蒙版
		img_cd_line = null;// cd回复的line
		img_friendly = null;// 我方标志
		img_key_bar = null;// 按键显示条
		img_go_right = null;//
		img_go_left = null;//
		img_icon_l = null;//
		img_icon_r = null;//
		img_lock = null;

		for (int i = 0; i < a_img_word_key.length; i++) {
			a_img_word_key[i] = null;
		}
		a_img_word_key = null;

		for (int i = 0; i < a_img_head.length; i++) {
			a_img_head[i] = null;
		}
		a_img_head = null;

		for (int i = 0; i < a_2_img_soldier.length; i++) {
			for (int j = 0; j < a_2_img_soldier[i].length; j++) {
				a_2_img_soldier[i][j] = null;
			}
		}
		a_2_img_soldier = null;

		System.gc();
	}

	public void removeResource() {
		for (int i = 0; i < 6; i++) {
			a_img_bullet[i] = null;
		}
		a_img_bullet = null;

		for (int i = 0; i < 5; i++) {
			a_img_boom_0[i] = null;
		}
		a_img_boom_0 = null;

		for (int i = 0; i < 7; i++) {
			a_img_boom_1[i] = null;
		}
		a_img_boom_1 = null;

		for (int i = 0; i < 7; i++) {
			a_img_boom_2[i] = null;
		}
		a_img_boom_2 = null;

		for (int i = 0; i < 7; i++) {
			a_img_boom_4[i] = null;
		}
		a_img_boom_4 = null;

		for (int i = 0; i < 4; i++) {
			a_img_blood_bar[i] = null;
		}
		a_img_blood_bar = null;

		for (int i = 0; i < 4; i++) {
			a_img_burning[i] = null;
		}
		a_img_burning = null;

		for (int i = 0; i < 6; i++) {
			a_img_broken[i] = null;
		}
		a_img_broken = null;

		for (int i = 0; i < 3; i++) {
			a_img_gun_fire[i] = null;
		}
		a_img_gun_fire = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {
		if (starting || showMap)
			return;

		if (prompt)
			prompt = false;

		switch (keyCode) {
		case C.KEY_RIGHT:
			keyDown = 2;
			break;
		case C.KEY_LEFT:
			keyDown = 1;
			break;
		case C.KEY_1:
			sendTroops(0);
			break;
		case C.KEY_2:
			if (CanvasControl.soldiers_level[1] == 0) {
				CanvasControl.goodsIndex = 1;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 5, this));
			} else {
				sendTroops(1);
			}
			break;
		case C.KEY_3:
			if (CanvasControl.soldiers_level[2] == 0) {
				CanvasControl.goodsIndex = 2;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 5, this));
			} else {
				sendTroops(2);
			}
			break;
		case C.KEY_4:
			if (CanvasControl.soldiers_level[3] == 0) {
				CanvasControl.goodsIndex = 3;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 5, this));
			} else {
				sendTroops(3);
			}
			break;
		case C.KEY_5:
			if (CanvasControl.soldiers_level[4] == 0) {
				CanvasControl.goodsIndex = 4;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 5, this));
			} else {
				sendTroops(4);
			}
			break;
		case C.KEY_6:
			if (!bomberView) {
				CanvasControl.goodsIndex = 15;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 5, this));
				// loadBomberImage();
				// bomber = new Bomber(this);
				// bomberView = true;
			}
			break;

		case C.KEY_9:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(new Help(canvasControl, 1));
			break;

		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(new Dialog(canvasControl, 1, this));
			break;

		default:
			break;
		}
	}

	/**
	 * 派兵
	 * 
	 * @param troopType
	 *            兵种
	 */
	private void sendTroops(int troopType) {
		if (coolTime[troopType] == 0) {
			if (rem_sendTimes[troopType] > 0) {
				rem_sendTimes[troopType]--;
				sendTimes++;
				for (int i = 0; i < 5; i++) {
					if (CanvasControl.soldiers_level[i] > 0 && coolTime[i] <= 0) {
						coolTime[i] = 10;
						coolTimeMax[i] = 10;
					}
				}
				coolTime[troopType] = coolDown[troopType];
				coolTimeMax[troopType] = coolDown[troopType];
				insertSoldier(CanvasControl.soldiers_prpty[troopType][6], true,
						troopType);
			} else {
				CanvasControl.goodsIndex = 10 + troopType;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 5, this));
			}
		}
	}

	/**
	 * 加载轰炸机相关图片
	 */
	private void loadBomberImage() {
		try {
			img_bomber = Image.createImage("/game/bomber.png");

			a_img_boom_5 = new Image[8];
			for (int i = 0; i < 8; i++) {
				a_img_boom_5[i] = Image.createImage("/game/boom_5_" + i
						+ ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除轰炸机相关图片
	 */
	public void removeBomberImage() {
		img_bomber = null;

		for (int i = 0; i < 8; i++) {
			a_img_boom_5[i] = null;
		}
		a_img_boom_5 = null;
	}

	public void keyReleased(int keyCode) {
		if (keyCode == C.KEY_RIGHT) {
			if (keyDown == 2)
				keyDown = 0;
		} else if (keyCode == C.KEY_LEFT) {
			if (keyDown == 1)
				keyDown = 0;
		}
	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if (starting) {// 开始战斗，动画效果控制
			startFrameContorl++;
			if (startFrameContorl > 4 && startFrameContorl % 2 == 0) {
				boom_fight_frame++;
			}

			if (startFrameContorl > 4 && y_word_fight < 235)
				y_word_fight += 20;

			if (startFrameContorl > 30) {
				starting = false;
				showMap = true;
				startFrameContorl = 0;
				boom_fight_frame = 0;
				y_word_fight = 20;
				removeStartImage();
			}
		} else if (showMap) {// 左右移动展示地图
			if (x_map == -320 || x_map == 0)
				showMapControl *= -1;

			x_map += showMapControl * 10;

			if (showMapControl == 1 && x_map == -160)
				showMap = false;
		} else {
			autoCreateEnemy();
			useTime++;

			if (sendBomber) {
				sendBomber = false;
				loadBomberImage();
				bomber = new Bomber(this);
				bomberView = true;
			}

			if (relive) {
				relive();
			}

			for (int i = 0; i < coolTime.length; i++) {
				if (coolTime[i] > 0)
					coolTime[i]--;
			}

			if (bomberView)
				bomber.logic();

			for (int i = 0; i < v_bullet.size(); i++) {
				((Bullet) v_bullet.elementAt(i)).fly();
			}

			for (int i = 0; i < v_soldier.size(); i++) {
				((Soldier) v_soldier.elementAt(i)).logic();
			}

			for (int i = 0; i < v_bomb.size(); i++) {
				((Bomb) v_bomb.elementAt(i)).logic();
			}
			keyDownLogic();
		}
	}

	/**
	 * 复活
	 */
	private void relive() {
		viewChanged = false;
		relive = false;

		if (dieWay == 0) {
			x_map = -320;
		} else {
			x_map = 0;
		}

		for (int i = 0; i < v_soldier.size(); i++) {
			Soldier p_soldier = (Soldier) v_soldier.elementAt(i);
			p_soldier.beHit(new Bullet(4, p_soldier.x, p_soldier.y, true, this,
					true, p_soldier, p_soldier));
		}
	}

	/**
	 * 自动生产敌人
	 */
	private void autoCreateEnemy() {
		if (CanvasControl.mission == 1) {
			if (remain_create != 0 && useTime % 200 == 0) {
				switch (remain_create) {
				case 5:
					insertSoldier(5, false, 0);
					remain_create--;
					break;
				case 4:
					insertSoldier(3, false, 1);
					remain_create--;
					break;
				case 3:
					insertSoldier(3, false, 2);
					remain_create--;
					break;
				case 2:
					insertSoldier(3, false, 3);
					remain_create--;
					break;
				case 1:
					insertSoldier(1, false, 4);
					remain_create--;
					break;
				}
			}
		} else if (CanvasControl.mission > 1) {
			if (remain_create != 0 && useTime % 200 == 0) {
				switch (remain_create) {
				case 5:
					insertSoldier(5, false, 0);
					insertSoldier(5, false, 1);
					remain_create--;
					break;
				case 4:
					insertSoldier(5, false, 2);
					insertSoldier(5, false, 3);
					remain_create--;
					break;
				case 3:
					insertSoldier(5, false, 0);
					insertSoldier(3, false, 4);
					remain_create--;
					break;
				case 2:
					insertSoldier(5, false, 1);
					insertSoldier(5, false, 3);
					remain_create--;
					break;
				case 1:
					insertSoldier(5, false, 2);
					insertSoldier(3, false, 4);
					remain_create--;
					break;
				}
			}
		}
	}

	/**
	 * 插入一定数量的士兵
	 * 
	 * @param number
	 *            数量
	 * @param friendly
	 *            友方
	 * @param type
	 *            兵种
	 */
	public void insertSoldier(int number, boolean friendly, int type) {
		int x_t, y_t;
		boolean inserted;
		for (int i = 0; i < number; i++) {
			if (friendly)
				x_t = -55 + C.R.nextInt(20);
			else
				x_t = 1005 + C.R.nextInt(20);

			inserted = false;
			y_t = C.R.nextInt(300) + 150;
			for (int j = 0; j < v_soldier.size(); j++) {
				if (y_t < ((Soldier) v_soldier.elementAt(j)).y) {// y坐标比他大插在他的位置
					v_soldier.insertElementAt(new Soldier(this, type, friendly,
							x_t, y_t), j);
					inserted = true;
					break;
				}
			}
			if (!inserted)// 没有比他y坐标大的
				v_soldier
						.addElement(new Soldier(this, type, friendly, x_t, y_t));

			if (friendly) {
				sentNumber++;
				if (sentNumber > 24) {
					remain_army++;
				}
			}
		}
	}

	/**
	 * 键长按逻辑
	 */
	private void keyDownLogic() {
		if (keyDown == 1 && x_map < 0)
			x_map += 10;
		else if (keyDown == 2 && x_map > -320)
			x_map -= 10;
	}

	/**
	 * 取到当前游戏的画布控制对象
	 * 
	 * @return
	 */
	public CanvasControl getCanvasControl() {
		return canvasControl;
	}

}
