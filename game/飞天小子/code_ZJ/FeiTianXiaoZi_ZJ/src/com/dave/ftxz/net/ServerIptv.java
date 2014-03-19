package com.dave.ftxz.net;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;

public class ServerIptv {
	private CanvasControl canvasControl;

	private String encryptedStr;
	private String submitURL;

	public ServerIptv(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	/**
	 * 处理返回的数据
	 */
	public void backStr(int index, String backstr) {
		JSONObject getJsonObject;
		switch (index) {
		case 2:// 处理获取用户数据请求
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("得到用户存档数据：" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {
					canvasControl.saveParam();
				} else {
					canvasControl.handleParam(data);
				}

				temp_j = null;
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 3:// 获取总排行排行榜数据处理
			break;
		case 4:// 日排行
			break;
		case 5:// 周排行
			try {
				getJsonObject = new JSONObject(backstr);
				String rankStr = getJsonObject.getString("res");
				String myRankStr = getJsonObject.getString("mineresults");
				C.out("得到周排行榜字符串：----" + backstr);

				getJsonObject = new JSONObject(myRankStr);
				CanvasControl.score_week = getJsonObject.getInt("score");
				CanvasControl.rank_week = getJsonObject.getInt("rank");

				if (!rankStr.equals("[]")) {
					if (CanvasControl.rankInfo_week == null)
						CanvasControl.rankInfo_week = new Vector();

					CanvasControl.rankInfo_week.removeAllElements();
					CanvasControl.rankInfo_week = this
							.getJsonArrayData(rankStr, new String[] { "userid",
									"score", "rank" });
				}

			} catch (JSONException je) {
				je.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 6:// 月排行
			try {
				getJsonObject = new JSONObject(backstr);
				String rankStr = getJsonObject.getString("res");
				String myRankStr = getJsonObject.getString("mineresults");
				C.out("得到月排行榜字符串：----" + backstr);

				getJsonObject = new JSONObject(myRankStr);
				CanvasControl.score_month = getJsonObject.getInt("score");
				CanvasControl.rank_month = getJsonObject.getInt("rank");

				if (!rankStr.equals("[]")) {
					if (CanvasControl.rankInfo_month == null)
						CanvasControl.rankInfo_month = new Vector();
					
					CanvasControl.rankInfo_month.removeAllElements();
					CanvasControl.rankInfo_month = this
							.getJsonArrayData(rankStr, new String[] { "userid",
									"score", "rank" });
				}

			} catch (JSONException je) {
				je.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 7:// 获取暗扣信息
				// try {
				// getJsonObject = new JSONObject(backstr);
				//
				// String gets = ""+getJsonObject.getString("res");
				// C.out(gets);
				// JSONObject temp_j = new JSONObject(gets);
				//
				// temp_j = null;
				// getJsonObject = null;
				//
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
			break;

		case 8:// 统计时长上传成功后退出
			if (CanvasControl.willExit) {
				try {
					getJsonObject = new JSONObject(backstr);
					C.out("return Code----" + getJsonObject.getString("code"));
					getJsonObject = null;
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
					canvasControl.getMidlet().exitGame();
				}
			}
			break;
		case 10://获取当前时间
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("得到系统时间数据：" + backstr);
				canvasControl.handleTimeStr(getJsonObject.getString("ymd"), getJsonObject.getString("week"));
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

//////////////////////////////////功能方法////////////////////////////////////////////////
	
	/**
	 * 获得用户基本信息
	 */
	public void doGetUserProfile() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 产品名称
				"&userid=" + CanvasControl.iptvID + // 用户唯一标识符（用户帐号）
				"&username=" + CanvasControl.iptvID + // username默认iptvid
				"&index=1"; // 档案序号
		encryptedStr = getUrl(".record.load", submitURL);

		C.out("获取用户信息，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 2);
	}

	/**
	 * @param index
	 *            存档下标值
	 * @param type
	 *            排行榜类型，0为总排行，1为日排行，2为周排行，3为月排行
	 */
	public void doGetRank(int index, int type) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&type=" + type +// 排行榜类型，0为总排行，1为日排行，2为周排行，3为月排行
				"&limit=10" + // 条目数
				"&index=" + index; // 存档下标值
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("获取排行榜，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3 + type);
	}

	/**
	 * 上传用户数据
	 */
	public void doSendUserInfo(String str_param) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + "&userid="
				+ CanvasControl.iptvID + "&index=1" + "&datas=" + str_param;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("上传用户数据，存档字符串------" + str_param);
		C.out("上传用户数据，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * 上传积分数据
	 */
	public void doSendScore(int score, int index) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&score=" + score + // username
				"&index=" + index; // 积分
		encryptedStr = getUrl(".rank.save", submitURL);

		C.out("上传积分数据，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * 获取ak信息
	 */
	public void getTheftInfo() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID;
		encryptedStr = getUrl(".randomfees.get", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("获取暗扣信息，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 7);
	}

	/**
	 * 上传道具购买信息
	 * 
	 * @param propid
	 *            道具码
	 */
	public void sendBuyInfo(String propid) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID + "&propcord=" + propid
				+ "&kindid=" + CanvasControl.GAME_PROP_CODE;
		encryptedStr = getUrl(".prop.log", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("上传道具购买信息，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 7);
	}

	/**
	 * 上传游戏时间和进入排行榜时间统计。
	 * 
	 * @param unique_orderid
	 *            13位时间戳 + iptvid
	 * @param sendType
	 *            上传数据的类型。1是游戏时间。2是排行榜时间
	 * @param operateType
	 *            操作类型。add添加游戏记录，update是结束时间。
	 */
	public void sendGameTimeInfo(String unique_orderid, String sendType,
			String operateType) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID + "&unique_orderid="
				+ unique_orderid + "&kindid=" + CanvasControl.GAME_PROP_CODE
				+ "&statisticstype=" + sendType + "&operationtype="
				+ operateType;
		encryptedStr = getUrl(".playtime.statistics.add", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("上传时间统计信息，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 8);
	}

	/**
	 * 上次点击次数。没上传一次算一次
	 */
	public void sendClickTimes() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID + "&gameid=33" + "&version="
				+ CanvasControl.VERSION + "&gameplatform=3.0";
		encryptedStr = getUrl(".prop.click", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("上点击次数，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * 获取当前时间
	 */
	public void getSystemTime() {
		encryptedStr = "";
		submitURL = "";
		encryptedStr = getUrl(".systemtime.get", submitURL);

		C.out("获取当前时间，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 10);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 对所有json数据进行处理，返回Vector对象
	 * 
	 * @param str
	 * @param requireStr
	 * @return
	 */
	public Vector getJsonArrayData(String str, String[] requireStr) {

		if (str == null || str.equals(""))
			return null;

		int require = requireStr.length;// 需求哪些"name","age","high",以三个小信息为一个单元
		Vector JsonVector = new Vector();
		JSONArray jsonArray = null;
		JSONObject jsonObject = new JSONObject();
		try {
			jsonArray = new JSONArray(str);
			int jolenght = jsonArray.length();
			for (int a = 0; a < jolenght; a++) {
				jsonObject = jsonArray.getJSONObject(a);
				String[] strarray = new String[require];
				for (int b = 0; b < require; b++) {
					strarray[b] = jsonObject.getString(requireStr[b]);
				}
				JsonVector.addElement(strarray);
				strarray = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			jsonArray = null;
			jsonObject = null;
			System.gc();
			// C.out("处理后:"+C.prizeNameVector.size());
		}
		return JsonVector;
	}

	/**
	 * 经过处理后得到最终的url
	 */
	public String getUrl(String action, String param) {
		String input = encrypt(param);
		input = md5.toMD5(input + C.APP_KEY) + input;
		String url = C.COMPANYURL_PHP + "?m=" + C.GAME_ADDRESS + action
				+ "&input=" + input;
		C.out(url);
		return url;
	}

	/**
	 * 加密方法，并返回一个字符串值 被调用的方法
	 * 
	 * @param param
	 * @return
	 */
	private String encrypt(String param) {
		byte[] mkey = md5.toMD5(C.APP_KEY).substring(1, 19).getBytes();
		int mlen = mkey.length;
		int mk;
		int num = param.length();
		byte[] output = new byte[num];
		byte[] strbyte = param.getBytes();
		for (int i = 0; i < num; i++) {
			mk = i % mlen;
			output[i] = (byte) ((int) strbyte[i] ^ (int) mkey[mk]);
		}
		return Base64.encode(new String(output));
	}

}
