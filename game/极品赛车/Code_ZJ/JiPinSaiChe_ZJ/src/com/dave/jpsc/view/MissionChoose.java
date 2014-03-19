package com.dave.jpsc.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

/**
 * @author Administrator �ؿ�ѡ�����
 */
/**
 * @author Dave
 *
 */
public class MissionChoose implements Showable {

	/**
	 * ���ڽ����µĹؿ�
	 */
	public static boolean unlocking;

	private CanvasControl canvasControl;

	public Image[][] a_img_icon;// �ؿ�ͼ��
	public Image[] a_img_star;// ���ǣ�������ǰ����
	public Image img_back;
	public Image img_param_back;
	public Image img_lock;
	public Image[] a_img_select;

	/**
	 * ѡ��ؿ��±�ֵ
	 */
	private int index;

	/**
	 * ѡ��ָ�붯��֡
	 */
	private int frame_select;

	/**
	 * �ؿ����ܿ�����
	 */
	private int x_param, y_param;

	/**
	 * �ؿ�ͼ�������
	 */
	private final int[][] iconCoord = { { 205, 160 },// ѩ��
			{ 269, 216 },// �Ϻ�
			{ 298, 313 },// ��̩������
			{ 352, 387 },// ��˹��ͷ
			{ 469, 444 },// ��˹̹����
			{ 470, 329 },// ����
			{ 554, 256 },// �������ع�԰
			{ 541, 159 },// ��¹
			{ 463, 211 },// Ħ�ɸ�
			{ 396, 141 },// ��ʯ
			{ 565, 97 },// Ŧ������
			{ 477, 39 },// �¼���
			{ 352, 62 },// ������˹
			{ 198, 67 },// ˹��
			{ 80, 71 },// ����
			{ 99, 145 },// Ӣ������˹
	};
	
	/**
	 * ·�ߵĵ�����
	 */
	private final int[][] route = {
			{//1��2��
				193, 177,
				197, 178,
				201, 180,
				204, 182,
				206, 184,
				208, 187,
				210, 190,
				212, 194,
				214, 199,
				215, 204,
			},
			{//2��3��
				227, 240,
				229, 248,
				231, 257,
				231, 265,
				228, 273,
				225, 281,
				228, 289,
				230, 297,
				233, 305,
				237, 314,
			},
			{//3��4��
				259, 328,
				268, 334,
				276, 340,
				283, 345,
				288, 350,
				292, 356,
				295, 361,
				298, 367,
				302, 373,
				305, 379,
			},
			{//4��5��
				326, 407,
				334, 411,
				342, 416,
				355, 421,
				368, 425,
				381, 430,
				389, 435,
				395, 440,
				401, 445,
				407, 450,
			},
			{//5��6��
				423, 421,
				426, 415,
				429, 408,
				428, 398,
				426, 390,
				423, 381,
				422, 372,
				423, 364,
				425, 355,
				427, 347,
			},
			{//6��7��
				443, 303,
				444, 301,
				450, 298,
				458, 295,
				466, 294,
				474, 291,
				482, 288,
				491, 285,
				492, 284,
				492, 283,
			},
			{//7��8��
				504, 228,
				508, 222,
				512, 217,
				515, 210,
				517, 204,
				519, 199,
				519, 194,
				517, 189,
				515, 185,
				513, 180,
			},
			{//8��9��
				501, 175,
				495, 178,
				489, 181,
				483, 186,
				480, 194,
				477, 200,
				473, 208,
				470, 214,
				464, 217,
				456, 219,
			},
			{//9��10��
				410, 210,
				405, 203,
				400, 197,
				398, 192,
				396, 187,
				393, 182,
				390, 177,
				386, 172,
				382, 168,
				380, 163,
			},
			{//10��11��
				392, 121,
				392, 116,
				403, 114,
				418, 116,
				433, 120,
				448, 124,
				477, 128,
				486, 122,
				495, 116,
				503, 109,
			},
			{//11��12��
				515, 79,
				510, 74,
				505, 69,
				500, 64,
				490, 59,
				485, 56,
				480, 54,
				474, 50,
				466, 48,
				459, 46,
			},
			{//12��13��
				422, 47,
				401, 49,
				400, 51,
				390, 54,
				380, 57,
				370, 59,
				360, 61,
				350, 63,
				340, 65,
				330, 66,
			},
			{//13��14��
				295, 69,
				294, 73,
				282, 77,
				270, 81,
				258, 79,
				246, 77,
				234, 75,
				222, 73,
				210, 71,
				198, 68,
			},
			{//14��15��
				152, 69,
				146, 69,
				137, 70,
				128, 70,
				119, 71,
				110, 71,
				101, 72,
				92, 73,
				83, 74,
				74, 75,
			},
			{//15��16��
				60, 87,
				62, 92,
				64, 98,
				66, 104,
				64, 110,
				60, 116,
				56, 121,
				54, 126,
				51, 131,
				51, 134,
			},
	};
	
