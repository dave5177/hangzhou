package com.zhangniu.game;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.zhangniu.prop.Props;
import com.zhangniu.update.ServerIptv;

public class Alert {

	private Image[] imagea_GetScore;

	private Timer timer;

	public Screen s;

	private int[] buttonTwoArray = { 242, 345, 402, 345 };

	public int buttonindex;

	/**
	 * 提示类的图片资源
	 */
	private Image[] imagea_alter;

	/**
	 * 小图
	 */
	private Image[] imagea_BuyNumber;

	/**
	 * 字符buffer
	 */
	public StringBuffer sb;

	/**
	 * 构造函数
	 */
	public Alert(Screen screen) {
		s = screen;
		timer = new Timer();
		if (imagea_alter == null)
			imagea_alter = new Image[9];
		if (imagea_BuyNumber == null)
			imagea_BuyNumber = new Image[8];
	}

	/**
	 * 导入图片资源
	 * 
	 * @param g
	 */
	public void loadSource() {
		excurrenttime = System.currentTimeMillis();

		// 30开头关卡模式下的提示框
		// 50开关计时模式下的提示框
		// 70开关挑战模式下的提示框
		if (imagea_alter == null)
			imagea_alter = new Image[7];
		if (imagea_BuyNumber == null)
			imagea_BuyNumber = new Image[8];

		switch (C.alertType) {
		case 31:// 从第1关到第10关
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40: {
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			Image once = C.GetImageSource("/alertMessage/levelnumber.png");
			imagea_alter[1] = Image.createImage(once, 0, (C.level) * 38, 103,
					38, 0);
			imagea_alter[2] = C.GetImageSource("/alertMessage/anything.png");
			once = null;
			System.gc();
		}
			break;
		case 51:// 倒计时模式下，询问是否购买时间
		case 41: {// 没有时间了，询问是否购买时间
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// 外框
			imagea_alter[2] = C.GetImageSource("/button/1.png");// 选择
			imagea_alter[3] = C
					.GetImageSource("/alertMessage/inadequateTime.png");// 提示框内容
			imagea_alter[4] = C.GetImageSource("/button/12.png");// 确定
			imagea_alter[5] = C.GetImageSource("/button/2.png");// 取消
			buttonindex = 1;// 取消状态
			priceX = 395;
			priceY = 210;
			propID = 0;
		}
			break;
		case 72:// 挑战模式下 购买托管器
		case 52:// 倒计时模式下 购买托管器
		case 42: {// 闯关模式下 购买托管器
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // 选中箭头
			C.GetImageSource("/button/1.png");
			imagea_alter[3] = // 文字，更多的时间
			C.GetImageSource("/alertMessage/ai.png");
			imagea_alter[4] = // 确认购买
			C.GetImageSource("/button/12.png");
			imagea_alter[5] = // 返回
			C.GetImageSource("/button/2.png");
			buttonindex = 1;// 按钮放在取消位置上
			priceX = 310;
			priceY = 213;
			propID = 1;
		}
			break;
		case 53:// 在倒计时模式下返回主菜单
		case 43: {// 返回游戏主菜单界面
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");
			imagea_alter[3] = C.GetImageSource("/alertMessage/pay.png");
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/2.png");
		}
			break;
		case 44: {// 恭喜成功进入下一关
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// 背景
			imagea_alter[2] = C.GetImageSource("/button/1.png");// 光标
			imagea_alter[3] = C.GetImageSource("/alertMessage/success.png");// 闯关成功
			imagea_alter[4] = C.GetImageSource("/button/goon.png");// 继续游戏
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");// 砸金蛋
		}
			break;
		case 45: {// 很遗憾，闯关失败
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// 背景
			imagea_alter[2] = C.GetImageSource("/button/1.png");// 光标
			imagea_alter[3] = C.GetImageSource("/alertMessage/fail.png");// 闯关失败
			imagea_alter[4] = C.GetImageSource("/button/goon.png");// 继续游戏
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");// 砸金蛋
		}
			break;
		case 50: {// 倒计时模式 按任意键开始游戏,在倒计时模式下
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[1] = C.GetImageSource("/alertMessage/anything.png");
		}
			break;
		case 54: {// 游戏结束，本次您获得了1000积分.
			Image once = C.GetImageSource("/smashgoldeggs/7.png");
			imagea_GetScore = new Image[10];
			for (int i = 0; i < 10; i++) {
				imagea_GetScore[i] = Image.createImage(once, i * 17, 0, 17, 20,
						0);
			}
			once = null;

			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");// 背景
			imagea_alter[2] = C.GetImageSource("/button/1.png");// 光标
			imagea_alter[3] = C.GetImageSource("/alertMessage/goscore.png");// 游戏结束，获得
			imagea_alter[4] = C.GetImageSource("/button/restart.png");// 继续游戏
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");// 砸金蛋
		}
			break;
		case 71: {// 挑战模式 按任意键开始游戏,在倒计时模式下
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[1] = C.GetImageSource("/alertMessage/anything.png");
		}
			break;
		case 0: {// 进入游戏前的第几关 提示进入第几关
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[1] = C
					.GetImageSource("/alertMessage/firstcontent.png");
		}
			break;
		case 2: {// 退出游戏，返回菜单界面
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");
			imagea_alter[3] = C.GetImageSource("/alertMessage/pay.png");
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/2.png");
		}
			break;
		case 6: {// 购买道具成功
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // 购买成功 文字说明
			C.GetImageSource("/alertMessage/successbuy.png");
		}
			break;
		case 7: {// 已经到达订购峰值
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // 已经到达峰值
			C.GetImageSource("/alertMessage/toptobuy.png");
		}
			break;
		case 8: {// 您的帐户余额不足
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // 您的余额不足
			C.GetImageSource("/alertMessage/notenoughtgold.png");
		}
			break;
		case 9: {// 购买失败请重试
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // 购买失败请重试
			C.GetImageSource("/alertMessage/failedtry.png");
		}
			break;
		case 10: {// 购买托管器
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = // 选中箭头
			C.GetImageSource("/button/1.png");
			imagea_alter[3] = // 文字，更多的时间
			C.GetImageSource("/alertMessage/ai.png");
			imagea_alter[4] = // 确认购买
			C.GetImageSource("/button/12.png");
			imagea_alter[5] = // 返回
			C.GetImageSource("/button/2.png");
			buttonindex = 1;
			priceX = 310;
			priceY = 213;
			propID = 1;
		}
			break;
		case 11: {
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // 确认按钮
			C.GetImageSource("/button/13.png");
			imagea_alter[2] = // 您没有金锤，可以通过以下方式
			C.GetImageSource("/alertMessage/notenoughtgoldhammer.png");
		}
			break;
		case 12: {// 在道具商场界面、按1,2,3,4个键分别去购买金锤
			Image imgonce = null;
			imgonce = C.GetImageSource("/alertMessage/tobuy.png");
			for (int a = 0; a < 8; a++) {
				imagea_BuyNumber[a] = Image.createImage(imgonce, a * 34, 0, 34,
						29, 0);
			}
			imgonce = null;
			System.gc();
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // 灰按钮
			C.GetImageSource("/button/0.png");
			imagea_alter[2] = // 选中箭头
			C.GetImageSource("/button/1.png");
			imagea_alter[3] = // 文字，更多的时间
			C.GetImageSource("/alertMessage/howtobuy.png");
			imagea_alter[4] = // 确认购买
			C.GetImageSource("/button/10.png");
			imagea_alter[5] = // 返回
			C.GetImageSource("/button/2.png");
			buttonindex = 1;
		}
			break;
		case 13: {// 如果是非电信的号码
			sb = new StringBuffer();
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // 您没有金锤，可以通过以下方式
			C.GetImageSource("/alertMessage/nott.png");
			imagea_alter[2] = // 您没有金锤，可以通过以下方式
			C.GetImageSource("/alertMessage/content.png");
		}
			break;
		case 14: {// 输入有误，或者输入的号码不是唯一的号码
			sb = new StringBuffer();
			imagea_alter[0] = // 外框
			C.GetImageSource("/alertMessage/alertBg1.png");
			imagea_alter[1] = // 您没有金锤，可以通过以下方式
			C.GetImageSource("/alertMessage/pleasetry.png");
			imagea_alter[2] = // 您没有金锤，可以通过以下方式
			C.GetImageSource("/alertMessage/content.png");
		}
			break;
		case 15: {// 如果生命没了，询问用户是否购买生命
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");// 选择
			imagea_alter[3] = C.GetImageSource("/alertMessage/fh.png");// 提示框内容
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/zajd.png");
			priceX = 215;
			priceY = 192;
			propID = 2;
		}
			break;
		case 20:
		case 21:
		case 22:
		case 23: {
			imagea_alter[0] = C.GetImageSource("/alertMessage/alert.png");
			imagea_alter[2] = C.GetImageSource("/button/1.png");// 选择
			imagea_alter[3] = C.GetImageSource("/alertMessage/" + C.alertType
					+ ".png");// 提示框内容
			imagea_alter[4] = C.GetImageSource("/button/12.png");
			imagea_alter[5] = C.GetImageSource("/button/2.png");
			buttonindex = 1;
			priceX = 195;
			priceY = 230;
			propID = C.alertType - 18;
			System.out.println("propID:" + propID);
		}
			break;
		}
	}

