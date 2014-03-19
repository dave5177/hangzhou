package com.zhangniu.prop;

import com.zhangniu.game.C;
import com.zhangniu.game.Screen;

public class Props extends Thread {

	public static String Coin;// 用户元宝数
	public static String pwd = "";// 童锁
	public static String kindid = "P10021";// 产品 id
	public static String[] propid = { "DJ10070",//时间道具
									"DJ10071",//托管器道具
									"DJ10072",//生命道具
									"DJ10073",//
									"DJ10074",//
									"DJ10075" //
									};// 道具id
	public static float[] propPrice;// 道具人民币价格
	public static int[] propCoinPrice;// 道具元宝价格

	public static int coin_result;// 充值结果
	public static int prop_result;// 道具购买结果
	public static int coinprice;// 记录购买的元宝值
	public static boolean waitResult;
	public static int buy_propid;
	public static String str_BuyCode;

	public Props(String iptvid) {
		waitResult = false;
	}

	/** 购买道具 */
	public void buyProp(int id) {
		Screen.nx_tool.do_BuyProp(C.GetImageSource("/js_prop/" + id + ".png"), propid[id]);
		
		buy_propid = id;
		str_BuyCode = propid[id];
	}

	/** 购买元宝 */
	public void buyCoin(int price) {
	}

}
