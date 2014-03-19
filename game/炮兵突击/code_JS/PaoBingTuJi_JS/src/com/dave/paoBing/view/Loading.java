package com.dave.paoBing.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.paoBing.tool.HttpLoadImage;
import com.dave.showable.Showable;

public class Loading implements Showable, Runnable {
	/**
	 * 0：load加油站图片。 1: load游戏图片. 2: load主页图片。 3: load排行榜图片。
	 */
	private final int type;

	private Image img_back;
	private Image img_loadingBar;
	private Image[] a_img_gear;

	private final CanvasControl canvasControl;

	/**
	 * 排行榜界面
	 */
	private Ranking ranking;

	/**
	 * 游戏界面
	 */
	private Game game;

	/**
	 * 主页
	 */
	private Home home;

	/**
	 * 总共加载多少张图片
	 */
	private int imgAmount;

	/**
	 * 齿轮转动图片下标值
	 */
	private int index_gear;

	public Loading(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;

		launch();
	}

	private void launch() {
		if (type == 3) {
			ranking = new Ranking(canvasControl);
			imgAmount = 2;
		} else if (type == 1) {
			game = new Game(canvasControl);
			imgAmount = 22;
		} else if (type == 2) {
			home = new Home(canvasControl);
			imgAmount = 11;
		}
		new Thread(this).start();
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.setClip(0, 0, 170 + 272 * HttpLoadImage.imgCount / imgAmount, 530);
		g.drawImage(img_loadingBar, 170, 407, Graphics.LEFT | Graphics.VCENTER);
		g.setClip(0, 0, 640, 530);

		g.drawImage(a_img_gear[index_gear], 310, 460, Graphics.VCENTER
				| Graphics.HCENTER);

	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/loading/back.png");
			img_loadingBar = Image.createImage("/loading/loadingBar.png");

			a_img_gear = new Image[6];
			for (int i = 0; i < 6; i++) {
				a_img_gear[i] = Image
						.createImage("/loading/gear_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_loadingBar = null;
		for (int i = 0; i < 6; i++) {
			a_img_gear[i] = null;
		}
		a_img_gear = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		index_gear++;
		if (index_gear > 5)
			index_gear = 0;
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

	public void run() {
		if (type == 3) {
			ranking.img_back = getImage("/ranking/back.png");
			ranking.img_number = getImage("/ranking/number.png");
		} else if (type == 1) {
			game.img_map = getImage("/game/back.png");

			game.a_img_hero = new Image[11][3];
			Image temp = null;
			int w, h;
			for (int i = 0; i < 11; i++) {
				temp = getImage("/hero/hero_" + i + ".png");
				w = temp.getWidth();
				h = temp.getHeight();
				for (int j = 0; j < 3; j++) {
					game.a_img_hero[i][j] = Image.createImage(temp, w * j / 3,
							0, w / 3, h, 0);
				}
			}

			game.a_img_point = new Image[11];
			for (int i = 0; i < 11; i++) {
				game.a_img_point[i] = getImage("/hero/point_" + i + ".png");
			}
		} else if (type == 2) {
			Image img_temp = null;
			home.img_back = getImage("/home/back.png");
			home.img_bomb = getImage("/home/bomb.png");
			home.img_point = getImage("/home/point.png");
			home.img_u_point = getImage("/home/u_point.png");

			home.ima_word = new Image[4];
			img_temp = getImage("/home/word.png");
			int w = img_temp.getWidth();
			int h = img_temp.getHeight();
			for (int i = 0; i < 4; i++) {
				home.ima_word[i] = Image.createImage(img_temp, 0, (h >> 2) * i,
						w, h >> 2, 0);
			}

			home.ima_fire = new Image[3];
			img_temp = getImage("/home/fire.png");
			w = img_temp.getWidth();
			h = img_temp.getHeight();
			for (int i = 0; i < 3; i++) {
				home.ima_fire[i] = Image.createImage(img_temp, w * i / 3, 0,
						w / 3, h, 0);
			}

			home.ima_boom = new Image[5];
			for (int i = 0; i < 5; i++) {
				home.ima_boom[i] = getImage("/home/boom_" + i + ".png");
			}
			
			img_temp = null;
		}
		gotoNextView();
	}

	public void gotoNextView() {
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		switch (type) {
		case 1:
			canvasControl.setView(game);
			break;
		case 2:
			canvasControl.setView(home);
			break;
		case 3:
			canvasControl.setView(ranking);
			break;
		}

		HttpLoadImage.imgCount = 0;

	}

	public Image getImage(String url) {
		String finalUrl = CanvasControl.imageServerUrl + url;
		C.out(finalUrl);
		return HttpLoadImage.catchImage(finalUrl);
	}

}
