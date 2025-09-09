package com.cicada.player.utils.media;

/* loaded from: classes3.dex */
public interface DrmCallback {
    byte[] requestKey(String str, byte[] bArr);

    byte[] requestProvision(String str, byte[] bArr);
}
