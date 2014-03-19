package com.dave.gowhere.intity;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

/**
 * 主角baby
 * 
 * @author Dave
 * 
 */
public class Baby implements Model {

	private int state;

	private Image[] a_img_frame;
	private Image[] a_img_flying;

	/**
	 * 任务帧
	 */
	private int frame;

	/**
	 * 吃金币帧
	 */
	private int eatEffectFrame = 4;

	/**
	 * 拥有的跳跃能力，初始为1（可以在空中跳跃一次）。
	 */
	private final int jumpAbility;

	/**
	 * 在空中跳跃过的次数
	 */
	private int jumpTimes;

	/**
	 * 底部中间的坐标
	 */
	public int x, y;

	public Rect rect_bottom;// 脚上的碰撞矩形
	public Rect rect_body;// 身体的碰撞矩形
	public Rect rect_top;// 头上的碰撞矩形

	private float gravity = 7f;

	/**
	 * 向上的加速度，踩在路上的时候与重力加速度大小相等方向相反，在空中时为0
	 */
	private float up_acc;

	/**
	 * y方向速度
	 */
	private float speed_y;

	private Game game;

	/**
	 * 卡住
	 */
	private boolean ka;

	/**
	 * 正在跳跃
	 */
	private boolean jumpping;

	/**
	 * 飞行中
	 */
	public boolean flying;

	/**
	 * 飞行了的时间
	 */
	private int flyTime;

	/**
	 * 飞行时间上限
	 */
	private int flyTimeMax;

	/**
	 * 飞行效果图片帧
	 */
	private int flyingImgFrame;

	/**
	 * 起跳点
	 */
	private int jumpStartPos;

	/**
	 * 释放了向下键，但是还在障碍物下面，等下站起来。
	 */
	private boolean willStand;

	/**
	 * 在空中按向下键，到达后蹲下
	 */
	private boolean willSquat;

	/**
	 * 飞行y速度
	 */
	private int flyYSpeed = 10;

	/**
	 * 飞行最高点
	 */
	private int flyTopY = 200;

	/**
	 * 飞行的状态：0为上升状态，1为保持飞行状态。
	 */
	private int flyState;

	/**
	 * 踩到弹簧的跳跃
	 */
	public boolean strongJump;

	private static final float SPEED_TANHUANG = -50;// 弹簧弹出去的初始y速度
	private static final float SPEED_JUMP = -35;// 从地面起跳的初始y速度
	private static final float SPEED_JUMP_SC = -30;// 控中再次跳跃的初始y速度

	public static final int STATE_RUNNING = 0;// 普通奔跑状态
	public static final int STATE_JUMPPING = 1;// 起跳上升状态
	public static final int STATE_FALL = 2;// 下落状态
	public static final int STATE_SQUATTING = 3;// 蹲下状态

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

