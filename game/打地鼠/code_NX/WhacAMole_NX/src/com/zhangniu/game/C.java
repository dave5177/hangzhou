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
	 * ����
	 */
	public static C cc=new C();
	public static Image[] loadingImage=new Image[20];
	
	/**
	 * ���
	 */
	public static Random random = new Random();
	
	/**
	 * �н�����
	 */
	public static Vector vector_PrizeName=new Vector();
	
	/**
	 * ���а�----�ܻ���
	 */
	public static Vector vector_Rank_Total=new Vector();
	
	/**
	 * ���а�----�ؿ�ģʽ
	 */
	public static Vector vector_Rank_Level = new Vector();
	
	/**
	 * ���а�----��ʱģʽ
	 */
	public static Vector vector_Rank_Time = new Vector();
	
	/**
	 * ���а�----��սģʽ
	 */
	public static Vector vector_Rank_Challenge = new Vector();
	
	/**
	 * �Ƿ���ܰ�ť�¼�
	 */
	public static boolean receiveKeyPressed;
	
	/**
	 * ��Ϸ�ܵ÷�
	 */
	public static int is_Activity;					//�Ƿ���
	
	/**
	 * �û��Ƿ񼤻�
	 */
	public static int activate;						//�Ƿ񼤻�
	
	/**
	 * �ܷ�
	 */
	public static int total_Score;
	
	/**
	 * ������߷�
	 */
	public static int level_Score_history;					//������߷�___��ʷ
	
	/**
	 * ��ս��߷�
	 */
	public static int challenge_Score_history;				//��ս��߷�___��ʷ
	
	/**
	 * ��ʱ��߷�
	 */
	public static int time_Score_history;					//��ʱ��߷�___��ʷ
	
	/**
	 * ������߷�
	 */
	public static int level_Score;					//������߷�
	
	/**
	 * ��ս��߷�
	 */
	public static int challenge_Score;				//��ս��߷�
	
	/**
	 * ��ʱ��߷�
	 */
	public static int time_Score;					//��ʱ��߷�
	
	/**
	 * �𴸸���
	 */
	public static int goldHammer_Count;				//�𴸸���
	
	/**
	 * �û�����
	 */
	public static int rank_total;
	public static int rank_totalScore;
	
	public static int rank_Level;
	public static int rank_LevelScore;
	
	public static int rank_Time;
	public static int rank_TimelScore;
	
	public static int rank_Challenge;
	public static int rank_ChallengeScore;
	
	
	public static int BuyHowGoldHAMMER;				//����ٽ�
	
	/**
	 * ��Ϸ�ؿ�
	 */
	public static int level;
	
	/**
	 * ���������صĹ�����Ļ
	 */
	public static String scroll_bar_Str="��ȡ��.....";
	
	// ��Ļ���
	public static int screenwidth;
	public static int screenheight;
	
	//�ӷ��������ص�ֵ
//	public static String serverBackStr;

	// ��ʾ������(0.��N��  1.���  2.����ʱ��)
	public static int alertType;
	public static int alertBeforeType;
	
	// ���(true.Ӯ  false.�� )
	public static boolean passed;
	
	// ��Ϸ�ٶ�
	public static int[] threadSleepTime =  { 
		75, 75, 75, 75, 75 ,75,75,75,75,75};
	
	/**
	 * GoldCount
	 */
	public final static int[]GOLDEGGS_ARRAY={
		1000,6000,7000,5000,3000,7000,4000,8000,10000,2000,
	};	//ͨ����
	
	// ��ǰ��ʱ��
	public static int nowCountDown;

	/****************************************************************************/
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
	public final static int KEY_BACK = -31;//��ʽƽ̨Ӧ�������
	public final static int KEY_BACK_ZX = -7;//��ʽƽ̨���˵ĺ���Ӧ�������
//	public final static int KEY_BACK = -6;//��ģ�����ϣ�����

	
	/**
	 * С������
	 */
	public final static Font FONT_SMALL=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	
	/**
	 * �е�����
	 */
	public final static Font FONT_MEDIUM=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
	
	/**
	 * ������
	 */
	public final static Font FONT_LARGE=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);
	
	/**
	 * ���ִ�����
	 */
	public final static Font FONT_BOLD_LARGE=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
	public final static int LARGE_BOLD_STRINGWIDTH= FONT_BOLD_LARGE.stringWidth("��");
	/**
	 * ������
	 */
	public final static Font FONT_BOLD_MEDIUM=
		Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
	/****************************************************************�ڴ��������*/

    /**
     * ����ͼƬ��Դ
     */
    public static void readImageArray(Image [] inImage,String sourceAddres) {
        try {
        	// ����ͼƬ��Դ
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
     * ȡ��ͼƬ��Դ
     * @param index ͼƬ��Դ����
     * @return ͼƬ
     */
    public static Image getImage(int index) {
        return loadingImage[index];
    }
    
    /**
     * ��÷ָ��ͼƬ
     */
    public static Image[] cut_Image(int imgCount){
    	Image [] a = new Image[imgCount];
    	
    	return a;
    }

    /**
     * �ͷ�ͼƬ��Դ
     * @param index ͼƬ��Դ����
     * @return ͼƬ
     */
    public static void freeImage(int index) {
    	loadingImage[index] = null;
    }
	
	/**
	 * ����Ϊ0,0,0�Ļ�ͼ����
	 */
	public static void DrawImage_LEFTTOP(Image i,Graphics g){
		g.drawImage(i, 0, 0, 0);
	}
	
	/**
	 * �ӱ�Ϊ�Լ����Ļ�ͼ���� �����Ϸ���
	 */
	public static void DrawImage_XY_LEFTTOP(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, 0);
	}
	
	/**
	 * �ӱ�Ϊ�Լ����Ļ�ͼ���� �����·���
	 */
	public static void DrawImage_XY_LEFTBUTTOM(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, Graphics.LEFT|Graphics.BOTTOM);
	}
	
	/**
	 * �ӱ�Ϊ�Լ����Ļ�ͼ���� (���·�)
	 */
	public static void DrawImage_XY_LEFTBOTTOM(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, Graphics.LEFT|Graphics.BOTTOM);
	}
	
	/**
	 * ����Ϊ�Լ����ģ���ͼƬ�����ĵ�Ϊ����Ļ�ͼ����
	 */
	public static void DrawImage_VH(Image i,int x,int y,Graphics g){
		g.drawImage(i, x, y, Graphics.VCENTER|Graphics.HCENTER);
	}
	
	/**
	 * ������ͼƬ
	 * @param imagea_number
	 * @param x
	 * @param y
	 * @param offer
	 * @param canvas
	 */
	public static void DrawNumber_XY_RIGHTTOP(Image [] imagea_number,int key,int x,int y, int offer,Graphics canvas){
		String strkey = String.valueOf(key);
		byte strlenght = (byte)strkey.length();//
		byte[] bytea_str = new byte[strlenght];//�õ�����int�͵ĳ���
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
	 * �ڴ��Ѿ�ʹ��Show
	 */
	public static void MemoryUsedShow() {
		long nowused;
		nowused = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		System.out.println("Ŀǰ�Ѿ�ʹ��" + nowused);
	}

	/**
	 * ��ȡͼƬ��Դ ͳһ����
	 */
	public static Image GetImageSource(String path) {
		Image getimg = null;
		try {
			getimg = Image.createImage(path);
		} catch (IOException e) {
			System.out.println("��ȡͼƬʧ��" + path);
		}
		return getimg;
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
		g.setClip(0, 0, 640, 530);
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
	/****************************************************************************/
}
