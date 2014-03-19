package com.dave.jpsc.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.tool.Circle;
import com.dave.jpsc.tool.Rect;
import com.dave.jpsc.tool.RectFree;
import com.dave.jpsc.tool.Vector2;
import com.dave.jpsc.view.Game;

public class Car implements Model {

	/**
	 * ������Ϸ
	 */
	protected Game game;

	/**
	 * 0~8,���ֳ���
	 */
	private int type;

	private Image[] a_img_car;
	
	/**
	 * ״̬
	 */
	public int state;

	/**
	 * ��������
	 */
	public int x, y;
	
	/**
	 * ���ڲ�����Ĭ��Ϊ0��Ͳ�
	 */
	public int floor;

	/**
	 * Ԥ��ײ���Σ��ȼ��þ��Σ��þ�����ײ���ټ����ʵ���Σ�
	 */
	public Rect advRect;

	/**
	 * ��ײ���ɾ���
	 */
	public RectFree rectFree;

	/**
	 * ��ײ���εĿ�
	 */
	public int w_rect;

	/**
	 * ��ײ���εĸ�
	 */
	public int h_rect;

	/**
	 * �ƶ��ٶ�(ÿ֡�ƶ�����)
	 */
	public int speed = 0;

	/**
	 * ��ײǰ���ٶ�
	 */
	public int speed_old = 0;

	/**
	 * ��һ֡���ٶ�
	 */
	public int speed_lastFrame = 0;

	/**
	 * ����ٶ�
	 */
	public int speedMax;

	/**
	 * ��ǰ����
	 */
	public int oil;

	/**
	 * �������
	 */
	public int oilMax;

	/**
	 * �������Ʊ���
	 */
	private int oilCtrl = 10;

	/**
	 * ��ǰ���ڵڼ�Ȧ
	 */
	public int lap;

	/**
	 * �������ٶ���������(ÿ֡���ӵ��ٶ�)
	 */
	public int accelPerFrame;

	/**
	 * �������ٶȵ�С�����֣�������֡����һ���ٶȣ�
	 */
	public int accelPerPx;

	/**
	 * ���ٶ�С�����ֵ�֡������
	 */
	public int accelFrameCtrl = 0;

	/**
	 * ����0~359��
	 */
	public float dir_angle;

	/**
	 * ���ڵ���������
	 */
	public RectFree rect_track;

	/**
	 * �����±� 0~15 ����0��22��45��68��90�������ȣ�
	 */
	public int dir_index;

	/**
	 * x,y����ÿ֡�ƶ����������루��λ�����أ�
	 */
	public int x_distn_frame, y_distn_frame;

	/**
	 * x,y����С���ƶ����루������֡�ƶ�һ�����أ���
	 */

	public int x_onePixe_frame, y_onePixe_frame;

	/**
	 * �����ƶ����ڼ�֡�ƶ�һ������
	 */
	private int x_moveCtrl, y_moveCtrl;

	/**
	 * ���ڴ����յ���
	 */
	public boolean passingFinLine;
	
	/**
	 * ���ڴ�����������������
	 */
	public boolean passingUpFloorLine;
	
	/**
	 * ���ڴ��������½�������
	 */
	public boolean passingDownFloorLine;

	/**
	 * ������������ʱ�ķ���0��Ϊ��ȷ����1��Ϊ������
	 */
	private int passingDir;

	/**
	 * ����ģʽ
	 */
	public boolean fastMode;

	/**
	 * ����ģʽЧ������֡
	 */
	private int fastFrame;

	/**
	 * ����ģʽʣ��ʱ��
	 */
	private int fastTime;

	/**
	 * �������ʱ���¼
	 */
	private int erroDirTime;

	/**
	 * �������״̬
	 */
	public boolean dirErro;

	/**
	 * ·�̣������������ϵ�·��
	 */
	public int mileage;

	/**
	 * ����
	 */
	public int rank;

	/**
	 * �����ٹ����󣬼���Ч��ʣ��ʱ��
	 */
	public int slowTime;

	/**
	 * ��ä����ʣ��ʱ��
	 */
	public int blindTime;

	/**
	 * ����ļ�ͷ�İ�����Ƶ�ʿ���
	 */
	private int keyLeftFreq;

	/**
	 * ����������Ĵ���
	 */
	public int keyLeftTimes;
	
	/**
	 * �Ҽ�Ƶ��
	 */
	private int keyRightFreq;

	/**
	 * �������Ҽ��Ĵ���
	 */
	public int keyRightTimes;

	/**
	 * ����Ư��
	 */
	public boolean perfect;

	/**
	 * ���
	 */
	private Player player;

	/**
	 * ��äͼƬ֡
	 */
	private int frame_blind;

	/**
	 * ��������
	 */
	private int isCovered = -1;

	/**
	 * ���ú���˸Ч��
	 */
	private boolean blink;

	/**
	 * ��˸ʱ��
	 */
	private int blinkTime;

	/**
	 * ײ��ǽ��
	 */
	protected boolean hitWall;

	/**
	 * ��һ֡������
	 */
	private int x_last, y_last;

	/**
	 * ԭʼ����ٶ�
	 */
	public int speedMax_Org;

	private int y_point;

	/**
	 * ������ʾʱ��
	 */
	private int dodgeTime;
	
	/**
	 * ������ʾ��x����
	 */
	private int x_dodge;

	/**
	 * ������ʾ��y����
	 */
	private int y_dodge;

	/**
	 * Ϩ���
	 */
	public static final int STATE_STOPED = 1;

