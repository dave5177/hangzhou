package com.dave.jpsc.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.model.Car;
import com.dave.jpsc.model.Player;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

/**
 * �ؿ��������
 * 
 * @author Dave
 * 
 */
public class Checkout implements Showable {
	private CanvasControl canvasControl;
	/**
	 * 0�Ǵ��سɹ���1�Ǵ���ʧ�ܡ�
	 */
	private int type;

	/**
	 * ѡ��ť�±�ֵ
	 */
	private int index_choose;

	/**
	 * ������֮ǰ������ �����ҷֱ��ǣ����飬������̥�־öȣ�����־öȣ�β��־ö�
	 */
	public static int[] old_prep = { 0, 0, 0, 0 };
	
	/**
	 * ��Ϸģʽ
	 */
	public static int gameMode;
	
	/**
	 * ������ս�Ķ���
	 */
	public static Player playerDualing;

	/**
	 * ��ʾ�ĳ־ö�
	 */
	private int[] prep_show;

	/**
	 * �־ö����ӻ��߼��ٵ�ֵ
	 */
	private int[] prep_diff;

	public Image img_back;
	public Image img_achieve;
	public Image img_number_power;
	public Image[] a_img_star;
	public Image[] a_img_title;
	public Image[] a_img_btn;
	public Image[] a_img_cbtn;// ѡ�еİ�ť
	public Image[] a_img_add;
	public Image[] a_img_minus;

	/**
	 * ����x����
	 */
	private int[] x_achieve = { 410, 410, 410 };

	/**
	 * ���ǵ�y����
	 */
	private int[] y_stars = { 258, 258, 258 };

	/**
	 * ���ǽ����ĵ����ٶ�
	 */
	private int fall_speed = 5;

	/**
	 * ��ʾ���ǵ�Ч��
	 */
	private boolean showStar;

	/**
	 * ���� 1, ����Ϊ 2.
	 */
	private int showAddMinus;

	/**
	 * ���ӻ����Ч��֡
	 */
	private int prep_frame;

	/**
	 * Ƶ�ʿ���
	 */
	private int freqCtrl;

	/**
	 * �Ӽ�y����
	 */
	private int y_diff = 400;
	
	/**
	 * ���λ�õ���������
	 */
	private int stars;

