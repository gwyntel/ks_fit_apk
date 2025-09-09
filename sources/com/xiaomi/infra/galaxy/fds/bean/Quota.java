package com.xiaomi.infra.galaxy.fds.bean;

import com.xiaomi.infra.galaxy.fds.Action;

/* loaded from: classes4.dex */
public class Quota {
    private Action action;
    private QuotaType type;
    private long value;

    public enum QuotaType {
        QPS,
        Throughput
    }

    public Quota() {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Quota quota = (Quota) obj;
        return this.value == quota.value && this.action == quota.action && this.type == quota.type;
    }

    public Action getAction() {
        return this.action;
    }

    public QuotaType getType() {
        return this.type;
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        QuotaType quotaType = this.type;
        int iHashCode = (quotaType != null ? quotaType.hashCode() : 0) * 31;
        Action action = this.action;
        int iHashCode2 = (iHashCode + (action != null ? action.hashCode() : 0)) * 31;
        long j2 = this.value;
        return iHashCode2 + ((int) (j2 ^ (j2 >>> 32)));
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setType(QuotaType quotaType) {
        this.type = quotaType;
    }

    public void setValue(long j2) {
        this.value = j2;
    }

    public Quota(QuotaType quotaType, Action action, long j2) {
        this.type = quotaType;
        this.action = action;
        this.value = j2;
    }
}
