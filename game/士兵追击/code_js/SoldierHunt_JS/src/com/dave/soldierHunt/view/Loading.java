package com.dave.soldierHunt.view;

import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.HttpLoadImage;
import com.dave.soldierHunt.tool.ImageManager;

public class Loading implements Showable, Runnable{
	/**
	 * 0：load加油站图片。
	 * 1: load游戏图片.
	 * 2: load主页图片。
	 * 3: load排行榜图片。
	 */
	private final int type;
	
	private Image img_back;
	private Image img_loadingBar;
	private Image[] ima_soldier;
	
	private final CanvasControl canvasControl;

	/**
	 * 加油站界面
	 */
	private GasStation gasStation;
	
	/**
	 * 游戏界面
	 */
	private Game game;
	
	/**
	 * 主页界面
	 */
	private Home home;
	
	/**
	 * 排行榜界面
	 */
	private Ranking ranking;

	/**
	 * 总共加载多少张图片
	 */
	private int imgAmount;

	/**
	 * 加载走路的效果
	 */
	private int moveIndex;

	
	/**
	 * 游戏图片存放的服务器地址
	 */
//	private final static String SERVER_URL = "http://222.46.20.151:8055/sbzj";//本地
//	public static String SERVER_URL = "http://122.224.212.78:7878/GameResource/sbzj";//浙江
	public static String SERVER_URL = "";//从jad获取

	public Loading(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
		
		launch();
	}

	private void launch() {
		switch(type) {
		case 0:
			gasStation = new GasStation(canvasControl);
			imgAmount = 7;
			
			new Thread(this).start();
			break;
		case 1:
			game = new Game(canvasControl, CanvasControl.level, GasStation.soldierCount);
			imgAmount = 82;
			new Thread(this).start();
			break;
		case 2:
			home = new Home(canvasControl);
			imgAmount = 3;
			new Thread(this).start();
			break;
		case 3:
			ranking = new Ranking(canvasControl);
			imgAmount = 3;
			new Thread(this).start();
			break;
		}
	}

	public void show(Graphics g) {
//		g.setColor(0, 255, 255);
//		g.fillRect(0, 0, 640, 530);
//		
		
//		g.fillRect(20, 450, 600, 20);
//		g.setColor(255, 0, 0);
//		g.fillRect(21, 451, 598 * HttpLoadImage.imgCount / imgAmount, 18);
		
		g.drawImage(img_back, 0, 0, 0);
		g.setClip(0, 0, 92 + 450 * HttpLoadImage.imgCount / imgAmount, 530);
		g.drawImage(img_loadingBar, 92, 424, 0);
		g.setClip(0, 0, 640, 530);
		
		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
		g.setColor(255, 255, 255);
		g.drawString("LOADING...", 320, 420,
				Graphics.BOTTOM | Graphics.HCENTER);
//		g.drawString(HttpLoadImage.imgCount * 100 / imgAmount + "%", 320, 443,
//				Graphics.BOTTOM | Graphics.HCENTER);
		
		g.drawImage(ima_soldier[moveIndex], 92 + 450 * HttpLoadImage.imgCount / imgAmount,
				424, Graphics.HCENTER | Graphics.VCENTER);
	}