	/**
	 * 提示框界面将所有图片资源返回
	 * 
	 * @param g
	 */
	public void removeAlertClassSource() {
		buttonindex = 0;
		for (int a = 0; a < 6; a++) {
			imagea_alter[a] = null;
		}
		for (int a = 0; a < 8; a++) {
			imagea_BuyNumber[a] = null;
		}
		System.gc();
	}

	/**
	 * 提示界面显示
	 * 
	 * @param c
	 */
	public void showMe(Graphics c) {
		switch (C.alertType) {
		case 31:// 关卡模式下第一关到第10关
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40: {// 进入游戏前的第几关 提示进入第几关
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);
			C.DrawImage_VH(imagea_alter[1], 318, 220, c);
			C.DrawImage_VH(imagea_alter[2], 318, 340, c);
		}
			break;
		case 54: {// 游戏结束，本次您获得了1000积分.
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// 背景窗口
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// 购买时间，说明文字
			C.DrawImage_VH(imagea_alter[4], // 确认
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // 返回
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // 亮底
					buttonTwoArray[buttonindex * 2] - 60,
					buttonTwoArray[1 + buttonindex * 2], c);
			C.DrawNumber_XY_RIGHTTOP(imagea_GetScore, C.time_Score, 220, 230,
					15, c);
		}
			break;
		case 45:// 很遗憾，闯关失败！
		case 44:// 恭喜，成功进入下一关！
		case 53:// 在倒计时模式下，是否退出游戏返回主菜单
		case 72:// 在托管模式下，是否购买托管器
		case 51:// 在计时模式下，是否购买时间
		case 52:// 在计时模式下，是否购买托管器
		case 43:// 是否返回游戏到主菜单
		case 41:// 询问用户是否购买时间
		case 15:// 没有生命了提示用户是否购买
		case 2: {// 退出游戏，返回菜单界面
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// 背景窗口
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// 购买时间，说明文字
			if (C.alertType == 72 || C.alertType == 51 || C.alertType == 52
					|| C.alertType == 15 || C.alertType == 41) {
				drawCoin(c, 150, 300);
			}
			C.DrawImage_VH(imagea_alter[4], // 确认
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // 返回
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // 亮底
					buttonTwoArray[buttonindex * 2] - 60,
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		case 6:// 购买道具成功
		case 7:// 已经到达订购峰值
		case 8:// 您的帐户余额不足
		case 9: {// 购买失败请重试
			C.DrawImage_VH(imagea_alter[0], // 外框
					318, 220, c);
			C.DrawImage_VH(imagea_alter[2], // 文字说明，订购成功
					308, 220, c);
			handleShopping();
//			timer.schedule(new task(), 2000);
		}
			break;
		case 42:
		case 10: {// 购买托管器
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// 背景窗口
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// 购买时间，说明文字
			drawCoin(c, 150, 300);
			C.DrawImage_VH(imagea_alter[4], // 确认购买
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // 返回
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // 亮底
					buttonTwoArray[buttonindex * 2] - 50,
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		case 71:
		case 50: {// 按任意键开始游戏
			C.level = 0;
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// 背景窗口
			C.DrawImage_VH(imagea_alter[1], 318, 240, c);// 按任意键开始游戏
		}
			break;
		case 11: {
			C.DrawImage_VH(imagea_alter[0], 318, 270, c);// 外框
			C.DrawImage_VH(imagea_alter[1], 318, 360, c);// 按钮
			C.DrawImage_VH(imagea_alter[2], 318, 270, c);// 文字说明
		}
			break;
		case 12: {// 1,2,3,4个键分别去购买金锤
			C.DrawImage_VH(imagea_alter[0], 318, 270, c);// 背景窗口
			C.DrawImage_VH(imagea_alter[3], 318, 260, c);// 购买时间，说明文字
			drawCoin(c, 150, 300);
			C.DrawImage_VH(imagea_BuyNumber[C.BuyHowGoldHAMMER - 1], 330, 245,
					c);// 数字
			C.DrawImage_VH(imagea_BuyNumber[C.BuyHowGoldHAMMER + 3], 365, 280,
					c);// 数字

			C.DrawImage_VH(imagea_alter[1], // 灰底
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[1], // 灰底
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(imagea_alter[4], // 确认购买
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // 返回
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // 亮底
					buttonTwoArray[0 + buttonindex * 2],
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		case 20:// 购买四种不同数量的金锤个数
		case 21:
		case 22:
		case 23: {
			C.DrawImage_VH(imagea_alter[0], 318, 220, c);// 背景窗口
			C.DrawImage_VH(imagea_alter[3], 318, 220, c);// 购买时间，说明文字
			drawCoin(c, 150, 300);
			System.out.println("draw锤子：" + C.BuyHowGoldHAMMER);
			C.drawString(c, num, "" + (C.BuyHowGoldHAMMER + 1), "0123456789.",
					270, 192, 12, 18, 0, 0, 0);

			C.DrawImage_VH(imagea_alter[4], // 确认
					buttonTwoArray[0], buttonTwoArray[1], c);
			C.DrawImage_VH(imagea_alter[5], // 返回
					buttonTwoArray[2], buttonTwoArray[3], c);
			C.DrawImage_VH(
					imagea_alter[2], // 亮底
					buttonTwoArray[buttonindex * 2] - 50,
					buttonTwoArray[1 + buttonindex * 2], c);
		}
			break;
		}
	}

	private Image coin;
	private Image num;
	private int priceX, priceY;
	private int propID;

	public void drawCoin(Graphics g, int x, int y) {
		/*if (coin == null) {
			coin = C.GetImageSource("/coin/coin.png");
		}*/
		if(num == null) {
			num = C.GetImageSource("/alertMessage/num.png");
		}
	/*	g.drawImage(coin, x, y, 0);
		C.drawString(g, num, Props.Coin, "0123456789", x + coin.getWidth(),
				y + 2, 12, 18, 0, 0, 0);*/

		C.drawString(g, num, Screen.nx_tool.getSpecificPropsPrice(Props.propid[propID]), "0123456789.",
				priceX, priceY, 12, 18, 0, 0, 0);
	}

	public long currenttime;
	public long excurrenttime;
	public long d_value = 2000;

	public void keyPressed(int keyCode) {
		if (C.alertType == 72 || C.alertType == 51 || C.alertType == 52
				|| C.alertType == 15 || C.alertType == 41 || C.alertType == 42
				|| C.alertType == 10 || C.alertType == 12 || C.alertType == 20
				|| C.alertType == 21 || C.alertType == 22 || C.alertType == 23) {
			currenttime = System.currentTimeMillis();
			System.out.println("currenttime = " + currenttime);

			if (currenttime - excurrenttime <= d_value) {
				if (keyCode == C.KEY_FIRE) {
					return;
				} else {
					excurrenttime = currenttime - d_value * 2;
				}
			}
		}
		if (C.alertType == 0) {// 进入游戏前的第几关 提示进入第几关,这里是按任意键进入游戏的
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// 删除所有导入的图片
			s.gamechallenge.coutDownStart = true;
			Screen.status = Screen.S_GAME_CHALLENGE;
			return;
		}
		if (C.alertType >= 31 && C.alertType <= 40) {// 第1关到第10关
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// 删除所有导入的图片
			s.gamelevel.coutDownStart = true;
			C.nowCountDown = s.gamelevel.countDown[C.level];
			Screen.status = Screen.S_GAME_LEVEL;
			return;
		}
		if (C.alertType == 50) {// 倒计时模式下，按任意键进入游戏
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// 删除所有导入的图片
			s.gametime.coutDownStart = true;
			C.nowCountDown = s.gametime.countDownTime;
			Screen.status = Screen.S_GAME_TIME;
			return;
		}
		if (C.alertType == 71) {// 挑模式下按任意键开始游戏
			Screen.status = Screen.S_NULL;
			removeAlertClassSource();// 删除所有导入的图片
			s.gamechallenge.coutDownStart = true;
			Screen.status = Screen.S_GAME_CHALLENGE;
			return;
		}
		switch (keyCode) {
		case C.KEY_FIRE: {
			switch (C.alertType) {
			case 2: { // 退出游戏，返回至菜单界面
				if (buttonindex == 0) {// 确定
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					// // 将所有的game类的资源全部置空;
					s.gamechallenge.removeAllSources();
					s.gamechallenge = null;
					System.gc();
					s.setGameStatus(Screen.S_MENU);
				} else {// 取消
					Screen.status = Screen.S_GAME_CHALLENGE;
					s.gamechallenge.coutDownStart = true;
				}
			}
				break;
			case 72: {// 购托管器
				if (buttonindex == 1) {// 用户点了"返回"
					removeAlertClassSource();
					Screen.status = Screen.S_GAME_CHALLENGE;
					s.gamechallenge.coutDownStart = true;
				} else {// 用户点了确认购买
					Screen.status = Screen.S_NULL;
					Screen.beforeStatus = Screen.S_GAME_CHALLENGE;
					C.alertBeforeType = 72;
					removeAlertClassSource();
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyAutoSmash();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(1);
					prop = null;
					System.gc();
				}
			}
				break;
			case 11: {// 您没有金锤，可以通过以下方式
				removeAlertClassSource();
				System.gc();
				Screen.status = Screen.S_SMASH;
				s.repaint();
			}
				break;
			case 12: {// 购买不同个数的金锤
				if (buttonindex == 1) {// 用户点了"返回"
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					if (Screen.beforeStatus == Screen.S_PROPSHOP) {// 在道具商城界面
						Screen.status = Screen.S_PROPSHOP;
						s.repaint();
					} else { // 在砸金蛋界面
						Screen.status = Screen.S_SMASH;
						s.repaint();
					}
				} else {// 用户点了确认购买
					removeAlertClassSource();
					C.receiveKeyPressed = false;
					C.alertBeforeType = 12;
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyGoldHammer(String.valueOf(C.BuyHowGoldHAMMER +
					// 1));
					System.out.println("12type购买锤子:" + C.BuyHowGoldHAMMER);
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(C.BuyHowGoldHAMMER + 1);
					prop = null;
					System.gc();
				}
			}
				break;
			case 15: {// 用户没有生命了，提示是否购买
				if (buttonindex == 1) {// 用户点了取消
					s.gamechallenge.removeAllSources();
					s.gamechallenge.autoSamsh = false;
					s.gamechallenge.autoSamshTime = 0;
					s.gamechallenge.coutDownStart = false;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// 用户点了确定
					C.receiveKeyPressed = false;
					Screen.beforeStatus = Screen.S_GAME_CHALLENGE;
					C.alertBeforeType = 15;
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyLife();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(2);
					prop = null;
					System.gc();
				}
			}
				break;
			case 20:
			case 21:
			case 22:
			case 23:// 购买四种购买
			{
				if (buttonindex == 1) {// 取消
					Screen.status = Screen.beforeStatus;
					s.repaint();
				} else {// 确定购买
					removeAlertClassSource();
					C.receiveKeyPressed = false;
					C.alertBeforeType = C.alertType;
//					C.BuyHowGoldHAMMER = C.alertType - 18;
					// if (s.prop == null)
					// s.prop = new Prop(s);
					// s.prop.buyGoldHammer(String.valueOf(C.BuyHowGoldHAMMER));
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(propID);
					prop = null;
					System.gc();
					
				}
			}
				break;
			case 41: {// 购买时间
				if (buttonindex == 1) {// 取消
					if (s.gamelevel != null)
						s.gamelevel.coutDownStart = true;
					if (s.gametime != null)
						s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// 确定
					// s.prop.buyTime();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(0);
					prop = null;
					System.gc();
				}
			}
				break;
			case 42: {// 购买托管
				if (buttonindex == 1) {// 取消
					s.gamelevel.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// 确定
					// s.prop.buyAutoSmash();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(1);
					prop = null;
					System.gc();
				}
			}
				break;
			case 43: {// 是否退出游戏到主菜单
				if (buttonindex == 1) {// 取消
					s.gamelevel.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// 确定
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					// // 将所有的game类的资源全部置空;
					s.gamelevel.removeAllSources();
					s.gamelevel = null;
					System.gc();
					s.setGameStatus(Screen.S_MENU);
				}
			}
				break;
			case 44: {// 恭喜，成功进入下一关！
				if (buttonindex == 1) {// 砸金蛋
					Screen.backScreen = Screen.S_GAME_LEVEL;
					Screen.status = Screen.S_NULL;
					Screen.beforeStatus = Screen.S_GAME_LEVEL;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// 继续游戏
					if (C.level < 10)
						++C.level;
					Screen.status = Screen.S_GAME_LEVEL;
					s.repaint();
					s.serviceRepaints();

					Screen.status = Screen.S_NULL;
					C.passed = false;

					C.alertType = 31;
					s.setGameStatus(Screen.S_ALERT);
					s.repaint();
					s.serviceRepaints();
				}
			}
				break;
			case 45: {
				if (s.si == null)
					s.si = new ServerIptv(s);
				s.si.doSendUserInformation(1);
				C.level_Score = 0;

				if (buttonindex == 1) {// 砸金蛋
					Screen.backScreen = Screen.S_GAME_LEVEL;
					Screen.status = Screen.S_NULL;
					Screen.beforeStatus = Screen.S_GAME_LEVEL;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// 继续游戏
					C.level = 0;
					Screen.status = Screen.S_GAME_LEVEL;
					s.repaint();
					s.serviceRepaints();

					Screen.status = Screen.S_NULL;
					C.passed = false;

					C.alertType = 31;
					s.setGameStatus(Screen.S_ALERT);
					s.repaint();
					s.serviceRepaints();
				}
			}
				break;
			case 54: {// 游戏结束，本次您获得了1000积分.
				if (s.si == null)
					s.si = new ServerIptv(s);
				s.si.doSendUserInformation(1);
				C.time_Score = 0;
				if (buttonindex == 1) {// 砸金蛋
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
					s.serviceRepaints();
				} else {// 重新游戏
					Screen.status = Screen.S_GAME_TIME;
					s.repaint();
					s.serviceRepaints();

					Screen.status = Screen.S_NULL;
					C.nowCountDown = s.gametime.countDownTime;

					C.alertType = 50;
					s.setGameStatus(Screen.S_ALERT);
					s.repaint();
					s.serviceRepaints();
				}
			}
				break;
			case 53: {// 是否退出游戏到主菜单
				if (buttonindex == 1) {// 取消
					s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// 确定
					Screen.status = Screen.S_NULL;
					removeAlertClassSource();
					// // 将所有的game类的资源全部置空;
					s.gametime.removeAllSources();
					s.gametime = null;
					System.gc();
					s.setGameStatus(Screen.S_MENU);
				}
			}
				break;
			case 51: {// 计时模式下是否购买时间
				if (buttonindex == 1) {// 取消
					s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// 确定
					Screen.beforeStatus = Screen.S_GAME_TIME;
					// s.prop.buyTime();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(0);
					prop = null;
					System.gc();
				}
			}
				break;
			case 52: {// 计时模式下是否购买托管器
				if (buttonindex == 1) {// 取消
					s.gametime.coutDownStart = true;
					Screen.status = Screen.beforeStatus;
				} else {// 确定
					// s.prop.buyAutoSmash();
					Props prop = new Props(Screen.iptvID);
					prop.buyProp(1);
					prop = null;
					System.gc();
				}
			}
				break;
			}
		}
			break;
		case C.KEY_LEFT: {
			if (buttonindex == 1)
				buttonindex = 0;
			s.repaint();
		}
			break;
		case C.KEY_RIGHT: {
			if (buttonindex == 0)
				buttonindex = 1;
			s.repaint();
		}
			break;
		}
	}

	public void handleShopping(){
		if (Screen.beforeStatus == Screen.S_GAME_TIME) {// 在计时模式下
			s.gametime.coutDownStart = true;
			C.passed = false;
			Screen.status = Screen.S_GAME_TIME;
		} else if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {// 在关卡模式下购买道具
			s.gamelevel.coutDownStart = true;
			C.passed = false;
			Screen.status = Screen.S_GAME_LEVEL;
		} else if (Screen.beforeStatus == Screen.S_GAME_CHALLENGE) {// 在游戏中购买托管
			if (C.alertBeforeType == 15) {// 游戏中没有生命了去买生命了
				s.gamechallenge.coutDownStart = true;
				C.passed = false;
				Screen.status = Screen.S_GAME_CHALLENGE;
			} else if (C.alertBeforeType == 16) {
				Screen.status = Screen.S_NULL;
				s.setGameStatus(Screen.S_SMASH);
				s.repaint();
			} else if (C.alertBeforeType == 72) {
				removeAlertClassSource();
				s.gamechallenge.loadAutoSamshImage();// 导入自动托管时所需要的图片
				s.gamechallenge.coutDownStart = true;
				Screen.status = Screen.S_GAME_CHALLENGE;
			}
		} else if (Screen.beforeStatus == Screen.S_PROPSHOP) {
			Screen.status = Screen.S_PROPSHOP;
			s.repaint();
		} else if (Screen.beforeStatus == Screen.S_SMASH) {
			Screen.status = Screen.S_SMASH;
			s.repaint();
		}
		C.receiveKeyPressed = true;
	}
	
	class task extends TimerTask {
		public void run() {
			// case 6:// 订购成功、达到峰值、余额不足、订购失败。
			// case 7://这个游戏比较特殊，确定与取消都返回了，不进行其它操作
			// case 8:
			// case 9: {// 购买成功与否
			if (Screen.beforeStatus == Screen.S_GAME_TIME) {// 在计时模式下
				s.gametime.coutDownStart = true;
				C.passed = false;
				Screen.status = Screen.S_GAME_TIME;
			} else if (Screen.beforeStatus == Screen.S_GAME_LEVEL) {// 在关卡模式下购买道具
				s.gamelevel.coutDownStart = true;
				C.passed = false;
				Screen.status = Screen.S_GAME_LEVEL;
			} else if (Screen.beforeStatus == Screen.S_GAME_CHALLENGE) {// 在游戏中购买托管
				if (C.alertBeforeType == 15) {// 游戏中没有生命了去买生命了
					s.gamechallenge.coutDownStart = true;
					C.passed = false;
					Screen.status = Screen.S_GAME_CHALLENGE;
				} else if (C.alertBeforeType == 16) {
					Screen.status = Screen.S_NULL;
					s.setGameStatus(Screen.S_SMASH);
					s.repaint();
				} else if (C.alertBeforeType == 72) {
					removeAlertClassSource();
					s.gamechallenge.loadAutoSamshImage();// 导入自动托管时所需要的图片
					s.gamechallenge.coutDownStart = true;
					Screen.status = Screen.S_GAME_CHALLENGE;
				}
			} else if (Screen.beforeStatus == Screen.S_PROPSHOP) {
				Screen.status = Screen.S_PROPSHOP;
				s.repaint();
			} else if (Screen.beforeStatus == Screen.S_SMASH) {
				Screen.status = Screen.S_SMASH;
				s.repaint();
			}
			C.receiveKeyPressed = true;
		}
	}

}
