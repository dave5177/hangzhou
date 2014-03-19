package com.dave.jpsc.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

/**
 * @author Administrator 关卡选择界面
 */
/**
 * @author Dave
 *
 */
public class MissionChoose implements Showable {

	/**
	 * 正在解锁新的关卡
	 */
	public static boolean unlocking;

	private CanvasControl canvasControl;

	public Image[][] a_img_icon;// 关卡图标
	public Image[] a_img_star;// 星星（背景和前景）
	public Image img_back;
	public Image img_param_back;
	public Image img_lock;
	public Image[] a_img_select;

	/**
	 * 选择关卡下标值
	 */
	private int index;

	/**
	 * 选择指针动画帧
	 */
	private int frame_select;

	/**
	 * 关卡介绍框坐标
	 */
	private int x_param, y_param;

	/**
	 * 关卡图标的坐标
	 */
	private final int[][] iconCoord = { { 205, 160 },// 雪邦
			{ 269, 216 },// 上海
			{ 298, 313 },// 加泰罗尼亚
			{ 352, 387 },// 亚斯码头
			{ 469, 444 },// 伊斯坦布尔
			{ 470, 329 },// 巴林
			{ 554, 256 },// 阿尔伯特公园
			{ 541, 159 },// 铃鹿
			{ 463, 211 },// 摩纳哥
			{ 396, 141 },// 银石
			{ 565, 97 },// 纽博格林
			{ 477, 39 },// 新加坡
			{ 352, 62 },// 布达佩斯
			{ 198, 67 },// 斯帕
			{ 80, 71 },// 蒙札
			{ 99, 145 },// 英特拉格斯
	};
	
	/**
	 * 路线的点坐标
	 */
	private final int[][] route = {
			{//1到2关
				193, 177,
				197, 178,
				201, 180,
				204, 182,
				206, 184,
				208, 187,
				210, 190,
				212, 194,
				214, 199,
				215, 204,
			},
			{//2到3关
				227, 240,
				229, 248,
				231, 257,
				231, 265,
				228, 273,
				225, 281,
				228, 289,
				230, 297,
				233, 305,
				237, 314,
			},
			{//3到4关
				259, 328,
				268, 334,
				276, 340,
				283, 345,
				288, 350,
				292, 356,
				295, 361,
				298, 367,
				302, 373,
				305, 379,
			},
			{//4到5关
				326, 407,
				334, 411,
				342, 416,
				355, 421,
				368, 425,
				381, 430,
				389, 435,
				395, 440,
				401, 445,
				407, 450,
			},
			{//5到6关
				423, 421,
				426, 415,
				429, 408,
				428, 398,
				426, 390,
				423, 381,
				422, 372,
				423, 364,
				425, 355,
				427, 347,
			},
			{//6到7关
				443, 303,
				444, 301,
				450, 298,
				458, 295,
				466, 294,
				474, 291,
				482, 288,
				491, 285,
				492, 284,
				492, 283,
			},
			{//7到8关
				504, 228,
				508, 222,
				512, 217,
				515, 210,
				517, 204,
				519, 199,
				519, 194,
				517, 189,
				515, 185,
				513, 180,
			},
			{//8到9关
				501, 175,
				495, 178,
				489, 181,
				483, 186,
				480, 194,
				477, 200,
				473, 208,
				470, 214,
				464, 217,
				456, 219,
			},
			{//9到10关
				410, 210,
				405, 203,
				400, 197,
				398, 192,
				396, 187,
				393, 182,
				390, 177,
				386, 172,
				382, 168,
				380, 163,
			},
			{//10到11关
				392, 121,
				392, 116,
				403, 114,
				418, 116,
				433, 120,
				448, 124,
				477, 128,
				486, 122,
				495, 116,
				503, 109,
			},
			{//11到12关
				515, 79,
				510, 74,
				505, 69,
				500, 64,
				490, 59,
				485, 56,
				480, 54,
				474, 50,
				466, 48,
				459, 46,
			},
			{//12到13关
				422, 47,
				401, 49,
				400, 51,
				390, 54,
				380, 57,
				370, 59,
				360, 61,
				350, 63,
				340, 65,
				330, 66,
			},
			{//13到14关
				295, 69,
				294, 73,
				282, 77,
				270, 81,
				258, 79,
				246, 77,
				234, 75,
				222, 73,
				210, 71,
				198, 68,
			},
			{//14到15关
				152, 69,
				146, 69,
				137, 70,
				128, 70,
				119, 71,
				110, 71,
				101, 72,
				92, 73,
				83, 74,
				74, 75,
			},
			{//15到16关
				60, 87,
				62, 92,
				64, 98,
				66, 104,
				64, 110,
				60, 116,
				56, 121,
				54, 126,
				51, 131,
				51, 134,
			},
	};
	
