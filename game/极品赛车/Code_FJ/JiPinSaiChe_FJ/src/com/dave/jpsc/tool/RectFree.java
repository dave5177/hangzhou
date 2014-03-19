package com.dave.jpsc.tool;

import javax.microedition.lcdui.Graphics;

/**
 * @author Dave 自由摆放的矩形
 */
public class RectFree {

	/**
	 * 顶点
	 */
	public Vector2 ver_a;
	public Vector2 ver_b;
	public Vector2 ver_c;
	public Vector2 ver_d;

	/**
	 * 中心点
	 */
	public Vector2 center;
	
	/**
	 * 与（1，0）顺时针旋转的角度
	 */
	public float angle;
	
	/**
	 * 纪录旋转角度的小数部分（10倍）
	 */
	private int decimal;
	
	/**
	 * 外接矩形
	 */
	private Rect boundingRect;

	/**
	 * @param ver_a
	 *            顶点1
	 * @param ver_b
	 *            顶点2
	 * @param ver_c
	 *            顶点3
	 * @param ver_d
	 *            顶点4
	 */
 	public RectFree(Vector2 ver_a, Vector2 ver_b, Vector2 ver_c, Vector2 ver_d,
			Vector2 center) {
		super();
		this.ver_a = ver_a;
		this.ver_b = ver_b;
		this.ver_c = ver_c;
		this.ver_d = ver_d;
		this.center = center;
		
		this.angle = C.arctan((int)(ver_b.y - ver_a.y), (int)(ver_b.x - ver_a.x));
		
		resetRect();
	}

	/**
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param center
	 *            中心点向量
	 * @param angle
	 *            角度
	 */
	public RectFree(float width, float height, Vector2 center, int angle) {
		RectFree rf = new RectFree(new Vector2(width / 2, height / 2), new Vector2(width / 2,
				-height / 2), new Vector2(-width / 2, -height / 2),
				new Vector2(-width / 2, height / 2), new Vector2(0, 0));
		rf.rotate(angle);
		rf.trasle(center);
		this.ver_a = rf.ver_a;
		this.ver_b = rf.ver_b;
		this.ver_c = rf.ver_c;
		this.ver_d = rf.ver_d;
		this.center = rf.center;
		this.angle = angle;
		
		resetRect();
	}

	/**
	 * 创建我的外接矩形
	 */
	private void resetRect() {
		float x_min = ver_a.x;
		float x_max = ver_a.x;
		float y_min = ver_a.y;
		float y_max = ver_a.y;
		
		if(x_min > ver_b.x)	x_min = ver_b.x;
		if(x_min > ver_c.x)	x_min = ver_c.x;
		if(x_min > ver_d.x)	x_min = ver_d.x;
		
		if(x_max < ver_b.x)	x_max = ver_b.x;
		if(x_max < ver_c.x)	x_max = ver_c.x;
		if(x_max < ver_d.x)	x_max = ver_d.x;
		
		if(y_min > ver_b.y)	y_min = ver_b.y;
		if(y_min > ver_c.y)	y_min = ver_c.y;
		if(y_min > ver_d.y)	y_min = ver_d.y;
		
		if(y_max < ver_b.y)	y_max = ver_b.y;
		if(y_max < ver_c.y)	y_max = ver_c.y;
		if(y_max < ver_d.y)	y_max = ver_d.y;
		
		boundingRect = new Rect((int)x_min, (int)y_min, (int)(x_max - x_min), (int)(y_max - y_min));
		
	}

	/**
	 * 转换
	 * 
	 * @param matrix
	 */
	public void transform(Matrix matrix) {
		try {
			ver_a = ver_a.transform(matrix);
			ver_b = ver_b.transform(matrix);
			ver_c = ver_c.transform(matrix);
			ver_d = ver_d.transform(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 平移
	 * 
	 * @param px
	 */
	public void trasle(Vector2 vector) {
		ver_a = ver_a.add(vector);
		ver_b = ver_b.add(vector);
		ver_c = ver_c.add(vector);
		ver_d = ver_d.add(vector);
		center = center.add(vector);
		
		resetRect();
	}

	/**
	 * 旋转
	 * 
	 * @param angle
	 *            角度值
	 */
	public void rotate(float angle) {
		int end_angle = (int)angle;
		decimal += (angle * 10) % 10;
		int mul = decimal / 10;
		if(mul != 0) {
			end_angle += mul;
			decimal -= mul * 10;
		}
		Matrix transMatrix = new Matrix((float) C.cos(end_angle) / 100000,
				(float) C.sin(end_angle) / 100000, 0,
				-(float) C.sin(end_angle) / 100000, (float) C.cos(end_angle) / 100000,
				0, 0, 0, 1);
		Vector2 restore = center;
		trasle(center.negative());
		transform(transMatrix);
		trasle(restore);
		
		this.angle += angle;
		resetRect();
	}

	/**
	 * 显示
	 * 
	 * @param x_map
	 * @param y_map
	 * @param g
	 */
	public void show(int x_map, int y_map, Graphics g) {
		g.drawLine((int) ver_a.x + x_map, (int) ver_a.y + y_map, (int) ver_b.x
				+ x_map, (int) ver_b.y + y_map);
		g.drawLine((int) ver_b.x + x_map, (int) ver_b.y + y_map, (int) ver_c.x
				+ x_map, (int) ver_c.y + y_map);
		g.drawLine((int) ver_c.x + x_map, (int) ver_c.y + y_map, (int) ver_d.x
				+ x_map, (int) ver_d.y + y_map);
		g.drawLine((int) ver_d.x + x_map, (int) ver_d.y + y_map, (int) ver_a.x
				+ x_map, (int) ver_a.y + y_map);
	}

	/**
	 * 画外接矩形
	 * @param x_map
	 * @param y_map
	 * @param g
	 */
	public void showBoundingRect(int x_map, int y_map, Graphics g) {
		g.drawRect(boundingRect.x + x_map, boundingRect.y + y_map, boundingRect.w, boundingRect.y);
	}
	
	/**
	 * 获取我的外接矩形
	 * @return
	 */
	public Rect getBoundingRect() {
		return boundingRect;
	}

	/**
	 * 获取自由矩形的方向向量（模取100px）
	 * @return
	 */
	public Vector2 getDirVector() {
		return new Vector2(C.cos((int)angle) /1000 , C.sin((int) angle) / 1000);
	}

	/**
	 * 设置中心点
	 * @param x_center
	 * @param y_center
	 */
	public void setCenterPos(int x_center, int y_center) {
		trasle(new Vector2(x_center - center.x, y_center - center.y));
	}

	/**
	 * 设置角度
	 * @param angle_tar
	 */
	public void setAngle(float angle_tar) {
		if(angle_tar != angle) {
			rotate(angle_tar - angle);
		}
			
	}
	
}
