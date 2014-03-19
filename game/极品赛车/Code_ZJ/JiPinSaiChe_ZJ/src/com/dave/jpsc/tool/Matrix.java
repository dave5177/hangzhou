package com.dave.jpsc.tool;

/**
 * @author Dave
 * 矩阵
 */
public class Matrix {
	
	private int row;
	private int col;
	
	public float[][] value;
	
	public Matrix(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		value = new float[row][col];
	}

	public Matrix(float[][] value) {
		super();
		this.row = value.length;
		this.col = value[0].length;
		this.value = value;
	}
	
	/**
	 * 用定值创建一个3X3矩阵
	 * @param v_00
	 * @param v_01
	 * @param v_02
	 * @param v_10
	 * @param v_11
	 * @param v_12
	 * @param v_20
	 * @param v_21
	 * @param v_22
	 */
	public Matrix(float v_00, float v_01, float v_02, 
			float v_10, float v_11, float v_12, 
			float v_20, float v_21, float v_22 ) {
		row = 3;
		col = 3;
		value = new float[3][3];
		value[0][0] = v_00;
		value[0][1] = v_01;
		value[0][2] = v_02;
		value[1][0] = v_10;
		value[1][1] = v_11;
		value[1][2] = v_12;
		value[2][0] = v_20;
		value[2][1] = v_21;
		value[1][2] = v_22;
	}

	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public float value(int row, int col) {
		return value[row][col];
	}
	
}
