package com.dave.paoBing.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.paoBing.model.Hero;
import com.dave.paoBing.net.ServerIptv;
import com.dave.paoBing.tool.C;
import com.dave.paoBing.view.Dialog;
import com.dave.paoBing.view.Game;
import com.dave.paoBing.view.Home;
import com.dave.paoBing.view.NullView;
import com.dave.showable.Showable;
import com.zn.hs.prop.HSProp;
import com.zn.hs.prop.HSPropListenner;
//import com.dave.paoBing.view.Loading;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, HSPropListenner{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	/**
	 * ��������
	 */
	public HSProp hs_tool;
	
	public final static String VERSION = "Version_HS_2.0.5";
	
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
	 * ��Ʒ��
	 */
	public final static String GAME_PROP_CODE = 					"P31010";
	
	/**
	 * �����ָ������ߴ��롣
	 */
	public final static String GAME_PROP_GoodsCode_LIFE = 			"DJ32001";
	
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
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
		getServerInfo(midlet);
		
		hs_init(midlet);
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		appStartTime = System.currentTimeMillis();
//		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
//		serverIptv.sendClickTimes();
		serverIptv = null;
		
//		playerHandler = new PlayerHandler(7, true);
		
		nullView = new NullView();
		
		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:"+pro);
		if(pro.substring(0, 1).equals("z")){
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
		hs_tool.setActionID("657");// ������ϷID��������Ϸ����ѯ�����Ե�ʱ����400
	}
	
	public void launch() {
		setView(new Home(this));
//		setView(new Loading(this, 2));
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
//		if(!(view instanceof Game)) {
//			playerHandler.playByIndex(1);
//		}
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
			iptvID = "66633300103";
		}
		
		String temp_ServiceIP = midlet.getAppProperty("ServiceIP");
		if(temp_ServiceIP == null){
			HSProp.stbid = "057100011";
			HSProp.userid = "HS05712315";
			HSProp.userid = "057100011";
			C.COMPANYURL_PHP = "http://10.48.179.118/app/";
//			C.COMPANYURL_PHP = "http://192.168.1.197/";//����ʱ��197����ʽƽ̨��ʱ�������������ַ
		}else{
			C.COMPANYURL_PHP = "http://"+temp_ServiceIP+"/app/";
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
		
		hs_tool.DoBuyPropSuccess(iptvID, GAME_PROP_CODE, Dialog.GOODS_PRICE[goodsIndex - 1]);
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

	public void onResult(String serviceType, String state, String limitfee,String code, String pwd){
		if(state.equals("+OK")){//�ɹ�
			this.setView(new Dialog(this, 3, null));
		}else{//ʧ��
			this.setView(new Dialog(this, 4, null));
		}
	}
	

}
