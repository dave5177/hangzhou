package com.zhangniu.prop;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import org.json.me.JSONArray;
import org.json.me.JSONObject;

import com.zhangniu.game.C;
import com.zhangniu.game.Screen;
import com.zhangniu.update.Base64;
import com.zhangniu.update.md5;

public class Props extends Thread {
	private final String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";

	private final String COMPANYURL = "http://61.160.131.57:8083/www.iptvgame.com/";//江苏服务器

	private String submit_url;
	private String last_url;

	private HttpConnection hc;

	private String lastResult;

	private Thread thread;

	private md5 md5class;
	private Base64 base64class;

	private int type;

	private String iptvid;
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
		this.iptvid = iptvid;
		md5class = new md5();
		base64class = new Base64();
		waitResult = false;
	}

	public void cleanAll() {
		submit_url = null;
		last_url = null;
		hc = null;
		lastResult = null;
		thread = null;
		md5class = null;
		base64class = null;
	}

	/** 获取道具码 */
	public void getPropID() {
		/*type = 4;
		submit_url = "kindid=" + kindid;
		last_url = getUrl("iptv.props.get.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	/** 获取用户元宝数 */
	public void getCoin() {
		/*type = 1;
		submit_url = "account=" + iptvid;
		last_url = getUrl("iptv.gamegold.Inquiry.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	/** 购买道具 */
	public void buyProp(int id) {
		Screen.js_tool.do_BuyProp(C.GetImageSource("/js_prop/" + id + ".png"), propid[id]);
		
		buy_propid = id;
		str_BuyCode = propid[id];
		cleanAll();
		/*type = 2;
		submit_url = "account=" + iptvid + "&prodCode=" + kindid
				+ "&propsCode=" + propid[id];
		last_url = getUrl("iptv.consumer.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	/** 购买元宝 */
	public void buyCoin(int price) {
		/*type = 3;
		coinprice = price;
		submit_url = "userid=" + iptvid + "&price=" + price + "&pwd=" + pwd;
		System.out.println("sub:" + submit_url);
		last_url = getUrl("gameorder.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	public void run() {
		try {
			System.out.println("last_url:" + last_url);
			lastResult = new String(getViaHttpConnection(last_url));
			System.out.println("type:" + type);
			System.out.println("lastResult:" + lastResult);
			switch (type) {
			case 1:
				Coin = lastResult;
				break;
			case 2:
				if ("1212200".equals(lastResult)) {
					System.out.println("订购成功");
					prop_result = 1;
				} else if ("1212301".equals(lastResult)) {
					System.out.println("余额不足");
					prop_result = 2;
				} else {
					System.out.println("订购失败");
					prop_result = 3;
				}
				waitResult = true;
				Screen.s.backFromPropServer(prop_result);
				break;
			case 3:
				if ("0".equals(lastResult)) {
					System.out.println("充值成功");
					coin_result = 1;
				} else if ("9103".equals(lastResult)) {
					System.out.println("童锁参数不对");
					coin_result = 2;
				} else {
					System.out.println("充值失败");
					coin_result = 3;
				}
//				pwd = "";
				waitResult = true;
				break;
			case 4:
				JSONObject jsonObject = new JSONObject(lastResult);
				JSONArray jsonArray = (JSONArray) jsonObject.get("res");
				JSONObject obj;
				propPrice = new float[jsonArray.length()];
				propCoinPrice = new int[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					obj = jsonArray.getJSONObject(i);
					propPrice[i] = Float.parseFloat(obj.getString("price"));
					propCoinPrice[i] = Integer.parseInt(obj
							.getString("gamegolds"));
				}
				break;
			}
			cleanAll();
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接网络，下载数据
	 */
	public byte[] getViaHttpConnection(String url) throws IOException {
		InputStream is = null;
		byte[] data = null;
		System.gc();
		int rc;

		try {
			hc = (HttpConnection) Connector.open(url);
			rc = hc.getResponseCode();
			if (rc != HttpConnection.HTTP_OK) {
				throw new IOException("HTTP response code: " + rc);
			}

			is = hc.openInputStream();
			int len = (int) hc.getLength();
			if (len > 0) {
				int actual = 0;
				int bytesread = 0;
				data = new byte[len];
				while ((bytesread != len) && (actual != -1)) {
					actual = is.read(data, bytesread, len - bytesread);
					bytesread += actual;
				}
			} else {
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Not an HTTP URL");
		} finally {
			is.close();
			hc.close();
			hc = null;
			is = null;
			System.gc();
		}
		return data;
	}

	/**
	 * 经过处理后得到最终的url
	 */
	public String getUrl(String action, String param) {
		String input = encrypt(param);
		input = md5class.getMD5ofStr((input + APP_KEY)) + input;
		String url = COMPANYURL + action + "?input=" + input;
		return url;
	}

	/**
	 * 加密方法，并返回一个字符串值 被调用的方法
	 * 
	 * @param param
	 * @return
	 */
	private String encrypt(String param) {
		byte[] mkey = md5class.getMD5ofStr(APP_KEY).substring(1, 19).getBytes();
		int mlen = mkey.length;
		int mk;
		int num = param.length();
		byte[] output = new byte[num];
		byte[] strbyte = param.getBytes();
		for (int i = 0; i < num; i++) {
			mk = i % mlen;
			output[i] = (byte) ((int) strbyte[i] ^ (int) mkey[mk]);
		}

		return base64class.encode(new String(output));
	}
}
