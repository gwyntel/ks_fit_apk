package com.google.protobuf;

import java.util.List;

/* loaded from: classes2.dex */
public interface ApiOrBuilder extends MessageOrBuilder {
    Method getMethods(int i2);

    int getMethodsCount();

    List<Method> getMethodsList();

    MethodOrBuilder getMethodsOrBuilder(int i2);

    List<? extends MethodOrBuilder> getMethodsOrBuilderList();

    Mixin getMixins(int i2);

    int getMixinsCount();

    List<Mixin> getMixinsList();

    MixinOrBuilder getMixinsOrBuilder(int i2);

    List<? extends MixinOrBuilder> getMixinsOrBuilderList();

    String getName();

    ByteString getNameBytes();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();

    OptionOrBuilder getOptionsOrBuilder(int i2);

    List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    SourceContext getSourceContext();

    SourceContextOrBuilder getSourceContextOrBuilder();

    Syntax getSyntax();

    int getSyntaxValue();

    String getVersion();

    ByteString getVersionBytes();

    boolean hasSourceContext();
}
