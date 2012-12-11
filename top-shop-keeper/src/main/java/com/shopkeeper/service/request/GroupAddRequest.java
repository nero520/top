package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午6:57
 */
public class GroupAddRequest extends AbstractRopRequest
{
    @NotNull
    private String name;

    private String description;

    private String category;

    public String getName() {
        return name;
    }

    public void setName(String groupName) {
        this.name = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
