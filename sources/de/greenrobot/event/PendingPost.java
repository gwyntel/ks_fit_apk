package de.greenrobot.event;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
final class PendingPost {
    private static final List<PendingPost> pendingPostPool = new ArrayList();

    /* renamed from: a, reason: collision with root package name */
    Object f24975a;

    /* renamed from: b, reason: collision with root package name */
    Subscription f24976b;

    /* renamed from: c, reason: collision with root package name */
    PendingPost f24977c;

    private PendingPost(Object obj, Subscription subscription) {
        this.f24975a = obj;
        this.f24976b = subscription;
    }

    static PendingPost a(Subscription subscription, Object obj) {
        List<PendingPost> list = pendingPostPool;
        synchronized (list) {
            try {
                int size = list.size();
                if (size <= 0) {
                    return new PendingPost(obj, subscription);
                }
                PendingPost pendingPostRemove = list.remove(size - 1);
                pendingPostRemove.f24975a = obj;
                pendingPostRemove.f24976b = subscription;
                pendingPostRemove.f24977c = null;
                return pendingPostRemove;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static void b(PendingPost pendingPost) {
        pendingPost.f24975a = null;
        pendingPost.f24976b = null;
        pendingPost.f24977c = null;
        List<PendingPost> list = pendingPostPool;
        synchronized (list) {
            try {
                if (list.size() < 10000) {
                    list.add(pendingPost);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
