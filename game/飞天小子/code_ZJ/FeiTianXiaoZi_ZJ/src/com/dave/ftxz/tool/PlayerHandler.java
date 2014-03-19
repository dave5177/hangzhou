package com.dave.ftxz.tool;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class PlayerHandler {

	// For example, here are a few common content types:
	//
	// Wave audio files: audio/x-wav
	// AU audio files: audio/basic
	// MP3 audio files: audio/mpeg
	// MIDI files: audio/midi
	// Tone sequences: audio/x-tone-seq

	/**
	 * ���еĲ���������
	 */
	private Player[] player = null;

	/**
	 * ������
	 */
	InputStream inputStream = null;

	/**
	 * ������size
	 */
	private final int size;

	/**
	 * �����������ŵ���������СΪ1.
	 * Ҳ����������м�������ͬʱ���š�
	 */
	private final int keepPlayNumber;

	/**
	 * @param size
	 *            ������size
	 * @param keepPlayNumber
	 *            �����������ŵ���������СΪ1��Ҳ����������м�������ͬʱ���š�
	 */
	public PlayerHandler(int size, int keepPlayNumber) {
		this.size = size;
		this.keepPlayNumber = keepPlayNumber;
		init();
	}

	private void init() {
		player = new Player[size];
		loadSource();
		// loadFromHttp();
	}

	public void loadSource() {
		inputStream = null;
		try {
			for (int i = 0; i < size; i++) {
				inputStream = getClass().getResourceAsStream(
						"/sound/" + i + ".wav");
				player[i] = Manager.createPlayer(inputStream, "audio/x-wav");
				player[i].realize();
				player[i].prefetch();
				// VolumeControl volume1 = (VolumeControl)
				// player[a].getControl("VolumeControl");
				// volume1.setLevel(60);
			}
			// inputStream.reset();
			inputStream = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}

		player[0].setLoopCount(-1);
	}

	public void loadFromHttp(String baseUrl) {
		String url = "";
		HttpConnection httpConnection = null;
		try {
			for (int i = 0; i < size; i++) {
				url = baseUrl + "/sound/" + i + ".wav";
				httpConnection = (HttpConnection) Connector.open(url);
				int result = httpConnection.getResponseCode();

				if (result == HttpConnection.HTTP_OK) {
					inputStream = httpConnection.openInputStream();
					player[i] = Manager
							.createPlayer(inputStream, "audio/x-wav");
					player[i].realize();
					player[i].prefetch();
				}
			}
			inputStream = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}

		player[0].setLoopCount(-1);
	}

	/**
	 * ����
	 * 
	 * @param index
	 */
	public void playByIndex(int index) {
		try {
			int play_number = 0;
			for (int i = 0; i < size; i++) {
				if (player[i].getState() == Player.STARTED) {
					play_number++;
					
					if(play_number >= keepPlayNumber)
						return;
				}
			}
			if (player[index] != null) {
				if (player[index].getState() == Player.STARTED) {
				} else {
					if (index == 0)
						player[index].prefetch();

					player[index].start();
				}
			}
		} catch (MediaException e) {
			System.out.println("run is error");
		}
	}

	/**
	 * ֹͣ
	 * 
	 * @param index
	 */
	public void stopByIndex(int index) {
		try {
			if (player[index].getState() == Player.STARTED) {
				player[index].stop();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void removeAudioPlaySources() {
		for (int a = 0; a < size; a++) {
			player[a] = null;
		}
		player = null;
		System.gc();
	}
}
