package androidx.health.platform.client.proto;

@CheckReturnValue
/* loaded from: classes.dex */
interface SchemaFactory {
    <T> Schema<T> createSchema(Class<T> cls);
}
