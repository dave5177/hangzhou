package com.dave.jpsc.main;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet{
	public CanvasControl canvasControl;
	
	public MainMIDlet() {
		
	}
	  
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException {
		canvasControl = new CanvasControl(this);
		canvasControl.setFullScreenMode(true);
		// TODO Auto-generated method stub
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
