package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import com.zhangniu.update.ServerIptv;

public class SmashGoldEggs{
	
	private Screen s;
	
	/**
	 * �������õ���ͼƬ
	 */
	Image[] imagea_smashGold;
	
	/**
	 * �𴸸�����ͼƬ(��ɫ)
	 */
	Image [] imagea_HummerNumber;
	
	/**
	 * �𵰻��ҿ���ģ����鶯��
	 */
	Image [] imagea_egg;
	
	/**
	 * ����Ķ���
	 */
	Image [] imagea_mouseImg;
	
	/**
	 * �𵰵�״̬
	 */
	int [] inta_goldEggsState;
	
	/**
	 * ���ڱ��ҿ�
	 */
	boolean EggBreaking;
	
	//��ͷλ��
	int hammerPostion;
	
	private byte smashFlashindex;
	
	/**
	 * �Ƿ��ҳ��й�
	 */
	private byte smash_Auto;
	
	/**
	 * �Ƿ��ҳ���������
	 */
	private byte smash_life;
	
	public Image hammer15;
	public Image hammer16;
	
	/**
	 * ��ʾ��ʱ����������
	 * [0]		X����
	 * [1]		Y����
	 * [2]		����Ŀ�ʼֵ,һ��ʼƫ��
	 */
	private short[] bytea_mp ={115,308,40};
	
	/**
	 * ��ʾ��ʱ��𵰵�����
	 */
	private short [] bytea_ep = {115,290,90};
	
//	/**
//	 * ��Ʒ�Ĵ���
//	 */
//	private byte[] bytea_jiangping={
//			0,1,2,3,4
//	};
	
	/**
	 * ������ֵ
	 */
	private int [] inta_valueJiangPing = {3000,5000,7000,9000,10000};
//	
//	/**
//	 * ��ƷͼƬ
//	 */
//	private Image [] imagea_jiangping;
	
	/**
	 * ��Ʒ��ֵͼƬ
	 */
	private Image [] imagea_JPNumber;
	
	/**
	 * ���캯��
	 */
	public SmashGoldEggs(Screen screen){
		s = screen;
	}
	
	/**
	 * ����λ��
	 */
	private void changePosition(int lenght){
		
		for (int a = 0; a < lenght; a++) {
			int y = C.random.nextInt(5);
			int x = C.random.nextInt(5);
			int yuan = inta_valueJiangPing[y];
			inta_valueJiangPing[y]=inta_valueJiangPing[x];
			inta_valueJiangPing[x]=yuan;
			C.out("����λ��x:"+x);
			C.out("����λ��y:"+y);
		}
	}
	
//	/**
//	 * ������Ψһ���ֵ�����
//	 */
//	public void createArray(){
//		int a=0;
//		a= C.random.nextInt(10);
//		for(int b =0;b<5;b++){
//			if(a+b>9){
//				a=0;
//			}
//			newGoldEggsArray[b]=
//				C.GOLDEGGS_ARRAY[a];
//			++a;
//		}
//	}
	
	/**
	 * ����������Դ
	 */
	public void init(){
		
		smash_Auto =0;
		smash_life =0;
		
		Image useOnce;
		
		imagea_JPNumber = new Image[10];
		useOnce = C.GetImageSource("/smashgoldeggs/7.png");
		for (int i = 0; i < 10; i++) {
			imagea_JPNumber[i]=
				Image.createImage(useOnce, i*17, 0, 17, 20, 0);
		}
		useOnce=null;
		
		hammer15 = C.GetImageSource("/game/hmup.png");
		hammer16 = C.GetImageSource("/game/hmdown.png");
		
		inta_goldEggsState = new int[5];
//		newGoldEggsArray = new int[5];
//		createArray();
		
		imagea_mouseImg = new Image[10];
		for (int a = 0; a < 10; a++) {
			imagea_mouseImg[a]=C.GetImageSource("/smashgoldeggs/m"+a+".png");
		}
		
		//��ԭ�����е�ֵ�ÿ�
		hammerPostion=0;
		for(int a =0;a<5;a++){
			inta_goldEggsState[a]=0;
		}
		imagea_smashGold= new Image[1];
		imagea_smashGold[0]=C.GetImageSource("/smashgoldeggs/0.png");
		
		useOnce=C.GetImageSource("/smashgoldeggs/4.png");
		imagea_HummerNumber = new Image[10];
		for(int a =0;a<10;a++){
			imagea_HummerNumber[a]=Image.createImage(useOnce, a*31, 0, 31, 50, 0);
		}
		useOnce=null;
		System.gc();
		
		useOnce=C.GetImageSource("/smashgoldeggs/6.png");
		imagea_egg = new Image[5];
		for(int a =0;a<5;a++){
			imagea_egg[a]=Image.createImage(useOnce, a*130, 0, 130, 96, 0);
		}
		useOnce=null;
//		useOnce = C.GetImageSource("/smashgoldeggs/jiangping.png");
//		imagea_jiangping = new Image[5];
//		for (int a = 0; a < 5; a++) {
//			imagea_jiangping[a]=Image.createImage(useOnce, 0, a*29, 94, 29, 0);
//		}
//		useOnce = null;
		System.gc();
		changePosition(10);//����λ��
	}
	
