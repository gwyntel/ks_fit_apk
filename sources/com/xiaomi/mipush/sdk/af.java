package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class af {

    /* renamed from: a, reason: collision with root package name */
    private static volatile af f23369a;

    /* renamed from: a, reason: collision with other field name */
    private Context f115a;

    /* renamed from: a, reason: collision with other field name */
    private List<x> f116a = new ArrayList();

    private af(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f115a = applicationContext;
        if (applicationContext == null) {
            this.f115a = context;
        }
    }

    public static af a(Context context) {
        if (f23369a == null) {
            synchronized (af.class) {
                try {
                    if (f23369a == null) {
                        f23369a = new af(context);
                    }
                } finally {
                }
            }
        }
        return f23369a;
    }

    public void b(String str) {
        synchronized (this.f116a) {
            try {
                x xVar = new x();
                xVar.f157a = str;
                if (this.f116a.contains(xVar)) {
                    Iterator<x> it = this.f116a.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        x next = it.next();
                        if (xVar.equals(next)) {
                            xVar = next;
                            break;
                        }
                    }
                }
                xVar.f23426a++;
                this.f116a.remove(xVar);
                this.f116a.add(xVar);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void c(String str) {
        synchronized (this.f116a) {
            try {
                x xVar = new x();
                xVar.f157a = str;
                if (this.f116a.contains(xVar)) {
                    this.f116a.remove(xVar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m117a(String str) {
        synchronized (this.f116a) {
            try {
                x xVar = new x();
                xVar.f23426a = 0;
                xVar.f157a = str;
                if (this.f116a.contains(xVar)) {
                    this.f116a.remove(xVar);
                }
                this.f116a.add(xVar);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int a(String str) {
        synchronized (this.f116a) {
            try {
                x xVar = new x();
                xVar.f157a = str;
                if (this.f116a.contains(xVar)) {
                    for (x xVar2 : this.f116a) {
                        if (xVar2.equals(xVar)) {
                            return xVar2.f23426a;
                        }
                    }
                }
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m118a(String str) {
        synchronized (this.f116a) {
            try {
                x xVar = new x();
                xVar.f157a = str;
                return this.f116a.contains(xVar);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized String a(au auVar) {
        return this.f115a.getSharedPreferences("mipush_extra", 0).getString(auVar.name(), "");
    }

    public synchronized void a(au auVar, String str) {
        SharedPreferences sharedPreferences = this.f115a.getSharedPreferences("mipush_extra", 0);
        sharedPreferences.edit().putString(auVar.name(), str).apply();
    }
}
