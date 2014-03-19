package com.dave.ftxz.main;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.PlayerHandler;
import com.dave.ftxz.view.Dialog;
import com.dave.ftxz.view.Game;
import com.dave.ftxz.view.Home;
import com.dave.ftxz.view.NullView;
import com.dave.showable.Showable;
import com.dave.xinJiang.Handler_XJ;
import com.dave.xinJiang.Main_XJ;

/**
 * @author Dave
 * 
 */
public class CanvasControl extends Canvas implements Runnable, Handler_XJ {

	private MainMIDlet midlet;

	public static String iptvID;

	public final static String VERSION = "Version_XJ_1.0.4";

	/**
	 * �½�����
	 */
	public Main_XJ xj_tool;

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
	public final static String GAME_PROP_CODE = "P31017";

	/**
	 * ���ߵĲ���0�������룬1���۸�
	 */
	public final static String[][] A_GOODS_PARAM = { { "DJ32075", "100" },// ��С��
			{ "DJ32076", "100" },// ��С��
			{ "DJ32077", "100" },// ��С��
			{ "DJ32079", "100" },// �ȳ�
			{ "DJ32080", "100" },// ����
			{ "DJ32081", "100" },// ����
			{ "DJ32082", "100" },// ���
			{ "DJ32083", "100" },// �޵�
			{ "DJ32084", "100" },// ����ʯ
			{ "DJ32078", "100" } // ����
	};

	/**
	 * �½��ĵ�����Ϣ�������ҷֱ��ǣ��۸񣬹��ܽ������֣��������ƣ�����ԭ��"����id,����"��
	 */
	public final static String[][] A_XJ_GOODS_INFO = {
			{ "100", "ӵ�п�¡���ܣ�����õĽ�Ҷ��ᷭ��", "��С��", "DJ32075,1", },// ��С��
			{ "100", "ӵ�д������ܣ����Զ�������Χ�Ľ��", "��С��", "DJ32076,1", },// ��С��
			{ "100", "���ܣ�������ֻ��ʹ��һ��", "��С��", "DJ32077,1", },// ��С��
			{ "100", "���������������", "�ȳ�", "DJ32079,1", },// �ȳ�
			{ "100", "�������ǵĹ������͹�����Χ", "����", "DJ32080,1", },// ����
			{ "100", "�������˵��ƶ��ٶȡ��������Ϸ���Զ�����", "����", "DJ32081,1", },// ����
			{ "100", "����һ�����ٶȳ��2000�ף����ҳ�̹����в����ܵ��˺����������Ϸ���Զ�����", "���",
					"DJ32082,1", },// ���
			{ "100", "30���ڣ����Ǵ����޵�״̬���������Ϸ���Զ�����", "�޵�", "DJ32083,1", },// �޵�
			{ "100", "30���ڣ������Զ�������Χ��ҡ��������Ϸ���Զ�����", "����ʯ", "DJ32084,1", },// ����ʯ
			{ "100", "���»���������������Ϸ���Զ�����", "����", "DJ32078,1", } // ����
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

		playerHandler = new PlayerHandler(5, 2);

		nullView = new NullView();

		String pro = (System.getProperty("microedition.platform"));
		C.out("pro:" + pro);
		if (pro.substring(0, 1).equals("z")) {
			C.isZHONGXING = true;
		}
		setView(new Home(this));
		// launch();
	}

	public void launch() {
		new Thread(this).start();
		
		xj_tool = new Main_XJ(false, midlet, this, "1101", "800102",
				"77f42e3e2a84d7e05ee7861e1ede0ef2", "CP800102");
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
		if (!(view instanceof Game))
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
	 * ��ȡiptvID���޷���ȡ����ֵ�� ��ȡ���߶�����ַ�� ��ȡͼƬ��������ַ��
	 * 
	 * @param midlet
	 */
	private void getServerInfo(MIDlet midlet) {
		iptvID = midlet.getAppProperty("userId");
		if (iptvID == null) {
			iptvID = "777722";
		}

		url = midlet.getAppProperty("URL_BSCS_PROPS_ORDER");
		if (url == null) {
			url = C.COMPANYURL_PHP;
		}

		imageServerUrl = midlet.getAppProperty("ImageServerUrl");
		if (imageServerUrl == null) {
			// imageServerUrl = "http://222.46.20.151:8055/ftxz";// ���ص�ַ
			imageServerUrl = "http://122.224.212.78:7878/GameResource/ftxz";// �㽭���Ե�ַ
		}
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

		xj_tool.saveParam(null, serverStr);
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

	/**
	 * �½�����
	 * 
	 * @param goodsIndex
	 */
	public void xj_shopping(int goodsIndex) {
		try {
			xj_tool.buy(
					Image.createImage("/dialog/goods_icon_" + goodsIndex
							+ ".png"),
					Integer.parseInt(A_XJ_GOODS_INFO[goodsIndex][0]),
					A_XJ_GOODS_INFO[goodsIndex][1],
					A_XJ_GOODS_INFO[goodsIndex][2],
					A_XJ_GOODS_INFO[goodsIndex][3]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleItem(int[][] szItem) {// ��������������б�[n][2]��[n][0]
											// �ǵ���id��[n][1]�ǵ���������

	}

	public void handleLinked() {// soket�����ѽ���

		xj_tool.getParam(null);

	}

	public void handleParam(String paramName, String param) {// ����浵
		handleParam(param);
	}

	public void handleReturn() {// �����ѽ��淵��ʱ�Ļص�����
		if (goodsIndex == 9) {
			setView(new Dialog(this, 7, goBackView));
		} else {
			setView(goBackView);
		}
	}

	public void handleScore(int mission, int score) {// �������

	}

	public void handleShop() {// ���ѳɹ�
		doBuySuccess();
		if (willStart) {// ��ʼ��Ϸ
			willStart = false;
			type_hero = CanvasControl.goodsIndex + 1;
			goodsNumber[CanvasControl.goodsIndex]--;
			setView(new Game(this));
		} else {
			setView(goBackView);
		}
	}

}