	/**
	 * 解锁图标显示的数量
	 */
	private int num_show_unlock;
	
	/**
	 * 关卡信息，从左到右分别代表：关卡信息，圈数，对手数量，比赛类型，完成条件，难度（星级），奖励，世界纪录。
	 */
	private final String[][] missionInfo = {
			{"雪邦赛道距离马来西亚首都吉隆坡约65公里，其赛道的设施非常出色", "1", "2", "排名赛", "获得第一，并在200s内完成赛事", "1", "30", "02:00:00"},
			{"上海国际赛车道，世界一级方程式比赛赛道之一．", "1", "2", "排名赛", "获得第一，并在200s内完成赛事", "1", "30", "01:50:00"},
			{"加泰罗尼亚赛道建于1991年，是被国际上公认的最贴近完美的跑道", "1", "2", "排名赛", "获得第一，并在200s内完成赛事", "1", "30", "01:40:00"},
			{"亚斯码头赛道，是一条逆时针赛道，由高速弯和紧凑的慢速弯组成", "2", "2", "排名赛", "获得第一，并在180s内完成赛事", "2", "40", "01:30:00"},
			{"伊斯坦布尔赛道属于逆时针赛道．", "1", "2", "排名赛", "获得第一，并在180s内完成赛事", "2", "40", "01:40:00"},
			{"巴林国际赛道是位于巴林的赛车场，其举办的主要赛事包括一级方程式赛车巴林大奖赛和三级方程式赛车等．", "2", "2", "排名赛", "获得第一，并在180s内完成赛事", "2", "40", "01:30:00"},
			{"阿尔伯特公园赛道属于街道赛场，赛道被安排在市区的阿尔伯特公园，整体布局相当流畅．", "2", "3", "排名赛", "获得第一，并在160s内完成赛事", "3", "60", "01:30:00"},
			{"铃鹿赛道以混合的高速和低速弯角闻名，速度颇高需要有强大的马力输出．", "2", "3", "排名赛", "获得第一，并在160s内完成赛事", "3", "60", "01:30:00"},
			{"摩纳哥赛道，是摩纳哥公国的一条街道赛道，这条赛道在每年五月的某个周末会迎来F1大奖赛，同时还会有F2和F3000（现在是GP2）作为垫场赛．", "2", "3", "排名赛", "获得第一，并在160s内完成赛事", "3", "60", "01:25:00"},
			{"银石赛道拥有很长的直线道与高速的弯道，这不仅测试赛车的性能，更是在考验着车手驾驶技术和胆识的极限度．", "2", "4", "排名赛", "获得第一，并在150s内完成赛事", "4", "70", "01:25:00"},
			{"纽博格林赛道位于漂亮的郊区，拥有庞大沙地的纽博格林赛道对车手来说是个不可多得的既快速又安全的赛场．", "2", "4", "排名赛", "获得第一，并在150s内完成赛事", "4", "70", "01:15:00"},
			{"新加坡赛道也是亚洲第一条F1街道赛道．", "2", "4", "排名赛", "获得第一，并在150s内完成赛事", "4", "70", "01:15:00"},
			{"布达佩斯赛道位于布达佩斯东北面十二英里的布达佩斯赛道是一个相当现代化的F1赛车场", "3", "5", "排名赛", "获得第一，并在150s内完成赛事", "5", "80", "01:10:00"},
			{"斯帕赛道位于比利时的中心，在1924年第一次被启用，自从1985年开始举行比利时F1大奖赛．", "3", "5", "排名赛", "获得第一，并在150s内完成赛事", "5", "80", "01:10:00"},
			{"蒙扎赛道于1922年建成，蒙扎位处于米兰东北30公里，而从一级方程式有史以来，蒙扎都是意大利F1比赛的主办地．", "3", "5", "排名赛", "获得第一，并在150s内完成赛事", "5", "80", "01:10:00"},
			{"英特拉格斯赛道距离圣保罗市中心约25公里，位于卡洛斯-佩斯赛车场，自从1991年开始这里便成为了巴西大奖赛的举办地．", "3", "5", "排名赛", "获得第一，并在150s内完成赛事", "5", "80", "01:10:00"},
	};
	
