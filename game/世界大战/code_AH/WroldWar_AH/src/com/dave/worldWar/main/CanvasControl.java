package com.dave.worldWar.main;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.dave.anHui.AnHuiHandler;
import com.dave.anHui.AnHui_Tool;
import com.dave.showable.Showable;
import com.dave.worldWar.net.ServerIptv;
import com.dave.worldWar.tool.C;
import com.dave.worldWar.tool.PlayerHandler;
import com.dave.worldWar.view.Dialog;
import com.dave.worldWar.view.Game;
import com.dave.worldWar.view.Loading;
import com.dave.worldWar.view.NullView;
//import com.dave.paoBing.view.Loading;

/**
 * @author Dave
 *
 */
public class CanvasControl extends Canvas implements Runnable, AnHuiHandler{
	
	private MainMIDlet midlet;
	
	public static String iptvID;
	
	public final static String VERSION = "Version_AH_1.0.3";
	
	/**
	 * ������ֲ����
	 */
	public  AnHui_Tool anHui_Tool;
	
	/**
	 * �浽�ڼ���
	 */
	public static int mission = 1;
	
	/**
	 * ����ʿ���ĵȼ�,0����δ������
	 * �����ҷֱ���������ڱ�����������ѻ�����̹�˱�����ը��
	 */
	public static int[] soldiers_level = {
		1, 0, 0, 0, 0, 1
	};
	
	/**
	 * ʿ����������ֵ
	 * �����ҷֱ���� Ѫ�����������������Χ�������ٶȡ��ƶ��ٶȡ������ʡ�û������
	 */
	public static int[][] soldiers_prpty = {
		{50, 50, 450, 10, 6, 30, 5},//����
		{60, 80, 350, 4, 7, 50, 3},//�ڱ�
		{80, 80, 400, 3, 10, 60, 3},//�����
		{90, 90, 550, 6, 8, 90, 2},//�ѻ���
		{100, 100, 600, 8, 2, 70, 2}//̹�˱�
	};
	
	/**
	 * �ӷ�������ȡ�浵�ɵõ���ѡ����Ӫ֮ǰΪ0��1��G����2��U����
	 */
	public static int group = 0;

	/**
	 * ��ϷͼƬ��ŵķ�������ַ
	 */
	public static String imageServerUrl = "";//��jad��ȡ
	
	/**
	 * ��Ʒ��
	 */
	public final static String GAME_PROP_CODE = 					"P31014";
	
