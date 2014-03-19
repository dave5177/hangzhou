package com.dave.paoBing.view;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.model.Bullet;
import com.dave.paoBing.model.Enemy;
import com.dave.paoBing.model.Hero;
import com.dave.paoBing.model.Missile;
import com.dave.paoBing.model.Obstacle;
import com.dave.paoBing.model.Pier;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;

public class Game implements Showable {

	private CanvasControl canvasControl;
	
	public int x_map;
	
	public Image img_map;
	private Image img_bottom_bar;
	private Image img_point_shooter;
	private Image img_number;
	public Image[] a_img_blood_bar;
	
	public Image[] a_img_point;
	public Image[][] a_img_hero;
	
	public Image img_pier;
	
	private Image img_weak;
	private Image img_word_weak;
	
	/**
	 * 子弹数量
	 */
	public static int remain_missile;

	/**
	 * 双威力子弹数，每次购买的时候将当前的所有剩余的子弹变为双威力子弹
	 */
	public static int doublePower;
	
	/**
	 * 血条的显示总长度
	 */
	public final static int LENGTH_BLOOD_BAR = 86;
	
	/**
	 * 保存树墩。
	 */
	public Vector v_pier;
	
	/**
	 * 保存敌人
	 */
	public Vector v_enemy;
	
	/**
	 * 保存障碍物
	 */
	public Vector v_obstacle;
	
	/**
	 * 当前射击者的下标值。
	 *  0代表自己的英雄，其他数字代表敌人。
	 */
	public static int shooterIndex;

	/**
	 * 没关通过后英雄剩下的生命值
	 */
	public static int remainLife;
	
	/**
	 * 树墩坐标数组
	 */
	private int[][] a_point_pier;
	
	/**
	 * 树墩数量
	 */
	public int count_pier;
	
	/**
	 * 敌人的数量
	 */
	public int count_enemy;
	
	/**
	 * 障碍物的数量。
	 */
	public int count_obstacle;

	/**
	 * 你的英雄
	 */
	public Hero hero;
	
	/**
	 * 一个导弹
	 */
	public Missile missile;

	/**
	 * 上下跳动值
	 */
	private int y_move;

	/**
	 * 残血效果图片显示控制
	 */
	private int weakControl;

	/**
	 * 显示残血效果
	 */
	public boolean showWeak;

	/**
	 * 1键加血文字跳动
	 */
	private int weak_w_y;

	/**
	 * 正在射击的敌人
	 */
	public Enemy shoottingEnemy;

	/**
	 * 敌人产生的子弹
	 */
	public Bullet bullet;

	private boolean shootterDead;

	/**
	 * 每关开始，展示地图
	 */
	public boolean showMap;

	/**
	 * 地图显示控制
	 */
	private int mapControl = -1;

	private Image img_word_bullet;

	private Image img_word_mission;

	/**
	 * 往左拉动屏幕
	 */
	private boolean moveMapRight;

	/**
	 * 往右拉屏
	 */
	private boolean moveMapLeft;

	public Game(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		v_pier = new Vector();
		v_enemy = new Vector();
		v_obstacle = new Vector();
		
		canvasControl.playerHandler.stopByIndex(0);
		canvasControl.setNeedRepaint(false);
		
		initMession();
	}
	
	/**
	 * 初始化关卡
	 */
	public void initMession(){
		v_pier.removeAllElements();
		v_obstacle.removeAllElements();
		v_enemy.removeAllElements();
		
		Obstacle obstacle;
		switch(CanvasControl.mission) {
		case 1:
			int[][] temp_point = {
					{54, 357},
					{373, 231},
					{858, 325},
					{950, 474}
			};
			a_point_pier = temp_point;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			
			count_pier = v_pier.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, 0, 1, Enemy.CENTER_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, 1, 1, Enemy.CENTER_PIER));
			
			count_enemy = v_enemy.size();
			
