package mini.stock.entity;

import java.io.Serializable;
import java.sql.Date;

public class StockInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4492275968684033135L;
	// stockid
	private int stockId;
	// stock名称
	private String stockName;
	// stock所属
	private String original = "unknown";
	// 最新的采集日期
	private Date recentPickDate;
	// 创建日期
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getRecentPickDate() {
		return recentPickDate;
	}

	public void setRecentPickDate(Date recentPickDate) {
		this.recentPickDate = recentPickDate;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
}