	/**
	 * ����ͼ����ʾ������
	 */
	private int num_show_unlock;
	
	/**
	 * �ؿ���Ϣ�������ҷֱ�����ؿ���Ϣ��Ȧ���������������������ͣ�����������Ѷȣ��Ǽ����������������¼��
	 */
	private final String[][] missionInfo = {
			{"ѩ�������������������׶���¡��Լ65�������������ʩ�ǳ���ɫ", "1", "2", "������", "��õ�һ������200s���������", "1", "30", "02:00:00"},
			{"�Ϻ�����������������һ������ʽ��������֮һ��", "1", "2", "������", "��õ�һ������200s���������", "1", "30", "01:50:00"},
			{"��̩��������������1991�꣬�Ǳ������Ϲ��ϵ��������������ܵ�", "1", "2", "������", "��õ�һ������200s���������", "1", "30", "01:40:00"},
			{"��˹��ͷ��������һ����ʱ���������ɸ�����ͽ��յ����������", "2", "2", "������", "��õ�һ������180s���������", "2", "40", "01:30:00"},
			{"��˹̹��������������ʱ��������", "1", "2", "������", "��õ�һ������180s���������", "2", "40", "01:40:00"},
			{"���ֹ���������λ�ڰ��ֵ�����������ٰ����Ҫ���°���һ������ʽ�������ִ�������������ʽ�����ȣ�", "2", "2", "������", "��õ�һ������180s���������", "2", "40", "01:30:00"},
			{"�������ع�԰�������ڽֵ������������������������İ������ع�԰�����岼���൱������", "2", "3", "������", "��õ�һ������160s���������", "3", "60", "01:30:00"},
			{"��¹�����Ի�ϵĸ��ٺ͵�������������ٶ��ĸ���Ҫ��ǿ������������", "2", "3", "������", "��õ�һ������160s���������", "3", "60", "01:30:00"},
			{"Ħ�ɸ���������Ħ�ɸ繫����һ���ֵ�����������������ÿ�����µ�ĳ����ĩ��ӭ��F1������ͬʱ������F2��F3000��������GP2����Ϊ�泡����", "2", "3", "������", "��õ�һ������160s���������", "3", "60", "01:25:00"},
			{"��ʯ����ӵ�кܳ���ֱ�ߵ�����ٵ�������ⲻ���������������ܣ������ڿ����ų��ּ�ʻ�����͵�ʶ�ļ��޶ȣ�", "2", "4", "������", "��õ�һ������150s���������", "4", "70", "01:25:00"},
			{"Ŧ����������λ��Ư���Ľ�����ӵ���Ӵ�ɳ�ص�Ŧ�����������Գ�����˵�Ǹ����ɶ�õļȿ����ְ�ȫ��������", "2", "4", "������", "��õ�һ������150s���������", "4", "70", "01:15:00"},
			{"�¼�������Ҳ�����޵�һ��F1�ֵ�������", "2", "4", "������", "��õ�һ������150s���������", "4", "70", "01:15:00"},
			{"������˹����λ�ڲ�����˹������ʮ��Ӣ��Ĳ�����˹������һ���൱�ִ�����F1������", "3", "5", "������", "��õ�һ������150s���������", "5", "80", "01:10:00"},
			{"˹������λ�ڱ���ʱ�����ģ���1924���һ�α����ã��Դ�1985�꿪ʼ���б���ʱF1������", "3", "5", "������", "��õ�һ������150s���������", "5", "80", "01:10:00"},
			{"����������1922�꽨�ɣ�����λ������������30�������һ������ʽ��ʷ�������������������F1����������أ�", "3", "5", "������", "��õ�һ������150s���������", "5", "80", "01:10:00"},
			{"Ӣ������˹��������ʥ����������Լ25���λ�ڿ���˹-��˹���������Դ�1991�꿪ʼ������Ϊ�˰��������ľٰ�أ�", "3", "5", "������", "��õ�һ������150s���������", "5", "80", "01:10:00"},
	};
	
