package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.Item;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午7:03
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "items_get_response")
public class ItemsGetResponse
{
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