	/**
	 * ���ߵĲ���0�������룬1���۸�
	 */
	public final static String[][] A_GOODS_PARAM = {
		{"DJ32001", "2"},//�����������ò�����
		{"DJ32031", "1"},//�����ڱ�
		{"DJ32034", "1.5"},//���������
		{"DJ32037", "1.5"},//�����ѻ���
		{"DJ32040", "2"},//����̹�˱�
		{"DJ32030", "1"},//��������
		{"DJ32033", "1"},//�����ڱ�
		{"DJ32036", "1"},//���������
		{"DJ32039", "1"},//�����ѻ���
		{"DJ32042", "1"},//����̹�˱�
		{"DJ32029", "1"},//�ɱ�����
		{"DJ32032", "1"},//�ɱ��ڱ�
		{"DJ32035", "1.5"},//�ɱ������
		{"DJ32038", "1.5"},//�ɱ��ѻ���
		{"DJ32041", "2"},//�ɱ�̹�˱�
		{"DJ32043", "1"},//��ը��
		{"DJ32044", "1"}//����
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
	 * �û�Ԫ����
	 */
	public static String userCoin = "0";

	/**
	 * ��¼��Ϸ֡��
	 */
	public static long fps;

	/**
	 * ����
	 */
	public static int rank;
	
	/**
	 * ���а���Ϣ��ÿһ����һ����Ӧ��string����
	 */
	public static Vector rankInfo_g;

	/**
	 * u�����а�
	 */
	public static Vector rankInfo_u;
	
	/**
	 * ��¼����ĵ��ߵ��±�ֵ��
	 * 1 ~ 4������ʿ����
	 * 5 ~ 9��������
	 * 10 ~ 14���ɱ���
	 * 15����ը����
	 * 16�����
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
	 * �洢�����������ַ����������û�����
	 */
	public static String serverStr;

	/**
	 * ��ǰ�ؿ��÷�
	 */
	public static int score_mission;
	
	public static int totalScore;

 	public CanvasControl(MainMIDlet midlet) {
		this.midlet = midlet;
		
		C.WIDTH = (short) getWidth();
		C.HEIGTH = (short) getHeight();
C.out("cavas��ȣ�" + C.WIDTH + "\ncavas�߶ȣ�" + C.HEIGTH);
		getServerInfo(midlet);
		anHui_Tool = new AnHui_Tool(midlet, iptvID, this, GAME_PROP_CODE, false);
		updateUserCoin();
		
		serverIptv = new ServerIptv(this);
		serverIptv.doGetUserProfile();
		serverIptv.doGetRank(0);
		appStartTime = System.currentTimeMillis();
		serverIptv.sendGameTimeInfo(appStartTime + iptvID, "1", "add");
		serverIptv.sendClickTimes();
		serverIptv = null;
		
		playerHandler = new PlayerHandler(3, true);
		
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
		setView(new Loading(this, 0));
//		setView(new Complete(this));
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
		if(view instanceof Loading) {
			playerHandler.stopByIndex(0);
		}
		view.loadResource();
		this.view = view;
		repaint();
		serviceRepaints();
		System.gc();
	}

	protected void keyPressed(int keyCode) {
		playerHandler.playByIndex(1);
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
		
		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
		if(imageServerUrl==null){
			imageServerUrl = "http://61.191.44.224:8080/GameResource/worldWar";//���ص�ַ
		}
	}

	/**
	 * ����ɹ���Ĵ����߼�
	 */
	public void doBuySuccess() {
		if(goodsIndex < 5) { //����
			soldiers_level[goodsIndex] = 1;
			saveParam();
		} else if(goodsIndex < 10){ //����
			soldiers_level[goodsIndex - 5] += 1;
			saveParam();
		} else if (goodsIndex < 15) { //�ɱ�
			Game.rem_sendTimes[goodsIndex - 10] += 5;
		} else if(goodsIndex == 15) { //��ը��
			Game.sendBomber = true;
		} else if(goodsIndex == 16) { //����
			Game.relive = true;
		}
		
		new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
	}
	
	/**
	 * ��������
	 * @param propstate
	 */
	public void backFromPropServer(int propstate){
		
		System.out.println("propstate="+propstate);
//propstate = 0;
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
		sb_param.append(mission);
		sb_param.append(",");
		sb_param.append(group);
		for (int i = 0; i < soldiers_level.length; i++) {
			sb_param.append(",");
			sb_param.append(soldiers_level[i]);
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
		String[] a_s_param = subString(param, ',', 8);
		mission = Integer.parseInt(a_s_param[0]);
		group = Integer.parseInt(a_s_param[1]);
		for (int i = 2; i < a_s_param.length; i++) {
			soldiers_level[i - 2] = Integer.parseInt(a_s_param[i]);
		}
	}

	/**
	 * ��ȡ�ַ���
	 * @param str Ŀ���ַ���
	 * @param c �ضϵ���ַ�����������c��
	 * @param length ��ȡ��ĵõ����ַ������鳤��
	 * @return �õ����ַ�����
	 */
	private String[] subString(String str, char c, int length) {
		String[] result_a_str = new String[length];
		int index = 0;
		int end = 0;
		for (int i = 0; i < result_a_str.length; i++) {
			end = str.indexOf(c, index);
			if(end == -1) {
				end = str.length();
			}
			result_a_str[i] = str.substring(index, end);
//C.out("��ȡ�ַ���ʼĩλ��--------��ʼ��" + index + "----������" + end);
			index = end + 1;
			
		}
		return result_a_str;
	}

	public void handleBuy(String resultCode) {
		if ("1212200".equals(resultCode)) { //�����ɹ�
			updateUserCoin();
			new ServerIptv(this).sendBuyInfo(A_GOODS_PARAM[goodsIndex][0]);
			this.setView(new Dialog(this, 3, null));
		} else {//����ʧ��
			this.setView(new Dialog(this, 4, null));
		}
	}

	/**
	 * �����û�Ԫ������
	 */
	private void updateUserCoin() {
		userCoin = anHui_Tool.getCoin();
	}

	public void handleRecharge(String resultCode) {
	}
}
