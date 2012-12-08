package com.shopkeeper.admin.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.session.SimpleSession;
import com.shopkeeper.admin.model.AdminModel;
import com.shopkeeper.admin.service.request.AdminLoginRequest;
import com.shopkeeper.admin.service.response.AdminLoginResponse;
import com.shopkeeper.exception.ModelException;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-23
 * Time: 上午5:13
 */
@ServiceMethodBean(version = "1.0")
public class AdminService
{
    @ServiceMethod(method = "admin.login", version = "1.0", needInSession = NeedInSessionType.NO)
    public Object login(AdminLoginRequest request) {
        AdminModel adminModel = new AdminModel();
        try {
            String accessToken = adminModel.login(request.getUserName(), request.getPassword());
            if (accessToken != null) {
                SimpleSession session = new SimpleSession();
                session.setAttribute("user_name", request.getUserName());
                request.getRopRequestContext().addSession(accessToken, session);
                AdminLoginResponse response = new AdminLoginResponse();
                response.setAccessToken(accessToken);
                return response;
            }
        } catch (ModelException e) {

        }

        return null;
    }

}
