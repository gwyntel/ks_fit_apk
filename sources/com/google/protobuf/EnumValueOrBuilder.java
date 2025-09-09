package com.google.protobuf;

import java.util.List;

/* loaded from: classes2.dex */
public interface EnumValueOrBuilder extends MessageOrBuilder {
    String getName();

    ByteString getNameBytes();

    int getNumber();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();

    OptionOrBuilder getOptionsOrBuilder(int i2);

    List<? extends OptionOrBuilder> getOptionsOrBuilderList();
}
