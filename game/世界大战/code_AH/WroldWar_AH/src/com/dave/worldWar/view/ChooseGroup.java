package com.dave.worldWar.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.net.ServerIptv;
import com.dave.worldWar.tool.C;

public class ChooseGroup implements Showable {

private CanvasControl canvasControl;
	
	public Image img_back;
	public Image img_select;
	public Image img_vs;
	
	/**
	 * 选择下标值。0，g国；1：u国。
	 */
	private int index;
	
	public ChooseGroup(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_select, 176 + index * 289, 267, Graphics.VCENTER | Graphics.HCENTER);
		g.drawImage(img_vs, 320, 273, Graphics.VCENTER | Graphics.HCENTER);
	}

	public void loadResource() {
	}
	
	public void removeResource() {
		img_back = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT:
			if(index > 0)
				index --;
			break;
		case C.KEY_RIGHT:
			if(index < 1)
				index ++;
			break;
		case C.KEY_FIRE:
			CanvasControl.group = index + 1;
			canvasControl.saveParam();
			new ServerIptv(canvasControl).doSendScore(0, CanvasControl.group);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 1));
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
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
		// TODO Auto-generated method stub
		
	}
}
