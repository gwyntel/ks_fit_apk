package c.a.b.b;

import c.a.b.i;
import c.a.d.g;
import c.a.d.h;

/* loaded from: classes2.dex */
public abstract class b implements i, g, a {

    /* renamed from: a, reason: collision with root package name */
    public static final long[] f8001a = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};

    /* renamed from: d, reason: collision with root package name */
    public long f8004d;

    /* renamed from: e, reason: collision with root package name */
    public long f8005e;

    /* renamed from: f, reason: collision with root package name */
    public long f8006f;

    /* renamed from: g, reason: collision with root package name */
    public long f8007g;

    /* renamed from: h, reason: collision with root package name */
    public long f8008h;

    /* renamed from: i, reason: collision with root package name */
    public long f8009i;

    /* renamed from: j, reason: collision with root package name */
    public long f8010j;

    /* renamed from: k, reason: collision with root package name */
    public long f8011k;

    /* renamed from: l, reason: collision with root package name */
    public long f8012l;

    /* renamed from: m, reason: collision with root package name */
    public long f8013m;

    /* renamed from: o, reason: collision with root package name */
    public int f8015o;

    /* renamed from: b, reason: collision with root package name */
    public byte[] f8002b = new byte[8];

    /* renamed from: n, reason: collision with root package name */
    public long[] f8014n = new long[80];

    /* renamed from: c, reason: collision with root package name */
    public int f8003c = 0;

    public b() {
        d();
    }

    public final long a(long j2) {
        return (j2 >>> 7) ^ (((j2 << 63) | (j2 >>> 1)) ^ ((j2 << 56) | (j2 >>> 8)));
    }

    public final long b(long j2) {
        return (j2 >>> 6) ^ (((j2 << 45) | (j2 >>> 19)) ^ ((j2 << 3) | (j2 >>> 61)));
    }

    public final long c(long j2) {
        return ((j2 >>> 39) | (j2 << 25)) ^ (((j2 << 36) | (j2 >>> 28)) ^ ((j2 << 30) | (j2 >>> 34)));
    }

    public final long d(long j2) {
        return ((j2 >>> 41) | (j2 << 23)) ^ (((j2 << 50) | (j2 >>> 14)) ^ ((j2 << 46) | (j2 >>> 18)));
    }

    public final long a(long j2, long j3, long j4) {
        return ((~j2) & j4) ^ (j3 & j2);
    }

    public final long b(long j2, long j3, long j4) {
        return ((j2 & j4) ^ (j2 & j3)) ^ (j3 & j4);
    }

    public void c() {
        a();
        for (int i2 = 16; i2 <= 79; i2++) {
            long[] jArr = this.f8014n;
            long jB = b(jArr[i2 - 2]);
            long[] jArr2 = this.f8014n;
            jArr[i2] = jB + jArr2[i2 - 7] + a(jArr2[i2 - 15]) + this.f8014n[i2 - 16];
        }
        long j2 = this.f8006f;
        long j3 = this.f8007g;
        long j4 = this.f8008h;
        long j5 = this.f8009i;
        long j6 = this.f8010j;
        long j7 = this.f8011k;
        long j8 = this.f8012l;
        long j9 = j7;
        long j10 = j5;
        int i3 = 0;
        long jC = j3;
        long j11 = j4;
        long j12 = j6;
        int i4 = 0;
        long j13 = this.f8013m;
        long j14 = j2;
        long j15 = j8;
        while (i4 < 10) {
            long j16 = j12;
            long jD = d(j12) + a(j12, j9, j15);
            long[] jArr3 = f8001a;
            int i5 = i3 + 1;
            long j17 = j13 + jD + jArr3[i3] + this.f8014n[i3];
            long j18 = j10 + j17;
            long jC2 = j17 + c(j14) + b(j14, jC, j11);
            int i6 = i3 + 2;
            long jD2 = j15 + d(j18) + a(j18, j16, j9) + jArr3[i5] + this.f8014n[i5];
            long j19 = j11 + jD2;
            long jC3 = jD2 + c(jC2) + b(jC2, j14, jC);
            int i7 = i3 + 3;
            long jD3 = j9 + d(j19) + a(j19, j18, j16) + jArr3[i6] + this.f8014n[i6];
            long j20 = jC + jD3;
            long jC4 = jD3 + c(jC3) + b(jC3, jC2, j14);
            int i8 = i3 + 4;
            long jD4 = j16 + d(j20) + a(j20, j19, j18) + jArr3[i7] + this.f8014n[i7];
            long j21 = j14 + jD4;
            long jC5 = jD4 + c(jC4) + b(jC4, jC3, jC2);
            int i9 = i3 + 5;
            long jD5 = j18 + d(j21) + a(j21, j20, j19) + jArr3[i8] + this.f8014n[i8];
            long j22 = jC2 + jD5;
            long jC6 = jD5 + c(jC5) + b(jC5, jC4, jC3);
            int i10 = i3 + 6;
            long jD6 = j19 + d(j22) + a(j22, j21, j20) + jArr3[i9] + this.f8014n[i9];
            long j23 = jC3 + jD6;
            long jC7 = jD6 + c(jC6) + b(jC6, jC5, jC4);
            j15 = j23;
            int i11 = i3 + 7;
            long jD7 = j20 + d(j23) + a(j23, j22, j21) + jArr3[i10] + this.f8014n[i10];
            long j24 = jC4 + jD7;
            j9 = j24;
            jC = jD7 + c(jC7) + b(jC7, jC6, jC5);
            i3 += 8;
            long jD8 = j21 + d(j24) + a(j24, j15, j22) + jArr3[i11] + this.f8014n[i11];
            long jC8 = jD8 + c(jC) + b(jC, jC7, jC6);
            i4++;
            j12 = jC5 + jD8;
            j11 = jC7;
            j13 = j22;
            j10 = jC6;
            j14 = jC8;
        }
        this.f8006f += j14;
        this.f8007g += jC;
        this.f8008h += j11;
        this.f8009i += j10;
        this.f8010j += j12;
        this.f8011k += j9;
        this.f8012l += j15;
        this.f8013m += j13;
        this.f8015o = 0;
        for (int i12 = 0; i12 < 16; i12++) {
            this.f8014n[i12] = 0;
        }
    }

    public void d() {
        this.f8004d = 0L;
        this.f8005e = 0L;
        int i2 = 0;
        this.f8003c = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.f8002b;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = 0;
            i3++;
        }
        this.f8015o = 0;
        while (true) {
            long[] jArr = this.f8014n;
            if (i2 == jArr.length) {
                return;
            }
            jArr[i2] = 0;
            i2++;
        }
    }

    public void a(byte b2) {
        byte[] bArr = this.f8002b;
        int i2 = this.f8003c;
        int i3 = i2 + 1;
        this.f8003c = i3;
        bArr[i2] = b2;
        if (i3 == bArr.length) {
            a(bArr, 0);
            this.f8003c = 0;
        }
        this.f8004d++;
    }

    public void b() {
        a();
        long j2 = this.f8004d << 3;
        long j3 = this.f8005e;
        a(Byte.MIN_VALUE);
        while (this.f8003c != 0) {
            a((byte) 0);
        }
        a(j2, j3);
        c();
    }

    public void a(byte[] bArr, int i2, int i3) {
        while (this.f8003c != 0 && i3 > 0) {
            a(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > this.f8002b.length) {
            a(bArr, i2);
            byte[] bArr2 = this.f8002b;
            i2 += bArr2.length;
            i3 -= bArr2.length;
            this.f8004d += bArr2.length;
        }
        while (i3 > 0) {
            a(bArr[i2]);
            i2++;
            i3--;
        }
    }

    public void a(byte[] bArr, int i2) {
        this.f8014n[this.f8015o] = h.b(bArr, i2);
        int i3 = this.f8015o + 1;
        this.f8015o = i3;
        if (i3 == 16) {
            c();
        }
    }

    public final void a() {
        long j2 = this.f8004d;
        if (j2 > 2305843009213693951L) {
            this.f8005e += j2 >>> 61;
            this.f8004d = j2 & 2305843009213693951L;
        }
    }

    public void a(long j2, long j3) {
        if (this.f8015o > 14) {
            c();
        }
        long[] jArr = this.f8014n;
        jArr[14] = j3;
        jArr[15] = j2;
    }
}
