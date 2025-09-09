package com.taobao.accs;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.SessionCenter;
import anet.channel.entity.ENV;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.AccsConnectStateListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.v;
import com.umeng.analytics.pro.ay;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class ACCSClient {
    private static String TAG = "ACCSClient";
    public static Map<String, ACCSClient> mACCSClients = new ConcurrentHashMap(2);
    private static Context mContext;
    private String OTAG = TAG;
    protected b mAccsManager;
    private AccsClientConfig mConfig;

    public ACCSClient(AccsClientConfig accsClientConfig) {
        this.mConfig = accsClientConfig;
        this.OTAG += accsClientConfig.getTag();
        if (mContext == null) {
            mContext = GlobalClientInfo.f20065a;
        }
        this.mAccsManager = ACCSManager.getAccsInstance(mContext, accsClientConfig.getAppKey(), accsClientConfig.getTag());
    }

    public static ACCSClient getAccsClient() throws AccsException {
        return getAccsClient(null);
    }

    public static synchronized String init(Context context, String str) throws AccsException {
        AccsClientConfig configByTag;
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    configByTag = AccsClientConfig.getConfigByTag(str);
                    if (!AccsClientConfig.loadedStaticConfig) {
                        configByTag = new AccsClientConfig.Builder().setAppKey(str).build();
                        ALog.i(TAG, "init", "create config, appkey as tag");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        throw new AccsException("params error");
        return init(context, configByTag);
    }

    public static synchronized void setEnvironment(Context context, @AccsClientConfig.ENV int i2) {
        synchronized (ACCSClient.class) {
            if (i2 < 0 || i2 > 2) {
                try {
                    ALog.e(TAG, "env error", ay.f21366a, Integer.valueOf(i2));
                    i2 = 0;
                } finally {
                    try {
                    } finally {
                    }
                }
            }
            int i3 = AccsClientConfig.mEnv;
            AccsClientConfig.mEnv = i2;
            if (i3 != i2 && v.e(context)) {
                ALog.i(TAG, "setEnvironment", "preEnv", Integer.valueOf(i3), "toEnv", Integer.valueOf(i2));
                v.c(context);
                v.f(context);
                v.d(context);
                if (i2 == 2) {
                    SessionCenter.switchEnvironment(ENV.TEST);
                } else if (i2 == 1) {
                    SessionCenter.switchEnvironment(ENV.PREPARE);
                }
                Iterator<Map.Entry<String, ACCSClient>> it = mACCSClients.entrySet().iterator();
                while (it.hasNext()) {
                    try {
                        getAccsClient(it.next().getKey());
                    } catch (AccsException e2) {
                        ALog.e(TAG, "setEnvironment update client", e2, new Object[0]);
                    }
                }
            }
        }
    }

    private void updateConfig(AccsClientConfig accsClientConfig) {
        this.mConfig = accsClientConfig;
        b accsInstance = ACCSManager.getAccsInstance(mContext, accsClientConfig.getAppKey(), accsClientConfig.getTag());
        this.mAccsManager = accsInstance;
        if (accsInstance != null) {
            accsInstance.a(accsClientConfig);
        }
    }

    public void bindApp(String str, IAppReceiver iAppReceiver) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "bindApp mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext, this.mConfig.getAppKey(), this.mConfig.getAppSecret(), str, iAppReceiver);
        }
    }

    public void bindService(String str) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "bindService mAccsManager null", new Object[0]);
        } else {
            bVar.b(mContext, str);
        }
    }

    public void bindUser(String str) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "bindUser mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext, str);
        }
    }

    public boolean cancel(String str) {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.a(str);
        }
        ALog.e(this.OTAG, "cancel mAccsManager null", new Object[0]);
        return false;
    }

    public void clearLoginInfo() {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "clearLoginInfo mAccsManager null", new Object[0]);
        } else {
            bVar.e(mContext);
        }
    }

    public void forceDisableService() {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "forceDisableService mAccsManager null", new Object[0]);
        } else {
            bVar.c(mContext);
        }
    }

    public void forceEnableService() {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "forceEnableService mAccsManager null", new Object[0]);
        } else {
            bVar.d(mContext);
        }
    }

    public Map<String, Boolean> forceReConnectChannel() throws Exception {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.c();
        }
        ALog.e(this.OTAG, "forceReConnectChannel mAccsManager null", new Object[0]);
        return null;
    }

    public Map<String, Boolean> getChannelState() throws Exception {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.b();
        }
        ALog.e(this.OTAG, "getChannelState mAccsManager null", new Object[0]);
        return null;
    }

    @Deprecated
    public String getUserUnit() {
        return null;
    }

    public boolean isAccsConnected() {
        b bVar = this.mAccsManager;
        return bVar != null && bVar.a();
    }

    public boolean isChannelError(int i2) {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.a(i2);
        }
        ALog.e(this.OTAG, "isChannelError mAccsManager null", new Object[0]);
        return true;
    }

    public boolean isNetworkReachable() {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.b(mContext);
        }
        ALog.e(this.OTAG, "isNetworkReachable mAccsManager null", new Object[0]);
        return false;
    }

    public void registerConnectStateListener(AccsConnectStateListener accsConnectStateListener) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "registerConnectStateListener mAccsManager null", new Object[0]);
        } else {
            bVar.a(accsConnectStateListener);
        }
    }

    public void registerDataListener(String str, AccsAbstractDataListener accsAbstractDataListener) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "registerDataListener mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext, str, accsAbstractDataListener);
        }
    }

    public void registerSerivce(String str, String str2) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "registerSerivce mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext, str, str2);
        }
    }

    public void sendBusinessAck(String str, String str2, String str3, short s2, String str4, Map<Integer, String> map) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "sendBusinessAck mAccsManager null", new Object[0]);
        } else {
            bVar.a(str, str2, str3, s2, str4, map);
        }
    }

    public String sendData(ACCSManager.AccsRequest accsRequest) {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.a(mContext, accsRequest);
        }
        ALog.e(this.OTAG, "sendData mAccsManager null", new Object[0]);
        return null;
    }

    public String sendPushResponse(ACCSManager.AccsRequest accsRequest, TaoBaseService.ExtraInfo extraInfo) {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.a(mContext, accsRequest, extraInfo);
        }
        ALog.e(this.OTAG, "sendPushResponse mAccsManager null", new Object[0]);
        return null;
    }

    public String sendRequest(ACCSManager.AccsRequest accsRequest) {
        b bVar = this.mAccsManager;
        if (bVar != null) {
            return bVar.b(mContext, accsRequest);
        }
        ALog.e(this.OTAG, "sendRequest mAccsManager null", new Object[0]);
        return null;
    }

    public void setLoginInfo(ILoginInfo iLoginInfo) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "setLoginInfo mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext, iLoginInfo);
        }
    }

    public void startInAppConnection(String str, IAppReceiver iAppReceiver) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "startInAppConnection mAccsManager null", new Object[0]);
            return;
        }
        Context context = mContext;
        String appKey = this.mConfig.getAppKey();
        this.mConfig.getAppSecret();
        bVar.a(context, appKey, str, iAppReceiver);
    }

    public void unRegisterConnectStateListener(AccsConnectStateListener accsConnectStateListener) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "unRegisterConnectStateListener mAccsManager null", new Object[0]);
        } else {
            bVar.b(accsConnectStateListener);
        }
    }

    public void unRegisterDataListener(String str) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "unRegisterDataListener mAccsManager null", new Object[0]);
        } else {
            bVar.e(mContext, str);
        }
    }

    public void unRegisterSerivce(String str) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "unRegisterSerivce mAccsManager null", new Object[0]);
        } else {
            bVar.d(mContext, str);
        }
    }

    public void unbindService(String str) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "unbindService mAccsManager null", new Object[0]);
        } else {
            bVar.c(mContext, str);
        }
    }

    public void unbindUser() {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "unbindUser mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext);
        }
    }

    public static synchronized ACCSClient getAccsClient(String str) throws AccsException {
        try {
            if (TextUtils.isEmpty(str)) {
                str = AccsClientConfig.DEFAULT_CONFIGTAG;
                ALog.w(TAG, "getAccsClient", "configTag is null, use default!");
            }
            ALog.i(TAG, "getAccsClient", Constants.KEY_CONFIG_TAG, str);
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag == null) {
                ALog.e(TAG, "getAccsClient", "configTag not exist, please init first!!");
                throw new AccsException("configTag not exist");
            }
            ACCSClient aCCSClient = mACCSClients.get(str);
            if (aCCSClient == null) {
                ALog.d(TAG, "getAccsClient create client", new Object[0]);
                ACCSClient aCCSClient2 = new ACCSClient(configByTag);
                mACCSClients.put(str, aCCSClient2);
                aCCSClient2.updateConfig(configByTag);
                return aCCSClient2;
            }
            if (configByTag.equals(aCCSClient.mConfig)) {
                ALog.i(TAG, "getAccsClient exists", new Object[0]);
            } else {
                ALog.i(TAG, "getAccsClient update config", "old config", aCCSClient.mConfig.getTag(), "new config", configByTag.getTag());
                aCCSClient.updateConfig(configByTag);
            }
            return aCCSClient;
        } catch (Throwable th) {
            throw th;
        }
    }

    public void bindUser(String str, boolean z2) {
        b bVar = this.mAccsManager;
        if (bVar == null) {
            ALog.e(this.OTAG, "bindUser mAccsManager null", new Object[0]);
        } else {
            bVar.a(mContext, str, z2);
        }
    }

    public static synchronized String init(Context context, AccsClientConfig accsClientConfig) throws AccsException {
        if (context != null && accsClientConfig != null) {
            mContext = context.getApplicationContext();
            GlobalClientInfo.f20065a = context.getApplicationContext();
            ALog.d(TAG, "init", "config", accsClientConfig);
        } else {
            throw new AccsException("init AccsClient params error");
        }
        return accsClientConfig.getTag();
    }
}
