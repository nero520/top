package com.shopkeeper;

import com.rop.event.AfterStartedRopEvent;
import com.rop.event.RopEventListener;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-24
 * Time: 上午8:16
 * To change this template use File | Settings | File Templates.
 */

public class SamplePostInitializeEventListener implements RopEventListener<AfterStartedRopEvent> {

    @Override
    public void onRopEvent(AfterStartedRopEvent ropRopEvent) {
        System.out.println("execute SamplePostInitializeEventListener!");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}