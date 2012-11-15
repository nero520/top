package com.shopkeeper.service;

import com.rop.RopRequest;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.ErrorResponse;
import com.rop.session.SimpleSession;
import com.shopkeeper.SkSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.UserModel;
import com.shopkeeper.service.request.LoginRequest;
import com.shopkeeper.service.request.UserGetRequest;
import com.shopkeeper.service.response.LoginResponse;
import com.shopkeeper.service.response.LogoutResponse;
import com.taobao.api.ApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 6:07 下午
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version = "1.0")
public class UserService
{
    @ServiceMethod(method = "user.login", version = "1.0", needInSession = NeedInSessionType.NO)
    public Object login(LoginRequest request) throws SQLException, ApiException {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("access_token", request.getAccessToken());
        datas.put("expires_in", request.getExpiresIn());
        datas.put("r1_expires_in", request.getR1ExpiresIn());
        datas.put("r2_expires_in", request.getR2ExpiresIn());
        datas.put("re_expires_in", request.getReExpiresIn());
        datas.put("refresh_token", request.getRefreshToken());
        datas.put("sub_user_id", request.getSubUserId());
        datas.put("sub_user_nick", request.getSubUserNick());
        datas.put("user_id", request.getUserId());
        datas.put("nick", request.getUserNick());
        datas.put("w1_expires_in", request.getW1ExpiresIn());
        datas.put("w2_expires_in", request.getW2ExpiresIn());

        try {
            UserModel userModel = new UserModel();
            userModel.login(datas);
            String accessToken = request.getUserId().toString();

            LoginResponse response = new LoginResponse();
            response.setSession(accessToken);

            SimpleSession session = new SimpleSession();
            session.setAttribute("user_id", request.getUserId());
            session.setAttribute("user_nick", request.getUserNick());
            request.getRopRequestContext().addSession(accessToken, session);

            return response;
        } catch (ModelException e) {
            ErrorResponse response = new ErrorResponse();
            response.setCode("100");
            response.setMessage(e.getMessage());
            return response;
        }
    }

    @ServiceMethod(method = "user.logout", version = "1.0", needInSession = NeedInSessionType.NO)
    public Object logout(RopRequest request) {
        request.getRopRequestContext().removeSession();
        LogoutResponse response = new LogoutResponse();
        response.setSuccessful(true);
        return response;
    }

    @ServiceMethod(method = "user.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getUser(UserGetRequest request) {
        String fields = request.getFields();
        String accessToken = request.getRopRequestContext().getSessionId();
        try {
            UserModel userModel = new UserModel();
            Map<String, Object> user = (Map<String, Object>)userModel.getUser(fields, accessToken);

        } catch (ModelException e) {

        }
        return null;
    }
}
