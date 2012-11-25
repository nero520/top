package com.shopkeeper.admin;

import com.shopkeeper.Config;
import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;
import com.taobao.api.internal.stream.TopCometStreamRequest;
import com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener;
import com.taobao.api.internal.stream.message.TopCometMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-24
 * Time: 上午8:28
 * To change this template use File | Settings | File Templates.
 */
public class TopCometManager
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static TopCometManager _instance = null;
    private TopCometStream topCometStream = null;

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
        conf.setConnectUrl("http://stream.api.tbsandbox.com/stream");
        topCometStream = new TopCometStreamFactory(conf).getInstance();
        topCometStream.setConnectionListener(new MyConnectionLifeCycleListener());
        topCometStream.setMessageListener(new MyTopCometMessageListener());
        topCometStream.start();
    }

    public void addNewStream(Long userId) {
        TopCometStreamRequest request = new TopCometStreamRequest(Config.TOP_APP_KEY, Config.TOP_APP_SECRET, userId.toString(), null);
        topCometStream.addNewStreamClient(request);
    }

    class MyConnectionLifeCycleListener implements ConnectionLifeCycleListener {

        @Override
        public void onBeforeConnect() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onException(Throwable e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onMaxReadTimeoutException() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    class MyTopCometMessageListener implements TopCometMessageListener {

        @Override
        public void onConnectMsg(String message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onHeartBeat() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onReceiveMsg(String message) {
            logger.info(message);
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onDiscardMsg(String message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onServerUpgrade(String message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onServerRehash() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onServerKickOff() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onClientKickOff() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onOtherMsg(String message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onException(Exception e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
