package com.dave.jpsc.net;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.jpsc.view.DuelRoom;
import com.dave.jpsc.view.Loading;
import com.dave.jpsc.view.NullView;

public class ServerIptv {
	private CanvasControl canvasControl;

	private String encryptedStr;
	private String submitURL;

	/**
	 * 正在上传的信息
	 */
	private String addingMsg;

	/**
	 * 正在上传信息的目标用户
	 */
	private String addingMsgIptvid;

	public ServerIptv(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	/**
	 * 处理返回的数据
	 */
	public void backStr(int index, String backstr) {
		JSONObject getJsonObject;
		switch (index) {
		case 2:// 处理获取用户存档请求
			try {
				getJsonObject = new JSONObject(backstr);

				NullView.resultStr = backstr;
				
				C.out("得到用户存档数据：" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {// 首次登入
					System.out.println("用户首次登入");
					// canvasControl.saveParam();
					getRandName();
					canvasControl.setView(new Loading(canvasControl, 100));
				} else {
					canvasControl.handleParam(data);
//					canvasControl.setView(new Home(canvasControl));
				}
				CanvasControl.getParam = true;
				
				temp_j = null;
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 3:// 获取总排行排行榜数据处理
			JSONObject getJson = null;
			try {
				getJson = new JSONObject(backstr);
				String rankStr = getJson.getString("res");
				String myRankStr = getJson.getString("mineresults");
C.out("得到排行榜字符串：----" + backstr);			

				
				getJson = new JSONObject(myRankStr);
				int index_me = getJson.getInt("index");
				if(index_me == 3) {//
					CanvasControl.winsTotal = getJson.getInt("score");
					CanvasControl.gamesTotal = getJson.getInt("expand_a");
					CanvasControl.rankMe[0] = getJson.getInt("rank");
				} else if(index_me == 4) {
					CanvasControl.rankMe[1] = getJson.getInt("rank");
				} else if(index_me == 5) {
					CanvasControl.rankMe[2] = getJson.getInt("rank");
				}

				if(!rankStr.equals("[]")) {
					JSONArray jsArray = new JSONArray(rankStr);
					JSONObject jsObject = jsArray.getJSONObject(0);
					int index_rank = jsObject.getInt("index");
					
					if(index_rank == 3) {//
						CanvasControl.rank_win = getJsonArrayData(rankStr, new String[] {"rank", "username", "score", "expand_a"});
					} else if(index_rank == 4){//
						CanvasControl.rank_level = getJsonArrayData(rankStr, new String[] {"rank", "username", "score"});
					} else if(index_rank == 5) {
						CanvasControl.rank_duel = getJsonArrayData(rankStr, new String[] {"rank", "username", "score"});
					}
				}
				
			} catch (JSONException je) {
//				doSendScore(CanvasControl.totalScore);
//				doGetRank();
				je.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 4:// 日排行
			break;
		case 5:// 周排行
			break;
		case 6:// 月排行
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
		case 10:// 获取当前时间
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("得到系统时间数据：" + backstr);
				canvasControl.handleTimeStr(getJsonObject.getString("ymd"),
						getJsonObject.getString("week"));
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 11:// 随机获取的昵称。
			try {
				C.out("得到昵称字符串：" + backstr);
				getJsonObject = new JSONObject(backstr);
				String surname = C.deUserName_base64(getJsonObject.getString("surname"));
				String name = C.deUserName_base64(getJsonObject.getString("name"));
				C.out("最终昵称：" + surname + name);
				CanvasControl.nickFromSer = surname + name;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 12:
			C.out("得到挑战字符串：" + backstr);
			try {
				getJsonObject = new JSONObject(backstr);
				String jsonArrayStr = getJsonObject.getString("res");
				if(!jsonArrayStr.equals("[]")) {
					if(CanvasControl.duelInfo == null)
						CanvasControl.duelInfo = new Vector();
					CanvasControl.duelInfo = getJsonArrayData(jsonArrayStr, new String[] {"datas", "userid"});
				}
				DuelRoom.requestFinish = true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 13://向用户添加一条信息
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("得到用户信息存储数据：" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {// 没有信息
					updateMsg(addingMsgIptvid, addingMsg);
				} else {
					String tarMsg = data + ";" + addingMsg;
					updateMsg(addingMsgIptvid, tarMsg);
				}
				temp_j = null;
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 14://获取我的信息
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("得到我的信息：" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {// 没有信息
					CanvasControl.haveMsg = false;
				} else {
					CanvasControl.haveMsg = true;
					canvasControl.analyzeMsg(data);
				}
				temp_j = null;
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	// ////////////////////////////////功能方法////////////////////////////////////////////////

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

		C.out("获取用户存档，访问地址" + "----" + encryptedStr);
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
				"&type=" + type + // 排行榜类型，0为总排行，1为日排行，2为周排行，3为月排行
				"&limit=10" + // 条目数
				"&index=" + index; // 存档下标值
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("获取排行榜，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3 + type);
	}

	/**
	 * 一条扩展数据的排行榜获取方法
	 * @param index
	 * @param type  排行榜类型，0为总排行，1为日排行，2为周排行，3为月排行
	 * @param sort 扩展数据的排序方式（降序：desc。升序：asc）
	 */
	public void doGetRank(int index, int type, String sort) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&type=" + type + // 排行榜类型，0为总排行，1为日排行，2为周排行，3为月排行
				"&limit=10" + // 条目数
				"&expand_a" + sort + // 
				"&index=" + index; // 存档下标值
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("获取排行榜，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3 + type);
	}
	
	/**
	 * 上传用户数据
	 */
	public void doSendUserInfo(String iptvid, String str_param) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + 
				"&userid=" + iptvid + 
				"&index=1" + 
				"&datas=" + str_param;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("上传用户数据，存档字符串------" + str_param);
		C.out("上传用户数据，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * 上传积分数据
	 */
	public void doSendScore(String iptvId, String nickName, int score, int index) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + iptvId + // userId
				"&username=" + nickName + // username
				"&score=" + score + // username
				"&index=" + index; // 积分
		encryptedStr = getUrl(".rank.save", submitURL);

		C.out("上传积分数据，参数" + "----" + submitURL);
		C.out("上传积分数据，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}
	
	/**
	 * 上传积分数据，增加了一条扩展数据
	 * @param score 基本积分数据
	 * @param expand_a 扩展数据
	 * @param index 
	 */
	public void doSendScore(String iptvId, String nickName, int score, int expand_a, int index) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 传入产品码
				"&userid=" + iptvId + // userId
				"&username=" + nickName + // username
				"&score=" + score + // 积分
				"&expand_a=" + expand_a + // 扩展
				"&index=" + index; // 排名下标
		encryptedStr = getUrl(".rank.save", submitURL);

		C.out("上传积分数据，参数" + "----" + submitURL);
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
	 * 上次点击次数。每上传一次算一次
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
	 * 向指定iptv用户添加一条信息（先从服务器获取信息再添加进去后进行替换）
	 * @param iptvid
	 * @param msg 信息内容
	 */
	public void addMsg(String iptvid, String msg) {
		this.addingMsgIptvid = iptvid;
		this.addingMsg = msg;
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 产品名称
				"&userid=" + iptvid + // 用户唯一标识符（用户帐号）
				"&username=" + iptvid + // username默认iptvid
				"&index=2"; // 档案序号
		encryptedStr = getUrl(".record.load", submitURL);

		C.out("添加信息，先获取信息：" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 13);
	}
	
	/**
	 * 获取的我信息
	 */
	public void getMsg() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // 产品名称
				"&userid=" + CanvasControl.iptvID + // 用户唯一标识符（用户帐号）
				"&username=" + CanvasControl.iptvID + // username默认iptvid
				"&index=2"; // 档案序号
		encryptedStr = getUrl(".record.load", submitURL);

		C.out("获取用户信息，参数" + "----" + submitURL);
		C.out("获取用户信息，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 14);
	}
	
	/**
	 * 向指定iptv用户的信息存储进行更改
	 * @param iptvid
	 */
	public void updateMsg(String iptvid, String msg) {
		encryptedStr = "";
		submitURL = "";
		submitURL = 
				"product=" + CanvasControl.GAME_PROP_CODE + 
				"&userid="+ iptvid + 
				"&index=2" + //index一定要是2。很重要
				"&datas=" + msg;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("向指定iptv用户发送一条信息，信息字符串------" + submitURL);
		C.out("向指定iptv用户发送一条信息，访问地址" + "----" + encryptedStr);
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

	/**
	 * 随机获取中文昵称
	 * 
	 * @param rLinner
	 */
	public void getRandName() {
		String surname_id = String
				.valueOf(System.currentTimeMillis() % 231 + 1);
		String name_id = String.valueOf(System.currentTimeMillis() % 1878 + 1);
		encryptedStr = "http://122.224.212.78:7878/"
				+ "?m=iptv.game.get.gamenickname&surname_id=" + surname_id
				+ "&name_id=" + name_id;

		new Connection(this, encryptedStr, 11);
	}

	/**
	 * 获取挑战对象
	 */
	public void getDuelInfo() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID
				+ "&productID=" + CanvasControl.GAME_PROP_CODE;
		encryptedStr = getUrl(".rand.get", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("获取挑战信息，访问地址" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 12);
	}
	
	// ////////////////////////////////////////////////////////////////////////////////////////////

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
	 * 加密方法，并返个字符串值 被调用的方法
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
		return new String(mBase64.encode(output));
	}

}
