package com.dave.tool;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class AudioPlay {
	
	Player[] player=null;
	InputStream inputStream=null;
	/**
	 * 0°´Å¥ÉùÒô
	 * 1Ìø
	 * 2³Ô½ð±Ò
	 */
    private static final int MUSIC_COUNT = 4;

	private String soundType = "audio/x-wav";

	public AudioPlay() {
		player = new Player[MUSIC_COUNT];
		
		try {
			for(int a=0; a<MUSIC_COUNT; a++){
				   	inputStream=getClass().getResourceAsStream("/audio/"+(a)+".wav");
				   	player[a]=Manager.createPlayer(inputStream,soundType);
				   	player[a].realize();
				   	player[a].prefetch();
//						VolumeControl volume1 = (VolumeControl) player[a].getControl("VolumeControl");
//						volume1.setLevel(60);
					inputStream.reset();
			   }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
//			playBackAudio_zhengchang();
//			player[0].setLoopCount(-1);
	}
	
//	public void playBackAudio_zhengchang(){
//		try {
//			player[0].start();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (MediaException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void removeGameAudioSources(){
		for(int a=0; a<MUSIC_COUNT; a++){
			player[a] = null;
		}
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
	
//	/**
//	 * Í£Ö¹²¥·Å
//	 */
//	public void toStopGameSound(int index){
//    	try {
//			player[index].stop();
//		} catch (MediaException e) {
//			System.out.println("run is error");
//		}
//	}
	
	public void playSound(byte index)
	{
		if(player[index] != null && player[index].getState() != Player.STARTED)
		{
			try {
				player[index].start();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * ÉùÒô²¥·Å
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
