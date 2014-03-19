package com.dave.gowhere.intity;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * ����baby
 * 
 * @author Dave
 * 
 */
public class Baby implements Model {

	private int state;

	private Image[] a_img_frame;
	private Image[] a_img_flying;

	/**
	 * ����֡
	 */
	private int frame;

	/**
	 * �Խ��֡
	 */
	private int eatEffectFrame = 4;

	/**
	 * ӵ�е���Ծ��������ʼΪ1�������ڿ�����Ծһ�Σ���
	 */
	private final int jumpAbility;

	/**
	 * �ڿ�����Ծ���Ĵ���
	 */
	private int jumpTimes;

	/**
	 * �ײ��м������
	 */
	public int x, y;

	public Rect rect_bottom;// ���ϵ���ײ����
	public Rect rect_body;// �������ײ����
	public Rect rect_top;// ͷ�ϵ���ײ����

	private float gravity = 7f;

	/**
	 * ���ϵļ��ٶȣ�����·�ϵ�ʱ�����������ٶȴ�С��ȷ����෴���ڿ���ʱΪ0
	 */
	private float up_acc;

	/**
	 * y�����ٶ�
	 */
	private float speed_y;

	private Game game;

	/**
	 * ��ס
	 */
	private boolean ka;

	/**
	 * ������Ծ
	 */
	private boolean jumpping;

	/**
	 * ������
	 */
	public boolean flying;

	/**
	 * �����˵�ʱ��
	 */
	private int flyTime;

	/**
	 * ����ʱ������
	 */
	private int flyTimeMax;

	/**
	 * ����Ч��ͼƬ֡
	 */
	private int flyingImgFrame;

	/**
	 * ������
	 */
	private int jumpStartPos;

	/**
	 * �ͷ������¼������ǻ����ϰ������棬����վ������
	 */
	private boolean willStand;

	/**
	 * �ڿ��а����¼�����������
	 */
	private boolean willSquat;

	/**
	 * ����y�ٶ�
	 */
	private int flyYSpeed = 10;

	/**
	 * ������ߵ�
	 */
	private int flyTopY = 200;

	/**
	 * ���е�״̬��0Ϊ����״̬��1Ϊ���ַ���״̬��
	 */
	private int flyState;

	/**
	 * �ȵ����ɵ���Ծ
	 */
	public boolean strongJump;

	private static final float SPEED_TANHUANG = -50;// ���ɵ���ȥ�ĳ�ʼy�ٶ�
	private static final float SPEED_JUMP = -35;// �ӵ��������ĳ�ʼy�ٶ�
	private static final float SPEED_JUMP_SC = -30;// �����ٴ���Ծ�ĳ�ʼy�ٶ�

	public static final int STATE_RUNNING = 0;// ��ͨ����״̬
	public static final int STATE_JUMPPING = 1;// ��������״̬
	public static final int STATE_FALL = 2;// ����״̬
	public static final int STATE_SQUATTING = 3;// ����״̬

