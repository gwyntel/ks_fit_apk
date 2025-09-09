package anet.channel.bytes;

import android.support.v4.media.session.PlaybackStateCompat;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class a {
    public static final int MAX_POOL_SIZE = 524288;
    public static final String TAG = "awcn.ByteArrayPool";

    /* renamed from: a, reason: collision with root package name */
    private final TreeSet<ByteArray> f6695a = new TreeSet<>();

    /* renamed from: b, reason: collision with root package name */
    private final ByteArray f6696b = ByteArray.create(0);

    /* renamed from: c, reason: collision with root package name */
    private final Random f6697c = new Random();

    /* renamed from: d, reason: collision with root package name */
    private long f6698d = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: anet.channel.bytes.a$a, reason: collision with other inner class name */
    public static class C0009a {

        /* renamed from: a, reason: collision with root package name */
        public static a f6699a = new a();

        C0009a() {
        }
    }

    public synchronized void a(ByteArray byteArray) {
        if (byteArray != null) {
            try {
                int i2 = byteArray.bufferLength;
                if (i2 < 524288) {
                    this.f6698d += i2;
                    this.f6695a.add(byteArray);
                    while (this.f6698d > PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                        this.f6698d -= (this.f6697c.nextBoolean() ? this.f6695a.pollFirst() : this.f6695a.pollLast()).bufferLength;
                    }
                }
            } finally {
            }
        }
    }

    public synchronized ByteArray a(int i2) {
        if (i2 >= 524288) {
            return ByteArray.create(i2);
        }
        ByteArray byteArray = this.f6696b;
        byteArray.bufferLength = i2;
        ByteArray byteArrayCeiling = this.f6695a.ceiling(byteArray);
        if (byteArrayCeiling == null) {
            byteArrayCeiling = ByteArray.create(i2);
        } else {
            Arrays.fill(byteArrayCeiling.buffer, (byte) 0);
            byteArrayCeiling.dataLength = 0;
            this.f6695a.remove(byteArrayCeiling);
            this.f6698d -= byteArrayCeiling.bufferLength;
        }
        return byteArrayCeiling;
    }

    public ByteArray a(byte[] bArr, int i2) {
        ByteArray byteArrayA = a(i2);
        System.arraycopy(bArr, 0, byteArrayA.buffer, 0, i2);
        byteArrayA.dataLength = i2;
        return byteArrayA;
    }
}
