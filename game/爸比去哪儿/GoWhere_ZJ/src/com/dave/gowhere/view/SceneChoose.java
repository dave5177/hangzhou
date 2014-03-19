package com.dave.gowhere.view;

import javax.microedition.lcdui.Graphics;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

/**
 * @author Dave
 * 场景选择
 */
public class SceneChoose implements Showable{
	private int chooseIndex;
	private CanvasControl canvasControl;

	public SceneChoose(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.setColor(0xe4bf4f);
		g.fillRect(0, 0, 640, 530);
		
		g.setColor(0xeb6d1a);
		g.fillRect(0, 200 + chooseIndex * 40, 640, C.FONT_LARGE_BLOD.getHeight());
		g.setColor(0x066b19);
		g.setFont(C.FONT_LARGE_BLOD);
		g.drawString("场景一", 320, 200, Graphics.HCENTER | Graphics.TOP);
		g.drawString("场景二", 320, 240, Graphics.HCENTER | Graphics.TOP);
	}

	public void loadResource() {
		// TODO Auto-generated method stub
		
	}

	public void removeResource() {
		// TODO Auto-generated method stub
		
	}

	public void removeServerImage() {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(int keyCode) {
		if(keyCode == C.KEY_UP || keyCode == C.KEY_DOWN) {
			chooseIndex = chooseIndex == 0 ? 1 : 0;
		}
		
		if(keyCode == C.KEY_FIRE) {
			canvasControl.setGoBackView(this);
			canvasControl.setView(new Game(canvasControl, chooseIndex + 1));//选择好场景开始游戏
		}
		if(keyCode == C.KEY_0) {
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void logic() {
		// TODO Auto-generated method stub
		
	}

}
