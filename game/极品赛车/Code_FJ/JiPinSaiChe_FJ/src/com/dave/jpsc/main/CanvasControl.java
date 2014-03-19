package com.dave.jpsc.main;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import com.dalin.fjiptv.prop.FJ_IPTV_Listenner;
import com.dalin.fjiptv.prop.FJ_IPTV_PORP_TOOL;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.view.Game;
import com.dave.jpsc.view.Message;
import com.dave.jpsc.view.NullView;
import com.dave.showable.Showable;

/**
 * @author Dave
 * 
 */
public class CanvasControl extends Canvas implements Runnable, FJ_IPTV_Listenner {

	/**
	 * 调试
	 */
	public static final boolean DEBUG = false;

	private MainMIDlet midlet;

	public static String iptvID;

	public final static String VERSION = "Version_FJ_1.0.4";

	/**
	 * 浙江工具
	 */
	public FJ_IPTV_PORP_TOOL fj_tool;

	/**
	 * 道具订购地址
	 */
	public static String url;

	/**
	 * 游戏图片存放的服务器地址
	 */
	public static String imageServerUrl = "";// 从jad获取

	/**
	 * 产品码
	 */
	public final static String GAME_PROP_CODE = "P31021";
	// public final static String GAME_PROP_CODE = "P31017";

	/**
	 * 道具的参数0：道具码，1：价格。
	 */
	public final static String[][] A_GOODS_PARAM = { { "DJ32109", "1" },// 0
																		// 挑战次数
			{ "DJ32110", "1" },// 1 长城越野
			{ "DJ32111", "1" },// 2 雪弗兰超级跑车
			{ "DJ32112", "1" },// 3 宝马高速赛车
			{ "DJ32113", "1" },// 4 尼桑螺旋赛车
			{ "DJ32114", "1" },// 5 雷诺方程式
			{ "DJ32115", "1" },// 6 保时捷尾翼
			{ "DJ32116", "1" },// 7 奔驰sl
			{ "DJ32117", "1" },// 8 马自达喷气式
			{ "DJ32119", "1" },// 9 排斥
			{ "DJ32125", "1" },// 10 补充时间
			{ "DJ32120", "1" },// 11 急速模式
			{ "DJ32121", "1" },// 12 补充燃料
			{ "DJ32122", "1" },// 13减速攻击
			{ "DJ32123", "1" },// 14 致盲攻击
			{ "DJ32124", "1" },// 15 一键修复
			{ "DJ32118", "1" },// 16 升级
	};

	/**
	 * 当前界面
	 */
	private Showable view;

	/**
	 * 记录前一界面
	 */
	private Showable goBackView;

	/**
	 * 声音控制 其中的:0、引擎音。 1、按键。 2、刹车。
	 */
//	public final PlayerHandler playerHandler;

	/**
	 * 记录每次逻辑循环开始时间
	 */
	private long cycleStartTime;

	/**
	 * 记录每次逻辑循环使用的时间
	 */
	private long cycleUseTime;

	/**
	 * 网路
	 */
	private ServerIptv serverIptv;

	/**
	 * 空界面
	 */
	public final NullView nullView;

	/**
	 * 当前可选的最高关卡
	 */
	public static int missionPassed = 1;

	/**
	 * 当前的关卡
	 */
	public static int mission = 1;

	/**
	 * 车辆属性，从左到右分别是：速度，加速度，转弯，燃料，等级（0是未拥有）
	 */
	public static int[][] carProperty = { { 35, 10, 60, 65, 1 },// 跑车系统赠送
			{ 55, 25, 90, 80, 0 },// 越野车
			{ 160, 30, 155, 110, 0 },// 超级跑车
			{ 90, 30, 50, 130, 0 },// 高速赛车
			{ 200, 10, 100, 90, 0 },// 螺旋赛车
			{ 90, 15, 160, 120, 0 },// 方程式赛车
			{ 65, 10, 120, 130, 0 },// 尾翼赛车
			{ 100, 15, 50, 100, 0 },// sl赛车
			{ 140, 20, 220, 110, 0 },// 喷气式赛车
	};

