package com.esb.springbootHw.repo.imp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.mapper.ItemInfoMapper;
import com.esb.springbootHw.repo.Dao;

@Repository
public class StoreDao implements Dao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 把它變成procedure

	@Override
	public List<ItemInfo> query(String item) {
		List<ItemInfo> itemList = jdbcTemplate.query("SELECT * FROM TB_ESB21815_ITEM_PRICE_NUMBER WHERE ITEM = ?",
				new ItemInfoMapper(), new Object[] { item });
		return itemList;
	}

	@Override
	public List<ItemInfo> queryItems(List<ItemInfo> items) {
		String inSql = String.join(",",
				items.stream().map(item -> "'" + item.getItem() + "'").collect(Collectors.toList()));
		List<ItemInfo> itemList = jdbcTemplate.query(
				String.format("SELECT * FROM TB_ESB21815_ITEM_PRICE_NUMBER WHERE ITEM IN (%s)", inSql),
				new ItemInfoMapper());
		return itemList;
	}

	@Override
	public List<ItemInfo> queryAll() {
		List<ItemInfo> itemList = jdbcTemplate.query("SELECT * FROM TB_ESB21815_ITEM_PRICE_NUMBER ORDER BY ITEM ASC",
				new ItemInfoMapper());
		return itemList;
	}

	@Override
	public int insert(ItemInfo itemInfo) {
		int count = jdbcTemplate.update("INSERT INTO TB_ESB21815_ITEM_PRICE_NUMBER(ITEM, PRICE, AMOUNT) VALUES(?, ?, ?)",
				new Object[] { itemInfo.getItem(), itemInfo.getPrice(), itemInfo.getAmount() });
		return count;
	}

	@Override
	public int delete(ItemInfo itemInfo) {
		int count = jdbcTemplate.update("DELETE FROM TB_ESB21815_ITEM_PRICE_NUMBER WHERE ITEM = ?",
				new Object[] { itemInfo.getItem() });
		return count;
	}

	@Override
	public int[] batchInsert(List<ItemInfo> items) {
		int[] counts = jdbcTemplate.batchUpdate(
				"INSERT INTO TB_ESB21815_ITEM_PRICE_NUMBER(ITEM, PRICE, AMOUNT) VALUES(?, ?, ?)",
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

	@Override
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

	@Override
	public int[] batchUpdate(List<ItemInfo> items) {
		int[] counts = jdbcTemplate.batchUpdate("UPDATE TB_ESB21815_ITEM_PRICE_NUMBER SET PRICE = ?, AMOUNT = ? WHERE ITEM = ?",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, items.get(i).getPrice());
						ps.setInt(2, items.get(i).getAmount());
						ps.setString(3, items.get(i).getItem());
					}

					public int getBatchSize() {
						return items.size();
					}
				});
		return counts;
	}
}