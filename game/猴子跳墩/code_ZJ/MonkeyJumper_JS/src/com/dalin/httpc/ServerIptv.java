package com.dalin.httpc;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class ServerIptv{
	
	private String submit_url;

	private String encryptedstr;

	private CanvasControl cc;

	public ServerIptv(CanvasControl canvasControl) {
		cc = canvasControl;
	}
	
	/**
	 * ���ص���ֵ
	 */
	public void backStr(int index,String backstr){
//		C.out("backDataArray:"+"\n"+backstr);
		switch (index) {
		case 11:{//����û�������Ϣ
//			C.out("����û�������Ϣ:"+"\n"+backstr);
			JSONObject getJsonObject;
			try {
				getJsonObject = new JSONObject(backstr);
				String gets = ""+getJsonObject.getString("res");
				JSONObject temp_j = new JSONObject(gets);
				CanvasControl.score = temp_j.getInt("score");
				CanvasControl.totalScore = temp_j.getInt("totalscore");
				
				CanvasControl.rank = temp_j.getInt("rank");
				CanvasControl.rankall = temp_j.getInt("rankall");
				
				temp_j = null;
				getJsonObject = null;
				
//				C.out("score:"+CanvasControl.score);
//				C.out("scoreall:"+CanvasControl.scoreall);
//				C.out("rank:"+CanvasControl.rank);
//				C.out("rankall:"+CanvasControl.rankall);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}break;
		case 12:{//�õ����а�����
			C.out("----------------------------------------");
			C.out("���а�����:"+backstr+"\n"+this);
			C.out("----------------------------------------");
			JSONObject getJsonObject = null;
			try {
				getJsonObject = new JSONObject(backstr);
				String rankStr = getJsonObject.getString("res");
				String rankMainStr = getJsonObject.getString("mineresults");
//				C.out("get rankAll data:"+rankAllStr);
//				C.out("my result : " + rankMainStr);
				getJsonObject = new JSONObject(rankMainStr);
				CanvasControl.score = getJsonObject.getInt("score");
				CanvasControl.rank = getJsonObject.getInt("rank");
				
				C.out("my score : " + CanvasControl.score);
				C.out("my rank : " + CanvasControl.rank);
				
//				C.out("get rankAll data:" + rankAllStr);
				if(CanvasControl.v_Rank==null)CanvasControl.v_Rank = new Vector();
				CanvasControl.v_Rank = this.getJsonArrayData(rankStr, new String[]{"phone","rank","score"});
//				C.out("count:"+CanvasControl.v_RankAll.size());
				//cc.setState(CanvasControl.ACTIVITY_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}break;
		case 13 : {//�õ��ܷ�����
			JSONObject getJsonObject = null;
			try {
				getJsonObject = new JSONObject(backstr);
				String rankAllStr = getJsonObject.getString("res");
				String rankAllMainStr = getJsonObject.getString("mineresults");
//				C.out("get rankAll data:"+rankAllStr);
//				C.out("my result : " + rankAllMainStr);
				getJsonObject = new JSONObject(rankAllMainStr);
				CanvasControl.totalScore = getJsonObject.getInt("score");
				CanvasControl.rankall = getJsonObject.getInt("rank");
				C.out("my totalScore : " + CanvasControl.totalScore);
				C.out("my rankall : " + CanvasControl.rankall);
				
				if(CanvasControl.v_RankAll==null)CanvasControl.v_RankAll = new Vector();
				CanvasControl.v_RankAll = this.getJsonArrayData(rankAllStr, new String[]{"phone","rank","score"});
				C.out("count:"+CanvasControl.v_RankAll.size());
				cc.setState(CanvasControl.ACTIVITY_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		case 14:{//���ͷ���  ����ķ��ͷ����ĵ�12ȥ�ˡ�
//			C.out("���ͷ���  :"+backstr+"\n"+this);
//			cc.setState(CanvasControl.sRank,0);
//			for (int a = 0; a < 5; a++) {
//				System.out.print("g"+C.sa_goldAndTime[a*2]+"t"+C.sa_goldAndTime[a*2+1]+"\n");
//			}
		}break;
		case 15:{//�ϴ�����ֵ
		}break;
		}
	}
	
	/**
	 * ����û�������Ϣ
	 */
	public void doGetUserProfile(){
		encryptedstr="";
		submit_url="";
		submit_url = "iptv=" + CanvasControl.iptvID;
		encryptedstr = getUrl(".get", submit_url);
//		C.out("��ȡ�û���Ϣ�����ʵ�ַ:"+"\n"+encryptedstr);
		new Connection(this, encryptedstr, 11);
	}
	
	/**
	 * ������а�
	 */
	public void doGetRank(){
		encryptedstr="";
		submit_url="";
		submit_url = "iptv=" + CanvasControl.iptvID+
		"&"+
		"limit="+"10"
		;
		encryptedstr = getUrl(".rank", submit_url);
//		C.out("�õ�����:"+"\n"+encryptedstr);
		new Connection(this, encryptedstr, 12);
	}
	
	public void doGetRankAll(){
		encryptedstr="";
		submit_url="";
		submit_url = "iptv=" + CanvasControl.iptvID + 
		"&" + "limit=10";
		encryptedstr = getUrl(".rank.all", submit_url);
		C.out("request address:"+"\n"+encryptedstr);
		new Connection(this, encryptedstr, 13);
	}
	
	/**
	 * �ϴ��û�����
	 */
	public void doSendScore(int gold){
		encryptedstr="";
		submit_url="";
		submit_url = 
			"iptv=" + CanvasControl.iptvID+
			"&"+
			"score="+(CanvasControl.game_score)+
			"&"+
			"phone=15105165132"
			;
		encryptedstr = getUrl(".add", submit_url);
		C.out("�ϴ��û�����:"+encryptedstr);
		
		new Connection(this, encryptedstr, 14);
//		cc.zj_iptv_prop_tool.doCrossPropertySaveOrUpdate(CanvasControl.GAME_PROP_CODE, ""+gold, ""+times);
		
	}
	
	/**
	 * �ϴ�������
	 */
	public void doSendLifeNumber(int lifeNumber){
		encryptedstr="";
		submit_url="";
//		submit_url = 
//			"iptv=" + CanvasControl.iptvID+
//			"&"+
//			"life="+C.life_Now
//			;
		encryptedstr = getUrl("life.edit", submit_url);
//		C.out("�ϴ�����ֵ:"+encryptedstr);
		
		new Connection(this, encryptedstr, 14);
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
		input = md5.toMD5(input+CanvasControl.APP_KEY) + input;
		String url = CanvasControl.COMPANYURL_PHP + "?m="+CanvasControl.GAME_ADDRESS + action
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
		byte[] mkey = md5.toMD5(CanvasControl.APP_KEY).substring(1, 19).getBytes();
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
