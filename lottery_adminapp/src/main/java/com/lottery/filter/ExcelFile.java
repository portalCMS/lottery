/**
 * ProjectName: PropertyMG
 * Date:        2011-2-18
 * Author:      ijse
 */
package com.lottery.filter;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 

import jxl.CellView;
import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
 
/**
 * 读写Excel文件工具类
 * <p>
 * 
 * @author ijse
 * 
 */
public class ExcelFile {
	private Integer ColWidth;
    private String Title;
    private List<String> Header;
    private List<List<String>> Data;
    private List<List<Object>> objData;
 
    private WritableWorkbook workbook;
    private WritableSheet wsheet;
 
    private HeaderFooter pageHeader;
    private HeaderFooter pageFooter;
    private WritableFont titleFont;
    private WritableCellFormat titleFormat;
    private WritableFont headerFont;
    private WritableCellFormat headerFormat;
 
    {
        // 设置打印页头
        pageHeader = new HeaderFooter();
        pageHeader.getLeft().appendDate();
        pageHeader.getLeft().append(" ");
        pageHeader.getLeft().appendTime();
        pageHeader.getCentre().appendWorkSheetName();
        pageHeader.getRight().append("彩王星报表");
 
        // 设置打印页脚
        pageFooter = new HeaderFooter();
        pageFooter.getCentre().appendPageNumber();
        pageFooter.getCentre().append("/");
        pageFooter.getCentre().appendTotalPages();
 
        // 设置标题
        titleFont = new WritableFont(WritableFont.createFont("黑体"), 32,
                WritableFont.BOLD);
        titleFormat = new WritableCellFormat(titleFont);
        try {
            titleFormat.setAlignment(Alignment.CENTRE);
            titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (WriteException e) {
            e.printStackTrace();
        }
 
        // 创建表头样式
        headerFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD);
        headerFormat = new WritableCellFormat(headerFont);
        try {
            headerFormat.setWrap(true);
        } catch (WriteException e) {
            e.printStackTrace();
        }
 
    } // init block
 
    public ExcelFile() {
    };
 
    public ExcelFile(String title, List<String> header, List<List<String>> data,Integer cw) {
        super();
        Title = title;
        Header = header;
        Data = data;
    }
    
    public ExcelFile(String title, List<String> header, List<List<Object>> objData,boolean isObj) {
        super();
        Title = title;
        Header = header;
        this.objData = objData;
    }
    
