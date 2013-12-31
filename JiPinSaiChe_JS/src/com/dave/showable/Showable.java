package com.dave.showable;

import javax.microedition.lcdui.Graphics;

/**
 * ������ʾ�Ľӿ�
 * @author Dave
 *
 */
public interface Showable {
	
	/**
	 * ��ʾ�ķ���
	 * @param g ����һ������
	 */
	public void show (Graphics g);
	
	/**
	 * ���ؽ�����Դ
	 */
	public void loadResource();
	
	/**
	 * �Ƴ�������Դ
	 */
	public void removeResource();
	
	/**
	 * �Ƴ��ӷ�������ȡ��ͼƬ
	 */
	public void removeServerImage();
	
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
	 * ����
	 * @param keyCode
	 */
	public void keyRepeated(int keyCode);

	/**
	 * �������涯���Լ���Ϸ�߼�
	 */
	public void logic();

	/**
	 * ���߳ɹ����������
	 * @param goodsIndex �����±�ֵ 
	 */
	public void handleGoods(int goodsIndex);
	
}
