package com.zhangniu.game;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.zhangniu.prop.Props;
import com.zhangniu.update.ServerIptv;

public class Alert {

	private Image[] imagea_GetScore;

	private Timer timer;

	public Screen s;

	private int[] buttonTwoArray = { 242, 345, 402, 345 };

	public int buttonindex;

	/**
	 * ��ʾ���ͼƬ��Դ
	 */
	private Image[] imagea_alter;

	/**
	 * Сͼ
	 */
	private Image[] imagea_BuyNumber;

	/**
	 * �ַ�buffer
	 */
	public StringBuffer sb;

	/**
	 * ���캯��
	 */
	public Alert(Screen screen) {
		s = screen;
		timer = new Timer();
		if (imagea_alter == null)
			imagea_alter = new Image[9];
		if (imagea_BuyNumber == null)
			imagea_BuyNumber = new Image[8];
	}

	/**
	 * ����ͼƬ��Դ
	 * 
	 * @param g
	 */
	public void loadSource() {
		excurrenttime = System.currentTimeMillis();

		// 30��ͷ�ؿ�ģʽ�µ���ʾ��
		// 50���ؼ�ʱģʽ�µ���ʾ��
		// 70������սģʽ�µ���ʾ��
		if (imagea_alter == null)
			imagea_alter = new Image[7];
		if (imagea_BuyNumber == null)
			imagea_BuyNumber = new Image[8];

		switch (C.alertType) {
		case 31:// �ӵ�1�ص���10��
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40: {
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			Image once = C.GetImageSource("/alertMessage/levelnumber.png");
			imagea_alter[1] = Image.createImage(once, 0, (C.level) * 38, 103,
					38, 0);
			imagea_alter[2] = C.GetImageSource("/alertMessage/anything.png");
			once = null;
			System.gc();
		}
			break;
		case 51:// ����ʱģʽ�£�ѯ���Ƿ���ʱ��
		case 41: {// û��ʱ���ˣ�ѯ���Ƿ���ʱ��
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// ���
			imagea_alter[2] = C.GetImageSource("/button/1.png");// ѡ��
			imagea_alter[3] = C
					.GetImageSource("/alertMessage/inadequateTime.png");// ��ʾ������
			imagea_alter[4] = C.GetImageSource("/button/12.png");// ȷ��
			imagea_alter[5] = C.GetImageSource("/button/2.png");// ȡ��
			buttonindex = 1;// ȡ��״̬
			priceX = 395;
			priceY = 210;
			propID = 0;
		}
			break;
		case 72:// ��սģʽ�� �����й���
		case 52:// ����ʱģʽ�� �����й���
		case 42: {// ����ģʽ�� �����й���
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // ѡ�м�ͷ
			C.GetImageSource("/button/1.png");
			imagea_alter[3] = // ���֣������ʱ��
			C.GetImageSource("/alertMessage/ai.png");
			imagea_alter[4] = // ȷ�Ϲ���
			C.GetImageSource("/button/12.png");
			imagea_alter[5] = // ����
			C.GetImageSource("/button/2.png");
			buttonindex = 1;// ��ť����ȡ��λ����
			priceX = 310;
			priceY = 213;
			propID = 1;
		}
			break;
		case 53:// �ڵ���ʱģʽ�·������˵�
		case 43: {// ������Ϸ���˵�����
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");
			imagea_alter[3] = C.GetImageSource("/alertMessage/pay.png");
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/2.png");
		}
			break;
		case 44: {// ��ϲ�ɹ�������һ��
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// ����
			imagea_alter[2] = C.GetImageSource("/button/1.png");// ���
			imagea_alter[3] = C.GetImageSource("/alertMessage/success.png");// ���سɹ�
			imagea_alter[4] = C.GetImageSource("/button/goon.png");// ������Ϸ
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");// �ҽ�
		}
			break;
		case 45: {// ���ź�������ʧ��
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// ����
			imagea_alter[2] = C.GetImageSource("/button/1.png");// ���
			imagea_alter[3] = C.GetImageSource("/alertMessage/fail.png");// ����ʧ��
			imagea_alter[4] = C.GetImageSource("/button/goon.png");// ������Ϸ
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");// �ҽ�
		}
			break;
		case 50: {// ����ʱģʽ ���������ʼ��Ϸ,�ڵ���ʱģʽ��
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[1] = C.GetImageSource("/alertMessage/anything.png");
		}
			break;
		case 54: {// ��Ϸ�����������������1000����.
			Image once = C.GetImageSource("/smashgoldeggs/7.png");
			imagea_GetScore = new Image[10];
			for (int i = 0; i < 10; i++) {
				imagea_GetScore[i] = Image.createImage(once, i * 17, 0, 17, 20,
						0);
			}
			once = null;

			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// ����
			imagea_alter[2] = C.GetImageSource("/button/1.png");// ���
			imagea_alter[3] = C.GetImageSource("/alertMessage/goscore.png");// ��Ϸ���������
			imagea_alter[4] = C.GetImageSource("/button/restart.png");// ������Ϸ
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");// �ҽ�
		}
			break;
		case 71: {// ��սģʽ ���������ʼ��Ϸ,�ڵ���ʱģʽ��
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[1] = C.GetImageSource("/alertMessage/anything.png");
		}
			break;
		case 0: {// ������Ϸǰ�ĵڼ��� ��ʾ����ڼ���
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[1] = C
					.GetImageSource("/alertMessage/firstcontent.png");
		}
			break;
		case 2: {// �˳���Ϸ�����ز˵�����
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");
			imagea_alter[3] = C.GetImageSource("/alertMessage/pay.png");
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/2.png");
		}
			break;
		case 6: {// ������߳ɹ�
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // ����ɹ� ����˵��
			C.GetImageSource("/alertMessage/successbuy.png");
		}
			break;
		case 7: {// �Ѿ����ﶩ����ֵ
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // �Ѿ������ֵ
			C.GetImageSource("/alertMessage/toptobuy.png");
		}
			break;
		case 8: {// �����ʻ�����
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // ��������
			C.GetImageSource("/alertMessage/notenoughtgold.png");
		}
			break;
		case 9: {// ����ʧ��������
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // ����ʧ��������
			C.GetImageSource("/alertMessage/failedtry.png");
		}
			break;
		case 10: {// �����й���
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // ѡ�м�ͷ
			C.GetImageSource("/button/1.png");
			imagea_alter[3] = // ���֣������ʱ��
			C.GetImageSource("/alertMessage/ai.png");
			imagea_alter[4] = // ȷ�Ϲ���
			C.GetImageSource("/button/12.png");
			imagea_alter[5] = // ����
			C.GetImageSource("/button/2.png");
			buttonindex = 1;
			priceX = 310;
			priceY = 213;
			propID = 1;
		}
			break;
		case 11: {
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // ȷ�ϰ�ť
			C.GetImageSource("/button/13.png");
			imagea_alter[2] = // ��û�н𴸣�����ͨ�����·�ʽ
			C.GetImageSource("/alertMessage/notenoughtgoldhammer.png");
		}
			break;
		case 12: {// �ڵ����̳����桢��1,2,3,4�����ֱ�ȥ�����
			Image imgonce = null;
			imgonce = C.GetImageSource("/alertMessage/tobuy.png");
			for (int a = 0; a < 8; a++) {
				imagea_BuyNumber[a] = Image.createImage(imgonce, a * 34, 0, 34,
						29, 0);
			}
			imgonce = null;
			System.gc();
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // �Ұ�ť
			C.GetImageSource("/button/0.png");
			imagea_alter[2] = // ѡ�м�ͷ
			C.GetImageSource("/button/1.png");
			imagea_alter[3] = // ���֣������ʱ��
			C.GetImageSource("/alertMessage/howtobuy.png");
			imagea_alter[4] = // ȷ�Ϲ���
			C.GetImageSource("/button/10.png");
			imagea_alter[5] = // ����
			C.GetImageSource("/button/2.png");
			buttonindex = 1;
		}
			break;
		case 13: {// ����Ƿǵ��ŵĺ���
			sb = new StringBuffer();
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // ��û�н𴸣�����ͨ�����·�ʽ
			C.GetImageSource("/alertMessage/nott.png");
			imagea_alter[2] = // ��û�н𴸣�����ͨ�����·�ʽ
			C.GetImageSource("/alertMessage/content.png");
		}
			break;
		case 14: {// �������󣬻�������ĺ��벻��Ψһ�ĺ���
			sb = new StringBuffer();
			imagea_alter[0] = // ���
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // ��û�н𴸣�����ͨ�����·�ʽ
			C.GetImageSource("/alertMessage/pleasetry.png");
			imagea_alter[2] = // ��û�н𴸣�����ͨ�����·�ʽ
			C.GetImageSource("/alertMessage/content.png");
		}
			break;
		case 15: {// �������û�ˣ�ѯ���û��Ƿ�������
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");// ѡ��
			imagea_alter[3] = C.GetImageSource("/alertMessage/fh.png");// ��ʾ������
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");
			priceX = 215;
			priceY = 192;
			propID = 2;
		}
			break;
		case 20:
		case 21:
		case 22:
		case 23: {
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");// ѡ��
			imagea_alter[3] = C.GetImageSource("/alertMessage/" + C.alertType
					+ ".png");// ��ʾ������
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/2.png");
			buttonindex = 1;
			priceX = 195;
			priceY = 230;
			propID = C.alertType - 18;
			System.out.println("propID:" + propID);
		}
			break;
		}
	}

