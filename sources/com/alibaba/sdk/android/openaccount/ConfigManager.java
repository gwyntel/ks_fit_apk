package com.alibaba.sdk.android.openaccount;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.callback.OnActivityResultCallback;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.config.LanguageCode;
import com.alibaba.sdk.android.openaccount.config.OpenAccountProvider;
import com.alibaba.sdk.android.openaccount.config.PropertyChangeListener;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.hook.OAApiHook;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.trace.TraceLoggerManager;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.RequestCodeAllocator;
import com.alibaba.sdk.android.openaccount.util.TraceHelper;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alibaba.sdk.android.pluto.meta.ModuleInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ConfigManager implements ConfigService {
    private static final String CONFIG = "config";
    private static final String PROPERTY_NAME_FILTER_KEY = "_property_name";
    private static final String TAG = "oa_config";
    private OAApiHook apiHook;
    private String bundleName;
    private Class checkCodeFragment;
    private Class confirmFragment;
    private Context context;
    private boolean debugEnabled;

    @Autowired
    private ExecutorService executorService;
    private boolean initialized;
    private String mAlipayAppId;
    private String mAlipayPid;
    private String mAlipaySignType;
    private String mAppKey;
    private String mAppSecret;
    private String mFacebookId;
    private String mGoogleClientId;
    private boolean mIsShowPasswordStrengthHint;
    private OnActivityResultCallback mOnActivityResultCallback;
    private String mTwitterId;
    private String mTwitterSecret;
    private Class mobileFragment;
    private Class mobileRegisterFragment;
    private OpenAccountProvider openAccountProvider;
    private Map<String, String> properties;
    private Class pwdLoginFragment;
    private Class resetPwdFragment;
    private SharedPreferences sp;
    private String userConfigSecurityJpgPostfix;
    private static final ConfigManager INSTANCE = new ConfigManager();
    private static final String[] DEFAULT_SECURITY_GUARD_IMAGE_SUFFIX = {"test", "", "", "test"};
    private int[] appKeyIndexes = {0, 0, 0, 0};
    private Map<String, String> userProperties = new ConcurrentHashMap();
    private Map<String, String> extBizParam = new HashMap(50);
    private Environment env = Environment.ONLINE;
    private boolean useSingleImage = false;
    private String mApiGatewayHost = "sdk.openaccount.aliyun.com";
    private boolean openTaobaoUILogin = false;
    private boolean isOpenMtop = false;
    private boolean isAPIGateway = false;
    private boolean degradeHttps = false;
    private boolean logoutLoginSDKSwitch = false;
    private boolean registerLoginBroadcast = true;
    private Version sdkVersion = new Version(3, 4, 2);
    private boolean dailyNocaptcha = false;
    private boolean debugOKHttp = false;
    private int mMinPasswordLength = 6;
    private int mMaxPasswordLength = 20;
    private List<String> mHostWhiteList = new ArrayList(50);
    int retryTime = 3;
    int socketTimeoutMillis = 5000;
    int connectionTimeoutMills = 5000;
    private boolean supportOfflineLogin = false;

    private static class InternalPropertyChangeEvent {
        public String key;
        public String newValue;
        public String oldValue;

        private InternalPropertyChangeEvent() {
        }
    }

    private ConfigManager() {
    }

    private void addPropertyChangeEvent(List<InternalPropertyChangeEvent> list, String str, String str2, String str3) {
        InternalPropertyChangeEvent internalPropertyChangeEvent = new InternalPropertyChangeEvent();
        internalPropertyChangeEvent.key = str;
        internalPropertyChangeEvent.newValue = str3;
        internalPropertyChangeEvent.oldValue = str2;
        list.add(internalPropertyChangeEvent);
    }

    private Map<String, String> getDynamicConfigs() {
        HashMap map = new HashMap();
        try {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(OpenAccountConstants.DYNAMIC_CONFIG_SP, 0);
            this.sp = sharedPreferences;
            String string = sharedPreferences.getString("config", null);
            if (string != null) {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.optString(next));
                }
            }
        } catch (Throwable th) {
            AliSDKLogger.e(TAG, "fail to get dynamic configs", th);
        }
        return map;
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private void overrideProperties(Map<String, String> map, boolean z2) {
        ArrayList arrayList = z2 ? new ArrayList(3) : null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                String str = this.properties.get(entry.getKey());
                if (z2 && !CommonUtils.isEqual(str, entry.getValue())) {
                    addPropertyChangeEvent(arrayList, entry.getKey(), str, entry.getValue());
                }
                this.properties.put(entry.getKey(), entry.getValue());
            }
        }
        if (z2) {
            postPropertyChangeEvents(arrayList);
        }
    }

    private void postPropertyChangeEvent(final String str, final String str2, final String str3) {
        ExecutorService executorService;
        final PropertyChangeListener[] propertyChangeListenerArr = (PropertyChangeListener[]) Pluto.DEFAULT_INSTANCE.getBeans(PropertyChangeListener.class, Collections.singletonMap(PROPERTY_NAME_FILTER_KEY, str));
        if (propertyChangeListenerArr == null || propertyChangeListenerArr.length == 0 || (executorService = this.executorService) == null) {
            return;
        }
        executorService.postTask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ConfigManager.3
            @Override // java.lang.Runnable
            public void run() {
                for (PropertyChangeListener propertyChangeListener : propertyChangeListenerArr) {
                    try {
                        propertyChangeListener.propertyChanged(str, str2, str3);
                    } catch (Exception unused) {
                    }
                }
            }
        });
    }

    private void postPropertyChangeEvents(final List<InternalPropertyChangeEvent> list) {
        ExecutorService executorService;
        if (list.size() == 0 || (executorService = this.executorService) == null) {
            return;
        }
        executorService.postTask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ConfigManager.2
            @Override // java.lang.Runnable
            public void run() {
                String str;
                PropertyChangeListener[] propertyChangeListenerArr;
                for (InternalPropertyChangeEvent internalPropertyChangeEvent : list) {
                    if (internalPropertyChangeEvent != null && (str = internalPropertyChangeEvent.key) != null && (propertyChangeListenerArr = (PropertyChangeListener[]) Pluto.DEFAULT_INSTANCE.getBeans(PropertyChangeListener.class, Collections.singletonMap(ConfigManager.PROPERTY_NAME_FILTER_KEY, str))) != null && propertyChangeListenerArr.length != 0) {
                        for (PropertyChangeListener propertyChangeListener : propertyChangeListenerArr) {
                            try {
                                propertyChangeListener.propertyChanged(internalPropertyChangeEvent.key, internalPropertyChangeEvent.oldValue, internalPropertyChangeEvent.newValue);
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            }
        });
    }

    public void addHostWhiteList(String str) {
        if (this.mHostWhiteList == null) {
            this.mHostWhiteList = new ArrayList(50);
        }
        if (TextUtils.isEmpty(str) || this.mHostWhiteList.contains(str)) {
            return;
        }
        this.mHostWhiteList.add(str);
    }

    public String getAlipayAppId() {
        return this.mAlipayAppId;
    }

    public String getAlipayPid() {
        return this.mAlipayPid;
    }

    public String getAlipaySignType() {
        return this.mAlipaySignType;
    }

    public String getApiGatewayHost() {
        return this.mApiGatewayHost;
    }

    public OAApiHook getApiHook() {
        return this.apiHook;
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public int getAppKeyIndex() {
        return this.appKeyIndexes[this.env.ordinal()];
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public boolean getBooleanProperty(String str, boolean z2) {
        String stringProperty = getStringProperty(str, null);
        return stringProperty != null ? Boolean.parseBoolean(stringProperty) : z2;
    }

    public String getBundleName() {
        return this.bundleName;
    }

    public Class getCheckCodeFragment() {
        return this.checkCodeFragment;
    }

    public Class getConfirmFragment() {
        return this.confirmFragment;
    }

    public int getConnectionTimeoutMills() {
        return this.connectionTimeoutMills;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public OpenAccountProvider getDataProvider() {
        return this.openAccountProvider;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public Environment getEnvironment() {
        return this.env;
    }

    public Map<String, String> getExtBizMap() {
        return this.extBizParam;
    }

    public String getFacebookId() {
        return this.mFacebookId;
    }

    public String getGoogleClientId() {
        return this.mGoogleClientId;
    }

    public List<String> getHostWhiteList() {
        return this.mHostWhiteList;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public int getIntProperty(String str, int i2) {
        String stringProperty = getStringProperty(str, null);
        if (stringProperty != null) {
            try {
                return Integer.parseInt(stringProperty);
            } catch (Exception unused) {
            }
        }
        return i2;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public int getLogLevel() {
        return TraceLoggerManager.INSTANCE.getLogLevel();
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public long getLongProperty(String str, long j2) {
        String stringProperty = getStringProperty(str, null);
        if (stringProperty != null) {
            try {
                return Long.parseLong(stringProperty);
            } catch (Exception unused) {
            }
        }
        return j2;
    }

    public int getMaxPasswordLength() {
        return this.mMaxPasswordLength;
    }

    public int getMinPasswordLength() {
        return this.mMinPasswordLength;
    }

    public Class getMobileFragment() {
        return this.mobileFragment;
    }

    public Class getMobileRegisterFragment() {
        return this.mobileRegisterFragment;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public OnActivityResultCallback getOnActivityResultCallback() {
        return this.mOnActivityResultCallback;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public Map<String, String> getProperties() {
        Map<String, String> map = this.properties;
        if (map == null) {
            map = this.userProperties;
        }
        return Collections.unmodifiableMap(map);
    }

    public Class getPwdLoginFragment() {
        return this.pwdLoginFragment;
    }

    public Class getResetPwdFragment() {
        return this.resetPwdFragment;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public Version getSDKVersion() {
        return this.sdkVersion;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public String getSecurityImagePostfix() {
        String str = this.userConfigSecurityJpgPostfix;
        return str != null ? str : this.useSingleImage ? "" : DEFAULT_SECURITY_GUARD_IMAGE_SUFFIX[this.env.ordinal()];
    }

    public int getSocketTimeoutMillis() {
        return this.socketTimeoutMillis;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public String[] getStringArrayProperty(String str, String[] strArr) {
        String stringProperty = getStringProperty(str, null);
        return stringProperty != null ? stringProperty.split("[,]") : strArr;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public String getStringProperty(String str, String str2) {
        Map<String, String> map = this.properties;
        if (map == null) {
            map = this.userProperties;
        }
        String str3 = map.get(str);
        return str3 == null ? str2 : str3;
    }

    public String getTwitterId() {
        return this.mTwitterId;
    }

    public String getTwitterSecret() {
        return this.mTwitterSecret;
    }

    public void init(Context context) {
        if (this.initialized) {
            return;
        }
        this.context = context;
        this.properties = new ConcurrentHashMap();
        Iterator<ModuleInfo> it = Pluto.DEFAULT_INSTANCE.getModuleInfos().iterator();
        while (it.hasNext()) {
            Map<String, String> map = it.next().properties;
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        this.properties.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        overrideProperties(getDynamicConfigs(), false);
        overrideProperties(this.userProperties, false);
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "Initialize plugin system persistent configurations successfully");
        }
        this.initialized = true;
    }

    public boolean isAPIGateway() {
        return this.isAPIGateway;
    }

    public boolean isDailyNocaptcha() {
        return this.dailyNocaptcha;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    public boolean isDebugOKHttp() {
        return this.debugOKHttp;
    }

    public boolean isDegradeHttps() {
        return this.degradeHttps;
    }

    public boolean isLogoutLoginSDKSwitch() {
        return this.logoutLoginSDKSwitch;
    }

    public boolean isOpenMtop() {
        return this.isOpenMtop;
    }

    public boolean isRegisterLoginBroadcast() {
        return this.registerLoginBroadcast;
    }

    public boolean isShowPasswordStrengthHint() {
        return this.mIsShowPasswordStrengthHint;
    }

    public boolean isSupportOfflineLogin() {
        return this.supportOfflineLogin;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public boolean isUseSingleImage() {
        return this.useSingleImage;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public boolean openTaobaoUILogin() {
        return this.openTaobaoUILogin;
    }

    public void putExtBizParam(String str, String str2) {
        Map<String, String> map;
        if (TextUtils.isEmpty(str) || str.length() > 50 || TextUtils.isEmpty(str2) || str2.length() > 200 || (map = this.extBizParam) == null || map.size() >= 50) {
            return;
        }
        this.extBizParam.put(str, str2);
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void registerPropertyChangeListener(String str, PropertyChangeListener propertyChangeListener) {
        if (str == null || propertyChangeListener == null) {
            return;
        }
        Pluto.DEFAULT_INSTANCE.registerBean(PropertyChangeListener.class, propertyChangeListener, Collections.singletonMap(PROPERTY_NAME_FILTER_KEY, str));
    }

    public void setAPIGateway(boolean z2) {
        this.isAPIGateway = z2;
    }

    public void setAlipayAuthConfig(String str, String str2, String str3) {
        this.mAlipayAppId = str;
        this.mAlipayPid = str2;
        this.mAlipaySignType = str3;
    }

    public void setApiGatewayHost(String str) {
        this.mApiGatewayHost = str;
    }

    public void setApiHook(OAApiHook oAApiHook) {
        this.apiHook = oAApiHook;
    }

    public void setAppKey(String str) {
        this.mAppKey = str;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setAppKeyIndex(int i2, int i3, int i4, int i5) {
        Log.d(TAG, "setAppKeyIndex() called with: test = [" + i2 + "], online = [" + i3 + "], pre = [" + i4 + "], sandbox = [" + i5 + "]" + Log.getStackTraceString(new Throwable()));
        this.appKeyIndexes = new int[]{i2, i3, i4, i5};
        setUseSingleImage(true);
    }

    public void setAppSecret(String str) {
        this.mAppSecret = str;
    }

    public void setBundleName(String str) {
        this.bundleName = str;
    }

    public void setCheckCodeFragment(Class cls) {
        this.checkCodeFragment = cls;
    }

    public void setConfirmFragment(Class cls) {
        this.confirmFragment = cls;
    }

    public void setConnectionTimeoutMills(int i2) {
        this.connectionTimeoutMills = i2;
    }

    public void setDailyNocaptcha(boolean z2) {
        this.dailyNocaptcha = z2;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setDataProvider(OpenAccountProvider openAccountProvider) {
        this.openAccountProvider = openAccountProvider;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setDebugEnabled(boolean z2) {
        this.debugEnabled = z2;
    }

    public void setDebugOKHttp(boolean z2) {
        this.debugOKHttp = z2;
    }

    public void setDegradeHttps(boolean z2) {
        this.degradeHttps = z2;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setDynamicProperties(String str) {
        try {
            this.sp.edit().putString("config", str).commit();
        } catch (Throwable unused) {
        }
        overrideProperties(getDynamicConfigs(), true);
    }

    public void setEnvironment(final Environment environment) {
        final Environment environment2 = this.env;
        this.env = environment;
        if (!this.initialized || environment2 == environment) {
            return;
        }
        this.executorService.postTask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ConfigManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    for (EnvironmentChangeListener environmentChangeListener : (EnvironmentChangeListener[]) Pluto.DEFAULT_INSTANCE.getBeans(EnvironmentChangeListener.class)) {
                        environmentChangeListener.onEnvironmentChange(environment2, environment);
                    }
                } catch (Exception e2) {
                    AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to switch environment from " + environment2 + " to " + environment, e2);
                }
            }
        });
    }

    public void setFacebookId(String str) {
        this.mFacebookId = str;
    }

    public void setGoogleClientId(String str) {
        this.mGoogleClientId = str;
    }

    public void setHostWhiteList(List<String> list) {
        this.mHostWhiteList = list;
    }

    public void setLanguageCode(@LanguageCode.Language String str) {
        OpenAccountConfigs.clientLocal = str;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setLogLevel(int i2) {
        TraceLoggerManager.INSTANCE.setLogLevel(i2);
    }

    public void setLogoutLoginSDKSwitch(boolean z2) {
        this.logoutLoginSDKSwitch = z2;
    }

    public void setMaxPasswordLength(int i2) {
        this.mMaxPasswordLength = i2;
    }

    public void setMinPasswordLength(int i2) {
        this.mMinPasswordLength = i2;
    }

    public void setMobileFragment(Class cls) {
        this.mobileFragment = cls;
    }

    public void setMobileRegisterFragment(Class cls) {
        this.mobileRegisterFragment = cls;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setOnActivityResultCallback(OnActivityResultCallback onActivityResultCallback) {
        this.mOnActivityResultCallback = onActivityResultCallback;
    }

    public void setOpenMtop(boolean z2) {
        this.isOpenMtop = z2;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setOpenTaobaoUILogin(boolean z2) {
        this.openTaobaoUILogin = z2;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setProperty(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Map<String, String> map = this.properties;
        String strPut = map == null ? this.userProperties.put(str, str2) : map.put(str, str2);
        if (CommonUtils.isEqual(strPut, str2)) {
            return;
        }
        postPropertyChangeEvent(str, strPut, str2);
    }

    public void setPwdLoginFragment(Class cls) {
        this.pwdLoginFragment = cls;
    }

    public void setRegisterLoginBroadcast(boolean z2) {
        this.registerLoginBroadcast = z2;
    }

    public void setRequestCodeStartIndex(int i2) {
        RequestCodeAllocator.setStartRequestCodeIndex(i2);
    }

    public void setResetPwdFragment(Class cls) {
        this.resetPwdFragment = cls;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void setSecGuardImagePostfix(String str) {
        this.userConfigSecurityJpgPostfix = str;
    }

    public void setShowPasswordStrengthHint(boolean z2) {
        this.mIsShowPasswordStrengthHint = z2;
    }

    public void setSocketTimeoutMillis(int i2) {
        this.socketTimeoutMillis = i2;
    }

    public void setSupportOfflineLogin(boolean z2) {
        this.supportOfflineLogin = z2;
    }

    public void setTtid(String str) {
        TraceHelper.clientTTID = str;
    }

    public void setTwitterConfig(String str, String str2) {
        this.mTwitterId = str;
        this.mTwitterSecret = str2;
    }

    public void setUseSingleImage(boolean z2) {
        this.useSingleImage = z2;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public int getAppKeyIndex(Environment environment) {
        return this.appKeyIndexes[environment.ordinal()];
    }

    @Override // com.alibaba.sdk.android.openaccount.config.ConfigService
    public void registerPropertyChangeListener(String[] strArr, PropertyChangeListener propertyChangeListener) {
        for (String str : strArr) {
            Pluto.DEFAULT_INSTANCE.registerBean(PropertyChangeListener.class, propertyChangeListener, Collections.singletonMap(PROPERTY_NAME_FILTER_KEY, str));
        }
    }
}