	public Baby(Game game, int x, int y, int jumpAbility) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.jumpAbility = jumpAbility;
		rect_bottom = new Rect(x - 10, y - 20, 30, 15);
		rect_top = new Rect(x - 10, y - 65, 35, 15);
		rect_body = new Rect(x, y - 50, 40, 30);
		try {
			a_img_frame = new Image[12];
			for (int i = 0; i < a_img_frame.length; i++) {
				a_img_frame[i] = Image.createImage("/baby/loading_" + (i + 1)
						+ ".png");
			}
			a_img_flying = new Image[2];
			Image img = Image.createImage("/game/flying.png");
			for (int i = 0; i < 2; i++) {
				a_img_flying[i] = Image.createImage(img,
						i * img.getWidth() / 2, 0, img.getWidth() / 2,
						img.getHeight(), 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void show(Graphics g) {
		if (flying) {
			flyingImgFrame = flyingImgFrame == 0 ? 1 : 0;
			g.drawImage(a_img_flying[flyingImgFrame], x - 80, y - 40,
					Graphics.HCENTER | Graphics.VCENTER);
		}
		g.drawImage(a_img_frame[frame], x, y, Graphics.BOTTOM
				| Graphics.HCENTER);
		if (eatEffectFrame < 3)
			g.drawImage(game.a_img_eatEffect[eatEffectFrame], x, y - 30,
					Graphics.HCENTER | Graphics.VCENTER);

		// //////////////////////////������Ϣ//////////////////////////
		if (CanvasControl.DEBUG) {
			g.setColor(0xffff00);
			rect_bottom.show(0, 0, g);
			g.setColor(0x00ff00);
			rect_body.show(0, 0, g);
			g.setColor(0x225992);
			rect_top.show(0, 0, g);
		}
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_UP:
		case C.KEY_FIRE:
			switch (state) {
			case STATE_RUNNING:
				speed_y = SPEED_JUMP;
				jumpping = true;
				jumpStartPos = game.runDistance;
				setState(STATE_JUMPPING);
				System.out.println(jumpTimes);
				break;
			case STATE_JUMPPING:
				if (jumpTimes < jumpAbility) {
					jumpTimes++;
					speed_y = SPEED_JUMP_SC;
				}
				break;
			case STATE_FALL:
				if (jumpTimes < jumpAbility) {
					jumpTimes++;
					speed_y += SPEED_JUMP_SC;
					if (speed_y > -25)
						speed_y = -25;
				}
				break;
			}
			// if (speed_y < 0) {
			// speed_y = SPEED_JUMP_SC;
			// } else if (speed_y > 0) {
			// speed_y += SPEED_JUMP_SC;
			// if (speed_y > -25)
			// speed_y = -25;
			// } else {
			// speed_y = SPEED_JUMP;
			// jumpping = true;
			// jumpStartPos = game.runDistance;
			// }
			break;
		case C.KEY_DOWN:
			if (state == STATE_RUNNING)
				setState(STATE_SQUATTING);
			else if (state != STATE_SQUATTING) {
				willSquat = true;
			}
			break;

		default:
			break;
		}
	}

	/**
	 * ����״̬
	 * 
	 * @param state
	 */
	private void setState(int state) {
		if (state == STATE_SQUATTING) {// �л�������״̬�ı����
			rect_body.h = 15;
			rect_body.y = y - 40;
		} else if (this.state == STATE_SQUATTING) {// �Ӷ���״̬�л������״̬
			rect_body.h = 30;
			rect_body.y = y - 50;
		}
		this.state = state;
	}

	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case C.KEY_DOWN:// �ͷ����¼�
			if (willSquat)
				willSquat = false;
			if (isUnderBarrier()) {
				willStand = true;
			} else {
				setState(STATE_RUNNING);
			}
			break;

		default:
			break;
		}
	}

	public void logic() {
		if (flying) {// ����״̬�߼�
			flyTime++;
			if (flyTime > flyTimeMax) {// ���н���
				flyTime = 0;
				flyTimeMax = 0;
				flying = false;
				game.goIntoFuTi(0);

				game.speedMax = game.speedOld;
			}
			if (flyState == 0) {// �����׶�
				if (y > 100) {
					y -= 40;
					if (y < 200) {
						y = 200;
						flyState = 1;
					}
				}
			} else {// ƽ�ȷ��������ƶ�
				y += flyYSpeed;
				if (flyYSpeed > 0) {
					if (y >= flyTopY + 50)
						flyYSpeed *= -1;
				} else if (flyYSpeed < 0) {

					if (y <= flyTopY)
						flyYSpeed *= -1;
				}

			}
		} else {

			// /////////////y�����ٶȿ���//////////////
			y += speed_y;
			speed_y += (gravity + up_acc);
			if (speed_y > 0) {
				if (state == STATE_SQUATTING)// �Ӷ��µ�״̬������
					willSquat = true;
				setState(STATE_FALL);
			}

			// ////////////���ﱼ��֡//////////////
			frame += 2;
			if (frame > 11)
				frame = 0;

			// ///////////���ɿ����ٶȿ���//////////////
			if (strongJump) {
				if (speed_y > 0) {// �½���ʱ��ʼ����
					strongJump = false;
					game.speedMax = game.speedOld;
				}
			}

			// //////////�Խ��Ч��֡/////////////////
			if (eatEffectFrame < 4) {
				eatEffectFrame++;
			}

			// ///////////�ӳ�վ������////////////////
			if (willStand) {
				if (!isUnderBarrier()) {
					setState(STATE_RUNNING);
					willStand = false;
				}
			}
		}
		keepPosition();

		updateRect();

		eatGold();
		mainCollision();

		if (x < -100 || y > 600) {// ����
		// reset();
			die();
		}
	}

