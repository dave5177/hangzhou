package com.dave.rangzidanf.main;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dalin.jsiptv.prop.JS_IPTV_Listenner;
import com.dalin.jsiptv.prop.JS_IPTV_PORP_TOOL;
import com.dave.rangzidanf.net.ServerIptv;
import com.dave.rangzidanf.tool.AudioPlay;
import com.dave.rangzidanf.tool.C;
import com.dave.rangzidanf.view.Dialog;
import com.dave.rangzidanf.view.Game;
import com.dave.rangzidanf.view.Home;
import com.dave.rangzidanf.view.Showable;

/**
 * @author Dave
 *
 */
/**
 * @author Administrator
 *
 */
public class CanvasControl extends Canvas implements Runnable, JS_IPTV_Listenner {
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "Version_JS_2.0.6";
	
	public JS_IPTV_PORP_TOOL js_tool;
	
	private boolean needRepaint;
	
	public ServerIptv serverIptv;
	
	/**
	 * 当前第几关
	 */
	public static int level = 1;
	
//	public static String url="http://122.224.212.68/props/propsAuth.py";
	/**
	 * 道具订购地址
	 */
	public static String url = "http://61.160.131.57:8083/www.iptvgame.com/";//江苏服务器
	
	/**
	 * 产品码
	 */
	public final static String GAME_PROP_CODE = 				"P10207";
//	public final static String GAME_PROP_CODE = 				"P10168";
	/**
	 * m16，道具代码。不用。
	 */
	public final static String GAME_PROP_GoodsCode_M16 = 		"DJ11365";
	/**
	 * at15道具码
	 */
	public final static String GAME_PROP_GoodsCode_AT15 = 		"DJ11364";//这个是0.5元的本来是ak的价格这里交换。
//	public final static String GAME_PROP_GoodsCode_AT15 = 		"DJ10984";
//	public final static String GAME_PROP_GoodsCode_AT15 = 		"DJ11366";//这个是一元的道具。用在ak那里。
	/**
	 * ak47道具码
	 */
	public final static String GAME_PROP_GoodsCode_AK47 = 		"DJ11366";
	/**
	 * m4道具码
	 */
	public final static String GAME_PROP_GoodsCode_M4A1S = 		"DJ11367";
	/**
	 * awm道具码
	 */
	public final static String GAME_PROP_GoodsCode_AWM = 		"DJ11363";
	/**
	 * 大炮道具码
	 */
	public final static String GAME_PROP_GoodsCode_BARRETT = 	"DJ11368";
	/**
	 * 自动换子弹道具码
	 */
	public final static String GAME_PROP_GoodsCode_AutoCB = 	"DJ11357";
	/**
	 * 自动瞄准道具码
	 */
	public final static String GAME_PROP_GoodsCode_AutoAim = 	"DJ11358";
	/**
	 * 复活道具码
	 */
	public final static String GAME_PROP_GoodsCode_Relive = 	"DJ11359";
	/**
	 * 弹药道具码
	 */
	public final static String GAME_PROP_GoodsCode_Bullet = 	"DJ11360";
	/**
	 * 隐身道具码
	 */
	public final static String GAME_PROP_GoodsCode_Stealth = 	"DJ11361";
	/**
	 * 高爆子弹道具码
	 */
	public final static String GAME_PROP_GoodsCode_HighBoom = 	"DJ11362";
	
	/**
	 * 保存当前用户所持有的抢的下标值
	 * 默认为2，就是m16
	 */
	private int gunIndex = 0;

	/**
	 * 分别记录是否持有该枪。下标值与枪的下标值对应。
	 * 1为拥有，0为不拥有。
	 */
	public static int[] hasGun = {1, 0, 0, 0, 0, 0};
	
	/**
	 * 保存用户是否已经购买高爆子弹。
	 * 0：否。
	 * 1：是。
	 */
	public static int highboom = 0;
	
	/**
	 * 保存用户是否已经购买自动换子弹。
	 * 0：否。
	 * 1：是。
	 */
	public static int autocb = 0;
	
	/**
	 * 用户第一次玩的话就是1
	 */
	public static int firstTimes = 0;
	
	/**
	 * 隐身控制
	 * 0：否
	 * 1：是
	 */
	public static int stealth = 0;
	
