package com.alibaba.ailabs.iot.mesh.provision;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.spec.TLV;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class WiFiConfigReplyParser {

    /* renamed from: a, reason: collision with root package name */
    public final String f8762a = "wifi_config_" + WiFiConfigReplyParser.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public int f8763b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f8764c = 0;

    /* renamed from: d, reason: collision with root package name */
    public a<Object> f8765d;

    public enum Status {
        START_CONNECT_AP(1, "start connect ap"),
        START_OBTAIN_IP(2, "start obtain ip"),
        START_CONNECT_MQTT(3, "start connect mqtt"),
        CONNECT_AP_SUCCESS(4, "on successful connect ap"),
        OBTAIN_IP_SUCCESS(5, "on successful obtain ip"),
        CONNECT_MQTT_SUCCESS(6, "on successful connect mqtt"),
        UNDEFINED(0, "undefined status");

        public int code;
        public String desc;

        Status(int i2, String str) {
            this.code = i2;
            this.desc = str;
        }

        public static Status parseFromValue(int i2) {
            switch (i2) {
                case 1:
                    return START_CONNECT_AP;
                case 2:
                    return START_OBTAIN_IP;
                case 3:
                    return START_CONNECT_MQTT;
                case 4:
                    return CONNECT_AP_SUCCESS;
                case 5:
                    return OBTAIN_IP_SUCCESS;
                case 6:
                    return CONNECT_MQTT_SUCCESS;
                default:
                    return UNDEFINED;
            }
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    public interface a<T> extends IActionListener<T> {
        void a(int i2, int i3, String str);

        void a(Status status);
    }

    public WiFiConfigReplyParser(a<Object> aVar) {
        this.f8765d = aVar;
    }

    public void a(byte[] bArr) {
        List<TLV> multiFromBytes = (bArr == null || bArr.length < 3) ? null : TLV.parseMultiFromBytes(bArr);
        if (multiFromBytes == null || multiFromBytes.size() == 0) {
            return;
        }
        for (TLV tlv : multiFromBytes) {
            byte[] value = tlv.getValue();
            if (value != null && value.length != 0) {
                byte type = tlv.getType();
                if (type == 1) {
                    byte b2 = value[0];
                    if (b2 == 2) {
                        a.a.a.a.b.m.a.d(this.f8762a, "onMessage device connect ap or connect mqtt failed.");
                        this.f8764c = -1;
                    } else if (b2 == 1) {
                        a.a.a.a.b.m.a.c(this.f8762a, "onMessage connect ap success.");
                        this.f8764c = 1;
                        a<Object> aVar = this.f8765d;
                        if (aVar != null) {
                            aVar.onSuccess("");
                            return;
                        }
                    } else if (b2 == 3) {
                        a.a.a.a.b.m.a.c(this.f8762a, "onMessage token report success. params=");
                        this.f8764c = 2;
                    }
                } else if (type == 2) {
                    continue;
                } else if (type == 3) {
                    if (value.length == 2) {
                        this.f8763b = ((value[1] & 255) << 8) | (value[0] & 255);
                    }
                    int i2 = this.f8763b;
                    if (i2 >= 50404) {
                        int i3 = this.f8764c;
                        if (i3 == -1) {
                            a.a.a.a.b.m.a.c(this.f8762a, "onMessage device connect provision fail, wait for device to retry until timeout.");
                            return;
                        }
                        if (i3 == 1) {
                            a.a.a.a.b.m.a.c(this.f8762a, "onMessage device connect ap success, device connect cloud failed, wait until timeout.");
                            return;
                        } else if (i3 == 2) {
                            a.a.a.a.b.m.a.c(this.f8762a, "onMessage device connect ap success, reportToken success, wait until loop cloud check.");
                            return;
                        } else {
                            a.a.a.a.b.m.a.c(this.f8762a, "onMessage device unexpected state returned, device connect cloud failed, wait until timeout.");
                            return;
                        }
                    }
                    a(-71, i2, "wifi config fail. error code from node: " + this.f8763b);
                } else if (type != 4) {
                    a.a.a.a.b.m.a.d(this.f8762a, String.format(Locale.getDefault(), "Unknown type: %d", Byte.valueOf(tlv.getType())));
                } else {
                    int i4 = ((value[1] & 255) << 8) | (value[0] & 255);
                    Status fromValue = Status.parseFromValue(i4);
                    a.a.a.a.b.m.a.a(this.f8762a, String.format(Locale.getDefault(), "device notifies of the status change to: %s, value: %d", fromValue, Integer.valueOf(i4)));
                    a<Object> aVar2 = this.f8765d;
                    if (aVar2 != null) {
                        aVar2.a(Status.parseFromValue(i4));
                        if (fromValue == Status.CONNECT_MQTT_SUCCESS) {
                            this.f8765d.onSuccess("");
                        } else if (fromValue == Status.UNDEFINED) {
                            a(-71, i4, "wifi config fail.");
                        }
                    }
                }
            }
        }
    }

    public final void a(int i2, int i3, String str) {
        a<Object> aVar = this.f8765d;
        if (aVar != null) {
            aVar.a(i2, i3, str);
        }
    }
}
