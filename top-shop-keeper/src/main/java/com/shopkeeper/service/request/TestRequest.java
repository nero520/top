package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-29
 * Time: 上午4:58
 */
public class TestRequest extends AbstractRopRequest {

    @AliasName(name = "user_name")
    private String userName = null;

    @AliasName(name = "user_id")
    private String userId = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
