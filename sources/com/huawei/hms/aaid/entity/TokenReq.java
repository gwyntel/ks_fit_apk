package com.huawei.hms.aaid.entity;

import com.alipay.sdk.m.u.i;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.annotation.Packed;

/* loaded from: classes.dex */
public class TokenReq implements IMessageEntity {

    @Packed
    private String aaid;

    @Packed
    private String appId;

    @Packed
    private boolean firstTime;

    @Packed
    private String packageName;

    @Packed
    private String projectId;

    @Packed
    private String scope;

    @Packed
    private String subjectId;

    @Packed
    private boolean isMultiSender = false;

    @Packed
    private boolean isFastApp = false;

    public String getAaid() {
        return this.aaid;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public String getScope() {
        return this.scope;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public boolean isFastApp() {
        return this.isFastApp;
    }

    public boolean isFirstTime() {
        return this.firstTime;
    }

    public boolean isMultiSender() {
        return this.isMultiSender;
    }

    public void setAaid(String str) {
        this.aaid = str;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setFastApp(boolean z2) {
        this.isFastApp = z2;
    }

    public void setFirstTime(boolean z2) {
        this.firstTime = z2;
    }

    public void setMultiSender(boolean z2) {
        this.isMultiSender = z2;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setProjectId(String str) {
        this.projectId = str;
    }

    public void setScope(String str) {
        this.scope = str;
    }

    public void setSubjectId(String str) {
        this.subjectId = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append("{");
        stringBuffer.append("pkgName: ");
        stringBuffer.append(this.packageName);
        stringBuffer.append(",isFirstTime: ");
        stringBuffer.append(this.firstTime);
        stringBuffer.append(",scope:");
        stringBuffer.append(this.scope);
        stringBuffer.append(",appId:");
        stringBuffer.append(this.appId);
        stringBuffer.append(",projectId:");
        stringBuffer.append(this.projectId);
        stringBuffer.append(",subjectId:");
        stringBuffer.append(this.subjectId);
        stringBuffer.append(i.f9804d);
        return stringBuffer.toString();
    }
}
