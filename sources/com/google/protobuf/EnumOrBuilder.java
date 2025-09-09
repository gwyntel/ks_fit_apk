package com.google.protobuf;

import java.util.List;

/* loaded from: classes2.dex */
public interface EnumOrBuilder extends MessageOrBuilder {
    EnumValue getEnumvalue(int i2);

    int getEnumvalueCount();

    List<EnumValue> getEnumvalueList();

    EnumValueOrBuilder getEnumvalueOrBuilder(int i2);

    List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList();

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

    boolean hasSourceContext();
}
