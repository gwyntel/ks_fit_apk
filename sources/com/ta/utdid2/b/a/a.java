package com.ta.utdid2.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.ta.a.c.f;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private SharedPreferences f20033a;

    public a(Context context, String str, String str2) {
        this.f20033a = null;
        if (context == null) {
            return;
        }
        this.f20033a = context.getSharedPreferences(str2, 0);
    }

    public int a() {
        SharedPreferences sharedPreferences = this.f20033a;
        int i2 = sharedPreferences != null ? sharedPreferences.getInt("type", 0) : 0;
        f.m77a("PersistentConfiguration", "getTypeFromSp type", Integer.valueOf(i2));
        return i2;
    }

    public String k() {
        SharedPreferences sharedPreferences = this.f20033a;
        String string = sharedPreferences != null ? sharedPreferences.getString("UTDID2", "") : "";
        f.m77a("PersistentConfiguration", "getUtdidFromSp utdid", string);
        return string;
    }

    public void a(String str, int i2) {
        if (this.f20033a != null) {
            f.m77a("PersistentConfiguration", "writeUtdidToSp utdid", str);
            SharedPreferences.Editor editorEdit = this.f20033a.edit();
            editorEdit.putString("UTDID2", str);
            editorEdit.putInt("type", i2);
            if (this.f20033a.getLong("t2", 0L) == 0) {
                editorEdit.putLong("t2", System.currentTimeMillis());
            }
            try {
                editorEdit.commit();
            } catch (Exception unused) {
            }
        }
    }
}
