package com.shopkeeper;

import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.exception.TopException;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.Task;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.*;
import com.taobao.api.response.TopatsResultGetResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-19
 * Time: 下午7:05
 */
public class TopAccessor
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String accessToken = null;

    //private String refreshToken = null;

    private DefaultTaobaoClient topClient = null;

    public TopAccessor(){
        this(null, null);
    }

    public TopAccessor(String accessToken) {
        this(accessToken, null);
    }

    public TopAccessor(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        //this.refreshToken = refreshToken;
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
            throw new TopException("101", "refresh top access token failed");
        } catch (IOException e) {
            throw new TopException("101", "refresh top access token failed");
        } catch (Exception e) {
            throw new TopException("101", "refresh top access token failed");
        }
    }

    private Map parse(String json) {
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
	    return unmarshaller.unmarshaller(json, Map.class);
    }

    public void throwException(Map exception) throws TopException {
        Map error = (Map)exception.get("error_response");
	    throw new TopException(error.get("code").toString(),
	            (String)error.get("msg"),
	            error.get("sub_code").toString(),
	            (String)error.get("sub_msg"));
    }

    public Map getUserInfo() throws TopException {
        UserGetRequest request = new UserGetRequest();
        request.setFields("user_id,uid,nick,sex,buyer_credit,seller_credit,location,created,last_visit,birthday,type,status,consumer_protection");
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, this.accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "user.get error");
        }
        String jsonStr = (String)rsp.get("rsp");
        Map rspObj = parse(jsonStr);
        Map response = (Map) rspObj.get("user_get_response");
        if (response != null) {
            return (Map)response.get("user");
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public List<Map<String, Object>> getOnsaleItems () throws TopException {
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
        long pageNo = 1;
        long pageSize = 20;
        request.setPageSize(pageSize);
        List<Map<String, Object>> totalItems = new LinkedList<Map<String, Object>>();
        while (true) {
            Map<String, Object> rsp;
            try {
                request.setPageNo(pageNo);
                rsp = topClient.doPost(request, accessToken);
            } catch (ApiException e) {
                throw new TopException("102", "top server error", "1", "items.onsale.get error");
            }
            String jsonStr = (String)rsp.get("rsp");
            RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
            Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
            Map response = (Map)rspObj.get("items_onsale_get_response");

            if (response != null) {
                Integer totalResultsNo = (Integer)response.get("total_results");

                response = (Map)response.get("items");
                List totalResult = (List)response.get("item");
	            if (totalResult != null) {
		            totalItems.addAll(totalResult);
	            }

                if (pageSize * pageNo >= totalResultsNo) {
                    break;
                }
            }
            else {
                throwException(rspObj);
            }
            pageNo++;
        }
        if (totalItems.size() > 0) {
            return totalItems;
        }
        return null;
    }

    public Map getInventoryItems() throws TopException {
        ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
        request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id,is_virtual,is_taobao,is_ex");
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "items.inventory.get error");
        }
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("items_inventory_get_response");
        if (response != null) {
            return (Map)response.get("items");
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public Map getOnShowcaseItems() throws TopException {
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setHasShowcase(true);
        request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "items.onsale.get error");
        }
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("items_onsale_get_response");
        if (response != null) {
            return (Map)response.get("items");
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public Map getSellerCatsList(String userNick) throws TopException {
        SellercatsListGetRequest request = new SellercatsListGetRequest();
        request.setNick(userNick);
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "sellercats.list.get error");
        }
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("sellercats_list_get_response");
        if (response != null) {
            return (Map)response.get("seller_cats");
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public Map getShopRemainShowcase() throws TopException {
        ShopRemainshowcaseGetRequest request = new ShopRemainshowcaseGetRequest();
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "shop.remainshowcase.get error");
        }
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("shop_remainshowcase_get_response");
        if (response != null) {
            return (Map)response.get("shop");
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public Map addRecommendItem(Long itemId) throws TopException {
        ItemRecommendAddRequest request = new ItemRecommendAddRequest();
        request.setNumIid(itemId);
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "item.recommend.add error");
        }
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("item_recommend_add_response");
        if (response != null) {
            return response;
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public Map deleteRecommendItem(Long itemId) throws TopException {
        ItemRecommendDeleteRequest request = new ItemRecommendDeleteRequest();
        request.setNumIid(itemId);
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "item.recommend.delete error");
        }
        String jsonStr = (String)rsp.get("rsp");
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("item_recommend_delete_response");
        if (response != null) {
            return response;
        }
        else {
            throwException(rspObj);
        }
        return null;
    }

    public Map createTradeSoldGetTask(Date startTime, Date endTime) throws TopException {
        TopatsTradesSoldGetRequest request = new TopatsTradesSoldGetRequest();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String strStartTime = dateFormat.format(startTime);
        String strEndTime = dateFormat.format(endTime);

        request.setStartTime(strStartTime);
        request.setEndTime(strEndTime);
        request.setFields("tid,num,num_iid,status,title,type,price,discount_fee,point_fee,total_fee,created,pay_time,modified,end_time,buyer_message,alipay_no,buyer_memo,seller_memo,shipping_type,adjust_fee,buyer_obtain_point_fee,cod_fee,cod_status,commission_fee,buyer_rate,seller_nick,pic_path,payment,seller_rate,real_point_fee,post_fee,consign_time,received_payment,orders");
        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "topats.trades.sold.get error");
        }
        String jsonStr = (String)rsp.get("rsp");
        logger.info(jsonStr);
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("topats_trades_sold_get_response");
        if (response != null) {
            return response;
        }
        else {
            //throwException(rspObj);
        }
        return null;
    }

    public boolean incrementCustomerPermit(String type, String topics, String status) throws TopException {
        IncrementCustomerPermitRequest request = new IncrementCustomerPermitRequest();
        request.setStatus(status);
        request.setTopics(topics);
        request.setType(type);

        Map<String, Object> rsp;
        try {
            rsp = topClient.doPost(request, accessToken);
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "increment.customer.permit error");
        }
        String jsonStr = (String)rsp.get("rsp");
        logger.info(jsonStr);
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map rspObj = unmarshaller.unmarshaller(jsonStr, Map.class);
        Map response = (Map) rspObj.get("increment_customer_permit_response");
        if (response != null) {
            return true;
        }
        else {
            //throwException(rspObj);
        }
        return false;
    }

    public Task getTaskResult(Long taskId) throws TopException {
        TopatsResultGetRequest request = new TopatsResultGetRequest();
        request.setTaskId(taskId);
        try {
            topClient.setNeedEnableParser(true);
            TopatsResultGetResponse response = topClient.execute(request, accessToken);
            topClient.setNeedEnableParser(false);
	        return response.getTask();
        } catch (ApiException e) {
            throw new TopException("102", "top server error", "1", "increment.customer.permit error");
        }
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