	/**
	 * �ÿ�������Դ
	 */
	public void removeClassSources(){
		hammer15 = null;
		hammer16 = null;
		
		for (int a = 0; a < 10; a++) {
			imagea_mouseImg[a]=null;
		}
		imagea_mouseImg = null;
		
		imagea_smashGold[0]=null;
		imagea_smashGold = null;
		
		for(int a =0;a<10;a++){
			imagea_HummerNumber[a]=null;
		}
		imagea_HummerNumber = null;
		
		for(int a =0;a<5;a++){
			imagea_egg[a]=null;
		}
		imagea_egg = null;
		
//		for (int a = 0; a < 5; a++) {
//			imagea_jiangping[a]=null;
//		}
//		imagea_jiangping = null;
		System.gc();
		
	}
	
	/**
	 * ����ǰ����
	 */
	public void showME(Graphics canvas){
		canvas.setClip(0, 0, C.screenwidth, C.screenheight);
		C.DrawImage_LEFTTOP(imagea_smashGold[0], canvas);//�ҵ�����
		for(int a =0;a<5;a++){
			if(inta_goldEggsState[a]==0){//��û�б��ҿ�
				C.DrawImage_XY_LEFTBUTTOM(imagea_mouseImg[a], bytea_mp[2]+a*bytea_mp[0], bytea_mp[1], canvas);//������
				C.DrawImage_VH(imagea_egg[0], bytea_ep[2]+a*bytea_ep[0], bytea_ep[1], canvas);//��
			}else if(inta_goldEggsState[a]==0){
				
			}else if(inta_goldEggsState[a]==2){//�Ѿ����ҿ�
				C.DrawImage_XY_LEFTBUTTOM(imagea_mouseImg[a+5], bytea_mp[2]+a*bytea_mp[0], bytea_mp[1], canvas);//������
				C.DrawImage_VH(imagea_egg[4], bytea_ep[2]+a*bytea_ep[0], bytea_ep[1], canvas);//��
//				C.DrawImage_VH(imagea_jiangping[bytea_jiangping[a]], 
//						bytea_ep[2]+a*bytea_ep[0], bytea_ep[1], canvas);//�ҳ����Ķ���
				
				C.DrawNumber_XY_RIGHTTOP(imagea_JPNumber, 
						inta_valueJiangPing[a],
						125+a*bytea_mp[0],bytea_mp[1]+30, 20, canvas);//�ҵ�����������
			}
		}
		C.DrawImage_XY_LEFTTOP(hammer15, 40+hammerPostion*125, 160, canvas);//��Ԥ����
		C.DrawNumber_XY_RIGHTTOP(imagea_HummerNumber, C.goldHammer_Count, 590, 438, 30, canvas);//���𴸸���
		
		if(EggBreaking)drawSmashFlash(canvas);//���Ҷ���
	}
	
	/**
	 * �ҽ𵰶���
	 */
	public void drawSmashFlash(Graphics canvas){
//		c.setClip(hammerPostion*125, 160, 160, 227);
		C.DrawImage_XY_LEFTBUTTOM(imagea_mouseImg[hammerPostion], bytea_mp[2]+hammerPostion*bytea_mp[0], bytea_mp[1], canvas);//������
		switch(smashFlashindex){
		case 0:{
			C.DrawImage_VH(imagea_egg[0], bytea_ep[2]+hammerPostion*bytea_ep[0], bytea_ep[1], canvas);//��
			C.DrawImage_XY_LEFTTOP(hammer15, 40+hammerPostion*125, 160, canvas);
		}break;
		case 1:{
			C.DrawImage_VH(imagea_egg[0], bytea_ep[2]+hammerPostion*bytea_ep[0], bytea_ep[1], canvas);//��
			C.DrawImage_XY_LEFTTOP(hammer16, 40+hammerPostion*125, 160, canvas);
		}break;
		case 2:{
			C.DrawImage_VH(imagea_egg[0], bytea_ep[2]+hammerPostion*bytea_ep[0], bytea_ep[1], canvas);//��
			C.DrawImage_XY_LEFTTOP(hammer15, 40+hammerPostion*125, 160, canvas);
		}break;
		case 3://���ƿ��Ķ���
		case 4:
		case 5:
		case 6:{
			C.DrawImage_XY_LEFTTOP(hammer15, 40+hammerPostion*125, 160, canvas);
			C.DrawImage_VH(imagea_egg[smashFlashindex-3], bytea_ep[2]+hammerPostion*bytea_ep[0], bytea_ep[1], canvas);//��
		}break;
		case 7:{//����������
//			C.DrawImage_VH(imagea_jiangping[bytea_jiangping[hammerPostion]], bytea_ep[2]+hammerPostion*bytea_ep[0], bytea_ep[1], canvas);//��
//			C.DrawNumber_XY_RIGHTTOP(imagea_jiangping[bytea_jiangping[hammerPostion]], newGoldEggsArray[hammerPostion],125+hammerPostion*bytea_mp[0],bytea_mp[1]+30, 20, canvas);//�ҵ�����������
			C.DrawNumber_XY_RIGHTTOP(imagea_JPNumber, 
					inta_valueJiangPing[hammerPostion],
					125+hammerPostion*bytea_mp[0],bytea_mp[1]+30, 20, canvas);//�ҵ�����������
		}break;
		}
		if(smashFlashindex==7){
//			C.challenge_Score+=newGoldEggsArray[hammerPostion];
//			chuli();//���ҳ����Ķ�������һ��
			inta_goldEggsState[hammerPostion]=2;
			smashFlashindex=0;
			C.receiveKeyPressed=true;
			EggBreaking=false;
		}
		++smashFlashindex;
	}
	
//	/**
//	 * �����ҳ����Ķ���
//	 */
//	public void chuli(){
//		byte key = bytea_jiangping[hammerPostion];
//		switch (key) {
//		case 1:{//150����
//			C.challenge_Score+=150;
//		}break;
//		case 2:{//�йܵ���
//			++smash_Auto;
//		}break;
//		case 3:{//��������
//			++smash_life;
//		}break;
//		case 4:{//100����
//			C.challenge_Score+=100;
//		}break;
//		}
//	}
	
