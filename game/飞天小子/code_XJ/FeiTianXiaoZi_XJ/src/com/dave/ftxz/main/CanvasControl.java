package com.dave.ftxz.main;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.PlayerHandler;
import com.dave.ftxz.view.Dialog;
import com.dave.ftxz.view.Game;
import com.dave.ftxz.view.Home;
import com.dave.ftxz.view.NullView;
import com.dave.showable.Showable;
import com.dave.xinJiang.Handler_XJ;
import com.dave.xinJiang.Main_XJ;

/**
 * @author Dave
 * 
 */
public class CanvasControl extends Canvas implements Runnable, Handler_XJ {

	private MainMIDlet midlet;

	public static String iptvID;

	public final static String VERSION = "Version_XJ_1.0.4";

	/**
	 * 新疆工具
	 */
	public Main_XJ xj_tool;

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
	public final static String GAME_PROP_CODE = "P31017";

	/**
	 * 道具的参数0：道具码，1：价格。
	 */
	public final static String[][] A_GOODS_PARAM = { { "DJ32075", "100" },// 乙小子
			{ "DJ32076", "100" },// 丙小子
			{ "DJ32077", "100" },// 丁小子
			{ "DJ32079", "100" },// 萌宠
			{ "DJ32080", "100" },// 威力
			{ "DJ32081", "100" },// 减速
			{ "DJ32082", "100" },// 冲刺
			{ "DJ32083", "100" },// 无敌
			{ "DJ32084", "100" },// 吸铁石
			{ "DJ32078", "100" } // 复活
	};

	/**
	 * 新疆的道具信息。从左到右分别是：价格，功能介绍文字，道具名称，消费原因"道具id,数量"。
	 */
	public final static String[][] A_XJ_GOODS_INFO = {
			{ "100", "拥有克隆功能，所获得的金币都会翻倍", "乙小子", "DJ32075,1", },// 乙小子
			{ "100", "拥有磁力技能，会自动吸引周围的金币", "丙小子", "DJ32076,1", },// 丙小子
			{ "100", "技能：重生，只能使用一次", "丁小子", "DJ32077,1", },// 丁小子
			{ "100", "帮助主角消灭敌人", "萌宠", "DJ32079,1", },// 萌宠
			{ "100", "提升主角的攻击力和攻击范围", "威力", "DJ32080,1", },// 威力
			{ "100", "减慢敌人的移动速度。购买后游戏内自动掉落", "减速", "DJ32081,1", },// 减速
			{ "100", "主角一最快的速度冲刺2000米，并且冲刺过程中不会受到伤害。购买后游戏内自动掉落", "冲刺",
					"DJ32082,1", },// 冲刺
			{ "100", "30秒内，主角处于无敌状态。购买后游戏内自动掉落", "无敌", "DJ32083,1", },// 无敌
			{ "100", "30秒内，主角自动吸引周围金币。购买后游戏内自动掉落", "吸铁石", "DJ32084,1", },// 吸铁石
			{ "100", "重新获得生命。购买后游戏内自动掉落", "复活", "DJ32078,1", } // 复活
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
	 * 声音控制 其中的:0、背景。 1、按键。 2、炮兵射击。 3、敌人射击。 4、敌人死亡。 5、闯关成功。 6、闯关失败。
	 */
	public final PlayerHandler playerHandler;
	/**
	 * 记录每次逻辑循环开始时间
	 */
	private long cycleStartTime;

	/**
	 * 记录每次逻辑循环使用的时间
	 */
	private long cycleUseTime;

	/**
	 * 空界面
	 */
	public final NullView nullView;

	/**
	 * 复活
	 */
	public boolean reliving;

	/**
	 * 记录游戏帧数
	 */
	public static long fps;

	/**
	 * 排行榜信息，每一条是一个对应的string数组
	 */
	public static Vector rankInfo_month;

	/**
	 * u国排行榜
	 */
	public static Vector rankInfo_week;

	/**
	 * 从0~9别是：乙小子、丙小子、丁小子、萌宠、威力、减速、冲刺、无敌、吸铁石、复活
	 */
	public static int goodsIndex = -1;

	/**
	 * 用来控制游戏帧率的默认循环时间
	 */
	public final static int DEFAULTCYCLEUSETIME = 100;

	/**
	 * 飞行距离。
	 */
	public static int distance = 0;

	/**
	 * 得到的总金币
	 */
	public static int coin_total = 0;

	/**
	 * 选择的英雄
	 */
	public static int type_hero = 0;

	/**
	 * 进入游戏的时间
	 */
	public static long appStartTime;

	/**
	 * 确认退出游戏的时候值设为true
	 */
	public static boolean willExit;

	/**
	 * 存储到服务器的字符串，保存用户数据
	 */
	public static String serverStr;

	/**
	 * 本周我的最高得分
	 */
	public static int score_week;

	/**
	 * 本周排名
	 */
	public static int rank_week;

	/**
	 * 本月我的最高得分
	 */
	public static int score_month;

	/**
	 * 本月排名
	 */
	public static int rank_month;

	/**
	 * 购买后进入直接进入
	 */
	public static boolean willStart;

	/**
	 * 道具数量。从左到右分别是：乙小子、丙小子、丁小子、萌宠、威力、减速、冲刺、无敌、吸铁石
	 */
	public static final int[] goodsNumber = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;

		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		C.out("cavas宽度：" + C.WIDTH + "\ncavas高度：" + C.HEIGTH);
		getServerInfo(midlet);

		playerHandler = new PlayerHandler(5, 2);

		nullView = new NullView();

		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:" + pro);
		if (pro.substring(0, 1).equals("z")) {
			C.isZHONGXING = true;
		}
		setView(new Home(this));
		// launch();
	}

