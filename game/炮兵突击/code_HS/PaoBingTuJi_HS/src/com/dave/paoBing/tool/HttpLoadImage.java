package com.dave.paoBing.tool;

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Image;

public class HttpLoadImage {
	/**
	 * 当前正在下载第几张图片，主要用于控制加载进度条。
	 */
	public static int imgCount = 0;
	
	
	/**
	 * 出现错误时重新加载循环到第几次 
	 */
	private static int cycleTimes;
	
	
	/**
	 * 出现错误时最大重新加载次数
	 */
	private static final int maxCycle = 10;
	
	/**
	 * 从网络获取一张图片的方法，出现网络异常时会自动重新加载。
	 * 可设置maxCycle参数来决定重新获取的次数。
	 * @param url图片地址
	 * @return Image对象
	 */
	public static Image catchImage(String url) {
		HttpConnection httpConnection = null;
		DataInputStream dataInputStream = null;
		
		try {
			httpConnection = (HttpConnection) Connector.open(url);
			int result = httpConnection.getResponseCode();
			if(result == HttpConnection.HTTP_OK) {
				
				dataInputStream = httpConnection.openDataInputStream();
				
				int length = (int) httpConnection.getLength();
				
				byte[] dataArray = new byte[length];
				
				int count = 0;
				int readControl = 0;
				
				while(count < length && readControl != -1) {
					readControl = dataInputStream.read(dataArray, count, length - count);//readControl记录读取了多少个字节。
					count += readControl;
				}
				
				imgCount ++;

				return Image.createImage(dataArray, 0, length);
			} else {
				System.out.println("连接错误 ：" + result);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(cycleTimes < maxCycle) {
				cycleTimes ++;
				catchImage(url);
			} else {
				cycleTimes = 0;
			}
			cycleTimes = 0;
		} finally {
			try {
				if(dataInputStream != null) {
					dataInputStream.close();
					dataInputStream = null;
				}
				if(httpConnection != null) {
					httpConnection.close();
					httpConnection = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 从网络获取多张图片的方法，需传入多个url地址。
	 * @param Image地址，每个Image对应一个url。传入的数组的长度决定输出的Image数组的长度。
	 * @return 一个与传入的url字符串数组长度一致的Image数组。
	 */
	public static Image[] getImageArray(String[] url) {
//		count = 0;
		int length = url.length;
		Image[] ima = new Image[length];
		for(int i=0; i<length; i++) {
			System.out.println("开始读取第 " + (imgCount + 1) + " 张图片");
			ima[imgCount] = catchImage(url[imgCount]);
//			count++;
		}
		return ima;
	}
	
	
}
