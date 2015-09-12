package mini.stock.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.utils.DateUtils;

public class StockHistory {

	private String stockId;

	private Date date;

	private double openPrice;

	private double maxPrice;

	private double closePrice;

	private double minPrice;

	private long volume;

	private long volumeMoney;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;

	}

	public double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getVolumeMoney() {
		return volumeMoney;
	}

	public void setVolumeMoney(long volumeMoney) {
		this.volumeMoney = volumeMoney;
	}

	@Override
	public String toString() {
		return "StockHistroy [stockId=" + stockId + ", date="
				+ DateUtils.formatDate(date, "yyyy-MM-dd") + ", openPrice="
				+ openPrice + ", maxPrice=" + maxPrice + ", closePrice="
				+ closePrice + ", minPrice=" + minPrice + ", volume=" + volume
				+ ", volumeMoney=" + volumeMoney + "]";
	}

	public String formartObject() {
		return stockId + "," + new SimpleDateFormat("yyyy-MM-dd").format(date)
				+ "," + openPrice + "," + maxPrice + "," + closePrice + ","
				+ minPrice + "," + volume + "," + volumeMoney;
	}

	public static String formartHead() {
		return "股票代码,日期,开盘价,最高价,收盘价,最低价,成交量,成交金额";
	}

}
