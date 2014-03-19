package com.dave.soldierHunt.tool;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class ImageManager {
	public static Image[][][] ima_hero_move = new Image[6][4][4];
	public static Image[][][] ima_hero_attack = new Image[6][4][2];
	public static Image img_shadow;
	
	public static Image[] ima_ironBall = new Image[4];
	public static Image[] ima_arrow = new Image[4];
	public static Image[] ima_fireBall = new Image[4];
	public static Image[] ima_protected = new Image[2];
	public static Image[][] ima_hero_beHitClose = new Image[4][2];
	public static Image[][] ima_hero_beHitRemote = new Image[4][4];
//	public static Image img_help;
	
	public static Image[][][] ima_devil_move = new Image[8][4][4];
	public static Image[][][] ima_devil_attack = new Image[8][4][2];
	public static Image[][][] ima_devil_attack_extra = new Image[4][4][2];
	
	public static Image[] ima_giddy = new Image[4];
	public static Image[] ima_arrowHit = new Image[2];
	public static Image[] ima_burning = new Image[3];
	public static Image[] ima_die = new Image[4];
	public static Image img_bang;
	public static Image[][] ima_devil_attackEffect = new Image[4][4];
	
	public static Image[] ima_hit = new Image[3];
	
	public static Image[] ima_coin = new Image[6];
	public static Image[] ima_join = new Image[4];
	
	public static Image[] ima_obstacle = new Image[8];

	/**
	 * 加载所有士兵及英雄用到的图片
	 */
	public static void loadHeroImage() {
		int w, h;
		try {
			/*阴影效果*/
			img_shadow = Image.createImage("/hero/shadow.png");
			
			/*加载英雄攻击图片*/
			for(int i=0; i<5; i++) {//1个英雄和4中士兵
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
						ima_hero_move[i][k][j] = Image.createImage(temp_hero_move, (w >> 2) * j, 0, w >> 2, h, 0);
					}
				}
			}
			
			/*加载英雄被攻击效果*/
			for(int i=0; i<2; i++) {
				Image image_beHit = Image.createImage("/hero/beHit_" + (i + 1) + ".png");
				w = image_beHit.getWidth();
				h = image_beHit.getHeight();
				for(int j=0; j<2; j++) {
					ima_hero_beHitClose[i][j] = Image.createImage(image_beHit, (w >> 1) * j, 0, w >> 1, h, 0);
				}
			}
			
			for(int i=0; i<4; i++) {
				Image image_beHit = Image.createImage("/hero/beHit_" + (i + 5) + ".png");
				w = image_beHit.getWidth();
				h = image_beHit.getHeight();
				for(int j=0; j<4; j++) {
					ima_hero_beHitRemote[i][j] = Image.createImage(image_beHit, (w >> 2) * j, 0, w >> 2, h, 0);
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
			Image img_coin = Image.createImage("/hero/coin.png");
			w = img_coin.getWidth();
			h = img_coin.getHeight();
			for(int i=0; i<6; i++) {
				ima_coin[i] = Image.createImage(img_coin, w / 6 * i, 0, w / 6, h, 0);
			}
			
			/*加载近战士兵攻击碰撞特效*/
			Image img_hit = Image.createImage("/hero/hit.png");
			w = img_hit.getWidth();
			h = img_hit.getHeight();
			for(int i=0; i<3; i++) {
				ima_hit[i] = Image.createImage(img_hit, w / 3 * i, 0, w / 3, h, 0);
			}
			
//			/*help 图片*/
//			img_help = Image.createImage("/hero/help.png");
			
			/*归队图片*/
			Image img_join = Image.createImage("/hero/join.png");
			w = img_join.getWidth();
			h = img_join.getHeight();
			for(int i=0; i<4; i++) {
				ima_join[i] = Image.createImage(img_join, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			/*无敌状态图片*/
			Image img_protected = Image.createImage("/hero/protected.png");
			w = img_protected.getWidth();
			h = img_protected.getHeight();
			for(int i=0; i<2; i++) {
				ima_protected[i] = Image.createImage(img_protected, (w >> 1) * i, 0, w >> 1, h, 0);
			}
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
							ima_devil_move[i][k][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_move_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<4; j++) {
							ima_devil_move[i][k][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, 0);
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
							ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_attack_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, 0);
						}
					}
				}
			}
			
			/*加载近战怪攻击效果图片*/
			for(int i=0; i<4; i++) {//四种近战怪
				for(int k=0; k<4; k++) {//四个方向
					if(k == 0) {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_attack_" + 3 + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = Image.createImage("/devil/" + (i + 1) + "_attack_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, 0);
						}
					}
				}
			}
			
			/*加载怪物攻击效果图片*/
			for(int i=0; i<4; i++) {//四种远程怪物
				Image temp_devil = Image.createImage("/devil/" + (i + 5) + "_effect.png");
				w = temp_devil.getWidth();
				h = temp_devil.getHeight();
				for(int j=0; j<4; j++) {
					ima_devil_attackEffect[i][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, Sprite.TRANS_MIRROR);
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
			
			/*加载被士兵2攻击后的效果图*/
			Image temp_arrowHit = Image.createImage("/devil/arrowHit.png");
			w = temp_arrowHit.getWidth();
			h = temp_arrowHit.getHeight();
			for(int i=0; i<2; i++) {
				ima_arrowHit[i] = Image.createImage(temp_arrowHit, (w >> 1) * i, 0, w >> 1, h, 0);
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
	
	/**
	 * 移除英雄图片
	 */
	public static void removeHeroImage() {
		for(int i=0; i<6; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<4; k++) {
					ima_hero_move[i][j][k] = null;
				}
			}
		}
		for(int i=0; i<6; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<2; k++) {
					ima_hero_attack[i][j][k] = null;
				}
			}
		}
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<2; j++) {
				ima_hero_beHitClose[i][j] = null;
			}
		}
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				ima_hero_beHitRemote[i][j] = null;
			}
		}
		
		for(int i=0; i<4; i++) {
			ima_ironBall[i] = null;
		}
		for(int i=0; i<4; i++) {
			ima_arrow[i] = null;
		}
		for(int i=0; i<4; i++) {
			ima_fireBall[i] = null;
		}
	
		for(int i=0; i<3; i++) {
			ima_hit[i] = null;
		}
		for(int i=0; i<2; i++) {
			ima_coin[i] = null;
		}
		for(int i=0; i<4; i++) {
			ima_join[i] = null;
		}
		for(int i=0; i<2; i++) {
			ima_protected[i] = null;
		}
		
		img_shadow = null;
//		img_help = null;
		
		System.gc();
		
	}
	
	
	/**
	 * 移除怪物图片
	 */
	public static void removeDevilImage() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<4; k++) {
					ima_devil_move[i][j][k] = null;
				}
			}
		}
		for(int i=0; i<8; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<2; k++) {
					ima_devil_attack[i][j][k] = null;
				}
			}
		}
		for(int i=0; i<2; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<2; k++) {
					ima_devil_attack_extra[i][j][k] = null;
				}
			}
		}
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				ima_devil_attackEffect[i][j] = null;
			}
		}
		
		for(int i=0; i<4; i++) {
			ima_giddy[i] = null;
		}
		for(int i=0; i<2; i++) {
			ima_arrowHit[i] = null;
		}
		for(int i=0; i<3; i++) {
			ima_burning[i] = null;
		}
		for(int i=0; i<4; i++) {
			ima_die[i] = null;
		}
		
		img_bang = null;
		
		System.gc();
	}
	
}
