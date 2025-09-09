package androidx.health.platform.client.proto;

@CheckReturnValue
/* loaded from: classes.dex */
interface MessageInfo {
    MessageLite getDefaultInstance();

    ProtoSyntax getSyntax();

    boolean isMessageSetWireFormat();
}
