package com.shopkeeper.model;

import com.shopkeeper.exception.ModelException;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-24
 * Time: 上午6:38
 * To change this template use File | Settings | File Templates.
 */
public class GroupModel extends Model
{
    public GroupModel() throws ModelException {
        table("sk_group");
    }

    public void createGroup(String userId, String groupName, String description, String category) {

    }

    public void updateGroup(String groupId, String groupName, String description) {

    }

    public void deleteGroupById(String groupId) {

    }

    public void deleteGroupByName(String groupName) {

    }
}