		// //////////////////////////调试信息//////////////////////////
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
	 * 设置状态
	 * 
	 * @param state
	 */
	private void setState(int state) {
		if (state == STATE_SQUATTING) {// 切换到蹲下状态改变矩形
			rect_body.h = 15;
			rect_body.y = y - 40;
		} else if (this.state == STATE_SQUATTING) {// 从蹲下状态切换到别的状态
			rect_body.h = 30;
			rect_body.y = y - 50;
		}
		this.state = state;
	}

	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case C.KEY_DOWN:// 释放向下键
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
		if (flying) {// 飞行状态逻辑
			flyTime++;
			if (flyTime > flyTimeMax) {// 飞行结束
				flyTime = 0;
				flyTimeMax = 0;
				flying = false;
				game.goIntoFuTi(0);

				game.speedMax = game.speedOld;
			}
			if (flyState == 0) {// 上升阶段
				if (y > 100) {
					y -= 40;
					if (y < 200) {
						y = 200;
						flyState = 1;
					}
				}
			} else {// 平稳飞行上下移动
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

			// /////////////y方向速度控制//////////////
			y += speed_y;
			speed_y += (gravity + up_acc);
			if (speed_y > 0) {
				if (state == STATE_SQUATTING)// 从蹲下的状态掉下来
					willSquat = true;
				setState(STATE_FALL);
			}

			// ////////////人物奔跑帧//////////////
			frame += 2;
			if (frame > 11)
				frame = 0;

			// ///////////弹簧空中速度控制//////////////
			if (strongJump) {
				if (speed_y > 0) {// 下降的时候开始减速
					strongJump = false;
					game.speedMax = game.speedOld;
				}
			}

			// //////////吃金币效果帧/////////////////
			if (eatEffectFrame < 4) {
				eatEffectFrame++;
			}

			// ///////////延迟站立控制////////////////
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

		if (x < -100 || y > 600) {// 死亡
		// reset();
			die();
		}
	}

	/**
	 * 保持主角的x轴坐标位置
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
	 * 重置
	 */
	private void reset() {
		setStandPoint(0, 400);
		speed_y = 0;
	}

	/**
	 * 更新各个碰撞矩形
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
	 * 主碰撞
	 */
	private void mainCollision() {
		up_acc = 0;
		// if(game.v_futi.size() > 0) {//浮梯碰撞
		// for (int i = game.v_futi.size() - 1; i >= 0; i--) {
		// FuTi futi = (FuTi) game.v_futi.elementAt(i);
		// if(C.rectInsect(rect_bottom, futi.rect)) {
		// if(jumpping) {
		// jumpping = false;
		// System.out.println("跳跃距离：" + (game.runDistance - jumpStartPos));
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

		for (int i = 0; i < game.v_missile.size(); i++) {// 与导弹的碰撞
			Missile missile = (Missile) game.v_missile.elementAt(i);
			if (C.rectInsect(rect_body, missile.rect))
				die();// 碰到就死
		}

		for (int i = 0; i < game.sizeMapElV; i++) {// 遍历地图元素容器
			MapElements mapEl = (MapElements) game.v_MapEl.elementAt(i);
			if (C.rectInsect(rect_bottom, mapEl.rect) && !mapEl.isDead()) {// 脚下的碰撞
				switch (mapEl.getType()) {
				case MapElements.TYPE_FUTI:// 踩到浮梯
				case MapElements.TYPE_ROAD:// 踩在路上
					if (jumpping) {
						jumpping = false;
						System.out.println("跳跃距离："
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
				case MapElements.TYPE_MONSTEREARTH:// 能踩死的怪
				case MapElements.TYPE_BRICK:// 砖块
					speed_y = SPEED_JUMP_SC;// 跳跃
					setStandPoint(x, mapEl.y + 18);
					mapEl.beKill();
					if (jumpping) {
						jumpping = false;
						System.out.println("跳跃距离："
								+ (game.runDistance - jumpStartPos));
					}
					jumpping = true;
					jumpStartPos = game.runDistance;
					break;
				case MapElements.TYPE_MONSTERAIR:// 闪电怪
				case MapElements.TYPE_THORN:// 刺
					die();
					break;
				case MapElements.TYPE_SPRING:// 弹簧
					setStandPoint(x, mapEl.y + 18);
					strongJump = true;
					if (game.speedMax <= 20)
						game.speedOld = game.speedMax;

					game.speedMax = 50;
					game.speed_run = 30;
					speed_y = SPEED_TANHUANG;

					if (jumpping) {
						jumpping = false;
						System.out.println("跳跃距离："
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
					&& C.rectInsect(rect_top, mapEl.rect)) {// 头顶的碰撞
				mapEl.beKill();
				speed_y = -speed_y;
				return;
			}

			ka = false;
			if (!mapEl.isDead() && C.rectInsect(rect_body, mapEl.rect)) {// 身体的碰撞
				if (mapEl.getType() == MapElements.TYPE_MONSTERAIR
						|| mapEl.getType() == MapElements.TYPE_MONSTEREARTH
						|| mapEl.getType() == MapElements.TYPE_THORN) {// 碰到刺或者怪
					die();

				} else {
					setStandPoint(mapEl.rect.x - game.speedMax * 2 - 20, y);
					ka = true;
				}
			}
		}
	}

	/**
	 * 检测人物是否在障碍下面
	 * 
	 * @return
	 */
	private boolean isUnderBarrier() {
		for (int i = 0; i < game.sizeMapElV; i++) {
			MapElements mapEl = (MapElements) game.v_MapEl.elementAt(i);
			Rect checkRect = new Rect(rect_body.x, y - 50, rect_body.w + 20, 30);

			if (mapEl.getType() == MapElements.TYPE_BARRIER
					&& C.rectInsect(checkRect, mapEl.rect)) {// 身体的碰撞
				return true;
			}
		}
		return false;
	}

	/**
	 * 吃金币
	 */
	private void eatGold() {
		for (int i = game.v_gold.size() - 1; i >= 0; i--) {
			Gold gold = (Gold) game.v_gold.elementAt(i);
			if (C.rectInsect(rect_body, gold.rect)) {// 吃到一个金币
				game.removeGold(gold);
				eatEffect();
			}
		}
	}

	/**
	 * 吃金币效果
	 */
	private void eatEffect() {
		eatEffectFrame = 0;
	}

	/**
	 * 设置站立的点
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
	 * 进入飞行状态
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
	 * 死亡
	 */
	public void die() {
		reLive();// 这里测试死亡直接复活。
	}

	/**
	 * 复活
	 */
	public void reLive() {
		reset();
		goToFly(10);
	}
}
