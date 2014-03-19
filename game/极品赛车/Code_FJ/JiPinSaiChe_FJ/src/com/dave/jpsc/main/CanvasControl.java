package com.dave.jpsc.main;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import com.dalin.fjiptv.prop.FJ_IPTV_Listenner;
import com.dalin.fjiptv.prop.FJ_IPTV_PORP_TOOL;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.view.Game;
import com.dave.jpsc.view.Message;
import com.dave.jpsc.view.NullView;
import com.dave.showable.Showable;

/**
 * @author Dave
 * 
 */
public class CanvasControl extends Canvas implements Runnable, FJ_IPTV_Listenner {

	/**
	 * ����
	 */
	public static final boolean DEBUG = false;

	private MainMIDlet midlet;

	public static String iptvID;

	public final static String VERSION = "Version_FJ_1.0.4";

	/**
	 * �㽭����
	 */
	public FJ_IPTV_PORP_TOOL fj_tool;

	/**
	 * ���߶�����ַ
	 */
	public static String url;

	/**
	 * ��ϷͼƬ��ŵķ�������ַ
	 */
	public static String imageServerUrl = "";// ��jad��ȡ

	/**
	 * ��Ʒ��
	 */
	public final static String GAME_PROP_CODE = "P31021";
	// public final static String GAME_PROP_CODE = "P31017";

