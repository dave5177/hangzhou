package com.dave.jpsc.tool;

import javax.microedition.lcdui.Graphics;

/**
 * @author Dave
 * 圆形
 */
public class Circle {
	/**
	 * 圆心坐标
	 */
	public int x, y;
	
	/**
	 * 半径
	 */
	public int r;
	
	/**
	 * 圆外碰撞作用范围
	 */
	public int r_range;
	
	/**
	 * 检测类型（取值：IN_DETECT，为圆内碰撞；OUT_DETECT，为圆外碰撞）
	 */
	public int typeDetect;
	
	/**
	 * 碰撞检测起始角度
	 */
	public int angleDtctSt;
	
	/**
	 * 碰撞检测结束角度
	 */
	public int angleDtctEnd;
	
	/**
	 * 圆内碰撞（圆内为碰撞体）
	 */
	public static final int IN_DETECT = 0;
	
	/**
	 * 圆外碰撞（圆外为碰撞体）
	 */
	public static final int OUT_DETECT = 1;

	/**
	 * @param x 圆心x坐标
	 * @param y 圆心y坐标
	 * @param r 半径
	 * @param typeDetect 检测类型（0，为圆内碰撞；1，为圆外碰撞）
	 * @param angleDtctSt 碰撞检测起始角度
	 * @param angleDtctEnd 碰撞检测结束角度
	 */
	public Circle(int x, int y, int r, int typeDetect, int angleDtctSt,
			int angleDtctEnd) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.typeDetect = typeDetect;
		this.angleDtctSt = angleDtctSt;
		this.angleDtctEnd = angleDtctEnd;
		this.r_range = r + 100;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param r
	 * @param typeDetect
	 * @param angleDtctSt
	 * @param angleDtctEnd
	 * @param r_range
	 */
	public Circle(int x, int y, int r, int typeDetect, int angleDtctSt,
			int angleDtctEnd, int r_range){
		this(x, y, r, typeDetect, angleDtctSt, angleDtctEnd);
		this.r_range = r_range;
	}
	
	public void show(int x_map, int y_map, Graphics g) {
		g.setColor(255, 255, 0);
		g.drawRoundRect(x - r + x_map, y - r + y_map, r * 2, r * 2, r * 2, r * 2);
	}
	
}
