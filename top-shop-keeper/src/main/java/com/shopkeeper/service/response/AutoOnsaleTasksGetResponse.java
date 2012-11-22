package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.AutoOnsaleTask;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午5:35
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoonsale_tasks_get_response")
public class AutoOnsaleTasksGetResponse
{
    @XmlElementWrapper(name = "autoonsale_tasks")
    @XmlElement(name = "autoonsale_task")
    private List<AutoOnsaleTask> tasks;

    public List<AutoOnsaleTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<AutoOnsaleTask> tasks) {
        this.tasks = tasks;
    }
}
