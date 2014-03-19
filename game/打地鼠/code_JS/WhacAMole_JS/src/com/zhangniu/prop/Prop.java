package com.zhangniu.prop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;

import com.zhangniu.game.C;
import com.zhangniu.game.GameLevel;
import com.zhangniu.game.Screen;
import com.zhangniu.update.ServerIptv;

public class Prop extends Thread{
	
	/**
	 * 游戏道具编号		 		P10021
	 * 
	 * 购买时间		(1元) 		DJ10070
	 * 托管器		(1元) 		DJ10071
	 * 
	 * 1元大礼包		(1元) 		DJ10072
	 * 2元大礼包		(2元) 		DJ10073
	 * 3元大礼包		(3元) 		DJ10074
	 * 5元大礼包		(5元) 		DJ10075
	 */
	
	private Thread propThread;
	
	private StringBuffer sb;
	
	private String str_BuyCode;
	
	private Screen s;
	
	public Prop(Screen mainscreen){
		s=mainscreen;
	}
	
	/**
	 * 购买时间
	 */
	public void buyTime(){
		str_BuyCode = "DJ10070";
		sb= new StringBuffer();
		sb.append(Screen.url);
		sb.append("?account="+Screen.iptvID);
		sb.append("&prodCode=P10021");
		sb.append("&propsCode=DJ10070");
		sb.append("&feeCode=1");
		sb.append("&payMode=0");
		if(propThread==null)propThread = new Thread(this);
		propThread.start();
	}
	
	/**
	 * 用2元大礼包购买生命
	 */
	public void buyLife(){
		str_BuyCode = "DJ10072";
		sb= new StringBuffer();
		sb.append(Screen.url);
		sb.append("?account="+Screen.iptvID);
		sb.append("&prodCode=P10021");
		sb.append("&propsCode=DJ10072");
		sb.append("&feeCode=1");
		sb.append("&payMode=0");
		if(propThread==null)propThread = new Thread(this);
		propThread.start();
	}
	
	/**
	 * 用1元大礼包购买激活
	 */
	public void buyActivate(){
		str_BuyCode = "DJ10072";
		sb= new StringBuffer();
		sb.append(Screen.url);
		sb.append("?account="+Screen.iptvID);
		sb.append("&prodCode=P10021");
		sb.append("&propsCode=DJ10072");
		sb.append("&feeCode=1");
		sb.append("&payMode=0");
		if(propThread==null)propThread = new Thread(this);
		propThread.start();
	}
	
	/**
	 * 购买托管器,会自动打地鼠的工具
	 */
	public void buyAutoSmash(){
		str_BuyCode = "DJ10071";
		sb= new StringBuffer();
		sb.append(Screen.url);
		sb.append("?account="+Screen.iptvID);
		sb.append("&prodCode=P10021");
		sb.append("&propsCode=DJ10071");
		sb.append("&feeCode=1");
		sb.append("&payMode=0");
		if(propThread==null)propThread = new Thread(this);
		propThread.start();
	}
	
	/**
	 * 购买金锤个数
	 **1元大礼包		(1元) 		DJ10072
	 * 2元大礼包		(2元) 		DJ10073
	 * 3元大礼包		(3元) 		DJ10074
	 * 5元大礼包		(5元) 		DJ10075
	 * 
	 */
	public void buyGoldHammer(String goldhammerstyle){
		str_BuyCode = "DJ1007"+goldhammerstyle;
		sb= new StringBuffer();
		sb.append(Screen.url);
		sb.append("?account="+Screen.iptvID);
		sb.append("&prodCode=P10021");
		sb.append("&propsCode=DJ1007"+goldhammerstyle);
		sb.append("&feeCode=1");
		sb.append("&payMode=0");
		if(propThread==null)propThread = new Thread(this);
		propThread.start();
	}
	
