package com.shopkeeper.exception;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-23
 * Time: 上午3:54
 * To change this template use File | Settings | File Templates.
 */
public class TopException extends Exception {
    public TopException() {

    }

    public TopException(String message) {
        super(message);
    }

    public TopException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
