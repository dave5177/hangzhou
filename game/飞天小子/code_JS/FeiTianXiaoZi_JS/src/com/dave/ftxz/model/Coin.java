package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

public class Coin implements Model {

	/**
	 * ������Ϸ
	 */
	Game game;

	/**
	 * ����
	 */
	private int x, y;

	/**
	 * ��ײ����
	 */
	public Rect rect_clsn;

	/**
	 * y�᷽���ٶ�
	 */
	private int speed_y, speed_x;

	/**
	 * ���Ե����ұߵ�״̬
	 */
	private boolean ate;

	/**
	 * ����Ľ��ֵ
	 */
	private static final int value = 2;

	public Coin(Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;

		rect_clsn = new Rect(x - 12, y - 24, 24, 24);

		speed_y = C.R.nextInt(5) - 20;
		speed_x = C.R.nextInt(21) - 10;
	}

	public void show(Graphics g) {
		g.drawImage(game.img_coin, x, y, Graphics.BOTTOM | Graphics.HCENTER);
		
//		g.drawString("y---" + y, x, y - 20, Graphics.BOTTOM | Graphics.HCENTER);
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void logic() {
		if (ate) {
			speed_y -= 5;
			speed_x += 5;
			x += speed_x;
			y -= speed_y;
			if (x > 600) {
				if (game.hero.type == 1) {// ��ҷ���С��
					game.num_coin += value;
					CanvasControl.coin_total += value;
				}
				game.num_coin += value;
				CanvasControl.coin_total += value;
				removeMe();
				if (game.num_coin >= 100 && !game.trans) {
					game.coinTransformDistance();
//					CanvasControl.distance += 500;
					game.num_coin -= 100;
				}
			}
		} else {
			fire();
			falling();
		}
	}

	/**
	 * ����
	 */
	private void falling() {
		if (game.treasure_info[3][2] > 0 || game.hero.type == 2) {// ����ʯЧ�����߱�С��Ч��
			if (x > game.hero.x + 20) {
				x -= 10;
				rect_clsn.x -= 10;
			} else if (x < game.hero.x - 20) {
				x += 10;
				rect_clsn.x += 10;
			}

			if (y > game.hero.y + 20) {
				y -= 30;
				rect_clsn.y -= 30;
			} else if (y < game.hero.y - 20) {
				y += 30;
				rect_clsn.y += 30;
			}

		} else {
			y += speed_y;
			rect_clsn.y += speed_y;
			x += speed_x;
			rect_clsn.x += speed_x;

			if (speed_y < 80) {
				speed_y += 5;
			}
			if (speed_x > 0) {
				speed_x--;
			} else if (speed_x < 0) {
				speed_x++;
			}

			if (y > 600)
				removeMe();
		}
	}

	public void fire() {// ���Ե�
		if (!ate && C.rectInsect(rect_clsn, game.hero.rect_clsn)) {
			ate = true;
			game.canvasControl.playerHandler.playByIndex(3);
			x = game.hero.x;
			y = 400;
			speed_x = C.R.nextInt(10) + 50;
			speed_y = C.R.nextInt(10) + 60;
			// if(game.hero.type == 1) {//��ҷ���С��
			// game.num_coin += value;
			// CanvasControl.coin_total += value;
			// }
			// game.num_coin += value;
			// CanvasControl.coin_total += value;
			// removeMe();
			// if(game.num_coin >= 100) {
			// CanvasControl.distance += 500;
			// game.num_coin -= 100;
			// }
		}
	}

	private void removeMe() {
		game.v_coin.removeElement(this);
	}

}
