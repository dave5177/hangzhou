package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Help {

	private Image helpBg;
	private Screen s;

	public Help(Screen screen) {
		s = screen;
	}

	public void init() {
		if (helpBg == null)
			helpBg = C.GetImageSource("/help/help.png");
	}

	public void keyPressed(int keyCode) {
		if (keyCode == C.KEY_0 || keyCode == C.KEY_9 || keyCode == C.KEY_BACK
				|| keyCode == C.KEY_BACK_ZX) {// 按了0返回，按9返回，按返回
			Screen.status = Screen.S_NULL;
			s.setGameStatus(Screen.S_MENU);
		}
	}

	public void showMe(Graphics g) {
		g.setClip(0, 0, C.screenwidth, C.screenheight);
		g.drawImage(helpBg, 0, 0, 0);
		helpBg = null;
		System.gc();
	}

}
