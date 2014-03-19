package com.dave.rangzidanf.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Player;

import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.tool.AudioPlaydalin;
import com.dave.rangzidanf.tool.C;

/**
 * @author Administrator
 *关卡说明类，可以选择关卡，显示各个关卡的敌人类型和数量。
 *显示关卡的背景故事。
 */
public class Explanation implements Showable {
	private CanvasControl canvasControl;
	
	/**
	 * 第几关
	 */
	private int level;
	
	/**
	 * 剧情介绍文字
	 */
	private String[] story = {
		"           第一关：七七卢沟桥事变        ",				//0
		"地点：卢沟桥           时间：1937年7月7日",				//1
		"    1937年7月7日，日本蓄意制造“七七事变”，",		//2
		"悍然发动全面侵华战争。中国驻军奋起反击。",		//3
		"事后，中共通电全国，号召团结抗日。蒋介石",		//4
		"也发表谈话，宣布准备抗战。“七七事变”后，",		//5
		"日军先后在华北、华中、华南发动全面进攻。",		//6
		"中国从此形成两个战场——国民党数十万军队",			//7
		"主要在日军进攻的正面作战，是为正面战场；",		//8
		"在日占区，中共领导的人民武装开辟敌后",			//9
		"战场，逐渐成为抗日战争主战场。",					//10
		
		"          第二关：南京！南京！",						//11
		"地点：南京总统府   时间：1938年12月13日",			//12
		"    1938年12月13日，南京陷落。日军屠杀中国",		//13
		"平民是士兵30万人以上（史称“南京大屠杀”）。",		//14
		"面对日本侵略，正面战场的中国军队浴血奋战，",		//15
		"顽强抵抗，徐州会战、武汉保卫战、长沙保卫战等",	//16
		"显示了中国军民的抗战决心。敌后军民则展开地",		//17
		"雷战、地道战、麻雀战等广泛的游击战争，钳制",		//18
		"与消耗日军，并在1940年进行了“百团大战”。",		//19
		"两党互相配合的抗日持久战，令日军虽占领大片中",	//20
		"国国土，但无力对中国实行全面进攻，无法实现征",	//21
		"服中国的目的。",								//22
		
		"            第三关：华南战争",						//23
		"地点：广州    时间：1938年10月12日",				//24
		"    10月12日，日军第十八、低一〇四师团，",		//25
		"从澎湖列岛的马公出航，在第五舰队海空兵力",		//26
		"（含航空母舰“加贺”、“苍龙”、“千岁”号",			//27
		"和“龙嚷”号等）的支援下，从广东大亚湾登",			//28
		"陆。第日，日军框炸广东惠阳。3天后，惠阳失",		//29
		"陷，19日，日军突袭增城，中国守军2万多人一",		//30
		"触即溃。21日，余汉谋部撤出广州。广州失陷。",		//31
		"    此时，我军派出了一名神秘的狙击手，歼灭",		//32
		"了驻守在广州市立银行的大量日军！",				//33
	
		"          第四关：淞沪会战",						//34
		"地点：上海外滩    时间：1938年8月13日",				//35
		"    1938年8月13日，日本海军进攻上海，淞沪",		//36
		"战役开始。",									//37
		"    此时，我军派出了一名神秘的狙击手，使",		//38
		"驻空在上海外滩的大量日军遭受严重打击！",			//39
								
		"          第五关：武汉会战",						//40
		"地点：武汉黄鹤楼    时间：1938年10月28日",			//41
		"    1938年10月25日，日军占领武汉，武汉沦陷。",	//42
		"据统计，自1937年至武汉沦陷，日机侵袭武汉61次，",	//43
		"炸死炸伤8600余人。其中死亡3389人。在日军占领",	//44
		"武汉7年间，残杀民众13508人。",					//45
				
		"            第六关：百团大战",						//46
		"地点：中国华北    时间：1940年8月20日",				//47
		"    19340年8月20日，八路军的晋察冀军区、第129、",//48
		"第120师在总部统一指挥下，发动了以破袭正太铁",		//49
		"路（石家庄至太原）为重点的战役。战役发起第3天，",	//50
		"八路军参战部队已达105个团，故称此为“百团大战”。"	//51
		
	};
	private int storyX = 100;
	private int storyY = 470;
	
