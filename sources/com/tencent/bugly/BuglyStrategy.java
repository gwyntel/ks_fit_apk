package com.tencent.bugly;

import com.tencent.bugly.proguard.aa;
import java.util.Map;

/* loaded from: classes4.dex */
public class BuglyStrategy {

    /* renamed from: c, reason: collision with root package name */
    private String f20547c;

    /* renamed from: d, reason: collision with root package name */
    private String f20548d;

    /* renamed from: e, reason: collision with root package name */
    private String f20549e;

    /* renamed from: f, reason: collision with root package name */
    private long f20550f;

    /* renamed from: g, reason: collision with root package name */
    private String f20551g;

    /* renamed from: h, reason: collision with root package name */
    private String f20552h;

    /* renamed from: i, reason: collision with root package name */
    private String f20553i;

    /* renamed from: u, reason: collision with root package name */
    private a f20565u;

    /* renamed from: j, reason: collision with root package name */
    private boolean f20554j = true;

    /* renamed from: k, reason: collision with root package name */
    private boolean f20555k = true;

    /* renamed from: l, reason: collision with root package name */
    private boolean f20556l = true;

    /* renamed from: m, reason: collision with root package name */
    private boolean f20557m = false;

    /* renamed from: n, reason: collision with root package name */
    private boolean f20558n = true;

    /* renamed from: o, reason: collision with root package name */
    private Class<?> f20559o = null;

    /* renamed from: p, reason: collision with root package name */
    private boolean f20560p = true;

    /* renamed from: q, reason: collision with root package name */
    private boolean f20561q = true;

    /* renamed from: r, reason: collision with root package name */
    private boolean f20562r = true;

    /* renamed from: s, reason: collision with root package name */
    private boolean f20563s = true;

    /* renamed from: t, reason: collision with root package name */
    private boolean f20564t = false;

    /* renamed from: a, reason: collision with root package name */
    protected int f20545a = 31;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f20546b = false;

    /* renamed from: v, reason: collision with root package name */
    private boolean f20566v = false;

    public static class a {
        public static final int CRASHTYPE_ANR = 4;
        public static final int CRASHTYPE_BLOCK = 7;
        public static final int CRASHTYPE_COCOS2DX_JS = 5;
        public static final int CRASHTYPE_COCOS2DX_LUA = 6;
        public static final int CRASHTYPE_JAVA_CATCH = 1;
        public static final int CRASHTYPE_JAVA_CRASH = 0;
        public static final int CRASHTYPE_NATIVE = 2;
        public static final int CRASHTYPE_U3D = 3;
        public static final int MAX_USERDATA_KEY_LENGTH = 100;
        public static final int MAX_USERDATA_VALUE_LENGTH = 100000;

        public synchronized Map<String, String> onCrashHandleStart(int i2, String str, String str2, String str3) {
            return null;
        }

        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int i2, String str, String str2, String str3) {
            return null;
        }
    }

    public synchronized String getAppChannel() {
        String str = this.f20548d;
        if (str != null) {
            return str;
        }
        return aa.b().f20694s;
    }

    public synchronized String getAppPackageName() {
        String str = this.f20549e;
        if (str != null) {
            return str;
        }
        return aa.b().f20678c;
    }

    public synchronized long getAppReportDelay() {
        return this.f20550f;
    }

    public synchronized String getAppVersion() {
        String str = this.f20547c;
        if (str != null) {
            return str;
        }
        return aa.b().f20690o;
    }

    public synchronized int getCallBackType() {
        return this.f20545a;
    }

    public synchronized boolean getCloseErrorCallback() {
        return this.f20546b;
    }

    public synchronized a getCrashHandleCallback() {
        return this.f20565u;
    }

    public synchronized String getDeviceID() {
        return this.f20552h;
    }

    public synchronized String getDeviceModel() {
        return this.f20553i;
    }

    public synchronized String getLibBuglySOFilePath() {
        return this.f20551g;
    }

    public synchronized Class<?> getUserInfoActivity() {
        return this.f20559o;
    }

    public synchronized boolean isBuglyLogUpload() {
        return this.f20560p;
    }

    public synchronized boolean isEnableANRCrashMonitor() {
        return this.f20555k;
    }

    public synchronized boolean isEnableCatchAnrTrace() {
        return this.f20556l;
    }

    public synchronized boolean isEnableNativeCrashMonitor() {
        return this.f20554j;
    }

    public boolean isEnableRecordAnrMainStack() {
        return this.f20557m;
    }

    public synchronized boolean isEnableUserInfo() {
        return this.f20558n;
    }

    public boolean isMerged() {
        return this.f20566v;
    }

    public boolean isReplaceOldChannel() {
        return this.f20561q;
    }

    public synchronized boolean isUploadProcess() {
        return this.f20562r;
    }

    public synchronized boolean isUploadSpotCrash() {
        return this.f20563s;
    }

    public synchronized boolean recordUserInfoOnceADay() {
        return this.f20564t;
    }

    public synchronized BuglyStrategy setAppChannel(String str) {
        this.f20548d = str;
        return this;
    }

    public synchronized BuglyStrategy setAppPackageName(String str) {
        this.f20549e = str;
        return this;
    }

    public synchronized BuglyStrategy setAppReportDelay(long j2) {
        this.f20550f = j2;
        return this;
    }

    public synchronized BuglyStrategy setAppVersion(String str) {
        this.f20547c = str;
        return this;
    }

    public synchronized BuglyStrategy setBuglyLogUpload(boolean z2) {
        this.f20560p = z2;
        return this;
    }

    public synchronized void setCallBackType(int i2) {
        this.f20545a = i2;
    }

    public synchronized void setCloseErrorCallback(boolean z2) {
        this.f20546b = z2;
    }

    public synchronized BuglyStrategy setCrashHandleCallback(a aVar) {
        this.f20565u = aVar;
        return this;
    }

    public synchronized BuglyStrategy setDeviceID(String str) {
        this.f20552h = str;
        return this;
    }

    public synchronized BuglyStrategy setDeviceModel(String str) {
        this.f20553i = str;
        return this;
    }

    public synchronized BuglyStrategy setEnableANRCrashMonitor(boolean z2) {
        this.f20555k = z2;
        return this;
    }

    public void setEnableCatchAnrTrace(boolean z2) {
        this.f20556l = z2;
    }

    public synchronized BuglyStrategy setEnableNativeCrashMonitor(boolean z2) {
        this.f20554j = z2;
        return this;
    }

    public void setEnableRecordAnrMainStack(boolean z2) {
        this.f20557m = z2;
    }

    public synchronized BuglyStrategy setEnableUserInfo(boolean z2) {
        this.f20558n = z2;
        return this;
    }

    public synchronized BuglyStrategy setLibBuglySOFilePath(String str) {
        this.f20551g = str;
        return this;
    }

    @Deprecated
    public void setMerged(boolean z2) {
        this.f20566v = z2;
    }

    public synchronized BuglyStrategy setRecordUserInfoOnceADay(boolean z2) {
        this.f20564t = z2;
        return this;
    }

    public void setReplaceOldChannel(boolean z2) {
        this.f20561q = z2;
    }

    public synchronized BuglyStrategy setUploadProcess(boolean z2) {
        this.f20562r = z2;
        return this;
    }

    public synchronized void setUploadSpotCrash(boolean z2) {
        this.f20563s = z2;
    }

    public synchronized BuglyStrategy setUserInfoActivity(Class<?> cls) {
        this.f20559o = cls;
        return this;
    }
}
