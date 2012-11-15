package com.shopkeeper.model;

import com.shopkeeper.exception.ModelException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-24
 * Time: 上午3:58
 * To change this template use File | Settings | File Templates.
 */
public class TopUserLocationModel extends Model
{
    public TopUserLocationModel() throws ModelException {
        table("sk_top_user_location");
    }

    @Override
    public int add(Map<String, Object> data) throws ModelException{
        where("user_id=" + data.get("user_id"));
        List<Map<String, Object>> ret = select();
        super.add(data, ret.size() > 0);
        return 0;
    }
}
