package com.alibaba.sdk.android.openaccount.config;

import android.content.Context;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes2.dex */
public class OpenAccountProvider {
    protected String TID;
    protected String TTID;
    protected String alipaySsoDesKey;
    protected String appKey;
    protected String appName;
    protected Context context;
    protected String deviceId;
    protected boolean forbidRefreshCookieInAutologin;
    protected String guideAppName;
    protected String guideBackground;
    protected String guideCloseResource;
    protected String guidePwdLoginResource;
    protected String imei;
    protected String imsi;
    protected String productId;
    protected String productVersion;
    protected boolean refreshCookieDegrade;
    protected ThreadPoolExecutor threadPool;
    protected String version;
    protected boolean isAppDebug = false;
    protected boolean isUnitDeploy = false;
    protected boolean forceShowPwdInAlert = false;
    protected boolean isTaobaoApp = false;
    protected boolean needSsoLogin = true;
    protected boolean needSsoLoginUI = false;
    protected boolean needWindVaneInit = false;
    protected boolean needSsoV2Login = false;
    protected boolean needAlipaySsoGuide = false;
    protected boolean needTaobaoSsoGuide = false;
    protected boolean needPwdGuide = true;
    protected boolean needAccsLogin = false;
    protected boolean needSsoV2LoginUI = false;
    protected boolean useSeparateThreadPool = false;
    protected boolean registerMachineCheckDegrade = false;
    protected boolean needEnterPriseRegister = true;
    protected String appId = "";
    protected int envType = 3;
    protected int site = 0;
    protected boolean isForbidLoginFromBackground = false;
    protected boolean needHelpButton = true;
    protected boolean needAlipayLoginBtn = true;

    public String getAlipaySsoDesKey() {
        return this.alipaySsoDesKey;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getAppName() {
        return this.appName;
    }

    public Context getContext() {
        return this.context;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public int getEnvType() {
        return this.envType;
    }

    public String getGuideAppName() {
        return this.guideAppName;
    }

    public String getGuideBackground() {
        return this.guideBackground;
    }

    public String getGuideCloseResource() {
        return this.guideCloseResource;
    }

    public String getGuidePwdLoginResource() {
        return this.guidePwdLoginResource;
    }

    public String getImei() {
        return this.imei;
    }

    public String getImsi() {
        return this.imsi;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public int getSite() {
        return this.site;
    }

    public String getTID() {
        return this.TID;
    }

    public String getTTID() {
        return this.TTID;
    }

    public ThreadPoolExecutor getThreadPool() {
        return this.threadPool;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean isAppDebug() {
        return this.isAppDebug;
    }

    public boolean isForbidLoginFromBackground() {
        return this.isForbidLoginFromBackground;
    }

    public boolean isForbidRefreshCookieInAutologin() {
        return this.forbidRefreshCookieInAutologin;
    }

    public boolean isNeedAccsLogin() {
        return this.needAccsLogin;
    }

    public boolean isNeedAlipayLoginBtn() {
        return this.needAlipayLoginBtn;
    }

    public boolean isNeedAlipaySsoGuide() {
        return this.needAlipaySsoGuide;
    }

    public boolean isNeedEnterPriseRegister() {
        return this.needEnterPriseRegister;
    }

    public boolean isNeedHelpButton() {
        return this.needHelpButton;
    }

    public boolean isNeedPwdGuide() {
        return this.needPwdGuide;
    }

    public boolean isNeedSsoLogin() {
        return this.needSsoLogin;
    }

    public boolean isNeedSsoLoginUI() {
        return this.needSsoLoginUI;
    }

    public boolean isNeedSsoV2Login() {
        return this.needSsoV2Login;
    }

    public boolean isNeedSsoV2LoginUI() {
        return this.needSsoV2LoginUI;
    }

    public boolean isNeedTaobaoSsoGuide() {
        return this.needTaobaoSsoGuide;
    }

    public boolean isNeedWindVaneInit() {
        return this.needWindVaneInit;
    }

    public boolean isRefreshCookieDegrade() {
        return this.refreshCookieDegrade;
    }

    public boolean isRegisterMachineCheckDegrade() {
        return this.registerMachineCheckDegrade;
    }

    public boolean isTaobaoApp() {
        return this.isTaobaoApp;
    }

    public boolean isUnitDeploy() {
        return this.isUnitDeploy;
    }

    public boolean isUseSeparateThreadPool() {
        return this.useSeparateThreadPool;
    }

    public void setAlipaySsoDesKey(String str) {
        this.alipaySsoDesKey = str;
    }

    public void setAppDebug(boolean z2) {
        this.isAppDebug = z2;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setEnvType(int i2) {
        this.envType = i2;
    }

    public void setForbidLoginFromBackground(boolean z2) {
        this.isForbidLoginFromBackground = z2;
    }

    public void setForbidRefreshCookieInAutologin(boolean z2) {
        this.forbidRefreshCookieInAutologin = z2;
    }

    public void setGuideAppName(String str) {
        this.guideAppName = str;
    }

    public void setGuideBackground(String str) {
        this.guideBackground = str;
    }

    public void setGuideCloseResource(String str) {
        this.guideCloseResource = str;
    }

    public void setGuidePwdLoginResource(String str) {
        this.guidePwdLoginResource = str;
    }

    public void setImei(String str) {
        this.imei = str;
    }

    public void setImsi(String str) {
        this.imsi = str;
    }

    public void setNeedAccsLogin(boolean z2) {
        this.needAccsLogin = z2;
    }

    public void setNeedAlipayLoginBtn(boolean z2) {
        this.needAlipayLoginBtn = z2;
    }

    public void setNeedAlipaySsoGuide(boolean z2) {
        this.needAlipaySsoGuide = z2;
    }

    public void setNeedEnterPriseRegister(boolean z2) {
        this.needEnterPriseRegister = z2;
    }

    public void setNeedHelpButton(boolean z2) {
        this.needHelpButton = z2;
    }

    public void setNeedPwdGuide(boolean z2) {
        this.needPwdGuide = z2;
    }

    public void setNeedSsoLogin(boolean z2) {
        this.needSsoLogin = z2;
    }

    public void setNeedSsoLoginUI(boolean z2) {
        this.needSsoLoginUI = z2;
    }

    public void setNeedSsoV2Login(boolean z2) {
        this.needSsoV2Login = z2;
    }

    public void setNeedSsoV2LoginUI(boolean z2) {
        this.needSsoV2LoginUI = z2;
    }

    public void setNeedTaobaoSsoGuide(boolean z2) {
        this.needTaobaoSsoGuide = z2;
    }

    public void setNeedWindVaneInit(boolean z2) {
        this.needWindVaneInit = z2;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public void setProductVersion(String str) {
        this.productVersion = str;
    }

    public void setRefreshCookieDegrade(boolean z2) {
        this.refreshCookieDegrade = z2;
    }

    public void setRegisterMachineCheckDegrade(boolean z2) {
        this.registerMachineCheckDegrade = z2;
    }

    public void setSite(int i2) {
        this.site = i2;
    }

    public void setTID(String str) {
        this.TID = str;
    }

    public void setTTID(String str) {
        this.TTID = str;
    }

    public void setTaobaoApp(boolean z2) {
        this.isTaobaoApp = z2;
    }

    public void setThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPool = threadPoolExecutor;
    }

    public void setUnitDeploy(boolean z2) {
        this.isUnitDeploy = z2;
    }

    public void setUseSeparateThreadPool(boolean z2) {
        this.useSeparateThreadPool = z2;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public boolean showPWDInAlert() {
        return this.forceShowPwdInAlert;
    }
}
