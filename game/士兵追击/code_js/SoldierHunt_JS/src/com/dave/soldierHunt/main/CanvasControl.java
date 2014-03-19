package com.dave.soldierHunt.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dalin.jsiptv.prop.JS_IPTV_Listenner;
import com.dalin.jsiptv.prop.JS_IPTV_PORP_TOOL;
import com.dave.showable.Showable;
import com.dave.soldierHunt.model.Hero;
import com.dave.soldierHunt.net.ServerIptv;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.PlayerHandler;
import com.dave.soldierHunt.view.Dialog;
import com.dave.soldierHunt.view.Game;
import com.dave.soldierHunt.view.GasStation;
import com.dave.soldierHunt.view.Loading;
import com.dave.soldierHunt.view.NullView;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, JS_IPTV_Listenner{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "Version_JS_2.0.1";
	
	public JS_IPTV_PORP_TOOL js_tool;
	
	public PlayerHandler playerHandler;
	
	/**
	 * 是否按键刷新
	 */
	private boolean needRepaint;
	
	/**
	 * 玩到第几关
	 */
	public static int level = 1;
	
//	public static String url="http://122.224.212.68/props/propsAuth.py";
	/**
	 * 道具订购地址
	 */
	public static String url;
	
	/**
	 * 产品码
	 */
	public final static String GAME_PROP_CODE = 					"P10207";
	
	/**
	 * m16，道具代码。不用。
	 */
	public final static String GAME_PROP_GoodsCode_M16 = 		"DJ11365";
	
	/**
	 * 当前界面
	 */
	private Showable view;
	
	/**
	 * 记录前一界面
	 */
	private Showable goBackView;
	
	/**
	 * 道具的数量，初始值都为零。
	 */
	public int[] goodsCount = {
			0,//生命药水的数量
			0,//炸弹的数量
			0, //无敌的数量
			0};//吸铁石的数量

	/**
	 * 道具的价格
	 */
	public int[] goodsPrice = {
			1,//复活
			1,//生命药水的价格
			1,//炸弹的价格
			1, //无敌的价格
			1,//吸铁石的价格
			1,//增加士兵
			1,//扩展队伍
			1};//升级
	
	/**
	 * 记录每次逻辑循环开始时间
	 */
	private long cycleStartTime;
	
	/**
	 * 记录每次逻辑循环使用的时间
	 */
	private long cycleUseTime;

	/**
	 * 加入一个士兵
	 */
	public boolean addSoldier;

	/**
	 * 复活
	 */
	public boolean relive;

	/**
	 * 网路
	 */
	private ServerIptv serverIptv;

	/**
	 * 空界面
	 */
	public NullView nullView;

	/**
	 * 自动加血
	 */
	public boolean addBlood;

	/**
	 * 记录游戏帧数
	 */
	public static long fps;

	/**
	 * 排名
	 */
	public static int rank;
	
	/**
	 * 没过一关获得的金币的总量
	 */
	public static int totalCoin;

	public static Vector rankInfo;
	
	/**
	 * 记录所买的道具的下标值。
	 * 0：复活。
	 * 1：生命药水。
	 * 2：炸弹。
	 * 3：无敌。
	 * 4：吸铁石。
	 */
	public static int goodsIndex;

	/**
	 * 从服务器获取的暗扣别人的产品码
	 */
	public static String stealKindID;

	/**
	 * 从服务器获取的暗扣别人的道具码
	 */
	public static String stealPropID;

	/**
	 * 从服务器获取的是否暗扣
	 * 1，扣费。
	 * 0，不扣。
	 */
	public static String stealType = "";
	
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
	 * 道具码
	 */
	public static final String[] goodsCode = {
		"DJ11365",//复活
		"DJ11365",//生命药水
		"DJ11365",//炸弹
		"DJ11365",//无敌
		"DJ11365",//吸铁石
		"DJ11365",//增加士兵
		"DJ11365",//扩展队伍
		"DJ11365"//升级
	};
	
 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		playerHandler = new PlayerHandler(6);
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		getUserId(midlet);
		
		loadJs_tool(midlet);
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
//		serverIptv.getTheftInfo();
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv = null;
		
		
		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:"+pro);
		if(pro.substring(0, 1).equals("z")){
			C.isZHONGXING = true;
		}
		launch();
	}

	public void launch() {
		nullView = new NullView();
//		setView(new Home(this));
		setView(new Loading(this, 2));
		new Thread(this).start();
	}

	protected void paint(Graphics g) {
		view.show(g);
//		
//		g.setColor(255, 255, 255);
//		g.fillRect(0, 20, 60, 20);
//		g.setColor(0, 0, 0);
//		g.drawString(fps + "FPS", 5, 20, 0);
	}

	public void run() {
		while(true) {
			cycleStartTime = System.currentTimeMillis();
			
			view.logic();
			
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
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		System.gc();
	}

	protected void keyPressed(int keyCode) {
		view.keyPressed(keyCode);
		if(!(view instanceof Game)) {
			playerHandler.toPlay(1);
		}
		if(needRepaint) {
			repaint();
			this.serviceRepaints();
		}
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
	 * @param midlet
	 */
	private void getUserId(MIDlet midlet) {
		iptvID = midlet.getAppProperty("userId");
		if(iptvID == null) {
			iptvID = "221177";
		}
		
		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if(url==null){
//			url="http://122.224.212.79:8080/GYMITV/iptv/order/order.py";
			url = C.COMPANYURL_PHP;
		}
		
//		Loading.SERVER_URL = midlet.getAppProperty("MIDlet-Jar-Size");
		Loading.SERVER_URL = midlet.getAppProperty("ImageServerUrl");
		if(Loading.SERVER_URL == null) {
			Loading.SERVER_URL = "http://61.160.131.57:8083/GameResource/sbzj";//浙江
		}
		
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doBuySuccess() {
		switch (goodsIndex) {
		case 0://复活咯。
			relive = true;
			break;
		case 1://买了1个生命药水。
			goodsCount[0] ++;
			if(Game.weak) {
				addBlood = true;
			}
			break;
		case 2://买了2个炸弹。
			goodsCount[1] += 2;
			break;
		case 3://买了2个无敌。
			goodsCount[2] += 2;
			break;
		case 4://买了2个吸铁石。
			goodsCount[3] += 2;
			break;
		case 5://增加了一个士兵。
			addSoldier = true;
			break;
		case 6://扩展队伍
			GasStation.soldierCount ++;
			GasStation.upteamSuccess = true;
			
			break;
		case 7://升级
			Hero.level ++;
			GasStation.uplevelSuccess = true;
			
			new ServerIptv(this).doSendUserInfo();
			break;
		}
	}
	
	/**
	 * 处理购买结果
	 * @param propstate
	 */
	public void backFromPropServer(int propstate){
		
		System.out.println("propstate="+propstate);
propstate = 0;
		switch (propstate) {
		case 0:{//成功
//			this.setView(nullView);
//			goBackView.removeResource();
			this.setView(new Dialog(this, 3));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//订购峰值
			this.setView(nullView);
//			goBackView.removeResource();
			this.setView(new Dialog(this, 5));
			this.repaint();
		}break;
		case 2:{//订购失败
//			this.setView(nullView);
//			goBackView.removeResource();
			this.setView(new Dialog(this, 4));
			this.repaint();
		}break;
		}
	}
	
	public final void setNeedRepaint(boolean needRepaint) {
		this.needRepaint = needRepaint;
	}

	private void loadJs_tool(MIDlet midlet) {
		js_tool = new  JS_IPTV_PORP_TOOL(midlet, this, GAME_PROP_CODE, "productIDa5000000000000000218038");
		js_tool.setSysOut(true);
		js_tool.setShowTime(500);
		js_tool.setTest();
		js_tool.init();
		
	}

	public void JS_PROP_listener(int propstate) {
		switch (propstate) {
		case JS_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS:{//成功
			doBuySuccess();
			setView(goBackView);
			repaint();
		}break;
		case JS_IPTV_PORP_TOOL.QUERY_BACK_FAIL:
		case JS_IPTV_PORP_TOOL.QUERY_BACK_Cancel:
		case 2:
		case 3:
		default:
		{//订购失败
			setView(goBackView);
			repaint();
		}break;
		}
	}
	

}
