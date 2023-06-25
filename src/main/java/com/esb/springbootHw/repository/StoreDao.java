package com.esb.springbootHw.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.mapper.ItemInfoMapper;

@Repository
public class StoreDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	@Autowired
//    public StoreDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
	
	public List<ItemInfo> query(String item)  {
		List<ItemInfo> itemList = jdbcTemplate.query("SELECT * FROM TB_ESB21815_ITEM_PRICE_NUMBER WHERE ITEM = ?", 
				new ItemInfoMapper(), new Object[] {item});
        return itemList;
	}
	
	public List<ItemInfo> queryAll() {
		List<ItemInfo> itemList = jdbcTemplate.query("SELECT * FROM TB_ESB21815_ITEM_PRICE_NUMBER", new ItemInfoMapper());
		return itemList;
	}
	
	public int insert(ItemInfo itemInfo) {
		int count = jdbcTemplate.update("INSERT INTO TB_ESB21815_ITEM_PRICE_NUMBER(ITEM, PRICE, AMOUNT) VALUES(?, ?, ?)",
				new Object[] {itemInfo.getItem(), itemInfo.getPrice(), itemInfo.getAmount()});
		return count;
	}
	
	public int delete(ItemInfo itemInfo) {
		int count = jdbcTemplate.update("DELETE FROM TB_ESB21815_ITEM_PRICE_NUMBER WHERE ITEM = ?", new Object[] {itemInfo.getItem()});
		return count;
	}
	
	public int[] batchInsert(List<ItemInfo> items) {
		int[] counts = jdbcTemplate.batchUpdate("INSERT INTO TB_ESB21815_ITEM_PRICE_NUMBER(ITEM, PRICE, AMOUNT) VALUES(?, ?, ?)",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, items.get(i).getItem());
						ps.setInt(2, items.get(i).getPrice());
						ps.setInt(3, items.get(i).getAmount());
					}
					public int getBatchSize() {
						return items.size();
					}
				});
		return counts;
	}
	
	public int[] batchDelete(List<ItemInfo> items) {
		int[] counts = jdbcTemplate.batchUpdate("DELETE FROM TB_ESB21815_ITEM_PRICE_NUMBER WHERE ITEM = ?",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, items.get(i).getItem());
					}
					public int getBatchSize() {
						return items.size();
					}
				});
		return counts;
	}
	
	public int[] batchUpdate(List<ItemInfo> items) {
		int[] counts = jdbcTemplate.batchUpdate("UPDATE TB_ESB21815_ITEM_PRICE_NUMBER SET AMOUNT = ? WHERE ITEM = ?",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, items.get(i).getAmount());
						ps.setString(2, items.get(i).getItem());
					}
					public int getBatchSize() {
						return items.size();
					}
				});
		return counts;
	}
}