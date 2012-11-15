package com.shopkeeper.model;

import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-27
 * Time: 上午3:38
 * To change this template use File | Settings | File Templates.
 */
public class ItemModel extends Model
{
    public ItemModel() throws ModelException {
        table("sk_item");
    }

    public void updateFromTop(String accessToken) throws ModelException {
        TopAccessor accessor = new TopAccessor(accessToken);

    }
}
