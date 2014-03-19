package com.dave.jpsc.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.net.Prop;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

public class Dialog implements Showable {
	private CanvasControl canvasControl;

	/**
	 * 对话框处在的界面
	 */
	private Showable lastView;

	/**
	 * 对话框类型控制 0：退出整个程序对话框。 1：退出游戏对话框。2：自定义对话框。 3：购买成功。 4：购买失败。 5：购买道具。 6：闯关成功。
	 * 7：燃油耗尽。8：时间耗尽。 10：当月购买已达到峰值。11：推荐赛车。
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
	private Image img_car;
	// private Image img_restart_btn;
	private Image img_button;
	private Image img_pointer;

	/**
	 * 所处的游戏（只有退出游戏的时候用到），用完后置空。
	 */
	public static Game game;
	
	public static int carIndex;

	public Dialog(CanvasControl canvasControl, int type, Showable lastView) {
		this.canvasControl = canvasControl;
		this.lastView = lastView;

		this.type = type;

		if (type == 3 || type == 4 || type == 10) {
			new Timer().schedule(new AutoCloseDialogTimerTask(), 1000);
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, C.WIDTH / 2, 254, Graphics.VCENTER
				| Graphics.HCENTER);// draw背景


		if(type == 11) {
			g.drawImage(img_word, C.WIDTH / 2, 180, Graphics.VCENTER
					| Graphics.HCENTER);// draw对话框文字
			g.drawImage(img_car, C.WIDTH / 2, 260, Graphics.VCENTER
					| Graphics.HCENTER);
		} else {
			g.drawImage(img_word, C.WIDTH / 2, 220, Graphics.VCENTER
					| Graphics.HCENTER);// draw对话框文字
		}

		if (type == 3 || type == 4 || type == 10)
			return;

		// draw选择bar
		g.drawImage(img_button, 250, 320, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(img_button, 410, 320, Graphics.HCENTER | Graphics.VCENTER);

		g.drawImage(img_pointer, 250 + index * 160, 320, Graphics.HCENTER
				| Graphics.VCENTER);
		g.drawImage(img_yesWord, 250, 320, Graphics.HCENTER | Graphics.VCENTER);// draw文字”是“
		g.drawImage(img_noWord, 410, 320, Graphics.HCENTER | Graphics.VCENTER);// draw文字”否“
	}

	/**
	 * // * @param g // * draw道具的价格 //
	 */
	// private void showGoodsPrice(Graphics g) {
	// C.drawString(g, img_number,
	// CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][1],
	// "0123456789.", 235, 208, 10, 15, 0, 0, 0);
	// g.drawImage(img_unit_price,
	// 235 + CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][1]
	// .length() * 10, 206, 0);
	// }

	public void loadResource() {
		try {
			img_back = Image.createImage("/dialog/back.png");
			img_button = Image.createImage("/dialog/button.png");
			loadWorldImage();

			img_pointer = Image.createImage("/dialog/pointer.png");

			if (type == 7 || type == 8) {
				img_yesWord = Image.createImage("/dialog/yes_btn_" + type
						+ ".png");
				img_noWord = Image.createImage("/dialog/quit_btn.png");
			} else if(type == 11) {
				img_car =  Image.createImage("/dialog/car_" + carIndex + ".png");
				img_yesWord = Image.createImage("/dialog/yesWord_11.png");
				img_noWord = Image.createImage("/dialog/noWord_11.png");
			} else {
				img_yesWord = Image.createImage("/dialog/yesWord.png");
				img_noWord = Image.createImage("/dialog/noWord.png");
			}

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
		img_car = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_FIRE:
			if (index == 0) {
				switch (type) {
				case 0:// 退出游戏
					CanvasControl.willExit = true;
					canvasControl.saveParam();
					new ServerIptv(canvasControl).sendGameTimeInfo(
							CanvasControl.appStartTime + CanvasControl.iptvID,
							"1", "update");
					break;
				case 1:// 返回，到首页
					CanvasControl.usingCar = canvasControl.me.mainCar;

					if (game.getMode() != Game.MODE_MISSION) {// 挑战模式
						game.duelPlayer.strength += 100;

						game.duelPlayer.upLoadMsg(Player.WIN,
								CanvasControl.iptvID);
						game.duelPlayer.updateParam();
						game.duelPlayer.saveScore();

						lastView.removeServerImage();
						canvasControl.setGoBackView(null);
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						canvasControl.setView(new Loading(canvasControl, 2));
					} else {
						lastView.removeServerImage();
						canvasControl.setGoBackView(null);
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						canvasControl.setView(new Loading(canvasControl, 1));
					}

					canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
					canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
					canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;

					for (int i = 1; i < 4; i++) {
						if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
							canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
						}
					}
					CanvasControl.gamesTotal++;
					canvasControl.saveScore();
					canvasControl.saveParam();
					game = null;
					
					break;
				case 5:// 购买道具
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					new Prop(canvasControl)
							.buyProp(
									CanvasControl.GAME_PROP_CODE,
									CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][0]);
					break;
				case 6:
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Loading(canvasControl, 2));
					break;
				case 7:// 购买燃油
					canvasControl.buyGoods(12);
					break;
				case 8:// 时间耗尽，购买时间
					canvasControl.buyGoods(10);
					break;
				case 11://使用推荐车辆
					int mycarIndex = canvasControl.me.getMyCarIndex(carIndex);
					if (mycarIndex >= 0) {//有这辆车
						CanvasControl.usingCar = mycarIndex;
						game.changeCar(carIndex);
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						canvasControl.setView(lastView);
					} else {//没有这辆车
						canvasControl.buyGoods(carIndex);
					}
					break;

				}
				System.gc();
			} else if (index == 1) {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				if (type == 7 || type == 8) {
					CanvasControl.tarAchieve[0] = false;
					CanvasControl.tarAchieve[1] = false;
					CanvasControl.tarAchieve[2] = false;

					CanvasControl.rankInGame = game.amount_car;
					CanvasControl.gameTimeStr = C.computeTimeStr(game.gameTime
							* Game.TIME_MUL, true);

					CanvasControl.passSuc = 1;
					game.handleFailed();

					game.gotoCheckOutView();
					
					/*CanvasControl.usingCar = canvasControl.me.mainCar;

					if (game.getMode() != Game.MODE_MISSION) {// 挑战模式
						game.duelPlayer.strength += 100;

						game.duelPlayer.upLoadMsg(Player.WIN,
								CanvasControl.iptvID);
						game.duelPlayer.updateParam();
						game.duelPlayer.saveScore();

						lastView.removeServerImage();
						canvasControl.setGoBackView(null);
						canvasControl.setView(new Loading(canvasControl, 2));
					} else {
						lastView.removeServerImage();
						canvasControl.setGoBackView(null);
						canvasControl.setView(new Loading(canvasControl, 1));
					}

					canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
					canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
					canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;

					for (int i = 1; i < 4; i++) {
						if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
							canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
						}
					}
					CanvasControl.gamesTotal++;
					canvasControl.saveScore();
					canvasControl.saveParam();
					game = null;*/
					
				} else {
					if (lastView instanceof Game)
						canvasControl.playerHandler.playByIndex(0);
					canvasControl.setView(lastView);
				}
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
			if (type == 7 || type == 8) {
				CanvasControl.usingCar = canvasControl.me.mainCar;
				
				if (game.getMode() != Game.MODE_MISSION) {//挑战模式
					game.duelPlayer.strength += 100;

					game.duelPlayer.upLoadMsg(Player.WIN,
							CanvasControl.iptvID);
					game.duelPlayer.updateParam();
					game.duelPlayer.saveScore();

					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(new Loading(canvasControl, 2));
				} else {
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(new Loading(canvasControl, 1));
				}
				
				canvasControl.me.cars[CanvasControl.usingCar][1] -= 6;
				canvasControl.me.cars[CanvasControl.usingCar][2] -= 4;
				canvasControl.me.cars[CanvasControl.usingCar][3] -= 5;
				
				for (int i = 1; i < 4; i++) {
					if (canvasControl.me.cars[CanvasControl.usingCar][i] < 0) {
						canvasControl.me.cars[CanvasControl.usingCar][i] = 0;
					}
				}
				CanvasControl.gamesTotal++;
				canvasControl.saveScore();
				canvasControl.saveParam();
				game = null;
				
			} else {
				if (lastView instanceof Game)
					canvasControl.playerHandler.playByIndex(0);
				canvasControl.setView(lastView);
			}
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
			if (type == 3)
				canvasControl.doBuySuccess();

			canvasControl.setView(canvasControl.nullView);
			removeResource();
			if (CanvasControl.goodsIndex == 9 && type != 3) {
				canvasControl.setView(new Dialog(canvasControl, 7,
						canvasControl.getGoBackView()));
			} else {
				canvasControl.setView(canvasControl.getGoBackView());
			}
			canvasControl.repaint();
			Dialog.this.removeResource();
		}
	}

	public void removeServerImage() {

	}

	public void handleGoods(int goodsIndex) {
		lastView.handleGoods(goodsIndex);

		if (lastView instanceof Game)
			canvasControl.playerHandler.playByIndex(0);

		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		this.removeServerImage();
		canvasControl.setView(lastView);
	}

}
