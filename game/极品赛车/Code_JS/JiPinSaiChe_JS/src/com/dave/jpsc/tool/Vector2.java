package com.dave.jpsc.tool;

/**
 * @author Dave 二维向量
 */
public class Vector2 {

	public float x;
	public float y;

	/**
	 * 二维向量
	 * 
	 * @param x
	 *            x轴坐标
	 * @param y
	 *            y轴坐标
	 */
	public Vector2(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * 两个向量的点积。得到一个标量值
	 * 
	 * @param v
	 *            第二个向量
	 * @return 得到的标量值
	 */
	public float dotProduct(Vector2 v) {
		return x * v.x + y * v.y;
	}
	
	/**
	 * 向量的倍乘
	 * @param multValue
	 * @return
	 */
	public Vector2 mult(float multValue) {
		return new Vector2(multValue * x, multValue * y);
	}
	
	/**
	 * 向量的转换，按照传入的矩阵
	 * @param matrix 三位矩阵
	 * @return
	 * @throws Exception
	 */
	public Vector2 transform(Matrix matrix) throws Exception {
		if (matrix.getRow() != 3) {
			throw (new Exception("矩阵格式不匹配"));
		} else {
			Vector2 result_v = new Vector2(0, 0);
			result_v.x = x * matrix.value(0, 0) + y * matrix.value(1, 0)
					+ matrix.value(2, 0);
			result_v.y = x * matrix.value(0, 1) + y * matrix.value(1, 1)
					+ matrix.value(2, 1);
			return result_v;
		}
	}

	/**
	 * 向量的减法
	 * @param tar 减去的目标向量
	 * @return
	 */
	public Vector2 minus(Vector2 tar) {
		return new Vector2(x - tar.x, y - tar.y);
	}
	
	/**
	 * 向量的加法
	 * @param tar 
	 * @return
	 */
	public Vector2 add(Vector2 tar) {
		return new Vector2(x + tar.x, y + tar.y);
	}
	
	/**
	 * 向量的负数
	 * @return
	 */
	public Vector2 negative() {
		return new Vector2(-x, -y);
	}
	
}
