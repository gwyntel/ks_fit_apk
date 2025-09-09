package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;

/* loaded from: classes2.dex */
public final class AnyProto {

    /* renamed from: a, reason: collision with root package name */
    static final Descriptors.Descriptor f15170a;

    /* renamed from: b, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15171b;
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0019google/protobuf/any.proto\u0012\u000fgoogle.protobuf\"6\n\u0003Any\u0012\u0019\n\btype_url\u0018\u0001 \u0001(\tR\u0007typeUrl\u0012\u0014\n\u0005value\u0018\u0002 \u0001(\fR\u0005valueBv\n\u0013com.google.protobufB\bAnyProtoP\u0001Z,google.golang.org/protobuf/types/known/anypb¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[0]);

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        f15170a = descriptor2;
        f15171b = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"TypeUrl", "Value"});
    }

    private AnyProto() {
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
