package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.webkit.ProxyConfig;
import bolts.MeasurementEvent;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzev;
import com.google.android.gms.internal.measurement.zzfh;
import com.google.android.gms.internal.measurement.zzlw;
import com.google.android.gms.internal.measurement.zzqk;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzqq;
import com.google.android.gms.internal.measurement.zzsg;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzao extends zzml {
    private static final String[] zza = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;", "current_session_count", "ALTER TABLE events ADD COLUMN current_session_count INTEGER;"};
    private static final String[] zzb = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzc = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;", "dynamite_version", "ALTER TABLE apps ADD COLUMN dynamite_version INTEGER;", "safelisted_events", "ALTER TABLE apps ADD COLUMN safelisted_events TEXT;", "ga_app_id", "ALTER TABLE apps ADD COLUMN ga_app_id TEXT;", "config_last_modified_time", "ALTER TABLE apps ADD COLUMN config_last_modified_time TEXT;", "e_tag", "ALTER TABLE apps ADD COLUMN e_tag TEXT;", "session_stitching_token", "ALTER TABLE apps ADD COLUMN session_stitching_token TEXT;", "sgtm_upload_enabled", "ALTER TABLE apps ADD COLUMN sgtm_upload_enabled INTEGER;", "target_os_version", "ALTER TABLE apps ADD COLUMN target_os_version INTEGER;", "session_stitching_token_hash", "ALTER TABLE apps ADD COLUMN session_stitching_token_hash INTEGER;", "ad_services_version", "ALTER TABLE apps ADD COLUMN ad_services_version INTEGER;", "unmatched_first_open_without_ad_id", "ALTER TABLE apps ADD COLUMN unmatched_first_open_without_ad_id INTEGER;", "npa_metadata_value", "ALTER TABLE apps ADD COLUMN npa_metadata_value INTEGER;", "attribution_eligibility_status", "ALTER TABLE apps ADD COLUMN attribution_eligibility_status INTEGER;"};
    private static final String[] zzd = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zze = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzg = {"session_scoped", "ALTER TABLE event_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzh = {"session_scoped", "ALTER TABLE property_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzi = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private static final String[] zzj = {"consent_source", "ALTER TABLE consent_settings ADD COLUMN consent_source INTEGER;", "dma_consent_settings", "ALTER TABLE consent_settings ADD COLUMN dma_consent_settings TEXT;"};
    private static final String[] zzk = {"idempotent", "CREATE INDEX IF NOT EXISTS trigger_uris_index ON trigger_uris (app_id);"};
    private final zzau zzl;
    private final zzmf zzm;

    zzao(zzmq zzmqVar) {
        super(zzmqVar);
        this.zzm = new zzmf(zzb());
        this.zzl = new zzau(this, zza(), "google_app_measurement.db");
    }

    @VisibleForTesting
    private final boolean zzan() {
        return zza().getDatabasePath("google_app_measurement.db").exists();
    }

    final SQLiteDatabase a() {
        zzt();
        try {
            return this.zzl.getWritableDatabase();
        } catch (SQLiteException e2) {
            zzj().zzu().zza("Error opening database", e2);
            throw e2;
        }
    }

    public final long b_() {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = a().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
                if (!cursorRawQuery.moveToFirst()) {
                    cursorRawQuery.close();
                    return -1L;
                }
                long j2 = cursorRawQuery.getLong(0);
                cursorRawQuery.close();
                return j2;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Error querying raw events", e2);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return -1L;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final long c_() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0L);
    }

    final void d(String str, List list) {
        boolean z2;
        boolean z3;
        Preconditions.checkNotNull(list);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzev.zza.C0105zza c0105zzaZzby = ((zzev.zza) list.get(i2)).zzby();
            if (c0105zzaZzby.zza() != 0) {
                for (int i3 = 0; i3 < c0105zzaZzby.zza(); i3++) {
                    zzev.zzb.zza zzaVarZzby = c0105zzaZzby.zza(i3).zzby();
                    zzev.zzb.zza zzaVar = (zzev.zzb.zza) ((zzlw.zza) zzaVarZzby.clone());
                    String strZzb = zzii.zzb(zzaVarZzby.zzb());
                    if (strZzb != null) {
                        zzaVar.zza(strZzb);
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    for (int i4 = 0; i4 < zzaVarZzby.zza(); i4++) {
                        zzev.zzc zzcVarZza = zzaVarZzby.zza(i4);
                        String strZza = zzih.zza(zzcVarZza.zze());
                        if (strZza != null) {
                            zzaVar.zza(i4, (zzev.zzc) ((com.google.android.gms.internal.measurement.zzlw) zzcVarZza.zzby().zza(strZza).zzab()));
                            z3 = true;
                        }
                    }
                    if (z3) {
                        zzev.zza.C0105zza c0105zzaZza = c0105zzaZzby.zza(i3, zzaVar);
                        list.set(i2, (zzev.zza) ((com.google.android.gms.internal.measurement.zzlw) c0105zzaZza.zzab()));
                        c0105zzaZzby = c0105zzaZza;
                    }
                }
            }
            if (c0105zzaZzby.zzb() != 0) {
                for (int i5 = 0; i5 < c0105zzaZzby.zzb(); i5++) {
                    zzev.zze zzeVarZzb = c0105zzaZzby.zzb(i5);
                    String strZza2 = zzik.zza(zzeVarZzb.zze());
                    if (strZza2 != null) {
                        c0105zzaZzby = c0105zzaZzby.zza(i5, zzeVarZzb.zzby().zza(strZza2));
                        list.set(i2, (zzev.zza) ((com.google.android.gms.internal.measurement.zzlw) c0105zzaZzby.zzab()));
                    }
                }
            }
        }
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(list);
        SQLiteDatabase sQLiteDatabaseA = a();
        sQLiteDatabaseA.beginTransaction();
        try {
            zzak();
            zzt();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase sQLiteDatabaseA2 = a();
            sQLiteDatabaseA2.delete("property_filters", "app_id=?", new String[]{str});
            sQLiteDatabaseA2.delete("event_filters", "app_id=?", new String[]{str});
            Iterator it = list.iterator();
            while (it.hasNext()) {
                zzev.zza zzaVar2 = (zzev.zza) it.next();
                zzak();
                zzt();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzaVar2);
                if (zzaVar2.zzg()) {
                    int iZza = zzaVar2.zza();
                    Iterator<zzev.zzb> it2 = zzaVar2.zze().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (!it2.next().zzl()) {
                                zzj().zzu().zza("Event filter with no ID. Audience definition ignored. appId, audienceId", zzfs.d(str), Integer.valueOf(iZza));
                                break;
                            }
                        } else {
                            Iterator<zzev.zze> it3 = zzaVar2.zzf().iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    if (!it3.next().zzi()) {
                                        zzj().zzu().zza("Property filter with no ID. Audience definition ignored. appId, audienceId", zzfs.d(str), Integer.valueOf(iZza));
                                        break;
                                    }
                                } else {
                                    Iterator<zzev.zzb> it4 = zzaVar2.zze().iterator();
                                    while (true) {
                                        if (it4.hasNext()) {
                                            if (!zza(str, iZza, it4.next())) {
                                                z2 = false;
                                                break;
                                            }
                                        } else {
                                            z2 = true;
                                            break;
                                        }
                                    }
                                    if (z2) {
                                        Iterator<zzev.zze> it5 = zzaVar2.zzf().iterator();
                                        while (true) {
                                            if (it5.hasNext()) {
                                                if (!zza(str, iZza, it5.next())) {
                                                    z2 = false;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                    if (!z2) {
                                        zzak();
                                        zzt();
                                        Preconditions.checkNotEmpty(str);
                                        SQLiteDatabase sQLiteDatabaseA3 = a();
                                        sQLiteDatabaseA3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(iZza)});
                                        sQLiteDatabaseA3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(iZza)});
                                    }
                                }
                            }
                        }
                    }
                } else {
                    zzj().zzu().zza("Audience with no ID. appId", zzfs.d(str));
                }
            }
            ArrayList arrayList = new ArrayList();
            Iterator it6 = list.iterator();
            while (it6.hasNext()) {
                zzev.zza zzaVar3 = (zzev.zza) it6.next();
                arrayList.add(zzaVar3.zzg() ? Integer.valueOf(zzaVar3.zza()) : null);
            }
            zzb(str, arrayList);
            sQLiteDatabaseA.setTransactionSuccessful();
            sQLiteDatabaseA.endTransaction();
        } catch (Throwable th) {
            sQLiteDatabaseA.endTransaction();
            throw th;
        }
    }

    @WorkerThread
    public final long d_() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0L);
    }

    final void e(List list) throws SQLException {
        zzt();
        zzak();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzan()) {
            String str = "(" + TextUtils.join(",", list) + ")";
            if (zzb("SELECT COUNT(1) FROM queue WHERE rowid IN " + str + " AND retry_count =  2147483647 LIMIT 1", (String[]) null) > 0) {
                zzj().zzu().zza("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                a().execSQL("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN " + str + " AND (retry_count IS NULL OR retry_count < 2147483647)");
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Error incrementing retry count. error", e2);
            }
        }
    }

    final boolean f(String str, Bundle bundle) {
        zzt();
        zzak();
        byte[] bArrZzbv = g_().d(new zzaz(this.f13286a, "", str, "dep", 0L, 0L, bundle)).zzbv();
        zzj().zzp().zza("Saving default event parameters, appId, data size", zzi().c(str), Integer.valueOf(bArrZzbv.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put(PushConstants.PARAMS, bArrZzbv);
        try {
            if (a().insertWithOnConflict("default_event_params", null, contentValues, 5) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert default event parameters (got -1). appId", zzfs.d(str));
            return false;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing default event parameters. appId", zzfs.d(str), e2);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003e  */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v4, types: [android.database.Cursor] */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String f_() throws java.lang.Throwable {
        /*
            r6 = this;
            android.database.sqlite.SQLiteDatabase r0 = r6.a()
            r1 = 0
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch: java.lang.Throwable -> L22 android.database.sqlite.SQLiteException -> L27
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L1a android.database.sqlite.SQLiteException -> L1c
            if (r2 == 0) goto L1e
            r2 = 0
            java.lang.String r1 = r0.getString(r2)     // Catch: java.lang.Throwable -> L1a android.database.sqlite.SQLiteException -> L1c
            r0.close()
            return r1
        L1a:
            r1 = move-exception
            goto L3c
        L1c:
            r2 = move-exception
            goto L29
        L1e:
            r0.close()
            return r1
        L22:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L3c
        L27:
            r2 = move-exception
            r0 = r1
        L29:
            com.google.android.gms.measurement.internal.zzfs r3 = r6.zzj()     // Catch: java.lang.Throwable -> L1a
            com.google.android.gms.measurement.internal.zzfu r3 = r3.zzg()     // Catch: java.lang.Throwable -> L1a
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zza(r4, r2)     // Catch: java.lang.Throwable -> L1a
            if (r0 == 0) goto L3b
            r0.close()
        L3b:
            return r1
        L3c:
            if (r0 == 0) goto L41
            r0.close()
        L41:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.f_():java.lang.String");
    }

    protected final long q(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzt();
        zzak();
        SQLiteDatabase sQLiteDatabaseA = a();
        sQLiteDatabaseA.beginTransaction();
        long j2 = 0;
        try {
            try {
                long jZza = zza("select " + str2 + " from app2 where app_id=?", new String[]{str}, -1L);
                if (jZza == -1) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("first_open_count", (Integer) 0);
                    contentValues.put("previous_install_count", (Integer) 0);
                    if (sQLiteDatabaseA.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                        zzj().zzg().zza("Failed to insert column (got -1). appId", zzfs.d(str), str2);
                        return -1L;
                    }
                    jZza = 0;
                }
                try {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("app_id", str);
                    contentValues2.put(str2, Long.valueOf(1 + jZza));
                    if (sQLiteDatabaseA.update("app2", contentValues2, "app_id = ?", new String[]{str}) == 0) {
                        zzj().zzg().zza("Failed to update column (got 0). appId", zzfs.d(str), str2);
                        return -1L;
                    }
                    sQLiteDatabaseA.setTransactionSuccessful();
                    return jZza;
                } catch (SQLiteException e2) {
                    long j3 = jZza;
                    e = e2;
                    j2 = j3;
                    zzj().zzg().zza("Error inserting column. appId", zzfs.d(str), str2, e);
                    sQLiteDatabaseA.endTransaction();
                    return j2;
                }
            } catch (SQLiteException e3) {
                e = e3;
            }
        } finally {
            sQLiteDatabaseA.endTransaction();
        }
    }

    final Map r(String str, String str2) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        ArrayMap arrayMap = new ArrayMap();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = a().query("event_filters", new String[]{"audience_id", "data"}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    Map mapEmptyMap = Collections.emptyMap();
                    cursorQuery.close();
                    return mapEmptyMap;
                }
                do {
                    try {
                        zzev.zzb zzbVar = (zzev.zzb) ((com.google.android.gms.internal.measurement.zzlw) ((zzev.zzb.zza) zzna.f(zzev.zzb.zzc(), cursorQuery.getBlob(1))).zzab());
                        int i2 = cursorQuery.getInt(0);
                        List arrayList = (List) arrayMap.get(Integer.valueOf(i2));
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            arrayMap.put(Integer.valueOf(i2), arrayList);
                        }
                        arrayList.add(zzbVar);
                    } catch (IOException e2) {
                        zzj().zzg().zza("Failed to merge filter. appId", zzfs.d(str), e2);
                    }
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
                return arrayMap;
            } catch (SQLiteException e3) {
                zzj().zzg().zza("Database error querying filters. appId", zzfs.d(str), e3);
                Map mapEmptyMap2 = Collections.emptyMap();
                if (0 != 0) {
                    cursor.close();
                }
                return mapEmptyMap2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    final Map s(String str, String str2) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        ArrayMap arrayMap = new ArrayMap();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = a().query("property_filters", new String[]{"audience_id", "data"}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    Map mapEmptyMap = Collections.emptyMap();
                    cursorQuery.close();
                    return mapEmptyMap;
                }
                do {
                    try {
                        zzev.zze zzeVar = (zzev.zze) ((com.google.android.gms.internal.measurement.zzlw) ((zzev.zze.zza) zzna.f(zzev.zze.zzc(), cursorQuery.getBlob(1))).zzab());
                        int i2 = cursorQuery.getInt(0);
                        List arrayList = (List) arrayMap.get(Integer.valueOf(i2));
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            arrayMap.put(Integer.valueOf(i2), arrayList);
                        }
                        arrayList.add(zzeVar);
                    } catch (IOException e2) {
                        zzj().zzg().zza("Failed to merge filter", zzfs.d(str), e2);
                    }
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
                return arrayMap;
            } catch (SQLiteException e3) {
                zzj().zzg().zza("Database error querying filters. appId", zzfs.d(str), e3);
                Map mapEmptyMap2 = Collections.emptyMap();
                if (0 != 0) {
                    cursor.close();
                }
                return mapEmptyMap2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    final Map t(String str) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = a().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    Map mapEmptyMap = Collections.emptyMap();
                    cursorQuery.close();
                    return mapEmptyMap;
                }
                ArrayMap arrayMap = new ArrayMap();
                do {
                    int i2 = cursorQuery.getInt(0);
                    try {
                        arrayMap.put(Integer.valueOf(i2), (zzfh.zzl) ((com.google.android.gms.internal.measurement.zzlw) ((zzfh.zzl.zza) zzna.f(zzfh.zzl.zze(), cursorQuery.getBlob(1))).zzab()));
                    } catch (IOException e2) {
                        zzj().zzg().zza("Failed to merge filter results. appId, audienceId, error", zzfs.d(str), Integer.valueOf(i2), e2);
                    }
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
                return arrayMap;
            } catch (SQLiteException e3) {
                zzj().zzg().zza("Database error querying filter results. appId", zzfs.d(str), e3);
                Map mapEmptyMap2 = Collections.emptyMap();
                if (0 != 0) {
                    cursor.close();
                }
                return mapEmptyMap2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    final Map u(String str) {
        Preconditions.checkNotEmpty(str);
        ArrayMap arrayMap = new ArrayMap();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = a().query("event_filters", new String[]{"audience_id", "data"}, "app_id=?", new String[]{str}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    Map mapEmptyMap = Collections.emptyMap();
                    cursorQuery.close();
                    return mapEmptyMap;
                }
                do {
                    try {
                        zzev.zzb zzbVar = (zzev.zzb) ((com.google.android.gms.internal.measurement.zzlw) ((zzev.zzb.zza) zzna.f(zzev.zzb.zzc(), cursorQuery.getBlob(1))).zzab());
                        if (zzbVar.zzk()) {
                            int i2 = cursorQuery.getInt(0);
                            List arrayList = (List) arrayMap.get(Integer.valueOf(i2));
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                arrayMap.put(Integer.valueOf(i2), arrayList);
                            }
                            arrayList.add(zzbVar);
                        }
                    } catch (IOException e2) {
                        zzj().zzg().zza("Failed to merge filter. appId", zzfs.d(str), e2);
                    }
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
                return arrayMap;
            } catch (SQLiteException e3) {
                zzj().zzg().zza("Database error querying filters. appId", zzfs.d(str), e3);
                Map mapEmptyMap2 = Collections.emptyMap();
                if (0 != 0) {
                    cursor.close();
                }
                return mapEmptyMap2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    final Map v(String str) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        ArrayMap arrayMap = new ArrayMap();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = a().rawQuery("select audience_id, filter_id from event_filters where app_id = ? and session_scoped = 1 UNION select audience_id, filter_id from property_filters where app_id = ? and session_scoped = 1;", new String[]{str, str});
                if (!cursorRawQuery.moveToFirst()) {
                    Map mapEmptyMap = Collections.emptyMap();
                    cursorRawQuery.close();
                    return mapEmptyMap;
                }
                do {
                    int i2 = cursorRawQuery.getInt(0);
                    List arrayList = (List) arrayMap.get(Integer.valueOf(i2));
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        arrayMap.put(Integer.valueOf(i2), arrayList);
                    }
                    arrayList.add(Integer.valueOf(cursorRawQuery.getInt(1)));
                } while (cursorRawQuery.moveToNext());
                cursorRawQuery.close();
                return arrayMap;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Database error querying scoped filters. appId", zzfs.d(str), e2);
                Map mapEmptyMap2 = Collections.emptyMap();
                if (0 != 0) {
                    cursor.close();
                }
                return mapEmptyMap2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final int zza(String str, String str2) throws IllegalStateException {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzt();
        zzak();
        try {
            return a().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error deleting conditional property", zzfs.d(str), zzi().e(str2), e2);
            return 0;
        }
    }

    public final long zzb(String str) {
        Preconditions.checkNotEmpty(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    protected final boolean zzc() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x02c3  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzh zzd(java.lang.String r42) {
        /*
            Method dump skipped, instructions count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zzd(java.lang.String):com.google.android.gms.measurement.internal.zzh");
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0059: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:14:0x0059 */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0089  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzaq zze(java.lang.String r10) {
        /*
            r9 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)
            r9.zzt()
            r9.zzak()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r9.a()     // Catch: java.lang.Throwable -> L6c android.database.sqlite.SQLiteException -> L6e
            java.lang.String r2 = "apps"
            java.lang.String r3 = "remote_config"
            java.lang.String r4 = "config_last_modified_time"
            java.lang.String r5 = "e_tag"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5}     // Catch: java.lang.Throwable -> L6c android.database.sqlite.SQLiteException -> L6e
            java.lang.String r4 = "app_id=?"
            java.lang.String[] r5 = new java.lang.String[]{r10}     // Catch: java.lang.Throwable -> L6c android.database.sqlite.SQLiteException -> L6e
            r7 = 0
            r8 = 0
            r6 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L6c android.database.sqlite.SQLiteException -> L6e
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            if (r2 != 0) goto L31
            r1.close()
            return r0
        L31:
            r2 = 0
            byte[] r2 = r1.getBlob(r2)     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            r4 = 2
            java.lang.String r4 = r1.getString(r4)     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            boolean r5 = r1.moveToNext()     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            if (r5 == 0) goto L5d
            com.google.android.gms.measurement.internal.zzfs r5 = r9.zzj()     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            com.google.android.gms.measurement.internal.zzfu r5 = r5.zzg()     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            java.lang.String r6 = "Got multiple records for app config, expected one. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzfs.d(r10)     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            r5.zza(r6, r7)     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            goto L5d
        L58:
            r10 = move-exception
            r0 = r1
            goto L87
        L5b:
            r2 = move-exception
            goto L70
        L5d:
            if (r2 != 0) goto L63
            r1.close()
            return r0
        L63:
            com.google.android.gms.measurement.internal.zzaq r5 = new com.google.android.gms.measurement.internal.zzaq     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            r5.<init>(r2, r3, r4)     // Catch: java.lang.Throwable -> L58 android.database.sqlite.SQLiteException -> L5b
            r1.close()
            return r5
        L6c:
            r10 = move-exception
            goto L87
        L6e:
            r2 = move-exception
            r1 = r0
        L70:
            com.google.android.gms.measurement.internal.zzfs r3 = r9.zzj()     // Catch: java.lang.Throwable -> L58
            com.google.android.gms.measurement.internal.zzfu r3 = r3.zzg()     // Catch: java.lang.Throwable -> L58
            java.lang.String r4 = "Error querying remote config. appId"
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzfs.d(r10)     // Catch: java.lang.Throwable -> L58
            r3.zza(r4, r10, r2)     // Catch: java.lang.Throwable -> L58
            if (r1 == 0) goto L86
            r1.close()
        L86:
            return r0
        L87:
            if (r0 == 0) goto L8c
            r0.close()
        L8c:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zze(java.lang.String):com.google.android.gms.measurement.internal.zzaq");
    }

    public final zzay zzf(String str) {
        if (!zzql.zzb() || !zze().zza(zzbi.zzcm)) {
            return zzay.zza;
        }
        Preconditions.checkNotNull(str);
        zzt();
        zzak();
        return zzay.zza(zza("select dma_consent_settings from consent_settings where app_id=? limit 1;", new String[]{str}, ""));
    }

    public final zzie zzg(String str) {
        Preconditions.checkNotNull(str);
        zzt();
        zzak();
        if (!zzql.zzb() || !zze().zza(zzbi.zzcm)) {
            return zzie.zza(zza("select consent_state from consent_settings where app_id=? limit 1;", new String[]{str}, "G1"));
        }
        zzie zzieVar = (zzie) zza("select consent_state, consent_source from consent_settings where app_id=? limit 1;", new String[]{str}, new zzar() { // from class: com.google.android.gms.measurement.internal.zzan
            @Override // com.google.android.gms.measurement.internal.zzar
            public final Object zza(Cursor cursor) {
                return zzie.zza(cursor.getString(0), cursor.getInt(1));
            }
        });
        return zzieVar == null ? zzie.zza : zzieVar;
    }

    public final List<zzmi> zzh(String str) {
        Preconditions.checkNotEmpty(str);
        zzt();
        zzak();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = a().query("trigger_uris", new String[]{"trigger_uri", "timestamp_millis", "source"}, "app_id=?", new String[]{str}, null, null, "rowid", null);
                if (!cursorQuery.moveToFirst()) {
                    cursorQuery.close();
                    return arrayList;
                }
                do {
                    String string = cursorQuery.getString(0);
                    if (string == null) {
                        string = "";
                    }
                    arrayList.add(new zzmi(string, cursorQuery.getLong(1), cursorQuery.getInt(2)));
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
                return arrayList;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Error querying trigger uris. appId", zzfs.d(str), e2);
                List<zzmi> listEmptyList = Collections.emptyList();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return listEmptyList;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zznb> zzi(String str) {
        Preconditions.checkNotEmpty(str);
        zzt();
        zzak();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = a().query("user_attributes", new String[]{"name", "origin", "set_timestamp", "value"}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
                if (!cursorQuery.moveToFirst()) {
                    cursorQuery.close();
                    return arrayList;
                }
                do {
                    String string = cursorQuery.getString(0);
                    String string2 = cursorQuery.getString(1);
                    if (string2 == null) {
                        string2 = "";
                    }
                    String str2 = string2;
                    long j2 = cursorQuery.getLong(2);
                    Object objZza = zza(cursorQuery, 3);
                    if (objZza == null) {
                        zzj().zzg().zza("Read invalid user property value, ignoring it. appId", zzfs.d(str));
                    } else {
                        arrayList.add(new zznb(str, str2, string, j2, objZza));
                    }
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
                return arrayList;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Error querying user properties. appId", zzfs.d(str), e2);
                List<zznb> listEmptyList = Collections.emptyList();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return listEmptyList;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final void zzp() {
        zzak();
        a().beginTransaction();
    }

    @WorkerThread
    public final void zzu() {
        zzak();
        a().endTransaction();
    }

    final void zzv() {
        int iDelete;
        zzt();
        zzak();
        if (zzan()) {
            long jZza = zzn().zza.zza();
            long jElapsedRealtime = zzb().elapsedRealtime();
            if (Math.abs(jElapsedRealtime - jZza) > zzbi.zzy.zza(null).longValue()) {
                zzn().zza.zza(jElapsedRealtime);
                zzt();
                zzak();
                if (!zzan() || (iDelete = a().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzb().currentTimeMillis()), String.valueOf(zzaf.zzm())})) <= 0) {
                    return;
                }
                zzj().zzp().zza("Deleted stale rows. rowsDeleted", Integer.valueOf(iDelete));
            }
        }
    }

    @WorkerThread
    public final void zzw() {
        zzak();
        a().setTransactionSuccessful();
    }

    public final boolean zzx() {
        return zzb("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzy() {
        return zzb("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    public final boolean zzz() {
        return zzb("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x002d: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:10:0x002d */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle zzc(java.lang.String r8) throws java.lang.Throwable {
        /*
            r7 = this;
            r7.zzt()
            r7.zzak()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r7.a()     // Catch: java.lang.Throwable -> Lbc android.database.sqlite.SQLiteException -> Lbe
            java.lang.String r2 = "select parameters from default_event_params where app_id=?"
            java.lang.String[] r3 = new java.lang.String[]{r8}     // Catch: java.lang.Throwable -> Lbc android.database.sqlite.SQLiteException -> Lbe
            android.database.Cursor r1 = r1.rawQuery(r2, r3)     // Catch: java.lang.Throwable -> Lbc android.database.sqlite.SQLiteException -> Lbe
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            if (r2 != 0) goto L33
            com.google.android.gms.measurement.internal.zzfs r8 = r7.zzj()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            com.google.android.gms.measurement.internal.zzfu r8 = r8.zzp()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            java.lang.String r2 = "Default event parameters not found"
            r8.zza(r2)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r1.close()
            return r0
        L2c:
            r8 = move-exception
            r0 = r1
            goto Ld3
        L30:
            r8 = move-exception
            goto Lc0
        L33:
            r2 = 0
            byte[] r2 = r1.getBlob(r2)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            com.google.android.gms.internal.measurement.zzfh$zze$zza r3 = com.google.android.gms.internal.measurement.zzfh.zze.zze()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30 java.io.IOException -> La6
            com.google.android.gms.internal.measurement.zzni r2 = com.google.android.gms.measurement.internal.zzna.f(r3, r2)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30 java.io.IOException -> La6
            com.google.android.gms.internal.measurement.zzfh$zze$zza r2 = (com.google.android.gms.internal.measurement.zzfh.zze.zza) r2     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30 java.io.IOException -> La6
            com.google.android.gms.internal.measurement.zznj r2 = r2.zzab()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30 java.io.IOException -> La6
            com.google.android.gms.internal.measurement.zzlw r2 = (com.google.android.gms.internal.measurement.zzlw) r2     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30 java.io.IOException -> La6
            com.google.android.gms.internal.measurement.zzfh$zze r2 = (com.google.android.gms.internal.measurement.zzfh.zze) r2     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30 java.io.IOException -> La6
            r7.g_()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            java.util.List r8 = r2.zzh()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            android.os.Bundle r2 = new android.os.Bundle     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r2.<init>()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
        L5a:
            boolean r3 = r8.hasNext()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            if (r3 == 0) goto La2
            java.lang.Object r3 = r8.next()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            com.google.android.gms.internal.measurement.zzfh$zzg r3 = (com.google.android.gms.internal.measurement.zzfh.zzg) r3     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            java.lang.String r4 = r3.zzg()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            boolean r5 = r3.zzj()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            if (r5 == 0) goto L78
            double r5 = r3.zza()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r2.putDouble(r4, r5)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            goto L5a
        L78:
            boolean r5 = r3.zzk()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            if (r5 == 0) goto L86
            float r3 = r3.zzb()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r2.putFloat(r4, r3)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            goto L5a
        L86:
            boolean r5 = r3.zzn()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            if (r5 == 0) goto L94
            java.lang.String r3 = r3.zzh()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r2.putString(r4, r3)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            goto L5a
        L94:
            boolean r5 = r3.zzl()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            if (r5 == 0) goto L5a
            long r5 = r3.zzd()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r2.putLong(r4, r5)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            goto L5a
        La2:
            r1.close()
            return r2
        La6:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzfs r3 = r7.zzj()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            com.google.android.gms.measurement.internal.zzfu r3 = r3.zzg()     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            java.lang.String r4 = "Failed to retrieve default event parameters. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzfs.d(r8)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r3.zza(r4, r8, r2)     // Catch: java.lang.Throwable -> L2c android.database.sqlite.SQLiteException -> L30
            r1.close()
            return r0
        Lbc:
            r8 = move-exception
            goto Ld3
        Lbe:
            r8 = move-exception
            r1 = r0
        Lc0:
            com.google.android.gms.measurement.internal.zzfs r2 = r7.zzj()     // Catch: java.lang.Throwable -> L2c
            com.google.android.gms.measurement.internal.zzfu r2 = r2.zzg()     // Catch: java.lang.Throwable -> L2c
            java.lang.String r3 = "Error selecting default event parameters"
            r2.zza(r3, r8)     // Catch: java.lang.Throwable -> L2c
            if (r1 == 0) goto Ld2
            r1.close()
        Ld2:
            return r0
        Ld3:
            if (r0 == 0) goto Ld8
            r0.close()
        Ld8:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zzc(java.lang.String):android.os.Bundle");
    }

    @WorkerThread
    private final long zzb(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = a().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    long j2 = cursorRawQuery.getLong(0);
                    cursorRawQuery.close();
                    return j2;
                }
                throw new SQLiteException("Database returned empty set");
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Database error", str, e2);
                throw e2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final long zza(String str) {
        Preconditions.checkNotEmpty(str);
        zzt();
        zzak();
        try {
            return a().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, zze().zzb(str, zzbi.zzp))))});
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error deleting over the limit events. appId", zzfs.d(str), e2);
            return 0L;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a1, code lost:
    
        zzj().zzg().zza("Read more than the max allowed user properties, ignoring excess", 1000);
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0126  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<com.google.android.gms.measurement.internal.zznb> zzb(java.lang.String r23, java.lang.String r24, java.lang.String r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zzb(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    @WorkerThread
    public final void zzh(String str, String str2) throws IllegalStateException {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzt();
        zzak();
        try {
            a().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error deleting user property. appId", zzfs.d(str), zzi().e(str2), e2);
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x006a: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:19:0x006a */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0097  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zznb zze(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r11)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r12)
            r10.zzt()
            r10.zzak()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r10.a()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L74
            java.lang.String r2 = "user_attributes"
            java.lang.String r3 = "set_timestamp"
            java.lang.String r4 = "value"
            java.lang.String r5 = "origin"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5}     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L74
            java.lang.String r4 = "app_id=? and name=?"
            java.lang.String[] r5 = new java.lang.String[]{r11, r12}     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L74
            r7 = 0
            r8 = 0
            r6 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L74
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            if (r2 != 0) goto L34
            r1.close()
            return r0
        L34:
            r2 = 0
            long r7 = r1.getLong(r2)     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            r2 = 1
            java.lang.Object r9 = r10.zza(r1, r2)     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            if (r9 != 0) goto L44
            r1.close()
            return r0
        L44:
            r2 = 2
            java.lang.String r5 = r1.getString(r2)     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            com.google.android.gms.measurement.internal.zznb r2 = new com.google.android.gms.measurement.internal.zznb     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            r3 = r2
            r4 = r11
            r6 = r12
            r3.<init>(r4, r5, r6, r7, r9)     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            boolean r3 = r1.moveToNext()     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            if (r3 == 0) goto L6e
            com.google.android.gms.measurement.internal.zzfs r3 = r10.zzj()     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            com.google.android.gms.measurement.internal.zzfu r3 = r3.zzg()     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            java.lang.String r4 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzfs.d(r11)     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            r3.zza(r4, r5)     // Catch: java.lang.Throwable -> L69 android.database.sqlite.SQLiteException -> L6c
            goto L6e
        L69:
            r11 = move-exception
            r0 = r1
            goto L95
        L6c:
            r2 = move-exception
            goto L76
        L6e:
            r1.close()
            return r2
        L72:
            r11 = move-exception
            goto L95
        L74:
            r2 = move-exception
            r1 = r0
        L76:
            com.google.android.gms.measurement.internal.zzfs r3 = r10.zzj()     // Catch: java.lang.Throwable -> L69
            com.google.android.gms.measurement.internal.zzfu r3 = r3.zzg()     // Catch: java.lang.Throwable -> L69
            java.lang.String r4 = "Error querying user property. appId"
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzfs.d(r11)     // Catch: java.lang.Throwable -> L69
            com.google.android.gms.measurement.internal.zzfn r5 = r10.zzi()     // Catch: java.lang.Throwable -> L69
            java.lang.String r12 = r5.e(r12)     // Catch: java.lang.Throwable -> L69
            r3.zza(r4, r11, r12, r2)     // Catch: java.lang.Throwable -> L69
            if (r1 == 0) goto L94
            r1.close()
        L94:
            return r0
        L95:
            if (r0 == 0) goto L9a
            r0.close()
        L9a:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zze(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zznb");
    }

    public final long zza(zzfh.zzj zzjVar) throws IOException {
        zzt();
        zzak();
        Preconditions.checkNotNull(zzjVar);
        Preconditions.checkNotEmpty(zzjVar.zzx());
        byte[] bArrZzbv = zzjVar.zzbv();
        long jB = g_().b(bArrZzbv);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzjVar.zzx());
        contentValues.put("metadata_fingerprint", Long.valueOf(jB));
        contentValues.put(TtmlNode.TAG_METADATA, bArrZzbv);
        try {
            a().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
            return jB;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing raw event metadata. appId", zzfs.d(zzjVar.zzx()), e2);
            throw e2;
        }
    }

    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0053: MOVE (r8 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]), block:B:14:0x0053 */
    /* JADX WARN: Removed duplicated region for block: B:35:0x011c  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzad zzc(java.lang.String r27, java.lang.String r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zzc(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzad");
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j2) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = a().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    long j3 = cursorRawQuery.getLong(0);
                    cursorRawQuery.close();
                    return j3;
                }
                cursorRawQuery.close();
                return j2;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Database error", str, e2);
                throw e2;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0031: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:10:0x0031 */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.Pair<com.google.android.gms.internal.measurement.zzfh.zze, java.lang.Long> zza(java.lang.String r6, java.lang.Long r7) throws java.lang.Throwable {
        /*
            r5 = this;
            r5.zzt()
            r5.zzak()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r5.a()     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteException -> L75
            java.lang.String r2 = "select main_event, children_to_process from main_event_params where app_id=? and event_id=?"
            java.lang.String r3 = java.lang.String.valueOf(r7)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteException -> L75
            java.lang.String[] r3 = new java.lang.String[]{r6, r3}     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteException -> L75
            android.database.Cursor r1 = r1.rawQuery(r2, r3)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteException -> L75
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            if (r2 != 0) goto L35
            com.google.android.gms.measurement.internal.zzfs r6 = r5.zzj()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            com.google.android.gms.measurement.internal.zzfu r6 = r6.zzp()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            java.lang.String r7 = "Main event not found"
            r6.zza(r7)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r1.close()
            return r0
        L30:
            r6 = move-exception
            r0 = r1
            goto L8a
        L33:
            r6 = move-exception
            goto L77
        L35:
            r2 = 0
            byte[] r2 = r1.getBlob(r2)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r3 = 1
            long r3 = r1.getLong(r3)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            com.google.android.gms.internal.measurement.zzfh$zze$zza r4 = com.google.android.gms.internal.measurement.zzfh.zze.zze()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33 java.io.IOException -> L5d
            com.google.android.gms.internal.measurement.zzni r2 = com.google.android.gms.measurement.internal.zzna.f(r4, r2)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33 java.io.IOException -> L5d
            com.google.android.gms.internal.measurement.zzfh$zze$zza r2 = (com.google.android.gms.internal.measurement.zzfh.zze.zza) r2     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33 java.io.IOException -> L5d
            com.google.android.gms.internal.measurement.zznj r2 = r2.zzab()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33 java.io.IOException -> L5d
            com.google.android.gms.internal.measurement.zzlw r2 = (com.google.android.gms.internal.measurement.zzlw) r2     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33 java.io.IOException -> L5d
            com.google.android.gms.internal.measurement.zzfh$zze r2 = (com.google.android.gms.internal.measurement.zzfh.zze) r2     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33 java.io.IOException -> L5d
            android.util.Pair r6 = android.util.Pair.create(r2, r3)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r1.close()
            return r6
        L5d:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzfs r3 = r5.zzj()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            com.google.android.gms.measurement.internal.zzfu r3 = r3.zzg()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            java.lang.String r4 = "Failed to merge main event. appId, eventId"
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzfs.d(r6)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r3.zza(r4, r6, r7, r2)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r1.close()
            return r0
        L73:
            r6 = move-exception
            goto L8a
        L75:
            r6 = move-exception
            r1 = r0
        L77:
            com.google.android.gms.measurement.internal.zzfs r7 = r5.zzj()     // Catch: java.lang.Throwable -> L30
            com.google.android.gms.measurement.internal.zzfu r7 = r7.zzg()     // Catch: java.lang.Throwable -> L30
            java.lang.String r2 = "Error selecting main event"
            r7.zza(r2, r6)     // Catch: java.lang.Throwable -> L30
            if (r1 == 0) goto L89
            r1.close()
        L89:
            return r0
        L8a:
            if (r0 == 0) goto L8f
            r0.close()
        L8f:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zza(java.lang.String, java.lang.Long):android.util.Pair");
    }

    private final boolean zzb(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        zzak();
        zzt();
        SQLiteDatabase sQLiteDatabaseA = a();
        try {
            long jZzb = zzb("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int iMax = Math.max(0, Math.min(2000, zze().zzb(str, zzbi.zzaf)));
            if (jZzb <= iMax) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                Integer num = list.get(i2);
                if (num == null) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String str2 = "(" + TextUtils.join(",", arrayList) + ")";
            StringBuilder sb = new StringBuilder("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            sb.append(str2);
            sb.append(" order by rowid desc limit -1 offset ?)");
            return sQLiteDatabaseA.delete("audience_filter_values", sb.toString(), new String[]{str, Integer.toString(iMax)}) > 0;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Database error querying filters. appId", zzfs.d(str), e2);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012c  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzbc zzd(java.lang.String r29, java.lang.String r30) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zzd(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzbc");
    }

    @WorkerThread
    public final zzap zza(long j2, String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        return zza(j2, str, 1L, false, false, z4, false, z6);
    }

    @WorkerThread
    public final zzap zza(long j2, String str, long j3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        Preconditions.checkNotEmpty(str);
        zzt();
        zzak();
        String[] strArr = {str};
        zzap zzapVar = new zzap();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase sQLiteDatabaseA = a();
                Cursor cursorQuery = sQLiteDatabaseA.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    zzj().zzu().zza("Not updating daily counts, app is not known. appId", zzfs.d(str));
                    cursorQuery.close();
                    return zzapVar;
                }
                if (cursorQuery.getLong(0) == j2) {
                    zzapVar.f13253b = cursorQuery.getLong(1);
                    zzapVar.f13252a = cursorQuery.getLong(2);
                    zzapVar.f13254c = cursorQuery.getLong(3);
                    zzapVar.f13255d = cursorQuery.getLong(4);
                    zzapVar.f13256e = cursorQuery.getLong(5);
                }
                if (z2) {
                    zzapVar.f13253b += j3;
                }
                if (z3) {
                    zzapVar.f13252a += j3;
                }
                if (z4) {
                    zzapVar.f13254c += j3;
                }
                if (z5) {
                    zzapVar.f13255d += j3;
                }
                if (z6) {
                    zzapVar.f13256e += j3;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("day", Long.valueOf(j2));
                contentValues.put("daily_public_events_count", Long.valueOf(zzapVar.f13252a));
                contentValues.put("daily_events_count", Long.valueOf(zzapVar.f13253b));
                contentValues.put("daily_conversions_count", Long.valueOf(zzapVar.f13254c));
                contentValues.put("daily_error_events_count", Long.valueOf(zzapVar.f13255d));
                contentValues.put("daily_realtime_events_count", Long.valueOf(zzapVar.f13256e));
                sQLiteDatabaseA.update("apps", contentValues, "app_id=?", strArr);
                cursorQuery.close();
                return zzapVar;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Error updating daily counts. appId", zzfs.d(str), e2);
                if (0 != 0) {
                    cursor.close();
                }
                return zzapVar;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    @VisibleForTesting
    @WorkerThread
    private final Object zza(Cursor cursor, int i2) {
        int type = cursor.getType(i2);
        if (type == 0) {
            zzj().zzg().zza("Loaded invalid null value from database");
            return null;
        }
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i2));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i2));
        }
        if (type == 3) {
            return cursor.getString(i2);
        }
        if (type != 4) {
            zzj().zzg().zza("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
            return null;
        }
        zzj().zzg().zza("Loaded invalid blob type value, ignoring it");
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0046  */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v2 */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final <T> T zza(java.lang.String r3, java.lang.String[] r4, com.google.android.gms.measurement.internal.zzar<T> r5) throws java.lang.Throwable {
        /*
            r2 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r2.a()     // Catch: java.lang.Throwable -> L2d android.database.sqlite.SQLiteException -> L2f
            android.database.Cursor r3 = r1.rawQuery(r3, r4)     // Catch: java.lang.Throwable -> L2d android.database.sqlite.SQLiteException -> L2f
            boolean r4 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L20 android.database.sqlite.SQLiteException -> L23
            if (r4 != 0) goto L25
            com.google.android.gms.measurement.internal.zzfs r4 = r2.zzj()     // Catch: java.lang.Throwable -> L20 android.database.sqlite.SQLiteException -> L23
            com.google.android.gms.measurement.internal.zzfu r4 = r4.zzp()     // Catch: java.lang.Throwable -> L20 android.database.sqlite.SQLiteException -> L23
            java.lang.String r5 = "No data found"
            r4.zza(r5)     // Catch: java.lang.Throwable -> L20 android.database.sqlite.SQLiteException -> L23
            r3.close()
            return r0
        L20:
            r4 = move-exception
            r0 = r3
            goto L44
        L23:
            r4 = move-exception
            goto L31
        L25:
            java.lang.Object r4 = r5.zza(r3)     // Catch: java.lang.Throwable -> L20 android.database.sqlite.SQLiteException -> L23
            r3.close()
            return r4
        L2d:
            r4 = move-exception
            goto L44
        L2f:
            r4 = move-exception
            r3 = r0
        L31:
            com.google.android.gms.measurement.internal.zzfs r5 = r2.zzj()     // Catch: java.lang.Throwable -> L20
            com.google.android.gms.measurement.internal.zzfu r5 = r5.zzg()     // Catch: java.lang.Throwable -> L20
            java.lang.String r1 = "Error querying database."
            r5.zza(r1, r4)     // Catch: java.lang.Throwable -> L20
            if (r3 == 0) goto L43
            r3.close()
        L43:
            return r0
        L44:
            if (r0 == 0) goto L49
            r0.close()
        L49:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zza(java.lang.String, java.lang.String[], com.google.android.gms.measurement.internal.zzar):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String zza(long r4) throws java.lang.Throwable {
        /*
            r3 = this;
            r3.zzt()
            r3.zzak()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r3.a()     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40
            java.lang.String r2 = "select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;"
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40
            java.lang.String[] r4 = new java.lang.String[]{r4}     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40
            android.database.Cursor r4 = r1.rawQuery(r2, r4)     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40
            boolean r5 = r4.moveToFirst()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            if (r5 != 0) goto L35
            com.google.android.gms.measurement.internal.zzfs r5 = r3.zzj()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            com.google.android.gms.measurement.internal.zzfu r5 = r5.zzp()     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            java.lang.String r1 = "No expired configs for apps with pending events"
            r5.zza(r1)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r4.close()
            return r0
        L30:
            r5 = move-exception
            r0 = r4
            goto L55
        L33:
            r5 = move-exception
            goto L42
        L35:
            r5 = 0
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Throwable -> L30 android.database.sqlite.SQLiteException -> L33
            r4.close()
            return r5
        L3e:
            r5 = move-exception
            goto L55
        L40:
            r5 = move-exception
            r4 = r0
        L42:
            com.google.android.gms.measurement.internal.zzfs r1 = r3.zzj()     // Catch: java.lang.Throwable -> L30
            com.google.android.gms.measurement.internal.zzfu r1 = r1.zzg()     // Catch: java.lang.Throwable -> L30
            java.lang.String r2 = "Error selecting expired configs"
            r1.zza(r2, r5)     // Catch: java.lang.Throwable -> L30
            if (r4 == 0) goto L54
            r4.close()
        L54:
            return r0
        L55:
            if (r0 == 0) goto L5a
            r0.close()
        L5a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zza(long):java.lang.String");
    }

    @WorkerThread
    private final String zza(String str, String[] strArr, String str2) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = a().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    String string = cursorRawQuery.getString(0);
                    cursorRawQuery.close();
                    return string;
                }
                cursorRawQuery.close();
                return str2;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Database error", str, e2);
                throw e2;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<Pair<zzfh.zzj, Long>> zza(String str, int i2, int i3) {
        byte[] bArrZ;
        long jZzc;
        long jZzc2;
        zzt();
        zzak();
        int i4 = 1;
        Preconditions.checkArgument(i2 > 0);
        Preconditions.checkArgument(i3 > 0);
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = a().query("queue", new String[]{"rowid", "data", "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i2));
                if (!cursorQuery.moveToFirst()) {
                    List<Pair<zzfh.zzj, Long>> listEmptyList = Collections.emptyList();
                    cursorQuery.close();
                    return listEmptyList;
                }
                ArrayList arrayList = new ArrayList();
                int length = 0;
                while (true) {
                    long j2 = cursorQuery.getLong(0);
                    try {
                        bArrZ = g_().z(cursorQuery.getBlob(i4));
                    } catch (IOException e2) {
                        zzj().zzg().zza("Failed to unzip queued bundle. appId", zzfs.d(str), e2);
                    }
                    if (!arrayList.isEmpty() && bArrZ.length + length > i3) {
                        break;
                    }
                    try {
                        zzfh.zzj.zza zzaVar = (zzfh.zzj.zza) zzna.f(zzfh.zzj.zzu(), bArrZ);
                        if (zzql.zzb() && zze().zza(zzbi.zzcq) && !arrayList.isEmpty()) {
                            zzfh.zzj zzjVar = (zzfh.zzj) ((Pair) arrayList.get(0)).first;
                            zzfh.zzj zzjVar2 = (zzfh.zzj) ((com.google.android.gms.internal.measurement.zzlw) zzaVar.zzab());
                            if (!zzjVar.zzac().equals(zzjVar2.zzac()) || !zzjVar.zzab().equals(zzjVar2.zzab()) || zzjVar.zzas() != zzjVar2.zzas() || !zzjVar.zzad().equals(zzjVar2.zzad())) {
                                break;
                            }
                            Iterator<zzfh.zzn> it = zzjVar.zzaq().iterator();
                            while (true) {
                                jZzc = -1;
                                if (!it.hasNext()) {
                                    jZzc2 = -1;
                                    break;
                                }
                                zzfh.zzn next = it.next();
                                if ("_npa".equals(next.zzg())) {
                                    jZzc2 = next.zzc();
                                    break;
                                }
                            }
                            Iterator<zzfh.zzn> it2 = zzjVar2.zzaq().iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                zzfh.zzn next2 = it2.next();
                                if ("_npa".equals(next2.zzg())) {
                                    jZzc = next2.zzc();
                                    break;
                                }
                            }
                            if (jZzc2 != jZzc) {
                                break;
                            }
                        }
                        if (!cursorQuery.isNull(2)) {
                            zzaVar.zzh(cursorQuery.getInt(2));
                        }
                        length += bArrZ.length;
                        arrayList.add(Pair.create((zzfh.zzj) ((com.google.android.gms.internal.measurement.zzlw) zzaVar.zzab()), Long.valueOf(j2)));
                    } catch (IOException e3) {
                        zzj().zzg().zza("Failed to merge queued bundle. appId", zzfs.d(str), e3);
                    }
                    if (!cursorQuery.moveToNext() || length > i3) {
                        break;
                    }
                    i4 = 1;
                }
                cursorQuery.close();
                return arrayList;
            } catch (SQLiteException e4) {
                zzj().zzg().zza("Error querying bundles. appId", zzfs.d(str), e4);
                List<Pair<zzfh.zzj, Long>> listEmptyList2 = Collections.emptyList();
                if (0 != 0) {
                    cursor.close();
                }
                return listEmptyList2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zzad> zza(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzt();
        zzak();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(str3 + ProxyConfig.MATCH_ALL_SCHEMES);
            sb.append(" and name glob ?");
        }
        return zza(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0050, code lost:
    
        zzj().zzg().zza("Read more than the max allowed conditional properties, ignoring extra", 1000);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<com.google.android.gms.measurement.internal.zzad> zza(java.lang.String r27, java.lang.String[] r28) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zza(java.lang.String, java.lang.String[]):java.util.List");
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else {
            if (obj instanceof Double) {
                contentValues.put(str, (Double) obj);
                return;
            }
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    @WorkerThread
    public final void zza(zzh zzhVar) {
        Preconditions.checkNotNull(zzhVar);
        zzt();
        zzak();
        String strZzx = zzhVar.zzx();
        Preconditions.checkNotNull(strZzx);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", strZzx);
        contentValues.put("app_instance_id", zzhVar.zzy());
        contentValues.put("gmp_app_id", zzhVar.zzac());
        contentValues.put("resettable_device_id_hash", zzhVar.zzae());
        contentValues.put("last_bundle_index", Long.valueOf(zzhVar.zzq()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzhVar.zzr()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzhVar.zzp()));
        contentValues.put("app_version", zzhVar.zzaa());
        contentValues.put("app_store", zzhVar.zzz());
        contentValues.put("gmp_version", Long.valueOf(zzhVar.zzo()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzhVar.zzl()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzhVar.zzak()));
        contentValues.put("day", Long.valueOf(zzhVar.zzk()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzhVar.zzi()));
        contentValues.put("daily_events_count", Long.valueOf(zzhVar.zzh()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzhVar.zzf()));
        contentValues.put("config_fetched_time", Long.valueOf(zzhVar.zze()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzhVar.zzn()));
        contentValues.put("app_version_int", Long.valueOf(zzhVar.zzc()));
        contentValues.put("firebase_instance_id", zzhVar.zzab());
        contentValues.put("daily_error_events_count", Long.valueOf(zzhVar.zzg()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzhVar.zzj()));
        contentValues.put("health_monitor_sample", zzhVar.zzad());
        contentValues.put("android_id", Long.valueOf(zzhVar.zzb()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzhVar.zzaj()));
        contentValues.put("admob_app_id", zzhVar.zzv());
        contentValues.put("dynamite_version", Long.valueOf(zzhVar.zzm()));
        contentValues.put("session_stitching_token", zzhVar.zzaf());
        contentValues.put("sgtm_upload_enabled", Boolean.valueOf(zzhVar.zzam()));
        contentValues.put("target_os_version", Long.valueOf(zzhVar.zzt()));
        contentValues.put("session_stitching_token_hash", Long.valueOf(zzhVar.zzs()));
        if (zzsg.zzb() && zze().zze(strZzx, zzbi.zzcf)) {
            contentValues.put("ad_services_version", Integer.valueOf(zzhVar.zza()));
            contentValues.put("attribution_eligibility_status", Long.valueOf(zzhVar.zzd()));
        }
        if (zzqk.zzb() && zze().zze(strZzx, zzbi.zzcr)) {
            contentValues.put("unmatched_first_open_without_ad_id", Boolean.valueOf(zzhVar.zzan()));
        }
        List<String> listZzag = zzhVar.zzag();
        if (listZzag != null) {
            if (listZzag.isEmpty()) {
                zzj().zzu().zza("Safelisted events should not be an empty list. appId", strZzx);
            } else {
                contentValues.put("safelisted_events", TextUtils.join(",", listZzag));
            }
        }
        if (zzqq.zzb() && zze().zza(zzbi.zzbp) && !contentValues.containsKey("safelisted_events")) {
            contentValues.put("safelisted_events", (String) null);
        }
        if (zzql.zzb() && zze().zze(strZzx, zzbi.zzcm)) {
            contentValues.put("npa_metadata_value", zzhVar.zzu());
        }
        try {
            SQLiteDatabase sQLiteDatabaseA = a();
            if (sQLiteDatabaseA.update("apps", contentValues, "app_id = ?", new String[]{strZzx}) == 0 && sQLiteDatabaseA.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzj().zzg().zza("Failed to insert/update app (got -1). appId", zzfs.d(strZzx));
            }
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing app. appId", zzfs.d(strZzx), e2);
        }
    }

    public final void zza(String str, zzie zzieVar) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(zzieVar);
        zzt();
        zzak();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("consent_state", zzieVar.zze());
        if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
            contentValues.put("consent_source", Integer.valueOf(zzieVar.zza()));
            zza("consent_settings", "app_id", contentValues);
            return;
        }
        try {
            if (a().insertWithOnConflict("consent_settings", null, contentValues, 5) == -1) {
                zzj().zzg().zza("Failed to insert/update consent setting (got -1). appId", zzfs.d(str));
            }
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing consent setting. appId, error", zzfs.d(str), e2);
        }
    }

    public final void zza(String str, zzay zzayVar) {
        if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(zzayVar);
            zzt();
            zzak();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("dma_consent_settings", zzayVar.zzf());
            zza("consent_settings", "app_id", contentValues);
        }
    }

    @WorkerThread
    public final void zza(zzbc zzbcVar) {
        Preconditions.checkNotNull(zzbcVar);
        zzt();
        zzak();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzbcVar.f13265a);
        contentValues.put("name", zzbcVar.f13266b);
        contentValues.put("lifetime_count", Long.valueOf(zzbcVar.f13267c));
        contentValues.put("current_bundle_count", Long.valueOf(zzbcVar.f13268d));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzbcVar.f13270f));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzbcVar.f13271g));
        contentValues.put("last_bundled_day", zzbcVar.f13272h);
        contentValues.put("last_sampled_complex_event_id", zzbcVar.f13273i);
        contentValues.put("last_sampling_rate", zzbcVar.f13274j);
        contentValues.put("current_session_count", Long.valueOf(zzbcVar.f13269e));
        Boolean bool = zzbcVar.f13275k;
        contentValues.put("last_exempt_from_sampling", (bool == null || !bool.booleanValue()) ? null : 1L);
        try {
            if (a().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzj().zzg().zza("Failed to insert/update event aggregates (got -1). appId", zzfs.d(zzbcVar.f13265a));
            }
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing event aggregates. appId", zzfs.d(zzbcVar.f13265a), e2);
        }
    }

    @WorkerThread
    private final void zza(String str, String str2, ContentValues contentValues) throws IllegalStateException {
        try {
            SQLiteDatabase sQLiteDatabaseA = a();
            if (contentValues.getAsString(str2) == null) {
                zzj().zzh().zza("Value of the primary key is not set.", zzfs.d(str2));
                return;
            }
            if (sQLiteDatabaseA.update(str, contentValues, str2 + " = ?", new String[]{r1}) == 0 && sQLiteDatabaseA.insertWithOnConflict(str, null, contentValues, 5) == -1) {
                zzj().zzg().zza("Failed to insert/update table (got -1). key", zzfs.d(str), zzfs.d(str2));
            }
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing into table. key", zzfs.d(str), zzfs.d(str2), e2);
        }
    }

    @WorkerThread
    public final boolean zza(zzfh.zzj zzjVar, boolean z2) throws IllegalStateException {
        zzt();
        zzak();
        Preconditions.checkNotNull(zzjVar);
        Preconditions.checkNotEmpty(zzjVar.zzx());
        Preconditions.checkState(zzjVar.zzbe());
        zzv();
        long jCurrentTimeMillis = zzb().currentTimeMillis();
        if (zzjVar.zzl() < jCurrentTimeMillis - zzaf.zzm() || zzjVar.zzl() > zzaf.zzm() + jCurrentTimeMillis) {
            zzj().zzu().zza("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzfs.d(zzjVar.zzx()), Long.valueOf(jCurrentTimeMillis), Long.valueOf(zzjVar.zzl()));
        }
        try {
            byte[] bArrX = g_().x(zzjVar.zzbv());
            zzj().zzp().zza("Saving bundle, size", Integer.valueOf(bArrX.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzjVar.zzx());
            contentValues.put("bundle_end_timestamp", Long.valueOf(zzjVar.zzl()));
            contentValues.put("data", bArrX);
            contentValues.put("has_realtime", Integer.valueOf(z2 ? 1 : 0));
            if (zzjVar.zzbl()) {
                contentValues.put("retry_count", Integer.valueOf(zzjVar.zzf()));
            }
            try {
                if (a().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzj().zzg().zza("Failed to insert bundle (got -1). appId", zzfs.d(zzjVar.zzx()));
                return false;
            } catch (SQLiteException e2) {
                zzj().zzg().zza("Error storing bundle. appId", zzfs.d(zzjVar.zzx()), e2);
                return false;
            }
        } catch (IOException e3) {
            zzj().zzg().zza("Data loss. Failed to serialize bundle. appId", zzfs.d(zzjVar.zzx()), e3);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i2, zzev.zzb zzbVar) throws IllegalStateException {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzbVar);
        if (zzbVar.zzf().isEmpty()) {
            zzj().zzu().zza("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzfs.d(str), Integer.valueOf(i2), String.valueOf(zzbVar.zzl() ? Integer.valueOf(zzbVar.zzb()) : null));
            return false;
        }
        byte[] bArrZzbv = zzbVar.zzbv();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("audience_id", Integer.valueOf(i2));
        contentValues.put("filter_id", zzbVar.zzl() ? Integer.valueOf(zzbVar.zzb()) : null);
        contentValues.put(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, zzbVar.zzf());
        contentValues.put("session_scoped", zzbVar.zzm() ? Boolean.valueOf(zzbVar.zzj()) : null);
        contentValues.put("data", bArrZzbv);
        try {
            if (a().insertWithOnConflict("event_filters", null, contentValues, 5) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert event filter (got -1). appId", zzfs.d(str));
            return true;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing event filter. appId", zzfs.d(str), e2);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i2, zzev.zze zzeVar) throws IllegalStateException {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzeVar);
        if (zzeVar.zze().isEmpty()) {
            zzj().zzu().zza("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzfs.d(str), Integer.valueOf(i2), String.valueOf(zzeVar.zzi() ? Integer.valueOf(zzeVar.zza()) : null));
            return false;
        }
        byte[] bArrZzbv = zzeVar.zzbv();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("audience_id", Integer.valueOf(i2));
        contentValues.put("filter_id", zzeVar.zzi() ? Integer.valueOf(zzeVar.zza()) : null);
        contentValues.put("property_name", zzeVar.zze());
        contentValues.put("session_scoped", zzeVar.zzj() ? Boolean.valueOf(zzeVar.zzh()) : null);
        contentValues.put("data", bArrZzbv);
        try {
            if (a().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert property filter (got -1). appId", zzfs.d(str));
            return false;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing property filter. appId", zzfs.d(str), e2);
            return false;
        }
    }

    public final boolean zza(zzaz zzazVar, long j2, boolean z2) {
        zzt();
        zzak();
        Preconditions.checkNotNull(zzazVar);
        Preconditions.checkNotEmpty(zzazVar.f13260a);
        byte[] bArrZzbv = g_().d(zzazVar).zzbv();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzazVar.f13260a);
        contentValues.put("name", zzazVar.f13261b);
        contentValues.put(com.alipay.sdk.m.t.a.f9743k, Long.valueOf(zzazVar.f13262c));
        contentValues.put("metadata_fingerprint", Long.valueOf(j2));
        contentValues.put("data", bArrZzbv);
        contentValues.put("realtime", Integer.valueOf(z2 ? 1 : 0));
        try {
            if (a().insert("raw_events", null, contentValues) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert raw event (got -1). appId", zzfs.d(zzazVar.f13260a));
            return false;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing raw event. appId", zzfs.d(zzazVar.f13260a), e2);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(String str, zzmi zzmiVar) throws IllegalStateException {
        zzt();
        zzak();
        Preconditions.checkNotNull(zzmiVar);
        Preconditions.checkNotEmpty(str);
        long jCurrentTimeMillis = zzb().currentTimeMillis();
        if (zzmiVar.zzb < jCurrentTimeMillis - zzaf.zzm() || zzmiVar.zzb > zzaf.zzm() + jCurrentTimeMillis) {
            zzj().zzu().zza("Storing trigger URI outside of the max retention time span. appId, now, timestamp", zzfs.d(str), Long.valueOf(jCurrentTimeMillis), Long.valueOf(zzmiVar.zzb));
        }
        zzj().zzp().zza("Saving trigger URI");
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("trigger_uri", zzmiVar.zza);
        contentValues.put("source", Integer.valueOf(zzmiVar.zzc));
        contentValues.put("timestamp_millis", Long.valueOf(zzmiVar.zzb));
        try {
            if (a().insert("trigger_uris", null, contentValues) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert trigger URI (got -1). appId", zzfs.d(str));
            return false;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing trigger URI. appId", zzfs.d(str), e2);
            return false;
        }
    }

    public final boolean zza(String str, Long l2, long j2, zzfh.zze zzeVar) {
        zzt();
        zzak();
        Preconditions.checkNotNull(zzeVar);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l2);
        byte[] bArrZzbv = zzeVar.zzbv();
        zzj().zzp().zza("Saving complex main event, appId, data size", zzi().c(str), Integer.valueOf(bArrZzbv.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("event_id", l2);
        contentValues.put("children_to_process", Long.valueOf(j2));
        contentValues.put("main_event", bArrZzbv);
        try {
            if (a().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert complex main event (got -1). appId", zzfs.d(str));
            return false;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing complex main event. appId", zzfs.d(str), e2);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(zzad zzadVar) {
        Preconditions.checkNotNull(zzadVar);
        zzt();
        zzak();
        String str = zzadVar.zza;
        Preconditions.checkNotNull(str);
        if (zze(str, zzadVar.zzc.zza) == null && zzb("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{str}) >= 1000) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("origin", zzadVar.zzb);
        contentValues.put("name", zzadVar.zzc.zza);
        zza(contentValues, "value", Preconditions.checkNotNull(zzadVar.zzc.zza()));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.valueOf(zzadVar.zze));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, zzadVar.zzf);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.valueOf(zzadVar.zzh));
        zzq();
        contentValues.put("timed_out_event", zzne.A(zzadVar.zzg));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.valueOf(zzadVar.zzd));
        zzq();
        contentValues.put("triggered_event", zzne.A(zzadVar.zzi));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.valueOf(zzadVar.zzc.zzb));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.valueOf(zzadVar.zzj));
        zzq();
        contentValues.put("expired_event", zzne.A(zzadVar.zzk));
        try {
            if (a().insertWithOnConflict("conditional_properties", null, contentValues, 5) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert/update conditional user property (got -1)", zzfs.d(str));
            return true;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing conditional user property", zzfs.d(str), e2);
            return true;
        }
    }

    @WorkerThread
    public final boolean zza(zznb zznbVar) {
        Preconditions.checkNotNull(zznbVar);
        zzt();
        zzak();
        if (zze(zznbVar.f13322a, zznbVar.f13324c) == null) {
            if (zzne.O(zznbVar.f13324c)) {
                if (zzb("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zznbVar.f13322a}) >= zze().zza(zznbVar.f13322a, zzbi.zzag, 25, 100)) {
                    return false;
                }
            } else if (!"_npa".equals(zznbVar.f13324c) && zzb("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zznbVar.f13322a, zznbVar.f13323b}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zznbVar.f13322a);
        contentValues.put("origin", zznbVar.f13323b);
        contentValues.put("name", zznbVar.f13324c);
        contentValues.put("set_timestamp", Long.valueOf(zznbVar.f13325d));
        zza(contentValues, "value", zznbVar.f13326e);
        try {
            if (a().insertWithOnConflict("user_attributes", null, contentValues, 5) != -1) {
                return true;
            }
            zzj().zzg().zza("Failed to insert/update user property (got -1). appId", zzfs.d(zznbVar.f13322a));
            return true;
        } catch (SQLiteException e2) {
            zzj().zzg().zza("Error storing user property. appId", zzfs.d(zznbVar.f13322a), e2);
            return true;
        }
    }
}
