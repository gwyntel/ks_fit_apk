package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;

/* loaded from: classes4.dex */
public class ei implements LoggerInterface {

    /* renamed from: a, reason: collision with root package name */
    private LoggerInterface f23656a;

    /* renamed from: b, reason: collision with root package name */
    private LoggerInterface f23657b;

    public ei(LoggerInterface loggerInterface, LoggerInterface loggerInterface2) {
        this.f23656a = loggerInterface;
        this.f23657b = loggerInterface2;
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str) {
        LoggerInterface loggerInterface = this.f23656a;
        if (loggerInterface != null) {
            loggerInterface.log(str);
        }
        LoggerInterface loggerInterface2 = this.f23657b;
        if (loggerInterface2 != null) {
            loggerInterface2.log(str);
        }
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void setTag(String str) {
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str, Throwable th) {
        LoggerInterface loggerInterface = this.f23656a;
        if (loggerInterface != null) {
            loggerInterface.log(str, th);
        }
        LoggerInterface loggerInterface2 = this.f23657b;
        if (loggerInterface2 != null) {
            loggerInterface2.log(str, th);
        }
    }
}
