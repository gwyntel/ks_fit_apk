package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;

/* loaded from: classes3.dex */
final class zaq implements PendingResultUtil.ResultConverter {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Response f12844a;

    zaq(Response response) {
        this.f12844a = response;
    }

    @Override // com.google.android.gms.common.internal.PendingResultUtil.ResultConverter
    public final /* bridge */ /* synthetic */ Object convert(Result result) {
        this.f12844a.setResult(result);
        return this.f12844a;
    }
}
