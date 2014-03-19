package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Affiche {

	Screen s;

	private String[] prize = { "һ�Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�",
			"���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�",
			"���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", "���Ƚ�", };

	private byte row_index;// ��������
	private byte raw_PageSum = 2;// �������ֵ
	private byte col_index;// ��������

	/**
	 * [0]�����ҳ�� [1] [2]�н�����������
	 */
	private byte[] bytea_colMax = { 2, 0, 0 };

	/**
	 * ҳ�������ͼƬ
	 */
	private Image[] imagea_PageNumber;

	private Image[] imagea_Affiche;
	private int afficheImageCount = 4;

	/**
	 * ҳ�����ͼ��
	 * 
	 * @param mainscreen
	 */
	private Image[] imagea_pageicon;

	private final byte AFFICHE_CONTENT = 0;// �����
	private final byte AFFICHE_PRIZE = 1;// ���Ʒ
	private final byte AFFICHE_RAFFILENAME = 2;// �н�����

	private final int fontPrizeColor = 0xa47700;// ��Ʒ������ɫ

	/**
	 * vector�ļ�¼����
	 */
	private int vectorSize;

	public Affiche(Screen screen) {
		s = screen;
	}

	public void init() {
		Image useOnce = null;
		vectorSize = C.vector_PrizeName.size();
		imagea_Affiche = new Image[afficheImageCount];

		useOnce = C.GetImageSource("/affiche/pagenumber.png");
		imagea_PageNumber = new Image[10];
		for (int a = 0; a < 10; a++) {
			imagea_PageNumber[a] = Image.createImage(useOnce, a * 32, 0, 32,
					32, 0);
		}

		useOnce = C.GetImageSource("/affiche/pages.png");
		imagea_pageicon = new Image[5];
		for (int a = 0; a < 5; a++) {
			imagea_pageicon[a] = Image.createImage(useOnce, a * 34, 0, 34, 34,
					0);
		}
		useOnce = null;

		bytea_colMax[2] = (byte) getPageSum(C.vector_PrizeName.size(), 10);
	}

	public void removeThisClassSource() {
		for (int a = 0; a < afficheImageCount; a++) {
			imagea_Affiche[a] = null;
		}
		imagea_Affiche = null;
		C.vector_PrizeName.removeAllElements();
		System.gc();
	}

	/**
	 * ��10Ϊһ����λ���õ���ҳ�� sum ������ unit �ǵ�λ��
	 */
	private int getPageSum(int sum, int unit) {
		// C.out("����������:"+sum);
		// C.out("�����ĵ�λ��:"+unit);
		if (sum == 0)
			return 0;
		int pagesum = 0;

		if (sum <= unit) {
			pagesum = 1;
		} else if (sum % 10 > 0) {
			pagesum = (sum / 10) + 1;
		} else {
			pagesum = sum / 10;
		}

		return pagesum;
	}

	/**
	 * ������Ӧ��ͼƬ
	 */
	public void LoadSource() {
		if (imagea_Affiche == null)
			imagea_Affiche = new Image[afficheImageCount];
		switch (row_index) {
		case AFFICHE_CONTENT: {// �����
			imagea_Affiche[0] = C.GetImageSource("/affiche/afficheback.png");
			imagea_Affiche[1] = C.GetImageSource("/affiche/1.png");// �����1
			imagea_Affiche[2] = C.GetImageSource("/affiche/2.png");// �����2
			imagea_Affiche[3] = C.GetImageSource("/affiche/a1.png");// �����С���
		}
			break;
		case AFFICHE_PRIZE: {// ���Ʒ
			imagea_Affiche[0] = C.GetImageSource("/affiche/afficheback.png");
			imagea_Affiche[1] = C.GetImageSource("/affiche/3.png");
			imagea_Affiche[2] = C.GetImageSource("/affiche/a2.png");// ��Ʒ����С���
		}
			break;
		case AFFICHE_RAFFILENAME: {// �н�����
			imagea_Affiche[0] = C.GetImageSource("/affiche/afficheback.png");
			imagea_Affiche[1] = C.GetImageSource("/affiche/6.png");
			imagea_Affiche[2] = C.GetImageSource("/affiche/a3.png");// �н�����
			imagea_Affiche[3] = C.GetImageSource("/affiche/novector.png");// û���κ���Ϣ
		}
			break;
		}
	}

	public void keyPressed(int keyCode) {
		if (keyCode == C.KEY_RIGHT) {
			if (row_index == raw_PageSum)
				return;
			col_index = 0;
			++row_index;
			cleanImage();
			LoadSource();
		} else if (keyCode == C.KEY_LEFT) {
			col_index = 0;
			if (row_index == 0)
				row_index = 1;
			--row_index;
			cleanImage();
			LoadSource();
		} else if (keyCode == C.KEY_DOWN) {
			if (row_index == AFFICHE_CONTENT) {
				if (col_index + 1 < 2)
					++col_index;
			}
			if (row_index == AFFICHE_RAFFILENAME) {
				if (col_index + 1 < bytea_colMax[2])
					++col_index;
			}
		} else if (keyCode == C.KEY_UP) {
			if (col_index > 0)
				--col_index;
		}
		s.repaint();
	}

	public void showMe(Graphics canvas) {
		canvas.setClip(0, 0, C.screenwidth, C.screenheight);
		switch (row_index) {
		case AFFICHE_CONTENT: {// �����
			C.DrawImage_LEFTTOP(imagea_Affiche[0], canvas);
			C.DrawImage_VH(imagea_Affiche[col_index + 1], 320, 280, canvas);
			C.DrawImage_VH(imagea_Affiche[3], 126, 58, canvas);// �����С���
			drawPage(canvas, (byte) (col_index + 1),
					(byte) (bytea_colMax[row_index]));// ��ҳ��
			drawPageIcon(canvas, col_index, bytea_colMax[row_index]);// ��ҳ����ص�ͼƬ
		}
			break;
		case AFFICHE_PRIZE: {// ���Ʒ
			C.DrawImage_LEFTTOP(imagea_Affiche[0], canvas);
			C.DrawImage_VH(imagea_Affiche[1], 320, 265, canvas);
			C.DrawImage_VH(imagea_Affiche[2], 319, 58, canvas);// ��Ʒ����С���
		}
			break;
		case AFFICHE_RAFFILENAME: {// �н�����
			C.DrawImage_LEFTTOP(imagea_Affiche[0], canvas);
			C.DrawImage_VH(imagea_Affiche[2], 512, 58, canvas);// ѡ��:�н�����
			C.DrawImage_VH(imagea_Affiche[1], 320, 110, canvas);// ״̬��:�ȼ����ֻ��ţ���Ʒ
			int forstart = 0;
			int forend = 0;
			int prizeY = 0;

			if (bytea_colMax[2] == 0) {// ���һ����ϢҲû�У�����ʾû����Ϣ
				C.DrawImage_VH(imagea_Affiche[3], 320, 280, canvas);// ѡ��:�н�����
				return;
			}

			if (bytea_colMax[2] == 1) {// ���ȫ��ֻ��һҳ�������
				forstart = 0;
				forend = C.vector_PrizeName.size();
			} else {
				if (col_index + 1 == bytea_colMax[2]) {// ��������һҳ�Ļ�
					forstart = col_index * 10;
					forend = vectorSize;
				} else {// ���м�����
					forstart = col_index * 10;
					forend = (col_index + 1) * 10;
				}
			}

			for (int a = forstart; a < forend; a++) {
				String[] getstr = (String[]) C.vector_PrizeName.elementAt(a);
				canvas.setColor(fontPrizeColor);
				canvas.setFont(C.FONT_BOLD_MEDIUM);
				canvas.drawString(prize[a], 70, 140 + prizeY * 30, 0);
				canvas.drawString(getstr[0], 260, 140 + prizeY * 30, 0);
				canvas.drawString(getstr[1], 435, 140 + prizeY * 30, 0);
				++prizeY;
			}

			drawPage(canvas, (byte) (col_index + 1), (byte) (bytea_colMax[2]));// ��ҳ��
			drawPageIcon(canvas, col_index, bytea_colMax[2]);// ��ҳ����ص�ͼƬ

		}
			break;
		}
	}

	/**
	 * ��ҳ��
	 */
	public void drawPage(Graphics canvas, byte index, byte max) {
		C.DrawNumber_XY_RIGHTTOP(imagea_PageNumber, index, 290, 465, 34, canvas);
		C.DrawImage_XY_LEFTTOP(imagea_pageicon[4], 310, 465, canvas);// �м�/
		C.DrawNumber_XY_RIGHTTOP(imagea_PageNumber, max, 330, 465, 34, canvas);
	}

	/**
	 * ��ҳ��ͼ��
	 */
	public void drawPageIcon(Graphics canvas, byte index, byte pageMax) {
		if (index + 1 == pageMax) {// ����ĩβ
			C.DrawImage_VH(imagea_pageicon[1], 258, 480, canvas);
			C.DrawImage_VH(imagea_pageicon[2], 385, 480, canvas);
		} else if (index == 0) {// ���ʼ
			C.DrawImage_VH(imagea_pageicon[0], 268, 480, canvas);
			C.DrawImage_VH(imagea_pageicon[3], 385, 480, canvas);
		} else {// ���¶�����ѡ���ʱ��
			C.DrawImage_VH(imagea_pageicon[1], 258, 480, canvas);
			C.DrawImage_VH(imagea_pageicon[3], 385, 480, canvas);
		}
	}

	public void cleanImage() {
		for (int a = 0; a < afficheImageCount; a++) {
			imagea_Affiche[a] = null;
		}
		System.gc();
	}

}
