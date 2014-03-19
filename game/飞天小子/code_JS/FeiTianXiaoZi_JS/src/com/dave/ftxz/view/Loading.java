package com.dave.ftxz.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.HttpLoadImage;
import com.dave.showable.Showable;

public class Loading implements Showable, Runnable {
	/**
	 * 0��load��ҳͼƬ�� 1: load��ϷͼƬ. 2: load�̳�ͼƬ�� 3: load���а�ͼƬ��4��load��Ӫѡ��ͼƬ��
	 */
	private final int type;

	private Image img_back;
	private Image img_loading_bar;

	private final CanvasControl canvasControl;

	/**
	 * ��Ϸ����
	 */
	private Game game;

	/**
	 * ��ҳ
	 */
	private Home home;

	/**
	 * �̳�
	 */
	private Shop shop;

	/**
	 * ���а�
	 */
	private Ranking ranking;

	/**
	 * �ܹ����ض�����ͼƬ
	 */
	private int imgAmount;

	public Loading(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;

		launch();
	}

	private void launch() {
		switch (type) {
		case 0:// ��ҳ
			home = new Home(canvasControl);
			imgAmount = 5;
			break;
		case 1:// ��Ϸҳ��
			game = new Game(canvasControl);
			imgAmount = 35;
			break;
		case 2:// �̳�
			shop = new Shop(canvasControl);
			imgAmount = 26;
			break;
		case 3:// ���а�
			ranking = new Ranking(canvasControl);
			imgAmount = 11;
			break;
		default:
			break;
		}
		new Thread(this).start();
	}

