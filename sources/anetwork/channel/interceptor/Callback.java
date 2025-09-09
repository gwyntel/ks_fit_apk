package anetwork.channel.interceptor;

import anet.channel.bytes.ByteArray;
import anetwork.channel.aidl.DefaultFinishEvent;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface Callback {
    void onDataReceiveSize(int i2, int i3, ByteArray byteArray);

    void onFinish(DefaultFinishEvent defaultFinishEvent);

    void onResponseCode(int i2, Map<String, List<String>> map);
}
