package com.alipay.sdk.m.l0;

import android.annotation.SuppressLint;
import com.alibaba.ailabs.iot.aisbase.Constants;
import java.io.UnsupportedEncodingException;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ boolean f9465a = true;

    public static abstract class a {

        /* renamed from: a, reason: collision with root package name */
        public byte[] f9466a;

        /* renamed from: b, reason: collision with root package name */
        public int f9467b;
    }

    /* renamed from: com.alipay.sdk.m.l0.b$b, reason: collision with other inner class name */
    public static class C0049b extends a {

        /* renamed from: f, reason: collision with root package name */
        public static final int[] f9468f = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

        /* renamed from: g, reason: collision with root package name */
        public static final int[] f9469g = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

        /* renamed from: c, reason: collision with root package name */
        public int f9470c;

        /* renamed from: d, reason: collision with root package name */
        public int f9471d;

        /* renamed from: e, reason: collision with root package name */
        public final int[] f9472e;

        public C0049b(int i2, byte[] bArr) {
            this.f9466a = bArr;
            this.f9472e = (i2 & 8) == 0 ? f9468f : f9469g;
            this.f9470c = 0;
            this.f9471d = 0;
        }

        public boolean a(byte[] bArr, int i2, int i3, boolean z2) {
            int i4 = this.f9470c;
            if (i4 == 6) {
                return false;
            }
            int i5 = i3 + i2;
            int i6 = this.f9471d;
            byte[] bArr2 = this.f9466a;
            int[] iArr = this.f9472e;
            int i7 = 0;
            int i8 = i6;
            int i9 = i4;
            int i10 = i2;
            while (i10 < i5) {
                if (i9 == 0) {
                    while (true) {
                        int i11 = i10 + 4;
                        if (i11 > i5 || (i8 = (iArr[bArr[i10] & 255] << 18) | (iArr[bArr[i10 + 1] & 255] << 12) | (iArr[bArr[i10 + 2] & 255] << 6) | iArr[bArr[i10 + 3] & 255]) < 0) {
                            break;
                        }
                        bArr2[i7 + 2] = (byte) i8;
                        bArr2[i7 + 1] = (byte) (i8 >> 8);
                        bArr2[i7] = (byte) (i8 >> 16);
                        i7 += 3;
                        i10 = i11;
                    }
                    if (i10 >= i5) {
                        break;
                    }
                }
                int i12 = i10 + 1;
                int i13 = iArr[bArr[i10] & 255];
                if (i9 != 0) {
                    if (i9 == 1) {
                        if (i13 < 0) {
                            if (i13 != -1) {
                                this.f9470c = 6;
                                return false;
                            }
                        }
                        i13 |= i8 << 6;
                    } else if (i9 == 2) {
                        if (i13 < 0) {
                            if (i13 == -2) {
                                bArr2[i7] = (byte) (i8 >> 4);
                                i7++;
                                i9 = 4;
                            } else if (i13 != -1) {
                                this.f9470c = 6;
                                return false;
                            }
                        }
                        i13 |= i8 << 6;
                    } else if (i9 != 3) {
                        if (i9 != 4) {
                            if (i9 == 5 && i13 != -1) {
                                this.f9470c = 6;
                                return false;
                            }
                        } else if (i13 == -2) {
                            i9++;
                        } else if (i13 != -1) {
                            this.f9470c = 6;
                            return false;
                        }
                    } else if (i13 >= 0) {
                        int i14 = i13 | (i8 << 6);
                        bArr2[i7 + 2] = (byte) i14;
                        bArr2[i7 + 1] = (byte) (i14 >> 8);
                        bArr2[i7] = (byte) (i14 >> 16);
                        i7 += 3;
                        i8 = i14;
                        i9 = 0;
                    } else if (i13 == -2) {
                        bArr2[i7 + 1] = (byte) (i8 >> 2);
                        bArr2[i7] = (byte) (i8 >> 10);
                        i7 += 2;
                        i9 = 5;
                    } else if (i13 != -1) {
                        this.f9470c = 6;
                        return false;
                    }
                    i9++;
                    i8 = i13;
                } else if (i13 >= 0) {
                    i9++;
                    i8 = i13;
                } else if (i13 != -1) {
                    this.f9470c = 6;
                    return false;
                }
                i10 = i12;
            }
            if (!z2) {
                this.f9470c = i9;
                this.f9471d = i8;
                this.f9467b = i7;
                return true;
            }
            if (i9 == 1) {
                this.f9470c = 6;
                return false;
            }
            if (i9 == 2) {
                bArr2[i7] = (byte) (i8 >> 4);
                i7++;
            } else if (i9 == 3) {
                int i15 = i7 + 1;
                bArr2[i7] = (byte) (i8 >> 10);
                i7 += 2;
                bArr2[i15] = (byte) (i8 >> 2);
            } else if (i9 == 4) {
                this.f9470c = 6;
                return false;
            }
            this.f9470c = i9;
            this.f9467b = i7;
            return true;
        }
    }

    public static class c extends a {

        /* renamed from: j, reason: collision with root package name */
        public static final byte[] f9473j = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 43, Constants.CMD_TYPE.CMD_OTA};

        /* renamed from: k, reason: collision with root package name */
        public static final byte[] f9474k = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 45, 95};

        /* renamed from: l, reason: collision with root package name */
        public static final /* synthetic */ boolean f9475l = true;

        /* renamed from: c, reason: collision with root package name */
        public final byte[] f9476c;

        /* renamed from: d, reason: collision with root package name */
        public int f9477d;

        /* renamed from: e, reason: collision with root package name */
        public int f9478e;

        /* renamed from: f, reason: collision with root package name */
        public final boolean f9479f;

        /* renamed from: g, reason: collision with root package name */
        public final boolean f9480g;

        /* renamed from: h, reason: collision with root package name */
        public final boolean f9481h;

        /* renamed from: i, reason: collision with root package name */
        public final byte[] f9482i;

        public c(int i2, byte[] bArr) {
            this.f9466a = bArr;
            this.f9479f = (i2 & 1) == 0;
            boolean z2 = (i2 & 2) == 0;
            this.f9480g = z2;
            this.f9481h = (i2 & 4) != 0;
            this.f9482i = (i2 & 8) == 0 ? f9473j : f9474k;
            this.f9476c = new byte[2];
            this.f9477d = 0;
            this.f9478e = z2 ? 19 : -1;
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
        /* JADX WARN: Removed duplicated region for block: B:81:0x01c0  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x01cd A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean a(byte[] r19, int r20, int r21, boolean r22) {
            /*
                Method dump skipped, instructions count: 515
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.l0.b.c.a(byte[], int, int, boolean):boolean");
        }
    }

    public static byte[] a(byte[] bArr, int i2) {
        return a(bArr, 0, bArr.length, i2);
    }

    public static byte[] b(byte[] bArr, int i2) {
        return b(bArr, 0, bArr.length, i2);
    }

    public static String c(byte[] bArr, int i2) {
        try {
            return new String(b(bArr, i2), "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    public static byte[] a(byte[] bArr, int i2, int i3, int i4) {
        C0049b c0049b = new C0049b(i4, new byte[(i3 * 3) / 4]);
        if (!c0049b.a(bArr, i2, i3, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        int i5 = c0049b.f9467b;
        byte[] bArr2 = c0049b.f9466a;
        if (i5 == bArr2.length) {
            return bArr2;
        }
        byte[] bArr3 = new byte[i5];
        System.arraycopy(bArr2, 0, bArr3, 0, i5);
        return bArr3;
    }

    @SuppressLint({"Assert"})
    public static byte[] b(byte[] bArr, int i2, int i3, int i4) {
        c cVar = new c(i4, null);
        int i5 = (i3 / 3) * 4;
        if (!cVar.f9479f) {
            int i6 = i3 % 3;
            if (i6 == 1) {
                i5 += 2;
            } else if (i6 == 2) {
                i5 += 3;
            }
        } else if (i3 % 3 > 0) {
            i5 += 4;
        }
        if (cVar.f9480g && i3 > 0) {
            i5 += (((i3 - 1) / 57) + 1) * (cVar.f9481h ? 2 : 1);
        }
        cVar.f9466a = new byte[i5];
        cVar.a(bArr, i2, i3, true);
        if (f9465a || cVar.f9467b == i5) {
            return cVar.f9466a;
        }
        throw new AssertionError();
    }
}
