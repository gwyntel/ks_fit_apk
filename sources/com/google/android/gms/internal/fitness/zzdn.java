package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
final class zzdn extends zzae {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataReadRequest f13077d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdn(zzds zzdsVar, GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
        super(googleApiClient);
        this.f13077d = dataReadRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzca) ((zzaj) anyClient).getService()).zzg(new DataReadRequest(this.f13077d, new zzdr(this, null)));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        List<DataType> dataTypes = this.f13077d.getDataTypes();
        List<DataSource> dataSources = this.f13077d.getDataSources();
        ArrayList arrayList = new ArrayList();
        Iterator<DataSource> it = dataSources.iterator();
        while (it.hasNext()) {
            arrayList.add(DataSet.builder(it.next()).build());
        }
        for (DataType dataType : dataTypes) {
            DataSource.Builder builder = new DataSource.Builder();
            builder.setType(1);
            builder.setDataType(dataType);
            builder.setStreamName("Default");
            arrayList.add(DataSet.builder(builder.build()).build());
        }
        return new DataReadResult(arrayList, Collections.emptyList(), status);
    }
}
