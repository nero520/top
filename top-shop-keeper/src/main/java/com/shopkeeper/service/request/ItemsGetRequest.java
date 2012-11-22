package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午7:04
 * To change this template use File | Settings | File Templates.
 */

public class ItemsGetRequest extends AbstractRopRequest
{
    @AliasName(name = "num_iids")
    private String numIids;

    @NotNull
    private String fields;

    @AliasName(name = "group_id")
    private String groupId;

    @AliasName(name = "task_id")
    private String taskId;

    @AliasName(name = "has_showcase")
    private boolean hasShowcase;

    @AliasName(name = "auto_showcase_status")
    private String autoShowcaseStatus;

    public String getNumIids() {
        return numIids;
    }

    public void setNumIids(String numIids) {
        this.numIids = numIids;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isHasShowcase() {
        return hasShowcase;
    }

    public void setHasShowcase(boolean hasShowcase) {
        this.hasShowcase = hasShowcase;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getAutoShowcaseStatus() {
        return autoShowcaseStatus;
    }

    public void setAutoShowcaseStatus(String autoShowcaseStatus) {
        this.autoShowcaseStatus = autoShowcaseStatus;
    }
}
