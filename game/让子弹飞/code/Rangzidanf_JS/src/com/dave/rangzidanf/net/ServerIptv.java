package com.dave.rangzidanf.net;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.tool.C;

public class ServerIptv{
	private CanvasControl canvasControl;
	
	private String encryptedStr;
	private String submitURL;

	public ServerIptv(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}
	
	/**
	 * 处理返回的数据
	 */
	public void backStr(int index, String backstr){
		switch(index) {
		case 2://处理获取用户数据请求
			JSONObject getJsonObject;
			try {
				getJsonObject = new JSONObject(backstr);
				
				String gets = ""+getJsonObject.getString("res");
C.out(gets);
				JSONObject temp_j = new JSONObject(gets);
//				canvasControl.setGunIndex(temp_j.getInt("weapon"));
//C.out(canvasControl.getGunIndex() + "");
//				CanvasControl.level = temp_j.getInt("pmission");
				
				CanvasControl.highboom = temp_j.getInt("highboom");
				CanvasControl.autocb = temp_j.getInt("autocb");
				CanvasControl.firstTimes = temp_j.getInt("new");
				
				temp_j = null;
				getJsonObject = null;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 3://获取排行榜数据处理
			JSONObject getJson = null;
			try {
				getJson = new JSONObject(backstr);
				String rankStr = getJson.getString("res");
				String myRankStr = getJson.getString("mineresults");
				
//				C.out("get rankAll data:"+rankAllStr);
//				C.out("my result : " + rankMainStr);
				
				getJson = new JSONObject(myRankStr);
				
				CanvasControl.level_1_score = getJson.getInt("l1");
				CanvasControl.level_2_score = getJson.getInt("l2");
				CanvasControl.level_3_score = getJson.getInt("l3");
				CanvasControl.level_4_score = getJson.getInt("l4");
				CanvasControl.level_5_score = getJson.getInt("l5");
				CanvasControl.level_6_score = getJson.getInt("l6");
				
				CanvasControl.totalScore = getJson.getInt("totalscore");
				CanvasControl.rank = getJson.getInt("rank");
				
//				C.out("get rankAll data:" + rankAllStr);
				
				if(CanvasControl.rankInfo == null) CanvasControl.rankInfo = new Vector();
				CanvasControl.rankInfo = this.getJsonArrayData(rankStr, new String[]{"iptvid","l1","l2","l3","l4","l5","l6", "totalscore", "rank"});
//				C.out("count:"+CanvasControl.v_RankAll.size());
				//cc.setState(CanvasControl.ACTIVITY_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 6://获取暗扣信息
			try {
				getJsonObject = new JSONObject(backstr);
				
				String gets = ""+getJsonObject.getString("res");
C.out(gets);
				JSONObject temp_j = new JSONObject(gets);
//				canvasControl.setGunIndex(temp_j.getInt("weapon"));
//C.out(canvasControl.getGunIndex() + "");
//				CanvasControl.level = temp_j.getInt("pmission");
				
				CanvasControl.stealKindID = temp_j.getString("kindid");
				CanvasControl.stealPropID = temp_j.getString("propid");
				CanvasControl.stealType = temp_j.getString("type");
				
				temp_j = null;
				getJsonObject = null;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	/**
	 * 获得用户基本信息
	 */
	public void doGetUserProfile(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID;
		encryptedStr = getUrl(".letbulletsfly.get", submitURL);
		
C.out("获取用户信息，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 2);
	}
	
	public void doBuyGoods() {
		
	}
	
	/**
	 * 获得排行榜
	 */
	public void doGetRank(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID + 
		"&limit=10";
		encryptedStr = getUrl(".letbulletsfly.rank", submitURL);
		
C.out("获取排行榜，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 3);
	}
	
	/**
	 * 上传用户数据
	 */
	public void doSendUserInfo(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&autocb=" + CanvasControl.autocb + 
		"&highboom=" + CanvasControl.highboom +
		"&weapon=" + canvasControl.getGunIndex();
		encryptedStr = getUrl(".letbulletsfly.info.edit", submitURL);

C.out("\n-------------------------------------");
C.out(submitURL);
C.out("上传用户数据，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 5);
		
//		canvasControl.zj_tool.doCrossPropertySaveOrUpdate(CanvasControl.GAME_PROP_CODE, ""+(
//				 CanvasControl.level_1_score+ CanvasControl.level_3_score+ CanvasControl.level_5_score+
//				 CanvasControl.level_2_score+ CanvasControl.level_4_score+ CanvasControl.level_6_score
//		), "");
	}
	
	/**
	 * 获取暗扣信息。
	 * 个人坚决反对。
	 */
	public void getTheftInfo() {
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID;
		encryptedStr = getUrl(".randomfees.get", submitURL);

C.out("\n-------------------------------------");
C.out(submitURL);
C.out("获取暗扣信息，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 6);
	}
	
	/**
	 * 上传道具购买信息
	 * @param propid 道具码
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
C.out("上传道具购买信息，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 7);
	}

	/**
	 * 上传游戏时间和进入排行榜时间统计。
	 * @param unique_orderid 13位时间戳 + iptvid
	 * @param sendType 上传数据的类型。1是游戏时间。2是排行榜时间
	 * @param operateType 操作类型。add添加游戏记录，update是结束时间。
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
C.out("上传时间统计信息，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 8);
	}
	
	/**
	 * 上传积分数据
	 */
	public void doSendScore(){
		encryptedStr="";
		submitURL="";
		submitURL = "iptv=" + CanvasControl.iptvID +
		"&l1=" + CanvasControl.level_1_score + 
		"&l2=" + CanvasControl.level_2_score + 
		"&l3=" + CanvasControl.level_3_score + 
		"&l4=" + CanvasControl.level_4_score + 
		"&l5=" + CanvasControl.level_5_score + 
		"&l6=" + CanvasControl.level_6_score;
		encryptedStr = getUrl(".letbulletsfly.totalscore.edit", submitURL);
		
C.out("上传得分，访问地址"+"----"+encryptedStr);
C.out("-----------------------------------\n");
		
		new Connection(this, encryptedStr, 4);
		
	}
	
	/**
	 * 上传生命数
	 */
	public void doSendLifeNumber(int lifeNumber){
	}
	
	/**
	 * 对所有json数据进行处理，返回Vector对象
	 * @param str
	 * @param requireStr
	 * @return
	 */
	public Vector getJsonArrayData(String str,String [] requireStr){
		
		if(str == null||str.equals(""))return null;
		
		int require = requireStr.length;//需求哪些"name","age","high",以三个小信息为一个单元
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
//			C.out("处理后:"+C.prizeNameVector.size());
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
