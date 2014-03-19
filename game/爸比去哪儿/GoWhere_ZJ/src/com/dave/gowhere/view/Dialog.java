package com.dave.gowhere.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class Dialog implements Showable {
	private CanvasControl canvasControl;

	/**
	 * 对话框处在的界面
	 */
	private Showable lastView;

	/**
	 * 对话框类型控制 0：退出整个程序对话框。 1：退出游戏对话框。2：自定义。 3：购买成功。 4：购买失败。 6：闯关成功。
	 * 10：当月购买已达到峰值。
	 */
	private int type;

	/**
	 * 记录选择用的下标值 0：是。（默认值） 1：否。
	 */
	private int index = 0;

	private Image img_back;
	private Image img_word;
	private Image img_yesWord;
	private Image img_noWord;
	private Image img_button;
	private Image img_pointer;

	/**
	 * 所处的游戏（只有退出游戏的时候用到），用完后置空。
	 */
	public static Game game;

	public Dialog(CanvasControl canvasControl, int type, Showable lastView) {
		this.canvasControl = canvasControl;
		this.lastView = lastView;

		this.type = type;

		if (type == 3 || type == 4 || type == 10 || type == 2) {
			new Timer().schedule(new AutoCloseDialogTimerTask(), 1000);
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, C.WIDTH / 2, 254, Graphics.VCENTER
				| Graphics.HCENTER);// draw背景

		g.drawImage(img_word, C.WIDTH / 2, 220, Graphics.VCENTER
				| Graphics.HCENTER);// draw对话框文字

		if (type == 3 || type == 4 || type == 10 || type == 2)
			return;

		// draw选择bar
		g.drawImage(img_button, 250, 320, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(img_button, 410, 320, Graphics.HCENTER | Graphics.VCENTER);

		g.drawImage(img_pointer, 250 + index * 160, 320, Graphics.HCENTER
				| Graphics.VCENTER);
		g.drawImage(img_yesWord, 250, 320, Graphics.HCENTER | Graphics.VCENTER);// draw文字”是“
		g.drawImage(img_noWord, 410, 320, Graphics.HCENTER | Graphics.VCENTER);// draw文字”否“
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/dialog/back.png");
			img_button = Image.createImage("/dialog/button.png");
			loadWorldImage();

			img_pointer = Image.createImage("/dialog/pointer.png");

			img_yesWord = Image.createImage("/dialog/yesWord.png");
			img_noWord = Image.createImage("/dialog/noWord.png");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadWorldImage() throws IOException {
		if (type == 1) {
			img_word = Image.createImage("/dialog/word_0.png");
		} else {
			img_word = Image.createImage("/dialog/word_" + type + ".png");
		}
	}

	public void removeResource() {
		img_back = null;
		img_word = null;
		img_yesWord = null;
		img_noWord = null;
		img_button = null;
		img_pointer = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_FIRE:
			if (index == 0) {
				switch (type) {
				case 0:// 退出游戏
					canvasControl.getMidlet().notifyDestroyed();
					break;
				case 1:// 返回，到首页
					
					break;
				case 6:
					break;

				}
				System.gc();
			} else if (index == 1) {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(lastView);
			}
			break;
		case C.KEY_LEFT:
			if (index > 0)
				index--;
			break;
		case C.KEY_RIGHT:
			if (index < 1)
				index++;
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			if (type == 1)
				game = null;

			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(lastView);
			index = 0;
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	/**
	 * @author Administrator 自动关闭对话框任务
	 */
	class AutoCloseDialogTimerTask extends TimerTask {

		public void run() {
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
			canvasControl.repaint();
			Dialog.this.removeResource();
		}
	}

	public void removeServerImage() {

	}

	public void handleGoods(int goodsIndex) {
		// lastView.handleGoods(goodsIndex);

		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		this.removeServerImage();
		canvasControl.setView(lastView);
	}

}
