package com.dave.worldWar.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.net.ServerIptv;
import com.dave.worldWar.tool.C;
import com.dave.worldWar.view.Dialog;
import com.dave.worldWar.view.Game;

public class Soldier {

	/**
	 * ʿ�������� 0��1��2��3��4�ֱ���������ڱ�����������ѻ��֡�̹�˱�
	 */
	public int type;

	/**
	 * ʿ����״̬1�����ߣ�2��������3������ս����4������ս����5��������
	 */
	public int state;

	/**
	 * ����ڵ�ͼ������ֵ
	 */
	public int x, y;

	/**
	 * trueΪ�ѷ���falseΪ�з�
	 */
	public boolean friendly;

	/**
	 * �ƶ��ٶ�
	 */
	private int moveSpeed;

	/**
	 * �������ֵ
	 */
	private int lifeMax;

	/**
	 * �����������λ�����루1�� = 10���룩
	 */
	private int interval;

	/**
	 * ��̡���λ������
	 */
	public int range;

	/**
	 * ������
	 */
	private int attackPower;

	/**
	 * ��ǰ����ֵ
	 */
	public int life;

	/**
	 * ����֡
	 */
	private int sdr_Frame;

	/**
	 * ʿ����������Ϸ
	 */
	private Game game;

	/**
	 * ����ͼƬ��ͼƬ��͸�
	 */
	private int w, h;

	/**
	 * ˢ�¿���
	 */
	private int refresh;

	/**
	 * �����󲥷�֡����
	 */
	private boolean afterShoot;

	/**
	 * ������ ��100�����磺10�����������Ϊ0.1��
	 */
	private int acc;

	/**
	 * ���Ŀ��ʿ��
	 */
	private Soldier shootPoint;

	/**
	 * ����ı�ըЧ��
	 */
	private int showBoom = -1;

	/**
	 * ��ը����֡
	 */
	private int boomFrame;

	/**
	 * ը�ɵĳ��ٶ�
	 */
	private int flySpeed;

	/**
	 * ���Ǯ��״̬
	 */
	private int state_bf_sht;

	/**
	 * ��������
	 */
	private Fire[] a_fire;

	/**
	 * ����ͣ��ʱ��
	 */
	private int stayTime;

	private int gun_fire_Frame;

	/**
	 * ǹ�ڻ���
	 */
	private boolean showGunFire;

	/**
	 * @param type
	 *            ʿ�������� 0��2��3�ֱ����������������ѻ���
	 * @param friendly
	 *            trueΪ�ѷ���falseΪ�з�
	 */
	public Soldier(Game game, int type, boolean friendly, int x, int y) {
		this.game = game;
		this.type = type;
		this.friendly = friendly;
		this.x = x;
		this.y = y;

		initPref();
	}

	/**
	 * ��ʼ������ֵ
	 */
	private void initPref() {
		lifeMax = CanvasControl.soldiers_prpty[type][0];
		attackPower = CanvasControl.soldiers_prpty[type][1];
		if (!friendly) {
			lifeMax += (CanvasControl.mission - 1) * 30;
			attackPower += (CanvasControl.mission - 1) * 30;
		} else {
			lifeMax += (CanvasControl.soldiers_level[type] - 1) * 50;
			attackPower += (CanvasControl.soldiers_level[type] - 1) * 50;
		}
		range = CanvasControl.soldiers_prpty[type][2];
		interval = 100 / CanvasControl.soldiers_prpty[type][3];
		moveSpeed = CanvasControl.soldiers_prpty[type][4] >> 1;
		acc = CanvasControl.soldiers_prpty[type][5];

		life = lifeMax;
		state = 1;
		sdr_Frame = C.R.nextInt(4);
	}

