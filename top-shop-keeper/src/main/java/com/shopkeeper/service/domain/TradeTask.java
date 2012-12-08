package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-12-6
 * Time: 下午1:50
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "trade_task")
public class TradeTask
{
    @XmlElement
    private String id;

    @XmlElement(name = "user_id")
    private Long userId;

    @XmlElement(name = "task_id")
    private Long taskId;

    @XmlElement
    private Date create;

    @XmlElement(name = "start_time")
    private Date startTime;

    @XmlElement(name = "end_time")
    private Date endTime;

    // status: new, done, download
    @XmlElement
    private String status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