	/**
	 * �������ǵ�x������λ��
	 */
	private void keepPosition() {
		if (!ka) {
			if (x - Game.X_KEEP > 0) {
				x -= 5;
				if (x < Game.X_KEEP)
					x = Game.X_KEEP;
			} else if (x - Game.X_KEEP < 0) {
				x += 5;
				if (x > Game.X_KEEP)
					x = Game.X_KEEP;
			}
		}
	}

	/**
	 * ����
	 */
	private void reset() {
		setStandPoint(0, 400);
		speed_y = 0;
	}

	/**
	 * ���¸�����ײ����
	 */
	private void updateRect() {
		rect_bottom.x = x - 10;
		rect_bottom.y = y - 20;
		rect_top.x = x - 10;
		rect_top.y = y - 65;

		if (state == STATE_SQUATTING) {
			rect_body.y = y - 35;
		} else {
			rect_body.y = y - 50;
		}
		rect_body.x = x;
	}

	/**
	 * ����ײ
	 */
	private void mainCollision() {
		up_acc = 0;
		// if(game.v_futi.size() > 0) {//������ײ
		// for (int i = game.v_futi.size() - 1; i >= 0; i--) {
		// FuTi futi = (FuTi) game.v_futi.elementAt(i);
		// if(C.rectInsect(rect_bottom, futi.rect)) {
		// if(jumpping) {
		// jumpping = false;
		// System.out.println("��Ծ���룺" + (game.runDistance - jumpStartPos));
		// }
		// up_acc = -gravity;
		// speed_y = 0;
		// setStandPoint(x, futi.y + 28);
		// if(willSquat) {
		// willSquat = false;
		// setState(STATE_SQUATTING);
		// }
		// }
		// }
		// }

		for (int i = 0; i < game.v_missile.size(); i++) {// �뵼������ײ
			Missile missile = (Missile) game.v_missile.elementAt(i);
			if (C.rectInsect(rect_body, missile.rect))
				die();// ��������
		}

		for (int i = 0; i < game.sizeMapElV; i++) {// ������ͼԪ������
			MapElements mapEl = (MapElements) game.v_MapEl.elementAt(i);
			if (C.rectInsect(rect_bottom, mapEl.rect) && !mapEl.isDead()) {// ���µ���ײ
				switch (mapEl.getType()) {
				case MapElements.TYPE_FUTI:// �ȵ�����
				case MapElements.TYPE_ROAD:// ����·��
					if (jumpping) {
						jumpping = false;
						System.out.println("��Ծ���룺"
								+ (game.runDistance - jumpStartPos));
					}
					up_acc = -gravity;
					speed_y = 0;
					setStandPoint(x, mapEl.y + 28);
					if (willSquat) {
						willSquat = false;
						setState(STATE_SQUATTING);
					}

					break;
				case MapElements.TYPE_MONSTEREARTH:// �ܲ����Ĺ�
				case MapElements.TYPE_BRICK:// ש��
					speed_y = SPEED_JUMP_SC;// ��Ծ
					setStandPoint(x, mapEl.y + 18);
					mapEl.beKill();
					if (jumpping) {
						jumpping = false;
						System.out.println("��Ծ���룺"
								+ (game.runDistance - jumpStartPos));
					}
					jumpping = true;
					jumpStartPos = game.runDistance;
					break;
				case MapElements.TYPE_MONSTERAIR:// �����
				case MapElements.TYPE_THORN:// ��
					die();
					break;
				case MapElements.TYPE_SPRING:// ����
					setStandPoint(x, mapEl.y + 18);
					strongJump = true;
					if (game.speedMax <= 20)
						game.speedOld = game.speedMax;

					game.speedMax = 50;
					game.speed_run = 30;
					speed_y = SPEED_TANHUANG;

					if (jumpping) {
						jumpping = false;
						System.out.println("��Ծ���룺"
								+ (game.runDistance - jumpStartPos));
					}
					jumpping = true;
					jumpStartPos = game.runDistance;
					break;
				}

				if (state == STATE_FALL || state == STATE_JUMPPING) {
					jumpTimes = 0;
					setState(STATE_RUNNING);
				}
			}

			if (mapEl.getType() == MapElements.TYPE_BRICK && !mapEl.isDead()
					&& C.rectInsect(rect_top, mapEl.rect)) {// ͷ������ײ
				mapEl.beKill();
				speed_y = -speed_y;
				return;
			}

			ka = false;
			if (!mapEl.isDead() && C.rectInsect(rect_body, mapEl.rect)) {// �������ײ
				if (mapEl.getType() == MapElements.TYPE_MONSTERAIR
						|| mapEl.getType() == MapElements.TYPE_MONSTEREARTH
						|| mapEl.getType() == MapElements.TYPE_THORN) {// �����̻��߹�
					die();

				} else {
					setStandPoint(mapEl.rect.x - game.speedMax * 2 - 20, y);
					ka = true;
				}
			}
		}
	}

