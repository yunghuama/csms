package com.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.platform.vo.JxlErrorVO;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class JXLUtil {
	
	private static WritableFont FONT_14_BOLD = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD);
	private static WritableFont FONT_10_BOLD = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
	private static WritableFont FONT_10 = new WritableFont(WritableFont.TIMES, 10);
	private static NumberFormat NUMBER_FORMAT = new NumberFormat("0.00");
	private static DateFormat DATE_FORMAT = new DateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat DATE_FORMAT_2 = new DateFormat("yyyy-MM-dd");
	private static WritableCellFormat WCF_14_BOLD = null;
	private static WritableCellFormat BOLD_14_TITLE_BACK = null;
	private static WritableCellFormat BOLD_10_FIELD = null;
	private static WritableCellFormat CENTER_10_VALUE = null;
	private static WritableCellFormat TOP_10_VALUE = null;
	private static WritableCellFormat NORMAL_10_VALUE = null;
	private static WritableCellFormat RIGHT_10_VALUE = null;
	private static WritableCellFormat DATE_10_VALUE = null;
	private static WritableCellFormat DATE_10_VALUE_2 = null;
	
	private static WritableCellFormat dataReport_reportTitle = null;
	private static WritableCellFormat dataReport_reportMessage = null;
	private static WritableCellFormat dataReport_value = null;
	private static WritableCellFormat dataReport_draw_Title = null;
	
	private static List errorList = null;
	
	public static WritableCellFormat BOLD_14_TITLE() throws WriteException {
		if(WCF_14_BOLD == null) {
			WCF_14_BOLD = new WritableCellFormat(FONT_14_BOLD);
			WCF_14_BOLD.setAlignment(Alignment.CENTRE);
			WCF_14_BOLD.setVerticalAlignment(VerticalAlignment.CENTRE);
			WCF_14_BOLD.setBorder(Border.ALL, BorderLineStyle.THIN);
		}
		return WCF_14_BOLD;
	}
	
	public static WritableCellFormat BOLD_14_TITLE_BACK() throws WriteException {
		if(BOLD_14_TITLE_BACK == null) {
			BOLD_14_TITLE_BACK = new WritableCellFormat(FONT_14_BOLD);
			BOLD_14_TITLE_BACK.setAlignment(Alignment.CENTRE);
			BOLD_14_TITLE_BACK.setVerticalAlignment(VerticalAlignment.CENTRE);
			BOLD_14_TITLE_BACK.setBorder(Border.ALL, BorderLineStyle.THIN);
			BOLD_14_TITLE_BACK.setBackground(Colour.TAN);
		}
		return BOLD_14_TITLE_BACK;
	}
	
	public static WritableCellFormat BOLD_10_FIELD() throws WriteException {
		if(BOLD_10_FIELD == null) {
			BOLD_10_FIELD = new WritableCellFormat(FONT_10_BOLD);
			BOLD_10_FIELD.setAlignment(Alignment.CENTRE);
			BOLD_10_FIELD.setVerticalAlignment(VerticalAlignment.CENTRE);
			BOLD_10_FIELD.setBorder(Border.ALL, BorderLineStyle.THIN);
			//BOLD_10_FIELD.setBackground(Colour.GREY_25_PERCENT);
			BOLD_10_FIELD.setBackground(Colour.PALE_BLUE);
		}
		return BOLD_10_FIELD;
	}
	
	public static WritableCellFormat CENTER_10_VALUE() throws WriteException {
		if(CENTER_10_VALUE == null) {
			CENTER_10_VALUE = new WritableCellFormat(FONT_10);
			CENTER_10_VALUE.setAlignment(Alignment.CENTRE);
			CENTER_10_VALUE.setVerticalAlignment(VerticalAlignment.CENTRE);
			CENTER_10_VALUE.setBorder(Border.ALL, BorderLineStyle.THIN);
			CENTER_10_VALUE.setWrap(true);
		}
		return CENTER_10_VALUE;
	}
	
	public static WritableCellFormat TOP_10_VALUE() throws WriteException {
		if(TOP_10_VALUE == null) {
			TOP_10_VALUE = new WritableCellFormat(FONT_10);
			TOP_10_VALUE.setAlignment(Alignment.CENTRE);
			TOP_10_VALUE.setVerticalAlignment(VerticalAlignment.TOP);
			TOP_10_VALUE.setBorder(Border.ALL, BorderLineStyle.THIN);
			TOP_10_VALUE.setWrap(true);
		}
		return TOP_10_VALUE;
	}
	
	public static WritableCellFormat NORMAL_10_VALUE() throws WriteException {
		if(NORMAL_10_VALUE == null) {
			NORMAL_10_VALUE = new WritableCellFormat(FONT_10);
			NORMAL_10_VALUE.setVerticalAlignment(VerticalAlignment.CENTRE);
			NORMAL_10_VALUE.setBorder(Border.ALL, BorderLineStyle.THIN);
			NORMAL_10_VALUE.setWrap(true);
		}
		return NORMAL_10_VALUE;
	}
	
	public static WritableCellFormat RIGHT_10_VALUE() throws WriteException {
		if(RIGHT_10_VALUE == null) {
			RIGHT_10_VALUE = new WritableCellFormat(FONT_10, NUMBER_FORMAT);
			RIGHT_10_VALUE.setAlignment(Alignment.RIGHT);
			RIGHT_10_VALUE.setVerticalAlignment(VerticalAlignment.CENTRE);
			RIGHT_10_VALUE.setBorder(Border.ALL, BorderLineStyle.THIN);
			RIGHT_10_VALUE.setWrap(true);
		}
		return RIGHT_10_VALUE;
	}
	
	public static WritableCellFormat DATE_10_VALUE() throws WriteException {
		if(DATE_10_VALUE == null) {
			DATE_10_VALUE = new WritableCellFormat(FONT_10, DATE_FORMAT);
			DATE_10_VALUE.setAlignment(Alignment.CENTRE);
			DATE_10_VALUE.setVerticalAlignment(VerticalAlignment.CENTRE);
			DATE_10_VALUE.setBorder(Border.ALL, BorderLineStyle.THIN);
			DATE_10_VALUE.setWrap(true);
		}
		return DATE_10_VALUE;
	}
	
	public static WritableCellFormat DATE_10_VALUE_2() throws WriteException {
		if(DATE_10_VALUE_2 == null) {
			DATE_10_VALUE_2 = new WritableCellFormat(FONT_10, DATE_FORMAT_2);
			DATE_10_VALUE_2.setAlignment(Alignment.CENTRE);
			DATE_10_VALUE_2.setVerticalAlignment(VerticalAlignment.CENTRE);
			DATE_10_VALUE_2.setBorder(Border.ALL, BorderLineStyle.THIN);
			DATE_10_VALUE_2.setWrap(true);
		}
		return DATE_10_VALUE_2;
	}
	
	public static WritableCellFormat dataReport_reportTitle() throws WriteException {
		if(dataReport_reportTitle == null) {
			dataReport_reportTitle = new WritableCellFormat(FONT_14_BOLD);
			dataReport_reportTitle.setAlignment(Alignment.CENTRE);
			dataReport_reportTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
			dataReport_reportTitle.setBorder(Border.TOP, BorderLineStyle.THIN);
			dataReport_reportTitle.setBorder(Border.BOTTOM, BorderLineStyle.NONE);
			dataReport_reportTitle.setBorder(Border.LEFT, BorderLineStyle.THIN);
			dataReport_reportTitle.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		}
		return dataReport_reportTitle;
	}
	
	public static WritableCellFormat dataReport_reportMessage() throws WriteException {
		if(dataReport_reportMessage == null) {
			dataReport_reportMessage = new WritableCellFormat(FONT_10);
			dataReport_reportMessage.setAlignment(Alignment.RIGHT);
			dataReport_reportMessage.setVerticalAlignment(VerticalAlignment.CENTRE);
			dataReport_reportMessage.setBorder(Border.LEFT, BorderLineStyle.THIN);
			dataReport_reportMessage.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			dataReport_reportMessage.setBorder(Border.TOP, BorderLineStyle.NONE);
			dataReport_reportMessage.setBorder(Border.BOTTOM, BorderLineStyle.NONE);
		}
		return dataReport_reportMessage;
	}
	
	public static WritableCellFormat dataReport_value() throws WriteException {
		if(dataReport_value == null) {
			dataReport_value = new WritableCellFormat(FONT_10);
			dataReport_value.setVerticalAlignment(VerticalAlignment.CENTRE);
			dataReport_value.setAlignment(Alignment.CENTRE);
			dataReport_value.setBorder(Border.ALL, BorderLineStyle.THIN);
			dataReport_value.setWrap(true);
		}
		return dataReport_value;
	}
	
	public static WritableCellFormat dataReport_draw_Title(String flag) throws WriteException {
		dataReport_draw_Title = new WritableCellFormat(FONT_10_BOLD);
		if("title2".equals(flag)){
			dataReport_draw_Title.setAlignment(Alignment.RIGHT);
			dataReport_draw_Title.setBorder(Border.LEFT, BorderLineStyle.THIN);
			dataReport_draw_Title.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			dataReport_draw_Title.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			dataReport_draw_Title.setBorder(Border.TOP, BorderLineStyle.NONE);
		}
				
		else{
			dataReport_draw_Title.setAlignment(Alignment.CENTRE);
			dataReport_draw_Title.setBorder(Border.LEFT, BorderLineStyle.THIN);
			dataReport_draw_Title.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			dataReport_draw_Title.setBorder(Border.BOTTOM, BorderLineStyle.NONE);
			dataReport_draw_Title.setBorder(Border.TOP, BorderLineStyle.THIN);
		}
		dataReport_draw_Title.setVerticalAlignment(VerticalAlignment.CENTRE);
		//BOLD_10_FIELD.setBackground(Colour.GREY_25_PERCENT);
		dataReport_draw_Title.setBackground(Colour.PALE_BLUE);
		
		return dataReport_draw_Title;
	}
	
  /**  
   *   
   * @param col  链接所在列  
   * @param row  链接所在行  
   * @param sheet要将链接添加到哪一个工作表  
   * @param destSheet 要连接到哪个工作表  
   * @param linkName  链接名称  
   * @param format  链接样式
   */  
  public void addHyperlink(int col,int row,WritableSheet sheet,WritableSheet destSheet,String linkName,WritableCellFormat format){   
      try {   
          WritableHyperlink link = new WritableHyperlink(col,row,linkName,destSheet,0,0);
          sheet.addHyperlink(link);   
      } catch (RowsExceededException e) {      
          e.printStackTrace();   
      } catch (WriteException e) {   
          e.printStackTrace();   
      }   
  } 
  
