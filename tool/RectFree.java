package com.dave.jpsc.tool;

import javax.microedition.lcdui.Graphics;

/**
 * @author Dave ���ɰڷŵľ���
 */
public class RectFree {

	/**
	 * ����
	 */
	public Vector2 ver_a;
	public Vector2 ver_b;
	public Vector2 ver_c;
	public Vector2 ver_d;

	/**
	 * ���ĵ�
	 */
	public Vector2 center;
	
	/**
	 * �루1��0��˳ʱ����ת�ĽǶ�
	 */
	public float angle;
	
	/**
	 * ��¼��ת�Ƕȵ�С�����֣�10����
	 */
	private int decimal;
	
	/**
	 * ��Ӿ���
	 */
	private Rect boundingRect;

	/**
	 * @param ver_a
	 *            ����1
	 * @param ver_b
	 *            ����2
	 * @param ver_c
	 *            ����3
	 * @param ver_d
	 *            ����4
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
	 *            ��
	 * @param height
	 *            ��
	 * @param center
	 *            ���ĵ�����
	 * @param angle
	 *            �Ƕ�
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
	 * �����ҵ���Ӿ���
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
	 * ת��
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
	 * ƽ��
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
	 * ��ת
	 * 
	 * @param angle
	 *            �Ƕ�ֵ
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
	 * ��ʾ
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
	 * ����Ӿ���
	 * @param x_map
	 * @param y_map
	 * @param g
	 */
	public void showBoundingRect(int x_map, int y_map, Graphics g) {
		g.drawRect(boundingRect.x + x_map, boundingRect.y + y_map, boundingRect.w, boundingRect.y);
	}
	
	/**
	 * ��ȡ�ҵ���Ӿ���
	 * @return
	 */
	public Rect getBoundingRect() {
		return boundingRect;
	}

	/**
	 * ��ȡ���ɾ��εķ���������ģȡ100px��
	 * @return
	 */
	public Vector2 getDirVector() {
		return new Vector2(C.cos((int)angle) /1000 , C.sin((int) angle) / 1000);
	}

	/**
	 * �������ĵ�
	 * @param x_center
	 * @param y_center
	 */
	public void setCenterPos(int x_center, int y_center) {
		trasle(new Vector2(x_center - center.x, y_center - center.y));
	}

	/**
	 * ���ýǶ�
	 * @param angle_tar
	 */
	public void setAngle(float angle_tar) {
		if(angle_tar != angle) {
			rotate(angle_tar - angle);
		}
			
	}
	
}