	/**
	 * ��������Ƿ����ϰ�����
	 * 
	 * @return
	 */
	private boolean isUnderBarrier() {
		for (int i = 0; i < game.sizeMapElV; i++) {
			MapElements mapEl = (MapElements) game.v_MapEl.elementAt(i);
			Rect checkRect = new Rect(rect_body.x, y - 50, rect_body.w + 20, 30);

			if (mapEl.getType() == MapElements.TYPE_BARRIER
					&& C.rectInsect(checkRect, mapEl.rect)) {// �������ײ
				return true;
			}
		}
		return false;
	}

	/**
	 * �Խ��
	 */
	private void eatGold() {
		for (int i = game.v_gold.size() - 1; i >= 0; i--) {
			Gold gold = (Gold) game.v_gold.elementAt(i);
			if (C.rectInsect(rect_body, gold.rect)) {// �Ե�һ�����
				game.removeGold(gold);
				eatEffect();
			}
		}
	}

	/**
	 * �Խ��Ч��
	 */
	private void eatEffect() {
		eatEffectFrame = 0;
	}

	/**
	 * ����վ���ĵ�
	 * 
	 * @param x_stand
	 * @param y_stand
	 */
	private void setStandPoint(int x_stand, int y_stand) {
		x = x_stand;
		y = y_stand;

		updateRect();
	}

	public void fire() {

	}

	/**
	 * �������״̬
	 */
	public void goToFly(int flyTime) {
		flyState = 0;
		flying = true;
		flyTimeMax += flyTime;

		if (game.speedMax <= 20)
			game.speedOld = game.speedMax;

		game.speedMax = Game.FLY_SPEED;
	}

	/**
	 * ����
	 */
	public void die() {
		reLive();// �����������ֱ�Ӹ��
	}

	/**
	 * ����
	 */
	public void reLive() {
		reset();
		goToFly(10);
	}
}
