package com.dave.jpsc.model;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;

public class Player {

	/**
	 * ������
	 */
	public static final int DEFEATED = 1;

	/**
	 * ��ʤ
	 */
	public static final int WIN = 2;

	/**
	 * �ѽ����Ĺؿ�
	 */
	public int missionPassed;

	/**
	 * �û��ǳ�
	 */
	public String nickName = "Momo";

	/**
	 * �ȼ��������þ�������أ�
	 */
	public int level = 1;

	/**
	 * ����ֵ�����100��
	 */
	public int expeirence = 100;

	/**
	 * �û�ʵ��ֵ(��ʼʱΪ0����ս�ɹ������ӣ�
	 */
	public int strength;

	/**
	 * ʣ����ս����
	 */
	public int duelTimes = 10;

	/**
	 * ��ս�ĳ���(����ӵ�еĳ�������±�ֵ��
	 */
	public int mainCar = 0;

	/**
	 * ����ӵ�еĳ�������־ö� �����ҷֱ��ǣ������±�ֵ����̥�־öȣ�����־öȣ�β��־öȣ�����������
	 */
	public int[][] cars;

	/**
	 * ������ʵ�ʲ�����������ģ�
	 */
	public int[][] carRealParams;

	/**
	 * �û�id
	 */
	public String userid;

	/**
	 * cavas
	 */
	private CanvasControl canvasControl;

	/**
	 * @param nickName
	 *            �ǳ�
	 * @param level
	 *            �ȼ�
	 * @param experience
	 *            ����
	 * @param strength
	 *            ʵ��֮
	 * @param duelTimes
	 *            ʣ����ս����
	 * @param mainCar
	 *            ��ս����
	 * @param cars
	 *            ӵ�е����еĳ�
	 * @param userid
	 *            iptvID
	 * @param canvasControl
	 *            canvas
	 */
	public Player(String nickName, int level, int experience, int strength,
			int duelTimes, int mainCar, int[][] cars, String userid,
			CanvasControl canvasControl) {
		super();
		this.nickName = nickName;
		this.level = level;
		this.expeirence = experience;
		this.strength = strength;
		this.duelTimes = duelTimes;
		this.mainCar = mainCar;
		this.cars = cars;
		this.userid = userid;
		this.canvasControl = canvasControl;

		computCarParam();
	}

	/**
	 * @param missionPassed
	 *            ��ͨ���ؿ�
	 * @param level
	 *            �ȼ�
	 * @param experience
	 *            ����
	 * @param strength
	 *            ʵ��֮
	 * @param duelTimes
	 *            ʣ����ս����
	 * @param mainCar
	 *            ��ս����
	 * @param cars
	 *            ӵ�е����еĳ�
	 * @param userid
	 *            iptvID
	 * @param canvasControl
	 *            canvas
	 */
	public Player(int missionPassed, String nickName, int level,
			int expeirence, int strength, int duelTimes, int mainCar,
			int[][] cars, String userid, CanvasControl canvasControl) {
		super();
		this.missionPassed = missionPassed;
		this.nickName = nickName;
		this.level = level;
		this.expeirence = expeirence;
		this.strength = strength;
		this.duelTimes = duelTimes;
		this.mainCar = mainCar;
		this.cars = cars;
		this.userid = userid;
		this.canvasControl = canvasControl;

		computCarParam();
	}

	/**
	 * �������еĳ���ʵ�ʲ���
	 */
	public void computCarParam() {
		carRealParams = new int[cars.length][4];
		for (int i = 0; i < carRealParams.length; i++) {
			carRealParams[i][0] = CanvasControl.carProperty[cars[i][0]][0]
					+ cars[i][4] * 20;// �ٶ�
			if (carRealParams[i][0] > CanvasControl.carMaxPower[cars[i][0]][0])
				carRealParams[i][0] = CanvasControl.carMaxPower[cars[i][0]][0];

			carRealParams[i][1] = CanvasControl.carProperty[cars[i][0]][1]
					+ cars[i][4] * 10;// ���ٶ�
			if (carRealParams[i][1] > CanvasControl.carMaxPower[cars[i][0]][1])
				carRealParams[i][1] = CanvasControl.carMaxPower[cars[i][0]][1];

			if(CanvasControl.DEBUG) {
				System.out.println("�������ٶȣ�" + carRealParams[i][0]); 
				System.out.println("��������ٶȣ�" + carRealParams[i][1]); 
			}
			carRealParams[i][2] = CanvasControl.carProperty[cars[i][0]][2];
			carRealParams[i][3] = CanvasControl.carProperty[cars[i][0]][3];
		}
	}

