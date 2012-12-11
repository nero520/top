package com.shopkeeper.model;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 上午12:56
 */
public interface Model<T>
{
    public String getCollectionName();

	public List<T> create(Map<String, Object> data);

	public List<T> query(Map<String, Object> query);

	public List<T> update(Map<String, Object> query, Map<String, Object> data);

	public List<T> update(Map<String, Object> query, Map<String, Object> data, boolean insert);

	public List<T> delete(Map<String, Object> query);

	public long count(Map<String, Object> query);
}
