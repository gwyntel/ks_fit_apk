package com.xiaomi.infra.galaxy.fds.model;

/* loaded from: classes4.dex */
public class MetricData {
    private String metricName;
    private MetricType metricType;
    private long timestamp;
    private long value;

    public enum MetricType {
        Latency,
        Throughput,
        Counter
    }

    public MetricData() {
    }

    public String getMetricName() {
        return this.metricName;
    }

    public MetricType getMetricType() {
        return this.metricType;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getValue() {
        return this.value;
    }

    public void setMetricName(String str) {
        this.metricName = str;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public void setTimestamp(long j2) {
        this.timestamp = j2;
    }

    public void setValue(long j2) {
        this.value = j2;
    }

    public MetricData withMetricName(String str) {
        this.metricName = str;
        return this;
    }

    public MetricData withMetricType(MetricType metricType) {
        this.metricType = metricType;
        return this;
    }

    public MetricData withTimeStamp(long j2) {
        this.timestamp = j2;
        return this;
    }

    public MetricData withValue(long j2) {
        this.value = j2;
        return this;
    }

    public MetricData(MetricType metricType, String str, long j2, long j3) {
        this.metricType = metricType;
        this.metricName = str;
        this.value = j2;
        this.timestamp = j3;
    }
}