	public void show(Graphics g) {
		switch (state) {
		case 0:// վ��
			w = game.a_2_img_soldier[type][1].getWidth();
			h = game.a_2_img_soldier[type][1].getHeight();
			if (friendly) {
				g.drawImage(game.img_friendly, x + game.x_map - 10, y - 5,
						Graphics.HCENTER | Graphics.VCENTER);
				g.drawRegion(game.a_2_img_soldier[type][1],
						(w * sdr_Frame) / 3, 0, (w / 3), h, 0, x + game.x_map,
						y + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
			} else {
				g.drawRegion(game.a_2_img_soldier[type][1],
						(w * sdr_Frame) / 3, 0, (w / 3), h,
						Sprite.TRANS_MIRROR, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.BOTTOM);
			}
			break;
		case 1:// �ƶ�

			if (type == 1 || type == 4) {
				w = game.a_2_img_soldier[type][1].getWidth();
				h = game.a_2_img_soldier[type][1].getHeight();
				if (friendly) {
					// g.drawImage(game.img_friendly, x + game.x_map - 10, y -
					// 5,
					// Graphics.HCENTER | Graphics.VCENTER);
					g.drawRegion(game.a_2_img_soldier[type][1], 0, 0, (w / 6),
							h, 0, x + game.x_map, y + game.y_map,
							Graphics.HCENTER | Graphics.BOTTOM);
				} else {
					g.drawRegion(game.a_2_img_soldier[type][1], 0, 0, (w / 6),
							h, Sprite.TRANS_MIRROR, x + game.x_map, y
									+ game.y_map, Graphics.HCENTER
									| Graphics.BOTTOM);
				}
			} else {
				w = game.a_2_img_soldier[type][0].getWidth();
				h = game.a_2_img_soldier[type][0].getHeight();
				if (friendly) {
					g.drawImage(game.img_friendly, x + game.x_map - 10, y - 5,
							Graphics.HCENTER | Graphics.VCENTER);
					g.drawRegion(game.a_2_img_soldier[type][0],
							(w * sdr_Frame) >> 2, 0, (w >> 2), h, 0, x
									+ game.x_map, y + game.y_map,
							Graphics.HCENTER | Graphics.BOTTOM);
				} else {
					g.drawRegion(game.a_2_img_soldier[type][0],
							(w * sdr_Frame) >> 2, 0, (w >> 2), h,
							Sprite.TRANS_MIRROR, x + game.x_map,
							y + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
				}
			}
			break;
		case 2:// ����
			if(showGunFire) {
				showGunFire(g);
			}
			
			w = game.a_2_img_soldier[type][1].getWidth();
			h = game.a_2_img_soldier[type][1].getHeight();
			
			if (friendly) {
				if (type == 1 || type == 4) {
					g.drawRegion(game.a_2_img_soldier[type][1],
							(w * sdr_Frame) / 6, 0, (w / 6), h, 0, x
									+ game.x_map, y + game.y_map,
							Graphics.HCENTER | Graphics.BOTTOM);
				} else {
					g.drawImage(game.img_friendly, x + game.x_map - 10, y - 5,
							Graphics.HCENTER | Graphics.VCENTER);
					g.drawRegion(game.a_2_img_soldier[type][1],
							(w * sdr_Frame) / 3, 0, (w / 3), h, 0, x
									+ game.x_map, y + game.y_map,
							Graphics.HCENTER | Graphics.BOTTOM);

				}
			} else {
				if (type == 1 || type == 4) {
					g.drawRegion(game.a_2_img_soldier[type][1],
							(w * sdr_Frame) / 6, 0, (w / 6), h,
							Sprite.TRANS_MIRROR, x + game.x_map,
							y + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
				} else {
					g.drawRegion(game.a_2_img_soldier[type][1],
							(w * sdr_Frame) / 3, 0, (w / 3), h,
							Sprite.TRANS_MIRROR, x + game.x_map,
							y + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);

				}
			}
			break;
		case 3:
			w = game.a_2_img_soldier[type][2].getWidth();
			h = game.a_2_img_soldier[type][2].getHeight();
			if (friendly) {
				g.drawImage(game.img_friendly, x + game.x_map - 10, y - 5,
						Graphics.HCENTER | Graphics.VCENTER);
				g.drawRegion(game.a_2_img_soldier[type][2],
						(w * sdr_Frame) >> 2, 0, (w >> 2), h, 0,
						x + game.x_map, y + game.y_map, Graphics.HCENTER
								| Graphics.BOTTOM);
			} else {
				g.drawRegion(game.a_2_img_soldier[type][2],
						(w * sdr_Frame) >> 2, 0, (w >> 2), h,
						Sprite.TRANS_MIRROR, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.BOTTOM);
			}
			break;
		case 4:
			w = game.a_2_img_soldier[type][3].getWidth();
			h = game.a_2_img_soldier[type][3].getHeight();
			if (friendly) {
				g.drawImage(game.img_friendly, x + game.x_map - 10, y - 5,
						Graphics.HCENTER | Graphics.VCENTER);
				g.drawRegion(game.a_2_img_soldier[type][3],
						(w * sdr_Frame) / 3, 0, (w / 3), h, 0, x + game.x_map,
						y + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
			} else {
				g.drawRegion(game.a_2_img_soldier[type][3],
						(w * sdr_Frame) / 3, 0, (w / 3), h,
						Sprite.TRANS_MIRROR, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.BOTTOM);
			}
			break;
		case 5:
			w = game.a_2_img_soldier[type][4].getWidth();
			h = game.a_2_img_soldier[type][4].getHeight();
			if (friendly) {
				g.drawRegion(game.a_2_img_soldier[type][4], 0, 0, w, h, 0, x
						+ game.x_map, y + game.y_map, Graphics.HCENTER
						| Graphics.BOTTOM);
			} else {
				g.drawRegion(game.a_2_img_soldier[type][4], 0, 0, w, h,
						Sprite.TRANS_MIRROR, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.BOTTOM);
			}

			if (type == 1 || type == 4) {
				for (int i = 0; i < a_fire.length; i++) {
					a_fire[i].show(g);
				}
			}
			break;
		case 6:// ��ը��
			w = game.a_2_img_soldier[type][4].getWidth();
			h = game.a_2_img_soldier[type][4].getHeight();
			if (friendly) {
				g.drawRegion(game.a_2_img_soldier[type][4], 0, 0, w, h, 0, x
						+ game.x_map, y + game.y_map, Graphics.HCENTER
						| Graphics.VCENTER);
			} else {
				g.drawRegion(game.a_2_img_soldier[type][4], 0, 0, w, h,
						Sprite.TRANS_MIRROR, x + game.x_map, y + game.y_map,
						Graphics.HCENTER | Graphics.VCENTER);
			}
			break;
		}

		if (state < 5) {
			g.drawImage(game.a_img_blood_bar[0], x - 20 + game.x_map, y - 80
					+ game.y_map, 0);
			int rem_bld = life * 100 / lifeMax;
			g.setClip(x - 20 + game.x_map, 0, rem_bld * 31 / 100, 530);
			if (rem_bld > 66) {
				g.drawImage(game.a_img_blood_bar[1], x - 20 + game.x_map, y
						- 80 + game.y_map, 0);
			} else if (rem_bld > 33) {

				g.drawImage(game.a_img_blood_bar[2], x - 20 + game.x_map, y
						- 80 + game.y_map, 0);
			} else {

				g.drawImage(game.a_img_blood_bar[3], x - 20 + game.x_map, y
						- 80 + game.y_map, 0);
			}
			g.setClip(0, 0, 640, 530);

			if (type == 4) {
				if (rem_bld < 20) {
					for (int i = 0; i < 10; i++) {
						g.drawImage(game.a_img_broken[i % 6], x - 60 + i * 8
								+ game.x_map, y - 44 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				} else if (rem_bld < 40) {
					for (int i = 0; i < 7; i++) {
						g.drawImage(game.a_img_broken[i % 6], x - 50 + i * 10
								+ game.x_map, y - 44 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				} else if (rem_bld < 60) {
					for (int i = 0; i < 5; i++) {
						g.drawImage(game.a_img_broken[i], x - 30 + i * 10
								+ game.x_map, y - 44 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				} else if (rem_bld < 80) {
					for (int i = 0; i < 3; i++) {
						g.drawImage(game.a_img_broken[i], x - 30 + i * 20
								+ game.x_map, y - 44 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				}
			} else if (type == 1) {
				if (rem_bld < 20) {
					for (int i = 0; i < 6; i++) {
						g.drawImage(game.a_img_broken[i % 6], x - 10 + i * 8
								+ game.x_map, y - 25 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				} else if (rem_bld < 40) {
					for (int i = 0; i < 4; i++) {
						g.drawImage(game.a_img_broken[i % 6], x - 10 + i * 10
								+ game.x_map, y - 25 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				} else if (rem_bld < 60) {
					for (int i = 0; i < 3; i++) {
						g.drawImage(game.a_img_broken[i], x + i * 10
								+ game.x_map, y - 25 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				} else if (rem_bld < 80) {
					for (int i = 0; i < 2; i++) {
						g.drawImage(game.a_img_broken[i], x + i * 20
								+ game.x_map, y - 25 + game.y_map,
								Graphics.HCENTER | Graphics.VCENTER);
					}
				}
			}
		}

		switch (showBoom) {
		case 0:
		case 3:
			g.drawImage(game.a_img_boom_0[boomFrame], x + game.x_map, y
					+ game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
			break;
		case 1:
			g.drawImage(game.a_img_boom_1[boomFrame], x + game.x_map, y
					+ game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
			break;
		case 2:
			g.drawImage(game.a_img_boom_2[boomFrame], x + game.x_map, y
					+ game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
			break;
		case 4:
			g.drawImage(game.a_img_boom_4[boomFrame], x + game.x_map, y
					+ game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
			break;
		}
	}

	private void showGunFire(Graphics g) {
		if(friendly) {
			switch (type) {
			case 0:
				g.drawRegion(game.a_img_gun_fire[0], 12 * gun_fire_Frame, 0, 12, 10, 0, x
						+ game.x_map + 20, y + game.y_map - 52,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 1:
				g.drawRegion(game.a_img_gun_fire[2], 31 * gun_fire_Frame, 0, 31, 24, 0, x
						+ game.x_map + 50, y + game.y_map - 30,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 2:
				g.drawRegion(game.a_img_gun_fire[1], 18 * gun_fire_Frame, 0, 18, 14, 0, x
						+ game.x_map + 20, y + game.y_map - 50,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 3:
				g.drawRegion(game.a_img_gun_fire[0], 12 * gun_fire_Frame, 0, 12, 10, 0, x
						+ game.x_map + 20, y + game.y_map - 58,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 4:
				g.drawRegion(game.a_img_gun_fire[2], 31 * gun_fire_Frame, 0, 31, 24, 0, x
						+ game.x_map + 80, y + game.y_map - 40,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			default:
				break;
			}
		} else {
			switch (type) {
			case 0:
				g.drawRegion(game.a_img_gun_fire[0], 12 * gun_fire_Frame, 0, 12, 10, 0, x
						+ game.x_map - 20, y + game.y_map - 52,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 1:
				g.drawRegion(game.a_img_gun_fire[2], 31 * gun_fire_Frame, 0, 31, 24, 0, x
						+ game.x_map - 50, y + game.y_map - 30,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 2:
				g.drawRegion(game.a_img_gun_fire[1], 18 * gun_fire_Frame, 0, 18, 14, 0, x
						+ game.x_map - 20, y + game.y_map - 50,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 3:
				g.drawRegion(game.a_img_gun_fire[0], 12 * gun_fire_Frame, 0, 12, 10, 0, x
						+ game.x_map - 20, y + game.y_map - 58,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 4:
				g.drawRegion(game.a_img_gun_fire[2], 31 * gun_fire_Frame, 0, 31, 24, 0, x
						+ game.x_map - 80, y + game.y_map - 40,
						Graphics.HCENTER | Graphics.BOTTOM);
				break;
			default:
				break;
			}
		}
	}

	public void logic() {
		switch (state) {
		case 0:
			gotoFire();
			break;
		case 1:
			move();
			break;
		case 2:
			autoShoot();
			break;
		case 3:
			jumpDown();
			break;
		case 4:
			jumpOut();
			break;
		case 5:
			if (type == 1 || type == 4) {
				for (int i = 0; i < a_fire.length; i++) {
					a_fire[i].logic();
				}

				stayTime++;
				if (stayTime > 20) {
					removeMe();
				}
			}
			break;
		case 6:
			bombFly();
			break;
		}

		boomLogic();
	}

	/**
	 * ��ըЧ���߼�
	 */
	private void boomLogic() {
		if (showBoom == 0 || showBoom == 3) {
			boomFrame++;
			if (boomFrame > 4) {
				boomFrame = 0;
				showBoom = -1;
				if (life <= 0) {
					if (type != 1 && type != 4) {
						removeMe();
					}
				}
			}
		} else if (showBoom != -1) {
			boomFrame++;
			if (boomFrame > 6) {
				boomFrame = 0;
				showBoom = -1;
				if (life <= 0) {
					if (type != 1 && type != 4) {
						removeMe();
					}
				}
			}
		}
	}

	/**
	 * ���Ҵ���Ϸ���Ƴ�
	 */
	private void removeMe() {
		game.v_soldier.removeElement(this);
		if (friendly) {
			if (game.remain_army <= 0 && !Game.viewChanged) {// ʧ����
				Game.viewChanged = true;
				game.dieWay = 0;

				CanvasControl.score_mission = Game.numb_killEnemy >> 1;
				CanvasControl.totalScore += CanvasControl.score_mission;
				new ServerIptv(game.canvasControl).doSendScore(
						CanvasControl.totalScore, CanvasControl.group);

				CanvasControl.goodsIndex = 16;
				game.canvasControl.setGoBackView(game);
				game.canvasControl.setView(game.canvasControl.nullView);
				game.removeResource();
				game.canvasControl.setView(new Dialog(game.canvasControl, 7,
						game));
			}
		} else {
			if (game.remain_enemy <= 0 && !Game.viewChanged) {// �����ˡ�
				Game.viewChanged = true;

				CanvasControl.mission++;
				int score = 300 - Game.sendTimes * Game.useTime / 500;
				CanvasControl.score_mission = score > 10 ? score : 10;
				CanvasControl.totalScore += CanvasControl.score_mission;

				new ServerIptv(game.canvasControl).doSendScore(
						CanvasControl.totalScore, CanvasControl.group);
				game.canvasControl.saveParam();

				game.canvasControl.setGoBackView(game);
				game.canvasControl.setView(game.canvasControl.nullView);
				game.removeResource();
				game.canvasControl.setView(new Dialog(game.canvasControl, 6,
						game).setGame(game));

				// game.initMession();
			}
		}
	}

	/**
	 * ը�ɵ��߼�
	 */
	private void bombFly() {
		flySpeed -= 5;
		y -= flySpeed;
		if (flySpeed <= -15)
			game.v_soldier.removeElement(this);
	}

	/**
	 * �Զ���ǹ
	 */
	private void autoShoot() {
		refresh++;
		if (refresh > interval) {
			shoot();
			refresh = 0;
		}
		
		if(showGunFire) {
			gun_fire_Frame ++;
			if(gun_fire_Frame > 3) {
				gun_fire_Frame = 0;
				showGunFire = false;
			}
		}

		if (afterShoot) {
			sdr_Frame++;

			if (type == 1 || type == 4) {
				if (sdr_Frame > 5) {
					sdr_Frame = 0;
					afterShoot = false;
				}
			} else {
				if (sdr_Frame > 2) {
					sdr_Frame = 0;
					afterShoot = false;
				}
			}
		}

		if (shootPoint == null || shootPoint.life <= 0) {
			if (type == 3)
				state = state_bf_sht;
			else
				state = 1;
		}
	}

	/**
	 * ���
	 */
	private void shoot() {
		afterShoot = true;
		showGunFire = true;
		
		game.canvasControl.playerHandler.playByIndex(2);
		
		boolean hit = false;
		if (C.R.nextInt(100) < acc)
			hit = true;
		game.v_bullet.addElement(new Bullet(type, x, y - 50, friendly, game,
				hit, shootPoint, this));
	}

	/**
	 * ������
	 */
	private void jumpOut() {
		refresh++;
		if (refresh > 1) {
			refresh = 0;

			if (friendly) {
				x += 10;
			} else {
				x -= 10;
			}

			y -= 30;

			sdr_Frame++;
			if (sdr_Frame > 2) {
				sdr_Frame = 0;
				state = 1;
			}
		}
	}

	/**
	 * ����
	 */
	private void jumpDown() {

		refresh++;
		if (refresh > 1) {
			refresh = 0;

			if (friendly) {
				x += 10;
			} else {
				x -= 10;
			}

			y += 20;

			sdr_Frame++;
			if (sdr_Frame > 3) {
				sdr_Frame = 0;
				if (type == 3) {
					state = 0;
				} else {
					state = 1;
				}
			}
		}

	}

	/**
	 * ����
	 */
	public void move() {
		if (friendly) {
			x += moveSpeed;
		} else {
			x -= moveSpeed;
		}

		gotoFire();

		refresh++;
		if (refresh > 1) {
			refresh = 0;

			sdr_Frame++;
			if (sdr_Frame > 3) {
				sdr_Frame = 0;
			}
		}

		if (type != 1 && type != 4) {
			if (friendly && ((x > 203 && x < 223) || (x > 660 && x < 680))) {
				sdr_Frame = 0;
				state = 3;
			} else if ((!friendly)
					&& ((x > 722 && x < 742) || (x < 282 && x > 262))) {
				sdr_Frame = 0;
				state = 3;
			} else if (friendly
					&& ((x > 262 && x < 282) || (x > 722 && x < 742))) {
				sdr_Frame = 0;
				state = 4;
			} else if ((!friendly)
					&& ((x > 203 && x < 223) || (x > 660 && x < 680))) {
				sdr_Frame = 0;
				state = 4;
			}
		}

		if (friendly && x > 1000 && !Game.viewChanged) {// ռ���˵з����ݵأ�ʤ����
			Game.viewChanged = true;

			CanvasControl.mission++;
			int score = 300 - Game.sendTimes * Game.useTime / 500;
			CanvasControl.score_mission = score > 10 ? score : 10;
			CanvasControl.totalScore += CanvasControl.score_mission;

			new ServerIptv(game.canvasControl).doSendScore(
					CanvasControl.totalScore, CanvasControl.group);
			game.canvasControl.saveParam();

			game.canvasControl.setGoBackView(game);
			game.canvasControl.setView(game.canvasControl.nullView);
			game.removeResource();
			game.canvasControl.setView(new Dialog(game.canvasControl, 6, game)
					.setGame(game));

			// game.initMession();
		} else if (!friendly && x < -50 && !Game.viewChanged) {// ���ݵر��з�ռ�죬ʧ�ܡ�
			Game.viewChanged = true;
			game.dieWay = 1;

			CanvasControl.score_mission = Game.numb_killEnemy >> 1;
			CanvasControl.totalScore += CanvasControl.score_mission;
			new ServerIptv(game.canvasControl).doSendScore(
					CanvasControl.totalScore, CanvasControl.group);

			CanvasControl.goodsIndex = 16;
			game.canvasControl.setGoBackView(game);
			game.canvasControl.setView(game.canvasControl.nullView);
			game.removeResource();
			game.canvasControl.setView(new Dialog(game.canvasControl, 7, game));
		}

	}

	/**
	 * �е����ڹ�����Χ�ڵ�ʱ����빥��״̬
	 */
	private void gotoFire() {
		for (int i = 0; i < game.v_soldier.size(); i++) {
			Soldier sodr = (Soldier) game.v_soldier.elementAt(i);
			if (friendly != sodr.friendly) {
				if (C.distance(x, y, sodr.x, sodr.y) < range * range) {
					if (sodr.life <= 0)
						continue;

					shootPoint = sodr;
					sdr_Frame = 0;
					state_bf_sht = state;
					state = 2;
					return;
				}
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @param bullet
	 *            �����ҵ��ӵ�
	 */
	public void beHit(Bullet bullet) {
		life -= bullet.soldier.attackPower;
		showBoom = bullet.type;
		if (life <= 0) {
			state = 5;

			if (friendly) {
				if (game.remain_army <= 0) {
					game.width_my_bar = 0;
				} else {
					game.width_my_bar -= (game.width_my_bar - 72)
							/ game.remain_army;
				}
				game.remain_army--;
			} else {
				Game.numb_killEnemy++;
				if (game.remain_enemy <= 0) {
					game.width_my_bar = 640;
				} else {
					game.width_my_bar += (570 - game.width_my_bar)
							/ game.remain_enemy;
				}
				game.remain_enemy--;
			}

			if (type == 1 || type == 4) {
				a_fire = new Fire[C.R.nextInt(3) + 3];
				for (int i = 0; i < a_fire.length; i++) {
					a_fire[i] = new Fire(x - 20 + C.R.nextInt(40), y
							- C.R.nextInt(40), game);
				}
			}
		}
	}

	/**
	 * ��ը��
	 */
	public void beBomb() {
		life = 0;

		Game.numb_killEnemy++;
		if (game.remain_enemy <= 0) {
			game.width_my_bar = 640;
		} else {
			game.width_my_bar += (570 - game.width_my_bar) / game.remain_enemy;
		}
		game.remain_enemy--;

		if (type == 1 || type == 4) {
			a_fire = new Fire[C.R.nextInt(3) + 3];
			for (int i = 0; i < a_fire.length; i++) {
				a_fire[i] = new Fire(x - 20 + C.R.nextInt(40), y
						- C.R.nextInt(40), game);
			}
		}

		state = 6;
		flySpeed = C.R.nextInt(10) + 10;
	}

}
