package com.zhangniu.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.zhangniu.update.ServerIptv;

public class Rank {

	public Screen s;

	public byte showContentStyle;// 显示什么东西系统值

	/**
	 * 页码索引
	 */
	private byte colIndex;
	private byte colSum = 4;

	/**
	 * 本类的图片对象
	 */
	private Image[] imagea_rank;

	/**
	 * 页码数字
	 */
	private Image[] imagea_pageNumber;

	/**
	 * 页码相关图标
	 * 
	 * @param mainscreen
	 */
	private Image[] imagea_pageicon;

	private final int fontPrizeColor = 0xa47700;// 奖品字体颜色

	private int vectorSize;

	/**
	 * 一个列中有几页
	 */
	private int rankPageSum;

	/**
	 * 公告中的相关值 遮盖部分坐标
	 */
	private int[][] position = { { 113, 117 }, // 横向第一个，遮盖部分 "总积分"
			{ 251, 117 }, // 横向第二个，遮盖部分 "关卡模式"
			{ 388, 117 }, // 横向第三个，遮盖部分 "计时模式"
			{ 527, 117 }, // 横向第四个，遮盖部分 "挑战模式"
	};

	public Rank(Screen mainscreen) {
		s = mainscreen;
	}

	/**
	 * rankinto
	 */
	public void init() {
		Image once = null;
		colIndex = 0;

		// vectorSize = C.vector_Rank_Total.size();//得到Vector的长度
		// C.out("vect长度:"+vectorSize);
		// if(vectorSize<=10){
		// rankPageSum=1;
		// }else if(vectorSize%10!=0){
		// rankPageSum=vectorSize/10+1;
		// }else{
		// rankPageSum=vectorSize/10;
		// }
		// C.out("排行榜:"+rankPageSum);

		once = null;
		imagea_rank = new Image[7];
		imagea_rank[0] = C.GetImageSource("/rank/rankback.png");

		imagea_rank[1] = C.GetImageSource("/rank/i1.png");
		imagea_rank[2] = C.GetImageSource("/rank/i2.png");
		imagea_rank[3] = C.GetImageSource("/rank/i3.png");
		imagea_rank[4] = C.GetImageSource("/rank/i4.png");
		once = C.GetImageSource("/rank/text.png");
		imagea_rank[5] = Image.createImage(once, 0, 0, 390, 16, 0);
		imagea_rank[6] = Image.createImage(once, 0, 16, 390, 17, 0);

		once = null;
		imagea_pageNumber = new Image[10];
		once = C.GetImageSource("/propshop/pagenumber.png");
		for (int a = 0; a < 10; a++) {
			imagea_pageNumber[a] = Image
					.createImage(once, a * 32, 0, 32, 32, 0);
		}

		once = null;
		imagea_pageicon = new Image[5];
		once = C.GetImageSource("/propshop/pages.png");
		for (int a = 0; a < 5; a++) {
			imagea_pageicon[a] = Image.createImage(once, a * 34, 0, 34, 34, 0);
		}
		once = null;
		System.gc();
	}

	/**
	 * 销毁资源
	 */
	public void removeThisClassSource() {

		C.vector_Rank_Challenge.removeAllElements();
		C.vector_Rank_Level.removeAllElements();
		C.vector_Rank_Time.removeAllElements();
		C.vector_Rank_Total.removeAllElements();

		for (int a = 0; a < 10; a++) {
			imagea_pageNumber[a] = null;
		}
		imagea_pageNumber = null;

		for (int a = 0; a < 7; a++) {
			imagea_rank[a] = null;
		}
		imagea_rank = null;

		for (int a = 0; a < 5; a++) {
			imagea_pageicon[a] = null;
		}
		imagea_pageicon = null;

		System.gc();
	}

	/**
	 * showPressed
	 */
	public void keyPressed(int keycode) {
		switch (keycode) {
		case C.KEY_LEFT: {
			if ((colIndex) != 0)
				--colIndex;
			s.repaint();
		}
			break;
		case C.KEY_RIGHT: {
			if ((colIndex + 1) < colSum)
				++colIndex;
			if (isHasData(colIndex))
				return;
			if (s.si == null)
				s.si = new ServerIptv(s);
			switch (colIndex) {
			case 1: {
				s.si.dogetRankLevel();
			}
				break;
			case 2: {
				s.si.dogetRankTime();
			}
				break;
			case 3: {
				s.si.dogetRankChallenge();
			}
				break;
			}
		}
			break;
		}
	}

