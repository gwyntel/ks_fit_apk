package org.mozilla.javascript;

/* loaded from: classes5.dex */
class BeanProperty {
    MemberBox getter;
    MemberBox setter;
    NativeJavaMethod setters;

    BeanProperty(MemberBox memberBox, MemberBox memberBox2, NativeJavaMethod nativeJavaMethod) {
        this.getter = memberBox;
        this.setter = memberBox2;
        this.setters = nativeJavaMethod;
    }
}
