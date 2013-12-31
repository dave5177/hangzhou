package com.dave.jpsc.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

public class Message implements Showable {

	/**
	 * ��Ϣ����
	 */
	public static int numbers;

	/**
	 * ��ս���
	 */
	public static int[] duelResult;

	/**
	 * ��ս���ҵ������Ϣ
	 */
	public static Player[] duelPlayer;
	
	/**
	 * ��ʾ����Ϣ
	 */
	private String[] show_msg;
	
	private CanvasControl canvasControl;

	public Image img_back;
	public Image[] a_img_btn;

	private int index_choose;

	public Message(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		
		createMsgStr();
	}

	private void createMsgStr() {
		show_msg = new String[numbers];
		for (int i = 0; i < numbers; i++) {
			if(duelResult[i] == Player.DEFEATED) {//�������
				show_msg[i] = "�㱻" + duelPlayer[i].nickName + 
						"��ս�ˣ�����սʧ�ܣ�����ʵ��ֵ�����100�㡣��������½���"
						+ (C.R.nextInt(19) + 1);
			} else {//��ʤ��
				show_msg[i] = "�㱻" + duelPlayer[i].nickName + 
						"��ս�ˣ���ϲ����ʤ�������ʵ��ֵ�����100�㡣�������������"
						+ (C.R.nextInt(19) + 1);
			}
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		if(duelResult != null) {
			g.setFont(C.FONT_LARGE_BLOD);
			g.setColor(0xffffff);
			for (int i = 0; i < numbers; i++) {
				if(duelResult[i] == Player.DEFEATED) {//�������
					g.drawImage(a_img_btn[0], 540, 120 + i * 75, Graphics.VCENTER | Graphics.HCENTER);
				} else {
					g.drawImage(a_img_btn[0], 540, 120 + i * 75, Graphics.VCENTER | Graphics.HCENTER);
				}
				C.drawStrInRect(20, 100 + i * 75, 480, 60, show_msg[i], C.FONT_LARGE_BLOD, g);
			}
			
			if(duelResult[index_choose] == Player.DEFEATED) {
				g.drawImage(a_img_btn[2], 540, 120 + index_choose * 75, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(a_img_btn[3], 540, 120 + index_choose * 75, Graphics.VCENTER | Graphics.HCENTER);
			}
		}
	}

	public void loadResource() {
	}

	public void removeResource() {

	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_UP:
			if (index_choose > 0)
				index_choose--;
			break;
		case C.KEY_DOWN:
			if (index_choose < numbers - 1)
				index_choose++;
			break;
		case C.KEY_FIRE:
			CanvasControl.mission = C.R.nextInt(16) + 1;
			canvasControl.setView(canvasControl.nullView);
			this.removeServerImage();
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 7, Game.MODE_DUAL, duelPlayer[index_choose]));
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 0));
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	public void removeServerImage() {
		img_back = null;
		for (int i = 0; i < a_img_btn.length; i++) {
			a_img_btn[i] = null;
		}
		a_img_btn = null;

		System.gc();
	}

	public void handleGoods(int goodsIndex) {
		
	}
}
