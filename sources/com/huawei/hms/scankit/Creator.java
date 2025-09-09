package com.huawei.hms.scankit;

import android.os.RemoteException;
import com.huawei.hms.hmsscankit.api.IRemoteCreator;
import com.huawei.hms.hmsscankit.api.IRemoteDecoderDelegate;
import com.huawei.hms.hmsscankit.api.IRemoteHmsDecoderDelegate;
import com.huawei.hms.scankit.p.o4;

/* loaded from: classes4.dex */
public class Creator extends IRemoteCreator.Stub {
    private static final String TAG = "Creator";
    private e iRemoteViewDelegate = null;
    private f iRemoteDecoderDelegate = null;
    private g iRemoteHmsDecoderDelegate = null;

    @Override // com.huawei.hms.hmsscankit.api.IRemoteCreator
    public IRemoteDecoderDelegate newRemoteDecoderDelegate() throws RemoteException {
        o4.d(TAG, "newRemoteDecoderDelegate()");
        f fVarA = f.a();
        this.iRemoteDecoderDelegate = fVarA;
        return fVarA;
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteCreator
    public IRemoteHmsDecoderDelegate newRemoteHmsDecoderDelegate() throws RemoteException {
        o4.d(TAG, "newRemoteHmsDecoderDelegate()");
        g gVarA = g.a();
        this.iRemoteHmsDecoderDelegate = gVarA;
        return gVarA;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00be  */
    @Override // com.huawei.hms.hmsscankit.api.IRemoteCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.huawei.hms.hmsscankit.api.IRemoteViewDelegate newRemoteViewDelegate(com.huawei.hms.feature.dynamic.IObjectWrapper r14, com.huawei.hms.feature.dynamic.IObjectWrapper r15) throws java.lang.SecurityException, android.os.RemoteException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.Creator.newRemoteViewDelegate(com.huawei.hms.feature.dynamic.IObjectWrapper, com.huawei.hms.feature.dynamic.IObjectWrapper):com.huawei.hms.hmsscankit.api.IRemoteViewDelegate");
    }
}
