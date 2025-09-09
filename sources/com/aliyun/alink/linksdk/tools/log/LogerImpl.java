package com.aliyun.alink.linksdk.tools.log;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class LogerImpl implements ILogger {

    /* renamed from: a, reason: collision with root package name */
    private String f11458a;

    public LogerImpl(String str) {
        this.f11458a = str;
    }

    @Override // com.aliyun.alink.linksdk.tools.log.ILogger
    public void d(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(this.f11458a + str, str2);
    }

    @Override // com.aliyun.alink.linksdk.tools.log.ILogger
    public void e(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(this.f11458a + str, str2);
    }

    @Override // com.aliyun.alink.linksdk.tools.log.ILogger
    public void i(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.i(this.f11458a + str, str2);
    }

    @Override // com.aliyun.alink.linksdk.tools.log.ILogger
    public void w(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.w(this.f11458a + str, str2);
    }

    @Override // com.aliyun.alink.linksdk.tools.log.ILogger
    public void e(String str, String str2, Exception exc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(this.f11458a + str, str2, exc);
    }
}
