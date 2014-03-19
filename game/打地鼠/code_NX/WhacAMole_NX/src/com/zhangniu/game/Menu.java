package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dalin.nxiptv.prop.NX_IPTV_PORP_TOOL;
import com.zhangniu.update.ServerIptv;

public class Menu {

	private Screen s;

	private Image[] imagea_Menu;
	private Image[] imagea_SelectButton;

	private int select_Index;

	private int scrollBar_X = 358;

	private final short[] inta_BUTTON_POSITION = { 115, 120, 120, 198, 120,
			277, 123, 360, 126, 442, 130, 538 };

	public Menu(Screen screen) {
		s = screen;
	}

	public void menuInit() {

		imagea_Menu = new Image[4];
		imagea_Menu[0] = C.GetImageSource("/menu/background.png");// ��Ϸ������
		imagea_Menu[1] = C.GetImageSource("/menu/buttonback.png");// ��ť����ͼ

		imagea_Menu[2] = C.GetImageSource("/menu/selectback.png");// ѡ��ť��ͼ
		imagea_Menu[3] = C.GetImageSource("/menu/selectbutton.png");// ѡ����������

		imagea_SelectButton = new Image[6];
		for (int a = 0; a < 4; a++) {
			imagea_SelectButton[a] = Image.createImage(imagea_Menu[3], 0,
					52 * a, 163, 52, 0);
		}
		imagea_Menu[3] = null;
		System.gc();
	}

	public void removeAllSource() {
		for (int a = 0; a < 3; a++) {
			imagea_Menu[a] = null;
			imagea_SelectButton[a] = null;
			imagea_SelectButton[a + 3] = null;
		}
		imagea_Menu[3] = null;
		imagea_Menu = null;
		imagea_SelectButton = null;
		System.gc();
	}

	public void keyPressed(int key) {
		switch (key) {
		case C.KEY_BACK_ZX:
		case C.KEY_BACK:
		case C.KEY_0: {
			s.mid.exitGame();
		}
			break;
		case C.KEY_FIRE: {// ȷ�������л���Ϸ״̬��
			Screen.status = Screen.S_NULL;
			removeAllSource();
			System.gc();
			switch (select_Index) {
			case 0: {// ��Ϸ������
				Screen.status = Screen.S_NULL;
				C.passed = false;
				C.alertType = 0;
				s.setGameStatus(Screen.S_MODESELECT);
			}
				break;
			case 2: { // ���а�
				// ��������
				s.setGameStatus(Screen.S_HELP);
				s.repaint();
			}
				break;
			case 1: { // �����������
				Screen.status = Screen.S_NULL;
				if (s.si == null)
					s.si = new ServerIptv(s);
				s.si.dogetRankSum();// �õ����а�����
			}
				break;
			case 3: {// �˳���Ϸ
				s.mid.exitGame();
			}
				break;
			}
		}
			break;
		case C.KEY_LEFT:
		case C.KEY_UP: {
			if (select_Index <= 0)
				select_Index = 4;
			--select_Index;
		}
			break;
		case C.KEY_RIGHT:
		case C.KEY_DOWN: {
			if (select_Index >= 3)
				select_Index = -1;
			++select_Index;
		}
			break;
		}
	}

	public void showMe(Graphics g) {
		g.setClip(0, 0, 645, 535);
		g.setColor(0);
		g.fillRect(0, 0, 645, 535);
		// drawMoveingString(g, Screen.scrolBarStrWidth);//��������
		C.DrawImage_LEFTTOP(imagea_Menu[0], g);// ��Menu����
		C.DrawImage_XY_LEFTTOP(imagea_Menu[1], 20, 80, g);
		drawButton(g);// ����ť
		
		g.setColor(0, 0, 0);
		g.drawString(Screen.VERSION + NX_IPTV_PORP_TOOL.js_PropVersion, 5, 525, Graphics.BOTTOM | Graphics.LEFT);
	}

	/**
	 * �����ڵȴ�
	 * 
	 * @param g
	 */
	public void showWait(Graphics g) {
		g.setClip(0, 0, C.screenwidth, C.screenheight);
		Image wait = C.GetImageSource("/menu/wait.png");
		C.DrawImage_VH(wait, C.screenwidth >> 1, C.screenheight >> 1, g);
		wait = null;
		System.gc();
	}

	/**
	 * ��������Ļ
	 * 
	 * @param g
	 * @param width
	 */
	public void drawMoveingString(Graphics g, int width) {
		g.setColor(0xffffff);
		g.fillRect(100, 494, 250, 35);
		g.setColor(0x000000);
		g.setFont(C.FONT_BOLD_MEDIUM);
		g.drawString(C.scroll_bar_Str, scrollBar_X, 500, 0);
		if (scrollBar_X <= (118 - width))
			scrollBar_X = 358;
		scrollBar_X -= 4;
	}

	/**
	 * ����ť
	 * 
	 * @param g
	 */
	public void drawButton(Graphics canvas) {
		C.DrawImage_VH(
				imagea_Menu[2], // ������
				inta_BUTTON_POSITION[select_Index * 2],
				inta_BUTTON_POSITION[select_Index * 2 + 1], canvas);
		C.DrawImage_VH(
				imagea_SelectButton[select_Index], // ��ť�ϵ�����
				inta_BUTTON_POSITION[select_Index * 2],
				inta_BUTTON_POSITION[select_Index * 2 + 1] - 10, canvas);
	}

}
