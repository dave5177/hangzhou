package com.dave.rangzidanf.main;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dalin.zjiptv.prop.ZJIPTV_Listenner;
import com.dalin.zjiptv.prop.ZJ_IPTV_PORP_TOOL;
import com.dave.rangzidanf.net.ServerIptv;
import com.dave.rangzidanf.tool.AudioPlaydalin;
import com.dave.rangzidanf.tool.C;
import com.dave.rangzidanf.view.Dialog;
import com.dave.rangzidanf.view.Home;
import com.dave.rangzidanf.view.Showable;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, ZJIPTV_Listenner {
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "V_ZJ_2.1.6";
	
	public ZJ_IPTV_PORP_TOOL zj_tool;
	
	private boolean needRepaint;
	
	public ServerIptv serverIptv;
	
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
//	public final static String GAME_PROP_CODE = 					"P10114";
	/**
	 * m16�����ߴ��롣���á�
	 */
	public final static String GAME_PROP_GoodsCode_M16 = 		"DJ11365";
	/**
	 * at15������
	 */
	public final static String GAME_PROP_GoodsCode_AT15 = 		"DJ11364";//�����0.5Ԫ�ı�����ak�ļ۸����ｻ����
//	public final static String GAME_PROP_GoodsCode_AT15 = 		"DJ11366";//�����һԪ�ĵ��ߡ�����ak���
	/**
	 * ak47������
	 */
	public final static String GAME_PROP_GoodsCode_AK47 = 		"DJ11366";
	/**
	 * m4������
	 */
	public final static String GAME_PROP_GoodsCode_M4A1S = 			"DJ11367";
	/**
	 * awm������
	 */
	public final static String GAME_PROP_GoodsCode_AWM = 			"DJ11363";
	/**
	 * ���ڵ�����
	 */
	public final static String GAME_PROP_GoodsCode_BARRETT = 			"DJ11368";
	/**
	 * �Զ����ӵ�������
	 */
	public final static String GAME_PROP_GoodsCode_AutoCB = 			"DJ11357";
	/**
	 * �Զ���׼������
	 */
	public final static String GAME_PROP_GoodsCode_AutoAim = 			"DJ11358";
	/**
	 * ���������
	 */
	public final static String GAME_PROP_GoodsCode_Relive = 			"DJ11359";
	/**
	 * ��ҩ������
	 */
	public final static String GAME_PROP_GoodsCode_Bullet = 			"DJ11360";
	/**
	 * ���������
	 */
	public final static String GAME_PROP_GoodsCode_Stealth = 			"DJ11361";
	/**
	 * �߱��ӵ�������
	 */
	public final static String GAME_PROP_GoodsCode_HighBoom = 			"DJ11362";
	
	/**
	 * ���浱ǰ�û������е������±�ֵ
	 * Ĭ��Ϊ2������m16
	 */
	private int gunIndex = 0;

	/**
	 * �ֱ��¼�Ƿ���и�ǹ���±�ֵ��ǹ���±�ֵ��Ӧ��
	 * 1Ϊӵ�У�0Ϊ��ӵ�С�
	 */
	public static int[] hasGun = {1, 0, 0, 0, 0, 0};
	
	/**
	 * �����û��Ƿ��Ѿ�����߱��ӵ���
	 * 0����
	 * 1���ǡ�
	 */
	public static int highboom = 0;
	
	/**
	 * �����û��Ƿ��Ѿ������Զ����ӵ���
	 * 0����
	 * 1���ǡ�
	 */
	public static int autocb = 0;
	
	/**
	 * �û���һ����Ļ�����1
	 */
	public static int firstTimes = 0;
	
	/**
	 * �������
	 * 0����
	 * 1����
	 */
	public static int stealth = 0;
	
	/**
	 * �Զ���׼����
	 * 0����
	 * 1����
	 */
	public static int autoAim = 0;

	/**
	 * �洢ÿ�ظ��ּ�����˵�����
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
	 * �ǲ������˸�����߰�
	 */
	public static int relive;
	
	/**
	 * ʣ��ĵ�����
	 */
	public static int restEnemy;
	
	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;

	/**
	 * �����ؿ��÷�
	 */
	public static int level_1_score;
	public static int level_2_score;
	public static int level_3_score;
	public static int level_4_score;
	public static int level_5_score;
	public static int level_6_score;

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
	 * ����ʣ��ʱ��
	 */
	public static int stealthRemain;

	/**
	 * �Զ���׼ʣ��ʱ��
	 */
	public static int autoAimRemain;
	
	/**
	 * ����������Ϸ֡�ʵ�Ĭ��ѭ��ʱ��
	 */
	public final static int DEFAULTCYCLEUSETIME = 100;
	
	public AudioPlaydalin audio; 
	
//	public static AudioPlay player;

 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		getUserId(midlet);
		
		zj_tool = new ZJ_IPTV_PORP_TOOL(midlet, this);
//		zj_tool.set_isSysOut(true);
//		zj_tool.set_isTest(true);
		zj_tool.init();
		zj_tool.doUserQuery(GAME_PROP_CODE);
		
		audio = new AudioPlaydalin();
		
		launch();
	}

	public void launch() {
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
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
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		
		System.gc();
	}

	protected void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
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
			iptvID = "333";
		}
		
		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if(url==null){
			url="http://122.224.212.79:8080/GYMITV/iptv/order/order.py";
		}
	}

	public final int getGunIndex() {
		return gunIndex;
	}

	public final void setGunIndex(int gunIndex) {
		this.gunIndex = gunIndex;
	}

	/**
	 * ����ɹ���Ĵ����߼�
	 */
	public void doAfterBuyGoods() {
		switch (goodsIndex) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			hasGun[goodsIndex] = 1;//�����ǰ�ǹ��������Ϊ���óɶ�Ӧ�±�ֵ
			gunIndex = goodsIndex;//ֱ�Ӱ�����ǹ֧����Ϊ��������
			new ServerIptv(this).doSendUserInfo();
			break;
		case 6://�߱��ӵ�����ɹ�
			highboom = 1;
			new ServerIptv(this).doSendUserInfo();//����ˣ�������������
			break;
		case 7://�����·�����ɹ�
			stealth = 1;
			stealthRemain += 20;
			break;
		case 8://�Զ���׼����ɹ�
			autoAim = 1;
			autoAimRemain += 30;
			break;
		case 9://�Զ����ӵ�����ɹ�
			autocb = 1;
			new ServerIptv(this).doSendUserInfo();//����ˣ��ϴ���������
			break;
		case 10://�������ϰ�ҩ
			relive = 1;
			break;
		case 11://�����ӵ�
			buyBullets = 1;
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
//		case 2:
//		case 3:
		case 0:{//�ɹ�
			new Timer().schedule(new TimerTask() {
				
				public void run() {
					doAfterBuyGoods();
				}
			}, 900);
			
			this.setView(new Dialog(this, 3));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//������ֵ
			this.setView(new Dialog(this, 10));
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

	
	public void ZJIPTV_Result(int index, String returnStr, Hashtable hashtable, Vector v_Rank) {
		switch(index){
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_USER_QUERY:{//�û���Ȩ
//			C.out_system("�û���Ȩ:"+"\n"+backstr);
//			System.out.println("message:"+hashtable.get("message"));
		}break;
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_PROPS_ORDER:{//�û���������
//			C.out_system("�û���Ȩ��������:"+"\n"+backstr);
			System.out.println("message:"+hashtable.get("message"));
		}break;
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_RANK_CROSSPROPERTYSAVEORUPDATE:{//�ϴ��������ݵ�������(�������ʱ)
//			System.out.println("�ϴ��������ݵ�������(�������ʱ):"+"\n"+returnStr);
			System.out.println("message:"+hashtable.get("message"));
		}break;
		case ZJ_IPTV_PORP_TOOL.state_ZJ_BSCS_RANK_CROSSPROPERTYQUERY:{//����������а�-----�������,��������
//			v_rank = v_Rank;
			System.out.println("����������а�-----�������,��������:"+"\n"+returnStr);
//			System.out.println("һ������:"+v_Rank.size());
//			zj_iptv_Parse.getParseXml(backstr);
//			System.out.println("accountStb:"+hashtable.get("accountStb"));
			System.out.println("message:"+hashtable.get("message"));
			System.out.println("���а����:"+v_Rank.size());
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
	}
	
}
