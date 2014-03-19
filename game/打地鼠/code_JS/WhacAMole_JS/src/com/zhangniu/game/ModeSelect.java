package com.zhangniu.game;


import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ModeSelect {
	
	private Screen s;
	
	private Image[]  imagea_ModeSelect;
	private Image[]  imagea_Content;
	private Image[]  imagea_SelectButton;
	
	private final short CONTENT_X=314;//内容的坐标
	private final short CONTENT_Y=176;//内容的坐标
	
	private int select_Index;
	
	private short [] inta_BUTTON_POSITION= {
			132,365,
			320,365,
			509,365
			};
	
	public ModeSelect(Screen screen){
		s = screen;
	}
	
	public void init(){
		imagea_ModeSelect=new Image[2];
		imagea_ModeSelect[0]=C.GetImageSource("/modeselect/msbackground.png");//背景
		imagea_ModeSelect[1]=C.GetImageSource("/modeselect/jt.png");//箭头
		
		imagea_Content = new Image[3];
		imagea_Content[0]=C.GetImageSource("/modeselect/msguanka.png");//关卡模式
		imagea_Content[1]=C.GetImageSource("/modeselect/msjishi.png");//计时模式
		imagea_Content[2]=C.GetImageSource("/modeselect/mstiaozhan.png");//挑战模式
		
		imagea_SelectButton = new Image[3];
		imagea_SelectButton[0]=C.GetImageSource("/modeselect/msgk.png");//关卡
		imagea_SelectButton[1]=C.GetImageSource("/modeselect/msjs.png");//计时
		imagea_SelectButton[2]=C.GetImageSource("/modeselect/mstz.png");//挑战
		System.gc();
	}
	
	public void removeAllSource() {
		
		imagea_ModeSelect[0]=null;//背景
		imagea_ModeSelect[1]=null;//箭头
		imagea_ModeSelect=null;
		
		imagea_Content[0]=null;//关卡模式
		imagea_Content[1]=null;//计时模式
		imagea_Content[2]=null;//挑战模式
		imagea_Content=null;
		
		imagea_SelectButton[0]=null;//关卡
		imagea_SelectButton[1]=null;//计时
		imagea_SelectButton[2]=null;//挑战
		imagea_SelectButton=null;
		
		System.gc();
	}

	public void keyPressed(int key){
		switch(key){
		case C.KEY_BACK_ZX:
		case C.KEY_BACK:
		case C.KEY_0:{
			Screen.status = Screen.S_NULL;
			removeAllSource();
			s.setGameStatus(Screen.S_MENU);
		}break;
		case C.KEY_FIRE:{//确定键，切换游戏状态了
			switch(select_Index){
			case 0:{//关卡模式
				C.level = 0;
				Screen.status=Screen.S_NULL;
				removeAllSource();
				C.passed = false;
				s.setGameStatus(Screen.S_GAME_LEVEL);
			}break;
			case 1:{//计时模式
				C.level = 0;
				Screen.status=Screen.S_NULL;
				removeAllSource();
				C.passed = false;
				s.setGameStatus(Screen.S_GAME_TIME);
			}break;
			case 2:{//关卡模式
				C.level = 0;
				Screen.status=Screen.S_NULL;
				removeAllSource();
				C.passed = false;
				s.setGameStatus(Screen.S_GAME_CHALLENGE);
			}break;
			}
		}break;
		case C.KEY_LEFT:
		case C.KEY_UP:{
			if(select_Index<=0)return;
			--select_Index;
			s.repaint();
		}break;
		case C.KEY_RIGHT:
		case C.KEY_DOWN:{
			if(select_Index>=2)return;
			++select_Index;
			s.repaint();
		}break;
		}
	}
	
	public void showMe(Graphics g) {
		g.setClip(0, 0, C.screenwidth, C.screenheight);
		C.DrawImage_LEFTTOP(imagea_ModeSelect[0], g);//画背景
		drawContent(g);//画文字说明
		drawButton(g);//画按钮
	}

	/**
	 * 画正在等待
	 * @param g
	 */
	public void drawWait(Graphics g){
		g.setClip(0, 0, C.screenwidth, C.screenheight);
		Image wait = C.GetImageSource("/menu/wait.png");
		C.DrawImage_VH(wait, C.screenwidth>>1, C.screenheight>>1, g);
		wait=null;
		System.gc();
	}
	
	/**
	 * 画文字说明
	 */
	public void drawContent(Graphics canvas){
		C.DrawImage_VH(imagea_Content[select_Index], CONTENT_X, CONTENT_Y, canvas);
	}
	
	/**
	 * 画焦点
	 * @param g
	 */
	public void drawButton(Graphics canvas) {
		C.DrawImage_VH(imagea_SelectButton[select_Index], //按键底
				inta_BUTTON_POSITION[select_Index*2], 
				inta_BUTTON_POSITION[select_Index*2+1], canvas);
		C.DrawImage_VH(imagea_ModeSelect[1], //箭头
				inta_BUTTON_POSITION[select_Index*2], 
				inta_BUTTON_POSITION[select_Index*2+1]-133, canvas);
	}

}
