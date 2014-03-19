package com.zhangniu.game;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dalin.nxiptv.prop.NX_IPTV_Listenner;
import com.dalin.nxiptv.prop.NX_IPTV_PORP_TOOL;
import com.dave.nxalert.NX_AlertListener;
import com.zhangniu.prop.Props;
import com.zhangniu.update.ServerIptv;

public class Screen extends Canvas implements Runnable, NX_IPTV_Listenner, NX_AlertListener{

	public static final String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";
	// public static final String COMPANYURL =
	// "http://122.224.212.101/";//����˾�ŵ��ŵķ�����
	// public static final String COMPANYURL = "http://122.224.212.78:7878/";//
	// ̩ɽ������
	public static final String COMPANYURL = "http://124.224.241.237:8080/www.iptvgame.com/";//���շ�����
	// public static final String COMPANYURL =
	// "http://192.168.1.197/";//����˾�ڲ��ķ�����
	// public static final String COMPANYURL =
	// "http://222.46.20.148:9888";//����˾�ڲ�����������

	public static String iptvID;
	// public static String iptvID="0571000210";
	// public static String iptvID="157112490765018";
	// public static String iptvID="157112490763";

	/**
	 * 1������ 0������
	 */
	// public static String isJumpToRet;
	// public static String isJumpToRet="1";

	/**
	 * ��ת��ַ
	 */
	public static String ret_url;
	// public static String ret_url="http://www.baidu.com";

	// �۷���
	// public Prop prop;

	// ��������
	public ServerIptv si;
	

	public static NX_IPTV_PORP_TOOL nx_tool;//������ֲ����
	
	public final com.dave.nxalert.Alert nx_alert;
	
	public static final String VERSION = "Version_NX_Demo_2.0.5";//�汾��Ϣ

	// MIDlet��
	public MainMIDlet mid;

	// ������
	// public AudioPlay ap;

	// �˵���
	public Menu menu;

	/**
	 * �����͹ؿ�
	 */
	public GameLevel gamelevel;

	/**
	 * ����ʱ�ؿ�
	 */
	public GameTime gametime;

	/**
	 * ��ս�͹ؿ�
	 */
	public GameChallenge gamechallenge;

	// ������
	public Help help;

	// ��ʾ����
	public Alert alert;

	// �ҽ𵰷���
	public SmashGoldEggs smash;

	// �����
	public Affiche affiche;

	// �����̳�
	public PropShop propshop;

	// ���а�
	public Rank rank;

	// ģʽѡ��
	public ModeSelect modeselect;

	/**
	 * ��Ϸ�еĸ���״̬
	 */
	public static byte status;
	public static byte beforeStatus;
	public static byte backScreen;

	public static boolean inGame;// ���Ƿ�����Ϸ��
	public final static byte S_LOGON = 9;
	public final static byte S_MENU = 10;
	public final static byte S_GAME_LEVEL = 11;
	public final static byte S_GAME_TIME = 12;
	public final static byte S_GAME_CHALLENGE = 13;
	public final static byte S_HELP = 2;
	public final static byte S_ALERT = 3;
	public final static byte S_SMASH = 4;
	public final static byte S_AFFICHE = 5;// ����
	public final static byte S_PROPSHOP = 6;// �����̳�
	public final static byte S_RANK = 7;// ���а�
	public final static byte S_NULL = 8;// ��
	public final static byte S_MODESELECT = 9;// ģʽѡ��

	private long thread_StartTime;
	private long thread_UsedTime;

	private Thread thread;

	public static int scrolBarStrWidth;

	public static boolean fireUp = true;
	public static boolean keyRelease = false;// ����жϷ�ֹ�еĺ��Ӳ�����keyRelease

	private Message message;
	public static Screen s;

