package okio;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public final class HashingSink extends ForwardingSink {

    @Nullable
    private final Mac mac;

    @Nullable
    private final MessageDigest messageDigest;

    private HashingSink(Sink sink, String str) {
        super(sink);
        try {
            this.messageDigest = MessageDigest.getInstance(str);
            this.mac = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public static HashingSink hmacSha1(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA1");
    }

    public static HashingSink hmacSha256(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA256");
    }

    public static HashingSink hmacSha512(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA512");
    }

    public static HashingSink md5(Sink sink) {
        return new HashingSink(sink, Utils.MD5);
    }

    public static HashingSink sha1(Sink sink) {
        return new HashingSink(sink, "SHA-1");
    }

    public static HashingSink sha256(Sink sink) {
        return new HashingSink(sink, "SHA-256");
    }

    public static HashingSink sha512(Sink sink) {
        return new HashingSink(sink, "SHA-512");
    }

    public final ByteString hash() {
        MessageDigest messageDigest = this.messageDigest;
        return ByteString.of(messageDigest != null ? messageDigest.digest() : this.mac.doFinal());
    }

    @Override // okio.ForwardingSink, okio.Sink
    public void write(Buffer buffer, long j2) throws IllegalStateException, IOException {
        Util.checkOffsetAndCount(buffer.f26470b, 0L, j2);
        Segment segment = buffer.f26469a;
        long j3 = 0;
        while (j3 < j2) {
            int iMin = (int) Math.min(j2 - j3, segment.f26495c - segment.f26494b);
            MessageDigest messageDigest = this.messageDigest;
            if (messageDigest != null) {
                messageDigest.update(segment.f26493a, segment.f26494b, iMin);
            } else {
                this.mac.update(segment.f26493a, segment.f26494b, iMin);
            }
            j3 += iMin;
            segment = segment.f26498f;
        }
        super.write(buffer, j2);
    }

    private HashingSink(Sink sink, ByteString byteString, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        super(sink);
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
