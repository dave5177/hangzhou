package com.dave.paoBing.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.paoBing.model.Hero;
import com.dave.paoBing.net.ServerIptv;
import com.dave.paoBing.tool.C;
import com.dave.paoBing.view.Dialog;
import com.dave.paoBing.view.Game;
import com.dave.paoBing.view.Home;
import com.dave.paoBing.view.NullView;
import com.dave.showable.Showable;
import com.zn.hs.prop.HSProp;
import com.zn.hs.prop.HSPropListenner;
//import com.dave.paoBing.view.Loading;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, HSPropListenner{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	/**
	 * 华数工具
	 */
	public HSProp hs_tool;
	
	public final static String VERSION = "Version_HS_2.0.5";
	
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
	 * 产品码
	 */
	public final static String GAME_PROP_CODE = 					"P31010";
	
	/**
	 * 生命恢复，道具代码。
	 */
	public final static String GAME_PROP_GoodsCode_LIFE = 			"DJ32001";
	
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
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		getServerInfo(midlet);
		
		hs_init(midlet);
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		appStartTime = System.currentTimeMillis();
//		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
//		serverIptv.sendClickTimes();
		serverIptv = null;
		
//		playerHandler = new PlayerHandler(7, true);
		
		nullView = new NullView();
		
		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:"+pro);
		if(pro.substring(0, 1).equals("z")){
			C.isZHONGXING = true;
		}
		launch();
	}

	private void hs_init(MIDlet midlet) {
		
		hs_tool = new HSProp(midlet, this);
		
		// 如果是测试，则
//		hs_tool.set_isTest(true);
		
		// 如果需要打印运行结果，则：
//		hs_tool.set_isSysOut(true);
		// 如下必需设置
		hs_tool.setCpcode("204");// CPcode是固定的204
		hs_tool.setActionID("657");// 设置游戏ID，其它游戏请咨询，测试的时候用400
	}
	
	public void launch() {
		setView(new Home(this));
//		setView(new Loading(this, 2));
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
//		if(!(view instanceof Game)) {
//			playerHandler.playByIndex(1);
//		}
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
			iptvID = "66633300103";
		}
		
		String temp_ServiceIP = midlet.getAppProperty("ServiceIP");
		if(temp_ServiceIP == null){
			HSProp.stbid = "057100011";
			HSProp.userid = "HS05712315";
			HSProp.userid = "057100011";
			C.COMPANYURL_PHP = "http://10.48.179.118/app/";
//			C.COMPANYURL_PHP = "http://192.168.1.197/";//测试时用197，正式平台的时候用上面这个地址
		}else{
			C.COMPANYURL_PHP = "http://"+temp_ServiceIP+"/app/";
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
		
		hs_tool.DoBuyPropSuccess(iptvID, GAME_PROP_CODE, Dialog.GOODS_PRICE[goodsIndex - 1]);
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

	public void onResult(String serviceType, String state, String limitfee,String code, String pwd){
		if(state.equals("+OK")){//成功
			this.setView(new Dialog(this, 3, null));
		}else{//失败
			this.setView(new Dialog(this, 4, null));
		}
	}
	

}
