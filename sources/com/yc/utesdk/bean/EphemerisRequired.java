package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class EphemerisRequired {
    public static final int OPERATOR_DOWNLOAD = 1;
    public static final int OPERATOR_QUERY_STATUS = 2;
    private int operatorType;

    public EphemerisRequired(int i2) {
        this.operatorType = i2;
    }

    public int getOperatorType() {
        return this.operatorType;
    }

    public void setOperatorType(int i2) {
        this.operatorType = i2;
    }
}
