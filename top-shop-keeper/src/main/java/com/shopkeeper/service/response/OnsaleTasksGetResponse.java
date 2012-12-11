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
@XmlRootElement(name = "onsale_tasks_get_response")
public class OnsaleTasksGetResponse
{
    @XmlElementWrapper(name = "onsale_tasks")
    @XmlElement(name = "onsale_task")
    private List<OnsaleTask> tasks;

    public List<OnsaleTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<OnsaleTask> tasks) {
        this.tasks = tasks;
    }
}
