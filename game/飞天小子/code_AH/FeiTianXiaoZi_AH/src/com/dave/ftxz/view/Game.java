package com.dave.ftxz.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.model.Aerolite;
import com.dave.ftxz.model.Bird;
import com.dave.ftxz.model.Bullet;
import com.dave.ftxz.model.Coin;
import com.dave.ftxz.model.Hero;
import com.dave.ftxz.model.Pet;
import com.dave.ftxz.model.Treasure;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;

/**
 * @author Administrator ��Ϸ����
 */
public class Game implements Showable {

	public CanvasControl canvasControl;

	public Image img_map;// ��ͼ
	public Image img_bar_side;//�̶���
	public Image img_bar_distance;
	public Image img_bar_coin;
	public Image img_number_gura;//�̶�����
	public Image img_key;
	public Image img_airship;
	public Image img_hero;
	public Image img_aerolite;//��ʯ
	public Image img_coin;
	public Image img_slow;
	public Image img_sprint;//���Ч��ͼ2
	public Image img_protected;//�޵�Ч��
	
	public Image[] a_img_count_number;//����ʱ����ͼƬ
//	public Image[] a_img_count_feel;//����ʱЧ��ͼƬ
	public Image[] a_img_full_coin;//���ת��Ϊ����Ч��ͼƬ
	
	public Image[] a_img_attract;//����ʯЧ��
	public Image[] a_img_sprint;//���Ч��ͼ1
	public Image[] a_img_boom_bird;//����ը��
	public Image[] a_img_bullet;//�ӵ�ͼƬ
	public Image[] a_img_pet;//�ȳ�ͼƬ
	public Image[] a_img_alert;//��̾��
	public Image[] a_img_beHit_bird;//���񱻹�������ͼƬ
	public Image[] a_img_beHit_hero;//Ӣ�۱���������ͼƬ
	public Image[] a_img_bar_blood;//Ѫ��
	public Image[] a_img_treasure;//�����ͼƬ
	public Image[][] a_2_img_bird;//�����ͼƬ
	
	/**
	 * ���ߵ���Ϣ��[����type][0]���ߵȼ�����õ��߹����������ȣ�
	 * [����type][1]������ȴʱ�䡣
	 * [����type][2]���߳���ʣ��ʱ�䡣
	 * type0~3:���١���̡��޵С�����ʯ
	 */
	public int[][] treasure_info;
	
	/**
	 * y����
	 */
	public int y_map = -372;
	
	/**
	 * �洢�����ӵ�
	 */
	public Vector v_bullet;
	
	/**
	 * ����
	 */
	public Vector v_bird;
	
	/**
	 * ��ʯ
	 */
	public Vector v_aerolite;
	
	/**
	 * ���
	 */
	public Vector v_coin;
	
	/**
	 * ����
	 */
	public Vector v_treasure;
	
	/**
	 * vector��size
	 */
	public int size_v;
	
	/**
	 * ���Ƶ�Ӣ��
	 */
	public Hero hero;
	
	/**
	 * ���������100���ڣ�100����Ϊ����100�ף�
	 */
	public int num_coin;
	
	/**
	 * ����Ŀ̶ȵ�λ����
	 */
	private int length_unit;
	
	/**
	 * �����ȼ�����������Ч����
	 */
	public int power_level = 0;

	private int gura_ctrl;

	/**
	 * ��ʼ��Ϸʱ�ĵ���ʱ
	 */
	private boolean countdown;

	/**
	 * ��ߵ���ʱЧ������֡
	 */
//	private int frame_count_feel_1 = 2;

	/**
	 * ��ߵ���ʱЧ��x���
	 */
//	private int x_feel_1 = 320;

	/**
	 * �ұߵ���ʱЧ������֡
	 */
//	private int frame_count_feel_2 = 3;

	/**
	 * �ұߵ���ʱЧ��x����
	 */
	private int x_feel_2 = 320;

