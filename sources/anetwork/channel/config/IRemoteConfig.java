package anetwork.channel.config;

/* loaded from: classes2.dex */
public interface IRemoteConfig {
    String getConfig(String... strArr);

    void onConfigUpdate(String str);

    void register();

    void unRegister();
}
