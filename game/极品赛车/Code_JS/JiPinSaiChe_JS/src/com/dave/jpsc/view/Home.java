package com.dave.jpsc.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dalin.jsiptv.prop.JS_IPTV_PORP_TOOL;
import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.tool.PlayerHandler;
import com.dave.showable.Showable;

/**
 * @author Administrator 主页
 */
public class Home implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_msg;
	public Image img_msg_word;
	public Image img_number_back;
	public Image img_number;
	public Image[] a_img_button;
//	private Image[] a_img_word_sound;

	/**
	 * 选择的下标值 0：选中开始游戏/继续游戏 1：选中商城 2：选中排行榜 3：选中帮助 4：选中退出游戏
	 */
	private byte index;

	/**
	 * 按键后按钮移动的状态。1：按右键，按钮向左移动；2：按左键，按钮向右移动。
	 */
	private int btnMove;

	/**
	 * 当前按钮x坐标。
	 */
	private int x_t_btn = 320;

	/**
	 * 移动时已成为历史的按钮的x轴坐标
	 */
	private int x_l_btn;

	/**
	 * 您有新消息动画帧
	 */
	private int msg_frame;

	private int x_msg_word = 550;

	private boolean msgAni;

	private int waitTime;

	/**
	 * 按7键开启或关闭声音文字x坐标
	 */
	private int x_word_sound = 700;

	public Home(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
		
		if(CanvasControl.haveMsg)
			index = 4;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);

		g.setClip(230, 0, 180, 530);
		g.drawImage(a_img_button[index], x_t_btn, 497, Graphics.HCENTER
				| Graphics.VCENTER);
		int index_l = 0;
		if (btnMove == 1) {
			index_l = index - 1 < 0 ? 7 : index - 1;
			g.drawImage(a_img_button[index_l], x_l_btn, 497, Graphics.HCENTER
					| Graphics.VCENTER);
		} else if (btnMove == 2) {
			index_l = index + 1 > 7 ? 0 : index + 1;
			g.drawImage(a_img_button[index_l], x_l_btn, 497, Graphics.HCENTER
					| Graphics.VCENTER);
		}
		g.setClip(0, 0, 640, 530);
		
		if(CanvasControl.haveMsg)
			showMsg(g);
		
