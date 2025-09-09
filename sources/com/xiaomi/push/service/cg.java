package com.xiaomi.push.service;

import com.xiaomi.push.in;

/* loaded from: classes4.dex */
/* synthetic */ class cg {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f24575a;

    static {
        int[] iArr = new int[in.values().length];
        f24575a = iArr;
        try {
            iArr[in.Registration.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f24575a[in.UnRegistration.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f24575a[in.Subscription.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f24575a[in.UnSubscription.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f24575a[in.SendMessage.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f24575a[in.AckMessage.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f24575a[in.SetConfig.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f24575a[in.ReportFeedback.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f24575a[in.Notification.ordinal()] = 9;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f24575a[in.Command.ordinal()] = 10;
        } catch (NoSuchFieldError unused10) {
        }
    }
}
