package com.dave.paoBing.model;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.paoBing.view.Game;

/**
 * @author Administrator �ϰ�����
 */
public class Obstacle {

	/**
	 * ������Ϸ
	 */
	private Game game;

	/**
	 * �ϰ������� ��1��ʯͷ�� 2��ǽ�ڡ� 3��ľͰ�� 4��ľ�䡣 5��ľ�塣
	 */
	public int type;

	/**
	 * ͼƬ
	 */
	private Image[] a_img_obstacle;

	/**
	 * ��ƬͼƬ
	 */
	private Image[] a_img_chip;

	/**
	 * �ϰ�����������ֵ
	 */
	private int lifeMax;

	/**
	 * �ϰ��������ֵ
	 */
	public int life;

	/**
	 * ����ֵ���������е�Ϊê�㣩
	 */
	public int x, y;

	/**
	 * ��ײ��Ⱥ���ײ�߶�
	 */
	public int width_cln, height_cln;

	/**
	 * ը����״̬
	 */
	public boolean boom;

	/**
	 * ��Ƭ
	 */
	private Vector v_fragment;

	/**
	 * ��ըʱ��
	 */
	private int boomTime;

	/**
	 * ǽ���ϰ���Ĳ���
	 */
	private int layer;

	/**
	 * ľ������� 1�������ġ�0���Ǻ�ġ�
	 */
	private int style;

	/**
	 * �����ζ�״̬
	 */
	private boolean rock;

	/**
	 * ��������ϰ���
	 */
	private Obstacle nextOst;

	/**
	 * ����֮ǰ��y����
	 */
	private int y_offer_down;

	/**
	 * �������Ҫ������
	 */
	private boolean upDown;
	
	/**
	 * ������ľ���ʾ������style����
	 */
	public static final int WOOD_DOWN = 100;
	
	/**
	 * u����ľ��
	 */
	public static final int WOOD_UP = 101;
	
	/**
	 * T��ľ��
	 */
	public static final int WOOD_T = 102;

	/**
	 * @param game
	 * @param type �ϰ������� ��1��ʯͷ�� 2��ǽ�ڡ� 3��ľͰ�� 4��ľ�䡣 5��ľ��
	 * @param x
	 * @param y
	 * @param style ľ������� 1�������ġ�0���Ǻ�ġ�3����б�ġ�
	 * @param layer ǽ���ϰ���Ĳ���,��������
	 */
	public Obstacle(Game game, int type, int x, int y, int style, int layer, Obstacle nextOst) {
		this.type = type;
		this.game = game;
		this.x = x;
		this.y = y;
		this.style = style;
		this.layer = layer;
		this.nextOst = nextOst;

		init();
	}

	private void init() {
		switch (type) {
		case 1:
			lifeMax = 100;
			width_cln = 44;
			height_cln = 44;
			break;
		case 2:
			lifeMax = 50;
			width_cln = 40;
			height_cln = (layer - 2) * 9 + 37;
			break;
		case 3:
			lifeMax = 50;
			width_cln = 50;
			height_cln = 60;
			break;
		case 4:
			lifeMax = 50;
			width_cln = 50;
			height_cln = 47;
			break;
		case 5:
			if(style == WOOD_DOWN || style == WOOD_UP) {//һ��ľ�����ڼ���ͼƬ��ʱ���ʼ����ײ��Ⱥ͸߶�
				lifeMax = 100;
				width_cln = 94;
				height_cln = 116;
			} else if(style == WOOD_T) {
				lifeMax = 100;
				width_cln = 97;
				height_cln = 225;
			} else {
				lifeMax = 50;
			}
			break;
		default:
			break;
		}

		y_offer_down = y;
		life = lifeMax;

		loadImage();
	}

