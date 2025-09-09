package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;

/* loaded from: classes2.dex */
public final class EmptyProto {

    /* renamed from: a, reason: collision with root package name */
    static final Descriptors.Descriptor f15217a;

    /* renamed from: b, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15218b;
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u001bgoogle/protobuf/empty.proto\u0012\u000fgoogle.protobuf\"\u0007\n\u0005EmptyB}\n\u0013com.google.protobufB\nEmptyProtoP\u0001Z.google.golang.org/protobuf/types/known/emptypbø\u0001\u0001¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[0]);

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        f15217a = descriptor2;
        f15218b = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[0]);
    }

    private EmptyProto() {
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    public static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
        registerAllExtensions((ExtensionRegistryLite) extensionRegistry);
    }
}
