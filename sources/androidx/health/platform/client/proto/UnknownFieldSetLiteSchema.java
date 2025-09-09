package androidx.health.platform.client.proto;

import java.io.IOException;

@CheckReturnValue
/* loaded from: classes.dex */
class UnknownFieldSetLiteSchema extends UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> {
    UnknownFieldSetLiteSchema() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: A, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSetLite g(Object obj) {
        return ((GeneratedMessageLite) obj).unknownFields;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: B, reason: merged with bridge method [inline-methods] */
    public int h(UnknownFieldSetLite unknownFieldSetLite) {
        return unknownFieldSetLite.getSerializedSize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: C, reason: merged with bridge method [inline-methods] */
    public int i(UnknownFieldSetLite unknownFieldSetLite) {
        return unknownFieldSetLite.getSerializedSizeAsMessageSet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: D, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSetLite k(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
        return UnknownFieldSetLite.getDefaultInstance().equals(unknownFieldSetLite2) ? unknownFieldSetLite : UnknownFieldSetLite.getDefaultInstance().equals(unknownFieldSetLite) ? UnknownFieldSetLite.f(unknownFieldSetLite, unknownFieldSetLite2) : unknownFieldSetLite.c(unknownFieldSetLite2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: E, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSetLite n() {
        return UnknownFieldSetLite.g();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: F, reason: merged with bridge method [inline-methods] */
    public void o(Object obj, UnknownFieldSetLite unknownFieldSetLite) {
        p(obj, unknownFieldSetLite);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: G, reason: merged with bridge method [inline-methods] */
    public void p(Object obj, UnknownFieldSetLite unknownFieldSetLite) {
        ((GeneratedMessageLite) obj).unknownFields = unknownFieldSetLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: H, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSetLite r(UnknownFieldSetLite unknownFieldSetLite) {
        unknownFieldSetLite.makeImmutable();
        return unknownFieldSetLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: I, reason: merged with bridge method [inline-methods] */
    public void s(UnknownFieldSetLite unknownFieldSetLite, Writer writer) throws IOException {
        unknownFieldSetLite.j(writer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: J, reason: merged with bridge method [inline-methods] */
    public void t(UnknownFieldSetLite unknownFieldSetLite, Writer writer) throws IOException {
        unknownFieldSetLite.writeTo(writer);
    }

    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    void j(Object obj) {
        g(obj).makeImmutable();
    }

    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    boolean q(Reader reader) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: u, reason: merged with bridge method [inline-methods] */
    public void a(UnknownFieldSetLite unknownFieldSetLite, int i2, int i3) {
        unknownFieldSetLite.i(WireFormat.a(i2, 5), Integer.valueOf(i3));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: v, reason: merged with bridge method [inline-methods] */
    public void b(UnknownFieldSetLite unknownFieldSetLite, int i2, long j2) {
        unknownFieldSetLite.i(WireFormat.a(i2, 1), Long.valueOf(j2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: w, reason: merged with bridge method [inline-methods] */
    public void c(UnknownFieldSetLite unknownFieldSetLite, int i2, UnknownFieldSetLite unknownFieldSetLite2) {
        unknownFieldSetLite.i(WireFormat.a(i2, 3), unknownFieldSetLite2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: x, reason: merged with bridge method [inline-methods] */
    public void d(UnknownFieldSetLite unknownFieldSetLite, int i2, ByteString byteString) {
        unknownFieldSetLite.i(WireFormat.a(i2, 2), byteString);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: y, reason: merged with bridge method [inline-methods] */
    public void e(UnknownFieldSetLite unknownFieldSetLite, int i2, long j2) {
        unknownFieldSetLite.i(WireFormat.a(i2, 0), Long.valueOf(j2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.health.platform.client.proto.UnknownFieldSchema
    /* renamed from: z, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSetLite f(Object obj) {
        UnknownFieldSetLite unknownFieldSetLiteG = g(obj);
        if (unknownFieldSetLiteG != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFieldSetLiteG;
        }
        UnknownFieldSetLite unknownFieldSetLiteG2 = UnknownFieldSetLite.g();
        p(obj, unknownFieldSetLiteG2);
        return unknownFieldSetLiteG2;
    }
}
