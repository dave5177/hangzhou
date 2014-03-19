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
	 * 0��load����վͼƬ��
	 * 1: load��ϷͼƬ.
	 * 2: load��ҳͼƬ��
	 * 3: load���а�ͼƬ��
	 */
	private final int type;
	
	private Image img_back;
	private Image img_loadingBar;
	private Image[] ima_soldier;
	
	private final CanvasControl canvasControl;

	/**
	 * ����վ����
	 */
	private GasStation gasStation;
	
	/**
	 * ��Ϸ����
	 */
	private Game game;
	
	/**
	 * ��ҳ����
	 */
	private Home home;
	
	/**
	 * ���а����
	 */
	private Ranking ranking;

	/**
	 * �ܹ����ض�����ͼƬ
	 */
	private int imgAmount;

	/**
	 * ������·��Ч��
	 */
	private int moveIndex;

	
	/**
	 * ��ϷͼƬ��ŵķ�������ַ
	 */
//	private final static String SERVER_URL = "http://222.46.20.151:8055/sbzj";//����
//	public static String SERVER_URL = "http://122.224.212.78:7878/GameResource/sbzj";//�㽭
	public static String SERVER_URL = "";//��jad��ȡ

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
			
			/*��ӰЧ��
			ImageManager.img_shadow = getImage("/hero/shadow.png");
			
			����Ӣ�۹���ͼƬ
			for(int i=0; i<5; i++) {//1��Ӣ�ۺ�4��ʿ��
				for(int k=0; k<4; k++) {//�ĸ�����
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
			

			����Ӣ���ƶ�ͼƬ
			for(int i=0; i<6; i++) {//1��Ӣ�ۺ�5��ʿ��
				for(int k=0; k<4; k++) {//�ĸ�����
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
			
			����Ӣ�۱�����Ч��
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
			
			����Ӣ������
			Image image_attack = getImage("/hero/ironBall.png");
			w = image_attack.getWidth();
			h = image_attack.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_ironBall[i] = Image.createImage(image_attack, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			����ʿ��2�ļ�
			Image img_effect = getImage("/hero/arrow.png");
			w = img_effect.getWidth();
			h = img_effect.getHeight();
			ImageManager.ima_arrow[0] = img_effect;
			ImageManager.ima_arrow[1] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT270);
			ImageManager.ima_arrow[2] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT180);
			ImageManager.ima_arrow[3] = Image.createImage(img_effect, 0, 0, w, h, Sprite.TRANS_ROT90);
			
			����ʿ��3�Ļ���
			Image img_fire = getImage("/hero/fireBall.png");
			w = img_fire.getWidth();
			h = img_fire.getHeight();
			ImageManager.ima_fireBall[0] = Image.createImage(img_fire, 0, 0, w >> 2, h, 0);
			ImageManager.ima_fireBall[1] = Image.createImage(img_fire, (w >> 2) * 3, 0, w >> 2, h, 0);
			ImageManager.ima_fireBall[2] = Image.createImage(img_fire, (w >> 2) * 2, 0, w >> 2, h, 0);
			ImageManager.ima_fireBall[3] = Image.createImage(img_fire, (w >> 2), 0, w >> 2, h, 0);
			
			���ؽ��ͼƬ
			ImageManager.ima_coin[0] = getImage("/hero/coin_0.png");
			ImageManager.ima_coin[1] = getImage("/hero/coin_1.png");
			
			���ؽ�սʿ��������ײ��Ч
			Image img_hit = getImage("/hero/hit.png");
			w = img_hit.getWidth();
			h = img_hit.getHeight();
			for(int i=0; i<3; i++) {
				ImageManager.ima_hit[i] = Image.createImage(img_hit, w / 3 * i, 0, w / 3, h, 0);
			}
			
			help ͼƬ
			ImageManager.img_help = getImage("/hero/help.png");
			
			���ͼƬ
			Image img_join = getImage("/hero/join.png");
			w = img_join.getWidth();
			h = img_join.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_join[i] = Image.createImage(img_join, (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			�޵�״̬ͼƬ
			Image img_protected = getImage("/hero/protected.png");
			w = img_protected.getWidth();
			h = img_protected.getHeight();
			for(int i=0; i<2; i++) {
				ImageManager.ima_protected[i] = Image.createImage(img_protected, (w >> 1) * i, 0, w >> 1, h, 0);
			}*/
			
			/*���ع����ƶ�ͼƬ*/
			for(int i=0; i<8; i++) {//���й���
				for(int k=1; k<4; k++) {//�ĸ�����
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
			
			/*���ع��﹥��ͼƬ*/
			for(int i=0; i<8; i++) {//���й���
				for(int k=0; k<4; k++) {//�ĸ�����
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
			
			/*���ؽ�ս�ֹ���Ч��ͼƬ*/
			for(int i=0; i<4; i++) {//���ֽ�ս��
				for(int k=0; k<4; k++) {//�ĸ�����
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
			
			/*���ع��﹥��Ч��ͼƬ*/
			for(int i=0; i<4; i++) {//����Զ�̹���
				Image temp_devil = getImage("/devil/" + (i + 5) + "_effect.png");
				w = temp_devil.getWidth();
				h = temp_devil.getHeight();
				for(int j=0; j<4; j++) {
					ImageManager.ima_devil_attackEffect[i][j] = Image.createImage(temp_devil, (w >> 2) * j, 0, w >> 2, h, Sprite.TRANS_MIRROR);
				}
			}
			
			/*���ر�Ӣ�۹������Ч��ͼ*/
			ImageManager.img_bang = getImage("/devil/bang.png");
			Image temp_giddy = getImage("/devil/giddy.png");
			w = temp_giddy.getWidth();
			h = temp_giddy.getHeight();
			for(int i=0; i<4; i++) {
				ImageManager.ima_giddy[i] = Image.createImage(temp_giddy, 0 + (w >> 2) * i, 0, w >> 2, h, 0);
			}
			
			/*���ر�ʿ��2�������Ч��ͼ*/
			Image temp_arrowHit = getImage("/devil/arrowHit.png");
			w = temp_arrowHit.getWidth();
			h = temp_arrowHit.getHeight();
			for(int i=0; i<2; i++) {
				ImageManager.ima_arrowHit[i] = Image.createImage(temp_arrowHit, (w >> 1) * i, 0, w >> 1, h, 0);
			}
			
			/*���ر�ʿ��3�������Ч��ͼ*/
			Image temp_burning = getImage("/devil/burning.png");
			w = temp_burning.getWidth();
			h = temp_burning.getHeight();
			for(int i=0; i<3; i++) {
				ImageManager.ima_burning[i] = Image.createImage(temp_burning, (w / 3) * i, 0, w / 3, h, 0);
			}
			
			/*��������ʱ��ʧ��ͼƬ*/
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
