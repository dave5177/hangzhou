package com.dave.rangzidanf.tool;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class AudioPlaydalin {
	public final static int STARTED = Player.STARTED;
	public final static int CLOSED = Player.CLOSED;
	
	public Player player;
	
	public Player[] playerarray;
	InputStream inputStream = null;
	
	
    /**
     * 所有的声音总数
     * 0：大狙的声音。
     * 1：ak的声音。
     * 2：m16的声音。
     * 3：at15的声音。
     * 4：m4的声音。
     * 5：大炮的声音。
     * 6：武器库背景音乐。
     */
    private int index;

	private String soundType = "audio/x-wav";
	
	public AudioPlaydalin() {
		loadAllSound();
	}
	
	public void loadAllSound(){
		playerarray = new Player[6];
		for (int a = 0; a < 6; a++) {
			try {
			   	inputStream = getClass().getResourceAsStream("/sound/" + a + ".wav");
			   	playerarray[a]=Manager.createPlayer(inputStream,soundType);
			   	playerarray[a].realize();
			   	playerarray[a].prefetch();
//				VolumeControl volume1 = (VolumeControl) player.getControl("VolumeControl");
//				volume1.setLevel(40);
				inputStream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MediaException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				inputStream = null;
			}
		}
//		playerarray[6].setLoopCount(-1);//背景音乐，问题解决不了。先不用。
	}
	
	public void playSound(int a){
		try {
			for(int i=0; i<6; i++) {
				if(getState(i) == STARTED) return;
			}
			playerarray[a].start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void stopSound(int a) {
		try {
			if(playerarray[a].getState() == Player.STARTED) {
				playerarray[a].stop();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}
	
	public AudioPlaydalin(int index) {
		this.index = index;
		try {
		   	inputStream = getClass().getResourceAsStream("/sound/" + index + ".wav");
		   	player=Manager.createPlayer(inputStream,soundType);
		   	player.realize();
		   	player.prefetch();
//			VolumeControl volume1 = (VolumeControl) player.getControl("VolumeControl");
//			volume1.setLevel(40);
			inputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputStream = null;
		}
		
		if(index == 6) {
			player.setLoopCount(-1);
		}
//			playBackAudio_zhengchang();
//			player[0].setLoopCount(-1);
	}
	
	public void removeGameAudioSources(){
		player = null;
		
		System.gc();
	}
	
	public int getState(int index) {
		return playerarray[index].getState();
	}
	
	public void close() {
		player.close();
	}
//	public void loadGameSource(){
//		if(player == null)player= new Player[MUSIC_COUNT];
//		   try {
//				for(int a =0;a<MUSIC_COUNT;a++){
//					if(player[a]!=null)return;
//					   	inputStream=getClass().getResourceAsStream("/audio/"+(a+20)+".wav");
//					   	player[a]=Manager.createPlayer(inputStream,soundType);
//					   	player[a].realize();
//					   	player[a].prefetch();
////						VolumeControl volume1 = (VolumeControl) player[a].getControl("VolumeControl");
////						volume1.setLevel(60);
//						inputStream.reset();
//				   }
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (MediaException e) {
//				e.printStackTrace();
//			}
//	}
	
	/**
	 * 停止播放
	 */
	public void toStopGameSound(){
    	try {
			player.stop();
		} catch (MediaException e) {
			System.out.println("run is error");
		}
	}
	
	public void playSound()
	{
		if(player != null && player.getState() != Player.STARTED)
		{
			try {
				player.start();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 声音播放
	 */
	/*public void toPlayGameSound(int index){
    	
			switch(index){
			case 0:{
				
				playMusic(index);
				
			}break;
			case 1:{
			//	player[index].start();
			}break;
			case 2:{
				playMusic();
			}break;
			case 3:{
//				player[index].start();
			}break;
			
			
			}
		
	}*/

}
