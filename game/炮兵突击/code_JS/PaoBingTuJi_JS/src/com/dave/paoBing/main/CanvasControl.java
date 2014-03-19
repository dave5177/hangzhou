package com.dave.paoBing.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dalin.jsiptv.prop.JS_IPTV_Listenner;
import com.dalin.jsiptv.prop.JS_IPTV_PORP_TOOL;
import com.dave.paoBing.model.Hero;
import com.dave.paoBing.net.ServerIptv;
import com.dave.paoBing.tool.C;
import com.dave.paoBing.tool.PlayerHandler;
import com.dave.paoBing.view.Dialog;
import com.dave.paoBing.view.Game;
import com.dave.paoBing.view.Loading;
import com.dave.paoBing.view.NullView;
import com.dave.showable.Showable;
//import com.dave.paoBing.view.Loading;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, JS_IPTV_Listenner{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public JS_IPTV_PORP_TOOL js_tool;
	
	public final static String VERSION = "Version_JS_3.0.5";
	
	/**
	 * 是否按键刷新
	 */
	private boolean needRepaint;
	
	/**
	 * 玩到第几关
	 */
	public static int mission = 1;
	
	/**
	 * 道具订购地址
	 */
	public static String url;
	

	/**
	 * 游戏图片存放的服务器地址
	 */
	public static String imageServerUrl = "";//从jad获取

	
	/**
	 * 产品码
	 */
	public final static String GAME_PROP_CODE = 					"P31010";
	
	/**
	 * 生命恢复，道具代码。
	 */
	public final static String GAME_PROP_GoodsCode_LIFE = 		"DJ32001";
	
	/**
	 * 补充子弹
	 */
	public final static String GAME_PROP_GoodsCode_BULLET = 		"DJ32002";
	
	/**
	 * 双倍威力
	 */
	public final static String GAME_PROP_GoodsCode_DOUBLE = 		"DJ32003";
	
	/**
	 * 复活
	 */
	public final static String GAME_PROP_GoodsCode_RELIVE = 		"DJ32004";
	
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
	 * 记录游戏帧数
	 */
	public static long fps;

	/**
	 * 排名
	 */
	public static int rank;
	
	/**
	 * 排行榜数据：完成的关卡
	 */
	public static int complete;
	
	/**
	 * 排行榜信息，每一条是一个对应的string数组
	 */
	public static Vector rankInfo;
	
	/**
	 * 记录所买的道具的下标值。
	 * 1：生命。
	 * 2：子弹。
	 * 3：双倍威力。
	 * 4：复活。
	 */
	public static int goodsIndex;
	
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
	
 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		
		loadJs_tool(midlet);
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		getServerInfo(midlet);
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv = null;
		
		playerHandler = new PlayerHandler(7, true);
		
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
		setView(new Loading(this, 2));
//		setView(new Complete(this));
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
			playerHandler.playByIndex(1);
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
	 * 获取图片服务器地址。
	 * @param midlet
	 */
	private void getServerInfo(MIDlet midlet) {
		iptvID = midlet.getAppProperty("userId");
		if(iptvID == null) {
			iptvID = "666333000";
		}
		
		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if(url==null){
			url = C.COMPANYURL_PHP;
		}
		

		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
		if(imageServerUrl==null){
			imageServerUrl = "http://61.160.131.57:8083/GameResource/ftxz";
		}
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doBuySuccess() {
		switch (goodsIndex) {
		case 1:
			Hero.life = Hero.lifeMax;
			break;
		case 2:
			Game.remain_missile += 4;
			break;
		case 3:
			Game.doublePower = Game.remain_missile;
			break;
		case 4:
			Hero.life = Hero.lifeMax;
//			Game.remain_missile = 4;
			break;
		default:
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
			this.setView(new Dialog(this, 3, null));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//订购峰值
			this.setView(new Dialog(this, 10, null));
			this.repaint();
		}break;
		case 2:{//订购失败
			this.setView(new Dialog(this, 4, null));
			this.repaint();
		}break;
		}
	}
	
	public final void setNeedRepaint(boolean needRepaint) {
		this.needRepaint = needRepaint;
	}

	private void loadJs_tool(MIDlet midlet) {
		js_tool = new  JS_IPTV_PORP_TOOL(midlet, this, GAME_PROP_CODE, "productIDa5000000000000000218510");
//		js_tool.setSysOut(true);
		js_tool.setShowTime(500);
//		js_tool.setTest();
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
			if(goodsIndex == 4) {
				setView(new Loading(this, 2));
			} else {
				setView(goBackView);
			}
			repaint();
		}break;
		}
	}
	

}
