package com.aliyun.alink.linksdk.channel.core.persistent.a;

import android.content.Context;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.ASend;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentInitParams;
import com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.PersisitentNetParams;
import com.aliyun.alink.linksdk.tools.ALog;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes2.dex */
public class a implements IPersisitentNet {

    /* renamed from: com.aliyun.alink.linksdk.channel.core.persistent.a.a$a, reason: collision with other inner class name */
    private static class C0070a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f10863a = new a();
    }

    public static a a() {
        return C0070a.f10863a;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.INet
    public ASend asyncSend(ARequest aRequest, IOnCallListener iOnCallListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "asyncSend unsupported with accs channel.");
        return null;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void destroy() {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "destroy() called");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void dynamicRegister(Context context, PersistentInitParams persistentInitParams, IOnCallListener iOnCallListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "dynamicRegister unsupported with accs channel.");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004a  */
    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState getConnectState() {
        /*
            r4 = this;
            java.lang.String r0 = "AccsNet"
            java.lang.String r1 = "ConnectSDK"
            java.lang.String r2 = "getConnectState()"
            com.aliyun.alink.linksdk.channel.core.b.a.a(r1, r2)
            java.lang.String r1 = "default"
            com.taobao.accs.ACCSClient r1 = com.taobao.accs.ACCSClient.getAccsClient(r1)     // Catch: java.lang.Exception -> L14 com.taobao.accs.AccsException -> L16
            boolean r0 = r1.isAccsConnected()     // Catch: java.lang.Exception -> L14 com.taobao.accs.AccsException -> L16
            goto L45
        L14:
            r1 = move-exception
            goto L18
        L16:
            r1 = move-exception
            goto L2d
        L18:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getConnectState getAccsClient e="
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.aliyun.alink.linksdk.channel.core.b.a.c(r0, r1)
            goto L44
        L2d:
            r1.printStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getConnectState getAccsClient AccsException="
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.aliyun.alink.linksdk.channel.core.b.a.c(r0, r1)
        L44:
            r0 = 0
        L45:
            if (r0 == 0) goto L4a
            com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState r0 = com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState.CONNECTED
            goto L4c
        L4a:
            com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState r0 = com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState.DISCONNECTED
        L4c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.channel.core.persistent.a.a.getConnectState():com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void init(Context context, PersistentInitParams persistentInitParams) {
        if (getConnectState() == PersistentConnectState.CONNECTED) {
            PersistentEventDispatcher.getInstance().broadcastMessage(1, null, null, 200, "is initing or inited.");
        } else {
            PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4300, "init tg_push sdk first or wait tg_push init done.");
        }
        EventBus.getDefault().register(this);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public boolean isDeiniting() {
        return false;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void openLog(boolean z2) {
        if (z2) {
            ALog.setLevel((byte) 1);
        } else {
            ALog.setLevel((byte) 4);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void reconnect() {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "reconnect unsupported with accs channel.");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.INet
    public void retry(ASend aSend) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "retry unsupported with accs channel.");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribe(String str, IOnSubscribeListener iOnSubscribeListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "subscribe unsupported with accs channel.");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribeRrpc(String str, IOnSubscribeRrpcListener iOnSubscribeRrpcListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "unSbscribeRrpc unsupported with accs channel.");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void unSubscribe(String str, IOnSubscribeListener iOnSubscribeListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "unSubscribe unsupported with accs channel.");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void destroy(long j2, Object obj, Object obj2) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "destroy() called with: quiesceTimeout = [" + j2 + "], userContext = [" + obj + "], callback = [" + obj2 + "]");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribe(String str, PersisitentNetParams persisitentNetParams, IOnSubscribeListener iOnSubscribeListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("AccsNet", "subscribe with params unsupported with accs channel.");
    }
}
