package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* loaded from: classes3.dex */
public final class zzgi {

    public static class zza {
        private static volatile zzho<zzgj> zza;

        private zza() {
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0034 A[Catch: all -> 0x0021, TryCatch #0 {all -> 0x0021, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x0018, B:18:0x0034, B:27:0x0050, B:13:0x0023, B:15:0x002b, B:20:0x003a, B:22:0x0040, B:25:0x0047, B:26:0x004b, B:28:0x0052), top: B:32:0x0003 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.google.android.gms.internal.measurement.zzho<com.google.android.gms.internal.measurement.zzgj> zza(android.content.Context r4) {
            /*
                java.lang.Class<com.google.android.gms.internal.measurement.zzgi$zza> r0 = com.google.android.gms.internal.measurement.zzgi.zza.class
                monitor-enter(r0)
                com.google.android.gms.internal.measurement.zzho<com.google.android.gms.internal.measurement.zzgj> r1 = com.google.android.gms.internal.measurement.zzgi.zza.zza     // Catch: java.lang.Throwable -> L21
                if (r1 != 0) goto L52
                com.google.android.gms.internal.measurement.zzgi r1 = new com.google.android.gms.internal.measurement.zzgi     // Catch: java.lang.Throwable -> L21
                r1.<init>()     // Catch: java.lang.Throwable -> L21
                java.lang.String r1 = android.os.Build.TYPE     // Catch: java.lang.Throwable -> L21
                java.lang.String r2 = android.os.Build.TAGS     // Catch: java.lang.Throwable -> L21
                java.lang.String r3 = "eng"
                boolean r3 = r1.equals(r3)     // Catch: java.lang.Throwable -> L21
                if (r3 != 0) goto L23
                java.lang.String r3 = "userdebug"
                boolean r1 = r1.equals(r3)     // Catch: java.lang.Throwable -> L21
                if (r1 == 0) goto L34
                goto L23
            L21:
                r4 = move-exception
                goto L54
            L23:
                java.lang.String r1 = "dev-keys"
                boolean r1 = r2.contains(r1)     // Catch: java.lang.Throwable -> L21
                if (r1 != 0) goto L3a
                java.lang.String r1 = "test-keys"
                boolean r1 = r2.contains(r1)     // Catch: java.lang.Throwable -> L21
                if (r1 == 0) goto L34
                goto L3a
            L34:
                com.google.android.gms.internal.measurement.zzho r4 = com.google.android.gms.internal.measurement.zzho.zzc()     // Catch: java.lang.Throwable -> L21
            L38:
                r1 = r4
                goto L50
            L3a:
                boolean r1 = com.google.android.gms.internal.measurement.zzfv.zza()     // Catch: java.lang.Throwable -> L21
                if (r1 == 0) goto L4b
                boolean r1 = com.google.android.gms.internal.auth.b.a(r4)     // Catch: java.lang.Throwable -> L21
                if (r1 == 0) goto L47
                goto L4b
            L47:
                android.content.Context r4 = androidx.profileinstaller.a.a(r4)     // Catch: java.lang.Throwable -> L21
            L4b:
                com.google.android.gms.internal.measurement.zzho r4 = com.google.android.gms.internal.measurement.zzgi.a(r4)     // Catch: java.lang.Throwable -> L21
                goto L38
            L50:
                com.google.android.gms.internal.measurement.zzgi.zza.zza = r1     // Catch: java.lang.Throwable -> L21
            L52:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
                return r1
            L54:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgi.zza.zza(android.content.Context):com.google.android.gms.internal.measurement.zzho");
        }
    }

    static zzho a(Context context) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            StrictMode.allowThreadDiskWrites();
            zzho<File> zzhoVarZzb = zzb(context);
            zzho zzhoVarZza = zzhoVarZzb.zzb() ? zzho.zza(zza(context, zzhoVarZzb.zza())) : zzho.zzc();
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            return zzhoVarZza;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th;
        }
    }

    private static zzgj zza(Context context, File file) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
                SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
                HashMap map = new HashMap();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        Log.w("HermeticFileOverrides", "Parsed " + String.valueOf(file) + " for Android package " + context.getPackageName());
                        zzga zzgaVar = new zzga(simpleArrayMap);
                        bufferedReader.close();
                        return zzgaVar;
                    }
                    String[] strArrSplit = line.split(" ", 3);
                    if (strArrSplit.length != 3) {
                        Log.e("HermeticFileOverrides", "Invalid: " + line);
                    } else {
                        String strZza = zza(strArrSplit[0]);
                        String strDecode = Uri.decode(zza(strArrSplit[1]));
                        String strDecode2 = (String) map.get(strArrSplit[2]);
                        if (strDecode2 == null) {
                            String strZza2 = zza(strArrSplit[2]);
                            strDecode2 = Uri.decode(strZza2);
                            if (strDecode2.length() < 1024 || strDecode2 == strZza2) {
                                map.put(strZza2, strDecode2);
                            }
                        }
                        if (!simpleArrayMap.containsKey(strZza)) {
                            simpleArrayMap.put(strZza, new SimpleArrayMap());
                        }
                        ((SimpleArrayMap) simpleArrayMap.get(strZza)).put(strDecode, strDecode2);
                    }
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    try {
                        Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class).invoke(th, th2);
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static zzho<File> zzb(Context context) {
        try {
            File file = new File(context.getDir("phenotype_hermetic", 0), "overrides.txt");
            return file.exists() ? zzho.zza(file) : zzho.zzc();
        } catch (RuntimeException e2) {
            Log.e("HermeticFileOverrides", "no data dir", e2);
            return zzho.zzc();
        }
    }

    private static final String zza(String str) {
        return new String(str);
    }
}
