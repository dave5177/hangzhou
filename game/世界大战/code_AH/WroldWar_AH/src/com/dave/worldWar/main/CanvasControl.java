package com.dave.worldWar.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.anHui.AnHuiHandler;
import com.dave.anHui.AnHui_Tool;
import com.dave.showable.Showable;
import com.dave.worldWar.net.ServerIptv;
import com.dave.worldWar.tool.C;
import com.dave.worldWar.tool.PlayerHandler;
import com.dave.worldWar.view.Dialog;
import com.dave.worldWar.view.Game;
import com.dave.worldWar.view.Loading;
import com.dave.worldWar.view.NullView;
//import com.dave.paoBing.view.Loading;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, AnHuiHandler{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "Version_AH_1.0.3";
	
	/**
	 * 安徽移植工具
	 */
	public  AnHui_Tool anHui_Tool;
	
	/**
	 * 玩到第几关
	 */
	public static int mission = 1;
	
	/**
	 * 各个士兵的等级,0代表未解锁。
	 * 从左到右分别代表步兵、炮兵、火箭兵、狙击兵、坦克兵、轰炸机
	 */
	public static int[] soldiers_level = {
		1, 0, 0, 0, 0, 1
	};
	
	/**
	 * 士兵基本属性值
	 * 从左到右分别代表 血量、攻击力、射击范围、攻击速度、移动速度、命中率、没波数量
	 */
	public static int[][] soldiers_prpty = {
		{50, 50, 450, 10, 6, 30, 5},//步兵
		{60, 80, 350, 4, 7, 50, 3},//炮兵
		{80, 80, 400, 3, 10, 60, 3},//火箭兵
		{90, 90, 550, 6, 8, 90, 2},//狙击手
		{100, 100, 600, 8, 2, 70, 2}//坦克兵
	};
	
	/**
	 * 从服务器获取存档可得到，选择阵营之前为0。1：G国。2：U国。
	 */
	public static int group = 0;

	/**
	 * 游戏图片存放的服务器地址
	 */
	public static String imageServerUrl = "";//从jad获取
	
	/**
	 * 产品码
	 */
	public final static String GAME_PROP_CODE = 					"P31014";
	
	/**
	 * 道具的参数0：道具码，1：价格。
	 */
	public final static String[][] A_GOODS_PARAM = {
		{"DJ32001", "2"},//解锁步兵（用不到）
		{"DJ32031", "1"},//解锁炮兵
		{"DJ32034", "1.5"},//解锁火箭兵
		{"DJ32037", "1.5"},//解锁狙击手
		{"DJ32040", "2"},//解锁坦克兵
		{"DJ32030", "1"},//升级步兵
		{"DJ32033", "1"},//升级炮兵
		{"DJ32036", "1"},//升级火箭兵
		{"DJ32039", "1"},//升级狙击手
		{"DJ32042", "1"},//升级坦克兵
		{"DJ32029", "1"},//派兵步兵
		{"DJ32032", "1"},//派兵炮兵
		{"DJ32035", "1.5"},//派兵火箭兵
		{"DJ32038", "1.5"},//派兵狙击手
		{"DJ32041", "2"},//派兵坦克兵
		{"DJ32043", "1"},//轰炸机
		{"DJ32044", "1"}//复活
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
	 * 声音控制
	 * 其中的:0、背景。
	 * 1、按键。
	 * 2、炮兵射击。
	 * 3、敌人射击。
	 * 4、敌人死亡。
	 * 5、闯关成功。
	 * 6、闯关失败。
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
	 * 网路
	 */
	private ServerIptv serverIptv;

	/**
	 * 空界面
	 */
	public final NullView nullView;

	/**
	 * 用户元宝数
	 */
	public static String userCoin = "0";

	/**
	 * 记录游戏帧数
	 */
	public static long fps;

	/**
	 * 排名
	 */
	public static int rank;
	
	/**
	 * 排行榜信息，每一条是一个对应的string数组
	 */
	public static Vector rankInfo_g;

	/**
	 * u国排行榜
	 */
	public static Vector rankInfo_u;
	
	/**
	 * 记录所买的道具的下标值。
	 * 1 ~ 4：解锁士兵。
	 * 5 ~ 9：升级。
	 * 10 ~ 14：派兵。
	 * 15：轰炸机。
	 * 16：复活。
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
	 * 存储到服务器的字符串，保存用户数据
	 */
	public static String serverStr;

	/**
	 * 当前关卡得分
	 */
	public static int score_mission;
	
	public static int totalScore;

 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
C.out("cavas宽度：" + C.WIDTH + "\ncavas高度：" + C.HEIGTH);
		getServerInfo(midlet);
		anHui_Tool = new AnHui_Tool(midlet, iptvID, this, GAME_PROP_CODE, false);
		updateUserCoin();
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		serverIptv.doGetRank(0);
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv = null;
		
		playerHandler = new PlayerHandler(3, true);
		
		nullView = new NullView();
		
		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:"+pro);
		if(pro.substring(0, 1).equals("z")){
			C.isZHONGXING = true;
		}
		launch();
	}

	public void launch() {
//		setView(new Home(this));
		setView(new Loading(this, 0));
//		setView(new Complete(this));
		new Thread(this).start();
	}

	protected void paint(Graphics g) {
		view.show(g);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 主逻辑
	 */
	public void run() {
		while(true) {
			cycleStartTime = System.currentTimeMillis();
			
			view.logic();
			
			repaint();
			serviceRepaints();
			
			cycleUseTime = System.currentTimeMillis() - cycleStartTime;
			if(cycleUseTime != 0) fps = 1000 / cycleUseTime;
			
			if(cycleUseTime < DEFAULTCYCLEUSETIME) {
				try {
					Thread.sleep(DEFAULTCYCLEUSETIME - cycleUseTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setView(Showable view) {
		if(view instanceof Loading) {
			playerHandler.stopByIndex(0);
		}
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		System.gc();
	}

	protected void keyPressed(int keyCode) {
		playerHandler.playByIndex(1);
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
	 * 获取iptvID，无法获取则传入值。
	 * 获取道具订购地址。
	 * 获取图片服务器地址。
	 * @param midlet
	 */
	private void getServerInfo(MIDlet midlet) {
		iptvID = midlet.getAppProperty("userId");
		if(iptvID == null) {
			iptvID = "90000138";
		}
		
		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
		if(imageServerUrl==null){
			imageServerUrl = "http://61.191.44.224:8080/GameResource/worldWar";//本地地址
		}
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doBuySuccess() {
		if(goodsIndex < 5) { //解锁
			soldiers_level[goodsIndex] = 1;
			saveParam();
		} else if(goodsIndex < 10){ //升级
			soldiers_level[goodsIndex - 5] += 1;
			saveParam();
		} else if (goodsIndex < 15) { //派兵
			Game.rem_sendTimes[goodsIndex - 10] += 5;
		} else if(goodsIndex == 15) { //轰炸机
			Game.sendBomber = true;
		} else if(goodsIndex == 16) { //复活
			Game.relive = true;
		}
		
		new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
	}
	
	/**
	 * 处理购买结果
	 * @param propstate
	 */
	public void backFromPropServer(int propstate){
		
		System.out.println("propstate="+propstate);
//propstate = 0;
		switch (propstate) {
		case 0:{//成功
			this.setView(new Dialog(this, 3, null));
		}break;
		case 3:{//订购峰值
			this.setView(new Dialog(this, 10, null));
		}break;
		case 2:{//订购失败
			this.setView(new Dialog(this, 4, null));
		}break;
		}
	}

	/**
	 * 存档
	 */
	public void saveParam() {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(mission);
		sb_param.append(",");
		sb_param.append(group);
		for (int i = 0; i < soldiers_level.length; i++) {
			sb_param.append(",");
			sb_param.append(soldiers_level[i]);
		}
		serverStr = sb_param.toString();
System.out.println(serverStr);
		new ServerIptv(this).doSendUserInfo(serverStr);
	}
	
	/**
	 * 处理得到的存档字符串
	 * @param param
	 */
	public void handleParam(String param) {
C.out("处理存档--------" + param);
		String[] a_s_param = subString(param, ',', 8);
		mission = Integer.parseInt(a_s_param[0]);
		group = Integer.parseInt(a_s_param[1]);
		for (int i = 2; i < a_s_param.length; i++) {
			soldiers_level[i - 2] = Integer.parseInt(a_s_param[i]);
		}
	}

	/**
	 * 截取字符串
	 * @param str 目标字符串
	 * @param c 截断点的字符串（不包括c）
	 * @param length 截取后的得到的字符串数组长度
	 * @return 得到的字符数组
	 */
	private String[] subString(String str, char c, int length) {
		String[] result_a_str = new String[length];
		int index = 0;
		int end = 0;
		for (int i = 0; i < result_a_str.length; i++) {
			end = str.indexOf(c, index);
			if(end == -1) {
				end = str.length();
			}
			result_a_str[i] = str.substring(index, end);
//C.out("截取字符串始末位置--------起始：" + index + "----结束：" + end);
			index = end + 1;
			
		}
		return result_a_str;
	}

	public void handleBuy(String resultCode) {
		if ("1212200".equals(resultCode)) { //订购成功
			updateUserCoin();
			new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
			this.setView(new Dialog(this, 3, null));
		} else {//订购失败
			this.setView(new Dialog(this, 4, null));
		}
	}

	/**
	 * 更新用户元宝数量
	 */
	private void updateUserCoin() {
		userCoin = anHui_Tool.getCoin();
	}

	public void handleRecharge(String resultCode) {
	}
}
