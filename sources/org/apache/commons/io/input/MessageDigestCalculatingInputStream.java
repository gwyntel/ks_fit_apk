package org.apache.commons.io.input;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.input.ObservableInputStream;

/* loaded from: classes5.dex */
public class MessageDigestCalculatingInputStream extends ObservableInputStream {
    private final MessageDigest messageDigest;

    public static class MessageDigestMaintainingObserver extends ObservableInputStream.Observer {
        private final MessageDigest md;

        public MessageDigestMaintainingObserver(MessageDigest messageDigest) {
            this.md = messageDigest;
        }

        @Override // org.apache.commons.io.input.ObservableInputStream.Observer
        void b(int i2) {
            this.md.update((byte) i2);
        }

        @Override // org.apache.commons.io.input.ObservableInputStream.Observer
        void c(byte[] bArr, int i2, int i3) {
            this.md.update(bArr, i2, i3);
        }
    }

    public MessageDigestCalculatingInputStream(InputStream inputStream, MessageDigest messageDigest) {
        super(inputStream);
        this.messageDigest = messageDigest;
        add(new MessageDigestMaintainingObserver(messageDigest));
    }

    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }

    public MessageDigestCalculatingInputStream(InputStream inputStream, String str) throws NoSuchAlgorithmException {
        this(inputStream, MessageDigest.getInstance(str));
    }

    public MessageDigestCalculatingInputStream(InputStream inputStream) throws NoSuchAlgorithmException {
        this(inputStream, MessageDigest.getInstance(Utils.MD5));
    }
}
