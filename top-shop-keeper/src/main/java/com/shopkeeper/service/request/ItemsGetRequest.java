package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午7:04
 */

public class ItemsGetRequest extends AbstractRopRequest
{
    @AliasName(name = "num_iids")
    private String numIids;

    @AliasName(name = "group_id")
    private String groupId;

    @AliasName(name = "task_id")
    private String taskId;

    @AliasName(name = "has_showcase")
    private Boolean hasShowcase = false;

    @AliasName(name = "showcase_status")
    private String showcaseStatus;

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

    public Boolean isHasShowcase() {
        return hasShowcase;
    }

    public void setHasShowcase(Boolean hasShowcase) {
        this.hasShowcase = hasShowcase;
    }

	public String getShowcaseStatus() {
		return showcaseStatus;
	}

	public void setShowcaseStatus(String showcaseStatus) {
		this.showcaseStatus = showcaseStatus;
	}
}