	public final static String[] MAP_NAME = {
		"雪邦赛道",//0
		"上海国际赛车道",//1
		"加泰罗尼亚赛",//2
		"亚斯码头赛",//3
		"伊斯坦布尔赛道",//4
		"巴林国际赛道",//5
		"阿尔伯特公园赛道",//6
		"铃鹿赛道",//7
		"摩纳哥赛道",//8
		"银石赛道",//9
		"纽博格林赛道",//10
		"新加坡赛道",//11
		"布达佩斯赛道",//12
		"斯帕赛道",//13
		"蒙扎赛道",//14
		"英特拉格斯赛道",//15
		"副本一",//16
		"副本二",//17
		"副本三",//18
		"副本四",//19
		"副本五",//20
		"副本六",//21
		"副本七",//22
		"副本八",//23
		"副本九",//24
		"挑战赛道一",//25
		"挑战赛道二",//26
		"挑战赛道三",//27
		"挑战赛道四",//28
		"挑战赛道五",//29
		"挑战赛道六",//30
		"挑战赛道七",//31
		"挑战赛道八",//32
		"挑战赛道九",//33
		"挑战赛道十",//34
		"挑战赛道十一"//35
	};

	/**
	 * 解锁中前进的点
	 */
	private int adv_unlock;
	
	/**
	 * 频率控制
	 */
	private int freq;

