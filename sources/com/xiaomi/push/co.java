package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.xiaomi.push.cr;

/* loaded from: classes4.dex */
public class co extends cr.e {

    /* renamed from: a, reason: collision with root package name */
    private String f23540a;

    public co(String str, ContentValues contentValues, String str2) {
        super(str, contentValues);
        this.f23540a = str2;
    }

    public static co a(Context context, String str, ir irVar) {
        byte[] bArrA = jx.a(irVar);
        if (bArrA == null || bArrA.length <= 0) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", (Integer) 0);
        contentValues.put(TmpConstant.RRESPONSE_MESSAGEID, "");
        contentValues.put("messageItemId", irVar.d());
        contentValues.put("messageItem", bArrA);
        contentValues.put("appId", cf.a(context).b());
        contentValues.put("packageName", cf.a(context).m253a());
        contentValues.put("createTimeStamp", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("uploadTimestamp", (Integer) 0);
        return new co(str, contentValues, "a job build to insert message to db");
    }
}
