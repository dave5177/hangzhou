package com.dave.soldierHunt.net;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.model.Hero;
import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.view.Game;

public class ServerIptv{
	private CanvasControl canvasControl;
	
	private String encryptedStr;
	private String submitURL;

	public ServerIptv(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}
	
	/**
	 * �����ص�����
	 */
	public void backStr(int index, String backstr){
		switch(index) {
		case 2://�����ȡ�û���������
			JSONObject getJsonObject;
			try {
				getJsonObject = new JSONObject(backstr);
				
				String gets = ""+getJsonObject.getString("res");
C.out(gets);
				JSONObject temp_j = new JSONObject(gets);
				CanvasControl.level = temp_j.getInt("mission");
				Hero.level = temp_j.getInt("hero_level");
				
				temp_j = null;
				getJsonObject = null;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 3://��ȡ���а����ݴ���
			JSONObject getJson = null;
			try {
				getJson = new JSONObject(backstr);
				String rankStr = getJson.getString("res");
				String myRankStr = getJson.getString("mineresults");
				
//				C.out("get rankAll data:"+rankAllStr);
//				C.out("my result : " + rankMainStr);
				
				getJson = new JSONObject(myRankStr);
				
				
				CanvasControl.level = getJson.getInt("mission");
				CanvasControl.totalCoin = getJson.getInt("coin");
				CanvasControl.rank = getJson.getInt("rank");
				
//				C.out("get rankAll data:" + rankAllStr);
				
				if(CanvasControl.rankInfo == null) CanvasControl.rankInfo = new Vector();
				CanvasControl.rankInfo = this.getJsonArrayData(rankStr, new String[]{"iptvid","coin", "mission", "rank"});
//				C.out("count:"+CanvasControl.v_RankAll.size());
				//cc.setState(CanvasControl.ACTIVITY_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case 6://��ȡ������Ϣ
//			try {
//				getJsonObject = new JSONObject(backstr);
//				
//				String gets = ""+getJsonObject.getString("res");
//C.out(gets);
//				JSONObject temp_j = new JSONObject(gets);
//				
//				temp_j = null;
//				getJsonObject = null;
//				
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			break;
			
		case 8:
			if (CanvasControl.willExit) {
				try {
					getJsonObject = new JSONObject(backstr);
C.out(backstr);
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
	 * ����û�������Ϣ
	 */
	public void doGetUserProfile(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID;
		encryptedStr = getUrl(".shibingtuji.get", submitURL);
		
C.out("��ȡ�û���Ϣ�����ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 2);
	}
	
	/**
	 * ������а�
	 */
	public void doGetRank(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID + 
		"&limit=10";
		encryptedStr = getUrl(".shibingtuji.rank", submitURL);
		
C.out("��ȡ���а񣬷��ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 3);
	}
	
	/**
	 * �ϴ��û�����
	 */
	public void doSendUserInfo(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&mission=" + CanvasControl.level +
		"&hero_level=" + Hero.level;
		encryptedStr = getUrl(".shibingtuji.info.edit", submitURL);
		
C.out("�ϴ��û����ݣ����ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 0);
	}
	
	/**
	 * �ϴ���������
	 */
	public void doSendScore(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&mission=" + CanvasControl.level +
		"&coin=" + Game.coinCount;
		encryptedStr = getUrl(".shibingtuji.score", submitURL);
		
C.out("�ϴ��������ݣ����ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 0);
	}
	
	/**
	 * ��ȡ������Ϣ��
	 * ���˼�����ԡ�
	 */
	public void getTheftInfo() {
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID;
		encryptedStr = getUrl(".randomfees.get", submitURL);

C.out("\n-------------------------------------");
C.out(submitURL);
C.out("��ȡ������Ϣ�����ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 6);
	}
	
	/**
	 * �ϴ����߹�����Ϣ
	 * @param propid ������
	 */
	public void sendBuyInfo(String propid) {
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&propcord=" + propid + 
		"&kindid=" + CanvasControl.GAME_PROP_CODE;
		encryptedStr = getUrl(".prop.log", submitURL);

C.out("\n-------------------------------------");
C.out(submitURL);
C.out("�ϴ����߹�����Ϣ�����ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 7);
	}

	/**
	 * �ϴ���Ϸʱ��ͽ������а�ʱ��ͳ�ơ�
	 * @param unique_orderid 13λʱ��� + iptvid
	 * @param sendType �ϴ����ݵ����͡�1����Ϸʱ�䡣2�����а�ʱ��
	 * @param operateType �������͡�add�����Ϸ��¼��update�ǽ���ʱ�䡣
	 */
	public void sendGameTimeInfo(String unique_orderid, String sendType, String operateType) {
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&unique_orderid=" + unique_orderid + 
		"&kindid=" + CanvasControl.GAME_PROP_CODE +
		"&statisticstype=" + sendType +
		"&operationtype=" + operateType;
		encryptedStr = getUrl(".playtime.statistics.add", submitURL);

C.out("\n-------------------------------------");
C.out(submitURL);
C.out("�ϴ�ʱ��ͳ����Ϣ�����ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 8);
	}
	
	public void sendClickTimes() {
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&gameid=33" + 
		"&version=" + CanvasControl.VERSION + 
		"&gameplatform=3.0";
		encryptedStr = getUrl(".prop.click", submitURL);

C.out("\n-------------------------------------");
C.out(submitURL);
C.out("�ϵ�����������ʵ�ַ"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 0);
	}
	
	/**
	 * ������json���ݽ��д�������Vector����
	 * @param str
	 * @param requireStr
	 * @return
	 */
	public Vector getJsonArrayData(String str,String [] requireStr){
		
		if(str == null||str.equals(""))return null;
		
		int require = requireStr.length;//������Щ"name","age","high",������С��ϢΪһ����Ԫ
		Vector JsonVector = new Vector();
		JSONArray jsonArray=null;
		JSONObject jsonObject=new JSONObject();
		try {
			jsonArray = new JSONArray(str);
			int jolenght =jsonArray.length();
			for(int a =0;a<jolenght;a++){
				jsonObject = jsonArray.getJSONObject(a);
				String [] strarray = new String[require];
				for(int b=0;b<require;b++){
					strarray[b]=jsonObject.getString(requireStr[b]);
				}
				JsonVector.addElement(strarray);
				strarray=null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			jsonArray=null;
			jsonObject=null;
			System.gc();
//			C.out("�����:"+C.prizeNameVector.size());
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
