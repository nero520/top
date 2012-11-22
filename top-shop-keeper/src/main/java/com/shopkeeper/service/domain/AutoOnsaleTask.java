package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午9:21
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoonsale_task")
public class AutoOnsaleTask
{
    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement(name = "time_slot")
    private List<TimeSlot> timeSlot;
    @XmlElement(name = "is_launch")
    private Boolean isLaunch = false;
    @XmlElement
    private Date created;
    @XmlElement(name = "user_id")
    private Long userId;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