	/**
	 * ��ʾ����潫����ͼƬ��Դ����
	 * 
	 * @param g
	 */
	public void removeAlertClassSource() {
		buttonindex = 0;
		for (int a = 0; a < 6; a++) {
			imagea_alter[a] = null;
		}
		for (int a = 0; a < 8; a++) {
			imagea_BuyNumber[a] = null;
		}
		System.gc();
	}

	/**
	 * ��ʾ������ʾ
	 * 
	 * @param c
	 */
	public void showMe(Graphics c) {
		switch (C.alertType) {
		case 31:// �ؿ�ģʽ�µ�һ�ص���10��
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40: {// ������Ϸǰ�ĵڼ��� ��ʾ����ڼ���
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);
			C.DrawImage_VH(imagea_alter[1], 318, 220, c);
			C.DrawImage_VH(imagea_alter[2], 318, 340, c);
		}
			break;
		case 54: {// ��Ϸ�����������������1000����.
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// ��������
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// ����ʱ�䣬˵������
			C.DrawImage_VH(imagea_alter[4], // ȷ��
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // ����
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // ����
					buttonTwoArray[buttonindex * 2] - 60,
					buttonTwoArray[1 + buttonindex * 2], c);
			C.DrawNumber_XY_RIGHTTOP(imagea_GetScore, C.time_Score, 220, 230,
					15, c);
		}
			break;
		case 45:// ���ź�������ʧ�ܣ�
		case 44:// ��ϲ���ɹ�������һ�أ�
		case 53:// �ڵ���ʱģʽ�£��Ƿ��˳���Ϸ�������˵�
		case 72:// ���й�ģʽ�£��Ƿ����й���
		case 51:// �ڼ�ʱģʽ�£��Ƿ���ʱ��
		case 52:// �ڼ�ʱģʽ�£��Ƿ����й���
		case 43:// �Ƿ񷵻���Ϸ�����˵�
		case 41:// ѯ���û��Ƿ���ʱ��
		case 15:// û����������ʾ�û��Ƿ���
		case 2: {// �˳���Ϸ�����ز˵�����
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// ��������
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// ����ʱ�䣬˵������
			if (C.alertType == 72 || C.alertType == 51 || C.alertType == 52
					|| C.alertType == 15 || C.alertType == 41) {
				drawCoin(c, 150, 300);
			}
			C.DrawImage_VH(imagea_alter[4], // ȷ��
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // ����
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // ����
					buttonTwoArray[buttonindex * 2] - 60,
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		case 6:// ������߳ɹ�
		case 7:// �Ѿ����ﶩ����ֵ
		case 8:// �����ʻ�����
		case 9: {// ����ʧ��������
			C.DrawImage_VH(imagea_alter[0], // ���
					318, 220, c);
			C.DrawImage_VH(imagea_alter[2], // ����˵���������ɹ�
					308, 220, c);
			handleShopping();
//			timer.schedule(new task(), 2000);
		}
			break;
		case 42:
		case 10: {// �����й���
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// ��������
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// ����ʱ�䣬˵������
			drawCoin(c, 150, 300);
			C.DrawImage_VH(imagea_alter[4], // ȷ�Ϲ���
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // ����
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // ����
					buttonTwoArray[buttonindex * 2] - 50,
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		case 71:
		case 50: {// ���������ʼ��Ϸ
			C.level = 0;
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// ��������
			C.DrawImage_VH(imagea_alter[1], 318, 240, c);// ���������ʼ��Ϸ
		}
			break;
		case 11: {
			C.DrawImage_VH(imagea_alter[0], 318, 270, c);// ���
			C.DrawImage_VH(imagea_alter[1], 318, 360, c);// ��ť
			C.DrawImage_VH(imagea_alter[2], 318, 270, c);// ����˵��
		}
			break;
		case 12: {// 1,2,3,4�����ֱ�ȥ�����
			C.DrawImage_VH(imagea_alter[0], 318, 270, c);// ��������
			C.DrawImage_VH(imagea_alter[3], 318, 260, c);// ����ʱ�䣬˵������
			drawCoin(c, 150, 300);
			C.DrawImage_VH(imagea_BuyNumber[C.BuyHowGoldHAMMER - 1], 330, 245,
					c);// ����
			C.DrawImage_VH(imagea_BuyNumber[C.BuyHowGoldHAMMER + 3], 365, 280,
					c);// ����

			C.DrawImage_VH(imagea_alter[1], // �ҵ�
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[1], // �ҵ�
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(imagea_alter[4], // ȷ�Ϲ���
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // ����
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // ����
					buttonTwoArray[0 + buttonindex * 2],
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		case 20:// �������ֲ�ͬ�����Ľ𴸸���
		case 21:
		case 22:
		case 23: {
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// ��������
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// ����ʱ�䣬˵������
			drawCoin(c, 150, 300);
			System.out.println("draw���ӣ�" + C.BuyHowGoldHAMMER);
			C.drawString(c, num, "" + (C.BuyHowGoldHAMMER + 1), "0123456789.",
					270, 192, 12, 18, 0, 0, 0);

			C.DrawImage_VH(imagea_alter[4], // ȷ��
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // ����
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // ����
					buttonTwoArray[buttonindex * 2] - 50,
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		}
	}

	private Image coin;
	private Image num;
	private int priceX, priceY;
	private int propID;

	public void drawCoin(Graphics g, int x, int y) {
		/*if (coin == null) {
			coin = C.GetImageSource("/coin/coin.png");
		}*/
		if(num == null) {
			num = C.GetImageSource("/alertMessage/num.png");
		}
	/*	g.drawImage(coin, x, y, 0);
		C.drawString(g, num, Props.Coin, "0123456789", x + coin.getWidth(),
				y + 2, 12, 18, 0, 0, 0);*/

		C.drawString(g, num, Screen.nx_tool.getSpecificPropsPrice(Props.propid[propID]), "0123456789.",
				priceX, priceY, 12, 18, 0, 0, 0);
	}

	public long currenttime;
	public long excurrenttime;
	public long d_value = 2000;

	public void keyPressed(int keyCode) {
		if (C.alertType == 72 || C.alertType == 51 || C.alertType == 52
				|| C.alertType == 15 || C.alertType == 41 || C.alertType == 42
				|| C.alertType == 10 || C.alertType == 12 || C.alertType == 20
				|| C.alertType == 21 || C.alertType == 22 || C.alertType == 23) {
			currenttime = System.currentTimeMillis();
			System.out.println("currenttime = " + currenttime);

			if (currenttime - excurrenttime <= d_value) {
				if (keyCode == C.KEY_FIRE) {
					return;
				} else {
					excurrenttime = currenttime - d_value * 2;
				}
			}
		}
		if (C.alertType == 0) {// ������Ϸǰ�ĵڼ��� ��ʾ����ڼ���,�����ǰ������������Ϸ��
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// ɾ�����е����ͼƬ
			s.gamechallenge.coutDownStart = true;
			Screen.status = Screen.S_GAME_CHALLENGE;
			return;
		}
		if (C.alertType >= 31 && C.alertType <= 40) {// ��1�ص���10��
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// ɾ�����е����ͼƬ
			s.gamelevel.coutDownStart = true;
			C.nowCountDown = s.gamelevel.countDown[C.level];
			Screen.status = Screen.S_GAME_LEVEL;
			return;
		}
		if (C.alertType == 50) {// ����ʱģʽ�£��������������Ϸ
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// ɾ�����е����ͼƬ
			s.gametime.coutDownStart = true;
			C.nowCountDown = s.gametime.countDownTime;
			Screen.status = Screen.S_GAME_TIME;
			return;
		}
		if (C.alertType == 71) {// ��ģʽ�°��������ʼ��Ϸ
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// ɾ�����е����ͼƬ
			s.gamechallenge.coutDownStart = true;
			Screen.status = Screen.S_GAME_CHALLENGE;
			return;
		}
		switch (keyCode) {
		case C.KEY_FIRE: {
			switch (C.alertType) {
			case 2: { // �˳���Ϸ���������˵�����
				if (buttonindex == 0) {// ȷ��
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					// // �����е�game�����Դȫ���ÿ�;
					s.gamechallenge.removeAllSources();
					s.gamechallenge = null;
					System.gc();
					s.setGameStatus(Screen.S_MENU);
				} else {// ȡ��
					Screen.status = Screen.S_GAME_CHALLENGE;
					s.gamechallenge.coutDownStart = true;
				}
			}
				break;
			case 72: {// ���й���
				if (buttonindex == 1) {// �û�����"����"
					removeAlertClassSource();
					Screen.status = Screen.S_GAME_CHALLENGE;
					s.gamechallenge.coutDownStart = true;
				} else {// �û�����ȷ�Ϲ���
					Screen.status = Screen.S_NULL;
					Screen.beforeStatus = Screen.S_GAME_CHALLENGE;
					C.alertBeforeType = 72;
					removeAlertClassSource();
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyAutoSmash();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(1);
					prop = null;
					System.gc();
				}
			}
				break;
			case 11: {// ��û�н𴸣�����ͨ�����·�ʽ
				removeAlertClassSource();
				System.gc();
				Screen.status = Screen.S_SMASH;
				s.repaint();
			}
				break;
			case 12: {// ����ͬ�����Ľ�
				if (buttonindex == 1) {// �û�����"����"
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					if (Screen.beforeStatus == Screen.S_PROPSHOP) {// �ڵ����̳ǽ���
						Screen.status = Screen.S_PROPSHOP;
						s.repaint();
					} else { // ���ҽ𵰽���
						Screen.status = Screen.S_SMASH;
						s.repaint();
					}
				} else {// �û�����ȷ�Ϲ���
					removeAlertClassSource();
					C.receiveKeyPressed = false;
					C.alertBeforeType = 12;
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyGoldHammer(String.valueOf(C.BuyHowGoldHAMMER +
					// 1));
					System.out.println("12type������:" + C.BuyHowGoldHAMMER);
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(C.BuyHowGoldHAMMER + 1);
					prop = null;
					System.gc();
				}
			}
				break;
			case 15: {// �û�û�������ˣ���ʾ�Ƿ���
				if (buttonindex == 1) {// �û�����ȡ��
					s.gamechallenge.removeAllSources();
					s.gamechallenge.autoSamsh = false;
					s.gamechallenge.autoSamshTime = 0;
					s.gamechallenge.coutDownStart = false;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// �û�����ȷ��
					C.receiveKeyPressed = false;
					Screen.beforeStatus = Screen.S_GAME_CHALLENGE;
					C.alertBeforeType = 15;
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyLife();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(2);
					prop = null;
					System.gc();
				}
			}
				break;
			case 20:
			case 21:
			case 22:
			case 23:// �������ֹ���
			{
				if (buttonindex == 1) {// ȡ��
					Screen.status = Screen.beforeStatus;
					s.repaint();
				} else {// ȷ������
					removeAlertClassSource();
					C.receiveKeyPressed = false;
					C.alertBeforeType = C.alertType;
//					C.BuyHowGoldHAMMER = C.alertType - 18;
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyGoldHammer(String.valueOf(C.BuyHowGoldHAMMER));
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(propID);
					prop = null;
					System.gc();
					
				}
			}
				break;
			case 41: {// ����ʱ��
				if (buttonindex == 1) {// ȡ��
					if (s.gamelevel != null)
						s.gamelevel.coutDownStart = true;
					if (s.gametime != null)
						s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// ȷ��
					// s.prop.buyTime();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(0);
					prop = null;
					System.gc();
				}
			}
				break;
			case 42: {// �����й�
				if (buttonindex == 1) {// ȡ��
					s.gamelevel.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// ȷ��
					// s.prop.buyAutoSmash();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(1);
					prop = null;
					System.gc();
				}
			}
				break;
			case 43: {// �Ƿ��˳���Ϸ�����˵�
				if (buttonindex == 1) {// ȡ��
					s.gamelevel.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// ȷ��
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					// // �����е�game�����Դȫ���ÿ�;
					s.gamelevel.removeAllSources();
					s.gamelevel = null;
					System.gc();
					s.setGameStatus(Screen.S_MENU);
				}
			}
				break;
			case 44: {// ��ϲ���ɹ�������һ�أ�
				if (buttonindex == 1) {// �ҽ�
					Screen.backScreen = Screen.S_GAME_LEVEL;
					Screen.status = Screen.S_NULL;
					Screen.beforeStatus = Screen.S_GAME_LEVEL;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// ������Ϸ
					if (C.level < 10)
						++C.level;
					Screen.status = Screen.S_GAME_LEVEL;
					s.repaint();
					s.serviceRepaints();

					Screen.status = Screen.S_NULL;
					C.passed = false;

					C.alertType = 31;
					s.setGameStatus(Screen.S_ALERT);
					s.repaint();
					s.serviceRepaints();
				}
			}
				break;
			case 45: {
				if (s.si == null)
					s.si = new ServerIptv(s);
				s.si.doSendUserInformation(1);
				C.level_Score = 0;

				if (buttonindex == 1) {// �ҽ�
					Screen.backScreen = Screen.S_GAME_LEVEL;
					Screen.status = Screen.S_NULL;
					Screen.beforeStatus = Screen.S_GAME_LEVEL;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// ������Ϸ
					C.level = 0;
					Screen.status = Screen.S_GAME_LEVEL;
					s.repaint();
					s.serviceRepaints();

					Screen.status = Screen.S_NULL;
					C.passed = false;

					C.alertType = 31;
					s.setGameStatus(Screen.S_ALERT);
					s.repaint();
					s.serviceRepaints();
				}
			}
				break;
			case 54: {// ��Ϸ�����������������1000����.
				if (s.si == null)
					s.si = new ServerIptv(s);
				s.si.doSendUserInformation(1);
				C.time_Score = 0;
				if (buttonindex == 1) {// �ҽ�
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// ������Ϸ
					Screen.status = Screen.S_GAME_TIME;
					s.repaint();
					s.serviceRepaints();

					Screen.status = Screen.S_NULL;
					C.nowCountDown = s.gametime.countDownTime;

					C.alertType = 50;
					s.setGameStatus(Screen.S_ALERT);
					s.repaint();
					s.serviceRepaints();
				}
			}
				break;
			case 53: {// �Ƿ��˳���Ϸ�����˵�
				if (buttonindex == 1) {// ȡ��
					s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// ȷ��
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					// // �����е�game�����Դȫ���ÿ�;
					s.gametime.removeAllSources();
					s.gametime = null;
					System.gc();
					s.setGameStatus(Screen.S_MENU);
				}
			}
				break;
			case 51: {// ��ʱģʽ���Ƿ���ʱ��
				if (buttonindex == 1) {// ȡ��
					s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// ȷ��
					Screen.beforeStatus = Screen.S_GAME_TIME;
					// s.prop.buyTime();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(0);
					prop = null;
					System.gc();
				}
			}
				break;
			case 52: {// ��ʱģʽ���Ƿ����й���
				if (buttonindex == 1) {// ȡ��
					s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// ȷ��
					// s.prop.buyAutoSmash();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(1);
					prop = null;
					System.gc();
				}
			}
				break;
			}
		}
			break;
		case C.KEY_LEFT: {
			if (buttonindex == 1)
				buttonindex = 0;
			s.repaint();
		}
			break;
		case C.KEY_RIGHT: {
			if (buttonindex == 0)
				buttonindex = 1;
			s.repaint();
		}
			break;
		}
	}

	public void handleShopping(){
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
				removeAlertClassSource();
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
	
	class task extends TimerTask {
		public void run() {
			// case 6:// �����ɹ����ﵽ��ֵ�����㡢����ʧ�ܡ�
			// case 7://�����Ϸ�Ƚ����⣬ȷ����ȡ���������ˣ���������������
			// case 8:
			// case 9: {// ����ɹ����
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
					removeAlertClassSource();
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
	}

}
