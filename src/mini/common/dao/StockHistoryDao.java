package mini.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import mini.common.c3p0.BaseDao;
import mini.stock.entity.StockHistory;

public class StockHistoryDao extends BaseDao {

	public int insert(StockHistory stock) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into stock_history(stock_id,business_date,open_price,close_price,max_price,min_price,volume,volume_money)"
					+ "values(?,?,?,?,?,?,?,?)";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setString(i++, stock.getStockId());
			ps.setDate(i++, new Date(stock.getBusinessDate().getTime()));
			ps.setDouble(i++, stock.getOpenPrice());
			ps.setDouble(i++, stock.getClosePrice());
			ps.setDouble(i++, stock.getMaxPrice());
			ps.setDouble(i++, stock.getMinPrice());
			ps.setLong(i++, stock.getVolume());
			ps.setLong(i++, stock.getVolumeMoney());
			return ps.executeUpdate();
		} finally {
			close(conn, ps, null);
		}
	}

	public void batchInsert(List<StockHistory> stockList) throws Exception {
		if (null == stockList || stockList.isEmpty()) {
			return;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into stock_history(stock_id,business_date,open_price,close_price,max_price,min_price,volume,volume_money)"
					+ "values(?,?,?,?,?,?,?,?)";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (StockHistory stock : stockList) {
				int i = 1;
				ps.setString(i++, stock.getStockId());
				ps.setDate(i++, new Date(stock.getBusinessDate().getTime()));
				ps.setDouble(i++, stock.getOpenPrice());
				ps.setDouble(i++, stock.getClosePrice());
				ps.setDouble(i++, stock.getMaxPrice());
				ps.setDouble(i++, stock.getMinPrice());
				ps.setLong(i++, stock.getVolume());
				ps.setLong(i++, stock.getVolumeMoney());
				ps.addBatch();
			}
			ps.executeBatch();
		} finally {
			close(conn, ps, null);
		}
	}

}
