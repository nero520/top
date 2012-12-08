package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.session.SimpleSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.AutoOnsaleTaskModel;
import com.shopkeeper.service.domain.AutoOnsaleTask;
import com.shopkeeper.service.request.*;
import com.shopkeeper.service.response.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:22
 */

@ServiceMethodBean(version = "1.0")
public class AutoOnsaleService
{
    @ServiceMethod(method = "autoonsale.task.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getTask(AutoOnsaleTaskGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoOnsaleTaskModel taskModel = new AutoOnsaleTaskModel();
        try {
            AutoOnsaleTask task = taskModel.getTask(request.getId(), request.getName(), userId);
            if (task != null) {
                AutoOnsaleTaskGetResponse response = new AutoOnsaleTaskGetResponse();
                response.setTask(task);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }

    @ServiceMethod(method = "autoonsale.tasks.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getTasks(AutoOnsaleTasksGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoOnsaleTaskModel taskModel = new AutoOnsaleTaskModel();
        try {
            List<AutoOnsaleTask> taskList = taskModel.getTasks(request.getIds(), userId);
            if (taskList != null) {
                AutoOnsaleTasksGetResponse response = new AutoOnsaleTasksGetResponse();
                response.setTasks(taskList);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }

    @ServiceMethod(method = "autoonsale.task.add", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object addTask(AutoOnsaleTaskAddRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoOnsaleTaskModel taskModel = new AutoOnsaleTaskModel();
        try {
            AutoOnsaleTask task = taskModel.createTask(request.getName(), request.getDescription(), request.getTimeSlot(), userId);
            if (task != null) {
                AutoOnsaleTaskAddResponse response = new AutoOnsaleTaskAddResponse();
                response.setTask(task);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }

    @ServiceMethod(method = "autoonsale.task.update", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object updateTask(AutoOnsaleTaskUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoOnsaleTaskModel taskModel = new AutoOnsaleTaskModel();
        try {
            AutoOnsaleTask task = taskModel.updateTask(request.getId(), request.getName(), request.getDescription(), request.getTimeSlot(), request.getLaunch(), userId);
            if (task != null) {
                AutoOnsaleTaskUpdateResponse response = new AutoOnsaleTaskUpdateResponse();
                response.setTask(task);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }

    @ServiceMethod(method = "autoonsale.task.delete", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object deleteTask(AutoOnsaleTaskDeleteRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoOnsaleTaskModel taskModel = new AutoOnsaleTaskModel();
        try {
            AutoOnsaleTask task = taskModel.deleteTask(request.getId(), request.getName(), userId);
            if (task != null) {
                AutoOnsaleTaskDeleteResponse response = new AutoOnsaleTaskDeleteResponse();
                response.setTask(task);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }
}
