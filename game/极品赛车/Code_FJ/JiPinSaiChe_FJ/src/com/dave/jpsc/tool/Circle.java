package com.dave.jpsc.tool;

import javax.microedition.lcdui.Graphics;

/**
 * @author Dave
 * Բ��
 */
public class Circle {
	/**
	 * Բ������
	 */
	public int x, y;
	
	/**
	 * �뾶
	 */
	public int r;
	
	/**
	 * Բ����ײ���÷�Χ
	 */
	public int r_range;
	
	/**
	 * ������ͣ�ȡֵ��IN_DETECT��ΪԲ����ײ��OUT_DETECT��ΪԲ����ײ��
	 */
	public int typeDetect;
	
	/**
	 * ��ײ�����ʼ�Ƕ�
	 */
	public int angleDtctSt;
	
	/**
	 * ��ײ�������Ƕ�
	 */
	public int angleDtctEnd;
	
	/**
	 * Բ����ײ��Բ��Ϊ��ײ�壩
	 */
	public static final int IN_DETECT = 0;
	
	/**
	 * Բ����ײ��Բ��Ϊ��ײ�壩
	 */
	public static final int OUT_DETECT = 1;

	/**
	 * @param x Բ��x����
	 * @param y Բ��y����
	 * @param r �뾶
	 * @param typeDetect ������ͣ�0��ΪԲ����ײ��1��ΪԲ����ײ��
	 * @param angleDtctSt ��ײ�����ʼ�Ƕ�
	 * @param angleDtctEnd ��ײ�������Ƕ�
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