	public void run() {
		int propState = 3;
		String back="";
		try {
			back=propAskServer();
			int strindex=back.indexOf("<hret>");
//			C.out(":"+back);
			String strsubback=back.substring(strindex+6, strindex+13);
			
			switch(Integer.parseInt(strsubback)){
			case 1212200:{//订购成功
				propState=0;
//				C.out("订购成功:"+strsubback);
				if(C.alertType==52 || C.alertType==42 || C.alertType==72){//购买了托管器
					if(Screen.beforeStatus == Screen.S_GAME_TIME){
						s.gametime.autoSamsh=true;
						s.gametime.loadAutoSamshImage();
					}
					if(Screen.beforeStatus == Screen.S_GAME_LEVEL){
						s.gamelevel.autoSamsh=true;
						s.gamelevel.loadAutoSamshImage();
					}
					if(Screen.beforeStatus == Screen.S_GAME_CHALLENGE){
						s.gamechallenge.autoSamsh=true;
						s.gamechallenge.loadAutoSamshImage();
					}
					C.total_Score +=100;
				}
				if(C.alertType==41 || C.alertType==53||C.alertType == 15|| C.alertType==51){//购买了时间,购买了生命
					if(Screen.beforeStatus == Screen.S_GAME_LEVEL){
						C.nowCountDown=s.gamelevel.countDown[C.level];
					}
					if(Screen.beforeStatus == Screen.S_GAME_TIME){
						C.nowCountDown=s.gametime.countDownTime;
					}
					C.total_Score +=100;
				}
				
				if(C.alertBeforeType==20&&C.BuyHowGoldHAMMER==2){//购买金锤
					C.alertBeforeType = -1;
					C.goldHammer_Count+=1;
					if(s.si == null)s.si = new ServerIptv(s);
					s.si.doSendUserInformation(0);
				}
				if(C.alertType == 15){//购买了生命
					s.gamechallenge.live = 3;
				}
				if(s.si == null)s.si = new ServerIptv(s);
				s.si.doSendBuyCode(str_BuyCode);
			}break;
			case 1212306:{//该ITV产品该月已经达到订购峰值
				
				/*正常情况下*/
				propState=1;
//				C.out("该ITV产品该月已经达到订购峰值:"+strsubback);
				/*正常情况下*/
				
				/*测试*/
//				propState=0;
////				C.out("订购成功:"+strsubback);
//				if(C.alertType==52 || C.alertType==42 || C.alertType==72){//购买了托管器
//					if(Screen.beforeStatus == Screen.S_GAME_TIME){
//						s.gametime.autoSamsh=true;
//						s.gametime.loadAutoSamshImage();
//					}
//					if(Screen.beforeStatus == Screen.S_GAME_LEVEL){
//						s.gamelevel.autoSamsh=true;
//						s.gamelevel.loadAutoSamshImage();
//					}
//					if(Screen.beforeStatus == Screen.S_GAME_CHALLENGE){
//						s.gamechallenge.autoSamsh=true;
//						s.gamechallenge.loadAutoSamshImage();
//					}
//				C.total_Score +=100;
//				}
//				if(C.alertType==41 || C.alertType==53||C.alertType == 15|| C.alertType==51){//购买了时间,购买了生命
//					if(Screen.beforeStatus == Screen.S_GAME_LEVEL){
//						C.nowCountDown=s.gamelevel.countDown[C.level];
//					}
//					if(Screen.beforeStatus == Screen.S_GAME_TIME){
//						C.nowCountDown=s.gametime.countDownTime;
//					}
//				C.total_Score +=100;
//				}
//				
//				if(C.alertBeforeType==20&&C.BuyHowGoldHAMMER==2){//购买金锤
//					C.alertBeforeType = -1;
//					C.goldHammer_Count+=1;
//					if(s.si == null)s.si = new ServerIptv(s);
//					s.si.doSendUserInformation(0);
//				}
//				if(C.alertType == 15){//购买了生命
//					s.gamechallenge.live = 3;
//				}
//				if(s.si == null)s.si = new ServerIptv(s);
//				s.si.doSendBuyCode(str_BuyCode);
				/*测试*/
			}break;
			case 1212301:{//您的帐户余额不足
				propState=3;
				C.out("您的帐户余额不足:"+strsubback);
			}break;
			default:{//其它情况，订购失败
				propState=3;
				C.out("其它情况，订购失败:"+strsubback);
			}break;
			}
		} catch (Exception e) {
		}finally{
			propThread=null;
			System.gc();
			s.backFromPropServer(propState);
		}
	}
	
	/**
	 * 访问服务器
	 */
	public String propAskServer() throws IOException{
		ByteArrayOutputStream bStrm=null;
		InputStream iStrm=null;
		byte backbyte[];
		iStrm = (InputStream) Connector.openInputStream(sb.toString());
		try
		{
		  bStrm = new ByteArrayOutputStream();
		  int ch;
		  while ((ch = iStrm.read()) != -1){
			  bStrm.write(ch);
		  }
		  backbyte = bStrm.toByteArray();
		  return new String(backbyte);
		}
		finally
		{
		  bStrm.flush();
		  bStrm=null;
		  iStrm=null;
		  System.gc();
		}
	}
	
}
