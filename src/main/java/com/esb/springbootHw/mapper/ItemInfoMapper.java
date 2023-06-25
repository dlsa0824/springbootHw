package com.esb.springbootHw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.esb.springbootHw.entity.ItemInfo;

public class ItemInfoMapper implements RowMapper<ItemInfo> {

	@Override
	public ItemInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setItem(rs.getString("item"));
		itemInfo.setPrice(rs.getInt("price"));
		itemInfo.setAmount(rs.getInt("amount"));
		return itemInfo;
	}
}
