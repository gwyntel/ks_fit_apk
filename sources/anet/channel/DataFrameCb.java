package anet.channel;

import anet.channel.session.TnetSpdySession;

/* loaded from: classes2.dex */
public interface DataFrameCb {
    void onDataReceive(TnetSpdySession tnetSpdySession, byte[] bArr, int i2, int i3);

    void onException(int i2, int i3, boolean z2, String str);
}