	/**
	 * ����ʱ���ֶ���֡
	 */
	private int frame_count_number;
	
	/**
	 * ������Ϸ���ʱ��
	 */
	private int time_start;

	/**
	 * ����ʱ����y����
	 */
	private int y_number;
	
	/**
	 * ����ʱ����y���������ٶ�
	 */
	private int downspeed_y = 20;

	/**
	 * ���ת��Ч��������
	 */
	private int trans_x, trans_y;

	/**
	 * ���ת��Ч����y���ٶ�
	 */
	private int trans_speed_y;

	/**
	 * ����ת��
	 */
	public boolean trans;
	
	/**
	 * ���ת��Ч����ͼƬ֡
	 */
	private int frame_trans;

 	public Game(CanvasControl canvasControl) {
 		this.canvasControl = canvasControl;
 		
 		length_unit = 100;
 		v_bullet = new Vector();
 		v_bird = new Vector();
 		v_aerolite = new Vector();
 		v_coin = new Vector();
 		v_treasure = new Vector();
 		
 		treasure_info = new int[4][3];
 		for (int i = 0; i < 4; i++) {
			treasure_info[i][0] = CanvasControl.goodsNumber[i + 5];
			CanvasControl.goodsNumber[i + 5] = 0;
		}
 		
 		////////////////����ͽ������/////////
 		CanvasControl.distance = 0;
 		CanvasControl.coin_total = 0;
 		
 		createBird();

 		//////////////��ʼ������//////////////////
		power_level = CanvasControl.goodsNumber[4];
		CanvasControl.goodsNumber[4] = 0;
 		hero = new Hero(this, CanvasControl.type_hero);

		if(CanvasControl.goodsNumber[3] > 0) {
			new Pet(this, hero);
			CanvasControl.goodsNumber[3] --;
		}
 		
 		canvasControl.saveParam();
 		
 		countdown = true;
 		loadCountImage();
	}

