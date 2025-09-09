package com.xiaomi.infra.galaxy.fds.android.model;

/* loaded from: classes4.dex */
public class ThumbParam extends UserParam {
    public ThumbParam(int i2, int i3) {
        this.params.put("thumb", "1");
        this.params.put("w", Integer.toString(i2));
        this.params.put("h", Integer.toString(i3));
    }
}
