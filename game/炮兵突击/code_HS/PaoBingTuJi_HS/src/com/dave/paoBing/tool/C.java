package com.dave.paoBing.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author Dave
 * 主要存储一些静态方法
 * 一些application范围的常量
 *
 */
public class C {
	/**
	 * 角度值
	 */
	public static Hashtable angleValue;
	
	static {
		loadAngleValue();
	}
	
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
	
	public static final Font FONT_LARGE_BLOD = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
	
	public static final Random R = new Random();
	
//	public static final String COMPANYURL_PHP = "http://192.168.1.197/";//本公司内部的服务器
//	public static final String COMPANYURL_PHP = "http://122.224.212.78:7878/";//泰山服务器
	public static String COMPANYURL_PHP;//华数服务器从jad获取
	public final static String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";//密钥
	public final static String GAME_ADDRESS = "iptv.game";
	
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

	public static void loadAngleValue() {
		angleValue = new Hashtable();
		angleValue.put(String.valueOf(0), new Integer(0));
		angleValue.put(String.valueOf(1), new Integer(1745));
		angleValue.put(String.valueOf(2), new Integer(3490));
		angleValue.put(String.valueOf(3), new Integer(5236));
		angleValue.put(String.valueOf(4), new Integer(6976));
		angleValue.put(String.valueOf(5), new Integer(8716));
		angleValue.put(String.valueOf(6), new Integer(10453));
		angleValue.put(String.valueOf(7), new Integer(12187));
		angleValue.put(String.valueOf(8), new Integer(13917));
		angleValue.put(String.valueOf(9), new Integer(15643));
		angleValue.put(String.valueOf(10), new Integer(17365));
		angleValue.put(String.valueOf(11), new Integer(19081));
		angleValue.put(String.valueOf(12), new Integer(20791));
		angleValue.put(String.valueOf(13), new Integer(22495));
		angleValue.put(String.valueOf(14), new Integer(24192));
		angleValue.put(String.valueOf(15), new Integer(25882));
		angleValue.put(String.valueOf(16), new Integer(27564));
		angleValue.put(String.valueOf(17), new Integer(29237));
		angleValue.put(String.valueOf(18), new Integer(30902));
		angleValue.put(String.valueOf(19), new Integer(32557));
		angleValue.put(String.valueOf(20), new Integer(34202));
		angleValue.put(String.valueOf(21), new Integer(35837));
		angleValue.put(String.valueOf(22), new Integer(37461));
		angleValue.put(String.valueOf(23), new Integer(39073));
		angleValue.put(String.valueOf(24), new Integer(40674));
		angleValue.put(String.valueOf(25), new Integer(42262));
		angleValue.put(String.valueOf(26), new Integer(43837));
		angleValue.put(String.valueOf(27), new Integer(45400));
		angleValue.put(String.valueOf(28), new Integer(46947));
		angleValue.put(String.valueOf(29), new Integer(48481));
		angleValue.put(String.valueOf(30), new Integer(50000));
		angleValue.put(String.valueOf(31), new Integer(51504));
		angleValue.put(String.valueOf(32), new Integer(52992));
		angleValue.put(String.valueOf(33), new Integer(54464));
		angleValue.put(String.valueOf(34), new Integer(55919));
		angleValue.put(String.valueOf(35), new Integer(57358));
		angleValue.put(String.valueOf(36), new Integer(58779));
		angleValue.put(String.valueOf(37), new Integer(60182));
		angleValue.put(String.valueOf(38), new Integer(61566));
		angleValue.put(String.valueOf(39), new Integer(62932));
		angleValue.put(String.valueOf(40), new Integer(62932));
		angleValue.put(String.valueOf(41), new Integer(62932));
		angleValue.put(String.valueOf(42), new Integer(66913));
		angleValue.put(String.valueOf(43), new Integer(68200));
		angleValue.put(String.valueOf(44), new Integer(69466));
		angleValue.put(String.valueOf(45), new Integer(70711));
		angleValue.put(String.valueOf(46), new Integer(71934));
		angleValue.put(String.valueOf(47), new Integer(73135));
		angleValue.put(String.valueOf(48), new Integer(74314));
		angleValue.put(String.valueOf(49), new Integer(75471));
		angleValue.put(String.valueOf(50), new Integer(76604));
		angleValue.put(String.valueOf(51), new Integer(77715));
		angleValue.put(String.valueOf(52), new Integer(78801));
		angleValue.put(String.valueOf(53), new Integer(79864));
		angleValue.put(String.valueOf(54), new Integer(80902));
		angleValue.put(String.valueOf(55), new Integer(81915));
		angleValue.put(String.valueOf(56), new Integer(82904));
		angleValue.put(String.valueOf(57), new Integer(83867));
		angleValue.put(String.valueOf(58), new Integer(84805));
		angleValue.put(String.valueOf(59), new Integer(85717));
		angleValue.put(String.valueOf(60), new Integer(86603));
		angleValue.put(String.valueOf(61), new Integer(87462));
		angleValue.put(String.valueOf(62), new Integer(88295));
		angleValue.put(String.valueOf(63), new Integer(89101));
		angleValue.put(String.valueOf(64), new Integer(89879));
		angleValue.put(String.valueOf(65), new Integer(90631));
		angleValue.put(String.valueOf(66), new Integer(91355));
		angleValue.put(String.valueOf(67), new Integer(92051));
		angleValue.put(String.valueOf(68), new Integer(92718));
		angleValue.put(String.valueOf(69), new Integer(93358));
		angleValue.put(String.valueOf(70), new Integer(93969));
		angleValue.put(String.valueOf(71), new Integer(94552));
		angleValue.put(String.valueOf(72), new Integer(95106));
		angleValue.put(String.valueOf(73), new Integer(95630));
		angleValue.put(String.valueOf(74), new Integer(96126));
		angleValue.put(String.valueOf(75), new Integer(96593));
		angleValue.put(String.valueOf(76), new Integer(97030));
		angleValue.put(String.valueOf(77), new Integer(97437));
		angleValue.put(String.valueOf(78), new Integer(97815));
		angleValue.put(String.valueOf(79), new Integer(98163));
		angleValue.put(String.valueOf(80), new Integer(98481));
		angleValue.put(String.valueOf(81), new Integer(98769));
		angleValue.put(String.valueOf(82), new Integer(99027));
		angleValue.put(String.valueOf(83), new Integer(99255));
		angleValue.put(String.valueOf(84), new Integer(99452));
		angleValue.put(String.valueOf(85), new Integer(99619));
		angleValue.put(String.valueOf(86), new Integer(99756));
		angleValue.put(String.valueOf(87), new Integer(99863));
		angleValue.put(String.valueOf(88), new Integer(99939));
		angleValue.put(String.valueOf(89), new Integer(99985));
		angleValue.put(String.valueOf(90), new Integer(100000));
	}