	/**
	 * ����һ�������
	 * 
	 * @param carIndex
	 */
	public void addCar(int carIndex) {
		int[] new_car = { carIndex, 100, 100, 100, 0 };
		int[][] result_cars = new int[cars.length + 1][5];
		for (int i = 0; i < result_cars.length; i++) {
			if (i < cars.length) {
				for (int j = 0; j < 5; j++) {
					result_cars[i][j] = cars[i][j];
				}
			} else {// �µĳ���
				for (int j = 0; j < 5; j++) {
					result_cars[i][j] = new_car[j];
				}

				mainCar = i;
				CanvasControl.usingCar = i;
			}
		}

		cars = result_cars;
	}

	/**
	 * �ϴ�һ������ս�ļ�¼
	 * 
	 * @param duelResult
	 *            ��ս���
	 * @param hisId
	 *            ��ս�ҵ�id
	 */
	public void upLoadMsg(int duelResult, String hisId) {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(duelResult);// ��ս���
		sb_param.append(",");
		sb_param.append(hisId);// iptvid
		sb_param.append(",");
		sb_param.append(CanvasControl.missionPassed);// ��ͨ���Ĺؿ�
		sb_param.append(",");
		sb_param.append(C.enUseName_base64(canvasControl.me.nickName));// �ǳ�
		sb_param.append(",");
		sb_param.append(canvasControl.me.level);// �ȼ�
		sb_param.append(",");
		sb_param.append(canvasControl.me.expeirence);// ����
		sb_param.append(",");
		sb_param.append(canvasControl.me.strength);// ʵ��
		sb_param.append(",");
		sb_param.append(canvasControl.me.duelTimes);// ʣ����ս����
		sb_param.append(",");
		sb_param.append(canvasControl.me.mainCar);// ��ս����
		sb_param.append(",");

		for (int i = 0; i < canvasControl.me.cars.length; i++) {// ���еĳ�����Ϣ
			for (int j = 0; j < canvasControl.me.cars[i].length; j++) {
				sb_param.append(canvasControl.me.cars[i][j]);
				sb_param.append(",");
			}
		}
		String msg = sb_param.toString();
		System.out.println(msg);
		new ServerIptv(canvasControl).addMsg(userid, msg);
	}

	/**
	 * �����ҵĻ���
	 */
	public void saveScore() {
		ServerIptv si = new ServerIptv(canvasControl);
		si.doSendScore(userid, C.enUseName_base64(nickName), expeirence, 4);
		si.doSendScore(userid, C.enUseName_base64(nickName), strength, 5);
	}

	/**
	 * ���´浵
	 */
	public void updateParam() {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(missionPassed);// ��ͨ���Ĺؿ�
		sb_param.append(",");
		sb_param.append(C.enUseName_base64(nickName));// �ǳ�
		sb_param.append(",");
		sb_param.append(level);// �ȼ�
		sb_param.append(",");
		sb_param.append(expeirence);// ����
		sb_param.append(",");
		sb_param.append(strength);// ʵ��
		sb_param.append(",");
		sb_param.append(duelTimes);// ʣ����ս����
		sb_param.append(",");
		sb_param.append(mainCar);// ��ս����
		sb_param.append(",");

		for (int i = 0; i < cars.length; i++) {// ���еĳ�����Ϣ
			for (int j = 0; j < cars[i].length; j++) {
				sb_param.append(cars[i][j]);
				sb_param.append(",");
			}
		}
		String param = sb_param.toString();
		System.out.println(param);
		new ServerIptv(canvasControl).doSendUserInfo(userid, param);
	}
	
	/**
	 * ��ȡ�ĳ������ҵĳ�������±�ֵ
	 * @param carIndex �����±�ֵ
	 * @return ���ҵĳ�������±�ֵ û�з���-1
	 */
	public int getMyCarIndex(int carIndex) {
		for (int i = 0; i < cars.length; i++) {
			if(cars[i][0] == carIndex) {
				return i;
			}
		}
		return -1;
	}
}
