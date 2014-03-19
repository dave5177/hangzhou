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
	 * �����ص�����
	 */
	public void backStr(int index, String backstr) {
		JSONObject getJsonObject;
		switch (index) {
		case 2:// �����ȡ�û���������
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("�õ��û��浵���ݣ�" + backstr);
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
		case 3:// ��ȡ���������а����ݴ���
			break;
		case 4:// ������
			break;
		case 5:// ������
			try {
				getJsonObject = new JSONObject(backstr);
				String rankStr = getJsonObject.getString("res");
				String myRankStr = getJsonObject.getString("mineresults");
				C.out("�õ������а��ַ�����----" + backstr);

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
		case 6:// ������
			try {
				getJsonObject = new JSONObject(backstr);
				String rankStr = getJsonObject.getString("res");
				String myRankStr = getJsonObject.getString("mineresults");
				C.out("�õ������а��ַ�����----" + backstr);

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
		case 10://��ȡ��ǰʱ��
			try {
				getJsonObject = new JSONObject(backstr);

				C.out("�õ�ϵͳʱ�����ݣ�" + backstr);
				canvasControl.handleTimeStr(getJsonObject.getString("ymd"), getJsonObject.getString("week"));
				getJsonObject = null;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

//////////////////////////////////���ܷ���////////////////////////////////////////////////
	
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

		C.out("��ȡ�û���Ϣ�����ʵ�ַ" + "----" + encryptedStr);
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
				"&type=" + type +// ���а����ͣ�0Ϊ�����У�1Ϊ�����У�2Ϊ�����У�3Ϊ������
				"&limit=10" + // ��Ŀ��
				"&index=" + index; // �浵�±�ֵ
		encryptedStr = getUrl(".rank.query", submitURL);

		C.out("��ȡ���а񣬷��ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 3 + type);
	}

	/**
	 * �ϴ��û�����
	 */
	public void doSendUserInfo(String str_param) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + "&userid="
				+ CanvasControl.iptvID + "&index=1" + "&datas=" + str_param;
		encryptedStr = getUrl(".record.save", submitURL);

		C.out("�ϴ��û����ݣ��浵�ַ���------" + str_param);
		C.out("�ϴ��û����ݣ����ʵ�ַ" + "----" + encryptedStr);
		C.out("-----------------------------------\n");

		new Connection(this, encryptedStr, 0);
	}

	/**
	 * �ϴ���������
	 */
	public void doSendScore(int score, int index) {
		encryptedStr = "";
		submitURL = "";
		submitURL = "product=" + CanvasControl.GAME_PROP_CODE + // �����Ʒ��
				"&userid=" + CanvasControl.iptvID + // userId
				"&username=" + CanvasControl.iptvID + // username
				"&score=" + score + // username
				"&index=" + index; // ����
		encryptedStr = getUrl(".rank.save", submitURL);

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
	 * �ϴε��������û�ϴ�һ����һ��
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
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
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
	 * ���ܷ�����������һ���ַ���ֵ �����õķ���
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
