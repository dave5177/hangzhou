package com.dave.gowhere.intity;

import javax.microedition.lcdui.Graphics;

/**
 * ��Ϸ�ڵ�ʵ����
 * @author Administrator
 *
 */
public interface Model {

	/**
	 * ��ʾ�ķ���
	 * @param g ����һ������
	 */
	public void show (Graphics g);
	
	/**
	 * ��������
	 * @param keyCode
	 */
	public void keyPressed(int keyCode);
	
	/**
	 * �����ͷ�
	 * @param keyCode
	 */
	public void keyReleased(int keyCode);

	/**
	 * ʵ�����߼�
	 */
	public void logic();
	
	/**
	 * ����
	 */
	public void fire();
}
