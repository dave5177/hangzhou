package com.zhangniu.game;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class GameTime extends Mouse{
	
	private Screen s;
	
	/**
	 * 游戏中老鼠被打中，地图振动
	 */
	private byte backgroundDiZheng;
	private byte backgroundDiZhengOffer=3;

	/**
	 * 用户按下的键值
	 */
	private int pressed_Key;
	
	/**
	 * 是否有键铵下
	 */
	private boolean isKeyPressed;
	
	/**
	 * 目标分数图片数组
	 */
	private Image [] imagea_objective;
	
	/**
	 * 分数图片数组
	 */
	private Image [] imagea_score;
	
	/**
	 * 本类中的中央图片数组
	 */
	public Image[] imagea_gamelevel;
	
	/**
	 * 锤子图片数组
	 */
	public Image[] imagea_hummer;
	
	/**
	 * auto打的时候
	 */
	private Image[] imagea_timeLast;

	/**
	 * 在游戏区中经常刷新的背景图片
	 */
	public Image[] imagea_gameBack;

	/**
	 * 显示瞄准十字架
	 */
	private Image[] imagea_objectX;
	
	// 锤子状态
	private boolean hummerState;
	// 倒计时状态
	public boolean coutDownStart;
	public boolean autoSamsh;//自动砸道具使用
	
	/**
	 * 自动打地鼠的倒计时
	 */
	public int autoSamshTime;
	
	/**
	 * 自动打地鼠的图片
	 */
	private Image[] imagea_autoSamsh;
	
	/**
	 * 游戏中提示是否购买时间
	 * backToGame==1;返回到游戏
	 * backToGame==2;返回菜单界面
	 */
	public boolean backToShop;// 购买后回到道具商场
	
	/**
	 * 创建老鼠的间隔
	 */
	public int[] inta_CreateTime = { 13, 12, 12, 12, 11,11,10,10,9,9 };
	
	/**
	 * 一关中的目标分值
	 */
	public int[] objectiveScore = { 50, 100, 150, 200, 350 ,400 ,450,650,850,1100};
	
	/**
	 * 地鼠在空中停留的时间
	 */
	public static int [] stopaWhile = { 13, 10, 8, 7, 7 , 6 , 6, 6, 5,4};
	
	/**
	 * 地鼠产生的各种概率
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
	 * 各关设定的时间
	 */
	public final int countDownTime = 100;
	
	/**
	 * 线程内时间统计
	 */
	int time;
	
	public GameTime(Screen screen){
		super(2,stopaWhile,mouseGaiLu);
		s = screen;
	}
	
	public void levelInit(){
		
		C.time_Score =0;
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
		imagea_gameBack[0] = C.GetImageSource("/game/gameBgtime.png");
		imagea_gameBack[1] = C.GetImageSource("/game/firsttime.png");
		imagea_gameBack[2] = C.GetImageSource("/game/statelinetime.png");
		imagea_gameBack[3] = C.GetImageSource("/game/mubiao.png");
		
		if(imagea_gamelevel == null)imagea_gamelevel = new Image[2];
		imagea_gamelevel[0]=C.GetImageSource("/game/jindutiao2.png");
		if(imagea_timeLast==null)imagea_timeLast = new Image[10];
		
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
		// 老鼠类
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
	 * 导入托管的图片
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
	 * 删除托管的图片
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
		
		drawGameBackGround(canvas);//画游戏背景
		drawCurrentScore(canvas);//画用户当前总分数和当前所得分
		
		if(coutDownStart){
			
			if (autoSamsh) {// 人工智能
				C.DrawImage_XY_LEFTTOP(imagea_autoSamsh[10], 				245, 20, canvas);
				C.DrawNumber_XY_RIGHTTOP(imagea_autoSamsh, autoSamshTime, 	325, 40, 30, canvas);
			}
			
			// 缩短进度条
			if (time % 16 == 0) {
				C.nowCountDown--;
			}
			
			// 画老鼠
			drawAllMouse(canvas);
			// 打老鼠时出现锤子和瞄把
			if (isKeyPressed) {
				if (pressed_Key != -1) {
					drawHammer(pressed_Key,canvas);
				}
			}
		}
		drawProgress(canvas);//画进度条
	}
	
	public void logi(){
		
		if (coutDownStart) {
			
			// 老鼠出现间隔
			if (time % inta_CreateTime[C.level] == 0) {
				//随便生成老鼠
				createMouse();
			}

			// 关闭计时器
			if (C.nowCountDown <= 0) {
				Screen.backScreen = Screen.status;
				Screen.status=Screen.S_NULL;
				coutDownStart = false;
				autoSamsh=false;
				C.passed = false;
				System.gc();
				time = 0;
				for (int i = 0; i < 9; i++) {
					intaa_mouseState[i][0] = 0;
				}
				Screen.beforeStatus = Screen.S_GAME_TIME;
				
				Screen.status=Screen.S_NULL;
				removeAllSources();
				s.nx_alert.show();
				
//				C.alertType = 54;
//				s.setGameStatus(Screen.S_ALERT);
//				s.repaint();
				return;
			}

			// 最后五秒询问用户是否购买时间
			if (C.nowCountDown == 5&&!autoSamsh) {
				Screen.beforeStatus=Screen.S_GAME_TIME;
				Screen.status=Screen.S_NULL;
				coutDownStart = false;
				C.alertType = 51;//倒计时模式下购买时间
				s.setGameStatus(Screen.S_ALERT);
				s.repaint();
				C.nowCountDown--;
			}

			if (autoSamsh) {// 人工智能
				if(time % 14 ==0){//大概是一秒
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
			
			if (C.time_Score >= objectiveScore[C.level]) {
				if (C.level < 10)++C.level;
			}
			
			//地震效果
			if(isKeyPressed){
				backgroundDiZheng =backgroundDiZhengOffer;
			}else{
				backgroundDiZheng=0;
			}

			time++;
		}
	
	}
	
	public void keyPressed(int keyCode){
		if(autoSamsh)return;
		int k = -1;
		switch (keyCode) {
		case C.KEY_FIRE: //出来一个主菜单
			if (C.time_Score != -1) {
				Screen.beforeStatus = Screen.S_GAME_TIME;
				Screen.status=Screen.S_NULL;
				removeAllSources();
				s.nx_alert.show();
				
//				coutDownStart = false;
//				C.alertType = 52;
//				s.setGameStatus(Screen.S_ALERT);
//				s.repaint();
			}
			break;
		case C.KEY_BACK_ZX:
		case C.KEY_0:
		case C.KEY_BACK://退出游戏，返回到主界面
			Screen.beforeStatus = Screen.S_GAME_TIME;
			Screen.status=Screen.S_NULL;
			coutDownStart = false;
			C.alertType = 53;
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
	 * 加减分
	 * @param mousePosition
	 */
	public void AddSubScore(int mousePosition) {
		if (intaa_mouseState[mousePosition][0] != 0) {
			if (intaa_mouseState[mousePosition][1] == 1) {
				intaa_mouseState[mousePosition][1] = 2;
				switch (intaa_mouseState[mousePosition][3]) {
				case 0:{
					C.total_Score+=10;
					C.time_Score+=10;
				}break;
				case 1:{
					C.total_Score+=20;
					C.time_Score+=20;
				}break;
				case 2:{
					if (C.total_Score >= 10)
						C.total_Score -= 10;
					if (C.time_Score >= 10)
						C.time_Score -= 10;
				}break;
				case 3:{
					if (C.total_Score >= 20)
						C.total_Score -= 20;
					if (C.time_Score >= 20)
						C.time_Score -= 20;
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
	 * 画用户总分数和当前所得分
	 */
	public void drawCurrentScore(Graphics canvas){
		
		C.DrawNumber_XY_RIGHTTOP(imagea_score, C.total_Score, 210, 32, 17, canvas);//画当前用户的总分
		
		C.DrawNumber_XY_RIGHTTOP(imagea_score, C.time_Score, 540, 32, 17, canvas);//画当前关的游戏分数
		
	}
	
	/**
	 * 画游戏背景
	 * @param i
	 * @param c
	 */
	public void drawGameBackGround(Graphics canvas){
		canvas.setClip(0, 0, C.screenwidth, C.screenheight);
		C.DrawImage_XY_LEFTTOP(imagea_gameBack[1], 0, 0, canvas);// 天空
		C.DrawImage_XY_LEFTTOP(imagea_gameBack[0], 0,57+backgroundDiZheng, canvas);// 游戏打地鼠区域
		C.DrawImage_XY_LEFTTOP(imagea_gameBack[2], 0,448, canvas);//游戏第三区域，最下面的进度条
	}
	
	/**
	 * 画击打，和瞄准
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
	 * 画进度条
	 * @param c
	 */
	public void drawProgress(Graphics c) {
		int height = 22;
		float width = 325 / (float) countDownTime;
		c.setClip(190, 493, (int) (width * C.nowCountDown), height);
		c.drawImage(imagea_gamelevel[0], 190, 493, 0);
	}
	
}