	/**
	 * 是否有数据
	 */
	public boolean isHasData(int colindex) {
		switch (colindex) {
		case 1: {
			if (C.vector_Rank_Level.size() > 0)
				return true;
		}
			break;
		case 2: {
			if (C.vector_Rank_Time.size() > 0)
				return true;
		}
			break;
		case 3: {
			if (C.vector_Rank_Challenge.size() > 0)
				return true;
		}
			break;
		}
		return false;
	}

	/**
	 * 画本类
	 */
	public void showMe(Graphics c) {
		c.setClip(0, 0, C.screenwidth, C.screenheight);
		C.DrawImage_LEFTTOP(imagea_rank[0], c);

		c.setColor(fontPrizeColor);
		c.setFont(C.FONT_BOLD_MEDIUM);

		int forstart = 0;
		int forend = 0;
		int prizeY = 0;

		if (colIndex + 1 == rankPageSum) {// 在最后一页了
			forstart = (colIndex) * 10;
			forend = vectorSize;
		} else if (colIndex == 0) {// 在第一页
			forstart = 0;
			forend = 10;
		} else {// 在中间页
			forstart = (colIndex) * 10;
			forend = (colIndex) * 10 + 10;
		}

		// for(int a =forstart;a<forend;a++){
		// String [] getstr =(String [])C.vector_Rank_Total.elementAt(a);
		// c.drawString(""+getstr[2], 85 , 145+prizeY*28, 0);
		// c.drawString(getstr[0], 255 , 145+prizeY*28, 0);
		// c.drawString(getstr[1], 500 , 145+prizeY*28, 0);
		// ++prizeY;
		// }

		drawRankContent(c, colIndex);// 画排行榜数据
		drawContent(c, colIndex);// 画相关内容
		drawPosition(c);// 画当前焦点
		// drawPage(c);//画页码
		// drawPageIcon(c);//画页码图标
	}

