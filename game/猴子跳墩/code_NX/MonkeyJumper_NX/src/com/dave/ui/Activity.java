package com.dave.ui;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class Activity {
	public CanvasControl canvasControl;
	private byte content_index = 3;//0为活动介绍，1为奖品设置，2为中奖名单，3为排行榜。
	
	private static Image img_background;
	private static Image img_no;
	//private static Image img_number;
	private static Image img_score_top_background;
	private static Image img_preKey; 	//按9键帮助，按0返回

	private static Image[] ima_title_off;
	private static Image[] ima_title_on;
	
	private String score;
	private String totalScore;
	private String rank;
	private String rankall;
	
	public Activity(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		loadAllSource();
	}
	
	public void loadAllSource() {
		try {
			img_background = Image.createImage("/activity/background.png");
			img_no = Image.createImage("/activity/no.png");
			//img_number = Image.createImage("/activity/num.png");
			img_score_top_background = Image.createImage("/activity/score_top_background.png");
			ima_title_off = new Image[4];
			ima_title_on = new Image[4];
			Image temp = Image.createImage("/home/back_help.png");
			img_preKey = Image.createImage(temp, 0, 0, temp.getWidth()/2, temp.getHeight(), 0);

			Image temp_off = Image.createImage("/activity/title_off.png");
			Image temp_on = Image.createImage("/activity/title_on.png");
			int w = temp_off.getWidth();
			int h = temp_off.getHeight();
			for(int i=0; i<4; i++) {
				ima_title_off[i] = Image.createImage(temp_off, w/4 * i, 0, w / 4, h, 0);
				ima_title_on[i] = Image.createImage(temp_on, w/4 * i, 0, w / 4, h, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		score = String.valueOf(CanvasControl.score);
		totalScore = String.valueOf(CanvasControl.totalScore);
		rank = String.valueOf(CanvasControl.rank);
		rankall = String.valueOf(CanvasControl.rankall);
	}
	
	public void removeSource() {
		img_background = null;
		img_no = null;
		//img_number = Image.createImage("/activity/num.png");
		img_score_top_background = null;
		img_preKey = null;

		for(int i=0; i<4; i++) {
			ima_title_off[i] = null;
			ima_title_on[i] = null;
		}
		ima_title_off = null;
		ima_title_on = null;
		
		System.gc();
	}
	
	public void showMe(Graphics canvas) {
		canvas.drawImage(img_background, 0, 0, Graphics.TOP|Graphics.LEFT);

		switch(content_index) {
		case 0 : {
			for(int i=0; i<4; i++) {
				switch(i){
				case 0 : {
					canvas.drawImage(ima_title_on[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
				}break;
				default :
					canvas.drawImage(ima_title_off[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
					break;
				}
			}
			canvas.drawImage(img_no, C.WIDTH/2, C.HEIGTH/2, Graphics.HCENTER|Graphics.TOP);

		} break;
		case 1 : {
			for(int i=0; i<4; i++) {
				switch(i){
				case 1 : {
					canvas.drawImage(ima_title_on[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
				}break;
				default :
					canvas.drawImage(ima_title_off[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
					break;
				}
			}
			canvas.drawImage(img_no, C.WIDTH/2, C.HEIGTH/2, Graphics.HCENTER|Graphics.TOP);
		} break;
		case 2 : {
			for(int i=0; i<4; i++) {
				switch(i){
				case 2 : {
					canvas.drawImage(ima_title_on[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
				}break;
				default :
					canvas.drawImage(ima_title_off[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
					break;
				}
			}
			canvas.drawImage(img_no, C.WIDTH/2, C.HEIGTH/2, Graphics.HCENTER|Graphics.TOP);
		} break;
		case 3 : {
			for(int i=0; i<4; i++) {
				switch(i){
				case 3 : {
					canvas.drawImage(ima_title_on[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
				}break;
				default :
					canvas.drawImage(ima_title_off[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
					break;
				}
			}
			canvas.drawImage(img_score_top_background, C.WIDTH/2, C.HEIGTH - 50, Graphics.HCENTER|Graphics.BOTTOM);
			
			drawRank(canvas);
			drawRankAll(canvas);
			canvas.drawString(score, 203, 435, 0);
			canvas.drawString(rank, 92, 461, 0);
			canvas.drawString(totalScore, 520, 435, 0);
			canvas.drawString(rankall, 392, 461, 0);
			
		} break;
		}
		
		/*for(int i=0; i<4; i++) {
			switch(i){
			case content_index : {
				canvas.drawImage(ima_title_on[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
			}break;
			default :
				canvas.drawImage(ima_title_off[i], 230 + i*90, 60, Graphics.TOP|Graphics.LEFT);
				break;
			}
		}*/
		canvas.drawImage(img_preKey, C.WIDTH, C.HEIGTH, Graphics.BOTTOM|Graphics.RIGHT);
		
	}
	
	public void drawRank(Graphics canvas){
		int size = CanvasControl.v_Rank.size();
		for (int i = 0; i < size; i++) {
			String[] stra_t = new String[3];
			stra_t = (String[])CanvasControl.v_Rank.elementAt(i);
			canvas.setColor(0);
			canvas.drawString(stra_t[1], 40, 	235+i*18, 0);
			canvas.drawString(stra_t[0], 80, 	235+i*18, 0);
			canvas.drawString(stra_t[2], 260, 	235+i*18, 0);
		}
	}
	
	public void drawRankAll(Graphics canvas) {
		int size = CanvasControl.v_RankAll.size();
		for (int i = 0; i < size; i++) {
			String[] stra_t = new String[3];
			stra_t = (String[])CanvasControl.v_RankAll.elementAt(i);
			canvas.setColor(0);
			canvas.drawString(stra_t[1], 350, 	235+i*18, 0);
			canvas.drawString(stra_t[0], 400, 	235+i*18, 0);
			canvas.drawString(stra_t[2], 560, 	235+i*18, 0);
		}
	}
	
	public void keyPressed(int keyCode){
		switch(keyCode) {
		/*case C.KEY_LEFT : {
			if(content_index != 0) {
				canvasControl.audioPlay.playSound((byte)0);
				content_index --;
			}
		} break;
		case C.KEY_RIGHT : {
			if(content_index != 3) {
				canvasControl.audioPlay.playSound((byte)0);
				content_index ++;
			}
		} break;*/
		case C.KEY_BACK :
		case C.KEY_BACK_ZX :
		case C.KEY_0 : {
//			canvasControl.audioPlay.playSound((byte)2);
			content_index = 3;
			canvasControl.state = CanvasControl.NULL;
			removeSource();
			canvasControl.activity_page = null;
			System.gc();
			canvasControl.setState(CanvasControl.HOME_PAGE);
		} break;
			
		}
	}

}
