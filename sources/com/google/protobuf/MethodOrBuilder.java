package com.google.protobuf;

import java.util.List;

/* loaded from: classes2.dex */
public interface MethodOrBuilder extends MessageOrBuilder {
    String getName();

    ByteString getNameBytes();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();

    OptionOrBuilder getOptionsOrBuilder(int i2);

    List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    boolean getRequestStreaming();

    String getRequestTypeUrl();

    ByteString getRequestTypeUrlBytes();

    boolean getResponseStreaming();

    String getResponseTypeUrl();

    ByteString getResponseTypeUrlBytes();

    Syntax getSyntax();

    int getSyntaxValue();
}