	/**
	 * 自动瞄准控制
	 * 0：否
	 * 1：是
	 */
	public static int autoAim = 0;

	/**
	 * 存储每关各种级别敌人的数量
	 */
	public static int[][] enemyQuantity = {
		{12, 3, 0, 0, 0},
		{13, 3, 2, 0, 0},
		{9, 5, 5, 1, 0},
		{8, 6, 7, 2, 1},
		{9, 8, 3, 3, 3},
		{5, 10, 6, 4, 5}
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
	 * 记录每次逻辑循环开始时间
	 */
	private long cycleStartTime;
	
	/**
	 * 记录每次逻辑循环使用的时间
	 */
	private long cycleUseTime;

	public static int buyBullets;

	/**
	 * 是不是买了复活道具啊
	 */
	public static int relive;
	
	/**
	 * 剩余的敌人数
	 */
	public static int restEnemy;
	
	/**
	 * 记录游戏帧数
	 */
	public static long fps;

	/**
	 * 各个关卡得分
	 */
	public static int level_1_score;
	public static int level_2_score;
	public static int level_3_score;
	public static int level_4_score;
	public static int level_5_score;
	public static int level_6_score;

	/**
	 * 总得分
	 */
	public static int totalScore;

	/**
	 * 排名
	 */
	public static int rank;

	public static Vector rankInfo;
	
	/**
	 * 记录所买的道具的下标值。
	 */
	public static int goodsIndex;

	/**
	 * 隐身剩余时间
	 */
	public static int stealthRemain;

	/**
	 * 自动瞄准剩余时间
	 */
	public static int autoAimRemain;

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
	 * 声音播放类
	 */
	public AudioPlay audio; 
	
	/**
	 * 进入游戏的时间
	 */
	public static long appStartTime;
	
	public boolean isGameing;
	
//	public static AudioPlay player;

 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		getUserId(midlet);
		
		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:"+pro);
		if(pro.substring(0, 1).equals("z")){
			C.isZHONGXING = true;
		}
		
		loadJs_tool(midlet);

		audio = new AudioPlay();
		
		audio.setAudioCount((byte) 7);
		audio.loadSource();
		
		/*if(C.isZHONGXING) {
			audio.setAudioCount((byte) 6);
			audio.loadSource();
		} else {
			audio.setAudioCount((byte)7);
			audio.loadSource();
			audio.player[6].setLoopCount(-1);
		}*/
		
		launch();
	}

	public void launch() {
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
//		serverIptv.getTheftInfo();
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv = null;
		
		setView(new Home(this));
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
		if(view instanceof Game){
			isGameing = true;
		}else isGameing = false;
		
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		
		System.gc();
	}

	protected void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
		if(!isGameing) {
			audio.toPlay(6);
		}
		
