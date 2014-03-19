package com.zhangniu.update;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import com.zhangniu.game.C;
import com.zhangniu.game.Screen;

public class ServerIptv{
	
	private String submit_url;

	private String encryptedstr;

	private Base64 base64class;

	private Screen ms;

	private int serverKey;

	/**
	 * ���캯��
	 */
	public ServerIptv(Screen mainscreen) {
		ms = mainscreen;
		// ����md5����
		base64class = new Base64();
	}
	
	/**
	 * ���ص���ֵ
	 */
	public void backStr(int index,String backstr){
		System.out.println("backstr:"+backstr);
		switch (index) {
		case 1:{//�ʼ���ع�����Ļ
			ms.backFromDateServer(1, backstr);
		}break;
		case 2:{//�����н��û�������
			try {
				JSONObject getJsonObject = new JSONObject(backstr);
				String gets = ""+getJsonObject.getString("res");
				C.vector_PrizeName = getJsonArrayData(gets, new String[]{"phone","prize"});
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ms.backFromDateServer(2, backstr);
		}break;
		case 3:{//�����ܻ������а�
			try {
				JSONObject getJsonObject = new JSONObject(backstr);
				String get = ""+getJsonObject.getString("res");
				C.vector_Rank_Total = getJsonArrayData(get, new String[]{"iptvid","score","rank"});
				
				String my = ""+getJsonObject.getString("my");
				JSONObject myo = new JSONObject(my);
				C.rank_total = Integer.parseInt(myo.getString("rank"));
				C.rank_totalScore = Integer.parseInt(myo.getString("score"));
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ms.backFromDateServer(3, backstr);
		}break;
		case 4:{//���ػ������а�
			try {
				JSONObject getJsonObject = new JSONObject(backstr);
				String get = ""+getJsonObject.getString("res");
				C.vector_Rank_Level = getJsonArrayData(get, new String[]{"iptvid","score","rank"});
				
				String my = ""+getJsonObject.getString("my");
				JSONObject myo = new JSONObject(my);
				C.rank_Level = Integer.parseInt(myo.getString("rank"));
				C.rank_LevelScore = Integer.parseInt(myo.getString("score"));
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ms.backFromDateServer(4, backstr);
		}break;
		case 5:{//��ս���а�
			try {
				JSONObject getJsonObject = new JSONObject(backstr);
				String get = ""+getJsonObject.getString("res");
				C.vector_Rank_Challenge = getJsonArrayData(get, new String[]{"iptvid","score","rank"});
				
				String my = ""+getJsonObject.getString("my");
				JSONObject myo = new JSONObject(my);
				C.rank_Challenge = Integer.parseInt(myo.getString("rank"));
				C.rank_ChallengeScore = Integer.parseInt(myo.getString("score"));
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ms.backFromDateServer(5, backstr);
		}break;
		case 6:{//����ʱ���а�
			try {
				JSONObject getJsonObject = new JSONObject(backstr);
				String get = ""+getJsonObject.getString("res");
				C.vector_Rank_Time = getJsonArrayData(get, new String[]{"iptvid","score","rank"});
				
				String my = ""+getJsonObject.getString("my");
				JSONObject myo = new JSONObject(my);
				C.rank_Time = Integer.parseInt(myo.getString("rank"));
				C.rank_TimelScore = Integer.parseInt(myo.getString("score"));
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ms.backFromDateServer(6, backstr);
		}break;
		case 11:{//�����û�������Ϣ
			try {
				JSONObject j1 = new JSONObject(backstr);
				String stringfirst = "" + j1.get("res");
				j1 = null;
				
				JSONObject j2 = new JSONObject();
				j2 = new JSONObject(stringfirst);
				
				C.total_Score = Integer.parseInt(j2.getString("total_score"));
				
				C.level_Score_history 		= Integer.parseInt(j2.getString("high_score"));
				C.challenge_Score_history 	= Integer.parseInt(j2.getString("challenge_score"));
				C.time_Score_history 		= Integer.parseInt(j2.getString("time_score"));
				C.goldHammer_Count 			= Integer.parseInt(j2.getString("goldhammer"));
				
				ms.setGameStatus(Screen.S_MENU);
				
//				C.out("�ܻ���:"+C.total_Score);
//				C.out("������߷�:"+C.level_Score_history);
//				C.out("��ս��߷�:"+C.challenge_Score_history);
//				C.out("��ʱ��߷�:"+C.time_Score_history);
//				C.out("�𴸸���:"+C.goldHammer_Count);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}break;
		case 12:{//�ϴ��û�����
//			C.out("�ɹ����:"+backstr);
		}break;
		}
	}
	
	/**
	 * �ӷ�����������Ļ����(1)
	 */
	public void dogetScroelBarStr() {
		encryptedstr = Screen.COMPANYURL + "?m=iptv.game.moleattack20.notice";
		Connection c = new Connection(this, encryptedstr, 1);
	}
	
	/**
	 * �ӷ����������û�������Ϣ(11)
	 */
	public void dogetUserInformation(){
		encryptedstr="";
		submit_url="";
		submit_url = "iptv=" + Screen.iptvID;
		encryptedstr = getUrl("get", submit_url);
		Connection c = new Connection(this, encryptedstr, 11);
	}
	
	/**
	 * �ϴ��������û���ص�����(12)
	 */
	public void doSendUserInformation(int serverkey){
		
		serverKey=serverkey;
		
		submit_url=
		"iptv="				+Screen.iptvID+"&"+
		"score="			+(C.total_Score)+"&"+
		"high_score="		+(C.level_Score)+"&"+
		"challenge_score="	+(C.challenge_Score)+"&"+
		"time_score="		+(C.time_Score)+"&"+
		"goldhammer="		+(C.goldHammer_Count);
		encryptedstr=getUrl("add", submit_url);
		Connection c = new Connection(this, encryptedstr, 12);
		
	}
	
	/**
	 * �ӷ����������н�����
	 */
	public void dogetPrizeName() {
		encryptedstr = Screen.COMPANYURL + "?m=iptv.game.moleattack20.winner";
		serverKey = 2;
		Connection c = new Connection(this, encryptedstr, 2);
	}

	/**
	 * �ӷ������������ܻ������а�
	 */
	public void dogetRankSum(){
		serverKey = 3;
		submit_url=
		"iptv="+Screen.iptvID+"&"+
		"type="+"total_score"+"&"+
		"limit="+"30";
		encryptedstr=getUrl("rank", submit_url);
		Connection c = new Connection(this, encryptedstr, 3);
	}
	
	/**
	 * �ӷ����������ش��ػ������а�
	 */
	public void dogetRankLevel(){
		submit_url=
		"iptv="+Screen.iptvID+"&"+
		"type="+"high_score"+"&"+
		"limit="+"30";
		encryptedstr=getUrl("rank", submit_url);
		Connection c = new Connection(this, encryptedstr, 4);
	}
	
	/**
	 * �ӷ�������������ս�������а�
	 */
	public void dogetRankChallenge(){
		submit_url=
		"iptv="+Screen.iptvID+"&"+
		"type="+"challenge_score"+"&"+
		"limit="+"30";
		encryptedstr=getUrl("rank", submit_url);
		Connection c = new Connection(this, encryptedstr, 5);
	}
	
	/**
	 * �ӷ����������ص���ʱ�������а�
	 */
	public void dogetRankTime(){
		submit_url=
		"iptv="+Screen.iptvID+"&"+
		"type="+"time_score"+"&"+
		"limit="+"30";
		encryptedstr=getUrl("rank", submit_url);
		Connection c = new Connection(this, encryptedstr, 6);
	}
	
	/**
	 * �ϴ��û��Ĺ����¼
	 */
	public void doSendBuyCode(String str){
		submit_url=
			"iptv="+Screen.iptvID+"&"+
			"prop="+str;
			encryptedstr=getUrl("proplog", submit_url);
			Connection c = new Connection(this, encryptedstr, 12);
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
		input = md5.toMD5(input+Screen.APP_KEY) + input;
		String url = Screen.COMPANYURL + "?m=iptv.game.moleattack20." + action
				+ "&input=" + input;
		return url;
	}

	/**
	 * ���ܷ�����������һ���ַ���ֵ �����õķ���
	 * 
	 * @param param
	 * @return
	 */
	private String encrypt(String param) {
		byte[] mkey = md5.toMD5(Screen.APP_KEY).substring(1, 19).getBytes();
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
