package com.dave.gowhere.intity;

import com.dave.gowhere.view.Game;

public class MapCreater {
	private Game game;
	
	private int lastDisCr;//��һ������·��ʱ�ľ���
	
	private int nextDisCr;//��һ������·��Ҫ�߹����پ���
	
	private int outDis;//�����ľ���洢
	
	/**
	 * ��ͼ����
	 */
	private int row_map = 2, col_map = 0;

	public MapCreater(Game game) {
		this.game = game;
	}
	
	public void createInitMap() {
//		game.addMapEl(new Road(game, 200 , 390));
		for (int j = 0; j < 12; j++) {
//			game.addMapEl(new Road(game, 60 + j * 80, 430, 0));
			switch(game.mapArray[row_map][col_map]) {
			case 1:
				game.addMapEl(new Road(game, 60 + j * 80, 350, game.a_img_road));
				break;
			case 2:
				game.addMapEl(new Road(game, 60 + j * 80, 365, game.a_img_road));
				break;
			case 3:
				game.addMapEl(new Road(game, 60 + j * 80, 380, game.a_img_road));
				break;
			case 4:
				game.addMapEl(new Road(game, 60 + j * 80, 335, game.a_img_road));
				break;
			case 5:
				game.addMapEl(new Road(game, 60 + j * 80, 320, game.a_img_road));
				break;
//			case 6:
//				game.addMapEl(new Road(game, 60 + j * 80, 300, game.a_img_road));
//				break;
//			case 7://��
//				game.addMapEl(new Road(game, 60 + j * 80, 400, 2));
//				break;
			case 8://�����
				game.addMapEl(new MonsterAir(game, 60 + j * 80, 350, game.a_img_monsterAir));
				break;
			case 11://����
				game.addMapEl(new Thorn(game, 60 + j * 80, 360, game.a_img_thorn));
				break;
//			case 14:
//				break;
			case 15://���ϵĹ���
				game.addMapEl(new Road(game, 60 + j * 80, 350, game.a_img_road));
				game.addMapEl(new MonsterEarth(game, 60 + j * 80, 310, game.a_img_monsterEarth));
				break;
			}
			
			int startY = 350;
			if(game.sizeMapElV > 0) {
				if(((MapElements)game.v_MapEl.elementAt(game.sizeMapElV - 1)).getType() == MapElements.TYPE_ROAD)
					startY = ((MapElements)game.v_MapEl.elementAt(game.sizeMapElV - 1)).y;
			}
			switch (game.mapArray[row_map - 1][col_map]) {
			case 77://������1
				for (int i = 0; i < 8; i++) {
					game.v_gold.addElement(new Gold(game, 110 + j * 80 + i * 20, startY - 100, 0));
					game.v_gold.addElement(new Gold(game, 270 + j * 80 + i * 20, startY - 50, 0));
					game.v_gold.addElement(new Gold(game, 430 + j * 80 + i * 20, startY - 100, 0));
				}
				
				for (int i = 0; i < 4; i++) {
					game.v_gold.addElement(new Gold(game, 630 + j * 80, startY - 120 + i * 20, 0));
					game.v_gold.addElement(new Gold(game, 700 + j * 80, startY - 120 + i * 20, 0));
				}
				game.v_gold.addElement(new Gold(game, 700 + j * 80, startY - 20, 0));
				game.v_gold.addElement(new Gold(game, 630 + j * 80, startY - 20, 0));
				break;
			}
			
			switch (game.mapArray[row_map - 2][col_map]) {
			case 16://�����ٶ�Ϊ8px/frame
				game.speedMax = 8;
				break;
			case 17:
				game.speedMax = 12;
				break;
			case 18:
				game.speedMax = 15;
				break;
			case 19:
				game.speedMax = 20;
				break;
			case 9://�ϰ�
//				game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, ((Road)game.v_MapEl.elementAt(game.v_MapEl.size() - 1)).y - 330, 4));
				break;
			}
			col_map ++;
		}
		
		lastDisCr = game.runDistance;
		nextDisCr = 80;
	}
	
