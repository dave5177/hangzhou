package com.dave.paoBing.tool;

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Image;

public class HttpLoadImage {
	/**
	 * ��ǰ�������صڼ���ͼƬ����Ҫ���ڿ��Ƽ��ؽ�������
	 */
	public static int imgCount = 0;
	
	
	/**
	 * ���ִ���ʱ���¼���ѭ�����ڼ��� 
	 */
	private static int cycleTimes;
	
	
	/**
	 * ���ִ���ʱ������¼��ش���
	 */
	private static final int maxCycle = 10;
	
	/**
	 * �������ȡһ��ͼƬ�ķ��������������쳣ʱ���Զ����¼��ء�
	 * ������maxCycle�������������»�ȡ�Ĵ�����
	 * @param urlͼƬ��ַ
	 * @return Image����
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
					readControl = dataInputStream.read(dataArray, count, length - count);//readControl��¼��ȡ�˶��ٸ��ֽڡ�
					count += readControl;
				}
				
				imgCount ++;

				return Image.createImage(dataArray, 0, length);
			} else {
				System.out.println("���Ӵ��� ��" + result);
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
	 * �������ȡ����ͼƬ�ķ������贫����url��ַ��
	 * @param Image��ַ��ÿ��Image��Ӧһ��url�����������ĳ��Ⱦ��������Image����ĳ��ȡ�
	 * @return һ���봫���url�ַ������鳤��һ�µ�Image���顣
	 */
	public static Image[] getImageArray(String[] url) {
//		count = 0;
		int length = url.length;
		Image[] ima = new Image[length];
		for(int i=0; i<length; i++) {
			System.out.println("��ʼ��ȡ�� " + (imgCount + 1) + " ��ͼƬ");
			ima[imgCount] = catchImage(url[imgCount]);
//			count++;
		}
		return ima;
	}
	
	
}
