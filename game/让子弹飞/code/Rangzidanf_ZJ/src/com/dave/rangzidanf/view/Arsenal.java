package com.dave.rangzidanf.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.gameModel.Gun;
import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.tool.C;

public class Arsenal implements Showable {
	
	private CanvasControl canvasControl;
	
	/**
	 * 选择的下标值
	 * 0, AWM-A
	 * 1, AK47-a
	 * 2, M16-S
	 * 3, AT15
	 * 4, M4A1-S
	 * 5, BARRET
	 * 6, 选中开始游戏按钮
	 */
	private int index;
	private int indexGoBack = 0;//从开始按钮返回的下标值
	
	/**
	 * 存储各个枪支的细节参数
	 */
	private String[][] gunDetail = {
//			{"140/200", "8/10", "9/10", "7/10", "8/10", "30", "20"},
//			{"120/200", "6/10", "8/10", "8/10", "6/10", "30", "15"},
//			{"100/200", "3/10", "5/10", "5/10", "6/10", "25", "0"},
//			{"120/200", "6/10", "7/10", "7/10", "6/10", "30", "15"},
//			{"120/200", "6/10", "8/10", "7/10", "6/10", "30", "25"},
//			{"150/200", "9/10", "9/10", "2/10", "10/10", "30", "30"},
			{"100/200", "3/10", "5/10", "5/10", "6/10", "25", "0.2"},
			{"120/200", "6/10", "7/10", "7/10", "6/10", "30", "0.5"},
			{"120/200", "6/10", "8/10", "8/10", "6/10", "30", "1"},
			{"120/200", "6/10", "8/10", "7/10", "6/10", "30", "1"},
			{"140/200", "8/10", "9/10", "7/10", "8/10", "30", "1"},
			{"150/200", "9/10", "9/10", "5/10", "10/10", "30", "1"},
	};
	
	private Image img_back;
	private Image img_goBack;
	private Image img_select;
	private Image img_number;
	private Image img_had;
	private Image img_free;
	
//	private Image img_startButton_unchose;
	private Image img_startButton_chosed;
	
	private Image img_gun;
	private Image img_gunInfo;
	private Image img_gunName;

	private Image img_arrow;

	private int arrowX = 230;
	
