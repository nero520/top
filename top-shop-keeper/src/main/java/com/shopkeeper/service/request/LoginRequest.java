package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 6:16 下午
 * To change this template use File | Settings | File Templates.
 */

public class LoginRequest extends AbstractRopRequest
{
    @NotNull
    @AliasName(name = "user_id")
    private Long userId;

    @NotNull
    @AliasName(name = "nick")
    private String userNick = null;

    @NotNull()
    @AliasName(name = "access_token")
    private String accessToken = null;

    @NotNull()
    @AliasName(name = "refresh_token")
    private String refreshToken = null;

    @AliasName(name = "sub_user_id")
    private String subUserId;

    @AliasName(name = "sub_user_nick")
    private String subUserNick = null;

    @NotNull()
    @AliasName(name = "expires_in")
    private int expiresIn;

    @NotNull()
    @AliasName(name = "re_expires_in")
    private int reExpiresIn;

    @NotNull()
    @AliasName(name = "r1_expires_in")
    private int r1ExpiresIn;

    @NotNull()
    @AliasName(name = "r2_expires_in")
    private int r2ExpiresIn;

    @NotNull()
    @AliasName(name = "w1_expires_in")
    private int w1ExpiresIn;

    @NotNull()
    @AliasName(name = "w2_expires_in")
    private int w2ExpiresIn;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
        this.subUserId = subUserId;
    }

    public String getSubUserNick() {
        return subUserNick;
    }

    public void setSubUserNick(String subUserNick) {
        this.subUserNick = subUserNick;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getReExpiresIn() {
        return reExpiresIn;
    }

    public void setReExpiresIn(int reExpiresIn) {
        this.reExpiresIn = reExpiresIn;
    }

    public int getR1ExpiresIn() {
        return r1ExpiresIn;
    }

    public void setR1ExpiresIn(int r1ExpiresIn) {
        this.r1ExpiresIn = r1ExpiresIn;
    }

    public int getR2ExpiresIn() {
        return r2ExpiresIn;
    }

    public void setR2ExpiresIn(int r2ExpiresIn) {
        this.r2ExpiresIn = r2ExpiresIn;
    }

    public int getW1ExpiresIn() {
        return w1ExpiresIn;
    }

    public void setW1ExpiresIn(int w1ExpiresIn) {
        this.w1ExpiresIn = w1ExpiresIn;
    }

    public int getW2ExpiresIn() {
        return w2ExpiresIn;
    }

    public void setW2ExpiresIn(int w2ExpiresIn) {
        this.w2ExpiresIn = w2ExpiresIn;
    }
}
