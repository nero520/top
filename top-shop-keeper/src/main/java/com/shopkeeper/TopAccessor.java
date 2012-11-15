package com.shopkeeper;

import com.rop.client.RopUnmarshaller;
import com.shopkeeper.exception.TopException;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.*;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-19
 * Time: 下午7:05
 * To change this template use File | Settings | File Templates.
 */
public class TopAccessor
{
    private String accessToken = null;

    private String refreshToken = null;

    private DefaultTaobaoClient topClient = null;

    public TopAccessor(){

    }

    public TopAccessor(String accessToken) {
        this(accessToken, null);
    }

    public TopAccessor(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        topClient = new DefaultTaobaoClient(Config.TOP_SERVER_URL, Config.TOP_APP_KEY, Config.TOP_APP_SECRET);
        topClient.setNeedEnableParser(false);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String refreshAccessToken(String refreshToken, String accessToken) throws TopException {
        try {
            String appkey = Config.TOP_APP_KEY;
            String secret = Config.TOP_APP_SECRET;

            //refreshToken = "61013180042a3be8945a9fb38f1454127cdaca100919e742074082786";
            //accessToken = "6101618f23825423ef88eb55c510a64bf0fef1fe663dace2074082786";

            Map<String, String> signParams = new TreeMap<String, String>();
            signParams.put("appkey", appkey);
            signParams.put("refresh_token", refreshToken);
            signParams.put("accessToken", accessToken);

            StringBuilder paramsString = new StringBuilder();
            Set<Map.Entry<String, String>> paramsEntry = signParams.entrySet();
            for (Map.Entry paramEntry : paramsEntry) {
                paramsString.append(paramEntry.getKey()).append(paramEntry.getValue());
            }
            String sign = DigestUtils.md5Hex((paramsString.toString() + secret).getBytes("utf-8")).toUpperCase();
            String signEncoder = URLEncoder.encode(sign, "utf-8");
            String appkeyEncoder = URLEncoder.encode(appkey, "utf-8");
            String refreshTokenEncoder = URLEncoder.encode(refreshToken, "utf-8");
            String sessionkeyEncoder = URLEncoder.encode(accessToken, "utf-8");

            String freshUrl = "http://container.api.taobao.com/container/refresh?appkey=" + appkeyEncoder
                    + "&refresh_token=" + refreshTokenEncoder + "&accessToken=" + sessionkeyEncoder + "&sign="
                    + signEncoder;
            System.out.println(freshUrl);
            //import com.taobao.api.internal.util.WebUtils
            String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60, 30 * 1000 * 60);
            System.out.println(response);
            return response;
        } catch (UnsupportedEncodingException e) {
            throw new TopException(e.getMessage());
        } catch (IOException e) {
            throw new TopException(e.getMessage());
        } catch (Exception e) {
            throw new TopException(e.getMessage());
        }
    }

    public Map<String, Object> getUserInfo() throws ApiException {
        UserGetRequest request = new UserGetRequest();
        request.setFields("user_id,uid,nick,sex,buyer_credit,seller_credit,location,created,last_visit,birthday,type,status,consumer_protection");
        Map<String, Object> rsp = topClient.doPost(request, this.accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Map<String, Object>> response = (Map<String, Map<String, Object>>) rspObj.get("user_get_response");
        if (response != null) {
            return response.get("user");
        }
        return rspObj;
    }

    public Map<String, Object> getOnsaleItems () throws ApiException {
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Map<String, Object>> response = (Map<String, Map<String, Object>>) rspObj.get("items_onsale_get_response");
        if (response != null) {
            return response.get("items");
        }
        return rspObj;
    }

    public Map<String, Object> getInventoryItems() throws ApiException {
        ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
        request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id,is_virtual,is_taobao,is_ex");
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Map<String, Object>> response = (Map<String, Map<String, Object>>) rspObj.get("items_inventory_get_response");
        if (response != null) {
            return response.get("items");
        }
        return rspObj;
    }

    public Map<String, Object> getOnShowcaseItems() throws ApiException {
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setHasShowcase(true);
        request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Map<String, Object>> response = (Map<String, Map<String, Object>>) rspObj.get("items_onsale_get_response");
        if (response != null) {
            return response.get("items");
        }
        return rspObj;
    }

    public Map<String, Object> getSellerCatsList(String userNick) throws ApiException {
        SellercatsListGetRequest request = new SellercatsListGetRequest();
        request.setNick(userNick);
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Map<String, Object>> response = (Map<String, Map<String, Object>>) rspObj.get("sellercats_list_get_response");
        if (response != null) {
            return response.get("seller_cats");
        }
        return rspObj;
    }

    public Map<String, Object> getShopRemainShowcase() throws ApiException {
        ShopRemainshowcaseGetRequest request = new ShopRemainshowcaseGetRequest();
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Map<String, Object>> response = (Map<String, Map<String, Object>>) rspObj.get("shop_remainshowcase_get_response");
        if (response != null) {
            return response.get("shop");
        }
        return rspObj;
    }

    public Map<String, Object> addRecommendItem(Long itemId) throws ApiException{
        ItemRecommendAddRequest request = new ItemRecommendAddRequest();
        request.setNumIid(itemId);
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Object> response = (Map<String, Object>) rspObj.get("item_recommend_add_response");
        if (response != null) {
            return response;
        }
        return rspObj;
    }

    public Map<String, Object> deleteRecommendItem(Long itemId) throws ApiException{
        ItemRecommendDeleteRequest request = new ItemRecommendDeleteRequest();
        request.setNumIid(itemId);
        Map<String, Object> rsp = topClient.doPost(request, accessToken);
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map<String, Object> response = (Map<String, Object>) rspObj.get("item_recommend_delete_response");
        if (response != null) {
            return response;
        }
        return rspObj;
    }
/*
    public Map<String, List<Long>> addRecommendItems(List<Long> itemIds) throws ApiException{
        for (int i = 0; i < itemIds.)
        return null;
    }

    public Map<String, List<Long>> deleteRecommendItems(List<Long> itemIds) throws ApiException{
        return null;
    }
*/

}
