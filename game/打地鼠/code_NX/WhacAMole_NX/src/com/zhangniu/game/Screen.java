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
	// "http://122.224.212.101/";//本公司放电信的服务器
	// public static final String COMPANYURL = "http://122.224.212.78:7878/";//
	// 泰山服务器
	public static final String COMPANYURL = "http://124.224.241.237:8080/www.iptvgame.com/";//江苏服务器
	// public static final String COMPANYURL =
	// "http://192.168.1.197/";//本公司内部的服务器
	// public static final String COMPANYURL =
	// "http://222.46.20.148:9888";//本公司内部外网服务器

	public static String iptvID;
	// public static String iptvID="0571000210";
	// public static String iptvID="157112490765018";
	// public static String iptvID="157112490763";

	/**
	 * 1代表跳 0代表不跳
	 */
	// public static String isJumpToRet;
	// public static String isJumpToRet="1";

	/**
	 * 跳转地址
	 */
	public static String ret_url;
	// public static String ret_url="http://www.baidu.com";

	// 扣费类
	// public Prop prop;

	// 服务器类
	public ServerIptv si;
	

	public static NX_IPTV_PORP_TOOL nx_tool;//江苏移植工具
	
	public final com.dave.nxalert.Alert nx_alert;
	
	public static final String VERSION = "Version_NX_Demo_2.0.5";//版本信息

	// MIDlet类
	public MainMIDlet mid;

	// 声音类
	// public AudioPlay ap;

	// 菜单类
	public Menu menu;

	/**
	 * 闯关型关卡
	 */
	public GameLevel gamelevel;

	/**
	 * 倒计时关卡
	 */
	public GameTime gametime;

	/**
	 * 挑战型关卡
	 */
	public GameChallenge gamechallenge;

	// 帮助类
	public Help help;

	// 提示框类
	public Alert alert;

	// 砸金蛋方法
	public SmashGoldEggs smash;

	// 活动公告
	public Affiche affiche;

	// 道具商城
	public PropShop propshop;

	// 排行榜
	public Rank rank;

	// 模式选择
	public ModeSelect modeselect;

	/**
	 * 游戏中的各个状态
	 */
	public static byte status;
	public static byte beforeStatus;
	public static byte backScreen;

	public static boolean inGame;// 刚是否在游戏中
	public final static byte S_LOGON = 9;
	public final static byte S_MENU = 10;
	public final static byte S_GAME_LEVEL = 11;
	public final static byte S_GAME_TIME = 12;
	public final static byte S_GAME_CHALLENGE = 13;
	public final static byte S_HELP = 2;
	public final static byte S_ALERT = 3;
	public final static byte S_SMASH = 4;
	public final static byte S_AFFICHE = 5;// 公告
	public final static byte S_PROPSHOP = 6;// 道具商场
	public final static byte S_RANK = 7;// 排行榜
	public final static byte S_NULL = 8;// 空
	public final static byte S_MODESELECT = 9;// 模式选择

	private long thread_StartTime;
	private long thread_UsedTime;

	private Thread thread;

	public static int scrolBarStrWidth;

	public static boolean fireUp = true;
	public static boolean keyRelease = false;// 这个判断防止有的盒子不调用keyRelease

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
		// C.out("是否跳转:" + Screen.isJumpToRet);
		// C.out("跳转地址:" + Screen.ret_url);

		// 设置屏幕显示方式为全屏显示
		setFullScreenMode(true);
		// 获取屏幕宽/高
		C.screenwidth = getWidth();
		C.screenheight = getHeight();
		// setGameStatus(S_MENU);

		// 提示框
		alert = new Alert(this);

		// 声音类
		// ap = new AudioPlay();

		// prop初始化
		// prop = new Prop(this);

		// 下载字幕
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
	 * 设置游戏状态
	 */
	public void setGameStatus(byte statuskey) {
		switch (statuskey) {
		case S_MENU: {// 导入menu界面
			if (menu == null)
				menu = new Menu(this);
			menu.menuInit();
			status = statuskey;
		}
			break;
		case S_MODESELECT: {// 模式选择
			if (modeselect == null)
				modeselect = new ModeSelect(this);
			modeselect.init();
			status = statuskey;
			repaint();
		}
			break;
		case S_HELP: {// 帮助界面
			if (help == null)
				help = new Help(this);
			help.init();
			status = statuskey;
		}
			break;
		case S_AFFICHE: {// 公告栏
			if (affiche == null)
				affiche = new Affiche(this);
			affiche.init();
			affiche.LoadSource();
			status = statuskey;
		}
			break;
		case S_PROPSHOP: {// 道具商场
			if (propshop == null)
				propshop = new PropShop(this);
			propshop.init();
			status = statuskey;
		}
			break;
		case S_RANK: {// 排行榜
			if (rank == null)
				rank = new Rank(this);
			rank.init();
			status = statuskey;
		}
			break;
		case S_ALERT: {// 提示窗口
			if (alert == null)
				alert = new Alert(this);
			alert.removeAlertClassSource();
			alert.loadSource();
			status = statuskey;
		}
			break;
		case S_GAME_LEVEL: {// 关卡模式
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
		case S_GAME_TIME: {// 计时模式
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
		case S_GAME_CHALLENGE: {// 挑战模式，游戏中
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
		case S_SMASH: {// 砸金蛋界面
			if (smash == null)
				smash = new SmashGoldEggs(this);
			smash.init();
			if (C.level == 4 && C.passed) {// 通关了
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
			 * 安徽中兴的盒子，按键稍稍长按游戏回到菜单直接又进入游戏 解决：添加一个判断，一定要释放了中间键才能进入游戏
			 */
			if (keyRelease && !fireUp) {
				fireUp = false;
				return;
			}
		}
		switch (status) {
		case Screen.S_MENU: {// 菜单界面按键事件
			menu.keyPressed(keyCode);
		}
			break;
		case Screen.S_GAME_LEVEL: { // 游戏主界面按键事件
			gamelevel.keyPressed(keyCode);
		}
			break;
		case Screen.S_GAME_TIME: { // 游戏主界面按键事件
			gametime.keyPressed(keyCode);
		}
			break;
		case Screen.S_GAME_CHALLENGE: { // 游戏主界面按键事件
			gamechallenge.keyPressed(keyCode);
		}
			break;
		case Screen.S_HELP: { // 帮助界面按键事件
			help.keyPressed(keyCode);
		}
			break;
		case Screen.S_ALERT: { // 提示框按键事件
			alert.keyPressed(keyCode);
		}
			break;
		case Screen.S_SMASH: { // 砸金蛋按键整个
			smash.keyPressed(keyCode);
		}
			break;
		case Screen.S_AFFICHE: {// 活动公告
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
		case Screen.S_PROPSHOP: {// 道具商城
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
		case Screen.S_RANK: {// 排行榜
			if (keyCode == C.KEY_0 || keyCode == C.KEY_BACK
					|| keyCode == C.KEY_BACK_ZX) {// 按了0返回
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
	 * 从服务器返回 0 从服务器返回，得到用户相关的一些信息
	 */
	public void backFromDateServer(int which, String back) {
		switch (which) {
		case 1: {// 从服务器下载字幕，以及用户相关信息后
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
		// case 2: {//一关结束后，（无论闯关成功与否）用户把数据上传给服务器后
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
		case 2: {// 中奖名单下载完成,显示游戏活动公告
			si = null;
			System.gc();
			Screen.status = Screen.S_NULL;
			setGameStatus(S_AFFICHE);
			repaint();
		}
			break;
		case 3: {// 周排名和月排名下载完成
			si = null;
			setGameStatus(S_RANK);// 排行榜排名
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
	 * 从道具服务器返回
	 */
	public void backFromPropServer(int successIS) {
		if (successIS == 1) {// 购买道具成功
			// if(C.alertType==12){//买锤子就上传一次
			// Screen.status=Screen.S_NULL;
			// C.alertType = 6;
			// setGameStatus(S_ALERT);
			// repaint();
			// }else{//买时间或买托管器
			if (C.alertType == 52 || C.alertType == 42 || C.alertType == 72) {// 购买了托管器
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
					|| C.alertType == 51) {// 购买了时间,购买了生命
				if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {
					C.nowCountDown = gamelevel.countDown[C.level];
				}
				if (Screen.beforeStatus == Screen.S_GAME_TIME) {
					C.nowCountDown = gametime.countDownTime;
				}
				C.total_Score += 100;
			}

			if (C.alertBeforeType == 20 && C.BuyHowGoldHAMMER == 0) {// 购买金锤
				C.alertBeforeType = -1;
				C.goldHammer_Count += 1;
				if (si == null)
					si = new ServerIptv(this);
				si.doSendUserInformation(0);
			}
			if (C.alertType == 15) {// 购买了生命
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
		// else if (successIS == 1) {// 该ITV产品该月已经达到订购峰值
		// C.alertType = 7;
		// setGameStatus(Screen.S_ALERT);
		// repaint();
		// }
		else if (successIS == 2) {// 您的帐户余额不足,请充值
			// C.alertType = 8;
			// setGameStatus(Screen.S_ALERT);
			// repaint();
			message = new Message(Message.BUY_GOLD);
		} else if (successIS == 3) {// 购买失败，请重试
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
					if (Screen.beforeStatus == Screen.S_GAME_TIME) {// 在计时模式下
						s.gametime.coutDownStart = true;
						C.passed = false;
						Screen.status = Screen.S_GAME_TIME;
					} else if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {// 在关卡模式下购买道具
						s.gamelevel.coutDownStart = true;
						C.passed = false;
						Screen.status = Screen.S_GAME_LEVEL;
					} else if (Screen.beforeStatus == Screen.S_GAME_CHALLENGE) {// 在游戏中购买托管
						if (C.alertBeforeType == 15) {// 游戏中没有生命了去买生命了
							s.gamechallenge.coutDownStart = true;
							C.passed = false;
							Screen.status = Screen.S_GAME_CHALLENGE;
						} else if (C.alertBeforeType == 16) {
							Screen.status = Screen.S_NULL;
							s.setGameStatus(Screen.S_SMASH);
							s.repaint();
						} else if (C.alertBeforeType == 72) {
							alert.removeAlertClassSource();
							s.gamechallenge.loadAutoSamshImage();// 导入自动托管时所需要的图片
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
				if (thread_UsedTime < C.threadSleepTime[C.level]) {// 如果循环使用的时间小于当前状态下规定使用时间
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
		case S_AFFICHE: {// 活动公告
			affiche.showMe(c);
		}
			break;
		case S_PROPSHOP: {// 道具商场
			propshop.showMe(c);
		}
			break;
		case S_RANK: {// 排行榜
			rank.showMe(c);
		}
			break;
		case S_ALERT: {// 提示框
			alert.showMe(c);
		}
			break;
		case S_GAME_LEVEL: {// 游戏中
			gamelevel.showMe(c);
		}
			break;
		case S_GAME_TIME: {// 游戏中
			gametime.showMe(c);
		}
			break;
		case S_GAME_CHALLENGE: {// 游戏中
			gamechallenge.showMe(c);
		}
			break;
		case S_SMASH: {// 砸金蛋界面
			smash.showME(c);
		}
			break;
		case S_MODESELECT: {// 模式选择
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
		case NX_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS:{//成功
			Props.prop_result = 1;
		}break;
		case NX_IPTV_PORP_TOOL.QUERY_BACK_FAIL:
		case NX_IPTV_PORP_TOOL.QUERY_BACK_Cancel:
		case 2:
		case 3:
		default:
		{//订购失败
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
