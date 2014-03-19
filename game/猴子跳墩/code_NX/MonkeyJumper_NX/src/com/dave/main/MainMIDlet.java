package com.dave.main;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet{
	CanvasControl canvasControl;
	
	public MainMIDlet() {
		canvasControl = new CanvasControl(this);
		canvasControl.setFullScreenMode(true);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
//		canvasControl.home_page.removeAllThisClassSources();
//		if(canvasControl.help_page != null) canvasControl.help_page.removeAllThisClassSources();
	}

	protected void pauseApp() {
	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(canvasControl);
		// TODO Auto-generated method stub
		
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
