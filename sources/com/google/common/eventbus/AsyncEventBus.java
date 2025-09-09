package com.google.common.eventbus;

import com.google.common.eventbus.EventBus;
import com.taobao.accs.AccsClientConfig;
import java.util.concurrent.Executor;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class AsyncEventBus extends EventBus {
    public AsyncEventBus(String str, Executor executor) {
        super(str, executor, Dispatcher.b(), EventBus.LoggingHandler.f14485a);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
        super(AccsClientConfig.DEFAULT_CONFIGTAG, executor, Dispatcher.b(), subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor) {
        super(AccsClientConfig.DEFAULT_CONFIGTAG, executor, Dispatcher.b(), EventBus.LoggingHandler.f14485a);
    }
}
