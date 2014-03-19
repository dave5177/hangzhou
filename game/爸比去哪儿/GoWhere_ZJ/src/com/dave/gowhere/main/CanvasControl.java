package com.dave.gowhere.main;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.tool.C;
import com.dave.gowhere.view.Game;
import com.dave.gowhere.view.Home;
import com.dave.gowhere.view.NullView;
import com.dave.showable.Showable;

/**
 * @author Dave
 * 
 */
public class CanvasControl extends Canvas implements Runnable {

	private MainMIDlet midlet;

	public static String iptvID;
	
	public static boolean DEBUG = false;

	public final static String VERSION = "Version_ZJ_1.0.1";
	
	/**
	 * 空界面
	 */
	public final Showable nullView;
	
	/**
	 * 界面公用图片，选择红心
	 */
	public final Image img_choose;
	
	/**
	 * 按0键返回
	 */
	public final Image img_key_0_goback;

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

	/**
	 * 记录游戏帧数
	 */
	public static float fps;
	
	/**
	 * 每帧时间
	 */
	public long timePerFrame;
	
	/**
	 * 爸爸等级，0是未购买。张亮、田亮、小志。
	 */
	public static int[] dadLevel = {
		0, 1, 0
	};
	
	/**
	 * 英雄等级，0是未解锁。天天、森碟、kimi
	 */
	public static int[] babyLevel = {
		1, 0, 0
	};
	
	/**
	 * 7种道具数量。浮剃、无敌、磁铁、自动飞行、奶瓶、招财猫、毅力飞行。
	 */
	public static int[] goodsAmount = {
		2, 3, 0, 5, 1, 0, 0
	};

	/**
	 * 用来控制游戏帧率的默认循环时间
	 */
	public final static int DEFAULTCYCLEUSETIME = 80;

	public CanvasControl(MainMIDlet midlet) throws IOException {
		this.midlet = midlet;

		img_choose = Image.createImage("/choose.png");
		img_key_0_goback = Image.createImage("/key_0_back.png");
		
		nullView = new NullView();
		launch();
	}

	public void launch() {
//		setView(new Game(this, 2));
//		setView(new SceneChoose(this));
		setView(new Home(this));
		new Thread(this).start();
	}

	protected void paint(Graphics g) {
		view.show(g);
		
		/////////////////////////调试信息//////////////////////
		if(DEBUG) {
			g.setColor(0xffffff);
			g.drawString("FPS:  " + fps, 5, 5, 0);
			g.drawString("帧时间（毫秒）:  " + timePerFrame, 5, 25, 0);
			g.drawString("奔跑速度（px/frame):  " + ((Game)view).speed_run, 5, 45, 0);
//			g.drawString("按1,2,3,4键设置奔跑速度为8,12,15,20", 5, 65, 0);
		}
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
			if (cycleUseTime < DEFAULTCYCLEUSETIME) {
				try {
					Thread.sleep(DEFAULTCYCLEUSETIME - cycleUseTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			timePerFrame = System.currentTimeMillis() - cycleStartTime;
			if (cycleUseTime != 0)
				fps = 1000 / timePerFrame;
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
		if(keyCode == C.KEY_5) {
			DEBUG = DEBUG ? false: true;
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

}
