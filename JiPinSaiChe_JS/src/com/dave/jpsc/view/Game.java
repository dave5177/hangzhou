package com.dave.jpsc.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.model.Car;
import com.dave.jpsc.model.EnemyCar;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.tool.Circle;
import com.dave.jpsc.tool.Rect;
import com.dave.jpsc.tool.RectFree;
import com.dave.jpsc.tool.Vector2;
import com.dave.showable.Showable;

public class Game implements Showable {

	public CanvasControl canvasControl;

	/**
	 * 状态
	 */
	private int state;

	/**
	 * 模式
	 */
	private int mode;

	/**
	 * 地图的坐标
	 */
	public int x_map, y_map;

	/**
	 * 地图的高和宽
	 */
	public int width_map, heigth_map;

	public Image img_map;
	public Image img_point;
	public Image img_small_map;// 小地图
	public Image img_yellow_point;// 小黄点
	public Image img_top;
	public Image img_bottom;
	public Image img_num_speed;
	public Image img_pointer;// 车速表盘指针
	public Image img_oil_bar;// 油量
	public Image img_car_slow;// 减速效果图片
	public Image[] a_img_car_blind;// 致盲效果图片
	private Image img_achieve;// 过关成功或失败
	private Image[] a_img_ready;
	private Image[] a_img_err_dir;// 你开反了
	private Image[] a_img_perfect;// 完美漂移
	public Image[][] a_img_speed_up;// 加速效果图片
	public Image[] a_img_cover;// 赛道覆盖层

	/**
	 * 赛道覆盖片的数量
	 */
	public int num_cover;

	/**
	 * 赛道覆盖片的坐标
	 */
	public int[] cover_point;

	/**
	 * 我的车
	 */
	public Car myCar;

	/**
	 * 敌人的车
	 */
	public Vector enemyCars;

	/**
	 * 地图矩形碰撞体
	 */
	public Vector rects;
	public Vector rects_f1;// 上层的矩形碰撞体

	/**
	 * 地图自由矩形碰撞体
	 */
	public Vector freeRects;
	public Vector freeRects_f1;// 上层的自由矩形碰撞体

	/**
	 * 地图圆形碰撞体
	 */
	public Vector circles;
	public Vector circles_f1;// 上层的圆形碰撞体

	/**
	 * 赛道方向矩形
	 */
	public Vector dirRects;
	public Vector dirRects_f1;

	/**
	 * 结束碰撞矩形
	 */
	public RectFree finishRect;

	/**
	 * 层上升矩形（与该矩形正方向一直的方向穿过，上升到第二层，相反则下降）
	 */
	public RectFree upFloorRect;

	/**
	 * 层下降矩形（与上升层矩形相反）
	 */
	public RectFree downFloorRect;

	/**
	 * 覆盖区域矩形
	 */
	public RectFree[] coverRects;

	/**
	 * 车辆当前速度（用来显示的真实速度）
	 */
	public int carSpeed;

	/**
	 * 时间字符串
	 */
	public String timeStr = "00:00:00";

	/**
	 * 世界纪录
	 */
	public String recordStr;

	/**
	 * 准备开始倒计时
	 */
	public static final int STATE_REDYGO = 1;

	/**
	 * 游戏中
	 */
	public static final int STATE_GAMING = 2;

	/**
	 * 跑完全程了
	 */
	public static final int STATE_ACHIEVE = 3;

	/**
	 * 关卡和副本模式
	 */
	public static final int MODE_MISSION = 0;

	/**
	 * 挑战模式
	 */
	public static final int MODE_DUAL = 3;

	/**
	 * y坐标图片
	 */
	private int y_ready;

	/**
	 * read帧
	 */
	private int frameReady;

	private int readySpeed = 10;

	/**
	 * 当前关卡游戏时间（分秒数）
	 */
	public int gameTime;

	/**
	 * 剩余时间
	 */
	public int remainTime;

	/**
	 * 使用道具次数
	 */
	public int useGoodsTimes;

	/**
	 * 游戏中车子的数量
	 */
	public int amount_car;

	/**
	 * 第几个穿过终点线（每穿过一辆车+1）
	 */
	public int firstPassEnd;

	/**
	 * 成功或失败提示框y坐标
	 */
	private int y_achieve = -100;

	/**
	 * 提示框下落速度
	 */
	private int y_speed_achieve = 10;

	/**
	 * 成功失败对话框显示时间
	 */
	private int achieveShowTime;

	/**
	 * 完美漂移每个字的y坐标
	 */
	private int[] y_perfect = { -35, -35, -35, -35 };

	/**
	 * 完美漂移下落速度
	 */
	private int y_speed_perfect = 40;

	/**
	 * 最左边的完美漂移字母的x坐标
	 */
	private int x_perfect_start = 200;

	/**
	 * 停留时间
	 */
	private int perfectShowTime = 10;

	/**
	 * 挑战对手
	 */
	public Player duelPlayer;

	/**
	 * 总共的圈数
	 */
	public int lapMax;

	private int y_word = 200;

	/**
	 * 燃料耗尽
	 */
	public static boolean fuelOut;

	/**
	 * 时间耗尽
	 */
	public static boolean timeOut;

	/**
	 * 游戏模拟时间是实际使用时间的倍数
	 */
	public static final int TIME_MUL = 4;

	/**
	 * 挑战模式我和挑战对手在各个地图的坐标和方向
	 */
	private static final int[][] DUEL_ENEMY_POINT = {
			{ 602, 228, 180, 602, 268, 180 }, { 725, 440, 0, 725, 480, 0 },
			{ 695, 94, 180, 695, 134, 180 }, { 667, 511, 180, 667, 551, 180 },
			{ 627, 475, 180, 627, 515, 180 }, { 494, 197, 0, 494, 237, 0 },
			{ 130, 363, 0, 130, 403, 0 }, { 619, 613, 0, 619, 653, 0 },
			{ 307, 533, 0, 307, 573, 0 }, { 358, 221, 0, 358, 261, 0 },
			{ 223, 205, 0, 223, 245, 0 }, { 178, 210, 0, 178, 250, 0 },
			{ 590, 140, 180, 590, 180, 180 }, { 445, 598, 0, 445, 638, 0 },
			{ 459, 194, 0, 459, 234, 0 }, { 434, 609, 180, 434, 649, 180 }, };

	public Game(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		rects = new Vector();
		rects_f1 = new Vector();
		freeRects = new Vector();
		freeRects_f1 = new Vector();
		circles = new Vector();
		circles_f1 = new Vector();
		enemyCars = new Vector();
		dirRects = new Vector();
		dirRects_f1 = new Vector();

		missionInit();
	}

	/**
	 * @param canvasControl
	 * @param mode
	 * @param duelPlayer
	 *            挑战对手
	 */
	public Game(CanvasControl canvasControl, int mode, Player duelPlayer) {
		this.canvasControl = canvasControl;
		this.mode = mode;
		this.duelPlayer = duelPlayer;
		rects = new Vector();
		rects_f1 = new Vector();
		freeRects = new Vector();
		freeRects_f1 = new Vector();
		circles = new Vector();
		circles_f1 = new Vector();
		enemyCars = new Vector();
		dirRects = new Vector();
		dirRects_f1 = new Vector();

		if (mode == MODE_DUAL) {
			duelInit();
		} else if (mode == MODE_MISSION) {
			missionInit();
		}
	}

	public void init() {
		width_map = img_map.getWidth();
		heigth_map = img_map.getHeight();
	}

