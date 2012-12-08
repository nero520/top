package com.shopkeeper.admin.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-23
 * Time: 上午5:24
 */
public class AdminLoginRequest extends AbstractRopRequest
{
    @AliasName(name = "user_name")
    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
