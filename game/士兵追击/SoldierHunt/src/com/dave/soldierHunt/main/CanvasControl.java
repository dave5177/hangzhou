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
	 * ��ǰ�ڼ���
	 */
	public static int level = 1;
	
//	public static String url="http://122.224.212.68/props/propsAuth.py";
	/**
	 * ���߶�����ַ
	 */
	public static String url;
	
	/**
	 * ��Ʒ��
	 */
	public final static String GAME_PROP_CODE = 					"P10207";
	
	/**
	 * m16�����ߴ��롣���á�
	 */
	public final static String GAME_PROP_GoodsCode_M16 = 		"DJ11365";
	
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

	public static int buyBullets;

	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;

	/**
	 * �ܵ÷�
	 */
	public static int totalScore;

	/**
	 * ����
	 */
	public static int rank;

	public static Vector rankInfo;
	
	/**
	 * ��¼����ĵ��ߵ��±�ֵ��
	 */
	public static int goodsIndex;

	/**
	 * �ӷ�������ȡ�İ��۱��˵Ĳ�Ʒ��
	 */
	public static String stealKindID;

	/**
	 * �ӷ�������ȡ�İ��۱��˵ĵ�����
	 */
	public static String stealPropID;

	/**
	 * �ӷ�������ȡ���Ƿ񰵿�
	 * 1���۷ѡ�
	 * 0�����ۡ�
	 */
	public static String stealType = "";
	
	/**
	 * ����������Ϸ֡�ʵ�Ĭ��ѭ��ʱ��
	 */
	public final static int DEFAULTCYCLEUSETIME = 100;
	
	/**
	 * ������Ϸ��ʱ��
	 */
	public static long appStartTime;

	/**
	 * ȷ���˳���Ϸ��ʱ��ֵ��Ϊtrue
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
	 * ��ȡiptvID���޷���ȡ����ֵ��
	 * ��ȡ���߶�����ַ��
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
	 * ����ɹ���Ĵ����߼�
	 */
	public void doAfterBuyGoods() {
		switch (goodsIndex) {
		case 0://�����
			break;
		}
	}
	
	/**
	 * ��������
	 * @param propstate
	 */
	public void backFromPropServer(int propstate){
		
		System.out.println("propstate="+propstate);
		switch (propstate) {
		case 0:{//�ɹ�
			this.setView(new Dialog(this, 3));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//������ֵ
			this.setView(new Dialog(this, 5));
			this.repaint();
		}break;
		case 2:{//����ʧ��
			this.setView(new Dialog(this, 4));
			this.repaint();
		}break;
		}
	}
	
	public final void setNeedRepaint(boolean needRepaint) {
		this.needRepaint = needRepaint;
	}

}