	private void duelInit() {
		mapInit(CanvasControl.mission);
		myCar = new Car(this, DUEL_ENEMY_POINT[CanvasControl.mission - 1][0],
				DUEL_ENEMY_POINT[CanvasControl.mission - 1][1],
				DUEL_ENEMY_POINT[CanvasControl.mission - 1][2],
				canvasControl.me.cars[CanvasControl.usingCar][0],
				canvasControl.me);

		enemyCars.removeAllElements();
		if (CanvasControl.mission == 16) {
			enemyCars.addElement(new EnemyCar(this,
					DUEL_ENEMY_POINT[CanvasControl.mission - 1][3],
					DUEL_ENEMY_POINT[CanvasControl.mission - 1][4],
					DUEL_ENEMY_POINT[CanvasControl.mission - 1][5], duelPlayer,
					1));
		} else {
			enemyCars
					.addElement(new EnemyCar(this,
							DUEL_ENEMY_POINT[CanvasControl.mission - 1][3],
							DUEL_ENEMY_POINT[CanvasControl.mission - 1][4],
							DUEL_ENEMY_POINT[CanvasControl.mission - 1][5],
							duelPlayer));
		}

		lapMax = 1;
		timeStr = C.computeTimeStr(gameTime * TIME_MUL, true);
		recordStr = C
				.computeTimeStr(
						CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][5] * 10,
						true);
		amount_car = enemyCars.size() + 1;
		gameTime = 0;
		firstPassEnd = 0;
		setState(STATE_REDYGO);
	}

	private void missionInit() {
		mapInit(CanvasControl.mission);
		playerInit();

		lapMax = CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][0];
		remainTime = CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][3] * 10;
		timeStr = C.computeTimeStr(remainTime, true);
		recordStr = C
				.computeTimeStr(
						CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][5] * 10,
						true);
		amount_car = enemyCars.size() + 1;
		gameTime = 0;
		firstPassEnd = 0;
		setState(STATE_REDYGO);
	}

	/**
	 * 玩家初始化
	 */
	private void playerInit() {
		switch (CanvasControl.mission) {
		case 1:
			myCar = new Car(this, 602, 248, 180,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 602, 218, 180, 0));
			enemyCars.addElement(new EnemyCar(this, 602, 278, 180, 0));
			break;
		case 2:
			myCar = new Car(this, 725, 455, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 725, 485, 0, 0));
			enemyCars.addElement(new EnemyCar(this, 725, 425, 0, 0));
			break;
		case 3:
			myCar = new Car(this, 695, 114, 180,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 695, 144, 180, 0));
			enemyCars.addElement(new EnemyCar(this, 695, 84, 180, 0));
			break;
		case 4:
			myCar = new Car(this, 667, 531, 180,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 667, 561, 180, 1));
			enemyCars.addElement(new EnemyCar(this, 667, 501, 180, 0));
			break;
		case 5:
			myCar = new Car(this, 627, 495, 180,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 627, 525, 180, 1));
			enemyCars.addElement(new EnemyCar(this, 627, 465, 180, 2));
			break;
		case 6:
			myCar = new Car(this, 494, 217, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 494, 247, 0, 1));
			enemyCars.addElement(new EnemyCar(this, 494, 187, 0, 1));
			break;
		case 7:
			myCar = new Car(this, 130, 383, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 130, 413, 0, 3));
			enemyCars.addElement(new EnemyCar(this, 130, 353, 0, 2));
			enemyCars.addElement(new EnemyCar(this, 70, 353, 0, 1));
			break;
		case 8:
			myCar = new Car(this, 619, 633, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 619, 603, 0, 2));
			enemyCars.addElement(new EnemyCar(this, 619, 663, 0, 3));
			enemyCars.addElement(new EnemyCar(this, 569, 603, 0, 4));
			break;
		case 9:
			myCar = new Car(this, 307, 553, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 307, 523, 0, 3));
			enemyCars.addElement(new EnemyCar(this, 307, 583, 0, 3));
			enemyCars.addElement(new EnemyCar(this, 257, 523, 0, 4));
			break;
		case 10:
			myCar = new Car(this, 358, 241, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 358, 211, 0, 4));
			enemyCars.addElement(new EnemyCar(this, 358, 271, 0, 5));
			enemyCars.addElement(new EnemyCar(this, 308, 211, 0, 6));
			enemyCars.addElement(new EnemyCar(this, 308, 241, 0, 4));
			break;
		case 11:
			myCar = new Car(this, 223, 225, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 223, 255, 0, 4));
			enemyCars.addElement(new EnemyCar(this, 223, 195, 0, 5));
			enemyCars.addElement(new EnemyCar(this, 173, 195, 0, 6));
			enemyCars.addElement(new EnemyCar(this, 173, 225, 0, 7));
			break;
		case 12:
			myCar = new Car(this, 178, 230, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 178, 200, 0, 4));
			enemyCars.addElement(new EnemyCar(this, 178, 260, 0, 5));
			enemyCars.addElement(new EnemyCar(this, 128, 200, 0, 6));
			enemyCars.addElement(new EnemyCar(this, 128, 230, 0, 7));
			break;
		case 13:
			myCar = new Car(this, 590, 160, 180,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 590, 135, 180, 4));
			enemyCars.addElement(new EnemyCar(this, 590, 185, 180, 5));
			enemyCars.addElement(new EnemyCar(this, 640, 135, 180, 6));
			enemyCars.addElement(new EnemyCar(this, 640, 160, 180, 7));
			enemyCars.addElement(new EnemyCar(this, 640, 185, 180, 8));
			break;
		case 14:
			myCar = new Car(this, 445, 618, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 445, 643, 0, 4));
			enemyCars.addElement(new EnemyCar(this, 445, 593, 0, 5));
			enemyCars.addElement(new EnemyCar(this, 395, 643, 0, 6));
			enemyCars.addElement(new EnemyCar(this, 395, 618, 0, 7));
			enemyCars.addElement(new EnemyCar(this, 395, 593, 0, 8));
			break;
		case 15:
			myCar = new Car(this, 459, 212, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 459, 242, 0, 4));
			enemyCars.addElement(new EnemyCar(this, 459, 182, 0, 5));
			enemyCars.addElement(new EnemyCar(this, 409, 182, 0, 6));
			enemyCars.addElement(new EnemyCar(this, 409, 212, 0, 7));
			enemyCars.addElement(new EnemyCar(this, 409, 242, 0, 8));
			break;
		case 16:
			myCar = new Car(this, 434, 625, 180,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me, 1);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 434, 595, 180, 4, 1));
			enemyCars.addElement(new EnemyCar(this, 434, 655, 180, 5, 1));
			enemyCars.addElement(new EnemyCar(this, 481, 592, 157.5f, 6, 1));
			enemyCars.addElement(new EnemyCar(this, 498, 610, 157.5f, 7, 1));
			enemyCars.addElement(new EnemyCar(this, 503, 637, 157.5f, 8, 1));
			break;
		case 17:
			myCar = new Car(this, 676, 415, 0,
					canvasControl.me.cars[CanvasControl.usingCar][0],
					canvasControl.me);

			enemyCars.removeAllElements();
			enemyCars.addElement(new EnemyCar(this, 676, 440, 0, 4));
			enemyCars.addElement(new EnemyCar(this, 676, 390, 0, 6));
			enemyCars.addElement(new EnemyCar(this, 626, 390, 0, 5));
			enemyCars.addElement(new EnemyCar(this, 626, 415, 0, 7));
			enemyCars.addElement(new EnemyCar(this, 626, 440, 0, 1));
			enemyCars.addElement(new EnemyCar(this, 576, 390, 0, 2));
			enemyCars.addElement(new EnemyCar(this, 576, 415, 0, 3));
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化地图
	 * 
	 * @param mapIndex
	 */
	private void mapInit(int mapIndex) {
		switch (mapIndex) {
		case 1:
			initMap_1();
			break;
		case 2:
			initMap_2();
			break;
		case 3:
			initMap_3();
			break;
		case 4:
			initMap_4();
			break;
		case 5:
			initMap_5();
			break;
		case 6:
			initMap_6();
			break;
		case 7:
			initMap_7();
			break;
		case 8:
			initMap_8();
			break;
		case 9:
			initMap_9();
			break;
		case 10:
			initMap_10();
			break;
		case 11:
			initMap_11();
			break;
		case 12:
			initMap_12();
			break;
		case 13:
			initMap_13();
			break;
		case 14:
			initMap_14();
			break;
		case 15:
			initMap_15();
			break;
		case 16:
			initMap_16();
			break;
		case 17:
			initMap_17();// 副本
			break;

		}
	}

	private void initMap_1() {
		finishRect = new RectFree(17, 100, new Vector2(550, 253), 0);

		rects.addElement(new Rect(201, 153, 576, 50));
		rects.addElement(new Rect(201, 299, 576, 199));
		rects.addElement(new Rect(201, 593, 576, 50));
		rects.addElement(new Rect(139, 361, 700, 77));
		rects.addElement(new Rect(0, 310, 44, 188));
		rects.addElement(new Rect(936, 290, 26, 210));

		circles.addElement(new Circle(201, 363, 162, Circle.OUT_DETECT, 180,
				270));
		circles.addElement(new Circle(201, 363, 55, Circle.IN_DETECT, 90, 180));
		circles.addElement(new Circle(777, 363, 162, Circle.OUT_DETECT, 270,
				360));
		circles.addElement(new Circle(777, 363, 55, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(201, 435, 162, Circle.OUT_DETECT, 90, 180));
		circles.addElement(new Circle(201, 435, 55, Circle.IN_DETECT, 180, 270));
		circles.addElement(new Circle(777, 435, 162, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(777, 435, 55, Circle.IN_DETECT, 270, 360));

		dirRects.addElement(new RectFree(341, 96, new Vector2(373, 253), 180));
		dirRects.addElement(new RectFree(247, 96, new Vector2(667, 253), 180));
		dirRects.addElement(new RectFree(579, 96, new Vector2(490, 551), 0));
		dirRects.addElement(new RectFree(158, 96, new Vector2(91, 395), 90));
		dirRects.addElement(new RectFree(158, 96, new Vector2(889, 395), 270));
		dirRects.addElement(new RectFree(158, 96, new Vector2(123, 281), 135));
		dirRects.addElement(new RectFree(158, 96, new Vector2(123, 515), 45));
		dirRects.addElement(new RectFree(158, 96, new Vector2(852, 515), 315));
		dirRects.addElement(new RectFree(158, 96, new Vector2(852, 281), 225));
	}

	private void initMap_2() {
		finishRect = new RectFree(17, 100, new Vector2(766, 454), 0);

		rects.addElement(new Rect(132, 343, 248, 36));
		rects.addElement(new Rect(128, 475, 246, 55));
		rects.addElement(new Rect(714, 380, 129, 25));
		rects.addElement(new Rect(710, 502, 166, 33));

		freeRects.addElement(new RectFree(302, 70, new Vector2(100, 174), 135));
		freeRects.addElement(new RectFree(47, 238, new Vector2(226, 273), 45));
		freeRects.addElement(new RectFree(302, 60, new Vector2(826, 183), 45));
		freeRects.addElement(new RectFree(280, 50, new Vector2(715, 285), 45));
		freeRects.addElement(new RectFree(110, 40, new Vector2(344, 218), 45));
		freeRects.addElement(new RectFree(110, 40, new Vector2(574, 213), 135));
		freeRects.addElement(new RectFree(100, 100, new Vector2(461, 85), 45));

		circles.addElement(new Circle(310, 166, 112, Circle.OUT_DETECT, 225,
				315));
		circles.addElement(new Circle(457, 151, 108, Circle.OUT_DETECT, 45,
				135, 200));
		circles.addElement(new Circle(612, 166, 112, Circle.OUT_DETECT, 225,
				315));
		circles.addElement(new Circle(120, 365, 117, Circle.OUT_DETECT, 90, 225));
		circles.addElement(new Circle(122, 359, 16, Circle.IN_DETECT, 90, 225));
		circles.addElement(new Circle(372, 490, 115, Circle.OUT_DETECT, 270,
				360, 135));
		circles.addElement(new Circle(372, 490, 20, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(504, 497, 119, Circle.OUT_DETECT, 315,
				180, 139));
		circles.addElement(new Circle(504, 497, 18, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(596, 395, 115, Circle.OUT_DETECT, 135,
				360, 135));
		circles.addElement(new Circle(596, 395, 16, Circle.IN_DETECT, 135, 360));
		circles.addElement(new Circle(717, 405, 97, Circle.OUT_DETECT, 90, 180,
				115));
		circles.addElement(new Circle(837, 398, 117, Circle.OUT_DETECT, 315, 90));
		circles.addElement(new Circle(845, 396, 10, Circle.IN_DETECT, 315, 90));

		dirRects.addElement(new RectFree(89, 91, new Vector2(742, 451), 0));
		dirRects.addElement(new RectFree(95, 91, new Vector2(808, 451), 0));
		dirRects.addElement(new RectFree(153, 85, new Vector2(902, 415), 270));
		dirRects.addElement(new RectFree(300, 90, new Vector2(772, 243), 225));
		dirRects.addElement(new RectFree(124, 90, new Vector2(615, 113), 180));
		dirRects.addElement(new RectFree(100, 90, new Vector2(530, 160), 135));
		dirRects.addElement(new RectFree(117, 104, new Vector2(465, 205), 180));
		dirRects.addElement(new RectFree(153, 101, new Vector2(311, 109), 180));
		dirRects.addElement(new RectFree(300, 90, new Vector2(156, 232), 135));
		dirRects.addElement(new RectFree(188, 96, new Vector2(63, 387), 90));
		dirRects.addElement(new RectFree(262, 93, new Vector2(242, 425), 0));
		dirRects.addElement(new RectFree(300, 90, new Vector2(156, 232), 135));
		dirRects.addElement(new RectFree(229, 102, new Vector2(434, 500), 90));
		dirRects.addElement(new RectFree(100, 90, new Vector2(551, 535), 315));
		dirRects.addElement(new RectFree(100, 90, new Vector2(545, 435), 225));
		dirRects.addElement(new RectFree(100, 90, new Vector2(541, 358), 315));
		dirRects.addElement(new RectFree(120, 90, new Vector2(665, 397), 90));
	}

	private void initMap_3() {
		finishRect = new RectFree(18, 97, new Vector2(651, 117), 180);

		rects.addElement(new Rect(219, 7, 565, 61));
		rects.addElement(new Rect(246, 164, 519, 36));
		rects.addElement(new Rect(246, 218, 23, 362));
		rects.addElement(new Rect(110, 267, 40, 368));
		rects.addElement(new Rect(544, 287, 274, 52));
		rects.addElement(new Rect(364, 432, 59, 179));

		circles.addElement(new Circle(139, 143, 112, Circle.OUT_DETECT, 90, 315));
		circles.addElement(new Circle(139, 143, 15, Circle.IN_DETECT, 90, 315));
		circles.addElement(new Circle(163, 247, 86, Circle.OUT_DETECT, 270,
				360, 106));
		circles.addElement(new Circle(126, 269, 24, Circle.IN_DETECT, 90, 315));
		circles.addElement(new Circle(258, 580, 120, Circle.OUT_DETECT, 0, 180));
		circles.addElement(new Circle(258, 580, 10, Circle.IN_DETECT, 0, 180));
		circles.addElement(new Circle(535, 466, 274, Circle.OUT_DETECT, 180,
				270, 290));
		circles.addElement(new Circle(535, 466, 178, Circle.IN_DETECT, 180, 270));
		circles.addElement(new Circle(765, 182, 120, Circle.OUT_DETECT, 270, 90));
		circles.addElement(new Circle(765, 182, 15, Circle.IN_DETECT, 270, 90));

		dirRects.addElement(new RectFree(447, 97, new Vector2(436, 115), 180));
		dirRects.addElement(new RectFree(165, 97, new Vector2(726, 115), 180));
		dirRects.addElement(new RectFree(60, 90, new Vector2(168, 89), 225));
		dirRects.addElement(new RectFree(60, 90, new Vector2(100, 87), 135));
		dirRects.addElement(new RectFree(60, 90, new Vector2(73, 138), 90));
		dirRects.addElement(new RectFree(60, 90, new Vector2(91, 188), 45));
		dirRects.addElement(new RectFree(70, 82, new Vector2(158, 206), 0));
		dirRects.addElement(new RectFree(60, 90, new Vector2(189, 233), 45));
		dirRects.addElement(new RectFree(341, 96, new Vector2(197, 409), 90));
		dirRects.addElement(new RectFree(120, 90, new Vector2(214, 633), 45));
		dirRects.addElement(new RectFree(120, 90, new Vector2(297, 633), 315));
		dirRects.addElement(new RectFree(161, 93, new Vector2(316, 503), 270));
		dirRects.addElement(new RectFree(240, 93, new Vector2(379, 304), 315));
		dirRects.addElement(new RectFree(251, 93, new Vector2(650, 244), 0));
		dirRects.addElement(new RectFree(80, 93, new Vector2(827, 220), 315));
		dirRects.addElement(new RectFree(80, 93, new Vector2(821, 143), 225));
	}

	private void initMap_4() {
		finishRect = new RectFree(18, 99, new Vector2(624, 533), 180);

		rects.addElement(new Rect(422, 397, 318, 86));
		rects.addElement(new Rect(476, 583, 273, 57));
		rects.addElement(new Rect(288, 109, 34, 254));
		rects.addElement(new Rect(422, 136, 109, 279));
		rects.addElement(new Rect(634, 102, 37, 163));
		rects.addElement(new Rect(881, 320, 48, 207));
		rects.addElement(new Rect(768, 285, 9, 178));
		rects.addElement(new Rect(350, 0, 286, 34));

		freeRects.addElement(new RectFree(120, 40, new Vector2(204, 484), 65));
		freeRects.addElement(new RectFree(80, 40, new Vector2(254, 558), 35));

		circles.addElement(new Circle(458, 580, 99, Circle.OUT_DETECT, 210,
				270, 120));
		circles.addElement(new Circle(482, 611, 26, Circle.IN_DETECT, 210, 270,
				120));
		circles.addElement(new Circle(287, 452, 239, Circle.OUT_DETECT, 45, 180));
		circles.addElement(new Circle(311, 538, 45, Circle.IN_DETECT, 210, 270));
		circles.addElement(new Circle(180, 421, 127, Circle.OUT_DETECT, 180,
				315, 147));
		circles.addElement(new Circle(180, 421, 15, Circle.IN_DETECT, 180, 315));
		circles.addElement(new Circle(307, 375, 124, Circle.OUT_DETECT, 0, 135,
				144));
		circles.addElement(new Circle(307, 375, 15, Circle.IN_DETECT, 0, 135));
		circles.addElement(new Circle(445, 160, 128, Circle.OUT_DETECT, 180,
				270));
		circles.addElement(new Circle(504, 165, 133, Circle.OUT_DETECT, 270,
				360));
		circles.addElement(new Circle(635, 265, 108, Circle.OUT_DETECT, 90,
				180, 128));
		circles.addElement(new Circle(664, 267, 108, Circle.OUT_DETECT, 0, 90,
				128));
		circles.addElement(new Circle(793, 247, 123, Circle.OUT_DETECT, 180,
				45, 143));
		circles.addElement(new Circle(783, 258, 10, Circle.IN_DETECT, 180, 45));
		circles.addElement(new Circle(755, 456, 128, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(755, 456, 20, Circle.IN_DETECT, 0, 90));

		dirRects.addElement(new RectFree(197, 100, new Vector2(535, 535), 180));
		dirRects.addElement(new RectFree(172, 100, new Vector2(702, 535), 180));
		dirRects.addElement(new RectFree(150, 100, new Vector2(397, 587), 135));
		dirRects.addElement(new RectFree(150, 100, new Vector2(283, 642), 180));
		dirRects.addElement(new RectFree(350, 100, new Vector2(142, 544), 225));
		dirRects.addElement(new RectFree(100, 100, new Vector2(109, 419), 270));
		dirRects.addElement(new RectFree(100, 100, new Vector2(177, 352), 0));
		dirRects.addElement(new RectFree(150, 100, new Vector2(246, 394), 45));
		dirRects.addElement(new RectFree(100, 100, new Vector2(310, 443), 0));
		dirRects.addElement(new RectFree(100, 100, new Vector2(353, 427), 315));
		dirRects.addElement(new RectFree(294, 100, new Vector2(373, 242), 270));
		dirRects.addElement(new RectFree(313, 100, new Vector2(478, 86), 0));
		dirRects.addElement(new RectFree(162, 100, new Vector2(584, 186), 90));
		dirRects.addElement(new RectFree(100, 100, new Vector2(596, 300), 45));
		dirRects.addElement(new RectFree(100, 100, new Vector2(652, 318), 0));
		dirRects.addElement(new RectFree(200, 100, new Vector2(730, 229), 315));
		dirRects.addElement(new RectFree(150, 100, new Vector2(835, 203), 45));
		dirRects.addElement(new RectFree(100, 100, new Vector2(845, 281), 135));
		dirRects.addElement(new RectFree(186, 100, new Vector2(830, 375), 90));
		dirRects.addElement(new RectFree(150, 100, new Vector2(807, 515), 135));
	}

	private void initMap_5() {
		finishRect = new RectFree(18, 99, new Vector2(580, 495), 180);

		rects.addElement(new Rect(341, 417, 407, 27));
		rects.addElement(new Rect(426, 545, 371, 53));
		rects.addElement(new Rect(249, 217, 88, 203));
		rects.addElement(new Rect(94, 120, 52, 211));
		rects.addElement(new Rect(295, 163, 70, 59));
		rects.addElement(new Rect(588, 143, 222, 47));
		rects.addElement(new Rect(201, 7, 265, 53));

		freeRects.addElement(new RectFree(100, 60, new Vector2(519, 234), 135));
		freeRects.addElement(new RectFree(80, 40, new Vector2(633, 360), 135));

		circles.addElement(new Circle(406, 705, 259, Circle.OUT_DETECT, 225,
				270, 279));
		circles.addElement(new Circle(416, 705, 160, Circle.IN_DETECT, 225, 270));
		circles.addElement(new Circle(164, 542, 132, Circle.OUT_DETECT, 45, 225));
		circles.addElement(new Circle(164, 542, 20, Circle.IN_DETECT, 45, 225));
		circles.addElement(new Circle(-100, 280, 349, Circle.OUT_DETECT, 0, 45,
				369));
		circles.addElement(new Circle(-100, 270, 249, Circle.IN_DETECT, 0, 45));
		circles.addElement(new Circle(279, 191, 123, Circle.OUT_DETECT, 180,
				270));
		circles.addElement(new Circle(300, 222, 54, Circle.IN_DETECT, 180, 270));
		circles.addElement(new Circle(373, 187, 128, Circle.OUT_DETECT, 270,
				360));
		circles.addElement(new Circle(353, 185, 26, Circle.IN_DETECT, 270, 45));
		circles.addElement(new Circle(488, 268, 148, Circle.OUT_DETECT, 45,
				225, 168));
		circles.addElement(new Circle(488, 268, 24, Circle.IN_DETECT, 45, 225));
		circles.addElement(new Circle(736, 371, 193, Circle.OUT_DETECT, 270, 90));
		circles.addElement(new Circle(748, 373, 65, Circle.IN_DETECT, 45, 225));
		circles.addElement(new Circle(724, 358, 80, Circle.IN_DETECT, 45, 225));

		dirRects.addElement(new RectFree(202, 100, new Vector2(489, 497), 180));
		dirRects.addElement(new RectFree(219, 100, new Vector2(683, 497), 180));
		dirRects.addElement(new RectFree(200, 100, new Vector2(297, 530), 135));
		dirRects.addElement(new RectFree(150, 100, new Vector2(102, 588), 225));
		dirRects.addElement(new RectFree(340, 140, new Vector2(164, 397), 290));
		dirRects.addElement(new RectFree(217, 101, new Vector2(198, 262), 270));
		dirRects.addElement(new RectFree(217, 101, new Vector2(329, 108), 0));
		dirRects.addElement(new RectFree(160, 101, new Vector2(425, 206), 100));
		dirRects.addElement(new RectFree(210, 100, new Vector2(447, 348), 45));
		dirRects.addElement(new RectFree(210, 100, new Vector2(590, 294), 315));
		dirRects.addElement(new RectFree(150, 100, new Vector2(715, 230), 0));
		dirRects.addElement(new RectFree(150, 100, new Vector2(846, 305), 45));
		dirRects.addElement(new RectFree(150, 100, new Vector2(869, 308), 90));
		dirRects.addElement(new RectFree(150, 100, new Vector2(821, 472), 135));
	}

	private void initMap_6() {
		finishRect = new RectFree(19, 99, new Vector2(543, 218), 0);

		rects.addElement(new Rect(384, 313, 321, 127));
		rects.addElement(new Rect(803, 262, 35, 170));
		rects.addElement(new Rect(933, 252, 27, 240));
		rects.addElement(new Rect(51, 244, 107, 187));
		rects.addElement(new Rect(253, 269, 34, 292));
		rects.addElement(new Rect(204, 543, 156, 20));
		rects.addElement(new Rect(231, 659, 88, 67));
		rects.addElement(new Rect(369, -34, 365, 50));
		rects.addElement(new Rect(475, 98, 134, 74));

		freeRects.addElement(new RectFree(160, 60, new Vector2(620, 312), 11));
		freeRects.addElement(new RectFree(160, 60, new Vector2(470, 312), 169));
		freeRects.addElement(new RectFree(100, 70, new Vector2(679, 155), 15));
		freeRects.addElement(new RectFree(90, 50, new Vector2(763, 185), 45));
		freeRects.addElement(new RectFree(100, 70, new Vector2(399, 150), 165));
		freeRects.addElement(new RectFree(80, 60, new Vector2(332, 181), 135));
		freeRects.addElement(new RectFree(60, 50, new Vector2(806, 241), 65));
		freeRects.addElement(new RectFree(60, 50, new Vector2(286, 235), 115));

		circles.addElement(new Circle(687, 318, 121, Circle.OUT_DETECT, 315,
				360, 151));
		circles.addElement(new Circle(822, 427, 130, Circle.OUT_DETECT, 0, 180));
		circles.addElement(new Circle(822, 427, 20, Circle.IN_DETECT, 225, 270));
		circles.addElement(new Circle(675, 280, 256, Circle.OUT_DETECT, 270,
				360));
		circles.addElement(new Circle(414, 274, 256, Circle.OUT_DETECT, 180,
				270));
		circles.addElement(new Circle(159, 454, 100, Circle.OUT_DETECT, 0, 90,
				142));
		circles.addElement(new Circle(134, 429, 22, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(147, 577, 124, Circle.OUT_DETECT, 45, 270));
		circles.addElement(new Circle(147, 570, 24, Circle.IN_DETECT, 45, 270));
		circles.addElement(new Circle(404, 573, 124, Circle.OUT_DETECT, 270,
				135, 300));
		circles.addElement(new Circle(404, 573, 24, Circle.IN_DETECT, 45, 270));
		circles.addElement(new Circle(403, 430, 119, Circle.OUT_DETECT, 90,
				180, 139));
		circles.addElement(new Circle(403, 430, 22, Circle.IN_DETECT, 45, 270));

		dirRects.addElement(new RectFree(197, 100, new Vector2(455, 229), 0));
		dirRects.addElement(new RectFree(221, 100, new Vector2(646, 229), 0));
		dirRects.addElement(new RectFree(100, 100, new Vector2(735, 261), 45));
		dirRects.addElement(new RectFree(172, 100, new Vector2(755, 370), 90));
		dirRects.addElement(new RectFree(150, 100, new Vector2(775, 494), 45));
		dirRects.addElement(new RectFree(150, 100, new Vector2(872, 488), 315));
		dirRects.addElement(new RectFree(229, 100, new Vector2(887, 338), 270));
		dirRects.addElement(new RectFree(229, 100, new Vector2(825, 126), 225));
		dirRects.addElement(new RectFree(409, 100, new Vector2(562, 56), 180));
		dirRects.addElement(new RectFree(229, 100, new Vector2(259, 144), 135));
		dirRects.addElement(new RectFree(251, 100, new Vector2(204, 311), 90));
		dirRects.addElement(new RectFree(175, 100, new Vector2(122, 515), 135));
		dirRects.addElement(new RectFree(80, 100, new Vector2(72, 577), 90));
		dirRects.addElement(new RectFree(80, 100, new Vector2(100, 631), 45));
		dirRects.addElement(new RectFree(143, 100, new Vector2(175, 639), 315));
		dirRects.addElement(new RectFree(160, 100, new Vector2(274, 610), 0));
		dirRects.addElement(new RectFree(40, 100, new Vector2(387, 639), 45));
		dirRects.addElement(new RectFree(40, 100, new Vector2(409, 648), 0));
		dirRects.addElement(new RectFree(148, 100, new Vector2(478, 578), 270));
		dirRects.addElement(new RectFree(181, 100, new Vector2(388, 500), 180));
		dirRects.addElement(new RectFree(190, 100, new Vector2(334, 365), 270));
		dirRects.addElement(new RectFree(100, 100, new Vector2(373, 250), 315));

	}

	private void initMap_7() {
		finishRect = new RectFree(18, 98, new Vector2(172, 379), 350);

		rects.addElement(new Rect(457, 645, 299, 67));
		rects.addElement(new Rect(705, 284, 129, 104));
		rects.addElement(new Rect(731, 116, 163, 79));
		rects.addElement(new Rect(-39, 291, 50, 134));

		freeRects.addElement(new RectFree(208, 9, new Vector2(146, 437), 355));
		freeRects.addElement(new RectFree(162, 16, new Vector2(206, 316), 355));
		freeRects.addElement(new RectFree(110, 18, new Vector2(237, 563), 26));
		freeRects.addElement(new RectFree(225, 85, new Vector2(200, 708), 26));
		freeRects.addElement(new RectFree(173, 27, new Vector2(352, 508), 112));
		freeRects.addElement(new RectFree(21, 143, new Vector2(436, 626), 24));
		freeRects.addElement(new RectFree(144, 81, new Vector2(632, 519), 350));
		freeRects.addElement(new RectFree(82, 64, new Vector2(699, 502), 315));
		freeRects.addElement(new RectFree(71, 93, new Vector2(771, 641), 57));
		freeRects.addElement(new RectFree(51, 153, new Vector2(780, 417), 13));
		freeRects.addElement(new RectFree(52, 76, new Vector2(808, 386), 40));
		freeRects.addElement(new RectFree(55, 51, new Vector2(832, 353), 26));
		freeRects.addElement(new RectFree(70, 56, new Vector2(820, 324), 338));
		freeRects.addElement(new RectFree(176, 85, new Vector2(686, 108), 40));
		freeRects.addElement(new RectFree(116, 99, new Vector2(551, 245), 44));
		freeRects.addElement(new RectFree(105, 88, new Vector2(666, 316), 15));
		freeRects.addElement(new RectFree(66, 87, new Vector2(504, 199), 25));
		freeRects.addElement(new RectFree(196, 71, new Vector2(400, 176), 4));
		freeRects.addElement(new RectFree(267, 37, new Vector2(483, 32), 5));
		freeRects.addElement(new RectFree(26, 59, new Vector2(274, 147), 15));
		freeRects.addElement(new RectFree(60, 130, new Vector2(137, 112), 22));
		freeRects.addElement(new RectFree(65, 142, new Vector2(60, 206), 51));
		freeRects.addElement(new RectFree(53, 132, new Vector2(236, 255), 34));
		freeRects.addElement(new RectFree(107, 15, new Vector2(167, 300), 327));
		freeRects.addElement(new RectFree(41, 56, new Vector2(911, 476), 23));
		freeRects.addElement(new RectFree(98, 51, new Vector2(599, 63), 30));
		freeRects.addElement(new RectFree(83, 98, new Vector2(931, 188), 318));
		freeRects.addElement(new RectFree(12, 84, new Vector2(460, 563), 13));
		freeRects.addElement(new RectFree(66, 27, new Vector2(321, 13), 21));

		circles.addElement(new Circle(253, 430, 108, Circle.OUT_DETECT, 270,
				90, 138));
		circles.addElement(new Circle(248, 430, 5, Circle.IN_DETECT, 270, 90));
		circles.addElement(new Circle(183, 538, 104, Circle.OUT_DETECT, 90,
				270, 114));
		circles.addElement(new Circle(194, 541, 10, Circle.IN_DETECT, 270, 90));
		circles.addElement(new Circle(305, 563, 136, Circle.OUT_DETECT, 45, 135));
		circles.addElement(new Circle(302, 566, 34, Circle.IN_DETECT, 45, 135));
		circles.addElement(new Circle(468, 520, 94, Circle.OUT_DETECT, 225,
				360, 114));
		circles.addElement(new Circle(567, 556, 94, Circle.OUT_DETECT, 90, 180,
				114));
		circles.addElement(new Circle(808, 506, 79, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(776, 339, 176, Circle.OUT_DETECT, 330, 30));
		circles.addElement(new Circle(263, 96, 87, Circle.OUT_DETECT, 180, 315));
		circles.addElement(new Circle(94, 354, 83, Circle.OUT_DETECT, 90, 180));
		circles.addElement(new Circle(103, 331, 94, Circle.OUT_DETECT, 180, 225));

		dirRects.addElement(new RectFree(90, 97, new Vector2(133, 382), 0));
		dirRects.addElement(new RectFree(117, 97, new Vector2(223, 375), 0));
		dirRects.addElement(new RectFree(80, 97, new Vector2(292, 387), 45));
		dirRects.addElement(new RectFree(80, 97, new Vector2(303, 427), 90));
		dirRects.addElement(new RectFree(200, 97, new Vector2(231, 486), 180));
		dirRects.addElement(new RectFree(90, 97, new Vector2(134, 527), 90));
		dirRects.addElement(new RectFree(300, 97, new Vector2(217, 617), 45));
		dirRects.addElement(new RectFree(100, 97, new Vector2(321, 650), 0));
		dirRects.addElement(new RectFree(198, 97, new Vector2(401, 555), 294));
		dirRects.addElement(new RectFree(80, 97, new Vector2(465, 477), 0));
		dirRects.addElement(new RectFree(150, 97, new Vector2(517, 546), 90));
		dirRects.addElement(new RectFree(80, 97, new Vector2(531, 593), 45));
		dirRects.addElement(new RectFree(200, 97, new Vector2(620, 610), 0));
		dirRects.addElement(new RectFree(120, 97, new Vector2(764, 559), 315));
		dirRects.addElement(new RectFree(120, 97, new Vector2(839, 479), 270));
		dirRects.addElement(new RectFree(80, 97, new Vector2(866, 424), 292));
		dirRects.addElement(new RectFree(200, 97, new Vector2(902, 316), 250));
		dirRects.addElement(new RectFree(179, 97, new Vector2(786, 241), 180));
		dirRects.addElement(new RectFree(469, 169, new Vector2(518, 140), 202));
		dirRects.addElement(new RectFree(330, 139, new Vector2(171, 160), 113));
		dirRects.addElement(new RectFree(90, 97, new Vector2(64, 343), 90));
		dirRects.addElement(new RectFree(90, 97, new Vector2(80, 384), 45));

	}

	private void initMap_8() {
		finishRect = new RectFree(18, 98, new Vector2(673, 634), 354);

		freeRects.addElement(new RectFree(336, 100, new Vector2(182, 343), 32));
		freeRects.addElement(new RectFree(506, 38, new Vector2(358, 251), 31));
		freeRects.addElement(new RectFree(577, 96, new Vector2(461, 117), 30));
		freeRects.addElement(new RectFree(201, 75, new Vector2(369, 526), 70));
		freeRects.addElement(new RectFree(90, 39, new Vector2(467, 327), 46));
		freeRects.addElement(new RectFree(138, 57, new Vector2(519, 442), 76));
		freeRects.addElement(new RectFree(53, 49, new Vector2(546, 523), 50));
		freeRects.addElement(new RectFree(92, 33, new Vector2(605, 559), 15));
		freeRects.addElement(new RectFree(20, 62, new Vector2(820, 451), 7));
		freeRects.addElement(new RectFree(19, 56, new Vector2(795, 512), 31));
		freeRects.addElement(new RectFree(15, 54, new Vector2(755, 553), 57));
		freeRects.addElement(new RectFree(73, 17, new Vector2(690, 572), 352));
		freeRects.addElement(new RectFree(102, 22, new Vector2(619, 567), 10));

		circles.addElement(new Circle(681, 428, 254, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(627, 444, 240, Circle.OUT_DETECT, 80, 175));
		circles.addElement(new Circle(819, 409, 113, Circle.OUT_DETECT, 170,
				360, 133));
		circles.addElement(new Circle(823, 416, 14, Circle.IN_DETECT, 170, 360));
		circles.addElement(new Circle(689, 444, 118, Circle.OUT_DETECT, 350,
				190, 133));
		circles.addElement(new Circle(691, 439, 17, Circle.IN_DETECT, 170, 360));
		circles.addElement(new Circle(583, 362, 108, Circle.OUT_DETECT, 300,
				360, 128));
		circles.addElement(new Circle(567, 381, 24, Circle.IN_DETECT, 170, 360));
		circles.addElement(new Circle(153, 132, 118, Circle.OUT_DETECT, 120,
				300));
		circles.addElement(new Circle(292, 443, 81, Circle.IN_DETECT, 170, 360));
		circles.addElement(new Circle(3337, 429, 145, Circle.OUT_DETECT, 300,
				360, 175));

		dirRects.addElement(new RectFree(132, 100, new Vector2(616, 627), 0));
		dirRects.addElement(new RectFree(134, 100, new Vector2(734, 630), 0));
		dirRects.addElement(new RectFree(170, 94, new Vector2(791, 598), 315));
		dirRects.addElement(new RectFree(170, 94, new Vector2(881, 444), 270));
		dirRects.addElement(new RectFree(153, 94, new Vector2(818, 358), 180));
		dirRects.addElement(new RectFree(153, 94, new Vector2(758, 443), 90));
		dirRects.addElement(new RectFree(211, 94, new Vector2(673, 510), 180));
		dirRects.addElement(new RectFree(184, 94, new Vector2(631, 418), 270));
		dirRects.addElement(new RectFree(514, 98, new Vector2(400, 195), 203));
		dirRects.addElement(new RectFree(210, 98, new Vector2(104, 86), 113));
		dirRects.addElement(new RectFree(337, 98, new Vector2(257, 270), 23));
		dirRects.addElement(new RectFree(218, 98, new Vector2(431, 449), 68));
		dirRects.addElement(new RectFree(140, 98, new Vector2(503, 588), 45));

	}

	private void initMap_9() {
		num_cover = 1;
		cover_point = new int[2];
		cover_point[0] = 313;
		cover_point[1] = 155;

		coverRects = new RectFree[1];
		coverRects[0] = new RectFree(92, 89, new Vector2(315, 152), 270);
		upFloorRect = new RectFree(20, 89, new Vector2(315, 360), 270);
		downFloorRect = new RectFree(20, 89, new Vector2(379, 55), 0);

		finishRect = new RectFree(18, 98, new Vector2(359, 550), 0);

		rects.addElement(new Rect(250, 598, 198, 40));
		rects.addElement(new Rect(549, 441, 216, 45));
		rects.addElement(new Rect(602, 300, 238, 55));
		rects.addElement(new Rect(359, 199, 31, 221));
		rects.addElement(new Rect(241, 195, 29, 255));
		rects.addElement(new Rect(238, 197, 169, 31));
		rects.addElement(new Rect(213, 41, 156, 68));
		rects.addElement(new Rect(477, 693, 158, 48));
		rects.addElement(new Rect(480, 369, 28, 113));
		rects.addElement(new Rect(175, 365, 33, 70));
		rects.addElement(new Rect(303, 500, 168, 5));

		rects_f1.addElement(new Rect(218, 76, 54, 332));
		rects_f1.addElement(new Rect(360, 108, 20, 300));
		circles_f1.addElement(new Circle(365, 107, 100, Circle.OUT_DETECT, 180,
				90, 140));

		freeRects.addElement(new RectFree(33, 97, new Vector2(219, 333), 35));
		freeRects.addElement(new RectFree(57, 17, new Vector2(267, 488), 14));
		freeRects.addElement(new RectFree(30, 89, new Vector2(651, 670), 20));

		circles.addElement(new Circle(468, 583, 78, Circle.OUT_DETECT, 270,
				360, 98));
		circles.addElement(new Circle(436, 618, 21, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(552, 604, 100, Circle.OUT_DETECT, 20, 180));
		circles.addElement(new Circle(646, 600, 89, Circle.OUT_DETECT, 180,
				300, 109));
		circles.addElement(new Circle(731, 542, 96, Circle.OUT_DETECT, 60, 135,
				106));
		circles.addElement(new Circle(723, 481, 155, Circle.OUT_DETECT, 300, 90));
		circles.addElement(new Circle(657, 613, 8, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(733, 493, 47, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(754, 475, 33, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(493, 355, 106, Circle.OUT_DETECT, 180,
				360, 126));
		circles.addElement(new Circle(582, 372, 72, Circle.OUT_DETECT, 90, 180,
				92));
		circles.addElement(new Circle(493, 356, 10, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(414, 434, 68, Circle.OUT_DETECT, 0, 90,
				68));
		circles.addElement(new Circle(339, 438, 68, Circle.OUT_DETECT, 90, 180,
				68));
		circles.addElement(new Circle(376, 399, 108, Circle.OUT_DETECT, 82, 98,
				108));
		circles.addElement(new Circle(365, 107, 100, Circle.OUT_DETECT, 180,
				90, 140));
		circles.addElement(new Circle(253, 206, 100, Circle.OUT_DETECT, 170,
				270));
		circles.addElement(new Circle(161, 247, 79, Circle.OUT_DETECT, 0, 60,
				99));
		circles.addElement(new Circle(277, 402, 199, Circle.OUT_DETECT, 90, 225));
		circles.addElement(new Circle(231, 448, 35, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(364, 744, 246, Circle.OUT_DETECT, 265,
				275, 246));

		dirRects.addElement(new RectFree(106, 96, new Vector2(315, 552), 0));
		dirRects.addElement(new RectFree(119, 96, new Vector2(410, 552), 0));
		dirRects.addElement(new RectFree(80, 96, new Vector2(487, 567), 45));
		dirRects.addElement(new RectFree(80, 96, new Vector2(498, 610), 90));
		dirRects.addElement(new RectFree(150, 96, new Vector2(554, 646), 0));
		dirRects.addElement(new RectFree(150, 96, new Vector2(610, 596), 315));
		dirRects.addElement(new RectFree(150, 96, new Vector2(683, 569), 23));
		dirRects.addElement(new RectFree(150, 96, new Vector2(777, 571), 330));
		dirRects.addElement(new RectFree(120, 96, new Vector2(829, 479), 270));
		dirRects.addElement(new RectFree(80, 96, new Vector2(808, 415), 225));
		dirRects.addElement(new RectFree(232, 96, new Vector2(690, 400), 180));
		dirRects.addElement(new RectFree(150, 96, new Vector2(552, 351), 247));
		dirRects.addElement(new RectFree(130, 96, new Vector2(436, 369), 90));
		dirRects.addElement(new RectFree(160, 96, new Vector2(373, 469), 180));
		dirRects.addElement(new RectFree(63, 96, new Vector2(317, 383), 270));
		dirRects.addElement(new RectFree(80, 96, new Vector2(337, 63), 315));
		dirRects.addElement(new RectFree(80, 96, new Vector2(400, 72), 45));
		dirRects.addElement(new RectFree(80, 96, new Vector2(407, 139), 135));
		dirRects.addElement(new RectFree(174, 96, new Vector2(304, 155), 180));
		dirRects.addElement(new RectFree(251, 105, new Vector2(167, 286), 135));
		dirRects.addElement(new RectFree(155, 88, new Vector2(211, 522), 45));
		dirRects.addElement(new RectFree(100, 96, new Vector2(137, 458), 90));

		dirRects_f1
				.addElement(new RectFree(251, 96, new Vector2(314, 226), 270));
		dirRects_f1.addElement(new RectFree(90, 96, new Vector2(327, 68), 315));
	}

	private void initMap_10() {
		num_cover = 1;
		cover_point = new int[2];
		cover_point[0] = 799;
		cover_point[1] = 476;

		coverRects = new RectFree[1];
		coverRects[0] = new RectFree(109, 89, new Vector2(800, 474), 90);
		upFloorRect = new RectFree(20, 89, new Vector2(802, 223), 90);
		downFloorRect = new RectFree(20, 89, new Vector2(852, 588), 0);

		finishRect = new RectFree(18, 98, new Vector2(404, 242), 0);

		rects.addElement(new Rect(212, 137, 350, 64));
		rects.addElement(new Rect(219, 285, 311, 114));
		rects.addElement(new Rect(293, 483, 94, 46));
		rects.addElement(new Rect(845, 189, 52, 237));
		rects.addElement(new Rect(730, 208, 24, 45));

		rects_f1.addElement(new Rect(720, 205, 34, 327));
		rects_f1.addElement(new Rect(845, 189, 14, 357));

		freeRects.addElement(new RectFree(183, 61, new Vector2(509, 422), 32));
		freeRects.addElement(new RectFree(139, 69, new Vector2(424, 558), 41));
		freeRects.addElement(new RectFree(212, 45, new Vector2(216, 559), 324));
		freeRects.addElement(new RectFree(176, 49, new Vector2(824, 410), 10));
		freeRects.addElement(new RectFree(114, 56, new Vector2(792, 556), 7));
		freeRects.addElement(new RectFree(145, 38, new Vector2(557, 607), 12));

		circles.addElement(new Circle(551, 269, 69, Circle.OUT_DETECT, 270,
				360, 79));
		circles.addElement(new Circle(644, 300, 102, Circle.OUT_DETECT, 315,
				180, 112));
		circles.addElement(new Circle(521, 304, 18, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(639, 288, 13, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(737, 219, 112, Circle.OUT_DETECT, 135,
				360, 122));
		circles.addElement(new Circle(733, 216, 22, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(852, 535, 101, Circle.OUT_DETECT, 270,
				180, 130));
		circles.addElement(new Circle(111, 488, 100, Circle.OUT_DETECT, 45,
				270, 130));
		circles.addElement(new Circle(116, 483, 17, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(123, 367, 100, Circle.OUT_DETECT, 270,
				90, 100));
		circles.addElement(new Circle(111, 370, 20, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(120, 254, 104, Circle.OUT_DETECT, 90,
				335, 134));
		circles.addElement(new Circle(114, 248, 18, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(209, 210, 78, Circle.OUT_DETECT, 90, 150,
				78));
		circles.addElement(new Circle(296, 574, 175, Circle.OUT_DETECT, 210,
				270, 175));
		circles.addElement(new Circle(593, 426, 165, Circle.OUT_DETECT, 30, 90,
				195));
		circles.addElement(new Circle(601, 447, 51, Circle.IN_DETECT, 315, 180));
		circles.addElement(new Circle(772, 579, 162, Circle.OUT_DETECT, 240,
				265, 200));

		circles_f1.addElement(new Circle(852, 535, 101, Circle.OUT_DETECT, 270,
				180, 130));

		dirRects.addElement(new RectFree(208, 88, new Vector2(310, 241), 0));
		dirRects.addElement(new RectFree(150, 88, new Vector2(472, 241), 0));
		dirRects.addElement(new RectFree(60, 88, new Vector2(561, 251), 45));
		dirRects.addElement(new RectFree(80, 88, new Vector2(586, 308), 90));
		dirRects.addElement(new RectFree(80, 88, new Vector2(647, 349), 0));
		dirRects.addElement(new RectFree(100, 88, new Vector2(696, 309), 270));
		dirRects.addElement(new RectFree(80, 88, new Vector2(685, 253), 250));
		dirRects.addElement(new RectFree(100, 88, new Vector2(683, 178), 315));
		dirRects.addElement(new RectFree(100, 88, new Vector2(780, 167), 45));
		dirRects.addElement(new RectFree(60, 88, new Vector2(817, 576), 45));
		dirRects.addElement(new RectFree(100, 88, new Vector2(852, 591), 0));
		dirRects.addElement(new RectFree(100, 88, new Vector2(902, 537), 270));
		dirRects.addElement(new RectFree(159, 88, new Vector2(813, 481), 180));
		dirRects.addElement(new RectFree(130, 88, new Vector2(687, 503), 135));
		dirRects.addElement(new RectFree(153, 88, new Vector2(585, 547), 180));
		dirRects.addElement(new RectFree(153, 88, new Vector2(475, 495), 225));
		dirRects.addElement(new RectFree(153, 88, new Vector2(343, 441), 180));
		dirRects.addElement(new RectFree(217, 88, new Vector2(198, 497), 135));
		dirRects.addElement(new RectFree(80, 88, new Vector2(106, 544), 180));
		dirRects.addElement(new RectFree(150, 88, new Vector2(60, 491), 270));
		dirRects.addElement(new RectFree(80, 88, new Vector2(71, 451), 315));
		dirRects.addElement(new RectFree(80, 88, new Vector2(115, 428), 0));
		dirRects.addElement(new RectFree(80, 88, new Vector2(156, 417), 315));
		dirRects.addElement(new RectFree(130, 88, new Vector2(175, 365), 270));
		dirRects.addElement(new RectFree(150, 88, new Vector2(113, 310), 180));
		dirRects.addElement(new RectFree(150, 88, new Vector2(59, 248), 270));
		dirRects.addElement(new RectFree(80, 88, new Vector2(107, 191), 0));
		dirRects.addElement(new RectFree(120, 88, new Vector2(168, 221), 45));

		dirRects_f1
				.addElement(new RectFree(100, 88, new Vector2(799, 360), 45));

	}

	private void initMap_11() {
		finishRect = new RectFree(18, 98, new Vector2(270, 228), 0);

		rects.addElement(new Rect(68, 98, 719, 82));
		rects.addElement(new Rect(204, 274, 189, 160));
		rects.addElement(new Rect(362, 543, 325, 108));
		rects.addElement(new Rect(339, 425, 336, 11));
		rects.addElement(new Rect(112, 276, 97, 10));
		rects.addElement(new Rect(952, 197, 50, 130));

		circles.addElement(new Circle(534, 301, 127, Circle.OUT_DETECT, 90,
				180, 137));
		circles.addElement(new Circle(410, 273, 92, Circle.OUT_DETECT, 270,
				360, 142));
		circles.addElement(new Circle(384, 293, 23, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(559, 349, 79, Circle.OUT_DETECT, 0, 90,
				89));
		circles.addElement(new Circle(651, 317, 111, Circle.OUT_DETECT, 180,
				320, 141));
		circles.addElement(new Circle(527, 312, 14, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(655, 322, 15, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(757, 282, 86, Circle.OUT_DETECT, 45, 135,
				106));
		circles.addElement(new Circle(858, 257, 96, Circle.OUT_DETECT, 180,
				360, 126));
		circles.addElement(new Circle(910, 342, 76, Circle.OUT_DETECT, 135,
				180, 86));
		circles.addElement(new Circle(862, 281, 92, Circle.OUT_DETECT, 0, 30));
		circles.addElement(new Circle(855, 404, 107, Circle.OUT_DETECT, 315,
				120));
		circles.addElement(new Circle(781, 472, 92, Circle.OUT_DETECT, 210,
				300, 112));
		circles.addElement(new Circle(678, 420, 124, Circle.OUT_DETECT, 30, 90));
		circles.addElement(new Circle(791, 516, 27, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(671, 392, 42, Circle.IN_DETECT, 270, 360));
		circles.addElement(new Circle(156, 537, 112, Circle.OUT_DETECT, 45, 180));
		circles.addElement(new Circle(171, 518, 20, Circle.IN_DETECT, 45, 180));
		circles.addElement(new Circle(341, 603, 162, Circle.OUT_DETECT, 210,
				270, 202));
		circles.addElement(new Circle(104, 382, 100, Circle.OUT_DETECT, 270,
				360, 100));
		circles.addElement(new Circle(51, 381, 150, Circle.OUT_DETECT, 0, 40,
				190));
		circles.addElement(new Circle(272, 541, 220, Circle.OUT_DETECT, 180,
				210));
		circles.addElement(new Circle(112, 279, 108, Circle.OUT_DETECT, 100,
				270));
		circles.addElement(new Circle(86, 392, 15, Circle.IN_DETECT, 45, 180));

		dirRects.addElement(new RectFree(201, 97, new Vector2(178, 227), 0));
		dirRects.addElement(new RectFree(157, 97, new Vector2(339, 227), 0));
		dirRects.addElement(new RectFree(80, 97, new Vector2(442, 246), 45));
		dirRects.addElement(new RectFree(120, 97, new Vector2(477, 346), 45));
		dirRects.addElement(new RectFree(100, 97, new Vector2(538, 377), 45));
		dirRects.addElement(new RectFree(80, 97, new Vector2(571, 370), 315));
		dirRects.addElement(new RectFree(100, 97, new Vector2(595, 320), 270));
		dirRects.addElement(new RectFree(120, 97, new Vector2(650, 263), 0));
		dirRects.addElement(new RectFree(120, 97, new Vector2(718, 292), 45));
		dirRects.addElement(new RectFree(150, 97, new Vector2(803, 278), 315));
		dirRects.addElement(new RectFree(300, 114, new Vector2(896, 328), 90));
		dirRects.addElement(new RectFree(168, 97, new Vector2(802, 443), 180));
		dirRects.addElement(new RectFree(404, 97, new Vector2(543, 491), 180));
		dirRects.addElement(new RectFree(200, 97, new Vector2(251, 544), 135));
		dirRects.addElement(new RectFree(158, 97, new Vector2(119, 563), 225));
		dirRects.addElement(new RectFree(134, 127, new Vector2(122, 459), 270));
		dirRects.addElement(new RectFree(60, 97, new Vector2(133, 353), 225));
		dirRects.addElement(new RectFree(60, 97, new Vector2(110, 331), 180));
		dirRects.addElement(new RectFree(150, 97, new Vector2(64, 280), 270));

	}

	private void initMap_12() {
		num_cover = 1;
		cover_point = new int[2];
		cover_point[0] = 525;
		cover_point[1] = 387;

		coverRects = new RectFree[1];
		coverRects[0] = new RectFree(101, 89, new Vector2(524, 386), 45);
		upFloorRect = new RectFree(20, 90, new Vector2(377, 254), 45);
		downFloorRect = new RectFree(20, 89, new Vector2(631, 492), 45);

		finishRect = new RectFree(18, 98, new Vector2(217, 230), 0);

		rects.addElement(new Rect(64, 56, 324, 127));
		rects.addElement(new Rect(144, 279, 165, 60));
		rects.addElement(new Rect(745, 199, 88, 217));
		rects.addElement(new Rect(930, 170, 88, 267));

		freeRects.addElement(new RectFree(152, 63, new Vector2(418, 189), 28));
		freeRects.addElement(new RectFree(101, 67, new Vector2(337, 331), 30));
		freeRects.addElement(new RectFree(32, 418, new Vector2(472, 351), 45));
		freeRects.addElement(new RectFree(57, 450, new Vector2(632, 388), 45));
		freeRects.addElement(new RectFree(63, 143, new Vector2(193, 541), 345));
		freeRects.addElement(new RectFree(143, 68, new Vector2(631, 136), 295));
		freeRects.addElement(new RectFree(56, 29, new Vector2(327, 513), 73));
		freeRects.addElement(new RectFree(32, 83, new Vector2(360, 492), 11));

		freeRects_f1
				.addElement(new RectFree(383, 66, new Vector2(558, 307), 45));
		freeRects_f1
				.addElement(new RectFree(298, 68, new Vector2(482, 460), 45));
		freeRects_f1
				.addElement(new RectFree(170, 50, new Vector2(651, 586), 24));

		circles.addElement(new Circle(745, 399, 181, Circle.OUT_DETECT, 0, 120));
		circles.addElement(new Circle(751, 415, 78, Circle.IN_DETECT, 0, 120));
		circles.addElement(new Circle(795, 188, 136, Circle.OUT_DETECT, 210,
				360));
		circles.addElement(new Circle(786, 206, 43, Circle.IN_DETECT, 0, 120));
		circles.addElement(new Circle(341, 541, 116, Circle.OUT_DETECT, 0, 180,
				166));
		circles.addElement(new Circle(343, 529, 26, Circle.IN_DETECT, 0, 120));
		circles.addElement(new Circle(149, 501, 161, Circle.OUT_DETECT, 270,
				360, 200));
		circles.addElement(new Circle(137, 510, 77, Circle.IN_DETECT, 0, 120));
		circles.addElement(new Circle(139, 307, 130, Circle.OUT_DETECT, 90, 270));
		circles.addElement(new Circle(142, 309, 31, Circle.IN_DETECT, 0, 120));

		dirRects.addElement(new RectFree(121, 97, new Vector2(165, 229), 0));
		dirRects.addElement(new RectFree(126, 97, new Vector2(274, 229), 0));
		dirRects.addElement(new RectFree(114, 132, new Vector2(174, 176), 45));
		dirRects.addElement(new RectFree(127, 109, new Vector2(652, 511), 45));
		dirRects.addElement(new RectFree(127, 97, new Vector2(752, 535), 0));
		dirRects.addElement(new RectFree(127, 97, new Vector2(851, 495), 315));
		dirRects.addElement(new RectFree(200, 97, new Vector2(885, 306), 270));
		dirRects.addElement(new RectFree(100, 97, new Vector2(873, 160), 225));
		dirRects.addElement(new RectFree(100, 97, new Vector2(796, 110), 180));
		dirRects.addElement(new RectFree(120, 97, new Vector2(711, 164), 135));
		dirRects.addElement(new RectFree(400, 97, new Vector2(564, 353), 135));
		dirRects.addElement(new RectFree(130, 97, new Vector2(415, 539), 90));
		dirRects.addElement(new RectFree(80, 97, new Vector2(380, 600), 135));
		dirRects.addElement(new RectFree(80, 97, new Vector2(528, 670), 180));
		dirRects.addElement(new RectFree(200, 97, new Vector2(266, 502), 270));
		dirRects.addElement(new RectFree(80, 97, new Vector2(234, 424), 225));
		dirRects.addElement(new RectFree(120, 97, new Vector2(164, 389), 180));
		dirRects.addElement(new RectFree(80, 97, new Vector2(72, 681), 225));
		dirRects.addElement(new RectFree(80, 97, new Vector2(67, 307), 270));
		dirRects.addElement(new RectFree(80, 97, new Vector2(90, 241), 315));

		dirRects_f1
				.addElement(new RectFree(425, 97, new Vector2(511, 372), 45));

	}

	private void initMap_13() {
		num_cover = 1;
		cover_point = new int[2];
		cover_point[0] = 731;
		cover_point[1] = 163;

		coverRects = new RectFree[1];
		coverRects[0] = new RectFree(86, 94, new Vector2(732, 165), 270);
		upFloorRect = new RectFree(20, 94, new Vector2(735, 359), 270);
		downFloorRect = new RectFree(20, 94, new Vector2(789, 65), 0);

		finishRect = new RectFree(18, 98, new Vector2(550, 161), 180);

		rects.addElement(new Rect(432, 45, 352, 77));
		rects.addElement(new Rect(442, 203, 391, 30));
		rects.addElement(new Rect(503, 395, 198, 50));
		rects.addElement(new Rect(457, 204, 230, 241));
		rects.addElement(new Rect(780, 209, 63, 134));

		rects_f1.addElement(new Rect(619, 74, 67, 306));
		rects_f1.addElement(new Rect(779, 114, 20, 230));

		freeRects.addElement(new RectFree(279, 76, new Vector2(92, 362), 135));
		freeRects.addElement(new RectFree(109, 48, new Vector2(23, 465), 114));
		freeRects.addElement(new RectFree(162, 14, new Vector2(186, 461), 135));
		freeRects.addElement(new RectFree(33, 47, new Vector2(128, 534), 26));
		freeRects.addElement(new RectFree(98, 93, new Vector2(638, 575), 5));
		freeRects.addElement(new RectFree(60, 70, new Vector2(568, 563), 354));
		freeRects.addElement(new RectFree(77, 48, new Vector2(495, 431), 350));
		freeRects.addElement(new RectFree(53, 26, new Vector2(709, 437), 8));
		freeRects.addElement(new RectFree(211, 62, new Vector2(875, 394), 45));
		freeRects.addElement(new RectFree(46, 101, new Vector2(929, 486), 340));
		freeRects.addElement(new RectFree(204, 7, new Vector2(744, 441), 45));
		freeRects.addElement(new RectFree(18, 44, new Vector2(812, 529), 342));

		circles.addElement(new Circle(347, 193, 113, Circle.OUT_DETECT, 90,
				315, 123));
		circles.addElement(new Circle(342, 319, 110, Circle.OUT_DETECT, 270,
				135, 123));
		circles.addElement(new Circle(348, 190, 15, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(336, 317, 15, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(250, 391, 96, Circle.OUT_DETECT, 225,
				315, 116));
		circles.addElement(new Circle(127, 560, 108, Circle.OUT_DETECT, 0, 210,
				148));
		circles.addElement(new Circle(126, 558, 22, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(255, 555, 107, Circle.OUT_DETECT, 180,
				360, 137));
		circles.addElement(new Circle(254, 565, 25, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(383, 570, 100, Circle.OUT_DETECT, 0, 180,
				140));
		circles.addElement(new Circle(381, 551, 24, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(487, 539, 80, Circle.OUT_DETECT, 180,
				270, 120));
		circles.addElement(new Circle(544, 597, 63, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(671, 575, 47, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(710, 539, 88, Circle.OUT_DETECT, 270,
				360, 98));
		circles.addElement(new Circle(798, 585, 79, Circle.OUT_DETECT, 90, 180,
				129));
		circles.addElement(new Circle(812, 556, 108, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(813, 553, 17, Circle.IN_DETECT, 270, 135));
		circles.addElement(new Circle(784, 116, 100, Circle.OUT_DETECT, 180, 90));
		circles.addElement(new Circle(784, 116, 6, Circle.IN_DETECT, 180, 90));
		circles.addElement(new Circle(416, 133, 72, Circle.OUT_DETECT, 80, 135,
				92));

		circles_f1.addElement(new Circle(784, 116, 100, Circle.OUT_DETECT, 180,
				90));
		circles_f1
				.addElement(new Circle(784, 116, 6, Circle.IN_DETECT, 180, 90));

		dirRects.addElement(new RectFree(137, 94, new Vector2(492, 160), 180));
		dirRects.addElement(new RectFree(268, 94, new Vector2(677, 160), 180));
		dirRects.addElement(new RectFree(80, 94, new Vector2(393, 144), 225));
		dirRects.addElement(new RectFree(150, 97, new Vector2(316, 136), 135));
		dirRects.addElement(new RectFree(80, 94, new Vector2(293, 228), 45));
		dirRects.addElement(new RectFree(80, 94, new Vector2(347, 261), 0));
		dirRects.addElement(new RectFree(80, 94, new Vector2(384, 277), 45));
		dirRects.addElement(new RectFree(80, 94, new Vector2(395, 324), 90));
		dirRects.addElement(new RectFree(180, 94, new Vector2(309, 365), 180));
		dirRects.addElement(new RectFree(200, 94, new Vector2(159, 414), 135));
		dirRects.addElement(new RectFree(120, 94, new Vector2(67, 558), 90));
		dirRects.addElement(new RectFree(120, 94, new Vector2(114, 618), 0));
		dirRects.addElement(new RectFree(150, 94, new Vector2(185, 571), 270));
		dirRects.addElement(new RectFree(150, 94, new Vector2(258, 495), 0));
		dirRects.addElement(new RectFree(150, 94, new Vector2(321, 565), 90));
		dirRects.addElement(new RectFree(150, 94, new Vector2(379, 617), 0));
		dirRects.addElement(new RectFree(150, 94, new Vector2(445, 560), 270));
		dirRects.addElement(new RectFree(350, 94, new Vector2(606, 491), 0));
		dirRects.addElement(new RectFree(150, 94, new Vector2(757, 554), 90));
		dirRects.addElement(new RectFree(150, 94, new Vector2(816, 614), 0));
		dirRects.addElement(new RectFree(150, 94, new Vector2(874, 542), 270));
		dirRects.addElement(new RectFree(200, 94, new Vector2(804, 428), 225));
		dirRects.addElement(new RectFree(80, 94, new Vector2(782, 60), 0));
		dirRects.addElement(new RectFree(80, 94, new Vector2(825, 76), 45));
		dirRects.addElement(new RectFree(80, 94, new Vector2(835, 112), 90));
		dirRects.addElement(new RectFree(80, 94, new Vector2(829, 147), 135));

		dirRects_f1
				.addElement(new RectFree(260, 94, new Vector2(736, 233), 270));
		dirRects_f1.addElement(new RectFree(80, 94, new Vector2(753, 77), 315));
	}

	private void initMap_14() {
		finishRect = new RectFree(18, 98, new Vector2(490, 616), 0);

		rects.addElement(new Rect(231, 452, 513, 123));
		rects.addElement(new Rect(82, 659, 762, 75));
		rects.addElement(new Rect(72, 0, 410, 64));
		rects.addElement(new Rect(194, 159, 240, 219));
		rects.addElement(new Rect(-16, 201, 30, 348));
		rects.addElement(new Rect(102, 328, 74, 90));

		freeRects.addElement(new RectFree(31, 273, new Vector2(17, 190), 15));
		freeRects.addElement(new RectFree(39, 177, new Vector2(7, 509), 349));
		freeRects.addElement(new RectFree(61, 111, new Vector2(141, 283), 10));
		freeRects.addElement(new RectFree(68, 90, new Vector2(145, 447), 346));
		freeRects.addElement(new RectFree(28, 188, new Vector2(832, 431), 9));
		freeRects.addElement(new RectFree(84, 250, new Vector2(977, 467), 9));

		circles.addElement(new Circle(786, 523, 137, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(769, 497, 64, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(851, 319, 101, Circle.OUT_DETECT, 180,
				360, 111));
		circles.addElement(new Circle(845, 333, 14, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(732, 333, 98, Circle.OUT_DETECT, 0, 225,
				108));
		circles.addElement(new Circle(735, 327, 13, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(649, 251, 99, Circle.OUT_DETECT, 180, 45,
				105));
		circles.addElement(new Circle(647, 257, 12, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(536, 262, 101, Circle.OUT_DETECT, 0, 225,
				111));
		circles.addElement(new Circle(543, 253, 10, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(454, 173, 106, Circle.OUT_DETECT, 270,
				45, 110));
		circles.addElement(new Circle(436, 186, 26, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(176, 201, 139, Circle.OUT_DETECT, 195,
				270));
		circles.addElement(new Circle(185, 512, 152, Circle.OUT_DETECT, 90, 180));
		circles.addElement(new Circle(214, 254, 94, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(246, 442, 133, Circle.IN_DETECT, 0, 90));

		dirRects.addElement(new RectFree(292, 90, new Vector2(354, 616), 0));
		dirRects.addElement(new RectFree(310, 90, new Vector2(639, 616), 0));
		dirRects.addElement(new RectFree(150, 90, new Vector2(834, 594), 315));
		dirRects.addElement(new RectFree(182, 123, new Vector2(894, 427), 270));
		dirRects.addElement(new RectFree(80, 90, new Vector2(893, 291), 225));
		dirRects.addElement(new RectFree(165, 90, new Vector2(790, 323), 90));
		dirRects.addElement(new RectFree(165, 90, new Vector2(712, 373), 225));
		dirRects.addElement(new RectFree(165, 90, new Vector2(692, 289), 270));
		dirRects.addElement(new RectFree(165, 90, new Vector2(667, 208), 225));
		dirRects.addElement(new RectFree(165, 90, new Vector2(596, 249), 90));
		dirRects.addElement(new RectFree(165, 90, new Vector2(530, 308), 180));
		dirRects.addElement(new RectFree(165, 90, new Vector2(497, 228), 270));
		dirRects.addElement(new RectFree(90, 90, new Vector2(497, 136), 225));
		dirRects.addElement(new RectFree(336, 90, new Vector2(309, 109), 180));
		dirRects.addElement(new RectFree(165, 90, new Vector2(111, 139), 135));
		dirRects.addElement(new RectFree(349, 129, new Vector2(77, 342), 90));
		dirRects.addElement(new RectFree(165, 129, new Vector2(130, 589), 45));

	}

	private void initMap_15() {
		finishRect = new RectFree(18, 98, new Vector2(501, 213), 0);

		rects.addElement(new Rect(376, 0, 265, 166));
		rects.addElement(new Rect(254, 259, 435, 155));
		rects.addElement(new Rect(346, 506, 189, 124));
		rects.addElement(new Rect(-18, 247, 30, 110));

		freeRects.addElement(new RectFree(332, 96, new Vector2(958, 347), 73));
		freeRects.addElement(new RectFree(169, 87, new Vector2(748, 322), 73));
		freeRects.addElement(new RectFree(127, 26, new Vector2(816, 436), 73));
		freeRects.addElement(new RectFree(83, 15, new Vector2(825, 529), 111));
		freeRects.addElement(new RectFree(211, 87, new Vector2(959, 596), 111));
		freeRects.addElement(new RectFree(86, 49, new Vector2(270, 393), 59));
		freeRects.addElement(new RectFree(86, 41, new Vector2(125, 297), 86));
		freeRects.addElement(new RectFree(48, 37, new Vector2(138, 345), 335));
		freeRects.addElement(new RectFree(39, 124, new Vector2(8, 400), 344));
		freeRects.addElement(new RectFree(9, 66, new Vector2(638, 468), 6));
		freeRects.addElement(new RectFree(14, 36, new Vector2(770, 138), 21));

		circles.addElement(new Circle(639, 137, 124, Circle.OUT_DETECT, 0, 90,
				144));
		circles.addElement(new Circle(623, 114, 53, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(776, 119, 103, Circle.OUT_DETECT, 180,
				360));
		circles.addElement(new Circle(739, 129, 142, Circle.OUT_DETECT, 0, 30));
		circles.addElement(new Circle(872, 199, 110, Circle.OUT_DETECT, 150,
				225, 130));
		circles.addElement(new Circle(813, 577, 106, Circle.OUT_DETECT, 0, 225,
				106));
		circles.addElement(new Circle(731, 510, 90, Circle.OUT_DETECT, 180, 45,
				90));
		circles.addElement(new Circle(633, 524, 86, Circle.OUT_DETECT, 0, 180,
				86));
		circles.addElement(new Circle(563, 481, 68, Circle.OUT_DETECT, 270, 10,
				86));
		circles.addElement(new Circle(520, 527, 23, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(263, 453, 94, Circle.OUT_DETECT, 45, 180,
				114));
		circles.addElement(new Circle(176, 434, 80, Circle.OUT_DETECT, 225,
				360, 80));
		circles.addElement(new Circle(115, 392, 84, Circle.OUT_DETECT, 60, 135,
				90));
		circles.addElement(new Circle(180, 243, 167, Circle.OUT_DETECT, 180,
				270));
		circles.addElement(new Circle(190, 255, 83, Circle.IN_DETECT, 0, 90));
		circles.addElement(new Circle(347, 188, 72, Circle.OUT_DETECT, 90, 150,
				90));
		circles.addElement(new Circle(182, 297, 221, Circle.OUT_DETECT, 270,
				315));
		circles.addElement(new Circle(382, 117, 47, Circle.IN_DETECT, 0, 90));

		dirRects.addElement(new RectFree(166, 90, new Vector2(429, 213), 0));
		dirRects.addElement(new RectFree(160, 90, new Vector2(574, 213), 0));
		dirRects.addElement(new RectFree(200, 90, new Vector2(703, 164), 315));
		dirRects.addElement(new RectFree(150, 90, new Vector2(806, 80), 45));
		dirRects.addElement(new RectFree(150, 90, new Vector2(821, 157), 135));
		dirRects.addElement(new RectFree(200, 110, new Vector2(835, 302), 90));
		dirRects.addElement(new RectFree(179, 129, new Vector2(890, 490), 90));
		dirRects.addElement(new RectFree(150, 110, new Vector2(833, 618), 135));
		dirRects.addElement(new RectFree(225, 90, new Vector2(769, 529), 270));
		dirRects.addElement(new RectFree(170, 90, new Vector2(690, 501), 135));
		dirRects.addElement(new RectFree(100, 90, new Vector2(636, 559), 180));
		dirRects.addElement(new RectFree(120, 90, new Vector2(584, 485), 225));
		dirRects.addElement(new RectFree(223, 90, new Vector2(450, 459), 180));
		dirRects.addElement(new RectFree(90, 90, new Vector2(309, 472), 135));
		dirRects.addElement(new RectFree(90, 90, new Vector2(254, 498), 180));
		dirRects.addElement(new RectFree(120, 90, new Vector2(214, 445), 225));
		dirRects.addElement(new RectFree(146, 90, new Vector2(131, 420), 180));
		dirRects.addElement(new RectFree(273, 90, new Vector2(59, 283), 270));
		dirRects.addElement(new RectFree(90, 90, new Vector2(107, 148), 315));
		dirRects.addElement(new RectFree(170, 90, new Vector2(177, 127), 0));
		dirRects.addElement(new RectFree(170, 90, new Vector2(297, 166), 45));
	}

	private void initMap_16() {
		num_cover = 2;
		cover_point = new int[4];
		cover_point[0] = 681;
		cover_point[1] = 277;
		cover_point[2] = 512;
		cover_point[3] = 298;

		coverRects = new RectFree[2];
		coverRects[0] = new RectFree(124, 93, new Vector2(676, 278), 0);
		coverRects[1] = new RectFree(200, 99, new Vector2(509, 306), 15);

		upFloorRect = new RectFree(20, 94, new Vector2(309, 370), 270);
		downFloorRect = new RectFree(20, 94, new Vector2(849, 352), 270);

		finishRect = new RectFree(18, 98, new Vector2(395, 629), 180);

		rects.addElement(new Rect(506, 322, 276, 60));
		rects.addElement(new Rect(511, 181, 298, 48));
		rects.addElement(new Rect(365, 206, 51, 180));

		rects_f1.addElement(new Rect(353, 196, 142, 50));
		rects_f1.addElement(new Rect(367, 339, 99, 65));
		rects_f1.addElement(new Rect(187, 304, 65, 108));
		rects_f1.addElement(new Rect(350, 125, 114, 41));

		freeRects_f1
				.addElement(new RectFree(59, 112, new Vector2(240, 264), 30));
		freeRects_f1
				.addElement(new RectFree(58, 126, new Vector2(312, 185), 57));
		freeRects_f1
				.addElement(new RectFree(101, 43, new Vector2(504, 164), 22));
		freeRects_f1
				.addElement(new RectFree(69, 53, new Vector2(565, 206), 45));
		freeRects_f1
				.addElement(new RectFree(120, 38, new Vector2(611, 273), 67));
		freeRects_f1
				.addElement(new RectFree(104, 63, new Vector2(236, 458), 65));
		freeRects_f1
				.addElement(new RectFree(102, 52, new Vector2(285, 524), 31));
		freeRects_f1
				.addElement(new RectFree(98, 48, new Vector2(367, 556), 10));
		freeRects_f1
				.addElement(new RectFree(78, 41, new Vector2(437, 563), 352));

		circles.addElement(new Circle(517, 332, 100, Circle.OUT_DETECT, 180,
				270, 140));
		circles.addElement(new Circle(386, 366, 128, Circle.OUT_DETECT, 0, 270,
				180));
		circles.addElement(new Circle(385, 371, 34, Circle.IN_DETECT, 0, 270));
		circles.addElement(new Circle(771, 346, 126, Circle.OUT_DETECT, 270,
				180, 136));

		circles_f1.addElement(new Circle(459, 443, 193, Circle.OUT_DETECT, 270,
				360, 203));
		circles_f1.addElement(new Circle(386, 366, 128, Circle.OUT_DETECT, 0,
				270, 180));
		circles_f1.addElement(new Circle(418, 439, 236, Circle.OUT_DETECT, 0,
				90, 266));
		circles_f1.addElement(new Circle(386, 381, 296, Circle.OUT_DETECT, 90,
				180));
		circles_f1.addElement(new Circle(422, 364, 334, Circle.OUT_DETECT, 180,
				315));
		circles_f1.addElement(new Circle(456, 322, 278, Circle.OUT_DETECT, 315,
				360, 308));
		circles_f1.addElement(new Circle(771, 346, 126, Circle.OUT_DETECT, 270,
				180, 136));
		circles_f1.addElement(new Circle(771, 355, 28, Circle.IN_DETECT, 270,
				180));
		circles_f1.addElement(new Circle(442, 453, 117, Circle.IN_DETECT, 270,
				180));
		circles_f1.addElement(new Circle(464, 531, 46, Circle.IN_DETECT, 270,
				180));

		dirRects.addElement(new RectFree(278, 94, new Vector2(633, 274), 180));
		dirRects.addElement(new RectFree(80, 94, new Vector2(482, 296), 135));
		dirRects.addElement(new RectFree(90, 94, new Vector2(467, 362), 90));
		dirRects.addElement(new RectFree(90, 94, new Vector2(445, 426), 135));
		dirRects.addElement(new RectFree(90, 94, new Vector2(385, 449), 180));
		dirRects.addElement(new RectFree(90, 94, new Vector2(331, 431), 225));
		dirRects.addElement(new RectFree(90, 94, new Vector2(312, 368), 270));
		dirRects.addElement(new RectFree(90, 94, new Vector2(317, 319), 315));

		dirRects_f1.addElement(new RectFree(102, 104, new Vector2(354, 632),
				180));
		dirRects_f1.addElement(new RectFree(107, 104, new Vector2(441, 632),
				180));
		dirRects_f1.addElement(new RectFree(227, 104, new Vector2(207, 553),
				225));
		dirRects_f1.addElement(new RectFree(227, 104, new Vector2(141, 373),
				270));
		dirRects_f1.addElement(new RectFree(227, 104, new Vector2(200, 181),
				315));
		dirRects_f1.addElement(new RectFree(227, 104, new Vector2(422, 83), 0));
		dirRects_f1
				.addElement(new RectFree(227, 104, new Vector2(613, 154), 45));
		dirRects_f1
				.addElement(new RectFree(227, 104, new Vector2(677, 275), 90));
		dirRects_f1.addElement(new RectFree(80, 90, new Vector2(713, 400), 45));
		dirRects_f1.addElement(new RectFree(80, 90, new Vector2(777, 421), 0));
		dirRects_f1
				.addElement(new RectFree(80, 90, new Vector2(831, 398), 315));
		dirRects_f1
				.addElement(new RectFree(80, 90, new Vector2(846, 351), 270));
		dirRects_f1
				.addElement(new RectFree(80, 90, new Vector2(834, 293), 225));
		dirRects_f1.addElement(new RectFree(156, 90, new Vector2(434, 290), 0));
		dirRects_f1
				.addElement(new RectFree(156, 104, new Vector2(556, 339), 45));
		dirRects_f1
				.addElement(new RectFree(192, 110, new Vector2(599, 460), 90));
		dirRects_f1.addElement(new RectFree(144, 110, new Vector2(544, 573),
				135));
	}

	private void initMap_17() {
		finishRect = new RectFree(18, 98, new Vector2(719, 410), 0);

		rects.addElement(new Rect(584, 333, 259, 38));
		rects.addElement(new Rect(614, 455, 297, 62));
		rects.addElement(new Rect(659, 180, 267, 68));
		rects.addElement(new Rect(175, 356, 255, 165));
		rects.addElement(new Rect(10, 295, 74, 306));
		rects.addElement(new Rect(930, 277, 30, 142));
		rects.addElement(new Rect(0, 78, 21, 163));

		freeRects.addElement(new RectFree(100, 42, new Vector2(279, 546), 342));
		freeRects.addElement(new RectFree(125, 38, new Vector2(287, 678), 345));
		freeRects.addElement(new RectFree(62, 41, new Vector2(446, 585), 37));
		freeRects.addElement(new RectFree(61, 32, new Vector2(221, 144), 23));
		freeRects.addElement(new RectFree(40, 32, new Vector2(185, 140), 339));
		freeRects.addElement(new RectFree(48, 11, new Vector2(144, 149), 326));
		freeRects.addElement(new RectFree(156, 49, new Vector2(344, 303), 10));
		freeRects.addElement(new RectFree(14, 93, new Vector2(377, 142), 20));

		circles.addElement(new Circle(842, 326, 90, Circle.OUT_DETECT, 270, 360));
		circles.addElement(new Circle(850, 376, 81, Circle.OUT_DETECT, 0, 90));
		circles.addElement(new Circle(628, 206, 127, Circle.OUT_DETECT, 90,
				180, 157));
		circles.addElement(new Circle(491, 167, 104, Circle.OUT_DETECT, 225,
				360, 157));
		circles.addElement(new Circle(659, 182, 66, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(373, 185, 99, Circle.OUT_DETECT, 0, 90,
				129));
		circles.addElement(new Circle(315, 197, 73, Circle.OUT_DETECT, 90, 225,
				93));
		circles.addElement(new Circle(250, 154, 108, Circle.OUT_DETECT, 300,
				360, 138));
		circles.addElement(new Circle(199, 228, 199, Circle.OUT_DETECT, 225,
				315));
		circles.addElement(new Circle(103, 197, 80, Circle.OUT_DETECT, 90, 180,
				90));
		circles.addElement(new Circle(156, 245, 80, Circle.OUT_DETECT, 240, 45,
				100));
		circles.addElement(new Circle(197, 346, 110, Circle.OUT_DETECT, 180,
				225, 110));
		circles.addElement(new Circle(262, 375, 88, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(232, 526, 54, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(533, 448, 6, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(503, 562, 22, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(476, 574, 38, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(489, 181, 18, Circle.IN_DETECT, 225, 360));
		circles.addElement(new Circle(227, 526, 144, Circle.OUT_DETECT, 90, 180));
		circles.addElement(new Circle(343, 645, 93, Circle.OUT_DETECT, 240,
				315, 143));
		circles.addElement(new Circle(464, 553, 153, Circle.OUT_DETECT, 0, 135));
		circles.addElement(new Circle(543, 529, 73, Circle.OUT_DETECT, 270,
				360, 73));
		circles.addElement(new Circle(528, 450, 100, Circle.OUT_DETECT, 90,
				300, 140));
		circles.addElement(new Circle(585, 338, 118, Circle.OUT_DETECT, 80,
				110, 118));

		dirRects.addElement(new RectFree(144, 90, new Vector2(664, 413), 0));
		dirRects.addElement(new RectFree(137, 90, new Vector2(777, 413), 0));
		dirRects.addElement(new RectFree(220, 90, new Vector2(885, 354), 270));
		dirRects.addElement(new RectFree(186, 90, new Vector2(758, 291), 180));
		dirRects.addElement(new RectFree(130, 90, new Vector2(590, 268), 225));
		dirRects.addElement(new RectFree(90, 90, new Vector2(551, 215), 270));
		dirRects.addElement(new RectFree(90, 90, new Vector2(532, 128), 225));
		dirRects.addElement(new RectFree(90, 90, new Vector2(422, 172), 135));
		dirRects.addElement(new RectFree(150, 90, new Vector2(341, 230), 180));
		dirRects.addElement(new RectFree(90, 90, new Vector2(302, 156), 270));
		dirRects.addElement(new RectFree(230, 90, new Vector2(204, 86), 180));
		dirRects.addElement(new RectFree(130, 90, new Vector2(69, 179), 90));
		dirRects.addElement(new RectFree(130, 90, new Vector2(121, 226), 0));
		dirRects.addElement(new RectFree(130, 90, new Vector2(151, 294), 135));
		dirRects.addElement(new RectFree(217, 90, new Vector2(128, 442), 90));
		dirRects.addElement(new RectFree(170, 90, new Vector2(163, 601), 45));
		dirRects.addElement(new RectFree(170, 90, new Vector2(290, 612), 0));
		dirRects.addElement(new RectFree(170, 90, new Vector2(396, 632), 45));
		dirRects.addElement(new RectFree(170, 90, new Vector2(539, 625), 315));
		dirRects.addElement(new RectFree(100, 90, new Vector2(568, 550), 270));
		dirRects.addElement(new RectFree(90, 90, new Vector2(535, 500), 180));
		dirRects.addElement(new RectFree(120, 90, new Vector2(484, 441), 270));
		dirRects.addElement(new RectFree(90, 90, new Vector2(518, 397), 0));
		dirRects.addElement(new RectFree(162, 90, new Vector2(639, 414), 0));
		dirRects.addElement(new RectFree(132, 90, new Vector2(780, 415), 0));
	}

	public void show(Graphics g) {
		g.drawImage(img_map, x_map, y_map, 0);

		for (int i = enemyCars.size() - 1; i >= 0; i--) {
			((EnemyCar) enemyCars.elementAt(i)).show(g);
		}
		myCar.show(g);

		// 显示排名
		if (myCar.rank != 0) {
			for (int i = enemyCars.size() - 1; i >= 0; i--) {
				EnemyCar ec = (EnemyCar) enemyCars.elementAt(i);
				if (ec.state != Car.STATE_LANCHED) {
					g.setColor(0xef8a31);
					g.drawString("第" + ec.rank + "名", ec.x + x_map, ec.y
							+ y_map - 20, Graphics.BOTTOM | Graphics.HCENTER);
				}
			}
			if (myCar.state != Car.STATE_LANCHED) {
				g.setColor(0xef8a31);
				g.drawString("第" + myCar.rank + "名", myCar.x + x_map, myCar.y
						+ y_map - 20, Graphics.BOTTOM | Graphics.HCENTER);
			}
		}

		// 你开反了
		if (myCar.state == Car.STATE_LANCHED) {
			if (myCar.dirErro) {
				for (int i = 0; i < 8; i++) {
					g.drawImage(a_img_err_dir[i], 30 + i * 80, 200,
							Graphics.VCENTER | Graphics.HCENTER);
				}
			} else if (myCar.perfect) {// 完美漂移
				for (int i = 0; i < 4; i++) {
					g.drawImage(a_img_perfect[i], x_perfect_start + i * 80,
							y_perfect[i], Graphics.VCENTER | Graphics.HCENTER);
				}

			}
		}

		// 被减速了显示反击文字
		if (myCar.slowTime > 0) {
			if (y_word == 200)
				y_word = 202;
			else
				y_word = 200;
			g.setColor(0xffffff);
			g.fillRect(400, y_word - 25, 200, 30);
			g.setColor(0xc72216);
			g.setFont(C.FONT_LARGE_BLOD);
			g.drawString("你被减速了，按5键还击。", 500, y_word, Graphics.HCENTER
					| Graphics.BOTTOM);
		}

		// 致盲效果
		if (myCar.blindTime > 0) {
			g.setColor(50, 50, 50);
			g.fillRect(0, 0, 640, 530);
			g.setColor(0xffffff);
			g.setFont(C.FONT_LARGE_BLOD);
			g.drawString("你被对手的致盲攻击击中了。。。", 320, 200, Graphics.HCENTER
					| Graphics.BOTTOM);
			g.drawString("你可以修复你的车辆减少被对手击中的概率", 320, 240, Graphics.HCENTER
					| Graphics.BOTTOM);
			g.setColor(0xb12812);
			g.drawString("按6键还击", 320, 280, Graphics.HCENTER | Graphics.BOTTOM);
		}

		// 3,2,1...ready go
		if (state == STATE_REDYGO)
			showReadyGo(g);
		else if (state == STATE_ACHIEVE)// 成功/失败
			showAchieve(g);

		g.drawImage(img_top, 0, 0, 0);
		g.drawImage(img_bottom, 0, 530, Graphics.LEFT | Graphics.BOTTOM);
		g.drawImage(img_pointer, 482, 490, Graphics.VCENTER | Graphics.HCENTER);// 速度表盘
		C.drawString(g, img_num_speed, carSpeed + "", "0123456789", 513, 513,// 速度文字
				8, 12, Graphics.HCENTER | Graphics.VCENTER, 0, 0);

		// 小地图
		g.drawImage(img_small_map, 545, 436, 0);
		g.drawImage(img_yellow_point, 545 + myCar.x / 13, 436 + myCar.y / 14,
				Graphics.VCENTER | Graphics.HCENTER);

		// 油量显示
		g.setClip(489, 9, myCar.oil * 137 / myCar.oilMax, 8);
		g.drawImage(img_oil_bar, 489, 9, 0);
		g.setClip(0, 0, 640, 530);
		g.setColor(255, 255, 255);
		g.setFont(C.FONT_SMALL_PLAIN);
		g.drawString(myCar.oil + "/" + myCar.oilMax, 557, 4, Graphics.TOP
				| Graphics.HCENTER);

		// 赛道名称
		g.setFont(C.FONT_MEDIUM_BOLD);
		g.drawString(MissionChoose.MAP_NAME[CanvasControl.mission - 1], 320, 1,
				Graphics.TOP | Graphics.HCENTER);

		// 圈数显示

		int lap_show = myCar.lap < 1 ? 1 : myCar.lap;
		g.drawString(lap_show + "/" + lapMax, 125, 1, Graphics.TOP
				| Graphics.HCENTER);

		// 时间显示
		g.setColor(0x00ff00);
		if (gameTime * TIME_MUL > (CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][5] * 10))
			g.setColor(0xffff00);
		if (remainTime < 300)
			g.setColor(0xff0000);
		g.drawString(timeStr, 145, 25, Graphics.TOP | Graphics.HCENTER);

		// 世界纪录
		if (mode == MODE_MISSION) {
			g.setColor(0xffffff);
			g.drawString(recordStr, 140, 50, Graphics.TOP | Graphics.HCENTER);
		}

		// /////////////////////////////调试///////////////////////////////
		if (CanvasControl.DEBUG) {
			g.setColor(0, 0, 255);
			finishRect.show(x_map, y_map, g);

			g.setColor(0, 255, 255);
			for (int i = rects.size() - 1; i >= 0; i--) {
				Rect rect = (Rect) rects.elementAt(i);
				g.drawRect(rect.x + x_map, rect.y + y_map, rect.w, rect.h);
			}

			for (int i = rects_f1.size() - 1; i >= 0; i--) {
				Rect rect = (Rect) rects_f1.elementAt(i);
				g.drawRect(rect.x + x_map, rect.y + y_map, rect.w, rect.h);
			}

			g.setColor(0, 255, 255);
			for (int i = freeRects.size() - 1; i >= 0; i--) {
				RectFree freeRect = (RectFree) freeRects.elementAt(i);
				freeRect.show(x_map, y_map, g);
			}
			for (int i = freeRects_f1.size() - 1; i >= 0; i--) {
				RectFree freeRect = (RectFree) freeRects_f1.elementAt(i);
				freeRect.show(x_map, y_map, g);
			}

			g.setColor(0, 255, 0);
			for (int i = circles.size() - 1; i >= 0; i--) {
				Circle circle = (Circle) circles.elementAt(i);
				circle.show(x_map, y_map, g);
			}
			for (int i = circles_f1.size() - 1; i >= 0; i--) {
				Circle circle = (Circle) circles_f1.elementAt(i);
				circle.show(x_map, y_map, g);
			}

			g.setColor(248, 82, 0);
			for (int i = dirRects.size() - 1; i >= 0; i--) {
				RectFree rectFree = (RectFree) dirRects.elementAt(i);
				rectFree.show(x_map, y_map, g);
			}
			for (int i = dirRects_f1.size() - 1; i >= 0; i--) {
				RectFree rectFree = (RectFree) dirRects_f1.elementAt(i);
				rectFree.show(x_map, y_map, g);
			}

			if (upFloorRect != null) {
				g.setColor(0);
				upFloorRect.show(x_map, y_map, g);
				downFloorRect.show(x_map, y_map, g);
			}
		}
	}

	/**
	 * 显示成功或者失败
	 * 
	 * @param g
	 */
	private void showAchieve(Graphics g) {
		g.drawImage(img_achieve, 320, y_achieve, Graphics.VCENTER
				| Graphics.HCENTER);
	}

	/**
	 * 显示倒计时图片
	 * 
	 * @param g
	 */
	public void showReadyGo(Graphics g) {
		g.drawImage(a_img_ready[frameReady], 320, y_ready, Graphics.VCENTER
				| Graphics.HCENTER);
	}

	/**
	 * 加载倒计时图片
	 */
	public void loadReadyImage() {
		try {
			a_img_ready = new Image[5];
			for (int i = 0; i < 5; i++) {
				a_img_ready[i] = Image.createImage("/game/ready_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除倒计时图片
	 */
	public void removeReadyImage() {
		for (int i = 0; i < 5; i++) {
			a_img_ready[i] = null;
		}
		a_img_ready = null;
	}

	/**
	 * 加载加速效果图片
	 */
	public void loadSpupImage() {
		a_img_speed_up = new Image[4][4];
		Image img_temp = null;
		for (int i = 0; i < 4; i++) {
			try {
				img_temp = Image.createImage("/game/speed_up_" + i + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			int width = img_temp.getWidth();
			int height = img_temp.getHeight();
			if (i == 1) {
				for (int j = 0; j < 4; j++) {
					a_img_speed_up[i][j] = Image.createImage(img_temp, 0, j
							* (height >> 2), width, (height >> 2), 0);
				}
			} else {
				for (int j = 0; j < 4; j++) {
					a_img_speed_up[i][j] = Image.createImage(img_temp, j
							* (width >> 2), 0, (width >> 2), height, 0);
				}
			}
		}
	}

	/**
	 * 移除加速图片
	 */
	public void removeSpupImage() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				a_img_speed_up[i][j] = null;
			}
		}
		a_img_speed_up = null;
		System.gc();
	}

	/**
	 * 您开反了图片
	 */
	public void loadDirErroImage() {
		a_img_err_dir = new Image[8];
		try {
			for (int i = 0; i < 8; i++) {
				a_img_err_dir[i] = Image.createImage("/game/erro_dir_" + i
						+ ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除您开反了图片
	 */
	public void removeDirErroImage() {
		for (int i = 0; i < 8; i++) {
			a_img_err_dir[i] = null;
		}
		a_img_err_dir = null;
	}

	/**
	 * 加载完美漂移图片
	 */
	public void loadPerfectImg() {
		a_img_perfect = new Image[4];
		try {
			for (int i = 0; i < 4; i++) {
				a_img_perfect[i] = Image.createImage("/game/perfect_" + i
						+ ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除完美漂移图片
	 */
	public void removePerfectImg() {
		for (int i = 0; i < 4; i++) {
			a_img_perfect[i] = null;
		}
		a_img_perfect = null;
	}

	/**
	 * 更新指针图片
	 */
	private void updatePointerImage() {
		if (myCar.speed_lastFrame != myCar.speed) {
			try {
				img_pointer = Image.createImage("/pointer/pointer_"
						+ ((carSpeed + 18)/ 25) + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadResource() {
		if (state == STATE_REDYGO)
			loadReadyImage();
		try {
			img_point = Image.createImage("/game/point.png");
			img_pointer = Image.createImage("/pointer/pointer_"
					+ ((carSpeed + 18) / 25) + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadPerfectImg();
	}

	public void removeResource() {
		img_pointer = null;
		img_point = null;
		removePerfectImg();
		if (state == STATE_REDYGO)
			removeReadyImage();
	}

	public void keyPressed(int keyCode) {
		if (state == STATE_GAMING) {
			myCar.keyPressed(keyCode);
			switch (keyCode) {
			case C.KEY_LEFT:
				break;
			case C.KEY_RIGHT:
				break;
			case C.KEY_1:// 排斥道具
				canvasControl.buyGoods(9);
				break;
			case C.KEY_2:// 补充时间
				canvasControl.buyGoods(10);
				break;
			case C.KEY_3:// 急速模式
				canvasControl.buyGoods(11);
				break;
			case C.KEY_4:// 补充燃油
				canvasControl.buyGoods(12);
				break;
			case C.KEY_5:// 减速攻击
				canvasControl.buyGoods(13);
				break;
			case C.KEY_6:// 致盲攻击
				canvasControl.buyGoods(14);
				break;
			case C.KEY_9:
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Help(canvasControl, 1));
				break;
			case C.KEY_0:
			case C.KEY_BACK:
			case C.KEY_BACK_ZX:
				Dialog.game = this;
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Dialog(canvasControl, 1, this));
				break;
			}
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if (state == STATE_REDYGO)
			readyLogic();
		else if (state == STATE_ACHIEVE) {
			achieveLogic();
			myCar.logic();
			for (int i = enemyCars.size() - 1; i >= 0; i--) {
				((EnemyCar) enemyCars.elementAt(i)).logic();
			}

			carClsCar();
			updatePointerImage();
		} else {
			if (mode == MODE_MISSION && remainTime <= 0) {
				timeOut();
			}
			gameTime++;
			if (mode == MODE_MISSION) {
				remainTime -= TIME_MUL;
			}
			updateTimeStr();

			myCar.logic();
			for (int i = enemyCars.size() - 1; i >= 0; i--) {
				((EnemyCar) enemyCars.elementAt(i)).logic();
			}

			animEff();

			carClsCar();
			updatePointerImage();
		}

		rollMap();
		carSpeed = (myCar.speedMax_Org - 4) * 30 * myCar.speed
				/ myCar.speedMax_Org;
	}

	/**
	 * 动画效果
	 */
	private void animEff() {
		if (myCar.perfect) {// 完美漂移
			y_speed_perfect += 5;
			if (y_perfect[0] < 200) {
				y_perfect[0] += y_speed_perfect;
				if (y_perfect[0] >= 200) {
					y_speed_perfect = 40;
					y_perfect[0] = 200;
				}
			} else if (y_perfect[1] < 200) {
				y_perfect[1] += y_speed_perfect;
				if (y_perfect[1] >= 200) {
					y_speed_perfect = 40;
					y_perfect[1] = 200;
				}
			} else if (y_perfect[2] < 200) {
				y_perfect[2] += y_speed_perfect;
				if (y_perfect[2] >= 200) {
					y_speed_perfect = 40;
					y_perfect[2] = 200;
				}
			} else if (y_perfect[3] < 200) {
				y_perfect[3] += y_speed_perfect;
				if (y_perfect[3] >= 200) {
					y_speed_perfect = 40;
					y_perfect[3] = 200;
				}
			} else if (perfectShowTime > 0) {
				perfectShowTime--;
			} else if (x_perfect_start > -400) {
				x_perfect_start -= 60;
			} else {
				myCar.perfect = false;
				resetPerfectParam();
			}
		}
	}

	/**
	 * 重置完美漂移字样数值
	 */
	public void resetPerfectParam() {
		x_perfect_start = 200;
		perfectShowTime = 10;
		for (int i = 0; i < 4; i++) {
			y_perfect[i] = -35;
		}
	}

	/**
	 * 跑完全程逻辑
	 */
	private void achieveLogic() {
		y_speed_achieve += 10;

		if (y_achieve < 220)
			y_achieve += y_speed_achieve;
		if (y_achieve >= 220) {
			y_achieve = 220;
			achieveShowTime++;
		}

		if (achieveShowTime > 20) {
			y_achieve = -100;
			y_speed_achieve = 5;
			achieveShowTime = 0;
			gotoCheckOutView();
		}
	}

	/**
	 * 车与车的碰撞检测
	 */
	public void carClsCar() {
		for (int i = enemyCars.size() - 1; i >= 0; i--) {
			EnemyCar ec = (EnemyCar) enemyCars.elementAt(i);
			if (ec.floor == myCar.floor
					&& C.rectInsect(ec.advRect, myCar.advRect)) {// 外接矩形预碰撞
				if (C.freeRectIsct(ec.rectFree, myCar.rectFree)) {// 两车相撞
					myCar.keyLeftTimes = 0;// 打断完美漂移判断
					myCar.keyRightTimes = 0;

					Vector2 vectorPoint = ec.getPointVector().minus(
							myCar.getPointVector());// myCar到ec的位置向量
					float dot = vectorPoint.dotProduct(myCar.getSpeedVector());// myCar的速度向量与两车位置差向量的点积
					if (dot > 0) {// 夹角为锐角，ec处于myCar的前方
						ec.translation(myCar.getSpeedVector());
					} else if (dot < 0) {// 夹角为钝角，ec处于myCar的后方
						ec.speed -= 4;
						if (ec.speed < 0)
							ec.speed = 0;

						ec.gobackLastPosition();
					}

					vectorPoint = vectorPoint.negative();// ec到myCar的位置向量
					dot = vectorPoint.dotProduct(ec.getSpeedVector());// ec的速度向量与位置向量的点积
					if (dot > 0) {
						myCar.translation(ec.getSpeedVector());
					} else if (dot < 0) {
						myCar.speed -= 4;
						if (myCar.speed < 0)
							myCar.speed = 0;

						myCar.gobackLastPosition();
					}
				}
			}

			for (int j = i - 1; j >= 0; j--) {
				EnemyCar ec_tar = (EnemyCar) enemyCars.elementAt(j);

				if (ec.floor == ec_tar.floor
						&& C.rectInsect(ec.advRect, ec_tar.advRect)) {// 外接矩形预碰撞
					if (C.freeRectIsct(ec.rectFree, ec_tar.rectFree)) {// 两车相撞
						Vector2 vectorPoint = ec.getPointVector().minus(
								ec_tar.getPointVector());// ec_tar到ec的位置向量
						float dot = vectorPoint.dotProduct(ec_tar
								.getSpeedVector());// ec_tar的速度向量与两车位置差向量的点积
						if (dot > 0) {// 夹角为锐角，ec_tar处于ec的后方
							ec.translation(ec_tar.getSpeedVector());
						} else if (dot < 0) {// 夹角为钝角，ec处于myCar的后方
							ec.speed -= 4;
							if (ec.speed < 0)
								ec.speed = 0;

							ec.gobackLastPosition();
						}

						vectorPoint = vectorPoint.negative();// ec到ec_tar的位置向量
						dot = vectorPoint.dotProduct(ec.getSpeedVector());// ec的速度向量与位置向量的点积
						if (dot > 0) {
							ec_tar.translation(ec.getSpeedVector());
						} else if (dot < 0) {
							ec_tar.speed -= 4;
							if (ec_tar.speed < 0)
								ec_tar.speed = 0;

							ec_tar.gobackLastPosition();
						}
					}
				}
			}
		}
	}

	/**
	 * 时间字符更新
	 */
	private void updateTimeStr() {
		if (mode == MODE_MISSION) {
			timeStr = C.computeTimeStr(remainTime, true);
		} else if (mode == MODE_DUAL) {
			timeStr = C.computeTimeStr(gameTime * TIME_MUL, true);
		}
	}

	/**
	 * 倒计时动画逻辑
	 */
	private void readyLogic() {
		readySpeed += 5;
		if (y_ready < 200) {
			y_ready += readySpeed;
		}

		if (readySpeed > 60) {
			readySpeed = 10;
			y_ready = 0;
			if (frameReady < 4) {
				frameReady++;
			} else {
				canvasControl.playerHandler.playByIndex(0);
				setState(STATE_GAMING);
				removeReadyImage();
			}
		}
	}

	/**
	 * 地图滚动
	 */
	public void rollMap() {
		x_map = 320 - myCar.x;
		y_map = 265 - myCar.y;

		if (x_map > 0)
			x_map = 0;
		else if (x_map < 640 - width_map)
			x_map = 640 - width_map;

		if (y_map > 0)
			y_map = 0;
		else if (y_map < 530 - heigth_map)
			y_map = 530 - heigth_map;
	}

	public void removeServerImage() {
		img_map = null;
		img_yellow_point = null;
		img_top = null;
		img_bottom = null;
		img_num_speed = null;
		img_oil_bar = null;

		if (num_cover > 0) {
			for (int i = 0; i < num_cover; i++) {
				a_img_cover[i] = null;
			}
			a_img_cover = null;
		}

		for (int i = 0; i < 2; i++) {
			a_img_car_blind[i] = null;
		}
		a_img_car_blind = null;

		System.gc();
	}

	/**
	 * 初始化您开反了相应数值
	 */
	public void initDirErro() {
		loadDirErroImage();
	}

	/**
	 * 去到结算界面
	 */
	private void gotoCheckOutView() {
		Checkout.gameMode = mode;
		if (mode == MODE_DUAL)
			Checkout.playerDualing = duelPlayer;

		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		this.removeServerImage();
		canvasControl.setView(new Loading(canvasControl, 8));
	}

	/**
	 * 获得游戏当前状态
	 * 
	 * @return
	 */
	public int getState() {
		return state;
	}

	/**
	 * 设置游戏状态
	 * 
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 成功或失败
	 */
	public void loadAchieveImg() {
		try {
			if (CanvasControl.passSuc == 0) {
				if (mode == MODE_MISSION)
					img_achieve = Image.createImage("/game/success.png");
				else
					img_achieve = Image.createImage("/game/success_duel.png");

			} else if (CanvasControl.passSuc == 1) {
				if (mode == MODE_MISSION)
					img_achieve = Image.createImage("/game/failed.png");
				else
					img_achieve = Image.createImage("/game/failed_duel.png");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 过关成功数值处理
	 */
	public void handleSuccess() {
		if (mode == MODE_MISSION) {// 关卡模式
			Checkout.old_prep[0] = canvasControl.me.expeirence;
			for (int i = 0; i < 3; i++) {
				Checkout.old_prep[i + 1] = canvasControl.me.cars[CanvasControl.usingCar][i + 1];

			}
			canvasControl.me.expeirence += CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][4];// 加经验
			canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
			canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
			canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;

			for (int i = 1; i < 4; i++) {
				if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
					canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
				}
			}

			if (CanvasControl.mission == CanvasControl.missionPassed) {// 解锁新的关卡
				if (CanvasControl.missionPassed < 16) {
					MissionChoose.unlocking = true;
					CanvasControl.missionPassed++;
				}
			}
		} else {
			for (int i = 0; i < 3; i++) {
				Checkout.old_prep[i + 1] = canvasControl.me.cars[CanvasControl.usingCar][i + 1];
			}

			canvasControl.me.strength += 100;// 增加实力值
			canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
			canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
			canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;

			for (int i = 1; i < 4; i++) {
				if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
					canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
				}
			}

			duelPlayer.upLoadMsg(Player.DEFEATED, CanvasControl.iptvID);
		}

		CanvasControl.winsTotal++;
		CanvasControl.gamesTotal++;
		canvasControl.saveScore();
		canvasControl.saveParam();
	}

	/**
	 * 过关失败数值处理
	 */
	public void handleFailed() {
		if (mode == MODE_MISSION) {
			Checkout.old_prep[0] = canvasControl.me.expeirence;
			for (int i = 0; i < 3; i++) {
				Checkout.old_prep[i + 1] = canvasControl.me.cars[CanvasControl.usingCar][i + 1];

			}
			canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
			canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
			canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;

			for (int i = 1; i < 4; i++) {
				if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
					canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
				}
			}
		} else {
			for (int i = 0; i < 3; i++) {
				Checkout.old_prep[i + 1] = canvasControl.me.cars[CanvasControl.usingCar][i + 1];
			}

			duelPlayer.strength += 100;
			canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
			canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
			canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;

			for (int i = 1; i < 4; i++) {
				if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
					canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
				}
			}

			duelPlayer.upLoadMsg(Player.WIN, CanvasControl.iptvID);
			duelPlayer.updateParam();
			duelPlayer.saveScore();
		}

		CanvasControl.gamesTotal++;
		canvasControl.saveScore();
		canvasControl.saveParam();
	}

	public void handleGoods(int goodsIndex) {
		canvasControl.playerHandler.playByIndex(0);

		switch (goodsIndex) {
		case 9:// 排斥道具
			myCar.addReject();
			useGoodsTimes++;
			break;
		case 10:// 补充时间
			remainTime = CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][3] * 10;
			if (timeOut)
				timeOut = false;
			break;
		case 11:// 急速模式
			myCar.fast();
			useGoodsTimes++;
			break;
		case 12:// 补充燃油
			myCar.oil = myCar.oilMax;
			if (fuelOut)
				fuelOut = false;
			break;
		case 13:// 减速攻击
			slowAttack();
			useGoodsTimes++;
			break;
		case 14:// 致盲攻击
			blindAttack();
			useGoodsTimes++;
			break;

		default:
			break;
		}
	}

	/**
	 * 致盲攻击
	 */
	private void blindAttack() {
		for (int i = enemyCars.size() - 1; i >= 0; i--) {
			((EnemyCar) enemyCars.elementAt(i)).beBlindHitted();
		}
	}

	/**
	 * 减速攻击
	 */
	private void slowAttack() {
		for (int i = enemyCars.size() - 1; i >= 0; i--) {
			((EnemyCar) enemyCars.elementAt(i)).beSlowHitted();
		}
	}

	/**
	 * 燃料耗尽
	 */
	public void fuelOut() {
		fuelOut = true;
		Dialog.game = this;
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		canvasControl.setView(new Dialog(canvasControl, 7, this));
	}

	/**
	 * 时间耗尽
	 */
	public void timeOut() {
		timeOut = true;
		Dialog.game = this;
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		canvasControl.setView(new Dialog(canvasControl, 8, this));
	}

	/**
	 * 获取游戏模式
	 * 
	 * @return
	 */
	public int getMode() {
		return mode;
	}

}