	public void show(Graphics g) {
		// g.setColor(0);
		// g.fillRect(0, 0, 640, 530);
		// g.setColor(255, 255, 255);
		// g.drawRect(50, 400, 540, 20);
		// g.fillRect(52, 402, 536 * HttpLoadImage.imgCount / imgAmount, 16);
		g.drawImage(img_back, 0, 0, 0);

		g.setClip(135, 0, 360 * HttpLoadImage.imgCount / imgAmount, 530);
		g.drawImage(img_loading_bar, 135, 410, Graphics.VCENTER | Graphics.LEFT);
		g.setClip(0, 0, 640, 530);

	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/loading/back.png");
			img_loading_bar = Image.createImage("/loading/loading_bar.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_loading_bar = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
	}

	public void run() {
		switch (type) {
		case 0: // ��ҳ
			home.img_back = getImage("/home/back.png");

			home.a_img_button = new Image[2];
			for (int i = 0; i < 2; i++) {
				home.a_img_button[i] = getImage("/home/btn_" + i + ".png");
			}
			home.a_img_word = new Image[2];
			for (int i = 0; i < home.a_img_word.length; i++) {
				home.a_img_word[i] = getImage("/home/word_" + i + ".png");
			}
			break;
		case 1:// ��Ϸ
			game.img_map = getImage("/game/map_" + C.R.nextInt(3) + ".png");
			game.img_airship = getImage("/game/airship.png");
			game.img_bar_coin = getImage("/game/bar_coin.png");
			game.img_bar_distance = getImage("/game/bar_distance.png");
			game.img_bar_side = getImage("/game/bar_side.png");
			game.img_key = getImage("/game/key.png");
			game.img_number_gura = getImage("/game/number_gura.png");
			game.img_hero = getImage("/game/hero_" + CanvasControl.type_hero + ".png");
			game.img_aerolite = getImage("/game/aerolite.png");
			game.img_coin = getImage("/game/coin.png");
			game.img_slow = getImage("/game/slow.png");
			game.img_sprint = getImage("/game/sprint_2.png");
			game.img_protected = getImage("/game/protected.png");

			game.a_img_bullet = new Image[5];
			for (int i = 0; i < 5; i++) {
				game.a_img_bullet[i] = getImage("/game/bullet_" + i + ".png");
			}

			game.a_img_treasure = new Image[4];
			for (int i = 0; i < 4; i++) {
				game.a_img_treasure[i] = getImage("/game/treasure_" + i + ".png");
			}
			game.a_img_pet = new Image[4];
			Image img_temp = getImage("/game/pet.png");
			for (int i = 0; i < 4; i++) {
				game.a_img_pet[i] = Image.createImage(img_temp, i * 51, 0, 51,
						46, 0);
			}
			game.a_img_attract = new Image[4];
			img_temp = getImage("/game/attract.png");
			for (int i = 0; i < 4; i++) {
				game.a_img_attract[i] = Image.createImage(img_temp, i * 89, 0, 89,
						78, 0);
			}
			game.a_img_beHit_bird = new Image[4];
			img_temp = getImage("/game/be_hit_bird.png");
			for (int i = 0; i < 4; i++) {
				game.a_img_beHit_bird[i] = Image.createImage(img_temp, i * 73, 0, 73,
						64, 0);
			}
			game.a_img_sprint = new Image[4];
			img_temp = getImage("/game/sprint_1.png");
			for (int i = 0; i < 4; i++) {
				game.a_img_sprint[i] = Image.createImage(img_temp, i * 111, 0, 111,
						154, 0);
			}
			game.a_img_boom_bird = new Image[5];
			img_temp = getImage("/game/boom_bird.png");
			for (int i = 0; i < 5; i++) {
				game.a_img_boom_bird[i] = Image.createImage(img_temp, i * 102, 0, 102,
						87, 0);
			}
			game.a_img_beHit_hero = new Image[2];
			img_temp = getImage("/game/be_hit_hero.png");
			for (int i = 0; i < 2; i++) {
				game.a_img_beHit_hero[i] = Image.createImage(img_temp, i * 199, 0, 199,
						191, 0);
			}
			game.a_img_alert = new Image[2];
			img_temp = getImage("/game/alert.png");
			for (int i = 0; i < 2; i++) {
				game.a_img_alert[i] = Image.createImage(img_temp, i * 84, 0, 84,
						84, 0);
			}
			game.a_img_bar_blood = new Image[2];
			img_temp = getImage("/game/bar_blood.png");
			for (int i = 0; i < 2; i++) {
				game.a_img_bar_blood[i] = Image.createImage(img_temp, i * 48, 0, 48,
						6, 0);
			}

			game.a_2_img_bird = new Image[5][2];
			for (int i = 0; i < 5; i++) {
				img_temp = getImage("/game/bird_" + i + ".png");
				game.a_2_img_bird[i][0] = Image.createImage(img_temp, 0, 0,
						img_temp.getWidth() >> 1, img_temp.getHeight(), 0);
				game.a_2_img_bird[i][1] = Image.createImage(img_temp,
						img_temp.getWidth() >> 1, 0, img_temp.getWidth() >> 1,
						img_temp.getHeight(), 0);
			}
			break;
		case 2:// �̳�
			shop.img_back = getImage("/shop/back.png");
			shop.img_choose = getImage("/shop/choose.png");
			shop.img_point = getImage("/shop/point.png");
			shop.img_number = getImage("/shop/number.png");
			shop.img_unit_price = getImage("/shop/yuan.png");
			shop.img_press_ok = getImage("/shop/press_ok.png");

			shop.a_img_goods = new Image[9][2];
			for (int i = 0; i < 9; i++) {
				shop.a_img_goods[i][0] = getImage("/shop/goods_name_" + i
						+ ".png");
				shop.a_img_goods[i][1] = getImage("/shop/goods_info_" + i
						+ ".png");
			}

			shop.a_img_btn_start = new Image[2];
			shop.a_img_btn_start[0] = getImage("/shop/btn_start_uc.png");
			shop.a_img_btn_start[1] = getImage("/shop/btn_start_c.png");
			break;
		case 3:// ���а�
			ranking.img_back = getImage("/ranking/back.png");
			ranking.img_number_mine = getImage("/ranking/number_mine.png");
			ranking.img_number_rank = getImage("/ranking/number_rank.png");

			ranking.a_2_img_date = new Image[2][4];
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 4; j++) {
					ranking.a_2_img_date[i][j] = getImage("/ranking/date_" + i
							+ j + ".png");
				}
			}
			break;
		default:
			break;
		}

		gotoNextView();
	}

	public void gotoNextView() {
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		switch (type) {
		case 0:// ��ҳ
			canvasControl.setView(home);
			break;
		case 1:// ��Ϸ
			canvasControl.setView(game);
			canvasControl.playerHandler.playByIndex(0);
			break;
		case 2://�̳�
			canvasControl.setView(shop);
			break;
		case 3:// ���а�
			canvasControl.setView(ranking);
			break;
		}

		System.out.println(HttpLoadImage.imgCount);
		HttpLoadImage.imgCount = 0;

	}

	public Image getImage(String url) {
		String finalUrl = CanvasControl.imageServerUrl + url;
		C.out(finalUrl);
		return HttpLoadImage.catchImage(finalUrl);
	}

	public void removeServerImage() {
		// TODO Auto-generated method stub

	}

}