	/**
	 * ���ߵĲ���0�������룬1���۸�
	 */
	public final static String[][] A_GOODS_PARAM = { { "DJ32109", "1" },// 0
																		// ��ս����
			{ "DJ32110", "1" },// 1 ����ԽҰ
			{ "DJ32111", "1" },// 2 ѩ���������ܳ�
			{ "DJ32112", "1" },// 3 �����������
			{ "DJ32113", "1" },// 4 ��ɣ��������
			{ "DJ32114", "1" },// 5 ��ŵ����ʽ
			{ "DJ32115", "1" },// 6 ��ʱ��β��
			{ "DJ32116", "1" },// 7 ����sl
			{ "DJ32117", "1" },// 8 ���Դ�����ʽ
			{ "DJ32119", "1" },// 9 �ų�
			{ "DJ32125", "1" },// 10 ����ʱ��
			{ "DJ32120", "1" },// 11 ����ģʽ
			{ "DJ32121", "1" },// 12 ����ȼ��
			{ "DJ32122", "1" },// 13���ٹ���
			{ "DJ32123", "1" },// 14 ��ä����
			{ "DJ32124", "1" },// 15 һ���޸�
			{ "DJ32118", "1" },// 16 ����
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
	 * �������� ���е�:0���������� 1�������� 2��ɲ����
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
	 * ��·
	 */
	private ServerIptv serverIptv;

	/**
	 * �ս���
	 */
	public final NullView nullView;

	/**
	 * ��ǰ��ѡ����߹ؿ�
	 */
	public static int missionPassed = 1;

	/**
	 * ��ǰ�Ĺؿ�
	 */
	public static int mission = 1;

	/**
	 * �������ԣ������ҷֱ��ǣ��ٶȣ����ٶȣ�ת�䣬ȼ�ϣ��ȼ���0��δӵ�У�
	 */
	public static int[][] carProperty = { { 35, 10, 60, 65, 1 },// �ܳ�ϵͳ����
			{ 55, 25, 90, 80, 0 },// ԽҰ��
			{ 160, 30, 155, 110, 0 },// �����ܳ�
			{ 90, 30, 50, 130, 0 },// ��������
			{ 200, 10, 100, 90, 0 },// ��������
			{ 90, 15, 160, 120, 0 },// ����ʽ����
			{ 65, 10, 120, 130, 0 },// β������
			{ 100, 15, 50, 100, 0 },// sl����
			{ 140, 20, 220, 110, 0 },// ����ʽ����
	};

	/**
	 * ���������������ֵ
	 */
	public final static int[][] carMaxPower = { { 250, 120, 220, 160 },
			{ 350, 130, 220, 160 }, { 300, 150, 220, 160 },
			{ 280, 180, 220, 160 }, { 480, 120, 220, 160 },
			{ 450, 130, 220, 160 }, { 500, 110, 220, 160 },
			{ 400, 140, 220, 160 }, { 550, 100, 220, 160 }, };

	/**
	 * �ؿ��������� �ֱ��ǣ�Ȧ�������������������������Ρ������޶�ʱ��(s)�������ľ���ֵ�������¼ʱ��(s)
	 */
	public final static int[][] MISSIONPROPERTY = { { 1, 2, 1, 220, 30, 120 },
			{ 1, 2, 1, 280, 30, 110 }, { 1, 2, 1, 250, 30, 100 },
			{ 2, 2, 1, 360, 40, 180 }, { 1, 2, 1, 180, 40, 100 },
			{ 2, 2, 1, 360, 40, 180 }, { 2, 3, 1, 320, 60, 180 },
			{ 2, 3, 1, 320, 60, 180 }, { 2, 3, 1, 320, 60, 170 },
			{ 2, 4, 1, 300, 70, 170 }, { 2, 4, 1, 300, 70, 150 },
			{ 2, 4, 1, 300, 70, 150 }, { 3, 5, 1, 450, 80, 210 },
			{ 3, 5, 1, 450, 80, 210 }, { 3, 5, 1, 450, 80, 210 },
			{ 3, 5, 1, 450, 80, 210 }, { 3, 5, 1, 300, 120, 150 }, // ����һ
	};

	/**
	 * ��Ϸ�е�����
	 */
	public static int rankInGame;

	/**
	 * �ؿ�ʱ��
	 */
	public static String gameTimeStr;

	/**
	 * ʹ�õĳ�(���ҵĳ�������±�ֵ�������еĳ�����±�ֵȡme.cars[usingCar][0])
	 */
	public static int usingCar;

	/**
	 * ��ɶ��Ǽ�
	 */
	public static boolean[] tarAchieve = { false, false, false };

	/**
	 * ��
	 */
	public Player me;

	/**
	 * �ӷ�������ȡ���ǳ�
	 */
	public static String nickFromSer = "ɵ����";

	/**
	 * ����
	 */
	public boolean reliving;

	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;

	/**
	 * ��ʤ��
	 */
	public static int winsTotal;

	/**
	 * �ܱ�������
	 */
	public static int gamesTotal;

	/**
	 * �ֱ��ǣ�ս��������������������ս����
	 */
	public static int[] rankMe = { 20, 20, 20 };

	/**
	 * ��ս����洢
	 */
	public static Vector duelInfo;

	/**
	 * ���а���Ϣ��ʤ�����С�
	 */
	public static Vector rank_win;

	/**
	 * �ȼ�����
	 */
	public static Vector rank_level;

	/**
	 * ��ս����
	 */
	public static Vector rank_duel;

	/**
	 * �����±�ֵ��0����ս������1~8����������9���ų���ߡ�10������ʱ�䡣11������ģʽ��12������ȼ�ϡ�13�����ٹ�����14����ä������15��
	 * һ���޸���16����������
	 */
	public static int goodsIndex = -1;

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
	 * ����������Ĳ���
	 */
	public Vector paramsUpload;

	/**
	 * �������
	 */
	public static int playDay;

	/**
	 * �洢�����������ַ����������û�����
	 */
	public static String serverStr;

	/**
	 * ���سɹ���0:�ɹ���1��ʧ��
	 */
	public static int passSuc = 1;

	/**
	 * ����Ϣ
	 */
	public static boolean haveMsg;

	/**
	 * �ϴδ浵����
	 */
	public static int lastPlayDay;

	/**
	 * �Ѿ����������ڴ浵ˢ��
	 */
	public static boolean handledDay;

	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;

//		playerHandler = new PlayerHandler(3, 2);

		paramsUpload = new Vector();

		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		C.out("cavas��ȣ�" + C.WIDTH + "\ncavas�߶ȣ�" + C.HEIGTH);
		getServerInfo(midlet);
		
		loadFj_tool(midlet);

		serverIptv = new ServerIptv(this);
		serverIptv.getSystemTime();
		serverIptv.doGetUserProfile();
		serverIptv.getMsg();
		serverIptv.getDuelInfo();
		serverIptv.doGetRank(3, 0, "asc");
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv = null;

		nullView = new NullView();

		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:" + pro);
		if (pro.substring(0, 1).equals("z")) {
			C.isZHONGXING = true;
		}
		launch();
	}
	
	private void loadFj_tool(MIDlet midlet) {
		fj_tool = new  FJ_IPTV_PORP_TOOL(midlet, this, GAME_PROP_CODE);
		fj_tool.setSysOut(true);
//		fj_tool.setTestAdress("http://61.160.131.57:8083/www.iptvtest.com/");
		fj_tool.setShowTime(500);
		fj_tool.setTest();
		fj_tool.init();
		
	}