	public Screen(MainMIDlet m) {
		mid = m;
		s = this;
		nx_alert = new com.dave.nxalert.Alert(m, this);
		
		loadJs_tool(m);
		
		Screen.iptvID = mid.getAppProperty("userId");
		if (Screen.iptvID == null) {
			Screen.iptvID = "90000231";
		}

		// Screen.isJumpToRet = mid.getAppProperty("isJumpToRet");
		// Screen.ret_url = mid.getAppProperty("RECTIONURL");

		// C.out("iptvID:" + Screen.iptvID);
		// C.out("�Ƿ���ת:" + Screen.isJumpToRet);
		// C.out("��ת��ַ:" + Screen.ret_url);

		// ������Ļ��ʾ��ʽΪȫ����ʾ
		setFullScreenMode(true);
		// ��ȡ��Ļ��/��
		C.screenwidth = getWidth();
		C.screenheight = getHeight();
		// setGameStatus(S_MENU);

		// ��ʾ��
		alert = new Alert(this);

		// ������
		// ap = new AudioPlay();

		// prop��ʼ��
		// prop = new Prop(this);

		// ������Ļ
		if (si == null)
			si = new ServerIptv(this);
//		si.dogetScroelBarStr();
		si.dogetUserInformation();
		
		setGameStatus(S_MENU);
		
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}

	}

	/**
	 * ������Ϸ״̬
	 */
	public void setGameStatus(byte statuskey) {
		switch (statuskey) {
		case S_MENU: {// ����menu����
			if (menu == null)
				menu = new Menu(this);
			menu.menuInit();
			status = statuskey;
		}
			break;
		case S_MODESELECT: {// ģʽѡ��
			if (modeselect == null)
				modeselect = new ModeSelect(this);
			modeselect.init();
			status = statuskey;
			repaint();
		}
			break;
		case S_HELP: {// ��������
			if (help == null)
				help = new Help(this);
			help.init();
			status = statuskey;
		}
			break;
		case S_AFFICHE: {// ������
			if (affiche == null)
				affiche = new Affiche(this);
			affiche.init();
			affiche.LoadSource();
			status = statuskey;
		}
			break;
		case S_PROPSHOP: {// �����̳�
			if (propshop == null)
				propshop = new PropShop(this);
			propshop.init();
			status = statuskey;
		}
			break;
		case S_RANK: {// ���а�
			if (rank == null)
				rank = new Rank(this);
			rank.init();
			status = statuskey;
		}
			break;
		case S_ALERT: {// ��ʾ����
			if (alert == null)
				alert = new Alert(this);
			alert.removeAlertClassSource();
			alert.loadSource();
			status = statuskey;
		}
			break;
		case S_GAME_LEVEL: {// �ؿ�ģʽ
			if (gamelevel == null)
				gamelevel = new GameLevel(this);
			gamelevel.coutDownStart = false;
			gamelevel.levelInit();
			status = statuskey;
			repaint();
			serviceRepaints();
			status = S_NULL;
			C.alertType = 31;
			setGameStatus(S_ALERT);
			repaint();
		}
			break;
		case S_GAME_TIME: {// ��ʱģʽ
			if (gametime == null)
				gametime = new GameTime(this);
			gametime.coutDownStart = false;
			gametime.levelInit();
			status = statuskey;
			repaint();
			serviceRepaints();
			status = S_NULL;
			C.alertType = 50;
			setGameStatus(S_ALERT);
			repaint();
		}
			break;
		case S_GAME_CHALLENGE: {// ��սģʽ����Ϸ��
			if (gamechallenge == null)
				gamechallenge = new GameChallenge(this);
			gamechallenge.coutDownStart = false;
			gamechallenge.challengeinit();
			status = statuskey;
			repaint();
			serviceRepaints();
			status = S_NULL;
			C.alertType = 71;
			setGameStatus(S_ALERT);
			repaint();
		}
			break;
		case S_SMASH: {// �ҽ𵰽���
			if (smash == null)
				smash = new SmashGoldEggs(this);
			smash.init();
			if (C.level == 4 && C.passed) {// ͨ����
				C.level = 0;
			} else {
				if (C.passed) {
					C.level++;
				} else {
					C.level = 0;
				}
			}
			status = statuskey;
		}
			break;
		}
	}

	protected void keyPressed(int keyCode) {
		if (message != null) {
			message.onKeyDown(keyCode);
			return;
		}
		if (keyCode == C.KEY_FIRE) {
			/**
			 * �������˵ĺ��ӣ��������Գ�����Ϸ�ص��˵�ֱ���ֽ�����Ϸ ��������һ���жϣ�һ��Ҫ�ͷ����м�����ܽ�����Ϸ
			 */
			if (keyRelease && !fireUp) {
				fireUp = false;
				return;
			}
		}
		switch (status) {
		case Screen.S_MENU: {// �˵����水���¼�
			menu.keyPressed(keyCode);
		}
			break;
		case Screen.S_GAME_LEVEL: { // ��Ϸ�����水���¼�
			gamelevel.keyPressed(keyCode);
		}
			break;
		case Screen.S_GAME_TIME: { // ��Ϸ�����水���¼�
			gametime.keyPressed(keyCode);
		}
			break;
		case Screen.S_GAME_CHALLENGE: { // ��Ϸ�����水���¼�
			gamechallenge.keyPressed(keyCode);
		}
			break;
		case Screen.S_HELP: { // �������水���¼�
			help.keyPressed(keyCode);
		}
			break;
		case Screen.S_ALERT: { // ��ʾ�򰴼��¼�
			alert.keyPressed(keyCode);
		}
			break;
		case Screen.S_SMASH: { // �ҽ𵰰�������
			smash.keyPressed(keyCode);
		}
			break;
		case Screen.S_AFFICHE: {// �����
			if (keyCode == C.KEY_0 || keyCode == C.KEY_BACK
					|| keyCode == C.KEY_BACK_ZX) {
				Screen.status = Screen.S_NULL;
				C.vector_PrizeName.removeAllElements();
				affiche.removeThisClassSource();
				affiche = null;
				System.gc();
				setGameStatus(Screen.S_MENU);
				return;
			}
			affiche.keyPressed(keyCode);
		}
			break;
		case Screen.S_PROPSHOP: {// �����̳�
			if (keyCode == C.KEY_0 || keyCode == C.KEY_BACK
					|| keyCode == C.KEY_BACK_ZX) {
				Screen.status = Screen.S_NULL;
				propshop.removeSource();
				propshop = null;
				System.gc();
				if (si == null)
					si = new ServerIptv(this);
				// si.sendUserInformation(4);
				// si.threadToStart();
				setGameStatus(Screen.S_MENU);
				return;
			}
			propshop.keyPressed(keyCode);
		}
			break;
		case Screen.S_RANK: {// ���а�
			if (keyCode == C.KEY_0 || keyCode == C.KEY_BACK
					|| keyCode == C.KEY_BACK_ZX) {// ����0����
				C.vector_Rank_Challenge.removeAllElements();
				C.vector_Rank_Level.removeAllElements();
				C.vector_Rank_Time.removeAllElements();
				C.vector_Rank_Total.removeAllElements();
				status = S_NULL;
				rank.removeThisClassSource();
				System.gc();
				setGameStatus(S_MENU);
			} else {
				rank.keyPressed(keyCode);
				repaint();
			}
		}
			break;
		case Screen.S_MODESELECT: {
			modeselect.keyPressed(keyCode);
		}
			break;
		}
		if (keyCode == C.KEY_FIRE) {
			fireUp = false;
		}
	}

	protected void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
		keyRelease = true;
		if (keyCode == C.KEY_FIRE) {
			fireUp = true;
		}
	}

	/**
	 * �ӷ��������� 0 �ӷ��������أ��õ��û���ص�һЩ��Ϣ
	 */
	public void backFromDateServer(int which, String back) {
		switch (which) {
		case 1: {// �ӷ�����������Ļ���Լ��û������Ϣ��
			C.scroll_bar_Str = back;
			si = null;
			System.gc();
			if (menu == null)
				menu = new Menu(this);
			scrolBarStrWidth = C.scroll_bar_Str.length()
					* C.LARGE_BOLD_STRINGWIDTH;
			repaint();
			serviceRepaints();
			if (thread == null) {
				thread = new Thread(this);
				thread.start();
			}
		}
			break;
		// case 2: {//һ�ؽ����󣬣����۴��سɹ�����û��������ϴ�����������
		// smash=null;
		// System.gc();
		// // C.userScrolHistory=C.userScrolCurrent;
		// Screen.status = Screen.S_NULL;
		// Screen.status=Screen.S_GAME_LEVEL;
		// repaint();
		// serviceRepaints();
		//
		// Screen.status=Screen.S_NULL;
		// C.passed = false;
		// C.alertType = 0;
		// gamelevel.coutDownStart = false;
		// setGameStatus(Screen.S_ALERT);
		// repaint();
		// serviceRepaints();
		// }break;
		case 2: {// �н������������,��ʾ��Ϸ�����
			si = null;
			System.gc();
			Screen.status = Screen.S_NULL;
			setGameStatus(S_AFFICHE);
			repaint();
		}
			break;
		case 3: {// ���������������������
			si = null;
			setGameStatus(S_RANK);// ���а�����
			repaint();
		}
			break;
		case 4:
		case 5:
		case 6: {
			repaint();
		}
			break;
		}
		C.receiveKeyPressed = true;
	}

	/**
	 * �ӵ��߷���������
	 */
	public void backFromPropServer(int successIS) {
		if (successIS == 1) {// ������߳ɹ�
			// if(C.alertType==12){//���Ӿ��ϴ�һ��
			// Screen.status=Screen.S_NULL;
			// C.alertType = 6;
			// setGameStatus(S_ALERT);
			// repaint();
			// }else{//��ʱ������й���
			if (C.alertType == 52 || C.alertType == 42 || C.alertType == 72) {// �������й���
				if (Screen.beforeStatus == Screen.S_GAME_TIME) {
					gametime.autoSamsh = true;
					gametime.loadAutoSamshImage();
				}
				if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {
					gamelevel.autoSamsh = true;
					gamelevel.loadAutoSamshImage();
				}
				if (Screen.beforeStatus == Screen.S_GAME_CHALLENGE) {
					gamechallenge.autoSamsh = true;
					gamechallenge.loadAutoSamshImage();
				}
				C.total_Score += 100;
			}
			if (C.alertType == 41 || C.alertType == 53 || C.alertType == 15
					|| C.alertType == 51) {// ������ʱ��,����������
				if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {
					C.nowCountDown = gamelevel.countDown[C.level];
				}
				if (Screen.beforeStatus == Screen.S_GAME_TIME) {
					C.nowCountDown = gametime.countDownTime;
				}
				C.total_Score += 100;
			}

			if (C.alertBeforeType == 20 && C.BuyHowGoldHAMMER == 0) {// �����
				C.alertBeforeType = -1;
				C.goldHammer_Count += 1;
				if (si == null)
					si = new ServerIptv(this);
				si.doSendUserInformation(0);
			}
			if (C.alertType == 15) {// ����������
				gamechallenge.live = 3;
			}
			if (si == null)
				si = new ServerIptv(this);
			si.doSendBuyCode(Props.str_BuyCode);

			if (message == null) {
				Props.prop_result = 0;
				Props prop = new Props(iptvID);
				prop = null;
				System.gc();

				Screen.status = Screen.S_NULL;
				alert.handleShopping();
//				C.alertType = 6;
//				setGameStatus(S_ALERT);
				repaint();
			}
			// }
		}
		// else if (successIS == 1) {// ��ITV��Ʒ�����Ѿ��ﵽ������ֵ
		// C.alertType = 7;
		// setGameStatus(Screen.S_ALERT);
		// repaint();
		// }
		else if (successIS == 2) {// �����ʻ�����,���ֵ
			// C.alertType = 8;
			// setGameStatus(Screen.S_ALERT);
			// repaint();
			message = new Message(Message.BUY_GOLD);
		} else if (successIS == 3) {// ����ʧ�ܣ�������
//			C.alertType = 9;
//			setGameStatus(Screen.S_ALERT);
			alert.handleShopping();
			repaint();
		}
	}

	public void run() {
		while (true) {
			thread_StartTime = System.currentTimeMillis();
			if (message != null) {
				if (!message.getpause()) {
					message = null;
					Props.prop_result = 0;
					if (Screen.beforeStatus == Screen.S_GAME_TIME) {// �ڼ�ʱģʽ��
						s.gametime.coutDownStart = true;
						C.passed = false;
						Screen.status = Screen.S_GAME_TIME;
					} else if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {// �ڹؿ�ģʽ�¹������
						s.gamelevel.coutDownStart = true;
						C.passed = false;
						Screen.status = Screen.S_GAME_LEVEL;
					} else if (Screen.beforeStatus == Screen.S_GAME_CHALLENGE) {// ����Ϸ�й����й�
						if (C.alertBeforeType == 15) {// ��Ϸ��û��������ȥ��������
							s.gamechallenge.coutDownStart = true;
							C.passed = false;
							Screen.status = Screen.S_GAME_CHALLENGE;
						} else if (C.alertBeforeType == 16) {
							Screen.status = Screen.S_NULL;
							s.setGameStatus(Screen.S_SMASH);
							s.repaint();
						} else if (C.alertBeforeType == 72) {
							alert.removeAlertClassSource();
							s.gamechallenge.loadAutoSamshImage();// �����Զ��й�ʱ����Ҫ��ͼƬ
							s.gamechallenge.coutDownStart = true;
							Screen.status = Screen.S_GAME_CHALLENGE;
						}
					} else if (Screen.beforeStatus == Screen.S_PROPSHOP) {
						Screen.status = Screen.S_PROPSHOP;
						s.repaint();
					} else if (Screen.beforeStatus == Screen.S_SMASH) {
						Screen.status = Screen.S_SMASH;
						s.repaint();
					}
					C.receiveKeyPressed = true;
				}
				repaint();
			}
			switch (status) {
			case S_GAME_LEVEL: {
				gamelevel.logi();
				repaint();
			}
				break;
			case S_GAME_TIME: {
				gametime.logi();
				repaint();
			}
				break;
			case S_GAME_CHALLENGE: {
				gamechallenge.logi();
				repaint();
			}
				break;
			case S_MENU: {
				repaint();
			}
				break;
			case S_SMASH: {
				repaint();
			}
				break;
			default: {
				System.gc();
			}
				break;
			}

			thread_UsedTime = System.currentTimeMillis() - thread_StartTime;
			try {
				if (thread_UsedTime < C.threadSleepTime[C.level]) {// ���ѭ��ʹ�õ�ʱ��С�ڵ�ǰ״̬�¹涨ʹ��ʱ��
					Thread.sleep(C.threadSleepTime[C.level] - thread_UsedTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	protected void paint(Graphics c) {
		if (message != null) {
			message.paint(c);
			return;
		}
		switch (status) {
		case S_MENU: {
			menu.showMe(c);
		}
			break;
		case S_HELP: {
			help.showMe(c);
		}
			break;
		case S_AFFICHE: {// �����
			affiche.showMe(c);
		}
			break;
		case S_PROPSHOP: {// �����̳�
			propshop.showMe(c);
		}
			break;
		case S_RANK: {// ���а�
			rank.showMe(c);
		}
			break;
		case S_ALERT: {// ��ʾ��
			alert.showMe(c);
		}
			break;
		case S_GAME_LEVEL: {// ��Ϸ��
			gamelevel.showMe(c);
		}
			break;
		case S_GAME_TIME: {// ��Ϸ��
			gametime.showMe(c);
		}
			break;
		case S_GAME_CHALLENGE: {// ��Ϸ��
			gamechallenge.showMe(c);
		}
			break;
		case S_SMASH: {// �ҽ𵰽���
			smash.showME(c);
		}
			break;
		case S_MODESELECT: {// ģʽѡ��
			modeselect.showMe(c);
		}
			break;
		}
		// c.setColor(0);
		// c.setClip(0, 0, 640, 50);
		// c.fillRect(0, 0, 640, 50);
		// c.setColor(-1);
		// c.drawString(ret_url.substring(0, ret_url.length() / 2), 0, 0, 0);
		// c.drawString(ret_url.substring(ret_url.length() / 2,
		// ret_url.length()),
		// 0, 25, 0);
	}

	private void loadJs_tool(MIDlet midlet) {
		nx_tool = new NX_IPTV_PORP_TOOL(midlet, this, Props.kindid, "productIDa5000000000000000217432");
		nx_tool.setShowTime(500);
		nx_tool.setSysOut(true);
		nx_tool.setTest();
		nx_tool.init();
	}

	public void JS_PROP_listener(int propstate) {
		switch (propstate) {
		case NX_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS:{//�ɹ�
			Props.prop_result = 1;
		}break;
		case NX_IPTV_PORP_TOOL.QUERY_BACK_FAIL:
		case NX_IPTV_PORP_TOOL.QUERY_BACK_Cancel:
		case 2:
		case 3:
		default:
		{//����ʧ��
			Props.prop_result = 3;
		}break;
		}
		Props.waitResult = true;
		s.backFromPropServer(Props.prop_result);
	}

	public void handleReturn() {
		s.setGameStatus(S_MENU);
	}
}
