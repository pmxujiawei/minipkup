package mini.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mini.common.c3p0.BaseDao;
import mini.stock.entity.StockInfo;

public class StockInfoDao extends BaseDao {

	public int insert(StockInfo stock) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into stock_info(stock_id,stock_name,original)values(?,?,?)";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, stock.getStockId());
			ps.setString(i++, stock.getStockName());
			ps.setString(i++, stock.getOriginal());
			return ps.executeUpdate();
		} finally {
			close(conn, ps, null);
		}
	}

	public int updateStockNameById(StockInfo stock) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "update stock_info set  stock_name=? where stock_id=?";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, stock.getStockId());
			ps.setDate(i++, stock.getRecentPickDate());
			return ps.executeUpdate();
		} finally {
			close(conn, ps, null);
		}
	}

	public int updatePickDateById(StockInfo stock) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "update stock_info set  recent_pick_date=? where stock_id=?";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, stock.getStockId());
			ps.setDate(i++, stock.getRecentPickDate());
			return ps.executeUpdate();
		} finally {
			close(conn, ps, null);
		}
	}

	public StockInfo getStockById(int stockId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from stock_info where stock_id=?";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, stockId);
			rs = ps.executeQuery();
			if (rs.next()) {
				StockInfo stock = new StockInfo();
				stock.setStockId(stockId);
				stock.setStockName(rs.getString("STOCK_NAME"));
				stock.setOriginal(rs.getString("ORIGINAL"));
				stock.setRecentPickDate(rs.getDate("RECENT_PICK_DATE"));
				stock.setCreateDate(rs.getDate("CREATE_DATE"));
				return stock;
			}
			return null;
		} finally {
			close(conn, ps, rs);
		}
	}
}
