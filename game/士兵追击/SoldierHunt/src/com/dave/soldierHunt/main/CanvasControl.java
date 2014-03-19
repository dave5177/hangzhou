package com.dave.soldierHunt.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.showable.Showable;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.view.Dialog;
import com.dave.soldierHunt.view.Home;

/**
 * @author Dave
 *
 */
/**
 * @author Administrator
 *
 */
public class CanvasControl extends Canvas implements Runnable{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "Version_1.0.1";
	
	private boolean needRepaint;
	
	/**
	 * 当前第几关
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
	 * 记录每次逻辑循环开始时间
	 */
	private long cycleStartTime;
	
	/**
	 * 记录每次逻辑循环使用的时间
	 */
	private long cycleUseTime;

	public static int buyBullets;

	/**
	 * 记录游戏帧数
	 */
	public static long fps;

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
		launch();
	}

	public void launch() {
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
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		
		System.gc();
	}

	protected void keyPressed(int keyCode) {
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
		
		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if(url==null){
//			url="http://122.224.212.79:8080/GYMITV/iptv/order/order.py";
			url = C.COMPANYURL_PHP;
		}
	}

	/**
	 * 购买成功后的处理逻辑
	 */
	public void doAfterBuyGoods() {
		switch (goodsIndex) {
		case 0://复活咯。
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
		case 0:{//成功
			this.setView(new Dialog(this, 3));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//订购峰值
			this.setView(new Dialog(this, 5));
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

}
