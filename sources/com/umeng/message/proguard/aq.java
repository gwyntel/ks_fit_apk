package com.umeng.message.proguard;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.umeng.message.common.UPLog;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;

/* loaded from: classes4.dex */
public final class aq {

    /* renamed from: a, reason: collision with root package name */
    private final String f22785a;

    public aq(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str + OpenAccountUIConstants.UNDER_LINE;
        }
        this.f22785a = str;
    }

    private String b(String str) {
        return this.f22785a + str;
    }

    private static String c(String str, String str2) {
        Application applicationA;
        Cursor cursorQuery = null;
        try {
            try {
                applicationA = x.a();
            } catch (Exception e2) {
                UPLog.e("KV", e2);
                if (0 != 0) {
                }
            }
            if (f.b(applicationA)) {
                return ap.a().f22784a.getString(str, str2);
            }
            cursorQuery = applicationA.getContentResolver().query(h.c(applicationA), null, null, new String[]{str}, null);
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Throwable unused) {
                    }
                }
                return str2;
            }
            if (cursorQuery.moveToFirst()) {
                str2 = cursorQuery.getString(cursorQuery.getColumnIndex("v"));
            }
            try {
                cursorQuery.close();
            } catch (Throwable unused2) {
            }
            return str2;
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    cursorQuery.close();
                } catch (Throwable unused3) {
                }
            }
            throw th;
        }
    }

    private static void d(String str, String str2) {
        try {
            Application applicationA = x.a();
            if (f.b(applicationA)) {
                ap.a().a(str, str2);
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("k", str);
            contentValues.put("v", str2);
            applicationA.getContentResolver().insert(h.c(applicationA), contentValues);
        } catch (Exception e2) {
            UPLog.e("KV", e2);
        }
    }

    public final void a(String str, int i2) {
        d(b(str), String.valueOf(i2));
    }

    public final void a(String str, long j2) {
        d(b(str), String.valueOf(j2));
    }

    public final Set<String> b(String str, Set<String> set) {
        try {
            String strC = c(b(str), "");
            if (!TextUtils.isEmpty(strC)) {
                HashSet hashSet = new HashSet();
                JSONArray jSONArray = new JSONArray(strC);
                int length = jSONArray.length();
                if (length > 0) {
                    for (int i2 = 0; i2 < length; i2++) {
                        hashSet.add(jSONArray.optString(i2));
                    }
                }
                return hashSet;
            }
        } catch (Throwable th) {
            UPLog.e("KV", th);
        }
        return set;
    }

    public final void a(String str, String str2) {
        d(b(str), String.valueOf(str2));
    }

    public final void a(String str, boolean z2) {
        d(b(str), String.valueOf(z2));
    }

    public final void a(String str, Set<String> set) {
        try {
            JSONArray jSONArray = new JSONArray();
            if (set != null) {
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    jSONArray.put(it.next());
                }
            }
            d(b(str), jSONArray.toString());
        } catch (Throwable th) {
            UPLog.e("KV", th);
        }
    }

    public final int b(String str, int i2) {
        String strC = c(b(str), String.valueOf(i2));
        if (!TextUtils.isEmpty(strC)) {
            try {
                return Integer.parseInt(strC);
            } catch (Exception e2) {
                UPLog.e("KV", e2);
            }
        }
        return i2;
    }

    public final void a(String str) {
        c(b(str));
    }

    public final long b(String str, long j2) {
        String strC = c(b(str), String.valueOf(j2));
        if (!TextUtils.isEmpty(strC)) {
            try {
                return Long.parseLong(strC);
            } catch (Exception e2) {
                UPLog.e("KV", e2);
            }
        }
        return j2;
    }

    private static void c(String str) {
        try {
            Application applicationA = x.a();
            if (f.b(applicationA)) {
                ap.a().a(str);
                return;
            }
            applicationA.getContentResolver().delete(h.c(applicationA), null, new String[]{str});
        } catch (Throwable th) {
            UPLog.e("KV", th);
        }
    }

    public final boolean b(String str, boolean z2) {
        String strC = c(b(str), String.valueOf(z2));
        if (!TextUtils.isEmpty(strC)) {
            try {
                return Boolean.parseBoolean(strC);
            } catch (Exception e2) {
                UPLog.e("KV", e2);
            }
        }
        return z2;
    }

    public final String b(String str, String str2) {
        return c(b(str), str2);
    }
}