	/**
	 * ���ɵ�ͼ��������ͼ��Ҫ������û֡����һ�Σ������߹��ľ��룬������ڵ���һ�����ӵľ���������һ���ͼԪ�ء�
	 */
	public void createMap() {
		if(game.runDistance - lastDisCr >= nextDisCr) {//�ж���һ������Ԫ�غ��߹��ľ���
			outDis += game.runDistance - lastDisCr - nextDisCr;//�洢����һ��ľ���
			
//			System.out.println("��ͼ��" + Game.mapArray[row_map][col_map]);
			createMapElement();//����һ����ͼԪ��
			
			//////////�ƶ���ͼ�����±꣬�����ڴ˿��Ƶ�ͼѭ��/////////////
			col_map ++;
//			System.out.println("col_map----" + col_map);
			if(col_map > 63) {
				col_map = 0;
				row_map += 3;
				if(row_map > game.mapArray.length - 1) {
					game.canvasControl.getMidlet().exitGame();
					row_map = 2;
				}
			}
			
			lastDisCr = game.runDistance;//�������ɵ�ͼ�ľ���
			
			//////////////ÿ��ʣ��ľ����ܺ��ж�///////////
			if(outDis >= nextDisCr) {//�洢�ľ������һ��ľ���ʱ����һ��Ԫ��
				outDis -= nextDisCr;
				createMapElement();
				
				///////////////һ��////////////////
				col_map ++;
				if(col_map > 63) {
					col_map = 0;
					row_map += 3;
					if(row_map > game.mapArray.length - 1) {
						game.canvasControl.getMidlet().exitGame();
						row_map = 2;
					}
				}
			}
		}
	}

	/**
	 * ����һ��Ԫ�أ�ȡ����ͼ�����ж����ɵ�ͼԪ�ء���������ͬʱ������
	 * @param outDis
	 */
	private void createMapElement() {
//		System.out.println("��ͼ��" + Game.mapArray[row_map][col_map]);
		int disCtrl = game.speedMax;//�������ٶ���صĽ����������
		if(game.baby.flying)
			disCtrl = game.speedOld;
		
		
		int startY = 365;//�������ɵ�ͼԪ�ص�y����
		if(game.sizeMapElV > 0) {
			int type = ((MapElements)game.v_MapEl.elementAt(game.sizeMapElV - 1)).getType();
			if( type == MapElements.TYPE_ROAD || type == MapElements.TYPE_FUTI || type == MapElements.TYPE_SPRING)
				startY = ((MapElements)game.v_MapEl.elementAt(game.sizeMapElV - 1)).y;
//			else if(game.v_futi.size() > 0)
//				startY = ((FuTi)game.v_futi.elementAt(game.v_futi.size() - 1)).y;
		}
		
		int bottomLayer = game.mapArray[row_map][col_map];//�ײ㣬��·�㣬�й���̡�
		switch(bottomLayer) {
		case 0:
			if(game.fuTi) {//����
				game.addMapEl(new FuTi(game, 60 + 11 * 80 - outDis, startY, game.a_img_futi));
			}
			break;
		case 1:
			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 350, game.a_img_road));
			break;
		case 2:
			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 365, game.a_img_road));
			break;
		case 3:
			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 380, game.a_img_road));
			break;
		case 4:
			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 335, game.a_img_road));
			break;
		case 5:
			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 320, game.a_img_road));
			break;
//		case 6:
//			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 300, game.a_img_road));
//			break;
		case 7://��
			if(game.fuTi) {//����
				game.addMapEl(new FuTi(game, 60 + 11 * 80 - outDis, startY, game.a_img_futi));
			} else {
				game.addMapEl(new Thorn(game, 60 + 11 * 80 - outDis, 400, game.a_img_thorn));
			}
			break;
		case 8://�����
			if(game.fuTi) {//����
				game.addMapEl(new FuTi(game, 60 + 11 * 80 - outDis, startY, game.a_img_futi));
			} else {
				game.addMapEl(new MonsterEarth(game, 60 + 11 * 80 - outDis, 360, game.a_img_monsterEarth));
			}
			break;
		case 10://�����
			if(game.fuTi) {//����
				game.addMapEl(new FuTi(game, 60 + 11 * 80 - outDis, startY, game.a_img_futi));
			} else {
				game.addMapEl(new MonsterAir(game, 60 + 11 * 80 - outDis, 360, game.a_img_monsterAir));
			}
			break;
		case 11://����
			game.addMapEl(new Spring(game, 60 + 11 * 80 - outDis, 360, game.a_img_spring));
			break;
