package com.dave.ftxz.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.ftxz.net.ServerIptv;
import com.dave.ftxz.tool.C;
import com.dave.ftxz.view.Dialog;
import com.dave.ftxz.view.Home;
import com.dave.ftxz.view.NullView;
import com.dave.showable.Showable;
import com.zn.hs.prop.HSProp;
import com.zn.hs.prop.HSPropListenner;

/**
 * @author Dave
 * 
 */
public class CanvasControl extends Canvas implements Runnable,HSPropListenner {

	private MainMIDlet midlet;

	public static String iptvID;

	public final static String VERSION = "Version_HS_2.0.2";

	/**
	 * ��������
	 */
	public HSProp hs_tool;

	/**
	 * ���߶�����ַ
	 */
	public static String url;

//	/**
//	 * ��ϷͼƬ��ŵķ�������ַ
//	 */
//	public static String imageServerUrl = "";// ��jad��ȡ

	/**
	 * ��Ʒ��
	 */
	public final static String GAME_PROP_CODE = "P31017";

	/**
	 * ���ߵĲ���0�������룬1���۸�
	 */
	public final static String[][] A_GOODS_PARAM = { 
		    { "DJ32075", "5" },// ��С��
			{ "DJ32076", "5" },// ��С��
			{ "DJ32077", "5" },// ��С��
			{ "DJ32079", "5" },// �ȳ�
			{ "DJ32080", "5" },// ����
			{ "DJ32081", "5" },// ����
			{ "DJ32082", "5" },// ���
			{ "DJ32083", "5" },// �޵�
			{ "DJ32084", "5" },// ����ʯ
			{ "DJ32078", "5" } // ����
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
	 * �������� ���е�:0�������� 1�������� 2���ڱ������ 3����������� 4������������ 5�����سɹ��� 6������ʧ�ܡ�
	 */
//	public final PlayerHandler playerHandler;
	/**
	 * ��¼ÿ���߼�ѭ����ʼʱ��
	 */
	private long cycleStartTime;

	/**
	 * ��¼ÿ���߼�ѭ��ʹ�õ�ʱ��
	 */
	private long cycleUseTime;

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
	 * ��0~9���ǣ���С�ӡ���С�ӡ���С�ӡ��ȳ衢���������١���̡��޵С�����ʯ������
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
	public static final int[] goodsNumber = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;

		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		C.out("cavas��ȣ�" + C.WIDTH + "\ncavas�߶ȣ�" + C.HEIGTH);
		getServerInfo(midlet);

		hs_init(midlet);
		
		ServerIptv serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		appStartTime = System.currentTimeMillis();
//		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
//		serverIptv.sendClickTimes();
		serverIptv = null;
		
//		playerHandler = new PlayerHandler(5, 2);

		nullView = new NullView();

		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:" + pro);
		if (pro.substring(0, 1).equals("z")) {
			C.isZHONGXING = true;
		}
		launch();
	}

	private void hs_init(MIDlet midlet) {
		hs_tool = new HSProp(midlet, this);
		
		// ����ǲ��ԣ���
//		hs_tool.set_isTest(true);
		
		// �����Ҫ��ӡ���н������
//		hs_tool.set_isSysOut(true);
		// ���±�������
		hs_tool.setCpcode("204");// CPcode�ǹ̶���204
//		hs_tool.setActionID("400");// ������ϷID��������Ϸ����ѯ�����Ե�ʱ����400
		hs_tool.setActionID("658");// ������ϷID��������Ϸ����ѯ�����Ե�ʱ����400
	}

	public void launch() {
		setView(new Home(this));
		new Thread(this).start();
	}

	protected void paint(Graphics g) {
		view.show(g);
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
			if (cycleUseTime != 0)
				fps = 1000 / cycleUseTime;

			if (cycleUseTime < DEFAULTCYCLEUSETIME) {
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
//		if (!(view instanceof Game))
//			playerHandler.playByIndex(1);
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
	 * ��ȡiptvID���޷���ȡ����ֵ�� ��ȡ���߶�����ַ�� ��ȡͼƬ��������ַ��
	 * 
	 * @param midlet
	 */
	private void getServerInfo(MIDlet midlet) {
		
		iptvID = midlet.getAppProperty("UserID");
		
		if (iptvID == null) {
			iptvID = "7777221";
		}
		
		String temp_ServiceIP = midlet.getAppProperty("ServiceIP");
		
		if(temp_ServiceIP == null){
			HSProp.stbid = "057100011";
			HSProp.userid = "HS05712315";
			HSProp.userid = "057100011";
//			C.COMPANYURL_PHP = "http://10.48.179.118/app/";
			C.COMPANYURL_PHP = "http://192.168.1.197/";//����ʱ��197����ʽƽ̨��ʱ�������������ַ
		}else{
			C.COMPANYURL_PHP = "http://"+temp_ServiceIP+"/app/";
		}

		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if (url == null) {
			url = C.COMPANYURL_PHP;
		}

//		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
//		if (imageServerUrl == null) {
//			// imageServerUrl = "http://222.46.20.151:8055/ftxz";// ���ص�ַ
//			imageServerUrl = "http://122.224.212.78:7878/GameResource/ftxz";// �㽭���Ե�ַ
//		}
		
	}

	/**
	 * ����ɹ���Ĵ����߼�
	 */
	public void doBuySuccess() {
		if (goodsIndex < 9) {
			goodsNumber[goodsIndex]++;
		} else if (goodsIndex == 9) {// ����
			reliving = true;
		}
		saveParam();
		hs_tool.DoBuyPropSuccess(iptvID, GAME_PROP_CODE, A_GOODS_PARAM[goodsIndex][1]);
	}

	/**
	 * ��������
	 * 
	 * @param propstate
	 */
	public void backFromPropServer(int propstate) {
		System.out.println("propstate=" + propstate);
		propstate = 0;
		switch (propstate) {
		case 0: {// �ɹ�
			this.setView(new Dialog(this, 3, null));
		}
			break;
		case 3: {// ������ֵ
			this.setView(new Dialog(this, 10, null));
		}
			break;
		case 2: {// ����ʧ��
			this.setView(new Dialog(this, 4, null));
		}
			break;
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

//		xj_tool.saveParam(null, serverStr);
	}

	/**
	 * ����õ��Ĵ浵�ַ���
	 * 
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
	 * 
	 * @param ymd
	 *            �������ַ�������ʽ��2013-09-30 10:33:19
	 * @param week
	 *            �����ַ�������ʽ������һ
	 */
	public void handleTimeStr(String ymd, String week) {
		C.out("ymd--" + ymd);
		C.out("week--" + week);

	}

	public void onResult(String serviceType, String state, String limitfee,
			String code, String pwd) {// �������ѽ������ص�����
		if (state.equals("+OK")) {// �ɹ�
			setView(new Dialog(this, 3, null));
		} else {// ʧ��
			setView(new Dialog(this, 4, null));
		}
	}

}
