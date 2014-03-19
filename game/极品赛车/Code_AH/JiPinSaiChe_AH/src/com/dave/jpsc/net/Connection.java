package com.dave.jpsc.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class Connection extends Thread {
	
	private ServerIptv si;

	private Thread thread;
	
	private String httpAddress;
	
	private int index;

	/**
	 * 构造函数
	 */
	public Connection(ServerIptv serveriptv, String httpaddres,int i) {
		si = serveriptv;
		httpAddress = httpaddres;
		index = i;
		if (thread == null)thread = new Thread(this);
		thread.start();
	}

	/**
	 * 线程
	 */
	public void run() {
		String str = "";
		for (int a = 0; a < 3; a++) {
			try {
				str = new String(getViaHttpConnection(httpAddress),"UTF-8");
				if (str != "")a = 3;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				thread=null;
				System.gc();
				si.backStr(index,str);
			}
		}
	}
	
	/**
	 * 连接网络，下载数据
	 */
	public byte[] getViaHttpConnection(String url) throws IOException {
		HttpConnection c = null;
		InputStream is = null;
		byte[] data = null;
		int rc;

		try {
			c = (HttpConnection) Connector.open(url);
			rc = c.getResponseCode();
			if (rc != HttpConnection.HTTP_OK) {
				throw new IOException("HTTP response code: " + rc);
			}

			is = c.openInputStream();
//			String type = c.getType();
			int len = (int) c.getLength();
//			System.out.println("------------len:"+len);
			if (len > 0) {
				int actual = 0;
				int bytesread = 0;
				data = new byte[len];
				while ((bytesread != len) && (actual != -1)) {
					actual = is.read(data, bytesread, len - bytesread);
					bytesread += actual;
				}
			} else {
//				int ch = 0;
//				while ((ch = is.read()) != -1) {
//
//				}
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Not an HTTP URL");
		} finally {
			if (is != null)
				is.close();
			if (c != null)
				c.close();
		}
		return data;
	}

}