//		case 14:
//			break;
		case 15://����
			game.addMapEl(new Road(game, 60 + 11 * 80 - outDis, 350, game.a_img_road));
			game.addMapEl(new MonsterEarth(game, 60 + 11 * 80 - outDis, 310, game.a_img_monsterEarth));
			break;
		case 20://20��24����Ӹߵ���
		case 21:
		case 22:
		case 23:
		case 24:
			game.addMapEl(new Brick(game, 60 + 11 * 80 - outDis, 350 - (bottomLayer - 20) * 10, game.a_img_brick));
			break;
		}
		
		
		startY = 320;
		if(game.sizeMapElV > 0) {
			if(((MapElements)game.v_MapEl.elementAt(game.sizeMapElV - 1)).getType() == MapElements.TYPE_ROAD)
				startY = ((MapElements)game.v_MapEl.elementAt(game.sizeMapElV - 1)).y;
		}
		int centerLayer = game.mapArray[row_map - 1][col_map];//��ͼ�м�㣬��Ҳ㡣Ҳ�й��ﵼ����
		switch (centerLayer) {
		case 20://20��24����Ӹߵ���
		case 21:
		case 22:
		case 23:
		case 24:
			game.addMapEl(new Brick(game, 60 + 11 * 80 - outDis, startY - 90 - (centerLayer - 20) * 20, game.a_img_brick));
			break;
			
		case 10://�����
			game.addMapEl(new MonsterAir(game, 60 + 11 * 80 - outDis, 240, game.a_img_monsterAir));
			break;
		case 14://����
			game.v_missile.addElement(new Missile(game, 60 + 11 * 80 - outDis, startY - 40, game.a_img_missile));
			break;
			
		case 77://������1
			for (int i = 0; i < 8; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 100, 0));
				game.v_gold.addElement(new Gold(game, 190 + 11 * 80 - outDis + i * 20, startY - 50, 0));
				game.v_gold.addElement(new Gold(game, 350 + 11 * 80 - outDis + i * 20, startY - 100, 0));
			}
			
			for (int i = 0; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 550 + 11 * 80 - outDis, startY - 120 + i * 20, 0));
				game.v_gold.addElement(new Gold(game, 620 + 11 * 80 - outDis, startY - 120 + i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 620 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 550 + 11 * 80 - outDis, startY - 20, 0));
			break;
		case 78://������2
			for (int i = 0; i < 5; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 10, startY - 60 - i * 20, 0));
				game.v_gold.addElement(new Gold(game, 190 + 11 * 80 - outDis + i * 10, startY - 60 - i * 20, 0));
			}
			for (int i = 0; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 80 + 11 * 80 - outDis + i * 10, startY - 120 + i * 20, 0));
				game.v_gold.addElement(new Gold(game, 240 + 11 * 80 - outDis + i * 10, startY - 120 + i * 20, 0));
			}
			
			for (int i = 0; i < 5; i++) {
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis + i * 20, startY - 30, 0));
			}
			break;
		case 79://������3
			for (int i = 0; i < 5; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 13 + i * (disCtrl - 8), startY - 60 - i * 20, 0));
				game.v_gold.addElement(new Gold(game, 134 + 11 * 80 - outDis + i * 13 + (i + 8) * (disCtrl - 8), startY - 60 - i * 20, 0));
				game.v_gold.addElement(new Gold(game, 238 + 11 * 80 - outDis + i * 13 + (i + 16) * (disCtrl - 8), startY - 60 - i * 20, 0));
			}
			for (int i = 0; i < 3; i++) {
				game.v_gold.addElement(new Gold(game, 95 + 11 * 80 - outDis + i * 13 + (i + 5)* (disCtrl - 8), startY - 120 + i * 20, 0));
				game.v_gold.addElement(new Gold(game, 199 + 11 * 80 - outDis + i * 13 + (i + 13) * (disCtrl - 8), startY - 120 + i * 20, 0));
				game.v_gold.addElement(new Gold(game, 303 + 11 * 80 - outDis + i * 13 + (i + 21) * (disCtrl - 8), startY - 120 + i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 303 + 11 * 80 - outDis + 3 * 13 + (3 + 21) * (disCtrl - 8), startY - 120 + 3 * 20, 0));
			break;
		case 80://������4
			for (int i = 0; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 100, 0));
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis, startY - 100 + i * 20, 0));
				game.v_gold.addElement(new Gold(game, 130 + 11 * 80 - outDis + i * 20, startY - 40, 0));
			}
			break;
		case 81://������5
			for (int i = 0; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 40, 0));
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis, startY - 100 + i * 20, 0));
				game.v_gold.addElement(new Gold(game, 130 + 11 * 80 - outDis + i * 20, startY - 100, 0));
			}
			break;
		case 82://������6
			for (int i = 0; i < 7; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY, 0));
			}
			game.v_gold.addElement(new Gold(game, 170 + 11 * 80 - outDis, startY, 1));
			break;
		case 83://������7
			for (int i = 0; i < 7; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 170 + 11 * 80 - outDis, startY - 20, 1));
			break;
		case 84://������8
			for (int i = 1; i < 7; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 100, 0));
			}
			game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis, startY - 100, 1));
			game.v_gold.addElement(new Gold(game, 170 + 11 * 80 - outDis, startY - 100, 1));
			break;
		case 85://������9
			game.v_gold.addElement(new Gold(game, 90 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 100 + (disCtrl - 7) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 110 + 2 * (disCtrl - 7) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 125 + 3 * (disCtrl - 7) + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 145 + 4 * (disCtrl - 7) + 11 * 80 - outDis, startY - 150, 0));
			game.v_gold.addElement(new Gold(game, 165 + 5 * (disCtrl - 7) + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 180 + 6 * (disCtrl - 7) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 190 + 7 * (disCtrl - 7) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 200 + 8 * (disCtrl - 7) + 11 * 80 - outDis, startY - 20, 0));
			break;
		case 86://������10
			game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 40 + (disCtrl - 7) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 50 + 2 * (disCtrl - 7) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 65 + 3 * (disCtrl - 7) + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 85 + 4 * (disCtrl - 7) + 11 * 80 - outDis, startY - 150, 0));
			game.v_gold.addElement(new Gold(game, 105 + 5 * (disCtrl - 7) + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 120 + 6 * (disCtrl - 7) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 130 + 7 * (disCtrl - 7) + 11 * 80 - outDis, startY - 60, 0));
			break;
		case 87://������11
			for (int i = 0; i < 8; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 100, 0));
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 80, 0));
			}
			break;
		case 88://������12
			for (int i = 0; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 30, 0));
				game.v_gold.addElement(new Gold(game, 150 + 11 * 80 - outDis, startY - 30 - i * 20, 0));
				game.v_gold.addElement(new Gold(game, 190 + 11 * 80 - outDis + i * 20, startY - 30, 0));
				
			}
			break;
		case 89://������13
			for (int i = 1; i < 5; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20 + i * (disCtrl - 8), startY - 30 - i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis, startY - 30, 1));
			break;
		case 90://������14
			for (int i = 1; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 30, 0));
			}
			for (int i = 0; i < 5; i++) {
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis + i * 20 + i * (disCtrl - 8), startY - 30 - i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis, startY - 30, 1));
			break;
		case 91://������15
			for (int i = 0; i < 3; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 30 - i * 20, 0));
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis + i * 20, startY - 110 - i * 20, 0));
				game.v_gold.addElement(new Gold(game, 190 + 11 * 80 - outDis + i * 20, startY - 190 - i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 90 + 11 * 80 - outDis, startY - 90, 1));
			game.v_gold.addElement(new Gold(game, 170 + 11 * 80 - outDis, startY - 170, 1));
			break;
		case 92://������16
			for (int i = 0; i < 3; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 30, 0));
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis + i * 20, startY - 30, 0));
			}
			game.v_gold.addElement(new Gold(game, 90 + 11 * 80 - outDis, startY - 30, 1));
			
			break;
		case 93://������17
			for (int i = 0; i < 9; i++) {
				if(i == 4) {
					game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 30, 1));
					game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 110, 1));
				} else {
					game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 30, 0));
					game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 110, 0));
				}
				
			}
			for (int i = 0; i < 3; i++) {
				if(i == 1) {
					game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis, startY - 50 - i * 20, 1));
					game.v_gold.addElement(new Gold(game, 190 + 11 * 80 - outDis, startY - 50 - i * 20, 1));
				} else {
					game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis, startY - 50 - i * 20, 0));
					game.v_gold.addElement(new Gold(game, 190 + 11 * 80 - outDis, startY - 50 - i * 20, 0));
				}
			}
			break;
		case 94://������18
			for (int i = 0; i < 5; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 15, startY - 60 - i * 10, 0));
			}
			for (int i = 0; i < 3; i++) {
				game.v_gold.addElement(new Gold(game, 105 + 11 * 80 - outDis + i * 15, startY - 90 + i * 10, 0));
			}
			for (int i = 0; i < 9; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 15, startY - 60, 0));
			}
			game.v_gold.addElement(new Gold(game, 90 + 11 * 80 - outDis, startY - 80, 1));
			break;
		case 95://������19
			for (int i = 0; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis, startY - 20, 1));
			for (int i = 1; i < 3; i++) {
				game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis + i * 10, startY - 20 - i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 110 + 11 * 80 - outDis + 3 * 10, startY - 20 - 3 * 20, 1));
			for (int i = 1; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 140 + 11 * 80 - outDis + i * 20, startY - 80, 0));
			}
			for (int i = 1; i < 3; i++) {
				game.v_gold.addElement(new Gold(game, 200 + 11 * 80 - outDis + i * 10, startY - 80 - i * 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 200 + 11 * 80 - outDis + 3 * 10, startY - 80 - 3 * 20, 1));
			for (int i = 1; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 200 + 11 * 80 - outDis + i * 20, startY - 140, 0));
			}
			game.v_gold.addElement(new Gold(game, 280 + 11 * 80 - outDis, startY - 140, 1));
			for (int i = 1; i < 7; i++) {
				if(i == 4) 
					game.v_gold.addElement(new Gold(game, 280 + 11 * 80 - outDis + i * 10, startY - 140 + i * 20, 1));
				else 
					game.v_gold.addElement(new Gold(game, 280 + 11 * 80 - outDis + i * 10, startY - 140 + i * 20, 0));
			}
			for (int i = 0; i < 29; i++) {
				if(i % 4 == 3)
					game.v_gold.addElement(new Gold(game, 350 + 11 * 80 - outDis + i * 20, startY - 20, 1));
				else
					game.v_gold.addElement(new Gold(game, 350 + 11 * 80 - outDis + i * 20, startY - 20, 0));
			}
			for (int i = 1; i < 7; i++) {
				if(i == 4) 
					game.v_gold.addElement(new Gold(game, 920 + 11 * 80 - outDis + i * 10, startY - 20 - i * 20, 1));
				else 
					game.v_gold.addElement(new Gold(game, 920 + 11 * 80 - outDis + i * 10, startY - 20 - i * 20, 0));
			}
			for (int i = 1; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 980 + 11 * 80 - outDis + i * 20, startY - 140, 0));
			}
			game.v_gold.addElement(new Gold(game, 1060 + 11 * 80 - outDis, startY - 140, 1));
			
			for (int i = 1; i < 6; i++) {
				if(i == 4) 
					game.v_gold.addElement(new Gold(game, 1060 + 11 * 80 - outDis, startY - 10 - i * 20, 1));
				else 
					game.v_gold.addElement(new Gold(game, 1060 + 11 * 80 - outDis, startY - 10 - i * 20, 0));
			}
			
			for (int i = 1; i < 4; i++) {
				game.v_gold.addElement(new Gold(game, 1060 + 11 * 80 - outDis + i * 20, startY - 20, 0));
			}
			game.v_gold.addElement(new Gold(game, 1140 + 11 * 80 - outDis, startY - 20, 1));
			
			break;
		case 58://���20
			game.v_gold.addElement(new Gold(game, 90 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 100 + (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 110 + 2 * (disCtrl - 8) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 125 + 3 * (disCtrl - 8) + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 145 + 4 * (disCtrl - 8) + 11 * 80 - outDis, startY - 150, 0));
			game.v_gold.addElement(new Gold(game, 165 + 5 * (disCtrl - 8) + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 180 + 6 * (disCtrl - 8) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 190 + 7 * (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 205 + 8 * (disCtrl - 8) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 225 + 9 * (disCtrl - 8) + 11 * 80 - outDis, startY - 120, 0));
			game.v_gold.addElement(new Gold(game, 245 + 10 * (disCtrl - 8) + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 260 + 11 * (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 270 + 12 * (disCtrl - 8) + 11 * 80 - outDis, startY - 20, 0));
//			game.v_gold.addElement(new Gold(game, 270 + 7 * (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
//			game.v_gold.addElement(new Gold(game, 280 + 7 * (disCtrl - 8) + 11 * 80 - outDis, startY - 20, 0));
			break;
		case 59://���21
			game.v_gold.addElement(new Gold(game, 70 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 80 + (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 90 + 2 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 105 + 3 * (disCtrl - 8) + 11 * 80 - outDis, startY - 110, 0));
			game.v_gold.addElement(new Gold(game, 120 + 4 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 130 + 5 * (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 140 + 6 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 155 + 7 * (disCtrl - 8) + 11 * 80 - outDis, startY - 110, 0));
			game.v_gold.addElement(new Gold(game, 170 + 8 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 180 + 9 * (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 190 + 10 * (disCtrl - 8) + 11 * 80 - outDis, startY - 20, 0));
			break;
		case 60://���22
			game.v_gold.addElement(new Gold(game, 70 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 80 + (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 90 + 2 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 105 + 3 * (disCtrl - 8) + 11 * 80 - outDis, startY - 110, 0));
			game.v_gold.addElement(new Gold(game, 120 + 4 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 130 + 5 * (disCtrl - 8) + 11 * 80 - outDis, startY - 110, 0));
			game.v_gold.addElement(new Gold(game, 140 + 6 * (disCtrl - 8) + 11 * 80 - outDis, startY - 120, 0));
			game.v_gold.addElement(new Gold(game, 155 + 7 * (disCtrl - 8) + 11 * 80 - outDis, startY - 150, 0));
			game.v_gold.addElement(new Gold(game, 170 + 8 * (disCtrl - 8) + 11 * 80 - outDis, startY - 180, 0));
			game.v_gold.addElement(new Gold(game, 180 + 9 * (disCtrl - 8) + 11 * 80 - outDis, startY - 190, 0));
			game.v_gold.addElement(new Gold(game, 190 + 10 * (disCtrl - 8) + 11 * 80 - outDis, startY - 180, 0));
			break;
		case 61://���23
			game.v_gold.addElement(new Gold(game, 70 + 11 * 80 - outDis, startY, 0));
			game.v_gold.addElement(new Gold(game, 120 + 11 * 80 - outDis, startY - 50, 0));
			game.v_gold.addElement(new Gold(game, 180 + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 240 + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 300 + 11 * 80 - outDis, startY - 180, 0));
			game.v_gold.addElement(new Gold(game, 350 + 11 * 80 - outDis, startY - 200, 0));
			game.v_gold.addElement(new Gold(game, 400 + 11 * 80 - outDis, startY - 180, 0));
			game.v_gold.addElement(new Gold(game, 440 + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 480 + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 520 + 11 * 80 - outDis, startY - 50, 0));
			game.v_gold.addElement(new Gold(game, 560 + 11 * 80 - outDis, startY, 0));
			break;
		case 62://���24
			game.v_gold.addElement(new Gold(game, 70 + 11 * 80 - outDis, startY, 0));
			game.v_gold.addElement(new Gold(game, 120 + 11 * 80 - outDis, startY - 50, 0));
			game.v_gold.addElement(new Gold(game, 180 + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 240 + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 300 + 11 * 80 - outDis, startY - 180, 0));
			game.v_gold.addElement(new Gold(game, 350 + 11 * 80 - outDis, startY - 200, 0));
			game.v_gold.addElement(new Gold(game, 400 + 11 * 80 - outDis, startY - 180, 0));
			game.v_gold.addElement(new Gold(game, 440 + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 480 + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 520 + 11 * 80 - outDis, startY - 50, 0));
			game.v_gold.addElement(new Gold(game, 560 + 11 * 80 - outDis, startY - 80, 0));
			game.v_gold.addElement(new Gold(game, 575 + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 615 + 11 * 80 - outDis, startY - 120, 0));
			game.v_gold.addElement(new Gold(game, 655 + 11 * 80 - outDis, startY - 140, 0));
			game.v_gold.addElement(new Gold(game, 695 + 11 * 80 - outDis, startY - 120, 0));
			game.v_gold.addElement(new Gold(game, 735 + 11 * 80 - outDis, startY - 100, 0));
			game.v_gold.addElement(new Gold(game, 775 + 11 * 80 - outDis, startY - 80, 0));
			game.v_gold.addElement(new Gold(game, 805 + 11 * 80 - outDis, startY - 50, 0));
			game.v_gold.addElement(new Gold(game, 835 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 865 + 11 * 80 - outDis, startY - 0, 0));
			break;
		case 63://���25
			game.v_gold.addElement(new Gold(game, 70 + 11 * 80 - outDis, startY - 20, 0));
			game.v_gold.addElement(new Gold(game, 80 + (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 90 + 2 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 105 + 3 * (disCtrl - 8) + 11 * 80 - outDis, startY - 110, 0));
			game.v_gold.addElement(new Gold(game, 120 + 4 * (disCtrl - 8) + 11 * 80 - outDis, startY - 90, 0));
			game.v_gold.addElement(new Gold(game, 130 + 5 * (disCtrl - 8) + 11 * 80 - outDis, startY - 60, 0));
			game.v_gold.addElement(new Gold(game, 140 + 6 * (disCtrl - 8) + 11 * 80 - outDis, startY - 20, 0));
			break;
			
		}
		
		////////////////////���ϲ㣬���ϰ���������ٶȿ��Ƶ�///////////////////////
		switch (game.mapArray[row_map - 2][col_map]) {
		case 16://�����ٶ�Ϊ8px/frame
			if(game.baby.flying)
				game.speedOld = 8;
			else 
				game.speedMax = 8;
				
			break;
		case 17:
			if(game.baby.flying)
				game.speedOld = 12;
			else 
				game.speedMax = 12;
			break;
		case 18:
			if(game.baby.flying)
				game.speedOld = 15;
			else 
				game.speedMax = 15;
			break;
		case 19:
			if(game.baby.flying)
				game.speedOld = 20;
			else 
				game.speedMax = 20;
			break;
		case 10://�����
			game.addMapEl(new MonsterAir(game, 60 + 11 * 80 - outDis, 120, game.a_img_monsterAir));
			break;
		case 14://����
			game.v_missile.addElement(new Missile(game, 60 + 11 * 80 - outDis, startY - 140, game.a_img_missile));
			break;
		case 87://������11
			for (int i = 0; i < 8; i++) {
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 200, 0));
				game.v_gold.addElement(new Gold(game, 30 + 11 * 80 - outDis + i * 20, startY - 180, 0));
			}
			break;
		case 9://�ϰ�
			game.addMapEl(new Barrier(game, 60 + 11 * 80 - outDis, startY - 260, game.a_img_barrier));
			break;
		}
	}

}
