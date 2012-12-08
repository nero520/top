package com.shopkeeper;

import com.rop.session.Session;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-11
 * Time: 下午9:09
 */
public class SkSession implements Session
{
    private String userId;

    private String userNick;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
}