	/**
	 * ����ͼƬ
	 */
	public void loadImage() {
		if (type == 2) {// ǽ�ڵ�ͼƬ��Ҫ�úܶ�ͼƬƴ�϶��ɡ�
			a_img_obstacle = new Image[4];
			try {
				for (int i = 0; i < a_img_obstacle.length; i++) {
					a_img_obstacle[i] = Image.createImage("/obstacle/brick_"
							+ i + ".png");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type == 5) {// ľ���ͼƬ���кܶ�ĽǶ�
			if(style == WOOD_DOWN || style == WOOD_UP || style == WOOD_T) {
				a_img_obstacle = new Image[2];
				try {
					for (int i = 0; i < 2; i++) {
						a_img_obstacle[i] = Image.createImage("/obstacle/"
								+ CanvasControl.mission + "_wood_" + i + ".png");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				a_img_obstacle = new Image[1];
				try {
					a_img_obstacle[0] = Image.createImage("/obstacle/"
							+ CanvasControl.mission + "_wood_" + style + ".png");
					
					if(CanvasControl.mission == 3) {
						width_cln = 40;
						height_cln = 180;
					} else {
						width_cln = a_img_obstacle[0].getWidth();
						height_cln = a_img_obstacle[0].getHeight();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			a_img_obstacle = new Image[1];
			try {
				a_img_obstacle[0] = Image.createImage("/obstacle/obstacle_"
						+ type + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void show(Graphics g) {
		if (boom) {
			for (int i = 0; i < v_fragment.size(); i++) {
				((Fragment) v_fragment.elementAt(i)).show(g);
			}
		} else {
			if (type == 2) {// ǽ�ڵ�ͼƬ��Ҫ�úܶ�ͼƬƴ�϶��ɡ�
				showWall(g);
			} else if (type == 5) {
				if(style == WOOD_DOWN) {
					g.drawImage(a_img_obstacle[1], x - 40 + game.x_map, y, Graphics.BOTTOM
							| Graphics.HCENTER);
					g.drawImage(a_img_obstacle[1], x + 40 + game.x_map, y, Graphics.BOTTOM
							| Graphics.HCENTER);
					g.drawImage(a_img_obstacle[0], x + game.x_map, y - 106, Graphics.BOTTOM
							| Graphics.HCENTER);
				} else if(style == WOOD_UP) {
					g.drawImage(a_img_obstacle[1], x - 40 + game.x_map, y - 15, Graphics.BOTTOM
							| Graphics.HCENTER);
					g.drawImage(a_img_obstacle[1], x + 40 + game.x_map, y - 15, Graphics.BOTTOM
							| Graphics.HCENTER);
					g.drawImage(a_img_obstacle[0], x + game.x_map, y, Graphics.BOTTOM
							| Graphics.HCENTER);
				} else if(style == WOOD_T) {
					g.drawImage(a_img_obstacle[1], x + game.x_map, y, Graphics.BOTTOM
							| Graphics.HCENTER);
					g.drawImage(a_img_obstacle[0], x + 31 + game.x_map, y - 217, Graphics.BOTTOM
							| Graphics.HCENTER);
				} else {
					g.drawImage(a_img_obstacle[0], x + game.x_map, y, Graphics.BOTTOM
							| Graphics.HCENTER);
				}
			} else {
				g.drawImage(a_img_obstacle[0], x + game.x_map, y, Graphics.BOTTOM
						| Graphics.HCENTER);
			}
		}
	}
	
	/**
	 * ǽ����ʾ
	 * @param g
	 */
	public void showWall(Graphics g) {
		g.drawImage(a_img_obstacle[3], x - 20 + game.x_map, y, Graphics.BOTTOM
				| Graphics.LEFT);
		for (int i = 0; i < (layer - 3) / 2 + 1; i++) {
			g.drawImage(a_img_obstacle[2], x - 20 + game.x_map, y - 19 - i * 18, Graphics.BOTTOM
				| Graphics.LEFT);
		}
		for (int i = 0; i < (layer - 2) / 2; i++) {
			g.drawImage(a_img_obstacle[1], x - 20 + game.x_map, y - 28 - i * 18, Graphics.BOTTOM
					| Graphics.LEFT);
		}
		g.drawImage(a_img_obstacle[0], x - 20 + game.x_map, y - 19 - (layer - 2) * 9, Graphics.BOTTOM
					| Graphics.LEFT);
	}

	/**
	 * ������
	 * 
	 * @param power
	 */
	public void beHit(int power, int soldier) {
		life -= power;
		if (life <= 0) {
			boom = true;
			upDown = true;
			y_offer_down = height_cln;
			removeImage();
			loadChipImage();
			initTrips();
		} else {
			if(soldier == 1) {
				x += 5;
			} else if(soldier == 2) {
				x -= 5;
			}
//			rock = true;
		}
	}

	/**
	 * ��ʼ����Ƭ
	 */
	private void initTrips() {
		switch (type) {
		case 1:
			v_fragment = new Vector();
			v_fragment.addElement(new Fragment(1, x - 20, y - 40, 2, C.R
					.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x + 20, y - 40, 8, C.R
					.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x - 20, y, 4,
					C.R.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x + 20, y, 6,
					C.R.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x, y - 20, 7,
					C.R.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x, y, 5, C.R.nextInt(3) + 1,
					a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x - 20, y - 20, 3, C.R
					.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			v_fragment.addElement(new Fragment(1, x, y - 40, 1,
					C.R.nextInt(3) + 1, a_img_chip[C.R.nextInt(2)], game));
			break;
		case 2:
			v_fragment = new Vector();
			for (int i = 0; i < layer; i++) {
				v_fragment.addElement(new Fragment(1, x, y - i * 12, C.R.nextInt(8) + 1, C.R
						.nextInt(3) + 1, a_img_chip[C.R.nextInt(2) + 1], game));
			}
			break;
		case 3:
			v_fragment = new Vector();
			for (int i = 0; i < 20; i++) {
				v_fragment.addElement(new Fragment(type, x + 25
						- C.R.nextInt(50), y - C.R.nextInt(50),
						C.R.nextInt(8) + 1, C.R.nextInt(3) + 1, a_img_chip[C.R
								.nextInt(4)], game));
			}
			break;
		case 4:
			v_fragment = new Vector();
			for (int i = 0; i < 15; i++) {
				v_fragment.addElement(new Fragment(type, x + 20
						- C.R.nextInt(40), y - C.R.nextInt(40),
						C.R.nextInt(8) + 1, C.R.nextInt(3) + 1, a_img_chip[C.R
								.nextInt(4)], game));
			}
			break;
		case 5:
			v_fragment = new Vector();
			for (int i = 0; i < 20; i++) {
				v_fragment.addElement(new Fragment(type, x + 4 - C.R.nextInt(5),
						y - height_cln / 2 + 4 - C.R.nextInt(5),
						C.R.nextInt(8) + 1, C.R.nextInt(3) + 1, a_img_chip[C.R
								.nextInt(4)], game));
			}
			break;

		default:
			break;
		}

	}

	/**
	 * �Ƴ�ǰ���ͼƬ
	 */
	public void removeImage() {
		if (type == 2) {// ǽ�ڵ�ͼƬ��Ҫ�úܶ�ͼƬƴ�϶��ɡ�

		} else if (type == 5) {// ľ���ͼƬ���кܶ�ĽǶ�

		} else {
			a_img_obstacle[0] = null;
			a_img_obstacle = null;
		}
	}

	/**
	 * ������ƬͼƬ
	 */
	private void loadChipImage() {
		try {
			switch (type) {
			case 2:
				a_img_chip = new Image[4];
				for (int i = 0; i < a_img_chip.length; i++) {
					a_img_chip[i] = Image.createImage("/obstacle/brick_"
							+ i + ".png");
				}
				break;
			case 1:
				a_img_chip = new Image[2];
				a_img_chip[0] = Image.createImage("/obstacle/stone_0.png");
				a_img_chip[1] = Image.createImage("/obstacle/stone_1.png");
				break;
			default:
				a_img_chip = new Image[4];
				for (int i = 0; i < 4; i++) {
					a_img_chip[i] = Image.createImage("/obstacle/wood_chips_"
							+ i + ".png");
				}
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logic() {
		if (boom) {
			boomTime++;
			if (boomTime > 5) {
				v_fragment.removeAllElements();
				game.v_obstacle.removeElement(this);
			}
			for (int i = 0; i < v_fragment.size(); i++) {
				((Fragment) v_fragment.elementAt(i)).move();
			}
		} else if(rock) {
			x += 5;
			rock = false;
		}
		
		if(nextOst != null && nextOst.upDown) {
			y += nextOst.y_offer_down;
			y_offer_down = nextOst.y_offer_down;
			upDown = true;
			nextOst.upDown = false;
			if(nextOst.boom) {
				nextOst = nextOst.nextOst;
			}
		}
	}

}
