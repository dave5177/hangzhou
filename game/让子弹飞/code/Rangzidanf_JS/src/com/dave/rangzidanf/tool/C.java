package com.dave.rangzidanf.tool;

import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author Dave
 * ��Ҫ�洢һЩ��̬����
 * һЩapplication��Χ�ĳ���
 *
 */
public class C {
	
	// ��Ϸ����
	public final static int KEY_0 = 48;
	public final static int KEY_1 = 49;
	public final static int KEY_2 = 50;
	public final static int KEY_3 = 51;
	public final static int KEY_4 = 52;
	public final static int KEY_5 = 53;
	public final static int KEY_6 = 54;
	public final static int KEY_7 = 55;
	public final static int KEY_8 = 56;
	public final static int KEY_9 = 57;

	public final static int KEY_UP = -1;
	public final static int KEY_DOWN = -2;
	public final static int KEY_LEFT = -3;
	public final static int KEY_RIGHT = -4;
	public final static int KEY_FIRE = -5;
	public final static int KEY_BACK = -31;// ��ʽƽ̨Ӧ�������
	public final static int KEY_BACK_ZX=-7;//  ���˷���
	
	public static short WIDTH = 640;  //��Ļ���
	public static short HEIGTH = 530;//��Ļ�߶�
	public static boolean isZHONGXING;
	
	public static final Random R = new Random();
	
//	public static final String COMPANYURL_PHP = "http://192.168.1.197/";//����˾�ڲ��ķ�����
//	public static final String COMPANYURL_PHP = "http://122.224.212.78:7878/";//̩ɽ������
	public static final String COMPANYURL_PHP = "http://61.160.131.57:8083/www.iptvgame.com/";//���շ�����
	public final static String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";//��Կ
	public final static String GAME_ADDRESS = "iptv.game";
	
	/**************************************************************** �ڴ�������� */
	/**
	 * ������ͼƬ
	 * 
	 * @param imagea_number
	 * @param x
	 * @param y
	 * @param offer
	 * @param canvas
	 */
	public static void DrawNumber_XY_RIGHTTOP(Image[] imagea_number, int key,
			int x, int y, int offer, Graphics canvas) {
		if(key<0)return;
		String strkey = String.valueOf(key);
		byte strlenght = (byte) strkey.length();//
		byte[] bytea_str = new byte[strlenght];// �õ�����int�͵ĳ���
		for (int a = 0; a < strlenght; a++) {
			bytea_str[a] = (byte) Integer.parseInt(strkey.substring(a, a + 1));
		}
		for (int a = 0; a < strlenght; a++) {
			canvas.drawImage(imagea_number[bytea_str[(strlenght - a - 1)]], x
					- a * offer, y, Graphics.LEFT | Graphics.TOP);
		}
	}


	/**
	 * system.out print ln
	 */
	public static void out(String string) {
		System.out.println(string);
	}
	
	/**
	 * ���Զ���ͼ�ֿ��������
	 * 
	 * @param g
	 * @param img
	 *            �ֿ�ͼ
	 * @param str
	 *            Ҫ���Ƶ��ַ���
	 * @param fontStoreroom
	 *            �ֿ��ַ���
	 * @param x
	 *            ���Ƶ�xλ��
	 * @param y
	 *            ���Ƶ�yλ��
	 * @param clipW
	 *            ͼ�Ͳü����(ÿ����)
	 * @param clipH
	 *            ͼ�Ͳü��߶�(ÿ����)
	 * @param anchor
	 *            �Ե�ǰһ�����ֽ���ê��
	 * @param space
	 *            ÿ�����ֵĿ�϶
	 * @param numDigit
	 *            �����ֽ��в�0 ,�����Ϊ6����Ϊ1��ôӦ�û���Ϊ000001
	 */
	public static void drawString(Graphics g, Image img, String str,
			String fontStoreroom, int x, int y, int clipW, int clipH,
			int anchor, int space, int numDigit) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		for (int i = sb.length(); i < numDigit; i++) {
			sb.insert(0, "0");// maxNumDigit �������Ļ���0 ��1 == 001
		}
		int charId;
		str = sb.toString();
		int strLength = str.length();
		int offset = getAnchorOffset(anchor, strLength * clipW + space, clipH);
		x -= (offset & 0xffff0000) >> 16;
		y -= (offset & 0xffff);
		for (int i = 0; i < strLength; i++, x += clipW + space) {
			charId = fontStoreroom.indexOf(str.charAt(i));
			if (charId == -1) {
				g.setClip(x, y, clipW, 30);
				g.drawChar(str.charAt(i), x, y, 0);
				// continue;
			} else {
				g.setClip(x, y, clipW, clipH);
				g.drawImage(img, x - charId * clipW, y, 0);
			}
		}
		g.setClip(0, 0, 644, 534);
	}

	/**
	 * �Զ����ê�㷽��. ��Ϊ����ͼ�ͺ����ֽ���ê�� �˷�����֧��Graphics.BASELINE��Ϊ transform���� �÷�int
	 * offsetXY = getAnchorOffset(anchor,transform,width,height); x_dest -=
	 * (short)((offsetXY >> 16) & 0xffff); y_dest -= (short)(offsetXY & 0xffff);
	 * 
	 * @param anchor
	 *            Graphics���ê��ֵ
	 * @param w
	 *            ����Ŀ��
	 * @param h
	 *            ����ĸ߶�
	 * @return һ��intֵ�������ê����λ��ƫ������
	 */
	public static int getAnchorOffset(int anchor, int w, int h) {
		int offX = 0;
		int offY = 0;
		if ((anchor & Graphics.BASELINE) != 0)
			throw new java.lang.IllegalArgumentException();
		if ((anchor & Graphics.HCENTER) != 0) {
			offX = w >> 1;
		} else if ((anchor & Graphics.RIGHT) != 0) {
			offX = w;
		}
		if ((anchor & Graphics.VCENTER) != 0) {
			offY = h >> 1;
		} else if ((anchor & Graphics.BOTTOM) != 0) {
			offY = h;
		}
		return (offX << 16) | offY;
	}// :~QN
	
}
	
	