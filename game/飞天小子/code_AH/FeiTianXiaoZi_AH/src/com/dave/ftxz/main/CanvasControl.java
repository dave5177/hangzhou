package com.dave.ftxz.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.anHui.AnHuiHandler;
import com.dave.anHui.AnHui_Tool;
import com.dave.ftxz.net.ServerIptv;
import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.PlayerHandler;
import com.dave.ftxz.view.Dialog;
import com.dave.ftxz.view.Game;
import com.dave.ftxz.view.Loading;
import com.dave.ftxz.view.NullView;
import com.dave.showable.Showable;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, AnHuiHandler{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "Version_XJ_1.0.6";
	
	/**
	 * ���չ���
	 */
	public AnHui_Tool anHui_Tool;
	
	/**
	 * �û�Ԫ����
	 */
	public static String userCoin;
	
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
	public final static String GAME_PROP_CODE = 					"P31017";
	
	/**
	 * ���ߵĲ���0�������룬1���۸�
	 */
	public final static String[][] A_GOODS_PARAM = {
		{"DJ32075", "1"},//��С��
		{"DJ32076", "1"},//��С��
		{"DJ32077", "1"},//��С��
		{"DJ32079", "1"},//�ȳ�
		{"DJ32080", "1"},//����
		{"DJ32081", "1"},//����
		{"DJ32082", "1"},//���
		{"DJ32083", "1"},//�޵�
		{"DJ32084", "1"},//����ʯ
		{"DJ32078", "1"}//����
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
	 * ����
	 */
	public boolean reliving;

	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;
	
	/**
	 * ���а���Ϣ��ÿһ����һ����Ӧ��string����
	 */
	public static Vector rankInfo_month;

	/**
	 * u�����а�
	 */
	public static Vector rankInfo_week;
	
	/**
	 *��0~8���ǣ���С�ӡ���С�ӡ���С�ӡ��ȳ衢���������١���̡��޵С�����ʯ
	 */
	public static int goodsIndex = -1;
	
	/**
	 * ����������Ϸ֡�ʵ�Ĭ��ѭ��ʱ��
	 */
	public final static int DEFAULTCYCLEUSETIME = 100;
	
	/**
	 * ���о��롣
	 */
	public static int distance = 0;
	
	/**
	 * �õ����ܽ��
	 */
	public static int coin_total = 0;
	
	/**
	 * ѡ���Ӣ��
	 */
	public static int type_hero = 0;
	
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

	/**
	 * �����ҵ���ߵ÷�
	 */
	public static int score_week;

	/**
	 * ��������
	 */
	public static int rank_week;
	
	/**
	 * �����ҵ���ߵ÷�
	 */
	public static int score_month;
	
	/**
	 * ��������
	 */
	public static int rank_month;

	/**
	 * ��������ֱ�ӽ���
	 */
	public static boolean willStart;

	/**
	 * ���������������ҷֱ��ǣ���С�ӡ���С�ӡ���С�ӡ��ȳ衢���������١���̡��޵С�����ʯ
	 */
	public static final int[] goodsNumber = {
		0, 0, 0, 0, 0, 0, 0, 0, 0
	};

 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
C.out("cavas��ȣ�" + C.WIDTH + "\ncavas�߶ȣ�" + C.HEIGTH);
		getServerInfo(midlet);
		
		anHui_Tool = new AnHui_Tool(midlet, iptvID, this, GAME_PROP_CODE, false);
		userCoin = anHui_Tool.getCoin();
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv.getSystemTime();
		serverIptv = null;
		
		playerHandler = new PlayerHandler(5, 2);
		
		nullView = new NullView();
		
		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:"+pro);
		if(pro.substring(0, 1).equals("z")){
			C.isZHONGXING = true;
		}
		launch();
	}

	public void launch() {
		setView(new Loading(this, 0));
//		setView(new Help(this, 0));
		new Thread(this).start();
	}

	protected void paint(Graphics g) {
		view.show(g);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * ���߼�
	 */
	public void run() {
		while(true) {
			cycleStartTime = System.currentTimeMillis();
			
			view.logic();
			
			repaint();
			serviceRepaints();
			
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
		if(!(view instanceof Game))
			playerHandler.playByIndex(1);
		view.keyPressed(keyCode);
		super.keyPressed(keyCode);
	}
	
	protected void keyReleased(int keyCode) {
		view.keyReleased(keyCode);
		super.keyReleased(keyCode);
	}
	
	protected void keyRepeated(int keyCode) {
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
			iptvID = "90000138";
		}
		
		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if(url==null){
			url = C.COMPANYURL_PHP;
		}
		

		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
		if(imageServerUrl==null){
//			imageServerUrl = "http://222.46.20.151:8055/ftxz";//���ص�ַ
			imageServerUrl = "http://61.191.44.224:8080/GameResource/ftxz";//���յ�ַ
		}
	}

	/**
	 * ����ɹ���Ĵ����߼�
	 */
	public void doBuySuccess() {
		if(goodsIndex < 9) {
			goodsNumber[goodsIndex] ++;
		}else if(goodsIndex == 9) {//����
			reliving = true;
		}
		saveParam();
		new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
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
		}break;
		case 3:{//������ֵ
			this.setView(new Dialog(this, 10, null));
		}break;
		case 2:{//����ʧ��
			this.setView(new Dialog(this, 4, null));
		}break;
		}
	}

	/**
	 * �浵
	 */
	public void saveParam() {
		StringBuffer sb_param = new StringBuffer();
		for (int i = 0; i < goodsNumber.length; i++) {
			sb_param.append(goodsNumber[i]);
			sb_param.append(",");
		}
		serverStr = sb_param.toString();
System.out.println(serverStr);
		new ServerIptv(this).doSendUserInfo(serverStr);
	}
	
	/**
	 * ����õ��Ĵ浵�ַ���
	 * @param param
	 */
	public void handleParam(String param) {
C.out("����浵--------" + param);
		String[] a_s_param = C.subString(param, ',', goodsNumber.length);
		for (int i = 0; i < goodsNumber.length; i++) {
			goodsNumber[i] = Integer.parseInt(a_s_param[i]);
		}
	}

	/**
	 * ����õ���ʱ���ַ���
	 * @param ymd �������ַ�������ʽ��2013-09-30 10:33:19
	 * @param week �����ַ�������ʽ������һ
	 */
	public void handleTimeStr(String ymd, String week) {
		C.out("ymd--" + ymd);
		C.out("week--" + week);
	}

	public void handleBuy(String resultCode) {
		if ("1212200".equals(resultCode)) { //�����ɹ�
			System.out.println("�����ɹ�");
			
			userCoin = anHui_Tool.getCoin();
			this.setView(new Dialog(this, 3, null));
		} else {//����ʧ��
			System.out.println("����ʧ��");
			this.setView(new Dialog(this, 4, null));
		}
	}

	public void handleRecharge(String arg0) {
		
	}

}
