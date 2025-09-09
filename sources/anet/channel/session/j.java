package anet.channel.session;

import anet.channel.security.ISecurity;
import anet.channel.util.ALog;
import org.android.spdy.AccsSSLCallback;
import org.android.spdy.SpdyProtocol;

/* loaded from: classes2.dex */
class j implements AccsSSLCallback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TnetSpdySession f6922a;

    j(TnetSpdySession tnetSpdySession) {
        this.f6922a = tnetSpdySession;
    }

    @Override // org.android.spdy.AccsSSLCallback
    public byte[] getSSLPublicKey(int i2, byte[] bArr) {
        byte[] bArrDecrypt;
        try {
            TnetSpdySession tnetSpdySession = this.f6922a;
            bArrDecrypt = tnetSpdySession.G.decrypt(tnetSpdySession.f6617a, ISecurity.CIPHER_ALGORITHM_AES128, SpdyProtocol.TNET_PUBKEY_SG_KEY, bArr);
            if (bArrDecrypt != null) {
                try {
                    if (ALog.isPrintLog(2)) {
                        ALog.i("getSSLPublicKey", null, "decrypt", new String(bArrDecrypt));
                    }
                } catch (Throwable th) {
                    th = th;
                    ALog.e("awcn.TnetSpdySession", "getSSLPublicKey", null, th, new Object[0]);
                    return bArrDecrypt;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            bArrDecrypt = null;
        }
        return bArrDecrypt;
    }
}
