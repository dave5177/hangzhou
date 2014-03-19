package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class PropShop{
	
	Screen s;
	
	/**
	 * 一些图标的显示位置
	 */
	private short[] shorta_position={
			508,262,508,413
	};

	/**
	 * 页码索引
	 */
	private byte rawindex;
	private byte colindex;
	private byte pageMax=1;
	
	private Image[] imagea_propshop;
	
	private int propshopcount = 6;
	
	/**
	 * 页码数字
	 */
	private Image [] imagea_pageNumber;
	
	/**
	 * 页码相关图标
	 * @param mainscreen
	 */
	private Image[] imagea_pageicon;
	
	public PropShop(Screen mainscreen){
		s=mainscreen;
	}
	
	public void init(){
		Image once = null;
		once = C.GetImageSource("/propshop/select.png");
		imagea_propshop = new Image[propshopcount];
		imagea_propshop[0]=C.GetImageSource("/propshop/psbackground.png");
		imagea_propshop[1]=C.GetImageSource("/propshop/p1.png");
		imagea_propshop[2]=C.GetImageSource("/propshop/p2.png");
		imagea_propshop[3]=Image.createImage(once, 0, 9, 28, 24, 0);//箭头
		imagea_propshop[4]=Image.createImage(once, 39, 0, 97, 35, 0);//选中按钮
		imagea_propshop[5]=C.GetImageSource("/propshop/unselect.png");//未选中按钮
		once = null;
		
		imagea_pageNumber = new Image[10];
		once = C.GetImageSource("/propshop/pagenumber.png");
		for (int a = 0; a < 10; a++) {
			imagea_pageNumber[a]=Image.createImage(once, a*32, 0, 32, 32, 0);
		}
		
		imagea_pageicon = new Image[5];
		once = C.GetImageSource("/propshop/pages.png");
		for (int a = 0; a < 5; a++) {
			imagea_pageicon[a] = Image.createImage(once, a*34, 0, 34, 34, 0);
		}
		once = null;
	}
	
	public void keyPressed(int keycode){
//		if(keycode==C.KEY_1||keycode==C.KEY_2||keycode==C.KEY_3||keycode==C.KEY_4){
//			Screen.beforeStatus=Screen.S_PROPSHOP;
//			Screen.status=Screen.S_NULL;
//			C.alertType=12;
//			C.BuyHowGoldHAMMER=keycode-48;
//			s.setGameStatus(Screen.S_ALERT);
//			s.repaint();
//		}
		if(keycode == C.KEY_RIGHT){
			if(rawindex<pageMax)++rawindex;
			s.repaint();
		}
		if(keycode == C.KEY_LEFT){
			if(rawindex>0)--rawindex;
			s.repaint();
		}
		if(keycode == C.KEY_DOWN){
			if(colindex<1)++colindex;
			s.repaint();
		}
		if(keycode == C.KEY_UP){
			if(colindex>0)--colindex;
			s.repaint();
		}
		if(keycode == C.KEY_FIRE){
			
		}
	}
	
	public void showMe(Graphics canvas){
		canvas.setClip(0, 0, C.screenwidth, C.screenheight);
		C.DrawImage_LEFTTOP(imagea_propshop[0], canvas);
		drawContent(canvas);//画内容
		drawPage(canvas);//画页码
		drawPageIcon(canvas);//画页码图标
		drawPosition(canvas);//画光标
	}
	
	/**
	 * 画光标
	 */
	public void drawPosition(Graphics canvas){
		if(colindex==0){
			C.DrawImage_VH(imagea_propshop[3], 430, 255, canvas);//箭头
			C.DrawImage_VH(imagea_propshop[4], 511, 255, canvas);
			C.DrawImage_VH(imagea_propshop[5], 511, 405, canvas);
		}else{
			C.DrawImage_VH(imagea_propshop[3], 430, 405, canvas);//箭头
			C.DrawImage_VH(imagea_propshop[5], 511, 255, canvas);
			C.DrawImage_VH(imagea_propshop[4], 511, 405, canvas);
		}
	}
	
	/**
	 * 画内容
	 * @param canvas
	 */
	public void drawContent(Graphics canvas){
		if(rawindex==0){
			C.DrawImage_VH(imagea_propshop[1], 318, 280, canvas);
		}else{
			C.DrawImage_VH(imagea_propshop[2], 318, 280, canvas);
		}
	}
	
	/**
	 * 画页码
	 */
	public void drawPage(Graphics canvas){
		C.DrawNumber_XY_RIGHTTOP(imagea_pageNumber, rawindex+1, 290, 440, 34, canvas);
		C.DrawImage_XY_LEFTTOP(imagea_pageicon[4], 310, 440, canvas);
		C.DrawNumber_XY_RIGHTTOP(imagea_pageNumber, 2, 330, 440, 34, canvas);
	}
	
	/**
	 * 画页码图标
	 */
	public void drawPageIcon(Graphics canvas){
		if(rawindex==0){//在最开始
			C.DrawImage_VH(imagea_pageicon[0], 268, 458, canvas);
			C.DrawImage_VH(imagea_pageicon[3], 385, 458, canvas);
		}else if(rawindex==1){//在最末尾
			C.DrawImage_VH(imagea_pageicon[1], 268, 458, canvas);
			C.DrawImage_VH(imagea_pageicon[2], 385, 458, canvas);
		}else{//上下都可以选择的时候
			C.DrawImage_VH(imagea_pageicon[1], 268, 458, canvas);
			C.DrawImage_VH(imagea_pageicon[3], 385, 458, canvas);
		}
	}
	
	public void removeSource(){
		for (int a = 0; a < propshopcount; a++) {
			imagea_propshop[a]=null;
		}
		imagea_propshop=null;
		System.gc();
	}
	
}
