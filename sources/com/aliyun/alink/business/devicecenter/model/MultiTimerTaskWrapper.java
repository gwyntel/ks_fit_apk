package com.aliyun.alink.business.devicecenter.model;

import com.aliyun.alink.business.devicecenter.channel.http.RetryTransitoryClient;
import com.aliyun.alink.business.devicecenter.discover.DiscoverChainProcessor;
import com.aliyun.alink.business.devicecenter.discover.IDiscoverChain;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;
import java.util.concurrent.ScheduledFuture;

/* loaded from: classes2.dex */
public class MultiTimerTaskWrapper {

    /* renamed from: a, reason: collision with root package name */
    public DiscoverChainProcessor f10514a;

    /* renamed from: b, reason: collision with root package name */
    public IDiscoverChain f10515b;

    /* renamed from: c, reason: collision with root package name */
    public TimerUtils f10516c;

    /* renamed from: d, reason: collision with root package name */
    public ScheduledFuture f10517d;

    /* renamed from: e, reason: collision with root package name */
    public RetryTransitoryClient f10518e;

    public MultiTimerTaskWrapper(DiscoverChainProcessor discoverChainProcessor, TimerUtils timerUtils, ScheduledFuture scheduledFuture) {
        this.f10515b = null;
        this.f10518e = null;
        this.f10514a = discoverChainProcessor;
        this.f10516c = timerUtils;
        this.f10517d = scheduledFuture;
    }

    public void cancelTimerTask(int i2) {
        ALog.d("MultiTimerTaskWrapper", "cancelTimerTask() called with: messageId = [" + i2 + "]， timerTask=" + this.f10516c + ", chainProcessor=" + this.f10514a + ", scheduledFutureTask=" + this.f10517d + ", cloudDiscoverChain=" + this.f10515b + ", retryTransitoryClient=" + this.f10518e);
        try {
            TimerUtils timerUtils = this.f10516c;
            if (timerUtils != null) {
                timerUtils.stop(i2);
                this.f10516c.setCallback(null);
            }
        } catch (Exception unused) {
        }
        try {
            DiscoverChainProcessor discoverChainProcessor = this.f10514a;
            if (discoverChainProcessor != null) {
                discoverChainProcessor.stopDiscover();
                this.f10514a = null;
            }
        } catch (Exception unused2) {
        }
        try {
            ScheduledFuture scheduledFuture = this.f10517d;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                this.f10517d = null;
            }
        } catch (Exception unused3) {
        }
        try {
            IDiscoverChain iDiscoverChain = this.f10515b;
            if (iDiscoverChain != null) {
                iDiscoverChain.stopDiscover();
                this.f10515b = null;
            }
        } catch (Exception unused4) {
        }
        try {
            RetryTransitoryClient retryTransitoryClient = this.f10518e;
            if (retryTransitoryClient != null) {
                retryTransitoryClient.cancelRequest();
                this.f10518e = null;
            }
        } catch (Exception unused5) {
        }
    }

    public String toString() {
        return "{\"chainProcessor\":\"" + this.f10514a + "\",\"timerTask\":\"" + this.f10516c + "\",\"scheduledFutureTask\":\"" + this.f10517d + "\",\"cloudDiscoverChain\":\"" + this.f10515b + "\",\"retryTransitoryClient\":\"" + this.f10518e + "\"}";
    }

    public MultiTimerTaskWrapper(DiscoverChainProcessor discoverChainProcessor, TimerUtils timerUtils, ScheduledFuture scheduledFuture, IDiscoverChain iDiscoverChain, RetryTransitoryClient retryTransitoryClient) {
        this.f10514a = discoverChainProcessor;
        this.f10516c = timerUtils;
        this.f10517d = scheduledFuture;
        this.f10515b = iDiscoverChain;
        this.f10518e = retryTransitoryClient;
    }
}
