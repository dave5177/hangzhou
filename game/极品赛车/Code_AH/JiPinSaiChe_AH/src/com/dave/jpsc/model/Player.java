package com.dave.jpsc.model;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;

public class Player {

	/**
	 * 被击败
	 */
	public static final int DEFEATED = 1;

	/**
	 * 获胜
	 */
	public static final int WIN = 2;

	/**
	 * 已解锁的关卡
	 */
	public int missionPassed;

	/**
	 * 用户昵称
	 */
	public String nickName = "Momo";

	/**
	 * 等级（与所得经验正相关）
	 */
	public int level = 1;

	/**
	 * 经验值（最初100）
	 */
	public int expeirence = 100;

	/**
	 * 用户实力值(开始时为0，挑战成功会增加）
	 */
	public int strength;

	/**
	 * 剩余挑战次数
	 */
	public int duelTimes = 10;

	/**
	 * 出战的车子(在我拥有的车辆里的下标值）
	 */
	public int mainCar = 0;

	/**
	 * 我所拥有的车辆及其持久度 从左到右分别是：车辆下标值，轮胎持久度，引擎持久度，尾翼持久度，升级次数。
	 */
	public int[][] cars;

	/**
	 * 车辆的实际参数（升级后的）
	 */
	public int[][] carRealParams;

	/**
	 * 用户id
	 */
	public String userid;

	/**
	 * cavas
	 */
	private CanvasControl canvasControl;

	/**
	 * @param nickName
	 *            昵称
	 * @param level
	 *            等级
	 * @param experience
	 *            经验
	 * @param strength
	 *            实力之
	 * @param duelTimes
	 *            剩余挑战次数
	 * @param mainCar
	 *            出战车辆
	 * @param cars
	 *            拥有的所有的车
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
	 *            已通过关卡
	 * @param level
	 *            等级
	 * @param experience
	 *            经验
	 * @param strength
	 *            实力之
	 * @param duelTimes
	 *            剩余挑战次数
	 * @param mainCar
	 *            出战车辆
	 * @param cars
	 *            拥有的所有的车
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
	 * 计算所有的车的实际参数
	 */
	public void computCarParam() {
		carRealParams = new int[cars.length][4];
		for (int i = 0; i < carRealParams.length; i++) {
			carRealParams[i][0] = CanvasControl.carProperty[cars[i][0]][0]
					+ cars[i][4] * 20;// 速度
			if (carRealParams[i][0] > CanvasControl.carMaxPower[cars[i][0]][0])
				carRealParams[i][0] = CanvasControl.carMaxPower[cars[i][0]][0];

			carRealParams[i][1] = CanvasControl.carProperty[cars[i][0]][1]
					+ cars[i][4] * 10;// 加速度
			if (carRealParams[i][1] > CanvasControl.carMaxPower[cars[i][0]][1])
				carRealParams[i][1] = CanvasControl.carMaxPower[cars[i][0]][1];

			if(CanvasControl.DEBUG) {
				System.out.println("升级后速度：" + carRealParams[i][0]); 
				System.out.println("升级后加速度：" + carRealParams[i][1]); 
			}
			carRealParams[i][2] = CanvasControl.carProperty[cars[i][0]][2];
			carRealParams[i][3] = CanvasControl.carProperty[cars[i][0]][3];
		}
	}

	/**
	 * 买了一辆车添加
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
			} else {// 新的车辆
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
	 * 上传一条被挑战的纪录
	 * 
	 * @param duelResult
	 *            挑战结果
	 * @param hisId
	 *            挑战我的id
	 */
	public void upLoadMsg(int duelResult, String hisId) {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(duelResult);// 挑战结果
		sb_param.append(",");
		sb_param.append(hisId);// iptvid
		sb_param.append(",");
		sb_param.append(CanvasControl.missionPassed);// 已通过的关卡
		sb_param.append(",");
		sb_param.append(C.enUseName_base64(canvasControl.me.nickName));// 昵称
		sb_param.append(",");
		sb_param.append(canvasControl.me.level);// 等级
		sb_param.append(",");
		sb_param.append(canvasControl.me.expeirence);// 经验
		sb_param.append(",");
		sb_param.append(canvasControl.me.strength);// 实力
		sb_param.append(",");
		sb_param.append(canvasControl.me.duelTimes);// 剩余挑战次数
		sb_param.append(",");
		sb_param.append(canvasControl.me.mainCar);// 主战车辆
		sb_param.append(",");

		for (int i = 0; i < canvasControl.me.cars.length; i++) {// 持有的车辆信息
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
	 * 保存我的积分
	 */
	public void saveScore() {
		ServerIptv si = new ServerIptv(canvasControl);
		si.doSendScore(userid, C.enUseName_base64(nickName), expeirence, 4);
		si.doSendScore(userid, C.enUseName_base64(nickName), strength, 5);
	}

	/**
	 * 更新存档
	 */
	public void updateParam() {
		StringBuffer sb_param = new StringBuffer();
		sb_param.append(missionPassed);// 已通过的关卡
		sb_param.append(",");
		sb_param.append(C.enUseName_base64(nickName));// 昵称
		sb_param.append(",");
		sb_param.append(level);// 等级
		sb_param.append(",");
		sb_param.append(expeirence);// 经验
		sb_param.append(",");
		sb_param.append(strength);// 实力
		sb_param.append(",");
		sb_param.append(duelTimes);// 剩余挑战次数
		sb_param.append(",");
		sb_param.append(mainCar);// 主战车辆
		sb_param.append(",");

		for (int i = 0; i < cars.length; i++) {// 持有的车辆信息
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
	 * 获取的车辆在我的车辆里的下标值
	 * @param carIndex 车辆下标值
	 * @return 在我的车辆里的下标值 没有返回-1
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
