package com.alibaba.sdk.android.openaccount;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes2.dex */
public class Version {
    private int major;
    private int micro;
    private int minor;

    public Version(int i2, int i3, int i4) {
        this.major = i2;
        this.minor = i3;
        this.micro = i4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Version version = (Version) obj;
        return this.major == version.major && this.micro == version.micro && this.minor == version.minor;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMicro() {
        return this.micro;
    }

    public int getMinor() {
        return this.minor;
    }

    public int hashCode() {
        return ((((this.major + 31) * 31) + this.micro) * 31) + this.minor;
    }

    void setVersion(String str) {
        if (str == null) {
            throw new IllegalStateException("null version");
        }
        String[] strArrSplit = str.split("[.]");
        if (strArrSplit.length != 3) {
            throw new IllegalArgumentException(str + " is not a valid version");
        }
        this.major = Integer.parseInt(strArrSplit[0]);
        this.minor = Integer.parseInt(strArrSplit[1]);
        int iIndexOf = strArrSplit[2].indexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        if (iIndexOf != -1) {
            this.micro = Integer.parseInt(strArrSplit[2].substring(0, iIndexOf));
        } else {
            this.micro = Integer.parseInt(strArrSplit[2]);
        }
    }

    public String toString() {
        return this.major + "." + this.minor + "." + this.micro;
    }
}
