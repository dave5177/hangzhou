package com.dave.jpsc.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dalin.fjiptv.prop.FJ_IPTV_PORP_TOOL;
import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.tool.HttpLoadImage;
import com.dave.showable.Showable;

public class Loading implements Showable, Runnable {
	/**
	 * 0：load主页图片。 1: load关卡选择. 2: load挑战界面。 3: load骑车超市。4：load排行榜。 5：查看消息。6:
	 * 我的车库。7:游戏区。8：关卡结算界面。9：副本选择。100：输入昵称界面。
	 */
	private final int type;

	private Image img_back;
	private Image[] a_img_loading_bar;
	private Image img_point;
	private Image img_msg;

	private Image img_nick;
	private Image[] a_img_btn;
	private Image[] a_img_choose_btn;

	private int index_choose;
	
	/**
	 * 弧形进度条坐标
	 */
	private int x_bar, y_bar;
	
	/**
	 * 进度
	 */
	private int rate;

	private final CanvasControl canvasControl;

	/**
	 * 游戏界面
	 */
	// private Game game;

	/**
	 * 主页
	 */
	private Home home;

	/**
	 * 关卡选择
	 */
	private MissionChoose missionChoose;

	/**
	 * 挑战大厅
	 */
	private DuelRoom duelRoom;

	/**
	 * 汽车超市
	 */
	private CarShop carShop;

	/**
	 * 查看消息
	 */
	private Message message;

	/**
	 * 我的车库
	 */
	private Garage garage;

	/**
	 * 排行榜
	 */
	private Ranking ranking;

	/**
	 * 游戏区
	 */
	private Game game;

	/**
	 * 游戏结算
	 */
	private Checkout checkout;
	
	/**
	 * 副本
	 */
	private Instance instance;

	/**
	 * 总共加载多少张图片
	 */
	private int imgAmount = 10;

	private int gameMode;

	/**
	 * 挑战对手
	 */
	private Player duelPlayer;

	/**
	 * @param canvasControl
	 * @param type
	 *            0：load主页图片。 1: load关卡选择. 2: load挑战界面。 3: load骑车超市。4：load排行榜。
	 *            5：查看消息。6: 我的车库。7:游戏区。8：关卡结算界面。9：副本选择。100：输入昵称界面。
	 */
	public Loading(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;

		launch();
	}
	
	public Loading(CanvasControl canvasControl, int type, int gameMode, Player duelPlayer) {
		this.canvasControl = canvasControl;
		this.type = type;
		this.gameMode = gameMode;
		this.duelPlayer = duelPlayer;
		
		launch();
	}

