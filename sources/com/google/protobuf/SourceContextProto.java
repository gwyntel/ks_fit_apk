package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;

/* loaded from: classes2.dex */
public final class SourceContextProto {

    /* renamed from: a, reason: collision with root package name */
    static final Descriptors.Descriptor f15329a;

    /* renamed from: b, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15330b;
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n$google/protobuf/source_context.proto\u0012\u000fgoogle.protobuf\",\n\rSourceContext\u0012\u001b\n\tfile_name\u0018\u0001 \u0001(\tR\bfileNameB\u008a\u0001\n\u0013com.google.protobufB\u0012SourceContextProtoP\u0001Z6google.golang.org/protobuf/types/known/sourcecontextpb¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[0]);

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        f15329a = descriptor2;
        f15330b = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"FileName"});
    }

    private SourceContextProto() {
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
