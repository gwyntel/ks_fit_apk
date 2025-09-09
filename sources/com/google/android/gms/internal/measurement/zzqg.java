package com.google.android.gms.internal.measurement;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.media3.common.C;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import com.heytap.mcssdk.constant.Constants;

/* loaded from: classes3.dex */
public final class zzqg implements zzqd {
    private static final zzgl<Long> zza;
    private static final zzgl<String> zzaa;
    private static final zzgl<String> zzab;
    private static final zzgl<String> zzac;
    private static final zzgl<String> zzad;
    private static final zzgl<Long> zzae;
    private static final zzgl<Long> zzaf;
    private static final zzgl<Long> zzag;
    private static final zzgl<Long> zzah;
    private static final zzgl<Long> zzai;
    private static final zzgl<Long> zzaj;
    private static final zzgl<Long> zzak;
    private static final zzgl<Long> zzal;
    private static final zzgl<Long> zzam;
    private static final zzgl<Long> zzan;
    private static final zzgl<Long> zzao;
    private static final zzgl<Long> zzap;
    private static final zzgl<Long> zzaq;
    private static final zzgl<Long> zzar;
    private static final zzgl<Long> zzas;
    private static final zzgl<Long> zzat;
    private static final zzgl<Long> zzau;
    private static final zzgl<String> zzav;
    private static final zzgl<Long> zzaw;
    private static final zzgl<String> zzax;
    private static final zzgl<Long> zzb;
    private static final zzgl<Long> zzc;
    private static final zzgl<Long> zzd;
    private static final zzgl<String> zze;
    private static final zzgl<String> zzf;
    private static final zzgl<String> zzg;
    private static final zzgl<Long> zzh;
    private static final zzgl<String> zzi;
    private static final zzgl<Long> zzj;
    private static final zzgl<Long> zzk;
    private static final zzgl<Long> zzl;
    private static final zzgl<Long> zzm;
    private static final zzgl<Long> zzn;
    private static final zzgl<Long> zzo;
    private static final zzgl<Long> zzp;
    private static final zzgl<Long> zzq;
    private static final zzgl<Long> zzr;
    private static final zzgl<Long> zzs;
    private static final zzgl<String> zzt;
    private static final zzgl<Long> zzu;
    private static final zzgl<Long> zzv;
    private static final zzgl<Long> zzw;
    private static final zzgl<Long> zzx;
    private static final zzgl<String> zzy;
    private static final zzgl<Long> zzz;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.ad_id_cache_time", 10000L);
        zzb = zzgtVarZza.zza("measurement.app_uninstalled_additional_ad_id_cache_time", 3600000L);
        zzc = zzgtVarZza.zza("measurement.max_bundles_per_iteration", 100L);
        zzd = zzgtVarZza.zza("measurement.config.cache_time", 86400000L);
        zze = zzgtVarZza.zza("measurement.log_tag", "FA");
        zzf = zzgtVarZza.zza("measurement.config.url_authority", "app-measurement.com");
        zzg = zzgtVarZza.zza("measurement.config.url_scheme", "https");
        zzh = zzgtVarZza.zza("measurement.upload.debug_upload_interval", 1000L);
        zzi = zzgtVarZza.zza("measurement.rb.attribution.event_params", "value|currency");
        zzj = zzgtVarZza.zza("measurement.lifetimevalue.max_currency_tracked", 4L);
        zzk = zzgtVarZza.zza("measurement.upload.max_event_parameter_value_length", 100L);
        zzl = zzgtVarZza.zza("measurement.store.max_stored_events_per_app", SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US);
        zzm = zzgtVarZza.zza("measurement.experiment.max_ids", 50L);
        zzn = zzgtVarZza.zza("measurement.audience.filter_result_max_count", 200L);
        zzo = zzgtVarZza.zza("measurement.upload.max_item_scoped_custom_parameters", 27L);
        zzp = zzgtVarZza.zza("measurement.rb.attribution.client.min_ad_services_version", 7L);
        zzq = zzgtVarZza.zza("measurement.alarm_manager.minimum_interval", 60000L);
        zzr = zzgtVarZza.zza("measurement.upload.minimum_delay", 500L);
        zzs = zzgtVarZza.zza("measurement.monitoring.sample_period_millis", 86400000L);
        zzt = zzgtVarZza.zza("measurement.rb.attribution.app_allowlist", "");
        zzu = zzgtVarZza.zza("measurement.upload.realtime_upload_interval", 10000L);
        zzv = zzgtVarZza.zza("measurement.upload.refresh_blacklisted_config_interval", 604800000L);
        zzw = zzgtVarZza.zza("measurement.config.cache_time.service", 3600000L);
        zzx = zzgtVarZza.zza("measurement.service_client.idle_disconnect_millis", 5000L);
        zzy = zzgtVarZza.zza("measurement.log_tag.service", "FA-SVC");
        zzz = zzgtVarZza.zza("measurement.upload.stale_data_deletion_interval", 86400000L);
        zzaa = zzgtVarZza.zza("measurement.rb.attribution.uri_authority", "google-analytics.com");
        zzab = zzgtVarZza.zza("measurement.rb.attribution.uri_path", "privacy-sandbox/register-app-conversion");
        zzac = zzgtVarZza.zza("measurement.rb.attribution.query_parameters_to_remove", "");
        zzad = zzgtVarZza.zza("measurement.rb.attribution.uri_scheme", "https");
        zzae = zzgtVarZza.zza("measurement.sdk.attribution.cache.ttl", 604800000L);
        zzaf = zzgtVarZza.zza("measurement.redaction.app_instance_id.ttl", Constants.MILLS_OF_WATCH_DOG);
        zzag = zzgtVarZza.zza("measurement.upload.backoff_period", Constants.MILLS_OF_LAUNCH_INTERVAL);
        zzah = zzgtVarZza.zza("measurement.upload.initial_upload_delay_time", C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
        zzai = zzgtVarZza.zza("measurement.upload.interval", 3600000L);
        zzaj = zzgtVarZza.zza("measurement.upload.max_bundle_size", PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        zzak = zzgtVarZza.zza("measurement.upload.max_bundles", 100L);
        zzal = zzgtVarZza.zza("measurement.upload.max_conversions_per_day", 500L);
        zzam = zzgtVarZza.zza("measurement.upload.max_error_events_per_day", 1000L);
        zzan = zzgtVarZza.zza("measurement.upload.max_events_per_bundle", 1000L);
        zzao = zzgtVarZza.zza("measurement.upload.max_events_per_day", SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US);
        zzap = zzgtVarZza.zza("measurement.upload.max_public_events_per_day", 50000L);
        zzaq = zzgtVarZza.zza("measurement.upload.max_queue_time", 2419200000L);
        zzar = zzgtVarZza.zza("measurement.upload.max_realtime_events_per_day", 10L);
        zzas = zzgtVarZza.zza("measurement.upload.max_batch_size", PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        zzat = zzgtVarZza.zza("measurement.upload.retry_count", 6L);
        zzau = zzgtVarZza.zza("measurement.upload.retry_time", 1800000L);
        zzav = zzgtVarZza.zza("measurement.upload.url", "https://app-measurement.com/a");
        zzaw = zzgtVarZza.zza("measurement.upload.window_interval", 3600000L);
        zzax = zzgtVarZza.zza("measurement.rb.attribution.user_properties", "_npa,npa");
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zza() {
        return zza.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzaa() {
        return zzal.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzab() {
        return zzam.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzac() {
        return zzan.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzad() {
        return zzao.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzae() {
        return zzap.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzaf() {
        return zzaq.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzag() {
        return zzar.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzah() {
        return zzas.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzai() {
        return zzat.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzaj() {
        return zzau.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzak() {
        return zzaw.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzal() {
        return zzf.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzam() {
        return zzg.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzan() {
        return zzi.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzao() {
        return zzt.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzap() {
        return zzaa.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzaq() {
        return zzab.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzar() {
        return zzac.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzas() {
        return zzad.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzat() {
        return zzav.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzau() {
        return zzax.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzb() {
        return zzb.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzc() {
        return zzc.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzd() {
        return zzd.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zze() {
        return zzh.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzf() {
        return zzj.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzg() {
        return zzk.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzh() {
        return zzl.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzi() {
        return zzm.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzj() {
        return zzn.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzk() {
        return zzo.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzl() {
        return zzp.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzm() {
        return zzq.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzn() {
        return zzr.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzo() {
        return zzs.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzp() {
        return zzu.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzq() {
        return zzv.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzr() {
        return zzx.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzs() {
        return zzz.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzt() {
        return zzae.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzu() {
        return zzaf.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzv() {
        return zzag.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzw() {
        return zzah.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzx() {
        return zzai.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzy() {
        return zzaj.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzz() {
        return zzak.zza().longValue();
    }
}
