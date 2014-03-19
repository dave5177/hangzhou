package com.dave.gowhere.intity;

import javax.microedition.lcdui.Graphics;

/**
 * 游戏内的实体类
 * @author Administrator
 *
 */
public interface Model {

	/**
	 * 显示的方法
	 * @param g 传入一个画笔
	 */
	public void show (Graphics g);
	
	/**
	 * 按键处理
	 * @param keyCode
	 */
	public void keyPressed(int keyCode);
	
	/**
	 * 按键释放
	 * @param keyCode
	 */
	public void keyReleased(int keyCode);

	/**
	 * 实体类逻辑
	 */
	public void logic();
	
	/**
	 * 攻击
	 */
	public void fire();
}
