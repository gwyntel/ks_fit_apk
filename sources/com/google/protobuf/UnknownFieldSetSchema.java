package com.google.protobuf;

import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;

/* loaded from: classes2.dex */
class UnknownFieldSetSchema extends UnknownFieldSchema<UnknownFieldSet, UnknownFieldSet.Builder> {
    private final boolean proto3;

    public UnknownFieldSetSchema(boolean z2) {
        this.proto3 = z2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: A, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSet g(Object obj) {
        return ((GeneratedMessageV3) obj).unknownFields;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: B, reason: merged with bridge method [inline-methods] */
    public int h(UnknownFieldSet unknownFieldSet) {
        return unknownFieldSet.getSerializedSize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: C, reason: merged with bridge method [inline-methods] */
    public int i(UnknownFieldSet unknownFieldSet) {
        return unknownFieldSet.getSerializedSizeAsMessageSet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: D, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSet k(UnknownFieldSet unknownFieldSet, UnknownFieldSet unknownFieldSet2) {
        return unknownFieldSet.toBuilder().mergeFrom(unknownFieldSet2).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: E, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSet.Builder n() {
        return UnknownFieldSet.newBuilder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: F, reason: merged with bridge method [inline-methods] */
    public void o(Object obj, UnknownFieldSet.Builder builder) {
        ((GeneratedMessageV3) obj).unknownFields = builder.build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: G, reason: merged with bridge method [inline-methods] */
    public void p(Object obj, UnknownFieldSet unknownFieldSet) {
        ((GeneratedMessageV3) obj).unknownFields = unknownFieldSet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: H, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSet r(UnknownFieldSet.Builder builder) {
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: I, reason: merged with bridge method [inline-methods] */
    public void s(UnknownFieldSet unknownFieldSet, Writer writer) throws IOException {
        unknownFieldSet.b(writer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: J, reason: merged with bridge method [inline-methods] */
    public void t(UnknownFieldSet unknownFieldSet, Writer writer) throws IOException {
        unknownFieldSet.c(writer);
    }

    @Override // com.google.protobuf.UnknownFieldSchema
    void j(Object obj) {
    }

    @Override // com.google.protobuf.UnknownFieldSchema
    boolean q(Reader reader) {
        return reader.shouldDiscardUnknownFields();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: u, reason: merged with bridge method [inline-methods] */
    public void a(UnknownFieldSet.Builder builder, int i2, int i3) {
        builder.mergeField(i2, UnknownFieldSet.Field.newBuilder().addFixed32(i3).build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: v, reason: merged with bridge method [inline-methods] */
    public void b(UnknownFieldSet.Builder builder, int i2, long j2) {
        builder.mergeField(i2, UnknownFieldSet.Field.newBuilder().addFixed64(j2).build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: w, reason: merged with bridge method [inline-methods] */
    public void c(UnknownFieldSet.Builder builder, int i2, UnknownFieldSet unknownFieldSet) {
        builder.mergeField(i2, UnknownFieldSet.Field.newBuilder().addGroup(unknownFieldSet).build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: x, reason: merged with bridge method [inline-methods] */
    public void d(UnknownFieldSet.Builder builder, int i2, ByteString byteString) {
        builder.mergeField(i2, UnknownFieldSet.Field.newBuilder().addLengthDelimited(byteString).build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: y, reason: merged with bridge method [inline-methods] */
    public void e(UnknownFieldSet.Builder builder, int i2, long j2) {
        builder.mergeField(i2, UnknownFieldSet.Field.newBuilder().addVarint(j2).build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.UnknownFieldSchema
    /* renamed from: z, reason: merged with bridge method [inline-methods] */
    public UnknownFieldSet.Builder f(Object obj) {
        return ((GeneratedMessageV3) obj).unknownFields.toBuilder();
    }
}
