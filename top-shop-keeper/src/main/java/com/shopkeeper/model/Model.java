package com.shopkeeper.model;

import com.mongodb.DBObject;
import com.shopkeeper.exception.ModelException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 上午12:56
 * To change this template use File | Settings | File Templates.
 */
public interface Model
{
    public void updateFromTop() throws ModelException;

    public String getCollectionName();
}
