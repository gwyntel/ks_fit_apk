package com.google.android.gms.fitness;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.internal.fitness.zzab;
import com.google.android.gms.internal.fitness.zzaj;
import com.google.android.gms.internal.fitness.zzar;
import com.google.android.gms.internal.fitness.zzaz;
import com.google.android.gms.internal.fitness.zzbh;
import com.google.android.gms.internal.fitness.zzcy;
import com.google.android.gms.internal.fitness.zzde;
import com.google.android.gms.internal.fitness.zzdh;
import com.google.android.gms.internal.fitness.zzds;
import com.google.android.gms.internal.fitness.zzea;
import com.google.android.gms.internal.fitness.zzee;
import com.google.android.gms.internal.fitness.zzep;
import com.google.android.gms.internal.fitness.zzu;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Fitness {

    @NonNull
    public static final String ACTION_TRACK = "vnd.google.fitness.TRACK";

    @NonNull
    public static final String ACTION_VIEW = "vnd.google.fitness.VIEW";

    @NonNull
    public static final String ACTION_VIEW_GOAL = "vnd.google.fitness.VIEW_GOAL";

    @NonNull
    @Deprecated
    public static final Void API = null;

    @NonNull
    public static final String EXTRA_END_TIME = "vnd.google.fitness.end_time";

    @NonNull
    public static final String EXTRA_START_TIME = "vnd.google.fitness.start_time";

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> SENSORS_API = zzaz.zzf;

    @NonNull
    @Deprecated
    public static final SensorsApi SensorsApi = new zzee();

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> RECORDING_API = zzar.zzf;

    @NonNull
    @Deprecated
    public static final RecordingApi RecordingApi = new zzea();

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> SESSIONS_API = zzbh.zzf;

    @NonNull
    @Deprecated
    public static final SessionsApi SessionsApi = new zzep();

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> HISTORY_API = zzaj.zzf;

    @NonNull
    @Deprecated
    public static final HistoryApi HistoryApi = new zzds();

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> GOALS_API = zzab.zzf;

    @NonNull
    @Deprecated
    public static final GoalsApi GoalsApi = new zzdh();

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> CONFIG_API = zzu.zzf;

    @NonNull
    @Deprecated
    public static final ConfigApi ConfigApi = new zzde();

    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> BLE_API = com.google.android.gms.internal.fitness.zzm.zzf;

    @NonNull
    @Deprecated
    public static final BleApi BleApi = new zzcy();

    @NonNull
    public static final Scope SCOPE_ACTIVITY_READ = new Scope("https://www.googleapis.com/auth/fitness.activity.read");

    @NonNull
    public static final Scope SCOPE_ACTIVITY_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.activity.write");

    @NonNull
    public static final Scope SCOPE_LOCATION_READ = new Scope("https://www.googleapis.com/auth/fitness.location.read");

    @NonNull
    public static final Scope SCOPE_LOCATION_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.location.write");

    @NonNull
    public static final Scope SCOPE_BODY_READ = new Scope("https://www.googleapis.com/auth/fitness.body.read");

    @NonNull
    public static final Scope SCOPE_BODY_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.body.write");

    @NonNull
    public static final Scope SCOPE_NUTRITION_READ = new Scope("https://www.googleapis.com/auth/fitness.nutrition.read");

    @NonNull
    public static final Scope SCOPE_NUTRITION_READ_WRITE = new Scope("https://www.googleapis.com/auth/fitness.nutrition.write");

    @NonNull
    @ShowFirstParty
    public static final Scope zza = new Scope("https://www.googleapis.com/auth/fitness.heart_rate.read");

    @NonNull
    @ShowFirstParty
    public static final Scope zzb = new Scope("https://www.googleapis.com/auth/fitness.heart_rate.write");

    @NonNull
    @ShowFirstParty
    public static final Scope zzc = new Scope("https://www.googleapis.com/auth/fitness.respiratory_rate.read");

    @NonNull
    @ShowFirstParty
    public static final Scope zzd = new Scope("https://www.googleapis.com/auth/fitness.respiratory_rate.write");

    @NonNull
    @ShowFirstParty
    public static final Scope zze = new Scope("https://www.googleapis.com/auth/fitness.sleep.read");

    @NonNull
    @ShowFirstParty
    public static final Scope zzf = new Scope("https://www.googleapis.com/auth/fitness.sleep.write");

    @NonNull
    @ShowFirstParty
    public static final GoogleSignInAccount zzg = GoogleSignInAccount.fromAccount(new Account("none", "com.google"));

    private Fitness() {
    }

    @NonNull
    @Deprecated
    public static BleClient getBleClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new BleClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static ConfigClient getConfigClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new ConfigClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    public static long getEndTime(@NonNull Intent intent, @NonNull TimeUnit timeUnit) {
        long longExtra = intent.getLongExtra(EXTRA_END_TIME, -1L);
        if (longExtra == -1) {
            return -1L;
        }
        return timeUnit.convert(longExtra, TimeUnit.MILLISECONDS);
    }

    @NonNull
    @Deprecated
    public static GoalsClient getGoalsClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new GoalsClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static HistoryClient getHistoryClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new HistoryClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    @NonNull
    public static RecordingClient getRecordingClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new RecordingClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static SensorsClient getSensorsClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new SensorsClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static SessionsClient getSessionsClient(@NonNull Activity activity, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new SessionsClient(activity, (Api.ApiOptions.HasGoogleSignInAccountOptions) new zzi(activity, googleSignInAccount));
    }

    public static long getStartTime(@NonNull Intent intent, @NonNull TimeUnit timeUnit) {
        long longExtra = intent.getLongExtra(EXTRA_START_TIME, -1L);
        if (longExtra == -1) {
            return -1L;
        }
        return timeUnit.convert(longExtra, TimeUnit.MILLISECONDS);
    }

    @NonNull
    @Deprecated
    public static BleClient getBleClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new BleClient(context, new zzi(context, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static ConfigClient getConfigClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new ConfigClient(context, new zzi(context, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static GoalsClient getGoalsClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new GoalsClient(context, new zzi(context, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static HistoryClient getHistoryClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new HistoryClient(context, new zzi(context, googleSignInAccount));
    }

    @NonNull
    public static RecordingClient getRecordingClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new RecordingClient(context, new zzi(context, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static SensorsClient getSensorsClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new SensorsClient(context, new zzi(context, googleSignInAccount));
    }

    @NonNull
    @Deprecated
    public static SessionsClient getSessionsClient(@NonNull Context context, @NonNull GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount);
        return new SessionsClient(context, new zzi(context, googleSignInAccount));
    }
}
