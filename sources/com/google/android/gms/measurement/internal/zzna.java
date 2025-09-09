package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import bolts.MeasurementEvent;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzev;
import com.google.android.gms.internal.measurement.zzfh;
import com.google.android.gms.internal.measurement.zzni;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzsg;
import com.google.android.gms.internal.measurement.zzss;
import com.google.android.gms.measurement.internal.zzie;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.bc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzna extends zzml {
    zzna(zzmq zzmqVar) {
        super(zzmqVar);
    }

    static int a(zzfh.zzj.zza zzaVar, String str) {
        if (zzaVar == null) {
            return -1;
        }
        for (int i2 = 0; i2 < zzaVar.zzb(); i2++) {
            if (str.equals(zzaVar.zzj(i2).zzg())) {
                return i2;
            }
        }
        return -1;
    }

    static zzfh.zzg e(zzfh.zze zzeVar, String str) {
        for (zzfh.zzg zzgVar : zzeVar.zzh()) {
            if (zzgVar.zzg().equals(str)) {
                return zzgVar;
            }
        }
        return null;
    }

    static zzni f(zzni zzniVar, byte[] bArr) {
        com.google.android.gms.internal.measurement.zzlj zzljVarZza = com.google.android.gms.internal.measurement.zzlj.zza();
        return zzljVarZza != null ? zzniVar.zza(bArr, zzljVarZza) : zzniVar.zza(bArr);
    }

    static List l(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            long j2 = 0;
            for (int i3 = 0; i3 < 64; i3++) {
                int i4 = (i2 << 6) + i3;
                if (i4 < bitSet.length()) {
                    if (bitSet.get(i4)) {
                        j2 |= 1 << i3;
                    }
                }
            }
            arrayList.add(Long.valueOf(j2));
        }
        return arrayList;
    }

    static void o(zzfh.zze.zza zzaVar, String str, Object obj) {
        List<zzfh.zzg> listZzf = zzaVar.zzf();
        int i2 = 0;
        while (true) {
            if (i2 >= listZzf.size()) {
                i2 = -1;
                break;
            } else if (str.equals(listZzf.get(i2).zzg())) {
                break;
            } else {
                i2++;
            }
        }
        zzfh.zzg.zza zzaVarZza = zzfh.zzg.zze().zza(str);
        if (obj instanceof Long) {
            zzaVarZza.zza(((Long) obj).longValue());
        } else if (obj instanceof String) {
            zzaVarZza.zzb((String) obj);
        } else if (obj instanceof Double) {
            zzaVarZza.zza(((Double) obj).doubleValue());
        }
        if (i2 >= 0) {
            zzaVar.zza(i2, zzaVarZza);
        } else {
            zzaVar.zza(zzaVarZza);
        }
    }

    static boolean t(zzbg zzbgVar, zzo zzoVar) {
        Preconditions.checkNotNull(zzbgVar);
        Preconditions.checkNotNull(zzoVar);
        return (TextUtils.isEmpty(zzoVar.zzb) && TextUtils.isEmpty(zzoVar.zzp)) ? false : true;
    }

    static boolean u(List list, int i2) {
        if (i2 < (list.size() << 6)) {
            return ((1 << (i2 % 64)) & ((Long) list.get(i2 / 64)).longValue()) != 0;
        }
        return false;
    }

    static Object v(zzfh.zze zzeVar, String str) {
        zzfh.zzg zzgVarE = e(zzeVar, str);
        if (zzgVarE == null) {
            return null;
        }
        if (zzgVarE.zzn()) {
            return zzgVarE.zzh();
        }
        if (zzgVarE.zzl()) {
            return Long.valueOf(zzgVarE.zzd());
        }
        if (zzgVarE.zzj()) {
            return Double.valueOf(zzgVarE.zza());
        }
        if (zzgVarE.zzc() <= 0) {
            return null;
        }
        List<zzfh.zzg> listZzi = zzgVarE.zzi();
        ArrayList arrayList = new ArrayList();
        for (zzfh.zzg zzgVar : listZzi) {
            if (zzgVar != null) {
                Bundle bundle = new Bundle();
                for (zzfh.zzg zzgVar2 : zzgVar.zzi()) {
                    if (zzgVar2.zzn()) {
                        bundle.putString(zzgVar2.zzg(), zzgVar2.zzh());
                    } else if (zzgVar2.zzl()) {
                        bundle.putLong(zzgVar2.zzg(), zzgVar2.zzd());
                    } else if (zzgVar2.zzj()) {
                        bundle.putDouble(zzgVar2.zzg(), zzgVar2.zza());
                    }
                }
                if (!bundle.isEmpty()) {
                    arrayList.add(bundle);
                }
            }
        }
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    static boolean w(String str) {
        return str != null && str.matches("([+-])?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    final List A() throws NumberFormatException {
        Map<String, String> mapZza = zzbi.zza(this.f13314b.zza());
        if (mapZza == null || mapZza.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int iIntValue = zzbi.zzap.zza(null).intValue();
        for (Map.Entry<String, String> entry : mapZza.entrySet()) {
            if (entry.getKey().startsWith("measurement.id.")) {
                try {
                    int i2 = Integer.parseInt(entry.getValue());
                    if (i2 != 0) {
                        arrayList.add(Integer.valueOf(i2));
                        if (arrayList.size() >= iIntValue) {
                            zzj().zzu().zza("Too many experiment IDs. Number of IDs", Integer.valueOf(arrayList.size()));
                            break;
                        }
                        continue;
                    } else {
                        continue;
                    }
                } catch (NumberFormatException e2) {
                    zzj().zzu().zza("Experiment ID NumberFormatException", e2);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    final long b(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        zzq().zzt();
        MessageDigest messageDigestR = zzne.R();
        if (messageDigestR != null) {
            return zzne.f(messageDigestR.digest(bArr));
        }
        zzj().zzg().zza("Failed to get MD5");
        return 0L;
    }

    final Parcelable c(byte[] bArr, Parcelable.Creator creator) {
        if (bArr == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.unmarshall(bArr, 0, bArr.length);
            parcelObtain.setDataPosition(0);
            return (Parcelable) creator.createFromParcel(parcelObtain);
        } catch (SafeParcelReader.ParseException unused) {
            zzj().zzg().zza("Failed to load parcelable from buffer");
            return null;
        } finally {
            parcelObtain.recycle();
        }
    }

    final zzfh.zze d(zzaz zzazVar) {
        zzfh.zze.zza zzaVarZza = zzfh.zze.zze().zza(zzazVar.f13263d);
        Iterator<String> it = zzazVar.f13264e.iterator();
        while (it.hasNext()) {
            String next = it.next();
            zzfh.zzg.zza zzaVarZza2 = zzfh.zzg.zze().zza(next);
            Object objD = zzazVar.f13264e.d(next);
            Preconditions.checkNotNull(objD);
            p(zzaVarZza2, objD);
            zzaVarZza.zza(zzaVarZza2);
        }
        return (zzfh.zze) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZza.zzab());
    }

    final zzbg g(com.google.android.gms.internal.measurement.zzad zzadVar) {
        Object obj;
        Bundle bundleZza = zza(zzadVar.zzc(), true);
        String string = (!bundleZza.containsKey("_o") || (obj = bundleZza.get("_o")) == null) ? PushConstants.EXTRA_APPLICATION_PENDING_INTENT : obj.toString();
        String strZzb = zzii.zzb(zzadVar.zzb());
        if (strZzb == null) {
            strZzb = zzadVar.zzb();
        }
        return new zzbg(strZzb, new zzbb(bundleZza), string, zzadVar.zza());
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzna g_() {
        return super.g_();
    }

    final zzmi h(String str, zzfh.zzj zzjVar, zzfh.zze.zza zzaVar, String str2) {
        int iIndexOf;
        if (!zzsg.zzb() || !zze().zze(str, zzbi.zzcf)) {
            return null;
        }
        long jCurrentTimeMillis = zzb().currentTimeMillis();
        String[] strArrSplit = zze().zzd(str, zzbi.zzbb).split(",");
        HashSet hashSet = new HashSet(strArrSplit.length);
        for (String str3 : strArrSplit) {
            str3.getClass();
            if (!hashSet.add(str3)) {
                throw new IllegalArgumentException("duplicate element: " + ((Object) str3));
            }
        }
        Set setUnmodifiableSet = Collections.unmodifiableSet(hashSet);
        zzmo zzmoVarZzo = zzo();
        String strM = zzmoVarZzo.zzm().m(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(zzmoVarZzo.zze().zzd(str, zzbi.zzav));
        if (TextUtils.isEmpty(strM)) {
            builder.authority(zzmoVarZzo.zze().zzd(str, zzbi.zzaw));
        } else {
            builder.authority(strM + "." + zzmoVarZzo.zze().zzd(str, zzbi.zzaw));
        }
        builder.path(zzmoVarZzo.zze().zzd(str, zzbi.zzax));
        zza(builder, "gmp_app_id", zzjVar.zzah(), (Set<String>) setUnmodifiableSet);
        zza(builder, "gmp_version", "81010", (Set<String>) setUnmodifiableSet);
        String strZzy = zzjVar.zzy();
        zzaf zzafVarZze = zze();
        zzff<Boolean> zzffVar = zzbi.zzci;
        String str4 = "";
        if (zzafVarZze.zze(str, zzffVar) && zzm().t(str)) {
            strZzy = "";
        }
        zza(builder, "app_instance_id", strZzy, (Set<String>) setUnmodifiableSet);
        zza(builder, "rdid", zzjVar.zzal(), (Set<String>) setUnmodifiableSet);
        zza(builder, "bundle_id", zzjVar.zzx(), (Set<String>) setUnmodifiableSet);
        String strZze = zzaVar.zze();
        String strZza = zzii.zza(strZze);
        if (!TextUtils.isEmpty(strZza)) {
            strZze = strZza;
        }
        zza(builder, "app_event_name", strZze, (Set<String>) setUnmodifiableSet);
        zza(builder, "app_version", String.valueOf(zzjVar.zzb()), (Set<String>) setUnmodifiableSet);
        String strZzaj = zzjVar.zzaj();
        if (!zze().zze(str, zzffVar) || !zzm().x(str)) {
            str4 = strZzaj;
        } else if (zze().zze(str, zzbi.zzbv)) {
            if (!TextUtils.isEmpty(strZzaj) && (iIndexOf = strZzaj.indexOf(".")) != -1) {
                strZzaj = strZzaj.substring(0, iIndexOf);
            }
            str4 = strZzaj;
        }
        zza(builder, bc.f21426y, str4, (Set<String>) setUnmodifiableSet);
        zza(builder, com.alipay.sdk.m.t.a.f9743k, String.valueOf(zzaVar.zzc()), (Set<String>) setUnmodifiableSet);
        if (zzjVar.zzat()) {
            zza(builder, "lat", "1", (Set<String>) setUnmodifiableSet);
        }
        zza(builder, "privacy_sandbox_version", String.valueOf(zzjVar.zza()), (Set<String>) setUnmodifiableSet);
        zza(builder, "trigger_uri_source", "1", (Set<String>) setUnmodifiableSet);
        zza(builder, "trigger_uri_timestamp", String.valueOf(jCurrentTimeMillis), (Set<String>) setUnmodifiableSet);
        if (str2 != null) {
            zza(builder, "request_uuid", str2, (Set<String>) setUnmodifiableSet);
        }
        List<zzfh.zzg> listZzf = zzaVar.zzf();
        Bundle bundle = new Bundle();
        for (zzfh.zzg zzgVar : listZzf) {
            String strZzg = zzgVar.zzg();
            if (zzgVar.zzj()) {
                bundle.putString(strZzg, String.valueOf(zzgVar.zza()));
            } else if (zzgVar.zzk()) {
                bundle.putString(strZzg, String.valueOf(zzgVar.zzb()));
            } else if (zzgVar.zzn()) {
                bundle.putString(strZzg, zzgVar.zzh());
            } else if (zzgVar.zzl()) {
                bundle.putString(strZzg, String.valueOf(zzgVar.zzd()));
            }
        }
        zza(builder, zze().zzd(str, zzbi.zzba).split("\\|"), bundle, (Set<String>) setUnmodifiableSet);
        List<zzfh.zzn> listZzaq = zzjVar.zzaq();
        Bundle bundle2 = new Bundle();
        for (zzfh.zzn zznVar : listZzaq) {
            String strZzg2 = zznVar.zzg();
            if (zznVar.zzi()) {
                bundle2.putString(strZzg2, String.valueOf(zznVar.zza()));
            } else if (zznVar.zzj()) {
                bundle2.putString(strZzg2, String.valueOf(zznVar.zzb()));
            } else if (zznVar.zzm()) {
                bundle2.putString(strZzg2, zznVar.zzh());
            } else if (zznVar.zzk()) {
                bundle2.putString(strZzg2, String.valueOf(zznVar.zzc()));
            }
        }
        zza(builder, zze().zzd(str, zzbi.zzaz).split("\\|"), bundle2, (Set<String>) setUnmodifiableSet);
        if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
            zza(builder, "dma", zzjVar.zzas() ? "1" : "0", (Set<String>) setUnmodifiableSet);
            if (!TextUtils.isEmpty(zzjVar.zzad())) {
                zza(builder, "dma_cps", zzjVar.zzad(), (Set<String>) setUnmodifiableSet);
            }
        }
        return new zzmi(builder.build().toString(), jCurrentTimeMillis, 1);
    }

    final String i(zzev.zzb zzbVar) {
        if (zzbVar == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        if (zzbVar.zzl()) {
            zza(sb, 0, "filter_id", Integer.valueOf(zzbVar.zzb()));
        }
        zza(sb, 0, MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, zzi().c(zzbVar.zzf()));
        String strZza = zza(zzbVar.zzh(), zzbVar.zzi(), zzbVar.zzj());
        if (!strZza.isEmpty()) {
            zza(sb, 0, "filter_type", strZza);
        }
        if (zzbVar.zzk()) {
            zza(sb, 1, "event_count_filter", zzbVar.zze());
        }
        if (zzbVar.zza() > 0) {
            sb.append("  filters {\n");
            Iterator<zzev.zzc> it = zzbVar.zzg().iterator();
            while (it.hasNext()) {
                zza(sb, 2, it.next());
            }
        }
        zza(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    final String j(zzev.zze zzeVar) {
        if (zzeVar == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        if (zzeVar.zzi()) {
            zza(sb, 0, "filter_id", Integer.valueOf(zzeVar.zza()));
        }
        zza(sb, 0, "property_name", zzi().e(zzeVar.zze()));
        String strZza = zza(zzeVar.zzf(), zzeVar.zzg(), zzeVar.zzh());
        if (!strZza.isEmpty()) {
            zza(sb, 0, "filter_type", strZza);
        }
        zza(sb, 1, zzeVar.zzb());
        sb.append("}\n");
        return sb.toString();
    }

    final String k(zzfh.zzi zziVar) {
        zzfh.zzb zzbVarZzt;
        if (zziVar == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        for (zzfh.zzj zzjVar : zziVar.zzd()) {
            if (zzjVar != null) {
                zza(sb, 1);
                sb.append("bundle {\n");
                if (zzjVar.zzbk()) {
                    zza(sb, 1, "protocol_version", Integer.valueOf(zzjVar.zze()));
                }
                if (zzss.zzb() && zze().zze(zzjVar.zzx(), zzbi.zzbt) && zzjVar.zzbn()) {
                    zza(sb, 1, "session_stitching_token", zzjVar.zzam());
                }
                zza(sb, 1, DispatchConstants.PLATFORM, zzjVar.zzak());
                if (zzjVar.zzbf()) {
                    zza(sb, 1, "gmp_version", Long.valueOf(zzjVar.zzm()));
                }
                if (zzjVar.zzbs()) {
                    zza(sb, 1, "uploading_gmp_version", Long.valueOf(zzjVar.zzs()));
                }
                if (zzjVar.zzbd()) {
                    zza(sb, 1, "dynamite_version", Long.valueOf(zzjVar.zzk()));
                }
                if (zzjVar.zzay()) {
                    zza(sb, 1, "config_version", Long.valueOf(zzjVar.zzi()));
                }
                zza(sb, 1, "gmp_app_id", zzjVar.zzah());
                zza(sb, 1, "admob_app_id", zzjVar.zzw());
                zza(sb, 1, "app_id", zzjVar.zzx());
                zza(sb, 1, "app_version", zzjVar.zzaa());
                if (zzjVar.zzav()) {
                    zza(sb, 1, "app_version_major", Integer.valueOf(zzjVar.zzb()));
                }
                zza(sb, 1, "firebase_instance_id", zzjVar.zzag());
                if (zzjVar.zzbc()) {
                    zza(sb, 1, "dev_cert_hash", Long.valueOf(zzjVar.zzj()));
                }
                zza(sb, 1, "app_store", zzjVar.zzz());
                if (zzjVar.zzbr()) {
                    zza(sb, 1, "upload_timestamp_millis", Long.valueOf(zzjVar.zzr()));
                }
                if (zzjVar.zzbo()) {
                    zza(sb, 1, "start_timestamp_millis", Long.valueOf(zzjVar.zzp()));
                }
                if (zzjVar.zzbe()) {
                    zza(sb, 1, "end_timestamp_millis", Long.valueOf(zzjVar.zzl()));
                }
                if (zzjVar.zzbj()) {
                    zza(sb, 1, "previous_bundle_start_timestamp_millis", Long.valueOf(zzjVar.zzo()));
                }
                if (zzjVar.zzbi()) {
                    zza(sb, 1, "previous_bundle_end_timestamp_millis", Long.valueOf(zzjVar.zzn()));
                }
                zza(sb, 1, "app_instance_id", zzjVar.zzy());
                zza(sb, 1, "resettable_device_id", zzjVar.zzal());
                zza(sb, 1, "ds_id", zzjVar.zzaf());
                if (zzjVar.zzbh()) {
                    zza(sb, 1, "limited_ad_tracking", Boolean.valueOf(zzjVar.zzat()));
                }
                zza(sb, 1, bc.f21426y, zzjVar.zzaj());
                zza(sb, 1, "device_model", zzjVar.zzae());
                zza(sb, 1, "user_default_language", zzjVar.zzan());
                if (zzjVar.zzbq()) {
                    zza(sb, 1, "time_zone_offset_minutes", Integer.valueOf(zzjVar.zzg()));
                }
                if (zzjVar.zzax()) {
                    zza(sb, 1, "bundle_sequential_index", Integer.valueOf(zzjVar.zzc()));
                }
                if (zzjVar.zzbm()) {
                    zza(sb, 1, "service_upload", Boolean.valueOf(zzjVar.zzau()));
                }
                zza(sb, 1, "health_monitor", zzjVar.zzai());
                if (zzjVar.zzbl()) {
                    zza(sb, 1, "retry_counter", Integer.valueOf(zzjVar.zzf()));
                }
                if (zzjVar.zzba()) {
                    zza(sb, 1, "consent_signals", zzjVar.zzac());
                }
                if (zzjVar.zzbg()) {
                    zza(sb, 1, "is_dma_region", Boolean.valueOf(zzjVar.zzas()));
                }
                if (zzjVar.zzbb()) {
                    zza(sb, 1, "core_platform_services", zzjVar.zzad());
                }
                if (zzjVar.zzaz()) {
                    zza(sb, 1, "consent_diagnostics", zzjVar.zzab());
                }
                if (zzjVar.zzbp()) {
                    zza(sb, 1, "target_os_version", Long.valueOf(zzjVar.zzq()));
                }
                if (zzsg.zzb() && zze().zze(zzjVar.zzx(), zzbi.zzcf)) {
                    zza(sb, 1, "ad_services_version", Integer.valueOf(zzjVar.zza()));
                    if (zzjVar.zzaw() && (zzbVarZzt = zzjVar.zzt()) != null) {
                        zza(sb, 2);
                        sb.append("attribution_eligibility_status {\n");
                        zza(sb, 2, "eligible", Boolean.valueOf(zzbVarZzt.zzf()));
                        zza(sb, 2, "no_access_adservices_attribution_permission", Boolean.valueOf(zzbVarZzt.zzh()));
                        zza(sb, 2, "pre_r", Boolean.valueOf(zzbVarZzt.zzi()));
                        zza(sb, 2, "r_extensions_too_old", Boolean.valueOf(zzbVarZzt.zzj()));
                        zza(sb, 2, "adservices_extension_too_old", Boolean.valueOf(zzbVarZzt.zze()));
                        zza(sb, 2, "ad_storage_not_allowed", Boolean.valueOf(zzbVarZzt.zzd()));
                        zza(sb, 2, "measurement_manager_disabled", Boolean.valueOf(zzbVarZzt.zzg()));
                        zza(sb, 2);
                        sb.append("}\n");
                    }
                }
                List<zzfh.zzn> listZzaq = zzjVar.zzaq();
                if (listZzaq != null) {
                    for (zzfh.zzn zznVar : listZzaq) {
                        if (zznVar != null) {
                            zza(sb, 2);
                            sb.append("user_property {\n");
                            zza(sb, 2, "set_timestamp_millis", zznVar.zzl() ? Long.valueOf(zznVar.zzd()) : null);
                            zza(sb, 2, "name", zzi().e(zznVar.zzg()));
                            zza(sb, 2, "string_value", zznVar.zzh());
                            zza(sb, 2, "int_value", zznVar.zzk() ? Long.valueOf(zznVar.zzc()) : null);
                            zza(sb, 2, "double_value", zznVar.zzi() ? Double.valueOf(zznVar.zza()) : null);
                            zza(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                List<zzfh.zzc> listZzao = zzjVar.zzao();
                zzjVar.zzx();
                if (listZzao != null) {
                    for (zzfh.zzc zzcVar : listZzao) {
                        if (zzcVar != null) {
                            zza(sb, 2);
                            sb.append("audience_membership {\n");
                            if (zzcVar.zzg()) {
                                zza(sb, 2, "audience_id", Integer.valueOf(zzcVar.zza()));
                            }
                            if (zzcVar.zzh()) {
                                zza(sb, 2, "new_audience", Boolean.valueOf(zzcVar.zzf()));
                            }
                            zza(sb, 2, "current_data", zzcVar.zzd());
                            if (zzcVar.zzi()) {
                                zza(sb, 2, "previous_data", zzcVar.zze());
                            }
                            zza(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                List<zzfh.zze> listZzap = zzjVar.zzap();
                if (listZzap != null) {
                    for (zzfh.zze zzeVar : listZzap) {
                        if (zzeVar != null) {
                            zza(sb, 2);
                            sb.append("event {\n");
                            zza(sb, 2, "name", zzi().c(zzeVar.zzg()));
                            if (zzeVar.zzk()) {
                                zza(sb, 2, "timestamp_millis", Long.valueOf(zzeVar.zzd()));
                            }
                            if (zzeVar.zzj()) {
                                zza(sb, 2, "previous_timestamp_millis", Long.valueOf(zzeVar.zzc()));
                            }
                            if (zzeVar.zzi()) {
                                zza(sb, 2, "count", Integer.valueOf(zzeVar.zza()));
                            }
                            if (zzeVar.zzb() != 0) {
                                zza(sb, 2, zzeVar.zzh());
                            }
                            zza(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                zza(sb, 1);
                sb.append("}\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    final List m(List list, List list2) {
        int i2;
        ArrayList arrayList = new ArrayList(list);
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() < 0) {
                zzj().zzu().zza("Ignoring negative bit index to be cleared", num);
            } else {
                int iIntValue = num.intValue() / 64;
                if (iIntValue >= arrayList.size()) {
                    zzj().zzu().zza("Ignoring bit index greater than bitSet size", num, Integer.valueOf(arrayList.size()));
                } else {
                    arrayList.set(iIntValue, Long.valueOf(((Long) arrayList.get(iIntValue)).longValue() & (~(1 << (num.intValue() % 64)))));
                }
            }
        }
        int size = arrayList.size();
        int size2 = arrayList.size() - 1;
        while (true) {
            int i3 = size2;
            i2 = size;
            size = i3;
            if (size < 0 || ((Long) arrayList.get(size)).longValue() != 0) {
                break;
            }
            size2 = size - 1;
        }
        return arrayList.subList(0, i2);
    }

    final Map n(Bundle bundle, boolean z2) {
        HashMap map = new HashMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            boolean z3 = obj instanceof Parcelable[];
            if (z3 || (obj instanceof ArrayList) || (obj instanceof Bundle)) {
                if (z2) {
                    ArrayList arrayList = new ArrayList();
                    if (z3) {
                        for (Parcelable parcelable : (Parcelable[]) obj) {
                            if (parcelable instanceof Bundle) {
                                arrayList.add(n((Bundle) parcelable, false));
                            }
                        }
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList2 = (ArrayList) obj;
                        int size = arrayList2.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj2 = arrayList2.get(i2);
                            i2++;
                            if (obj2 instanceof Bundle) {
                                arrayList.add(n((Bundle) obj2, false));
                            }
                        }
                    } else if (obj instanceof Bundle) {
                        arrayList.add(n((Bundle) obj, false));
                    }
                    map.put(str, arrayList);
                }
            } else if (obj != null) {
                map.put(str, obj);
            }
        }
        return map;
    }

    final void p(zzfh.zzg.zza zzaVar, Object obj) {
        Preconditions.checkNotNull(obj);
        zzaVar.zze().zzc().zzb().zzd();
        if (obj instanceof String) {
            zzaVar.zzb((String) obj);
            return;
        }
        if (obj instanceof Long) {
            zzaVar.zza(((Long) obj).longValue());
            return;
        }
        if (obj instanceof Double) {
            zzaVar.zza(((Double) obj).doubleValue());
            return;
        }
        if (!(obj instanceof Bundle[])) {
            zzj().zzg().zza("Ignoring invalid (type) event param value", obj);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Bundle bundle : (Bundle[]) obj) {
            if (bundle != null) {
                zzfh.zzg.zza zzaVarZze = zzfh.zzg.zze();
                for (String str : bundle.keySet()) {
                    zzfh.zzg.zza zzaVarZza = zzfh.zzg.zze().zza(str);
                    Object obj2 = bundle.get(str);
                    if (obj2 instanceof Long) {
                        zzaVarZza.zza(((Long) obj2).longValue());
                    } else if (obj2 instanceof String) {
                        zzaVarZza.zzb((String) obj2);
                    } else if (obj2 instanceof Double) {
                        zzaVarZza.zza(((Double) obj2).doubleValue());
                    }
                    zzaVarZze.zza(zzaVarZza);
                }
                if (zzaVarZze.zza() > 0) {
                    arrayList.add((zzfh.zzg) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZze.zzab()));
                }
            }
        }
        zzaVar.zza(arrayList);
    }

    final void q(zzfh.zzj.zza zzaVar) {
        zzj().zzp().zza("Checking account type status for ad personalization signals");
        if (y(zzaVar.zzr())) {
            zzj().zzc().zza("Turning off ad personalization due to account type");
            zzfh.zzn zznVar = (zzfh.zzn) ((com.google.android.gms.internal.measurement.zzlw) zzfh.zzn.zze().zza("_npa").zzb(zzf().c()).zza(1L).zzab());
            int i2 = 0;
            while (true) {
                if (i2 >= zzaVar.zzb()) {
                    zzaVar.zza(zznVar);
                    break;
                } else {
                    if ("_npa".equals(zzaVar.zzj(i2).zzg())) {
                        zzaVar.zza(i2, zznVar);
                        break;
                    }
                    i2++;
                }
            }
            if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
                zzak zzakVarZza = zzak.zza(zzaVar.zzs());
                zzakVarZza.zza(zzie.zza.AD_PERSONALIZATION, zzaj.CHILD_ACCOUNT);
                zzaVar.zzf(zzakVarZza.toString());
            }
        }
    }

    final void r(zzfh.zzn.zza zzaVar, Object obj) {
        Preconditions.checkNotNull(obj);
        zzaVar.zzc().zzb().zza();
        if (obj instanceof String) {
            zzaVar.zzb((String) obj);
            return;
        }
        if (obj instanceof Long) {
            zzaVar.zza(((Long) obj).longValue());
        } else if (obj instanceof Double) {
            zzaVar.zza(((Double) obj).doubleValue());
        } else {
            zzj().zzg().zza("Ignoring invalid (type) user attribute value", obj);
        }
    }

    final boolean s(long j2, long j3) {
        return j2 == 0 || j3 <= 0 || Math.abs(zzb().currentTimeMillis() - j2) > j3;
    }

    final byte[] x(byte[] bArr) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            zzj().zzg().zza("Failed to gzip content", e2);
            throw e2;
        }
    }

    final boolean y(String str) {
        Preconditions.checkNotNull(str);
        zzh zzhVarZzd = zzh().zzd(str);
        return zzhVarZzd != null && zzf().e() && zzhVarZzd.zzaj() && zzm().zzk(str);
    }

    final byte[] z(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int i2 = gZIPInputStream.read(bArr2);
                if (i2 <= 0) {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr2, 0, i2);
            }
        } catch (IOException e2) {
            zzj().zzg().zza("Failed to ungzip content", e2);
            throw e2;
        }
    }

    final long zza(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        return b(str.getBytes(Charset.forName("UTF-8")));
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    protected final boolean zzc() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzt zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzao zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzgp zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzlp zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzmo zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    private final Bundle zza(Map<String, Object> map, boolean z2) {
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj == null) {
                bundle.putString(str, null);
            } else if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (!(obj instanceof ArrayList)) {
                bundle.putString(str, obj.toString());
            } else if (z2) {
                ArrayList arrayList = (ArrayList) obj;
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    arrayList2.add(zza((Map<String, Object>) obj2, false));
                }
                bundle.putParcelableArray(str, (Parcelable[]) arrayList2.toArray(new Parcelable[0]));
            }
        }
        return bundle;
    }

    private static String zza(boolean z2, boolean z3, boolean z4) {
        StringBuilder sb = new StringBuilder();
        if (z2) {
            sb.append("Dynamic ");
        }
        if (z3) {
            sb.append("Sequence ");
        }
        if (z4) {
            sb.append("Session-Scoped ");
        }
        return sb.toString();
    }

    private static void zza(Uri.Builder builder, String[] strArr, Bundle bundle, Set<String> set) {
        for (String str : strArr) {
            String[] strArrSplit = str.split(",");
            String str2 = strArrSplit[0];
            String str3 = strArrSplit[strArrSplit.length - 1];
            String string = bundle.getString(str2);
            if (string != null) {
                zza(builder, str3, string, set);
            }
        }
    }

    private static void zza(StringBuilder sb, int i2, String str, zzfh.zzl zzlVar) {
        if (zzlVar == null) {
            return;
        }
        zza(sb, 3);
        sb.append(str);
        sb.append(" {\n");
        if (zzlVar.zzb() != 0) {
            zza(sb, 4);
            sb.append("results: ");
            int i3 = 0;
            for (Long l2 : zzlVar.zzi()) {
                int i4 = i3 + 1;
                if (i3 != 0) {
                    sb.append(", ");
                }
                sb.append(l2);
                i3 = i4;
            }
            sb.append('\n');
        }
        if (zzlVar.zzd() != 0) {
            zza(sb, 4);
            sb.append("status: ");
            int i5 = 0;
            for (Long l3 : zzlVar.zzk()) {
                int i6 = i5 + 1;
                if (i5 != 0) {
                    sb.append(", ");
                }
                sb.append(l3);
                i5 = i6;
            }
            sb.append('\n');
        }
        if (zzlVar.zza() != 0) {
            zza(sb, 4);
            sb.append("dynamic_filter_timestamps: {");
            int i7 = 0;
            for (zzfh.zzd zzdVar : zzlVar.zzh()) {
                int i8 = i7 + 1;
                if (i7 != 0) {
                    sb.append(", ");
                }
                sb.append(zzdVar.zzf() ? Integer.valueOf(zzdVar.zza()) : null);
                sb.append(":");
                sb.append(zzdVar.zze() ? Long.valueOf(zzdVar.zzb()) : null);
                i7 = i8;
            }
            sb.append("}\n");
        }
        if (zzlVar.zzc() != 0) {
            zza(sb, 4);
            sb.append("sequence_filter_timestamps: {");
            int i9 = 0;
            for (zzfh.zzm zzmVar : zzlVar.zzj()) {
                int i10 = i9 + 1;
                if (i9 != 0) {
                    sb.append(", ");
                }
                sb.append(zzmVar.zzf() ? Integer.valueOf(zzmVar.zzb()) : null);
                sb.append(": [");
                Iterator<Long> it = zzmVar.zze().iterator();
                int i11 = 0;
                while (it.hasNext()) {
                    long jLongValue = it.next().longValue();
                    int i12 = i11 + 1;
                    if (i11 != 0) {
                        sb.append(", ");
                    }
                    sb.append(jLongValue);
                    i11 = i12;
                }
                sb.append("]");
                i9 = i10;
            }
            sb.append("}\n");
        }
        zza(sb, 3);
        sb.append("}\n");
    }

    private final void zza(StringBuilder sb, int i2, List<zzfh.zzg> list) {
        if (list == null) {
            return;
        }
        int i3 = i2 + 1;
        for (zzfh.zzg zzgVar : list) {
            if (zzgVar != null) {
                zza(sb, i3);
                sb.append("param {\n");
                zza(sb, i3, "name", zzgVar.zzm() ? zzi().d(zzgVar.zzg()) : null);
                zza(sb, i3, "string_value", zzgVar.zzn() ? zzgVar.zzh() : null);
                zza(sb, i3, "int_value", zzgVar.zzl() ? Long.valueOf(zzgVar.zzd()) : null);
                zza(sb, i3, "double_value", zzgVar.zzj() ? Double.valueOf(zzgVar.zza()) : null);
                if (zzgVar.zzc() > 0) {
                    zza(sb, i3, zzgVar.zzi());
                }
                zza(sb, i3);
                sb.append("}\n");
            }
        }
    }

    private final void zza(StringBuilder sb, int i2, zzev.zzc zzcVar) {
        if (zzcVar == null) {
            return;
        }
        zza(sb, i2);
        sb.append("filter {\n");
        if (zzcVar.zzg()) {
            zza(sb, i2, "complement", Boolean.valueOf(zzcVar.zzf()));
        }
        if (zzcVar.zzi()) {
            zza(sb, i2, "param_name", zzi().d(zzcVar.zze()));
        }
        if (zzcVar.zzj()) {
            int i3 = i2 + 1;
            zzev.zzf zzfVarZzd = zzcVar.zzd();
            if (zzfVarZzd != null) {
                zza(sb, i3);
                sb.append("string_filter");
                sb.append(" {\n");
                if (zzfVarZzd.zzj()) {
                    zza(sb, i3, "match_type", zzfVarZzd.zzb().name());
                }
                if (zzfVarZzd.zzi()) {
                    zza(sb, i3, "expression", zzfVarZzd.zze());
                }
                if (zzfVarZzd.zzh()) {
                    zza(sb, i3, "case_sensitive", Boolean.valueOf(zzfVarZzd.zzg()));
                }
                if (zzfVarZzd.zza() > 0) {
                    zza(sb, i2 + 2);
                    sb.append("expression_list {\n");
                    for (String str : zzfVarZzd.zzf()) {
                        zza(sb, i2 + 3);
                        sb.append(str);
                        sb.append("\n");
                    }
                    sb.append("}\n");
                }
                zza(sb, i3);
                sb.append("}\n");
            }
        }
        if (zzcVar.zzh()) {
            zza(sb, i2 + 1, "number_filter", zzcVar.zzc());
        }
        zza(sb, i2);
        sb.append("}\n");
    }

    private static void zza(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append("  ");
        }
    }

    private static void zza(StringBuilder sb, int i2, String str, zzev.zzd zzdVar) {
        if (zzdVar == null) {
            return;
        }
        zza(sb, i2);
        sb.append(str);
        sb.append(" {\n");
        if (zzdVar.zzh()) {
            zza(sb, i2, "comparison_type", zzdVar.zza().name());
        }
        if (zzdVar.zzj()) {
            zza(sb, i2, "match_as_float", Boolean.valueOf(zzdVar.zzg()));
        }
        if (zzdVar.zzi()) {
            zza(sb, i2, "comparison_value", zzdVar.zzd());
        }
        if (zzdVar.zzl()) {
            zza(sb, i2, "min_comparison_value", zzdVar.zzf());
        }
        if (zzdVar.zzk()) {
            zza(sb, i2, "max_comparison_value", zzdVar.zze());
        }
        zza(sb, i2);
        sb.append("}\n");
    }

    private static void zza(Uri.Builder builder, String str, String str2, Set<String> set) {
        if (set.contains(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        builder.appendQueryParameter(str, str2);
    }

    private static void zza(StringBuilder sb, int i2, String str, Object obj) {
        if (obj == null) {
            return;
        }
        zza(sb, i2 + 1);
        sb.append(str);
        sb.append(": ");
        sb.append(obj);
        sb.append('\n');
    }
}
