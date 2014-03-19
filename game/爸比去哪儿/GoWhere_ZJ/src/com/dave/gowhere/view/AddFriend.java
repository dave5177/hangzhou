package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

/**
 * @author Dave
 * 添加好友
 */
public class AddFriend implements Showable {
	private CanvasControl canvasControl;
	
	private Image img_back;
	
	private int indexChoose;

	/**
	 * 选择红心的y坐标
	 */
	private int yChoose;

	public AddFriend(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(canvasControl.img_key_0_goback, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
		
		yChoose = yChoose == 80 + indexChoose * 64 ? 78 + indexChoose * 64 : 80 + indexChoose * 64;
		g.drawImage(canvasControl.img_choose, 590, yChoose, Graphics.HCENTER | Graphics.VCENTER);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/addfriend/back.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		img_back = null;
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_DOWN:
			if(indexChoose < 6)
				indexChoose ++;
			break;
		case C.KEY_UP:
			if(indexChoose > 0)
				indexChoose --;
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
			break;
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

	public void removeServerImage() {
		// TODO Auto-generated method stub
		
	}


}
