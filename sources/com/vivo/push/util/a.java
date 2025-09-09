package com.vivo.push.util;

import android.content.Context;
import android.util.Base64;
import com.yc.utesdk.utils.close.AESUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: c, reason: collision with root package name */
    private static volatile a f23222c;

    /* renamed from: a, reason: collision with root package name */
    private byte[] f23223a;

    /* renamed from: b, reason: collision with root package name */
    private byte[] f23224b;

    private a(Context context) {
        w.b().a(ContextDelegate.getContext(context));
        w wVarB = w.b();
        this.f23223a = wVarB.c();
        this.f23224b = wVarB.d();
    }

    public static a a(Context context) {
        if (f23222c == null) {
            synchronized (a.class) {
                try {
                    if (f23222c == null) {
                        f23222c = new a(context.getApplicationContext());
                    }
                } finally {
                }
            }
        }
        return f23222c;
    }

    public final String b(String str) throws Exception {
        return new String(f.a(f.a(a()), f.a(b()), Base64.decode(str, 2)), "utf-8");
    }

    private byte[] b() {
        byte[] bArr = this.f23224b;
        return (bArr == null || bArr.length <= 0) ? w.b().d() : bArr;
    }

    public final String a(String str) throws Exception {
        String strA = f.a(a());
        String strA2 = f.a(b());
        byte[] bytes = str.getBytes("utf-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(strA2.getBytes("utf-8"), AESUtils.AES);
        Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
        cipher.init(1, secretKeySpec, new IvParameterSpec(strA.getBytes("utf-8")));
        return Base64.encodeToString(cipher.doFinal(bytes), 2);
    }

    private byte[] a() {
        byte[] bArr = this.f23223a;
        return (bArr == null || bArr.length <= 0) ? w.b().c() : bArr;
    }
}
