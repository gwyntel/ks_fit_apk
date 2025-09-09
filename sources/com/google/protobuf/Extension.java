package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.MessageLite;

/* loaded from: classes2.dex */
public abstract class Extension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {

    protected enum ExtensionType {
        IMMUTABLE,
        MUTABLE,
        PROTO1
    }

    public enum MessageType {
        PROTO1,
        PROTO2
    }

    @Override // com.google.protobuf.ExtensionLite
    final boolean a() {
        return false;
    }

    protected abstract Object b(Object obj);

    protected abstract ExtensionType c();

    protected abstract Object d(Object obj);

    protected abstract Object e(Object obj);

    protected abstract Object f(Object obj);

    public abstract Descriptors.FieldDescriptor getDescriptor();

    @Override // com.google.protobuf.ExtensionLite
    public abstract Message getMessageDefaultInstance();

    public MessageType getMessageType() {
        return MessageType.PROTO2;
    }
}
