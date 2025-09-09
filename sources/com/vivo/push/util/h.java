package com.vivo.push.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import com.vivo.push.model.InsideNotificationItem;

/* loaded from: classes4.dex */
public final class h implements BaseNotifyDataAdapter {

    /* renamed from: e, reason: collision with root package name */
    private static int f23232e;

    /* renamed from: f, reason: collision with root package name */
    private static int f23233f;

    /* renamed from: a, reason: collision with root package name */
    private Resources f23234a;

    /* renamed from: b, reason: collision with root package name */
    private String f23235b;

    /* renamed from: c, reason: collision with root package name */
    private String f23236c;

    /* renamed from: d, reason: collision with root package name */
    private String f23237d;

    private static boolean a(int i2) {
        return (i2 == -1 || i2 == 0) ? false : true;
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final int getDefaultNotifyIcon() {
        if (a(f23232e)) {
            return f23232e;
        }
        String str = this.f23237d;
        int iA = !a(str) ? -1 : a(str, "_notifyicon");
        f23232e = iA;
        if (a(iA)) {
            return f23232e;
        }
        for (String strSubstring = this.f23236c; !TextUtils.isEmpty(strSubstring); strSubstring = strSubstring.substring(0, strSubstring.length() - 1)) {
            int identifier = this.f23234a.getIdentifier("vivo_push_rom" + strSubstring + "_notifyicon", "drawable", this.f23235b);
            if (identifier > 0) {
                return identifier;
            }
        }
        return this.f23234a.getIdentifier("vivo_push_notifyicon", "drawable", this.f23235b);
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final int getDefaultSmallIconId() {
        if (a(f23233f)) {
            return f23233f;
        }
        String str = this.f23237d;
        int iA = !a(str) ? -1 : a(str, "_icon");
        f23233f = iA;
        if (a(iA)) {
            return f23233f;
        }
        for (String strSubstring = this.f23236c; !TextUtils.isEmpty(strSubstring); strSubstring = strSubstring.substring(0, strSubstring.length() - 1)) {
            int identifier = this.f23234a.getIdentifier("vivo_push_rom" + strSubstring + "_icon", "drawable", this.f23235b);
            if (identifier > 0) {
                return identifier;
            }
        }
        return this.f23234a.getIdentifier("vivo_push_icon", "drawable", this.f23235b);
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final int getNotifyMode(InsideNotificationItem insideNotificationItem) {
        return 2;
    }

    @Override // com.vivo.push.util.BaseNotifyDataAdapter
    public final void init(Context context) {
        this.f23235b = context.getPackageName();
        this.f23234a = context.getResources();
        this.f23236c = j.a();
        this.f23237d = Build.VERSION.RELEASE;
    }

    private static boolean a(String str) {
        if (Build.VERSION.SDK_INT < 26) {
            return false;
        }
        if (!TextUtils.isEmpty(str)) {
            return true;
        }
        p.d("DefaultNotifyDataAdapter", "systemVersion is not suit ");
        return false;
    }

    private int a(String str, String str2) throws NumberFormatException {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit != null && strArrSplit.length > 0) {
                str = strArrSplit[0];
            }
            try {
                for (int i2 = Integer.parseInt(str); i2 > 0; i2--) {
                    String str3 = "vivo_push_ard" + i2 + str2;
                    p.c("DefaultNotifyDataAdapter", "get notify icon : ".concat(String.valueOf(str3)));
                    int identifier = this.f23234a.getIdentifier(str3, "drawable", this.f23235b);
                    if (identifier > 0) {
                        p.c("DefaultNotifyDataAdapter", "find notify icon : ".concat(String.valueOf(str3)));
                        return identifier;
                    }
                }
            } catch (Exception e2) {
                p.a("DefaultNotifyDataAdapter", e2);
            }
        }
        return -1;
    }
}
