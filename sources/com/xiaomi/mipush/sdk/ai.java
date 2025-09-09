package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.bm;
import com.xiaomi.push.in;
import com.xiaomi.push.jc;
import com.xiaomi.push.jd;
import com.xiaomi.push.je;
import com.xiaomi.push.ji;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.jo;
import com.xiaomi.push.jp;
import com.xiaomi.push.jq;
import com.xiaomi.push.js;
import com.xiaomi.push.ju;
import com.xiaomi.push.jw;
import com.xiaomi.push.jx;
import com.xiaomi.push.jy;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class ai {
    protected static <T extends jy<T, ?>> jj a(Context context, T t2, in inVar) {
        return a(context, t2, inVar, !inVar.equals(in.Registration), context.getPackageName(), b.m140a(context).m141a());
    }

    protected static <T extends jy<T, ?>> jj b(Context context, T t2, in inVar, boolean z2, String str, String str2) {
        return a(context, t2, inVar, z2, str, str2, false);
    }

    protected static <T extends jy<T, ?>> jj a(Context context, T t2, in inVar, boolean z2, String str, String str2) {
        return a(context, t2, inVar, z2, str, str2, true);
    }

    protected static <T extends jy<T, ?>> jj a(Context context, T t2, in inVar, boolean z2, String str, String str2, boolean z3) {
        byte[] bArrA = jx.a(t2);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("invoke convertThriftObjectToBytes method, return null.");
            return null;
        }
        jj jjVar = new jj();
        if (z2) {
            String strD = b.m140a(context).d();
            if (TextUtils.isEmpty(strD)) {
                com.xiaomi.channel.commonutils.logger.b.m91a("regSecret is empty, return null");
                return null;
            }
            try {
                bArrA = com.xiaomi.push.h.b(bm.m218a(strD), bArrA);
            } catch (Exception unused) {
                com.xiaomi.channel.commonutils.logger.b.d("encryption error. ");
            }
        }
        jc jcVar = new jc();
        jcVar.f669a = 5L;
        jcVar.f670a = "fakeid";
        jjVar.a(jcVar);
        jjVar.a(ByteBuffer.wrap(bArrA));
        jjVar.a(inVar);
        jjVar.b(z3);
        jjVar.b(str);
        jjVar.a(z2);
        jjVar.a(str2);
        return jjVar;
    }

    public static jy a(Context context, jj jjVar) {
        byte[] bArrM599a;
        if (jjVar.m601b()) {
            byte[] bArrA = i.a(context, jjVar, e.ASSEMBLE_PUSH_FCM);
            if (bArrA == null) {
                bArrA = bm.m218a(b.m140a(context).d());
            }
            try {
                bArrM599a = com.xiaomi.push.h.a(bArrA, jjVar.m599a());
            } catch (Exception e2) {
                throw new u("the aes decrypt failed.", e2);
            }
        } else {
            bArrM599a = jjVar.m599a();
        }
        jy jyVarA = a(jjVar.a(), jjVar.f749b);
        if (jyVarA != null) {
            jx.a(jyVarA, bArrM599a);
        }
        return jyVarA;
    }

    private static jy a(in inVar, boolean z2) {
        switch (aj.f23376a[inVar.ordinal()]) {
            case 1:
                return new jo();
            case 2:
                return new ju();
            case 3:
                return new js();
            case 4:
                return new jw();
            case 5:
                return new jq();
            case 6:
                return new jd();
            case 7:
                return new ji();
            case 8:
                return new jp();
            case 9:
                if (z2) {
                    return new jm();
                }
                je jeVar = new je();
                jeVar.a(true);
                return jeVar;
            case 10:
                return new ji();
            default:
                return null;
        }
    }
}
