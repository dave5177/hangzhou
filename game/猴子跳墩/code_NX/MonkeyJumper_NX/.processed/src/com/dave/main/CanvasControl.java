package com.dave.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

import com.dalin.httpc.ServerIptv;
import com.dalin.nxiptv.prop.NX_IPTV_Listenner;
import com.dalin.nxiptv.prop.NX_IPTV_PORP_TOOL;
import com.dave.frame.CountFrame;
import com.dave.modeauto.AutoGame;
import com.dave.nxalert.NX_AlertListener;
import com.dave.tool.C;
import com.dave.ui.Activity;
import com.dave.ui.Alert;
import com.dave.ui.Help;
import com.dave.ui.Home;

public class CanvasControl extends Canvas implements Runnable,NX_IPTV_Listenner, NX_AlertListener {
	
//	public static String iptvID;
//	public static String iptvID="0571000210";
//	public static String iptvID="1571124907634";
	public static String iptvID;
//	public static String iptvID="157112490763";
	
	public static final String VERSION = "Version_NX_Demo_2.0.3";//版本信息
	
	public final com.dave.nxalert.Alert nx_alert;
	
	public CountFrame CF;
//	public AudioPlay audioPlay;
	
	public static Vector v_Rank;
	public static Vector v_RankAll;
	
	public MainMIDlet mainMIDlet;
	
	private long thread_StartTime;
	private long thread_UsedTime;
	
	public static int gameFrame = 80;
	
	public byte state;					//记录所在页面。
	public byte state_Back_For;
	public static final byte NULL = 0;
	public static final byte HOME_PAGE = 1;
	public static final byte GAME_PAGE = 2;
	public static final byte HELP_PAGE = 3;
	public static final byte ALERT_PAGE = 4;
	public static final byte ACTIVITY_PAGE = 5;
	
	public Home home_page;		//主页
	public AutoGame game_page;	//游戏页面
	public Help help_page;
	public Alert alert_page;
	public Activity activity_page;
	public NX_IPTV_PORP_TOOL nx_iptv_prop_tool;
	
	public ServerIptv si;
	
	public static float frameNow = 0;
	
	public static final String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";
//	public static final String COMPANYURL_PHP = "http://122.224.212.101/";//本公司放电信的服务器
//	public static final String COMPANYURL_PHP = "http://122.224.212.78:7878/";//泰山服务器
//	public static final String COMPANYURL_PHP = "http://192.168.1.197/";//本公司内部的服务器
//	public static final String COMPANYURL_PHP = "http://222.46.20.148:9888";//本公司内部外网服务器
	
//	public static final String COMPANYURL_PHP = "http://61.160.131.57:8083/www.iptvgame.com/";//江苏服务器
	public static final String COMPANYURL_PHP = "http://124.224.241.237:8080/www.iptvgame.com/";//江苏服务器
	
//	public static final String COMPANYURL_PHP 	= "http://192.168.1.197";//本地测试PHP服务器
//	public static final String COMPANYURL_PHP 	= "http://222.46.20.151:9002/index.php";//正式PHP服务器
	
	public static final String GAME_ADDRESS 	= "iptv.game.monkey";//游戏路径
	public final static String GAME_PROP_CODE = 					"P10168";
//	public final static String GAME_PROP_CODE = 					"P10114";
	public final static String GAME_PROP_TimeCode_life = 			"DJ21020";
	
	public static int score;
	public static int totalScore;
	public static int game_score;				//得分
	
	public static int rank;
	public static int rankall;
	
	private long keyPressedTime;
	
