package com.zhangniu.prop;

import com.zhangniu.game.C;
import com.zhangniu.game.Screen;

public class Props extends Thread {

	public static String Coin;// �û�Ԫ����
	public static String pwd = "";// ͯ��
	public static String kindid = "P10021";// ��Ʒ id
	public static String[] propid = { "DJ10070",//ʱ�����
									"DJ10071",//�й�������
									"DJ10072",//��������
									"DJ10073",//
									"DJ10074",//
									"DJ10075" //
									};// ����id
	public static float[] propPrice;// ��������Ҽ۸�
	public static int[] propCoinPrice;// ����Ԫ���۸�

	public static int coin_result;// ��ֵ���
	public static int prop_result;// ���߹�����
	public static int coinprice;// ��¼�����Ԫ��ֵ
	public static boolean waitResult;
	public static int buy_propid;
	public static String str_BuyCode;

	public Props(String iptvid) {
		waitResult = false;
	}

	/** ������� */
	public void buyProp(int id) {
		Screen.nx_tool.do_BuyProp(C.GetImageSource("/js_prop/" + id + ".png"), propid[id]);
		
		buy_propid = id;
		str_BuyCode = propid[id];
	}

	/** ����Ԫ�� */
	public void buyCoin(int price) {
	}

}