	/**
	 * 0~180°正弦值
	 * @param angle
	 * @return
	 */
	public static int getSinAngleValue(int angle) {
		int tempAngle = (angle > 90 ? (180 - angle) : angle);
		int tempValue = ((Integer) (angleValue.get(String.valueOf(tempAngle))))
				.intValue();
		return tempValue;
	}
	
	/**
	 * 任意角度的正弦值
	 * @param angle
	 * @return sin值的100000倍
	 */
	public static int sin(int angle) {
		int angle_f = angle % 180;
		
		if(angle_f < 0) {
			int angle_s = angle_f < -90 ? 180 + angle_f : -angle_f;
			return  -((Integer)angleValue.get(String.valueOf(angle_s))).intValue();
		} else {
			int angle_s = angle > 90 ? (180 - angle) : angle;
			return ((Integer)angleValue.get(String.valueOf(angle_s))).intValue();
		}
	}

	/**
	 * 0~180°余弦值
	 * @param angle
	 * @return 
	 */
	public static int getCosAngleValue(int angle) {
		int tempAngle = (angle > 90 ? angle - 90 : 90 - angle);
		int tempValue = 0;
		try {
			tempValue = ((Integer) (angleValue.get(String.valueOf(tempAngle))))
					.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			C.out("这个值没有:"+tempAngle);
		}
		return (angle <= 90 ? tempValue : (-tempValue));
	}
	
	/**
	 * 任意角度的cos值
	 * @param angle 角度值（不是弧度！）
	 * @return cos值的100000倍
	 */
	public static int cos(int angle) {
		int angle_f = angle % 180;
		
		int angle_s = angle_f < 0 ? -angle_f : angle_f;
		
		return getCosAngleValue(angle_s);
	}

