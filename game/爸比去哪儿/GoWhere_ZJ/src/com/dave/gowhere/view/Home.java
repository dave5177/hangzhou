package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class Home implements Showable{
	private Image img_back;
	private Image[][] imgArr_btn;//9����ť��ѡ�к�δѡ��ͼƬ[n][0]Ϊδѡ�У�[n][1]Ϊѡ��

	private int chooseIndex;//ѡ��ť�±�
	
	private CanvasControl canvasControl;
	
	public Home(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	private final int[][] btnCoor = {//9����ť������λ��ê��Ϊ��|��
			{75, 284},
			{218, 276},
			{278, 349},
			{419, 336},
			{579, 406},
			{50, 442},
			{187, 484},
			{265, 443},
			{466, 444},
	};
	
	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		
		for (int i = 0; i < 9; i++) {
			g.drawImage(imgArr_btn[i][0], btnCoor[i][0], btnCoor[i][1], Graphics.HCENTER | Graphics.VCENTER);
		}
		g.drawImage(imgArr_btn[chooseIndex][1], btnCoor[chooseIndex][0], btnCoor[chooseIndex][1], Graphics.HCENTER | Graphics.VCENTER);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/home/back.png");
			
			imgArr_btn = new Image[9][2];
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 2; j++) {
					imgArr_btn[i][j] = Image.createImage("/home/btn_" + i + "_" + j + ".png");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		// TODO Auto-generated method stub
		
	}

	public void removeServerImage() {
		img_back = null;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 2; j++) {
				imgArr_btn[i][j] = null;
			}
		}
		imgArr_btn = null;
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_RIGHT:
			chooseIndex ++;
			if(chooseIndex > 8)
				chooseIndex = 0;
			break;
		case C.KEY_LEFT:
			chooseIndex --;
			if(chooseIndex < 0)
				chooseIndex = 8;
			break;
		case C.KEY_DOWN:
			if(chooseIndex < 4)
				chooseIndex += 5;
			else if(chooseIndex == 4)
				chooseIndex += 4;
			break;
		case C.KEY_UP:
			if(chooseIndex > 4)
				chooseIndex -= 5;
			break;
			
		case C.KEY_FIRE:
			gotoNextView(chooseIndex);
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			break;
		default:
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
		// TODO Auto-generated method stub
		
	}

	private void gotoNextView(int viewIndex) {
		canvasControl.setGoBackView(this);
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		switch (viewIndex) {
		case 0://������Ϸ�����Խ���ѡ�񳡾����棬���ߵ�ʱ����Ը�Ϊ���һ������������Ϸ��
			canvasControl.setView(new SceneChoose(canvasControl));
			break;
		case 1://����ģʽ
			
			break;
		case 2://��ɫѡ��
			canvasControl.setView(new HeroChoose(canvasControl));
			break;
		case 3://���߼���
			canvasControl.setView(new Shop(canvasControl));
			break;
		case 4://�˳���Ϸ
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			break;
		case 5://�ְְ�æ
			canvasControl.setView(new DadHelp(canvasControl));
			break;
		case 6://����
			canvasControl.setView(new Help(canvasControl, 0));
			break;
		case 7://�Ӻ���
			canvasControl.setView(new AddFriend(canvasControl));
			break;
		case 8://���а�
			canvasControl.setView(new Ranking(canvasControl));
			break;
		}
	}

}
