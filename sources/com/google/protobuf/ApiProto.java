package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;

/* loaded from: classes2.dex */
public final class ApiProto {

    /* renamed from: a, reason: collision with root package name */
    static final Descriptors.Descriptor f15172a;

    /* renamed from: b, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15173b;

    /* renamed from: c, reason: collision with root package name */
    static final Descriptors.Descriptor f15174c;

    /* renamed from: d, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15175d;
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0019google/protobuf/api.proto\u0012\u000fgoogle.protobuf\u001a$google/protobuf/source_context.proto\u001a\u001agoogle/protobuf/type.proto\"Á\u0002\n\u0003Api\u0012\u0012\n\u0004name\u0018\u0001 \u0001(\tR\u0004name\u00121\n\u0007methods\u0018\u0002 \u0003(\u000b2\u0017.google.protobuf.MethodR\u0007methods\u00121\n\u0007options\u0018\u0003 \u0003(\u000b2\u0017.google.protobuf.OptionR\u0007options\u0012\u0018\n\u0007version\u0018\u0004 \u0001(\tR\u0007version\u0012E\n\u000esource_context\u0018\u0005 \u0001(\u000b2\u001e.google.protobuf.SourceContextR\rsourceContext\u0012.\n\u0006mixins\u0018\u0006 \u0003(\u000b2\u0016.google.protobuf.MixinR\u0006mixins\u0012/\n\u0006syntax\u0018\u0007 \u0001(\u000e2\u0017.google.protobuf.SyntaxR\u0006syntax\"²\u0002\n\u0006Method\u0012\u0012\n\u0004name\u0018\u0001 \u0001(\tR\u0004name\u0012(\n\u0010request_type_url\u0018\u0002 \u0001(\tR\u000erequestTypeUrl\u0012+\n\u0011request_streaming\u0018\u0003 \u0001(\bR\u0010requestStreaming\u0012*\n\u0011response_type_url\u0018\u0004 \u0001(\tR\u000fresponseTypeUrl\u0012-\n\u0012response_streaming\u0018\u0005 \u0001(\bR\u0011responseStreaming\u00121\n\u0007options\u0018\u0006 \u0003(\u000b2\u0017.google.protobuf.OptionR\u0007options\u0012/\n\u0006syntax\u0018\u0007 \u0001(\u000e2\u0017.google.protobuf.SyntaxR\u0006syntax\"/\n\u0005Mixin\u0012\u0012\n\u0004name\u0018\u0001 \u0001(\tR\u0004name\u0012\u0012\n\u0004root\u0018\u0002 \u0001(\tR\u0004rootBv\n\u0013com.google.protobufB\bApiProtoP\u0001Z,google.golang.org/protobuf/types/known/apipb¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[]{SourceContextProto.getDescriptor(), TypeProto.getDescriptor()});

    /* renamed from: e, reason: collision with root package name */
    static final Descriptors.Descriptor f15176e;

    /* renamed from: f, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15177f;

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        f15172a = descriptor2;
        f15173b = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"Name", "Methods", "Options", com.alipay.sdk.m.p.e.f9612g, "SourceContext", "Mixins", "Syntax"});
        Descriptors.Descriptor descriptor3 = getDescriptor().getMessageTypes().get(1);
        f15174c = descriptor3;
        f15175d = new GeneratedMessageV3.FieldAccessorTable(descriptor3, new String[]{"Name", "RequestTypeUrl", "RequestStreaming", "ResponseTypeUrl", "ResponseStreaming", "Options", "Syntax"});
        Descriptors.Descriptor descriptor4 = getDescriptor().getMessageTypes().get(2);
        f15176e = descriptor4;
        f15177f = new GeneratedMessageV3.FieldAccessorTable(descriptor4, new String[]{"Name", "Root"});
        SourceContextProto.getDescriptor();
        TypeProto.getDescriptor();
    }

    private ApiProto() {
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
