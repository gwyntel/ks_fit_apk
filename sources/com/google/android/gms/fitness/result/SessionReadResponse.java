package com.google.android.gms.fitness.result;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import java.util.List;

/* loaded from: classes3.dex */
public class SessionReadResponse extends Response<SessionReadResult> {
    @ShowFirstParty
    public SessionReadResponse() {
    }

    @NonNull
    public List<DataSet> getDataSet(@NonNull Session session) {
        return ((SessionReadResult) e()).getDataSet(session);
    }

    @NonNull
    public List<Session> getSessions() {
        return ((SessionReadResult) e()).getSessions();
    }

    @NonNull
    public Status getStatus() {
        return ((SessionReadResult) e()).getStatus();
    }

    @NonNull
    public List<DataSet> getDataSet(@NonNull Session session, @NonNull DataType dataType) {
        return ((SessionReadResult) e()).getDataSet(session, dataType);
    }
}
