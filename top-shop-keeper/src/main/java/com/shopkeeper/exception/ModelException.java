package com.shopkeeper.exception;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-18
 * Time: 上午9:42
 * To change this template use File | Settings | File Templates.
 */
public class ModelException extends Exception
{
    public ModelException() {

    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
