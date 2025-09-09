package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import com.xiaomi.push.is;
import com.xiaomi.push.it;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class az {

    /* renamed from: a, reason: collision with root package name */
    private static volatile az f24488a;

    /* renamed from: a, reason: collision with other field name */
    protected SharedPreferences f1025a;

    /* renamed from: a, reason: collision with other field name */
    private HashSet<a> f1026a = new HashSet<>();

    /* renamed from: b, reason: collision with root package name */
    protected SharedPreferences f24489b;

    public static abstract class a implements Runnable {
        private String mDescription;
        private int mId;

        public a(int i2, String str) {
            this.mId = i2;
            this.mDescription = str;
        }

        public boolean equals(Object obj) {
            return (obj instanceof a) && this.mId == ((a) obj).mId;
        }

        public int hashCode() {
            return this.mId;
        }

        protected abstract void onCallback();

        @Override // java.lang.Runnable
        public final void run() {
            onCallback();
        }
    }

    private az(Context context) {
        this.f1025a = context.getSharedPreferences("mipush_oc_normal", 0);
        this.f24489b = context.getSharedPreferences("mipush_oc_custom", 0);
    }

    public synchronized void a(a aVar) {
        if (!this.f1026a.contains(aVar)) {
            this.f1026a.add(aVar);
        }
    }

    void b() {
        com.xiaomi.channel.commonutils.logger.b.c("OC_Callback : receive new oc data");
        HashSet hashSet = new HashSet();
        synchronized (this) {
            hashSet.addAll(this.f1026a);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar != null) {
                aVar.run();
            }
        }
        hashSet.clear();
    }

    public synchronized void a() {
        this.f1026a.clear();
    }

    public static az a(Context context) {
        if (f24488a == null) {
            synchronized (az.class) {
                try {
                    if (f24488a == null) {
                        f24488a = new az(context);
                    }
                } finally {
                }
            }
        }
        return f24488a;
    }

    public void a(List<Pair<it, Integer>> list, List<Pair<Integer, Object>> list2) {
        if (!com.xiaomi.push.ac.a(list) && !com.xiaomi.push.ac.a(list2)) {
            SharedPreferences.Editor editorEdit = this.f1025a.edit();
            editorEdit.clear();
            for (Pair<it, Integer> pair : list) {
                Object obj = pair.first;
                if (obj != null && pair.second != null) {
                    editorEdit.putInt(a((it) obj), ((Integer) pair.second).intValue());
                }
            }
            for (Pair<Integer, Object> pair2 : list2) {
                Object obj2 = pair2.first;
                if (obj2 != null && pair2.second != null) {
                    a(editorEdit, pair2, a(((Integer) obj2).intValue()));
                }
            }
            editorEdit.apply();
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("not update oc, because versions or configs are empty");
    }

    public void a(List<Pair<Integer, Object>> list) {
        if (com.xiaomi.push.ac.a(list)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.f24489b.edit();
        for (Pair<Integer, Object> pair : list) {
            Object obj = pair.first;
            if (obj != null) {
                String strA = a(((Integer) obj).intValue());
                if (pair.second == null) {
                    editorEdit.remove(strA);
                } else {
                    a(editorEdit, pair, strA);
                }
            }
        }
        editorEdit.apply();
    }

    private void a(SharedPreferences.Editor editor, Pair<Integer, Object> pair, String str) {
        Object obj = pair.second;
        if (obj instanceof Integer) {
            editor.putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Long) {
            editor.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof String) {
            String str2 = (String) obj;
            if (str.equals(a(is.AppIsInstalledList.a()))) {
                editor.putString(str, com.xiaomi.push.bm.a(str2));
                return;
            } else {
                editor.putString(str, str2);
                return;
            }
        }
        if (obj instanceof Boolean) {
            editor.putBoolean(str, ((Boolean) obj).booleanValue());
        }
    }

    public int a(int i2, int i3) {
        try {
            String strA = a(i2);
            if (this.f24489b.contains(strA)) {
                return this.f24489b.getInt(strA, 0);
            }
            return this.f1025a.contains(strA) ? this.f1025a.getInt(strA, 0) : i3;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a(i2 + " oc int error " + e2);
            return i3;
        }
    }

    public long a(int i2, long j2) {
        try {
            String strA = a(i2);
            if (this.f24489b.contains(strA)) {
                return this.f24489b.getLong(strA, 0L);
            }
            return this.f1025a.contains(strA) ? this.f1025a.getLong(strA, 0L) : j2;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a(i2 + " oc long error " + e2);
            return j2;
        }
    }

    public String a(int i2, String str) {
        try {
            String strA = a(i2);
            if (this.f24489b.contains(strA)) {
                return this.f24489b.getString(strA, null);
            }
            return this.f1025a.contains(strA) ? this.f1025a.getString(strA, null) : str;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a(i2 + " oc string error " + e2);
            return str;
        }
    }

    public boolean a(int i2, boolean z2) {
        try {
            String strA = a(i2);
            if (this.f24489b.contains(strA)) {
                return this.f24489b.getBoolean(strA, false);
            }
            return this.f1025a.contains(strA) ? this.f1025a.getBoolean(strA, false) : z2;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a(i2 + " oc boolean error " + e2);
            return z2;
        }
    }

    public int a(it itVar, int i2) {
        try {
            return this.f1025a.getInt(a(itVar), i2);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a(itVar + " version error " + e2);
            return i2;
        }
    }

    private String a(int i2) {
        return "oc_" + i2;
    }

    private String a(it itVar) {
        return "oc_version_" + itVar.a();
    }
}
