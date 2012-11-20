package com.shopkeeper.exception;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午1:48
 * To change this template use File | Settings | File Templates.
 */
public class TopException extends SkException{
    public TopException() {

    }

    public TopException(String errorCode, String msg) {
        super(errorCode, msg);
    }

    public TopException(String errorCode, String msg, String subCode, String subMsg) {
        super(errorCode, msg, subCode, subMsg);
    }

    public TopException(String errorCode, String msg, String subCode, String subMsg, String topForbiddenFields) {
        super(errorCode, msg, subCode, subMsg, topForbiddenFields);
    }
}
