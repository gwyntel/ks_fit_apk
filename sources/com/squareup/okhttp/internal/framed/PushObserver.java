package com.squareup.okhttp.internal.framed;

import java.io.IOException;
import java.util.List;
import okio.BufferedSource;

/* loaded from: classes4.dex */
public interface PushObserver {
    public static final PushObserver CANCEL = new PushObserver() { // from class: com.squareup.okhttp.internal.framed.PushObserver.1
        @Override // com.squareup.okhttp.internal.framed.PushObserver
        public boolean onData(int i2, BufferedSource bufferedSource, int i3, boolean z2) throws IOException {
            bufferedSource.skip(i3);
            return true;
        }

        @Override // com.squareup.okhttp.internal.framed.PushObserver
        public boolean onHeaders(int i2, List<Header> list, boolean z2) {
            return true;
        }

        @Override // com.squareup.okhttp.internal.framed.PushObserver
        public boolean onRequest(int i2, List<Header> list) {
            return true;
        }

        @Override // com.squareup.okhttp.internal.framed.PushObserver
        public void onReset(int i2, ErrorCode errorCode) {
        }
    };

    boolean onData(int i2, BufferedSource bufferedSource, int i3, boolean z2) throws IOException;

    boolean onHeaders(int i2, List<Header> list, boolean z2);

    boolean onRequest(int i2, List<Header> list);

    void onReset(int i2, ErrorCode errorCode);
}
