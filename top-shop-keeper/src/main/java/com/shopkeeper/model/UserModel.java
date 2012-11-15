package com.shopkeeper.model;
import java.beans.BeanDescriptor;
import java.lang.reflect.InvocationTargetException;


import com.shopkeeper.Config;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-29
 * Time: 上午12:08
 * To change this template use File | Settings | File Templates.
 */
public class UserModel extends Model
{
    public UserModel() throws ModelException {
        table("sk_user");
    }

    private String calMatchString(String fields) {
        int c = StringUtils.countMatches(fields, ",") + 1;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < c; i++) {
            sb.append("?,");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public boolean login(Map<String, Object> data) throws ModelException {
        TopAccessor accessor = new TopAccessor((String)data.get("access_token"), (String)data.get("refresh_token"));
        try {
            Map<String, Object> ret = accessor.getUserInfo();
            where("user_id=" + data.get("user_id"));
            List<Map<String, Object>> t = select();
            add(data, t.size() > 0);

            TopUserModel topUserModel = new TopUserModel();
            topUserModel.updateFromTop((String)data.get("access_token"));


        } catch (ApiException e) {
            throw new ModelException(e.getMessage());
        }
        return true;
    }

    public Object getUser(String fields, String userId) throws ModelException {
        field(fields);
        where("user_id=" + userId);
        List<Map<String, Object>> ret = select();
        if (ret != null && ret.size() > 0) {
            return ret.get(0);
        }
        return null;
    }


}
