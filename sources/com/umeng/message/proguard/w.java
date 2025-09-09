package com.umeng.message.proguard;

import android.text.TextUtils;
import com.umeng.message.entity.UMessage;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public class w {

    /* renamed from: b, reason: collision with root package name */
    private static volatile w f22945b;

    /* renamed from: a, reason: collision with root package name */
    private final LinkedList<ac> f22946a = new LinkedList<>();

    private w() {
    }

    public static w a() {
        if (f22945b == null) {
            synchronized (w.class) {
                try {
                    if (f22945b == null) {
                        f22945b = new w();
                    }
                } finally {
                }
            }
        }
        return f22945b;
    }

    public final ac b() {
        ac acVarPollFirst;
        synchronized (this.f22946a) {
            acVarPollFirst = this.f22946a.pollFirst();
        }
        return acVarPollFirst;
    }

    public final int c() {
        int size;
        synchronized (this.f22946a) {
            size = this.f22946a.size();
        }
        return size;
    }

    public final void b(ac acVar) {
        synchronized (this.f22946a) {
            this.f22946a.remove(acVar);
        }
    }

    public final void a(ac acVar) {
        synchronized (this.f22946a) {
            this.f22946a.addLast(acVar);
        }
    }

    public final ac a(String str) {
        synchronized (this.f22946a) {
            try {
                Iterator<ac> it = this.f22946a.iterator();
                while (it.hasNext()) {
                    ac next = it.next();
                    UMessage uMessage = next.f22710b;
                    if (uMessage != null && TextUtils.equals(str, uMessage.getMsgId())) {
                        return next;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
