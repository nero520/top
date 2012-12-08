package com.shopkeeper.common;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-12-6
 * Time: 上午11:33
 */
public interface TopCometListener
{
    public void onReceiveMsg(String message);

    public void onException(Exception e);
}
