package com.shopkeeper.model;

import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.taobao.api.ApiException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-23
 * Time: 下午8:33
 * To change this template use File | Settings | File Templates.
 */
public class TopUserModel extends Model
{
    protected String userId;

    public TopUserModel() throws ModelException {
        this(null);
    }

    public TopUserModel(String userId) throws ModelException {
        this.userId = userId;
        table("sk_top_user");
    }

    public void updateFromTop(String accessToken) throws ModelException {
        TopAccessor topAccessor = new TopAccessor(accessToken);
        try {
            Map<String, Object> rsp = topAccessor.getUserInfo();
            String userId = rsp.get("user_id").toString();
            where("user_id=" + userId);
            Map<String, Object> buyerCredit = (Map<String, Object>) rsp.get("buyer_credit");
            Map<String, Object> sellerCredit = (Map<String, Object>) rsp.get("seller_credit");
            Map<String, Object> userLocation = (Map<String, Object>) rsp.get("location");
            buyerCredit.put("user_id", userId);
            sellerCredit.put("user_id", userId);
            userLocation.put("user_id", userId);
            List<Map<String, Object>> ret = select();
            rsp.remove("buyer_credit");
            rsp.remove("seller_credit");
            rsp.remove("location");

            add(rsp, ret.size() > 0);
            TopBuyerCreditModel buyerCreditModel = new TopBuyerCreditModel();
            buyerCreditModel.add(buyerCredit);
            TopSellerCreditModel sellerCreditModel = new TopSellerCreditModel();
            sellerCreditModel.add(sellerCredit);
            TopUserLocationModel userLocationModel = new TopUserLocationModel();
            userLocationModel.add(userLocation);


        } catch (ApiException e) {
            throw new ModelException(e.getMessage());
        }
    }
}
