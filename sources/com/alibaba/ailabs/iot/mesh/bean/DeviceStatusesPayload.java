package com.alibaba.ailabs.iot.mesh.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes2.dex */
public class DeviceStatusesPayload implements Serializable {
    public List<SubDeviceStatuses> subDeviceStatuses;

    public static class SubDeviceStatuses implements Serializable {
        public String linkQualityMetric;
        public String status;
        public int unicastAddress;

        public SubDeviceStatuses() {
        }

        public String getLinkQualityMetric() {
            return this.linkQualityMetric;
        }

        public String getStatus() {
            return this.status;
        }

        public int getUnicastAddress() {
            return this.unicastAddress;
        }

        public void setLinkQualityMetric(String str) {
            this.linkQualityMetric = str;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public void setUnicastAddress(int i2) {
            this.unicastAddress = i2;
        }

        public SubDeviceStatuses(int i2, String str) {
            this.unicastAddress = i2;
            this.status = str;
        }
    }

    public List<SubDeviceStatuses> getSubDeviceStatuses() {
        return this.subDeviceStatuses;
    }

    public void setSubDeviceStatuses(List<SubDeviceStatuses> list) {
        this.subDeviceStatuses = list;
    }
}