//-----------------------------------------我是分隔线，线下为导入部分，线上为导出部分-------------------------------------------
	/**
	 * 获得数值类型 
	 * @param cell
	 * @return
	 */
	public static Double NumberValue(Cell cell){
		try{
		    //如果是数字类型则直接返回
			if(CellType.NUMBER.equals(cell.getType())){
				return toNumberCell(cell).getValue();
			}else if(CellType.LABEL.equals(cell.getType())){
				//如果是字符串，则尝试进行相关转换
				return Double.parseDouble(toLabelCell(cell).getString().trim());
			}else {
				//其它类型则返回null
				return null;
			}
		}catch(Exception e){
			JxlErrorVO jvo = new JxlErrorVO();
			jvo.setColumn(cell.getColumn());
			jvo.setRow(cell.getRow());
			jvo.setErrorStr("单元格内容为非数值类型！！");
			errorList.add(jvo);
			return null;
		}
		
	}
	
	/**
	 * 获得字符串类型的值
	 * @param cell
	 * @return
	 */
	public static String StringValue(Cell cell){
		try{
			if(CellType.EMPTY.equals(cell.getType())){
				return null;
			}else if(CellType.LABEL.equals(cell.getType())){
				return toLabelCell(cell).getString().trim();
			}else if(CellType.NUMBER.equals(cell.getType())){
				return toNumberCell(cell).getValue()+"";
			}else if(CellType.DATE.equals(cell.getType())){
				return toDateCell(cell).toString();
			}else {
				return "";
			}
		}catch(Exception e){
			JxlErrorVO jvo = new JxlErrorVO();
			jvo.setColumn(cell.getColumn());
			jvo.setRow(cell.getRow());
			jvo.setErrorStr("单元格内容为非文本类型！！");
			errorList.add(jvo);
			return null;
		}
		
	}
	
	/**
	 * 获得日期类型的值
	 * @param cell
	 * @return
	 * @throws ParseException 
	 */
	public static Date DateValue(Cell cell) throws ParseException{
		try{
			if(CellType.LABEL.equals(cell.getType())){
				LabelCell labelCell = toLabelCell(cell);
				return formatToDate(labelCell.getString(),"yyyy-MM-dd");
			}else if(CellType.DATE.equals(cell.getType())){
				return toDateCell(cell).getDate();
			}else {
				return null;
			}
		}catch(Exception e){
			JxlErrorVO jvo = new JxlErrorVO();
			jvo.setColumn(cell.getColumn());
			jvo.setRow(cell.getRow());
			jvo.setErrorStr("单元格内容为非日期类型！！");
			errorList.add(jvo);
			return null;
		}
		
	}
	
	/**
	 * 转换为数值类型
	 * @param cell
	 * @return
	 */
	public static NumberCell toNumberCell(Cell cell){
		return (NumberCell)cell;
	}
	
	/**
	 * 转换为文本类型
	 * @param cell
	 * @return
	 */
	public static LabelCell toLabelCell(Cell cell){
		return (LabelCell)cell;
	}
	
	/**
	 * 转换为日期类型
	 */
	public static DateCell toDateCell(Cell cell){
		return (DateCell)cell;
	}
	
	/**
	 * 格式化日期类型
	 */
	public static String dateFormat(Date date,String reg){
		SimpleDateFormat sdf = new SimpleDateFormat(reg);
		return sdf.format(date);
	}
	
	/**
	 * 把字符串转换为日期
	 * @throws ParseException 
	 */
	public static Date formatToDate(String date,String reg) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(reg);
		return sdf.parse(date);
	}

	
	public List getErrorList() {
		return errorList;
	}

	public void setErrorList(List errorList) {
		this.errorList = errorList;
	}
	
  
}
