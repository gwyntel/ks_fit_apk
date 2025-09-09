package anet.channel;

/* loaded from: classes2.dex */
public interface IAuth {

    public interface AuthCallback {
        void onAuthFail(int i2, String str);

        void onAuthSuccess();
    }

    void auth(Session session, AuthCallback authCallback);
}
