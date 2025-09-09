package com.google.android.gms.internal.auth;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class zzcb {

    /* renamed from: a, reason: collision with root package name */
    static HashMap f12995a;

    /* renamed from: f, reason: collision with root package name */
    static boolean f13000f;
    private static Object zzm;
    public static final Uri zza = Uri.parse("content://com.google.android.gsf.gservices");
    public static final Uri zzb = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzc = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzd = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzl = new AtomicBoolean();

    /* renamed from: b, reason: collision with root package name */
    static final HashMap f12996b = new HashMap(16, 1.0f);

    /* renamed from: c, reason: collision with root package name */
    static final HashMap f12997c = new HashMap(16, 1.0f);

    /* renamed from: d, reason: collision with root package name */
    static final HashMap f12998d = new HashMap(16, 1.0f);

    /* renamed from: e, reason: collision with root package name */
    static final HashMap f12999e = new HashMap(16, 1.0f);

    /* renamed from: g, reason: collision with root package name */
    static final String[] f13001g = new String[0];

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzcb.class) {
            try {
                String str3 = null;
                if (f12995a == null) {
                    zzl.set(false);
                    f12995a = new HashMap(16, 1.0f);
                    zzm = new Object();
                    f13000f = false;
                    contentResolver.registerContentObserver(zza, true, new zzca(null));
                } else if (zzl.getAndSet(false)) {
                    f12995a.clear();
                    f12996b.clear();
                    f12997c.clear();
                    f12998d.clear();
                    f12999e.clear();
                    zzm = new Object();
                    f13000f = false;
                }
                Object obj = zzm;
                if (f12995a.containsKey(str)) {
                    String str4 = (String) f12995a.get(str);
                    if (str4 != null) {
                        str3 = str4;
                    }
                    return str3;
                }
                int length = f13001g.length;
                Cursor cursorQuery = contentResolver.query(zza, null, null, new String[]{str}, null);
                if (cursorQuery == null) {
                    return null;
                }
                try {
                    if (!cursorQuery.moveToFirst()) {
                        zzc(obj, str, null);
                        return null;
                    }
                    String string = cursorQuery.getString(1);
                    if (string != null && string.equals(null)) {
                        string = null;
                    }
                    zzc(obj, str, string);
                    if (string != null) {
                        return string;
                    }
                    return null;
                } finally {
                    cursorQuery.close();
                }
            } finally {
            }
        }
    }

    private static void zzc(Object obj, String str, String str2) {
        synchronized (zzcb.class) {
            try {
                if (obj == zzm) {
                    f12995a.put(str, str2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
