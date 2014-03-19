package com.zhangniu.prop;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import org.json.me.JSONArray;
import org.json.me.JSONObject;

import com.zhangniu.game.C;
import com.zhangniu.game.Screen;
import com.zhangniu.update.Base64;
import com.zhangniu.update.md5;

public class Props extends Thread {
	private final String APP_KEY = "bH2i2F3h58cj9bfl9A2baSeQ0A7Z7p0T8seC36aT9Cbn8jcn8z3DcH3S5g6U9Z3D";

	private final String COMPANYURL = "http://61.160.131.57:8083/www.iptvgame.com/";//���շ�����

	private String submit_url;
	private String last_url;

	private HttpConnection hc;

	private String lastResult;

	private Thread thread;

	private md5 md5class;
	private Base64 base64class;

	private int type;

	private String iptvid;
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
		this.iptvid = iptvid;
		md5class = new md5();
		base64class = new Base64();
		waitResult = false;
	}

	public void cleanAll() {
		submit_url = null;
		last_url = null;
		hc = null;
		lastResult = null;
		thread = null;
		md5class = null;
		base64class = null;
	}

	/** ��ȡ������ */
	public void getPropID() {
		/*type = 4;
		submit_url = "kindid=" + kindid;
		last_url = getUrl("iptv.props.get.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	/** ��ȡ�û�Ԫ���� */
	public void getCoin() {
		/*type = 1;
		submit_url = "account=" + iptvid;
		last_url = getUrl("iptv.gamegold.Inquiry.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	/** ������� */
	public void buyProp(int id) {
		Screen.js_tool.do_BuyProp(C.GetImageSource("/js_prop/" + id + ".png"), propid[id]);
		
		buy_propid = id;
		str_BuyCode = propid[id];
		cleanAll();
		/*type = 2;
		submit_url = "account=" + iptvid + "&prodCode=" + kindid
				+ "&propsCode=" + propid[id];
		last_url = getUrl("iptv.consumer.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	/** ����Ԫ�� */
	public void buyCoin(int price) {
		/*type = 3;
		coinprice = price;
		submit_url = "userid=" + iptvid + "&price=" + price + "&pwd=" + pwd;
		System.out.println("sub:" + submit_url);
		last_url = getUrl("gameorder.php", submit_url);
		thread = new Thread(this);
		thread.start();*/
	}

	public void run() {
		try {
			System.out.println("last_url:" + last_url);
			lastResult = new String(getViaHttpConnection(last_url));
			System.out.println("type:" + type);
			System.out.println("lastResult:" + lastResult);
			switch (type) {
			case 1:
				Coin = lastResult;
				break;
			case 2:
				if ("1212200".equals(lastResult)) {
					System.out.println("�����ɹ�");
					prop_result = 1;
				} else if ("1212301".equals(lastResult)) {
					System.out.println("����");
					prop_result = 2;
				} else {
					System.out.println("����ʧ��");
					prop_result = 3;
				}
				waitResult = true;
				Screen.s.backFromPropServer(prop_result);
				break;
			case 3:
				if ("0".equals(lastResult)) {
					System.out.println("��ֵ�ɹ�");
					coin_result = 1;
				} else if ("9103".equals(lastResult)) {
					System.out.println("ͯ����������");
					coin_result = 2;
				} else {
					System.out.println("��ֵʧ��");
					coin_result = 3;
				}
//				pwd = "";
				waitResult = true;
				break;
			case 4:
				JSONObject jsonObject = new JSONObject(lastResult);
				JSONArray jsonArray = (JSONArray) jsonObject.get("res");
				JSONObject obj;
				propPrice = new float[jsonArray.length()];
				propCoinPrice = new int[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					obj = jsonArray.getJSONObject(i);
					propPrice[i] = Float.parseFloat(obj.getString("price"));
					propCoinPrice[i] = Integer.parseInt(obj
							.getString("gamegolds"));
				}
				break;
			}
			cleanAll();
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������磬��������
	 */
	public byte[] getViaHttpConnection(String url) throws IOException {
		InputStream is = null;
		byte[] data = null;
		System.gc();
		int rc;

		try {
			hc = (HttpConnection) Connector.open(url);
			rc = hc.getResponseCode();
			if (rc != HttpConnection.HTTP_OK) {
				throw new IOException("HTTP response code: " + rc);
			}

			is = hc.openInputStream();
			int len = (int) hc.getLength();
			if (len > 0) {
				int actual = 0;
				int bytesread = 0;
				data = new byte[len];
				while ((bytesread != len) && (actual != -1)) {
					actual = is.read(data, bytesread, len - bytesread);
					bytesread += actual;
				}
			} else {
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Not an HTTP URL");
		} finally {
			is.close();
			hc.close();
			hc = null;
			is = null;
			System.gc();
		}
		return data;
	}

	/**
	 * ���������õ����յ�url
	 */
	public String getUrl(String action, String param) {
		String input = encrypt(param);
		input = md5class.getMD5ofStr((input + APP_KEY)) + input;
		String url = COMPANYURL + action + "?input=" + input;
		return url;
	}

	/**
	 * ���ܷ�����������һ���ַ���ֵ �����õķ���
	 * 
	 * @param param
	 * @return
	 */
	private String encrypt(String param) {
		byte[] mkey = md5class.getMD5ofStr(APP_KEY).substring(1, 19).getBytes();
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
