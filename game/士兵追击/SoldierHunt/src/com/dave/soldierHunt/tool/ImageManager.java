package com.dave.soldierHunt.tool;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class ImageManager {
	public static Image[][][] ima_hero_move = new Image[6][4][4];
	public static Image[][][] ima_hero_attack = new Image[6][4][2];
	
	public static Image[] ima_ironBall = new Image[4];
	public static Image[] ima_arrow = new Image[4];
	public static Image[] ima_fireBall = new Image[4];
	
	public static Image[][][] ima_devil_move = new Image[8][4][4];
	public static Image[][][] ima_devil_attack = new Image[8][4][2];
	
	public static Image[] ima_giddy = new Image[4];
	public static Image[] ima_burning = new Image[3];
	public static Image[] ima_die = new Image[4];
	public static Image img_bang;
	
	public static Image[] ima_coin = new Image[2];

	/**
	 * 加载所有士兵及英雄用到的图片
	 */
	public static void loadHeroImage() {
		int w, h;
		try {
			/*加载英雄攻击图片*/
			for(int i=0; i<5; i++) {//1个英雄和5中士兵
				for(int k=0; k<4; k++) {//四个方向
					StringBuffer imageUrl = new StringBuffer();
					imageUrl.append("/hero/");
					imageUrl.append(i);
					imageUrl.append("_attack_");
					imageUrl.append(k + 1);
					imageUrl.append(".png");
					Image temp_hero_attack = Image.createImage(imageUrl.toString());
					w = temp_hero_attack.getWidth();
					h = temp_hero_attack.getHeight();
					for(int j=0; j<2; j++) {
						ima_hero_attack[i][k][j] = Image.createImage(temp_hero_attack, (w >> 1) * j, 0, w >> 1, h, 0);
					}
				}
			}
			
			/*加载英雄移动图片*/
			for(int i=0; i<6; i++) {//1个英雄和5中士兵
				for(int k=0; k<4; k++) {//四个方向
					StringBuffer imageUrl = new StringBuffer();
					imageUrl.append("/hero/");
					imageUrl.append(i);
					imageUrl.append("_move_");
					imageUrl.append(k + 1);
					imageUrl.append(".png");
					Image temp_hero_move = Image.createImage(imageUrl.toString());
					w = temp_hero_move.getWidth();
					h = temp_hero_move.getHeight();
					for(int j=0; j<4; j++) {
						ima_hero_move[i][k][j] = Image.createImage(temp_hero_move, w / 4 * j, 0, w / 4, h, 0);
					}
				}
			}
			
			/*加载英雄铁球*/
			Image image_attack = Image.createImage("/hero/ironBall.png");
			w = image_attack.getWidth();
			h = image_attack.getHeight();
			for(int i=0; i<4; i++) {
				ima_ironBall[i] = Image.createImage(image_attack, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			/*加载士兵2的箭*/
			Image img_effect = Image.createImage("/hero/arrow.png");
			w = img_effect.getWidth();
			h = img_effect.getHeight();
			ima_arrow[0] = img_effect;
			ima_arrow[1] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT270);
			ima_arrow[2] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT180);
			ima_arrow[3] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT90);
			
			/*加载士兵3的火球*/
			Image img_fire = Image.createImage("/hero/fireBall.png");
			w = img_fire.getWidth();
			h = img_fire.getHeight();
			ima_fireBall[0] = Image.createImage(img_fire, 0, 0, w >> 2, h, 0);
			ima_fireBall[1] = Image.createImage(img_fire, (w >> 2) * 3, 0, w >> 2, h, 0);
			ima_fireBall[2] = Image.createImage(img_fire, (w >> 2) * 2, 0, w >> 2, h, 0);
			ima_fireBall[3] = Image.createImage(img_fire, (w >> 2), 0, w >> 2, h, 0);
			
			/*加载金币图片*/
			ima_coin[0] = Image.createImage("/hero/coin_0.png");
			ima_coin[1] = Image.createImage("/hero/coin_1.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载怪物所有的图片
	 */
	public static void loadDevilImage() {
		int w,h;
		try {
			/*加载怪物移动图片*/
			for(int i=0; i<8; i++) {//八中怪物
				for(int k=0; k<4; k++) {//四个方向
					if(k == 0) {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_move_3.png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<4; j++) {
							ima_devil_move[i][k][j] = Image.createImage(temp_devil, w / 4 * j, 0, w / 4, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_move_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<4; j++) {
							ima_devil_move[i][k][j] = Image.createImage(temp_devil, w / 4 * j, 0, w / 4, h, 0);
						}
					}
				}
			}
			
			/*加载怪物攻击图片*/
			for(int i=0; i<8; i++) {//八中怪物
				for(int k=0; k<4; k++) {//四个方向
					if(k == 0) {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_attack_" + 3 + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ima_devil_move[i][k][j] = Image.createImage(temp_devil, w / 4 * j, 0, w / 4, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_attack_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ima_devil_move[i][k][j] = Image.createImage(temp_devil, w / 4 * j, 0, w / 4, h, 0);
						}
					}
				}
			}
			
			/*加载被英雄攻击后的效果图*/
			img_bang = Image.createImage("/devil/bang.png");
			Image temp_giddy = Image.createImage("/devil/giddy.png");
			w = temp_giddy.getWidth();
			h = temp_giddy.getHeight();
			for(int i=0; i<4; i++) {
				ima_giddy[i] = Image.createImage(temp_giddy, 0 + (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			/*加载被士兵3攻击后的效果图*/
			Image temp_burning = Image.createImage("/devil/burning.png");
			w = temp_burning.getWidth();
			h = temp_burning.getHeight();
			for(int i=0; i<3; i++) {
				ima_burning[i] = Image.createImage(temp_burning, (w / 3) * i, 0, w / 3, h, 0);
			}
			
			/*加载死亡时消失的图片*/
			Image temp_die = Image.createImage("/devil/die.png");
			w = temp_die.getWidth();
			h = temp_die.getHeight();
			for(int i=0; i<4; i++) {
				ima_die[i] = Image.createImage(temp_die, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