//		if(PlayerHandler.soundClosed) {
//			g.drawImage(a_img_word_sound[0], x_word_sound, 528, Graphics.BOTTOM | Graphics.HCENTER);
//			g.drawImage(a_img_word_sound[1], x_word_sound + 4, 528, Graphics.BOTTOM | Graphics.HCENTER);
//		} else {
//			g.drawImage(a_img_word_sound[0], x_word_sound, 528, Graphics.BOTTOM | Graphics.HCENTER);
//			g.drawImage(a_img_word_sound[2], x_word_sound + 4, 528, Graphics.BOTTOM | Graphics.HCENTER);
//		}

		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION + JS_IPTV_PORP_TOOL.js_PropVersion, 10, 10, 0);
	}

	/**
	 * 您有新信息
	 * @param g
	 */
	private void showMsg(Graphics g) {
		g.drawRegion(img_msg, msg_frame * 54, 0, 54, 52, 0, 550, 200, Graphics.HCENTER | Graphics.BOTTOM);
		g.drawImage(img_number_back, 580, 165, Graphics.HCENTER | Graphics.VCENTER);
		C.drawString(g, img_number, Message.numbers + "", "0123456789", 580, 173, 10, 15, Graphics.HCENTER | Graphics.BOTTOM, 0, 0);
		
		g.setClip(500, 180, 100, 50);
		
		g.drawImage(img_msg_word, x_msg_word, 185, Graphics.TOP | Graphics.HCENTER);
		g.setClip(0, 0, 640, 530);
	}

	public void keyPressed(int keyCode) {
		
		switch (keyCode) {
		case C.KEY_7://关闭或者开启声音
			if(PlayerHandler.soundClosed)
				PlayerHandler.soundClosed = false;
			else
				PlayerHandler.soundClosed = true;
			
			x_word_sound = 700;
			break;
		case C.KEY_DOWN:
		case C.KEY_RIGHT:
			index++;
			if (index > 7)
				index = 0;

			x_l_btn = x_t_btn;
			x_t_btn = x_l_btn + 180;
			btnMove = 1;
			break;
		case C.KEY_UP:
		case C.KEY_LEFT:
			index--;
			if (index < 0)
				index = 7;
			x_l_btn = x_t_btn;
			x_t_btn = x_l_btn - 180;
			btnMove = 2;
			break;
		case C.KEY_FIRE:
			gotoNextView(index);
			break;
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_0:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/home/back.jpg");
			img_msg = Image.createImage("/home/msg.png");
			img_msg_word = Image.createImage("/home/msg_word.png");
			img_number = Image.createImage("/home/number.png");
			img_number_back = Image.createImage("/home/number_back.png");

			a_img_button = new Image[8];
			for (int i = 0; i < 8; i++) {
				a_img_button[i] = Image.createImage("/home/btn_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		a_img_word_sound = new Image[3];
//		for (int i = 0; i < a_img_word_sound.length; i++) {
//			try {
//				a_img_word_sound[i] = Image.createImage("/home/word_sound_" + i + ".png");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void removeResource() {
//		for (int i = 0; i < a_img_word_sound.length; i++) {
//			a_img_word_sound[i] = null;
//		}
//		a_img_word_sound = null;
		
		img_back = null;
		img_msg = null;
		img_msg_word = null;
		img_number = null;
		img_number_back = null;
		for (int i = 0; i < a_img_button.length; i++) {
			a_img_button[i] = null;
		}
		a_img_button = null;

		System.gc();
	}

	public void logic() {
		if(x_word_sound > -60) {
			x_word_sound -= 6;
		} else {
			x_word_sound = 700;
		}
		
		if (btnMove == 1) {
			x_l_btn -= 50;
			x_t_btn -= 50;
			if (x_t_btn <= 320) {
				x_t_btn = 320;
				btnMove = 0;
			}
		} else if (btnMove == 2) {
			x_l_btn += 50;
			x_t_btn += 50;
			if (x_t_btn >= 320) {
				x_t_btn = 320;
				btnMove = 0;
			}
		}
		
		if(CanvasControl.haveMsg) {
			if(msgAni) {
				msg_frame --;
				if(msg_frame < 0) {
					msg_frame = 2;
					msgAni = false;
				}
			} else {
				waitTime ++;
				if(waitTime >= 20) {
					waitTime = 0;
					msgAni = true;
				}
			}
			
			x_msg_word -= 2;
			if(x_msg_word < 450 )
				x_msg_word = 650;
		}
		
		//处理挑战次数刷新
		if(!CanvasControl.handledDay) {
			if(CanvasControl.playDay != 0 && CanvasControl.lastPlayDay != 0) {
				CanvasControl.handledDay = true;
				if(CanvasControl.playDay != CanvasControl.lastPlayDay && canvasControl.me.duelTimes < 10) {
					canvasControl.me.duelTimes = 10;
					canvasControl.saveParam();
				}
			}
		}
	}

	/**
	 * 去到下一个界面
	 */
	public void gotoNextView(int index) {
		canvasControl.setView(canvasControl.nullView);
		canvasControl.setGoBackView(this);
		switch (index) {
		case 0:// 关卡模式(进入关卡选择界面）
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 1));
			break;
		case 1:// 挑战模式
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 2));
			break;
		case 2:// 车行
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 3));
			break;
		case 3:// 排行榜
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 4));
			break;
		case 4://查看消息
			canvasControl.emptyMsg();
			CanvasControl.haveMsg = false;
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 5));
			break;
		case 5://我的车库
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 6));
			if(CanvasControl.haveMsg) {
				CanvasControl.haveMsg = false;
				canvasControl.emptyMsg();
			}
			break;
		case 6://帮助
			this.removeServerImage();
			canvasControl.setView(new Help(canvasControl, 0));
			break;
		case 7:// 退出游戏
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			canvasControl.setGoBackView(this);
			break;
		}
	}

	public void removeServerImage() {
	}

	public void handleGoods(int goodsIndex) {
		
	}

}
