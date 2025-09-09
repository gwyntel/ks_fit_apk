package com.google.protobuf;

@CheckReturnValue
/* loaded from: classes2.dex */
abstract class UnknownFieldSchema<T, B> {
    UnknownFieldSchema() {
    }

    abstract void a(Object obj, int i2, int i3);

    abstract void b(Object obj, int i2, long j2);

    abstract void c(Object obj, int i2, Object obj2);

    abstract void d(Object obj, int i2, ByteString byteString);

    abstract void e(Object obj, int i2, long j2);

    abstract Object f(Object obj);

    abstract Object g(Object obj);

    abstract int h(Object obj);

    abstract int i(Object obj);

    abstract void j(Object obj);

    abstract Object k(Object obj, Object obj2);

    final void l(Object obj, Reader reader) {
        while (reader.getFieldNumber() != Integer.MAX_VALUE && m(obj, reader)) {
        }
    }

    final boolean m(Object obj, Reader reader) throws InvalidProtocolBufferException {
        int tag = reader.getTag();
        int tagFieldNumber = WireFormat.getTagFieldNumber(tag);
        int tagWireType = WireFormat.getTagWireType(tag);
        if (tagWireType == 0) {
            e(obj, tagFieldNumber, reader.readInt64());
            return true;
        }
        if (tagWireType == 1) {
            b(obj, tagFieldNumber, reader.readFixed64());
            return true;
        }
        if (tagWireType == 2) {
            d(obj, tagFieldNumber, reader.readBytes());
            return true;
        }
        if (tagWireType != 3) {
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            a(obj, tagFieldNumber, reader.readFixed32());
            return true;
        }
        Object objN = n();
        int iA = WireFormat.a(tagFieldNumber, 4);
        l(objN, reader);
        if (iA != reader.getTag()) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
        c(obj, tagFieldNumber, r(objN));
        return true;
    }

    abstract Object n();

    abstract void o(Object obj, Object obj2);

    abstract void p(Object obj, Object obj2);

    abstract boolean q(Reader reader);

    abstract Object r(Object obj);

    abstract void s(Object obj, Writer writer);

    abstract void t(Object obj, Writer writer);
}