	public void show(Graphics g) {
		g.setClip(0, 0, 640, 530);
		g.drawImage(img_map, 0, y_map, 0);
		g.drawImage(img_map, 0, y_map, Graphics.LEFT | Graphics.BOTTOM);
		
		if(countdown) {
//			if(time_start >= 20) {
//				g.drawImage(a_img_count_feel[frame_count_feel_1], x_feel_1, 200, Graphics.VCENTER | Graphics.HCENTER);
//				g.drawImage(a_img_count_feel[frame_count_feel_2], x_feel_2, 200, Graphics.VCENTER | Graphics.HCENTER);
//			}
			g.drawImage(a_img_count_number[frame_count_number], 320, y_number, Graphics.VCENTER | Graphics.HCENTER);
			return;
		}
		
		///////////////////////����///////////////////////////
		size_v = v_coin.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Coin)v_coin.elementAt(i)).show(g);
		}
		
		size_v = v_treasure.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Treasure)v_treasure.elementAt(i)).show(g);
		}
		
		size_v = v_bullet.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Bullet)v_bullet.elementAt(i)).show(g);
		}
		
		hero.show(g);
		
		size_v = v_bird.size(); 
		for (int i = size_v - 1; i >= 0; i--) {
			((Bird)v_bird.elementAt(i)).show(g);
		}
		
		size_v = v_aerolite.size(); 
		for (int i = size_v - 1; i >= 0; i--) {
			((Aerolite)v_aerolite.elementAt(i)).show(g);
		}
		
		///////////////////////////�̶�///////////////////////
		g.drawImage(img_bar_side, 10, 250, Graphics.LEFT | Graphics.VCENTER);
		for (int i = 1; i < 10; i++) {
			C.drawString(g, img_number_gura, (length_unit * i) + "", "0123456789", 30, 445 - i * 40, 10, 13, 0, 0, 0);
		}
		g.drawRegion(img_bar_side, 0, 0, 18, 397, Sprite.TRANS_MIRROR, 630, 250, Graphics.RIGHT | Graphics.VCENTER);
		for (int i = 1; i < 10; i++) {
			C.drawString(g, img_number_gura, (10 * i) + "", "0123456789", 610, 445 - i * 40, 10, 13, Graphics.RIGHT | Graphics.TOP, 0, 0);
		}
		gura_ctrl = num_coin - 3;
		gura_ctrl = gura_ctrl > 0 ? gura_ctrl : 0;
		g.setClip(400, 439 - (gura_ctrl) * 385 / 97, 240, 420);
		g.drawImage(img_bar_coin, 612, 53, 0);
		
		gura_ctrl = CanvasControl.distance - 3 * (length_unit / 10);
		gura_ctrl = gura_ctrl > 0 ? gura_ctrl : 0;
		g.setClip(0, 439 - (gura_ctrl) * 385 / (length_unit * 10 - 3 * (length_unit / 10)), 200, 420);
		g.drawImage(img_bar_distance, 1, 53, 0);
		g.setClip(0, 0, 640, 530);
		
		//����9������0���ء�
		g.drawImage(img_key, 630, 520, Graphics.BOTTOM | Graphics.RIGHT);
		
		if(trans) {
			g.drawImage(a_img_full_coin[frame_trans], trans_x, trans_y, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		/////////////////////////////�������////////////////////////////////////////
//		g.setColor(0);
//		g.fillRect(200, 0, 100, 120);
//		g.setColor(255, 255, 255);
//		g.drawString("��������:" + v_bird.size(), 203, 2, 0);
//		g.drawString("�ӵ�����:" + v_bullet.size(), 203, 13, 0);
//		g.drawString("�������:" + v_coin.size(), 203, 23, 0);
//		g.drawString("��������:" + v_treasure.size(), 203, 33, 0);
//		g.drawString("���о���:" + CanvasControl.distance, 203, 43, 0);
//		g.drawString("��õĽ����:" + num_coin, 203, 53, 0);
//		g.drawString("�̶Ƚ�ȡ:" + gura_ctrl, 203, 63, 0);
//		g.drawString("��λ����:" + length_unit, 203, 73, 0);
	}

	/**
	 * ���ص���ʱ��ͼƬ
	 */
	private void loadCountImage() {
		try {
//			a_img_count_feel = new Image[6];
//			Image img_get = Image.createImage("/game/count_feel.png");
//			for (int i = 0; i < 6; i++) {
//				a_img_count_feel[i] = Image.createImage(img_get, 0, i * 116, 640, 116, 0);
//			}
			a_img_count_number = new Image[7];
			Image img_get = Image.createImage("/game/count.png");
			for (int i = 0; i < 7; i++) {
				a_img_count_number[i] = Image.createImage(img_get, i* 84, 0, 84, 129, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �Ƴ�����ʱͼƬ
	 */
	private void removeCountImage() {
//		for (int i = 0; i < 6; i++) {
//			a_img_count_feel[i] = null;
//		}
//		a_img_count_feel = null;
		for (int i = 0; i < 7; i++) {
			a_img_count_number[i] = null;
		}
		a_img_count_number = null;
		
		System.gc();
	}

	public void loadResource() {
		
	}

	/**
	 * �Ƴ��ӷ��������ص�ͼƬ
	 */
	public void removeServerImage() {
//		canvasControl.playerHandler.stopByIndex(0);
		
		img_map = null;
		img_airship = null;
		img_bar_coin = null;
		img_bar_distance = null;
		img_bar_side = null;
		img_key = null;
		img_number_gura = null;
		img_hero = null;
		img_aerolite = null;
		img_coin = null;
		img_slow = null;
		img_sprint = null;
		img_protected = null;

		for (int i = 0; i < 5; i++) {
			a_img_bullet[i] = null;
		}
		a_img_bullet = null;

		for (int i = 0; i < 4; i++) {
			a_img_treasure[i] = null;
		}
		a_img_treasure = null;
		for (int i = 0; i < 4; i++) {
			a_img_pet[i] = null;
		}
		a_img_pet = null;
		for (int i = 0; i < 4; i++) {
			a_img_attract[i] = null;
		}
		a_img_attract = null;
		for (int i = 0; i < 4; i++) {
			a_img_beHit_bird[i] = null;
		}
		a_img_beHit_bird = null;
		for (int i = 0; i < 4; i++) {
			a_img_sprint[i] = null;
		}
		a_img_sprint = new Image[4];
		for (int i = 0; i < 5; i++) {
			a_img_boom_bird[i] = null;
		}
		a_img_boom_bird = null;
		for (int i = 0; i < 2; i++) {
			a_img_beHit_hero[i] = null;
		}
		a_img_beHit_hero = null;
		for (int i = 0; i < 2; i++) {
			a_img_alert[i] = null;
		}
		a_img_alert = null;
		for (int i = 0; i < 2; i++) {
			a_img_bar_blood[i] = null;
		}
		a_img_bar_blood = null;
		
		for (int i = 0; i < 5; i++) {
			a_2_img_bird[i][0] = null;
			a_2_img_bird[i][1] = null;
		}
		a_2_img_bird = null;
		System.gc();
	}

	public void removeResource() {
		
		System.gc();
	}

	public void keyPressed(int keyCode) {
		hero.keyPressed(keyCode);
		switch (keyCode) {
		case C.KEY_9:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(new Help(canvasControl, 1));
			break;

		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(new Dialog(canvasControl, 1, this));
			break;
		default:
			break;
		}
	}
	
	public void keyReleased(int keyCode) {
		hero.keyReleased(keyCode);
	}

	public void keyRepeated(int keyCode) {
		
	}

	public void logic() {
		if(countdown) {
			time_start ++;
			if(time_start == 10) {
				frame_count_number ++;
				downspeed_y = 0;
				y_number = 0;
			}
			else if(time_start >= 20) {
//				if(time_start == 20){
//					downspeed_y = 0;
//					y_number = 0;
//				}
				if(frame_count_number < 6) 
					frame_count_number ++;
				
//				x_feel_1 -= 100;
				x_feel_2 += 100;
				
				if(x_feel_2 > 1000){
					countdown = false;
					removeCountImage();
				}
			}
			
			if(time_start < 20) {
				downspeed_y += 5;
				y_number += downspeed_y;
				if(y_number >= 200) {
					y_number = 200;
				}
			}
			return;
		}
		size_v = v_bird.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Bird)v_bird.elementAt(i)).logic();
		}
		
		size_v = v_aerolite.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Aerolite)v_aerolite.elementAt(i)).logic();
		}
		
		size_v = v_bullet.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Bullet)v_bullet.elementAt(i)).logic();
		}
		
		size_v = v_coin.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Coin)v_coin.elementAt(i)).logic();
		}
		
		size_v = v_treasure.size();
		for (int i = size_v - 1; i >= 0; i--) {
			((Treasure)v_treasure.elementAt(i)).logic();
		}
		
		hero.logic();
		
		check();
	}
	
	/**
	 * �����ļ���߼�
	 */
	private void check() {
		if(canvasControl.reliving) {
			relive();
			canvasControl.reliving = false;
		}
		
		if(CanvasControl.distance > 100000000)
			length_unit = 100000000;
		else if(CanvasControl.distance > 10000000)
			length_unit = 10000000;
		else if(CanvasControl.distance > 1000000)
			length_unit = 1000000;
		else if(CanvasControl.distance > 100000)
			length_unit = 100000;
		else if(CanvasControl.distance > 10000)
			length_unit = 10000;
		else if(CanvasControl.distance > 1000)
			length_unit = 1000;
		
		if(v_bird.isEmpty())
			createBird();
		
		for (int i = 0; i < 4; i++) {
			if(treasure_info[i][2] > 0) {
				treasure_info[i][2] --;
				if(i == 1) {//���
					if(treasure_info[i][2] < 50) {
						if(hero.speed_fly > 2)
							hero.speed_fly --;
					}
				}
			}
		}
		
		if(trans) {//���ת��������߼�����
			trans_x -= 50;
			
			if(trans_x < 150)
				frame_trans = 4;
			else if(trans_x < 300)
				frame_trans = 3;
			else if(trans_x < 400)
				frame_trans = 2;
			else if(trans_x < 500)
				frame_trans = 1;
			
			if(trans_x <= 320)
				trans_y += trans_speed_y;
			else 
				trans_y -= trans_speed_y;
			
			if(trans_x < 20) {
				trans = false;
				CanvasControl.distance += 500;
//				num_coin -= 100;
				removeFullcoinImage();
			}
		}
		
		createAerolite();
		createTreasure();
	}

	/**
	 * �������ˡ�
	 */
	public void createBird() {
		int type_ctrl = 0;
		if(CanvasControl.distance <= 1000)
			type_ctrl = 1;
		else if (CanvasControl.distance <= 2000)
			type_ctrl = 2;
		else if (CanvasControl.distance <= 3000)
			type_ctrl = 3;
		else if (CanvasControl.distance <= 4000)
			type_ctrl = 4;
		else
			type_ctrl = 5;
		
		for (int i = 0; i < 5; i++) {
			int type_temp = C.R.nextInt(type_ctrl);
			v_bird.addElement(new Bird(170 + 75 * i, 100, this, type_temp));
		}
	}
	
	/**
	 * ������ʯ
	 */
	public void createAerolite() {
		if(CanvasControl.distance % 400 == 0) {//����������ʯ
			for (int i = 0; i < 3; i++) {
				v_aerolite.addElement(new Aerolite(this, hero.x - 80 + i * 80, 0));
			}
		} else if(CanvasControl.distance % 200 == 0) {//����һ����ʯ
			v_aerolite.addElement(new Aerolite(this, hero.x, 0));
		}
	}
	
	/**
	 * ��������
	 */
	public void createTreasure() {
		for (int i = 0; i < 4; i++) {
			if(treasure_info[i][2] <= 0) {
				if(treasure_info[i][0] > 5) {
					treasure_info[i][1] ++;
					if(treasure_info[i][1] > 100){
						treasure_info[i][1] = 0;
						v_treasure.addElement(new Treasure(this, C.R.nextInt(300) + 170, 0, i));
					}
				} else if(treasure_info[i][0] > 0) {
					treasure_info[i][1] ++;
					if(treasure_info[i][1] > 350 - treasure_info[i][0] * 50){
						treasure_info[i][1] = 0;
						v_treasure.addElement(new Treasure(this, C.R.nextInt(300) + 170, 0, i));
					}
				}
			}
		}
	}
	
	/**
	 * ����
	 */
	public void relive() {
		hero = new Hero(this, CanvasControl.type_hero);
	}

	
	/**
	 * 100���ת��Ϊ500����
	 */
	public void coinTransformDistance() {
		trans = true;
		trans_x = 600;
		trans_y = 50;
		trans_speed_y = 5;
		loadFullCoinImage();
	}

	/**
	 * ���ؽ��ת�������Ч��ͼ
	 */
	private void loadFullCoinImage() {
		try {
			a_img_full_coin = new Image[5];
			Image img_get = Image.createImage("/game/full_coin.png");
			for (int i = 0; i < 5; i++) {
				a_img_full_coin[i] = Image.createImage(img_get, 0, i * 46, 104, 46, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �Ƴ����ת�������Ч��ͼ
	 */
	private void removeFullcoinImage() {
		for (int i = 0; i < 5; i++) {
			a_img_full_coin[i] = null;
		}
		a_img_full_coin = null;
		
		System.gc();
	}
	
}
