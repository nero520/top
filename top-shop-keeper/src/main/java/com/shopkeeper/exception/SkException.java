package com.shopkeeper.exception;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-23
 * Time: 上午3:54
 */
public class SkException extends Throwable {
    public SkException() {

    }

    public SkException(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public SkException(String errorCode, String msg, String subCode, String subMsg) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.subCode = subCode;
        this.subMsg = subMsg;
    }

    public SkException(String errorCode, String msg, String subCode, String subMsg, String topForbiddenFields) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.subCode = subCode;
        this.subMsg = subMsg;
        this.topForbiddenFields = topForbiddenFields;
    }

    private String errorCode;
    private String msg;
    private String subCode;
    private String subMsg;
    private String topForbiddenFields;

    public String getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public String getTopForbiddenFields() {
        return topForbiddenFields;
    }
}