	private void launch() {
		switch (type) {
		case 0:// 主页
			home = new Home(canvasControl);
			imgAmount = 13;
			break;
		case 1:// 关卡选择
			missionChoose = new MissionChoose(canvasControl);
			imgAmount = 37;
			break;
		case 2:// 挑战大厅
			duelRoom = new DuelRoom(canvasControl);
			imgAmount = 21;
			break;
		case 3:// 汽车超市
			carShop = new CarShop(canvasControl);
			imgAmount = 33;
			break;
		case 4:// 排行榜
			ranking = new Ranking(canvasControl);
			imgAmount = 6;
			break;
		case 5://查看消息
			message = new Message(canvasControl);
			imgAmount = 5;
			break;
		case 6://车库
			garage = new Garage(canvasControl);
			imgAmount = 31;
			break;
		case 7://游戏区
			if(gameMode == Game.MODE_DUAL) {
				game = new Game(canvasControl, gameMode, duelPlayer);
			} else if(gameMode == Game.MODE_MISSION) {
				game = new Game(canvasControl);
			}
			imgAmount = 15;
			break;
		case 8://结算界面
			checkout = new Checkout(canvasControl, CanvasControl.passSuc);
			imgAmount = 23;
			break;
		case 9://副本
			instance = new Instance(canvasControl);
			imgAmount = 14;
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
		int rate_temp = 10 * HttpLoadImage.imgCount / imgAmount;
		for (int i = 0; i < rate_temp; i++) {
			switch (i) {
			case 0:
				x_bar = 191;
				y_bar = 269;
				break;
			case 1:
				x_bar = 199;
				y_bar = 230;
				break;
			case 2:
				x_bar = 222;
				y_bar = 196;
				break;
			case 3:
				x_bar = 256;
				y_bar = 171;
				break;
			case 4:
				x_bar = 295;
				y_bar = 157;
				break;
			case 5:
				x_bar = 337;
				y_bar = 156;
				break;
			case 6:
				x_bar = 378;
				y_bar = 168;
				break;
			case 7:
				x_bar = 412;
				y_bar = 192;
				break;
			case 8:
				x_bar = 438;
				y_bar = 225;
				break;
			case 9:
				x_bar = 451;
				y_bar = 267;
				break;

			default:
				break;
			}
			g.drawImage(a_img_loading_bar[i], x_bar, y_bar, Graphics.VCENTER | Graphics.HCENTER);
		}
		g.drawImage(img_point, 317, 305, Graphics.BOTTOM | Graphics.HCENTER);
		g.drawImage(img_msg, 320, 510, Graphics.VCENTER | Graphics.HCENTER);

//		g.setClip(135, 0, 360 * HttpLoadImage.imgCount / imgAmount, 530);
//		g.drawImage(img_loading_bar, 135, 410, Graphics.VCENTER | Graphics.LEFT);
//		g.setClip(0, 0, 640, 530);

		if (type == 100) {
			g.drawImage(img_nick, 320, 200, Graphics.VCENTER | Graphics.HCENTER);
			for (int i = 0; i < 2; i++) {
				g.drawImage(a_img_btn[i], 260 + i * 120, 250, Graphics.HCENTER
						| Graphics.VCENTER);
			}
			g.drawImage(a_img_choose_btn[index_choose],
					260 + index_choose * 120, 250, Graphics.HCENTER
							| Graphics.VCENTER);
			
			g.setColor(0x00ff00);
			g.setFont(C.FONT_LARGE_BLOD);
			g.drawString(CanvasControl.nickFromSer, 200, 185, 0);
		}
		
		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION + FJ_IPTV_PORP_TOOL.fj_PropVersion, 630, 10, Graphics.RIGHT | Graphics.TOP);

	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/loading/back.jpg");
			a_img_loading_bar = new Image[10];
			for (int i = 0; i < 10; i++) {
				a_img_loading_bar[i] = Image.createImage("/loading/bar_"
						+ (i * 10 + 10) + ".png");
			}
			img_point = Image.createImage("/loading/point_0.png");
			img_msg = Image.createImage("/loading/msg_" + C.R.nextInt(2)
					+ ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (type == 100) {
			try {
				img_nick = Image.createImage("/loading/nick.png");
				a_img_btn = new Image[2];
				for (int i = 0; i < 2; i++) {
					a_img_btn[i] = Image.createImage("/loading/btn_" + i
							+ ".png");
				}
				a_img_choose_btn = new Image[2];
				for (int i = 0; i < 2; i++) {
					a_img_choose_btn[i] = Image
							.createImage("/loading/choose_btn_" + i + ".png");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeResource() {
		// img_back = null;
		// img_loading_bar = null;
		if (type == 100) {
			img_nick = null;
			for (int i = 0; i < 2; i++) {
				a_img_btn[i] = null;
				a_img_choose_btn = null;
			}
			a_img_btn = null;
			a_img_choose_btn = null;
		}

		System.gc();
	}

	public void keyPressed(int keyCode) {
		if (type == 100) {
			switch (keyCode) {
			case C.KEY_LEFT:
				index_choose = 0;
				break;
			case C.KEY_RIGHT:
				index_choose = 1;
				break;
			case C.KEY_FIRE:
				if (index_choose == 0) {// 更换
					new ServerIptv(canvasControl).getRandName();
				} else {// 进入游戏
					int[][] myCars = {
							{0, 100, 100, 100, 0}
					};
					canvasControl.me = new Player(CanvasControl.nickFromSer, 1, 100, 0, 10, 0, myCars, CanvasControl.iptvID, canvasControl);
					canvasControl.saveParam();
					canvasControl.saveScore();
					canvasControl.setView(canvasControl.nullView);
					removeResource();
					canvasControl.setView(new Loading(canvasControl, 0));
				}
				break;

			default:
				break;
			}
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		int rate_now = 10 * HttpLoadImage.imgCount / imgAmount;
		if(rate_now != rate) {
			rate = rate_now;
			updateImagePoint();
		}
	}

	private void updateImagePoint() {
		try {
			img_point = Image.createImage("/loading/point_" + rate + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		switch (type) {
		case 0: // 首页
			home.img_back = getImage("/home/back.jpg");
			home.img_msg = getImage("/home/msg.png");
			home.img_msg_word = getImage("/home/msg_word.png");
			home.img_number = getImage("/home/number.png");
			home.img_number_back = getImage("/home/number_back.png");

			home.a_img_button = new Image[8];
			for (int i = 0; i < 8; i++) {
				home.a_img_button[i] = getImage("/home/btn_" + i + ".png");
			}
			break;
		case 1:// 关卡选择
			missionChoose.a_img_icon = new Image[16][2];
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 2; j++) {
					missionChoose.a_img_icon[i][j] = getImage("/missionChoose/icon_"
							+ i + "_" + j + ".png");
				}
			}

			missionChoose.img_back = getImage("/missionChoose/back.jpg");
			missionChoose.img_lock = getImage("/missionChoose/lock.png");
			missionChoose.img_param_back = getImage("/missionChoose/param_back.png");
			missionChoose.a_img_star = new Image[2];
			Image img_temp = getImage("/missionChoose/star.png");
			for (int i = 0; i < 2; i++) {
				missionChoose.a_img_star[i] = Image.createImage(img_temp,
						i * 19, 0, 19, 17, 0);
			}

			missionChoose.a_img_select = new Image[3];
			img_temp = getImage("/missionChoose/select.png");
			for (int i = 0; i < 3; i++) {
				missionChoose.a_img_select[i] = Image.createImage(img_temp,
						i * 59, 0, 59, 49, 0);
			}

			break;
		case 2:// 挑战大厅
			duelRoom.img_back = getImage("/duelRoom/back.jpg");
			duelRoom.a_img_car = new Image[9];
			duelRoom.a_img_car_small = new Image[9];
			for (int i = 0; i < 9; i++) {
				duelRoom.a_img_car[i] = getImage("/duelRoom/car_" + i + ".png");
				duelRoom.a_img_car_small[i] = getImage("/duelRoom/car_small_"
						+ i + ".png");
			}
			duelRoom.a_img_btn = new Image[2];
			for (int i = 0; i < 2; i++) {
				duelRoom.a_img_btn[i] = getImage("/duelRoom/btn_" + i + ".png");
			}
			break;
		case 3:
			carShop.img_back = getImage("/carShop/back.png");
			carShop.img_bottom = getImage("/carShop/bottom.png");
			carShop.img_number = getImage("/carShop/number.png");
			carShop.img_own = getImage("/carShop/own.png");
			carShop.img_pre_bar = getImage("/carShop/pre_bar.png");
			carShop.img_stage = getImage("/carShop/stage.png");
			carShop.img_select = getImage("/carShop/select.png");

			carShop.a_img_btn = new Image[3];
			for (int i = 0; i < 3; i++) {
				carShop.a_img_btn[i] = getImage("/carShop/btn_" + i + ".png");
			}

			carShop.a_img_choose_btn = new Image[3];
			for (int i = 0; i < 3; i++) {
				carShop.a_img_choose_btn[i] = getImage("/carShop/choose_btn_"
						+ i + ".png");
			}

			carShop.a_img_car = new Image[9];
			carShop.a_img_car_name = new Image[9];
			for (int i = 0; i < 9; i++) {
				carShop.a_img_car[i] = getImage("/carShop/car_" + i + ".png");
				carShop.a_img_car_name[i] = getImage("/carShop/car_name_" + i
						+ ".png");
			}

			carShop.a_img_arrow = new Image[2];
			for (int i = 0; i < 2; i++) {
				carShop.a_img_arrow[i] = getImage("/carShop/arrow_" + i
						+ ".png");
			}
			break;
		case 4:// 排行榜
			ranking.img_back = getImage("/ranking/back.png");
			ranking.img_rank_type = getImage("/ranking/rank_type.png");
			ranking.img_select = getImage("/ranking/select.png");

			ranking.a_img_word_bar = new Image[3];
			for (int i = 0; i < ranking.a_img_word_bar.length; i++) {
				ranking.a_img_word_bar[i] = getImage("/ranking/word_bar_" + i
						+ ".png");
			}
			break;
		case 5:
			message.img_back = getImage("/message/back.jpg");
			message.a_img_btn = new Image[4];
			for (int i = 0; i < 4; i++) {
				message.a_img_btn[i] = getImage("/message/btn_" + i + ".png");
			}
			break;
		case 6:// 我的车库
			garage.img_back = getImage("/garage/back.jpg");
			garage.img_blink = getImage("/garage/blink.png");
			garage.img_main = getImage("/garage/main.png");
			garage.img_select = getImage("/garage/select.png");
			garage.img_unknow = getImage("/garage/unknow.png");
			garage.img_pre_bar = getImage("/garage/pre_bar.png");
			garage.img_number = getImage("/garage/number.png");

			garage.a_img_btn = new Image[3];
			garage.a_img_choose_btn = new Image[3];
			for (int i = 0; i < 3; i++) {
				garage.a_img_btn[i] = getImage("/garage/btn_" + i + ".png");
				garage.a_img_choose_btn[i] = getImage("/garage/choose_btn_" + i
						+ ".png");
			}

			garage.a_img_car = new Image[9];
			garage.a_img_car_small = new Image[9];
			for (int i = 0; i < 9; i++) {
				garage.a_img_car[i] = getImage("/garage/car_" + i + ".png");
				garage.a_img_car_small[i] = getImage("/garage/small_car_" + i
						+ ".png");
			}
			break;
		case 7:// 游戏
			game.a_img_map = new Image[4];
			for (int i = 0; i < 4; i++) {
				game.a_img_map[i] = getImage("/map_segm/map_" + CanvasControl.mission
						+ "_0" + (i + 1) + ".jpg");
			}
			if(game.num_cover > 0) {
				game.a_img_cover = new Image[game.num_cover];
				for (int i = 0; i < game.num_cover; i++) {
					game.a_img_cover[i] = getImage("/map/map_above_" + CanvasControl.mission + "_" + i
					+ ".png");
				}
			}
			game.img_small_map = getImage("/map/small_map_" + CanvasControl.mission
					+ ".png");
			game.img_yellow_point = getImage("/map/yellow_point.png");
			game.img_top = getImage("/game/top.png");
			game.img_bottom = getImage("/game/bottom.png");
			game.img_num_speed = getImage("/game/num_speed.png");
			game.img_oil_bar = getImage("/game/oil_bar.png");
			game.img_car_slow = getImage("/game/car_slow.png");
			game.a_img_car_blind = new Image[2];
			for (int i = 0; i < 2; i++) {
				game.a_img_car_blind[i] = getImage("/game/car_blind_" + i + ".png");
			}
			break;
		case 8://结算界面

			checkout.img_back = getImage("/checkout/back.jpg");
			checkout.img_achieve = getImage("/checkout/achieve.png");
			checkout.img_number_power = getImage("/checkout/number_power.png");
			checkout.a_img_btn = new Image[5];
			for (int i = 0; i < 5; i++) {
				checkout.a_img_btn[i] = getImage("/checkout/btn_" + i + ".png");
			}
			checkout.a_img_cbtn = new Image[5];
			for (int i = 0; i < 5; i++) {
				checkout.a_img_cbtn[i] = getImage("/checkout/cbtn_" + i
						+ ".png");
			}
			checkout.a_img_star = new Image[2];
			for (int i = 0; i < 2; i++) {
				checkout.a_img_star[i] = getImage("/checkout/star_" + i
						+ ".png");
			}
			checkout.a_img_title = new Image[2];
			for (int i = 0; i < 2; i++) {
				checkout.a_img_title[i] = getImage("/checkout/title_" + i
						+ ".png");
			}
			
			checkout.a_img_add = new Image[3];
			checkout.a_img_minus = new Image[3];
			for (int i = 0; i < 3; i++) {
				checkout.a_img_add[i] = getImage("/checkout/add_" + i + ".png");
				checkout.a_img_minus[i] = getImage("/checkout/minus_" + i + ".png");
			}
			break;
		case 9:
			instance.img_back = getImage("/instance/back.png");
			instance.img_back_param = getImage("/instance/back_param.png");
			instance.img_back_param_unopen = getImage("/instance/back_param_unopen.png");
			instance.img_lock = getImage("/instance/lock.png");
			instance.img_number = getImage("/instance/number.png");
			instance.a_img_map_show = new Image[9];
			for (int i = 0; i < 9; i++) {
				instance.a_img_map_show[i] = getImage("/instance/map_show_" + i + ".jpg");
			}
			break;
		default:
			break;
		}

		if (type != 100)
			gotoNextView();

	}

	public void gotoNextView() {
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		switch (type) {
		case 0:// 主页
			canvasControl.setView(home);
			break;
		case 1:// 关卡选择
			missionChoose.resetParamPoint();
			canvasControl.setView(missionChoose);
			break;
		case 2:// 挑战大厅
			canvasControl.setView(duelRoom);
			break;
		case 3:// 汽车超市
			canvasControl.setView(carShop);
			break;
		case 4:// 排行榜
			canvasControl.setView(ranking);
			break;
		case 5:// 查看消息
			canvasControl.setView(message);
			break;
		case 6:// 我的车库
			canvasControl.setView(garage);
			break;
		case 7:// 游戏区
			game.init();
			canvasControl.setView(game);
			break;
		case 8:
			canvasControl.setView(checkout);
			break;
		case 9:
			canvasControl.setView(instance);
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

	public void handleGoods(int goodsIndex) {
		
	}

}