	public Arsenal(CanvasControl canvasControl){
		this.canvasControl = canvasControl;
		index = canvasControl.getGunIndex();
		
		canvasControl.setNeedRepaint(false);
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_goBack, C.WIDTH, 530, Graphics.BOTTOM | Graphics.RIGHT);
		showFitting(g);
	}
	
	/**
	 * 显示各个组件图片。
	 * @param g
	 */
	public void showFitting(Graphics g) {
		int gunIndex = 2;
		
		for(int i=0; i<6; i++) {//画持有的枪的钩子
			if(CanvasControl.hasGun[i] == 1) {
				g.drawImage(img_had, 90 + i * 104, 403, 0);
			}
		}
		
		int w = img_number.getWidth();
		int h = img_number.getHeight();
		if(index != 6) {
			gunIndex = index;
//			loadFittingResource(gunIndex);
			g.drawImage(img_select, 60 + gunIndex * 104, 439, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_startButton_chosed, C.WIDTH / 2, 505, Graphics.HCENTER | Graphics.VCENTER);
			for(int i=0; i<7; i++) {
				if(index == 0 && i == 6) {
					g.drawImage(img_free, 520, 152 + i * 33, 0);
					continue;
				}
				C.drawString(g, img_number, gunDetail[index][i], "0123456789/.", 520, 155 + i * 33, w / 12, h, 0, 0, 0);
			}
		} else {
//			loadFittingResource(indexGoBack);
			for(int i=0; i<7; i++) {
				if(canvasControl.getGunIndex() == 0 && i == 6) {
					g.drawImage(img_free, 520, 152 + i * 33, 0);
					continue;
				}
				C.drawString(g, img_number, gunDetail[canvasControl.getGunIndex()][i], "0123456789/.", 520, 155 + i * 33, w / 12, h, 0, 0, 0);
			}
			g.drawImage(img_select, 60 + canvasControl.getGunIndex() * 104, 439, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_startButton_chosed, C.WIDTH / 2, 505, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_arrow, arrowX, 505, Graphics.VCENTER | Graphics.RIGHT);
		}
		g.drawImage(img_gun, 210, 240, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(img_gunInfo, C.WIDTH / 2, 100, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(img_gunName, 210, 350, Graphics.HCENTER | Graphics.VCENTER);
	}
	
	public void loadResource() {
		try {
			img_back = Image.createImage("/arsenal/back.png");
			img_goBack = Image.createImage("/button/goBack.png");
			img_select = Image.createImage("/arsenal/select.png");
			img_number = Image.createImage("/arsenal/number.png");
			img_arrow = Image.createImage("/button/arrow.png");
			img_had = Image.createImage("/arsenal/had.png");
			img_free = Image.createImage("/arsenal/free.png");
			
			img_startButton_chosed = Image.createImage("/arsenal/startButton_chosed.png");
//			img_startButton_unchose = Image.createImage("/arsenal/startButton_unchose.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

//		gunSoundPlayer = new AudioPlay[6];
//		for(int i=0; i<6; i++) {
//			gunSoundPlayer[i] = new AudioPlay(i);
//		}
		
		if(index == 6) {
			loadFittingResource(canvasControl.getGunIndex());
		} else {
			loadFittingResource(index);
		}
	
	}
	
	/**
	 * 加载组件资源
	 * @param index
	 */
	public void loadFittingResource(int index) {
		try {
			img_gun = Image.createImage("/arsenal/gun_" + index + ".png");
			img_gunInfo = Image.createImage("/arsenal/gunInfo_" + index + ".png");
			img_gunName = Image.createImage("/arsenal/gunName_" + index + ".png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		img_back = null;
		img_goBack = null;
		img_select = null;
		img_number = null;
		img_arrow = null;
		img_had = null;
		img_free = null;
		
		img_startButton_chosed = null;
//		img_startButton_unchose = null;
		
		img_gun = null;
		img_gunInfo = null;
		img_gunName = null;
		
//		for(int i=0; i<6; i++) {
//			if(gunSoundPlayer[i] != null){
//				gunSoundPlayer[i].removeGameAudioSources();
//				gunSoundPlayer[i] = null;
//			}
//		}
//		gunSoundPlayer = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT:
			if(index < 6 && index > 0) {
				index --;
				playGunSound(index);
				loadFittingResource(index);
			}
			break;
		case C.KEY_RIGHT:
			if(index < 5) {
				index ++;
				playGunSound(index);
				loadFittingResource(index);
			}
			break;
		case C.KEY_DOWN:
			if(index < 6) {
				indexGoBack = index;
				index = 6;
				loadFittingResource(canvasControl.getGunIndex());
			}
			break;
		case C.KEY_UP:
			if(index >= 6) {
				index = indexGoBack;
				playGunSound(indexGoBack);
				loadFittingResource(index);
			}
			break;
		case C.KEY_FIRE:
			switch(index) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				if(CanvasControl.hasGun[index] == 1) {
					canvasControl.setGunIndex(index);
				} else {
					canvasControl.setView(new Dialog(canvasControl, 2).setGunIndex(index));
					canvasControl.setGoBackView(this);
					this.removeResource();
				}
				break;
			case 6:
//				canvasControl.setView(new Game(canvasControl, CanvasControl.level, 
//						new Gun(canvasControl.getGunIndex())));
				canvasControl.setView(new Dialog(canvasControl, 14).setGunImage(getUseGunImage(canvasControl.getGunIndex())));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
			}
			break;
		case C.KEY_1 :
//			canvasControl.setView(new Game(canvasControl, CanvasControl.level, new Gun(canvasControl.getGunIndex())));
			canvasControl.setView(new Dialog(canvasControl, 14).setGunImage(getUseGunImage(canvasControl.getGunIndex())));
			canvasControl.setGoBackView(this);
			this.removeResource();
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(new Home(canvasControl));
			canvasControl.setGoBackView(this);
			this.removeResource();
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		if(arrowX == 230) arrowX = 226;
		else arrowX = 230;
		
		canvasControl.repaint();
	}
	
	public Image getUseGunImage(int index) {
		Image gun = null;
		try {
			gun = Image.createImage("/arsenal/gun_" + index + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gun;
	}
	
	public void playGunSound(int index) {
		canvasControl.audio.playSound(index);
//		for(int i=0; i<6; i++) {
//			if(i != index) {
//				gunSoundPlayer[i].toStopGameSound();
//				if(gunSoundPlayer[i].getState() == AudioPlay.STARTED) {
//					return;
//				}
//			}
//		}
//		gunSoundPlayer[index].playSound();
	}
}
