package com.dave.ftxz.main;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet{
	public CanvasControl canvasControl;
	
	public MainMIDlet() {
		canvasControl = new CanvasControl(this);
		canvasControl.setFullScreenMode(true);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		
	}

	protected void pauseApp() {
		
	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(canvasControl);
	}
	
	public void exitGame(){
		try {
			destroyApp(true);
			notifyDestroyed();
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}
	}

}
