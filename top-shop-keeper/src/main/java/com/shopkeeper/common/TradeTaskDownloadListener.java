package com.shopkeeper.common;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 下午12:10
 */
public interface TradeTaskDownloadListener
{
    public void receivedData(Object object);

    public void taskFinished();

    public void taskError(String msg);
}
