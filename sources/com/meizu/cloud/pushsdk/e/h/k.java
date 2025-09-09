package com.meizu.cloud.pushsdk.e.h;

import android.support.v4.media.session.PlaybackStateCompat;

/* loaded from: classes4.dex */
final class k {

    /* renamed from: a, reason: collision with root package name */
    private static j f19514a;

    /* renamed from: b, reason: collision with root package name */
    private static long f19515b;

    private k() {
    }

    static j a() {
        synchronized (k.class) {
            try {
                j jVar = f19514a;
                if (jVar == null) {
                    return new j();
                }
                f19514a = jVar.f19512f;
                jVar.f19512f = null;
                f19515b -= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                return jVar;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static void a(j jVar) {
        if (jVar.f19512f != null || jVar.f19513g != null) {
            throw new IllegalArgumentException();
        }
        if (jVar.f19510d) {
            return;
        }
        synchronized (k.class) {
            try {
                long j2 = f19515b + PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                if (j2 > PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                    return;
                }
                f19515b = j2;
                jVar.f19512f = f19514a;
                jVar.f19509c = 0;
                jVar.f19508b = 0;
                f19514a = jVar;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
