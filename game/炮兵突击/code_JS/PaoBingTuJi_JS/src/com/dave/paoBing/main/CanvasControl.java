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
	 * �Ƿ񰴼�ˢ��
	 */
	private boolean needRepaint;
	
	/**
	 * �浽�ڼ���
	 */
	public static int mission = 1;
	
	/**
	 * ���߶�����ַ
	 */
	public static String url;
	

	/**
	 * ��ϷͼƬ��ŵķ�������ַ
	 */
	public static String imageServerUrl = "";//��jad��ȡ

	
	/**
	 * ��Ʒ��
	 */
	public final static String GAME_PROP_CODE = 					"P31010";
	
	/**
	 * �����ָ������ߴ��롣
	 */
	public final static String GAME_PROP_GoodsCode_LIFE = 		"DJ32001";
	
	/**
	 * �����ӵ�
	 */
	public final static String GAME_PROP_GoodsCode_BULLET = 		"DJ32002";
	
	/**
	 * ˫������
	 */
	public final static String GAME_PROP_GoodsCode_DOUBLE = 		"DJ32003";
	
	/**
	 * ����
	 */
	public final static String GAME_PROP_GoodsCode_RELIVE = 		"DJ32004";
	
	/**
	 * ��ǰ����
	 */
	private Showable view;
	
	/**
	 * ��¼ǰһ����
	 */
	private Showable goBackView;
	
	/**
	 * ��������
	 * ���е�:0��������
	 * 1��������
	 * 2���ڱ������
	 * 3�����������
	 * 4������������
	 * 5�����سɹ���
	 * 6������ʧ�ܡ�
	 */
	public final PlayerHandler playerHandler;
	
	/**
	 * ��¼ÿ���߼�ѭ����ʼʱ��
	 */
	private long cycleStartTime;
	
	/**
	 * ��¼ÿ���߼�ѭ��ʹ�õ�ʱ��
	 */
	private long cycleUseTime;

	/**
	 * ��·
	 */
	private ServerIptv serverIptv;

	/**
	 * �ս���
	 */
	public final NullView nullView;

	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;

	/**
	 * ����
	 */
	public static int rank;
	
	/**
	 * ���а����ݣ���ɵĹؿ�
	 */
	public static int complete;
	
	/**
	 * ���а���Ϣ��ÿһ����һ����Ӧ��string����
	 */
	public static Vector rankInfo;
	
	/**
	 * ��¼����ĵ��ߵ��±�ֵ��
	 * 1��������
	 * 2���ӵ���
	 * 3��˫��������
	 * 4�����
	 */
	public static int goodsIndex;
	
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

	/**
	 * �洢�����������ַ����������û�����
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
	 * ��ȡiptvID���޷���ȡ����ֵ��
	 * ��ȡ���߶�����ַ��
	 * ��ȡͼƬ��������ַ��
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
	 * ����ɹ���Ĵ����߼�
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
	 * ��������
	 * @param propstate
	 */
	public void backFromPropServer(int propstate){
		
		System.out.println("propstate="+propstate);
propstate = 0;
		switch (propstate) {
		case 0:{//�ɹ�
			this.setView(new Dialog(this, 3, null));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//������ֵ
			this.setView(new Dialog(this, 10, null));
			this.repaint();
		}break;
		case 2:{//����ʧ��
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
		case JS_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS:{//�ɹ�
			doBuySuccess();
			setView(goBackView);
			repaint();
		}break;
		case JS_IPTV_PORP_TOOL.QUERY_BACK_FAIL:
		case JS_IPTV_PORP_TOOL.QUERY_BACK_Cancel:
		case 2:
		case 3:
		default:
		{//����ʧ��
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