	/**
	 * �ҽ𵰽��水���¼�
	 */
	public void keyPressed(int keyCode){
		if(!C.receiveKeyPressed)return;
		switch (keyCode) {
		case C.KEY_FIRE: {
			if (C.goldHammer_Count <= 0) {
				Screen.beforeStatus=Screen.S_SMASH;
				Screen.status=Screen.S_NULL;
				C.alertType = 20;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
			} else {// �н𴸵������
				if(inta_goldEggsState[hammerPostion]>0)return;
				inta_goldEggsState[hammerPostion]=1;
				C.receiveKeyPressed=false;
				EggBreaking=true;
				C.total_Score+=inta_valueJiangPing[hammerPostion];
				--C.goldHammer_Count;
			}
		}break;
		case C.KEY_LEFT: {
			if (hammerPostion == 0)return;
			--hammerPostion;
			s.repaint();
		}break;
		case C.KEY_RIGHT: {
			if (hammerPostion == 4)return;
			++hammerPostion;
			s.repaint();
		}break;
//		case C.KEY_1:{
//			Screen.beforeStatus=Screen.S_SMASH;
//			C.alertType = 20;
//			s.setGameStatus(Screen.S_ALERT);
//			s.repaint();
//		}break;
//		case C.KEY_2:{
//			Screen.beforeStatus=Screen.S_SMASH;
//			C.alertType = 21;
//			s.setGameStatus(Screen.S_ALERT);
//			s.repaint();
//		}break;
		case C.KEY_BACK_ZX:
		case C.KEY_0: 
		case C.KEY_BACK:{// �û�����0 ѡ�������Ϸ
			Screen.status=Screen.S_NULL;
			C.passed=false;
			removeClassSources();
			System.gc();
			if (s.si == null)s.si = new ServerIptv(s);
			s.si.doSendUserInformation(1);
			
//			s.gamechallenge.init();//������Դ
			
			Screen.status = Screen.S_NULL;
			
			switch (Screen.backScreen) {
			case Screen.S_GAME_LEVEL:{//�ؿ�
				Screen.status=Screen.S_GAME_LEVEL;
				s.repaint();
				s.serviceRepaints();
				
				Screen.status=Screen.S_NULL;
				C.passed = false;
				s.gamelevel.coutDownStart = false;
				
				C.alertType = 31;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				s.serviceRepaints();
			}break;
			case Screen.S_GAME_TIME:{//����ʱģʽ
				Screen.status=Screen.S_GAME_TIME;
				s.repaint();
				s.serviceRepaints();
				
				Screen.status=Screen.S_NULL;
				C.nowCountDown = s.gametime.countDownTime;
				
				C.alertType = 50;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				s.serviceRepaints();
			}break;
			case Screen.S_GAME_CHALLENGE:{//��սģʽ
				Screen.status=Screen.S_GAME_CHALLENGE;
				s.gamechallenge.challengeinit();
				s.gamechallenge.restartGame();
				s.repaint();
				s.serviceRepaints();
//				
				Screen.status=Screen.S_NULL;
				C.passed = false;
				C.alertType = 71;
				s.gamechallenge.coutDownStart = false;
				
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				s.serviceRepaints();
			}break;
			}
			
		}break;
		}
	
	}

}
