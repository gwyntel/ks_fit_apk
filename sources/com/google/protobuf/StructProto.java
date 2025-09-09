package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;

/* loaded from: classes2.dex */
public final class StructProto {

    /* renamed from: a, reason: collision with root package name */
    static final Descriptors.Descriptor f15332a;

    /* renamed from: b, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15333b;

    /* renamed from: c, reason: collision with root package name */
    static final Descriptors.Descriptor f15334c;

    /* renamed from: d, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15335d;
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u001cgoogle/protobuf/struct.proto\u0012\u000fgoogle.protobuf\"\u0098\u0001\n\u0006Struct\u0012;\n\u0006fields\u0018\u0001 \u0003(\u000b2#.google.protobuf.Struct.FieldsEntryR\u0006fields\u001aQ\n\u000bFieldsEntry\u0012\u0010\n\u0003key\u0018\u0001 \u0001(\tR\u0003key\u0012,\n\u0005value\u0018\u0002 \u0001(\u000b2\u0016.google.protobuf.ValueR\u0005value:\u00028\u0001\"²\u0002\n\u0005Value\u0012;\n\nnull_value\u0018\u0001 \u0001(\u000e2\u001a.google.protobuf.NullValueH\u0000R\tnullValue\u0012#\n\fnumber_value\u0018\u0002 \u0001(\u0001H\u0000R\u000bnumberValue\u0012#\n\fstring_value\u0018\u0003 \u0001(\tH\u0000R\u000bstringValue\u0012\u001f\n\nbool_value\u0018\u0004 \u0001(\bH\u0000R\tboolValue\u0012<\n\fstruct_value\u0018\u0005 \u0001(\u000b2\u0017.google.protobuf.StructH\u0000R\u000bstructValue\u0012;\n\nlist_value\u0018\u0006 \u0001(\u000b2\u001a.google.protobuf.ListValueH\u0000R\tlistValueB\u0006\n\u0004kind\";\n\tListValue\u0012.\n\u0006values\u0018\u0001 \u0003(\u000b2\u0016.google.protobuf.ValueR\u0006values*\u001b\n\tNullValue\u0012\u000e\n\nNULL_VALUE\u0010\u0000B\u007f\n\u0013com.google.protobufB\u000bStructProtoP\u0001Z/google.golang.org/protobuf/types/known/structpbø\u0001\u0001¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[0]);

    /* renamed from: e, reason: collision with root package name */
    static final Descriptors.Descriptor f15336e;

    /* renamed from: f, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15337f;

    /* renamed from: g, reason: collision with root package name */
    static final Descriptors.Descriptor f15338g;

    /* renamed from: h, reason: collision with root package name */
    static final GeneratedMessageV3.FieldAccessorTable f15339h;

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        f15332a = descriptor2;
        f15333b = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"Fields"});
        Descriptors.Descriptor descriptor3 = descriptor2.getNestedTypes().get(0);
        f15334c = descriptor3;
        f15335d = new GeneratedMessageV3.FieldAccessorTable(descriptor3, new String[]{"Key", "Value"});
        Descriptors.Descriptor descriptor4 = getDescriptor().getMessageTypes().get(1);
        f15336e = descriptor4;
        f15337f = new GeneratedMessageV3.FieldAccessorTable(descriptor4, new String[]{"NullValue", "NumberValue", "StringValue", "BoolValue", "StructValue", "ListValue", "Kind"});
        Descriptors.Descriptor descriptor5 = getDescriptor().getMessageTypes().get(2);
        f15338g = descriptor5;
        f15339h = new GeneratedMessageV3.FieldAccessorTable(descriptor5, new String[]{"Values"});
    }

    private StructProto() {
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
