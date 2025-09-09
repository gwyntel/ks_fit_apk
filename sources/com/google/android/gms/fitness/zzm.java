package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

/* loaded from: classes3.dex */
public final /* synthetic */ class zzm implements PendingResultUtil.ResultConverter {
    public static final /* synthetic */ zzm zza = new zzm();

    private /* synthetic */ zzm() {
    }

    @Override // com.google.android.gms.common.internal.PendingResultUtil.ResultConverter
    public final Object convert(Result result) {
        return ((ListSubscriptionsResult) result).getSubscriptions();
    }
}
