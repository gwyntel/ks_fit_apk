package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class BodyFatPersonInfo {

    /* renamed from: a, reason: collision with root package name */
    boolean f24687a;

    /* renamed from: b, reason: collision with root package name */
    int f24688b;
    private int bodyHeight;
    private float bodyWeight;

    /* renamed from: c, reason: collision with root package name */
    int f24689c;

    public BodyFatPersonInfo() {
        this.f24688b = -1;
        this.f24689c = -1;
        this.bodyHeight = -1;
        this.bodyWeight = -1.0f;
    }

    public int getBodyAge() {
        if (this.f24689c == -1) {
            this.f24689c = SPUtil.getInstance().getPersonageAge();
        }
        return this.f24689c;
    }

    public int getBodyGender() {
        if (this.f24688b == -1) {
            this.f24688b = SPUtil.getInstance().getPersonageGender();
        }
        return this.f24688b;
    }

    public int getBodyHeight() {
        if (this.bodyHeight == -1) {
            this.bodyHeight = SPUtil.getInstance().getPersonageHeight();
        }
        return this.bodyHeight;
    }

    public float getBodyWeight() {
        if (this.bodyWeight == -1.0f) {
            this.bodyWeight = SPUtil.getInstance().getPersonageWeight();
        }
        return this.bodyWeight;
    }

    public boolean getStartTest() {
        return this.f24687a;
    }

    public void setBodyAge(int i2) {
        this.f24689c = i2;
    }

    public void setBodyGender(int i2) {
        this.f24688b = i2;
    }

    public void setBodyHeight(int i2) {
        this.bodyHeight = i2;
    }

    public void setBodyWeight(float f2) {
        this.bodyWeight = f2;
    }

    public void setStartTest(boolean z2) {
        this.f24687a = z2;
    }

    public BodyFatPersonInfo(boolean z2, int i2, int i3, int i4, float f2) {
        this.f24687a = z2;
        this.f24688b = i2;
        this.f24689c = i3;
        this.bodyHeight = i4;
        this.bodyWeight = f2;
    }
}
