package com.dave.paoBing.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;

public class Complete implements Showable{
	CanvasControl canvasControl;
	
	/**
	 * 标题字体
	 */
	public static final Font FONT_TITLE = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
	
	/**
	 * 正文字体
	 */
	public static final Font FONT_TEXT = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
	
	/**
	 * 标语
	 */
	public static final String slogan = "游戏人生  梦想常在";
	
	/**
	 * 工作人员表
	 */
	public static final String staff[] = 
		{"策划——SB默默" ,
			"美工——小美" ,
			"程序——Mr.D" ,
			"测试——小莫"};
	
	/**
	 * 通关提示语
	 */
	public static final String word = 
			"《炮兵突击》的游戏到此结束了，" +
			"首先恭喜你通关，我们还将继续为《炮兵突击》进行一系列的延伸制作" +
			"，感谢各位玩家的大力支持，" +
			"你们的喜爱，是我们坚持制作游戏和不断进取的最大动力，" +
			"让我们一起期待更多的游戏。";
			
	/**
	 * 截取后的字符串数组
	 */
	private String text[];

	/**
	 * 坐标
	 */
	private int x_text, y_text;

	/**
	 * 行间距
	 */
	private int row_height;

	/**
	 * 字幕在屏幕中间停留时间
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
		g.drawString("恭喜通关", 320, 50, Graphics.BOTTOM | Graphics.HCENTER);
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
