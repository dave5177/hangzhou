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
 *�ؿ�˵���࣬����ѡ��ؿ�����ʾ�����ؿ��ĵ������ͺ�������
 *��ʾ�ؿ��ı������¡�
 */
public class Explanation implements Showable {
	private CanvasControl canvasControl;
	
	/**
	 * �ڼ���
	 */
	private int level;
	
	/**
	 * �����������
	 */
	private String[] story = {
		"           ��һ�أ�����¬�����±�        ",				//0
		"�ص㣺¬����           ʱ�䣺1937��7��7��",				//1
		"    1937��7��7�գ��ձ��������조�����±䡱��",		//2
		"��Ȼ����ȫ���ֻ�ս�����й�פ�����𷴻���",		//3
		"�º��й�ͨ��ȫ���������ŽΌ�ա�����ʯ",		//4
		"Ҳ����̸��������׼����ս���������±䡱��",		//5
		"�վ��Ⱥ��ڻ��������С����Ϸ���ȫ�������",		//6
		"�й��Ӵ��γ�����ս������������ʮ�����",			//7
		"��Ҫ���վ�������������ս����Ϊ����ս����",		//8
		"����ռ�����й��쵼��������װ���ٵк�",			//9
		"ս�����𽥳�Ϊ����ս����ս����",					//10
		
		"          �ڶ��أ��Ͼ����Ͼ���",						//11
		"�ص㣺�Ͼ���ͳ��   ʱ�䣺1938��12��13��",			//12
		"    1938��12��13�գ��Ͼ����䡣�վ���ɱ�й�",		//13
		"ƽ����ʿ��30�������ϣ�ʷ�ơ��Ͼ�����ɱ������",		//14
		"����ձ����ԣ�����ս�����й�����ԡѪ��ս��",		//15
		"��ǿ�ֿ������ݻ�ս���人����ս����ɳ����ս��",	//16
		"��ʾ���й�����Ŀ�ս���ġ��к������չ����",		//17
		"��ս���ص�ս����ȸս�ȹ㷺���λ�ս����ǯ��",		//18
		"�������վ�������1940������ˡ����Ŵ�ս����",		//19
		"����������ϵĿ��ճ־�ս�����վ���ռ���Ƭ��",	//20
		"�����������������й�ʵ��ȫ��������޷�ʵ����",	//21
		"���й���Ŀ�ġ�",								//22
		
		"            �����أ�����ս��",						//23
		"�ص㣺����    ʱ�䣺1938��10��12��",				//24
		"    10��12�գ��վ���ʮ�ˡ���һ����ʦ�ţ�",		//25
		"������е������������ڵ��形�Ӻ��ձ���",		//26
		"��������ĸ�����Ӻء���������������ǧ�ꡱ��",			//27
		"�͡����¡��ŵȣ���֧Ԯ�£��ӹ㶫�������",			//28
		"½�����գ��վ���ը�㶫������3��󣬻���ʧ",		//29
		"�ݣ�19�գ��վ�ͻϮ���ǣ��й��ؾ�2�����һ",		//30
		"��������21�գ��຺ı���������ݡ�����ʧ�ݡ�",		//31
		"    ��ʱ���Ҿ��ɳ���һ�����صľѻ��֣�����",		//32
		"��פ���ڹ����������еĴ����վ���",				//33
	
		"          ���Ĺأ�������ս",						//34
		"�ص㣺�Ϻ���̲    ʱ�䣺1938��8��13��",				//35
		"    1938��8��13�գ��ձ����������Ϻ�������",		//36
		"ս�ۿ�ʼ��",									//37
		"    ��ʱ���Ҿ��ɳ���һ�����صľѻ��֣�ʹ",		//38
		"פ�����Ϻ���̲�Ĵ����վ��������ش����",			//39
								
		"          ����أ��人��ս",						//40
		"�ص㣺�人�ƺ�¥    ʱ�䣺1938��10��28��",			//41
		"    1938��10��25�գ��վ�ռ���人���人���ݡ�",	//42
		"��ͳ�ƣ���1937�����人���ݣ��ջ���Ϯ�人61�Σ�",	//43
		"ը��ը��8600���ˡ���������3389�ˡ����վ�ռ��",	//44
		"�人7��䣬��ɱ����13508�ˡ�",					//45
				
		"            �����أ����Ŵ�ս",						//46
		"�ص㣺�й�����    ʱ�䣺1940��8��20��",				//47
		"    19340��8��20�գ���·���Ľ��켽��������129��",//48
		"��120ʦ���ܲ�ͳһָ���£�����������Ϯ��̫��",		//49
		"·��ʯ��ׯ��̫ԭ��Ϊ�ص��ս�ۡ�ս�۷����3�죬",	//50
		"��·����ս�����Ѵ�105���ţ��ʳƴ�Ϊ�����Ŵ�ս����"	//51
		
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
	 * ���ֽ��������������
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