    public ExcelFile(String title, List<String> header, List<List<String>> data) {
        super();
        Title = title;
        Header = header;
        Data = data;
        ColWidth = 30;
    }
 
 
    public boolean save(File fileName) throws IOException {
        // 创建WorkBook 和 Sheet
        workbook = Workbook.createWorkbook(fileName);
        wsheet = workbook.createSheet(this.Title, workbook.getNumberOfSheets());
        // 设置工作表全局设定
        SheetSettings sheetSettings = wsheet.getSettings();
        sheetSettings.setHeader(this.pageHeader);
        sheetSettings.setFooter(this.pageFooter);
        // sheetSettings.setDefaultRowHeight(500);
        sheetSettings.setFitToPages(true);
        sheetSettings.setPrintHeaders(false);
        sheetSettings.setDisplayZeroValues(true);
        sheetSettings.setPrintGridLines(true);
        sheetSettings.setFitWidth(1);
        try {
            // 标题文字
            Label nc1 = new Label(0, 0, this.Title, titleFormat);
            // 合并单元格
            wsheet.mergeCells(0, 0, this.Header.size() - 1, 0);
 
            // 插入标题单元格
            wsheet.addCell(nc1);
            
            // 写入表头
            for (int i = 0; i < this.Header.size(); ++i) {
                Label nc = new Label(i, 1, this.Header.get(i), headerFormat);
                wsheet.addCell(nc);
            }
            // 加入数据
            for (int i = 0; i < this.Data.size(); ++i) {
                for (int j = 0; j < this.Data.get(i).size(); ++j) {
                    Label nc = new Label(j, i + 2, this.Data.get(i).get(j));
                    wsheet.addCell(nc);
                }
            }
            //设置列宽为40个字符
            int cols = wsheet.getColumns();
        	for(int j=0;j<cols;j++){
        		wsheet.setColumnView(j, ColWidth);
        	}
            workbook.write();
            workbook.close();
        } catch (RowsExceededException e) {
            e.printStackTrace();
            return false;
        } catch (WriteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    public boolean objSave(File fileName) throws IOException {
        // 创建WorkBook 和 Sheet
        workbook = Workbook.createWorkbook(fileName);
        wsheet = workbook.createSheet(this.Title, workbook.getNumberOfSheets());
        // 设置工作表全局设定
        SheetSettings sheetSettings = wsheet.getSettings();
        sheetSettings.setHeader(this.pageHeader);
        sheetSettings.setFooter(this.pageFooter);
        // sheetSettings.setDefaultRowHeight(500);
        sheetSettings.setFitToPages(true);
        sheetSettings.setPrintHeaders(false);
        sheetSettings.setDisplayZeroValues(true);
        sheetSettings.setPrintGridLines(true);
        sheetSettings.setFitWidth(1);
        try {
            // 标题文字
            Label nc1 = new Label(0, 0, this.Title, titleFormat);
            // 合并单元格
            wsheet.mergeCells(0, 0, this.Header.size() - 1, 0);
 
            // 插入标题单元格
            wsheet.addCell(nc1);
            // 写入表头
            for (int i = 0; i < this.Header.size(); ++i) {
                Label nc = new Label(i, 1, this.Header.get(i), headerFormat);
                wsheet.addCell(nc);
            }
            // 加入数据
            for (int i = 0; i < this.objData.size(); ++i) {
                for (int j = 0; j < this.objData.get(i).size(); ++j) {
                    Label nc = new Label(j, i + 2, this.objData.get(i).get(j).toString());
                    wsheet.addCell(nc);
                }
            }
            int cols = wsheet.getColumns();
        	for(int j=0;j<cols;j++){
        		wsheet.setColumnView(j, 25);
        	}
            workbook.write();
            workbook.close();
        } catch (RowsExceededException e) {
            e.printStackTrace();
            return false;
        } catch (WriteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) throws BiffException, IOException,
            RowsExceededException, WriteException {
 
        // 默认测试数据
        File file = new File("d:/test2.xls");
        String title = "2014年10月用户数据统计";
        List<String> header = new ArrayList<String>();
        for (int i = 0; i < 10; ++i) {
            header.add("列头" + i);
        }
        List<List<String>> data = new ArrayList<List<String>>();
        for (int i = 0; i < 10; ++i) {
            List<String> inlist = new ArrayList<String>();
            for (int j = 0; j < 10; ++j) {
                inlist.add("数据(" + i + "," + j + ")");
            }
            data.add(inlist);
        }
 
        ExcelFile ef = new ExcelFile(title, header, data);
        try {
            ef.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    /**
     * @return the title
     */
    public String getTitle() {
        return Title;
    }
 
    /**
     * @return the header
     */
    public List<String> getHeader() {
        return Header;
    }
 
    /**
     * @return the data
     */
    public List<List<String>> getData() {
        return Data;
    }
 
    /**
     * @return the workbook
     */
    public WritableWorkbook getWorkbook() {
        return workbook;
    }
 
    /**
     * @return the wsheet
     */
    public WritableSheet getWsheet() {
        return wsheet;
    }
 
    /**
     * @return the pageHeader
     */
    public HeaderFooter getPageHeader() {
        return pageHeader;
    }
 
    /**
     * @return the pageFooter
     */
    public HeaderFooter getPageFooter() {
        return pageFooter;
    }
 
    /**
     * @return the titleFont
     */
    public WritableFont getTitleFont() {
        return titleFont;
    }
 
    /**
     * @return the titleFormat
     */
    public WritableCellFormat getTitleFormat() {
        return titleFormat;
    }
 
    /**
     * @return the headerFont
     */
    public WritableFont getHeaderFont() {
        return headerFont;
    }
 
    /**
     * @return the headerFormat
     */
    public WritableCellFormat getHeaderFormat() {
        return headerFormat;
    }
 
    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        Title = title;
    }
 
    /**
     * @param header
     *            the header to set
     */
    public void setHeader(List<String> header) {
        Header = header;
    }
 
    /**
     * @param data
     *            the data to set
     */
    public void setData(List<List<String>> data) {
        Data = data;
    }
 
    /**
     * @param workbook
     *            the workbook to set
     */
    public void setWorkbook(WritableWorkbook workbook) {
        this.workbook = workbook;
    }
 
    /**
     * @param wsheet
     *            the wsheet to set
     */
    public void setWsheet(WritableSheet wsheet) {
        this.wsheet = wsheet;
    }
 
    /**
     * @param pageHeader
     *            the pageHeader to set
     */
    public void setPageHeader(HeaderFooter pageHeader) {
        this.pageHeader = pageHeader;
    }
 
    /**
     * @param pageFooter
     *            the pageFooter to set
     */
    public void setPageFooter(HeaderFooter pageFooter) {
        this.pageFooter = pageFooter;
    }
 
    /**
     * @param titleFont
     *            the titleFont to set
     */
    public void setTitleFont(WritableFont titleFont) {
        this.titleFont = titleFont;
    }
 
    /**
     * @param titleFormat
     *            the titleFormat to set
     */
    public void setTitleFormat(WritableCellFormat titleFormat) {
        this.titleFormat = titleFormat;
    }
 
    /**
     * @param headerFont
     *            the headerFont to set
     */
    public void setHeaderFont(WritableFont headerFont) {
        this.headerFont = headerFont;
    }
 
    /**
     * @param headerFormat
     *            the headerFormat to set
     */
    public void setHeaderFormat(WritableCellFormat headerFormat) {
        this.headerFormat = headerFormat;
    }

	public Integer getColWidth() {
		return ColWidth;
	}

	public void setColWidth(Integer colWidth) {
		ColWidth = colWidth;
	}
}