	/**
	 * ������
	 */
	public static final int STATE_LANCHED = 2;

	/**
	 * ͣ�õ�
	 */
	public static final int STATE_PARKING = 3;

	public static final String[] NAME = { "�����ܳ�", "����ԽҰ��", "ѩ���������ܳ�", "�����������",
			"��ɣ��������", "��ŵ����ʽ����", "��ʱ��β������", "����sl", "���Դ�����ʽ����" };

	public Car(Game game, int x, int y, float dir_angle, int type) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.dir_angle = dir_angle;
		this.type = type;
		y_point = y - 10;

		init();
	}
	
	public Car(Game game, int x, int y, int dir_angle, int type, Player user) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.dir_angle = dir_angle;
		this.type = type;
		this.player = user;
		y_point = y - 10;

		init();
	}

	public Car(Game game, int x, int y, int dir_angle, Player user) {
		this(game, x, y, dir_angle, user.cars[user.mainCar][0], user);
	}
	
	public Car(Game game, int x, int y, int dir_angle, int type, int floor) {
		this(game, x, y, dir_angle, type);
		this.floor = floor;
	}

	public Car(Game game, int x, int y, int dir_angle, Player user, int floor) {
		this(game, x, y, dir_angle, user);
		this.floor = floor;
	}
	
	public Car(Game game, int x, int y, int dir_angle, int type, Player user, int floor) {
		this(game, x, y, dir_angle, type, user);
		this.floor = floor;
	}

	private void init() {
		try {
			a_img_car = new Image[4];
			for (int i = 0; i < a_img_car.length; i++) {
				a_img_car[i] = Image.createImage("/car/" + type + "_car_" + i
						+ ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(player != null) {
			speedMax_Org = player.carRealParams[CanvasControl.usingCar][0] / 30 + 4;
			oilMax = player.carRealParams[CanvasControl.usingCar][3];
			
			accelPerFrame = player.carRealParams[CanvasControl.usingCar][1] / 50;
			int accel_re = player.carRealParams[CanvasControl.usingCar][1] % 50;
			accelPerPx = accel_re > 0 ? 50 / accel_re : 0;
		} else {
			speedMax_Org = CanvasControl.carProperty[type][0] / 30 + 4;
			oilMax = CanvasControl.carProperty[type][3];
			
			accelPerFrame = CanvasControl.carProperty[type][1] / 50;
			int accel_re = CanvasControl.carProperty[type][1] % 50;
			accelPerPx = accel_re > 0 ? 50 / accel_re : 0;
		}
		speedMax = speedMax_Org;
		oil = oilMax;
		createRect();
		updateRectTrack();
		
		lanch();
	}

	/**
	 * ������ײ����
	 */
	private void createRect() {
		w_rect = 40;
		h_rect = 18;
		rectFree = new RectFree(w_rect, h_rect, new Vector2(x, y), (int)dir_angle);
//
//		rectFree = new RectFree(new Vector2(x - w_rect / 2, y - h_rect / 2),
//				new Vector2(x + w_rect / 2, y - h_rect / 2), new Vector2(x
//						+ w_rect / 2, y + h_rect / 2), new Vector2(x - w_rect
//						/ 2, y + h_rect / 2), new Vector2(x, y));

		advRect = new Rect(x - 22, y - 22, 44, 44);
	}

	public void show(Graphics g) {
		if(slowTime > 0)
			showSlow(g);
		
		if(blink) {
			if(blinkTime % 3 != 0)
				showCar(g);
		} else {
			showCar(g);
		}
		
		if(dodgeTime > 0)
			g.drawImage(game.img_dodge, x_dodge + game.x_map, y_dodge + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
		
		if(blindTime > 0)
			showBlind(g);
		
		if(this == game.myCar) {
			y_point = y_point == y - 10 ? y - 12 : y - 10;
			g.drawImage(game.img_point, x + game.x_map, y_point + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
		}
		
		if(isCovered != -1) {
			g.drawImage(game.a_img_cover[isCovered], game.cover_point[isCovered * 2] + game.x_map, game.cover_point[ 1 + isCovered * 2] + game.y_map, Graphics.VCENTER | Graphics.HCENTER);
		}

		// g.drawImage(img_car, x + game.x_map, y + game.y_map, Graphics.HCENTER
		// | Graphics.VCENTER);
		// g.drawRGB(byte_img_car_bak, 0, img_car_bak.getWidth(), x, y,
		// img_car_bak.getWidth(), img_car_bak.getHeight(), false);

		// ////////////////////////////������Ϣ///////////////////////////////////
		if (CanvasControl.DEBUG) {
			g.setColor(0xffffff);
			g.fillRect(x - 30 + game.x_map, y - 40 + game.y_map, 60, 20);
			g.setColor(0);
			// g.drawString("dir:" + dir_angle, x - 20 + game.x_map, y - 40
			// + game.y_map, 0);
			g.drawString("ʵ��Ȧ��:" + lap, x - 20 + game.x_map, y - 60
					+ game.y_map, 0);
			g.drawString("·��:" + mileage, x - 20 + game.x_map, y - 40
					+ game.y_map, 0);
			g.drawString("�ڼ���:" + floor, x - 20 + game.x_map, y - 20
					+ game.y_map, 0);
			g.setColor(255, 0, 0);
			rectFree.show(game.x_map, game.y_map, g);
		}
	}

	private void showCar(Graphics g) {
		if (dir_angle < 12) {
			g.drawRegion(a_img_car[0], 0, 0, a_img_car[0].getWidth(),
					a_img_car[0].getHeight(), Sprite.TRANS_ROT180, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode)
				g.drawRegion(game.a_img_speed_up[0][fastFrame], 0, 0,
						game.a_img_speed_up[0][fastFrame].getWidth(),
						game.a_img_speed_up[0][fastFrame].getHeight(),
						Sprite.TRANS_ROT180, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
		} else if (dir_angle < 34) {
			g.drawRegion(a_img_car[1], 0, 0, a_img_car[1].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT180, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[1][fastFrame], 0, 0,
						game.a_img_speed_up[1][fastFrame].getWidth(),
						game.a_img_speed_up[1][fastFrame].getHeight(),
						Sprite.TRANS_ROT180, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 56) {
			g.drawRegion(a_img_car[2], 0, 0, a_img_car[2].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT180, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[2][fastFrame], 0, 0,
						game.a_img_speed_up[2][fastFrame].getWidth(),
						game.a_img_speed_up[2][fastFrame].getHeight(),
						Sprite.TRANS_ROT180, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 80) {
			g.drawRegion(a_img_car[3], 0, 0, a_img_car[3].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT180, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[3][fastFrame], 0, 0,
						game.a_img_speed_up[3][fastFrame].getWidth(),
						game.a_img_speed_up[3][fastFrame].getHeight(),
						Sprite.TRANS_ROT180, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 102) {
			g.drawRegion(a_img_car[0], 0, 0, a_img_car[0].getWidth(),
					a_img_car[0].getHeight(), Sprite.TRANS_ROT270, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode)
				g.drawRegion(game.a_img_speed_up[0][fastFrame], 0, 0,
						game.a_img_speed_up[0][fastFrame].getWidth(),
						game.a_img_speed_up[0][fastFrame].getHeight(),
						Sprite.TRANS_ROT270, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
		} else if (dir_angle < 124) {
			g.drawRegion(a_img_car[1], 0, 0, a_img_car[1].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT270, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[1][fastFrame], 0, 0,
						game.a_img_speed_up[1][fastFrame].getWidth(),
						game.a_img_speed_up[1][fastFrame].getHeight(),
						Sprite.TRANS_ROT270, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 146) {
			g.drawRegion(a_img_car[2], 0, 0, a_img_car[2].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT270, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[2][fastFrame], 0, 0,
						game.a_img_speed_up[2][fastFrame].getWidth(),
						game.a_img_speed_up[2][fastFrame].getHeight(),
						Sprite.TRANS_ROT270, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		}

		else if (dir_angle < 170) {
			g.drawRegion(a_img_car[3], 0, 0, a_img_car[3].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT270, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[3][fastFrame], 0, 0,
						game.a_img_speed_up[3][fastFrame].getWidth(),
						game.a_img_speed_up[3][fastFrame].getHeight(),
						Sprite.TRANS_ROT270, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 192) {
			g.drawImage(a_img_car[0], x + game.x_map, y + game.y_map,
					Graphics.HCENTER | Graphics.VCENTER);

			if (fastMode) {
				g.drawImage(game.a_img_speed_up[0][fastFrame], x + game.x_map,
						y + game.y_map, Graphics.HCENTER | Graphics.VCENTER);
				
			}
		} else if (dir_angle < 214) {
			g.drawImage(a_img_car[1], x + game.x_map, y + game.y_map,
					Graphics.HCENTER | Graphics.VCENTER);

			if (fastMode) {
				g.drawImage(game.a_img_speed_up[1][fastFrame], x + game.x_map,
						y + game.y_map, Graphics.HCENTER | Graphics.VCENTER);
			}
			
		} else if (dir_angle < 236) {
			g.drawImage(a_img_car[2], x + game.x_map, y + game.y_map,
					Graphics.HCENTER | Graphics.VCENTER);

			if (fastMode) {
				g.drawImage(game.a_img_speed_up[2][fastFrame], x + game.x_map,
						y + game.y_map, Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 260) {
			g.drawImage(a_img_car[3], x + game.x_map, y + game.y_map,
					Graphics.HCENTER | Graphics.VCENTER);

			if (fastMode) {
				g.drawImage(game.a_img_speed_up[3][fastFrame], x + game.x_map,
						y + game.y_map, Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 282) {
			g.drawRegion(a_img_car[0], 0, 0, a_img_car[0].getWidth(),
					a_img_car[0].getHeight(), Sprite.TRANS_ROT90, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[0][fastFrame], 0, 0,
						game.a_img_speed_up[0][fastFrame].getWidth(),
						game.a_img_speed_up[0][fastFrame].getHeight(),
						Sprite.TRANS_ROT90, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 304) {
			g.drawRegion(a_img_car[1], 0, 0, a_img_car[1].getWidth(),
					a_img_car[1].getHeight(), Sprite.TRANS_ROT90, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[1][fastFrame], 0, 0,
						game.a_img_speed_up[1][fastFrame].getWidth(),
						game.a_img_speed_up[1][fastFrame].getHeight(),
						Sprite.TRANS_ROT90, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 326) {
			g.drawRegion(a_img_car[2], 0, 0, a_img_car[2].getWidth(),
					a_img_car[2].getHeight(), Sprite.TRANS_ROT90, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[2][fastFrame], 0, 0,
						game.a_img_speed_up[2][fastFrame].getWidth(),
						game.a_img_speed_up[2][fastFrame].getHeight(),
						Sprite.TRANS_ROT90, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else if (dir_angle < 350) {
			g.drawRegion(a_img_car[3], 0, 0, a_img_car[3].getWidth(),
					a_img_car[3].getHeight(), Sprite.TRANS_ROT90, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[3][fastFrame], 0, 0,
						game.a_img_speed_up[3][fastFrame].getWidth(),
						game.a_img_speed_up[3][fastFrame].getHeight(),
						Sprite.TRANS_ROT90, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		} else {
			g.drawRegion(a_img_car[0], 0, 0, a_img_car[0].getWidth(),
					a_img_car[0].getHeight(), Sprite.TRANS_ROT180, x
							+ game.x_map, y + game.y_map, Graphics.HCENTER
							| Graphics.VCENTER);

			if (fastMode) {
				g.drawRegion(game.a_img_speed_up[0][fastFrame], 0, 0,
						game.a_img_speed_up[0][fastFrame].getWidth(),
						game.a_img_speed_up[0][fastFrame].getHeight(),
						Sprite.TRANS_ROT180, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
		}
	}

	/**
	 * ��ʾ��äЧ��
	 * @param g
	 */
	private void showBlind(Graphics g) {
		g.drawImage(game.a_img_car_blind[frame_blind], x + game.x_map, y + game.y_map - 20, Graphics.VCENTER | Graphics.HCENTER);
	}

	/**
	 * ��ʾ����Ч��
	 * @param g
	 */
	private void showSlow(Graphics g) {
		g.drawImage(game.img_car_slow, x + game.x_map, y + game.y_map, Graphics.VCENTER | Graphics.HCENTER);
	}

	/**
	 * �˶������ļ���
	 */
	private void moveComput() {

		int x_dtn = 0, y_dtn = 0;
		x_dtn = speed * C.cos((int) dir_angle) / 1000;// ÿ֡�ƶ���x��������һ�ٱ�
		y_dtn = speed * C.sin((int) dir_angle) / 1000;// ÿ֡�ƶ���y��������һ�ٱ�
		x_distn_frame = x_dtn / 100;
		y_distn_frame = y_dtn / 100;
		int x_re = x_dtn % 100;
		int y_re = y_dtn % 100;

		if (x_re != 0)
			x_onePixe_frame = 100 / x_re;
		else
			x_onePixe_frame = 0;

		if (y_re != 0)
			y_onePixe_frame = 100 / y_re;
		else
			y_onePixe_frame = 0;

		x_moveCtrl = 0;
		y_moveCtrl = 0;

	}

	/**
	 * ��ת�ҵ�ͼƬ
	 */
	// private void rotateImage() {
	// img_car = Define.rotate(img_car_bak, 90 - dir_angle);
	// }

	public void keyPressed(int keyCode) {
		if (state == STATE_LANCHED) {
			switch (keyCode) {
			case C.KEY_LEFT:
				changeDir(-22.5f);
//				game.canvasControl.playerHandler.playByIndex(2);
				
				keyLeftFreqLogic();
				break;
			case C.KEY_RIGHT:
				changeDir(22.5f);
//				game.canvasControl.playerHandler.playByIndex(2);
				
				keyRightFreqLogic();
				break;
			case C.KEY_FIRE:
				if (dirErro) {
					resetDir();
				}
				break;
			}
		}
	}

	/**
	 * ���ó��ӷ���
	 */
	protected void resetDir() {
		changeDir(rect_track.angle - dir_angle);
		speed = 0;
	}

	/**
	 * ����߼�
	 */
	private void keyLeftFreqLogic() {
		keyRightTimes = 0;
		if(keyLeftFreq > 0)
			keyLeftTimes ++;
		
		if(keyLeftTimes >= 3) {//����Ư��
			if(!perfect) {
				perfect = true;
			}
		}
		keyLeftFreq = 5;
	}
	
	/**
	 * �Ҽ��߼�
	 */
	private void keyRightFreqLogic() {
		keyLeftTimes = 0;
		if(keyRightFreq > 0)
			keyRightTimes ++;
		
		if(keyRightTimes >= 3) {
			if(!perfect) {
				perfect = true;
			}
		}
		
		keyRightFreq = 5;
	}
	

	/**
	 * �ڵ�ǰ�ķ�����ת����ٽǶȵķ���
	 * 
	 * @param angle�Ƕ�ֵ
	 *            ����Ϊ��ʱ�룬��Ϊ˳ʱ��
	 */
	public void changeDir(float angle) {
		rectFree.rotate(angle);
		// if(clsWithWall()) {
		// rectFree.rotate(-angle);
		// return;
		// }
		if(speed > 4) 
			speed -= 2;
		
		float old_dir = dir_angle;
		dir_angle += angle;
		if (dir_angle > 359)
			dir_angle = old_dir + angle - 360;
		else if (dir_angle < 0)
			dir_angle = old_dir + angle + 360;
		// rotateImage();
	}

	/**
	 * �Ƕ��±�ֵ����ָ����ֵ
	 * 
	 * @param value
	 *            0~15 ����0��22��45��68��90�������ȣ�
	 */
	public void addDirIndex(int value) {
		dir_index += value;
		if (dir_index > 15)
			dir_index = dir_index - 16;
		else if (dir_angle < 0)
			dir_index = dir_index + 16;

		setDirByIndex(dir_index);
	}

	/**
	 * �����±�ֵ���÷���
	 * 
	 * @param index
	 */
	private void setDirByIndex(int index) {
		switch (index) {
		case 0:
			dir_angle = 0;
			break;
		case 1:
			dir_angle = 22;
			break;
		case 2:
			dir_angle = 45;
			break;
		case 3:
			dir_angle = 68;
			break;
		case 4:
			dir_angle = 90;
			break;
		case 5:
			dir_angle = 112;
			break;
		case 6:
			dir_angle = 135;
			break;
		case 7:
			dir_angle = 158;
			break;
		case 8:
			dir_angle = 180;
			break;
		case 9:
			dir_angle = 202;
			break;
		case 10:
			dir_angle = 225;
			break;
		case 11:
			dir_angle = 248;
			break;
		case 12:
			dir_angle = 270;
			break;
		case 13:
			dir_angle = 292;
			break;
		case 14:
			dir_angle = 315;
			break;
		case 15:
			dir_angle = 338;
			break;

		default:
			break;
		}
	}

	/**
	 * ͣ��
	 */
	private void stop() {
		game.firstPassEnd++;
		rank = game.firstPassEnd;
		state = STATE_STOPED;
		
		if (game.myCar == this) {//�ؿ�����
			CanvasControl.tarAchieve[0] = false;
			CanvasControl.tarAchieve[1] = false;
			CanvasControl.tarAchieve[2] = false;
			
			if(game.getMode() == Game.MODE_DUAL) {//��սģʽ
				CanvasControl.rankInGame = rank;
				CanvasControl.gameTimeStr = C.computeTimeStr(game.gameTime * Game.TIME_MUL, true);
				
				if (game.useGoodsTimes >= 3)
					CanvasControl.tarAchieve[1] = true;
				
				if (CanvasControl.rankInGame == 1) {
					CanvasControl.passSuc = 0;
					CanvasControl.tarAchieve[0] = true;
					CanvasControl.tarAchieve[2] = true;
					game.handleSuccess();
				} else {//ʧ��
					CanvasControl.passSuc = 1;
					game.handleFailed();
				}
				game.loadAchieveImg();
				
				game.setState(Game.STATE_ACHIEVE);
			} else {//�ؿ�ģʽ
				CanvasControl.rankInGame = rank;
				CanvasControl.gameTimeStr = C.computeTimeStr(game.gameTime * Game.TIME_MUL, true);
				if (game.gameTime * Game.TIME_MUL / 10 <= CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][5])
					CanvasControl.tarAchieve[2] = true;
				if (game.useGoodsTimes >= 3)
					CanvasControl.tarAchieve[1] = true;
				
				if (CanvasControl.rankInGame <= CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][2]) {//�ɹ�
					CanvasControl.passSuc = 0;
					CanvasControl.tarAchieve[0] = true;
					game.handleSuccess();
				} else {//ʧ��
					CanvasControl.passSuc = 1;
					game.handleFailed();
				}
				game.loadAchieveImg();
				
				game.setState(Game.STATE_ACHIEVE);
			}
		}
	}

	/**
	 * ����
	 */
	private void lanch() {
		state = STATE_LANCHED;
	}

	public void keyReleased(int keyCode) {

	}

	public void logic() {

		if (state == STATE_LANCHED) {
			speed_lastFrame = speed;
			
			speedCtrl();

			move();
			eatTreasure();
			updateRectTrack();
			computeMileage();
			updateRank();
			dirCrl();
			if (clsWithWall())
				hittingWall();
			
			if(game.coverRects != null)
				underCoverCls();
			
			if(keyLeftFreq > 0) {
				keyLeftFreq --;
				if(keyLeftFreq <= 0)
					keyLeftTimes = 0;
			}
			if(keyRightFreq > 0) {
				keyRightFreq --;
				if(keyRightFreq <= 0)
					keyRightTimes = 0;
			}
			
			if (fastMode)
				fastCtrl();
			
			if(slowTime > 0)
				slowCtrl();
			
			if(blink)
				blinkCtrl();
			
			if(blindTime > 0) {
				blindTime --;
				frame_blind ++;
				if(frame_blind >= 2)
					frame_blind = 0;
								
			}
			
			if(dodgeTime > 0) {
				dodgeTime --;
				x_dodge = x;
				y_dodge = y - (10 - dodgeTime) * 2;
			}

			if(game.myCar == this)
				oilConsu();

			floorLogic();
			finishLogic();
		} else if (state == STATE_STOPED) {
			if (clsWithWall())
				hittingWall();
			
			slowDown();
			move();
		}

	}

	/**
	 * �Ա���
	 */
	private void eatTreasure() {
		for (int i = game.treasures.size() - 1; i >= 0; i--) {
			Treasure treasure = ((Treasure) game.treasures.elementAt(i));
			if(C.rectInsect(advRect, treasure.rect)) {
				if(C.freeRectIsct(rectFree, treasure.rect)) {//�Ե�����
					treasure.beEta();
					handleTreasure(treasure.type);
				}
			}
		}
	}

	/**
	 * ����Ե��ı���
	 * @param treType ���������
	 */
	private void handleTreasure(int treType) {
		switch (treType) {
		case 9:// ���״̬
			removeHarmful();
			break;
		case 10:// ����ʱ��
			if(this == game.myCar) {
				game.remainTime = CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][3] * 10;
			}
			break;
		case 11:// ����ģʽ
			fast();
			break;
		case 12:// ����ȼ��
			if(this == game.myCar) {
				oil = oilMax;
			}
			break;
		case 13:// ���ٹ���
			if(this == game.myCar) {                    
				game.slowAttack();
			} else {
				game.bullets.addElement(new Bullet(game, x, y, 0, game.myCar));
			}
			break;
		case 14:// ��ä����
			if(this == game.myCar) {                    
				game.blindAttack();
			} else {
				game.bullets.addElement(new Bullet(game, x, y, 1, game.myCar));
			}
			break;
		}
	}

	private void blinkCtrl() {
		blinkTime ++;
		
		if(blinkTime > 30) {
			blinkTime = 0;
			blink = false;
		}
	}

	/**
	 * �����ٹ������У����ٿ����߼�
	 */
	private void slowCtrl() {
		slowTime --;
		
		if(slowTime <= 0)
			speedMax = speedMax_Org;
	}

	/**
	 * �����ˣ�����
	 */
	private void slowDown() {
		speed--;

		if (speed <= 0) {
			speed = 0;
			state = STATE_PARKING;
		}
	}

	/**
	 * �������������½��߼�����
	 */
	private void floorLogic() {
		if(game.num_cover > 0) {
			if(!passingUpFloorLine) {
				if(C.rectInsect(rectFree.getBoundingRect(), game.upFloorRect.getBoundingRect())) {
					if(C.freeRectIsct(rectFree, game.upFloorRect)) {
						if(getSpeedVector().dotProduct(game.upFloorRect.getDirVector()) > 0) {//��ȷ����
							passingDir = 0;
						} else {
							passingDir = 1;
						}
						passingUpFloorLine = true;
					}
				}
			} else {
				if(!C.freeRectIsct(rectFree, game.upFloorRect)) {
					passingUpFloorLine = false;
					if(passingDir == 0 && getSpeedVector().dotProduct(game.upFloorRect.getDirVector()) > 0) {//��ȷ����
						floor ++;
					} else if (passingDir == 1 && getSpeedVector().dotProduct(game.upFloorRect.getDirVector()) < 0){
						floor --;
					}
				}
			}
			
			if(!passingDownFloorLine) {
				if(C.rectInsect(rectFree.getBoundingRect(), game.downFloorRect.getBoundingRect())) {
					if(C.freeRectIsct(rectFree, game.downFloorRect)) {
						if(getSpeedVector().dotProduct(game.downFloorRect.getDirVector()) > 0) {//��ȷ����
							passingDir = 0;
						} else {
							passingDir = 1;
						}
						passingDownFloorLine = true;
					}
				}
			} else {
				if(!C.freeRectIsct(rectFree, game.downFloorRect)) {
					passingDownFloorLine = false;
					if(passingDir == 0 && getSpeedVector().dotProduct(game.downFloorRect.getDirVector()) > 0) {//��ȷ����
						floor --;
					} else if (passingDir == 1 && getSpeedVector().dotProduct(game.downFloorRect.getDirVector()) < 0){
						floor ++;
					}
				}
			}
		}
	}
	
	/**
	 * �յ㴦��
	 */
	private void finishLogic() {
		if (!passingFinLine) {
			if (C.freeRectIsct(rectFree, game.finishRect)) {
				if (erroDirTime > 0) {
					passingDir = 1;
					System.out.println("���ڴ�Խ�յ���---������");
				} else {
					passingDir = 0;
					System.out.println("���ڴ�Խ�յ���---��ȷ����");
				}
				passingFinLine = true;
			}
		} else {
			if (!C.freeRectIsct(rectFree, game.finishRect)) {
				passingFinLine = false;
				if (passingDir == 0 && erroDirTime <= 0) {// ��ȷ����ͨ���յ���
					System.out.println("�Ѿ������յ���---��ȷ����");
//					if (lap < CanvasControl.MISSIONPROPERTY[CanvasControl.mission - 1][0]) {
					if (lap < game.lapMax) {
						if(lap > 0) {
							if(mileage > lap * 170000)
								lap++;
						} else {
							lap++;
						}
					} else if(mileage > lap * 170000) {// ����
						stop();
					}
				} else if (passingDir == 1 && erroDirTime > 0) {// ������ͨ���յ���
					System.out.println("�Ѿ������յ���---������");
					lap--;
				}
			}
		}
	}

	/**
	 * �����������
	 */
	private void updateRank() {
		rank = game.amount_car;

		if (this != game.myCar) {
			if (mileage > game.myCar.mileage)
				rank--;
		}

		for (int i = game.enemyCars.size() - 1; i >= 0; i--) {
			EnemyCar enemyCar = ((EnemyCar) game.enemyCars.elementAt(i));
			if (enemyCar == this) {
				continue;
			} else {
				if (mileage > enemyCar.mileage)
					rank--;
			}
		}
	}

	/**
	 * ��������߼�
	 */
	public void dirCrl() {
		float minus = dir_angle - rect_track.angle;
		if ((minus > 90 && minus < 270) || (minus < -90 && minus > -270)) {// ������
			erroDirTime++;
			if (!dirErro && erroDirTime > 20) {// ��ʾ�������
				
				dirErro = true;
				
				if(this == game.myCar) {
					game.initDirErro();
				}
			}
		} else if (erroDirTime > 0) {
			erroDirTime = 0;
			if (dirErro) {
				dirErro = false;
			}
		}
	}

	/**
	 * ��������·�̣�����ÿһ֡�ڵ�ǰ�����������
	 */
	public void computeMileage() {
		float angle = dir_angle - rect_track.angle;
		if (angle > 180) {
			angle = 360 - angle;
		} else if (angle < -180) {
			angle = 360 + angle;
		} else if (angle < 0) {
			angle = -angle;
		}
		int mileage_frame = speed * C.cos((int) angle) / 1000;
		mileage += mileage_frame;
	}

	/**
	 * ����������������
	 */
	private void updateRectTrack() {
		if(floor == 0) {
			for (int i = game.dirRects.size() - 1; i >= 0; i--) {
				RectFree rectFree = (RectFree) game.dirRects.elementAt(i);
				if (C.pointInFR(new Vector2(x, y), rectFree)) {
					rect_track = rectFree;
					return;
				}
			}
			if(rect_track == null)
				rect_track = (RectFree) game.dirRects.elementAt(0);
				
		} else if(floor == 1) {
			for (int i = game.dirRects_f1.size() - 1; i >= 0; i--) {
				RectFree rectFree = (RectFree) game.dirRects_f1.elementAt(i);
				if (C.pointInFR(new Vector2(x, y), rectFree)) {
					rect_track = rectFree;
					return;
				}
			}
			
			if(rect_track == null)
				rect_track = (RectFree) game.dirRects_f1.elementAt(0);
		}
	}

	/**
	 * ��������
	 */
	private void oilConsu() {
		oilCtrl--;
		if(oil <= 0) {//ȼ�ͺľ�
			game.fuelOut();
		}
		if (oilCtrl <= 0) {
			oilCtrl = 10;
			oil--;
		}
	}

	/**
	 * ����ģʽ����
	 */
	private void fastCtrl() {
		fastTime--;
		if (fastTime < 0) {
			fastTime = 0;
			fastMode = false;
			speedMax = speedMax_Org;
		}

		fastFrame++;
		if (fastFrame > 3)
			fastFrame = 0;
	}

	/**
	 * �ٶȿ��ƣ����ٺͼ���
	 */
	private void speedCtrl() {
		if (speed < speedMax) {
			speed += accelPerFrame;

			accelFrameCtrl++;
			if (accelPerPx > 0 && accelFrameCtrl >= accelPerPx) {
				accelFrameCtrl = 0;
				speed++;
			}
			if (speed > speedMax)
				speed = speedMax;
		} else if (speed > speedMax) {// ����
			speed--;
		}
	}

	private void move() {
		moveComput();
		
		x_last = x;
		y_last = y;

		x += x_distn_frame;
		y += y_distn_frame;

		advRect.x += x_distn_frame;
		advRect.y += y_distn_frame;
		rectFree.trasle(new Vector2(x_distn_frame, y_distn_frame));
		if (y_onePixe_frame != 0) {
			y_moveCtrl++;
			int y_abs = Math.abs(y_onePixe_frame);
			if (y_moveCtrl >= y_abs) {
				y_moveCtrl = 0;
				y += y_onePixe_frame / y_abs;
				advRect.y += y_onePixe_frame / y_abs;
				rectFree.trasle(new Vector2(0, y_onePixe_frame / y_abs));
			}
		}

		if (x_onePixe_frame != 0) {
			x_moveCtrl++;
			int x_abs = Math.abs(x_onePixe_frame);
			if (x_moveCtrl >= x_abs) {
				x_moveCtrl = 0;
				x += x_onePixe_frame / x_abs;
				advRect.x += x_onePixe_frame / x_abs;

				rectFree.trasle(new Vector2(x_onePixe_frame / x_abs, 0));
			}
		}

	}

	public void fire() {
		int fireType = C.R.nextInt(2);
		if(fireType == 1) {
			game.bullets.addElement(new Bullet(game, x, y, 1, game.myCar));
//			game.myCar.beBlindHitted();
		} else {
			game.bullets.addElement(new Bullet(game, x, y, 0, game.myCar));
//			game.myCar.beSlowHitted();
		}
	}

	/**
	 * �Ƿ���븲�Ǿ��η�Χ
	 */
	private void underCoverCls() {
		if(isCovered == -1 && floor == 0) {
			int covers = game.coverRects.length;
			for (int i = 0; i < covers; i++) {
				if (C.rectInsect(game.coverRects[i].getBoundingRect(), advRect)) {
					if (C.freeRectIsct(rectFree, game.coverRects[i])) {
						isCovered = i;
					}
				}
			}
		}
		
		if(isCovered != -1 && floor == 0) {
			if (!C.freeRectIsct(rectFree, game.coverRects[isCovered])) {
				isCovered = -1;
			}
		}
	}
	
	/**
	 * ��ǽ����ײ���
	 * 
	 * @return
	 */
	public boolean clsWithWall() {
		if(floor == 0) {
			for (int i = game.rects.size() - 1; i >= 0; i--) {
				Rect rect = (Rect) game.rects.elementAt(i);
				if (C.rectInsect(rect, advRect)) {
					if (C.freeRectIsct(rectFree, rect)) {
						if (CanvasControl.DEBUG)
							System.out.println("������λ�����ײ�ˣ����α�ţ�" + i);
						return true;
					}
				}
			}
			
			for (int i = game.freeRects.size() - 1; i >= 0; i--) {
				RectFree rectF_wall = (RectFree) game.freeRects.elementAt(i);
				if (C.rectInsect(rectF_wall.getBoundingRect(), advRect)) {
					if (C.freeRectIsct(rectFree, rectF_wall)) {
						if (CanvasControl.DEBUG)
							System.out.println("�������ɾ��ξ��λ�����ײ�ˣ����α�ţ�" + i);
						return true;
					}
				}
			}
			
			for (int i = game.circles.size() - 1; i >= 0; i--) {
				Circle circle = (Circle) game.circles.elementAt(i);
				if (C.ciclFreeRectInst(circle, rectFree)) {
					if (CanvasControl.DEBUG)
						System.out.println("����Բ�λ�����ײ�ˣ�Բ�ı�ţ�" + i);
					return true;
				}
			}
		} else if(floor == 1) {
			for (int i = game.rects_f1.size() - 1; i >= 0; i--) {
				Rect rect = (Rect) game.rects_f1.elementAt(i);
				if (C.rectInsect(rect, advRect)) {
					if (C.freeRectIsct(rectFree, rect)) {
						if (CanvasControl.DEBUG)
							System.out.println("����area1���λ�����ײ�ˣ����α�ţ�" + i);
						return true;
					}
				}
			}
			
			for (int i = game.freeRects_f1.size() - 1; i >= 0; i--) {
				RectFree rectF_wall = (RectFree) game.freeRects_f1.elementAt(i);
				if (C.rectInsect(rectF_wall.getBoundingRect(), advRect)) {
					if (C.freeRectIsct(rectFree, rectF_wall)) {
						if (CanvasControl.DEBUG)
							System.out.println("����area1���ɾ��ξ��λ�����ײ�ˣ����α�ţ�" + i);
						return true;
					}
				}
			}
			
			for (int i = game.circles_f1.size() - 1; i >= 0; i--) {
				Circle circle = (Circle) game.circles_f1.elementAt(i);
				if (C.ciclFreeRectInst(circle, rectFree)) {
					if (CanvasControl.DEBUG)
						System.out.println("����area1Բ�λ�����ײ�ˣ�Բ�ı�ţ�" + i);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * ײǽ
	 */
	public void hittingWall() {
		keyLeftTimes = 0;
		keyRightTimes = 0;
		
		hitWall = true;
		
		speed_old = speed;
		speed -= 4;
		// speed = 0;
		if (speed < 0)
			speed = 0;

		doAfterHit();
	}

	/**
	 * ��ײ֮��Ĵ���
	 */
	private void doAfterHit() {
		translation(new Vector2(speed_old, 0));
		if (clsWithWall()) {
			translation(new Vector2(-speed_old * 2, 0));
			if (clsWithWall()) {
				translation(new Vector2(speed_old, speed_old));
				if (clsWithWall()) {
					translation(new Vector2(0, -speed_old * 2));
					if(clsWithWall()) {
						resetPositon();
					}
				}
			}
		}
	}

	/**
	 * ��������ȥ��ʱ������
	 */
	private void resetPositon() {
		blink = true;
		translation(rect_track.center.minus(new Vector2(x, y)));
		resetDir();
	}

	/**
	 * ƽ��
	 * 
	 * @param units
	 */
	public void translation(Vector2 v_units) {
		x = (int) (x + v_units.x);
		y = (int) (y + v_units.y);

		advRect.x += v_units.x;
		advRect.y += v_units.y;

		rectFree.trasle(v_units);
	}

	/**
	 * ���뼱��ģʽ
	 */
	public void fast() {
		fastTime += 80;
		if(!fastMode) {
			speedMax = speedMax_Org * 2 > 15 ? 15 : speedMax_Org * 2;
			fastMode = true;
		}
	}

	/**
	 * ��ȡ�������ٶ�����
	 * 
	 * @return
	 */
	public Vector2 getSpeedVector() {
		return new Vector2(speed * C.cos((int) dir_angle) / 100000, speed
				* C.sin((int) dir_angle) / 100000);
	}

	/**
	 * ��ȡ������λ������
	 * 
	 * @return
	 */
	public Vector2 getPointVector() {
		return new Vector2(x, y);
	}

	/**
	 * �����ٹ�����
	 */
	public void beSlowHitted() {
		if(this == game.myCar) {
			if(C.R.nextInt(130) < 130 - game.canvasControl.me.cars[CanvasControl.usingCar][2]) {
				if(slowTime <= 0) {
					speedMax = (speedMax_Org >> 1);
				}
				slowTime += 150;
			} else {//����
				dodge();
			}
		} else {
			if(slowTime <= 0) {
				speedMax = (speedMax_Org >> 1);
			}
			slowTime += 150;
		}
	}


	/**
	 * ����ä����������
	 */
	public void beBlindHitted() {
		if(this == game.myCar) {
			if(C.R.nextInt(130) < 130 - game.canvasControl.me.cars[CanvasControl.usingCar][1])
				blindTime += 70;
			 else //����
				dodge();
		} else {
			blindTime += 70;
		}
	}
	
	/**
	 * ����
	 */
	private void dodge() {
		dodgeTime = 10;
		
		x_dodge = x;
		y_dodge = y;
	}

	/**
	 * �ص���һ֡��λ��
	 */
	public void gobackLastPosition() {
		translation(new Vector2(x_last - x, y_last - y));
	}

	/**
	 * ����1���Ƴ��к�״̬
	 */
	public void removeHarmful() {
		if(blindTime > 0)
			blindTime = 0;
		else if(slowTime > 0) {
			slowTime = 0;
			speedMax = speedMax_Org;
		}
	}

	
	/**
	 * �����ٻ�����ä����
	 * @param sillIndex 0 ���٣�1 ��ä
	 */
	public void beHit(int sillIndex) {
		if(sillIndex == 0)
			beSlowHitted();
		else if(sillIndex == 1)
			beBlindHitted();
	}

	
	public int getType() {
		return type;
	}

	public void changeType(int carIndex) {
		this.type = carIndex;
		init();
	}
	
}
