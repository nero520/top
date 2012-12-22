package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;
import com.shopkeeper.service.domain.TimeSlot;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:51
 */
public class OnsaleTaskAddRequest extends AbstractRopRequest
{
    @NotNull
    private String name;

    private String description;

    @AliasName(name = "time_slot")
    private LinkedList<TimeSlot> timeSlot;

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

    public LinkedList<TimeSlot> getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LinkedList<TimeSlot> timeSlot) {
        this.timeSlot = timeSlot;
    }
}
