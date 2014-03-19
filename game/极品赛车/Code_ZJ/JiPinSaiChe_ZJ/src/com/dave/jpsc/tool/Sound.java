package com.dave.jpsc.tool;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public class Sound {

	private Player player;

	/** 播放次数 -1为循环播放 */
	private int count;
	/**
	 * 音量
	 */
	private int volume;

	public Sound(String path, boolean isUrl) {
		volume = 50;
		count = 1;
		if (isUrl) {
			loadSound(path);
		} else {
			load(path);
		}
	}

	public void load(String path) {
		try {
			InputStream is = getClass().getResourceAsStream(path);
			player = Manager.createPlayer(is, "audio/x-wav");
			player.realize();
			player.prefetch();
			is = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			System.out.println("音效加载失败");
			e.printStackTrace();
		}
	}

	public void loadSound(String url) {
		try {
			HttpConnection hc = null;
			InputStream is = null;
			int rc;

			try {
				hc = (HttpConnection) Connector.open(url);
				rc = hc.getResponseCode();
				System.out.println("从服务器返回的值是：" + rc);
				if (rc != HttpConnection.HTTP_OK) {
					throw new IOException("HTTP response code: " + rc);
				}

				is = hc.openInputStream();

				player = Manager.createPlayer(is, "audio/x-wav");
				player.realize();
				player.prefetch();
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Not an HTTP URL");
			} catch (MediaException e) {
				System.out.println("下载音效失败");
				e.printStackTrace();
			} finally {
				is.close();
				hc.close();
				hc = null;
				is = null;
				System.gc();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			if (count == -1) {
				VolumeControl volume1 = (VolumeControl) player
						.getControl("VolumeControl");
				volume1.setLevel(volume);
				player.setLoopCount(count);
			}
			player.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			player.stop();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		stop();
		player.deallocate();
		player.close();
		player = null;
		System.gc();
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public boolean isStopped() {
		if (player.getState() == Player.PREFETCHED) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isStarted() {
		if (player.getState() == Player.STARTED) {
			return true;
		} else {
			return false;
		}
	}

}
