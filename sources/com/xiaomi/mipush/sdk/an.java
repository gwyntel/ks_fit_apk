package com.xiaomi.mipush.sdk;

import com.xiaomi.push.in;

/* loaded from: classes4.dex */
/* synthetic */ class an {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f23379a;

    static {
        int[] iArr = new int[in.values().length];
        f23379a = iArr;
        try {
            iArr[in.SendMessage.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f23379a[in.Registration.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f23379a[in.UnRegistration.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f23379a[in.Subscription.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f23379a[in.UnSubscription.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f23379a[in.Command.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f23379a[in.Notification.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
    }
}
