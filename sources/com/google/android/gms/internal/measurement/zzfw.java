package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzfw implements zzgd {

    @GuardedBy("ConfigurationContentLoader.class")
    private static final Map<Uri, zzfw> zza = new ArrayMap();
    private static final String[] zzb = {KsProperty.Key, "value"};
    private final ContentResolver zzc;
    private final Uri zzd;
    private final Runnable zze;
    private final ContentObserver zzf;
    private final Object zzg;
    private volatile Map<String, String> zzh;

    @GuardedBy("this")
    private final List<zzgb> zzi;

    private zzfw(ContentResolver contentResolver, Uri uri, Runnable runnable) {
        zzfy zzfyVar = new zzfy(this, null);
        this.zzf = zzfyVar;
        this.zzg = new Object();
        this.zzi = new ArrayList();
        zzhn.zza(contentResolver);
        zzhn.zza(uri);
        this.zzc = contentResolver;
        this.zzd = uri;
        this.zze = runnable;
        contentResolver.registerContentObserver(uri, false, zzfyVar);
    }

    static synchronized void b() {
        try {
            for (zzfw zzfwVar : zza.values()) {
                zzfwVar.zzc.unregisterContentObserver(zzfwVar.zzf);
            }
            zza.clear();
        } catch (Throwable th) {
            throw th;
        }
    }

    public static zzfw zza(ContentResolver contentResolver, Uri uri, Runnable runnable) {
        zzfw zzfwVar;
        synchronized (zzfw.class) {
            Map<Uri, zzfw> map = zza;
            zzfwVar = map.get(uri);
            if (zzfwVar == null) {
                try {
                    zzfw zzfwVar2 = new zzfw(contentResolver, uri, runnable);
                    try {
                        map.put(uri, zzfwVar2);
                    } catch (SecurityException unused) {
                    }
                    zzfwVar = zzfwVar2;
                } catch (SecurityException unused2) {
                }
            }
        }
        return zzfwVar;
    }

    @Nullable
    private final Map<String, String> zze() {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                return (Map) zzgc.zza(new zzgf() { // from class: com.google.android.gms.internal.measurement.zzfz
                    @Override // com.google.android.gms.internal.measurement.zzgf
                    public final Object zza() {
                        return this.zza.a();
                    }
                });
            } catch (SQLiteException | IllegalStateException | SecurityException unused) {
                Log.e("ConfigurationContentLdr", "PhenotypeFlag unable to load ContentProvider, using default values");
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return null;
            }
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    final /* synthetic */ Map a() {
        Cursor cursorQuery = this.zzc.query(this.zzd, zzb, null, null, null);
        if (cursorQuery == null) {
            return Collections.emptyMap();
        }
        try {
            int count = cursorQuery.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            Map arrayMap = count <= 256 ? new ArrayMap(count) : new HashMap(count, 1.0f);
            while (cursorQuery.moveToNext()) {
                arrayMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            }
            return arrayMap;
        } finally {
            cursorQuery.close();
        }
    }

    public final void zzd() {
        synchronized (this.zzg) {
            this.zzh = null;
            this.zze.run();
        }
        synchronized (this) {
            try {
                Iterator<zzgb> it = this.zzi.iterator();
                while (it.hasNext()) {
                    it.next().zza();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzgd
    public final /* synthetic */ Object zza(String str) {
        return zza().get(str);
    }

    public final Map<String, String> zza() {
        Map<String, String> mapZze = this.zzh;
        if (mapZze == null) {
            synchronized (this.zzg) {
                try {
                    mapZze = this.zzh;
                    if (mapZze == null) {
                        mapZze = zze();
                        this.zzh = mapZze;
                    }
                } finally {
                }
            }
        }
        return mapZze != null ? mapZze : Collections.emptyMap();
    }
}
