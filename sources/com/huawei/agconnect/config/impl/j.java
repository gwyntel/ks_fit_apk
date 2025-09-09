package com.huawei.agconnect.config.impl;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
class j implements d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f15613a;

    /* renamed from: b, reason: collision with root package name */
    private final String f15614b;

    j(Context context, String str) {
        this.f15613a = context;
        this.f15614b = str;
    }

    private static String a(String str) {
        try {
            return "agc_" + Hex.encodeHexString(a(str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            return "";
        }
    }

    @Override // com.huawei.agconnect.config.impl.d
    public String a(String str, String str2) {
        int identifier;
        String strA = a(str);
        if (TextUtils.isEmpty(strA) || (identifier = this.f15613a.getResources().getIdentifier(strA, "string", this.f15614b)) == 0) {
            return str2;
        }
        try {
            return this.f15613a.getResources().getString(identifier);
        } catch (Resources.NotFoundException unused) {
            return str2;
        }
    }

    private static byte[] a(byte[] bArr) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(bArr);
    }
}
