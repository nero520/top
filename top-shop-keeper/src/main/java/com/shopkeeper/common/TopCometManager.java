package com.shopkeeper.common;

import com.shopkeeper.Config;
import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;
import com.taobao.api.internal.stream.TopCometStreamRequest;
import com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener;
import com.taobao.api.internal.stream.message.TopCometMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-24
 * Time: 上午8:28
 */

/*
   处理指定用户的长连接
 */
public class TopCometManager
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static TopCometManager _instance = null;
    private TopCometStream topCometStream = null;

    private Map<Long, TopCometListener> listenerMap;

    private TopCometManager() {
        init();
    }

    public static TopCometManager getInstance() {
        if(_instance == null) {
            _instance = new TopCometManager();
        }
        return _instance;
    }

    private void init() {
        Configuration conf = new Configuration(Config.TOP_APP_KEY,Config.TOP_APP_SECRET,null);
        conf.setConnectUrl(Config.TOP_COMET_SERVER_URL);
        topCometStream = new TopCometStreamFactory(conf).getInstance();
        topCometStream.setConnectionListener(new MyConnectionLifeCycleListener());
        topCometStream.setMessageListener(new MyTopCometMessageListener());
        topCometStream.start();

        listenerMap = new HashMap<Long, TopCometListener>();
    }

    private void connect(Long userId) {
        TopCometStreamRequest request = new TopCometStreamRequest(Config.TOP_APP_KEY, Config.TOP_APP_SECRET, userId.toString(), null);
        MyConnectionLifeCycleListener connectionLifeCycleListener = new MyConnectionLifeCycleListener(userId);
        MyTopCometMessageListener topCometMessageListener = new MyTopCometMessageListener(userId);
        request.setConnectListener(connectionLifeCycleListener);
        request.setMsgListener(topCometMessageListener);
        topCometStream.addNewStreamClient(request);
    }

    public void addNewStream(Long userId, TopCometListener listener) {
        listenerMap.put(userId, listener);
        connect(userId);
    }

        class MyConnectionLifeCycleListener implements ConnectionLifeCycleListener
        {
            private Long userId;

            public MyConnectionLifeCycleListener() {}

            public MyConnectionLifeCycleListener(Long userId) {
                this.userId = userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public Long getUserId() {
                return userId;
            }

            @Override
            public void onBeforeConnect() {

            }

            @Override
            public void onException(Throwable e) {
                connect(userId);
            }

            @Override
            public void onMaxReadTimeoutException() {
                connect(userId);
            }
        }

        class MyTopCometMessageListener implements TopCometMessageListener
        {
            private Long userId;

            public MyTopCometMessageListener() {}

            public MyTopCometMessageListener(Long userId) {
                this.userId = userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public Long getUserId() {
                return userId;
            }

            @Override
            public void onConnectMsg(String message) {
                logger.info("top comet connect message: " + message);
            }

            @Override
            public void onHeartBeat() {
                logger.info("top comet heart beat");
            }

            @Override
            public void onReceiveMsg(String message) {
                TopCometListener listener = listenerMap.get(userId);
                listener.onReceiveMsg(message);
            }

            @Override
            public void onDiscardMsg(String message) {
                logger.info("top comet discard message: " + message);
            }

            @Override
            public void onServerUpgrade(String message) {
                logger.info("top comet server upgrade: " + message);
            }

            @Override
            public void onServerRehash() {
                logger.info("top comet server rehash");
            }

            @Override
            public void onServerKickOff() {
                logger.info("top comet server kick off");
            }

            @Override
            public void onClientKickOff() {
                logger.info("top comet client kick off");
            }

            @Override
            public void onOtherMsg(String message) {
                logger.info("top comet other message");
            }

            @Override
            public void onException(Exception e) {
                TopCometListener listener = listenerMap.get(userId);
                listener.onException(e);
                logger.info("top comet exception: " + e.getMessage());
            }
        }
}
