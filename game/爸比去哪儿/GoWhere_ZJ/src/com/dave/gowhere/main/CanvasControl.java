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
	 * �ս���
	 */
	public final Showable nullView;
	
	/**
	 * ���湫��ͼƬ��ѡ�����
	 */
	public final Image img_choose;
	
	/**
	 * ��0������
	 */
	public final Image img_key_0_goback;

	/**
	 * ��ǰ����
	 */
	private Showable view;

	/**
	 * ��¼ǰһ����
	 */
	private Showable goBackView;

	/**
	 * ��¼ÿ���߼�ѭ����ʼʱ��
	 */
	private long cycleStartTime;

	/**
	 * ��¼ÿ���߼�ѭ��ʹ�õ�ʱ��
	 */
	private long cycleUseTime;

	/**
	 * ��¼��Ϸ֡��
	 */
	public static float fps;
	
	/**
	 * ÿ֡ʱ��
	 */
	public long timePerFrame;
	
	/**
	 * �ְֵȼ���0��δ����������������С־��
	 */
	public static int[] dadLevel = {
		0, 1, 0
	};
	
	/**
	 * Ӣ�۵ȼ���0��δ���������졢ɭ����kimi
	 */
	public static int[] babyLevel = {
		1, 0, 0
	};
	
	/**
	 * 7�ֵ������������ꡢ�޵С��������Զ����С���ƿ���в�è���������С�
	 */
	public static int[] goodsAmount = {
		2, 3, 0, 5, 1, 0, 0
	};

	/**
	 * ����������Ϸ֡�ʵ�Ĭ��ѭ��ʱ��
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
		
		/////////////////////////������Ϣ//////////////////////
		if(DEBUG) {
			g.setColor(0xffffff);
			g.drawString("FPS:  " + fps, 5, 5, 0);
			g.drawString("֡ʱ�䣨���룩:  " + timePerFrame, 5, 25, 0);
			g.drawString("�����ٶȣ�px/frame):  " + ((Game)view).speed_run, 5, 45, 0);
//			g.drawString("��1,2,3,4�����ñ����ٶ�Ϊ8,12,15,20", 5, 65, 0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run() ���߼�
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
