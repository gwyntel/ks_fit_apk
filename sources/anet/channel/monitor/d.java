package anet.channel.monitor;

import anet.channel.util.ALog;
import com.kingsmith.miot.KsProperty;

/* loaded from: classes2.dex */
class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f6831a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ long f6832b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ long f6833c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ b f6834d;

    d(b bVar, long j2, long j3, long j4) {
        this.f6834d = bVar;
        this.f6831a = j2;
        this.f6832b = j3;
        this.f6833c = j4;
    }

    @Override // java.lang.Runnable
    public void run() {
        b.f6815a++;
        b.f6819e += this.f6831a;
        if (b.f6815a == 1) {
            b.f6818d = this.f6832b - this.f6833c;
        }
        int i2 = b.f6815a;
        if (i2 >= 2 && i2 <= 3) {
            long j2 = this.f6833c;
            long j3 = b.f6817c;
            if (j2 >= j3) {
                b.f6818d += this.f6832b - j2;
            } else if (j2 < j3) {
                long j4 = this.f6832b;
                if (j4 >= j3) {
                    long j5 = b.f6818d + (j4 - j2);
                    b.f6818d = j5;
                    b.f6818d = j5 - (b.f6817c - j2);
                }
            }
        }
        b.f6816b = this.f6833c;
        b.f6817c = this.f6832b;
        if (b.f6815a == 3) {
            b.f6823i = (long) this.f6834d.f6828n.a(b.f6819e, b.f6818d);
            b.f6820f++;
            b.b(this.f6834d);
            if (b.f6820f > 30) {
                this.f6834d.f6828n.a();
                b.f6820f = 3L;
            }
            double d2 = (b.f6823i * 0.68d) + (b.f6822h * 0.27d) + (b.f6821g * 0.05d);
            b.f6821g = b.f6822h;
            b.f6822h = b.f6823i;
            if (b.f6823i < b.f6821g * 0.65d || b.f6823i > b.f6821g * 2.0d) {
                b.f6823i = d2;
            }
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.BandWidthSampler", "NetworkSpeed", null, "mKalmanDataSize", Long.valueOf(b.f6819e), "mKalmanTimeUsed", Long.valueOf(b.f6818d), KsProperty.Speed, Double.valueOf(b.f6823i), "mSpeedKalmanCount", Long.valueOf(b.f6820f));
            }
            if (this.f6834d.f6827m > 5 || b.f6820f == 2) {
                a.a().a(b.f6823i);
                this.f6834d.f6827m = 0;
                this.f6834d.f6826l = b.f6823i < b.f6824j ? 1 : 5;
                ALog.i("awcn.BandWidthSampler", "NetworkSpeed notification!", null, "Send Network quality notification.");
            }
            b.f6818d = 0L;
            b.f6819e = 0L;
            b.f6815a = 0;
        }
    }
}
