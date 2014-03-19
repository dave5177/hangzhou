package com.zhangniu.game;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class GameLevel extends Mouse{
	
	private Screen s;
	
	/**
	 * ��Ϸ�����󱻴��У���ͼ��
	 */
	private byte backgroundDiZheng;
	private byte backgroundDiZhengOffer=3;

	/**
	 * �û����µļ�ֵ
	 */
	private int pressed_Key;
	
	/**
	 * �Ƿ��м����
	 */
	private boolean isKeyPressed;
	
	/**
	 * Ŀ�����ͼƬ����
	 */
	private Image [] imagea_objective;
	
	/**
	 * ����ͼƬ����
	 */
	private Image [] imagea_score;
	
	/**
	 * �����е�����ͼƬ����
	 */
	public Image[] imagea_gamelevel;
	
	/**
	 * ����ͼƬ����
	 */
	public Image[] imagea_hummer;
	
	/**
	 * auto���ʱ��
	 */
	private Image[] imagea_timeLast;

	/**
	 * ����Ϸ���о���ˢ�µı���ͼƬ
	 */
	public Image[] imagea_gameBack;

	/**
	 * ��ʾ��׼ʮ�ּ�
	 */
	private Image[] imagea_objectX;
	
	// ����״̬
	private boolean hummerState;
	// ����ʱ״̬
	public boolean coutDownStart;
	public boolean autoSamsh;//�Զ��ҵ���ʹ��
	
	/**
	 * �Զ������ĵ���ʱ
	 */
	public int autoSamshTime;
	
	/**
	 * �Զ�������ͼƬ
	 */
	private Image[] imagea_autoSamsh;
	
	/**
	 * ��Ϸ����ʾ�Ƿ���ʱ��
	 * backToGame==1;���ص���Ϸ
	 * backToGame==2;���ز˵�����
	 */
	public boolean backToShop;// �����ص������̳�
	
	/**
	 * ��������ļ��
	 */
	public int[] inta_CreateTime = { 13, 12, 12, 12, 11,11,10,10,9,9 };
	
	/**
	 * һ���е�Ŀ���ֵ
	 */
	public int[] objectiveScore = { 120, 300, 550, 1000, 1700 ,2700 ,3800,4900,5900,6900};
	
	/**
	 * �����趨��ʱ��
	 */
	public int[] countDown = { 30, 30, 30, 36, 50 , 66, 80, 98, 118,140};
	
	/**
	 * �����ڿ���ͣ����ʱ��
	 */
	public static int [] stopaWhile = { 13, 10, 8, 6, 6 , 6 , 6, 4, 4,4};
	
	/**
	 * ��������ĸ��ָ���
	 */
	public static byte[][] mouseGaiLu={
			{80,85,90,95,100},
			{79,84,89,94,100},
			{79,84,89,95,100},
			{75,81,88,94,100},
			{69,75,84,92,100},
			
			{65,71,81,91,100},
			{60,67,78,89,100},
			{55,64,76,88,100},
			{40,53,70,75,100},
			{20,40,60,80,100},

	};
	
	/**
	 * �߳���ʱ��ͳ��
	 */
	private int time;
	
	public GameLevel(Screen screen){
		super(2,stopaWhile,mouseGaiLu);
		s = screen;
	}
	
	public void levelInit(){
		
		C.level_Score =0;
		time = 0;
		
		Image img_useronce=null;
		imagea_objective = new Image[10];
		img_useronce=C.GetImageSource("/game/objectivescrol.png");
		for(int a =0;a<10;a++){
			imagea_objective[a]=
				Image.createImage(img_useronce, a*32, 0, 32, 32, 0);
		}
		img_useronce=null;
		img_useronce=C.GetImageSource("/game/num.png");
		imagea_score = new Image[10];
		for(int a =0;a<10;a++){
			imagea_score[a]=
				Image.createImage(img_useronce, a*32, 0, 32, 32, 0);
		}
		img_useronce=null;
		System.gc();
		imagea_gameBack = new Image[5];
		imagea_gameBack[0] = C.GetImageSource("/game/gameBg.png");
		imagea_gameBack[1] = C.GetImageSource("/game/first.png");
		imagea_gameBack[2] = C.GetImageSource("/game/stateline.png");
		imagea_gameBack[3] = C.GetImageSource("/game/mubiao.png");
		
		if(imagea_gamelevel == null)imagea_gamelevel = new Image[2];
		imagea_gamelevel[0]=C.GetImageSource("/game/jindutiao1.png");
//		if(imagea_game==null)imagea_game = new Image[19];
//		C.readImageArray(imagea_game, "/game/g1");
//		imagea_game[17] = null;
//		imagea_game[17] = C.GetImageSource("/game/jindutiao1.png");
		

//		imagea_game[0] = null;
		if(imagea_timeLast==null)imagea_timeLast = new Image[10];
//		for (int a = 0; a < 10; a++) {
//			imagea_timeLast[a] = Image.createImage(imagea_game[1], a * 53, 0, 53, 53, 0);
//		}
//		imagea_game[1]=null;
//		System.gc();
		
		if(imagea_objectX==null)imagea_objectX = new Image[3];
		imagea_objectX[0]=C.GetImageSource("/game/smallObject.png");
		imagea_objectX[1]=C.GetImageSource("/game/midObject.png");
		imagea_objectX[2]=C.GetImageSource("/game/bigObject.png");
		
		if(imagea_hummer ==  null)imagea_hummer = new Image[6];
		imagea_hummer[0]= C.GetImageSource("/game/hsup.png");
		imagea_hummer[1]= C.GetImageSource("/game/hsdown.png");
		
		imagea_hummer[2]= C.GetImageSource("/game/hmup.png");
		imagea_hummer[3]= C.GetImageSource("/game/hmdown.png");
		
		imagea_hummer[4]= C.GetImageSource("/game/hbup.png");
		imagea_hummer[5]= C.GetImageSource("/game/hbdown.png");
		
	}
	
	public void removeAllSource() {
		for (int a = 0; a < 10; a++) {
			imagea_timeLast[a] = null;
		}
		// ������
		imagea_objectX=null;
		System.gc();
	}
	
	public void distoryAllImage() {
		imagea_gameBack = null;
		for(int a =0;a<10;a++){
			imagea_score[a]=null;
		}
		for(int a =0;a<5;a++){
			imagea_objective[a]=null;
		}
		System.gc();
	}
	
	/**
	 * �����йܵ�ͼƬ
	 */
	public void loadAutoSamshImage(){
		autoSamshTime =30;
		imagea_autoSamsh = new Image[11];
		Image once = C.GetImageSource("/game/tg.png");
		for (int a = 0; a < 10; a++) {
			imagea_autoSamsh[a]=Image.createImage(once, a*29, 0, 29, 38, 0);
		}
		imagea_autoSamsh[10] = C.GetImageSource("/game/tgk.png");
	}
	
	/**
	 * ɾ���йܵ�ͼƬ
	 */
	public void removeAutoSamshImage(){
		autoSamshTime = 0;
		if(imagea_autoSamsh == null)return;
		for (int a = 0; a < 11; a++) {
			imagea_autoSamsh[a]=null;
		}
		imagea_autoSamsh = null;
		System.gc();
	}
	
	public void showMe(Graphics canvas) {
		
		drawGameBackGround(canvas);//����Ϸ����
		drawObjectScore(canvas);//��Ŀ���������
		drawCurrentScore(canvas);//���û���ǰ�ܷ����͵�ǰ���÷�
		
		if(coutDownStart){
			
			if (autoSamsh) {// �˹�����
				C.DrawImage_XY_LEFTTOP(imagea_autoSamsh[10], 				245, 20, canvas);
				C.DrawNumber_XY_RIGHTTOP(imagea_autoSamsh, autoSamshTime, 	325, 40, 30, canvas);
			}
			
			// ���̽�����
			if (time % 16 == 0) {
				C.nowCountDown--;
			}
			
			// ������
			drawAllMouse(canvas);
			// ������ʱ���ִ��Ӻ����
			if (isKeyPressed) {
				if (pressed_Key != -1) {
					drawHammer(pressed_Key,canvas);
				}
			}
			drawProgress(canvas);//��������
		}
	}
	
	public void logi(){
		
		if (coutDownStart) {
			
			// ������ּ��
			if (time % inta_CreateTime[C.level] == 0) {
				//�����������
				createMouse();
			}

			// �رռ�ʱ��
			if (C.nowCountDown <= 0) {
				Screen.status=Screen.S_NULL;
				removeAutoSamshImage();
				autoSamsh=false;
				C.passed = false;
				coutDownStart = false;
				time = 0;
				
				System.gc();
				for (int i = 0; i < 9; i++) {
					intaa_mouseState[i][0] = 0;
				}
				
				C.alertType = 45;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				return;
			}

			// �������ѯ���û��Ƿ���ʱ��
			if (C.nowCountDown == 3&&!autoSamsh) {
				Screen.beforeStatus=Screen.S_GAME_LEVEL;
				Screen.status=Screen.S_NULL;
				coutDownStart = false;
				C.alertType = 41;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				C.nowCountDown--;
			}

			// ���سɹ�
			if (C.passed) {
				Screen.status=Screen.S_NULL;
				removeAutoSamshImage();
				autoSamsh = false;
				coutDownStart = false;
				
				
				C.alertType = 44;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				
			}

			if (autoSamsh) {// �˹�����
				if(time % 14 ==0){//�����һ��
					if(autoSamshTime<=0){
						autoSamsh=false;
						autoSamshTime=0;
						removeAutoSamshImage();
					}else{
						--autoSamshTime;
					}
				}
				for (int a = 0; a < 9; a++) {
					if (intaa_mouseState[a][0] == 1 && intaa_mouseState[a][3] < 2
							&& intaa_mouseState[a][1] == 1) {
						pressed_Key = a;
						AddSubScore(a);
						hummerState = true;
						isKeyPressed = true;
					}
				}
			}
			
			//����Ч��
			if(isKeyPressed){
				backgroundDiZheng =backgroundDiZhengOffer;
			}else{
				backgroundDiZheng=0;
			}

			if (C.level_Score >= objectiveScore[C.level]) {
				if (C.level < 10) {
					C.passed = true;
				}
			}

			time++;
		}
	
	}
	
	public void keyPressed(int keyCode){
		if(autoSamsh)return;
		int k = -1;
		switch (keyCode) {
		case C.KEY_FIRE: //����һ�����˵�
			if (C.level_Score != -1) {
				Screen.beforeStatus = Screen.S_GAME_LEVEL;
				Screen.status=Screen.S_NULL;
				coutDownStart = false;
				C.alertType = 42;
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
			}
			break;
		case C.KEY_BACK_ZX:
		case C.KEY_0:
		case C.KEY_BACK://�˳���Ϸ�����ص�������
			Screen.beforeStatus = Screen.S_GAME_LEVEL;
			Screen.status=Screen.S_NULL;
			coutDownStart = false;
			C.alertType = 43;
			s.setGameStatus(Screen.S_ALERT);
			s.repaint();
			break;
		case C.KEY_1:
		case C.KEY_2:
		case C.KEY_3:
		case C.KEY_4:
		case C.KEY_5:
		case C.KEY_6:
		case C.KEY_7:
		case C.KEY_8:
		case C.KEY_9:
			k= keyCode - 49;
			break;
		}
		if (k != -1) {
			pressed_Key = k;
			AddSubScore(k);
			isKeyPressed = true;
			hummerState = true;
		}
	}
	
	/**
	 * �Ӽ���
	 * @param mousePosition
	 */
	public void AddSubScore(int mousePosition) {
		if (intaa_mouseState[mousePosition][0] != 0) {
			if (intaa_mouseState[mousePosition][1] == 1) {
				intaa_mouseState[mousePosition][1] = 2;
				switch (intaa_mouseState[mousePosition][3]) {
				case 0:{
					C.total_Score+=10;
					C.level_Score+=10;
				}break;
				case 1:{
					C.total_Score+=20;
					C.level_Score+=20;
				}break;
				case 2:{
					if (C.total_Score >= 10)
						C.total_Score -= 10;
					if (C.level_Score >= 10)
						C.level_Score -= 10;
				}break;
				case 3:{
					if (C.total_Score >= 20)
						C.total_Score -= 20;
					if (C.level_Score >= 20)
						C.level_Score -= 20;
				}break;
				case 4:{
					if(C.nowCountDown>5){
						C.nowCountDown-=5;
					}else{
						C.nowCountDown=1;
					}
				}break;
				}
				intaa_mouseState[mousePosition][4] = 0;
			}
		} else {
		}
	}
	
	/**
	 * ���û��ܷ����͵�ǰ���÷�
	 */
	public void drawCurrentScore(Graphics canvas){
		
		C.DrawNumber_XY_RIGHTTOP(imagea_score, C.total_Score, 220, 32, 17, canvas);//����ǰ�û����ܷ�
		
		C.DrawNumber_XY_RIGHTTOP(imagea_score, C.level_Score, 540, 32, 17, canvas);//����ǰ�ص���Ϸ����
		
	}
	
	/**
	 * ����Ϸ����
	 * @param i
	 * @param c
	 */
	public void drawGameBackGround(Graphics canvas){
		canvas.setClip(0, 0, C.screenwidth, C.screenheight);
		C.DrawImage_XY_LEFTTOP(imagea_gameBack[1], 0, 0, canvas);// ���
		C.DrawImage_XY_LEFTTOP(imagea_gameBack[0], 0,135+backgroundDiZheng, canvas);// ��Ϸ���������
		C.DrawImage_XY_LEFTTOP(imagea_gameBack[2], 0,458, canvas);//��Ϸ��������������Ľ�����
	}
	
	/**
	 * �����򣬺���׼
	 * @param i
	 * @param c
	 */
	public void drawHammer(int i,Graphics c) {
		c.setClip(0, 0, 645, 525);
		switch (i) {
		case 0:
			c.drawImage(imagea_objectX[0], 125, 192, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[0], 160, 80, 0);
				hummerState = false;
			} else {
				c.setClip(135, 89, 125, 130);
				c.drawImage(imagea_hummer[1], 144, 90, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 1:
			c.drawImage(imagea_objectX[0], 274, 192, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[0], 310, 80, 0);
				hummerState = false;
			} else {
				c.setClip(275, 89, 125, 130);
				c.drawImage(imagea_hummer[1], 294, 90, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 2:
			c.drawImage(imagea_objectX[0], 425, 192, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[0], 450, 80, 0);
				hummerState = false;
			} else {
				c.setClip(420, 89, 125, 130);
				c.drawImage(imagea_hummer[1], 436, 90, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 3:
			c.drawImage(imagea_objectX[1], 87, 265, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[2], 120, 126, 0);
				hummerState = false;
			} else {
				c.setClip(80, 125, 160, 175);
				c.drawImage(imagea_hummer[3], 109, 150, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 4:
			c.drawImage(imagea_objectX[1], 267, 265, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[2], 299, 126, 0);
				hummerState = false;
			} else {
				c.setClip(260, 125, 160, 175);
				c.drawImage(imagea_hummer[3], 288, 150, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 5:
			c.drawImage(imagea_objectX[1], 452, 265, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[2], 485, 126, 0);
				hummerState = false;
			} else {
				c.setClip(445, 125, 160, 175);
				c.drawImage(imagea_hummer[3], 474, 150, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 6:
			c.drawImage(imagea_objectX[2], 58, 355, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[4], 103, 170, 0);
				hummerState = false;
			} else {
				c.setClip(30, 149, 200, 240);
				c.drawImage(imagea_hummer[5], 77, 220, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 7:
			c.drawImage(imagea_objectX[2], 266, 355, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[4], 309, 170, 0);
				hummerState = false;
			} else {
				c.setClip(240, 149, 200, 240);
				c.drawImage(imagea_hummer[5], 289, 220, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		case 8:
			c.drawImage(imagea_objectX[2], 472, 355, 0);
			if (hummerState) {
				c.drawImage(imagea_hummer[4], 522, 170, 0);
				hummerState = false;
			} else {
				c.setClip(460, 149, 200, 240);
				c.drawImage(imagea_hummer[5], 502, 220, 0);
				pressed_Key = -1;
				isKeyPressed = false;
			}
			break;
		}
		c.setClip(0, 0, C.screenwidth, C.screenheight);
	}

	/**
	 * ��������
	 * @param c
	 */
	public void drawProgress(Graphics c) {
		int height = 22;
		float width = 213 / (float) countDown[C.level];
		c.setClip(138, 493, (int) (width * C.nowCountDown), height);
		c.drawImage(imagea_gamelevel[0], 138, 493, 0);
	}
	
	/**
	 * ��Ŀ�����,����ǰ�ڵڼ���
	 * @param canvas
	 */
	public void drawObjectScore(Graphics canvas){
		C.DrawNumber_XY_RIGHTTOP(imagea_objective, objectiveScore[C.level], 325, 140, 15, canvas);
		C.DrawNumber_XY_RIGHTTOP(imagea_score, C.level+1, 430, 490, 15, canvas);
	}
	
	
}