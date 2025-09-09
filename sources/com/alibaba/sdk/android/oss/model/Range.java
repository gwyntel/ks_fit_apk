package com.alibaba.sdk.android.oss.model;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes2.dex */
public class Range {
    public static final long INFINITE = -1;
    private long begin;
    private long end;

    public Range(long j2, long j3) {
        setBegin(j2);
        setEnd(j3);
    }

    public boolean checkIsValid() {
        long j2 = this.begin;
        if (j2 >= -1) {
            long j3 = this.end;
            if (j3 >= -1) {
                return j2 < 0 || j3 < 0 || j2 <= j3;
            }
        }
        return false;
    }

    public long getBegin() {
        return this.begin;
    }

    public long getEnd() {
        return this.end;
    }

    public void setBegin(long j2) {
        this.begin = j2;
    }

    public void setEnd(long j2) {
        this.end = j2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bytes=");
        long j2 = this.begin;
        sb.append(j2 == -1 ? "" : String.valueOf(j2));
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        long j3 = this.end;
        sb.append(j3 != -1 ? String.valueOf(j3) : "");
        return sb.toString();
    }
}
