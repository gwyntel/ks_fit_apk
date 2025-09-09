package com.aliyun.alink.linksdk.tmp.device.c;

import com.aliyun.alink.linksdk.tmp.listener.IProvisionResponser;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class d implements IProvisionResponser {

    /* renamed from: g, reason: collision with root package name */
    private static final String f11393g = "[Tmp]ProvisionResponser";

    /* renamed from: a, reason: collision with root package name */
    protected ITResResponseCallback f11394a;

    /* renamed from: b, reason: collision with root package name */
    protected int f11395b;

    /* renamed from: c, reason: collision with root package name */
    protected AtomicInteger f11396c = new AtomicInteger(0);

    /* renamed from: d, reason: collision with root package name */
    protected boolean f11397d = true;

    /* renamed from: e, reason: collision with root package name */
    protected ErrorInfo f11398e;

    /* renamed from: f, reason: collision with root package name */
    protected Object f11399f;

    public d(ITResResponseCallback iTResResponseCallback, int i2) {
        this.f11394a = iTResResponseCallback;
        this.f11395b = i2;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IProvisionResponser
    public void onComplete(String str, ErrorInfo errorInfo, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        int iIncrementAndGet = this.f11396c.incrementAndGet();
        ALog.d(f11393g, "onComplete identifer :" + str + " Ret:" + this.f11397d + " finishedCount:" + iIncrementAndGet + " mListenerCount:" + this.f11395b + " errorInfo:" + errorInfo + " data:" + obj);
        if (errorInfo == null || errorInfo.getErrorCode() == 200) {
            z2 = true;
        } else {
            this.f11398e = errorInfo;
            this.f11399f = obj;
            z2 = false;
        }
        boolean z3 = z2 | this.f11397d;
        this.f11397d = z3;
        if (iIncrementAndGet >= this.f11395b) {
            if (z3) {
                this.f11399f = obj;
            }
            this.f11394a.onComplete(str, this.f11398e, this.f11399f);
        }
    }
}