	/**
	 * 计算0~30°以内的反正切。
	 * @param value 正切值放大1000000得到的值。
	 * @return 放回角度的int值。0~30；
	 */
	public static int arctan(int value) {
		int result = 0;
		if(value < 8727) result = 0;
		else if(value < 26186) result = 1;
		else if(value < 43661) result = 2;
		else if(value < 61163) result = 3;
		else if(value < 78702) result = 4;
		else if(value < 96289) result = 5;
		else if(value < 113936) result = 6;
		else if(value < 131652) result = 7;
		else if(value < 149457) result = 8;
		else if(value < 167343) result = 9;
		else if(value < 185339) result = 10;
		else if(value < 203452) result = 11;
		else if(value < 221695) result = 12;
		else if(value < 240079) result = 13;
		else if(value < 258618) result = 14;
		else if(value < 277325) result = 15;
		else if(value < 296213) result = 16;
		else if(value < 315299) result = 17;
		else if(value < 334595) result = 18;
		else if(value < 354118) result = 19;
		else if(value < 373885) result = 20;
		else if(value < 393910) result = 21;
		else if(value < 414214) result = 22;
		else if(value < 434812) result = 23;
		else if(value < 455726) result = 24;
		else if(value < 476976) result = 25;
		else if(value < 498582) result = 26;
		else if(value < 520567) result = 27;
		else if(value < 542956) result = 28;
		else if(value < 565773) result = 29;
		else if(value < 589045) result = 30;
		else result = 361;//不在0~30°以内。
		
		return result;
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
	
	/** 加载地图数组
	 * @param name
	 * @return
	 * @throws FileNotFoundException */
	public static int[][] loadObstacleArray(String name) throws IOException {
		InputStream is = C.class.getResourceAsStream(name);
		if (is == null) {
			throw new IOException("无法加载文件：" + name);
		}
		int[][] mapSource = null;
		int index = 0;
		int[] mapRowSource = null;
		int rowIndex = 0;
		int i = -1;
		int row = -1;
		int col = -1;
		StringBuffer sb = new StringBuffer();
		try {
			while ((i = is.read()) != -1) {
				switch (i) {
				case '=':
				case ' ':
				case '\r':
				case '\n':
				case 'm':
				case 'a':
				case 'p':
					break;
				case '[':
					sb.setLength(0);
					break;
				case ']':
					int num = Integer.parseInt(sb.toString());
					if (row == -1) {
						row = num;
						mapSource = new int[row][];
					} else {
						col = num;
					}
					sb.setLength(0);
					break;
				case '{':
					mapRowSource = new int[col];
					rowIndex = 0;
					break;
				case '}':
					if (index >= mapSource.length) {
						break;
					}
				case ',':
					if ("".equals(sb.toString())) {
						break;
					}
					int n = Integer.parseInt(sb.toString());
					mapRowSource[rowIndex++] = n;
					sb.setLength(0);
					if (i == '}') {
						mapSource[index++] = mapRowSource;
					}
					break;
				default:
					sb.append((char) i);
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mapSource;
	}


	/**
	 * 截取字符串
	 * @param width_show 字符串显示框的宽度
	 * @param font 字体
	 * @param str_target 目标字符串
	 * @return 得到的字符串数组
	 */
	public static String[] subStr(int width_show, Font font, String str_target) {
		String[] result = null;
		int w_font = font.charWidth('国');
//		int h_font = font.getHeight();
		int amountRow = width_show / w_font;
//		heightRow = h_font;
		
		int length = str_target.length();
		int row = length % amountRow == 0 ? length / amountRow : length / amountRow + 1;
		result = new String[row];
		for(int i=0; i<row; i++) {
			if(i == row - 1) {
				result[i] = str_target.substring(i * amountRow, length);
			} else {
				result[i] = str_target.substring(i * amountRow, i * amountRow + amountRow);
			}
		}
		
		return result;
	}

	public static void DrawImage_VH(Image image, int x, int y, Graphics g) {
		g.drawImage(image, x, y, Graphics.VCENTER | Graphics.HCENTER);
	}

	
}
	
	