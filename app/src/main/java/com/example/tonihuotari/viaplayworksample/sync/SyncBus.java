package com.example.tonihuotari.viaplayworksample.sync;

import com.squareup.otto.Bus;

public class SyncBus {
    private static Bus mInstance;

    public static synchronized Bus getBus() {
        if(mInstance == null) {
            mInstance = new Bus();
        }
        return mInstance;
    }

    public static class PageAvailableEvent {

        public String sectiondId;

        public PageAvailableEvent(String sectiondId) {
            this.sectiondId = sectiondId;
        }

        public boolean isRootPage() {
            return sectiondId == null;
        }
    }
}