	public void loadResource() {
		canvasControl.playerHandler.toPlay(0);
		
		try {
			img_back = Image.createImage("/loading/back.png");
			img_loadingBar = Image.createImage("/loading/loadingBar.png");
			
			ima_soldier = new Image[4];
			Image img_soldierMove = Image.createImage("/hero/1_move_1.png");
			int w = img_soldierMove.getWidth();
			int h = img_soldierMove.getHeight();
			for(int j=0; j<4; j++) {
				ima_soldier[j] = Image.createImage(img_soldierMove, (w >> 2) * j, 0, w >> 2, h, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		canvasControl.playerHandler.stop(0);
		
		img_back = null;
		img_loadingBar = null;
		
		for(int j=0; j<4; j++) {
			ima_soldier[j] = null;
		}
		
		ima_soldier = null;
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		moveIndex ++;
		if(moveIndex > 3) moveIndex = 0;
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

	public void run() {
		switch(type) {
		case 0:
			gasStation.img_back = getImage("/gasStation/back.png");
			gasStation.img_uplevel_chosed = getImage("/gasStation/uplevel_chosed.png");
			gasStation.img_uplevel_unchose = getImage("/gasStation/uplevel_unchose.png");
			gasStation.img_upteam_chosed = getImage("/gasStation/upteam_chosed.png");
			gasStation.img_upteam_unchose = getImage("/gasStation/upteam_unchose.png");
			gasStation.img_number = getImage("/gasStation/number.png");
//			gasStation.img_key_0 = getImage("/button/key_0.png");
			gasStation.img_select = getImage("/gasStation/select.png");
//
//			gasStation.ima_hero = new Image[2][4];
//			for(int i=0; i<2; i++) {
//				Image img_heroMove = getImage("/hero/0_move_" + (i * 2 + 1) + ".png");
//				int w = img_heroMove.getWidth();
//				int h = img_heroMove.getHeight();
//				for(int j=0; j<4; j++) {
//					gasStation.ima_hero[i][j] = Image.createImage(img_heroMove, (w >> 2) * j, 0, w >> 2, h, 0);
//				}
//			}
//			
//			gasStation.ima_soldier = new Image[2][4];
//			for(int i=0; i<2; i++) {
//				Image img_soldierMove = getImage("/hero/1_move_" + (i * 2 + 1) + ".png");
//				int w = img_soldierMove.getWidth();
//				int h = img_soldierMove.getHeight();
//				for(int j=0; j<4; j++) {
//					gasStation.ima_soldier[i][j] = Image.createImage(img_soldierMove, (w >> 2) * j, 0, w >> 2, h, 0);
//				}
//			}
//			
//			gasStation.img_shadow = getImage("/hero/shadow.png");
			
			break;
		case 1:
			int w, h;
			
			game.img_map = getImage("/game/map.png");
			
			/*阴影效果
			ImageManager.img_shadow = getImage("/hero/shadow.png");
			
			加载英雄攻击图片
			for(int i=0; i<5; i++) {//1个英雄和4中士兵
				for(int k=0; k<4; k++) {//四个方向
					StringBuffer imageUrl = new StringBuffer();
					imageUrl.append("/hero/");
					imageUrl.append(i);
					imageUrl.append("_attack_");
					imageUrl.append(k + 1);
					imageUrl.append(".png");
					Image temp_hero_attack = getImage(imageUrl.toString());
					w = temp_hero_attack.getWidth();
					h = temp_hero_attack.getHeight();
					for(int j=0; j<2; j++) {
						ImageManager.ima_hero_attack[i][k][j] = Image.createImage(temp_hero_attack, (w >> 1) * j, 0, w >> 1, h, 0);
					}
				}
			}
			

			加载英雄移动图片
			for(int i=0; i<6; i++) {//1个英雄和5中士兵
				for(int k=0; k<4; k++) {//四个方向
					StringBuffer imageUrl = new StringBuffer();
					imageUrl.append("/hero/");
					imageUrl.append(i);
					imageUrl.append("_move_");
					imageUrl.append(k + 1);
					imageUrl.append(".png");
					Image temp_hero_move = getImage(imageUrl.toString());
					w = temp_hero_move.getWidth();
					h = temp_hero_move.getHeight();
					for(int j=0; j<4; j++) {
						ImageManager.ima_hero_move[i][k][j] = Image.createImage(temp_hero_move, (w >> 2) * j, 0, w >> 2, h, 0);
					}
				}
			}
			
			加载英雄被攻击效果
			for(int i=0; i<2; i++) {
				Image image_beHit = getImage("/hero/beHit_" + (i + 1) + ".png");
				w = image_beHit.getWidth();
				h = image_beHit.getHeight();
				for(int j=0; j<2; j++) {
					ImageManager.ima_hero_beHitClose[i][j] = Image.createImage(image_beHit, (w >> 1) * j, 0, w >> 1, h, 0);
				}
			}
			
			for(int i=0; i<4; i++) {
				Image image_beHit = getImage("/hero/beHit_" + (i + 5) + ".png");
				w = image_beHit.getWidth();
				h = image_beHit.getHeight();
				for(int j=0; j<4; j++) {
					ImageManager.ima_hero_beHitRemote[i][j] = Image.createImage(image_beHit, (w >> 2) * j, 0, w >> 2, h, 0);
				}
			}
			
			加载英雄铁球
			Image image_attack = getImage("/hero/ironBall.png");
			w = image_attack.getWidth();
			h = image_attack.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_ironBall[i] = Image.createImage(image_attack, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			加载士兵2的箭
			Image img_effect = getImage("/hero/arrow.png");
			w = img_effect.getWidth();
			h = img_effect.getHeight();
			ImageManager.ima_arrow[0] = img_effect;
			ImageManager.ima_arrow[1] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT270);
			ImageManager.ima_arrow[2] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT180);
			ImageManager.ima_arrow[3] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT90);
			
			加载士兵3的火球
			Image img_fire = getImage("/hero/fireBall.png");
			w = img_fire.getWidth();
			h = img_fire.getHeight();
			ImageManager.ima_fireBall[0] = Image.createImage(img_fire, 0, 0, w >> 2, h, 0);
			ImageManager.ima_fireBall[1] = Image.createImage(img_fire, (w >> 2) * 3, 0, w >> 2, h, 0);
			ImageManager.ima_fireBall[2] = Image.createImage(img_fire, (w >> 2) * 2, 0, w >> 2, h, 0);
			ImageManager.ima_fireBall[3] = Image.createImage(img_fire, (w >> 2), 0, w >> 2, h, 0);
			
			加载金币图片
			ImageManager.ima_coin[0] = getImage("/hero/coin_0.png");
			ImageManager.ima_coin[1] = getImage("/hero/coin_1.png");
			
			加载近战士兵攻击碰撞特效
			Image img_hit = getImage("/hero/hit.png");
			w = img_hit.getWidth();
			h = img_hit.getHeight();
			for(int i=0; i<3; i++) {
				ImageManager.ima_hit[i] = Image.createImage(img_hit, w / 3 * i, 0, w / 3, h, 0);
			}
			
			help 图片
			ImageManager.img_help = getImage("/hero/help.png");
			
			归队图片
			Image img_join = getImage("/hero/join.png");
			w = img_join.getWidth();
			h = img_join.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_join[i] = Image.createImage(img_join, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			无敌状态图片
			Image img_protected = getImage("/hero/protected.png");
			w = img_protected.getWidth();
			h = img_protected.getHeight();
			for(int i=0; i<2; i++) {
				ImageManager.ima_protected[i] = Image.createImage(img_protected, (w >> 1) * i, 0, w >> 1, h, 0);
			}*/
			
			/*加载怪物移动图片*/
			for(int i=0; i<8; i++) {//八中怪物
				for(int k=1; k<4; k++) {//四个方向
					Image temp_devil = getImage("/devil/" + (i + 1) + "_move_" + (k+1) + ".png");
					w = temp_devil.getWidth();
					h = temp_devil.getHeight();
					for(int j=0; j<4; j++) {
						ImageManager.ima_devil_move[i][k][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, 0);
					}
					if(k == 2) {
						for(int j=0; j<4; j++) {
							ImageManager.ima_devil_move[i][0][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, Sprite.TRANS_MIRROR);
						}
					}
				}
			}
			
			/*加载怪物攻击图片*/
			for(int i=0; i<8; i++) {//八中怪物
				for(int k=0; k<4; k++) {//四个方向
					if(k == 0) {
						Image temp_devil = getImage("/devil/" + (i + 1) + "_attack_" + 3 + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ImageManager.ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = getImage("/devil/" + (i + 1) + "_attack_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ImageManager.ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, 0);
						}
					}
				}
			}
			
			/*加载近战怪攻击效果图片*/
			for(int i=0; i<4; i++) {//四种近战怪
				for(int k=0; k<4; k++) {//四个方向
					if(k == 0) {
						Image temp_devil = getImage("/devil/" + (i + 1) + "_attack_" + 3 + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ImageManager.ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, Sprite.TRANS_MIRROR);
						}
					} else {
						Image temp_devil = getImage("/devil/" + (i + 1) + "_attack_" + (k+1) + ".png");
						w = temp_devil.getWidth();
						h = temp_devil.getHeight();
						for(int j=0; j<2; j++) {
							ImageManager.ima_devil_attack[i][k][j] = Image.createImage(temp_devil, (w >> 1) * j, 0, w >> 1, h, 0);
						}
					}
				}
			}
			
			/*加载怪物攻击效果图片*/
			for(int i=0; i<4; i++) {//四种远程怪物
				Image temp_devil = getImage("/devil/" + (i + 5) + "_effect.png");
				w = temp_devil.getWidth();
				h = temp_devil.getHeight();
				for(int j=0; j<4; j++) {
					ImageManager.ima_devil_attackEffect[i][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, Sprite.TRANS_MIRROR);
				}
			}
			
			/*加载被英雄攻击后的效果图*/
			ImageManager.img_bang = getImage("/devil/bang.png");
			Image temp_giddy = getImage("/devil/giddy.png");
			w = temp_giddy.getWidth();
			h = temp_giddy.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_giddy[i] = Image.createImage(temp_giddy, 0 + (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			/*加载被士兵2攻击后的效果图*/
			Image temp_arrowHit = getImage("/devil/arrowHit.png");
			w = temp_arrowHit.getWidth();
			h = temp_arrowHit.getHeight();
			for(int i=0; i<2; i++) {
				ImageManager.ima_arrowHit[i] = Image.createImage(temp_arrowHit, (w >> 1) * i, 0, w >> 1, h, 0);
			}
			
			/*加载被士兵3攻击后的效果图*/
			Image temp_burning = getImage("/devil/burning.png");
			w = temp_burning.getWidth();
			h = temp_burning.getHeight();
			for(int i=0; i<3; i++) {
				ImageManager.ima_burning[i] = Image.createImage(temp_burning, (w / 3) * i, 0, w / 3, h, 0);
			}
			
			/*加载死亡时消失的图片*/
			Image temp_die = getImage("/devil/die.png");
			w = temp_die.getWidth();
			h = temp_die.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_die[i] = Image.createImage(temp_die, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			break;
			
		case 2:
			home.img_back = getImage("/home/back.png");
			
			Image temp_chosed = getImage("/home/chosed.png");
			Image temp_unchose = getImage("/home/unchose.png");
			w = temp_chosed.getWidth();
			h = temp_chosed.getHeight();
			int w_un = temp_unchose.getWidth();
			int h_un = temp_unchose.getHeight();
			
			home.ima_chosed = new Image[5];
			home.ima_unchose = new Image[5];
			
			for(int i=0; i<5; i++) {
				home.ima_chosed[i] = Image.createImage(temp_chosed, 0, h / 5 * i, w, h / 5, 0);
				home.ima_unchose[i] = Image.createImage(temp_unchose, 0, h_un / 5 * i, w_un, h_un / 5, 0);
			}
			
			break;
			
		case 3:
			ranking.img_back = getImage("/ranking/back.png");
			ranking.img_number = getImage("/ranking/number.png");
			ranking.img_number_me = getImage("/ranking/number_me.png");
			break;
		}
		
		gotoNextView();
	}
	
	public void gotoNextView() {
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		switch(type) {
		case 0:
			canvasControl.setView(gasStation);
			break;
		case 1:
			canvasControl.setView(game);
			break;
		case 2:
			canvasControl.setView(home);
			break;
		case 3:
			canvasControl.setView(ranking);
			break;
		}
		C.out(HttpLoadImage.imgCount + "");
		HttpLoadImage.imgCount = 0;
	}
	
	public Image getImage(String url) {
		String finalUrl = SERVER_URL + url;
		C.out(finalUrl);
		return HttpLoadImage.catchImage(finalUrl);
	}

}
