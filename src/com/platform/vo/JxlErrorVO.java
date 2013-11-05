package com.platform.vo;
/**
 * 封装了导入失败的单元格信息
 * @author mayh
 *
 */
public class JxlErrorVO {
	
	private int column;
	private int row;
	private String errorStr;
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getErrorStr() {
		return errorStr;
	}
	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}
}
