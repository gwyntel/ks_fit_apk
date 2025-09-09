package com.google.android.gms.fitness.result;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.List;

/* loaded from: classes3.dex */
public class DataReadResponse extends Response<DataReadResult> {
    @NonNull
    public List<Bucket> getBuckets() {
        return ((DataReadResult) e()).getBuckets();
    }

    @NonNull
    public DataSet getDataSet(@NonNull DataSource dataSource) {
        return ((DataReadResult) e()).getDataSet(dataSource);
    }

    @NonNull
    public List<DataSet> getDataSets() {
        return ((DataReadResult) e()).getDataSets();
    }

    @NonNull
    public Status getStatus() {
        return ((DataReadResult) e()).getStatus();
    }

    @NonNull
    public DataSet getDataSet(@NonNull DataType dataType) {
        return ((DataReadResult) e()).getDataSet(dataType);
    }
}
