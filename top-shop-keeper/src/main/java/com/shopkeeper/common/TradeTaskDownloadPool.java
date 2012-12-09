package com.shopkeeper.common;

import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.TopException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Task;
import com.taobao.api.internal.util.AtsUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 下午12:07
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

    public void addTask(String taskId, Long userId, String accessToken) {
        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put("task_id", taskId);
	    taskMap.put("user_id", userId);
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

    class TaskRunnable implements Runnable
    {
        private void downloadTask(Map<String, Object> taskMap) {
            Long taskId = Long.parseLong((String)taskMap.get("task_id"));
            String accessToken = (String)taskMap.get("access_token");
            TopAccessor topAccessor = new TopAccessor(accessToken);
            try {
                Task task = topAccessor.getTaskResult(taskId);
                String taskUrl = task.getDownloadUrl();
                String projectPath = System.getProperty("project_path");
                String taskTempDir = projectPath + "temp\\" + taskId.toString();
                File tempDir = new File(taskTempDir);
                File taskFile = AtsUtils.download(taskUrl, tempDir);
                File taskResultFile = new File(taskTempDir + "\\unzip");
                List<File> fileList = AtsUtils.unzip(taskFile, taskResultFile);

                JsonFactory jsonFactory = new JsonFactory();
                ObjectMapper objectMapper = new ObjectMapper();
	            TradeTaskDownloadListener listener = new TradeTaskDownloadHandler((Long)taskMap.get("user_id"));
                for (File file : fileList) {
                    JsonParser jp = jsonFactory.createJsonParser(file);
                    Map obj;
                    do {
                        obj = objectMapper.readValue(jp, Map.class);
	                    listener.receivedData(obj);
                    }while (jp.nextToken() != null);
                }
                listener.taskFinished();

            } catch (TopException e) {
	            logger.info(e.getMsg());
            } catch (IOException e) {
	            logger.info(e.getMessage());
            } catch (ApiException e) {
	            logger.info(e.getMessage());
            }
        }

        @Override
        public void run() {
	        while (true) {
		        Map<String, Object> task = popTask();
		        if (task != null) {
			        downloadTask(task);
		        }
		        else {
			        try {
				        waitObj.wait();
			        } catch (InterruptedException e) {
						break;
			        }
		        }
	        }
        }
    }

    class TradeTaskDownloadHandler implements TradeTaskDownloadListener
    {
	    private Long userId;
	    public TradeTaskDownloadHandler(Long userId) {
			this.userId = userId;
	    }

        @Override
        public void receivedData(Object object) {
            logger.info(object.toString());
        }

        @Override
        public void taskFinished() {

        }

        @Override
        public void taskError(String msg) {

        }
    }
}
