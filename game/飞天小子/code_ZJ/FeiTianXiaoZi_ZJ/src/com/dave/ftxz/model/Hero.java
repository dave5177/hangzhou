package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Dialog;
import com.dave.ftxz.view.Game;

public class Hero implements Model {
	
	/**
	 * Ӣ��������Ϸ
	 */
	protected Game game;
	
	/**
	 * ��0~3���׵�������С�ӣ�
	 */
	public int type;
	
	/**
	 * ����
	 */
	public int x, y;
	
	/**
	 * ��ײ����
	 */
	public Rect rect_clsn;
	
	/**
	 * ���еĳ���
	 */
	public Pet pet;

	/**
	 * ���µļ���1������2�����ң�
	 */
	private int codeKeyDown;
	
	/**
	 * �������ʱ������
	 */
	protected int time_fire;
	
	/**
	 * �����ٶȣ�Ĭ��Ϊ10����/���루ÿ10���ش���1�ף���
	 */
	public int speed_fly = 2;

	/**
	 * ��������
	 */
	public boolean beHit;

	/**
	 * ����������֡
	 */
	private int frame_beHit;

	/**
	 * ��̵���˸����
	 */
	private int blink_sprint;

	/**
	 * ��̶���֡
	 */
	private int frame_sprint;

	/**
	 * �޵�Ч����˸����
	 */
	private int blink_protected;

	/**
	 * ����ʯЧ��֡
	 */
	private int frame_attract;

	/**
	 * ����ʯЧ����˸����
	 */
	private int blink_attract;

	/**
	 * �ɸ��������С�Ӷ���һ��
	 */
	private int numb_relive;

	/**
	 * ���ڸ���
	 */
	private boolean goin;
	
	/**
	 * �����������λ�����룩
	 */
	protected static final int INTERVAL_FIRE = 2;

	/**
	 * ���ҿ����ƶ����ٶ�
	 */
	private static final int moveSpeed = 10;

	public Hero(Game game, int type) {
		this.game = game;
		this.type = type;
		
		init();
	}

	private void init() {
		y = 600;
		x = 320;
		rect_clsn = new Rect(x - 20, y - 45, 40, 40);
		goin = true;
		if(type != 5)game.treasure_info[2][2] = 30;
		
		if(type == 3)
			numb_relive = 1;
	}

