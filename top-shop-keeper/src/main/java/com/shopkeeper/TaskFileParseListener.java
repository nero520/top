package com.shopkeeper;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 下午12:10
 * To change this template use File | Settings | File Templates.
 */
public interface TaskFileParseListener
{
    public void receivedData(Object object);

    public void taskFinished();

    public void taskError(String msg);
}
