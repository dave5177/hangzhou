package com.dave.paoBing.net;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;

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
			
//			try {
//				C.out("2:返回： "+backstr);
//				getJsonObject = new JSONObject(backstr);
//				String gets = "" + getJsonObject.getString("res");
//				JSONObject temp_j = new JSONObject(gets);
//				String datas = temp_j.getString("datas");
//				
//				if(datas.length()>1){
//					CanvasControl.mission = Integer.parseInt(datas);
//				}
//				
//				temp_j = null;
//				getJsonObject = null;
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			 
			break;
		case 3:// 获取排行榜数据处理
			JSONObject getJson = null;
			try {
				getJson = new JSONObject(backstr);
				String rankStr = getJson.getString("res");
				String myRankStr = getJson.getString("mineresults");
				C.out("得到排行榜字符串：----" + myRankStr);

				getJson = new JSONObject(myRankStr);
				CanvasControl.complete = getJson.getInt("score");
				CanvasControl.rank = getJson.getInt("rank");

				if (CanvasControl.rankInfo == null)
					CanvasControl.rankInfo = new Vector();
				CanvasControl.rankInfo = this.getJsonArrayData(rankStr,
						new String[] { "userid", "score", "rank" });

			} catch (JSONException je) {
				doSendScore();
				doGetRank();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 6:// 获取暗扣信息
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

		case 8:
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
		}
	}

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
	 * 获得排行榜
	 */
	public void doGetRank() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&type=0" + // 排行榜类型，0为总排行，1为日排行，2为周排行，3为月排行
				"&limit=10"; // 条目数
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("获取排行榜，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3);
	}

	/**
	 * 上传用户数据
	 */
	public void doSendUserInfo() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + "&userid="
				+ CanvasControl.iptvID + "&index=1" + "&datas="
				+ CanvasControl.mission;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("上传用户数据，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * 上传积分数据
	 */
	public void doSendScore() {
		encryptedStr = "";
		submitURL = "";
		// product=P31010&userid=666333000&username=666333000&score=1&limit=10
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&score=" + CanvasControl.mission + // 积分
				"&limit=10";
		encryptedStr = getUrl(".rank.save", submitURL);

		C.out("上传积分数据，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * 获取暗扣信息。 个人坚决反对。
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

		new Connection(this, encryptedStr, 6);
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
