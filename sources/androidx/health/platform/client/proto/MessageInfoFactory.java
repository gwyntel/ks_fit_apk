package androidx.health.platform.client.proto;

@CheckReturnValue
/* loaded from: classes.dex */
interface MessageInfoFactory {
    boolean isSupported(Class<?> cls);

    MessageInfo messageInfoFor(Class<?> cls);
}
