package com.dave.paoBing.tool;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import com.dave.paoBing.main.CanvasControl;

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
	 * �Ƿ񱣳�ֻ��һ����������
	 */
	private final boolean keepOnePlay;
	
	public PlayerHandler(int size, boolean keepOnePlay) {
		this.size = size;
		this.keepOnePlay = keepOnePlay;
		init();
	}
	
	private void init() {
		player = new Player[size];
//		loadSource();
		loadFromHttp();
	}

	public void loadSource() {
		inputStream = null;
		try {
			for(int i=0; i<size; i++) {
				inputStream = getClass().getResourceAsStream("/sound/" + i + ".wav");
				player[i] = Manager.createPlayer(inputStream, "audio/x-wav");
				player[i].realize();
				player[i].prefetch();
				// VolumeControl volume1 = (VolumeControl)
				// player[a].getControl("VolumeControl");
				// volume1.setLevel(60);
			}
//			inputStream.reset();
			inputStream = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
		
		player[0].setLoopCount(-1);
	}

	public void loadFromHttp() {
		String url = "";
		HttpConnection httpConnection = null;
		try {
			for(int i=0; i<size; i++) {
				url = CanvasControl.imageServerUrl + "/sound/" + i + ".wav";
				httpConnection = (HttpConnection) Connector.open(url);
				int result = httpConnection.getResponseCode();
				
				if(result == HttpConnection.HTTP_OK) {
					inputStream = httpConnection.openInputStream();
					player[i] = Manager.createPlayer(inputStream, "audio/x-wav");
				}
//				inputStream = getClass().getResourceAsStream("/sound/" + i + ".wav");
//				player[i] = Manager.createPlayer(inputStream, "audio/x-wav");
//				player[i].realize();
//				player[i].prefetch();
				// VolumeControl volume1 = (VolumeControl)
				// player[a].getControl("VolumeControl");
				// volume1.setLevel(60);
			}
//			inputStream.reset();
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
	 * @param index
	 */
	public void playByIndex(int index) {
		try {
			if(keepOnePlay) {
				for(int i=0; i<size; i++) {
					if(player[i].getState()==Player.STARTED){
						return;
					}
				}
			}
			if (player[index] != null){
				if(player[index].getState()==Player.STARTED){
				}else{
					player[index].start();
				}
			}
		} catch (MediaException e) {
			System.out.println("run is error");
		}
	}

	/**
	 * ֹͣ
	 * @param index
	 */
	public void stopByIndex(int index) {
		try {
			if(player[index].getState() == Player.STARTED) {
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
