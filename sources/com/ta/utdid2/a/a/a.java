package com.ta.utdid2.a.a;

import android.annotation.SuppressLint;
import com.alibaba.ailabs.iot.aisbase.Constants;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: d, reason: collision with root package name */
    static final /* synthetic */ boolean f20023d = true;

    /* renamed from: com.ta.utdid2.a.a.a$a, reason: collision with other inner class name */
    static abstract class AbstractC0167a {

        /* renamed from: a, reason: collision with root package name */
        public byte[] f20024a;

        /* renamed from: b, reason: collision with root package name */
        public int f20025b;

        AbstractC0167a() {
        }
    }

    static class b extends AbstractC0167a {

        /* renamed from: b, reason: collision with root package name */
        private static final byte[] f20026b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 43, Constants.CMD_TYPE.CMD_OTA};

        /* renamed from: c, reason: collision with root package name */
        private static final byte[] f20027c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 45, 95};

        /* renamed from: d, reason: collision with root package name */
        static final /* synthetic */ boolean f20028d = true;

        /* renamed from: c, reason: collision with other field name */
        int f61c;
        private int count;

        /* renamed from: d, reason: collision with other field name */
        private final byte[] f62d;

        /* renamed from: e, reason: collision with root package name */
        public final boolean f20029e;

        /* renamed from: e, reason: collision with other field name */
        private final byte[] f63e;

        /* renamed from: f, reason: collision with root package name */
        public final boolean f20030f;

        /* renamed from: g, reason: collision with root package name */
        public final boolean f20031g;

        public b(int i2, byte[] bArr) {
            this.f20024a = bArr;
            this.f20029e = (i2 & 1) == 0;
            boolean z2 = (i2 & 2) == 0;
            this.f20030f = z2;
            this.f20031g = (i2 & 4) != 0;
            this.f63e = (i2 & 8) == 0 ? f20026b : f20027c;
            this.f62d = new byte[2];
            this.f61c = 0;
            this.count = z2 ? 19 : -1;
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        /* JADX WARN: Removed duplicated region for block: B:100:0x00e6 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0094  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x01c2  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x01cf A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean a(byte[] r19, int r20, int r21, boolean r22) {
            /*
                Method dump skipped, instructions count: 517
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.a.a.a.b.a(byte[], int, int, boolean):boolean");
        }
    }

    private a() {
    }

    public static byte[] encode(byte[] bArr, int i2) {
        return encode(bArr, 0, bArr.length, i2);
    }

    public static String encodeToString(byte[] bArr, int i2) {
        try {
            return new String(encode(bArr, i2), "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    @SuppressLint({"Assert"})
    public static byte[] encode(byte[] bArr, int i2, int i3, int i4) {
        b bVar = new b(i4, null);
        int i5 = (i3 / 3) * 4;
        if (!bVar.f20029e) {
            int i6 = i3 % 3;
            if (i6 == 1) {
                i5 += 2;
            } else if (i6 == 2) {
                i5 += 3;
            }
        } else if (i3 % 3 > 0) {
            i5 += 4;
        }
        if (bVar.f20030f && i3 > 0) {
            i5 += (((i3 - 1) / 57) + 1) * (bVar.f20031g ? 2 : 1);
        }
        bVar.f20024a = new byte[i5];
        bVar.a(bArr, i2, i3, true);
        if (f20023d || bVar.f20025b == i5) {
            return bVar.f20024a;
        }
        throw new AssertionError();
    }
}
