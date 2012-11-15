package com.shopkeeper.model;

import com.shopkeeper.exception.ModelException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-24
 * Time: 上午3:57
 * To change this template use File | Settings | File Templates.
 */
public class TopBuyerCreditModel extends Model
{
    public TopBuyerCreditModel() throws ModelException{
        table("sk_top_buyer_credit");
    }

    @Override
    public int add(Map<String, Object> data) throws ModelException{
        where("user_id=" + data.get("user_id"));
        List<Map<String, Object>> ret = select();
        super.add(data, ret.size() > 0);
        return 0;
    }
}