	public MissionChoose(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
		
		index = CanvasControl.missionPassed - 1;//默认选中最后的关卡
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		num_show_unlock = unlocking ? CanvasControl.missionPassed - 1 : CanvasControl.missionPassed;
		for (int i = 0; i < 16; i++) {
			if (i < num_show_unlock) {
				g.drawImage(a_img_icon[i][1], iconCoord[i][0], iconCoord[i][1],
						Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(img_lock,
						iconCoord[i][0] - a_img_icon[i][0].getWidth() / 2 + 30,
						iconCoord[i][1] + 15, Graphics.VCENTER
								| Graphics.HCENTER);
			}
		}
		
		showRoute(g);
		
		if(unlocking) {
			
		} else {
			g.drawImage(a_img_select[frame_select], iconCoord[index][0]
					- a_img_icon[index][0].getWidth() / 2 + 20,
					iconCoord[index][1], Graphics.VCENTER | Graphics.HCENTER);
		}

		if(!unlocking) {
			g.drawImage(img_param_back, x_param, y_param, 0);
			showTitle(g);
			showMissionInfo(g);
		}
	}

	/**
	 * 画路线
	 * @param g
	 */
	private void showRoute(Graphics g) {
		g.setColor(0xffffff);
		
		if(unlocking) {//正在解锁
			for (int i = 0; i < CanvasControl.missionPassed - 2; i++) {
				for (int j = 0; j < 10; j++) {
					C.drawPoint(g, route[i][2 * j], route[i][1 + 2 * j]);
				}
			}
			
			for (int i = 0; i < adv_unlock; i++) {
				C.drawPoint(g, route[CanvasControl.missionPassed - 2][2 * i], route[CanvasControl.missionPassed - 2][1 + 2 * i]);
			}
		} else {
			for (int i = 0; i < (CanvasControl.missionPassed - 1); i++) {
				for (int j = 0; j < 10; j++) {
					C.drawPoint(g, route[i][2 * j], route[i][1 + 2 * j]);
				}
			}
			
		}
	}

	/**
	 * 关卡信息里的标题文字
	 * @param g
	 */
	private void showTitle(Graphics g) {
		g.setColor(255, 80, 80);
		g.setFont(C.FONT_MEDIUM_BOLD);
		g.drawString("赛道介绍", x_param + 8, y_param + 35, 0);
		g.drawString("比赛相关内容", x_param + 8, y_param + 120, 0);
		g.drawString("奖励", x_param + 8, y_param + 260, 0);
		g.drawString("世界纪录：" + missionInfo[index][7], x_param + 8, y_param + 320, 0);
	}
	
	/**
	 * 关卡信息内容
	 * @param g
	 */
	private void showMissionInfo(Graphics g) {
		g.setColor(0xffbc38);
		C.drawStrInRect(x_param + 8, y_param + 55, 300, 70, missionInfo[index][0], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 140, 300, 70, "圈数：" + missionInfo[index][1], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 140, y_param + 140, 300, 70, "对手数量：" + missionInfo[index][2], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 160, 300, 70, "比赛类型：" + missionInfo[index][3], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 180, 300, 70, "完成条件：" + missionInfo[index][4], C.FONT_MEDIUM_BOLD, g);
		C.drawStrInRect(x_param + 8, y_param + 220, 300, 70, "难度：", C.FONT_MEDIUM_BOLD, g);
		for (int i = 0; i < 5; i++) {
			g.drawImage(a_img_star[1], x_param + 50 + i * 19, y_param + 228, Graphics.VCENTER | Graphics.HCENTER);
		}
		int count = Integer.parseInt(missionInfo[index][5]);
		for (int i = 0; i < count; i++) {
			g.drawImage(a_img_star[0], x_param + 50 + i * 19, y_param + 228, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		C.drawStrInRect(x_param + 8, y_param + 280, 300, 70, "经验：" + missionInfo[index][6], C.FONT_MEDIUM_BOLD, g);
	}
	
	public void loadResource() {
		try {
			a_img_icon = new Image[16][2];
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 2; j++) {
					a_img_icon[i][j] = Image.createImage("/missionChoose/icon_"
							+ i + "_" + j + ".png");
				}
			}

//			img_back = Image.createImage("/missionChoose/back.jpg");
			img_lock = Image.createImage("/missionChoose/lock.png");
			img_param_back = Image.createImage("/missionChoose/param_back.png");
			a_img_star = new Image[2];
			Image img_temp = Image.createImage("/missionChoose/star.png");
			for (int i = 0; i < 2; i++) {
				a_img_star[i] = Image.createImage(img_temp, i * 19, 0, 19, 17,
						0);
			}

			a_img_select = new Image[3];
			img_temp = Image.createImage("/missionChoose/select.png");
			for (int i = 0; i < 3; i++) {
				a_img_select[i] = Image.createImage(img_temp, i * 59, 0, 59,
						49, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resetParamPoint();

	}

	public void removeResource() {
		img_lock = null;
		img_param_back = null;

		for (int i = 0; i < a_img_star.length; i++) {
			a_img_star[i] = null;
		}
		a_img_star = null;

		for (int i = 0; i < a_img_select.length; i++) {
			a_img_select[i] = null;
		}
		a_img_select = null;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 2; j++) {
				a_img_icon[i][j] = null;
			}
		}
		a_img_icon = null;

		System.gc();
	}

	public void removeServerImage() {
		img_back = null;
		/*img_lock = null;
		img_param_back = null;

		for (int i = 0; i < a_img_star.length; i++) {
			a_img_star[i] = null;
		}
		a_img_star = null;

		for (int i = 0; i < a_img_select.length; i++) {
			a_img_select[i] = null;
		}
		a_img_select = null;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 2; j++) {
				a_img_icon[i][j] = null;
			}
		}
		a_img_icon = null;

		System.gc();*/
	}
	

	public void keyPressed(int keyCode) {
		canvasControl.playKeySound();
		if(unlocking)
			return;
		switch (keyCode) {
		case C.KEY_RIGHT:
		case C.KEY_DOWN:
			if (index == 15) {
				index = 0;
			} else {
				if (index < CanvasControl.missionPassed - 1)
					index++;
			}
			resetParamPoint();
			break;
		case C.KEY_LEFT:
		case C.KEY_UP:
			if (index == 0) {
				if (CanvasControl.missionPassed >= 16)
					index = 15;
			} else
				index--;

			resetParamPoint();
			break;
		case C.KEY_1:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 9));
			break;
		case C.KEY_FIRE:
			CanvasControl.mission = index + 1;
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 7));
			break;

		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Home(canvasControl));
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

	/**
	 * 每次移动光标都更新关卡介绍框坐标
	 */
	public void resetParamPoint() {
		if (iconCoord[index][0] < 300)
			x_param = iconCoord[index][0] + 20;
		else
			x_param = iconCoord[index][0] - 340 - a_img_icon[index][0].getWidth() / 2;
		
		if(x_param < 10)
			x_param = 10;
		else if (x_param > 320) {
			x_param = 320;
		}
		
		if (iconCoord[index][1] < 250) {
			y_param = iconCoord[index][1] + 20;
		} else {
			y_param = iconCoord[index][1] - 380;
		}
		if(y_param < 20)
			y_param = 20;
		else if(y_param > 140)
			y_param = 140;
	}

	public void logic() {
		frame_select++;
		if (frame_select > 2) {
			frame_select = 0;
		}
		
		if(unlocking) {
			freq ++;
			if(freq > 5) {
				freq = 0;
				if(adv_unlock < 9) {
					adv_unlock ++;
				} else {
					unlocking = false;
					index = CanvasControl.missionPassed - 1;
					resetParamPoint();
				}
			}
		}
	}

	public void handleGoods(int goodsIndex) {
		
	}

}
