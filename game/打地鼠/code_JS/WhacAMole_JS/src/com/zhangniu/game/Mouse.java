package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Mouse{
	
	/**
	 * 头顶上的分值
	 */
	public int addScoreType;
	
	/**
	 * 生命
	 */
	public byte live;
	
	/**
	 * 错失
	 */
	public byte lost;
	
	/**
	 * 地鼠前面的(zheyan)遮掩物
	 */
	public Image[] imagea_MouseZheYan;
	
	/**
	 * 打中地鼠后的加加减减图片数组
	 */
	public Image[] imagea_score_addORsubtract;
	
	/**
	 * 地鼠状态[9][7]
	 * [][0]地鼠是否生成  	(1没有被生成)(2已经被生成)
	 * [][1]地鼠是否被打		(1自然出来)(2被打)(3打晕)
	 * [][2]地鼠是否出现		(1上升)(2下降)
	 * [][3]哪种地鼠			(0,1加10分)(2减10分)(3减20分)(4减5秒)
	 * [][4]空中停留			(1在空中停留)
	 * [][5]具体哪张地鼠图的索引
	 * [][6]这个是老鼠的晕的眼睛动画的索引
	 */
	public int[][] intaa_mouseState;
	
	/**
	 * 地鼠的坐标位置x
	 */
	public int[] mouseX = {
			163, 312, 462, 
			132, 314, 495, 
			108, 316, 525 };
	
	/**
	 * 地鼠的坐标位置y
	 */
	public int[] mouseY = { 
			254, 254, 254, 
			340, 340, 340, 
			440, 440, 440 };
	
	/**
	 * 到达一个顶值就开始下降
	 * 三种老鼠到达顶值不一样
	 */
	public int[] mouseYTop = { 179, 270, 345 };
	
	/**
	 * 游戏中老鼠的上升偏移量
	 */
	public int mouseY_Offer = 25;
	
	/**
	 * 地鼠遮掩坐标
	 */
	public short [] inta_zheyan = {
			115, 88, 125, 130,
			265, 88, 125, 124,
			410, 88, 145, 124,
			
			80, 	123, 160, 169,
			260, 	123, 160, 169,
			445, 	123, 160, 169,
			
			30, 	153, 200, 238,
			240, 	153, 200, 238,
			460, 	153, 200, 238
	};
	
	/**
	 * 游戏中老鼠的中间停留时间
	 */
	public int[] inta_mouse_stopaWhile;
	
	/**
	 * 地鼠产生的各种概率
	 */
	public byte[][] byteaa_mouseGaiLu
//	=
//	{
//			{80,85,90,95,100},
//			{79,84,89,94,100},
//			{79,84,89,95,100},
//			{75,81,88,94,100},
//			{69,75,84,92,100},
//			
//			{65,71,81,91,100},
//			{60,67,78,89,100},
//			{55,64,76,88,100},
//			{40,53,70,75,100},
//			{20,40,60,80,100},
//
//	}
	;
	
	/**
	 * 地鼠图片数组
	 * [][0]鼠身
	 * [][1]平时的眼睛
	 * [][2]平时的嘴巴
	 * [][3]被打时的眼睛
	 * [][4]被打时的嘴巴
	 * [][5]晕的眼睛
	 * [][6]晕的眼睛
	 * [][7]晕的眼睛
	 */
	public Image [][] imageaa_MouseImage = new Image[9][8];
	
	/**
	 * 按坐标来分割
	 * 先是鼠身
	 */
	public short [][] intaa_MouseCut ={
			{
				0,0,80,94,//身体
				78,16,42,7,//平时的眼睛
				80,23,22,22,//平时的嘴巴
				80,0,40,16,//被打的眼睛
				102,23,9,18,//被打的嘴巴
				80,45,40,11,//打晕的眼睛
				80,57,40,11,//这两个都是被打的眼睛
				80,70,40,11},//+10的老鼠 	min
			{
				0,0,96,113,
				94,19,50,9,
				96,28,27,27,
				96,0,48,19,
				123,28,11,21,
				96,55,48,12,
				96,69,48,12,
				96,84,48,13
					},//+10的老鼠	mid
			{
				0,0,119,139,
				119,22,50,12,//平时的眼睛
				121,34,31,33,//平时的嘴巴
				119,0,61,22,//被打的眼睛
				152,34,15,26,//被打的嘴巴
				119,67,52,15,
				119,84,52,16,
				119,103,52,17,
			},//+10的老鼠	max
			
			{
				0,0,71,82,//身体
				83,16,30,10,//平时的眼睛
				75,2,47,14,//平时的嘴巴
				80,27,34,13,//被打的眼睛
				75,2,47,14,//被打的嘴巴
				82,43,37,13,//被打晕
				82,56,37,13,
				82,70,37,13
			},//+20的老鼠 	min
			{
				0,0,85,99,//身体
				99,19,35,12,//平时的眼睛
				88,0,56,19,//平时的嘴巴
				94,32,42,17,//被打的眼睛
				88,0,56,19,//被打的嘴巴
				96,52,46,16,//被打晕
				96,69,46,16,
				96,86,45,16
			},//+20的老鼠	mid
			{
				0,0,98,117,//身体
				111,23,41,14,//平时的眼睛
				98,0,69,22,//平时的嘴巴
				106,38,51,19,//被打的眼睛
				98,0,69,22,//被打的嘴巴
				109,62,54,19,//被打晕
				109,81,54,18,
				109,102,54,18
				
			},//+20的老鼠	max
			
			{
				0,0,76,113,
				70,0,64,10,
				76,26,58,23,
				76,10,53,16,
				94,49,18,33,
				76,83,48,15,
				76,98,48,14,
				76,112,48,15},//-10的老鼠 	min
			{
				0,0,92,135,
				92,0,58,13,
				92,32,66,28,
				92,13,66,19,
				112,60,22,38,
				92,99,62,18,
				92,117,62,17,
				93,134,62,17},//-10的老鼠	mid
			{//从0开始第8
				0,0,104,160,//身体
				105,0,68,12,//平时的眼睛
				104,34,77,31,//平时的嘴巴
				106,12,70,22,//被打的眼睛
				127,65,26,44,//被打的嘴巴
				104,109,64,21,
				104,130,63,19,
				104,149,63,19,},//-10的老鼠	max
			{
				0,0,80,113,
				80,4,50,11,
				80,15,50,12,
				80,27,50,15,
				80,42,50,17,
				80,60,42,11,
				80,71,42,11,
				80,82,42,12
				},//-20的老鼠 	min
			{
				0,0,101,141,//身体
				101,5,60,13,//平时
				122,18,19,15,
				101,33,64,19,//被打
				119,52,28,20,
				101,76,51,14,
				101,90,51,13,
				101,103,51,15,
			},//-20的老鼠	mid
			{
				0,0,114,160,
				114,7,69,15,
				114,23,69,15,
				114,38,69,32,
				114,60,69,24,
				114,91,59,16,
				114,107,59,16,
				114,123,59,17
			},//-20的老鼠	max
			
			{//从0开始第12
				0,0,71,88,//身体
				71,0,60,8,//平时眼睛
				76,21,48,9,//平时嘴巴
				71,8,57,13,//被打眼睛
				74,32,53,15,//被打嘴巴
				71,49,59,16,
				71,65,59,16,
				71,82,59,16
			},//-5秒的老鼠 	min
			{//13
				0,0,87,108,//身体
				87,0,72,11,//平时的眼睛
				93,26,59,12,//平时的嘴巴
				87,11,72,15,//被打眼睛
				91,40,64,18,//被打嘴巴
				87,60,72,21,
				87,81,72,19,
				87,101,72,20
			},//-5秒的老鼠	mid
			{
				0,0,100,125,
				100,0,85,12,
				100,30,85,15,
				100,12,85,18,
				100,45,85,23,
				100,71,85,23,
				100,94,85,24,
				100,118,85,22
			},//-5秒的老鼠	max
	};
	
	/**
	 * 部件坐标与动画索引
	 * 先画嘴巴再画眼睛
	 * 0是眼睛
	 * 1是嘴巴
	 */
	public short [][] shortaa_MouseFlashSpirtx ={
			{11,0},//+10的老鼠 	min
			{18,0},//+10的老鼠 	mid
			{22,0},//+10的老鼠 	max
			
			{11,0},//+20的老鼠 	min
			{18,0},//+20的老鼠 	mid
			{22,0},//+20的老鼠 	max
			
			{11,0},//-10的老鼠 	min
			{18,0},//-10的老鼠 	mid
			{22,0},//-10的老鼠 	max
			
			{11,0},//-20的老鼠 	min
			{18,0},//-20的老鼠 	mid
			{22,0},//-20的老鼠 	max
			
			{11,0},//-5秒的老鼠 	min
			{18,0},//-5秒的老鼠 	mid
			{22,0},//-5秒的老鼠 	max
	};
	
	public Mouse(int addscore ,int [] stop , byte [][] gailu){
		byteaa_mouseGaiLu = gailu;
		inta_mouse_stopaWhile = stop;
		addScoreType = addscore;
		mouseinit();
	}
	  
	public void mouseinit(){
		Image once = null;//仅使用一次
//		
		// 老鼠状态
		intaa_mouseState = new int[9][7];
//		
		// 游戏界面
		if(addScoreType == 1){
			once = C.GetImageSource("/mouse/addscore.png");
			if(imagea_score_addORsubtract==null)imagea_score_addORsubtract = new Image[5];// 分数图片数组
			for (int a = 0; a < 5; a++) {
				imagea_score_addORsubtract[a] = Image.createImage(once, 0, a*35, 90, 35, 0);
			}
		}else{
			once = C.GetImageSource("/mouse/addscore1.png");
			if(imagea_score_addORsubtract==null)imagea_score_addORsubtract = new Image[5];// 分数图片数组
			for (int a = 0; a < 5; a++) {
				imagea_score_addORsubtract[a] = Image.createImage(once, 0, a*35, 66, 35, 0);
			}
		}
		
		//遮掩物上的数字
		imagea_MouseZheYan = new Image[3];
		//地鼠出来动画的遮掩物
		imagea_MouseZheYan[0]= C.GetImageSource("/mouse/zheyansmall.png");
		imagea_MouseZheYan[1]= C.GetImageSource("/mouse/zheyanmiddle.png");
		imagea_MouseZheYan[2]= C.GetImageSource("/mouse/zheyanbig.png");
	}

	/**
	 * 清空所有地鼠类中的资源
	 */
	public void removeAllSources(){
		for (int b = 0; b < 9; b++) {
			for(byte a =0;a<8;a++){
				imageaa_MouseImage[b][a] = null;
			}
		}

		imageaa_MouseImage=null;
		
		for (int a = 0; a < 5; a++) {
			imagea_score_addORsubtract[a] = null;
		}
		imagea_score_addORsubtract= null;
		
		imagea_MouseZheYan[0]=null;
		imagea_MouseZheYan[1]=null;
		imagea_MouseZheYan[2]=null;
		imagea_MouseZheYan = null;
		
		System.gc();
	}
	
	/**
	 * 将各个位置的一组图片清空
	 */
	public void removeSpecifiedMouseImagea(byte index){
		for(byte a =0;a<8;a++){
			imageaa_MouseImage[index][a] = null;
		}
		System.gc();
	}
	
	/**
	 * 加载相应的老鼠对象(类型,索引1,2,3,4,5....)
	 * @param mousetype
	 * @param mouseposition
	 * @param intaa_mouseState
	 * @param mouseNum
	 */
	public void loadMouseImage(byte mouseposition,byte which) {
		if(imageaa_MouseImage[mouseposition][0]!=null)//如果原来的资源还没有清空掉就清空下
			removeSpecifiedMouseImagea(mouseposition);
		Image once = null;
		once = C.GetImageSource("/mouse/"+which+".png");//读取指定的图片
		try {
			for(byte a =0;a<8;a++){
				imageaa_MouseImage[mouseposition][a]=
					Image.createImage(once, 
							intaa_MouseCut[which][a*4+0], 
							intaa_MouseCut[which][a*4+1], 
							intaa_MouseCut[which][a*4+2], 
							intaa_MouseCut[which][a*4+3], 
							0);
			}
		} catch (Exception e) {
			C.out("shit"+which);
			e.printStackTrace();
		}
		once = null;
		System.gc();
	}
	
	/**
	 * 随便生成老鼠
	 * 1：先是确定老鼠的类型
	 * 2: 然后再确定老婆的位置
	 */
	public void createMouse() {
		int temp = 0;
		while (true) {
			temp++;
			// mouseNum.老鼠号(
			// 1.2.3.坏老鼠,打一个加十分
			// 4.打一个减十分
			// 5.打一个减二十分)
			int mouseType = C.random.nextInt(100);
			
			if (mouseType < byteaa_mouseGaiLu[C.level][0]) {
				mouseType = 1;
			} else if (mouseType <  byteaa_mouseGaiLu[C.level][1]) {
				mouseType = 2;
			} else if (mouseType <  byteaa_mouseGaiLu[C.level][2]) {
				mouseType = 3;
			} else if (mouseType <  byteaa_mouseGaiLu[C.level][3]) {
				mouseType = 4;
			} else if (mouseType <  byteaa_mouseGaiLu[C.level][4]) {
				mouseType = 5;
			}

			byte mousePosition = (byte)C.random.nextInt(9);
			
			if (intaa_mouseState[mousePosition][0] == 0) {

				intaa_mouseState[mousePosition][0] = 1; // 老鼠是否存在
				intaa_mouseState[mousePosition][1] = 1; // 老鼠完好还是被打 (1.完好) (2.被打)(3.打晕)
				intaa_mouseState[mousePosition][2] = 1; // 老鼠是出现还是回去 1.出现 2.回去
				intaa_mouseState[mousePosition][3] = mouseType-1; // 哪种老鼠
													//1:+10
													//2:+20
													//3:-10
													//4:-20
													//5:-5秒
				intaa_mouseState[mousePosition][4] = 0; // 空中停留
				intaa_mouseState[mousePosition][5] = ((mouseType-1)*3)+(mousePosition/3); //15种老鼠里,具体是哪只,用于动画帧数据使用
				loadMouseImage(mousePosition,(byte)intaa_mouseState[mousePosition][5]);//导入该位置的图片
				break;
			}

			if (temp > 10) {
				break;
			}
		}
	}
	
	/**
	 * 是否是丢失
	 */
	public void isLost(int index){
		if(intaa_mouseState[index][1]==1&&(intaa_mouseState[index][3]==0||intaa_mouseState[index][3]==1)){
			++lost;
			if(lost==3){
				if(live>0)--live;
				lost-=3;
			}
		}
	}
	
	/**
	 * 根据状态画所有地鼠
	 * @param canvas
	 */
	public void drawAllMouse(Graphics canvas) {
		canvas.setColor(0x0000ff);
		/***********第1个位置*/
		if (intaa_mouseState[0][0] != 0) {
			canvas.setClip(inta_zheyan[0], inta_zheyan[1], inta_zheyan[2], inta_zheyan[3]);
			if (intaa_mouseState[0][2] == 1) {//出现
				// 老鼠出现
				if (mouseY[0] <= mouseYTop[0]) {
					intaa_mouseState[0][2] = 2;
				} else {
					mouseY[0] -= mouseY_Offer;
				}
				drawMouse(mouseX[0], mouseY[0], 0,intaa_mouseState[0][5],intaa_mouseState[0][1], canvas);
			} else {					//返回
				drawMouse(mouseX[0], mouseY[0], 0,intaa_mouseState[0][5],intaa_mouseState[0][1], canvas);
				intaa_mouseState[0][4]++;
				if (intaa_mouseState[0][4] >= inta_mouse_stopaWhile[C.level]) {
					mouseY[0] = 254;
					intaa_mouseState[0][4] = 0;
					intaa_mouseState[0][0] = 0;
					removeSpecifiedMouseImagea((byte)0);
					isLost(0);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[0][1],intaa_mouseState[0][3],0,canvas);// 打中地鼠后的画分数加减
			C.DrawImage_VH(imagea_MouseZheYan[0], 168, 240, canvas);//遮掩物
		}
		/***********第2个位置*/
		if (intaa_mouseState[1][0] != 0) {
			canvas.setClip(inta_zheyan[1*4+0], inta_zheyan[1*4+1], inta_zheyan[1*4+2], inta_zheyan[1*4+3]);
			if (intaa_mouseState[1][2] == 1) {
				// 老鼠出现
				if (mouseY[1] <= mouseYTop[0]) {
					intaa_mouseState[1][2] = 2;
				} else {
					mouseY[1] -= mouseY_Offer;
				}
				drawMouse(mouseX[1], mouseY[1], 1,intaa_mouseState[1][5],intaa_mouseState[1][1], canvas);
			} else {
				drawMouse(mouseX[1], mouseY[1], 1,intaa_mouseState[1][5],intaa_mouseState[1][1], canvas);
				intaa_mouseState[1][4]++;
				if (intaa_mouseState[1][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[1] = 254;
					intaa_mouseState[1][4] = 0;
					intaa_mouseState[1][0] = 0;
					removeSpecifiedMouseImagea((byte)1);
					isLost(1);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[1][1],intaa_mouseState[1][3],1,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[0], 318, 240, canvas);//遮掩物
		}
		/***********第3个位置*/
		if (intaa_mouseState[2][0] != 0) {
			canvas.setClip(inta_zheyan[2*4+0], inta_zheyan[2*4+1], inta_zheyan[2*4+2], inta_zheyan[2*4+3]);
			if (intaa_mouseState[2][2] == 1) {
				// 老鼠出现
				if (mouseY[2] <= mouseYTop[0]) {
					intaa_mouseState[2][2] = 2;
				} else {
					mouseY[2] -= mouseY_Offer;
				}
				drawMouse(mouseX[2], mouseY[2], 2,intaa_mouseState[2][5],intaa_mouseState[2][1], canvas);
			} else {
				drawMouse(mouseX[2], mouseY[2], 2,intaa_mouseState[2][5],intaa_mouseState[2][1], canvas);
				intaa_mouseState[2][4]++;
				if (intaa_mouseState[2][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[2] = 254;
					intaa_mouseState[2][4] = 0;
					intaa_mouseState[2][0] = 0;
					removeSpecifiedMouseImagea((byte)2);
					isLost(2);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[2][1],intaa_mouseState[2][3],2,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[0], 469, 240, canvas);//遮掩物
		}
		if (intaa_mouseState[3][0] != 0) {
			canvas.setClip(inta_zheyan[3*4+0], inta_zheyan[3*4+1], inta_zheyan[3*4+2], inta_zheyan[3*4+3]);
			if (intaa_mouseState[3][2] == 1) {
				// 老鼠出现
				if (mouseY[3] <= mouseYTop[1]) {
					intaa_mouseState[3][2] = 2;
				} else {
					mouseY[3] -= (mouseY_Offer + 3);
				}
				drawMouse(mouseX[3], mouseY[3], 3,intaa_mouseState[3][5],intaa_mouseState[3][1], canvas);
			} else {
				drawMouse(mouseX[3], mouseY[3], 3,intaa_mouseState[3][5],intaa_mouseState[3][1], canvas);
				intaa_mouseState[3][4]++;
				if (intaa_mouseState[3][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[3] = 359;
					intaa_mouseState[3][0] = 0;
					intaa_mouseState[3][4] = 0;
					removeSpecifiedMouseImagea((byte)3);
					isLost(3);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[3][1],intaa_mouseState[3][3],3,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[1], 137, 316, canvas);//遮掩物
		}
		if (intaa_mouseState[4][0] != 0) {
			canvas.setClip(inta_zheyan[4*4+0], inta_zheyan[4*4+1], inta_zheyan[4*4+2], inta_zheyan[4*4+3]);
			if (intaa_mouseState[4][2] == 1) {
				// 老鼠出现
				if (mouseY[4] <= mouseYTop[1]) {
					intaa_mouseState[4][2] = 2;
				} else {
					mouseY[4] -= (mouseY_Offer + 3);
				}
				drawMouse(mouseX[4], mouseY[4], 4,intaa_mouseState[4][5],intaa_mouseState[4][1], canvas);
			} else {
				drawMouse(mouseX[4], mouseY[4], 4,intaa_mouseState[4][5],intaa_mouseState[4][1], canvas);
				intaa_mouseState[4][4]++;
				if (intaa_mouseState[4][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[4] = 359;
					intaa_mouseState[4][0] = 0;
					intaa_mouseState[4][4] = 0;
					removeSpecifiedMouseImagea((byte)4);
					isLost(4);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[4][1],intaa_mouseState[4][3],4,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[1], 319, 316, canvas);//遮掩物
		}
		if (intaa_mouseState[5][0] != 0) {
			canvas.setClip(inta_zheyan[5*4+0], inta_zheyan[5*4+1], inta_zheyan[5*4+2], inta_zheyan[5*4+3]);
			if (intaa_mouseState[5][2] == 1) {
				// 老鼠出现
				if (mouseY[5] <= mouseYTop[1]) {
					intaa_mouseState[5][2] = 2;
				} else {
					mouseY[5] -= (mouseY_Offer + 3);
				}
				drawMouse(mouseX[5], mouseY[5], 5,intaa_mouseState[5][5],intaa_mouseState[5][1], canvas);
			} else {
				drawMouse(mouseX[5], mouseY[5], 5,intaa_mouseState[5][5],intaa_mouseState[5][1], canvas);
				intaa_mouseState[5][4]++;
				if (intaa_mouseState[5][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[5] = 359;
					intaa_mouseState[5][0] = 0;
					intaa_mouseState[5][4] = 0;
					removeSpecifiedMouseImagea((byte)5);
					isLost(5);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[5][1],intaa_mouseState[5][3],5,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[1], 502, 316, canvas);//遮掩物
		}
		if (intaa_mouseState[6][0] != 0) {
			canvas.setClip(inta_zheyan[6*4+0], inta_zheyan[6*4+1], inta_zheyan[6*4+2], inta_zheyan[6*4+3]);
			if (intaa_mouseState[6][2] == 1) {
				// 老鼠出现
				if (mouseY[6] <= mouseYTop[2]) {
					intaa_mouseState[6][2] = 2;
				} else {
					mouseY[6] -= (mouseY_Offer + 8);
				}
				drawMouse(mouseX[6], mouseY[6], 6,intaa_mouseState[6][5],intaa_mouseState[6][1], canvas);
			} else {
				drawMouse(mouseX[6], mouseY[6], 6,intaa_mouseState[6][5],intaa_mouseState[6][1], canvas);
				intaa_mouseState[6][4]++;
				if (intaa_mouseState[6][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[6] = 454;
					intaa_mouseState[6][0] = 0;
					intaa_mouseState[6][4] = 0;
					removeSpecifiedMouseImagea((byte)6);
					isLost(6);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[6][1],intaa_mouseState[6][3],6,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[2], 113, 409, canvas);//遮掩物
		}
		if (intaa_mouseState[7][0] != 0) {
			canvas.setClip(inta_zheyan[7*4+0], inta_zheyan[7*4+1], inta_zheyan[7*4+2], inta_zheyan[7*4+3]);
			if (intaa_mouseState[7][2] == 1) {
				// 老鼠出现

				if (mouseY[7] <= mouseYTop[2]) {
					intaa_mouseState[7][2] = 2;
				} else {
					mouseY[7] -= (mouseY_Offer + 8);
				}
				drawMouse(mouseX[7], mouseY[7], 7,intaa_mouseState[7][5],intaa_mouseState[7][1], canvas);
			} else {
				drawMouse(mouseX[7], mouseY[7], 7,intaa_mouseState[7][5],intaa_mouseState[7][1], canvas);
				intaa_mouseState[7][4]++;
				if (intaa_mouseState[7][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[7] = 454;
					intaa_mouseState[7][0] = 0;
					intaa_mouseState[7][4] = 0;
					removeSpecifiedMouseImagea((byte)7);
					isLost(7);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[7][1],intaa_mouseState[7][3],7,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[2], 321, 409, canvas);//遮掩物
		}
		if (intaa_mouseState[8][0] != 0) {
			canvas.setClip(inta_zheyan[8*4+0], inta_zheyan[8*4+1], inta_zheyan[8*4+2], inta_zheyan[8*4+3]);
			if (intaa_mouseState[8][2] == 1) {
				// 老鼠出现
				if (mouseY[8] <= mouseYTop[2]) {
					intaa_mouseState[8][2] = 2;
				} else {
					mouseY[8] -= (mouseY_Offer + 8);
				}
				drawMouse(mouseX[8], mouseY[8], 8,intaa_mouseState[8][5],intaa_mouseState[8][1], canvas);
			} else {
				drawMouse(mouseX[8], mouseY[8], 8,intaa_mouseState[8][5],intaa_mouseState[8][1], canvas);
				intaa_mouseState[8][4]++;
				if (intaa_mouseState[8][4] >= inta_mouse_stopaWhile[C.level]) {
					// 老鼠回去
					mouseY[8] = 454;
					intaa_mouseState[8][0] = 0;
					intaa_mouseState[8][4] = 0;
					removeSpecifiedMouseImagea((byte)8);
					isLost(8);//判断是否丢失
				}
			}
			drawScrolAdd(intaa_mouseState[8][1],intaa_mouseState[8][3],8,canvas);// 打中地鼠后的分数加减
			C.DrawImage_VH(imagea_MouseZheYan[2], 529, 409, canvas);//遮掩物
		}
	}
	
	/**
	 * 画打中地鼠的分数的加减
	 * @param mouseType			地鼠的类型0,1,2,3,4
	 * @param mousePosition		地鼠的坐标0~8
	 * @param canvas
	 */
	public void drawScrolAdd(int moustStatus,int mouseType,int mousePosition,Graphics canvas) {
//		C.out("新的方法画打中地鼠后的分数"+"地鼠的类别:"+mouseType+"地鼠的坐标:"+mousePosition);
		if(moustStatus<2)return;
		C.DrawImage_VH(imagea_score_addORsubtract[mouseType],mouseX[mousePosition],mouseY[mousePosition]-40, canvas);		
	}
	
	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mousePosition(1~9地鼠的坐标,0,1,2,3,4.....)
	 * @param whichmouse(哪只老鼠的索引,0~14)
	 * @param mouseState
	 * @param canvas
	 */
	public void drawMouse(int mouseX,int mouseY,int mousePosition,int whichmouse,int mouseState,Graphics canvas){
		C.DrawImage_VH(imageaa_MouseImage[mousePosition][0], mouseX, mouseY, canvas);//身体
		switch (mouseState) {
		case 1:{//自然出来
			//平时的嘴巴
			C.DrawImage_VH(imageaa_MouseImage[mousePosition][2], mouseX, mouseY-shortaa_MouseFlashSpirtx[whichmouse][1], canvas);
			//平时的眼睛
			C.DrawImage_VH(imageaa_MouseImage[mousePosition][1], mouseX, mouseY-shortaa_MouseFlashSpirtx[whichmouse][0], canvas);
		}break;
		case 2:{//被打了一下
			//被打的嘴巴
			C.DrawImage_VH(imageaa_MouseImage[mousePosition][4], mouseX, mouseY-shortaa_MouseFlashSpirtx[whichmouse][1], canvas);
			//被打的眼睛
			C.DrawImage_VH(imageaa_MouseImage[mousePosition][3], mouseX, mouseY-shortaa_MouseFlashSpirtx[whichmouse][0], canvas);
			intaa_mouseState[mousePosition][1]=5;
		}break;
		case 6:
		case 7:
		case 8:
		case 5:{//晕掉了
			C.DrawImage_VH(imageaa_MouseImage[mousePosition][intaa_mouseState[mousePosition][1]],mouseX, mouseY-shortaa_MouseFlashSpirtx[whichmouse][0], canvas);
			
			C.DrawImage_VH(imageaa_MouseImage[mousePosition][4], mouseX, mouseY-shortaa_MouseFlashSpirtx[whichmouse][1], canvas);
			++intaa_mouseState[mousePosition][1];
			if(intaa_mouseState[mousePosition][1]>7)intaa_mouseState[mousePosition][1]=5;
		}break;
		}
	}
	
}
