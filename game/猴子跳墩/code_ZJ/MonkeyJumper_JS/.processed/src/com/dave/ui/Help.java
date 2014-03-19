package com.dave.ui;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class Help {
	private CanvasControl canvasControl;
	
	private static Image back;		//帮助信息
	private static Image preKey; 	//按9键返回
	
	public Help(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		loadAllSources();
	}
	
	public void showMe(Graphics canvas) {
		canvas.drawImage(back, C.WIDTH, C.HEIGTH, Graphics.RIGHT|Graphics.BOTTOM);
		canvas.drawImage(preKey, C.WIDTH - 60, C.HEIGTH - 10, Graphics.RIGHT|Graphics.BOTTOM);
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_0: 
		case C.KEY_9:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			/*if(CanvasControl.state_Back_For == CanvasControl.GAME_PAGE){
				canvasControl.state = CanvasControl.NULL;
				removeAllThisClassSources();
				CanvasControl.state = CanvasControl.GAME_PAGE;
				CanvasControl.state_Back_For = 0;
				return;
			}*/
			canvasControl.audioPlay.playSound((byte)2);
			canvasControl.state = CanvasControl.NULL;
			canvasControl.setState(canvasControl.state_Back_For);
			removeAllThisClassSources();
			break;
		}
	}
	
	public void removeAllThisClassSources(){
		back = null;
		preKey = null;
	}
	
	public void loadAllSources() {
		try {
			back = Image.createImage("/help/back.png");
			preKey = Image.createImage("/help/preKey.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