	public void launch() {
		// setView(new Home(this));
		// setView(new Loading(this, 0));
		// setView(new Complete(this));
		setView(nullView);
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
//		if (this.view instanceof Game)
//			playerHandler.stopByIndex(0);

//		if ((view instanceof Game) && )
//			playerHandler.playByIndex(0);

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
	 * ��ȡiptvID���޷���ȡ����ֵ�� ��ȡ���߶�����ַ�� ��ȡͼƬ��������ַ��
	 * 
	 * @param midlet
	 */
	private void getServerInfo(MIDlet midlet) {
		iptvID = midlet.getAppProperty("userId");
		if (iptvID == null) {
			iptvID = "919100";
		}

		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if (url == null) {
			url = C.COMPANYURL_PHP;
		}

		imageServerUrl = midlet.getAppProperty("ImageServerUrl_FJ");
		if (imageServerUrl == null) {
			// imageServerUrl = "http://222.46.20.151:8055/jpsc";// ���ص�ַ
			imageServerUrl = "http://110.84.142.23:8080/GameResource/jpsc";// ������ʽ��ַ
		}
	}

	/**
	 * ����ɹ���Ĵ����߼�
	 */
	public void doBuySuccess() {
		goBackView.handleGoods(goodsIndex);

		saveParam();
		new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
	}

	/**
	 * ��������
	 * 
	 * @param propstate
	 */
	public void backFromPropServer(int propstate) {
		// System.out.println("propstate=" + propstate);
		// propstate = 0;
		// switch (propstate) {
		// case 0: {// �ɹ�
		// this.setView(new Dialog(this, 3, null));
		// }
		// break;
		// case 3: {// ������ֵ
		// this.setView(new Dialog(this, 10, null));
		// }
		// break;
		// case 2: {// ����ʧ��
		// this.setView(new Dialog(this, 4, null));
		// }
		// break;
		// }
	}

	/**
	 * �浵
	 */
	public void saveParam() {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(playDay);// �浵���ڣ�����ˢ����ս������
		sb_param.append(",");
		sb_param.append(CanvasControl.missionPassed);// ��ͨ���Ĺؿ�
		// sb_param.append(16);// ��ͨ���Ĺؿ�
		sb_param.append(",");
		sb_param.append(C.enUseName_base64(me.nickName));// �ǳ�
		sb_param.append(",");
		sb_param.append(me.level);// �ȼ�
		sb_param.append(",");
		sb_param.append(me.expeirence);// ����
		sb_param.append(",");
		sb_param.append(me.strength);// ʵ��
		sb_param.append(",");
		sb_param.append(me.duelTimes);// ʣ����ս����
		sb_param.append(",");
		sb_param.append(me.mainCar);// ��ս����
		sb_param.append(",");

		for (int i = 0; i < me.cars.length; i++) {// ���еĳ�����Ϣ
			for (int j = 0; j < me.cars[i].length; j++) {
				sb_param.append(me.cars[i][j]);
				sb_param.append(",");
			}
		}
		serverStr = sb_param.toString();
		System.out.println(serverStr);
		new ServerIptv(this).doSendUserInfo(iptvID, serverStr);
	}

	/**
	 * �������
	 */
	public void saveScore() {
		if (me != null) {
			new ServerIptv(this).doSendScore(iptvID,
					C.enUseName_base64(me.nickName), winsTotal, gamesTotal, 3);
			me.saveScore();
		}
	}

	/**
	 * ����õ��Ĵ浵�ַ���
	 * 
	 * @param param
	 */
	public void handleParam(String param) {
		C.out("����浵--------" + param);
		String[] a_s_param = C.subString(param, ',');
		CanvasControl.lastPlayDay = Integer.parseInt(a_s_param[0]);
		CanvasControl.missionPassed = Integer.parseInt(a_s_param[1]);
		int car_num = (a_s_param.length - 8) / 5;
		int[][] cars = new int[car_num][5];
		for (int i = 0; i < car_num; i++) {
			for (int j = 0; j < 5; j++) {
				cars[i][j] = Integer.parseInt(a_s_param[8 + 5 * i + j]);
			}
			carProperty[cars[i][0]][4] = cars[i][4] + 1;
		}
		me = new Player(C.deUserName_base64(a_s_param[2]),
				Integer.parseInt(a_s_param[3]), Integer.parseInt(a_s_param[4]),
				Integer.parseInt(a_s_param[5]), Integer.parseInt(a_s_param[6]),
				Integer.parseInt(a_s_param[7]), cars, iptvID, this);

		usingCar = me.mainCar;
	}

	/**
	 * ��һ���浵��������һ�����
	 * 
	 * @param param
	 * @return
	 */
	public Player createPlayerByParam(String[] params) {
		String[] a_s_param = C.subString(params[0], ',');
		int car_num = (a_s_param.length - 8) / 5;
		int[][] cars = new int[car_num][5];
		for (int i = 0; i < car_num; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.println("������ң���" + (8 + 5 * i + j) + "������----" + a_s_param[8 + 5 * i + j]);
				cars[i][j] = Integer.parseInt(a_s_param[8 + 5 * i + j]);
			}
		}
		Player player = new Player(Integer.parseInt(a_s_param[1]),
				C.deUserName_base64(a_s_param[2]),
				Integer.parseInt(a_s_param[3]), Integer.parseInt(a_s_param[4]),
				Integer.parseInt(a_s_param[5]), Integer.parseInt(a_s_param[6]),
				Integer.parseInt(a_s_param[7]), cars, params[1], this);

		return player;
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

		String day = ymd.substring(0, 10);
		String[] date = C.subString(day, '-');
		playDay = Integer.parseInt(date[2]);
		System.out.println("playDay---" + playDay);
	}

