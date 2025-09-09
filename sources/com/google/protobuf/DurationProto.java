package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;

/* loaded from: classes2.dex */
public final class DurationProto {

    /* renamed from: a, reason: collision with root package name */
    static final Descriptors.Descriptor f15213a;

    /* renamed from: b, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15214b;
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u001egoogle/protobuf/duration.proto\u0012\u000fgoogle.protobuf\":\n\bDuration\u0012\u0018\n\u0007seconds\u0018\u0001 \u0001(\u0003R\u0007seconds\u0012\u0014\n\u0005nanos\u0018\u0002 \u0001(\u0005R\u0005nanosB\u0083\u0001\n\u0013com.google.protobufB\rDurationProtoP\u0001Z1google.golang.org/protobuf/types/known/durationpbø\u0001\u0001¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[0]);

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        f15213a = descriptor2;
        f15214b = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"Seconds", "Nanos"});
    }

    private DurationProto() {
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
