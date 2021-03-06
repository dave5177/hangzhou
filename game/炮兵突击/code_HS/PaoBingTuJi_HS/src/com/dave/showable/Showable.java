package com.dave.showable;

import javax.microedition.lcdui.Graphics;

/**
 * 用于显示的接口
 * @author Dave
 *
 */
public interface Showable {
	
	/**
	 * 显示的方法
	 * @param g 传入一个画笔
	 */
	public void show (Graphics g);
	
	/**
	 * 加载界面资源
	 */
	public void loadResource();
	
	/**
	 * 移除界面资源
	 */
	public void removeResource();
	
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
	 * 长按
	 * @param keyCode
	 */
	public void keyRepeated(int keyCode);

	/**
	 * 各个界面动画以及游戏逻辑
	 */
	public void logic();
	
}