	/**
	 * ���ù��߰��������
	 */
	public void buyGoods(int goodsIndex) {
		System.out.println("����ĵ����±꣺" + goodsIndex);
		CanvasControl.goodsIndex = goodsIndex;
		setGoBackView(view);
		setView(nullView);
		goBackView.removeResource();
		try {
//			fj_tool.do_BuyProp(A_GOODS_PARAM[goodsIndex][0]);
			fj_tool.do_BuyProp_New(
					Image.createImage("/goods/goods_name_" + goodsIndex
							+ ".png"),
							Image.createImage("/goods/goods_info_" + goodsIndex
									+ ".png"), A_GOODS_PARAM[goodsIndex][0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������Ϣ�ַ���
	 * 
	 * @param msg
	 *            �õ�����Ϣ�ַ���
	 */
	public void analyzeMsg(String msg) {
		C.out("��Ϣ����--------" + msg);
		String[] a_s_msgs = C.subString(msg, ';');
		Message.numbers = a_s_msgs.length;
		Message.duelResult = new int[Message.numbers];
		Message.duelPlayer = new Player[Message.numbers];
		for (int i = 0; i < Message.numbers; i++) {
			String[] msg_data = C.subString(a_s_msgs[i], ',');
			Message.duelResult[i] = Integer.parseInt(msg_data[0]);

			int car_num = (msg_data.length - 9) / 5;
			int[][] cars = new int[car_num][5];
			for (int m = 0; m < car_num; m++) {
				for (int n = 0; n < 5; n++) {
					cars[m][n] = Integer.parseInt(msg_data[9 + 5 * m + n]);
				}
			}
			Message.duelPlayer[i] = new Player(Integer.parseInt(msg_data[2]),
					C.deUserName_base64(msg_data[3]),
					Integer.parseInt(msg_data[4]),
					Integer.parseInt(msg_data[5]),
					Integer.parseInt(msg_data[6]),
					Integer.parseInt(msg_data[7]),
					Integer.parseInt(msg_data[8]), cars, msg_data[1], this);
		}
	}

	/**
	 * ����ҵ���Ϣ�б�
	 */
	public void emptyMsg() {
		new ServerIptv(this).updateMsg(iptvID, "null");
	}

	public void FJ_PROP_listener(int propstate) {
		switch (propstate) {
		case FJ_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS_BUYProp: {// �ɹ�
			if (!Game.timeOut && !Game.fuelOut)
				setView(goBackView);
			doBuySuccess();
		}
			break;
		case FJ_IPTV_PORP_TOOL.QUERY_BACK_FAIL_BUYProp:
		case FJ_IPTV_PORP_TOOL.QUERY_BACK_Cancel_BUYProp:
		case 2:
		case 3:
		default: {// ����ʧ��
//			if(goBackView instanceof Game)
//				playerHandler.playByIndex(0);
			setView(goBackView);
			repaint();
		}
			break;
		}
	}

}