	private Image img_back;
	private Image img_arrow;
	private Image img_mouth;
	private Image img_number_enemy;
	private Image img_number_level;
	
	private Image img_map;
	
	private boolean drawMouth;
	
	private boolean pressOK;

	private int arrowX = 490;

	public Explanation(CanvasControl canvasControl, int level) {
		this.canvasControl = canvasControl;
		canvasControl.setNeedRepaint(false);
		
		this.level = level;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_arrow, arrowX, 83, Graphics.VCENTER | Graphics.RIGHT);
		showFitting(g);
		showStroy(g);
	}
	
	public void showFitting(Graphics g) {
		g.drawImage(img_map, 30, 110, 0);
		C.drawString(g, img_number_level, level + "", "123456", 175, 75, 12, 18, 0, 0, 0);
		
		for(int i=0; i<5; i++) {
			C.drawString(g, img_number_enemy, CanvasControl.enemyQuantity[level - 1][i] + "", "0123456789", 550, 127 + i * 40, 11, 16, 0, 0, 0);
		}
		
		if(drawMouth) g.drawImage(img_mouth, 527, 430, 0);
	}
	
	public void showStroy(Graphics g) {
		g.setClip(storyX, 420, 300, 50);
		switch (level) {
		case 1:
			for(int i=0; i<11; i++) {
				g.drawString(story[i], storyX, storyY + i * 20, 0);
			}
			break;
		case 2:
			for(int i=11; i<23; i++) {
				g.drawString(story[i], storyX, storyY + (i - 11) * 20, 0);
			}
			break;
		case 3:
			for(int i=23; i<34; i++) {
				g.drawString(story[i], storyX, storyY + (i - 23) * 20, 0);
			}
			break;
		case 4:
			for(int i=34; i<40; i++) {
				g.drawString(story[i], storyX, storyY + (i - 34) * 20, 0);
			}
			break;
		case 5:
			for(int i=40; i<46; i++) {
				g.drawString(story[i], storyX, storyY + (i - 40) * 20, 0);
			}
			break;
		case 6:
			for(int i=46; i<52; i++) {
				g.drawString(story[i], storyX, storyY + (i - 46) * 20, 0);
			}
			break;
		}
		g.setClip(0, 0, C.WIDTH, 530);
	}
	
	public void logic(){
		if(pressOK) {
			startAfterMusic();
			return;
		}
		storyY -= 2;
		
		if(arrowX == 490) arrowX = 484;
		else arrowX = 490;
		
		if(storyY % 4 == 0) drawMouth = true;
		else drawMouth = false;
		canvasControl.repaint();
		
		if(storyY < 200) storyY = 470;
	}
	
	/**
	 * 音乐结束后进入武器库
	 */
	public void startAfterMusic() {
//		if(canvasControl.audio.getState(6) == Player.PREFETCHED) {
//			canvasControl.setView(new Arsenal(canvasControl));
//			this.removeResource();
//		}
//		if(CanvasControl.player.getState() == AudioPlay.CLOSED) {
//			canvasControl.setView(new Arsenal(canvasControl));
//			this.removeResource();
//		}
	}
	
	public void loadResource() {
		try {
			img_back = Image.createImage("/explanation/back.png");
			img_arrow = Image.createImage("/button/arrow.png");
			img_mouth = Image.createImage("/explanation/mouth.png");
			img_number_level = Image.createImage("/explanation/number_level.png");
			img_number_enemy = Image.createImage("/explanation/number_enemy.png");
			
			img_map = Image.createImage("/explanation/map_" + level + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		img_back = null;
		img_arrow = null;
		img_mouth = null;
		img_number_level = null;
		img_number_enemy = null;
		img_map = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_FIRE :
//			CanvasControl.player.close();
//			canvasControl.audio.stopSound(6);
//			pressOK = true;
			
			canvasControl.setView(new Arsenal(canvasControl));
			this.removeResource();
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

}
