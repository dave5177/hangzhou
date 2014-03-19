package com.dave.jpsc.view;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

/**
 * @author Dave 挑战界面
 */
public class DuelRoom implements Showable {

	/**
	 * 更换挑战对象请求已经返回
	 */
	public static boolean requestFinish = true;

	private CanvasControl canvasControl;

	public Image img_back;
	public Image[] a_img_btn;
	public Image[] a_img_car;
	public Image[] a_img_car_small;

	private int index_choose;

	/**
	 * 挑战对象
	 */
	private Vector usersDuel;

	public DuelRoom(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		usersDuel = new Vector(4);
		createUsers();
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(
				a_img_car[canvasControl.me.cars[canvasControl.me.mainCar][0]],
				130, 240, Graphics.VCENTER | Graphics.HCENTER);

		g.setColor(255, 255, 255);
		g.setFont(C.FONT_MEDIUM_BOLD);
		g.drawString(
				canvasControl.me.nickName + "：lv" + canvasControl.me.level, 20,
				110, 0);
		g.drawString("实力值：" + canvasControl.me.strength, 20, 130, 0);
		g.drawString("今日挑战次数：" + canvasControl.me.duelTimes, 20, 150, 0);

		g.setFont(C.FONT_SMALL_PLAIN);
		int userNumber = usersDuel.size();
		if(userNumber > 4)
			userNumber = 4;
		for (int i = 0; i < userNumber; i++) {
			Player user = ((Player) usersDuel.elementAt(i));
			g.drawString(user.nickName + "：lv" + user.level,
					(i % 2) * 170 + 300, (i >> 1) * 170 + 120, 0);
			g.drawString("实力值：" + user.strength, (i % 2) * 170 + 300,
					(i >> 1) * 170 + 140, 0);
			g.drawImage(a_img_car_small[user.cars[user.mainCar][0]],
					(i % 2) * 170 + 370, (i >> 1) * 170 + 210, Graphics.VCENTER
							| Graphics.HCENTER);

			g.drawImage(a_img_btn[0], (i % 2) * 170 + 370,
					(i >> 1) * 170 + 250, Graphics.VCENTER | Graphics.HCENTER);
		}
		if(usersDuel.size() > 0) {
			g.drawImage(a_img_btn[1], (index_choose % 2) * 170 + 370,
					(index_choose >> 1) * 170 + 250, Graphics.VCENTER
					| Graphics.HCENTER);
		}
	}

	public void loadResource() {
	}

	public void removeResource() {

	}

	public void keyPressed(int keyCode) {
		int userNumber = usersDuel.size();
		if(userNumber > 4)
			userNumber = 4;
		switch (keyCode) {
		case C.KEY_LEFT:
			if (index_choose > 0)
				index_choose--;
			break;
		case C.KEY_RIGHT:
			if (index_choose < userNumber - 1)
				index_choose++;
			break;
		case C.KEY_UP:
			if (index_choose > 1)
				index_choose -= 2;
			break;
		case C.KEY_DOWN:
			if (index_choose < 2 && userNumber > index_choose + 2)
				index_choose += 2;
			break;
		case C.KEY_FIRE://挑战
			if(usersDuel.size() > index_choose) {
				if(canvasControl.me.duelTimes > 0) {
					canvasControl.me.duelTimes --;
					canvasControl.saveParam();
					
					CanvasControl.mission = C.R.nextInt(CanvasControl.missionPassed) + 1;
					canvasControl.setView(canvasControl.nullView);
					this.removeServerImage();
					this.removeResource();
					canvasControl.setView(new Loading(canvasControl, 7, Game.MODE_DUAL, (Player) usersDuel.elementAt(index_choose)));
				} else {
					canvasControl.buyGoods(0);
				}
			}
			break;
		case C.KEY_1:// 刷新
			updateUsers();
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeServerImage();
			this.removeResource();
			canvasControl.setView(new Home(canvasControl));
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if (requestFinish) {
			requestFinish = false;
			createUsers();
		}
	}

	public void removeServerImage() {
		img_back = null;

		for (int i = 0; i < a_img_btn.length; i++) {
			a_img_btn[i] = null;

		}
		a_img_btn = null;

		for (int i = 0; i < a_img_car.length; i++) {
			a_img_car[i] = null;
			a_img_car_small[i] = null;
		}
		a_img_car = null;
		a_img_car_small = null;

		System.gc();
	}

	/**
	 * 更新挑战对象
	 */
	private void updateUsers() {
		usersDuel.removeAllElements();
		new ServerIptv(canvasControl).getDuelInfo();
		requestFinish = false;
	}

	private void createUsers() {
		usersDuel.removeAllElements();
		if (CanvasControl.duelInfo != null) {
			int size = CanvasControl.duelInfo.size();
			for (int i = 0; i < size; i++) {
				String[] user_params = (String[]) CanvasControl.duelInfo
						.elementAt(i);
				if(user_params[0] == null || user_params[0].equals("") || user_params[0].length() < 10)
					continue;
				
				Player duelPlayer = canvasControl
						.createPlayerByParam(user_params);
				if(duelPlayer != null)
					usersDuel.addElement(duelPlayer);
			}
			C.out("用户数量：" + usersDuel.size());
		}
	}

	public void handleGoods(int goodsIndex) {
		if(goodsIndex == 0) {
			canvasControl.me.duelTimes += 10;
		}
	}
	
}
