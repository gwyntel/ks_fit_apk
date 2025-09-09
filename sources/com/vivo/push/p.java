package com.vivo.push;

import android.net.Uri;
import androidx.media3.common.PlaybackException;

/* loaded from: classes4.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    public static final Uri f23194a = Uri.parse("content://com.vivo.push.sdk.service.SystemPushConfig/config");

    /* renamed from: b, reason: collision with root package name */
    public static final Uri f23195b = Uri.parse("content://com.vivo.push.sdk.service.SystemPushConfig/permission");

    /* renamed from: c, reason: collision with root package name */
    public static final Uri f23196c = Uri.parse("content://com.vivo.push.sdk.service.SystemPushConfig/clientState");

    /* renamed from: d, reason: collision with root package name */
    public static final Uri f23197d = Uri.parse("content://com.vivo.push.sdk.service.SystemPushConfig/debugInfo");

    public static String a(int i2) {
        switch (i2) {
            case 2002:
                return "method_alias_bind";
            case 2003:
                return "method_alias_unbind";
            case 2004:
                return "method_tag_bind";
            case 2005:
                return "method_tag_unbind";
            case PlaybackException.ERROR_CODE_IO_NO_PERMISSION /* 2006 */:
                return "method_sdk_bind";
            case PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED /* 2007 */:
                return "method_sdk_unbind";
            case 2008:
                return "method_stop";
            default:
                return null;
        }
    }
}
