package com.shopkeeper.service.response;

import com.rop.annotation.AliasName;
import com.shopkeeper.service.domain.AutoOnsaleTask;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:50
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoonsale_task_add_response")
public class AutoOnsaleTaskAddResponse
{
    @XmlElement
    private AutoOnsaleTask task;

    public AutoOnsaleTask getTask() {
        return task;
    }

    public void setTask(AutoOnsaleTask task) {
        this.task = task;
    }
}
