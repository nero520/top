package com.shopkeeper.exception;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-18
 * Time: 上午9:42
 * To change this template use File | Settings | File Templates.
 */
public class ModelException extends SkException
{
    public ModelException() {

    }

    public ModelException(String errorCode, String msg) {
        super(errorCode, msg);
    }

    public ModelException(String errorCode, String msg, String subCode, String subMsg) {
        super(errorCode, msg, subCode, subMsg);
    }

    public ModelException(String errorCode, String msg, String subCode, String subMsg, String topForbiddenFields) {
        super(errorCode, msg, subCode, subMsg, topForbiddenFields);
    }

}
