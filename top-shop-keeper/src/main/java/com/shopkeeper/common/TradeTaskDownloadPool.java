package com.shopkeeper.common;

import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.TopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 下午12:07
 * To change this template use File | Settings | File Templates.
 */
public class TradeTaskDownloadPool
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static TradeTaskDownloadPool _instance = null;

    private List<Map<String, Object>> taskList = Collections.synchronizedList(new LinkedList<Map<String, Object>>());

    private Object waitObj = new Object();

    private Thread thread;

    private TradeTaskDownloadPool() {
        init();
    }

    private void init() {
        thread = new Thread(new TaskRunnable());
        thread.start();
    }

    public static TradeTaskDownloadPool getInstance() {
        if (_instance == null) {
            _instance = new TradeTaskDownloadPool();
        }
        return _instance;
    }

    public void addTask(String taskId, String accessToken) {
        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put("task_id", taskId);
        taskMap.put("access_token", accessToken);
        taskList.add(taskMap);
        if (thread.getState() == Thread.State.WAITING) {
            waitObj.notify();
        }
    }

    private Map<String, Object> popTask() {
        Map<String, Object> task = null;
        if (taskList.size() > 0) {
            task = taskList.remove(0);
        }
        return task;
    }

    class TradeTaskParseListener implements TradeTaskListener {

        @Override
        public void receivedData(Object object) {
            logger.info(object.toString());
        }

        @Override
        public void taskFinished() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void taskError(String msg) {

        }
    }

    class TaskRunnable implements Runnable
    {
        private void downloadTask(Map<String, Object> task) {
            RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
            Long taskId = Long.parseLong((String)task.get("task_id"));
            String accessToken = (String)task.get("access_token");
            TopAccessor topAccessor = new TopAccessor(accessToken);
            try {
                topAccessor.getTaskResult(taskId, new TradeTaskParseListener());
            } catch (TopException e) {

            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Map<String, Object> task = popTask();
                    if (task != null) {
                        downloadTask(task);
                        //logger.info(taskResult);
                    }
                    else {
                        waitObj.wait();
                    }
                }
            } catch (InterruptedException e) {

            }
        }
    }
}
