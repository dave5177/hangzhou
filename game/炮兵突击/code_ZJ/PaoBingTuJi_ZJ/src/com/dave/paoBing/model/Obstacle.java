package com.dave.paoBing.model;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.paoBing.view.Game;

/**
 * @author Administrator 障碍物类
 */
public class Obstacle {

	/**
	 * 所在游戏
	 */
	private Game game;

	/**
	 * 障碍物类型 ：1、石头。 2、墙壁。 3、木桶。 4、木箱。 5、木板。
	 */
	public int type;

	/**
	 * 图片
	 */
	private Image[] a_img_obstacle;

	/**
	 * 碎片图片
	 */
	private Image[] a_img_chip;

	/**
	 * 障碍物的最大生命值
	 */
	private int lifeMax;

	/**
	 * 障碍物的生命值
	 */
	public int life;

	/**
	 * 坐标值（以下面中点为锚点）
	 */
	public int x, y;

	/**
	 * 碰撞宽度和碰撞高度
	 */
	public int width_cln, height_cln;

	/**
	 * 炸开的状态
	 */
	public boolean boom;

	/**
	 * 碎片
	 */
	private Vector v_fragment;

	/**
	 * 爆炸时间
	 */
	private int boomTime;

	/**
	 * 墙壁障碍物的层数
	 */
	private int layer;

	/**
	 * 木板的类型 1、是竖的。0、是横的。
	 */
	private int style;

	/**
	 * 被打后晃动状态
	 */
	private boolean rock;

	/**
	 * 我下面的障碍物
	 */
	private Obstacle nextOst;

	/**
	 * 下落之前的y坐标
	 */
	private int y_offer_down;

	/**
	 * 我上面的要掉下来
	 */
	private boolean upDown;
	
	/**
	 * π字形木板标示，用于style参数
	 */
	public static final int WOOD_DOWN = 100;
	
	/**
	 * u字形木板
	 */
	public static final int WOOD_UP = 101;
	
	/**
	 * T型木板
	 */
	public static final int WOOD_T = 102;

	/**
	 * @param game
	 * @param type 障碍物类型 ：1、石头。 2、墙壁。 3、木桶。 4、木箱。 5、木板
	 * @param x
	 * @param y
	 * @param style 木板的类型 1、是竖的。0、是横的。3、是斜的。
	 * @param layer 墙壁障碍物的层数,至少两层
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
			if(style == WOOD_DOWN || style == WOOD_UP) {//一块木板则在加载图片的时候初始化碰撞宽度和高度
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
	 * 加载图片
	 */
	public void loadImage() {
		if (type == 2) {// 墙壁的图片，要用很多图片拼合而成。
			a_img_obstacle = new Image[4];
			try {
				for (int i = 0; i < a_img_obstacle.length; i++) {
					a_img_obstacle[i] = Image.createImage("/obstacle/brick_"
							+ i + ".png");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type == 5) {// 木板的图片，有很多的角度
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
			if (type == 2) {// 墙壁的图片，要用很多图片拼合而成。
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
	 * 墙的显示
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
	 * 被打到了
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
	 * 初始化碎片
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
	 * 移除前面的图片
	 */
	public void removeImage() {
		if (type == 2) {// 墙壁的图片，要用很多图片拼合而成。

		} else if (type == 5) {// 木板的图片，有很多的角度

		} else {
			a_img_obstacle[0] = null;
			a_img_obstacle = null;
		}
	}

	/**
	 * 加载碎片图片
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
