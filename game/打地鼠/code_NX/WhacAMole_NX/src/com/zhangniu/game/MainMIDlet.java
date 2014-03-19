package com.zhangniu.game;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet {

	public MainMIDlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(new Screen(this));
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}

	/**
	 * ÍË³öÓÎÏ·
	 */
	public void exitGame() {
		try {
//			if (Screen.ret_url != null) {
//				platformRequest(Screen.ret_url);
//			}
			destroyApp(true);
			notifyDestroyed();
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}
//		catch (ConnectionNotFoundException e) {
//			e.printStackTrace();
//		}
	}

}