package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class zzft {
    private static HashMap<String, String> zzh;
    private static Object zzm;
    private static boolean zzn;
    public static final Uri zza = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzd = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzb = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzc = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zze = new AtomicBoolean();

    @Nullable
    private static ContentResolver zzf = null;

    @Nullable
    private static zza zzg = null;
    private static final HashMap<String, Boolean> zzi = new HashMap<>(16, 1.0f);
    private static final HashMap<String, Integer> zzj = new HashMap<>(16, 1.0f);
    private static final HashMap<String, Long> zzk = new HashMap<>(16, 1.0f);
    private static final HashMap<String, Float> zzl = new HashMap<>(16, 1.0f);
    private static String[] zzo = new String[0];

    public interface zza {
    }

    public interface zzb<T extends Map<String, String>> {
        T zza(int i2);
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzft.class) {
            try {
                if (zzh == null) {
                    zze.set(false);
                    zzh = new HashMap<>(16, 1.0f);
                    zzm = new Object();
                    zzn = false;
                    contentResolver.registerContentObserver(zza, true, new zzfs(null));
                } else if (zze.getAndSet(false)) {
                    zzh.clear();
                    zzi.clear();
                    zzj.clear();
                    zzk.clear();
                    zzl.clear();
                    zzm = new Object();
                    zzn = false;
                }
                Object obj = zzm;
                if (zzh.containsKey(str)) {
                    String str3 = zzh.get(str);
                    return str3 != null ? str3 : null;
                }
                for (String str4 : zzo) {
                    if (str.startsWith(str4)) {
                        if (!zzn) {
                            HashMap<String, String> map = (HashMap) zza(contentResolver, zzo, new zzfu());
                            if (map != null) {
                                if (!map.isEmpty()) {
                                    Set<String> setKeySet = map.keySet();
                                    setKeySet.removeAll(zzi.keySet());
                                    setKeySet.removeAll(zzj.keySet());
                                    setKeySet.removeAll(zzk.keySet());
                                    setKeySet.removeAll(zzl.keySet());
                                }
                                if (!map.isEmpty()) {
                                    if (zzh.isEmpty()) {
                                        zzh = map;
                                    } else {
                                        zzh.putAll(map);
                                    }
                                }
                                zzn = true;
                            }
                            if (zzh.containsKey(str)) {
                                String str5 = zzh.get(str);
                                return str5 != null ? str5 : null;
                            }
                        }
                        return null;
                    }
                }
                Cursor cursorQuery = contentResolver.query(zza, null, null, new String[]{str}, null);
                if (cursorQuery == null) {
                    if (cursorQuery != null) {
                    }
                    return null;
                }
                try {
                    if (!cursorQuery.moveToFirst()) {
                        zza(obj, str, (String) null);
                        return null;
                    }
                    String string = cursorQuery.getString(1);
                    if (string != null && string.equals(null)) {
                        string = null;
                    }
                    zza(obj, str, string);
                    if (string != null) {
                        return string;
                    }
                    return null;
                } finally {
                    cursorQuery.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static <T extends Map<String, String>> T zza(ContentResolver contentResolver, String[] strArr, zzb<T> zzbVar) {
        Cursor cursorQuery = contentResolver.query(zzd, null, null, strArr, null);
        if (cursorQuery == null) {
            return null;
        }
        T t2 = (T) zzbVar.zza(cursorQuery.getCount());
        while (cursorQuery.moveToNext()) {
            try {
                t2.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } finally {
                cursorQuery.close();
            }
        }
        return t2;
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzft.class) {
            try {
                if (obj == zzm) {
                    zzh.put(str, str2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
