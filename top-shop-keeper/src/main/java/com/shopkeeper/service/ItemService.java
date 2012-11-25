package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.session.SimpleSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.ItemModel;
import com.shopkeeper.service.domain.Item;
import com.shopkeeper.service.request.ItemGetRequest;
import com.shopkeeper.service.request.ItemUpdateRequest;
import com.shopkeeper.service.request.ItemsGetRequest;
import com.shopkeeper.service.response.ItemGetResponse;
import com.shopkeeper.service.response.ItemUpdateResponse;
import com.shopkeeper.service.response.ItemsGetResponse;
import com.shopkeeper.utils.Utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午3:27
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version = "1.0")
public class ItemService
{

    @ServiceMethod(method = "item.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getItem(ItemGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ItemModel itemModel = new ItemModel();
        try {
            Item item = itemModel.getItem(request.getNumIid(), request.getFields(), userId);
            if (item != null) {
                ItemGetResponse response = new ItemGetResponse();
                response.setItem(item);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {
            // todo
        }
        return null;
    }

    @ServiceMethod(method = "item.update", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object update(ItemUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");

        ItemModel itemModel = new ItemModel();
        try {
            Item item = itemModel.updateItem(request.getNumIid(), request.getGroupId(),
                    request.getTaskId(), Utils.dateToString(request.getTimeOnsale()),
                    request.getAutoShowcaseStatus(), userId);
            if (item != null) {
                ItemUpdateResponse response = new ItemUpdateResponse();
                response.setItem(item);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {
            // todo
        }

        return null;
    }

    @ServiceMethod(method = "items.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getItems(ItemsGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        String numIids = request.getNumIids();
        String fields = request.getFields();
        String groupId = request.getGroupId();
        String taskId = request.getTaskId();
        ItemModel itemModel = new ItemModel();
        try {
            List<Item> itemList = itemModel.getItems(numIids, fields, groupId, taskId,
                    request.getAutoShowcaseStatus(), userId);
            if (itemList != null) {
                ItemsGetResponse response = new ItemsGetResponse();
                response.setItems(itemList);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }
}
