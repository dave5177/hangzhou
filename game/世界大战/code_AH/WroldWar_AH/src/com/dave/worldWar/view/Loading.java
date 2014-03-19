package com.dave.worldWar.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.tool.C;
import com.dave.worldWar.tool.HttpLoadImage;

public class Loading implements Showable, Runnable {
	/**
	 * 0：load主页图片。 1: load游戏图片. 2: load训练营图片。 3: load排行榜图片。4：load阵营选择图片。
	 */
	private final int type;

	private Image img_back;
	private Image img_buttle_bg;
	private Image img_buttle;
	private Image img_number;
	private Image img_percent_sign;

	private final CanvasControl canvasControl;

	/**
	 * 游戏界面
	 */
	private Game game;

	/**
	 * 主页
	 */
	private Home home;
	
	/**
	 * 训练营
	 */
	private Training training;
	
	
	/**
	 * 排行榜
	 */
	private Ranking ranking;
	
	/**
	 * 选择阵营
	 */
	private ChooseGroup chooseGroup;

	/**
	 * 总共加载多少张图片
	 */
	private int imgAmount;

	public Loading(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;

		launch();
	}

	private void launch() {
		switch (type) {
		case 0://主页
			home = new Home(canvasControl);
			imgAmount = 9;
			break;
		case 1://游戏页面
			game = new Game(canvasControl);
			imgAmount = 46;
			break;
		case 2://训练营]
			training = new Training(canvasControl);
			imgAmount = 23;
			break;
		case 3://排行榜
			ranking = new Ranking(canvasControl);
			imgAmount = 3;
			break;
		case 4://选择阵营
			chooseGroup = new ChooseGroup(canvasControl);
			imgAmount = 3;
			break;

		default:
			break;
		}
		new Thread(this).start();
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		
		for (int i = 0; i < 16; i++) {
			g.drawImage(img_buttle_bg, 134 + i * 25, 422, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		for (int i = 0; i < (HttpLoadImage.imgCount << 4) / imgAmount; i++) {
			g.drawImage(img_buttle, 134 + i * 25, 422, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		String percent = HttpLoadImage.imgCount * 100 / imgAmount + "";
		C.drawString(g, img_number, percent, "0123456789", 310 - (percent.length() << 3), 290, 16, 24, 0, 0, 0);
		
		g.drawImage(img_percent_sign, 312 + (percent.length() << 3), 288, 0);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/loading/back.png");
			img_buttle = Image.createImage("/loading/buttle.png");
			img_buttle_bg = Image.createImage("/loading/buttle_bg.png");
			img_back = Image.createImage("/loading/back.png");
			img_number = Image.createImage("/loading/number.png");
			img_percent_sign= Image.createImage("/loading/percent_sign.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_buttle = null;
		img_buttle_bg = null;
		img_number = null;
		img_percent_sign = null;

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
		case 0: // 首页
			home.a_img_button = new Image[5];
			if(CanvasControl.group != 0)
				home.a_img_button[0] = getImage("/home/button_0.png");
			else 
				home.a_img_button[0] = getImage("/home/button_1.png");
			
			for (int i = 1; i < 5; i++) {
				home.a_img_button[i] = getImage("/home/button_" + (i + 1) + ".png");
			}
			
			home.a_img_back = new Image[4];
			for (int i = 0; i < 4; i++) {
				home.a_img_back[i] = getImage("/home/back_" + i + ".png");
			}
			break;
		case 1://游戏
			game.img_map = getImage("/game/map.png");
			game.img_top_bar = getImage("/game/top_bar.png");
			game.img_bottom_bar = getImage("/game/bottom_bar.png");
			game.img_cd = getImage("/game/cd.png");
			game.img_cd_line = getImage("/game/cd_line.png");
			game.img_enemy_bar = getImage("/game/enemy_bar.png");
			game.img_me_bar = getImage("/game/me_bar.png");
			game.img_go_left = getImage("/game/go_left.png");
			game.img_go_right = getImage("/game/go_right.png");
			game.img_lock = getImage("/game/lock.png");
			if(CanvasControl.group == 1) {
				game.img_icon_r = getImage("/game/icon_u_r.png");
				game.img_icon_l = getImage("/game/icon_g_l.png");
			} else if(CanvasControl.group == 2){
				game.img_icon_r = getImage("/game/icon_g_r.png");
				game.img_icon_l = getImage("/game/icon_u_l.png");
			}
			game.img_friendly = getImage("/game/friendly.png");
			game.img_number = getImage("/game/number.png");
			game.img_key_bar = getImage("/game/key_bar.png");
			
			game.a_img_word_key = new Image[6];
			for (int i = 0; i < 6; i++) {
				game.a_img_word_key[i] = getImage("/game/word_key_" + (i + 1) + ".png");
			}
			
			game.a_img_head = new Image[6];
			for (int i = 0; i < 6; i++) {
				game.a_img_head[i] = getImage("/head/" + i + "_head_1.png");
			}
			
			game.a_2_img_soldier = new Image[5][5];
			for (int i = 0; i < 5; i++) {
				if(i == 1 || i == 4) {
					game.a_2_img_soldier[i][1] = getImage("/soldier/soldier_" + i + "0.png");
					game.a_2_img_soldier[i][4] = getImage("/soldier/soldier_" + i + "4.png");
					continue;
				}
				for (int j = 0; j < 5; j++) {
					game.a_2_img_soldier[i][j] = getImage("/soldier/soldier_" + i + j + ".png");
				}
			}
			break;
		case 2://训练营
			training.img_back = getImage("/training/back.png");
			training.img_choose_bar = getImage("/training/choose_bar.png");
			training.img_choose_bar_start = getImage("/training/choose_bar_start.png");
			training.img_unchoose_bar = getImage("/training/unchoose_bar.png");
			training.img_unchoose_bar_start = getImage("/training/unchoose_bar_start.png");
			training.img_number_level = getImage("/training/number_level.png");
			training.img_number_param = getImage("/training/number_param.png");
			training.img_number_price = getImage("/training/number_price.png");
			training.img_unit_price = getImage("/training/yuan_bao.png");
			training.img_lock = getImage("/training/lock.png");
			training.img_param_bar = getImage("/training/param_bar.png");
			training.img_word_start = getImage("/training/word_start.png");
			training.img_word_unlock = getImage("/training/word_unlock.png");
			training.img_word_uplevel = getImage("/training/word_uplevel.png");
			
			training.a_2_img_head = new Image[5][2];
			training.a_2_img_head[0][1] = getImage("/head/0_head_1.png");
			for (int i = 1; i < 5; i++) {
				for (int j = 0; j < 2; j++) {
					training.a_2_img_head[i][j] = getImage("/head/" + i + "_head_" + j + ".png");
				}
			}
			break;
		case 3://排行榜
			ranking.img_back = getImage("/ranking/back.png");
			ranking.img_number = getImage("/ranking/number.png");
			ranking.img_choose = getImage("/ranking/choose.png");
			break;
		case 4://阵营选择
			chooseGroup.img_back = getImage("/chooseGroup/back.png");
			chooseGroup.img_select = getImage("/chooseGroup/select.png");
			chooseGroup.img_vs = getImage("/chooseGroup/vs.png");
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
		case 0://主页
			canvasControl.setView(home);
			canvasControl.playerHandler.playByIndex(0);
			break;
		case 1://游戏
			canvasControl.setView(game);
//			canvasControl.playerHandler.stopByIndex(0);
			break;
		case 2://训练营
			canvasControl.setView(training);
			canvasControl.playerHandler.playByIndex(0);
			break;
		case 3://排行榜
			canvasControl.setView(ranking);
			canvasControl.playerHandler.playByIndex(0);
			break;
		case 4://阵营选择
			canvasControl.setView(chooseGroup);
			canvasControl.playerHandler.playByIndex(0);
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