	public Checkout(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
		prep_show = new int[3];
		prep_diff = new int[3];
		for (int i = 0; i < 3; i++) {
			prep_show[i] = old_prep[i + 1];
			prep_diff[i] = old_prep[i + 1]
					- canvasControl.me.cars[CanvasControl.usingCar][i + 1];
		}
		if (type == 0) {
			showStar = true;
		} else {
			showAddMinus = 2;
		}
		
		for (int i = 0; i < 3; i++) {
			if (CanvasControl.tarAchieve[i]) {
				x_achieve[i] = 680;
				stars++;
			}
			if (stars > 0)
				y_stars[stars - 1] = 0;
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(a_img_title[type], 320, 35, Graphics.VCENTER
				| Graphics.HCENTER);
		g.setColor(255, 255, 255);
		g.setFont(C.FONT_LARGE_BLOD);
		g.drawString(CanvasControl.rankInGame + "", 45, 125, Graphics.TOP
				| Graphics.HCENTER);
		g.drawString(canvasControl.me.nickName, 140, 125, Graphics.TOP
				| Graphics.HCENTER);
		g.drawString(CanvasControl.gameTimeStr, 275, 125, Graphics.TOP
				| Graphics.HCENTER);
		g.drawString(Car.NAME[canvasControl.me.cars[CanvasControl.usingCar][0]], 410, 125, Graphics.TOP
				| Graphics.HCENTER);
		g.drawString("����+"
				+ CanvasControl.MISSIONPROPERTY[CanvasControl.mission][4], 545,
				125, Graphics.TOP | Graphics.HCENTER);

		int stars = 0;
		for (int i = 0; i < 3; i++) {
			if (CanvasControl.tarAchieve[i]) {
				stars++;
				g.drawImage(img_achieve, x_achieve[i], 223 + i * 35,
						Graphics.VCENTER | Graphics.HCENTER);// ����ͼ��
			}
			g.drawImage(a_img_star[1], 485 + i * 29, 258, Graphics.VCENTER
					| Graphics.HCENTER);// ���ǵı���
			if (stars > 0)
				g.drawImage(a_img_star[0], 485 + (stars - 1) * 29,
						y_stars[stars - 1], Graphics.VCENTER | Graphics.HCENTER);// ����

			C.drawString(g, img_number_power, prep_show[i] + "%",
					"0123456789%", 120 + i * 200, 450, 18, 18, Graphics.HCENTER
							| Graphics.VCENTER, 0, 0);

			if (showAddMinus == 1) {
				C.drawString(g, a_img_add[prep_frame], "+" + prep_diff[i],
						"0123456789+", 210 + i * 200, y_diff, 13, 18,
						Graphics.HCENTER | Graphics.VCENTER, 0, 0);
			} else if (showAddMinus == 2) {
				C.drawString(g, a_img_minus[prep_frame], "-" + prep_diff[i],
						"0123456789-", 210 + i * 200, y_diff, 13, 18,
						Graphics.HCENTER | Graphics.VCENTER, 0, 0);
			}
		}

		if(gameMode == Game.MODE_MISSION) {
			if (type == 0) {
				g.drawImage(a_img_btn[0], 90, 500, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(a_img_btn[1], 250, 500, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(a_img_btn[3], 410, 500, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(a_img_cbtn[index_choose + index_choose / 2], 90 + index_choose * 160,
						500, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(a_img_btn[0], 90, 500, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(a_img_btn[2], 250, 500, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(a_img_btn[3], 410, 500, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(a_img_cbtn[index_choose * 2 - index_choose / 2], 90 + index_choose * 160,
						500, Graphics.VCENTER | Graphics.HCENTER);
			}
		} else {
			g.drawImage(a_img_btn[0], 90, 500, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawImage(a_img_btn[2], 250, 500, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawImage(a_img_btn[4], 410, 500, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawImage(a_img_cbtn[index_choose * 2], 90 + index_choose * 160,
					500, Graphics.VCENTER | Graphics.HCENTER);
		}
	}

	public void loadResource() {
		
	}

	public void removeResource() {
		
	}

	public void removeServerImage() {
		img_back = null;
		img_achieve = null;
		img_number_power = null;
		for (int i = 0; i < 5; i++) {
			a_img_btn[i] = null;
		}
		a_img_btn = null;
		for (int i = 0; i < 5; i++) {
			a_img_cbtn[i] = null;
		}
		a_img_cbtn = new Image[3];
		for (int i = 0; i < 2; i++) {
			a_img_star[i] = null;
		}
		a_img_star = new Image[2];
		for (int i = 0; i < 2; i++) {
			a_img_title[i] = null;
		}
		a_img_title = new Image[2];

		for (int i = 0; i < 3; i++) {
			a_img_add[i] = null;
			a_img_minus[i] = null;
		}
		a_img_add = new Image[3];
		a_img_minus = new Image[3];
		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_RIGHT:
			if (index_choose < 2)
				index_choose++;
			break;
		case C.KEY_LEFT:
			if (index_choose > 0)
				index_choose--;
			break;
		case C.KEY_FIRE:
			if(gameMode == Game.MODE_MISSION) {
				if (type == 0) {
					if (index_choose == 0) {// һ���޸�
						if(canvasControl.me.cars[CanvasControl.usingCar][1] < 100) {
							canvasControl.buyGoods(15);
						}
					} else if (index_choose == 1) {// ������Ϸ
						CanvasControl.mission ++;
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						this.removeServerImage();
						canvasControl.setView(new Loading(canvasControl, 7));
					}
				} else if (type == 1) {
					if (index_choose == 0) {// һ���޸�
						if(canvasControl.me.cars[CanvasControl.usingCar][1] < 100) {
							canvasControl.buyGoods(15);
						}
					} else if (index_choose == 1) {// ��������
						canvasControl.buyGoods(16);
					}
				}
				
				if(index_choose == 2) {//����
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					this.removeServerImage();
					canvasControl.setView(new Loading(canvasControl, 7));
				}
			} else {
				if (index_choose == 0) {// һ���޸�
					if(canvasControl.me.cars[CanvasControl.usingCar][1] < 100) {
						canvasControl.buyGoods(15);
					}
				} else if (index_choose == 1) {// ����
					canvasControl.buyGoods(16);
				} else  if(index_choose == 2) {//������ս
					if(canvasControl.me.duelTimes > 0) {
						canvasControl.me.duelTimes --;
						canvasControl.saveParam();
						CanvasControl.mission = C.R.nextInt(CanvasControl.missionPassed) + 1;
						canvasControl.setView(canvasControl.nullView);
						this.removeServerImage();
						this.removeResource();
						canvasControl.setView(new Loading(canvasControl, 7, Game.MODE_DUAL, playerDualing));
					} else {
						canvasControl.buyGoods(0);
					}
				}
			}
			
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_9:
			CanvasControl.usingCar = canvasControl.me.mainCar;
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Home(canvasControl));
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		if (showStar)
			starLogic();
		else
			prepLogic();
	}

	/**
	 * �־ö����Ӻͼ��ٵ��߼�
	 */
	private void prepLogic() {
		for (int i = 0; i < 3; i++) {
			if (prep_show[i] > canvasControl.me.cars[CanvasControl.usingCar][i + 1])
				prep_show[i]--;
			else if (prep_show[i] < canvasControl.me.cars[CanvasControl.usingCar][i + 1])
				prep_show[i]++;
		}

		if (showAddMinus == 1 || showAddMinus == 2) {
			freqCtrl++;
			if (showAddMinus == 2) {
				y_diff += 3;
			} else {
				y_diff -= 3;
			}
			if (freqCtrl > 5) {
				freqCtrl = 0;
				if (prep_frame < 2)
					prep_frame++;
				else {
					showAddMinus = 0;
				}
			}

		}
	}

	private void starLogic() {
		fall_speed += 10;
		if (x_achieve[0] > 410) {
			x_achieve[0] -= fall_speed;
			if (x_achieve[0] <= 410) {
				x_achieve[0] = 410;
				fall_speed = 10;
			}
		} else if (x_achieve[1] > 410) {
			x_achieve[1] -= fall_speed;
			if (x_achieve[1] <= 410) {
				x_achieve[1] = 410;
				fall_speed = 10;
			}
		} else if (x_achieve[2] > 410) {
			x_achieve[2] -= fall_speed;
			if (x_achieve[2] <= 410) {
				x_achieve[2] = 410;
				fall_speed = 10;
			}
		} else if (y_stars[0] < 258) {
			y_stars[0] += fall_speed;
			if (y_stars[0] >= 258) {
				y_stars[0] = 258;
				fall_speed = 10;
			}
		} else if (y_stars[1] < 258) {
			y_stars[1] += fall_speed;
			if (y_stars[1] >= 258) {
				y_stars[1] = 258;
				fall_speed = 10;
			}
		} else if (y_stars[2] < 258) {
			y_stars[2] += fall_speed;
			if (y_stars[2] >= 258) {
				y_stars[2] = 258;
				fall_speed = 10;
			}
		}
		
		if(showStar && y_stars[stars - 1] >= 258) {
			showStar = false;
			showAddMinus = 2;
		}
	}

	public void handleGoods(int goodsIndex) {
		if(goodsIndex == 15) {
			canvasControl.me.cars[CanvasControl.usingCar][1] += 6;
			canvasControl.me.cars[CanvasControl.usingCar][2] += 4;
			canvasControl.me.cars[CanvasControl.usingCar][3] += 5;
			for (int i = 1; i < 4; i++) {
				if(canvasControl.me.cars[CanvasControl.usingCar][i] > 100) {
					canvasControl.me.cars[CanvasControl.usingCar][i] = 100;
				}
			}
			
			showAddMinus = 1;
			y_diff = 420;
		} else if(goodsIndex == 0) {
			canvasControl.me.duelTimes = 10;
		} else if(goodsIndex == 16) {//����
			canvasControl.me.cars[CanvasControl.usingCar][4] ++;
			canvasControl.me.computCarParam();
			CanvasControl.carProperty[canvasControl.me.cars[CanvasControl.usingCar][0]][4] ++;
		}
	}

}
