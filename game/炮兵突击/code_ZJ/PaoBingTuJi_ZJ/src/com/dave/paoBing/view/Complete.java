package com.dave.paoBing.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;

public class Complete implements Showable{
	CanvasControl canvasControl;
	
	/**
	 * ��������
	 */
	public static final Font FONT_TITLE = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
	
	/**
	 * ��������
	 */
	public static final Font FONT_TEXT = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
	
	/**
	 * ����
	 */
	public static final String slogan = "��Ϸ����  ���볣��";
	
	/**
	 * ������Ա��
	 */
	public static final String staff[] = 
		{"�߻�����SBĬĬ" ,
			"��������С��" ,
			"���򡪡�Mr.D" ,
			"���ԡ���СĪ"};
	
	/**
	 * ͨ����ʾ��
	 */
	public static final String word = 
			"���ڱ�ͻ��������Ϸ���˽����ˣ�" +
			"���ȹ�ϲ��ͨ�أ����ǻ�������Ϊ���ڱ�ͻ��������һϵ�е���������" +
			"����л��λ��ҵĴ���֧�֣�" +
			"���ǵ�ϲ���������Ǽ��������Ϸ�Ͳ��Ͻ�ȡ���������" +
			"������һ���ڴ��������Ϸ��";
			
	/**
	 * ��ȡ����ַ�������
	 */
	private String text[];

	/**
	 * ����
	 */
	private int x_text, y_text;

	/**
	 * �м��
	 */
	private int row_height;

	/**
	 * ��Ļ����Ļ�м�ͣ��ʱ��
	 */
	private int waitTime;
	
	public Complete(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		text = C.subStr(300, FONT_TEXT, word);
		row_height = FONT_TEXT.getHeight() + 2;
		x_text = 170;
		y_text = 510;
	}
	
	public void show(Graphics g) {
		g.setColor(0, 0, 0);
		g.fillRect(0, 0, 645, 535);
		g.setColor(255, 255, 255);
		g.setFont(FONT_TITLE);
		g.drawString("��ϲͨ��", 320, 50, Graphics.BOTTOM | Graphics.HCENTER);
		g.drawString(slogan, 630, 520, Graphics.BOTTOM | Graphics.RIGHT);
//		g.setColor(0, 255, 0);
		g.drawLine(10, 60, 630, 60);
		g.drawLine(10, 61, 630, 61);
//		g.setColor(255, 255, 255);
		g.setClip(170, 80, 300, 400);
		g.setFont(FONT_TEXT);
		for (int i = 0; i < text.length; i++) {
			g.drawString(text[i], x_text, y_text + row_height * i, Graphics.BOTTOM | Graphics.LEFT);
		}
		for (int i = 0; i < staff.length; i++) {
			g.drawString(staff[i], 320, y_text + 270 + row_height * i, Graphics.BOTTOM | Graphics.HCENTER);
		}
	}

	public void loadResource() {
		
	}

	public void removeResource() {
		
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(new Loading(canvasControl, 2));
			break;

		default:
			break;
		}
	}

	public void keyReleased(int keyCode) {
		
	}

	public void keyRepeated(int keyCode) {
		
	}

	public void logic() {
		if(y_text == 250) {
			waitTime ++;
			if(waitTime > 50){
				y_text --;
				waitTime = 0;
			}
		} else if(y_text == -10) {
			waitTime ++;
			if(waitTime > 50){
				y_text --;
				waitTime = 0;
			}
		} else if(y_text < -260) {
			canvasControl.setView(new Loading(canvasControl, 2));
		} else {
			y_text --;
		}
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

}
