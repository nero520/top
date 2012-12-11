package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.OnsaleTask;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:50
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoonsale_task_delete_response")
public class AutoOnsaleTaskDeleteResponse
{
    @XmlElement(name = "autoonsale_task")
    private OnsaleTask task;

    public OnsaleTask getTask() {
        return task;
    }

    public void setTask(OnsaleTask task) {
        this.task = task;
    }
}
