package com.dave.jpsc.model;

import javax.microedition.lcdui.Graphics;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.tool.Circle;
import com.dave.jpsc.tool.Rect;
import com.dave.jpsc.tool.RectFree;
import com.dave.jpsc.tool.Vector2;
import com.dave.jpsc.view.Game;

public class EnemyCar extends Car {

	/**
	 * 探测矩形（用来探测前面的路是否需要转弯）
	 */
	public RectFree probeRect;

	/**
	 * 致盲后随机转向的时间间隔
	 */
	private int freq_change_rand = 15;

	/**
	 * 下一次时间间隔
	 */
	private int nextFireTime;

	public EnemyCar(Game game, int x, int y, float dir_angle, int type) {
		super(game, x, y, dir_angle, type);
		int prob_x = x + C.cos((int)dir_angle) * 50 / 100000;
		int prob_y = y + C.sin((int)dir_angle) * 50 / 100000;
		probeRect = new RectFree(50, 10, new Vector2(prob_x, prob_y), (int)dir_angle);

		nextFireTime = C.R.nextInt(150) + 50;
		// probeRect = new Rect(prob_x - 5, prob_y - 5, 10, 10);
	}

	public EnemyCar(Game game, int x, int y, float dir_angle, Player user) {
		this(game, x, y, dir_angle, user.cars[user.mainCar][0]);
	}

	public EnemyCar(Game game, int x, int y, float dir_angle, int type, int floor) {
		this(game, x, y, dir_angle, type);
		this.floor = floor;
	}

	public EnemyCar(Game game, int x, int y, int dir_angle, Player user,
			int floor) {
		this(game, x, y, dir_angle, user);
		this.floor = floor;
	}

	public void show(Graphics g) {
		super.show(g);

		// ////////////////////////调试信息/////////////////////////////
		if (CanvasControl.DEBUG) {
			g.setClip(0, 0, 640, 530);
			g.setColor(0, 0, 0);
			probeRect.show(game.x_map, game.y_map, g);
		}
	}

	/**
	 * 更新探测矩形
	 */
	public void updateProbeRect() {
		int x_center = x + C.cos((int) dir_angle) * 50 / 100000;
		int y_center = y + C.sin((int) dir_angle) * 50 / 100000;
		probeRect.setCenterPos(x_center, y_center);
		probeRect.setAngle(dir_angle);
		// C.out(probeRect.x + "\n" + probeRect.y);
	}

	/**
	 * 探测矩形与墙碰撞检测
	 * 
	 * @return
	 */
	public boolean clsProbeWithWall() {
		if (floor == 0) {
			for (int i = game.rects.size() - 1; i >= 0; i--) {
				Rect rect = (Rect) game.rects.elementAt(i);
				if (C.rectInsect(probeRect.getBoundingRect(), rect)) {
					if (C.freeRectIsct(probeRect, rect))
						return true;
				}
			}

			for (int i = game.freeRects.size() - 1; i >= 0; i--) {
				RectFree rectF = (RectFree) game.freeRects.elementAt(i);
				if (C.rectInsect(probeRect.getBoundingRect(),
						rectF.getBoundingRect())) {
					if (C.freeRectIsct(rectF, probeRect))
						return true;
				}
			}

			for (int i = game.circles.size() - 1; i >= 0; i--) {
				Circle circle = (Circle) game.circles.elementAt(i);
				if (C.ciclFreeRectInst(circle, probeRect)) {
					return true;
				}
			}
		} else if (floor == 1) {
			for (int i = game.rects_f1.size() - 1; i >= 0; i--) {
				Rect rect = (Rect) game.rects_f1.elementAt(i);
				if (C.rectInsect(probeRect.getBoundingRect(), rect)) {
					if (C.freeRectIsct(probeRect, rect))
						return true;
				}
			}

			for (int i = game.freeRects_f1.size() - 1; i >= 0; i--) {
				RectFree rectF = (RectFree) game.freeRects_f1.elementAt(i);
				if (C.rectInsect(probeRect.getBoundingRect(),
						rectF.getBoundingRect())) {
					if (C.freeRectIsct(rectF, probeRect))
						return true;
				}
			}

			for (int i = game.circles_f1.size() - 1; i >= 0; i--) {
				Circle circle = (Circle) game.circles_f1.elementAt(i);
				if (C.ciclFreeRectInst(circle, probeRect)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 与别的车碰撞检测。
	 * 
	 * @return
	 */
	public boolean clsProbeWithOtherCar() {

		// 与我的车的碰撞
//		if (game.myCar.rejectTime > 0) {
//		}
		
		if (game.myCar.floor == floor && game.myCar.state == Car.STATE_LANCHED
				&& C.freeRectIsct(game.myCar.rectFree, probeRect)) {
			return true;
		}
		
		// 与别人的车的碰撞
		for (int i = game.enemyCars.size() - 1; i >= 0; i--) {
			EnemyCar ec = ((EnemyCar) game.enemyCars.elementAt(i));
			if (ec == this) {
				continue;
			} else {
				if (ec.floor == floor && ec.state == Car.STATE_LANCHED
						&& C.rectInsect(probeRect.getBoundingRect(), ec.advRect)) {
					if (C.freeRectIsct(ec.rectFree, probeRect)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * ai自动控制逻辑
	 */
	public void autoCtrl() {
		autoFire();

		autoChangeDir();

		if (blindTime > 0) {// 被致盲了（每过10~20帧随机转变一下方向）
			freq_change_rand--;
			if (freq_change_rand < 0) {
				freq_change_rand = C.R.nextInt(10) + 10;

				int dir = C.R.nextInt(2);
				if (dir == 1)
					changeDir(22.5f);
				else if (dir == 0)
					changeDir(-22.5f);
			}
		}

		if (dirErro) {
			resetDir();
		}

		if (hitWall) {
			if(dir_angle < rect_track.angle) {
				changeDir(22.5f);
			} else if(dir_angle > rect_track.angle){
				changeDir(-22.5f);
			} else {
				changeDir(22.5f);
				if(clsWithWall())
					changeDir(-45.0f);
			}
			hitWall = false;
		}
	}

	private void autoChangeDir() {
		if (blindTime <= 0) {
			if (clsProbeWithWall()) {// 自动转弯
				changeDir(22.5f);
				updateProbeRect();
				if (clsProbeWithWall()) {
					changeDir(-45f);
					updateProbeRect();
					if (clsProbeWithOtherCar()) {
						autoBrake();
						return;
					}
					return;
				} else if(clsProbeWithOtherCar()) {
					autoBrake();
					return;
				}
				return;
			} else if(clsProbeWithOtherCar()){//前面有车子
				changeDir(22.5f);
				updateProbeRect();
				if(clsProbeWithWall() || clsProbeWithOtherCar()) {
					changeDir(-45f);
					updateProbeRect();
					if(clsProbeWithWall() || clsProbeWithOtherCar()) {
						changeDir(22.5f);
						updateProbeRect();
						autoBrake();
						return;
					}
				}
			}
		}
	}

	/**
	 * 自动攻击
	 */
	private void autoFire() {
		nextFireTime--;
		if (nextFireTime < 0) {
			nextFireTime = C.R.nextInt(150) + 50;
			if (C.R.nextInt(100) < 20)
				fire();
		}
	}

	/**
	 * 前面被堵的时候自动刹车
	 */
	private void autoBrake() {
		if (speed > 0)
			speed--;
	}

	public void logic() {
		super.logic();
		updateProbeRect();
		autoCtrl();
	}

}
