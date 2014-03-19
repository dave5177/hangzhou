package com.dave.jpsc.tool;

/**
 * @author Dave ��ά����
 */
public class Vector2 {

	public float x;
	public float y;

	/**
	 * ��ά����
	 * 
	 * @param x
	 *            x������
	 * @param y
	 *            y������
	 */
	public Vector2(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * ���������ĵ�����õ�һ������ֵ
	 * 
	 * @param v
	 *            �ڶ�������
	 * @return �õ��ı���ֵ
	 */
	public float dotProduct(Vector2 v) {
		return x * v.x + y * v.y;
	}
	
	/**
	 * �����ı���
	 * @param multValue
	 * @return
	 */
	public Vector2 mult(float multValue) {
		return new Vector2(multValue * x, multValue * y);
	}
	
	/**
	 * ������ת�������մ���ľ���
	 * @param matrix ��λ����
	 * @return
	 * @throws Exception
	 */
	public Vector2 transform(Matrix matrix) throws Exception {
		if (matrix.getRow() != 3) {
			throw (new Exception("�����ʽ��ƥ��"));
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
	 * �����ļ���
	 * @param tar ��ȥ��Ŀ������
	 * @return
	 */
	public Vector2 minus(Vector2 tar) {
		return new Vector2(x - tar.x, y - tar.y);
	}
	
	/**
	 * �����ļӷ�
	 * @param tar 
	 * @return
	 */
	public Vector2 add(Vector2 tar) {
		return new Vector2(x + tar.x, y + tar.y);
	}
	
	/**
	 * �����ĸ���
	 * @return
	 */
	public Vector2 negative() {
		return new Vector2(-x, -y);
	}
	
}
