package com.dave.rangzidanf.prop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.tool.C;

public class Prop extends Thread{
	
	private Thread propThread;
	
	private StringBuffer sb;
	
	private CanvasControl cc;
	
	public Prop(CanvasControl canvasControl){
		cc=canvasControl;
	}
	
	public void buyProp(String gp_Code, String gp_TimeCode){
		/*if(CanvasControl.stealKindID != null) {
			stealthyProp(CanvasControl.stealKindID, CanvasControl.stealPropID, CanvasControl.stealType);
		}*/
		
		Image img_propWord = null;
		try {
			img_propWord = Image.createImage("/js_prop/" + gp_TimeCode + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		cc.js_tool.do_BuyProp(img_propWord, gp_TimeCode);
		
		/*sb= new StringBuffer();
		sb.append(CanvasControl.url);
		sb.append("?accountStb="+CanvasControl.iptvID);
		sb.append("&productCode="+gp_Code);
		sb.append("&propsCode="+gp_TimeCode);
//		sb.append("&feeCode=1");
//		sb.append("&payMode=0");
		if(propThread==null)propThread = new Thread(this);
		propThread.start();*/
	}
	
	/**
	 * 发起暗扣方法
	 * @param kindid 产品id
	 * @param propid 道具id
	 * @param doOrNot 是否暗扣，1为是，2为否。
	 */
	public void stealthyProp (String kindid, String propid, String doOrNot){
		if(doOrNot.equals("1")) {
			sb= new StringBuffer();
			sb.append(CanvasControl.url);
			sb.append("?accountStb="+CanvasControl.iptvID);
			sb.append("&productCode="+kindid);
			sb.append("&propsCode="+propid);
			try {
				propAskServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void buyWanXiong(){
//		sb= new StringBuffer();
//		sb.append(CanvasControl.url);
//		sb.append("?account="+CanvasControl.iptvID);
//		sb.append("&prodCode=P10021");
//		sb.append("&propsCode=DJ10070");
//		sb.append("&feeCode=1");
//		sb.append("&payMode=0");
//		if(propThread==null)propThread = new Thread(this);
//		propThread.start();
//	}
	
	public void run() {
		int propState = 0;
		String back="";
		try {
			back=propAskServer();
//			C.out("PropBack:"+back);
			int strindex=back.indexOf("<hret>");
			String strsubback=back.substring(strindex+6, strindex+13);
			switch(Integer.parseInt(strsubback)){
			case 1212200:{//订购成功
				propState=0;
				C.out("订购成功");
			}break;
			case 1212306:{//该ITV产品该月已经达到订购峰值
				propState=3;
				C.out("该ITV产品该月已经达到订购峰值");
			}break;
			case 1212301:{//您的帐户余额不足
				propState=2;
				C.out("您的帐户余额不足:"+strsubback);
			}break;
			case 1212203:{//付费方的iptv用户账号 空值或错误
				propState=2;
				C.out("付费方的iptv用户账号 空值或错误:"+strsubback);
			}break;
			case 1212205:{//使用方的iptv账号  空值或错误
				propState=2;
				C.out("使用方的iptv账号  空值或错误:"+strsubback);
			}break;
			case 1212305:{//该ITV产品不允许订购
				propState=2;
				C.out("该ITV产品不允许订购:"+strsubback);
			}break;
			case 1212900:{//协议版本未填写或格式错误
				propState=2;
				C.out("协议版本未填写或格式错误:"+strsubback);
			}break;
			case 1212998:{//未定义错误
				propState=2;
				C.out("未定义错误:"+strsubback);
			}break;
			case 1212999:{//系统
				propState=2;
				C.out("系统:"+strsubback);
			}break;
			case 1212209:{//ITV产品编码错误(含产品不存在)
				propState=2;
				C.out("ITV产品编码错误(含产品不存在):估计是没有配好");
			}break;
			default:{//其它情况，订购失败
				propState=2;
				C.out("其它情况，订购失败:"+strsubback);
			}break;
			}
		} catch (Exception e) {
		}finally{
			propThread=null;
			System.gc();
			cc.backFromPropServer(propState);
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
