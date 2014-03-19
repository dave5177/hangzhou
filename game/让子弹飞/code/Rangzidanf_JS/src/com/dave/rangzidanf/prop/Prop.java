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
	 * ���𰵿۷���
	 * @param kindid ��Ʒid
	 * @param propid ����id
	 * @param doOrNot �Ƿ񰵿ۣ�1Ϊ�ǣ�2Ϊ��
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
			case 1212200:{//�����ɹ�
				propState=0;
				C.out("�����ɹ�");
			}break;
			case 1212306:{//��ITV��Ʒ�����Ѿ��ﵽ������ֵ
				propState=3;
				C.out("��ITV��Ʒ�����Ѿ��ﵽ������ֵ");
			}break;
			case 1212301:{//�����ʻ�����
				propState=2;
				C.out("�����ʻ�����:"+strsubback);
			}break;
			case 1212203:{//���ѷ���iptv�û��˺� ��ֵ�����
				propState=2;
				C.out("���ѷ���iptv�û��˺� ��ֵ�����:"+strsubback);
			}break;
			case 1212205:{//ʹ�÷���iptv�˺�  ��ֵ�����
				propState=2;
				C.out("ʹ�÷���iptv�˺�  ��ֵ�����:"+strsubback);
			}break;
			case 1212305:{//��ITV��Ʒ��������
				propState=2;
				C.out("��ITV��Ʒ��������:"+strsubback);
			}break;
			case 1212900:{//Э��汾δ��д���ʽ����
				propState=2;
				C.out("Э��汾δ��д���ʽ����:"+strsubback);
			}break;
			case 1212998:{//δ�������
				propState=2;
				C.out("δ�������:"+strsubback);
			}break;
			case 1212999:{//ϵͳ
				propState=2;
				C.out("ϵͳ:"+strsubback);
			}break;
			case 1212209:{//ITV��Ʒ�������(����Ʒ������)
				propState=2;
				C.out("ITV��Ʒ�������(����Ʒ������):������û�����");
			}break;
			default:{//�������������ʧ��
				propState=2;
				C.out("�������������ʧ��:"+strsubback);
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