	public void show(Graphics g) {
		if (game.treasure_info[1][2] > 0) {
			for (int i = 0; i < 4; i++) {
				g.drawImage(game.img_sprint, x, y - 30 - i * 119, Graphics.HCENTER | Graphics.BOTTOM);
			}
		}
		g.drawImage(game.img_airship, x, y - 20, Graphics.VCENTER | Graphics.HCENTER);
		g.drawImage(game.img_hero, x, y, Graphics.HCENTER | Graphics.BOTTOM);
		
		if(beHit)
			g.drawImage(game.a_img_beHit_hero[frame_beHit], x, y - 20, Graphics.VCENTER | Graphics.HCENTER);
		
		if ( game.treasure_info[2][2] > 50) {//�޵�
			g.drawImage(game.img_protected, x, y - 20, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (game.treasure_info[2][2] > 0) {// �޵���˸
			if (blink_protected < 4) {
				g.drawImage(game.img_protected, x, y - 20, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}

		if ( game.treasure_info[3][2] > 50) {//����ʯ
			g.drawImage(game.a_img_attract[frame_attract], x, y - 20, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (game.treasure_info[3][2] > 0) {//����ʯ��˸
			if (blink_attract < 4) {
				g.drawImage(game.a_img_attract[frame_attract], x, y - 20, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}
		
		if ( game.treasure_info[1][2] > 50) {//���
			g.drawImage(game.a_img_sprint[frame_sprint], x, y, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (game.treasure_info[1][2] > 0) {// �����˸
			if (blink_sprint < 4) {
				g.drawImage(game.a_img_sprint[frame_sprint], x, y, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}
		
		if(pet != null) pet.show(g);
//		g.drawRect(rect_clsn.x, rect_clsn.y, rect_clsn.w, rect_clsn.h);
	}

	public void keyPressed(int keyCode) {
		if(goin)
			return;
		
		switch (keyCode) {
		case C.KEY_LEFT:
			codeKeyDown = 1;
			if(pet != null)
				pet.moveToRight();
			break;
		case C.KEY_RIGHT:
			codeKeyDown = 2;
			if(pet != null)
				pet.moveToLeft();
			break;
		
		default:
			break;
		}
	}

	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT:
			if(codeKeyDown == 1)
				codeKeyDown = 0;
			break;
		case C.KEY_RIGHT:
			if(codeKeyDown == 2)
				codeKeyDown = 0;
			break;
		default:
			break;
		}
	}
	
	public void move() {
		if(codeKeyDown == 1) {
			if(x > 170) {
				x -= moveSpeed;
				rect_clsn.x -= moveSpeed;
				if(pet != null && pet.moveToSide == 0) {
					pet.x -= moveSpeed;
					pet.rect_clsn.x -= moveSpeed;
				}
			}
		} else if(codeKeyDown == 2){
			if(x < 470) {
				x += moveSpeed ;
				rect_clsn.x += moveSpeed;
				if(pet != null && pet.moveToSide == 0) {
					pet.x += moveSpeed;
					pet.rect_clsn.x += moveSpeed;
				}
			}
		}
	}

	public void logic() {
		fly();
		
		if(goin) {
			if(y > 500) {
				y -= 10;
				rect_clsn.y -= 10;
			} else {
				y = 500;
				rect_clsn.y = 500 - 45;
				goin = false;
				if(CanvasControl.goodsNumber[3] > 0) {
					new Pet(game, this);
					CanvasControl.goodsNumber[3] --;
					game.canvasControl.saveParam();
				}
			}
		
			return;
		}
		
		move();
		fire();
		if(pet != null) pet.logic();
		
		if(beHit) {
			frame_beHit ++;
			if(frame_beHit > 1) {//�Ѿ���������ʾ�����Ի���
				frame_beHit = 0;
				beHit = false;
				pet = null;
				
				if(type == 3 && numb_relive > 0) {
					numb_relive --;
					relive();
				} else {
					CanvasControl.goodsIndex = 9;
					game.canvasControl.setGoBackView(game);
					game.canvasControl.setView(game.canvasControl.nullView);
					game.removeResource();
					game.canvasControl.setView(new Dialog(game.canvasControl, 7, game));
				}
			}
		}
		
		if(game.treasure_info[1][2] > 0) {//���
			blink_sprint ++;
			if(blink_sprint >= 8)
				blink_sprint = 0;
			
			frame_sprint ++;
			if(frame_sprint > 3)
				frame_sprint = 0;
		}
		if(game.treasure_info[2][2] > 0) {//�޵�
			blink_protected ++;
			if(blink_protected >= 8)
				blink_protected = 0;
		}
		
		if(game.treasure_info[3][2] > 0) {//����ʯ
			blink_attract ++;
			if(blink_attract == 8)
				blink_attract = 0;
			
			frame_attract ++;
			if(frame_attract > 3)
				frame_attract = 0;
		}
	}

	/**
	 * ����
	 */
	public void relive() {
		game.treasure_info[2][2] = 30;
		y = 600;
		x = 320;
		rect_clsn.x = 320 - 20;
		rect_clsn.y = 600 - 45;
		goin = true;
	}

	/**
	 * ��ǰ��
	 */
	public void fly() {
		game.y_map += speed_fly;
		CanvasControl.distance += speed_fly / 2;
		if(game.y_map > 530) 
			game.y_map -= 902;
		
	}

	public void fire() {
		game.canvasControl.playerHandler.playByIndex(2);
		time_fire ++;
		if(time_fire >= INTERVAL_FIRE) {
			time_fire = 0;
			game.v_bullet.addElement(new Bullet(game, x, y - 30, game.power_level));
		}
	}

	/**
	 * ����������������
	 */
	public void beHit() {
		if(game.treasure_info[1][2] <= 0 && game.treasure_info[2][2] <= 0)//��̺��޵в����ܵ��˺�
			beHit = true;
	}

	/**
	 * ��ñ���
	 * @param type ���������
	 */
	public void getTreasure(int type) {
		game.treasure_info[type][2] = 300;
		switch (type) {
		case 0://����
			break;
		case 1://���
			game.treasure_info[type][2] = 150;
			speed_fly = 32;
			break;
		case 2://�޵�
			
			break;
		case 3://����ʯ
			
			break;

		default:
			break;
		}
	}

}
