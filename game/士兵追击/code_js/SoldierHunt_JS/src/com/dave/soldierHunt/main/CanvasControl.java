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
	 * �Ƿ񰴼�ˢ��
	 */
	private boolean needRepaint;
	
	/**
	 * �浽�ڼ���
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
	 * ���ߵ���������ʼֵ��Ϊ�㡣
	 */
	public int[] goodsCount = {
			0,//����ҩˮ������
			0,//ը��������
			0, //�޵е�����
			0};//����ʯ������

	/**
	 * ���ߵļ۸�
	 */
	public int[] goodsPrice = {
			1,//����
			1,//����ҩˮ�ļ۸�
			1,//ը���ļ۸�
			1, //�޵еļ۸�
			1,//����ʯ�ļ۸�
			1,//����ʿ��
			1,//��չ����
			1};//����
	
	/**
	 * ��¼ÿ���߼�ѭ����ʼʱ��
	 */
	private long cycleStartTime;
	
	/**
	 * ��¼ÿ���߼�ѭ��ʹ�õ�ʱ��
	 */
	private long cycleUseTime;

	/**
	 * ����һ��ʿ��
	 */
	public boolean addSoldier;

	/**
	 * ����
	 */
	public boolean relive;

	/**
	 * ��·
	 */
	private ServerIptv serverIptv;

	/**
	 * �ս���
	 */
	public NullView nullView;

	/**
	 * �Զ���Ѫ
	 */
	public boolean addBlood;

	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;

	/**
	 * ����
	 */
	public static int rank;
	
	/**
	 * û��һ�ػ�õĽ�ҵ�����
	 */
	public static int totalCoin;

	public static Vector rankInfo;
	
	/**
	 * ��¼����ĵ��ߵ��±�ֵ��
	 * 0�����
	 * 1������ҩˮ��
	 * 2��ը����
	 * 3���޵С�
	 * 4������ʯ��
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

	/**
	 * ������
	 */
	public static final String[] goodsCode = {
		"DJ11365",//����
		"DJ11365",//����ҩˮ
		"DJ11365",//ը��
		"DJ11365",//�޵�
		"DJ11365",//����ʯ
		"DJ11365",//����ʿ��
		"DJ11365",//��չ����
		"DJ11365"//����
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
	 * ��ȡiptvID���޷���ȡ����ֵ��
	 * ��ȡ���߶�����ַ��
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
			Loading.SERVER_URL = "http://61.160.131.57:8083/GameResource/sbzj";//�㽭
		}
		
	}

	/**
	 * ����ɹ���Ĵ����߼�
	 */
	public void doBuySuccess() {
		switch (goodsIndex) {
		case 0://�����
			relive = true;
			break;
		case 1://����1������ҩˮ��
			goodsCount[0] ++;
			if(Game.weak) {
				addBlood = true;
			}
			break;
		case 2://����2��ը����
			goodsCount[1] += 2;
			break;
		case 3://����2���޵С�
			goodsCount[2] += 2;
			break;
		case 4://����2������ʯ��
			goodsCount[3] += 2;
			break;
		case 5://������һ��ʿ����
			addSoldier = true;
			break;
		case 6://��չ����
			GasStation.soldierCount ++;
			GasStation.upteamSuccess = true;
			
			break;
		case 7://����
			Hero.level ++;
			GasStation.uplevelSuccess = true;
			
			new ServerIptv(this).doSendUserInfo();
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
//			this.setView(nullView);
//			goBackView.removeResource();
			this.setView(new Dialog(this, 3));
			this.repaint();
			this.serviceRepaints();
		}break;
		case 3:{//������ֵ
			this.setView(nullView);
//			goBackView.removeResource();
			this.setView(new Dialog(this, 5));
			this.repaint();
		}break;
		case 2:{//����ʧ��
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
			setView(goBackView);
			repaint();
		}break;
		}
	}
	

}