			v_obstacle.addElement(new Obstacle(this, 1, 393, 200, 0, 0, null));
			v_obstacle.addElement(new Obstacle(this, 1, 403, 160, 0, 0, null));
			count_obstacle = v_obstacle.size();
			break;
		case 2:
			int[][] temp_point2 = {
					{55, 330},
					{348, 330},
					{430, 330},
					{865, 310}
			};
			a_point_pier = temp_point2;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			
			count_pier = v_pier.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, 0, 1, Enemy.CENTER_PIER));
			
			count_enemy = v_enemy.size();
			
			obstacle = new Obstacle(this, 4, 358, 300, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 338, 253, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			v_obstacle.addElement(new Obstacle(this, 1, 430, 300, 0, 0, null));
			count_obstacle = v_obstacle.size();
			break;
		case 3:
			int[][] temp_point3 = {
					{68, 376},
					{731, 223},
					{731, 355},
					{815, 355}//有敌人2
			};
			a_point_pier = temp_point3;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, 1, 1, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			
			v_obstacle.addElement(new Obstacle(this, 2, 710, 325, 0, 10, null));
			v_obstacle.addElement(new Obstacle(this, 5, 805, 325, 3, 0, null));
			count_obstacle = v_obstacle.size();
			break;
		case 4:
			int[][] temp_point4 = {
					{52, 358},
					{418, 283},
					{851, 201},//有敌人2
					{795, 472},//有敌人1
					{41, 116},
					{123, 116}
			};
			a_point_pier = temp_point4;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -1, 2, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, 2, 1, 0));
			count_enemy = v_enemy.size();
			
			v_obstacle.addElement(new Obstacle(this, 3, 418, 253, 0, 10, null));
			v_obstacle.addElement(new Obstacle(this, 4, 61, 80, 3, 0, null));
			v_obstacle.addElement(new Obstacle(this, 4, 123, 80, 3, 0, null));
			count_obstacle = v_obstacle.size();
			break;
		case 5:
			int[][] temp_point5 = {
					{46, 446},
					{41, 154},
					{713, 162},//有敌人1
					{788, 446},//有敌人1
					{881, 480},
					{939, 480}
			};
			a_point_pier = temp_point5;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 5, 883, 332, Obstacle.WOOD_DOWN, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 5, 883, 451, Obstacle.WOOD_DOWN, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 883, 335, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			v_obstacle.addElement(new Obstacle(this, 4, 883, 452, 0, 0, null));
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -1, 1, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, 1, 1, 0));
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(0), -2, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 6:
			int[][] temp_point6 = {
					{55, 357},
					{160, 379},
					{755, 328},//有敌人1
					{899, 189},//有敌人1
					{847, 489}//有敌人1
			};
			a_point_pier = temp_point6;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			v_obstacle.addElement(new Obstacle(this, 3, 160, 349, 0, 0, null));
			v_obstacle.addElement(new Obstacle(this, 4, 939, 159, 0, 0, null));
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -1, 1, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -2, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, 1, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 7:
			int[][] temp_point7 = {
					{43, 344},
					{678, 150},
					{764, 150},
					{854, 150},
					{944, 150},
					{723, 353},
					{923, 353},
					{676, 318}
			};
			a_point_pier = temp_point7;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			v_obstacle.addElement(new Obstacle(this, 3, 825, 126, 0, 0, null));
			v_obstacle.addElement(new Obstacle(this, 5, 825, 324, 0, 0, null));
			v_obstacle.addElement(new Obstacle(this, 2, 664, 287, 0, 14, null));
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(1), 1, 3, 0));
			count_enemy = v_enemy.size();
			break;
		case 8:
			int[][] temp_point8 = {
					{52, 407},
					{298, 398},
					{823, 413},
					{760, 224},
					{850, 224},
					{940, 224}
			};
			a_point_pier = temp_point8;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 1, 300, 366, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 314, 322, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 295, 278, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 300, 234, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 318, 187, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, 1, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 9:
			int[][] temp_point9 = {
					{56, 409},
					{297, 369},
					{322, 369},
					{851, 127},
					{853, 276},
					{856, 409}
			};
			a_point_pier = temp_point9;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 4, 277, 341, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 277, 294, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 277, 247, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 3, 342, 344, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 324, 284, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -2, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, -1, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(5), null, 1, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 10:
			int[][] temp_point10 = {
					{47, 384},
					{735, 110},
					{698, 342},
					{914, 335},
					{914, 391},
					{914, 447},
					{703, 509},
					{786, 509},
					{850, 509}
			};
			a_point_pier = temp_point10;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 5, 795, 308, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 804, 296, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 790, 250, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 3, 862, 294, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 2, 687, 480, 0, 14, null);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(1), null, -2, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, null, (Obstacle)v_obstacle.elementAt(0), -1, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(7), null, 1, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 11:
			int[][] temp_point11 = {
					{55, 356},
					{146, 407},
					{644, 327},
					{845, 82},
					{811, 189},
					{910, 344},
					{796, 474}
			};
			a_point_pier = temp_point11;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 3, 160, 380, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 3, 620, 301, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 678, 297, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 678, 250, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -2, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, -1, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(5), null, 0, 1, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(6), null, 1, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 12:

			int[][] temp_point12 = {
					{53, 358},
					{345, 292},
					{506, 292},
					{885, 199},
					{644, 472},
					{724, 472},
					{804, 472}
			};
			a_point_pier = temp_point12;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			Obstacle obstacle_w = new Obstacle(this, 5, 422, 258, 0, 0, null);
			v_obstacle.addElement(obstacle_w);
			obstacle = new Obstacle(this, 4, 367, 243, 0, 0, obstacle_w);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 360, 195, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 420, 243, 0, 0, obstacle_w);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 420, 200, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 479, 243, 0, 0, obstacle_w);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 488, 195, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle_w = null;
			System.gc();
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -2, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(6), null, 1, 1, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			
			break;
		case 13:
			int[][] temp_point13 = {
					{53, 441},
					{753, 453},
					{821, 453},
					{907, 453}
			};
			a_point_pier = temp_point13;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, 1, 3, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 14:
			int[][] temp_point14 = {
					{53, 357},
					{221, 405},
					{720, 211},
					{758, 211},
					{720, 68},
					{800, 68},
					{880, 68},
					{720, 365},
					{758, 365},
					{720, 484},
					{800, 484},
					{880, 484}
			};
			a_point_pier = temp_point14;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();

			obstacle = new Obstacle(this, 1, 220, 370, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 775, 330, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 848, 446, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 2, 696, 180, 0, 11, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 2, 700, 456, 0, 9, null);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -1, 1, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(10), null, 1, 2, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 15:
			int[][] temp_point15 = {
					{43, 459},
					{685, 411},
					{781, 401},
					{861, 401},
					{608, 286},
					{781, 286},
					{867, 286},
					{947, 286},
					{776, 243},
					{608, 243},
					{508, 243},
					{771, 196},
					{612, 196},
					{688, 157},
					{33, 189},
					{89, 189}
			};
			a_point_pier = temp_point15;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 3, 685, 385, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 685, 325, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 3, 685, 281, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 685, 221, 0, 11, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -1, 2, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 16:
			int[][] temp_point16 = {
					{49, 340},
					{533, 474},
					{620, 304},
					{671, 304},
					{876, 191},
					{916, 191},
					{716, 412},
					{796, 412},
					{876, 412}
			};
			a_point_pier = temp_point16;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 5, 729, 380, Obstacle.WOOD_T, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 728, 155, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(1), null, 2, 2, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -1, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, -2, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(7), null, 1, 1, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 17:
			int[][] temp_point17 = {
					{42, 356},
					{651, 471},
					{904, 219},
					{40, 80},
					{120, 80},
					{200, 80},
					{320, 10}
			};
			a_point_pier = temp_point17;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 5, 694, 442, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 5, 877, 188, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(0), 2, 2, 0));
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(1), -1, 2, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 18:
			int[][] temp_point18 = {
					{46, 208},
					{360, 193},
					{440, 193},
					{750, 193},
					{830, 193},
					{750, 71},
					{830, 71},
					{910, 71},
					{940, 193},
					{424, 330},
					{440, 430}
			};
			a_point_pier = temp_point18;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 1, 388, 161, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 368, 117, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 378, 73, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 422, 296, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 454, 396, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 5, 878, 160, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 2, 725, 160, 0, 9, null);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(5), 0, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 19:
			int[][] temp_point19 = {
					{45, 451},
					{638, 453},
					{762, 303},
					{800, 170},
					{424, 159}
			};
			a_point_pier = temp_point19;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 4, 453, 127, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 473, 80, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(1), null, 0, 2, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -1, 2, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -2, 2, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 20:
			int[][] temp_point20 = {
					{45, 454},
					{578, 161},
					{797, 294},
					{736, 426},
					{865, 426},
					{605, 458},
					{314, 272}
			};
			a_point_pier = temp_point20;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 3, 314, 239, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 800, 399, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 780, 359, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 885, 399, 0, 0, null);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(1), null, -2, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -2, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -1, 1, 0));
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(3), -1, 1, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(5), null, 1, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 21:
			int[][] temp_point21 = {
					{45, 434},
					{618, 485},
					{698, 485},
					{787, 294},
					{805, 438},
					{885, 438}
			};
			a_point_pier = temp_point21;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 5, 690, 457, Obstacle.WOOD_DOWN, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 690, 457, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 5, 890, 409, Obstacle.WOOD_DOWN, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 5, 880, 290, Obstacle.WOOD_DOWN, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 3, 890, 409, 0, 0, null);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(1), null, 1, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(0), -1, 1, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -2, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, 0, 1, 0));
			v_enemy.addElement(new Enemy(this, null, (Obstacle) v_obstacle.elementAt(3), -2, 1, 0));
			count_enemy = v_enemy.size();
			break;
		case 22:
			int[][] temp_point22 = {
					{40, 429},
					{312, 445},
					{808, 142},
					{893, 236},
					{812, 297},
					{669, 478},
					{875, 407}
			};
			a_point_pier = temp_point22;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 3, 314, 415, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 314, 355, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 324, 311, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 344, 264, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 844, 275, 0, 0, null);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -2, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -1, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, -1, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(5), null, 0, 1, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(6), null, 0, 1, Enemy.LEFT_PIER));
			count_enemy = v_enemy.size();
			break;
		case 23:
			int[][] temp_point23 = {
					{47, 402},
					{610, 405},
					{690, 405},
					{770, 405},
					{865, 373},
					{527, 165},
					{712, 223},
					{527, 223},
					{712, 165}
			};
			a_point_pier = temp_point23;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 5, 622, 191, Obstacle.WOOD_UP, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 622, 175, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 622, 135, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 622, 95, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(1), null, 0, 2, 0));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, 0, 2, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, -1,2, 0));
			count_enemy = v_enemy.size();
			break;
		case 24:
			int[][] temp_point24 = {
					{45, 454},
					{500, 250},
					{880, 130},
					{880, 290},
					{880, 450}
			};
			a_point_pier = temp_point24;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -1, 3, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, 0, 3, 0));
			count_enemy = v_enemy.size();
			break;
		case 25:
			int[][] temp_point25 = {
					{45, 454},
					{500, 450},
					{780, 180},
					{880, 320},
					{880, 450}
			};
			a_point_pier = temp_point25;
			for(int i=0; i< a_point_pier.length; i++) {
				v_pier.addElement(new Pier(this, a_point_pier[i][0], a_point_pier[i][1]));
			}
			count_pier = v_pier.size();
			
			obstacle = new Obstacle(this, 3, 500, 430, 0, 0, null);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 520, 370, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 4, 540, 330, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			obstacle = new Obstacle(this, 1, 520, 280, 0, 0, obstacle);
			v_obstacle.addElement(obstacle);
			count_obstacle = v_obstacle.size();
			
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(2), null, -2, 3, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(3), null, -1, 3, Enemy.LEFT_PIER));
			v_enemy.addElement(new Enemy(this, (Pier) v_pier.elementAt(4), null, 0, 3, 0));
			count_enemy = v_enemy.size();
			break;
		}
		
		showMap = true;
		x_map = 0;
		remain_missile = 4;
		doublePower = 0;
		shooterIndex = 0;//每关默认为自己先开枪
		hero = new Hero(this, (Pier) v_pier.elementAt(0));
		
		obstacle = null;
		System.gc();
	}

	public void show(Graphics g) {
		g.drawImage(img_map, x_map, 0, 0);
		
		for (int i = 0; i < count_pier; i++) {
			((Pier)v_pier.elementAt(i)).show(g);
		}
		
		for (int i = 0; i < v_obstacle.size(); i++) {
			((Obstacle)v_obstacle.elementAt(i)).show(g);
		}
		
		for (int i = 0; i < v_enemy.size(); i++) {
			((Enemy)v_enemy.elementAt(i)).show(g);
		}
		
		hero.show(g);
		
		if(y_move == 3) y_move = -3;
		else y_move = 3;
		if(shooterIndex == 0) {
			g.drawImage(img_point_shooter, hero.x + x_map, hero.y - 120 + y_move, Graphics.BOTTOM | Graphics.HCENTER);
		} else {
			g.drawImage(img_point_shooter, shoottingEnemy.x + x_map, shoottingEnemy.y - 110 + y_move, Graphics.BOTTOM | Graphics.HCENTER);
		}
		
		if(missile != null) missile.show(g);
		if(bullet != null) bullet.show(g);
		
		C.drawString(g, img_number, remain_missile + "", "0123456789", 560, 5, 16, 23, 0, 0, 0);
		C.drawString(g, img_number, CanvasControl.mission + "", "0123456789", 110, 5, 16, 23, 0, 0, 0);
		
		g.drawImage(img_bottom_bar, 0, 530, Graphics.BOTTOM | Graphics.LEFT);
		g.drawImage(img_word_mission, 10, 5, 0);
		g.drawImage(img_word_bullet, 440, 5, 0);
		
		if(showWeak) {
			g.drawImage(img_weak, 0, 0, 0);
		}
		if(hero.weak) {
			if(img_word_weak != null) g.drawImage(img_word_weak, 320, 100 + weak_w_y, Graphics.HCENTER | Graphics.BOTTOM);
		}
	}

	public void loadResource() {
		try {
			img_bottom_bar = Image.createImage("/game/bottom_bar.png");
			img_word_mission = Image.createImage("/game/word_mission.png");
			img_word_bullet = Image.createImage("/game/word_bullet.png");
			img_pier = Image.createImage("/game/pier.png");
			img_number = Image.createImage("/game/number.png");
			img_point_shooter = Image.createImage("/game/point_you.png");
			a_img_blood_bar = new Image[2];
			a_img_blood_bar[0] = Image.createImage("/game/blood_bar_b.png");
			a_img_blood_bar[1] = Image.createImage("/game/blood_bar_u.png");
			
			for (int i = 0; i < v_enemy.size(); i++) {
				((Enemy)v_enemy.elementAt(i)).loadImage();
			}
			
			for (int i = 0; i < v_obstacle.size(); i++) {
				((Obstacle)v_obstacle.elementAt(i)).loadImage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Pier.width_cln = img_pier.getWidth();
		Pier.height_cln = img_pier.getHeight();

	}
	
	/**
	 * 加载残血图片
	 */
	public void loadWeakImage() {
		try {
			img_weak = Image.createImage("/game/weak.png");
			img_word_weak = Image.createImage("/game/word_weak.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除残血效果图片
	 */
	public void removeWeakImage() {
		img_weak = null;
		img_word_weak = null;
		
		System.gc();
	}
	
	/**
	 * 移除从服务器下载的图片
	 */
	public void removeServerImage() {
		img_map = null;
		

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 3; j++) {
				a_img_hero[i][j] = null;
			}
		}
		a_img_hero = null;
		
		for (int i = 0; i < 11; i++) {
			a_img_point[i] = null;
		}
		a_img_point = null;
	}

	public void removeResource() {
		img_bottom_bar = null;
		img_word_bullet = null;
		img_word_mission = null;
		img_pier = null;
		img_number = null;
		img_point_shooter = null;
		
		for (int i = 0; i < 2; i++) {
			a_img_blood_bar[i] = null;
		}
		a_img_blood_bar = null;
		

		for (int i = 0; i < v_enemy.size(); i++) {
			Enemy enemy = (Enemy)v_enemy.elementAt(i);
			if(!enemy.dead){
				enemy.removeAliveImage();
			}
		}
		
		for (int i = 0; i < v_obstacle.size(); i++) {
			((Obstacle)v_obstacle.elementAt(i)).removeImage();
		}
		
		System.gc();
	}

	public void keyPressed(int keyCode) {
		hero.keyPressed(keyCode);
		
		switch (keyCode) {
		case C.KEY_1:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 5, this));
			break;
		case C.KEY_2:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 6, this));
			break;
		case C.KEY_3:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 7, this));
			break;
		case C.KEY_9:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Help(canvasControl, 1));
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setGoBackView(this);
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Dialog(canvasControl, 1, this));
			break;
		default:
			break;
		}
	}

	public void keyReleased(int keyCode) {
		hero.keyReleased(keyCode);
	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if(showMap) {
			showMap();
			canvasControl.repaint();
			canvasControl.serviceRepaints();
			return;
		}
		
		if(moveMapLeft) {
			x_map -= 40;
			if(x_map <= -320) {
				x_map = -320;
				moveMapLeft = false;
			}
		} else if(moveMapRight) {
			x_map += 40;
			if(x_map >= 0){
				x_map = 0;
				moveMapRight = false;
			}
		}
		
		hero.logic();
		
		for (int i = 0; i < v_enemy.size(); i++) {
			((Enemy)v_enemy.elementAt(i)).logic();
		}
		
		for (int i = 0; i < v_obstacle.size(); i++) {
			((Obstacle)v_obstacle.elementAt(i)).logic();
		}
		
		if(missile != null) missile.logic();
		if(bullet != null) bullet.logic();
		
		if(hero.weak) {
			if(weakControl == 0) {
				showWeak = true;
			} else if(weakControl == 4) {
				showWeak = false;
			} else if(weakControl == 6) {
				showWeak = true;
			} else if(weakControl == 10) {
				showWeak = false;
			}
			weakControl ++;
			if(weakControl > 20)
				weakControl = 0;
			
			if(weak_w_y == 0) weak_w_y = 5;
			else weak_w_y = 0;
			
			if(Hero.life > 30) {
				hero.weak = false;
				showWeak = false;
				weakControl = 0;
				removeWeakImage();
			}
		}
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

	private void showMap() {
		if(x_map <= -320) 
			mapControl  *= -1;
		
		x_map += mapControl * 10;
		
		if(x_map >= 0) {
			mapControl = -1;
			x_map = 0;
			showMap = false;
		}
	}

	/**
	 * 前一个射击完成
	 * 下一个人射击
	 */
	public void nextShooter() {
		if(shooterIndex != 0 && shooterIndex - 1 != v_enemy.indexOf(shoottingEnemy) && !shootterDead) {
			
		} else {
			shooterIndex ++;
			if(shootterDead)
				shootterDead = false;
		}
		if(shooterIndex > v_enemy.size()) {
			shooterIndex = 0;
			moveMapRight = true;
//			x_map = 0;
		} else {
			Enemy enemy = (Enemy)v_enemy.elementAt(shooterIndex - 1);
			if(enemy.dead) {
				shootterDead = true;
				nextShooter();
			} else {
				enemy.shootting = true;
				shoottingEnemy = enemy;
				moveMapLeft = true;
//				x_map = -320;
			}
		}
	}
	
	/**
	 * 取到当前游戏的画布控制对象
	 * @return
	 */
	public CanvasControl getCanvasControl() {
		return canvasControl;
	}

}
