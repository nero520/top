package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.OnsaleTask;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午5:35
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoonsale_tasks_get_response")
public class OnsaleTasksGetResponse
{
    @XmlElementWrapper(name = "autoonsale_tasks")
    @XmlElement(name = "autoonsale_task")
    private List<OnsaleTask> tasks;

    public List<OnsaleTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<OnsaleTask> tasks) {
        this.tasks = tasks;
    }
}
