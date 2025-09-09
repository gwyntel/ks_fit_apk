package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.cr;

/* loaded from: classes4.dex */
public class cl extends cn {
    public cl(String str, String str2, String[] strArr, String str3) {
        super(str, str2, strArr, str3);
    }

    public static cl a(Context context, String str, int i2) {
        com.xiaomi.channel.commonutils.logger.b.b("delete  messages when db size is too bigger");
        String strM254a = cr.a(context).m254a(str);
        if (TextUtils.isEmpty(strM254a)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("rowDataId in (select ");
        sb.append("rowDataId from " + strM254a);
        sb.append(" order by createTimeStamp asc");
        sb.append(" limit ?)");
        return new cl(str, sb.toString(), new String[]{String.valueOf(i2)}, "a job build to delete history message");
    }

    private void a(long j2) {
        String[] strArr = ((cr.d) this).f252a;
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        strArr[0] = String.valueOf(j2);
    }

    @Override // com.xiaomi.push.cr.a
    public void a(Context context, Object obj) {
        if (obj instanceof Long) {
            long jLongValue = ((Long) obj).longValue();
            long jA = cx.a(m256a());
            long j2 = cj.f235a;
            if (jA > j2) {
                long j3 = (long) ((((jA - j2) * 1.2d) / j2) * jLongValue);
                a(j3);
                cf.a(context).a("begin delete " + j3 + "noUpload messages , because db size is " + jA + "B");
                super.a(context, obj);
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.b("db size is suitable");
        }
    }
}
