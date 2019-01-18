package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.bean.entity.vo.GenericEntityVO;

public class Page<T,V> {

	/**
	 * 当前页数
	 */
	private int pageNum;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 每页最大查询数
	 */
	private int maxCount=10;
	
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * URL
	 */
	private String url;
	
	/**
	 * 结果集
	 */
	private List<T> pagelist;
	
	/**
	 * 实体类结果集
	 */
	private List<V> entitylist;
	
	/**
	 * 备用字段用来放一些额外信息
	 */
	private String rsvst1;
	/**
	 * 备用字段用来放一些额外信息
	 */
	private BigDecimal rsvdc1;

	/**
	 * 备用字段用来放一些额外信息
	 */
	private BigDecimal rsvdc2;
	/**
	 * 存放一些需要带出的统计
	 */
	private Object[] cells;
	
	/**
	 * 存放一些需要带出的统计 列表
	 */
	private List<Object[]> cellList;
	
	
	public String getRsvst1() {
		return rsvst1;
	}

	public void setRsvst1(String rsvst1) {
		this.rsvst1 = rsvst1;
	}
	
	public BigDecimal getRsvdc2() {
		return rsvdc2;
	}

	public void setRsvsdc2(BigDecimal rsvdc2) {
		this.rsvdc2 = rsvdc2;
	}
	
	public BigDecimal getRsvdc1() {
		return rsvdc1;
	}

	public void setRsvsdc1(BigDecimal rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<T> getPagelist() {
		return pagelist;
	}

	public void setPagelist(List<T> pagelist) {
		this.pagelist = pagelist;
	}

	public List<V> getEntitylist() {
		return entitylist;
	}

	public void setEntitylist(List<V> entitylist) {
		this.entitylist = entitylist;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "Page [pageNum=" + pageNum + ", pageCount=" + pageCount
				+ ", maxCount=" + maxCount + ", totalCount=" + totalCount
				+ ", url=" + url + ", pagelist=" + pagelist + ", entitylist="
				+ entitylist + "]";
	}

	public Object[] getCells() {
		return cells;
	}

	public void setCells(Object[] cells) {
		this.cells = cells;
	}

	public void setRsvdc1(BigDecimal rsvdc1) {
		this.rsvdc1 = rsvdc1;
	}

	public List<Object[]> getCellList() {
		return cellList;
	}

	public void setCellList(List<Object[]> cellList) {
		this.cellList = cellList;
	}

	
}
