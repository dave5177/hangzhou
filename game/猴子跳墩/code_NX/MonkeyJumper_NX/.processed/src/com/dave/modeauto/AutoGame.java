package com.dave.modeauto;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class AutoGame{
	private CanvasControl canvasControl;
	private byte banana = 0;
	private static boolean isContinuous;	//判断连续跳跃
	
	private final byte down_offer = 20;
	
	private static Image img_back;//背景图片
	private static Image img_top1;//顶部背景图片
	private static Image img_tree_left;//左右两侧的树的图片
	private static Image img_tree_right;//左右两侧的树的图片
	private static Image img_floor_brand;//记录层数的牌
	private static Image img_life_brand;//记录生命的牌
	private static Image img_score_brand;//记录得分的牌
	private static Image img_dog;//狗的身体图片
	private static Image img_thicket;//树丛图片
	private static Image img_pier;//树墩图
	private static Image img_bear;//熊身体图片
	private static Image img_bearhead;//熊头图片
	private static Image img_gameover;//游戏结束对话框
	private static Image img_continuousjump_dialog;//连续跳跃对话框
	private static Image img_continuousjump_word;//连续跳跃汉字
	private static Image img_banana;//香蕉
	private static Image img_preKey; 	//按9键帮助，按0返回
	
	private static Image[] img_wing;//连续跳跃翅膀
	//猴子
	private static Image[] img_monkey;
	private static Image[] img_monkey_eye;
	private static Image[] img_monkey_mouth;
	
	private static Image[] img_small_parrot; //小鹦鹉的两张图片
	private static Image[] img_dog_hand; //狗手两张图片
	
	//层数字、生命数字、得分数字
	private static Image[] img_number_floor;
	private static Image[] img_number_life;
	private static Image[] img_number_score;
	private static Image[] img_number_continuousjump;//连续跳跃数字
	
	private short dog_y; 						//画狗的y坐标.
	private short bearhead_y;					//熊头的y坐标
	private short tree_y;
	private short gameover_y;
	private short continuous_y = 300;
	
	public byte back_state = 0;					//控制整个画面的状态，0为正常状态，1为跳到第四层之后下拉的状态,2为游戏结束画面。
	private byte bit_index = 0;					//用来记录画数字时的个位数下标值
	private byte ten_index = 0;					//用来记录画数字时的十位数下标值
	private byte hundred_index = 0;				//用来记录画数字时的百位数下标值
	private byte parrot_index = 0;				//控制鹦鹉动画
	private byte parrot_index1 = 0;				//控制鹦鹉动画
	private byte parrot_index2 = 0;				//控制鹦鹉动画
	private short parrot_time = 0;
	private short continuousDialog_time = 0;
	private byte wing_index = 0;
	
	private byte monkey_jump_offer = 40;
	
	private long[] la_jump_time = new long[3];
	
	public Pier[] piers = new Pier[7];
	public Monkey monkey;
	
	private int monkey_i = 0;					//用来记录猴子的动画四张图片
	private byte dog_ud = 1;
	private byte bear_ud = 1;
	private boolean monkey_wake;
	private Image img_top2;
	private byte gameover_time;
	private byte dog_state;			//狗的状态，0为普通状态，1为上来招手状态。
	private byte dogHand_index;   	//狗手图片下标值。
	private byte dog_y_state;
	private byte continuous_index;
	
	public AutoGame(CanvasControl canvasControl){
		this.canvasControl = canvasControl;
		loadAllSources();
		piers[0] = new Pier(img_pier, (short)(476));
		for(int i=1; i<7; i++) {
			piers[i] = new Pier(img_pier, (short)(476 - i * 132));
			if(piers[i - 1].x_state == 2 || piers[i].x_state == 2) piers[i].x = piers[i - 1].x;
			if(i > 1) {if(piers[i - 2].x_state == 2 || piers[i - 1].x_state == 2) piers[i].x_state = 1;}
			if(piers[i - 1].x_state != 2) piers[i].x_state = 2;
		}
		monkey = new Monkey(canvasControl, piers, img_monkey, img_monkey_eye, img_monkey_mouth);
		isContinuous = false;
		bearhead_y = 360;
		dog_y = 519;
	}
	
	public void removeAllThisClassSources(){
		img_back = null;
		img_banana = null;
		img_top1 = null;//顶部背景图片
		img_top2 = null;//顶部背景图片
		img_tree_left = null;//左右两侧的树的图片
		img_tree_right = null;//左右两侧的树的图片
		img_floor_brand = null;//记录层数的牌
		img_life_brand = null;//记录生命的牌
		img_score_brand = null;//记录得分的牌
		img_dog = null;//狗的身体图片
		img_thicket = null;//树丛图片
		img_pier = null;//树墩图
		img_bear = null;//熊身体图片
		img_bearhead = null;//熊头图片
		img_gameover = null;//游戏结束对话框
		img_continuousjump_dialog = null;//连续跳跃对话框
		img_continuousjump_word = null;//连续跳跃汉字
		img_preKey = null; 	//按9键返回
		
		img_small_parrot[0] = null;
		img_small_parrot[1] = null;
		img_small_parrot = null;
		
		img_dog_hand[1] = null;
		img_dog_hand[1] = null;
		img_dog_hand = null;
		
		/*for(int i=0; i<4; i++) {
			img_monkey[i] = null;
			img_monkey_eye[i] = null;
			img_monkey_mouth[i] = null;
		}
		img_monkey = null;
		img_monkey_eye = null;
		img_monkey_mouth = null;*/
		
		for(int i=0; i<10; i++) {
			img_number_floor[i] = null;
			img_number_life[i] = null;
			img_number_score[i] = null;
			img_number_continuousjump[i] = null;
		}
		img_number_floor = null;
		img_number_life = null;
		img_number_score = null;
		img_number_continuousjump = null;
		
		System.gc();
	}
	
	public void removeMonkeySources() {
		for(int i=0; i<4; i++) {
			img_monkey[i] = null;
			img_monkey_eye[i] = null;
			img_monkey_mouth[i] = null;
		}
		img_monkey = null;
		img_monkey_eye = null;
		img_monkey_mouth = null;
		
		monkey = null;
	}
	
	public void loadAllSources() {
		try {
			img_monkey = new Image[4];
			img_monkey_eye = new Image[4];
			img_monkey_mouth = new Image[4];
			
			img_number_floor = new Image[10];
			img_number_life = new Image[10];
			img_number_score = new Image[10];
			img_number_continuousjump = new Image[10];
			
			img_small_parrot = new Image[2];
			img_dog_hand = new Image[2];
			
			img_back = Image.createImage("/modeauto/g_back.png");
			img_top1 = Image.createImage("/modeauto/top1.png");
			img_top2 = Image.createImage("/modeauto/top2.png");
			img_tree_left = Image.createImage("/modeauto/treeLeft.png");
			img_tree_right = Image.createImage("/modeauto/treeRight.png");
			img_floor_brand = Image.createImage("/modeauto/floorBrand.png");
			img_life_brand = Image.createImage("/modeauto/lifeBrand.png");
			img_score_brand = Image.createImage("/modeauto/scoreBrand.png");
			img_dog = Image.createImage("/modeauto/dog.png");
			img_thicket = Image.createImage("/modeauto/thicket.png");
			img_pier = Image.createImage("/modeauto/pier.png");
			img_bear = Image.createImage("/modeauto/bear.png");
			img_bearhead = Image.createImage("/modeauto/bearhead.png");
			img_preKey = Image.createImage("/home/back_help.png");
			
			
			Image floorNumber = Image.createImage("/modeauto/floorNumber.png");
			int w = floorNumber.getWidth();
			int h = floorNumber.getHeight();
			for(int i=0; i<10; i++) {
				if(i < 9) img_number_floor[i + 1] = Image.createImage(floorNumber, i * (w / 10), 0, w / 10, h, 0);
				else img_number_floor[0] = Image.createImage(floorNumber, 9 * (w / 10), 0, w / 10, h, 0);
			}
			
			Image lifeNumber = Image.createImage("/modeauto/lifeNumber.png");
			w = lifeNumber.getWidth();
			h = lifeNumber.getHeight();
			for(int i=0; i<10; i++) {
				if(i < 9) img_number_life[i + 1] = Image.createImage(lifeNumber, i * (w / 10), 0, w / 10, h, 0);
				else img_number_life[0] = Image.createImage(lifeNumber, 9 * (w / 10), 0, w / 10, h, 0);
			}
			
			Image scoreNumber = Image.createImage("/modeauto/scoreNumber.png");
			w = scoreNumber.getWidth();
			h = scoreNumber.getHeight();
			for(int i=0; i<10; i++) {
				if(i < 9) img_number_score[i + 1] = Image.createImage(scoreNumber, i * (w / 10), 0, w / 10, h, 0);
				else img_number_score[0] = Image.createImage(scoreNumber, 9 * (w / 10), 0, w / 10, h, 0);
			}
			
			Image jumpNumber = Image.createImage("/modeauto/continuousJumpNumber.png");
			w = jumpNumber.getWidth();
			h = jumpNumber.getHeight();
			for(int i=0; i<10; i++) {
				if(i < 9) img_number_continuousjump[i + 1] = Image.createImage(jumpNumber, i * (w / 10), 0, w / 10, h, 0);
				else img_number_continuousjump[0] = Image.createImage(jumpNumber, 9 * (w / 10), 0, w / 10, h, 0);
			}
			
			img_small_parrot[0] = Image.createImage("/modeauto/smallParrot1.png");
			img_small_parrot[1] = Image.createImage("/modeauto/smallParrot2.png");
			
			img_dog_hand[0] = Image.createImage("/modeauto/dogHand1.png");
			img_dog_hand[1] = Image.createImage("/modeauto/dogHand2.png");
			
			img_monkey[0] = Image.createImage("/modeauto/monkey1.png");
			img_monkey[1] = Image.createImage("/modeauto/monkey2.png");
			img_monkey[2] = Image.createImage("/modeauto/monkey3.png");
			img_monkey[3] = img_monkey[1];
			
			img_monkey_eye[0] = Image.createImage("/modeauto/eyeLeft.png");
			img_monkey_eye[1] = Image.createImage("/modeauto/eyeCenter.png");
			img_monkey_eye[2] = Image.createImage("/modeauto/eyeRight.png");
			img_monkey_eye[3] = Image.createImage("/modeauto/eyeClose.png");
			
			img_monkey_mouth[0] = Image.createImage("/modeauto/mouth1.png");
			img_monkey_mouth[1] = Image.createImage("/modeauto/mouth2.png");
			img_monkey_mouth[2] = Image.createImage("/modeauto/mouth3.png");
			img_monkey_mouth[3] = img_monkey_mouth[1];
			
		} catch (IOException e) {
			System.out.println("图片路径错误");
			e.printStackTrace();
		}
	}
	
	public void keyPressed(int keyCode){
			switch(keyCode) {
			case C.KEY_FIRE : {
				if(back_state == 0) {
					if(monkey.state == 0) {
//						canvasControl.audioPlay.playSound((byte)1);
						monkey.jump();
					}
				}
			} break;
			case C.KEY_BACK :
			case C.KEY_BACK_ZX :
			case C.KEY_0 : {
//				canvasControl.audioPlay.playSound((byte)2);
				canvasControl.state_Back_For = CanvasControl.GAME_PAGE;
				canvasControl.state = CanvasControl.NULL;
				canvasControl.setState(CanvasControl.ALERT_PAGE);
				canvasControl.alert_page.type = C.A_TYPE_OUT;
			} break;
			case C.KEY_1 : {
				canvasControl.nx_alert.show();
//				canvasControl.audioPlay.playSound((byte)2);
//				canvasControl.state_Back_For = CanvasControl.GAME_PAGE;
//				canvasControl.state = CanvasControl.NULL;
//				canvasControl.setState(CanvasControl.ALERT_PAGE);
//				canvasControl.alert_page.type = C.A_TYPE_BUY;
			} break;
			case C.KEY_9 : {
//				canvasControl.audioPlay.playSound((byte)2);
				canvasControl.state = CanvasControl.NULL;
				canvasControl.state_Back_For = CanvasControl.GAME_PAGE;
				canvasControl.setState(CanvasControl.HELP_PAGE);
			} break;
				
			}
	}
	
	public void showMe(Graphics canvas){
		//背景
		canvas.drawImage(img_back, 0, 0, Graphics.TOP|Graphics.LEFT);

	
		
//		canvas.setColor(255, 255, 255);
//		canvas.fillRect(0, 0, C.WIDTH, C.HEIGTH);
		switch(back_state) {
		case 0 : {
			canvas.drawImage(img_tree_left, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_tree_right, C.WIDTH, C.HEIGTH,
					Graphics.BOTTOM|Graphics.RIGHT);

			canvas.drawImage(img_top1, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_top2, C.WIDTH, 0, Graphics.TOP|Graphics.RIGHT);
			
			//牌子
			canvas.drawImage(img_floor_brand, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_life_brand, 0, C.HEIGTH/3, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_score_brand, C.WIDTH, 0, Graphics.TOP|Graphics.RIGHT);
			C.DrawNumber_XY_RIGHTTOP(img_number_score, CanvasControl.totalScore, 580, 70, 15, canvas);
			C.DrawNumber_XY_RIGHTTOP(img_number_score, CanvasControl.game_score, 580, 140, 15, canvas);
			C.DrawNumber_XY_RIGHTTOP(img_number_life, monkey.life, 100, 288, 20, canvas);
			
			//层数字，小鹦鹉动画。
			for(int i=0; i<3; i++) {
				switch(i) {
				case 0 : {
					canvas.drawImage(img_number_floor[hundred_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index2], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				case 1 : {
					canvas.drawImage(img_number_floor[ten_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index1], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				case 2 : {
					canvas.drawImage(img_number_floor[bit_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				}
			}
			
			//树墩
			for(int i=0; i<4; i++) {
				piers[i].draw(canvas);
			}
			
			//猴子的动画
			if(back_state == 0) {
				monkey.draw(canvas, monkey_i >> 1);
			}
		} break;
		
		case 1 : {
			//背景
			canvas.drawImage(img_tree_left, 0, tree_y, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_tree_left, 0, tree_y, Graphics.BOTTOM|Graphics.LEFT);
			canvas.drawImage(img_tree_right, C.WIDTH, tree_y + C.HEIGTH, Graphics.BOTTOM|Graphics.RIGHT);
			canvas.drawImage(img_tree_right, C.WIDTH, tree_y, Graphics.BOTTOM|Graphics.RIGHT);

			canvas.drawImage(img_top1, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_top2, C.WIDTH, 0, Graphics.TOP|Graphics.RIGHT);
			
			//牌子
			canvas.drawImage(img_floor_brand, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_life_brand, 0, C.HEIGTH/3, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_score_brand, C.WIDTH, 0, Graphics.TOP|Graphics.RIGHT);
			C.DrawNumber_XY_RIGHTTOP(img_number_score, CanvasControl.totalScore, 580, 70, 15, canvas);
			C.DrawNumber_XY_RIGHTTOP(img_number_score, CanvasControl.game_score, 580, 140, 15, canvas);
			C.DrawNumber_XY_RIGHTTOP(img_number_life, monkey.life, 100, 288, 20, canvas);
			
			//层数字，小鹦鹉动画。
			for(int i=0; i<3; i++) {
				switch(i) {
				case 0 : {
					canvas.drawImage(img_number_floor[hundred_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index2], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				case 1 : {
					canvas.drawImage(img_number_floor[ten_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index1], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				case 2 : {
					canvas.drawImage(img_number_floor[bit_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				}
			}
			
			//树墩
			for(int i=0; i<7; i++) {
				piers[i].draw(canvas);
			}
			
			//猴子的动画
			monkey.draw(canvas, 1);
		} break;
		case 2 : {
			canvas.drawImage(img_tree_left, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_tree_right, C.WIDTH, C.HEIGTH,
					Graphics.BOTTOM|Graphics.RIGHT);

			canvas.drawImage(img_top1, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_top2, C.WIDTH, 0, Graphics.TOP|Graphics.RIGHT);
			
			//牌子
			canvas.drawImage(img_floor_brand, 0, 0, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_life_brand, 0, C.HEIGTH/3, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_score_brand, C.WIDTH, 0, Graphics.TOP|Graphics.RIGHT);
			C.DrawNumber_XY_RIGHTTOP(img_number_score, CanvasControl.totalScore, 580, 70, 15, canvas);
			C.DrawNumber_XY_RIGHTTOP(img_number_score, CanvasControl.game_score, 580, 140, 15, canvas);
			C.DrawNumber_XY_RIGHTTOP(img_number_life, 0, 100, 288, 20, canvas);
			
			//层数字，小鹦鹉动画。
			for(int i=0; i<3; i++) {
				switch(i) {
				case 0 : {
					canvas.drawImage(img_number_floor[hundred_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index2], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				case 1 : {
					canvas.drawImage(img_number_floor[ten_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index1], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				case 2 : {
					canvas.drawImage(img_number_floor[bit_index], 96 + i * 34, 88, Graphics.TOP|Graphics.HCENTER);
					canvas.drawImage(img_small_parrot[parrot_index], 96 + i * 34, 55, Graphics.TOP|Graphics.HCENTER);
				} break;
				}
			}
			
			
			//树墩
			for(int i=0; i<4; i++) {
				piers[i].draw(canvas);
			}
			canvas.drawImage(img_gameover, C.WIDTH/2, gameover_y, Graphics.BOTTOM|Graphics.HCENTER);
		} break;
		}
		
		
		//熊与狗
		canvas.drawImage(img_bear, C.WIDTH, C.HEIGTH - 50, Graphics.BOTTOM|Graphics.RIGHT);
		canvas.drawImage(img_bearhead, C.WIDTH - 30, bearhead_y, Graphics.BOTTOM|Graphics.RIGHT);
		switch(dog_state) {
		case 0:
			canvas.drawImage(img_dog, 20, dog_y, Graphics.BOTTOM|Graphics.LEFT);
			break;
		case 1:
			if(dogHand_index > 0) canvas.drawImage(img_dog_hand[dogHand_index % 2], 60, dog_y - 80, Graphics.BOTTOM|Graphics.LEFT);
			canvas.drawImage(img_dog, 20, dog_y, Graphics.BOTTOM|Graphics.LEFT);
		}
		canvas.drawImage(img_thicket, 0, C.HEIGTH, Graphics.BOTTOM|Graphics.LEFT);
		canvas.drawImage(img_preKey, C.WIDTH, C.HEIGTH, Graphics.BOTTOM|Graphics.RIGHT);
		
		
		//连续跳跃动画
		if(isContinuous) {
			canvas.drawImage(img_wing[wing_index], 400, continuous_y - 50, Graphics.BOTTOM|Graphics.HCENTER);
			canvas.drawImage(img_continuousjump_dialog, 400, continuous_y, Graphics.BOTTOM|Graphics.HCENTER);
			canvas.drawImage(img_continuousjump_word, 400, continuous_y - 10, Graphics.BOTTOM|Graphics.HCENTER);
			canvas.drawImage(img_number_continuousjump[continuous_index], 400, continuous_y - 50, Graphics.BOTTOM|Graphics.HCENTER);
		}
		
		//香蕉
		for(int i=0; i<banana; i++) {
			canvas.drawImage(img_banana, 500 + i*20, 500, Graphics.BOTTOM|Graphics.HCENTER);
		}
		
	}
	
	public void relive() {
		monkey.life = 5;
	}
	
	public void logi() {
		switch(back_state) {
		case 2 :{//游戏结束
			if(gameover_y < 350) {
				gameover_y += 40;
			} else gameover_time ++;
			check();
			return;
		}
		case 0 : {//游戏正常运行
			switch(dog_state) {
			case 0 : {
				if(dog_y > 520 || dog_y < 500) dog_ud *= -1;
				dog_y += dog_ud;
			} break;
			case 1 :{
				switch(dog_y_state){
				case 0 :
					dog_y -= 10;
					if(dog_y <= 460) dog_y_state = 1;
					break;
				case 1:
					dogHand_index ++;
					if(dogHand_index == 8) {
						dog_y_state = 2;
						dogHand_index = 0;
					}
					break;
				case 2:
					dog_y +=10;
					if(dog_y >= 510) {
						dog_state = 0;
						dog_y_state = 0;
					}
					break;
				}
			}break;
			}
			if(bearhead_y < 360 || bearhead_y > 370) bear_ud *= -1;
			bearhead_y += bear_ud;
			
			//猴子起跳和下落的动画
			switch(monkey.state) {
			case 2 :
				monkey_jump_offer -= 2;
			case 1 : {
				monkey_jump_offer -= 5;
				monkey.y -= monkey_jump_offer;
			} break;
			case 0 :
				if(monkey_wake){
					monkey_i ++;
				}
				if(!monkey_wake){
					if(C.R.nextInt(8) == 1) monkey_wake = true;
				}
			}
			
			for(int i=0; i<4; i++) {
				piers[i].move();
			}
			//猴子动画
			monkey.move();
		} break;
			
		case 1 : {//画面下拉状态
				for(int i=0; i<7; i++) {
					piers[i].y += down_offer;
				}
				monkey.y += down_offer;
				tree_y += down_offer + 7;
		} break;
		}
		
		if(isContinuous) {//连跳动画
			if((continuousDialog_time / 5) % 2 == 0) {
				continuous_y ++;
			}
			if((continuousDialog_time / 5) % 2 != 0) {
				continuous_y --;
			}
			continuousDialog_time ++;
			if(continuousDialog_time % 2 == 0) {
				wing_index ++;
				if(wing_index >= 3) wing_index = 0;
			}
			

			if(continuousDialog_time >= 40) {
				isContinuous = false; 
				continuousDialog_time = 0;
				
				img_continuousjump_dialog = null;
				img_continuousjump_word = null;
				img_wing = null;
			}
			
		}

		if(parrot_index == 1 || parrot_index1 == 1 || parrot_index2 ==1) parrot_time ++;
		if(parrot_time >= 5) {
			parrot_index = 0;
			parrot_index1 = 0;
			parrot_index2 = 0;
			parrot_time = 0;
		}
		check();
	}

	private void check() {
		switch(back_state) {
		case 2 : {
			if(gameover_time>=10) {
				canvasControl.state = CanvasControl.NULL;
				canvasControl.setState(CanvasControl.HOME_PAGE);
			}
		} break;
		case 1 : {
			if(monkey.y >= 476) {
				piers[0] = piers[3];
				piers[1] = piers[4];
				piers[2] = piers[5];
				piers[3] = piers[6];
				
				for(int i=0; i<4; i++) {
					piers[i].y = (short)(476 - i * 132);
				}
				
				monkey.y = 476;
				
				for(int i=4; i<7; i++) {
					piers[i] = new Pier(img_pier, (short)(476 - i * 132));
					if(piers[i -1].x_state == 2 || piers[i].x_state == 2) piers[i].x = piers[i - 1].x;
					if(monkey.all_floor <= 15) {
						if(i > 1) {if(piers[i - 2].x_state == 2 || piers[i - 1].x_state == 2) piers[i].x_state = 1;}
						if(piers[i - 1].x_state != 2) piers[i].x_state = 2;
					}
					if(monkey.all_floor > 30) {
						piers[i].x_state = (byte)C.R.nextInt(1);
						if(monkey.all_floor % 30 == 0) {
							Pier.x_offer ++;
						}
					}
				}
				
				back_state = 0;
				monkey.floor = 0;
				tree_y = 0;
			}
		} break;
		case 0 : {
			switch(monkey.state) {
			case 0 : {
				if(monkey_i == 8) {monkey_i = 0; monkey_wake = false;}
				if(monkey.all_floor != monkey.old_floor) {
					monkey_jump_offer = 40;
					CanvasControl.game_score = CanvasControl.game_score + (monkey.all_floor / 3 + 1) * 10;
					if(monkey.all_floor % 3 == 0) CanvasControl.game_score += 100;
					
					if(la_jump_time[0] == 0) {
						la_jump_time[0] = System.currentTimeMillis();
						la_jump_time[1] = System.currentTimeMillis();
						la_jump_time[2] = System.currentTimeMillis();
					}
					la_jump_time[0] = la_jump_time[1];
					la_jump_time[1] = la_jump_time[2];
					la_jump_time[2] = System.currentTimeMillis();
					
					if(la_jump_time[2] - la_jump_time[1] < C.C_TIME && monkey.all_floor >= 2) {
						try {
							img_continuousjump_dialog = Image.createImage("/modeauto/continuousJumpDialog.png");
							Image temp = Image.createImage("/modeauto/continuousJumpWord.png");
							img_continuousjump_word = Image.createImage(temp, 0, 0, temp.getWidth(), temp.getHeight() / 2, 0);
							
							temp = Image.createImage("/modeauto/banana.png");
							img_banana = Image.createImage(temp, 0, 0, temp.getWidth(), temp.getHeight()/2, 0);
							
							img_wing = new Image[4];
							img_wing[0] = Image.createImage("/modeauto/wing1.png");
							img_wing[1] = Image.createImage("/modeauto/wing2.png");
							img_wing[2] = Image.createImage("/modeauto/wing3.png");
							img_wing[3] = img_wing[2];
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						if(la_jump_time[2] - la_jump_time[0] < C.C_TIME * 2 && monkey.all_floor >= 3) continuous_index = 3;
						else continuous_index = 2;
//						canvasControl.audioPlay.playSound((byte)3);
						isContinuous = true;
						banana ++;
						if(banana >= 6) {
							monkey.life ++;
							banana = 0;
						}
					}
					if(monkey.floor == 3) {
						back_state = 1;
					}
					
					if(monkey.all_floor % 10 != 0) {
						parrot_index = 1;
						bit_index ++;
					}
					if(monkey.all_floor % 10 == 0 && monkey.all_floor % 100 != 0) {
						parrot_index = 1; parrot_index1 = 1;
						ten_index ++; bit_index = 0;
					}
					if(monkey.all_floor % 100 == 0) {
						parrot_index = 1; parrot_index1 = 1; parrot_index2 = 1;
						hundred_index ++; ten_index = 0; bit_index = 0;
					}
					monkey.old_floor = monkey.all_floor;
				}
			} break;
			case 1 : {
				if(monkey_jump_offer == 0) monkey.state = 2;
			} break;
			case 2 : {
				if(monkey.y >= 680) {
					monkey.life --;
					if(monkey.life >= 0) {
						dog_state = 1;
						monkey_jump_offer = 40;
						monkey.state = 0;
						monkey.y = monkey.pier[monkey.floor].y;
						monkey.x = monkey.pier[monkey.floor].x;
					}
				}
				if(monkey.life < 0) {
					canvasControl.state_Back_For = CanvasControl.GAME_PAGE;
					canvasControl.state = CanvasControl.NULL;
					canvasControl.nx_alert.show();
//					canvasControl.setState(CanvasControl.ALERT_PAGE);
//					canvasControl.alert_page.type = C.A_TYPE_RELIVE;
					try {
						img_gameover = Image.createImage("/modeauto/gameover.png");
					} catch (IOException e) {
						e.printStackTrace();
					}
					//back_state = 2;
				}
			} break;
			}
			
		} break;
		}
		
	}
}
