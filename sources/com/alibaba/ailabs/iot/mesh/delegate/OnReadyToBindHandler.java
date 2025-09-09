package com.alibaba.ailabs.iot.mesh.delegate;

import datasource.MeshConfigCallback;

/* loaded from: classes2.dex */
public interface OnReadyToBindHandler {
    void onReadyToBind(String str, MeshConfigCallback<Boolean> meshConfigCallback);
}