	/**
	 * 车辆各种属性最大值
	 */
	public final static int[][] carMaxPower = { { 250, 120, 220, 160 },
			{ 350, 130, 220, 160 }, { 300, 150, 220, 160 },
			{ 280, 180, 220, 160 }, { 480, 120, 220, 160 },
			{ 450, 130, 220, 160 }, { 500, 110, 220, 160 },
			{ 400, 140, 220, 160 }, { 550, 100, 220, 160 }, };

	/**
	 * 关卡赛道属性 分别是：圈数、对手数量、过关所需名次、过关限定时间(s)、奖励的经验值、世界纪录时间(s)
	 */
	public final static int[][] MISSIONPROPERTY = { { 1, 2, 1, 220, 30, 120 },
			{ 1, 2, 1, 280, 30, 110 }, { 1, 2, 1, 250, 30, 100 },
			{ 2, 2, 1, 360, 40, 180 }, { 1, 2, 1, 180, 40, 100 },
			{ 2, 2, 1, 360, 40, 180 }, { 2, 3, 1, 320, 60, 180 },
			{ 2, 3, 1, 320, 60, 180 }, { 2, 3, 1, 320, 60, 170 },
			{ 2, 4, 1, 300, 70, 170 }, { 2, 4, 1, 300, 70, 150 },
			{ 2, 4, 1, 300, 70, 150 }, { 3, 5, 1, 450, 80, 210 },
			{ 3, 5, 1, 450, 80, 210 }, { 3, 5, 1, 450, 80, 210 },
			{ 3, 5, 1, 450, 80, 210 }, { 3, 5, 1, 300, 120, 150 }, // 副本一
	};

	/**
	 * 游戏中的排名
	 */
	public static int rankInGame;

	/**
	 * 关卡时间
	 */
	public static String gameTimeStr;

	/**
	 * 使用的车(在我的车里面的下标值，在所有的车里的下标值取me.cars[usingCar][0])
	 */
	public static int usingCar;

	/**
	 * 完成度星级
	 */
	public static boolean[] tarAchieve = { false, false, false };

	/**
	 * 我
	 */
	public Player me;

	/**
	 * 从服务器获取的昵称
	 */
	public static String nickFromSer = "傻逼们";

	/**
	 * 复活
	 */
	public boolean reliving;

	/**
	 * 记录游戏帧数
	 */
	public static long fps;

	/**
	 * 总胜场
	 */
	public static int winsTotal;

	/**
	 * 总比赛场次
	 */
	public static int gamesTotal;

	/**
	 * 分别是：战绩排名，经验排名，挑战排名
	 */
	public static int[] rankMe = { 20, 20, 20 };

	/**
	 * 挑战对象存储
	 */
	public static Vector duelInfo;

	/**
	 * 排行榜信息，胜率排行。
	 */
	public static Vector rank_win;

	/**
	 * 等级排名
	 */
	public static Vector rank_level;

	/**
	 * 挑战排名
	 */
	public static Vector rank_duel;

	/**
	 * 道具下标值。0：挑战次数。1~8八种赛车。9：排斥道具。10：补充时间。11：急速模式。12：补充燃料。13：减速攻击。14：致盲攻击。15：
	 * 一键修复。16：赛车升级
	 */
	public static int goodsIndex = -1;

	/**
	 * 用来控制游戏帧率的默认循环时间
	 */
	public final static int DEFAULTCYCLEUSETIME = 100;

	/**
	 * 进入游戏的时间
	 */
	public static long appStartTime;

	/**
	 * 确认退出游戏的时候值设为true
	 */
	public static boolean willExit;

	/**
	 * 存入服务器的参数
	 */
	public Vector paramsUpload;

	/**
	 * 玩的日期
	 */
	public static int playDay;

