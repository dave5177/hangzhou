package com.dave.rangzidanf.tool;

import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author Dave
 * 主要存储一些静态方法
 * 一些application范围的常量
 *
 */
public class C {
	
	// 游戏按键
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
	public final static int KEY_BACK = -31;// 正式平台应该是这个
	public final static int KEY_BACK_ZX=-7;//  中兴返回
	
	public static short WIDTH = 640;  //屏幕宽度
	public static short HEIGTH = 530;//屏幕高度
	public static boolean isZHONGXING;
	
	public static final Random R = new Random();
	
//	public static final String COMPANYURL_PHP = "http://192.168.1.197/";//本公司内部的服务器
//	public static final String COMPANYURL_PHP = "http://122.224.212.78:7878/";//泰山服务器
	public static final String COMPANYURL_PHP = "http://61.160.131.57:8083/www.iptvgame.com/";//江苏服务器
	public final static String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";//密钥
	public final static String GAME_ADDRESS = "iptv.game";
	
	/**************************************************************** 内存检测块区域 */
	/**
	 * 画数字图片
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
		byte[] bytea_str = new byte[strlenght];// 得到传入int型的长度
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
	 * 用自定义图字库绘制文字
	 * 
	 * @param g
	 * @param img
	 *            字库图
	 * @param str
	 *            要绘制的字符串
	 * @param fontStoreroom
	 *            字库字符串
	 * @param x
	 *            绘制的x位置
	 * @param y
	 *            绘制的y位置
	 * @param clipW
	 *            图型裁剪宽度(每个字)
	 * @param clipH
	 *            图型裁剪高度(每个字)
	 * @param anchor
	 *            对当前一行文字进行锚点
	 * @param space
	 *            每个文字的空隙
	 * @param numDigit
	 *            对数字进行补0 ,如参数为6数字为1那么应该绘制为000001
	 */
	public static void drawString(Graphics g, Image img, String str,
			String fontStoreroom, int x, int y, int clipW, int clipH,
			int anchor, int space, int numDigit) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		for (int i = sb.length(); i < numDigit; i++) {
			sb.insert(0, "0");// maxNumDigit 不够数的话补0 例1 == 001
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
	 * 自定义的锚点方法. 可为各种图型和文字进行锚点 此方法不支持Graphics.BASELINE作为 transform参数 用法int
	 * offsetXY = getAnchorOffset(anchor,transform,width,height); x_dest -=
	 * (short)((offsetXY >> 16) & 0xffff); y_dest -= (short)(offsetXY & 0xffff);
	 * 
	 * @param anchor
	 *            Graphics类的锚点值
	 * @param w
	 *            对象的宽度
	 * @param h
	 *            对象的高度
	 * @return 一个int值里面包括锚点后的位置偏移坐标
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
	
	