	public CanvasControl(MainMIDlet mainMIDlet) {
		this.mainMIDlet = mainMIDlet;
		nx_alert = new com.dave.nxalert.Alert(mainMIDlet, this);
		CanvasControl.iptvID = mainMIDlet.getAppProperty("userId");
		
		iptvID = getIptvID();
		
		CF = new CountFrame();
		home_page = new Home(this);
//		audioPlay = new AudioPlay();
		loadJS_IPTV_PORP_TOOL();
		
		state = CanvasControl.HOME_PAGE;
		
		si = new ServerIptv(this);
		si.doGetUserProfile();//得到自己的数据
		
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			thread_StartTime=System.currentTimeMillis();
			switch (state) {
			case GAME_PAGE:{
				game_page.logi();
				this.repaint();
				this.serviceRepaints();
			} break;
			case ALERT_PAGE:
				alert_page.logi();
//			case HOME_PAGE:
			case ACTIVITY_PAGE:
			case HELP_PAGE:{
				this.repaint();
				this.serviceRepaints();
			} break;
			}
			thread_UsedTime = System.currentTimeMillis() - thread_StartTime;
			CF.logi(thread_UsedTime);
			try {
				if(thread_UsedTime < gameFrame){//如果循环使用的时间小于当前状态下规定使用时间
					Thread.sleep(gameFrame - thread_UsedTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void paint(Graphics canvas) {
		switch(state) {
		case CanvasControl.HOME_PAGE :
			home_page.showMe(canvas);
			break;
		case CanvasControl.GAME_PAGE :
			game_page.showMe(canvas);
			break;
		case CanvasControl.HELP_PAGE :
			help_page.showMe(canvas);
			break;
		case CanvasControl.ALERT_PAGE :
			alert_page.showMe(canvas);
			break;
		case CanvasControl.ACTIVITY_PAGE :
			activity_page.showMe(canvas);
			break;
		}
		//this.drawGF(canvas);
	}
	
	/*private void drawGF(Graphics canvas){
		canvas.setClip(0, 0, 640, 530);
//		if(isShowGF==0)return;
		canvas.setColor(0xffffff);
		canvas.fillRect(0, 60, 100, 40);
		canvas.setColor(0xff0000);
		canvas.drawString("usedTime:" + thread_UsedTime, 0, 60, 0);
		canvas.drawString("GF:" + frameNow, 0, 80, 0);
	}*/
	
	protected void keyPressed(int keyCode) {
		
		if(System.currentTimeMillis() - keyPressedTime < 50) {
			keyPressedTime = System.currentTimeMillis();
			return;
		}
		keyPressedTime = System.currentTimeMillis();
		
		switch(state) {
		case CanvasControl.HOME_PAGE :
			home_page.keyPressed(keyCode);
			break;
		case CanvasControl.GAME_PAGE :
			game_page.keyPressed(keyCode);
			break;
		case CanvasControl.HELP_PAGE :
			help_page.keyPressed(keyCode);
			break;
		case CanvasControl.ALERT_PAGE :
			alert_page.keyPressed(keyCode);
			break;
		case CanvasControl.ACTIVITY_PAGE :
			activity_page.keyPressed(keyCode);
			break;
		}
	}

	protected void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
	}

	protected void keyRepeated(int keyCode) {
		super.keyRepeated(keyCode);
	}

	public int getState() {
		return state;
	}

	public void setState(byte state) {
		switch (state) {
		case GAME_PAGE:{
			if(game_page == null) {
				game_page = new AutoGame(this);
			}else {
				game_page.loadAllSources();
			}
			
			this.state = state;
			repaint();
			serviceRepaints();
			
			if(home_page != null) {
				home_page.removeAllThisClassSources();
				home_page = null;
			}
			if(help_page != null) {
				help_page.removeAllThisClassSources();
				help_page = null;
			}
			if(alert_page != null) {
				alert_page.removeSourse();
				alert_page = null;
			}
		}break;
		
		case HELP_PAGE:{
			if(help_page == null) {
				help_page = new Help(this);
			} else help_page.loadAllSources();
			
			this.state = state;
			repaint();
			serviceRepaints();
			
			if(home_page != null) {
				home_page.removeAllThisClassSources();
				home_page = null;
			}
		}break;
		
		case ALERT_PAGE:{
			if(alert_page == null) alert_page = new Alert(this);
			else alert_page.loadSourse();

			this.state = state;
			repaint();
			serviceRepaints();
			
			if(game_page != null) game_page.removeAllThisClassSources();
			
			if(home_page != null) {
				home_page.removeAllThisClassSources();
				home_page = null;
			}
		}break;
		
		case ACTIVITY_PAGE:{
			if(activity_page == null) activity_page = new Activity(this);
			else activity_page.loadAllSource();

			this.state = state;
			repaint();
			serviceRepaints();
			
			if(home_page != null) {
				home_page.removeAllThisClassSources();
			}
		}break;
		
		case HOME_PAGE:{
			if(home_page == null) {
				home_page = new Home(this);
			} else home_page.loadAllSources();

			this.state = state;
			repaint();
			serviceRepaints();
			
			if(game_page != null) {
				game_page.removeMonkeySources();
				game_page = null;
			}
			if(help_page != null) {
				help_page.removeAllThisClassSources();
				help_page = null;
			}
			if(alert_page != null) {
				alert_page.removeSourse();
				alert_page = null;
			}
		}break;
		}
	/*	this.state = state;
		repaint();
		serviceRepaints();*/
	}

	
	public void loadJS_IPTV_PORP_TOOL(){
		nx_iptv_prop_tool = new NX_IPTV_PORP_TOOL(mainMIDlet, this, GAME_PROP_CODE, "productIDa5000000000000000217898");
		nx_iptv_prop_tool.setShowTime(500);
//		js_iptv_prop_tool.setTest();
		nx_iptv_prop_tool.init();
		
	}
	
	public void JS_PROP_listener(int propstate) {
		switch (propstate) {
		case NX_IPTV_PORP_TOOL.QUERY_BACK_SUCCESS:{//成功
			if(alert_page.type == C.A_TYPE_RELIVE) game_page.monkey.life = 6;
			else game_page.monkey.life += 5;
			setState(CanvasControl.GAME_PAGE);
//			alert_page.type = C.A_TYPE_BUYSUCCESS;
		}break;
		case NX_IPTV_PORP_TOOL.QUERY_BACK_FAIL:
		case NX_IPTV_PORP_TOOL.QUERY_BACK_Cancel:
		case 2:
		case 3:
		default:
		{//订购失败
			if(alert_page.type == C.A_TYPE_RELIVE) game_page.back_state = 2;
			setState(CanvasControl.GAME_PAGE);
		}break;
		}
	}
	
	public static String getIptvID() {
		return iptvID == null ? "000111222" : iptvID;
	}

	public void handleReturn() {
		setState(HOME_PAGE);
	}
}