	/**
	 * 存储到服务器的字符串，保存用户数据
	 */
	public static String serverStr;

	/**
	 * 过关成功？0:成功，1：失败
	 */
	public static int passSuc = 1;

	/**
	 * 有信息
	 */
	public static boolean haveMsg;

	/**
	 * 上次存档日期
	 */
	public static int lastPlayDay;

	/**
	 * 已经处理了日期存档刷新
	 */
	public static boolean handledDay;

	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;

//		playerHandler = new PlayerHandler(3, 2);

		paramsUpload = new Vector();

		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		C.out("cavas宽度：" + C.WIDTH + "\ncavas高度：" + C.HEIGTH);
		getServerInfo(midlet);
		
		loadFj_tool(midlet);

		serverIptv = new ServerIptv(this);
		serverIptv.getSystemTime();
		serverIptv.doGetUserProfile();
		serverIptv.getMsg();
		serverIptv.getDuelInfo();
		serverIptv.doGetRank(3, 0, "asc");
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv = null;

		nullView = new NullView();

		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:" + pro);
		if (pro.substring(0, 1).equals("z")) {
			C.isZHONGXING = true;
		}
		launch();
	}
	
	private void loadFj_tool(MIDlet midlet) {
		fj_tool = new  FJ_IPTV_PORP_TOOL(midlet, this, GAME_PROP_CODE);
		fj_tool.setSysOut(true);
//		fj_tool.setTestAdress("http://61.160.131.57:8083/www.iptvtest.com/");
		fj_tool.setShowTime(500);
		fj_tool.setTest();
		fj_tool.init();
		
	}

	public void launch() {
		// setView(new Home(this));
		// setView(new Loading(this, 0));
		// setView(new Complete(this));
		setView(nullView);
		new Thread(this).start();
	}

	protected void paint(Graphics g) {
		view.show(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run() 主逻辑
	 */
	public void run() {
		while (true) {
			cycleStartTime = System.currentTimeMillis();

			view.logic();

			repaint();
			serviceRepaints();

			cycleUseTime = System.currentTimeMillis() - cycleStartTime;
			if (cycleUseTime != 0)
				fps = 1000 / cycleUseTime;

			if (cycleUseTime < DEFAULTCYCLEUSETIME) {
				try {
					Thread.sleep(DEFAULTCYCLEUSETIME - cycleUseTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setView(Showable view) {
//		if (this.view instanceof Game)
//			playerHandler.stopByIndex(0);

//		if ((view instanceof Game) && )
//			playerHandler.playByIndex(0);

		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		System.gc();
	}

	protected void keyPressed(int keyCode) {
//		if (!(view instanceof Game))
//			playerHandler.playByIndex(1);
		view.keyPressed(keyCode);
		super.keyPressed(keyCode);
	}

	protected void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		view.keyReleased(keyCode);
		super.keyReleased(keyCode);
	}

	protected void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub
		view.keyRepeated(keyCode);
		super.keyRepeated(keyCode);
	}

	public final MainMIDlet getMidlet() {
		return midlet;
	}

	public final void setMidlet(MainMIDlet midlet) {
		this.midlet = midlet;
	}

	public final Showable getGoBackView() {
		return goBackView;
	}

	public final void setGoBackView(Showable goBackView) {
		this.goBackView = goBackView;
	}

	public final Showable getView() {
		return view;
	}

	/**
	 * 获取iptvID，无法获取则传入值。 获取道具订购地址。 获取图片服务器地址。
	 * 
	 * @param midlet
	 */
	private void getServerInfo(MIDlet midlet) {
		iptvID = midlet.getAppProperty("userId");
		if (iptvID == null) {
			iptvID = "919100";
		}

		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if (url == null) {
			url = C.COMPANYURL_PHP;
		}

		imageServerUrl = midlet.getAppProperty("ImageServerUrl_FJ");
		if (imageServerUrl == null) {
			// imageServerUrl = "http://222.46.20.151:8055/jpsc";// 本地地址
			imageServerUrl = "http://110.84.142.23:8080/GameResource/jpsc";// 安徽正式地址
		}
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doBuySuccess() {
		goBackView.handleGoods(goodsIndex);

		saveParam();
		new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
	}

	/**
	 * 处理购买结果
	 * 
	 * @param propstate
	 */
	public void backFromPropServer(int propstate) {
		// System.out.println("propstate=" + propstate);
		// propstate = 0;
		// switch (propstate) {
		// case 0: {// 成功
		// this.setView(new Dialog(this, 3, null));
		// }
		// break;
		// case 3: {// 订购峰值
		// this.setView(new Dialog(this, 10, null));
		// }
		// break;
		// case 2: {// 订购失败
		// this.setView(new Dialog(this, 4, null));
		// }
		// break;
		// }
	}

	/**
	 * 存档
	 */
	public void saveParam() {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(playDay);// 存档日期（用于刷新挑战次数）
		sb_param.append(",");
		sb_param.append(CanvasControl.missionPassed);// 已通过的关卡
		// sb_param.append(16);// 已通过的关卡
		sb_param.append(",");
		sb_param.append(C.enUseName_base64(me.nickName));// 昵称
		sb_param.append(",");
		sb_param.append(me.level);// 等级
		sb_param.append(",");
		sb_param.append(me.expeirence);// 经验
		sb_param.append(",");
		sb_param.append(me.strength);// 实力
		sb_param.append(",");
		sb_param.append(me.duelTimes);// 剩余挑战次数
		sb_param.append(",");
		sb_param.append(me.mainCar);// 主战车辆
		sb_param.append(",");

		for (int i = 0; i < me.cars.length; i++) {// 持有的车辆信息
			for (int j = 0; j < me.cars[i].length; j++) {
				sb_param.append(me.cars[i][j]);
				sb_param.append(",");
			}
		}
		serverStr = sb_param.toString();
		System.out.println(serverStr);
		new ServerIptv(this).doSendUserInfo(iptvID, serverStr);
	}

	/**
	 * 保存积分
	 */
	public void saveScore() {
		if (me != null) {
			new ServerIptv(this).doSendScore(iptvID,
					C.enUseName_base64(me.nickName), winsTotal, gamesTotal, 3);
			me.saveScore();
		}
	}

	/**
	 * 处理得到的存档字符串
	 * 
	 * @param param
	 */
	public void handleParam(String param) {
		C.out("处理存档--------" + param);
		String[] a_s_param = C.subString(param, ',');
		CanvasControl.lastPlayDay = Integer.parseInt(a_s_param[0]);
		CanvasControl.missionPassed = Integer.parseInt(a_s_param[1]);
		int car_num = (a_s_param.length - 8) / 5;
		int[][] cars = new int[car_num][5];
		for (int i = 0; i < car_num; i++) {
			for (int j = 0; j < 5; j++) {
				cars[i][j] = Integer.parseInt(a_s_param[8 + 5 * i + j]);
			}
			carProperty[cars[i][0]][4] = cars[i][4] + 1;
		}
		me = new Player(C.deUserName_base64(a_s_param[2]),
				Integer.parseInt(a_s_param[3]), Integer.parseInt(a_s_param[4]),
				Integer.parseInt(a_s_param[5]), Integer.parseInt(a_s_param[6]),
				Integer.parseInt(a_s_param[7]), cars, iptvID, this);

		usingCar = me.mainCar;
	}

	/**
	 * 用一条存档参数创建一个玩家
	 * 
	 * @param param
	 * @return
	 */
	public Player createPlayerByParam(String[] params) {
		String[] a_s_param = C.subString(params[0], ',');
		int car_num = (a_s_param.length - 8) / 5;
		int[][] cars = new int[car_num][5];
		for (int i = 0; i < car_num; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.println("生成玩家，第" + (8 + 5 * i + j) + "个数据----" + a_s_param[8 + 5 * i + j]);
				cars[i][j] = Integer.parseInt(a_s_param[8 + 5 * i + j]);
			}
		}
		Player player = new Player(Integer.parseInt(a_s_param[1]),
				C.deUserName_base64(a_s_param[2]),
				Integer.parseInt(a_s_param[3]), Integer.parseInt(a_s_param[4]),
				Integer.parseInt(a_s_param[5]), Integer.parseInt(a_s_param[6]),
				Integer.parseInt(a_s_param[7]), cars, params[1], this);

		return player;
	}

	/**
	 * 处理得到的时间字符串
	 * 
	 * @param ymd
	 *            年月日字符串，格式：2013-09-30 10:33:19
	 * @param week
	 *            星期字符串，格式：星期一
	 */
	public void handleTimeStr(String ymd, String week) {
		C.out("ymd--" + ymd);
		C.out("week--" + week);

		String day = ymd.substring(0, 10);
		String[] date = C.subString(day, '-');
		playDay = Integer.parseInt(date[2]);
		System.out.println("playDay---" + playDay);
	}

	/**
	 * 调用工具包购买道具
	 */
	public void buyGoods(int goodsIndex) {
		System.out.println("购买的道具下标：" + goodsIndex);
		CanvasControl.goodsIndex = goodsIndex;
		setGoBackView(view);
		setView(nullView);
		goBackView.removeResource();
		try {
//			fj_tool.do_BuyProp(A_GOODS_PARAM[goodsIndex][0]);
			fj_tool.do_BuyProp_New(
					Image.createImage("/goods/goods_name_" + goodsIndex
							+ ".png"),
							Image.createImage("/goods/goods_info_" + goodsIndex
									+ ".png"), A_GOODS_PARAM[goodsIndex][0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析信息字符串
	 * 
	 * @param msg
	 *            得到的信息字符串
	 */
	public void analyzeMsg(String msg) {
		C.out("信息处理--------" + msg);
		String[] a_s_msgs = C.subString(msg, ';');
		Message.numbers = a_s_msgs.length;
		Message.duelResult = new int[Message.numbers];
		Message.duelPlayer = new Player[Message.numbers];
		for (int i = 0; i < Message.numbers; i++) {
			String[] msg_data = C.subString(a_s_msgs[i], ',');
			Message.duelResult[i] = Integer.parseInt(msg_data[0]);

			int car_num = (msg_data.length - 9) / 5;
			int[][] cars = new int[car_num][5];
			for (int m = 0; m < car_num; m++) {
				for (int n = 0; n < 5; n++) {
					cars[m][n] = Integer.parseInt(msg_data[9 + 5 * m + n]);
				}
			}
			Message.duelPlayer[i] = new Player(Integer.parseInt(msg_data[2]),
					C.deUserName_base64(msg_data[3]),
					Integer.parseInt(msg_data[4]),
					Integer.parseInt(msg_data[5]),
					Integer.parseInt(msg_data[6]),
					Integer.parseInt(msg_data[7]),
					Integer.parseInt(msg_data[8]), cars, msg_data[1], this);
		}
	}

	/**
	 * 清空我的消息列表
	 */
	public void emptyMsg() {
		new ServerIptv(this).updateMsg(iptvID, "null");
	}

	public void FJ_PROP_listener(int propstate) {
		switch (propstate) {
		case FJ_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS_BUYProp: {// 成功
			if (!Game.timeOut && !Game.fuelOut)
				setView(goBackView);
			doBuySuccess();
		}
			break;
		case FJ_IPTV_PORP_TOOL.QUERY_BACK_FAIL_BUYProp:
		case FJ_IPTV_PORP_TOOL.QUERY_BACK_Cancel_BUYProp:
		case 2:
		case 3:
		default: {// 订购失败
//			if(goBackView instanceof Game)
//				playerHandler.playByIndex(0);
			setView(goBackView);
			repaint();
		}
			break;
		}
	}

}
