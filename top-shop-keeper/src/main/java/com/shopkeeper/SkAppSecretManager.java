package com.shopkeeper;

import com.rop.security.AppSecretManager;
/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 5:13 下午
 */
public class SkAppSecretManager implements AppSecretManager {
    /**
     * 获取应用程序的密钥
     *
     * @param appKey
     * @return
     */

    private String appKey = "sk_app_key";
    private String appSecret = "sk_app_secret";

    @Override
    public String getSecret(String appKey) {
        if (appKey.equalsIgnoreCase(this.appKey)) {
            return appSecret;

        }
        return null;
    }

    /**
     * 是否是合法的appKey
     *
     * @param appKey
     * @return
     */
    @Override
    public boolean isValidAppKey(String appKey) {
        if (appKey.equalsIgnoreCase(this.appKey)) {
            return true;
        }
        return false;
    }
}