	public void launch() {
		new Thread(this).start();
		
		xj_tool = new Main_XJ(false, midlet, this, "1101", "800102",
				"77f42e3e2a84d7e05ee7861e1ede0ef2", "CP800102");
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
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		System.gc();
	}

	protected void keyPressed(int keyCode) {
		if (!(view instanceof Game))
			playerHandler.playByIndex(1);
		view.keyPressed(keyCode);
		super.keyPressed(keyCode);
	}

	protected void keyReleased(int keyCode) {
		view.keyReleased(keyCode);
		super.keyReleased(keyCode);
	}

	protected void keyRepeated(int keyCode) {
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
			iptvID = "777722";
		}

		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if (url == null) {
			url = C.COMPANYURL_PHP;
		}

		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
		if (imageServerUrl == null) {
			// imageServerUrl = "http://222.46.20.151:8055/ftxz";// 本地地址
			imageServerUrl = "http://122.224.212.78:7878/GameResource/ftxz";// 浙江测试地址
		}
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doBuySuccess() {
		if (goodsIndex < 9) {
			goodsNumber[goodsIndex]++;
		} else if (goodsIndex == 9) {// 复活
			reliving = true;
		}
		saveParam();
	}

	/**
	 * 处理购买结果
	 * 
	 * @param propstate
	 */
	public void backFromPropServer(int propstate) {
		System.out.println("propstate=" + propstate);
		propstate = 0;
		switch (propstate) {
		case 0: {// 成功
			this.setView(new Dialog(this, 3, null));
		}
			break;
		case 3: {// 订购峰值
			this.setView(new Dialog(this, 10, null));
		}
			break;
		case 2: {// 订购失败
			this.setView(new Dialog(this, 4, null));
		}
			break;
		}
	}

	/**
	 * 存档
	 */
	public void saveParam() {
		StringBuffer sb_param = new StringBuffer();
		for (int i = 0; i < goodsNumber.length; i++) {
			sb_param.append(goodsNumber[i]);
			sb_param.append(",");
		}
		serverStr = sb_param.toString();
		System.out.println(serverStr);

		xj_tool.saveParam(null, serverStr);
	}

	/**
	 * 处理得到的存档字符串
	 * 
	 * @param param
	 */
	public void handleParam(String param) {
		C.out("处理存档--------" + param);
		String[] a_s_param = C.subString(param, ',', goodsNumber.length);
		for (int i = 0; i < goodsNumber.length; i++) {
			goodsNumber[i] = Integer.parseInt(a_s_param[i]);
		}
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

	}

	/**
	 * 新疆消费
	 * 
	 * @param goodsIndex
	 */
	public void xj_shopping(int goodsIndex) {
		try {
			xj_tool.buy(
					Image.createImage("/dialog/goods_icon_" + goodsIndex
							+ ".png"),
					Integer.parseInt(A_XJ_GOODS_INFO[goodsIndex][0]),
					A_XJ_GOODS_INFO[goodsIndex][1],
					A_XJ_GOODS_INFO[goodsIndex][2],
					A_XJ_GOODS_INFO[goodsIndex][3]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleItem(int[][] szItem) {// 道具数量。结果列表[n][2]，[n][0]
											// 是道具id，[n][1]是道具数量。

	}

	public void handleLinked() {// soket连接已建立

		xj_tool.getParam(null);

	}

	public void handleParam(String paramName, String param) {// 处理存档
		handleParam(param);
	}

	public void handleReturn() {// 从消费界面返回时的回调方法
		if (goodsIndex == 9) {
			setView(new Dialog(this, 7, goBackView));
		} else {
			setView(goBackView);
		}
	}

	public void handleScore(int mission, int score) {// 处理积分

	}

	public void handleShop() {// 消费成功
		doBuySuccess();
		if (willStart) {// 开始游戏
			willStart = false;
			type_hero = CanvasControl.goodsIndex + 1;
			goodsNumber[CanvasControl.goodsIndex]--;
			setView(new Game(this));
		} else {
			setView(goBackView);
		}
	}

}
