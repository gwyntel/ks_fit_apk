package com.taobao.accs.net;

import androidx.media3.exoplayer.ExoPlayer;
import anet.channel.strategy.dispatch.DispatchEvent;
import anet.channel.strategy.dispatch.HttpDispatcher;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
class i implements HttpDispatcher.IDispatchEventListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ h f20227a;

    i(h hVar) {
        this.f20227a = hVar;
    }

    @Override // anet.channel.strategy.dispatch.HttpDispatcher.IDispatchEventListener
    public void onEvent(DispatchEvent dispatchEvent) {
        ThreadPoolExecutorFactory.schedule(new j(this), ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }
}
