package com.dave.ui;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class Alert {
	public CanvasControl canvasControl;
	public byte type =  C.A_TYPE_OUT; //类型，默认为返回主菜单。
	
	private static Image img_dialog;
	private static Image img_out_word;
	private static Image img_select;
	private static Image img_buy_word;
	private static Image img_buy_success_word;
	private static Image img_relive_word;
	private static Image img_js_sm;
	private static Image img_number_price;
	private static Image img_yuan;
//	private static Image img_yuanBao;
	
	private byte index;	//光标位置，默认为是。
	private byte show_time; //自动消失类对话框显示时间。
	
	private short number_width;
	private short number_height;
	
	public Alert(CanvasControl canvascontrol) {
		this.canvasControl = canvascontrol;
		loadSourse();
	}

	public void loadSourse() {
			try {
				img_dialog = Image.createImage("/alert/dialog.png");
				img_out_word = Image.createImage("/alert/outWord.png");
				img_select = Image.createImage("/alert/select.png");
				img_buy_word = Image.createImage("/alert/buy.png");
				Image temp = Image.createImage("/alert/buySuccess.png");
				img_buy_success_word = Image.createImage(temp, 0, 0, temp.getWidth(), temp.getHeight()/2, 0);
				img_relive_word = Image.createImage("/alert/relive.png");
				img_js_sm = Image.createImage("/alert/js_sm.png");
				img_number_price = Image.createImage("/alert/number.png");
				img_yuan = Image.createImage("/alert/yuan.png");
//				img_yuanBao = Image.createImage("/alert/yuanBao.png");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			index = 1;
			number_height = (short)img_number_price.getHeight();
			number_width = (short)(img_number_price.getWidth() / 11);
	}
	
	public void showMe(Graphics canvas) {
		canvas.drawImage(img_dialog, C.WIDTH / 2, C.HEIGTH / 4,
				Graphics.TOP | Graphics.HCENTER);
		switch(type) {
		case C.A_TYPE_EXIT :
		case C.A_TYPE_OUT : {
			canvas.drawImage(img_select, C.WIDTH / 2 - 60 + index * 160, C.HEIGTH / 4 + 140,
					Graphics.TOP | Graphics.RIGHT);
			canvas.drawImage(img_out_word, C.WIDTH / 2, C.HEIGTH / 4 + 60,
					Graphics.TOP | Graphics.HCENTER);
		} break;
		case C.A_TYPE_BUY : {
			canvas.drawImage(img_select, C.WIDTH / 2 - 60 + index * 160, C.HEIGTH / 4 + 160,
					Graphics.TOP | Graphics.RIGHT);
			String price = canvasControl.js_iptv_prop_tool.getSpecificPropsPrice(CanvasControl.GAME_PROP_TimeCode_life);
			C.drawString(canvas, img_number_price, price, "0123456789.",
					420, 200, number_width, number_height, 0, 0, 0);
			canvas.drawImage(img_yuan, 470, 195,
					Graphics.TOP | Graphics.RIGHT);
			/*canvas.drawImage(img_select, C.WIDTH / 2 - 60 + index * 160, C.HEIGTH / 4 + 160,
					Graphics.TOP | Graphics.RIGHT);*/
			canvas.drawImage(img_buy_word, C.WIDTH / 2, C.HEIGTH / 4,
					Graphics.TOP | Graphics.HCENTER);
		} break;
		case C.A_TYPE_RELIVE : {
			canvas.drawImage(img_select, C.WIDTH / 2 - 60 + index * 160, C.HEIGTH / 4 + 170,
					Graphics.TOP | Graphics.RIGHT);
			String price = canvasControl.js_iptv_prop_tool.getSpecificPropsPrice(CanvasControl.GAME_PROP_TimeCode_life);
			C.drawString(canvas, img_number_price, price, "0123456789.",
					340, 228, number_width, number_height, 0, 0, 0);
			canvas.drawImage(img_yuan, 400, 226,
					Graphics.TOP | Graphics.RIGHT);
			canvas.drawImage(img_relive_word, C.WIDTH / 2, C.HEIGTH / 4,
					Graphics.TOP | Graphics.HCENTER);
		} break;
		case C.A_TYPE_BUYSUCCESS : {
		/*	canvas.drawImage(img_select, C.WIDTH / 2, C.HEIGTH / 4 + 160,
					Graphics.TOP | Graphics.HCENTER);*/
			canvas.drawImage(img_buy_success_word, C.WIDTH / 2, C.HEIGTH / 4,
					Graphics.TOP | Graphics.HCENTER);
		} break;
		}
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT : {
			if(index == 1) {
				index = 0;
//				canvasControl.audioPlay.playSound((byte)0);
			}
		} break;
		case C.KEY_RIGHT : {
			if(index == 0) {
				index = 1;
//				canvasControl.audioPlay.playSound((byte)0);
			}
		} break;
		case C.KEY_FIRE : {
//			canvasControl.audioPlay.playSound((byte)2);
			switch(type) {
			case C.A_TYPE_OUT : {
				if(index == 0) {
					canvasControl.state = CanvasControl.NULL;
					canvasControl.setState(CanvasControl.HOME_PAGE);
					canvasControl.si.doSendScore(CanvasControl.game_score);
				}
				else if(index == 1) {
					canvasControl.state = CanvasControl.NULL;
					canvasControl.setState(canvasControl.state_Back_For);
				}
				index = 1;
			} break;
			case C.A_TYPE_EXIT : {
				if(index == 0) {
					canvasControl.mainMIDlet.exitGame();
				}
				else if(index == 1) {
					canvasControl.state = CanvasControl.NULL;
					canvasControl.setState(canvasControl.state_Back_For);
				}
				index = 1;
			}break;
			case C.A_TYPE_BUY : {
				if(index == 0) {
//					this.type = C.A_TYPE_BUYSUCCESS;
					canvasControl.js_iptv_prop_tool.do_BuyProp(img_js_sm, CanvasControl.GAME_PROP_TimeCode_life);
//					canvasControl.game_page.monkey.life += 5;
				}
				else if(index == 1) {
					canvasControl.state = CanvasControl.NULL;
					canvasControl.setState(CanvasControl.GAME_PAGE);
				}
				index = 1;
			}break;
			case C.A_TYPE_RELIVE : {
				if(index == 0) {
//					this.type = C.A_TYPE_BUYSUCCESS;
					canvasControl.js_iptv_prop_tool.do_BuyProp(img_js_sm, CanvasControl.GAME_PROP_TimeCode_life);

//					canvasControl.game_page.back_state = 0;
//					canvasControl.game_page.monkey.life = 5;
				}
				else if(index == 1) {
					canvasControl.si.doSendScore(CanvasControl.game_score);
					canvasControl.state = CanvasControl.NULL;
					canvasControl.setState(canvasControl.state_Back_For);
					canvasControl.game_page.back_state = 2;
				}
				index = 1;
			}break;
			case C.A_TYPE_BUYSUCCESS : {
				//canvascontrol.setState(CanvasControl.GAME_PAGE);
				}break;
		} 
		}break;
		case C.KEY_BACK :
		case C.KEY_BACK_ZX :
		case C.KEY_0 :
			canvasControl.audioPlay.playSound((byte)2);
			index = 1;
			switch(type) {
			case C.A_TYPE_RELIVE : 
				canvasControl.state = CanvasControl.NULL;
				canvasControl.setState(canvasControl.state_Back_For);
				canvasControl.game_page.back_state = 2;
				break;
			default :
				canvasControl.state = CanvasControl.NULL;
				canvasControl.setState(canvasControl.state_Back_For);
				break;
			}
			
		}
	}
	
	public void logi() {
		switch(type) {
		case C.A_TYPE_BUYSUCCESS : {
			show_time ++;
			if(show_time == 10) {
				show_time = 0;
				canvasControl.setState(CanvasControl.GAME_PAGE);
			}
		}break;
		}
	}
}
