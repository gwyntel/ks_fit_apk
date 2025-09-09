package com.umeng.commonsdk.statistics.noise;

import android.content.Context;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.statistics.a;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.c;
import com.umeng.commonsdk.statistics.idtracking.Envelope;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.internal.d;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
public class ImLatent implements d {
    private static ImLatent instanse;
    private Context context;
    private StatTracer statTracer;
    private c storeHelper;
    private final int _DEFAULT_HOURS = 360;
    private final int _DEFAULT_MIN_HOURS = 36;
    private final int _DEFAULT_MIN_LATENT = 1;
    private final int _DEFAULT_MAX_LATENT = 1800;
    private final long _ONE_HOURS_IN_MS = 3600000;
    private final long _360HOURS_IN_MS = 1296000000;
    private final long _36HOURS_IN_MS = 129600000;
    private final int LATENT_MAX = DNSConstants.ANNOUNCED_RENEWAL_TTL_INTERVAL;
    private final int LATENT_WINDOW = 10;
    private long latentHour = 1296000000;
    private int latentWindow = 10;
    private long mDelay = 0;
    private long mElapsed = 0;
    private boolean mLatentActivite = false;
    private Object mLatentLock = new Object();

    private ImLatent(Context context, StatTracer statTracer) {
        this.context = context;
        this.storeHelper = c.a(context);
        this.statTracer = statTracer;
    }

    public static synchronized ImLatent getService(Context context, StatTracer statTracer) {
        try {
            if (instanse == null) {
                ImLatent imLatent = new ImLatent(context, statTracer);
                instanse = imLatent;
                imLatent.onImprintChanged(ImprintHandler.getImprintService(context).c());
            }
        } catch (Throwable th) {
            throw th;
        }
        return instanse;
    }

    public long getDelayTime() {
        long j2;
        synchronized (this.mLatentLock) {
            j2 = this.mDelay;
        }
        return j2;
    }

    public long getElapsedTime() {
        return this.mElapsed;
    }

    public boolean isLatentActivite() {
        boolean z2;
        synchronized (this.mLatentLock) {
            z2 = this.mLatentActivite;
        }
        return z2;
    }

    public void latentDeactivite() {
        synchronized (this.mLatentLock) {
            this.mLatentActivite = false;
        }
    }

    @Override // com.umeng.commonsdk.statistics.internal.d
    public void onImprintChanged(ImprintHandler.a aVar) {
        this.latentHour = (Integer.valueOf(aVar.a("latent_hours", String.valueOf(360))).intValue() > 36 ? r1 : 360) * 3600000;
        int iIntValue = Integer.valueOf(aVar.a(bc.aY, "0")).intValue();
        if (iIntValue < 1 || iIntValue > 1800) {
            iIntValue = 0;
        }
        if (iIntValue != 0) {
            this.latentWindow = iIntValue;
            return;
        }
        int i2 = a.f22321c;
        if (i2 <= 0 || i2 > 1800000) {
            this.latentWindow = 10;
        } else {
            this.latentWindow = i2;
        }
    }

    public boolean shouldStartLatency() {
        if (this.storeHelper.c() || this.statTracer.isFirstRequest()) {
            return false;
        }
        synchronized (this.mLatentLock) {
            try {
                if (this.mLatentActivite) {
                    return false;
                }
                long jCurrentTimeMillis = System.currentTimeMillis() - this.statTracer.getLastReqTime();
                if (jCurrentTimeMillis > this.latentHour) {
                    String signature = Envelope.getSignature(this.context);
                    synchronized (this.mLatentLock) {
                        this.mDelay = DataHelper.random(this.latentWindow, signature);
                        this.mElapsed = jCurrentTimeMillis;
                        this.mLatentActivite = true;
                    }
                    return true;
                }
                if (jCurrentTimeMillis <= 129600000) {
                    return false;
                }
                synchronized (this.mLatentLock) {
                    this.mDelay = 0L;
                    this.mElapsed = jCurrentTimeMillis;
                    this.mLatentActivite = true;
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
