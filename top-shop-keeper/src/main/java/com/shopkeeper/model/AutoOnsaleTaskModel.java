package com.shopkeeper.model;

import com.shopkeeper.exception.ModelException;
import com.shopkeeper.service.domain.AutoOnsaleTask;
import com.shopkeeper.service.domain.TimeSlot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class AutoOnsaleTaskModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_autoonsale_task";
    @Override
    public void updateFromTop() throws ModelException {

    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AutoOnsaleTask createTask(String name, String description, List<TimeSlot> timeSlotList, Long userId) {
        return null;
    }
}
