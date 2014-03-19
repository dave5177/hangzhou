package com.dave.paoBing.view;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;

public class CheckCode implements Showable {

	/**
	 * 大又粗字体
	 */
	public final static Font CheckCode_FONT_BOLD_LARGE = Font.getFont(
			Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);

	public final static int CheckCode_LARGE_BOLD_STRINGWIDTH = CheckCode_FONT_BOLD_LARGE
			.stringWidth("8");

	private CanvasControl canvasControl;

	private Image[] imagea_Check;
	private byte ImageCount = 5;

	public byte buttonIndex;// 按钮索引
	public byte rawIndex;// 行索引

	private String gui = "|";

	private boolean b_gui;

	private byte byte_logi;

	private StringBuffer sb_CheckCode;

	private int rani;

	/**
	 * 前一个界面
	 */
	private Showable lastView;

	public CheckCode(CanvasControl screencontrol, Showable lastView) {
		this.lastView = lastView;
		canvasControl = screencontrol;
		init();
	}

	public void init() {
		rani = NextInt(1000, 9999);
		sb_CheckCode = new StringBuffer();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT: {// 左
			if (rawIndex == 0 && sb_CheckCode.length() > 0)
				sb_CheckCode.deleteCharAt(sb_CheckCode.length() - 1);
			if (rawIndex == 1)
				buttonIndex = 0;// 第二行
		}
			break;
		case C.KEY_RIGHT: {// 右
			if (rawIndex == 1)
				buttonIndex = 1;// 第二行
		}
			break;
		case C.KEY_DOWN: {// 下
			rawIndex = 1;
			buttonIndex = 0;
		}
			break;
		case C.KEY_UP: {// 上
			rawIndex = 0;
		}
			break;
		case C.KEY_FIRE: {// 确定
			if (rawIndex == 1) {
				if (buttonIndex == 0) {// 确定
					if (sb_CheckCode.toString().equals(String.valueOf(rani))) {
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						canvasControl.hs_tool.DodeductFee("500", "");
					} else {
						sb_CheckCode.delete(0, (sb_CheckCode.length()));
						rawIndex = 0;
					}
				} else {// 取消
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(lastView);
					canvasControl.repaint();
				}
			}
		}
			break;
		case C.KEY_0:
		case C.KEY_1:
		case C.KEY_2:
		case C.KEY_3:
		case C.KEY_4:
		case C.KEY_5:
		case C.KEY_6:
		case C.KEY_7:
		case C.KEY_8:
		case C.KEY_9: {
			if (sb_CheckCode.length() < 4) {
				sb_CheckCode.append(keyCode - 48);
			}
		}
			break;
		}
	}

	/**
	 * 显示提示框，框架，就两个按钮
	 */
	private void drawFrame(Graphics c) {
		C.DrawImage_VH(imagea_Check[0], 320, 245, c);// 框

		c.setColor(0);
		c.setFont(C.FONT_LARGE_BLOD);
		c.drawString("请输入验证码:", 255, 170, 0);

		c.setColor(0x406a9c);
		c.fillRect(200, 220, 65, 25);// 输入底框

		c.setColor(0);
		c.drawString(sb_CheckCode.toString(), 200, 220, 0);// 输入的文字

		c.setColor(0xff0000);
		c.drawString("[" + rani + "]" + " (左键删除)", 270, 220, 0);

		C.DrawImage_VH(imagea_Check[2], 320 - 80, 245 + 50, c);// 按钮底,左
		C.DrawImage_VH(imagea_Check[2], 320 + 80, 245 + 50, c);// 按钮底,右

		if (rawIndex == 1) {
			C.DrawImage_VH(imagea_Check[1], 240 + (160 * buttonIndex),
					245 + 50, c);// 选中状态底
		} else {
			c.setColor(-1);
			if (b_gui)
				c.drawString(
						gui,
						200 + (sb_CheckCode.length() * CheckCode_LARGE_BOLD_STRINGWIDTH),
						220, 0);
		}

		C.DrawImage_VH(imagea_Check[3], 320 - 80, 245 + 50, c);// 按钮文字 ,左
		C.DrawImage_VH(imagea_Check[4], 320 + 80, 245 + 50, c);// 按钮文字,右

	}

	public int NextInt(int num1, int num2) {
		Random random = new Random();
		int result = 0;
		if (num2 > num1) {
			result = (random.nextInt() >>> 1) % (num2 - num1) + num1;
		} else if (num1 > num2) {
			result = (random.nextInt() >>> 1) % (num1 - num2) + num2;
		}
		return result;
	}

	public void show(Graphics g) {
		drawFrame(g);
	}

	public void loadResource() {
		imagea_Check = new Image[ImageCount];
		for (int a = 0; a < ImageCount; a++) {
			try {
				imagea_Check[a] = Image.createImage("/hscheckcode/" + a
						+ ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeResource() {
		for (int a = 0; a < ImageCount; a++) {
			imagea_Check[a] = null;
		}
		imagea_Check = null;
		System.gc();
	}

	public void removeServerImage() {

	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		byte_logi++;
		if (byte_logi < 5)
			b_gui = true;
		if (byte_logi >= 5)
			b_gui = false;
		if (byte_logi > 9)
			byte_logi = 0;
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

}
