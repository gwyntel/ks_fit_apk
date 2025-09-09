package androidx.health.platform.client.proto;

import org.apache.commons.io.IOUtils;

/* loaded from: classes.dex */
final class TextFormatEscaper {

    /* renamed from: androidx.health.platform.client.proto.TextFormatEscaper$2, reason: invalid class name */
    final class AnonymousClass2 implements ByteSequence {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ byte[] f4494a;

        @Override // androidx.health.platform.client.proto.TextFormatEscaper.ByteSequence
        public byte byteAt(int i2) {
            return this.f4494a[i2];
        }

        @Override // androidx.health.platform.client.proto.TextFormatEscaper.ByteSequence
        public int size() {
            return this.f4494a.length;
        }
    }

    private interface ByteSequence {
        byte byteAt(int i2);

        int size();
    }

    private TextFormatEscaper() {
    }

    static String a(final ByteString byteString) {
        return b(new ByteSequence() { // from class: androidx.health.platform.client.proto.TextFormatEscaper.1
            @Override // androidx.health.platform.client.proto.TextFormatEscaper.ByteSequence
            public byte byteAt(int i2) {
                return byteString.byteAt(i2);
            }

            @Override // androidx.health.platform.client.proto.TextFormatEscaper.ByteSequence
            public int size() {
                return byteString.size();
            }
        });
    }

    static String b(ByteSequence byteSequence) {
        StringBuilder sb = new StringBuilder(byteSequence.size());
        for (int i2 = 0; i2 < byteSequence.size(); i2++) {
            byte bByteAt = byteSequence.byteAt(i2);
            if (bByteAt == 34) {
                sb.append("\\\"");
            } else if (bByteAt == 39) {
                sb.append("\\'");
            } else if (bByteAt != 92) {
                switch (bByteAt) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bByteAt < 32 || bByteAt > 126) {
                            sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                            sb.append((char) (((bByteAt >>> 6) & 3) + 48));
                            sb.append((char) (((bByteAt >>> 3) & 7) + 48));
                            sb.append((char) ((bByteAt & 7) + 48));
                            break;
                        } else {
                            sb.append((char) bByteAt);
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }

    static String c(String str) {
        return a(ByteString.copyFromUtf8(str));
    }
}
