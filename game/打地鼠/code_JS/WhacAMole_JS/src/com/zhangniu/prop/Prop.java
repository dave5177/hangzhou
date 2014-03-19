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
	 * ��Ϸ���߱��		 		P10021
	 * 
	 * ����ʱ��		(1Ԫ) 		DJ10070
	 * �й���		(1Ԫ) 		DJ10071
	 * 
	 * 1Ԫ�����		(1Ԫ) 		DJ10072
	 * 2Ԫ�����		(2Ԫ) 		DJ10073
	 * 3Ԫ�����		(3Ԫ) 		DJ10074
	 * 5Ԫ�����		(5Ԫ) 		DJ10075
	 */
	
	private Thread propThread;
	
	private StringBuffer sb;
	
	private String str_BuyCode;
	
	private Screen s;
	
	public Prop(Screen mainscreen){
		s=mainscreen;
	}
	
	/**
	 * ����ʱ��
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
	 * ��2Ԫ�������������
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
	 * ��1Ԫ��������򼤻�
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
	 * �����й���,���Զ������Ĺ���
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
	 * ����𴸸���
	 **1Ԫ�����		(1Ԫ) 		DJ10072
	 * 2Ԫ�����		(2Ԫ) 		DJ10073
	 * 3Ԫ�����		(3Ԫ) 		DJ10074
	 * 5Ԫ�����		(5Ԫ) 		DJ10075
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
			case 1212200:{//�����ɹ�
				propState=0;
//				C.out("�����ɹ�:"+strsubback);
				if(C.alertType==52 || C.alertType==42 || C.alertType==72){//�������й���
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
				if(C.alertType==41 || C.alertType==53||C.alertType == 15|| C.alertType==51){//������ʱ��,����������
					if(Screen.beforeStatus == Screen.S_GAME_LEVEL){
						C.nowCountDown=s.gamelevel.countDown[C.level];
					}
					if(Screen.beforeStatus == Screen.S_GAME_TIME){
						C.nowCountDown=s.gametime.countDownTime;
					}
					C.total_Score +=100;
				}
				
				if(C.alertBeforeType==20&&C.BuyHowGoldHAMMER==2){//�����
					C.alertBeforeType = -1;
					C.goldHammer_Count+=1;
					if(s.si == null)s.si = new ServerIptv(s);
					s.si.doSendUserInformation(0);
				}
				if(C.alertType == 15){//����������
					s.gamechallenge.live = 3;
				}
				if(s.si == null)s.si = new ServerIptv(s);
				s.si.doSendBuyCode(str_BuyCode);
			}break;
			case 1212306:{//��ITV��Ʒ�����Ѿ��ﵽ������ֵ
				
				/*���������*/
				propState=1;
//				C.out("��ITV��Ʒ�����Ѿ��ﵽ������ֵ:"+strsubback);
				/*���������*/
				
				/*����*/
//				propState=0;
////				C.out("�����ɹ�:"+strsubback);
//				if(C.alertType==52 || C.alertType==42 || C.alertType==72){//�������й���
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
//				if(C.alertType==41 || C.alertType==53||C.alertType == 15|| C.alertType==51){//������ʱ��,����������
//					if(Screen.beforeStatus == Screen.S_GAME_LEVEL){
//						C.nowCountDown=s.gamelevel.countDown[C.level];
//					}
//					if(Screen.beforeStatus == Screen.S_GAME_TIME){
//						C.nowCountDown=s.gametime.countDownTime;
//					}
//				C.total_Score +=100;
//				}
//				
//				if(C.alertBeforeType==20&&C.BuyHowGoldHAMMER==2){//�����
//					C.alertBeforeType = -1;
//					C.goldHammer_Count+=1;
//					if(s.si == null)s.si = new ServerIptv(s);
//					s.si.doSendUserInformation(0);
//				}
//				if(C.alertType == 15){//����������
//					s.gamechallenge.live = 3;
//				}
//				if(s.si == null)s.si = new ServerIptv(s);
//				s.si.doSendBuyCode(str_BuyCode);
				/*����*/
			}break;
			case 1212301:{//�����ʻ�����
				propState=3;
				C.out("�����ʻ�����:"+strsubback);
			}break;
			default:{//�������������ʧ��
				propState=3;
				C.out("�������������ʧ��:"+strsubback);
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
	 * ���ʷ�����
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
