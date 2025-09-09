package androidx.health.platform.client.proto;

@CheckReturnValue
/* loaded from: classes.dex */
final class NewInstanceSchemaLite implements NewInstanceSchema {
    NewInstanceSchemaLite() {
    }

    @Override // androidx.health.platform.client.proto.NewInstanceSchema
    public Object newInstance(Object obj) {
        return ((GeneratedMessageLite) obj).E();
    }
}
