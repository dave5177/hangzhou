package com.dave.gowhere.main;

import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet{
	public CanvasControl canvasControl;
	
	public MainMIDlet() {
		try {
			canvasControl = new CanvasControl(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvasControl.setFullScreenMode(true);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		Display.getDisplay(this).setCurrent(canvasControl);
		System.out.println((0x11000000>>24)&0xff);
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