	/**
	 * 画排行榜数据
	 */
	public void drawRankContent(Graphics c, byte key) {
		switch (key) {
		case 0: {// 画总积分
			int size = C.vector_Rank_Total.size();
			if (size == 0)
				return;
			int prizeY = 0;
			int forsum = 0;
			if (size < 10) {
				forsum = size;
			} else {
				forsum = 10;
			}
			for (int a = 0; a < forsum; a++) {
				String[] getstr = (String[]) C.vector_Rank_Total.elementAt(a);
				c.drawString("" + getstr[2], 85, 185 + prizeY * 25, 0);
				c.drawString(getstr[0], 255, 185 + prizeY * 25, 0);
				c.drawString(getstr[1], 500, 185 + prizeY * 25, 0);
				++prizeY;
			}
			c.setColor(0xff0000);
			c.drawString("" + C.rank_total, 85, 460, 0);
			c.drawString(String.valueOf(Screen.iptvID), 255, 460, 0);
			c.drawString("" + C.rank_totalScore, 500, 460, 0);
		}
			break;
		case 1: {// 关卡模式
			int size = C.vector_Rank_Level.size();
			if (size == 0)
				return;
			int prizeY = 0;
			int forsum = 0;
			if (size < 10) {
				forsum = size;
			} else {
				forsum = 10;
			}
			for (int a = 0; a < forsum; a++) {
				String[] getstr = (String[]) C.vector_Rank_Level.elementAt(a);
				c.drawString("" + getstr[2], 85, 185 + prizeY * 25, 0);
				c.drawString(getstr[0], 255, 185 + prizeY * 25, 0);
				c.drawString(getstr[1], 500, 185 + prizeY * 25, 0);
				++prizeY;
			}
			c.setColor(0xff0000);
			c.drawString("" + C.rank_Level, 85, 460, 0);
			c.drawString(String.valueOf(Screen.iptvID), 255, 460, 0);
			c.drawString("" + C.rank_LevelScore, 500, 460, 0);
		}
			break;
		case 2: {// 计时模式
			int size = C.vector_Rank_Time.size();
			if (size == 0)
				return;
			int prizeY = 0;
			int forsum = 0;
			if (size < 10) {
				forsum = size;
			} else {
				forsum = 10;
			}
			for (int a = 0; a < forsum; a++) {
				String[] getstr = (String[]) C.vector_Rank_Time.elementAt(a);
				c.drawString("" + getstr[2], 85, 185 + prizeY * 25, 0);
				c.drawString(getstr[0], 255, 185 + prizeY * 25, 0);
				c.drawString(getstr[1], 500, 185 + prizeY * 25, 0);
				++prizeY;
			}
			c.setColor(0xff0000);
			c.drawString("" + C.rank_Time, 85, 460, 0);
			c.drawString(String.valueOf(Screen.iptvID), 255, 460, 0);
			c.drawString("" + C.rank_TimelScore, 500, 460, 0);
		}
			break;
		case 3: {// 挑战模式
			int size = C.vector_Rank_Challenge.size();
			if (size == 0)
				return;
			int prizeY = 0;
			int forsum = 0;
			if (size < 10) {
				forsum = size;
			} else {
				forsum = 10;
			}
			for (int a = 0; a < forsum; a++) {
				String[] getstr = (String[]) C.vector_Rank_Challenge
						.elementAt(a);
				c.drawString("" + getstr[2], 85, 185 + prizeY * 25, 0);
				c.drawString(getstr[0], 255, 185 + prizeY * 25, 0);
				c.drawString(getstr[1], 500, 185 + prizeY * 25, 0);
				++prizeY;
			}
			c.setColor(0xff0000);
			c.drawString("" + C.rank_Challenge, 85, 460, 0);
			c.drawString(String.valueOf(Screen.iptvID), 255, 460, 0);
			c.drawString("" + C.rank_ChallengeScore, 500, 460, 0);
		}
			break;
		}
	}

	/**
	 * 画相关信息
	 */
	public void drawContent(Graphics c, byte key) {

		// if (key == 0) {
		// C.DrawImage_XY_LEFTTOP(imagea_rank[5], 40, 460, c);
		// } else {
		// C.DrawImage_XY_LEFTTOP(imagea_rank[6], 40, 460, c);
		// }

		c.drawString(String.valueOf(Screen.iptvID), 130, 495, 0);
		// c.drawString(String.valueOf(C.rank), 360, 470, 0);
		// c.drawString(String.valueOf(C.challenge_Score), 500, 470, 0);//当前总得分
	}

	/**
	 * 画当前的光标位置
	 */
	public void drawPosition(Graphics c) {
		C.DrawImage_VH(imagea_rank[colIndex + 1], position[colIndex][0],
				position[colIndex][1], c);
	}

	/**
	 * 画页码
	 */
	public void drawPage(Graphics canvas) {
		C.DrawNumber_XY_RIGHTTOP(imagea_pageNumber, colIndex + 1, 290, 490, 34,
				canvas);
		C.DrawImage_XY_LEFTTOP(imagea_pageicon[4], 310, 490, canvas);
		C.DrawNumber_XY_RIGHTTOP(imagea_pageNumber, rankPageSum, 330, 490, 34,
				canvas);
	}

	/**
	 * 画页码图标
	 */
	public void drawPageIcon(Graphics canvas) {
		if (colIndex == 0) {// 在最开始
			C.DrawImage_VH(imagea_pageicon[0], 268, 505, canvas);
			C.DrawImage_VH(imagea_pageicon[3], 385, 505, canvas);
		} else if (colIndex == rankPageSum - 1) {// 在最末尾
			C.DrawImage_VH(imagea_pageicon[1], 268, 505, canvas);
			C.DrawImage_VH(imagea_pageicon[2], 385, 505, canvas);
		} else {// 上下都可以选择的时候
			C.DrawImage_VH(imagea_pageicon[1], 268, 505, canvas);
			C.DrawImage_VH(imagea_pageicon[3], 385, 505, canvas);
		}
	}

}
