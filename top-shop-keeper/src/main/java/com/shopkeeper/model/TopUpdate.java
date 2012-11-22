package com.shopkeeper.model;

import com.shopkeeper.exception.TopException;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-23
 * Time: 上午12:12
 * To change this template use File | Settings | File Templates.
 */
public interface TopUpdate
{
    public void updateFromTop(String topAccessToken) throws TopException;
}
