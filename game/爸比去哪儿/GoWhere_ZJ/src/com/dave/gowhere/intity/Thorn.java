package com.dave.gowhere.intity;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * @author Dave
 * ´Ì
 */
public class Thorn extends MapElements {

	public Thorn(Game game, int x, int y, Image[] frameImg) {
		super(game, x, y, frameImg);
		initRect();
		type = TYPE_THORN;
	}
	
	public void show(Graphics g) {
		g.drawImage(frameImg[0], x - 15, y, Graphics.HCENTER | Graphics.TOP);
		g.drawImage(frameImg[0], x + 15, y, Graphics.HCENTER | Graphics.TOP);
		////////////////////////////µ÷ÊÔÐÅÏ¢///////////////////////////////
		if(CanvasControl.DEBUG) {
			g.setColor(0x0000ff);
			rect.show(0, 0, g);
		}
	}
	
	protected void initRect() {
		rect = new Rect(x - 40, y + 20, 80, 80);
	}
	
}
