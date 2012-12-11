package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;
import com.shopkeeper.service.domain.TimeSlot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:51
 */
public class OnsaleTaskUpdateRequest extends AbstractRopRequest
{
    private String id;

    private String name;

    private String description;

    @AliasName(name = "time_slot")
    private List<TimeSlot> timeSlot;
    @AliasName(name = "is_launch")
    private Boolean isLaunch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TimeSlot> getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(List<TimeSlot> timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Boolean getLaunch() {
        return isLaunch;
    }

    public void setLaunch(Boolean launch) {
        isLaunch = launch;
    }
}