		view.keyPressed(keyCode);
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
			iptvID = "0571000447";
		}
		
		/*url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if(url==null){
			url="http://122.224.212.79:8080/GYMITV/iptv/order/order.py";
		}*/
	}

	public final int getGunIndex() {
		return gunIndex;
	}

	public final void setGunIndex(int gunIndex) {
		this.gunIndex = gunIndex;
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doAfterBuyGoods() {
		switch (goodsIndex) {
		case 0:
			hasGun[goodsIndex] = 1;//买了那把枪，这里因为设置成对应下标值
			gunIndex = goodsIndex;//直接把所用枪支设置为刚买的这把
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_M16);
			break;
		case 1:
			hasGun[goodsIndex] = 1;//买了那把枪，这里因为设置成对应下标值
			gunIndex = goodsIndex;//直接把所用枪支设置为刚买的这把
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_AT15);
			break;
		case 2:
			hasGun[goodsIndex] = 1;//买了那把枪，这里因为设置成对应下标值
			gunIndex = goodsIndex;//直接把所用枪支设置为刚买的这把
//			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_AK47); //ak不用上传
			break;
		case 3:
			hasGun[goodsIndex] = 1;//买了那把枪，这里因为设置成对应下标值
			gunIndex = goodsIndex;//直接把所用枪支设置为刚买的这把
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_M4A1S);
			break;
		case 4:
			hasGun[goodsIndex] = 1;//买了那把枪，这里因为设置成对应下标值
			gunIndex = goodsIndex;//直接把所用枪支设置为刚买的这把
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_AWM);
			break;
		case 5:
			hasGun[goodsIndex] = 1;//买了那把枪，这里因为设置成对应下标值
			gunIndex = goodsIndex;//直接把所用枪支设置为刚买的这把
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_BARRETT);
			break;
		case 6://高爆子弹购买成功
			highboom = 1;
			new ServerIptv(this).doSendUserInfo();//买好了，长传服务器。
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_HighBoom);
			break;
		case 7://隐身衣服购买成功
			stealth = 1;
			stealthRemain += 20;
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_Stealth);
			break;
		case 8://自动瞄准购买成功
			autoAim = 1;
			autoAimRemain += 30;
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_AutoAim);
			break;
		case 9://自动换子弹购买成功
			autocb = 1;
			new ServerIptv(this).doSendUserInfo();//买好了，上传服务器。
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_AutoCB);
			break;
		case 10://买了云南白药
			relive = 1;
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_Relive);
			break;
		case 11://买了子弹
			buyBullets = 1;
			new ServerIptv(this).sendBuyInfo(GAME_PROP_GoodsCode_Bullet);
			break;
			
		}
	}
	
	/**
	 * 处理购买结果
	 * @param propstate
	 */
	public void backFromPropServer(int propstate){
		
		System.out.println("propstate="+propstate);
		switch (propstate) {
//		case 2:
//		case 3:
		case 0:{//成功
			new Timer().schedule(new TimerTask() {
				
				public void run() {
					doAfterBuyGoods();
				}
			}, 900);
			
			this.setView(new Dialog(this, 3));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//订购峰值
			this.setView(new Dialog(this, 10));
			this.repaint();
		}break;
		case 2:{//订购失败
			this.setView(new Dialog(this, 4));
			this.repaint();
		}break;
		}
	}
	
	public final void setNeedRepaint(boolean needRepaint) {
		this.needRepaint = needRepaint;
	}

	
	/*public void ZJIPTV_Result(int index, String returnStr, Hashtable hashtable, Vector v_Rank) {
		switch(index){
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_USER_QUERY:{//用户鉴权
//			C.out_system("用户鉴权:"+"\n"+backstr);
//			System.out.println("message:"+hashtable.get("message"));
		}break;
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_PROPS_ORDER:{//用户订购道具
//			C.out_system("用户鉴权订购道具:"+"\n"+backstr);
			System.out.println("message:"+hashtable.get("message"));
		}break;
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_RANK_CROSSPROPERTYSAVEORUPDATE:{//上传两个数据到服务器(多个参数时)
//			System.out.println("上传两个数据到服务器(多个参数时):"+"\n"+returnStr);
			System.out.println("message:"+hashtable.get("message"));
		}break;
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_RANK_CROSSPROPERTYQUERY:{//请求积分排行榜-----多个参数,则调用这个
//			v_rank = v_Rank;
			System.out.println("请求积分排行榜-----多个参数,则调用这个:"+"\n"+returnStr);
//			System.out.println("一共几个:"+v_Rank.size());
//			zj_iptv_Parse.getParseXml(backstr);
//			System.out.println("accountStb:"+hashtable.get("accountStb"));
			System.out.println("message:"+hashtable.get("message"));
			System.out.println("排行榜个数:"+v_Rank.size());
//			test(v_Rank);
			
//			<levels>11</levels>
//			<rankPoints>11</rankPoints>
//			<rankOwn>1</rankOwn>m
//			C.rank_Gold = Integer.parseInt(""+hashtable.get("levels"));
//			C.rank_Time = Integer.parseInt(""+hashtable.get("rankPoints"));
//			C.rank_Rank = Integer.parseInt(""+hashtable.get("rankOwn"));
			
//			setState(CanvasControl.sRank,0);
		}break;
		}
	}*/
	
	private void loadJs_tool(MIDlet midlet) {
		js_tool = new  JS_IPTV_PORP_TOOL(midlet, this, GAME_PROP_CODE, "productIDa5000000000000000217432");
		js_tool.setSysOut(true);
		js_tool.setShowTime(500);
		js_tool.setTest();
		js_tool.init();
	}

	public void JS_PROP_listener(int propstate) {
		switch (propstate) {
		case JS_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS:{//成功
			doAfterBuyGoods();
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
