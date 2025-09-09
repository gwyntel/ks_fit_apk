package com.google.protobuf;

@CheckReturnValue
/* loaded from: classes2.dex */
interface SchemaFactory {
    <T> Schema<T> createSchema(Class<T> cls);
}
