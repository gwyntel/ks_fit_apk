package okio;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public final class HashingSource extends ForwardingSource {
    private final Mac mac;
    private final MessageDigest messageDigest;

    private HashingSource(Source source, String str) {
        super(source);
        try {
            this.messageDigest = MessageDigest.getInstance(str);
            this.mac = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public static HashingSource hmacSha1(Source source, ByteString byteString) {
        return new HashingSource(source, byteString, "HmacSHA1");
    }

    public static HashingSource hmacSha256(Source source, ByteString byteString) {
        return new HashingSource(source, byteString, "HmacSHA256");
    }

    public static HashingSource md5(Source source) {
        return new HashingSource(source, Utils.MD5);
    }

    public static HashingSource sha1(Source source) {
        return new HashingSource(source, "SHA-1");
    }

    public static HashingSource sha256(Source source) {
        return new HashingSource(source, "SHA-256");
    }

    public final ByteString hash() {
        MessageDigest messageDigest = this.messageDigest;
        return ByteString.of(messageDigest != null ? messageDigest.digest() : this.mac.doFinal());
    }

    @Override // okio.ForwardingSource, okio.Source
    public long read(Buffer buffer, long j2) throws IllegalStateException, IOException {
        long j3 = super.read(buffer, j2);
        if (j3 != -1) {
            long j4 = buffer.f26470b;
            long j5 = j4 - j3;
            Segment segment = buffer.f26469a;
            while (j4 > j5) {
                segment = segment.f26499g;
                j4 -= segment.f26495c - segment.f26494b;
            }
            while (j4 < buffer.f26470b) {
                int i2 = (int) ((segment.f26494b + j5) - j4);
                MessageDigest messageDigest = this.messageDigest;
                if (messageDigest != null) {
                    messageDigest.update(segment.f26493a, i2, segment.f26495c - i2);
                } else {
                    this.mac.update(segment.f26493a, i2, segment.f26495c - i2);
                }
                j5 = (segment.f26495c - segment.f26494b) + j4;
                segment = segment.f26498f;
                j4 = j5;
            }
        }
        return j3;
    }

    private HashingSource(Source source, ByteString byteString, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        super(source);
        try {
            Mac mac = Mac.getInstance(str);
            this.mac = mac;
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            this.messageDigest = null;
        } catch (InvalidKeyException e2) {
            throw new IllegalArgumentException(e2);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }
}
