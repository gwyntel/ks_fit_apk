package com.xiaomi.push;

import android.content.Context;
import android.database.Cursor;
import com.xiaomi.push.cr;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class cm extends cr.b<Long> {

    /* renamed from: a, reason: collision with root package name */
    private long f23538a;

    /* renamed from: a, reason: collision with other field name */
    private String f238a;

    public cm(String str, List<String> list, String str2, String[] strArr, String str3, String str4, String str5, int i2, String str6) {
        super(str, list, str2, strArr, str3, str4, str5, i2);
        this.f23538a = 0L;
        this.f238a = str6;
    }

    public static cm a(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("count(*)");
        return new cm(str, arrayList, null, null, null, null, null, 0, "job to get count of all message");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaomi.push.cr.b
    public Long a(Context context, Cursor cursor) {
        return Long.valueOf(cursor.getLong(0));
    }

    @Override // com.xiaomi.push.cr.b
    public void a(Context context, List<Long> list) {
        if (context == null || list == null || list.size() <= 0) {
            return;
        }
        this.f23538a = list.get(0).longValue();
    }

    @Override // com.xiaomi.push.cr.a
    /* renamed from: a */
    public Object mo255a() {
        return Long.valueOf(this.f23538a);
    }
}
