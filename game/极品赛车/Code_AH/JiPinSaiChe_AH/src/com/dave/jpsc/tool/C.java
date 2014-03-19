package com.dave.jpsc.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.net.mBase64;

/**
 * @author Dave 主要存储一些静态方法 一些application范围的常量
 * 
 */
public class C {
	/**
	 * 角度sin值
	 */
	public static int[] angleValue;

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
	public final static int KEY_BACK_ZX = -7;// 中兴返回

	public static short WIDTH = 640; // 屏幕宽度
	public static short HEIGTH = 530;// 屏幕高度
	public static boolean isZHONGXING;

	public static final Font FONT_LARGE_BLOD = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_BOLD, Font.SIZE_LARGE);
	public static final Font FONT_MEDIUM_BOLD = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_BOLD, Font.SIZE_MEDIUM);
	public static final Font FONT_SMALL_PLAIN = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_SMALL);

	public static final Random R = new Random();

//	public static final String COMPANYURL_PHP = "http://192.168.1.197/";// 本公司内部的服务器
//	public static final String COMPANYURL_PHP = "http://122.224.212.78:7878/";// 泰山服务器
	public final static String COMPANYURL_PHP = "http://61.191.44.224:8080/www.iptvgame.com/";// 安徽的服务器
	public final static String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";// 密钥
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
		if (key < 0)
			return;
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
		angleValue = new int[91];
		angleValue[0] = 0;
		angleValue[1] = 1745;
		angleValue[2] = 3490;
		angleValue[3] = 5236;
		angleValue[4] = 6976;
		angleValue[5] = 8716;
		angleValue[6] = 10453;
		angleValue[7] = 12187;
		angleValue[8] = 13917;
		angleValue[9] = 15643;
		angleValue[10] = 17365;
		angleValue[11] = 19081;
		angleValue[12] = 20791;
		angleValue[13] = 22495;
		angleValue[14] = 24192;
		angleValue[15] = 25882;
		angleValue[16] = 27564;
		angleValue[17] = 29237;
		angleValue[18] = 30902;
		angleValue[19] = 32557;
		angleValue[20] = 34202;
		angleValue[21] = 35837;
		angleValue[22] = 37461;
		angleValue[23] = 39073;
		angleValue[24] = 40674;
		angleValue[25] = 42262;
		angleValue[26] = 43837;
		angleValue[27] = 45400;
		angleValue[28] = 46947;
		angleValue[29] = 48481;
		angleValue[30] = 50000;
		angleValue[31] = 51504;
		angleValue[32] = 52992;
		angleValue[33] = 54464;
		angleValue[34] = 55919;
		angleValue[35] = 57358;
		angleValue[36] = 58779;
		angleValue[37] = 60182;
		angleValue[38] = 61566;
		angleValue[39] = 62932;
		angleValue[40] = 62932;
		angleValue[41] = 62932;
		angleValue[42] = 66913;
		angleValue[43] = 68200;
		angleValue[44] = 69466;
		angleValue[45] = 70711;
		angleValue[46] = 71934;
		angleValue[47] = 73135;
		angleValue[48] = 74314;
		angleValue[49] = 75471;
		angleValue[50] = 76604;
		angleValue[51] = 77715;
		angleValue[52] = 78801;
		angleValue[53] = 79864;
		angleValue[54] = 80902;
		angleValue[55] = 81915;
		angleValue[56] = 82904;
		angleValue[57] = 83867;
		angleValue[58] = 84805;
		angleValue[59] = 85717;
		angleValue[60] = 86603;
		angleValue[61] = 87462;
		angleValue[62] = 88295;
		angleValue[63] = 89101;
		angleValue[64] = 89879;
		angleValue[65] = 90631;
		angleValue[66] = 91355;
		angleValue[67] = 92051;
		angleValue[68] = 92718;
		angleValue[69] = 93358;
		angleValue[70] = 93969;
		angleValue[71] = 94552;
		angleValue[72] = 95106;
		angleValue[73] = 95630;
		angleValue[74] = 96126;
		angleValue[75] = 96593;
		angleValue[76] = 97030;
		angleValue[77] = 97437;
		angleValue[78] = 97815;
		angleValue[79] = 98163;
		angleValue[80] = 98481;
		angleValue[81] = 98769;
		angleValue[82] = 99027;
		angleValue[83] = 99255;
		angleValue[84] = 99452;
		angleValue[85] = 99619;
		angleValue[86] = 99756;
		angleValue[87] = 99863;
		angleValue[88] = 99939;
		angleValue[89] = 99985;
		angleValue[90] = 100000;
	}

	/**
	 * 0~180°正弦值
	 * 
	 * @param angle
	 * @return
	 */
	public static int getSinAngleValue(int angle) {
		int tempAngle = (angle > 90 ? (180 - angle) : angle);
		int tempValue = angleValue[tempAngle];
		return tempValue;
	}

	/**
	 * 任意角度的正弦值
	 * 
	 * @param angle
	 * @return sin值的100000倍
	 */
	public static int sin(int angle) {
		int angle_f = angle % 360;
		if (angle_f >= 180)
			angle_f = angle_f - 360;
		else if (angle_f <= -180)
			angle_f = angle_f + 360;

		if (angle_f < 0) {
			int angle_s = angle_f < -90 ? 180 + angle_f : -angle_f;
			return -angleValue[angle_s];
		} else {
			int angle_s = angle_f > 90 ? (180 - angle_f) : angle_f;
			return angleValue[angle_s];
		}
	}

	/**
	 * 0~180°余弦值
	 * 
	 * @param angle
	 * @return
	 */
	public static int getCosAngleValue(int angle) {
		int tempAngle = (angle > 90 ? angle - 90 : 90 - angle);
		int tempValue = 0;
		try {
			tempValue = angleValue[tempAngle];
		} catch (Exception e) {
			e.printStackTrace();
			C.out("这个值没有:" + tempAngle);
		}
		return (angle <= 90 ? tempValue : (-tempValue));
	}

	/**
	 * 任意角度的cos值
	 * 
	 * @param angle
	 *            角度值（不是弧度！）
	 * @return cos值的100000倍
	 */
	public static int cos(int angle) {
		int angle_f = angle % 360;
		if (angle_f >= 180)
			angle_f = angle_f - 360;
		else if (angle_f <= -180)
			angle_f = angle_f + 360;

		int angle_s = angle_f < 0 ? -angle_f : angle_f;

		return getCosAngleValue(angle_s);
	}

	/**
	 * 计算0~30°以内的反正切。
	 * 
	 * @param value
	 *            正切值放大1000000得到的值。
	 * @return 放回角度的int值。0~90；
	 */
	public static int arctan(int value) {
		int result = 0;
		if (value < 8727)
			result = 0;
		else if (value < 26186)
			result = 1;
		else if (value < 43661)
			result = 2;
		else if (value < 61163)
			result = 3;
		else if (value < 78702)
			result = 4;
		else if (value < 96289)
			result = 5;
		else if (value < 113936)
			result = 6;
		else if (value < 131652)
			result = 7;
		else if (value < 149457)
			result = 8;
		else if (value < 167343)
			result = 9;
		else if (value < 185339)
			result = 10;
		else if (value < 203452)
			result = 11;
		else if (value < 221695)
			result = 12;
		else if (value < 240079)
			result = 13;
		else if (value < 258618)
			result = 14;
		else if (value < 277325)
			result = 15;
		else if (value < 296213)
			result = 16;
		else if (value < 315299)
			result = 17;
		else if (value < 334595)
			result = 18;
		else if (value < 354118)
			result = 19;
		else if (value < 373885)
			result = 20;
		else if (value < 393910)
			result = 21;
		else if (value < 414214)
			result = 22;
		else if (value < 434812)
			result = 23;
		else if (value < 455726)
			result = 24;
		else if (value < 476976)
			result = 25;
		else if (value < 498582)
			result = 26;
		else if (value < 520567)
			result = 27;
		else if (value < 542956)
			result = 28;
		else if (value < 565773)
			result = 29;
		else if (value < 589045)
			result = 30;
		else if (value < 612800)
			result = 31;
		else if (value < 637070)
			result = 32;
		else if (value < 661885)
			result = 33;
		else if (value < 687280)
			result = 34;
		else if (value < 713293)
			result = 35;
		else if (value < 739961)
			result = 36;
		else if (value < 767326)
			result = 37;
		else if (value < 795435)
			result = 38;
		else if (value < 824336)
			result = 39;
		else if (value < 854080)
			result = 40;
		else if (value < 884725)
			result = 41;
		else if (value < 916331)
			result = 42;
		else if (value < 948964)
			result = 43;
		else if (value < 982697)
			result = 44;
		else if (value < 1017607)
			result = 45;
		else if (value < 1053780)
			result = 46;
		else if (value < 1091308)
			result = 47;
		else if (value < 1130294)
			result = 48;
		else if (value < 1170849)
			result = 49;
		else if (value < 1213097)
			result = 50;
		else if (value < 1257172)
			result = 51;
		else if (value < 1303225)
			result = 52;
		else if (value < 1351422)
			result = 53;
		else if (value < 1401948)
			result = 54;
		else if (value < 1455009)
			result = 55;
		else if (value < 1510835)
			result = 56;
		else if (value < 1569685)
			result = 57;
		else if (value < 1631851)
			result = 58;
		else if (value < 1697663)
			result = 59;
		else if (value < 1767494)
			result = 60;
		else if (value < 1841770)
			result = 61;
		else if (value < 1920982)
			result = 62;
		else if (value < 2005689)
			result = 63;
		else if (value < 2096543)
			result = 64;
		else if (value < 2194299)
			result = 65;
		else if (value < 2299842)
			result = 66;
		else if (value < 2414213)
			result = 67;
		else if (value < 2538647)
			result = 68;
		else if (value < 2674621)
			result = 69;
		else if (value < 2823912)
			result = 70;
		else if (value < 2988684)
			result = 71;
		else if (value < 3171594)
			result = 72;
		else if (value < 3375943)
			result = 73;
		else if (value < 3605883)
			result = 74;
		else if (value < 3866713)
			result = 75;
		else if (value < 4165299)
			result = 76;
		else if (value < 4510708)
			result = 77;
		else if (value < 4915157)
			result = 78;
		else if (value < 5395517)
			result = 79;
		else if (value < 5975764)
			result = 80;
		else if (value < 6691156)
			result = 81;
		else if (value < 7595754)
			result = 82;
		else if (value < 8776887)
			result = 83;
		else if (value < 10385397)
			result = 84;
		else if (value < 12706204)
			result = 85;
		else if (value < 16349855)
			result = 86;
		else if (value < 22903765)
			result = 87;
		else if (value < 38188459)
			result = 88;
		else if (value < 114588650)
			result = 89;
		else
			result = 90;// 不在0~30°以内。

		return result;
	}

	/**
	 * @param detaY
	 *            y的差值
	 * @param detaX
	 *            x的差值
	 * @return （0 到 360的角度值）
	 */
	public static int arctan(int detaY, int detaX) {
		if (detaX == 0) {
			if (detaY >= 0)
				return 90;
			else
				return -90;
		} else {
			int ratio = detaY * 1000000 / detaX;
			if (detaX > 0) {
				if (detaY == 0)
					return 0;
				else if (detaY > 0)
					return arctan(ratio);
				else
					return 360 - arctan(-ratio);
			} else {
				if (detaY == 0)
					return 180;
				else if (detaY > 0)
					return 180 - arctan(-ratio);
				else
					return 180 + arctan(ratio);
			}

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
	 * 画点
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public static void drawPoint(Graphics g, int x, int y) {
		g.drawLine(x, y, x, y);
		g.drawLine(x - 1, y, x - 1, y);
		g.drawLine(x + 1, y, x + 1, y);
		g.drawLine(x, y - 1, x, y - 1);
		g.drawLine(x, y + 1, x, y + 1);
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

	/**
	 * 加载地图数组
	 * 
	 * @param name
	 * @return
	 * @throws FileNotFoundException
	 */
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
	 * 裁剪字符串在一定显示区域内
	 * 
	 * @param width_show
	 *            字符串显示框的宽度
	 * @param font
	 *            字体
	 * @param str_target
	 *            目标字符串
	 * @return 得到的字符串数组
	 */
	public static String[] subStr(int width_show, Font font, String str_target) {
		String[] result = null;
		int w_font = font.charWidth('国');
		// int h_font = font.getHeight();
		int amountRow = width_show / w_font;
		// heightRow = h_font;

		int length = str_target.length();
		int row = length % amountRow == 0 ? length / amountRow : length
				/ amountRow + 1;
		result = new String[row];
		for (int i = 0; i < row; i++) {
			if (i == row - 1) {
				result[i] = str_target.substring(i * amountRow, length);
			} else {
				result[i] = str_target.substring(i * amountRow, i * amountRow
						+ amountRow);
			}
		}

		return result;
	}

	/**
	 * 在指定矩形区域内画字符串
	 * 
	 * @param x
	 *            矩形左上方x坐标
	 * @param y
	 *            矩形左上方y坐标
	 * @param w
	 *            矩形宽
	 * @param h
	 *            矩形高
	 * @param str_target
	 *            目标字符串
	 * @param font
	 *            画的字体
	 */
	public static void drawStrInRect(int x, int y, int w, int h,
			String str_target, Font font, Graphics g) {
		int w_font = font.charWidth('国');
		int h_font = font.getHeight();
		int amountRow = w / w_font;

		int length = str_target.length();
		int row = length % amountRow == 0 ? length / amountRow : length
				/ amountRow + 1;
		Font font_dft = g.getFont();
		g.setFont(font);
		for (int i = 0; i < row; i++) {
			if (i == row - 1) {
				g.drawString(str_target.substring(i * amountRow, length), x, y
						+ i * h_font, 0);
			} else {
				g.drawString(
						str_target.substring(i * amountRow, i * amountRow
								+ amountRow), x, y + i * h_font, 0);
			}
		}
		g.setFont(font_dft);
	}

	/**
	 * 两点间的距离的平方
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static int distance(int x1, int y1, int x2, int y2) {
		return ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            目标字符串
	 * @param c
	 *            截断点的字符串（不包括c）
	 * @param length
	 *            截取后的得到的字符串数组长度
	 * @return 得到的字符数组
	 */
	public static String[] subString(String str, char c) {
		Vector v_str = new Vector();
		int index = 0;
		int end = 0;
		int length_str = str.length();
		while (end < length_str) {
			end = str.indexOf(c, index);
			if (end == -1) {
				end = str.length();
			}
			v_str.addElement(str.substring(index, end));
			index = end + 1;
		}
		int length = v_str.size();
		String[] result_a_str = new String[length];
		for (int i = 0; i < length; i++) {
			result_a_str[i] = (String) v_str.elementAt(i);
		}

		return result_a_str;
	}

	/**
	 * 计算时间字符串
	 * 
	 * @param ds
	 *            分秒数
	 * @param digit
	 *            是否补位为两位
	 * @return
	 */
	public static String computeTimeStr(int ds_tag, boolean digit) {
		StringBuffer sb = new StringBuffer();
		int ds = (ds_tag % 10) * 10;
		int s_inital = ds_tag / 10;
		int s = s_inital % 60;
		int min = s_inital / 60;
		if (digit && min < 10)
			sb.append(0);
		sb.append(min);
		sb.append(":");
		if (digit && s < 10)
			sb.append(0);
		sb.append(s);
		sb.append(":");
		if (digit && ds < 10)
			sb.append(0);
		sb.append(ds);

		return sb.toString();
	}

	/**
	 * 两个矩形碰撞检测
	 * 
	 * @param x1
	 *            矩形1的左上角x坐标
	 * @param y1
	 *            矩形1的左上角y坐标
	 * @param w1
	 *            矩形1的宽
	 * @param h1
	 *            矩形1的高
	 * @param x2
	 *            矩形2的左上角x坐标
	 * @param y2
	 *            矩形2的左上角y坐标
	 * @param w2
	 *            矩形2的宽
	 * @param h2
	 *            矩形2的高
	 * @return false没有相交，true相交
	 */
	public static boolean rectInsect(int x1, int y1, int w1, int h1, int x2,
			int y2, int w2, int h2) {
		if (x2 - x1 > w1)
			return false;
		if (x1 - x2 > w2)
			return false;
		if (y2 - y1 > h1)
			return false;
		if (y1 - y2 > h2)
			return false;
		return true;
	}

	/**
	 * 矩形碰撞检测
	 * 
	 * @param rect1
	 *            矩形一
	 * @param rect2
	 *            矩形二
	 * @return false没有相交，true相交
	 */
	public static boolean rectInsect(Rect rect1, Rect rect2) {
		return rectInsect(rect1.x, rect1.y, rect1.w, rect1.h, rect2.x, rect2.y,
				rect2.w, rect2.h);
	}

	/**
	 * 矩形与圆碰撞检测
	 * 
	 * @param circle
	 *            圆
	 * @param rect
	 *            矩形
	 * @return 相交返回true，否则返回false；
	 */
	public static boolean ciclRectInst(Circle circle, Rect rect) {
		if (circle.typeDetect == Circle.IN_DETECT) {
			if (distance(rect.x, rect.y, circle.x, circle.y) <= circle.r
					* circle.r)
				return true;
			if (distance(rect.x + rect.w, rect.y, circle.x, circle.y) <= circle.r
					* circle.r)
				return true;
			if (distance(rect.x, rect.y + rect.h, circle.x, circle.y) <= circle.r
					* circle.r)
				return true;
			if (distance(rect.x + rect.w, rect.y + rect.h, circle.x, circle.y) <= circle.r
					* circle.r)
				return true;
		} else if (circle.typeDetect == Circle.OUT_DETECT
				&& distance(rect.x + (rect.w >> 1), rect.y + (rect.h >> 1), circle.x, circle.y) < circle.r_range * circle.r_range
				) {
			if(circle.angleDtctSt > circle.angleDtctEnd) {
				if(arctan(rect.y - circle.y, rect.x - circle.x) > circle.angleDtctSt
						|| arctan(rect.y - circle.y, rect.x - circle.x) < circle.angleDtctEnd) {
					if (distance(rect.x, rect.y, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
					if (distance(rect.x + rect.w, rect.y, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
					if (distance(rect.x, rect.y + rect.h, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
					if (distance(rect.x + rect.w, rect.y + rect.h, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
				} 
			} else {
				if(arctan(rect.y - circle.y, rect.x - circle.x) > circle.angleDtctSt
						&& arctan(rect.y - circle.y, rect.x - circle.x) < circle.angleDtctEnd) {
					if (distance(rect.x, rect.y, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
					if (distance(rect.x + rect.w, rect.y, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
					if (distance(rect.x, rect.y + rect.h, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
					if (distance(rect.x + rect.w, rect.y + rect.h, circle.x, circle.y) > circle.r
							* circle.r)
						return true;
				} 
			}
			// System.out.println("左上角到圆心的距离的平方：" + distance(rect.x, rect.y,
			// circle.x, circle.y));
			// System.out.println("半径的平方：" + circle.r * circle.r);
		}
		return false;
	}

	/**
	 * 自由矩形与圆的碰撞
	 * 
	 * @param circle
	 * @param rectF
	 * @return
	 */
	public static boolean ciclFreeRectInst(Circle circle, RectFree rectF) {
		if (circle.typeDetect == Circle.IN_DETECT) {
			if (distance((int) rectF.ver_a.x, (int) rectF.ver_a.y, circle.x,
					circle.y) <= circle.r * circle.r)
				return true;
			if (distance((int) rectF.ver_b.x, (int) rectF.ver_b.y, circle.x,
					circle.y) <= circle.r * circle.r)
				return true;
			if (distance((int) rectF.ver_c.x, (int) rectF.ver_c.y, circle.x,
					circle.y) <= circle.r * circle.r)
				return true;
			if (distance((int) rectF.ver_d.x, (int) rectF.ver_d.y, circle.x,
					circle.y) <= circle.r * circle.r)
				return true;
		} else if (circle.typeDetect == Circle.OUT_DETECT
				&& C.distance((int)rectF.center.x, (int)rectF.center.y, circle.x, circle.y) < circle.r_range * circle.r_range
				) {
			if (circle.angleDtctEnd < circle.angleDtctSt) {
				if(arctan((int) rectF.center.y - circle.y, (int) rectF.center.x
						- circle.x) > circle.angleDtctSt
				|| arctan((int) rectF.center.y - circle.y, (int) rectF.center.x
						- circle.x) < circle.angleDtctEnd) {
					if (distance((int) rectF.ver_a.x, (int) rectF.ver_a.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
					if (distance((int) rectF.ver_b.x, (int) rectF.ver_b.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
					if (distance((int) rectF.ver_c.x, (int) rectF.ver_c.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
					if (distance((int) rectF.ver_d.x, (int) rectF.ver_d.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
				}
				
			} else {
				if(arctan((int) rectF.center.y - circle.y, (int) rectF.center.x
						- circle.x) > circle.angleDtctSt
				&& arctan((int) rectF.center.y - circle.y, (int) rectF.center.x
						- circle.x) < circle.angleDtctEnd) {
					if (distance((int) rectF.ver_a.x, (int) rectF.ver_a.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
					if (distance((int) rectF.ver_b.x, (int) rectF.ver_b.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
					if (distance((int) rectF.ver_c.x, (int) rectF.ver_c.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
					if (distance((int) rectF.ver_d.x, (int) rectF.ver_d.y, circle.x,
							circle.y) > circle.r * circle.r)
						return true;
				}
			}
		} 
		return false;
	}

	/**
	 * 两个自由矩形的碰撞检测 sat（分离轴定律）方法
	 * 
	 * @param rectFree_a
	 *            自由矩形a
	 * @param rectFree_b
	 *            自由矩形b
	 * @return
	 */
	public static boolean freeRectIsct(RectFree rectFree_a, RectFree rectFree_b) {
		Vector2[] axis_proj = new Vector2[4];// 4个投影轴
		axis_proj[0] = new Vector2(rectFree_a.ver_d.x - rectFree_a.ver_a.x,
				rectFree_a.ver_d.y - rectFree_a.ver_a.y);
		axis_proj[1] = new Vector2(rectFree_a.ver_a.x - rectFree_a.ver_b.x,
				rectFree_a.ver_a.y - rectFree_a.ver_b.y);
		axis_proj[2] = new Vector2(rectFree_b.ver_d.x - rectFree_b.ver_a.x,
				rectFree_b.ver_d.y - rectFree_b.ver_a.y);
		axis_proj[3] = new Vector2(rectFree_b.ver_a.x - rectFree_b.ver_b.x,
				rectFree_b.ver_a.y - rectFree_b.ver_b.y);

		// 最大最小的投影标量
		float scalar_max_a = 0;
		float scalar_min_a = 0;
		float scalar_max_b = 0;
		float scalar_min_b = 0;

		Vector2 v_translate = null;
		for (int i = 0; i < 4; i++) {
			if (i == 0)
				v_translate = rectFree_a.ver_a;
			else if (i == 1)
				v_translate = rectFree_a.ver_b;
			else if (i == 2)
				v_translate = rectFree_b.ver_a;
			else
				v_translate = rectFree_b.ver_b;

			Vector2[] vector_proj_b = new Vector2[4];
			vector_proj_b[0] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_b.ver_a.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			vector_proj_b[1] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_b.ver_b.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			vector_proj_b[2] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_b.ver_c.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			vector_proj_b[3] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_b.ver_d.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			float[] scalar_proj_b = new float[4];// 各个顶点投影的标量值
			for (int j = 0; j < 4; j++) {
				scalar_proj_b[j] = vector_proj_b[j].dotProduct(axis_proj[i]);
			}
			scalar_max_b = scalar_proj_b[0];
			scalar_min_b = scalar_proj_b[0];
			for (int j = 1; j < 4; j++) {
				if (scalar_proj_b[j] < scalar_min_b)
					scalar_min_b = scalar_proj_b[j];
				if (scalar_proj_b[j] > scalar_max_b)
					scalar_max_b = scalar_proj_b[j];
			}

			Vector2[] vector_proj_a = new Vector2[4];
			vector_proj_a[0] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_a.ver_a.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			vector_proj_a[1] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_a.ver_b.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			vector_proj_a[2] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_a.ver_c.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			vector_proj_a[3] = axis_proj[i].mult(axis_proj[i]
					.dotProduct(rectFree_a.ver_d.add(v_translate.negative()))
					/ axis_proj[i].dotProduct(axis_proj[i]));
			float[] scalar_proj_a = new float[4];
			for (int j = 0; j < 4; j++) {
				scalar_proj_a[j] = vector_proj_a[j].dotProduct(axis_proj[i]);
			}
			scalar_max_a = scalar_proj_a[0];
			scalar_min_a = scalar_proj_a[0];
			for (int j = 1; j < 4; j++) {
				if (scalar_proj_a[j] < scalar_min_a)
					scalar_min_a = scalar_proj_a[j];
				if (scalar_proj_a[j] > scalar_max_a)
					scalar_max_a = scalar_proj_a[j];
			}

			if (scalar_min_a > scalar_max_b || scalar_max_a < scalar_min_b)
				return false;
		}

		return true;
	}

	/**
	 * 自由矩形与常规矩形的碰撞
	 * 
	 * @param rectFree
	 *            自由矩形
	 * @param rect
	 *            常规矩形
	 * @return
	 */
	public static boolean freeRectIsct(RectFree rectFree, Rect rect) {
		RectFree rf = new RectFree(rect.w, rect.h, new Vector2(rect.x + rect.w
				/ 2, rect.y + rect.h / 2), 0);
		// RectFree rf = new RectFree(new Vector2(rect.x, rect.y), new Vector2(
		// rect.x + rect.w, rect.y), new Vector2(rect.x + rect.w, rect.y
		// + rect.h), new Vector2(rect.x, rect.y + rect.h), new Vector2(
		// rect.x + rect.w / 2, rect.y + rect.h / 2));

		return freeRectIsct(rectFree, rf);
		// float x_max_rectFree = rectFree.ver_a.x;
		// float x_min_rectFree = rectFree.ver_a.x;
		// float y_max_rectFree = rectFree.ver_a.y;
		// float y_min_rectFree = rectFree.ver_a.y;
		//
		// if (rectFree.ver_b.x > x_max_rectFree) {
		// x_max_rectFree = rectFree.ver_b.x;
		// }
		// if (rectFree.ver_c.x > x_max_rectFree) {
		// x_max_rectFree = rectFree.ver_c.x;
		// }
		// if (rectFree.ver_d.x > x_max_rectFree) {
		// x_max_rectFree = rectFree.ver_d.x;
		// }
		//
		// if (rectFree.ver_b.x < x_min_rectFree) {
		// x_min_rectFree = rectFree.ver_b.x;
		// }
		// if (rectFree.ver_c.x < x_min_rectFree) {
		// x_min_rectFree = rectFree.ver_c.x;
		// }
		// if (rectFree.ver_d.x < x_min_rectFree) {
		// x_min_rectFree = rectFree.ver_d.x;
		// }
		//
		// if (rectFree.ver_b.y > y_max_rectFree) {
		// y_max_rectFree = rectFree.ver_b.y;
		// }
		// if (rectFree.ver_c.y > y_max_rectFree) {
		// y_max_rectFree = rectFree.ver_c.y;
		// }
		// if (rectFree.ver_d.y > y_max_rectFree) {
		// y_max_rectFree = rectFree.ver_d.y;
		// }
		//
		// if (rectFree.ver_b.y < y_min_rectFree) {
		// y_min_rectFree = rectFree.ver_b.y;
		// }
		// if (rectFree.ver_c.y < y_min_rectFree) {
		// y_min_rectFree = rectFree.ver_c.y;
		// }
		// if (rectFree.ver_d.y < y_min_rectFree) {
		// y_min_rectFree = rectFree.ver_d.y;
		// }
		//
		// if (x_max_rectFree < rect.x || x_min_rectFree > rect.x + rect.w
		// || y_max_rectFree < rect.y || y_min_rectFree > rect.y + rect.h)
		// return false;
		// else
		// return true;
	}

	/**
	 * 点是否在自由矩形内部
	 * 
	 * @param point
	 *            点
	 * @param rectFree
	 *            自由矩形
	 * @return
	 */
	public static boolean pointInFR(Vector2 point, RectFree rectFree) {
		Vector2[] axis_proj = new Vector2[2];// 2个投影轴
		axis_proj[0] = new Vector2(rectFree.ver_d.x - rectFree.ver_a.x,
				rectFree.ver_d.y - rectFree.ver_a.y);
		axis_proj[1] = new Vector2(rectFree.ver_a.x - rectFree.ver_b.x,
				rectFree.ver_a.y - rectFree.ver_b.y);

		Vector2 point_proj_1 = axis_proj[0].mult(axis_proj[0].dotProduct(point
				.add(rectFree.ver_a.negative()))
				/ axis_proj[0].dotProduct(axis_proj[0]));
		float scalar_proj = point_proj_1.dotProduct(axis_proj[0]);
		float scalar_d = axis_proj[0].dotProduct(axis_proj[0]);
		if (scalar_proj < 0 || scalar_proj > scalar_d)
			return false;

		Vector2 point_proj_2 = axis_proj[1].mult(axis_proj[1].dotProduct(point
				.add(rectFree.ver_b.negative()))
				/ axis_proj[1].dotProduct(axis_proj[1]));
		scalar_proj = point_proj_2.dotProduct(axis_proj[1]);
		scalar_d = axis_proj[1].dotProduct(axis_proj[1]);
		if (scalar_proj < 0 || scalar_proj > scalar_d)
			return false;
		return true;
	}

	/**
	 * 使用经验计算等级（以2的指数倍形式）
	 * 
	 * @param experience
	 *            经验
	 * @param base
	 *            倍数的基准数（如base = 100，第一级到第二级需要100exp,第二级到第三季则需要200exp）
	 * @return 得到的等级
	 */
	public static int computeLevel(int experience, int base) {
		int level = 1;
		while ((base << 1) < experience) {
			level++;
		}
		return level;
	}

	// 用户名字解码
	private static String deUserName(String de) {
		String deName = "";
		deName = de.replace('_', '+');
		deName = deName.replace('-', '/');
		return deName;
	}

	/**
	 * 字符串base64解码
	 * @param de
	 * @return
	 */
	public static String deUserName_base64(String de) {
		String temp = deUserName(de);
//		try {
//			temp = new String(mBase64.decode(temp), "GB2312");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		temp = new String(mBase64.decode(temp));

		return temp;

	}

	private static String enUseName(String en) {
		String enName = ""; // +替换成_ , /替换成-
		enName = en.replace('+', '_');
		enName = enName.replace('/', '-');
		return enName;
	}

	/**
	 * 字符串base64编码
	 * @param en
	 * @return
	 */
	public static String enUseName_base64(String en) {
		String enName = "";
		String temp = new String(mBase64.encode(en.getBytes()));
		enName = enUseName(temp);
		return enName;
	}


}
