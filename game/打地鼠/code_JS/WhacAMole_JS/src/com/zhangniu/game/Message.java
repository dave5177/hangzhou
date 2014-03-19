package com.zhangniu.game;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.zhangniu.prop.Props;

public class Message {
	public final static Font FONT_BOLD_LARGE = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_BOLD, Font.SIZE_LARGE);
	public final static Font FONT_BOLD_MEDIUM = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
	public final static Font FONT_BOLD_SMALL = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_SMALL);
	public final static int LARGE_BOLD_WIDTH = FONT_BOLD_LARGE.stringWidth("国");

	/** 充值 */
	public static final byte BUY_GOLD = 1;

	/** 购买成功 */
	public static final byte BUY_SUCCESS = 7;

	/** 购买失败 */
	public static final byte BUY_PROPFAIL = 5;

	/** 购买失败 */
	public static final byte BUY_FAIL = 8;

	public static final byte CHILD_LOCK = 3;

	/** 出现位置 */
	private int x, y;

	private Button yesno;

	private boolean right;

	private Button yesnos;

	/** 类型 */
	private byte type;

	private Image background;

	private Image word;

	private Image star;

	private boolean pause;

	private int count;

	private int counts;

	private StringBuffer password;

	private boolean input;

	private boolean inputs;

	public Message(byte type) {
		this.type = type;
		// setType(CHILD_LOCK);
		if (this.type == BUY_GOLD) {
			yesnos = null;
			yesno = new Button(true);
			yesno.setSelectindex();
		}

		if (this.type == CHILD_LOCK) {
			password = new StringBuffer();
			yesno = null;
			yesnos = new Button(false);
			yesnos.setSelectindex();
		}

		background = loadImage("/alertMessage/alert.png");
		word = getWordImage();
		initLocation();
		setpause(true);
	}

	/** 初始显示位置 */
	private void initLocation() {
		x = 640 / 2 - 2;
		y = 530 / 2 - 45;
	}

	/** 获得显示文字 */
	private Image getWordImage() {
		String name = null;
		switch (type) {
		case BUY_GOLD:
			name = "buyCoin.png";
			break;
		case BUY_SUCCESS:
			name = "buy_s.png";
			break;
		case BUY_FAIL:
			name = "fail.png";
			break;
		case CHILD_LOCK:
			name = "lock.png";
			break;
		case BUY_PROPFAIL:
			name = "buy_f.png";
			break;
		}
		return loadImage("/coin/" + name);
	}

	public void setType(byte type) {
		this.type = type;
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setClip(0, 0, 640, 530);
		g.drawImage(background, x, y, Graphics.HCENTER | Graphics.VCENTER);
		int tmp = 40;
		if (type != BUY_GOLD) {
			tmp = 0;
		}
		g.drawImage(word, x, y - tmp, Graphics.HCENTER | Graphics.VCENTER);
		if (yesno != null) {
			yesno.draw(g, x - background.getWidth() / 2,
					y - background.getHeight() / 2 + 30, background.getWidth());
		}
		if (type == CHILD_LOCK) {
			count++;
			g.setColor(0x000000);
			g.setFont(FONT_BOLD_LARGE);
			if (count % 10 == 0 && !input && !inputs) {
				if (password.length() <= 5) {
					g.drawString("|", x, y - 15, 0);
				} else {
					g.drawString("|", x + (password.length() - 5) * 15, y - 15,
							0);
				}

			}
			if (!input && !inputs) {
				for (int i = 0; i < password.length(); i++) {
					if (i < 5) {
						// g.drawString("*", x - (i + 1) * 5, y, 0);
						g.drawImage(star, x - (i + 1) * 15, y - 5, 0);
					} else {
						// g.drawString("*", x + (i - 5) * 5, y, 0);
						g.drawImage(star, x + (i - 5) * 15, y - 5, 0);
					}
				}
			}
			if (yesnos != null) {
				yesnos.draws(g, x, y, background.getWidth());
			}

			if (input || inputs) {
				counts++;
				if (input) {
					g.drawString("请输入童锁密码", x - 50, y - 15, 0);
				} else {
					g.drawString("密码错误，请重新输入", x - 80, y - 15, 0);
				}
				if (counts >= 10) {
					input = false;
					inputs = false;
					counts = 0;
				}
			}
			// System.out.println("counts:" + counts);
			// System.out.println("password:"+password.toString());
			// g.drawString(password.toString(), x, y, 0);
		}
		waitBuyResult();
		waitResult();
		// System.out.println(type+"  type");
	}

	public void onKeyDown(int keyCode) {
		if (yesno == null && type != CHILD_LOCK) {
			return;
		}

		switch (keyCode) {
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			if (type == CHILD_LOCK) {
				if (password.length() > 0) {
					password.deleteCharAt(password.length() - 1);
				}
			}
			break;
		case C.KEY_LEFT:
			if (yesno != null) {
				yesno.selectLeft();
			}
			if (yesnos != null) {
				yesnos.selectLeft();
			}
			break;
		case C.KEY_RIGHT:
			if (yesno != null) {
				yesno.selectRight();
			}
			if (yesnos != null) {
				yesnos.selectRight();
			}
			break;
		case C.KEY_DOWN:
			if (yesno != null) {
				yesno.selectDown();
			}
			break;
		case C.KEY_UP:
			if (yesno != null) {
				yesno.selectUp();
			}
			break;
		case C.KEY_FIRE:
			switch (type) {
			case BUY_GOLD:
				if (yesno.isSelected() != 4) {
					buyGoods(yesno.getPrice());
				} else {
					autoClose(10);
				}
				break;
			case CHILD_LOCK:
				if (yesnos.isSelected() == 0) {
					if (password.length() == 0) {
						input = true;
					} else {
						buyGoods(password.toString());
					}
				} else {
					autoClose(10);
				}
				break;
			}
			break;
		case C.KEY_0:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("0");
			break;
		case C.KEY_1:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("1");
			break;
		case C.KEY_2:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("2");
			break;
		case C.KEY_3:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("3");
			break;
		case C.KEY_4:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("4");
			break;
		case C.KEY_5:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("5");
			break;
		case C.KEY_6:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("6");
			break;
		case C.KEY_7:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("7");
			break;
		case C.KEY_8:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("8");
			break;
		case C.KEY_9:
			if (this.type != CHILD_LOCK) {
				break;
			}
			if (password.length() >= 10) {
				break;
			}
			password.append("9");
			break;
		}
	}

	private void buyGoods(int goodsId) {
		Props props = new Props(Screen.iptvID);
		props.buyCoin(goodsId);
		props = null;
		System.gc();
	}

	private void waitBuyResult() {
		if (right) {
			// System.out.println("right为true  "+Props.waitResult);
			if (Props.waitResult) {
				Props.waitResult = false;
				right = false;
				// System.out.println("right为true waitresult为true "+Props.prop_result);
				if (Props.prop_result == 1) {
					yesno = null;
					yesnos = null;
					showBuySuccessPop();
				} else {
					yesno = null;
					yesnos = null;
					showPropFailPop();
				}
				Props prop = new Props(Screen.iptvID);
				prop.getCoin();
				prop = null;
				System.gc();
			}
		}
	}

	private void waitResult() {
		if (Props.waitResult) {
			Props.waitResult = false;
			if (type == CHILD_LOCK) {
				if (Props.coin_result == 1) {
					right = true;
					/** 获取用户元宝数 */
					Props prop = new Props(Screen.iptvID);
					// prop.getCoin();
					System.out.println("是否执行props" + right);
					prop.buyProp(Props.buy_propid);
					prop = null;
					System.gc();
					// yesno = null;
					// yesnos = null;
					// showBuySuccessPop();
				} else if (Props.coin_result == 3) {
					yesnos = null;
					yesno = null;
					autoClose(10);
				} else if (Props.coin_result == 2) {
					// yesnos = null;
					// autoClose(10);
					yesno = null;
					inputs = true;
					password = null;
					password = new StringBuffer();
				}
			} else {
				if (Props.coin_result == 1) {
					right = true;
					/** 获取用户元宝数 */
					Props prop = new Props(Screen.iptvID);
					// prop.getCoin();
					prop.buyProp(Props.buy_propid);
					prop = null;
					System.gc();
					// yesno = null;
					// yesnos = null;
					// showBuySuccessPop();
				} else if (Props.coin_result == 3) {
					yesno = null;
					yesnos = null;
					showBuyFailPop();
				} else if (Props.coin_result == 2) {
					yesno = null;
					yesnos = new Button(false);
					yesnos.setSelectindex();
					password = new StringBuffer();
					showChildLokcPop();
				}
			}
			Props.coin_result = 0;
		}
	}

	private void buyGoods(String goodsId) {
		Props.pwd = goodsId;
		Props props = new Props(Screen.iptvID);
		props.buyCoin(Props.coinprice);
		props = null;
		System.gc();
	}

	/** 展示购买成功弹框 */
	private void showBuySuccessPop() {
		this.setType(BUY_SUCCESS);
		word = getWordImage();
		autoClose(2000);
	}

	/** 展示订购失败弹框 */
	private void showPropFailPop() {
		this.setType(BUY_PROPFAIL);
		word = getWordImage();
		autoClose(2000);
	}

	/** 展示购买失败弹框 */
	private void showBuyFailPop() {
		this.setType(BUY_FAIL);
		word = getWordImage();
		autoClose(2000);
	}

	/** 展示童锁输入框 */
	private void showChildLokcPop() {
		this.setType(CHILD_LOCK);
		word = getWordImage();
		star = loadImage("/coin/star.png");
	}

	/**
	 * 自动关闭
	 * 
	 * @param delay
	 */
	private void autoClose(int tm) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				setpause(false);
			}
		}, tm);
	}

	public void setpause(boolean pause) {
		this.pause = pause;
	}

	public boolean getpause() {
		return pause;
	}

	/** 按钮 */
	private class Button {

		private Image[] img_item;

		private Image img_button;

		private int selectindex;

		private int price;

		Button(boolean is) {
			initImage(is);
		}

		private void initImage(boolean is) {
			if (is) {
				img_item = new Image[5];
				for (int i = 0; i < img_item.length; i++) {
					img_item[i] = loadImage("/coin/bt" + i + ".png");
				}
			} else {
				img_item = new Image[2];
				img_item[1] = loadImage("/coin/bt4.png");
				img_item[0] = loadImage("/coin/bt5.png");
			}
			img_button = loadImage("/button/1.png");
			selectindex = 0;
		}

		void draw(Graphics g, int x, int y, int width) {
			int w = img_item[0].getWidth();
			int offx = (width - 2 * img_item[0].getWidth()) / 3;
			int space = img_item[0].getHeight() + 15;
			int offy = 70 + word.getHeight() + 40;
			for (int i = 0; i < img_item.length - 1; i++) {
				g.drawImage(img_item[i], x + offx * (i % 2 + 1) + i % 2 * w, y
						+ offy + space * (i / 2), 0);
			}
			if (selectindex != img_item.length - 1) {
				g.drawImage(img_button, x - img_button.getWidth() + offx
						* (selectindex % 2 + 1) + selectindex % 2 * w, y + offy
						+ space * (selectindex / 2), 0);
			}
			offx = (width - img_item[4].getWidth()) / 2;
			if (selectindex == img_item.length - 1) {
				g.drawImage(img_button, x + offx - img_button.getWidth(), y
						+ offy + space * 2, 0);
			}
			g.drawImage(img_item[4], x + offx, y + offy + space * 2, 0);
		}

		void draws(Graphics g, int x, int y, int width) {
			int w = img_item[0].getWidth();
			int offx = (width - 2 * w) / 3;
			if (selectindex == 0) {
				g.drawImage(img_button,
						x - offx / 2 - w - img_button.getWidth(), y + 100, 0);
			} else {
				g.drawImage(img_button, x + offx / 2 - img_button.getWidth(),
						y + 100, 0);
			}
			g.drawImage(img_item[1], x + offx / 2, y + 100, 0);
			g.drawImage(img_item[0], x - offx / 2 - w, y + 100, 0);
		}

		/** 选择下一个 */
		void selectRight() {
			if (selectindex < img_item.length - 1) {
				selectindex++;
			}
		}

		/** 选择上一个 */
		void selectLeft() {
			if (selectindex > 0) {
				selectindex--;
			}
		}

		/** 选择上一个 */
		private void selectUp() {
			if (selectindex - 2 >= 0) {
				selectindex -= 2;
			}
		}

		/** 选择下一个 */
		private void selectDown() {
			if (selectindex + 2 <= img_item.length - 1) {
				selectindex += 2;
			}
		}

		private void setSelectindex() {
			selectindex = img_item.length - 1;
		}

		int getPrice() {
			if (selectindex == 0) {
				return price = 1;
			} else if (selectindex == 1) {
				return price = 2;
			} else if (selectindex == 2) {
				return price = 5;
			} else if (selectindex == 3) {
				return price = 10;
			}
			return price;
		}

		int isSelected() {
			return selectindex;
		}
	}

	/**
	 * 加载图片
	 * 
	 * @param path
	 *            图片路径
	 * @return
	 */
	private Image loadImage(String path) {
		try {
			return Image.createImage(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
