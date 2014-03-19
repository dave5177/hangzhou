package com.dave.soldierHunt.tool;

import java.io.IOException;
import java.io.InputStream;

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

	public Player[] player = null;
	InputStream inputStream = null;
	private final int size;
	
	public PlayerHandler(int size) {
		this.size = size;
		init();
	}
	
	private void init() {
		player = new Player[size];
		loadSource();
	}

	public void loadSource() {
		InputStream temp_InputStream = null;
		try {
			for(int i=0; i<size; i++) {
				temp_InputStream = getClass().getResourceAsStream("/sound/" + i + ".wav");
				player[i] = Manager.createPlayer(temp_InputStream, "audio/x-wav");
				player[i].realize();
				player[i].prefetch();
				// VolumeControl volume1 = (VolumeControl)
				// player[a].getControl("VolumeControl");
				// volume1.setLevel(60);
			}
			temp_InputStream.reset();
			temp_InputStream = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
		
		player[0].setLoopCount(-1);
	}

	public void toPlay(int index) {
		try {
			for(int i=0; i<size; i++) {
				if(player[i].getState()==Player.STARTED){
					return;
				}
			}
			if (player[index] != null){
				if(player[index].getState()==Player.STARTED){
				}else{
					player[index].start();
				}
			}
			// switch(index){
			// case 0:{
			// player[0].start();
			// }break;
			// case 1:{
			// player[1].setLoopCount(-1);
			// player[1].start();
			// }break;
			// case 2:{
			// player[2].start();
			// }break;
			// case 3:{
			// player[3].start();
			// }break;
			// }
		} catch (MediaException e) {
			System.out.println("run is error");
		}
	}

	public void stop(int index) {
		try {
			if(player[index].getState() == Player.STARTED) {
				player[index].stop();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
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

	// /**
	// * 声音导入
	// */
	// public void loadSource(){
	// // for(int a =0;a<MUSIC_COUNT;a++){
	// // inputStream=getClass().getResourceAsStream("/sound/"+a+".wav");
	// // player[a]=Manager.createPlayer(inputStream,TYPE_WAV);
	// // player[a].realize();
	// // player[a].prefetch();
	// //// VolumeControl volume1 = (VolumeControl)
	// player[a].getControl("VolumeControl");
	// //// volume1.setLevel(60);
	// // inputStream.reset();
	// // }
	// try {
	// inputStream=getClass().getResourceAsStream("/sound/0.wav");
	// player[0]=Manager.createPlayer(inputStream,AUDIO_TYPE_wav);
	// player[0].realize();
	// player[0].prefetch();
	// // VolumeControl volume1 = (VolumeControl)
	// player[a].getControl("VolumeControl");
	// // volume1.setLevel(60);
	// inputStream.reset();
	// inputStream=null;
	// System.gc();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (MediaException e) {
	// e.printStackTrace();
	// }
	// }

	// /**
	// * 导入背景音乐
	// */
	// public void loadSourcesback(){
	// try {
	// inputStream=getClass().getResourceAsStream("/sound/1.wav");
	// player[1]=Manager.createPlayer(inputStream,AUDIO_TYPE_wav);
	// player[1].realize();
	// player[1].prefetch();
	// // VolumeControl volume1 = (VolumeControl)
	// player[a].getControl("VolumeControl");
	// // volume1.setLevel(60);
	// inputStream.reset();
	// inputStream = null;
	// System.gc();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (MediaException e) {
	// e.printStackTrace();
	// }
	// }

}
