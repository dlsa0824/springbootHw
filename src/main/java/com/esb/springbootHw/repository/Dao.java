package com.esb.springbootHw.repository;

public interface Dao {
	public abstract Object query(Object object);
	
	public abstract Object insert(Object object);
	
	public abstract Object update(Object object);
	
	public abstract Object delete(Object object);
	
	public abstract Object batchInsert(Object object);
	
	public abstract Object batchUpdate(Object object);

}
