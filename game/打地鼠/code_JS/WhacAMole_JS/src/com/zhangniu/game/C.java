package com.zhangniu.game;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class C {

	/**
	 * 返回
	 */
	public static C cc=new C();
	public static Image[] loadingImage=new Image[20];
	
	/**
	 * 随机
	 */
	public static Random random = new Random();
	
	/**
	 * 中奖名单
	 */
	public static Vector vector_PrizeName=new Vector();
	
	/**
	 * 排行榜----总积分
	 */
	public static Vector vector_Rank_Total=new Vector();
	
	/**
	 * 排行榜----关卡模式
	 */
	public static Vector vector_Rank_Level = new Vector();
	
	/**
	 * 排行榜----计时模式
	 */
	public static Vector vector_Rank_Time = new Vector();
	
	/**
	 * 排行榜----挑战模式
	 */
	public static Vector vector_Rank_Challenge = new Vector();
	
	/**
	 * 是否接受按钮事件
	 */
	public static boolean receiveKeyPressed;
	
	/**
	 * 游戏总得分
	 */
	public static int is_Activity;					//是否活动期
	
	/**
	 * 用户是否激活
	 */
	public static int activate;						//是否激活
	
	/**
	 * 总分
	 */
	public static int total_Score;
	
	/**
	 * 闯关最高分
	 */
	public static int level_Score_history;					//闯关最高分___历史
	
	/**
	 * 挑战最高分
	 */
	public static int challenge_Score_history;				//挑战最高分___历史
	
	/**
	 * 计时最高分
	 */
	public static int time_Score_history;					//计时最高分___历史
	
	/**
	 * 闯关最高分
	 */
	public static int level_Score;					//闯关最高分
	
	/**
	 * 挑战最高分
	 */
	public static int challenge_Score;				//挑战最高分
	
	/**
	 * 计时最高分
	 */
	public static int time_Score;					//计时最高分
	
	/**
	 * 金锤个数
	 */
	public static int goldHammer_Count;				//金锤个数
	
	/**
	 * 用户排名
	 */
	public static int rank_total;
	public static int rank_totalScore;
	
	public static int rank_Level;
	public static int rank_LevelScore;
	
	public static int rank_Time;
	public static int rank_TimelScore;
	
	public static int rank_Challenge;
	public static int rank_ChallengeScore;
	
	
	public static int BuyHowGoldHAMMER;				//买多少金锤
	
	/**
	 * 游戏关卡
	 */
	public static int level;
	
	/**
	 * 服务器返回的滚动字幕
	 */
	public static String scroll_bar_Str="读取中.....";
	
	// 屏幕宽高
	public static int screenwidth;
	public static int screenheight;
	
	//从服务器返回的值
//	public static String serverBackStr;

	// 提示框类型(0.第N关  1.结果  2.购买时间)
	public static int alertType;
	public static int alertBeforeType;
	
	// 结果(true.赢  false.输 )
	public static boolean passed;
	
	// 游戏速度
	public static int[] threadSleepTime =  { 
		75, 75, 75, 75, 75 ,75,75,75,75,75};
	
	/**
	 * GoldCount
	 */
	public final static int[]GOLDEGGS_ARRAY={
		1000,6000,7000,5000,3000,7000,4000,8000,10000,2000,
	};	//通关了
	
	// 当前关时间
	public static int nowCountDown;

	/****************************************************************************/
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
	public final static int KEY_BACK = -31;//正式平台应该是这个
	public final static int KEY_BACK_ZX = -7;//正式平台中兴的盒子应该是这个
//	public final static int KEY_BACK = -6;//在模拟器上，试试

	
	/**
	 * 小的字体
	 */
	public final static Font FONT_SMALL=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	
	/**
	 * 中等字体
	 */
	public final static Font FONT_MEDIUM=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
	
	/**
	 * 大字体
	 */
	public final static Font FONT_LARGE=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);
	
	/**
	 * 大又粗字体
	 */
	public final static Font FONT_BOLD_LARGE=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
	public final static int LARGE_BOLD_STRINGWIDTH= FONT_BOLD_LARGE.stringWidth("国");
	/**
	 * 中字体
	 */
	public final static Font FONT_BOLD_MEDIUM=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
	/****************************************************************内存检测块区域*/

    /**
     * 载入图片资源
     */
    public static void readImageArray(Image [] inImage,String sourceAddres) {
        try {
        	// 载入图片资源
            InputStream is = cc.getClass().getResourceAsStream(sourceAddres);
            DataInputStream dis = new DataInputStream(is);
            int imageIndex = 0;
            int length = 0;
            byte [] buffer = null;

            while (dis.available() > 0) {
                length = dis.readInt();
//                C.out(""+length+sourceAddres);
                buffer = new byte[length];
                dis.read(buffer);
                inImage[imageIndex++] = Image.createImage(buffer, 0, length);
                buffer = null;
                System.gc();
            }

            dis.close();
            is.close();
            dis = null;
            is = null;
            System.gc();

        } catch(IOException e) {
            System.out.println("Error reading resources");
        }
    }
	
    /**
     * 取得图片资源
     * @param index 图片资源索引
     * @return 图片
     */
    public static Image getImage(int index) {
        return loadingImage[index];
    }
    
    /**
     * 获得分割的图片
     */
    public static Image[] cut_Image(int imgCount){
    	Image [] a = new Image[imgCount];
    	
    	return a;
    }

    /**
     * 释放图片资源
     * @param index 图片资源索引
     * @return 图片
     */
    public static void freeImage(int index) {
    	loadingImage[index] = null;
    }
	
	/**
	 * 坐标为0,0,0的画图方法
	 */
	public static void DrawImage_LEFTTOP(Image i,Graphics g){
		g.drawImage(i, 0, 0, 0);
	}
	
	/**
	 * 从标为自己定的画图方法 （左上方）
	 */
	public static void DrawImage_XY_LEFTTOP(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, 0);
	}
	
	/**
	 * 从标为自己定的画图方法 （左下方）
	 */
	public static void DrawImage_XY_LEFTBUTTOM(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, Graphics.LEFT|Graphics.BOTTOM);
	}
	
	/**
	 * 从标为自己定的画图方法 (左下方)
	 */
	public static void DrawImage_XY_LEFTBOTTOM(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, Graphics.LEFT|Graphics.BOTTOM);
	}
	
	/**
	 * 坐标为自己定的，以图片的中心点为画点的画图方法
	 */
	public static void DrawImage_VH(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, Graphics.VCENTER|Graphics.HCENTER);
	}
	
	/**
	 * 画数字图片
	 * @param imagea_number
	 * @param x
	 * @param y
	 * @param offer
	 * @param canvas
	 */
	public static void DrawNumber_XY_RIGHTTOP(Image [] imagea_number,int key,int x,int y, int offer,Graphics canvas){
		String strkey = String.valueOf(key);
		byte strlenght = (byte)strkey.length();//
		byte[] bytea_str = new byte[strlenght];//得到传入int型的长度
		for(int a =0;a<strlenght;a++){
			bytea_str[a]=(byte)Integer.parseInt(strkey.substring(a, a+1));
		}
		for(int a =0;a<strlenght;a++){
			canvas.drawImage(imagea_number[bytea_str[(strlenght-a-1)]], x-a*offer, y, Graphics.LEFT|Graphics.TOP);
		}
	}
	
	/**
	 * system.out print ln
	 */
	public static void out(String string){
		System.out.println(string);
	}
	
	/**
	 * 内存已经使用Show
	 */
	public static void MemoryUsedShow() {
		long nowused;
		nowused = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		System.out.println("目前已经使用" + nowused);
	}

	/**
	 * 获取图片资源 统一处理
	 */
	public static Image GetImageSource(String path) {
		Image getimg = null;
		try {
			getimg = Image.createImage(path);
		} catch (IOException e) {
			System.out.println("读取图片失败" + path);
		}
		return getimg;
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
		g.setClip(0, 0, 640, 530);
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
	/****************************************************************************/
}