	public final static String[] MAP_NAME = {
		"ѩ������",//0
		"�Ϻ�����������",//1
		"��̩��������",//2
		"��˹��ͷ��",//3
		"��˹̹��������",//4
		"���ֹ�������",//5
		"�������ع�԰����",//6
		"��¹����",//7
		"Ħ�ɸ�����",//8
		"��ʯ����",//9
		"Ŧ����������",//10
		"�¼�������",//11
		"������˹����",//12
		"˹������",//13
		"��������",//14
		"Ӣ������˹����",//15
		"����һ",//16
		"������",//17
		"������",//18
		"������",//19
		"������",//20
		"������",//21
		"������",//22
		"������",//23
		"������",//24
		"��ս����һ",//25
		"��ս������",//26
		"��ս������",//27
		"��ս������",//28
		"��ս������",//29
		"��ս������",//30
		"��ս������",//31
		"��ս������",//32
		"��ս������",//33
		"��ս����ʮ",//34
		"��ս����ʮһ"//35
	};

	/**
	 * ������ǰ���ĵ�
	 */
	private int adv_unlock;
	
	/**
	 * Ƶ�ʿ���
	 */
	private int freq;

	public MissionChoose(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
		
		index = CanvasControl.missionPassed - 1;//Ĭ��ѡ�����Ĺؿ�
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		num_show_unlock = unlocking ? CanvasControl.missionPassed - 1 : CanvasControl.missionPassed;
		for (int i = 0; i < 16; i++) {
			if (i < num_show_unlock) {
				g.drawImage(a_img_icon[i][1], iconCoord[i][0], iconCoord[i][1],
						Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(img_lock,
						iconCoord[i][0] - a_img_icon[i][0].getWidth() / 2 + 30,
						iconCoord[i][1] + 15, Graphics.VCENTER
								| Graphics.HCENTER);
			}
		}
		
		showRoute(g);
		
		if(unlocking) {
			
		} else {
			g.drawImage(a_img_select[frame_select], iconCoord[index][0]
					- a_img_icon[index][0].getWidth() / 2 + 20,
					iconCoord[index][1], Graphics.VCENTER | Graphics.HCENTER);
		}

		if(!unlocking) {
			g.drawImage(img_param_back, x_param, y_param, 0);
			showTitle(g);
			showMissionInfo(g);
		}
	}

	/**
	 * ��·��
	 * @param g
	 */
	private void showRoute(Graphics g) {
		g.setColor(0xffffff);
		
		if(unlocking) {//���ڽ���
			for (int i = 0; i < CanvasControl.missionPassed - 2; i++) {
				for (int j = 0; j < 10; j++) {
					C.drawPoint(g, route[i][2 * j], route[i][1 + 2 * j]);
				}
			}
			
			for (int i = 0; i < adv_unlock; i++) {
				C.drawPoint(g, route[CanvasControl.missionPassed - 2][2 * i], route[CanvasControl.missionPassed - 2][1 + 2 * i]);
			}
		} else {
			for (int i = 0; i < (CanvasControl.missionPassed - 1); i++) {
				for (int j = 0; j < 10; j++) {
					C.drawPoint(g, route[i][2 * j], route[i][1 + 2 * j]);
				}
			}
			
		}
	}

	/**
	 * �ؿ���Ϣ��ı�������
	 * @param g
	 */
	private void showTitle(Graphics g) {
		g.setColor(255, 80, 80);
		g.setFont(C.FONT_MEDIUM_BOLD);
		g.drawString("��������", x_param + 8, y_param + 35, 0);
		g.drawString("�����������", x_param + 8, y_param + 120, 0);
		g.drawString("����", x_param + 8, y_param + 260, 0);
		g.drawString("�����¼��" + missionInfo[index][7], x_param + 8, y_param + 320, 0);
	}
	
	/**
	 * �ؿ���Ϣ����
	 * @param g
	 */
	private void showMissionInfo(Graphics g) {
		g.setColor(0xffbc38);
		C.drawStrInRect(x_param + 8, y_param + 55, 300, 70, missionInfo[index][0], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 140, 300, 70, "Ȧ����" + missionInfo[index][1], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 140, y_param + 140, 300, 70, "����������" + missionInfo[index][2], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 160, 300, 70, "�������ͣ�" + missionInfo[index][3], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 180, 300, 70, "���������" + missionInfo[index][4], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 220, 300, 70, "�Ѷȣ�", C.FONT_MEDIUM_BOLD, g);
		for (int i = 0; i < 5; i++) {
			g.drawImage(a_img_star[1], x_param + 50 + i * 19, y_param + 228, Graphics.VCENTER | Graphics.HCENTER);
		}
		int count = Integer.parseInt(missionInfo[index][5]);
		for (int i = 0; i < count; i++) {
			g.drawImage(a_img_star[0], x_param + 50 + i * 19, y_param + 228, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		C.drawStrInRect(x_param + 8, y_param + 280, 300, 70, "���飺" + missionInfo[index][6], C.FONT_MEDIUM_BOLD, g);
	}
	
	public void loadResource() {
		try {
			a_img_icon = new Image[16][2];
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 2; j++) {
					a_img_icon[i][j] = Image.createImage("/missionChoose/icon_"
							+ i + "_" + j + ".png");
				}
			}

//			img_back = Image.createImage("/missionChoose/back.jpg");
			img_lock = Image.createImage("/missionChoose/lock.png");
			img_param_back = Image.createImage("/missionChoose/param_back.png");
			a_img_star = new Image[2];
			Image img_temp = Image.createImage("/missionChoose/star.png");
			for (int i = 0; i < 2; i++) {
				a_img_star[i] = Image.createImage(img_temp, i * 19, 0, 19, 17,
						0);
			}

			a_img_select = new Image[3];
			img_temp = Image.createImage("/missionChoose/select.png");
			for (int i = 0; i < 3; i++) {
				a_img_select[i] = Image.createImage(img_temp, i * 59, 0, 59,
						49, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resetParamPoint();

	}

	public void removeResource() {
		img_lock = null;
		img_param_back = null;

		for (int i = 0; i < a_img_star.length; i++) {
			a_img_star[i] = null;
		}
		a_img_star = null;

		for (int i = 0; i < a_img_select.length; i++) {
			a_img_select[i] = null;
		}
		a_img_select = null;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 2; j++) {
				a_img_icon[i][j] = null;
			}
		}
		a_img_icon = null;

		System.gc();
	}

	public void removeServerImage() {
		img_back = null;
		/*img_lock = null;
		img_param_back = null;

		for (int i = 0; i < a_img_star.length; i++) {
			a_img_star[i] = null;
		}
		a_img_star = null;

		for (int i = 0; i < a_img_select.length; i++) {
			a_img_select[i] = null;
		}
		a_img_select = null;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 2; j++) {
				a_img_icon[i][j] = null;
			}
		}
		a_img_icon = null;

		System.gc();*/
	}
	

	public void keyPressed(int keyCode) {
		canvasControl.playKeySound();
		if(unlocking)
			return;
		switch (keyCode) {
		case C.KEY_RIGHT:
		case C.KEY_DOWN:
			if (index == 15) {
				index = 0;
			} else {
				if (index < CanvasControl.missionPassed - 1)
					index++;
			}
			resetParamPoint();
			break;
		case C.KEY_LEFT:
		case C.KEY_UP:
			if (index == 0) {
				if (CanvasControl.missionPassed >= 16)
					index = 15;
			} else
				index--;

			resetParamPoint();
			break;
		case C.KEY_1:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 9));
			break;
		case C.KEY_FIRE:
			CanvasControl.mission = index + 1;
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 7));
			break;

		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Home(canvasControl));
			break;

		default:
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	/**
	 * ÿ���ƶ���궼���¹ؿ����ܿ�����
	 */
	public void resetParamPoint() {
		if (iconCoord[index][0] < 300)
			x_param = iconCoord[index][0] + 20;
		else
			x_param = iconCoord[index][0] - 340 - a_img_icon[index][0].getWidth() / 2;
		
		if(x_param < 10)
			x_param = 10;
		else if (x_param > 320) {
			x_param = 320;
		}
		
		if (iconCoord[index][1] < 250) {
			y_param = iconCoord[index][1] + 20;
		} else {
			y_param = iconCoord[index][1] - 380;
		}
		if(y_param < 20)
			y_param = 20;
		else if(y_param > 140)
			y_param = 140;
	}

	public void logic() {
		frame_select++;
		if (frame_select > 2) {
			frame_select = 0;
		}
		
		if(unlocking) {
			freq ++;
			if(freq > 5) {
				freq = 0;
				if(adv_unlock < 9) {
					adv_unlock ++;
				} else {
					unlocking = false;
					index = CanvasControl.missionPassed - 1;
					resetParamPoint();
				}
			}
		}
	}

	public void handleGoods(int goodsIndex) {
		
	}

}
