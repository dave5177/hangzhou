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
	 * �����ϴ�����Ϣ
	 */
	private String addingMsg;

	/**
	 * �����ϴ���Ϣ��Ŀ���û�
	 */
	private String addingMsgIptvid;

	public ServerIptv(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	/**
	 * �����ص�����
	 */
	public void backStr(int index, String backstr) {
		JSONObject getJsonObject;
		switch (index) {
		case 2:// �����ȡ�û��浵����
			try {
				getJsonObject = new JSONObject(backstr);

				NullView.resultStr = backstr;
				
				C.out("�õ��û��浵���ݣ�" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {// �״ε���
					System.out.println("�û��״ε���");
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
		case 3:// ��ȡ���������а����ݴ���
			JSONObject getJson = null;
			try {
				getJson = new JSONObject(backstr);
				String rankStr = getJson.getString("res");
				String myRankStr = getJson.getString("mineresults");
C.out("�õ����а��ַ�����----" + backstr);			

				
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
		case 4:// ������
			break;
		case 5:// ������
			break;
		case 6:// ������
			break;

		case 7:// ��ȡ������Ϣ
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

		case 8:// ͳ��ʱ���ϴ��ɹ����˳�
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
		case 10:// ��ȡ��ǰʱ��
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("�õ�ϵͳʱ�����ݣ�" + backstr);
				canvasControl.handleTimeStr(getJsonObject.getString("ymd"),
						getJsonObject.getString("week"));
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 11:// �����ȡ���ǳơ�
			try {
				C.out("�õ��ǳ��ַ�����" + backstr);
				getJsonObject = new JSONObject(backstr);
				String surname = C.deUserName_base64(getJsonObject.getString("surname"));
				String name = C.deUserName_base64(getJsonObject.getString("name"));
				C.out("�����ǳƣ�" + surname + name);
				CanvasControl.nickFromSer = surname + name;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 12:
			C.out("�õ���ս�ַ�����" + backstr);
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
		case 13://���û����һ����Ϣ
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("�õ��û���Ϣ�洢���ݣ�" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {// û����Ϣ
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
		case 14://��ȡ�ҵ���Ϣ
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("�õ��ҵ���Ϣ��" + backstr);
				String gets = "" + getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				String data = temp_j.getString("datas");
				if (data == null || data.equals("") || data.equals("null")) {// û����Ϣ
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

	// ////////////////////////////////���ܷ���////////////////////////////////////////////////

	/**
	 * ����û�������Ϣ
	 */
	public void doGetUserProfile() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // ��Ʒ����
				"&userid=" + CanvasControl.iptvID + // �û�Ψһ��ʶ�����û��ʺţ�
				"&username=" + CanvasControl.iptvID + // usernameĬ��iptvid
				"&index=1"; // �������
		encryptedStr = getUrl(".record.load", submitURL);

		C.out("��ȡ�û��浵�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 2);
	}

	/**
	 * @param index
	 *            �浵�±�ֵ
	 * @param type
	 *            ���а����ͣ�0Ϊ�����У�1Ϊ�����У�2Ϊ�����У�3Ϊ������
	 */
	public void doGetRank(int index, int type) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // �����Ʒ��
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&type=" + type + // ���а����ͣ�0Ϊ�����У�1Ϊ�����У�2Ϊ�����У�3Ϊ������
				"&limit=10" + // ��Ŀ��
				"&index=" + index; // �浵�±�ֵ
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("��ȡ���а񣬷��ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3 + type);
	}

	/**
	 * һ����չ���ݵ����а��ȡ����
	 * @param index
	 * @param type  ���а����ͣ�0Ϊ�����У�1Ϊ�����У�2Ϊ�����У�3Ϊ������
	 * @param sort ��չ���ݵ�����ʽ������desc������asc��
	 */
	public void doGetRank(int index, int type, String sort) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // �����Ʒ��
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&type=" + type + // ���а����ͣ�0Ϊ�����У�1Ϊ�����У�2Ϊ�����У�3Ϊ������
				"&limit=10" + // ��Ŀ��
				"&expand_a" + sort + // 
				"&index=" + index; // �浵�±�ֵ
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("��ȡ���а񣬷��ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3 + type);
	}
	
	/**
	 * �ϴ��û�����
	 */
	public void doSendUserInfo(String iptvid, String str_param) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + 
				"&userid=" + iptvid + 
				"&index=1" + 
				"&datas=" + str_param;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("�ϴ��û����ݣ��浵�ַ���------" + str_param);
		C.out("�ϴ��û����ݣ����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * �ϴ���������
	 */
	public void doSendScore(String iptvId, String nickName, int score, int index) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // �����Ʒ��
				"&userid=" + iptvId + // userId
				"&username=" + nickName + // username
				"&score=" + score + // username
				"&index=" + index; // ����
		encryptedStr = getUrl(".rank.save", submitURL);

		C.out("�ϴ��������ݣ�����" + "----" + submitURL);
		C.out("�ϴ��������ݣ����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}
	
	/**
	 * �ϴ��������ݣ�������һ����չ����
	 * @param score ������������
	 * @param expand_a ��չ����
	 * @param index 
	 */
	public void doSendScore(String iptvId, String nickName, int score, int expand_a, int index) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // �����Ʒ��
				"&userid=" + iptvId + // userId
				"&username=" + nickName + // username
				"&score=" + score + // ����
				"&expand_a=" + expand_a + // ��չ
				"&index=" + index; // �����±�
		encryptedStr = getUrl(".rank.save", submitURL);

		C.out("�ϴ��������ݣ�����" + "----" + submitURL);
		C.out("�ϴ��������ݣ����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * ��ȡak��Ϣ
	 */
	public void getTheftInfo() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID;
		encryptedStr = getUrl(".randomfees.get", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("��ȡ������Ϣ�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 7);
	}

	/**
	 * �ϴ����߹�����Ϣ
	 * 
	 * @param propid
	 *            ������
	 */
	public void sendBuyInfo(String propid) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID + "&propcord=" + propid
				+ "&kindid=" + CanvasControl.GAME_PROP_CODE;
		encryptedStr = getUrl(".prop.log", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("�ϴ����߹�����Ϣ�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 7);
	}

	/**
	 * �ϴ���Ϸʱ��ͽ������а�ʱ��ͳ�ơ�
	 * 
	 * @param unique_orderid
	 *            13λʱ��� + iptvid
	 * @param sendType
	 *            �ϴ����ݵ����͡�1����Ϸʱ�䡣2�����а�ʱ��
	 * @param operateType
	 *            �������͡�add�����Ϸ��¼��update�ǽ���ʱ�䡣
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
		C.out("�ϴ�ʱ��ͳ����Ϣ�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 8);
	}

	/**
	 * �ϴε��������ÿ�ϴ�һ����һ��
	 */
	public void sendClickTimes() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID + "&gameid=33" + "&version="
				+ CanvasControl.VERSION + "&gameplatform=3.0";
		encryptedStr = getUrl(".prop.click", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("�ϵ�����������ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * ��ָ��iptv�û����һ����Ϣ���ȴӷ�������ȡ��Ϣ����ӽ�ȥ������滻��
	 * @param iptvid
	 * @param msg ��Ϣ����
	 */
	public void addMsg(String iptvid, String msg) {
		this.addingMsgIptvid = iptvid;
		this.addingMsg = msg;
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // ��Ʒ����
				"&userid=" + iptvid + // �û�Ψһ��ʶ�����û��ʺţ�
				"&username=" + iptvid + // usernameĬ��iptvid
				"&index=2"; // �������
		encryptedStr = getUrl(".record.load", submitURL);

		C.out("�����Ϣ���Ȼ�ȡ��Ϣ��" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 13);
	}
	
	/**
	 * ��ȡ������Ϣ
	 */
	public void getMsg() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // ��Ʒ����
				"&userid=" + CanvasControl.iptvID + // �û�Ψһ��ʶ�����û��ʺţ�
				"&username=" + CanvasControl.iptvID + // usernameĬ��iptvid
				"&index=2"; // �������
		encryptedStr = getUrl(".record.load", submitURL);

		C.out("��ȡ�û���Ϣ������" + "----" + submitURL);
		C.out("��ȡ�û���Ϣ�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 14);
	}
	
	/**
	 * ��ָ��iptv�û�����Ϣ�洢���и���
	 * @param iptvid
	 */
	public void updateMsg(String iptvid, String msg) {
		encryptedStr = "";
		submitURL = "";
		submitURL = 
				"product=" + CanvasControl.GAME_PROP_CODE + 
				"&userid="+ iptvid + 
				"&index=2" + //indexһ��Ҫ��2������Ҫ
				"&datas=" + msg;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("��ָ��iptv�û�����һ����Ϣ����Ϣ�ַ���------" + submitURL);
		C.out("��ָ��iptv�û�����һ����Ϣ�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}
	
	/**
	 * ��ȡ��ǰʱ��
	 */
	public void getSystemTime() {
		encryptedStr = "";
		submitURL = "";
		encryptedStr = getUrl(".systemtime.get", submitURL);

		C.out("��ȡ��ǰʱ�䣬���ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 10);
	}

	/**
	 * �����ȡ�����ǳ�
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
	 * ��ȡ��ս����
	 */
	public void getDuelInfo() {
		encryptedStr = "";
		submitURL = "";
		submitURL = "iptv=" + CanvasControl.iptvID
				+ "&productID=" + CanvasControl.GAME_PROP_CODE;
		encryptedStr = getUrl(".rand.get", submitURL);

		C.out("\n-------------------------------------");
		C.out(submitURL);
		C.out("��ȡ��ս��Ϣ�����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 12);
	}
	
	// ////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ������json���ݽ��д�������Vector����
	 * 
	 * @param str
	 * @param requireStr
	 * @return
	 */
	public Vector getJsonArrayData(String str, String[] requireStr) {

		if (str == null || str.equals(""))
			return null;

		int require = requireStr.length;// ������Щ"name","age","high",������С��ϢΪһ����Ԫ
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
			// C.out("�����:"+C.prizeNameVector.size());
		}
		return JsonVector;
	}

	/**
	 * ���������õ����յ�url
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
	 * ���ܷ������������ַ���ֵ �����õķ���
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
